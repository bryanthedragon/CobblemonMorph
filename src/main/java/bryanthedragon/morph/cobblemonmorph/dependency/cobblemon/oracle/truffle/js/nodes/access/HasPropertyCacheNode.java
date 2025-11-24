
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNode;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNodeGen;
import com.oracle.truffle.js.nodes.access.PropertyCacheNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public class HasPropertyCacheNode
extends PropertyCacheNode<HasCacheNode> {
    private final boolean hasOwnProperty;
    private boolean propertyAssumptionCheckEnabled = true;
    @Node.Child
    protected HasCacheNode cacheNode;

    public static HasPropertyCacheNode create(Object key, JSContext context, boolean hasOwnProperty) {
        return new HasPropertyCacheNode(key, context, hasOwnProperty);
    }

    public static HasPropertyCacheNode create(Object key, JSContext context) {
        return HasPropertyCacheNode.create(key, context, false);
    }

    protected HasPropertyCacheNode(Object key, JSContext context, boolean hasOwnProperty) {
        super(key, context);
        this.hasOwnProperty = hasOwnProperty;
    }

    @ExplodeLoop
    public boolean hasProperty(Object thisObj) {
        HasCacheNode c = this.cacheNode;
        while (c != null) {
            block5: {
                Object castObj;
                boolean guard;
                PropertyCacheNode.ReceiverCheckNode receiverCheck;
                boolean isSimpleShapeCheck;
                block6: {
                    block4: {
                        if (c instanceof GenericHasPropertyCacheNode) {
                            return ((GenericHasPropertyCacheNode)c).hasProperty(thisObj, this);
                        }
                        isSimpleShapeCheck = c.isSimpleShapeCheck();
                        receiverCheck = c.receiverCheck;
                        if (!isSimpleShapeCheck) break block4;
                        Shape shape = receiverCheck.getShape();
                        if (!HasPropertyCacheNode.isDynamicObject(thisObj, shape)) break block5;
                        JSDynamicObject jsobj = HasPropertyCacheNode.castDynamicObject(thisObj, shape);
                        guard = shape.check(jsobj);
                        castObj = jsobj;
                        if (!shape.getValidAssumption().isValid()) {
                            break;
                        }
                        break block6;
                    }
                    guard = receiverCheck.accept(thisObj);
                    castObj = thisObj;
                }
                if (guard) {
                    if (!isSimpleShapeCheck && !receiverCheck.isValid()) break;
                    return c.hasProperty(castObj, this);
                }
            }
            c = c.next;
        }
        this.deoptimize(c);
        return this.hasPropertyAndSpecialize(thisObj);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean hasPropertyAndSpecialize(Object thisObj) {
        HasCacheNode c = (HasCacheNode)this.specialize(thisObj);
        return c.hasProperty(thisObj, this);
    }

    @Override
    protected HasCacheNode getCacheNode() {
        return this.cacheNode;
    }

    @Override
    protected void setCacheNode(HasCacheNode cache) {
        this.cacheNode = cache;
    }

    @Override
    protected HasCacheNode createCachedPropertyNode(Property property, Object thisObj, int depth, Object value2, HasCacheNode currentHead) {
        PropertyCacheNode.ReceiverCheckNode check;
        assert (!this.isOwnProperty() || depth == 0);
        if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
            Shape cacheShape = thisJSObj.getShape();
            check = this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false);
        } else {
            check = this.createPrimitiveReceiverCheck(thisObj, depth);
        }
        return new PresentHasPropertyCacheNode(check);
    }

    @Override
    protected HasCacheNode createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value2) {
        HasCacheNode specialized = this.createJavaPropertyNodeMaybe(thisObj, depth);
        if (specialized != null) {
            return specialized;
        }
        if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
            Shape cacheShape = thisJSObj.getShape();
            if (JSAdapter.isJSAdapter(store)) {
                return new JSAdapterHasPropertyCacheNode(this.key, this.createJSClassCheck(thisObj, depth));
            }
            if (JSProxy.isJSProxy(store)) {
                return new JSProxyDispatcherPropertyHasNode(this.context, this.key, this.createJSClassCheck(thisObj, depth), this.isOwnProperty());
            }
            if (JSModuleNamespace.isJSModuleNamespace(store)) {
                return new UnspecializedHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth));
            }
            return new AbsentHasPropertyCacheNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false));
        }
        return new AbsentHasPropertyCacheNode(new PropertyCacheNode.InstanceofCheckNode(thisObj.getClass()));
    }

    @Override
    protected HasCacheNode createJavaPropertyNodeMaybe(Object thisObj, int depth) {
        if (JavaPackage.isJavaPackage(thisObj)) {
            return new PresentHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth));
        }
        if (JavaImporter.isJavaImporter(thisObj)) {
            return new UnspecializedHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth));
        }
        return null;
    }

    @Override
    protected HasCacheNode createGenericPropertyNode() {
        return new GenericHasPropertyCacheNode();
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
    protected boolean isGlobal() {
        return false;
    }

    @Override
    protected boolean isOwnProperty() {
        return this.hasOwnProperty;
    }

    @Override
    protected HasCacheNode createTruffleObjectPropertyNode() {
        return new ForeignHasPropertyCacheNode();
    }

    @Override
    protected boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value2, Property property) {
        assert (this.shapesHaveCommonLayoutForKey(parentShape, cacheShape));
        if (JSDynamicObject.isJSDynamicObject(thisObj) && JSProperty.isData(property)) {
            assert (depth == 0);
            return !this.isGlobal();
        }
        return false;
    }

    @Override
    protected HasCacheNode createCombinedIcPropertyNode(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value2, Property property) {
        return new PresentHasPropertyCacheNode(new PropertyCacheNode.CombinedShapeCheckNode(parentShape, cacheShape));
    }

    public static final class ForeignHasPropertyCacheNode
    extends LinkedHasPropertyCacheNode {
        @Node.Child
        private InteropLibrary interop = InteropLibrary.getFactory().createDispatched(5);

        public ForeignHasPropertyCacheNode() {
            super(new PropertyCacheNode.ForeignLanguageCheckNode());
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            assert (JSRuntime.isForeignObject(thisObj));
            Object key = root.getKey();
            if (Strings.isTString(key)) {
                return this.interop.isMemberExisting(thisObj, Strings.toJavaString((TruffleString)key));
            }
            return false;
        }
    }

    @NodeInfo(cost=NodeCost.MEGAMORPHIC)
    public static final class GenericHasPropertyCacheNode
    extends HasCacheNode {
        @Node.Child
        private InteropLibrary interop;
        private final JSClassProfile jsclassProfile = JSClassProfile.create();

        public GenericHasPropertyCacheNode() {
            super(null);
            this.interop = InteropLibrary.getFactory().createDispatched(5);
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            if (JSDynamicObject.isJSDynamicObject(thisObj)) {
                Object key = root.getKey();
                if (root.isOwnProperty()) {
                    return JSObject.hasOwnProperty((JSDynamicObject)thisObj, key, this.jsclassProfile);
                }
                return JSObject.hasProperty((JSDynamicObject)thisObj, key, this.jsclassProfile);
            }
            assert (JSRuntime.isForeignObject(thisObj));
            Object key = root.getKey();
            if (Strings.isTString(key)) {
                return this.interop.isMemberExisting(thisObj, Strings.toJavaString((TruffleString)key));
            }
            return false;
        }
    }

    public static final class UnspecializedHasPropertyCacheNode
    extends LinkedHasPropertyCacheNode {
        public UnspecializedHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
            super(receiverCheckNode);
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            Object key = root.getKey();
            if (root.isOwnProperty()) {
                return JSObject.hasOwnProperty((JSDynamicObject)thisObj, key);
            }
            return JSObject.hasProperty((JSDynamicObject)thisObj, key);
        }
    }

    public static final class JSProxyDispatcherPropertyHasNode
    extends LinkedHasPropertyCacheNode {
        private final boolean hasOwnProperty;
        @Node.Child
        private JSProxyHasPropertyNode proxyGet;
        @Node.Child
        private JSGetOwnPropertyNode getOwnPropertyNode;

        public JSProxyDispatcherPropertyHasNode(JSContext context, Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean hasOwnProperty) {
            super(receiverCheck);
            this.hasOwnProperty = hasOwnProperty;
            assert (JSRuntime.isPropertyKey(key));
            this.proxyGet = hasOwnProperty ? null : JSProxyHasPropertyNodeGen.create(context);
            this.getOwnPropertyNode = hasOwnProperty ? JSGetOwnPropertyNode.create() : null;
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            Object key = root.getKey();
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);
            if (this.hasOwnProperty) {
                return this.getOwnPropertyNode.execute(store, key) != null;
            }
            return this.proxyGet.executeWithTargetAndKeyBoolean(store, key);
        }
    }

    public static final class JSAdapterHasPropertyCacheNode
    extends LinkedHasPropertyCacheNode {
        public JSAdapterHasPropertyCacheNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
            super(receiverCheckNode);
            assert (JSRuntime.isPropertyKey(key));
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            return JSObject.hasOwnProperty((JSDynamicObject)thisObj, root.getKey());
        }
    }

    public static final class AbsentHasPropertyCacheNode
    extends LinkedHasPropertyCacheNode {
        public AbsentHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode shapeCheckNode) {
            super(shapeCheckNode);
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            return false;
        }
    }

    public static final class PresentHasPropertyCacheNode
    extends LinkedHasPropertyCacheNode {
        public PresentHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode shapeCheck) {
            super(shapeCheck);
        }

        @Override
        protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
            return true;
        }
    }

    public static abstract class LinkedHasPropertyCacheNode
    extends HasCacheNode {
        protected LinkedHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
            super(receiverCheckNode);
        }
    }

    public static abstract class HasCacheNode
    extends PropertyCacheNode.CacheNode<HasCacheNode> {
        @Node.Child
        protected HasCacheNode next;

        protected HasCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
            super(receiverCheck);
        }

        @Override
        protected final HasCacheNode getNext() {
            return this.next;
        }

        @Override
        protected final void setNext(HasCacheNode next) {
            this.next = next;
        }

        protected abstract boolean hasProperty(Object var1, HasPropertyCacheNode var2);
    }
}

