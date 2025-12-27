package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultIndicesArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSSlowArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSSlowArray;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class ReadElementNode extends JSTargetableNode implements ReadNode {
   @Node.Child
   private JavaScriptNode targetNode;
   @Node.Child
   private JavaScriptNode indexNode;
   @Node.Child
   private ReadElementNode.ReadElementTypeCacheNode typeCacheNode;
   protected final JSContext context;
   @CompilerDirectives.CompilationFinal
   private byte indexState;
   private static final byte INDEX_INT = 1;
   private static final byte INDEX_OBJECT = 2;

   public static ReadElementNode create(JSContext context) {
      return new ReadElementNode(null, null, context);
   }

   public static ReadElementNode create(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
      return new ReadElementNode(targetNode, indexNode, context);
   }

   protected ReadElementNode(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
      this.targetNode = targetNode;
      this.indexNode = indexNode;
      this.context = context;
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.ReadElementTag.class) && this.materializationNeeded()) {
         JavaScriptNode clonedTarget = this.targetNode != null && !this.targetNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.targetNode, this, materializedTags)
            : this.targetNode;
         JavaScriptNode clonedIndex = this.indexNode != null && !this.indexNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.indexNode, this, materializedTags)
            : this.indexNode;
         if (clonedTarget == this.targetNode && clonedIndex == this.indexNode) {
            return this;
         } else {
            if (clonedTarget == this.targetNode) {
               clonedTarget = cloneUninitialized(this.targetNode, materializedTags);
            }

            if (clonedIndex == this.indexNode) {
               clonedIndex = cloneUninitialized(this.indexNode, materializedTags);
            }

            JavaScriptNode cloned = create(clonedTarget, clonedIndex, this.getContext());
            transferSourceSectionAndTags(this, cloned);
            return cloned;
         }
      } else {
         return this;
      }
   }

   private boolean materializationNeeded() {
      return this.targetNode != null && !this.targetNode.hasSourceSection() || this.indexNode != null && !this.indexNode.hasSourceSection();
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ReadElementTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object evaluateTarget(VirtualFrame frame) {
      return this.targetNode.execute(frame);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object target = this.evaluateTarget(frame);
      return this.executeWithTarget(frame, target, evaluateReceiver(this.targetNode, frame, target));
   }

   @Override
   public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      Object target = this.evaluateTarget(frame);
      return this.executeWithTargetInt(frame, target, evaluateReceiver(this.targetNode, frame, target));
   }

   @Override
   public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      Object target = this.evaluateTarget(frame);
      return this.executeWithTargetDouble(frame, target, evaluateReceiver(this.targetNode, frame, target));
   }

   @Override
   public Object executeWithTarget(VirtualFrame frame, Object target) {
      return this.executeWithTarget(frame, target, target);
   }

   public Object executeWithTarget(VirtualFrame frame, Object target, Object receiver) {
      byte is = this.indexState;
      if (is == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object index = this.getIndexNode().execute(frame);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndex(target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndex(target, index, receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.getIndexNode().executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            return this.executeWithTargetAndIndex(target, var7.getResult(), receiver);
         }

         return this.executeWithTargetAndIndex(target, index);
      } else {
         assert is == 2;

         Object index = this.getIndexNode().execute(frame);
         return this.executeWithTargetAndIndex(target, index, receiver);
      }
   }

   public int executeWithTargetInt(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
      byte is = this.indexState;
      if (is == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object index = this.getIndexNode().execute(frame);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndexInt(target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndexInt(target, index, receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.getIndexNode().executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            return this.executeWithTargetAndIndexInt(target, var7.getResult(), receiver);
         }

         return this.executeWithTargetAndIndexInt(target, index, receiver);
      } else {
         assert is == 2;

         Object index = this.getIndexNode().execute(frame);
         return this.executeWithTargetAndIndexInt(target, index, receiver);
      }
   }

   public double executeWithTargetDouble(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
      byte is = this.indexState;
      if (is == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object index = this.getIndexNode().execute(frame);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndexDouble(target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndexDouble(target, index, receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.getIndexNode().executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            return this.executeWithTargetAndIndexDouble(target, var7.getResult(), receiver);
         }

         return this.executeWithTargetAndIndexDouble(target, index, receiver);
      } else {
         assert is == 2;

         Object index = this.getIndexNode().execute(frame);
         return this.executeWithTargetAndIndexDouble(target, index, receiver);
      }
   }

   public final Object executeWithTargetAndIndex(Object target, Object index) {
      return this.executeTypeDispatch(target, index, target, Undefined.instance);
   }

   public final Object executeWithTargetAndIndex(Object target, int index) {
      return this.executeTypeDispatch(target, index, target, Undefined.instance);
   }

   public final Object executeWithTargetAndIndex(Object target, long index) {
      return this.executeTypeDispatch(target, index, target, Undefined.instance);
   }

   public final Object executeWithTargetAndIndex(Object target, Object index, Object receiver) {
      return this.executeTypeDispatch(target, index, receiver, Undefined.instance);
   }

   public final Object executeWithTargetAndIndex(Object target, int index, Object receiver) {
      return this.executeTypeDispatch(target, index, receiver, Undefined.instance);
   }

   public final int executeWithTargetAndIndexInt(Object target, Object index, Object receiver) throws UnexpectedResultException {
      return this.executeTypeDispatchInt(target, index, receiver, Undefined.instance);
   }

   public final int executeWithTargetAndIndexInt(Object target, int index, Object receiver) throws UnexpectedResultException {
      return this.executeTypeDispatchInt(target, index, receiver, Undefined.instance);
   }

   public final double executeWithTargetAndIndexDouble(Object target, Object index, Object receiver) throws UnexpectedResultException {
      return this.executeTypeDispatchDouble(target, index, receiver, Undefined.instance);
   }

   public final double executeWithTargetAndIndexDouble(Object target, int index, Object receiver) throws UnexpectedResultException {
      return this.executeTypeDispatchDouble(target, index, receiver, Undefined.instance);
   }

   public final Object executeWithTargetAndIndexOrDefault(Object target, Object index, Object defaultValue) {
      return this.executeTypeDispatch(target, index, target, defaultValue);
   }

   @ExplodeLoop
   protected final Object executeTypeDispatch(Object target, Object index, Object receiver, Object defaultValue) {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final Object executeTypeDispatch(Object target, int index, Object receiver, Object defaultValue) {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final Object executeTypeDispatch(Object target, long index, Object receiver, Object defaultValue) {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final int executeTypeDispatchInt(Object target, Object index, Object receiver, Object defaultValue) throws UnexpectedResultException {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final int executeTypeDispatchInt(Object target, int index, Object receiver, Object defaultValue) throws UnexpectedResultException {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final double executeTypeDispatchDouble(Object target, Object index, Object receiver, Object defaultValue) throws UnexpectedResultException {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
   }

   @ExplodeLoop
   protected final double executeTypeDispatchDouble(Object target, int index, Object receiver, Object defaultValue) throws UnexpectedResultException {
      for (ReadElementNode.ReadElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            return c.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      ReadElementNode.ReadElementTypeCacheNode specialization = this.specialize(target);
      return specialization.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
   }

   private ReadElementNode.ReadElementTypeCacheNode specialize(Object target) {
      CompilerAsserts.neverPartOfCompilation();
      Lock lock = this.getLock();
      lock.lock();

      try {
         ReadElementNode.ReadElementTypeCacheNode currentHead = this.typeCacheNode;

         for (ReadElementNode.ReadElementTypeCacheNode c = currentHead; c != null; c = c.typeCacheNext) {
            if (c.guard(target)) {
               return c;
            }
         }

         ReadElementNode.ReadElementTypeCacheNode newCacheNode = this.makeTypeCacheNode(target, currentHead);
         this.insert(newCacheNode);
         this.typeCacheNode = newCacheNode;
         if (currentHead != null && currentHead.typeCacheNext != null && currentHead.typeCacheNext.typeCacheNext != null) {
            this.reportPolymorphicSpecialize();
         }

         if (!newCacheNode.guard(target)) {
            throw Errors.shouldNotReachHere();
         } else {
            return newCacheNode;
         }
      } finally {
         lock.unlock();
      }
   }

   private ReadElementNode.ReadElementTypeCacheNode makeTypeCacheNode(Object target, ReadElementNode.ReadElementTypeCacheNode next) {
      if (JSDynamicObject.isJSDynamicObject(target)) {
         return new ReadElementNode.JSObjectReadElementTypeCacheNode(next);
      } else if (Strings.isTString(target)) {
         return new ReadElementNode.StringReadElementTypeCacheNode(this.context, next);
      } else if (target instanceof Boolean) {
         return new ReadElementNode.BooleanReadElementTypeCacheNode(next);
      } else if (target instanceof Number) {
         return new ReadElementNode.NumberReadElementTypeCacheNode(target.getClass(), next);
      } else if (target instanceof Symbol) {
         return new ReadElementNode.SymbolReadElementTypeCacheNode(next);
      } else if (target instanceof BigInt) {
         return new ReadElementNode.BigIntReadElementTypeCacheNode(next);
      } else if (target instanceof TruffleObject) {
         assert JSRuntime.isForeignObject(target);

         return new ReadElementNode.ForeignObjectReadElementTypeCacheNode(target.getClass(), next);
      } else {
         assert JSRuntime.isJavaPrimitive(target) : target;

         return new ReadElementNode.JavaObjectReadElementTypeCacheNode(target.getClass(), next);
      }
   }

   protected static ReadElementNode.ArrayReadElementCacheNode makeArrayCacheNode(
      JSDynamicObject target, ScriptArray array, ReadElementNode.ArrayReadElementCacheNode next
   ) {
      if (array instanceof ConstantEmptyArray) {
         return new ReadElementNode.EmptyArrayReadElementCacheNode(array, next);
      } else if (array instanceof ConstantObjectArray && array.isHolesType()) {
         return new ReadElementNode.ConstantObjectArrayReadElementCacheNode(array, next);
      } else if (array instanceof LazyRegexResultArray) {
         return new ReadElementNode.LazyRegexResultArrayReadElementCacheNode(array, next);
      } else if (array instanceof LazyRegexResultIndicesArray) {
         return new ReadElementNode.LazyRegexResultIndicesArrayReadElementCacheNode(array, next);
      } else if (array instanceof LazyArray) {
         return new ReadElementNode.LazyArrayReadElementCacheNode(array, next);
      } else if (array instanceof AbstractConstantArray) {
         return new ReadElementNode.ConstantArrayReadElementCacheNode(array, next);
      } else if (array instanceof HolesIntArray) {
         return new ReadElementNode.HolesIntArrayReadElementCacheNode(array, next);
      } else if (array instanceof HolesDoubleArray) {
         return new ReadElementNode.HolesDoubleArrayReadElementCacheNode(array, next);
      } else if (array instanceof HolesJSObjectArray) {
         return new ReadElementNode.HolesJSObjectArrayReadElementCacheNode(array, next);
      } else if (array instanceof HolesObjectArray) {
         return new ReadElementNode.HolesObjectArrayReadElementCacheNode(array, next);
      } else if (array instanceof AbstractWritableArray) {
         return new ReadElementNode.WritableArrayReadElementCacheNode(array, next);
      } else if (array instanceof TypedArray) {
         if (array instanceof TypedArray.AbstractUint32Array) {
            return new ReadElementNode.Uint32ArrayReadElementCacheNode((TypedArray)array, next);
         } else if (array instanceof TypedArray.TypedIntArray) {
            return new ReadElementNode.TypedIntArrayReadElementCacheNode((TypedArray)array, next);
         } else if (array instanceof TypedArray.TypedFloatArray) {
            return new ReadElementNode.TypedFloatArrayReadElementCacheNode((TypedArray)array, next);
         } else if (array instanceof TypedArray.TypedBigIntArray) {
            return new ReadElementNode.TypedBigIntArrayReadElementCacheNode((TypedArray)array, next);
         } else {
            throw Errors.shouldNotReachHere();
         }
      } else {
         return new ReadElementNode.ExactArrayReadElementCacheNode(array, next);
      }
   }

   @Override
   public final JavaScriptNode getTarget() {
      return this.targetNode;
   }

   public final JavaScriptNode getElement() {
      return this.getIndexNode();
   }

   public final JSContext getContext() {
      return this.context;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.targetNode, materializedTags), cloneUninitialized(this.getIndexNode(), materializedTags), this.getContext());
   }

   @Override
   public String expressionToString() {
      return this.targetNode != null && this.indexNode != null
         ? Objects.toString(this.targetNode.expressionToString(), "(intermediate value)")
            + "["
            + Objects.toString(this.indexNode.expressionToString(), "(intermediate value)")
            + "]"
         : null;
   }

   public JavaScriptNode getIndexNode() {
      return this.indexNode;
   }

   private abstract static class AbstractTypedArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      @Node.Child
      protected InteropLibrary interop;

      AbstractTypedArrayReadElementCacheNode(TypedArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
         this.interop = arrayType.isInterop() ? InteropLibrary.getFactory().createDispatched(5) : InteropLibrary.getUncached();
      }
   }

   private abstract static class ArrayClassGuardCachedArrayReadElementCacheNode extends ReadElementNode.ArrayReadElementCacheNode {
      private final ScriptArray arrayType;
      protected final ConditionProfile inBounds = ConditionProfile.createBinaryProfile();
      private final ConditionProfile needGetProperty = ConditionProfile.createBinaryProfile();
      private final JSClassProfile outOfBoundsClassProfile = JSClassProfile.create();

      ArrayClassGuardCachedArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(next);
         this.arrayType = arrayType;
      }

      @Override
      protected final boolean guard(Object target, ScriptArray array) {
         return this.arrayType.isInstance(array);
      }

      protected final ScriptArray cast(ScriptArray array) {
         return this.arrayType.cast(array);
      }

      protected final ScriptArray getArrayType() {
         return this.arrayType;
      }

      protected Object readOutOfBounds(JSDynamicObject target, long index, Object receiver, Object defaultValue, JSContext context) {
         return this.needGetProperty.profile(needsSlowGet(target, context))
            ? JSObject.getOrDefault(target, index, receiver, defaultValue, this.outOfBoundsClassProfile, this)
            : defaultValue;
      }

      private static boolean needsSlowGet(JSDynamicObject target, JSContext context) {
         return !context.getArrayPrototypeNoElementsAssumption().isValid()
            || !context.getFastArrayAssumption().isValid() && JSSlowArray.isJSSlowArray(target)
            || !context.getFastArgumentsObjectAssumption().isValid() && JSSlowArgumentsArray.isJSSlowArgumentsObject(target);
      }
   }

   abstract static class ArrayReadElementCacheNode extends JavaScriptBaseNode {
      @Node.Child
      ReadElementNode.ArrayReadElementCacheNode arrayCacheNext;

      protected ArrayReadElementCacheNode(ReadElementNode.ArrayReadElementCacheNode next) {
         this.arrayCacheNext = next;
      }

      protected abstract Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context);

      protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         return JSTypesGen.expectInteger(this.executeArrayGet(target, array, index, receiver, defaultValue, context));
      }

      protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         return JSTypesGen.expectDouble(this.executeArrayGet(target, array, index, receiver, defaultValue, context));
      }

      protected abstract boolean guard(Object target, ScriptArray array);
   }

   private static class BigIntReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      BigIntReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         BigInt bigInt = (BigInt)target;
         return JSObject.getOrDefault(
            JSBigInt.create(root.context, this.getRealm(), bigInt), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, root
         );
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         BigInt bigInt = (BigInt)target;
         return JSObject.getOrDefault(JSBigInt.create(root.context, this.getRealm(), bigInt), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof BigInt;
      }
   }

   private static class BooleanReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      BooleanReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         Boolean bool = (Boolean)target;
         return JSObject.getOrDefault(
            JSBoolean.create(root.context, this.getRealm(), bool), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, root
         );
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         Boolean bool = (Boolean)target;
         return JSObject.getOrDefault(JSBoolean.create(root.context, this.getRealm(), bool), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof Boolean;
      }
   }

   private static class ConstantArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      ConstantArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         AbstractConstantArray constantArray = (AbstractConstantArray)this.cast(array);
         return this.inBounds.profile(constantArray.hasElement(target, index))
            ? constantArray.getElementInBounds(target, (int)index)
            : this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class ConstantObjectArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final ConditionProfile holeArrayProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

      ConstantObjectArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         ConstantObjectArray constantObjectArray = (ConstantObjectArray)this.cast(array);
         if (this.inBounds.profile(constantObjectArray.isInBoundsFast(target, index))) {
            Object value = ConstantObjectArray.getElementInBoundsDirect(target, (int)index);
            if (this.holeArrayProfile.profile(!constantObjectArray.hasHoles(target))) {
               return value;
            }

            if (this.holeProfile.profile(!HolesObjectArray.isHoleValue(value))) {
               return value;
            }
         }

         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class EmptyArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      EmptyArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);

         assert arrayType.getClass() == ConstantEmptyArray.class;
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class ExactArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final JSClassProfile classProfile = JSClassProfile.create();

      ExactArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         return JSObject.getOrDefault(target, index, receiver, defaultValue, this.classProfile, this);
      }
   }

   static class ForeignObjectReadElementTypeCacheNode extends ReadElementNode.ReadElementTypeCacheNode {
      private final Class<?> targetClass;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode;
      @Node.Child
      private ImportValueNode importValueNode;
      @Node.Child
      private InteropLibrary getterInterop;
      @Node.Child
      private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
      @Node.Child
      private ReadElementNode readFromPrototypeNode;
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @CompilerDirectives.CompilationFinal
      private boolean optimistic = true;

      ForeignObjectReadElementTypeCacheNode(Class<?> targetClass, ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);

         assert !JSDynamicObject.class.isAssignableFrom(targetClass) : targetClass;

         this.targetClass = targetClass;
         this.importValueNode = ImportValueNode.create();
         this.interop = InteropLibrary.getFactory().createDispatched(5);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         Object truffleObject = CompilerDirectives.castExact(target, this.targetClass);
         if (this.interop.isNull(truffleObject)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorCannotGetProperty(root.getContext(), JSRuntime.safeToString(index), target, false, this);
         } else {
            Object foreignResult = this.getImpl(truffleObject, index, root);
            return this.importValueNode.executeWithTarget(foreignResult);
         }
      }

      private Object getImpl(Object truffleObject, Object key, ReadElementNode root) {
         boolean hasArrayElements = this.interop.hasArrayElements(truffleObject);
         Object propertyKey;
         if (hasArrayElements) {
            try {
               Object indexOrPropertyKey = this.toArrayIndex(key);
               if (indexOrPropertyKey instanceof Long) {
                  return this.interop.readArrayElement(truffleObject, (Long)indexOrPropertyKey);
               }

               propertyKey = indexOrPropertyKey;

               assert JSRuntime.isPropertyKey(indexOrPropertyKey);
            } catch (UnsupportedMessageException | InvalidArrayIndexException var11) {
               return Undefined.instance;
            }
         } else {
            propertyKey = this.toPropertyKey(key);
         }

         if (root.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(truffleObject)) {
            try {
               return this.interop.readHashValue(truffleObject, propertyKey);
            } catch (UnknownKeyException var12) {
            } catch (UnsupportedMessageException var13) {
               return Undefined.instance;
            }
         }

         if (propertyKey instanceof Symbol) {
            return this.maybeReadFromPrototype(truffleObject, propertyKey, root.context);
         } else {
            TruffleString exportedKeyStr = (TruffleString)propertyKey;
            if (hasArrayElements && Strings.equals(JSAbstractArray.LENGTH, exportedKeyStr)) {
               return this.getSize(truffleObject);
            } else {
               if (root.context.isOptionNashornCompatibilityMode()) {
                  Object result = this.tryGetters(truffleObject, exportedKeyStr, root.context);
                  if (result != null) {
                     return result;
                  }
               }

               String stringKey = Strings.toJavaString(exportedKeyStr);
               if (this.optimistic) {
                  try {
                     return this.interop.readMember(truffleObject, stringKey);
                  } catch (UnsupportedMessageException | UnknownIdentifierException var9) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     this.optimistic = false;
                     return this.maybeReadFromPrototype(truffleObject, exportedKeyStr, root.context);
                  }
               } else if (this.interop.isMemberReadable(truffleObject, stringKey)) {
                  try {
                     return this.interop.readMember(truffleObject, stringKey);
                  } catch (UnsupportedMessageException | UnknownIdentifierException var10) {
                     return Undefined.instance;
                  }
               } else {
                  return this.maybeReadFromPrototype(truffleObject, exportedKeyStr, root.context);
               }
            }
         }
      }

      private Object tryGetters(Object thisObj, TruffleString key, JSContext context) {
         assert context.isOptionNashornCompatibilityMode();

         TruffleLanguage.Env env = this.getRealm().getEnv();
         if (env.isHostObject(thisObj)) {
            Object result = this.tryInvokeGetter(thisObj, Strings.GET, key);
            if (result != null) {
               return result;
            }

            result = this.tryInvokeGetter(thisObj, Strings.IS, key);
            if (result != null) {
               return result;
            }
         }

         return null;
      }

      private Object tryInvokeGetter(Object thisObj, TruffleString prefix, TruffleString key) {
         TruffleString getterKey = PropertyCacheNode.getAccessorKey(prefix, key);
         if (getterKey == null) {
            return null;
         } else {
            if (this.getterInterop == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.getterInterop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }

            if (!this.getterInterop.isMemberInvocable(thisObj, Strings.toJavaString(getterKey))) {
               return null;
            } else {
               try {
                  return this.getterInterop.invokeMember(thisObj, Strings.toJavaString(getterKey), JSArguments.EMPTY_ARGUMENTS_ARRAY);
               } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException | UnknownIdentifierException var6) {
                  return null;
               }
            }
         }
      }

      private Object getSize(Object truffleObject) {
         try {
            return JSRuntime.longToIntOrDouble(this.interop.getArraySize(truffleObject));
         } catch (UnsupportedMessageException var3) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorInteropException(truffleObject, var3, "getArraySize", this);
         }
      }

      private Object maybeReadFromPrototype(Object truffleObject, Object key, JSContext context) {
         assert JSRuntime.isPropertyKey(key);

         if (!context.getContextOptions().hasForeignObjectPrototype()
            && !(key instanceof Symbol)
            && !JSInteropUtil.isBoxedPrimitive(truffleObject, this.interop)) {
            return Undefined.instance;
         } else {
            if (this.readFromPrototypeNode == null || this.foreignObjectPrototypeNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.readFromPrototypeNode = this.insert(ReadElementNode.create(context));
               this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
            }

            JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(truffleObject);
            return this.readFromPrototypeNode.executeWithTargetAndIndex(prototype, key);
         }
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
         return this.executeWithTargetAndIndexUnchecked(target, Integer.valueOf(index), receiver, defaultValue, root);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         return this.executeWithTargetAndIndexUnchecked(target, Long.valueOf(index), receiver, defaultValue, root);
      }

      @Override
      public boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.targetClass);
      }

      private Object toArrayIndex(Object maybeIndex) {
         if (this.toArrayIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
         }

         return this.toArrayIndexNode.execute(maybeIndex);
      }

      private Object toPropertyKey(Object index) {
         if (this.toPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
         }

         return this.toPropertyKeyNode.execute(index);
      }
   }

   private static class HolesDoubleArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

      HolesDoubleArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         HolesDoubleArray holesDoubleArray = (HolesDoubleArray)this.cast(array);
         if (this.inBounds.profile(holesDoubleArray.isInBoundsFast(target, index))) {
            double value = holesDoubleArray.getInBoundsFastDouble(target, (int)index);
            if (this.holeProfile.profile(!HolesDoubleArray.isHoleValue(value))) {
               return value;
            }
         }

         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class HolesIntArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

      HolesIntArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         HolesIntArray holesIntArray = (HolesIntArray)this.cast(array);
         if (this.inBounds.profile(holesIntArray.isInBoundsFast(target, index))) {
            int value = holesIntArray.getInBoundsFastInt(target, (int)index);
            if (this.holeProfile.profile(!HolesIntArray.isHoleValue(value))) {
               return value;
            }
         }

         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class HolesJSObjectArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

      HolesJSObjectArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         HolesJSObjectArray holesArray = (HolesJSObjectArray)this.cast(array);
         if (this.inBounds.profile(holesArray.isInBoundsFast(target, index))) {
            JSDynamicObject value = holesArray.getInBoundsFastJSObject(target, (int)index);
            if (this.holeProfile.profile(!HolesJSObjectArray.isHoleValue(value))) {
               return value;
            }
         }

         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class HolesObjectArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

      HolesObjectArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         HolesObjectArray holesArray = (HolesObjectArray)this.cast(array);
         if (this.inBounds.profile(holesArray.isInBoundsFast(target, index))) {
            Object value = holesArray.getInBoundsFastObject(target, (int)index);
            if (this.holeProfile.profile(!HolesObjectArray.isHoleValue(value))) {
               return value;
            }
         }

         return this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class JSObjectReadElementNonArrayTypeCacheNode extends JavaScriptBaseNode {
      @Node.Child
      private CachedGetPropertyNode getPropertyCachedNode;

      JSObjectReadElementNonArrayTypeCacheNode() {
      }

      public Object execute(JSDynamicObject targetObject, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         if (this.getPropertyCachedNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getPropertyCachedNode = this.insert(CachedGetPropertyNode.create(root.context));
         }

         return this.getPropertyCachedNode.execute(targetObject, index, receiver, defaultValue);
      }
   }

   private static class JSObjectReadElementTypeCacheNode extends ReadElementNode.ReadElementArrayDispatchNode {
      @Node.Child
      private IsArrayNode isArrayNode;
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode;
      @Node.Child
      private ReadElementNode.JSObjectReadElementNonArrayTypeCacheNode nonArrayCaseNode;
      @Node.Child
      private IsJSDynamicObjectNode isObjectNode;
      private final ConditionProfile arrayProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile arrayIndexProfile = ConditionProfile.createBinaryProfile();
      private final JSClassProfile jsclassProfile = JSClassProfile.create();

      JSObjectReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
         this.isArrayNode = IsArrayNode.createIsAnyArray();
         this.isObjectNode = IsJSDynamicObjectNode.create();
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            Object objIndex = this.toArrayIndex(index);
            if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
               long longIndex = (Long)objIndex;
               return this.executeArrayGet(targetObject, array, longIndex, receiver, defaultValue, root.context);
            } else {
               return this.getProperty(targetObject, objIndex, receiver, defaultValue);
            }
         } else {
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
         }
      }

      private Object toArrayIndex(Object index) {
         if (this.toArrayIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
         }

         return this.toArrayIndexNode.execute(index);
      }

      private Object readNonArrayObjectIndex(JSDynamicObject targetObject, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         return this.getNonArrayNode().execute(targetObject, index, receiver, defaultValue, root);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            return this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))
               ? this.executeArrayGet(targetObject, array, index, receiver, defaultValue, root.context)
               : this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue);
         } else {
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
         }
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            return this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))
               ? this.executeArrayGet(targetObject, array, index, receiver, defaultValue, root.context)
               : this.getProperty(targetObject, Strings.fromLong(index), receiver, defaultValue);
         } else {
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
         }
      }

      @Override
      protected int executeWithTargetAndIndexUncheckedInt(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            Object objIndex = this.toArrayIndex(index);
            if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
               long longIndex = (Long)objIndex;
               return this.executeArrayGetInt(targetObject, array, longIndex, receiver, defaultValue, root.context);
            } else {
               return JSTypesGen.expectInteger(this.getProperty(targetObject, objIndex, receiver, defaultValue));
            }
         } else {
            return JSTypesGen.expectInteger(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
         }
      }

      @Override
      protected int executeWithTargetAndIndexUncheckedInt(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            return this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))
               ? this.executeArrayGetInt(targetObject, array, index, receiver, defaultValue, root.context)
               : JSTypesGen.expectInteger(this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue));
         } else {
            return JSTypesGen.expectInteger(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
         }
      }

      @Override
      protected double executeWithTargetAndIndexUncheckedDouble(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            Object objIndex = this.toArrayIndex(index);
            if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
               long longIndex = (Long)objIndex;
               return this.executeArrayGetDouble(targetObject, array, longIndex, receiver, defaultValue, root.context);
            } else {
               return JSTypesGen.expectDouble(this.getProperty(targetObject, objIndex, receiver, defaultValue));
            }
         } else {
            return JSTypesGen.expectDouble(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
         }
      }

      @Override
      protected double executeWithTargetAndIndexUncheckedDouble(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         boolean arrayCondition = this.isArrayNode.execute(targetObject);
         if (this.arrayProfile.profile(arrayCondition)) {
            ScriptArray array = JSObject.getArray(targetObject);
            return this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))
               ? this.executeArrayGetDouble(targetObject, array, index, receiver, defaultValue, root.context)
               : JSTypesGen.expectDouble(this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue));
         } else {
            return JSTypesGen.expectDouble(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
         }
      }

      @Override
      public boolean guard(Object target) {
         return this.isObjectNode.executeBoolean(target);
      }

      private Object getProperty(JSDynamicObject targetObject, Object objIndex, Object receiver, Object defaultValue) {
         return JSObject.getOrDefault(targetObject, objIndex, receiver, defaultValue, this.jsclassProfile, this);
      }

      private ReadElementNode.JSObjectReadElementNonArrayTypeCacheNode getNonArrayNode() {
         if (this.nonArrayCaseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.nonArrayCaseNode = this.insert(new ReadElementNode.JSObjectReadElementNonArrayTypeCacheNode());
         }

         return this.nonArrayCaseNode;
      }
   }

   private static class JavaObjectReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      protected final Class<?> targetClass;

      JavaObjectReadElementTypeCacheNode(Class<?> targetClass, ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
         this.targetClass = targetClass;
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         this.toPropertyKey(index);
         return Undefined.instance;
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         return Undefined.instance;
      }

      @Override
      public final boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.targetClass);
      }
   }

   private static class LazyArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      @Node.Child
      private ListGetNode listGetNode = ListGetNode.create();

      LazyArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         LazyArray lazyRegexResultArray = (LazyArray)array;
         int intIndex = (int)index;
         return this.inBounds.profile(lazyRegexResultArray.hasElement(target, intIndex))
            ? lazyRegexResultArray.getElementInBounds(target, intIndex, this.listGetNode)
            : this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class LazyRegexResultArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      @Node.Child
      private TRegexUtil.TRegexMaterializeResultNode materializeResultNode;
      @Node.Child
      private DynamicObjectLibrary lazyRegexResultNode;
      @Node.Child
      private DynamicObjectLibrary lazyRegexResultOriginalInputNode;

      LazyRegexResultArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      private TRegexUtil.TRegexMaterializeResultNode getMaterializeResultNode() {
         if (this.materializeResultNode == null || this.lazyRegexResultNode == null || this.lazyRegexResultOriginalInputNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.materializeResultNode = this.insert(TRegexUtil.TRegexMaterializeResultNode.create());
            this.lazyRegexResultNode = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
            this.lazyRegexResultOriginalInputNode = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
         }

         return this.materializeResultNode;
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         LazyRegexResultArray lazyRegexResultArray = (LazyRegexResultArray)array;
         return this.inBounds.profile(lazyRegexResultArray.hasElement(target, (int)index))
            ? LazyRegexResultArray.materializeGroup(
               context, this.getMaterializeResultNode(), target, (int)index, this.lazyRegexResultNode, this.lazyRegexResultOriginalInputNode
            )
            : this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class LazyRegexResultIndicesArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      @Node.Child
      TRegexUtil.TRegexResultAccessor resultAccessor;

      LazyRegexResultIndicesArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      private TRegexUtil.TRegexResultAccessor getResultAccessor() {
         if (this.resultAccessor == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.resultAccessor = this.insert(TRegexUtil.TRegexResultAccessor.create());
         }

         return this.resultAccessor;
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         LazyRegexResultIndicesArray lazyRegexResultIndicesArray = (LazyRegexResultIndicesArray)array;
         return this.inBounds.profile(lazyRegexResultIndicesArray.hasElement(target, (int)index))
            ? LazyRegexResultIndicesArray.materializeGroup(context, this.getResultAccessor(), target, (int)index)
            : this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }
   }

   private static class NumberReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      private final Class<?> numberClass;

      NumberReadElementTypeCacheNode(Class<?> stringClass, ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
         this.numberClass = stringClass;
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         Number charSequence = CompilerDirectives.castExact(target, (Class<Number>)this.numberClass);
         return JSObject.getOrDefault(
            JSNumber.create(root.context, this.getRealm(), charSequence), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, root
         );
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         Number charSequence = CompilerDirectives.castExact(target, (Class<Number>)this.numberClass);
         return JSObject.getOrDefault(JSNumber.create(root.context, this.getRealm(), charSequence), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.numberClass);
      }
   }

   protected abstract static class ReadElementArrayDispatchNode extends ReadElementNode.ReadElementTypeCacheNode {
      @Node.Child
      private ReadElementNode.ArrayReadElementCacheNode arrayReadElementNode;

      protected ReadElementArrayDispatchNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
      }

      @ExplodeLoop
      protected final Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) {
         for (ReadElementNode.ArrayReadElementCacheNode c = this.arrayReadElementNode; c != null; c = c.arrayCacheNext) {
            boolean guard = c.guard(target, array);
            if (guard) {
               return c.executeArrayGet(target, array, index, receiver, defaultValue, root);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         ReadElementNode.ArrayReadElementCacheNode specialization = this.specialize(target, array);
         return specialization.executeArrayGet(target, array, index, receiver, defaultValue, root);
      }

      @ExplodeLoop
      protected final int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) throws UnexpectedResultException {
         for (ReadElementNode.ArrayReadElementCacheNode c = this.arrayReadElementNode; c != null; c = c.arrayCacheNext) {
            boolean guard = c.guard(target, array);
            if (guard) {
               return c.executeArrayGetInt(target, array, index, receiver, defaultValue, root);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         ReadElementNode.ArrayReadElementCacheNode specialization = this.specialize(target, array);
         return specialization.executeArrayGetInt(target, array, index, receiver, defaultValue, root);
      }

      @ExplodeLoop
      protected final double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) throws UnexpectedResultException {
         for (ReadElementNode.ArrayReadElementCacheNode c = this.arrayReadElementNode; c != null; c = c.arrayCacheNext) {
            boolean guard = c.guard(target, array);
            if (guard) {
               return c.executeArrayGetDouble(target, array, index, receiver, defaultValue, root);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         ReadElementNode.ArrayReadElementCacheNode specialization = this.specialize(target, array);
         return specialization.executeArrayGetDouble(target, array, index, receiver, defaultValue, root);
      }

      private ReadElementNode.ArrayReadElementCacheNode specialize(JSDynamicObject target, ScriptArray array) {
         CompilerAsserts.neverPartOfCompilation();
         Lock lock = this.getLock();
         lock.lock();

         try {
            ReadElementNode.ArrayReadElementCacheNode currentHead = this.arrayReadElementNode;

            for (ReadElementNode.ArrayReadElementCacheNode c = currentHead; c != null; c = c.arrayCacheNext) {
               if (c.guard(target, array)) {
                  return c;
               }
            }

            currentHead = purgeStaleCacheEntries(currentHead, target);
            ReadElementNode.ArrayReadElementCacheNode newCacheNode = ReadElementNode.makeArrayCacheNode(target, array, currentHead);
            this.insert(newCacheNode);
            this.arrayReadElementNode = newCacheNode;
            if (!newCacheNode.guard(target, array)) {
               throw Errors.shouldNotReachHere();
            } else {
               return newCacheNode;
            }
         } finally {
            lock.unlock();
         }
      }

      private static ReadElementNode.ArrayReadElementCacheNode purgeStaleCacheEntries(ReadElementNode.ArrayReadElementCacheNode head, JSDynamicObject target) {
         if (JSConfig.TrackArrayAllocationSites && head != null && JSArray.isJSArray(target)) {
            ArrayAllocationSite allocationSite = JSAbstractArray.arrayGetAllocationSite(target);
            if (allocationSite != null && allocationSite.getInitialArrayType() != null) {
               ReadElementNode.ArrayReadElementCacheNode c = head;

               for (ReadElementNode.ArrayReadElementCacheNode prev = null; c != null; c = c.arrayCacheNext) {
                  if (c instanceof ReadElementNode.ConstantArrayReadElementCacheNode) {
                     ReadElementNode.ConstantArrayReadElementCacheNode existingNode = (ReadElementNode.ConstantArrayReadElementCacheNode)c;
                     ScriptArray initialArrayType = allocationSite.getInitialArrayType();
                     if (!(initialArrayType instanceof ConstantEmptyArray) && existingNode.getArrayType() instanceof ConstantEmptyArray) {
                        if (JSConfig.TraceArrayTransitions) {
                           System.out
                              .println("purging " + existingNode + ": " + existingNode.getArrayType() + " => " + JSAbstractArray.arrayGetArrayType(target));
                        }

                        if (prev == null) {
                           return existingNode.arrayCacheNext;
                        }

                        prev.arrayCacheNext = existingNode.arrayCacheNext;
                        return head;
                     }
                  }

                  prev = c;
               }
            }
         }

         return head;
      }

      protected static ReadElementNode.ReadElementArrayDispatchNode create() {
         return new ReadElementNode.ReadElementArrayDispatchNode(null) {
            @Override
            public boolean guard(Object target) {
               return true;
            }

            @Override
            protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
               throw Errors.shouldNotReachHere();
            }

            @Override
            protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
               throw Errors.shouldNotReachHere();
            }

            @Override
            protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
               throw Errors.shouldNotReachHere();
            }
         };
      }
   }

   abstract static class ReadElementTypeCacheNode extends JavaScriptBaseNode {
      @Node.Child
      private ReadElementNode.ReadElementTypeCacheNode typeCacheNext;

      protected ReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         this.typeCacheNext = next;
      }

      public abstract boolean guard(Object target);

      protected abstract Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root);

      protected abstract Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root);

      protected abstract Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root);

      protected int executeWithTargetAndIndexUncheckedInt(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         return JSTypesGen.expectInteger(this.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, root));
      }

      protected int executeWithTargetAndIndexUncheckedInt(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         return this.executeWithTargetAndIndexUncheckedInt(target, Integer.valueOf(index), receiver, defaultValue, root);
      }

      protected double executeWithTargetAndIndexUncheckedDouble(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         return JSTypesGen.expectDouble(this.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, root));
      }

      protected double executeWithTargetAndIndexUncheckedDouble(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
         return this.executeWithTargetAndIndexUncheckedDouble(target, Integer.valueOf(index), receiver, defaultValue, root);
      }
   }

   private static class StringReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      private final JSContext context;
      private final ConditionProfile arrayIndexProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile stringIndexInBounds = ConditionProfile.createBinaryProfile();
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringByteIndexNode;

      StringReadElementTypeCacheNode(JSContext context, ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
         this.context = context;
         this.toArrayIndexNode = ToArrayIndexNode.createNoToPropertyKey();
         this.substringByteIndexNode = TruffleString.SubstringByteIndexNode.create();
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         TruffleString string = (TruffleString)target;
         Object convertedIndex = this.toArrayIndexNode.execute(index);
         if (this.arrayIndexProfile.profile(convertedIndex instanceof Long)) {
            int intIndex = ((Long)convertedIndex).intValue();
            if (this.stringIndexInBounds.profile(intIndex >= 0 && intIndex < Strings.length(string))) {
               return Strings.substring(this.context, this.substringByteIndexNode, string, intIndex, 1);
            }
         }

         return JSObject.getOrDefault(
            JSString.create(root.context, this.getRealm(), string), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, root
         );
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
         TruffleString string = (TruffleString)target;
         return this.stringIndexInBounds.profile(index >= 0 && index < Strings.length(string))
            ? Strings.substring(this.context, this.substringByteIndexNode, string, index, 1)
            : JSObject.getOrDefault(JSString.create(root.context, this.getRealm(), string), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         TruffleString string = (TruffleString)target;
         return this.stringIndexInBounds.profile(index >= 0L && index < Strings.length(string))
            ? Strings.substring(this.context, this.substringByteIndexNode, string, (int)index, 1)
            : JSObject.getOrDefault(JSString.create(root.context, this.getRealm(), string), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof TruffleString;
      }
   }

   private static class SymbolReadElementTypeCacheNode extends ReadElementNode.ToPropertyKeyCachedReadElementTypeCacheNode {
      SymbolReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
         Symbol symbol = (Symbol)target;
         return JSObject.getOrDefault(
            JSSymbol.create(root.context, this.getRealm(), symbol), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, root
         );
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
         Symbol symbol = (Symbol)target;
         return JSObject.getOrDefault(JSSymbol.create(root.context, this.getRealm(), symbol), index, receiver, defaultValue, this.jsclassProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof Symbol;
      }
   }

   private abstract static class ToPropertyKeyCachedReadElementTypeCacheNode extends ReadElementNode.ReadElementTypeCacheNode {
      @Node.Child
      private JSToPropertyKeyNode indexToPropertyKeyNode;
      protected final JSClassProfile jsclassProfile = JSClassProfile.create();

      ToPropertyKeyCachedReadElementTypeCacheNode(ReadElementNode.ReadElementTypeCacheNode next) {
         super(next);
      }

      protected final Object toPropertyKey(Object index) {
         if (this.indexToPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexToPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
         }

         return this.indexToPropertyKeyNode.execute(index);
      }

      @Override
      protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
         return this.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, root);
      }
   }

   private static class TypedBigIntArrayReadElementCacheNode extends ReadElementNode.AbstractTypedArrayReadElementCacheNode {
      TypedBigIntArrayReadElementCacheNode(TypedArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         TypedArray.TypedBigIntArray typedArray = (TypedArray.TypedBigIntArray)this.cast(array);
         return !JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))
            ? typedArray.getBigInt(target, (int)index, this.interop)
            : defaultValue;
      }
   }

   private static class TypedFloatArrayReadElementCacheNode extends ReadElementNode.AbstractTypedArrayReadElementCacheNode {
      TypedFloatArrayReadElementCacheNode(TypedArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         TypedArray.TypedFloatArray typedArray = (TypedArray.TypedFloatArray)this.cast(array);
         return !JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))
            ? typedArray.getDouble(target, (int)index, this.interop)
            : defaultValue;
      }

      @Override
      protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         TypedArray.TypedFloatArray typedArray = (TypedArray.TypedFloatArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            return typedArray.getDouble(target, (int)index, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
         }
      }
   }

   private static class TypedIntArrayReadElementCacheNode extends ReadElementNode.AbstractTypedArrayReadElementCacheNode {
      TypedIntArrayReadElementCacheNode(TypedArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         return !JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))
            ? typedArray.getInt(target, (int)index, this.interop)
            : defaultValue;
      }

      @Override
      protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            return typedArray.getInt(target, (int)index, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
         }
      }

      @Override
      protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            return typedArray.getInt(target, (int)index, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
         }
      }
   }

   private static class Uint32ArrayReadElementCacheNode extends ReadElementNode.AbstractTypedArrayReadElementCacheNode {
      private final ConditionProfile isSignedProfile = ConditionProfile.createBinaryProfile();

      Uint32ArrayReadElementCacheNode(TypedArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            int intValue = typedArray.getInt(target, (int)index, this.interop);
            return this.isSignedProfile.profile(intValue >= 0) ? intValue : (double)(intValue & 4294967295L);
         } else {
            return defaultValue;
         }
      }

      @Override
      protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            int intValue = typedArray.getInt(target, (int)index, this.interop);
            if (this.isSignedProfile.profile(intValue >= 0)) {
               return intValue;
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               throw new UnexpectedResultException((double)(intValue & 4294967295L));
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
         }
      }

      @Override
      protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
            return typedArray.getInt(target, (int)index, this.interop) & 4294967295L;
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
         }
      }
   }

   private static class WritableArrayReadElementCacheNode extends ReadElementNode.ArrayClassGuardCachedArrayReadElementCacheNode {
      WritableArrayReadElementCacheNode(ScriptArray arrayType, ReadElementNode.ArrayReadElementCacheNode next) {
         super(arrayType, next);
      }

      @Override
      protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
         AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
         return this.inBounds.profile(writableArray.isInBoundsFast(target, index))
            ? writableArray.getInBoundsFast(target, (int)index)
            : this.readOutOfBounds(target, index, receiver, defaultValue, context);
      }

      @Override
      protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
         return this.inBounds.profile(writableArray.isInBoundsFast(target, index))
            ? writableArray.getInBoundsFastInt(target, (int)index)
            : JSTypesGen.expectInteger(this.readOutOfBounds(target, index, receiver, defaultValue, context));
      }

      @Override
      protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
         AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
         return this.inBounds.profile(writableArray.isInBoundsFast(target, index))
            ? writableArray.getInBoundsFastDouble(target, (int)index)
            : JSTypesGen.expectDouble(this.readOutOfBounds(target, index, receiver, defaultValue, context));
      }
   }
}
