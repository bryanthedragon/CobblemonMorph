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

@NodeInfo(shortName = "delete")
@ImportStatic(JSConfig.class)
public abstract class DeletePropertyNode extends JSTargetableNode {
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
      return create(null, null, strict, context);
   }

   public static DeletePropertyNode createNonStrict(JSContext context) {
      return create(null, null, false, context);
   }

   public static DeletePropertyNode create(JavaScriptNode object, JavaScriptNode property, boolean strict, JSContext context) {
      return DeletePropertyNodeGen.create(strict, context, object, property);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (this.materializationNeeded() && materializedTags.contains(JSTags.UnaryOperationTag.class)) {
         JavaScriptNode key = cloneUninitialized(this.propertyNode, materializedTags);
         JavaScriptNode target = cloneUninitialized(this.targetNode, materializedTags);
         transferSourceSectionAddExpressionTag(this, key);
         transferSourceSectionAddExpressionTag(this, target);
         DeletePropertyNode node = create(target, key, this.strict, this.context);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
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

   public abstract boolean executeEvaluated(Object objectResult, Object propertyResult);

   @Specialization(guards = "isJSOrdinaryObject(targetObject)")
   protected final boolean doJSOrdinaryObject(
      JSDynamicObject targetObject,
      Object key,
      @Cached.Shared("toPropertyKey") @Cached("create()") JSToPropertyKeyNode toPropertyKeyNode,
      @CachedLibrary(limit = "InteropLibraryLimit") DynamicObjectLibrary dynamicObjectLib
   ) {
      Object propertyKey = toPropertyKeyNode.execute(key);
      Property foundProperty = dynamicObjectLib.getProperty(targetObject, propertyKey);
      if (foundProperty != null) {
         if (!JSProperty.isConfigurable(foundProperty)) {
            if (this.strict) {
               throw Errors.createTypeErrorNotConfigurableProperty(propertyKey);
            } else {
               return false;
            }
         } else {
            dynamicObjectLib.removeKey(targetObject, propertyKey);
            return true;
         }
      } else {
         return true;
      }
   }

   @Specialization(guards = "!isJSOrdinaryObject(targetObject)")
   protected final boolean doJSObject(
      JSDynamicObject targetObject,
      Object key,
      @Cached("createIsFastArray()") IsArrayNode isArrayNode,
      @Cached("createBinaryProfile()") ConditionProfile arrayProfile,
      @Cached ToArrayIndexNode toArrayIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile arrayIndexProfile,
      @Cached("create(context, strict)") JSArrayDeleteIndexNode deleteArrayIndexNode,
      @Cached JSClassProfile jsclassProfile,
      @Cached.Shared("toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode
   ) {
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
   protected static boolean doSymbol(Symbol target, Object property, @Cached.Shared("toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
      toPropertyKeyNode.execute(property);
      return true;
   }

   @Specialization
   protected static boolean doSafeInteger(SafeInteger target, Object property, @Cached.Shared("toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
      toPropertyKeyNode.execute(property);
      return true;
   }

   @Specialization
   protected static boolean doBigInt(BigInt target, Object property, @Cached.Shared("toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
      toPropertyKeyNode.execute(property);
      return true;
   }

   @Specialization
   protected boolean doString(
      TruffleString target,
      Object property,
      @Cached.Shared("toArrayIndex") @Cached ToArrayIndexNode toArrayIndexNode,
      @Cached TruffleString.EqualNode equalsNode
   ) {
      Object objIndex = toArrayIndexNode.execute(property);
      boolean result;
      if (objIndex instanceof Long) {
         long index = (Long)objIndex;
         result = index < 0L || Strings.length(target) <= index;
      } else {
         result = !Strings.equals(equalsNode, JSString.LENGTH, (TruffleString)objIndex);
      }

      if (this.strict && !result) {
         throw Errors.createTypeError("cannot delete index");
      } else {
         return result;
      }
   }

   @Specialization(guards = {"isForeignObject(target)", "!interop.hasArrayElements(target)"})
   protected boolean member(Object target, TruffleString name, @Cached.Shared("interop") @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
      if (this.context.getContextOptions().hasForeignHashProperties() && interop.hasHashEntries(target)) {
         try {
            interop.removeHashEntry(target, name);
            return true;
         } catch (UnknownKeyException var7) {
         } catch (UnsupportedMessageException var8) {
            if (this.strict) {
               throw Errors.createTypeErrorInteropException(target, var8, "delete", this);
            }

            return false;
         }
      }

      String javaName = Strings.toJavaString(name);
      if (interop.isMemberExisting(target, javaName)) {
         try {
            interop.removeMember(target, javaName);
            return true;
         } catch (UnsupportedMessageException | UnknownIdentifierException var6) {
            if (this.strict) {
               throw Errors.createTypeErrorCannotDeletePropertyOf(name, target);
            } else {
               return false;
            }
         }
      } else {
         return true;
      }
   }

   @Specialization(guards = {"isForeignObject(target)", "interop.hasArrayElements(target)"})
   protected boolean arrayElementInt(Object target, int index, @Cached.Shared("interop") @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
      return this.arrayElementLong(target, index, interop);
   }

   private boolean arrayElementLong(Object target, long index, InteropLibrary interop) {
      long length;
      try {
         length = interop.getArraySize(target);
      } catch (UnsupportedMessageException var8) {
         return true;
      }

      if (index < 0L || index >= length) {
         return true;
      } else if (this.strict) {
         throw Errors.createTypeErrorNotConfigurableProperty(Strings.fromLong(index));
      } else {
         return false;
      }
   }

   private boolean hashEntry(Object target, Object key, InteropLibrary interop) {
      try {
         interop.removeHashEntry(target, key);
         return true;
      } catch (UnknownKeyException var5) {
         return true;
      } catch (UnsupportedMessageException var6) {
         if (this.strict) {
            throw Errors.createTypeErrorInteropException(target, var6, "delete", this);
         } else {
            return false;
         }
      }
   }

   @Specialization(guards = "isForeignObject(target)", replaces = {"member", "arrayElementInt"})
   protected boolean foreignObject(
      Object target,
      Object key,
      @Cached.Shared("interop") @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop,
      @Cached.Shared("toArrayIndex") @Cached("create()") ToArrayIndexNode toArrayIndexNode,
      @Cached.Shared("toPropertyKey") @Cached("create()") JSToPropertyKeyNode toPropertyKeyNode
   ) {
      Object propertyKey;
      if (interop.hasArrayElements(target)) {
         Object indexOrPropertyKey = toArrayIndexNode.execute(key);
         if (indexOrPropertyKey instanceof Long) {
            return this.arrayElementLong(target, (Long)indexOrPropertyKey, interop);
         }

         propertyKey = indexOrPropertyKey;

         assert JSRuntime.isPropertyKey(indexOrPropertyKey);
      } else {
         propertyKey = toPropertyKeyNode.execute(key);
      }

      if (this.context.getContextOptions().hasForeignHashProperties() && interop.hasHashEntries(target)) {
         return this.hashEntry(target, propertyKey, interop);
      } else if (interop.hasMembers(target)) {
         if (Strings.isTString(propertyKey)) {
            return this.member(target, (TruffleString)propertyKey, interop);
         } else {
            assert propertyKey instanceof Symbol;

            return true;
         }
      } else {
         return true;
      }
   }

   @Specialization(guards = {"!isTruffleObject(target)", "!isString(target)"})
   public boolean doOther(Object target, Object property, @Cached.Shared("toPropertyKey") @Cached JSToPropertyKeyNode toPropertyKeyNode) {
      toPropertyKeyNode.execute(property);
      return true;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.getTarget(), materializedTags), cloneUninitialized(this.propertyNode, materializedTags), this.strict, this.context);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == boolean.class;
   }
}
