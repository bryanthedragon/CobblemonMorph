
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CopyDataPropertiesNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.FrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNodeFactory;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.FunctionNameHolder;
import com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode;
import com.oracle.truffle.js.nodes.function.NamedEvaluationTargetNode;
import com.oracle.truffle.js.nodes.function.SetFunctionNameNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Arrays;
import java.util.Set;

public class ObjectLiteralNode
extends JavaScriptNode {
    @Node.Children
    private final ObjectLiteralMemberNode[] members;
    @Node.Child
    private CreateObjectNode objectCreateNode;

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.LiteralTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.ObjectLiteral.name());
    }

    protected static Object executeWithRealm(JavaScriptNode valueNode, VirtualFrame frame, JSRealm realm) {
        if (valueNode instanceof JSFunctionExpressionNode) {
            return ((JSFunctionExpressionNode)valueNode).executeWithRealm(frame, realm);
        }
        return valueNode.execute(frame);
    }

    public static boolean isAutoAccessor(ObjectLiteralMemberNode memberNode) {
        return memberNode instanceof AutoAccessorDataMemberNode;
    }

    public static boolean isPrivateMethod(ObjectLiteralMemberNode memberNode) {
        assert (ObjectLiteralNode.isMethod(memberNode));
        return memberNode instanceof PrivateMethodMemberNode;
    }

    public static boolean isMethod(ObjectLiteralMemberNode memberNode) {
        if (memberNode instanceof PrivateMethodMemberNode) {
            return true;
        }
        if (memberNode instanceof AccessorMemberNode || memberNode instanceof AutoAccessorDataMemberNode) {
            return false;
        }
        return !memberNode.isFieldOrStaticBlock();
    }

    public static boolean isAccessor(ObjectLiteralMemberNode memberNode) {
        return memberNode instanceof AccessorMemberNode;
    }

    public static ObjectLiteralMemberNode newDataMember(TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode, boolean isField) {
        return new ObjectLiteralDataMemberNode(name, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode, isField);
    }

    public static ObjectLiteralMemberNode newAutoAccessor(TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode) {
        return new AutoAccessorDataMemberNode((Object)name, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode);
    }

    public static ObjectLiteralMemberNode newComputedAutoAccessor(JavaScriptNode keyNode, boolean isStatic, boolean enumerable, JavaScriptNode valueNode) {
        return new ComputedAutoAccessorDataMemberNode(keyNode, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode);
    }

    public static ObjectLiteralMemberNode newAccessorMember(TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode getterNode, JavaScriptNode setterNode) {
        return new ObjectLiteralAccessorMemberNode(name, isStatic, JSAttributes.fromConfigurableEnumerable(true, enumerable), getterNode, setterNode);
    }

    public static ObjectLiteralMemberNode newComputedDataMember(JavaScriptNode name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition) {
        int attributes = enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable();
        return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(name, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition);
    }

    public static ObjectLiteralMemberNode newComputedAccessorMember(JavaScriptNode name, boolean isStatic, boolean enumerable, JavaScriptNode getter, JavaScriptNode setter) {
        return new ComputedObjectLiteralAccessorMemberNode(name, isStatic, JSAttributes.fromConfigurableEnumerable(true, enumerable), getter, setter);
    }

    public static ObjectLiteralMemberNode newDataMember(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
        return new ObjectLiteralDataMemberNode(name, isStatic, attributes, valueNode, false);
    }

    public static ObjectLiteralMemberNode newAccessorMember(Object name, boolean isStatic, int attributes, JavaScriptNode getterNode, JavaScriptNode setterNode) {
        return new ObjectLiteralAccessorMemberNode(name, isStatic, attributes, getterNode, setterNode);
    }

    public static ObjectLiteralMemberNode newComputedDataMember(JavaScriptNode name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
        return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(name, isStatic, attributes, valueNode, false, false);
    }

    public static ObjectLiteralMemberNode newPrivateFieldMember(JavaScriptNode name, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode) {
        return new PrivateFieldMemberNode(name, isStatic, valueNode, writePrivateNode);
    }

    public static ObjectLiteralMemberNode newPrivateMethodMember(TruffleString privateName, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex) {
        return new PrivateMethodMemberNode(privateName, isStatic, valueNode, writePrivateNode, privateBrandSlotIndex);
    }

    public static ObjectLiteralMemberNode newPrivateAccessorMember(boolean isStatic, JavaScriptNode getterNode, JavaScriptNode setterNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex) {
        return new PrivateAccessorMemberNode(isStatic, getterNode, setterNode, writePrivateNode, privateBrandSlotIndex);
    }

    public static ObjectLiteralMemberNode newProtoMember(TruffleString name, boolean isStatic, JavaScriptNode valueNode) {
        assert (Strings.equals(JSObject.PROTO, name));
        return new ObjectLiteralProtoMemberNode(isStatic, valueNode);
    }

    public static ObjectLiteralMemberNode newSpreadObjectMember(boolean isStatic, JavaScriptNode valueNode) {
        return new ObjectLiteralSpreadMemberNode(isStatic, JSAttributes.getDefault(), valueNode);
    }

    public static ObjectLiteralMemberNode newStaticBlockMember(JavaScriptNode valueNode) {
        return new ObjectLiteralDataMemberNode(null, true, JSAttributes.getDefaultNotEnumerable(), valueNode, true);
    }

    public ObjectLiteralNode(ObjectLiteralMemberNode[] members, CreateObjectNode objectCreateNode) {
        this.members = members;
        this.objectCreateNode = objectCreateNode;
    }

    public static ObjectLiteralNode create(JSContext context, ObjectLiteralMemberNode[] members) {
        if (members.length > 0 && members[0] instanceof ObjectLiteralProtoMemberNode) {
            return new ObjectLiteralNode(Arrays.copyOfRange(members, 1, members.length), CreateObjectNode.createOrdinaryWithPrototype(context, ((ObjectLiteralProtoMemberNode)members[0]).valueNode));
        }
        if (members.length > 256 && ObjectLiteralNode.onlyDataMembers(members)) {
            return ObjectLiteralNode.createDictionaryObject(context, members);
        }
        return new ObjectLiteralNode(members, CreateObjectNode.create(context));
    }

    private static boolean onlyDataMembers(ObjectLiteralMemberNode[] members) {
        for (ObjectLiteralMemberNode member : members) {
            if (member instanceof ObjectLiteralDataMemberNode) continue;
            return false;
        }
        return true;
    }

    private static ObjectLiteralNode createDictionaryObject(JSContext context, ObjectLiteralMemberNode[] members) {
        ObjectLiteralMemberNode[] newMembers = new ObjectLiteralMemberNode[members.length];
        for (int i = 0; i < members.length; ++i) {
            ObjectLiteralDataMemberNode member = (ObjectLiteralDataMemberNode)members[i];
            newMembers[i] = new DictionaryObjectDataMemberNode(member.name, member.isStatic, member.attributes, member.valueNode);
        }
        return new ObjectLiteralNode(newMembers, CreateObjectNode.createDictionary(context));
    }

    @Override
    public JSDynamicObject execute(VirtualFrame frame) {
        JSRealm realm = this.getRealm();
        JSDynamicObject ret = this.objectCreateNode.executeWithRealm(frame, realm);
        return this.executeWithObject(frame, ret, realm);
    }

    @ExplodeLoop
    protected JSDynamicObject executeWithObject(VirtualFrame frame, JSDynamicObject ret, JSRealm realm) {
        for (int i = 0; i < this.members.length; ++i) {
            this.members[i].executeVoid(frame, ret, realm);
        }
        return ret;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == JSDynamicObject.class;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new ObjectLiteralNode(ObjectLiteralMemberNode.cloneUninitialized(this.members, materializedTags), this.objectCreateNode.copyUninitialized(materializedTags));
    }

    public static class PrivateAccessorMemberNode
    extends ObjectLiteralMemberNode
    implements AccessorMemberNode {
        @Node.Child
        private JavaScriptNode getterNode;
        @Node.Child
        private JavaScriptNode setterNode;
        @Node.Child
        private JSWriteFrameSlotNode writePrivateNode;
        private final int privateBrandSlotIndex;

        PrivateAccessorMemberNode(boolean isStatic, JavaScriptNode getterNode, JavaScriptNode setterNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex) {
            super(isStatic, true, JSAttributes.getDefaultNotEnumerable(), false, false);
            this.getterNode = getterNode;
            this.setterNode = setterNode;
            this.writePrivateNode = writePrivateNode;
            this.privateBrandSlotIndex = privateBrandSlotIndex;
        }

        public int getPrivateBrandSlotIndex() {
            return this.privateBrandSlotIndex;
        }

        public FrameSlotNode getWritePrivateNode() {
            return this.writePrivateNode;
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            return this.writePrivateNode.getIdentifier();
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
            this.executeVoid(frame, obj, realm);
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object getter = null;
            Object setter = null;
            if (this.getterNode != null) {
                getter = PrivateAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
            }
            if (this.setterNode != null) {
                setter = PrivateAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
            }
            assert (getter != null || setter != null);
            Accessor accessor = new Accessor(getter, setter);
            this.writePrivateNode.executeWrite(frame, accessor);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new PrivateAccessorMemberNode(this.isStatic, JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags), JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags), JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags), this.privateBrandSlotIndex);
        }

        @Override
        public boolean hasGetter() {
            return this.getterNode != null;
        }

        @Override
        public boolean hasSetter() {
            return this.setterNode != null;
        }

        @Override
        public Object evaluateGetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return PrivateAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
        }

        @Override
        public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return PrivateAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
        }
    }

    public static class PrivateMethodMemberNode
    extends ObjectLiteralMemberNode {
        @Node.Child
        private JavaScriptNode valueNode;
        @Node.Child
        private JSWriteFrameSlotNode writePrivateNode;
        private final TruffleString privateName;
        private final int privateBrandSlotIndex;

        PrivateMethodMemberNode(TruffleString privateName, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex) {
            super(isStatic, true, JSAttributes.getDefaultNotEnumerable(), false, false);
            this.privateName = privateName;
            this.valueNode = valueNode;
            this.writePrivateNode = writePrivateNode;
            this.privateBrandSlotIndex = privateBrandSlotIndex;
        }

        public int getPrivateBrandSlotIndex() {
            return this.privateBrandSlotIndex;
        }

        public JSWriteFrameSlotNode getWritePrivateNode() {
            return this.writePrivateNode;
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            return this.privateName;
        }

        @Override
        public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return PrivateMethodMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
            this.writePrivateNode.executeWrite(frame, value2);
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object value2 = PrivateMethodMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
            this.writePrivateNode.executeWrite(frame, value2);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new PrivateMethodMemberNode(this.privateName, this.isStatic, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags), JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags), this.privateBrandSlotIndex);
        }
    }

    private static class PrivateFieldMemberNode
    extends ObjectLiteralMemberNode {
        @Node.Child
        private JavaScriptNode keyNode;
        @Node.Child
        private JavaScriptNode valueNode;
        @Node.Child
        private JSWriteFrameSlotNode writePrivateNode;

        PrivateFieldMemberNode(JavaScriptNode key, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode) {
            super(isStatic, true, JSAttributes.getDefaultNotEnumerable(), true, false);
            this.keyNode = key;
            this.valueNode = valueNode;
            this.writePrivateNode = writePrivateNode;
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            this.writePrivateNode.execute(frame);
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            return this.keyNode.execute(frame);
        }

        @Override
        public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return PrivateFieldMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new PrivateFieldMemberNode(JavaScriptNode.cloneUninitialized(this.keyNode, materializedTags), this.isStatic, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags), JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags));
        }
    }

    private static class DictionaryObjectDataMemberNode
    extends ObjectLiteralMemberNode {
        private final Object name;
        @Node.Child
        private JavaScriptNode valueNode;

        DictionaryObjectDataMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
            super(isStatic, attributes);
            assert (JSRuntime.isPropertyKey(name));
            this.name = name;
            this.valueNode = valueNode;
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object value2 = DictionaryObjectDataMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
            PropertyDescriptor propDesc = PropertyDescriptor.createData(value2, this.attributes);
            JSObject.defineOwnProperty(receiver, this.name, propDesc, true);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DictionaryObjectDataMemberNode(this.name, this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags));
        }
    }

    private static class ObjectLiteralSpreadMemberNode
    extends ObjectLiteralMemberNode {
        @Node.Child
        private JavaScriptNode valueNode;
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private CopyDataPropertiesNode copyDataPropertiesNode;

        ObjectLiteralSpreadMemberNode(boolean isStatic, int attributes, JavaScriptNode valueNode) {
            super(isStatic, attributes);
            this.valueNode = valueNode;
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject target, JSRealm realm) {
            Object sourceValue = this.valueNode.execute(frame);
            if (JSGuards.isNullOrUndefined(sourceValue)) {
                return;
            }
            if (this.toObjectNode == null || this.copyDataPropertiesNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                JSContext context = this.getLanguage().getJSContext();
                this.toObjectNode = this.insert(JSToObjectNode.createToObjectNoCheck(context));
                this.copyDataPropertiesNode = this.insert(CopyDataPropertiesNode.create(context));
            }
            Object from = this.toObjectNode.execute(sourceValue);
            this.copyDataPropertiesNode.execute(target, from);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ObjectLiteralSpreadMemberNode(this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags));
        }
    }

    private static class ObjectLiteralProtoMemberNode
    extends ObjectLiteralMemberNode {
        @Node.Child
        protected JavaScriptNode valueNode;

        ObjectLiteralProtoMemberNode(boolean isStatic, JavaScriptNode valueNode) {
            super(isStatic, 0);
            this.valueNode = valueNode;
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object value2 = this.valueNode.execute(frame);
            if (JSDynamicObject.isJSDynamicObject(value2)) {
                if (value2 == Undefined.instance) {
                    return;
                }
                JSObject.setPrototype(receiver, (JSDynamicObject)value2);
            }
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ObjectLiteralProtoMemberNode(this.isStatic, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags));
        }
    }

    private static class ComputedObjectLiteralAccessorMemberNode
    extends ObjectLiteralMemberNode
    implements AccessorMemberNode {
        @Node.Child
        private JavaScriptNode propertyKey;
        @Node.Child
        private JavaScriptNode getterNode;
        @Node.Child
        private JavaScriptNode setterNode;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKey;
        @Node.Child
        private SetFunctionNameNode setFunctionName;
        private final boolean isGetterAnonymousFunction;
        private final boolean isSetterAnonymousFunction;

        ComputedObjectLiteralAccessorMemberNode(JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode getter, JavaScriptNode setter) {
            super(isStatic, attributes);
            this.propertyKey = key;
            this.getterNode = getter;
            this.setterNode = setter;
            this.toPropertyKey = JSToPropertyKeyNode.create();
            this.isGetterAnonymousFunction = ComputedObjectLiteralAccessorMemberNode.isAnonymousFunctionDefinition(getter);
            this.isSetterAnonymousFunction = ComputedObjectLiteralAccessorMemberNode.isAnonymousFunctionDefinition(setter);
            this.setFunctionName = this.isGetterAnonymousFunction || this.isSetterAnonymousFunction ? SetFunctionNameNode.create() : null;
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object key = this.evaluateKey(frame);
            Object getterV = null;
            Object setterV = null;
            if (this.getterNode != null) {
                getterV = ComputedObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
                if (this.isGetterAnonymousFunction) {
                    this.setFunctionName.execute(getterV, key, Strings.GET);
                }
            }
            if (this.setterNode != null) {
                setterV = ComputedObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
                if (this.isSetterAnonymousFunction) {
                    this.setFunctionName.execute(setterV, key, Strings.SET);
                }
            }
            assert (getterV != null || setterV != null);
            PropertyDescriptor propDesc = PropertyDescriptor.createAccessor(getterV, setterV, this.attributes);
            JSRuntime.definePropertyOrThrow(receiver, key, propDesc);
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            Object key = this.propertyKey.execute(frame);
            return this.toPropertyKey.execute(key);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ComputedObjectLiteralAccessorMemberNode(JavaScriptNode.cloneUninitialized(this.propertyKey, materializedTags), this.isStatic, (int)this.attributes, JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags), JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags));
        }

        @Override
        public boolean hasGetter() {
            return this.getterNode != null;
        }

        @Override
        public boolean hasSetter() {
            return this.setterNode != null;
        }

        @Override
        public Object evaluateGetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            Object getterV = ComputedObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
            if (this.isGetterAnonymousFunction) {
                this.setFunctionName.execute(getterV, key, Strings.GET);
            }
            return getterV;
        }

        @Override
        public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            Object setterV = ComputedObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
            if (this.isSetterAnonymousFunction) {
                this.setFunctionName.execute(setterV, key, Strings.SET);
            }
            return setterV;
        }
    }

    public static abstract class ComputedObjectLiteralDataMemberNode
    extends ObjectLiteralMemberNode {
        @Node.Child
        private JavaScriptNode propertyKey;
        @Node.Child
        protected JavaScriptNode valueNode;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKey;
        @Node.Child
        protected SetFunctionNameNode setFunctionName;

        ComputedObjectLiteralDataMemberNode(JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition) {
            super(isStatic, false, attributes, isField, isAnonymousFunctionDefinition);
            this.propertyKey = key;
            this.valueNode = valueNode;
            this.toPropertyKey = JSToPropertyKeyNode.create();
            this.setFunctionName = ComputedObjectLiteralDataMemberNode.isAnonymousFunctionDefinition(valueNode) ? SetFunctionNameNode.create() : null;
        }

        @Specialization(guards={"!isFieldOrStaticBlock", "!isAnonymousFunctionDefinition", "setFunctionName==null", "!isMethodNode(valueNode)"}, limit="3")
        public final void doNoFieldNoFunctionDef(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm, @CachedLibrary(value="receiver") DynamicObjectLibrary dynamicObject) {
            Object key = this.evaluateKey(frame);
            Object value2 = this.valueNode.execute(frame);
            dynamicObject.putWithFlags(receiver, key, value2, this.attributes);
        }

        @Specialization
        public final void doGeneric(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object value2;
            if (this.isFieldOrStaticBlock) {
                return;
            }
            Object key = this.evaluateKey(frame);
            if (this.isAnonymousFunctionDefinition && this.valueNode instanceof NamedEvaluationTargetNode) {
                value2 = ((NamedEvaluationTargetNode)this.valueNode).executeWithName(frame, key);
            } else {
                value2 = ComputedObjectLiteralDataMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
                if (this.setFunctionName != null) {
                    this.setFunctionName.execute(value2, key);
                }
            }
            PropertyDescriptor propDesc = PropertyDescriptor.createData(value2, this.attributes);
            JSRuntime.definePropertyOrThrow(receiver, key, propDesc);
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            Object key = this.propertyKey.execute(frame);
            return this.toPropertyKey.execute(key);
        }

        @Override
        public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            Object value2;
            if (!(this.isFieldOrStaticBlock || this.isAnonymousFunctionDefinition || this.setFunctionName != null || ComputedObjectLiteralDataMemberNode.isMethodNode(this.valueNode))) {
                return this.valueNode.execute(frame);
            }
            if (this.isAnonymousFunctionDefinition && this.valueNode instanceof NamedEvaluationTargetNode) {
                value2 = ((NamedEvaluationTargetNode)this.valueNode).executeWithName(frame, key);
            } else {
                value2 = ComputedObjectLiteralDataMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
                if (this.setFunctionName != null) {
                    this.setFunctionName.execute(value2, key);
                }
            }
            return value2;
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(JavaScriptNode.cloneUninitialized(this.propertyKey, materializedTags), this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags), this.isFieldOrStaticBlock, this.isAnonymousFunctionDefinition);
        }
    }

    public static class ObjectLiteralAccessorMemberNode
    extends CachingObjectLiteralMemberNode
    implements AccessorMemberNode {
        @Node.Child
        protected JavaScriptNode getterNode;
        @Node.Child
        protected JavaScriptNode setterNode;

        ObjectLiteralAccessorMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode getter, JavaScriptNode setter) {
            super(name, isStatic, attributes, false);
            this.getterNode = getter;
            this.setterNode = setter;
        }

        @Override
        public boolean hasGetter() {
            return this.getterNode != null;
        }

        @Override
        public boolean hasSetter() {
            return this.setterNode != null;
        }

        @Override
        public Object evaluateGetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return ObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
        }

        @Override
        public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return ObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
        }

        @Override
        public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object getterV = null;
            Object setterV = null;
            if (this.getterNode != null) {
                getterV = ObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
            }
            if (this.setterNode != null) {
                setterV = ObjectLiteralAccessorMemberNode.evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
            }
            assert (getterV != null || setterV != null);
            this.execute(receiver, getterV, setterV);
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
        }

        private void execute(JSDynamicObject obj, Object getterV, Object setterV) {
            DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
            Object getter = getterV;
            Object setter = setterV;
            if ((this.getterNode == null || this.setterNode == null) && JSProperty.isAccessor(dynamicObjectLib.getPropertyFlagsOrDefault(obj, this.name, 0))) {
                Accessor existing = (Accessor)dynamicObjectLib.getOrDefault(obj, this.name, null);
                getter = getter == null ? existing.getGetter() : getter;
                setter = setter == null ? existing.getSetter() : setter;
            }
            Accessor accessor = new Accessor(getter, setter);
            dynamicObjectLib.putWithFlags(obj, this.name, accessor, this.attributes | 8);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ObjectLiteralAccessorMemberNode(this.name, this.isStatic, (int)this.attributes, JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags), JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags));
        }
    }

    public static interface AccessorMemberNode {
        public boolean hasGetter();

        public boolean hasSetter();

        public Object evaluateGetter(VirtualFrame var1, JSDynamicObject var2, Object var3, JSRealm var4);

        public Object evaluateSetter(VirtualFrame var1, JSDynamicObject var2, Object var3, JSRealm var4);
    }

    private static class ObjectLiteralDataMemberNode
    extends CachingObjectLiteralMemberNode {
        @Node.Child
        protected JavaScriptNode valueNode;

        ObjectLiteralDataMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isFieldOrStaticBlock) {
            super(name, isStatic, attributes, isFieldOrStaticBlock);
            this.valueNode = valueNode;
        }

        @Override
        public void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
            Object value2 = ObjectLiteralDataMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
            this.execute(receiver, value2, this.name);
        }

        @Override
        public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            return ObjectLiteralDataMemberNode.evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
        }

        @Override
        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
        }

        private void execute(JSDynamicObject obj, Object value2, Object key) {
            if (this.isFieldOrStaticBlock) {
                return;
            }
            DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
            dynamicObjectLib.putWithFlags(obj, key, value2, this.attributes);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ObjectLiteralDataMemberNode(this.name, this.isStatic, (int)this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags), this.isFieldOrStaticBlock);
        }
    }

    public static class AutoAccessorDataMemberNode
    extends ObjectLiteralDataMemberNode {
        private static final String ACCESSOR_STORAGE = " accessor storage";
        private static final HiddenKey STORAGE_KEY_MAGIC = new HiddenKey(":storage-key-magic");
        @Node.Child
        private PropertySetNode backingStorageMagicSetNode;
        private final JSFunctionData getterFunctionData;
        private final JSFunctionData setterFunctionData = this.createAutoAccessorSetFunctionData();

        AutoAccessorDataMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
            super(name, isStatic, attributes, valueNode, false);
            this.getterFunctionData = this.createAutoAccessorGetFunctionData();
            this.backingStorageMagicSetNode = PropertySetNode.createSetHidden(STORAGE_KEY_MAGIC, this.getRealm().getContext());
        }

        private static HiddenKey checkAutoaccessorTarget(VirtualFrame frame, PropertyGetNode getMagicNode, DynamicObjectLibrary storageLibrary, Object thiz) {
            JSFunctionObject function = JSFrameUtil.getFunctionObject(frame);
            HiddenKey backingStorageKey = (HiddenKey)getMagicNode.getValue(function);
            if (!(thiz instanceof JSDynamicObject) || !storageLibrary.containsKey((JSDynamicObject)thiz, backingStorageKey)) {
                CompilerDirectives.transferToInterpreter();
                throw JSException.create(JSErrorType.TypeError, "Bad auto-accessor target.");
            }
            return backingStorageKey;
        }

        @CompilerDirectives.TruffleBoundary
        private JSFunctionData createAutoAccessorSetFunctionData() {
            CompilerAsserts.neverPartOfCompilation();
            JSRealm realm = this.getRealm();
            final JSContext context = realm.getContext();
            RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
                @Node.Child
                private PropertyGetNode getMagicNode;
                @Node.Child
                private DynamicObjectLibrary storageLibrary;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.getMagicNode = PropertyGetNode.createGetHidden(STORAGE_KEY_MAGIC, context);
                    this.storageLibrary = DynamicObjectLibrary.getFactory().createDispatched(5);
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    HiddenKey backingStorageKey = AutoAccessorDataMemberNode.checkAutoaccessorTarget(frame, this.getMagicNode, this.storageLibrary, thiz);
                    Object[] args = frame.getArguments();
                    int userArgumentCount = JSArguments.getUserArgumentCount(args);
                    JSDynamicObject value2 = userArgumentCount > 0 ? JSArguments.getUserArgument(args, 0) : Undefined.instance;
                    this.storageLibrary.put((DynamicObject)thiz, backingStorageKey, value2);
                    return value2;
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(context, callTarget, 1, Strings.SET);
        }

        @CompilerDirectives.TruffleBoundary
        private JSFunctionData createAutoAccessorGetFunctionData() {
            CompilerAsserts.neverPartOfCompilation();
            JSRealm realm = this.getRealm();
            final JSContext context = realm.getContext();
            RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){
                @Node.Child
                private PropertyGetNode getMagicNode;
                @Node.Child
                private DynamicObjectLibrary storageLibrary;
                {
                    super(lang, sourceSection, frameDescriptor);
                    this.getMagicNode = PropertyGetNode.createGetHidden(STORAGE_KEY_MAGIC, context);
                    this.storageLibrary = DynamicObjectLibrary.getFactory().createDispatched(5);
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object thiz = JSFrameUtil.getThisObj(frame);
                    HiddenKey backingStorageKey = AutoAccessorDataMemberNode.checkAutoaccessorTarget(frame, this.getMagicNode, this.storageLibrary, thiz);
                    return this.storageLibrary.getOrDefault((DynamicObject)thiz, backingStorageKey, Undefined.instance);
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.GET);
        }

        public void executeWithGetterSetter(JSDynamicObject obj, Object key, JSDynamicObject getterV, JSDynamicObject setterV) {
            DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
            Accessor accessor = new Accessor(getterV, setterV);
            dynamicObjectLib.putWithFlags(obj, key, accessor, this.attributes | 8);
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new AutoAccessorDataMemberNode(this.name, this.isStatic, (int)this.attributes, this.valueNode);
        }

        public JSFunctionObject createAutoAccessorSetter(HiddenKey backingStorageKey) {
            JSFunctionObject functionObject = JSFunction.create(this.getRealm(), this.setterFunctionData);
            this.backingStorageMagicSetNode.setValue(functionObject, backingStorageKey);
            return functionObject;
        }

        public JSFunctionObject createAutoAccessorGetter(HiddenKey backingStorageKey) {
            JSFunctionObject functionObject = JSFunction.create(this.getRealm(), this.getterFunctionData);
            this.backingStorageMagicSetNode.setValue(functionObject, backingStorageKey);
            return functionObject;
        }

        @CompilerDirectives.TruffleBoundary
        public HiddenKey createBackingStorageKey(Object key) {
            return new HiddenKey(JSRuntime.safeToString(key) + ACCESSOR_STORAGE);
        }
    }

    public static class ComputedAutoAccessorDataMemberNode
    extends AutoAccessorDataMemberNode {
        @Node.Child
        private JavaScriptNode keyNode;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode;

        ComputedAutoAccessorDataMemberNode(JavaScriptNode keyNode, boolean isStatic, int attributes, JavaScriptNode valueNode) {
            super((Object)Undefined.instance, isStatic, attributes, valueNode);
            this.keyNode = keyNode;
            this.toPropertyKeyNode = JSToPropertyKeyNode.create();
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            return this.toPropertyKeyNode.execute(this.keyNode.execute(frame));
        }

        @Override
        protected ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ComputedAutoAccessorDataMemberNode(this.keyNode, this.isStatic, (int)this.attributes, this.valueNode);
        }
    }

    private static abstract class CachingObjectLiteralMemberNode
    extends ObjectLiteralMemberNode {
        protected final Object name;
        @Node.Child
        private DynamicObjectLibrary dynamicObjectLibrary;

        CachingObjectLiteralMemberNode(Object name, boolean isStatic, int attributes, boolean isFieldOrStaticBlock) {
            super(isStatic, false, attributes, isFieldOrStaticBlock, false);
            assert (this instanceof AutoAccessorDataMemberNode || JSRuntime.isPropertyKey(name) || name == null && isStatic && isFieldOrStaticBlock) : name;
            this.name = name;
        }

        @Override
        public Object evaluateKey(VirtualFrame frame) {
            return this.name;
        }

        protected final DynamicObjectLibrary dynamicObjectLibrary() {
            DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary;
            if (dynamicObjectLib == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                JSContext context = this.getLanguage().getJSContext();
                this.dynamicObjectLibrary = dynamicObjectLib = this.insert(JSObjectUtil.createDispatched(this.name, context.getPropertyCacheLimit()));
                JSObjectUtil.checkForNoSuchPropertyOrMethod(context, this.name);
            }
            return dynamicObjectLib;
        }
    }

    public static abstract class ObjectLiteralMemberNode
    extends JavaScriptBaseNode {
        public static final ObjectLiteralMemberNode[] EMPTY = new ObjectLiteralMemberNode[0];
        protected final boolean isStatic;
        protected final boolean isPrivate;
        protected final byte attributes;
        protected final boolean isFieldOrStaticBlock;
        protected final boolean isAnonymousFunctionDefinition;

        public ObjectLiteralMemberNode(boolean isStatic, int attributes) {
            this(isStatic, false, attributes, false, false);
        }

        public ObjectLiteralMemberNode(boolean isStatic, boolean isPrivate, int attributes, boolean isFieldOrStaticBlock, boolean isAnonymousFunctionDefinition) {
            assert (attributes == (attributes & 7));
            this.isStatic = isStatic;
            this.isPrivate = isPrivate;
            this.attributes = (byte)attributes;
            this.isFieldOrStaticBlock = isFieldOrStaticBlock;
            this.isAnonymousFunctionDefinition = isAnonymousFunctionDefinition;
        }

        public abstract void executeVoid(VirtualFrame var1, JSDynamicObject var2, JSDynamicObject var3, JSRealm var4);

        public final void executeVoid(VirtualFrame frame, JSDynamicObject obj, JSRealm realm) {
            this.executeVoid(frame, obj, obj, realm);
        }

        public Object evaluateKey(VirtualFrame frame) {
            throw Errors.shouldNotReachHere(this.getClass().getName());
        }

        public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
            throw Errors.shouldNotReachHere();
        }

        public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value2, JSRealm realm) {
            throw Errors.shouldNotReachHere(this.getClass().getName());
        }

        public final boolean isStatic() {
            return this.isStatic;
        }

        public final boolean isPrivate() {
            return this.isPrivate;
        }

        public final boolean isFieldOrStaticBlock() {
            return this.isFieldOrStaticBlock;
        }

        public final boolean isAnonymousFunctionDefinition() {
            return this.isAnonymousFunctionDefinition;
        }

        static boolean isAnonymousFunctionDefinition(JavaScriptNode expression) {
            return expression instanceof FunctionNameHolder && ((FunctionNameHolder)((Object)expression)).isAnonymous();
        }

        protected static boolean isMethodNode(JavaScriptNode valueNode) {
            return valueNode instanceof MakeMethodNode;
        }

        protected static Object evaluateWithHomeObject(JavaScriptNode valueNode, VirtualFrame frame, JSDynamicObject obj, JSRealm realm) {
            if (ObjectLiteralMemberNode.isMethodNode(valueNode)) {
                return ((MakeMethodNode)valueNode).executeWithObject(frame, obj, realm);
            }
            return ObjectLiteralNode.executeWithRealm(valueNode, frame, realm);
        }

        protected abstract ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> var1);

        public static ObjectLiteralMemberNode[] cloneUninitialized(ObjectLiteralMemberNode[] members, Set<Class<? extends Tag>> materializedTags) {
            ObjectLiteralMemberNode[] copy = (ObjectLiteralMemberNode[])members.clone();
            for (int i = 0; i < copy.length; ++i) {
                copy[i] = copy[i].copyUninitialized(materializedTags);
            }
            return copy;
        }

        public int getAttributes() {
            return this.attributes;
        }
    }

    public static final class MakeMethodNode
    extends JavaScriptNode
    implements FunctionNameHolder.Delegate {
        @Node.Child
        private JavaScriptNode functionNode;
        @Node.Child
        private PropertySetNode makeMethodNode;

        private MakeMethodNode(JSContext context, JavaScriptNode functionNode) {
            this.functionNode = functionNode;
            this.makeMethodNode = PropertySetNode.createSetHidden(JSFunction.HOME_OBJECT_ID, context);
        }

        private MakeMethodNode(JSContext context, JavaScriptNode functionNode, HiddenKey key) {
            this.functionNode = functionNode;
            this.makeMethodNode = PropertySetNode.createSetHidden(key, context);
        }

        public static JavaScriptNode create(JSContext context, JavaScriptNode functionNode) {
            return new MakeMethodNode(context, functionNode);
        }

        public static JavaScriptNode createWithKey(JSContext context, JavaScriptNode functionNode, HiddenKey key) {
            return new MakeMethodNode(context, functionNode, key);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.functionNode.execute(frame);
        }

        public Object executeWithObject(VirtualFrame frame, JSDynamicObject obj, JSRealm realm) {
            Object function = ObjectLiteralNode.executeWithRealm(this.functionNode, frame, realm);
            this.makeMethodNode.setValue(function, obj);
            return function;
        }

        @Override
        public FunctionNameHolder getFunctionNameHolder() {
            return (FunctionNameHolder)((Object)this.functionNode);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return MakeMethodNode.create(this.makeMethodNode.getContext(), MakeMethodNode.cloneUninitialized(this.functionNode, materializedTags));
        }
    }
}

