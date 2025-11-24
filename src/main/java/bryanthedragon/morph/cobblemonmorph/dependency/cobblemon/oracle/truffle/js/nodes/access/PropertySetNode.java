
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.BooleanLocation;
import com.oracle.truffle.api.object.DoubleLocation;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.IntLocation;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.JSProxyPropertySetNode;
import com.oracle.truffle.js.nodes.access.PropertyCacheNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public class PropertySetNode
extends PropertyCacheNode<SetCacheNode> {
    private final boolean isGlobal;
    private final boolean isStrict;
    private final boolean setOwnProperty;
    private final boolean declaration;
    private final boolean superProperty;
    private final byte attributeFlags;
    private boolean propertyAssumptionCheckEnabled;
    @Node.Child
    protected SetCacheNode cacheNode;

    public static PropertySetNode create(Object key, boolean isGlobal, JSContext context, boolean isStrict) {
        boolean setOwnProperty = false;
        return PropertySetNode.createImpl(key, isGlobal, context, isStrict, false, JSAttributes.getDefault());
    }

    public static PropertySetNode createImpl(Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags) {
        return PropertySetNode.createImpl(key, isGlobal, context, isStrict, setOwnProperty, attributeFlags, false, false);
    }

    public static PropertySetNode createImpl(Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration) {
        return PropertySetNode.createImpl(key, isGlobal, context, isStrict, setOwnProperty, attributeFlags, declaration, false);
    }

    public static PropertySetNode createImpl(Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration, boolean superProperty) {
        return new PropertySetNode(key, context, isGlobal, isStrict, setOwnProperty, attributeFlags, declaration, superProperty);
    }

    public static PropertySetNode createSetHidden(HiddenKey key, JSContext context) {
        return PropertySetNode.createImpl(key, false, context, false, true, 0);
    }

    protected PropertySetNode(Object key, JSContext context, boolean isGlobal, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration, boolean superProperty) {
        super(key, context);
        assert (!setOwnProperty ? attributeFlags == JSAttributes.getDefault() : attributeFlags == (attributeFlags & 0x27));
        this.isGlobal = isGlobal;
        this.isStrict = isStrict;
        this.setOwnProperty = setOwnProperty;
        this.attributeFlags = (byte)attributeFlags;
        this.declaration = declaration;
        this.superProperty = superProperty;
    }

    public final void setValue(Object obj, Object value2) {
        this.setValue(obj, value2, obj);
    }

    public final void setValueInt(Object obj, int value2) {
        this.setValueInt(obj, value2, obj);
    }

    public final void setValueDouble(Object obj, double value2) {
        this.setValueDouble(obj, value2, obj);
    }

    public final void setValueBoolean(Object obj, boolean value2) {
        this.setValueBoolean(obj, value2, obj);
    }

    @ExplodeLoop
    protected void setValue(Object thisObj, Object value2, Object receiver) {
        SetCacheNode c = this.cacheNode;
        while (c != null) {
            block6: {
                Object castObj;
                boolean guard;
                PropertyCacheNode.ReceiverCheckNode receiverCheck;
                boolean isSimpleShapeCheck;
                block7: {
                    block5: {
                        if (c instanceof GenericPropertySetNode) {
                            ((GenericPropertySetNode)c).setValue(thisObj, value2, receiver, this, false);
                            return;
                        }
                        isSimpleShapeCheck = c.isSimpleShapeCheck();
                        receiverCheck = c.receiverCheck;
                        if (!isSimpleShapeCheck) break block5;
                        Shape shape = receiverCheck.getShape();
                        if (!PropertySetNode.isDynamicObject(thisObj, shape)) break block6;
                        JSDynamicObject jsobj = PropertySetNode.castDynamicObject(thisObj, shape);
                        guard = shape.check(jsobj);
                        castObj = jsobj;
                        if (!shape.getValidAssumption().isValid()) {
                            break;
                        }
                        break block7;
                    }
                    guard = receiverCheck.accept(thisObj);
                    castObj = thisObj;
                }
                if (guard) {
                    if (!isSimpleShapeCheck && !receiverCheck.isValid()) break;
                    if (c.setValue(castObj, value2, receiver, this, guard)) {
                        return;
                    }
                }
            }
            c = c.next;
        }
        this.deoptimize(c);
        this.setValueAndSpecialize(thisObj, value2, receiver);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean setValueAndSpecialize(Object thisObj, Object value2, Object receiver) {
        SetCacheNode c = (SetCacheNode)this.specialize(thisObj, value2);
        return c.setValue(thisObj, value2, receiver, this, false);
    }

    @ExplodeLoop
    protected void setValueInt(Object thisObj, int value2, Object receiver) {
        SetCacheNode c = this.cacheNode;
        while (c != null) {
            block6: {
                Object castObj;
                boolean guard;
                PropertyCacheNode.ReceiverCheckNode receiverCheck;
                boolean isSimpleShapeCheck;
                block7: {
                    block5: {
                        if (c instanceof GenericPropertySetNode) {
                            ((GenericPropertySetNode)c).setValueInt(thisObj, value2, receiver, this, false);
                            return;
                        }
                        isSimpleShapeCheck = c.isSimpleShapeCheck();
                        receiverCheck = c.receiverCheck;
                        if (!isSimpleShapeCheck) break block5;
                        Shape shape = receiverCheck.getShape();
                        if (!PropertySetNode.isDynamicObject(thisObj, shape)) break block6;
                        JSDynamicObject jsobj = PropertySetNode.castDynamicObject(thisObj, shape);
                        guard = shape.check(jsobj);
                        castObj = jsobj;
                        if (!shape.getValidAssumption().isValid()) {
                            break;
                        }
                        break block7;
                    }
                    guard = receiverCheck.accept(thisObj);
                    castObj = thisObj;
                }
                if (guard) {
                    if (!isSimpleShapeCheck && !receiverCheck.isValid()) break;
                    if (c.setValueInt(castObj, value2, receiver, this, guard)) {
                        return;
                    }
                }
            }
            c = c.next;
        }
        this.deoptimize(c);
        this.setValueIntAndSpecialize(thisObj, value2, receiver);
    }

    @CompilerDirectives.TruffleBoundary
    private void setValueIntAndSpecialize(Object thisObj, int value2, Object receiver) {
        SetCacheNode c = (SetCacheNode)this.specialize(thisObj, value2);
        c.setValueInt(thisObj, value2, receiver, this, false);
    }

    @ExplodeLoop
    protected void setValueDouble(Object thisObj, double value2, Object receiver) {
        SetCacheNode c = this.cacheNode;
        while (c != null) {
            block6: {
                Object castObj;
                boolean guard;
                PropertyCacheNode.ReceiverCheckNode receiverCheck;
                boolean isSimpleShapeCheck;
                block7: {
                    block5: {
                        if (c instanceof GenericPropertySetNode) {
                            ((GenericPropertySetNode)c).setValueDouble(thisObj, value2, receiver, this, false);
                            return;
                        }
                        isSimpleShapeCheck = c.isSimpleShapeCheck();
                        receiverCheck = c.receiverCheck;
                        if (!isSimpleShapeCheck) break block5;
                        Shape shape = receiverCheck.getShape();
                        if (!PropertySetNode.isDynamicObject(thisObj, shape)) break block6;
                        JSDynamicObject jsobj = PropertySetNode.castDynamicObject(thisObj, shape);
                        guard = shape.check(jsobj);
                        castObj = jsobj;
                        if (!shape.getValidAssumption().isValid()) {
                            break;
                        }
                        break block7;
                    }
                    guard = receiverCheck.accept(thisObj);
                    castObj = thisObj;
                }
                if (guard) {
                    if (!isSimpleShapeCheck && !receiverCheck.isValid()) break;
                    if (c.setValueDouble(castObj, value2, receiver, this, guard)) {
                        return;
                    }
                }
            }
            c = c.next;
        }
        this.deoptimize(c);
        this.setValueDoubleAndSpecialize(thisObj, value2, receiver);
    }

    @CompilerDirectives.TruffleBoundary
    private void setValueDoubleAndSpecialize(Object thisObj, double value2, Object receiver) {
        SetCacheNode c = (SetCacheNode)this.specialize(thisObj, value2);
        c.setValueDouble(thisObj, value2, receiver, this, false);
    }

    @ExplodeLoop
    protected void setValueBoolean(Object thisObj, boolean value2, Object receiver) {
        SetCacheNode c = this.cacheNode;
        while (c != null) {
            block6: {
                Object castObj;
                boolean guard;
                PropertyCacheNode.ReceiverCheckNode receiverCheck;
                boolean isSimpleShapeCheck;
                block7: {
                    block5: {
                        if (c instanceof GenericPropertySetNode) {
                            ((GenericPropertySetNode)c).setValueBoolean(thisObj, value2, receiver, this, false);
                            return;
                        }
                        isSimpleShapeCheck = c.isSimpleShapeCheck();
                        receiverCheck = c.receiverCheck;
                        if (!isSimpleShapeCheck) break block5;
                        Shape shape = receiverCheck.getShape();
                        if (!PropertySetNode.isDynamicObject(thisObj, shape)) break block6;
                        JSDynamicObject jsobj = PropertySetNode.castDynamicObject(thisObj, shape);
                        guard = shape.check(jsobj);
                        castObj = jsobj;
                        if (!shape.getValidAssumption().isValid()) {
                            break;
                        }
                        break block7;
                    }
                    guard = receiverCheck.accept(thisObj);
                    castObj = thisObj;
                }
                if (guard) {
                    if (!isSimpleShapeCheck && !receiverCheck.isValid()) break;
                    if (c.setValueBoolean(castObj, value2, receiver, this, guard)) {
                        return;
                    }
                }
            }
            c = c.next;
        }
        this.deoptimize(c);
        this.setValueBooleanAndSpecialize(thisObj, value2, receiver);
    }

    @CompilerDirectives.TruffleBoundary
    private void setValueBooleanAndSpecialize(Object thisObj, boolean value2, Object receiver) {
        SetCacheNode c = (SetCacheNode)this.specialize(thisObj, value2);
        c.setValueBoolean(thisObj, value2, receiver, this, false);
    }

    @Override
    protected SetCacheNode getCacheNode() {
        return this.cacheNode;
    }

    @Override
    protected void setCacheNode(SetCacheNode cache) {
        this.cacheNode = cache;
    }

    @Override
    protected SetCacheNode createCachedPropertyNode(Property property, Object thisObj, int depth, Object value2, SetCacheNode currentHead) {
        if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            return this.createCachedPropertyNodeJSObject(property, (JSDynamicObject)thisObj, depth, value2);
        }
        return this.createCachedPropertyNodeNotJSObject(property, thisObj, depth);
    }

    private SetCacheNode createCachedPropertyNodeJSObject(Property property, JSDynamicObject thisObj, int depth, Object value2) {
        Shape cacheShape = thisObj.getShape();
        PropertyCacheNode.AbstractShapeCheckNode shapeCheck = this.createShapeCheckNode(cacheShape, thisObj, depth, false, false);
        if (this.isOwnProperty() && JSAttributes.isConfigurable(property.getFlags()) && JSAttributes.configurableEnumerableWritable() != property.getFlags()) {
            return new DataPropertyPutWithFlagsNode(this.key, shapeCheck);
        }
        if (JSProperty.isData(property)) {
            return this.createCachedDataPropertyNodeJSObject(thisObj, depth, value2, shapeCheck, property);
        }
        assert (JSProperty.isAccessor(property));
        return new AccessorPropertySetNode(property, shapeCheck, this.isStrict());
    }

    private SetCacheNode createCachedDataPropertyNodeJSObject(JSDynamicObject thisObj, int depth, Object value2, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, Property property) {
        assert (!JSProperty.isConst(property) || depth == 0 && this.isGlobal() && property.getLocation().isConstant() && property.getLocation().getConstantValue() == Dead.instance()) : "const assignment";
        if (!JSProperty.isWritable(property)) {
            return new ReadOnlyPropertySetNode((PropertyCacheNode.ReceiverCheckNode)shapeCheck, this.isStrict());
        }
        if (this.superProperty) {
            return this.createGenericPropertyNode();
        }
        if (depth > 0) {
            assert (JSProperty.isWritable(property));
            return this.createUndefinedPropertyNode(thisObj, thisObj, depth, value2);
        }
        if (JSProperty.isProxy(property)) {
            if (PropertySetNode.isArrayLengthProperty(property) && JSArray.isJSFastArray(thisObj)) {
                return new ArrayLengthPropertySetNode(property, shapeCheck, this.isStrict());
            }
            return new PropertyProxySetNode(property, shapeCheck, this.isStrict());
        }
        assert (JSProperty.isWritable(property) && depth == 0 && !JSProperty.isProxy(property));
        if (property.getLocation().isConstant() || !property.getLocation().canStore(value2)) {
            return PropertySetNode.createRedefinePropertyNode(this.key, shapeCheck, shapeCheck.getShape(), property);
        }
        if (property.getLocation() instanceof IntLocation) {
            return new IntPropertySetNode(property, shapeCheck);
        }
        if (property.getLocation() instanceof DoubleLocation) {
            return new DoublePropertySetNode(property, shapeCheck);
        }
        if (property.getLocation() instanceof BooleanLocation) {
            return new BooleanPropertySetNode(property, shapeCheck);
        }
        return new ObjectPropertySetNode(property, shapeCheck);
    }

    private SetCacheNode createDefineNewPropertyNode(PropertyCacheNode.ReceiverCheckNode shapeCheck) {
        JSObjectUtil.checkForNoSuchPropertyOrMethod(this.context, this.key);
        if (this.isDeclaration()) {
            return new DataPropertyPutConstantNode(this.key, shapeCheck);
        }
        if (this.getAttributeFlags() == 0) {
            return new DataPropertyPutWithoutFlagsNode(this.key, shapeCheck);
        }
        return new DataPropertyPutWithFlagsNode(this.key, shapeCheck);
    }

    private static SetCacheNode createRedefinePropertyNode(Object key, PropertyCacheNode.ReceiverCheckNode shapeCheck, Shape oldShape, Property property) {
        assert (JSProperty.isData(property) && JSProperty.isWritable(property));
        assert (property == oldShape.getProperty(key));
        return new DataPropertyPutWithoutFlagsNode(key, shapeCheck);
    }

    private SetCacheNode createCachedPropertyNodeNotJSObject(Property property, Object thisObj, int depth) {
        PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
        if (JSProperty.isData(property)) {
            return new ReadOnlyPropertySetNode(receiverCheck, this.isStrict());
        }
        assert (JSProperty.isAccessor(property));
        return new AccessorPropertySetNode(property, receiverCheck, this.isStrict());
    }

    @Override
    protected SetCacheNode createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value2) {
        SetCacheNode specialized = this.createJavaPropertyNodeMaybe(thisObj, depth);
        if (specialized != null) {
            return specialized;
        }
        if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
            Shape cacheShape = thisJSObj.getShape();
            if (JSAdapter.isJSAdapter(store)) {
                return new JSAdapterPropertySetNode(this.createJSClassCheck(thisObj, depth));
            }
            if (JSProxy.isJSProxy(store) && JSRuntime.isPropertyKey(this.key)) {
                return new JSProxyDispatcherPropertySetNode(this.context, this.createJSClassCheck(thisObj, depth), this.isStrict());
            }
            if (JSArrayBufferView.isJSArrayBufferView(store) && this.key instanceof TruffleString && JSRuntime.canonicalNumericIndexString((TruffleString)this.key) != Undefined.instance) {
                assert (!JSArrayBufferView.isValidIntegerIndex((JSDynamicObject)store, (Number)JSRuntime.canonicalNumericIndexString((TruffleString)this.key)));
                return new ReadOnlyPropertySetNode((PropertyCacheNode.ReceiverCheckNode)this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false), false);
            }
            if (!JSRuntime.isObject(thisJSObj)) {
                return new TypeErrorPropertySetNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, true));
            }
            if (this.superProperty) {
                return this.createGenericPropertyNode();
            }
            if (JSShape.isExtensible(cacheShape) || this.key instanceof HiddenKey) {
                return this.createDefineNewPropertyNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, true));
            }
            return new ReadOnlyPropertySetNode((PropertyCacheNode.ReceiverCheckNode)this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false), this.isStrict());
        }
        if (JSProxy.isJSProxy(store)) {
            PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
            return new JSProxyDispatcherPropertySetNode(this.context, receiverCheck, this.isStrict());
        }
        boolean doThrow = this.isStrict();
        if (!JSRuntime.isJSNative(thisObj)) {
            doThrow = false;
        }
        return new ReadOnlyPropertySetNode((PropertyCacheNode.ReceiverCheckNode)new PropertyCacheNode.InstanceofCheckNode(thisObj.getClass()), doThrow);
    }

    @Override
    protected SetCacheNode createJavaPropertyNodeMaybe(Object thisObj, int depth) {
        return null;
    }

    @Override
    protected SetCacheNode createGenericPropertyNode() {
        return new GenericPropertySetNode(this.context);
    }

    @Override
    protected boolean isGlobal() {
        return this.isGlobal;
    }

    @Override
    protected boolean isOwnProperty() {
        return this.setOwnProperty;
    }

    protected final boolean isStrict() {
        return this.isStrict;
    }

    protected final int getAttributeFlags() {
        return this.attributeFlags;
    }

    protected final boolean isDeclaration() {
        return this.declaration;
    }

    @Override
    protected SetCacheNode createTruffleObjectPropertyNode() {
        return new ForeignPropertySetNode(this.context);
    }

    @Override
    protected boolean isPropertyAssumptionCheckEnabled() {
        return this.propertyAssumptionCheckEnabled && this.context.isSingleRealm();
    }

    @Override
    protected void setPropertyAssumptionCheckEnabled(boolean value2) {
        this.propertyAssumptionCheckEnabled = value2;
    }

    @Override
    protected boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value2, Property property) {
        assert (this.shapesHaveCommonLayoutForKey(parentShape, cacheShape));
        if (JSDynamicObject.isJSDynamicObject(thisObj) && JSProperty.isData(property) && JSProperty.isWritable(property) && depth == 0 && !this.superProperty && !JSProperty.isProxy(property)) {
            return !property.getLocation().isConstant() && property.getLocation().canStore(value2);
        }
        return false;
    }

    @Override
    protected SetCacheNode createCombinedIcPropertyNode(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value2, Property property) {
        PropertyCacheNode.CombinedShapeCheckNode shapeCheck = new PropertyCacheNode.CombinedShapeCheckNode(parentShape, cacheShape);
        if (property.getLocation() instanceof IntLocation) {
            return new IntPropertySetNode(property, shapeCheck);
        }
        if (property.getLocation() instanceof DoubleLocation) {
            return new DoublePropertySetNode(property, shapeCheck);
        }
        if (property.getLocation() instanceof BooleanLocation) {
            return new BooleanPropertySetNode(property, shapeCheck);
        }
        return new ObjectPropertySetNode(property, shapeCheck);
    }

    public static final class ArrayLengthPropertySetNode
    extends LinkedPropertySetNode {
        @Node.Child
        private ArrayLengthNode.ArrayLengthWriteNode arrayLengthWrite;
        private final boolean isStrict;
        private final BranchProfile errorBranch = BranchProfile.create();

        public ArrayLengthPropertySetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, boolean isStrict) {
            super(shapeCheck);
            assert (JSProperty.isData(property) && JSProperty.isWritable(property) && PropertyCacheNode.isArrayLengthProperty(property));
            this.isStrict = isStrict;
            this.arrayLengthWrite = ArrayLengthNode.ArrayLengthWriteNode.create(isStrict);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            boolean ret = JSArray.setLength(store, value2);
            if (!ret && this.isStrict) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotWritableProperty(JSArray.LENGTH, thisObj);
            }
            return true;
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            assert (JSArray.isJSFastArray(store));
            if (value2 < 0) {
                this.errorBranch.enter();
                throw Errors.createRangeErrorInvalidArrayLength();
            }
            this.arrayLengthWrite.executeVoid(store, value2);
            return true;
        }
    }

    public static final class ForeignPropertySetNode
    extends LinkedPropertySetNode {
        @Node.Child
        private ExportValueNode export;
        @CompilerDirectives.CompilationFinal
        private boolean optimistic = true;
        private final JSContext context;
        @Node.Child
        private InteropLibrary interop;
        @Node.Child
        private InteropLibrary setterInterop;
        private final BranchProfile errorBranch = BranchProfile.create();

        public ForeignPropertySetNode(JSContext context) {
            super(new PropertyCacheNode.ForeignLanguageCheckNode());
            this.context = context;
            this.export = ExportValueNode.create();
            this.interop = InteropLibrary.getFactory().createDispatched(5);
        }

        private Object nullCheck(Object truffleObject, Object key) {
            if (this.interop.isNull(truffleObject)) {
                throw Errors.createTypeErrorCannotSetProperty(key, truffleObject, this, this.context);
            }
            return truffleObject;
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            Object key = root.getKey();
            Object truffleObject = this.nullCheck(thisObj, key);
            if (!Strings.isTString(key)) {
                return false;
            }
            return this.performWriteMember(truffleObject, value2, root);
        }

        @Override
        protected boolean setValueDouble(Object thisObj, double value2, Object receiver, PropertySetNode root, boolean guard) {
            Object key = root.getKey();
            Object truffleObject = this.nullCheck(thisObj, key);
            if (!Strings.isTString(key)) {
                return false;
            }
            return this.performWriteMember(truffleObject, value2, root);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            Object key = root.getKey();
            Object truffleObject = this.nullCheck(thisObj, key);
            if (!Strings.isTString(key)) {
                return false;
            }
            Object exportedValue = this.export.execute(value2);
            return this.performWriteMember(truffleObject, exportedValue, root);
        }

        private boolean performWriteMember(Object truffleObject, Object value2, PropertySetNode root) {
            if (this.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(truffleObject)) {
                try {
                    this.interop.writeHashEntry(truffleObject, root.getKey(), value2);
                    return true;
                }
                catch (UnknownKeyException | UnsupportedMessageException | UnsupportedTypeException e) {
                    if (root.isStrict) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(truffleObject, e, "writeHashEntry", this);
                    }
                    return false;
                }
            }
            if (this.context.isOptionNashornCompatibilityMode() && this.tryInvokeSetter(truffleObject, value2, root)) {
                return true;
            }
            String stringKey = Strings.toJavaString((TruffleString)root.getKey());
            if (root.isStrict || this.optimistic) {
                try {
                    this.interop.writeMember(truffleObject, stringKey, value2);
                    return true;
                }
                catch (UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                    if (root.isStrict) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(truffleObject, e, "writeMember", stringKey, this);
                    }
                    if (e instanceof UnknownIdentifierException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.optimistic = false;
                    }
                    return false;
                }
            }
            assert (!root.isStrict);
            if (this.interop.isMemberWritable(truffleObject, stringKey)) {
                try {
                    this.interop.writeMember(truffleObject, stringKey, value2);
                    return true;
                }
                catch (UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                    return false;
                }
            }
            return false;
        }

        private boolean tryInvokeSetter(Object thisObj, Object value2, PropertySetNode root) {
            assert (this.context.isOptionNashornCompatibilityMode());
            TruffleLanguage.Env env = this.getRealm().getEnv();
            if (env.isHostObject(thisObj)) {
                TruffleString setterKey = root.getAccessorKey(Strings.SET);
                if (setterKey == null) {
                    return false;
                }
                if (this.setterInterop == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.setterInterop = this.insert(InteropLibrary.getFactory().createDispatched(5));
                }
                if (!this.setterInterop.isMemberInvocable(thisObj, Strings.toJavaString(setterKey))) {
                    return false;
                }
                try {
                    this.setterInterop.invokeMember(thisObj, Strings.toJavaString(setterKey), value2);
                    return true;
                }
                catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException interopException) {
                    // empty catch block
                }
            }
            return false;
        }
    }

    @NodeInfo(cost=NodeCost.MEGAMORPHIC)
    public static final class GenericPropertySetNode
    extends SetCacheNode {
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private ForeignPropertySetNode foreignSetNode;
        private final JSClassProfile jsclassProfile = JSClassProfile.create();
        private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isStrictSymbol = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isForeignObject = ConditionProfile.createBinaryProfile();

        public GenericPropertySetNode(JSContext context) {
            super(null);
            this.toObjectNode = JSToObjectNode.createToObjectNoCheck(context);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            if (this.isObject.profile(JSDynamicObject.isJSDynamicObject(thisObj))) {
                this.setValueInDynamicObject(thisObj, value2, receiver, root);
            } else {
                if (this.isStrictSymbol.profile(root.isStrict() && thisObj instanceof Symbol)) {
                    throw Errors.createTypeError("Cannot create property on symbol", (Node)this);
                }
                if (this.isForeignObject.profile(JSGuards.isForeignObject(thisObj))) {
                    if (this.foreignSetNode == null) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.foreignSetNode = this.insert(new ForeignPropertySetNode(root.getContext()));
                    }
                    this.foreignSetNode.setValue(thisObj, value2, receiver, root, guard);
                } else {
                    this.setValueInDynamicObject(this.toObjectNode.execute(thisObj), value2, receiver, root);
                }
            }
            return true;
        }

        private void setValueInDynamicObject(Object thisObj, Object value2, Object receiver, PropertySetNode root) {
            JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
            Object key = root.getKey();
            if (key instanceof HiddenKey) {
                JSObjectUtil.putHiddenProperty(thisJSObj, key, value2);
            } else if (root.isOwnProperty()) {
                if (root.isDeclaration()) {
                    assert (JSGlobal.isJSGlobalObject(thisJSObj) && !JSObject.hasProperty(thisJSObj, key));
                    JSObjectUtil.putDeclaredDataProperty(root.getContext(), thisJSObj, key, value2, root.getAttributeFlags());
                } else {
                    JSObject.defineOwnProperty(thisJSObj, key, PropertyDescriptor.createData(value2, root.getAttributeFlags()), root.isStrict());
                }
            } else {
                JSObject.setWithReceiver(thisJSObj, key, value2, receiver, root.isStrict(), this.jsclassProfile, (Node)root);
            }
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }

        @Override
        protected boolean setValueDouble(Object thisObj, double value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }

        @Override
        protected boolean setValueBoolean(Object thisObj, boolean value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }
    }

    public static final class JSProxyDispatcherPropertySetNode
    extends LinkedPropertySetNode {
        @Node.Child
        private JSProxyPropertySetNode proxySet;

        public JSProxyDispatcherPropertySetNode(JSContext context, PropertyCacheNode.ReceiverCheckNode receiverCheckNode, boolean isStrict) {
            super(receiverCheckNode);
            this.proxySet = JSProxyPropertySetNode.create(context, isStrict);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            this.proxySet.executeWithReceiverAndValue(this.receiverCheck.getStore(thisObj), receiver, value2, root.getKey());
            return true;
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            this.proxySet.executeWithReceiverAndValueInt(this.receiverCheck.getStore(thisObj), receiver, value2, root.getKey());
            return true;
        }
    }

    public static final class JSAdapterPropertySetNode
    extends LinkedPropertySetNode {
        public JSAdapterPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
            super(receiverCheckNode);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSObject.set((JSDynamicObject)thisObj, root.getKey(), value2, root.isStrict(), (Node)root);
            return true;
        }
    }

    public static final class TypeErrorPropertySetNode
    extends LinkedPropertySetNode {
        public TypeErrorPropertySetNode(PropertyCacheNode.AbstractShapeCheckNode shapeCheckNode) {
            super(shapeCheckNode);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            assert (thisObj == Undefined.instance || thisObj == Null.instance);
            throw Errors.createTypeErrorCannotSetProperty(root.getKey(), thisObj, this, root.getContext());
        }
    }

    public static final class ReadOnlyPropertySetNode
    extends LinkedPropertySetNode {
        private final boolean isStrict;

        public ReadOnlyPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isStrict) {
            super(receiverCheck);
            this.isStrict = isStrict;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            if (this.isStrict) {
                throw Errors.createTypeErrorNotWritableProperty(root.getKey(), thisObj, this);
            }
            return true;
        }
    }

    public static class DataPropertyPutConstantNode
    extends LinkedPropertySetNode {
        @Node.Child
        protected DynamicObjectLibrary objectLib;

        protected DataPropertyPutConstantNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
            this.objectLib = JSObjectUtil.createDispatched(key);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = (JSDynamicObject)thisObj;
            this.objectLib.putConstant(store, root.key, value2, root.getAttributeFlags());
            return true;
        }
    }

    public static class DataPropertyPutWithFlagsNode
    extends LinkedPropertySetNode {
        @Node.Child
        protected DynamicObjectLibrary objectLib;

        protected DataPropertyPutWithFlagsNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
            this.objectLib = JSObjectUtil.createDispatched(key);
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = (JSDynamicObject)thisObj;
            this.objectLib.putWithFlags(store, root.key, value2, root.getAttributeFlags());
            return true;
        }
    }

    public static class DataPropertyPutWithoutFlagsNode
    extends LinkedPropertySetNode {
        @Node.Child
        protected DynamicObjectLibrary objectLib;

        public DataPropertyPutWithoutFlagsNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
            this.objectLib = JSObjectUtil.createDispatched(key);
        }

        protected static JSDynamicObject getStore(Object thisObj) {
            return (JSDynamicObject)thisObj;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = DataPropertyPutWithoutFlagsNode.getStore(thisObj);
            this.objectLib.put(store, root.key, value2);
            return true;
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = DataPropertyPutWithoutFlagsNode.getStore(thisObj);
            this.objectLib.putInt(store, root.key, value2);
            return true;
        }

        @Override
        protected boolean setValueDouble(Object thisObj, double value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = DataPropertyPutWithoutFlagsNode.getStore(thisObj);
            this.objectLib.putDouble(store, root.key, value2);
            return true;
        }
    }

    public static final class AccessorPropertySetNode
    extends LinkedPropertySetNode {
        private final boolean isStrict;
        private final Location location;
        @Node.Child
        private JSFunctionCallNode callNode;
        private final BranchProfile undefinedSetterBranch = BranchProfile.create();

        public AccessorPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isStrict) {
            super(receiverCheck);
            assert (JSProperty.isAccessor(property));
            this.isStrict = isStrict;
            this.location = property.getLocation();
            this.callNode = JSFunctionCallNode.createCall();
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            Accessor accessor = (Accessor)this.location.get((DynamicObject)store, guard);
            Object setter = accessor.getSetter();
            if (setter != Undefined.instance) {
                this.callNode.executeCall(JSArguments.createOneArg(receiver, setter, value2));
            } else {
                this.undefinedSetterBranch.enter();
                if (this.isStrict) {
                    throw Errors.createTypeErrorCannotSetAccessorProperty(root.getKey(), store);
                }
            }
            return true;
        }
    }

    public static final class BooleanPropertySetNode
    extends LinkedPropertySetNode {
        private final BooleanLocation location;

        public BooleanPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
            super(shapeCheck);
            this.location = (BooleanLocation)((Object)property.getLocation());
            assert (JSProperty.isData(property) && JSProperty.isWritable(property)) : property;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            if (value2 instanceof Boolean) {
                JSDynamicObject store = this.receiverCheck.getStore(thisObj);
                try {
                    this.location.setBoolean(store, (Boolean)value2, this.receiverCheck.getShape());
                }
                catch (FinalLocationException e) {
                    throw Errors.shouldNotReachHere(e);
                }
                return true;
            }
            return false;
        }

        @Override
        protected boolean setValueBoolean(Object thisObj, boolean value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            try {
                this.location.setBoolean(store, value2, this.receiverCheck.getShape());
                return true;
            }
            catch (FinalLocationException e) {
                throw Errors.shouldNotReachHere(e);
            }
        }

        @Override
        protected boolean acceptsValue(Object value2) {
            return value2 instanceof Boolean;
        }
    }

    public static final class DoublePropertySetNode
    extends LinkedPropertySetNode {
        private final DoubleLocation location;
        @CompilerDirectives.CompilationFinal
        int valueProfile;
        private static final int INT = 1;
        private static final int DOUBLE = 2;
        private static final int OTHER = 4;

        public DoublePropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
            super(shapeCheck);
            this.location = (DoubleLocation)((Object)property.getLocation());
            assert (JSProperty.isData(property) && JSProperty.isWritable(property)) : property;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            double doubleValue;
            int p = this.valueProfile;
            if ((p & 2) != 0 && value2 instanceof Double) {
                doubleValue = (Double)value2;
            } else if ((p & 1) != 0 && value2 instanceof Integer) {
                doubleValue = ((Integer)value2).intValue();
            } else {
                if ((p & 4) != 0 && !(value2 instanceof Double) && !(value2 instanceof Integer)) {
                    return false;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                p = value2 instanceof Double ? (p |= 2) : (value2 instanceof Integer ? (p |= 1) : (p |= 4));
                this.valueProfile = p;
                return this.setValue(thisObj, value2, receiver, root, guard);
            }
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            try {
                this.location.setDouble(store, doubleValue, this.receiverCheck.getShape());
                return true;
            }
            catch (FinalLocationException e) {
                throw Errors.shouldNotReachHere(e);
            }
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            try {
                this.location.setDouble(store, value2, this.receiverCheck.getShape());
                return true;
            }
            catch (FinalLocationException e) {
                throw Errors.shouldNotReachHere(e);
            }
        }

        @Override
        protected boolean setValueDouble(Object thisObj, double value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            try {
                this.location.setDouble(store, value2, this.receiverCheck.getShape());
                return true;
            }
            catch (FinalLocationException e) {
                throw Errors.shouldNotReachHere(e);
            }
        }

        @Override
        protected boolean acceptsValue(Object value2) {
            return value2 instanceof Double || value2 instanceof Integer;
        }
    }

    public static final class IntPropertySetNode
    extends LinkedPropertySetNode {
        private final IntLocation location;

        public IntPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
            super(shapeCheck);
            this.location = (IntLocation)((Object)property.getLocation());
            assert (JSProperty.isData(property) && JSProperty.isWritable(property)) : property;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            if (value2 instanceof Integer) {
                int intValue = (Integer)value2;
                JSDynamicObject store = this.receiverCheck.getStore(thisObj);
                try {
                    this.location.setInt(store, intValue, this.receiverCheck.getShape());
                }
                catch (FinalLocationException e) {
                    throw Errors.shouldNotReachHere(e);
                }
                return true;
            }
            return false;
        }

        @Override
        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            try {
                this.location.setInt(store, value2, this.receiverCheck.getShape());
                return true;
            }
            catch (FinalLocationException e) {
                throw Errors.shouldNotReachHere(e);
            }
        }

        @Override
        protected boolean acceptsValue(Object value2) {
            return value2 instanceof Integer;
        }
    }

    public static final class PropertyProxySetNode
    extends LinkedPropertySetNode {
        private final boolean isStrict;
        private final Location location;
        private final BranchProfile errorBranch = BranchProfile.create();

        public PropertyProxySetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, boolean isStrict) {
            super(shapeCheck);
            this.isStrict = isStrict;
            this.location = property.getLocation();
            assert (JSProperty.isData(property) && JSProperty.isWritable(property) && JSProperty.isProxy(property)) : property;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            boolean ret = ((PropertyProxy)this.location.get((DynamicObject)store, guard)).set(store, value2);
            if (!ret && this.isStrict) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotWritableProperty(root.getKey(), thisObj);
            }
            return true;
        }
    }

    public static final class ObjectPropertySetNode
    extends LinkedPropertySetNode {
        private final Location location;

        public ObjectPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
            super(shapeCheck);
            this.location = property.getLocation();
            assert (JSProperty.isData(property) && JSProperty.isWritable(property) && !JSProperty.isProxy(property)) : property;
        }

        @Override
        protected boolean setValue(Object thisObj, Object value2, Object receiver, PropertySetNode root, boolean guard) {
            if (this.location.canStore(value2)) {
                JSDynamicObject store = this.receiverCheck.getStore(thisObj);
                try {
                    this.location.set(store, value2, this.receiverCheck.getShape());
                }
                catch (FinalLocationException | IncompatibleLocationException e) {
                    throw Errors.shouldNotReachHere(e);
                }
                return true;
            }
            return false;
        }

        @Override
        protected boolean acceptsValue(Object value2) {
            return this.location.canStore(value2);
        }
    }

    public static abstract class LinkedPropertySetNode
    extends SetCacheNode {
        protected LinkedPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
        }
    }

    public static abstract class SetCacheNode
    extends PropertyCacheNode.CacheNode<SetCacheNode> {
        @Node.Child
        protected SetCacheNode next;

        protected SetCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
        }

        @Override
        protected final SetCacheNode getNext() {
            return this.next;
        }

        @Override
        protected final void setNext(SetCacheNode next) {
            this.next = next;
        }

        protected abstract boolean setValue(Object var1, Object var2, Object var3, PropertySetNode var4, boolean var5);

        protected boolean setValueInt(Object thisObj, int value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }

        protected boolean setValueDouble(Object thisObj, double value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }

        protected boolean setValueBoolean(Object thisObj, boolean value2, Object receiver, PropertySetNode root, boolean guard) {
            return this.setValue(thisObj, value2, receiver, root, guard);
        }

        @Override
        protected boolean acceptsValue(Object value2) {
            return true;
        }
    }
}

