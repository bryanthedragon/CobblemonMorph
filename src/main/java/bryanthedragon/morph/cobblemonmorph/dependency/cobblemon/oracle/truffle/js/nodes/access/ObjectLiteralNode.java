package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
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

public class ObjectLiteralNode extends JavaScriptNode {
   @Node.Children
   private final ObjectLiteralNode.ObjectLiteralMemberNode[] members;
   @Node.Child
   private CreateObjectNode objectCreateNode;

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.LiteralTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.ObjectLiteral.name());
   }

   protected static Object executeWithRealm(JavaScriptNode valueNode, VirtualFrame frame, JSRealm realm) {
      return valueNode instanceof JSFunctionExpressionNode ? ((JSFunctionExpressionNode)valueNode).executeWithRealm(frame, realm) : valueNode.execute(frame);
   }

   public static boolean isAutoAccessor(ObjectLiteralNode.ObjectLiteralMemberNode memberNode) {
      return memberNode instanceof ObjectLiteralNode.AutoAccessorDataMemberNode;
   }

   public static boolean isPrivateMethod(ObjectLiteralNode.ObjectLiteralMemberNode memberNode) {
      assert isMethod(memberNode);

      return memberNode instanceof ObjectLiteralNode.PrivateMethodMemberNode;
   }

   public static boolean isMethod(ObjectLiteralNode.ObjectLiteralMemberNode memberNode) {
      if (memberNode instanceof ObjectLiteralNode.PrivateMethodMemberNode) {
         return true;
      } else {
         return !(memberNode instanceof ObjectLiteralNode.AccessorMemberNode) && !(memberNode instanceof ObjectLiteralNode.AutoAccessorDataMemberNode)
            ? !memberNode.isFieldOrStaticBlock()
            : false;
      }
   }

   public static boolean isAccessor(ObjectLiteralNode.ObjectLiteralMemberNode memberNode) {
      return memberNode instanceof ObjectLiteralNode.AccessorMemberNode;
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newDataMember(
      TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode, boolean isField
   ) {
      return new ObjectLiteralNode.ObjectLiteralDataMemberNode(
         name, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode, isField
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newAutoAccessor(TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode) {
      return new ObjectLiteralNode.AutoAccessorDataMemberNode(
         name, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newComputedAutoAccessor(
      JavaScriptNode keyNode, boolean isStatic, boolean enumerable, JavaScriptNode valueNode
   ) {
      return new ObjectLiteralNode.ComputedAutoAccessorDataMemberNode(
         keyNode, isStatic, enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable(), valueNode
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newAccessorMember(
      TruffleString name, boolean isStatic, boolean enumerable, JavaScriptNode getterNode, JavaScriptNode setterNode
   ) {
      return new ObjectLiteralNode.ObjectLiteralAccessorMemberNode(
         name, isStatic, JSAttributes.fromConfigurableEnumerable(true, enumerable), getterNode, setterNode
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newComputedDataMember(
      JavaScriptNode name, boolean isStatic, boolean enumerable, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition
   ) {
      int attributes = enumerable ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable();
      return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(
         name, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newComputedAccessorMember(
      JavaScriptNode name, boolean isStatic, boolean enumerable, JavaScriptNode getter, JavaScriptNode setter
   ) {
      return new ObjectLiteralNode.ComputedObjectLiteralAccessorMemberNode(
         name, isStatic, JSAttributes.fromConfigurableEnumerable(true, enumerable), getter, setter
      );
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newDataMember(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
      return new ObjectLiteralNode.ObjectLiteralDataMemberNode(name, isStatic, attributes, valueNode, false);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newAccessorMember(
      Object name, boolean isStatic, int attributes, JavaScriptNode getterNode, JavaScriptNode setterNode
   ) {
      return new ObjectLiteralNode.ObjectLiteralAccessorMemberNode(name, isStatic, attributes, getterNode, setterNode);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newComputedDataMember(
      JavaScriptNode name, boolean isStatic, int attributes, JavaScriptNode valueNode
   ) {
      return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(name, isStatic, attributes, valueNode, false, false);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newPrivateFieldMember(
      JavaScriptNode name, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode
   ) {
      return new ObjectLiteralNode.PrivateFieldMemberNode(name, isStatic, valueNode, writePrivateNode);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newPrivateMethodMember(
      TruffleString privateName, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex
   ) {
      return new ObjectLiteralNode.PrivateMethodMemberNode(privateName, isStatic, valueNode, writePrivateNode, privateBrandSlotIndex);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newPrivateAccessorMember(
      boolean isStatic, JavaScriptNode getterNode, JavaScriptNode setterNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex
   ) {
      return new ObjectLiteralNode.PrivateAccessorMemberNode(isStatic, getterNode, setterNode, writePrivateNode, privateBrandSlotIndex);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newProtoMember(TruffleString name, boolean isStatic, JavaScriptNode valueNode) {
      assert Strings.equals(JSObject.PROTO, name);

      return new ObjectLiteralNode.ObjectLiteralProtoMemberNode(isStatic, valueNode);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newSpreadObjectMember(boolean isStatic, JavaScriptNode valueNode) {
      return new ObjectLiteralNode.ObjectLiteralSpreadMemberNode(isStatic, JSAttributes.getDefault(), valueNode);
   }

   public static ObjectLiteralNode.ObjectLiteralMemberNode newStaticBlockMember(JavaScriptNode valueNode) {
      return new ObjectLiteralNode.ObjectLiteralDataMemberNode(null, true, JSAttributes.getDefaultNotEnumerable(), valueNode, true);
   }

   public ObjectLiteralNode(ObjectLiteralNode.ObjectLiteralMemberNode[] members, CreateObjectNode objectCreateNode) {
      this.members = members;
      this.objectCreateNode = objectCreateNode;
   }

   public static ObjectLiteralNode create(JSContext context, ObjectLiteralNode.ObjectLiteralMemberNode[] members) {
      if (members.length > 0 && members[0] instanceof ObjectLiteralNode.ObjectLiteralProtoMemberNode) {
         return new ObjectLiteralNode(
            Arrays.copyOfRange(members, 1, members.length),
            CreateObjectNode.createOrdinaryWithPrototype(context, ((ObjectLiteralNode.ObjectLiteralProtoMemberNode)members[0]).valueNode)
         );
      } else {
         return members.length > 256 && onlyDataMembers(members)
            ? createDictionaryObject(context, members)
            : new ObjectLiteralNode(members, CreateObjectNode.create(context));
      }
   }

   private static boolean onlyDataMembers(ObjectLiteralNode.ObjectLiteralMemberNode[] members) {
      for (ObjectLiteralNode.ObjectLiteralMemberNode member : members) {
         if (!(member instanceof ObjectLiteralNode.ObjectLiteralDataMemberNode)) {
            return false;
         }
      }

      return true;
   }

   private static ObjectLiteralNode createDictionaryObject(JSContext context, ObjectLiteralNode.ObjectLiteralMemberNode[] members) {
      ObjectLiteralNode.ObjectLiteralMemberNode[] newMembers = new ObjectLiteralNode.ObjectLiteralMemberNode[members.length];

      for (int i = 0; i < members.length; i++) {
         ObjectLiteralNode.ObjectLiteralDataMemberNode member = (ObjectLiteralNode.ObjectLiteralDataMemberNode)members[i];
         newMembers[i] = new ObjectLiteralNode.DictionaryObjectDataMemberNode(member.name, member.isStatic, member.attributes, member.valueNode);
      }

      return new ObjectLiteralNode(newMembers, CreateObjectNode.createDictionary(context));
   }

   public JSDynamicObject execute(VirtualFrame frame) {
      JSRealm realm = this.getRealm();
      JSDynamicObject ret = this.objectCreateNode.executeWithRealm(frame, realm);
      return this.executeWithObject(frame, ret, realm);
   }

   @ExplodeLoop
   protected JSDynamicObject executeWithObject(VirtualFrame frame, JSDynamicObject ret, JSRealm realm) {
      for (int i = 0; i < this.members.length; i++) {
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
      return new ObjectLiteralNode(
         ObjectLiteralNode.ObjectLiteralMemberNode.cloneUninitialized(this.members, materializedTags),
         this.objectCreateNode.copyUninitialized(materializedTags)
      );
   }

   public interface AccessorMemberNode {
      boolean hasGetter();

      boolean hasSetter();

      Object evaluateGetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm);

      Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm);
   }

   public static class AutoAccessorDataMemberNode extends ObjectLiteralNode.ObjectLiteralDataMemberNode {
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
         Object function = JSFrameUtil.getFunctionObject(frame);
         HiddenKey backingStorageKey = (HiddenKey)getMagicNode.getValue(function);
         if (thiz instanceof JSDynamicObject && storageLibrary.containsKey((JSDynamicObject)thiz, backingStorageKey)) {
            return backingStorageKey;
         } else {
            CompilerDirectives.transferToInterpreter();
            throw JSException.create(JSErrorType.TypeError, "Bad auto-accessor target.");
         }
      }

      @CompilerDirectives.TruffleBoundary
      private JSFunctionData createAutoAccessorSetFunctionData() {
         CompilerAsserts.neverPartOfCompilation();
         JSRealm realm = this.getRealm();
         final JSContext context = realm.getContext();
         CallTarget callTarget = (new JavaScriptRootNode(context.getLanguage(), null, null) {
               @Node.Child
               private PropertyGetNode getMagicNode = PropertyGetNode.createGetHidden(ObjectLiteralNode.AutoAccessorDataMemberNode.STORAGE_KEY_MAGIC, context);
               @Node.Child
               private DynamicObjectLibrary storageLibrary = DynamicObjectLibrary.getFactory().createDispatched(5);

               @Override
               public Object execute(VirtualFrame frame) {
                  Object thiz = JSFrameUtil.getThisObj(frame);
                  HiddenKey backingStorageKey = ObjectLiteralNode.AutoAccessorDataMemberNode.checkAutoaccessorTarget(
                     frame, this.getMagicNode, this.storageLibrary, thiz
                  );
                  Object[] args = frame.getArguments();
                  int userArgumentCount = JSArguments.getUserArgumentCount(args);
                  Object value = userArgumentCount > 0 ? JSArguments.getUserArgument(args, 0) : Undefined.instance;
                  this.storageLibrary.put((DynamicObject)thiz, backingStorageKey, value);
                  return value;
               }
            })
            .getCallTarget();
         return JSFunctionData.createCallOnly(context, callTarget, 1, Strings.SET);
      }

      @CompilerDirectives.TruffleBoundary
      private JSFunctionData createAutoAccessorGetFunctionData() {
         CompilerAsserts.neverPartOfCompilation();
         JSRealm realm = this.getRealm();
         final JSContext context = realm.getContext();
         CallTarget callTarget = (new JavaScriptRootNode(context.getLanguage(), null, null) {
               @Node.Child
               private PropertyGetNode getMagicNode = PropertyGetNode.createGetHidden(ObjectLiteralNode.AutoAccessorDataMemberNode.STORAGE_KEY_MAGIC, context);
               @Node.Child
               private DynamicObjectLibrary storageLibrary = DynamicObjectLibrary.getFactory().createDispatched(5);

               @Override
               public Object execute(VirtualFrame frame) {
                  Object thiz = JSFrameUtil.getThisObj(frame);
                  HiddenKey backingStorageKey = ObjectLiteralNode.AutoAccessorDataMemberNode.checkAutoaccessorTarget(
                     frame, this.getMagicNode, this.storageLibrary, thiz
                  );
                  return this.storageLibrary.getOrDefault((DynamicObject)thiz, backingStorageKey, Undefined.instance);
               }
            })
            .getCallTarget();
         return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.GET);
      }

      public void executeWithGetterSetter(JSDynamicObject obj, Object key, JSDynamicObject getterV, JSDynamicObject setterV) {
         DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
         Accessor accessor = new Accessor(getterV, setterV);
         dynamicObjectLib.putWithFlags(obj, key, accessor, this.attributes | 8);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.AutoAccessorDataMemberNode(this.name, this.isStatic, this.attributes, this.valueNode);
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
         return new HiddenKey(JSRuntime.safeToString(key) + " accessor storage");
      }
   }

   private abstract static class CachingObjectLiteralMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
      protected final Object name;
      @Node.Child
      private DynamicObjectLibrary dynamicObjectLibrary;

      CachingObjectLiteralMemberNode(Object name, boolean isStatic, int attributes, boolean isFieldOrStaticBlock) {
         super(isStatic, false, attributes, isFieldOrStaticBlock, false);

         assert this instanceof ObjectLiteralNode.AutoAccessorDataMemberNode
            || JSRuntime.isPropertyKey(name)
            || name == null && isStatic && isFieldOrStaticBlock : name;

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

   public static class ComputedAutoAccessorDataMemberNode extends ObjectLiteralNode.AutoAccessorDataMemberNode {
      @Node.Child
      private JavaScriptNode keyNode;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode;

      ComputedAutoAccessorDataMemberNode(JavaScriptNode keyNode, boolean isStatic, int attributes, JavaScriptNode valueNode) {
         super(Undefined.instance, isStatic, attributes, valueNode);
         this.keyNode = keyNode;
         this.toPropertyKeyNode = JSToPropertyKeyNode.create();
      }

      @Override
      public Object evaluateKey(VirtualFrame frame) {
         return this.toPropertyKeyNode.execute(this.keyNode.execute(frame));
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ComputedAutoAccessorDataMemberNode(this.keyNode, this.isStatic, this.attributes, this.valueNode);
      }
   }

   private static class ComputedObjectLiteralAccessorMemberNode
      extends ObjectLiteralNode.ObjectLiteralMemberNode
      implements ObjectLiteralNode.AccessorMemberNode {
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
         this.isGetterAnonymousFunction = isAnonymousFunctionDefinition(getter);
         this.isSetterAnonymousFunction = isAnonymousFunctionDefinition(setter);
         this.setFunctionName = !this.isGetterAnonymousFunction && !this.isSetterAnonymousFunction ? null : SetFunctionNameNode.create();
      }

      @Override
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object key = this.evaluateKey(frame);
         Object getterV = null;
         Object setterV = null;
         if (this.getterNode != null) {
            getterV = evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
            if (this.isGetterAnonymousFunction) {
               this.setFunctionName.execute(getterV, key, Strings.GET);
            }
         }

         if (this.setterNode != null) {
            setterV = evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
            if (this.isSetterAnonymousFunction) {
               this.setFunctionName.execute(setterV, key, Strings.SET);
            }
         }

         assert getterV != null || setterV != null;

         PropertyDescriptor propDesc = PropertyDescriptor.createAccessor(getterV, setterV, this.attributes);
         JSRuntime.definePropertyOrThrow(receiver, key, propDesc);
      }

      @Override
      public Object evaluateKey(VirtualFrame frame) {
         Object key = this.propertyKey.execute(frame);
         return this.toPropertyKey.execute(key);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ComputedObjectLiteralAccessorMemberNode(
            JavaScriptNode.cloneUninitialized(this.propertyKey, materializedTags),
            this.isStatic,
            this.attributes,
            JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags)
         );
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
         Object getterV = evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
         if (this.isGetterAnonymousFunction) {
            this.setFunctionName.execute(getterV, key, Strings.GET);
         }

         return getterV;
      }

      @Override
      public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         Object setterV = evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
         if (this.isSetterAnonymousFunction) {
            this.setFunctionName.execute(setterV, key, Strings.SET);
         }

         return setterV;
      }
   }

   public abstract static class ComputedObjectLiteralDataMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
      @Node.Child
      private JavaScriptNode propertyKey;
      @Node.Child
      protected JavaScriptNode valueNode;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKey;
      @Node.Child
      protected SetFunctionNameNode setFunctionName;

      ComputedObjectLiteralDataMemberNode(
         JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition
      ) {
         super(isStatic, false, attributes, isField, isAnonymousFunctionDefinition);
         this.propertyKey = key;
         this.valueNode = valueNode;
         this.toPropertyKey = JSToPropertyKeyNode.create();
         this.setFunctionName = isAnonymousFunctionDefinition(valueNode) ? SetFunctionNameNode.create() : null;
      }

      @Specialization(guards = {"!isFieldOrStaticBlock", "!isAnonymousFunctionDefinition", "setFunctionName==null", "!isMethodNode(valueNode)"}, limit = "3")
      public final void doNoFieldNoFunctionDef(
         VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm, @CachedLibrary("receiver") DynamicObjectLibrary dynamicObject
      ) {
         Object key = this.evaluateKey(frame);
         Object value = this.valueNode.execute(frame);
         dynamicObject.putWithFlags(receiver, key, value, this.attributes);
      }

      @Specialization
      public final void doGeneric(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         if (!this.isFieldOrStaticBlock) {
            Object key = this.evaluateKey(frame);
            Object value;
            if (this.isAnonymousFunctionDefinition && this.valueNode instanceof NamedEvaluationTargetNode) {
               value = ((NamedEvaluationTargetNode)this.valueNode).executeWithName(frame, key);
            } else {
               value = evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
               if (this.setFunctionName != null) {
                  this.setFunctionName.execute(value, key);
               }
            }

            PropertyDescriptor propDesc = PropertyDescriptor.createData(value, this.attributes);
            JSRuntime.definePropertyOrThrow(receiver, key, propDesc);
         }
      }

      @Override
      public Object evaluateKey(VirtualFrame frame) {
         Object key = this.propertyKey.execute(frame);
         return this.toPropertyKey.execute(key);
      }

      @Override
      public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         if (!this.isFieldOrStaticBlock && !this.isAnonymousFunctionDefinition && this.setFunctionName == null && !isMethodNode(this.valueNode)) {
            return this.valueNode.execute(frame);
         } else {
            Object value;
            if (this.isAnonymousFunctionDefinition && this.valueNode instanceof NamedEvaluationTargetNode) {
               value = ((NamedEvaluationTargetNode)this.valueNode).executeWithName(frame, key);
            } else {
               value = evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
               if (this.setFunctionName != null) {
                  this.setFunctionName.execute(value, key);
               }
            }

            return value;
         }
      }

      @Override
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.create(
            JavaScriptNode.cloneUninitialized(this.propertyKey, materializedTags),
            this.isStatic,
            this.attributes,
            JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags),
            this.isFieldOrStaticBlock,
            this.isAnonymousFunctionDefinition
         );
      }
   }

   private static class DictionaryObjectDataMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
      private final Object name;
      @Node.Child
      private JavaScriptNode valueNode;

      DictionaryObjectDataMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode) {
         super(isStatic, attributes);

         assert JSRuntime.isPropertyKey(name);

         this.name = name;
         this.valueNode = valueNode;
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object value = evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
         PropertyDescriptor propDesc = PropertyDescriptor.createData(value, this.attributes);
         JSObject.defineOwnProperty(receiver, this.name, propDesc, true);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.DictionaryObjectDataMemberNode(
            this.name, this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags)
         );
      }
   }

   public static final class MakeMethodNode extends JavaScriptNode implements FunctionNameHolder.Delegate {
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
         return new ObjectLiteralNode.MakeMethodNode(context, functionNode);
      }

      public static JavaScriptNode createWithKey(JSContext context, JavaScriptNode functionNode, HiddenKey key) {
         return new ObjectLiteralNode.MakeMethodNode(context, functionNode, key);
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
         return (FunctionNameHolder)this.functionNode;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return create(this.makeMethodNode.getContext(), cloneUninitialized(this.functionNode, materializedTags));
      }
   }

   public static class ObjectLiteralAccessorMemberNode extends ObjectLiteralNode.CachingObjectLiteralMemberNode implements ObjectLiteralNode.AccessorMemberNode {
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
         return evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
      }

      @Override
      public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         return evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object getterV = null;
         Object setterV = null;
         if (this.getterNode != null) {
            getterV = evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
         }

         if (this.setterNode != null) {
            setterV = evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
         }

         assert getterV != null || setterV != null;

         this.execute(receiver, getterV, setterV);
      }

      @Override
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
      }

      private void execute(JSDynamicObject obj, Object getterV, Object setterV) {
         DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
         Object getter = getterV;
         Object setter = setterV;
         if ((this.getterNode == null || this.setterNode == null) && JSProperty.isAccessor(dynamicObjectLib.getPropertyFlagsOrDefault(obj, this.name, 0))) {
            Accessor existing = (Accessor)dynamicObjectLib.getOrDefault(obj, this.name, null);
            getter = getterV == null ? existing.getGetter() : getterV;
            setter = setterV == null ? existing.getSetter() : setterV;
         }

         Accessor accessor = new Accessor(getter, setter);
         dynamicObjectLib.putWithFlags(obj, this.name, accessor, this.attributes | 8);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ObjectLiteralAccessorMemberNode(
            this.name,
            this.isStatic,
            this.attributes,
            JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags)
         );
      }
   }

   private static class ObjectLiteralDataMemberNode extends ObjectLiteralNode.CachingObjectLiteralMemberNode {
      @Node.Child
      protected JavaScriptNode valueNode;

      ObjectLiteralDataMemberNode(Object name, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isFieldOrStaticBlock) {
         super(name, isStatic, attributes, isFieldOrStaticBlock);
         this.valueNode = valueNode;
      }

      @Override
      public void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object value = evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
         this.execute(receiver, value, this.name);
      }

      @Override
      public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         return evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
      }

      @Override
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
      }

      private void execute(JSDynamicObject obj, Object value, Object key) {
         if (!this.isFieldOrStaticBlock) {
            DynamicObjectLibrary dynamicObjectLib = this.dynamicObjectLibrary();
            dynamicObjectLib.putWithFlags(obj, key, value, this.attributes);
         }
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ObjectLiteralDataMemberNode(
            this.name, this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags), this.isFieldOrStaticBlock
         );
      }
   }

   public abstract static class ObjectLiteralMemberNode extends JavaScriptBaseNode {
      public static final ObjectLiteralNode.ObjectLiteralMemberNode[] EMPTY = new ObjectLiteralNode.ObjectLiteralMemberNode[0];
      protected final boolean isStatic;
      protected final boolean isPrivate;
      protected final byte attributes;
      protected final boolean isFieldOrStaticBlock;
      protected final boolean isAnonymousFunctionDefinition;

      public ObjectLiteralMemberNode(boolean isStatic, int attributes) {
         this(isStatic, false, attributes, false, false);
      }

      public ObjectLiteralMemberNode(boolean isStatic, boolean isPrivate, int attributes, boolean isFieldOrStaticBlock, boolean isAnonymousFunctionDefinition) {
         assert attributes == (attributes & 7);

         this.isStatic = isStatic;
         this.isPrivate = isPrivate;
         this.attributes = (byte)attributes;
         this.isFieldOrStaticBlock = isFieldOrStaticBlock;
         this.isAnonymousFunctionDefinition = isAnonymousFunctionDefinition;
      }

      public abstract void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm);

      public final void executeVoid(VirtualFrame frame, JSDynamicObject obj, JSRealm realm) {
         this.executeVoid(frame, obj, obj, realm);
      }

      public Object evaluateKey(VirtualFrame frame) {
         throw Errors.shouldNotReachHere(this.getClass().getName());
      }

      public Object evaluateValue(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         throw Errors.shouldNotReachHere();
      }

      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
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
         return expression instanceof FunctionNameHolder && ((FunctionNameHolder)expression).isAnonymous();
      }

      protected static boolean isMethodNode(JavaScriptNode valueNode) {
         return valueNode instanceof ObjectLiteralNode.MakeMethodNode;
      }

      protected static Object evaluateWithHomeObject(JavaScriptNode valueNode, VirtualFrame frame, JSDynamicObject obj, JSRealm realm) {
         return isMethodNode(valueNode)
            ? ((ObjectLiteralNode.MakeMethodNode)valueNode).executeWithObject(frame, obj, realm)
            : ObjectLiteralNode.executeWithRealm(valueNode, frame, realm);
      }

      protected abstract ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags);

      public static ObjectLiteralNode.ObjectLiteralMemberNode[] cloneUninitialized(
         ObjectLiteralNode.ObjectLiteralMemberNode[] members, Set<Class<? extends Tag>> materializedTags
      ) {
         ObjectLiteralNode.ObjectLiteralMemberNode[] copy = (ObjectLiteralNode.ObjectLiteralMemberNode[])members.clone();

         for (int i = 0; i < copy.length; i++) {
            copy[i] = copy[i].copyUninitialized(materializedTags);
         }

         return copy;
      }

      public int getAttributes() {
         return this.attributes;
      }
   }

   private static class ObjectLiteralProtoMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
      @Node.Child
      protected JavaScriptNode valueNode;

      ObjectLiteralProtoMemberNode(boolean isStatic, JavaScriptNode valueNode) {
         super(isStatic, 0);
         this.valueNode = valueNode;
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object value = this.valueNode.execute(frame);
         if (JSDynamicObject.isJSDynamicObject(value)) {
            if (value == Undefined.instance) {
               return;
            }

            JSObject.setPrototype(receiver, (JSDynamicObject)value);
         }
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ObjectLiteralProtoMemberNode(this.isStatic, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags));
      }
   }

   private static class ObjectLiteralSpreadMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
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
         if (!JSGuards.isNullOrUndefined(sourceValue)) {
            if (this.toObjectNode == null || this.copyDataPropertiesNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               JSContext context = this.getLanguage().getJSContext();
               this.toObjectNode = this.insert(JSToObjectNode.createToObjectNoCheck(context));
               this.copyDataPropertiesNode = this.insert(CopyDataPropertiesNode.create(context));
            }

            Object from = this.toObjectNode.execute(sourceValue);
            this.copyDataPropertiesNode.execute(target, from);
         }
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.ObjectLiteralSpreadMemberNode(
            this.isStatic, this.attributes, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags)
         );
      }
   }

   public static class PrivateAccessorMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode implements ObjectLiteralNode.AccessorMemberNode {
      @Node.Child
      private JavaScriptNode getterNode;
      @Node.Child
      private JavaScriptNode setterNode;
      @Node.Child
      private JSWriteFrameSlotNode writePrivateNode;
      private final int privateBrandSlotIndex;

      PrivateAccessorMemberNode(
         boolean isStatic, JavaScriptNode getterNode, JavaScriptNode setterNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex
      ) {
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
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
         this.executeVoid(frame, obj, realm);
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object getter = null;
         Object setter = null;
         if (this.getterNode != null) {
            getter = evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
         }

         if (this.setterNode != null) {
            setter = evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
         }

         assert getter != null || setter != null;

         Accessor accessor = new Accessor(getter, setter);
         this.writePrivateNode.executeWrite(frame, accessor);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.PrivateAccessorMemberNode(
            this.isStatic,
            JavaScriptNode.cloneUninitialized(this.getterNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.setterNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags),
            this.privateBrandSlotIndex
         );
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
         return evaluateWithHomeObject(this.getterNode, frame, homeObject, realm);
      }

      @Override
      public Object evaluateSetter(VirtualFrame frame, JSDynamicObject homeObject, Object key, JSRealm realm) {
         return evaluateWithHomeObject(this.setterNode, frame, homeObject, realm);
      }
   }

   private static class PrivateFieldMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
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
         return evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.PrivateFieldMemberNode(
            JavaScriptNode.cloneUninitialized(this.keyNode, materializedTags),
            this.isStatic,
            JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags)
         );
      }
   }

   public static class PrivateMethodMemberNode extends ObjectLiteralNode.ObjectLiteralMemberNode {
      @Node.Child
      private JavaScriptNode valueNode;
      @Node.Child
      private JSWriteFrameSlotNode writePrivateNode;
      private final TruffleString privateName;
      private final int privateBrandSlotIndex;

      PrivateMethodMemberNode(
         TruffleString privateName, boolean isStatic, JavaScriptNode valueNode, JSWriteFrameSlotNode writePrivateNode, int privateBrandSlotIndex
      ) {
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
         return evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
      }

      @Override
      public void evaluateWithKeyAndValue(VirtualFrame frame, JSDynamicObject obj, Object key, Object value, JSRealm realm) {
         this.writePrivateNode.executeWrite(frame, value);
      }

      @Override
      public final void executeVoid(VirtualFrame frame, JSDynamicObject receiver, JSDynamicObject homeObject, JSRealm realm) {
         Object value = evaluateWithHomeObject(this.valueNode, frame, homeObject, realm);
         this.writePrivateNode.executeWrite(frame, value);
      }

      @Override
      protected ObjectLiteralNode.ObjectLiteralMemberNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ObjectLiteralNode.PrivateMethodMemberNode(
            this.privateName,
            this.isStatic,
            JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags),
            JavaScriptNode.cloneUninitialized(this.writePrivateNode, materializedTags),
            this.privateBrandSlotIndex
         );
      }
   }
}
