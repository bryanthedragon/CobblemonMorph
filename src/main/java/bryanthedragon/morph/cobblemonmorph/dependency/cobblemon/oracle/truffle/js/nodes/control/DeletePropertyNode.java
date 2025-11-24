
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.array.JSArrayDeleteIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNodeGen;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.Set;

@NodeInfo(shortName="delete")
@ImportStatic(value={JSConfig.class})
public abstract class DeletePropertyNode
extends JSTargetableNode {
    protected final boolean strict;
    protected final JSContext context;
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode propertyNode;

    protected DeletePropertyNode(boolean strict, JSContext context, JavaScriptNode targetNode, JavaScriptNode propertyNode) {
        this.strict = strict;
        this.context = context;
        this.targetNode = targetNode;
        this.propertyNode = propertyNode;
    }

    public static DeletePropertyNode create(boolean strict, JSContext context) {
        return DeletePropertyNode.create(null, null, strict, context);
    }

    public static DeletePropertyNode createNonStrict(JSContext context) {
        return DeletePropertyNode.create(null, null, false, context);
    }

    public static DeletePropertyNode create(JavaScriptNode object, JavaScriptNode property, boolean strict, JSContext context) {
        return DeletePropertyNodeGen.create(strict, context, object, property);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.UnaryOperationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (this.materializationNeeded() && materializedTags.contains(JSTags.UnaryOperationTag.class)) {
            JavaScriptNode key = DeletePropertyNode.cloneUninitialized(this.propertyNode, materializedTags);
            JavaScriptNode target = DeletePropertyNode.cloneUninitialized(this.targetNode, materializedTags);
            DeletePropertyNode.transferSourceSectionAddExpressionTag(this, key);
            DeletePropertyNode.transferSourceSectionAddExpressionTag(this, target);
            DeletePropertyNode node = DeletePropertyNode.create(target, key, this.strict, this.context);
            DeletePropertyNode.transferSourceSectionAndTags(this, node);
            return node;
        }
        return this;
    }

    private boolean materializationNeeded() {
        return !this.propertyNode.hasSourceSection() || !this.targetNode.hasSourceSection();
    }

    @Override
    public final JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        return this.executeWithTarget(frame, this.evaluateTarget(frame));
    }

    @Override
    public final Object evaluateTarget(VirtualFrame frame) {
        return this.getTarget().execute(frame);
    }

    public abstract boolean executeEvaluated(Object var1, Object var2);

    @Specialization(guards={"isJSOrdinaryObject(targetObject)"})
    protected final boolean doJSOrdinaryObject(JSDynamicObject targetObject, Object key, @Cached.Shared(value="toPropertyKey") @Cached(value="create()") JSToPropertyKeyNode toPropertyKeyNode, @CachedLibrary(limit="InteropLibraryLimit") DynamicObjectLibrary dynamicObjectLib) {
        Object propertyKey = toPropertyKeyNode.execute(key);
        Property foundProperty = dynamicObjectLib.getProperty(targetObject, propertyKey);
        if (foundProperty != null) {
            if (!JSProperty.isConfigurable(foundProperty)) {
                if (this.strict) {
                    throw Errors.createTypeErrorNotConfigurableProperty(propertyKey);
                }
                return false;
            }
            dynamicObjectLib.removeKey(targetObject, propertyKey);
            return true;
        }
        return true;
    }

    @Specialization(guards={"!isJSOrdinaryObject(targetObject)"})
    protected final boolean doJSObject(JSDynamicObject targetObject, Object key, @Cached(value="createIsFastArray()") IsArrayNode isArrayNode, @Cached(value="createBinaryProfile()") ConditionProfile arrayProfile, @Cached ToArrayIndexNode toArrayIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile arrayIndexProfile, @Cached(value="create(context, strict)") JSArrayDeleteIndexNode deleteArrayIndexNode, @Cached JSClassProfile jsclassProfile, @Cached.Shared(value="toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
        Object propertyKey;
        if (arrayProfile.profile(isArrayNode.execute(targetObject))) {
            Object objIndex = toArrayIndexNode.execute(key);
            if (arrayIndexProfile.profile(objIndex instanceof Long)) {
                long longIndex = (Long)objIndex;
                return deleteArrayIndexNode.execute(targetObject, JSAbstractArray.arrayGetArrayType(targetObject), longIndex);
            }
            propertyKey = objIndex;
        } else {
            propertyKey = toPropertyKeyNode.execute(key);
        }
        return JSObject.delete(targetObject, propertyKey, this.strict, jsclassProfile);
    }

    @Specialization
    protected static boolean doSymbol(Symbol target, Object property, @Cached.Shared(value="toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
        toPropertyKeyNode.execute(property);
        return true;
    }

    @Specialization
    protected static boolean doSafeInteger(SafeInteger target, Object property, @Cached.Shared(value="toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
        toPropertyKeyNode.execute(property);
        return true;
    }

    @Specialization
    protected static boolean doBigInt(BigInt target, Object property, @Cached.Shared(value="toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
        toPropertyKeyNode.execute(property);
        return true;
    }

    @Specialization
    protected boolean doString(TruffleString target, Object property, @Cached.Shared(value="toArrayIndex") @Cached ToArrayIndexNode toArrayIndexNode, @Cached TruffleString.EqualNode equalsNode) {
        boolean result;
        Object objIndex = toArrayIndexNode.execute(property);
        if (objIndex instanceof Long) {
            long index = (Long)objIndex;
            result = index < 0L || (long)Strings.length(target) <= index;
        } else {
            boolean bl = result = !Strings.equals(equalsNode, JSString.LENGTH, (TruffleString)objIndex);
        }
        if (this.strict && !result) {
            throw Errors.createTypeError("cannot delete index");
        }
        return result;
    }

    @Specialization(guards={"isForeignObject(target)", "!interop.hasArrayElements(target)"})
    protected boolean member(Object target, TruffleString name, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        String javaName;
        if (this.context.getContextOptions().hasForeignHashProperties() && interop.hasHashEntries(target)) {
            try {
                interop.removeHashEntry(target, name);
                return true;
            }
            catch (UnknownKeyException unknownKeyException) {
            }
            catch (UnsupportedMessageException e) {
                if (this.strict) {
                    throw Errors.createTypeErrorInteropException(target, e, "delete", this);
                }
                return false;
            }
        }
        if (interop.isMemberExisting(target, javaName = Strings.toJavaString(name))) {
            try {
                interop.removeMember(target, javaName);
                return true;
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                if (this.strict) {
                    throw Errors.createTypeErrorCannotDeletePropertyOf(name, target);
                }
                return false;
            }
        }
        return true;
    }

    @Specialization(guards={"isForeignObject(target)", "interop.hasArrayElements(target)"})
    protected boolean arrayElementInt(Object target, int index, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        return this.arrayElementLong(target, index, interop);
    }

    private boolean arrayElementLong(Object target, long index, InteropLibrary interop) {
        long length;
        try {
            length = interop.getArraySize(target);
        }
        catch (UnsupportedMessageException e) {
            return true;
        }
        if (index >= 0L && index < length) {
            if (this.strict) {
                throw Errors.createTypeErrorNotConfigurableProperty(Strings.fromLong(index));
            }
            return false;
        }
        return true;
    }

    private boolean hashEntry(Object target, Object key, InteropLibrary interop) {
        try {
            interop.removeHashEntry(target, key);
            return true;
        }
        catch (UnknownKeyException e) {
            return true;
        }
        catch (UnsupportedMessageException e) {
            if (this.strict) {
                throw Errors.createTypeErrorInteropException(target, e, "delete", this);
            }
            return false;
        }
    }

    @Specialization(guards={"isForeignObject(target)"}, replaces={"member", "arrayElementInt"})
    protected boolean foreignObject(Object target, Object key, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached.Shared(value="toArrayIndex") @Cached(value="create()") ToArrayIndexNode toArrayIndexNode, @Cached.Shared(value="toPropertyKey") @Cached(value="create()") JSToPropertyKeyNode toPropertyKeyNode) {
        Object propertyKey;
        if (interop.hasArrayElements(target)) {
            Object indexOrPropertyKey = toArrayIndexNode.execute(key);
            if (indexOrPropertyKey instanceof Long) {
                return this.arrayElementLong(target, (Long)indexOrPropertyKey, interop);
            }
            propertyKey = indexOrPropertyKey;
            assert (JSRuntime.isPropertyKey(propertyKey));
        } else {
            propertyKey = toPropertyKeyNode.execute(key);
        }
        if (this.context.getContextOptions().hasForeignHashProperties() && interop.hasHashEntries(target)) {
            return this.hashEntry(target, propertyKey, interop);
        }
        if (interop.hasMembers(target)) {
            if (Strings.isTString(propertyKey)) {
                return this.member(target, (TruffleString)propertyKey, interop);
            }
            assert (propertyKey instanceof Symbol);
            return true;
        }
        return true;
    }

    @Specialization(guards={"!isTruffleObject(target)", "!isString(target)"})
    public boolean doOther(Object target, Object property, @Cached.Shared(value="toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
        toPropertyKeyNode.execute(property);
        return true;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return DeletePropertyNode.create(DeletePropertyNode.cloneUninitialized(this.getTarget(), materializedTags), DeletePropertyNode.cloneUninitialized(this.propertyNode, materializedTags), this.strict, this.context);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }
}

