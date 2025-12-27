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
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
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
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class WriteElementNode extends JSTargetableNode {
   @Node.Child
   protected JavaScriptNode targetNode;
   @Node.Child
   protected JavaScriptNode indexNode;
   @Node.Child
   private ToArrayIndexNode toArrayIndexNode;
   @Node.Child
   protected JavaScriptNode valueNode;
   @Node.Child
   private WriteElementNode.WriteElementTypeCacheNode typeCacheNode;
   final JSContext context;
   final boolean isStrict;
   final boolean writeOwn;
   @CompilerDirectives.CompilationFinal
   private byte indexState;
   private static final byte INDEX_INT = 1;
   private static final byte INDEX_OBJECT = 2;

   public static WriteElementNode create(JSContext context, boolean isStrict) {
      return create(null, null, null, context, isStrict, false);
   }

   public static WriteElementNode create(JSContext context, boolean isStrict, boolean writeOwn) {
      return create(null, null, null, context, isStrict, writeOwn);
   }

   public static WriteElementNode create(JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSContext context, boolean isStrict) {
      return create(targetNode, indexNode, valueNode, context, isStrict, false);
   }

   private static WriteElementNode create(
      JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSContext context, boolean isStrict, boolean writeOwn
   ) {
      return new WriteElementNode(targetNode, indexNode, valueNode, context, isStrict, writeOwn);
   }

   protected WriteElementNode(
      JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSContext context, boolean isStrict, boolean writeOwn
   ) {
      assert !(indexNode instanceof JSToPropertyKeyNode.JSToPropertyKeyWrapperNode);

      this.targetNode = targetNode;
      this.indexNode = indexNode;
      this.valueNode = valueNode;
      this.context = context;
      this.isStrict = isStrict;
      this.writeOwn = writeOwn;
   }

   protected final Object toArrayIndex(Object index) {
      if (this.toArrayIndexNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toArrayIndexNode = this.insert(ToArrayIndexNode.createNoStringToIndex());
      }

      return this.toArrayIndexNode.execute(index);
   }

   protected void requireObjectCoercible(Object target, int index) {
   }

   protected void requireObjectCoercible(Object target, Object index) {
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.WriteElementTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (this.materializationNeeded() && materializedTags.contains(JSTags.WriteElementTag.class)) {
         JavaScriptNode clonedTarget = this.targetNode != null && !this.targetNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.targetNode, this, materializedTags)
            : this.targetNode;
         JavaScriptNode clonedIndex = this.indexNode != null && !this.indexNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.indexNode, this, materializedTags)
            : this.indexNode;
         JavaScriptNode clonedValue = this.valueNode != null && !this.valueNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.valueNode, this, materializedTags)
            : this.valueNode;
         if (clonedTarget == this.targetNode && clonedIndex == this.indexNode && clonedValue == this.valueNode) {
            return this;
         } else {
            if (clonedTarget == this.targetNode) {
               clonedTarget = cloneUninitialized(this.targetNode, materializedTags);
            }

            if (clonedIndex == this.indexNode) {
               clonedIndex = cloneUninitialized(this.indexNode, materializedTags);
            }

            if (clonedValue == this.valueNode) {
               clonedValue = cloneUninitialized(this.valueNode, materializedTags);
            }

            WriteElementNode cloned = this.createMaterialized(clonedTarget, clonedIndex, clonedValue);
            transferSourceSectionAndTags(this, cloned);
            return cloned;
         }
      } else {
         return this;
      }
   }

   private boolean materializationNeeded() {
      return this.targetNode != null && !this.targetNode.hasSourceSection()
         || this.indexNode != null && !this.indexNode.hasSourceSection()
         || this.valueNode != null && !this.valueNode.hasSourceSection();
   }

   protected WriteElementNode createMaterialized(JavaScriptNode newTarget, JavaScriptNode newIndex, JavaScriptNode newValue) {
      return create(newTarget, newIndex, newValue, this.getContext(), this.isStrict(), this.writeOwn());
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
         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndex(frame, target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndex(frame, target, this.toArrayIndex(index), receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.indexNode.executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            this.requireObjectCoercible(target, var7.getResult());
            return this.executeWithTargetAndIndex(frame, target, this.toArrayIndex(var7.getResult()), receiver);
         }

         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndex(frame, target, index, receiver);
      } else {
         assert is == 2;

         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndex(frame, target, this.toArrayIndex(index), receiver);
      }
   }

   public int executeWithTargetInt(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
      byte is = this.indexState;
      if (is == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndexInt(frame, target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndexInt(frame, target, this.toArrayIndex(index), receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.indexNode.executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            this.requireObjectCoercible(target, var7.getResult());
            return this.executeWithTargetAndIndexInt(frame, target, this.toArrayIndex(var7.getResult()), receiver);
         }

         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndexInt(frame, target, index, receiver);
      } else {
         assert is == 2;

         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndexInt(frame, target, this.toArrayIndex(index), receiver);
      }
   }

   public double executeWithTargetDouble(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
      byte is = this.indexState;
      if (is == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         if (index instanceof Integer) {
            this.indexState = 1;
            return this.executeWithTargetAndIndexDouble(frame, target, ((Integer)index).intValue(), receiver);
         } else {
            this.indexState = 2;
            return this.executeWithTargetAndIndexDouble(frame, target, this.toArrayIndex(index), receiver);
         }
      } else if (is == 1) {
         int index;
         try {
            index = this.indexNode.executeInt(frame);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.indexState = 2;
            this.requireObjectCoercible(target, var7.getResult());
            return this.executeWithTargetAndIndexDouble(frame, target, this.toArrayIndex(var7.getResult()), receiver);
         }

         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndexDouble(frame, target, index, receiver);
      } else {
         assert is == 2;

         Object index = this.indexNode.execute(frame);
         this.requireObjectCoercible(target, index);
         return this.executeWithTargetAndIndexDouble(frame, target, this.toArrayIndex(index), receiver);
      }
   }

   protected Object executeWithTargetAndIndex(VirtualFrame frame, Object target, Object index, Object receiver) {
      Object value = this.valueNode.execute(frame);
      this.executeWithTargetAndIndexAndValue(target, index, value, receiver);
      return value;
   }

   protected Object executeWithTargetAndIndex(VirtualFrame frame, Object target, int index, Object receiver) {
      Object value = this.valueNode.execute(frame);
      this.executeWithTargetAndIndexAndValue(target, index, value, receiver);
      return value;
   }

   protected int executeWithTargetAndIndexInt(VirtualFrame frame, Object target, Object index, Object receiver) throws UnexpectedResultException {
      try {
         int value = this.valueNode.executeInt(frame);
         this.executeWithTargetAndIndexAndValue(target, index, value, receiver);
         return value;
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeWithTargetAndIndexAndValue(target, index, var6.getResult(), receiver);
         throw var6;
      }
   }

   protected int executeWithTargetAndIndexInt(VirtualFrame frame, Object target, int index, Object receiver) throws UnexpectedResultException {
      try {
         int value = this.valueNode.executeInt(frame);
         this.executeWithTargetAndIndexAndValue(target, index, Integer.valueOf(value), receiver);
         return value;
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeWithTargetAndIndexAndValue(target, index, var6.getResult(), receiver);
         throw var6;
      }
   }

   protected double executeWithTargetAndIndexDouble(VirtualFrame frame, Object target, Object index, Object receiver) throws UnexpectedResultException {
      try {
         double value = this.valueNode.executeDouble(frame);
         this.executeWithTargetAndIndexAndValue(target, index, value, receiver);
         return value;
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeWithTargetAndIndexAndValue(target, index, var7.getResult(), receiver);
         throw var7;
      }
   }

   protected double executeWithTargetAndIndexDouble(VirtualFrame frame, Object target, int index, Object receiver) throws UnexpectedResultException {
      try {
         double value = this.valueNode.executeDouble(frame);
         this.executeWithTargetAndIndexAndValue(target, index, Double.valueOf(value), receiver);
         return value;
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeWithTargetAndIndexAndValue(target, index, var7.getResult(), receiver);
         throw var7;
      }
   }

   public final void executeWithTargetAndIndexAndValue(Object target, Object index, Object value) {
      this.executeWithTargetAndIndexAndValue(target, index, value, target);
   }

   public final void executeWithTargetAndIndexAndValue(Object target, int index, Object value) {
      this.executeWithTargetAndIndexAndValue(target, index, value, target);
   }

   public final void executeWithTargetAndIndexAndValue(Object target, long index, Object value) {
      this.executeWithTargetAndIndexAndValue(target, index, value, target);
   }

   @ExplodeLoop
   public final void executeWithTargetAndIndexAndValue(Object target, Object index, Object value, Object receiver) {
      for (WriteElementNode.WriteElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            c.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      WriteElementNode.WriteElementTypeCacheNode specialization = this.specialize(target);
      specialization.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
   }

   @ExplodeLoop
   public final void executeWithTargetAndIndexAndValue(Object target, int index, Object value, Object receiver) {
      for (WriteElementNode.WriteElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            c.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      WriteElementNode.WriteElementTypeCacheNode specialization = this.specialize(target);
      specialization.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
   }

   @ExplodeLoop
   public final void executeWithTargetAndIndexAndValue(Object target, long index, Object value, Object receiver) {
      for (WriteElementNode.WriteElementTypeCacheNode c = this.typeCacheNode; c != null; c = c.typeCacheNext) {
         boolean guard = c.guard(target);
         if (guard) {
            c.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      WriteElementNode.WriteElementTypeCacheNode specialization = this.specialize(target);
      specialization.executeWithTargetAndIndexUnguarded(target, index, value, receiver, this);
   }

   private WriteElementNode.WriteElementTypeCacheNode specialize(Object target) {
      CompilerAsserts.neverPartOfCompilation();
      Lock lock = this.getLock();
      lock.lock();

      try {
         WriteElementNode.WriteElementTypeCacheNode currentHead = this.typeCacheNode;

         for (WriteElementNode.WriteElementTypeCacheNode c = currentHead; c != null; c = c.typeCacheNext) {
            if (c.guard(target)) {
               return c;
            }
         }

         WriteElementNode.WriteElementTypeCacheNode newCacheNode = makeTypeCacheNode(target, currentHead);
         this.insert(newCacheNode);
         this.typeCacheNode = newCacheNode;
         if (!newCacheNode.guard(target)) {
            throw Errors.shouldNotReachHere();
         } else {
            return newCacheNode;
         }
      } finally {
         lock.unlock();
      }
   }

   private static WriteElementNode.WriteElementTypeCacheNode makeTypeCacheNode(Object target, WriteElementNode.WriteElementTypeCacheNode next) {
      if (JSDynamicObject.isJSDynamicObject(target)) {
         return new WriteElementNode.JSObjectWriteElementTypeCacheNode(next);
      } else if (Strings.isTString(target)) {
         return new WriteElementNode.StringWriteElementTypeCacheNode(next);
      } else if (target instanceof Boolean) {
         return new WriteElementNode.BooleanWriteElementTypeCacheNode(next);
      } else if (target instanceof Number) {
         return new WriteElementNode.NumberWriteElementTypeCacheNode(target.getClass(), next);
      } else if (target instanceof Symbol) {
         return new WriteElementNode.SymbolWriteElementTypeCacheNode(next);
      } else if (target instanceof BigInt) {
         return new WriteElementNode.BigIntWriteElementTypeCacheNode(next);
      } else if (target instanceof TruffleObject) {
         assert JSRuntime.isForeignObject(target);

         return new WriteElementNode.ForeignObjectWriteElementTypeCacheNode(target.getClass(), next);
      } else {
         assert JSRuntime.isJavaPrimitive(target);

         return new WriteElementNode.JavaObjectWriteElementTypeCacheNode(target.getClass(), next);
      }
   }

   static WriteElementNode.ArrayWriteElementCacheNode makeArrayCacheNode(
      JSDynamicObject target, ScriptArray array, WriteElementNode.ArrayWriteElementCacheNode next
   ) {
      if (JSSlowArray.isJSSlowArray(target) || JSSlowArgumentsArray.isJSSlowArgumentsObject(target)) {
         return new WriteElementNode.ExactArrayWriteElementCacheNode(array, next);
      } else if (array.isLengthNotWritable() || !array.isExtensible()) {
         return new WriteElementNode.ExactArrayWriteElementCacheNode(array, next);
      } else if (array instanceof LazyRegexResultArray) {
         return new WriteElementNode.LazyRegexResultArrayWriteElementCacheNode(array, next);
      } else if (array instanceof LazyRegexResultIndicesArray) {
         return new WriteElementNode.LazyRegexResultIndicesArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractConstantArray) {
         return new WriteElementNode.ConstantArrayWriteElementCacheNode(array, next);
      } else if (array instanceof HolesIntArray) {
         return new WriteElementNode.HolesIntArrayWriteElementCacheNode(array, next);
      } else if (array instanceof HolesDoubleArray) {
         return new WriteElementNode.HolesDoubleArrayWriteElementCacheNode(array, next);
      } else if (array instanceof HolesJSObjectArray) {
         return new WriteElementNode.HolesJSObjectArrayWriteElementCacheNode(array, next);
      } else if (array instanceof HolesObjectArray) {
         return new WriteElementNode.HolesObjectArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractIntArray) {
         return new WriteElementNode.IntArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractDoubleArray) {
         return new WriteElementNode.DoubleArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractObjectArray) {
         return new WriteElementNode.ObjectArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractJSObjectArray) {
         return new WriteElementNode.JSObjectArrayWriteElementCacheNode(array, next);
      } else if (array instanceof AbstractWritableArray) {
         return new WriteElementNode.WritableArrayWriteElementCacheNode(array, next);
      } else if (!(array instanceof TypedArray)) {
         return new WriteElementNode.ExactArrayWriteElementCacheNode(array, next);
      } else if (array instanceof TypedArray.AbstractUint32Array) {
         return new WriteElementNode.Uint32ArrayWriteElementCacheNode((TypedArray)array, next);
      } else if (array instanceof TypedArray.AbstractUint8ClampedArray) {
         return new WriteElementNode.Uint8ClampedArrayWriteElementCacheNode((TypedArray)array, next);
      } else if (array instanceof TypedArray.TypedIntArray) {
         return new WriteElementNode.TypedIntArrayWriteElementCacheNode((TypedArray)array, next);
      } else if (array instanceof TypedArray.TypedFloatArray) {
         return new WriteElementNode.TypedFloatArrayWriteElementCacheNode((TypedArray)array, next);
      } else if (array instanceof TypedArray.TypedBigIntArray) {
         return new WriteElementNode.TypedBigIntArrayWriteElementCacheNode((TypedArray)array, next);
      } else {
         throw Errors.shouldNotReachHere();
      }
   }

   @Override
   public JavaScriptNode getTarget() {
      return this.targetNode;
   }

   public JavaScriptNode getElement() {
      return this.indexNode;
   }

   public JavaScriptNode getValue() {
      return this.valueNode;
   }

   public JSContext getContext() {
      return this.context;
   }

   public boolean isStrict() {
      return this.isStrict;
   }

   public boolean writeOwn() {
      return this.writeOwn;
   }

   boolean isSuperProperty() {
      return this.targetNode instanceof SuperPropertyReferenceNode;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         cloneUninitialized(this.targetNode, materializedTags),
         cloneUninitialized(this.indexNode, materializedTags),
         cloneUninitialized(this.valueNode, materializedTags),
         this.getContext(),
         this.isStrict(),
         this.writeOwn()
      );
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.valueNode.isResultAlwaysOfType(clazz);
   }

   public static WriteElementNode createCachedInterop() {
      return create(JavaScriptLanguage.get(null).getJSContext(), true);
   }

   private abstract static class AbstractTypedArrayWriteElementCacheNode extends WriteElementNode.ArrayClassGuardCachedArrayWriteElementCacheNode {
      @Node.Child
      protected InteropLibrary interop;

      AbstractTypedArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
         this.interop = arrayType.isInterop() ? InteropLibrary.getFactory().createDispatched(5) : InteropLibrary.getUncached();
      }
   }

   private abstract static class AbstractTypedIntArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();

      AbstractTypedIntArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected final boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
         int iValue = this.toInt(value);
         if (!JSArrayBufferView.hasDetachedBuffer(target, root.context) && this.inBoundsProfile.profile(typedArray.hasElement(target, index))) {
            typedArray.setInt(target, (int)index, iValue, this.interop);
         }

         return true;
      }

      protected abstract int toInt(Object value);
   }

   private abstract static class ArrayClassGuardCachedArrayWriteElementCacheNode extends WriteElementNode.ArrayWriteElementCacheNode {
      private final ScriptArray arrayType;

      ArrayClassGuardCachedArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayCacheNext);
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
   }

   abstract static class ArrayWriteElementCacheNode extends JavaScriptBaseNode {
      @Node.Child
      WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext;

      ArrayWriteElementCacheNode(WriteElementNode.ArrayWriteElementCacheNode next) {
         this.arrayCacheNext = next;
      }

      protected abstract boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root);

      protected abstract boolean guard(Object target, ScriptArray array);
   }

   private static class BigIntWriteElementTypeCacheNode extends WriteElementNode.ToPropertyKeyCachedWriteElementTypeCacheNode {
      BigIntWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         BigInt bigInt = (BigInt)target;
         JSContext context = root.context;
         JSObject.setWithReceiver(
            JSBigInt.create(context, this.getRealm(), bigInt), this.toPropertyKey(index), value, target, root.isStrict, this.classProfile, root
         );
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         BigInt bigInt = (BigInt)target;
         JSContext context = root.context;
         JSObject.setWithReceiver(JSBigInt.create(context, this.getRealm(), bigInt), index, value, target, root.isStrict, this.classProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof BigInt;
      }
   }

   private static class BooleanWriteElementTypeCacheNode extends WriteElementNode.ToPropertyKeyCachedWriteElementTypeCacheNode {
      BooleanWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         Boolean bool = (Boolean)target;
         JSObject.setWithReceiver(
            JSBoolean.create(root.context, this.getRealm(), bool), this.toPropertyKey(index), value, target, root.isStrict, this.classProfile, root
         );
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         Boolean bool = (Boolean)target;
         JSObject.setWithReceiver(JSBoolean.create(root.context, this.getRealm(), bool), index, value, target, root.isStrict, this.classProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof Boolean;
      }
   }

   private static class ConstantArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();
      private final BranchProfile inBoundsIntBranch = BranchProfile.create();
      private final BranchProfile inBoundsDoubleBranch = BranchProfile.create();
      private final BranchProfile inBoundsJSObjectBranch = BranchProfile.create();
      private final BranchProfile inBoundsObjectBranch = BranchProfile.create();
      private final ScriptArray.ProfileHolder createWritableProfile = AbstractConstantArray.createCreateWritableProfile();

      ConstantArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractConstantArray constantArray = (AbstractConstantArray)this.cast(array);
         if (this.inBoundsProfile.profile(index >= 0L && index < 2147483647L)) {
            ScriptArray newArray;
            if (value instanceof Integer) {
               this.inBoundsIntBranch.enter();
               newArray = constantArray.createWriteableInt(target, index, (Integer)value, this.createWritableProfile);
            } else if (value instanceof Double) {
               this.inBoundsDoubleBranch.enter();
               newArray = constantArray.createWriteableDouble(target, index, (Double)value, this.createWritableProfile);
            } else if (JSDynamicObject.isJSDynamicObject(value)) {
               this.inBoundsJSObjectBranch.enter();
               newArray = constantArray.createWriteableJSObject(target, index, (JSDynamicObject)value, this.createWritableProfile);
            } else {
               this.inBoundsObjectBranch.enter();
               newArray = constantArray.createWriteableObject(target, index, value, this.createWritableProfile);
            }

            return this.setArrayAndWrite(newArray, target, index, value, root);
         } else {
            JSAbstractArray.arraySetArrayType(target, SparseArray.makeSparseArray(target, array).setElement(target, index, value, root.isStrict));
            return true;
         }
      }
   }

   private static class DoubleArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final BranchProfile intValueBranch = BranchProfile.create();
      private final BranchProfile doubleValueBranch = BranchProfile.create();
      private final BranchProfile toObjectBranch = BranchProfile.create();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContiguousCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedHolesCondition = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      DoubleArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractDoubleArray doubleArray = (AbstractDoubleArray)this.cast(array);
         double doubleValue;
         if (value instanceof Double) {
            this.doubleValueBranch.enter();
            doubleValue = (Double)value;
         } else {
            if (!(value instanceof Integer)) {
               this.toObjectBranch.enter();
               return this.setArrayAndWrite(doubleArray.toObject(target, index, value), target, index, value, root);
            }

            this.intValueBranch.enter();
            doubleValue = ((Integer)value).intValue();
         }

         return this.executeWithDoubleValueInner(target, doubleArray, index, doubleValue, root);
      }

      private boolean executeWithDoubleValueInner(
         JSDynamicObject target, AbstractDoubleArray doubleArray, long index, double doubleValue, WriteElementNode root
      ) {
         assert !(doubleArray instanceof HolesDoubleArray);

         if (this.nonHolesArrayNeedsSlowSet(target, doubleArray, index, root)) {
            return false;
         } else {
            int iIndex = (int)index;
            if (this.inBoundsFastCondition.profile(doubleArray.isInBoundsFast(target, index))) {
               doubleArray.setInBoundsFast(target, iIndex, doubleValue);
               return true;
            } else if (this.inBoundsCondition.profile(doubleArray.isInBounds(target, iIndex))) {
               doubleArray.setInBounds(target, iIndex, doubleValue, this.profile);
               return true;
            } else if (this.supportedCondition.profile(doubleArray.isSupported(target, index))) {
               doubleArray.setSupported(target, iIndex, doubleValue, this.profile);
               return true;
            } else {
               ScriptArray toArrayType;
               if (this.supportedContiguousCondition
                  .profile(!(doubleArray instanceof AbstractContiguousDoubleArray) && doubleArray.isSupportedContiguous(target, index))) {
                  toArrayType = doubleArray.toContiguous(target, index, doubleValue);
               } else if (this.supportedHolesCondition.profile(doubleArray.isSupportedHoles(target, index))) {
                  toArrayType = doubleArray.toHoles(target, index, doubleValue);
               } else {
                  assert doubleArray.isSparse(target, index);

                  toArrayType = doubleArray.toSparse(target, index, doubleValue);
               }

               return this.setArrayAndWrite(toArrayType, target, index, doubleValue, root);
            }
         }
      }
   }

   private static class ExactArrayWriteElementCacheNode extends WriteElementNode.ArrayClassGuardCachedArrayWriteElementCacheNode {
      ExactArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         return false;
      }
   }

   static class ForeignObjectWriteElementTypeCacheNode extends WriteElementNode.WriteElementTypeCacheNode {
      private final Class<?> targetClass;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private InteropLibrary keyInterop;
      @Node.Child
      private InteropLibrary setterInterop;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode;
      @Node.Child
      private ExportValueNode exportValue;
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode;
      private final BranchProfile errorBranch = BranchProfile.create();

      ForeignObjectWriteElementTypeCacheNode(Class<?> targetClass, WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);

         assert !JSDynamicObject.class.isAssignableFrom(targetClass) : targetClass;

         this.targetClass = targetClass;
         this.exportValue = ExportValueNode.create();
         this.interop = InteropLibrary.getFactory().createDispatched(5);
         this.keyInterop = InteropLibrary.getFactory().createDispatched(5);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         Object truffleObject = CompilerDirectives.castExact(target, this.targetClass);
         if (this.interop.isNull(truffleObject)) {
            throw Errors.createTypeErrorCannotSetProperty(index, truffleObject, this, root.getContext());
         } else {
            Object exportedValue = this.exportValue.execute(value);
            Object propertyKey;
            if (this.interop.hasArrayElements(truffleObject)) {
               Object indexOrPropertyKey = this.toArrayIndex(index);
               if (indexOrPropertyKey instanceof Long) {
                  try {
                     this.interop.writeArrayElement(truffleObject, (Long)indexOrPropertyKey, exportedValue);
                     return;
                  } catch (UnsupportedTypeException | UnsupportedMessageException | InvalidArrayIndexException var13) {
                     if (root.isStrict) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(truffleObject, var13, "writeArrayElement", this);
                     }

                     return;
                  }
               }

               propertyKey = indexOrPropertyKey;

               assert JSRuntime.isPropertyKey(indexOrPropertyKey);
            } else {
               propertyKey = this.toPropertyKey(index);
            }

            if (root.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(truffleObject)) {
               try {
                  this.interop.writeHashEntry(truffleObject, propertyKey, exportedValue);
               } catch (UnsupportedMessageException | UnsupportedTypeException | UnknownKeyException var12) {
                  if (root.isStrict) {
                     this.errorBranch.enter();
                     throw Errors.createTypeErrorInteropException(truffleObject, var12, "writeHashEntry", this);
                  }
               }
            } else if (!(propertyKey instanceof Symbol)) {
               TruffleString stringKey = (TruffleString)propertyKey;
               if (!root.context.isOptionNashornCompatibilityMode() || !this.tryInvokeSetter(truffleObject, stringKey, exportedValue, root.context)) {
                  try {
                     String javaPropertyKey = Strings.toJavaString(stringKey);
                     this.interop.writeMember(truffleObject, javaPropertyKey, exportedValue);
                  } catch (UnsupportedTypeException | UnsupportedMessageException | UnknownIdentifierException var11) {
                     if (root.isStrict) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorInteropException(truffleObject, var11, "writeMember", this);
                     }
                  }
               }
            }
         }
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, int index, Object value, Object receiver, WriteElementNode root) {
         this.executeWithTargetAndIndexUnguarded(target, Integer.valueOf(index), value, receiver, root);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         this.executeWithTargetAndIndexUnguarded(target, Long.valueOf(index), value, receiver, root);
      }

      @Override
      public boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.targetClass);
      }

      private boolean tryInvokeSetter(Object thisObj, TruffleString key, Object value, JSContext context) {
         assert context.isOptionNashornCompatibilityMode();

         TruffleLanguage.Env env = this.getRealm().getEnv();
         if (env.isHostObject(thisObj)) {
            TruffleString setterKey = PropertyCacheNode.getAccessorKey(Strings.SET, key);
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
               this.setterInterop.invokeMember(thisObj, Strings.toJavaString(setterKey), value);
               return true;
            } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException | UnknownIdentifierException var8) {
            }
         }

         return false;
      }

      private Object toArrayIndex(Object index) {
         if (this.toArrayIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
         }

         return this.toArrayIndexNode.execute(index);
      }

      private Object toPropertyKey(Object index) {
         if (this.toPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
         }

         return this.toPropertyKeyNode.execute(index);
      }
   }

   private static class HolesDoubleArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final BranchProfile doubleValueBranch = BranchProfile.create();
      private final BranchProfile intValueBranch = BranchProfile.create();
      private final BranchProfile toObjectBranch = BranchProfile.create();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastHoleCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedNotContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile hasExplicitHolesProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile containsHolesProfile = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      HolesDoubleArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         HolesDoubleArray holesDoubleArray = (HolesDoubleArray)this.cast(array);
         double doubleValue;
         if (value instanceof Double) {
            this.doubleValueBranch.enter();
            doubleValue = (Double)value;
         } else {
            if (!(value instanceof Integer)) {
               this.toObjectBranch.enter();
               return this.setArrayAndWrite(holesDoubleArray.toObject(target, index, value), target, index, value, root);
            }

            this.intValueBranch.enter();
            doubleValue = ((Integer)value).intValue();
         }

         return this.executeWithDoubleValueInner(target, holesDoubleArray, index, doubleValue, root);
      }

      private boolean executeWithDoubleValueInner(
         JSDynamicObject target, HolesDoubleArray holesDoubleArray, long index, double doubleValue, WriteElementNode root
      ) {
         if (this.holesArrayNeedsSlowSet(target, holesDoubleArray, index, root)) {
            return false;
         } else {
            int iIndex = (int)index;
            boolean containsHoles = this.containsHolesProfile.profile(this.containsHoles(target, holesDoubleArray, index));
            if (containsHoles
               && this.inBoundsFastCondition.profile(holesDoubleArray.isInBoundsFast(target, index) && !HolesDoubleArray.isHoleValue(doubleValue))) {
               if (this.inBoundsFastHoleCondition.profile(holesDoubleArray.isHoleFast(target, iIndex))) {
                  holesDoubleArray.setInBoundsFastHole(target, iIndex, doubleValue);
               } else {
                  holesDoubleArray.setInBoundsFastNonHole(target, iIndex, doubleValue);
               }

               return true;
            } else if (containsHoles
               && this.inBoundsCondition.profile(holesDoubleArray.isInBounds(target, iIndex) && !HolesDoubleArray.isHoleValue(doubleValue))) {
               holesDoubleArray.setInBounds(target, iIndex, doubleValue, this.profile);
               return true;
            } else if (containsHoles
               && this.supportedContainsHolesCondition.profile(holesDoubleArray.isSupported(target, index) && !HolesDoubleArray.isHoleValue(doubleValue))) {
               holesDoubleArray.setSupported(target, iIndex, doubleValue, this.profile);
               return true;
            } else {
               ScriptArray toArrayType;
               if (!containsHoles && this.supportedNotContainsHolesCondition.profile(holesDoubleArray.isSupported(target, index))) {
                  toArrayType = holesDoubleArray.toNonHoles(target, index, doubleValue);
               } else {
                  assert holesDoubleArray.isSparse(target, index);

                  toArrayType = holesDoubleArray.toSparse(target, index, doubleValue);
               }

               return this.setArrayAndWrite(toArrayType, target, index, doubleValue, root);
            }
         }
      }

      private boolean containsHoles(JSDynamicObject target, HolesDoubleArray holesDoubleArray, long index) {
         return this.hasExplicitHolesProfile.profile(JSArray.arrayGetHoleCount(target) > 0) || !holesDoubleArray.isInBoundsFast(target, index);
      }
   }

   private static class HolesIntArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final BranchProfile intValueBranch = BranchProfile.create();
      private final BranchProfile toDoubleBranch = BranchProfile.create();
      private final BranchProfile toObjectBranch = BranchProfile.create();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastHoleCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedNotContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile hasExplicitHolesProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile containsHolesProfile = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      HolesIntArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         HolesIntArray holesIntArray = (HolesIntArray)this.cast(array);
         if (value instanceof Integer) {
            this.intValueBranch.enter();
            int intValue = (Integer)value;
            return this.executeWithIntValueInner(target, holesIntArray, index, intValue, root);
         } else if (value instanceof Double) {
            this.toDoubleBranch.enter();
            double doubleValue = (Double)value;
            return this.setArrayAndWrite(holesIntArray.toDouble(target, index, doubleValue), target, index, doubleValue, root);
         } else {
            this.toObjectBranch.enter();
            return this.setArrayAndWrite(holesIntArray.toObject(target, index, value), target, index, value, root);
         }
      }

      private boolean executeWithIntValueInner(JSDynamicObject target, HolesIntArray holesIntArray, long index, int intValue, WriteElementNode root) {
         if (this.holesArrayNeedsSlowSet(target, holesIntArray, index, root)) {
            return false;
         } else {
            int iIndex = (int)index;
            boolean containsHoles = this.containsHolesProfile.profile(this.containsHoles(target, holesIntArray, index));
            if (containsHoles && this.inBoundsFastCondition.profile(holesIntArray.isInBoundsFast(target, index) && !HolesIntArray.isHoleValue(intValue))) {
               if (this.inBoundsFastHoleCondition.profile(holesIntArray.isHoleFast(target, iIndex))) {
                  holesIntArray.setInBoundsFastHole(target, iIndex, intValue);
               } else {
                  holesIntArray.setInBoundsFastNonHole(target, iIndex, intValue);
               }

               return true;
            } else if (containsHoles && this.inBoundsCondition.profile(holesIntArray.isInBounds(target, iIndex) && !HolesIntArray.isHoleValue(intValue))) {
               holesIntArray.setInBounds(target, iIndex, intValue, this.profile);
               return true;
            } else if (containsHoles
               && this.supportedContainsHolesCondition.profile(holesIntArray.isSupported(target, index) && !HolesIntArray.isHoleValue(intValue))) {
               holesIntArray.setSupported(target, iIndex, intValue, this.profile);
               return true;
            } else {
               ScriptArray toArrayType;
               if (!containsHoles && this.supportedNotContainsHolesCondition.profile(holesIntArray.isSupported(target, index))) {
                  toArrayType = holesIntArray.toNonHoles(target, index, intValue);
               } else {
                  assert holesIntArray.isSparse(target, index);

                  toArrayType = holesIntArray.toSparse(target, index, intValue);
               }

               return this.setArrayAndWrite(toArrayType, target, index, intValue, root);
            }
         }
      }

      private boolean containsHoles(JSDynamicObject target, HolesIntArray holesIntArray, long index) {
         return this.hasExplicitHolesProfile.profile(JSArray.arrayGetHoleCount(target) > 0) || !holesIntArray.isInBoundsFast(target, index);
      }
   }

   private static class HolesJSObjectArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile objectType = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastHoleCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedNotContainsHolesCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile hasExplicitHolesProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile containsHolesProfile = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      HolesJSObjectArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);

         assert arrayType.getClass() == HolesJSObjectArray.class;
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         HolesJSObjectArray holesArray = (HolesJSObjectArray)this.cast(array);
         return this.objectType.profile(JSDynamicObject.isJSDynamicObject(value))
            ? this.executeWithJSObjectValueInner(target, holesArray, index, (JSDynamicObject)value, root)
            : this.setArrayAndWrite(holesArray.toObject(target, index, value), target, index, value, root);
      }

      private boolean executeWithJSObjectValueInner(
         JSDynamicObject target, HolesJSObjectArray jsobjectArray, long index, JSDynamicObject value, WriteElementNode root
      ) {
         if (this.holesArrayNeedsSlowSet(target, jsobjectArray, index, root)) {
            return false;
         } else {
            boolean containsHoles = this.containsHolesProfile.profile(this.containsHoles(target, jsobjectArray, index));
            if (containsHoles && this.inBoundsFastCondition.profile(jsobjectArray.isInBoundsFast(target, index))) {
               assert !HolesJSObjectArray.isHoleValue(value);

               if (this.inBoundsFastHoleCondition.profile(jsobjectArray.isHoleFast(target, (int)index))) {
                  jsobjectArray.setInBoundsFastHole(target, (int)index, value);
               } else {
                  jsobjectArray.setInBoundsFastNonHole(target, (int)index, value);
               }

               return true;
            } else if (containsHoles && this.inBoundsCondition.profile(jsobjectArray.isInBounds(target, (int)index))) {
               assert !HolesJSObjectArray.isHoleValue(value);

               jsobjectArray.setInBounds(target, (int)index, value, this.profile);
               return true;
            } else if (containsHoles && this.supportedContainsHolesCondition.profile(jsobjectArray.isSupported(target, index))) {
               assert !HolesJSObjectArray.isHoleValue(value);

               jsobjectArray.setSupported(target, (int)index, value, this.profile);
               return true;
            } else {
               ScriptArray toArrayType;
               if (!containsHoles && this.supportedNotContainsHolesCondition.profile(jsobjectArray.isSupported(target, index))) {
                  toArrayType = jsobjectArray.toNonHoles(target, index, value);
               } else {
                  assert jsobjectArray.isSparse(target, index);

                  toArrayType = jsobjectArray.toSparse(target, index, value);
               }

               return this.setArrayAndWrite(toArrayType, target, index, value, root);
            }
         }
      }

      private boolean containsHoles(JSDynamicObject target, HolesJSObjectArray holesJSObjectArray, long index) {
         return this.hasExplicitHolesProfile.profile(JSArray.arrayGetHoleCount(target) > 0) || !holesJSObjectArray.isInBoundsFast(target, index);
      }
   }

   private static class HolesObjectArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastHoleCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedCondition = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      HolesObjectArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);

         assert arrayType.getClass() == HolesObjectArray.class;
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         HolesObjectArray objectArray = (HolesObjectArray)array;
         if (this.holesArrayNeedsSlowSet(target, objectArray, index, root)) {
            return false;
         } else if (this.inBoundsFastCondition.profile(objectArray.isInBoundsFast(target, index))) {
            assert !HolesObjectArray.isHoleValue(value);

            if (this.inBoundsFastHoleCondition.profile(objectArray.isHoleFast(target, (int)index))) {
               objectArray.setInBoundsFastHole(target, (int)index, value);
            } else {
               objectArray.setInBoundsFastNonHole(target, (int)index, value);
            }

            return true;
         } else if (this.inBoundsCondition.profile(objectArray.isInBounds(target, (int)index))) {
            assert !HolesObjectArray.isHoleValue(value);

            objectArray.setInBounds(target, (int)index, value, this.profile);
            return true;
         } else if (this.supportedCondition.profile(objectArray.isSupported(target, index))) {
            assert !HolesObjectArray.isHoleValue(value);

            objectArray.setSupported(target, (int)index, value);
            return true;
         } else {
            assert objectArray.isSparse(target, index);

            return this.setArrayAndWrite(objectArray.toSparse(target, index, value), target, index, value, root);
         }
      }
   }

   private static class IntArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final BranchProfile intValueBranch = BranchProfile.create();
      private final BranchProfile toDoubleBranch = BranchProfile.create();
      private final BranchProfile toObjectBranch = BranchProfile.create();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedNonZeroCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedZeroCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContiguousCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedHolesCondition = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      IntArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractIntArray intArray = (AbstractIntArray)this.cast(array);
         if (value instanceof Integer) {
            this.intValueBranch.enter();
            return this.executeWithIntValueInner(target, intArray, index, (Integer)value, root);
         } else if (value instanceof Double) {
            this.toDoubleBranch.enter();
            double doubleValue = (Double)value;
            return this.setArrayAndWrite(intArray.toDouble(target, index, doubleValue), target, index, doubleValue, root);
         } else {
            this.toObjectBranch.enter();
            return this.setArrayAndWrite(intArray.toObject(target, index, value), target, index, value, root);
         }
      }

      private boolean executeWithIntValueInner(JSDynamicObject target, AbstractIntArray intArray, long index, int intValue, WriteElementNode root) {
         assert !(intArray instanceof HolesIntArray);

         if (this.nonHolesArrayNeedsSlowSet(target, intArray, index, root)) {
            return false;
         } else {
            int iIndex = (int)index;
            if (this.inBoundsFastCondition.profile(intArray.isInBoundsFast(target, index) && !mightTransferToNonContiguous(intArray, target, index))) {
               intArray.setInBoundsFast(target, iIndex, intValue);
               return true;
            } else if (this.inBoundsCondition.profile(intArray.isInBounds(target, iIndex) && !mightTransferToNonContiguous(intArray, target, index))) {
               intArray.setInBounds(target, iIndex, intValue, this.profile);
               return true;
            } else if (this.supportedNonZeroCondition.profile(intArray.isSupported(target, index) && !mightTransferToNonContiguous(intArray, target, index))) {
               intArray.setSupported(target, iIndex, intValue, this.profile);
               return true;
            } else {
               ScriptArray toArrayType;
               if (this.supportedZeroCondition.profile(mightTransferToNonContiguous(intArray, target, index) && intArray.isSupported(target, index))) {
                  toArrayType = intArray.toNonContiguous(target, iIndex, intValue, this.profile);
               } else if (this.supportedContiguousCondition
                  .profile(!(intArray instanceof AbstractContiguousIntArray) && intArray.isSupportedContiguous(target, index))) {
                  toArrayType = intArray.toContiguous(target, index, intValue);
               } else if (this.supportedHolesCondition.profile(intArray.isSupportedHoles(target, index))) {
                  toArrayType = intArray.toHoles(target, index, intValue);
               } else {
                  assert intArray.isSparse(target, index);

                  toArrayType = intArray.toSparse(target, index, intValue);
               }

               return this.setArrayAndWrite(toArrayType, target, index, intValue, root);
            }
         }
      }

      private static boolean mightTransferToNonContiguous(AbstractIntArray intArray, JSDynamicObject target, long index) {
         return intArray instanceof ContiguousIntArray && index == 0L && intArray.firstElementIndex(target) == 1L;
      }
   }

   private static class JSObjectArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile objectType = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContiguousCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedHolesCondition = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      JSObjectArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractJSObjectArray jsobjectArray = (AbstractJSObjectArray)this.cast(array);
         if (this.objectType.profile(JSDynamicObject.isJSDynamicObject(value))) {
            JSDynamicObject jsobjectValue = (JSDynamicObject)value;
            return this.executeWithJSObjectValueInner(target, jsobjectArray, index, jsobjectValue, root);
         } else {
            return this.setArrayAndWrite(jsobjectArray.toObject(target, index, value), target, index, value, root);
         }
      }

      private boolean executeWithJSObjectValueInner(
         JSDynamicObject target, AbstractJSObjectArray jsobjectArray, long index, JSDynamicObject jsobjectValue, WriteElementNode root
      ) {
         assert !(jsobjectArray instanceof HolesJSObjectArray);

         int iIndex = (int)index;
         if (this.nonHolesArrayNeedsSlowSet(target, jsobjectArray, index, root)) {
            return false;
         } else if (this.inBoundsFastCondition.profile(jsobjectArray.isInBoundsFast(target, index))) {
            jsobjectArray.setInBoundsFast(target, iIndex, jsobjectValue);
            return true;
         } else if (this.inBoundsCondition.profile(jsobjectArray.isInBounds(target, iIndex))) {
            jsobjectArray.setInBounds(target, iIndex, jsobjectValue, this.profile);
            return true;
         } else if (this.supportedCondition.profile(jsobjectArray.isSupported(target, index))) {
            jsobjectArray.setSupported(target, iIndex, jsobjectValue, this.profile);
            return true;
         } else {
            ScriptArray toArrayType;
            if (this.supportedContiguousCondition
               .profile(!(jsobjectArray instanceof AbstractContiguousJSObjectArray) && jsobjectArray.isSupportedContiguous(target, index))) {
               toArrayType = jsobjectArray.toContiguous(target, index, jsobjectValue);
            } else if (this.supportedHolesCondition.profile(jsobjectArray.isSupportedHoles(target, index))) {
               toArrayType = jsobjectArray.toHoles(target, index, jsobjectValue);
            } else {
               assert jsobjectArray.isSparse(target, index);

               toArrayType = jsobjectArray.toSparse(target, index, jsobjectValue);
            }

            return this.setArrayAndWrite(toArrayType, target, index, jsobjectValue, root);
         }
      }
   }

   private static class JSObjectWriteElementTypeCacheNode extends WriteElementNode.WriteElementTypeCacheNode {
      @Node.Child
      private IsArrayNode isArrayNode;
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode;
      @Node.Child
      private WriteElementNode.ArrayWriteElementCacheNode arrayWriteElementNode;
      @Node.Child
      private IsJSDynamicObjectNode isObjectNode;
      private final ConditionProfile intOrStringIndexProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile arrayProfile = ConditionProfile.createBinaryProfile();
      private final JSClassProfile jsclassProfile = JSClassProfile.create();
      @Node.Child
      private CachedSetPropertyNode setPropertyCachedNode;

      JSObjectWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
         this.isArrayNode = IsArrayNode.createIsFastOrTypedArray();
         this.isObjectNode = IsJSDynamicObjectNode.create();
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         if (this.arrayProfile.profile(this.isArrayNode.execute(targetObject))) {
            ScriptArray array = JSObject.getArray(targetObject);
            Object objIndex = this.toArrayIndex(index);
            if (this.intOrStringIndexProfile.profile(objIndex instanceof Long)) {
               long longIndex = (Long)objIndex;
               if (!this.executeSetArray(targetObject, array, longIndex, value, root)) {
                  this.setPropertyGenericEvaluatedIndex(targetObject, longIndex, value, receiver, root);
               }
            } else {
               this.setPropertyGenericEvaluatedStringOrSymbol(targetObject, objIndex, value, receiver, root);
            }
         } else {
            this.setPropertyGeneric(targetObject, index, value, receiver, root);
         }
      }

      private Object toArrayIndex(Object index) {
         if (this.toArrayIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
         }

         return this.toArrayIndexNode.execute(index);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, int index, Object value, Object receiver, WriteElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         if (this.arrayProfile.profile(this.isArrayNode.execute(targetObject))) {
            ScriptArray array = JSObject.getArray(targetObject);
            if (this.intOrStringIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
               if (!this.executeSetArray(targetObject, array, index, value, root)) {
                  this.setPropertyGenericEvaluatedIndex(targetObject, index, value, receiver, root);
               }
            } else {
               this.setPropertyGenericEvaluatedStringOrSymbol(targetObject, Strings.fromInt(index), value, receiver, root);
            }
         } else {
            this.setPropertyGeneric(targetObject, index, value, receiver, root);
         }
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         JSDynamicObject targetObject = (JSDynamicObject)target;
         if (this.arrayProfile.profile(this.isArrayNode.execute(targetObject))) {
            ScriptArray array = JSObject.getArray(targetObject);
            if (this.intOrStringIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
               if (!this.executeSetArray(targetObject, array, index, value, root)) {
                  this.setPropertyGenericEvaluatedIndex(targetObject, index, value, receiver, root);
               }
            } else {
               this.setPropertyGenericEvaluatedStringOrSymbol(targetObject, Strings.fromLong(index), value, receiver, root);
            }
         } else {
            this.setPropertyGeneric(targetObject, index, value, receiver, root);
         }
      }

      private void setPropertyGenericEvaluatedIndex(JSDynamicObject targetObject, long index, Object value, Object receiver, WriteElementNode root) {
         JSObject.setWithReceiver(targetObject, index, value, receiver, root.isStrict, this.jsclassProfile, root);
      }

      private void setPropertyGenericEvaluatedStringOrSymbol(JSDynamicObject targetObject, Object key, Object value, Object receiver, WriteElementNode root) {
         JSObject.setWithReceiver(targetObject, key, value, receiver, root.isStrict, this.jsclassProfile, root);
      }

      private void setPropertyGeneric(JSDynamicObject targetObject, Object index, Object value, Object receiver, WriteElementNode root) {
         this.setCachedProperty(targetObject, index, value, receiver, root);
      }

      private void setCachedProperty(JSDynamicObject targetObject, Object index, Object value, Object receiver, WriteElementNode root) {
         if (this.setPropertyCachedNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.setPropertyCachedNode = this.insert(CachedSetPropertyNode.create(root.context, root.isStrict, root.writeOwn, root.isSuperProperty()));
         }

         this.setPropertyCachedNode.execute(targetObject, index, value, receiver);
      }

      @Override
      public boolean guard(Object target) {
         return this.isObjectNode.executeBoolean(target);
      }

      @ExplodeLoop
      private boolean executeSetArray(JSDynamicObject targetObject, ScriptArray array, long index, Object value, WriteElementNode root) {
         for (WriteElementNode.ArrayWriteElementCacheNode c = this.arrayWriteElementNode; c != null; c = c.arrayCacheNext) {
            boolean guard = c.guard(targetObject, array);
            if (guard) {
               return c.executeSetArray(targetObject, array, index, value, root);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         WriteElementNode.ArrayWriteElementCacheNode specialization = this.specialize(targetObject, array);
         return specialization.executeSetArray(targetObject, array, index, value, root);
      }

      private WriteElementNode.ArrayWriteElementCacheNode specialize(JSDynamicObject target, ScriptArray array) {
         CompilerAsserts.neverPartOfCompilation();
         Lock lock = this.getLock();
         lock.lock();

         try {
            WriteElementNode.ArrayWriteElementCacheNode currentHead = this.arrayWriteElementNode;

            for (WriteElementNode.ArrayWriteElementCacheNode c = currentHead; c != null; c = c.arrayCacheNext) {
               if (c.guard(target, array)) {
                  return c;
               }
            }

            currentHead = purgeStaleCacheEntries(currentHead, target);
            WriteElementNode.ArrayWriteElementCacheNode newCacheNode = WriteElementNode.makeArrayCacheNode(target, array, currentHead);
            this.insert(newCacheNode);
            this.arrayWriteElementNode = newCacheNode;
            if (currentHead != null && currentHead.arrayCacheNext != null && currentHead.arrayCacheNext.arrayCacheNext != null) {
               this.reportPolymorphicSpecialize();
            }

            if (!newCacheNode.guard(target, array)) {
               throw Errors.shouldNotReachHere();
            } else {
               return newCacheNode;
            }
         } finally {
            lock.unlock();
         }
      }

      private static WriteElementNode.ArrayWriteElementCacheNode purgeStaleCacheEntries(
         WriteElementNode.ArrayWriteElementCacheNode head, JSDynamicObject target
      ) {
         if (JSConfig.TrackArrayAllocationSites && head != null && JSArray.isJSArray(target)) {
            ArrayAllocationSite allocationSite = JSAbstractArray.arrayGetAllocationSite(target);
            if (allocationSite != null && allocationSite.getInitialArrayType() != null) {
               WriteElementNode.ArrayWriteElementCacheNode c = head;

               for (WriteElementNode.ArrayWriteElementCacheNode prev = null; c != null; c = c.arrayCacheNext) {
                  if (c instanceof WriteElementNode.ConstantArrayWriteElementCacheNode) {
                     WriteElementNode.ConstantArrayWriteElementCacheNode existingNode = (WriteElementNode.ConstantArrayWriteElementCacheNode)c;
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
   }

   private static class JavaObjectWriteElementTypeCacheNode extends WriteElementNode.WriteElementTypeCacheNode {
      protected final Class<?> targetClass;

      JavaObjectWriteElementTypeCacheNode(Class<?> targetClass, WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
         this.targetClass = targetClass;
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, int index, Object value, Object receiver, WriteElementNode root) {
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
      }

      @Override
      public final boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.targetClass);
      }
   }

   private static class LazyRegexResultArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();
      @Node.Child
      private TRegexUtil.TRegexMaterializeResultNode materializeResultNode = TRegexUtil.TRegexMaterializeResultNode.create();

      LazyRegexResultArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         LazyRegexResultArray lazyRegexResultArray = (LazyRegexResultArray)this.cast(array);
         ScriptArray newArray = lazyRegexResultArray.createWritable(root.context, this.materializeResultNode, target, index, value);
         if (this.inBoundsProfile.profile(index >= 0L && index < 2147483647L)) {
            return this.setArrayAndWrite(newArray, target, index, value, root);
         } else {
            JSAbstractArray.arraySetArrayType(target, SparseArray.makeSparseArray(target, newArray).setElement(target, index, value, root.isStrict));
            return true;
         }
      }
   }

   private static class LazyRegexResultIndicesArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();

      LazyRegexResultIndicesArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         LazyRegexResultIndicesArray lazyRegexResultIndicesArray = (LazyRegexResultIndicesArray)this.cast(array);
         ScriptArray newArray = lazyRegexResultIndicesArray.createWritable(root.context, this.resultAccessor, target, index, value);
         if (this.inBoundsProfile.profile(index >= 0L && index < 2147483647L)) {
            return this.setArrayAndWrite(newArray, target, index, value, root);
         } else {
            JSAbstractArray.arraySetArrayType(target, SparseArray.makeSparseArray(target, newArray).setElement(target, index, value, root.isStrict));
            return true;
         }
      }
   }

   private static class NumberWriteElementTypeCacheNode extends WriteElementNode.ToPropertyKeyCachedWriteElementTypeCacheNode {
      private final Class<?> numberClass;

      NumberWriteElementTypeCacheNode(Class<?> numberClass, WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
         this.numberClass = numberClass;
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         Number number = CompilerDirectives.castExact(target, (Class<Number>)this.numberClass);
         JSObject.setWithReceiver(
            JSNumber.create(root.context, this.getRealm(), number), this.toPropertyKey(index), value, target, root.isStrict, this.classProfile, root
         );
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         Number number = CompilerDirectives.castExact(target, (Class<Number>)this.numberClass);
         JSObject.setWithReceiver(JSNumber.create(root.context, this.getRealm(), number), index, value, target, root.isStrict, this.classProfile, root);
      }

      @Override
      public boolean guard(Object target) {
         return CompilerDirectives.isExact(target, this.numberClass);
      }
   }

   private static class ObjectArrayWriteElementCacheNode extends WriteElementNode.RecursiveCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsFastCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile inBoundsCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedContiguousCondition = ConditionProfile.createBinaryProfile();
      private final ConditionProfile supportedHolesCondition = ConditionProfile.createBinaryProfile();
      private final ScriptArray.ProfileHolder profile = AbstractWritableArray.createSetSupportedProfile();

      ObjectArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractObjectArray objectArray = (AbstractObjectArray)this.cast(array);

         assert !(objectArray instanceof HolesObjectArray);

         if (this.nonHolesArrayNeedsSlowSet(target, objectArray, index, root)) {
            return false;
         } else {
            int iIndex = (int)index;
            if (this.inBoundsFastCondition.profile(objectArray.isInBoundsFast(target, index))) {
               objectArray.setInBoundsFast(target, iIndex, value);
               return true;
            } else if (this.inBoundsCondition.profile(objectArray.isInBounds(target, iIndex))) {
               objectArray.setInBounds(target, iIndex, value, this.profile);
               return true;
            } else if (this.supportedCondition.profile(objectArray.isSupported(target, index))) {
               objectArray.setSupported(target, iIndex, value);
               return true;
            } else {
               ScriptArray toArrayType;
               if (this.supportedContiguousCondition
                  .profile(!(objectArray instanceof AbstractContiguousObjectArray) && objectArray.isSupportedContiguous(target, index))) {
                  toArrayType = objectArray.toContiguous(target, index, value);
               } else if (this.supportedHolesCondition.profile(objectArray.isSupportedHoles(target, index))) {
                  toArrayType = objectArray.toHoles(target, index, value);
               } else {
                  assert objectArray.isSparse(target, index);

                  toArrayType = objectArray.toSparse(target, index, value);
               }

               return this.setArrayAndWrite(toArrayType, target, index, value, root);
            }
         }
      }
   }

   private abstract static class RecursiveCachedArrayWriteElementCacheNode extends WriteElementNode.ArrayClassGuardCachedArrayWriteElementCacheNode {
      @Node.Child
      private WriteElementNode.ArrayWriteElementCacheNode recursiveWrite;
      private final BranchProfile needPrototypeBranch = BranchProfile.create();

      RecursiveCachedArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      protected final boolean setArrayAndWrite(ScriptArray newArray, JSDynamicObject target, long index, Object value, WriteElementNode root) {
         JSAbstractArray.arraySetArrayType(target, newArray);
         return this.executeRecursive(target, newArray, index, value, root);
      }

      protected final boolean nonHolesArrayNeedsSlowSet(JSDynamicObject target, AbstractWritableArray arrayType, long index, WriteElementNode root) {
         assert !arrayType.isHolesType();

         if (!root.context.getArrayPrototypeNoElementsAssumption().isValid() && !root.writeOwn && !arrayType.hasElement(target, index)) {
            this.needPrototypeBranch.enter();
            return true;
         } else {
            return false;
         }
      }

      protected final boolean holesArrayNeedsSlowSet(JSDynamicObject target, AbstractWritableArray arrayType, long index, WriteElementNode root) {
         assert arrayType.isHolesType();

         if ((
               !root.context.getArrayPrototypeNoElementsAssumption().isValid() && !root.writeOwn
                  || !root.context.getFastArrayAssumption().isValid() && JSSlowArray.isJSSlowArray(target)
                  || !root.context.getFastArgumentsObjectAssumption().isValid() && JSSlowArgumentsArray.isJSSlowArgumentsObject(target)
            )
            && !arrayType.hasElement(target, index)) {
            this.needPrototypeBranch.enter();
            return true;
         } else {
            return false;
         }
      }

      @ExplodeLoop
      private boolean executeRecursive(JSDynamicObject targetObject, ScriptArray array, long index, Object value, WriteElementNode root) {
         for (WriteElementNode.ArrayWriteElementCacheNode c = this.recursiveWrite; c != null; c = c.arrayCacheNext) {
            boolean guard = c.guard(targetObject, array);
            if (guard) {
               return c.executeSetArray(targetObject, array, index, value, root);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         WriteElementNode.ArrayWriteElementCacheNode specialization = this.specialize(targetObject, array);
         return specialization.executeSetArray(targetObject, array, index, value, root);
      }

      private WriteElementNode.ArrayWriteElementCacheNode specialize(JSDynamicObject target, ScriptArray array) {
         CompilerAsserts.neverPartOfCompilation();
         Lock lock = this.getLock();
         lock.lock();

         try {
            WriteElementNode.ArrayWriteElementCacheNode currentHead = this.recursiveWrite;

            for (WriteElementNode.ArrayWriteElementCacheNode c = currentHead; c != null; c = c.arrayCacheNext) {
               if (c.guard(target, array)) {
                  return c;
               }
            }

            WriteElementNode.ArrayWriteElementCacheNode newCacheNode = WriteElementNode.makeArrayCacheNode(target, array, currentHead);
            this.insert(newCacheNode);
            this.recursiveWrite = newCacheNode;
            if (!newCacheNode.guard(target, array)) {
               throw Errors.shouldNotReachHere();
            } else {
               return newCacheNode;
            }
         } finally {
            lock.unlock();
         }
      }
   }

   private static class StringWriteElementTypeCacheNode extends WriteElementNode.ToPropertyKeyCachedWriteElementTypeCacheNode {
      private final ConditionProfile isIndexProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isImmutable = ConditionProfile.createBinaryProfile();
      @Node.Child
      private ToArrayIndexNode toArrayIndexNode = ToArrayIndexNode.createNoToPropertyKey();

      StringWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         TruffleString string = (TruffleString)target;
         Object convertedIndex = this.toArrayIndexNode.execute(index);
         if (this.isIndexProfile.profile(convertedIndex instanceof Long)) {
            long longIndex = (Long)convertedIndex;
            if (this.isImmutable.profile(longIndex >= 0L && longIndex < Strings.length(string))) {
               if (root.isStrict) {
                  throw Errors.createTypeErrorNotWritableIndex(longIndex, string, this);
               }

               return;
            }
         }

         JSObject.setWithReceiver(
            JSString.create(root.context, this.getRealm(), string), this.toPropertyKey(index), value, target, root.isStrict, this.classProfile, root
         );
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         TruffleString string = (TruffleString)target;
         if (this.isImmutable.profile(index >= 0L && index < Strings.length(string))) {
            if (root.isStrict) {
               throw Errors.createTypeErrorNotWritableIndex(index, string, this);
            }
         } else {
            JSObject.setWithReceiver(JSString.create(root.context, this.getRealm(), string), index, value, target, root.isStrict, this.classProfile, root);
         }
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof TruffleString;
      }
   }

   private static class SymbolWriteElementTypeCacheNode extends WriteElementNode.ToPropertyKeyCachedWriteElementTypeCacheNode {
      SymbolWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         super(next);
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root) {
         if (root.isStrict) {
            throw Errors.createTypeError("cannot set element on Symbol in strict mode", this);
         } else {
            Symbol symbol = (Symbol)target;
            JSObject.setWithReceiver(
               JSSymbol.create(root.context, this.getRealm(), symbol), this.toPropertyKey(index), value, receiver, root.isStrict, this.classProfile, root
            );
         }
      }

      @Override
      protected void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root) {
         if (root.isStrict) {
            throw Errors.createTypeError("cannot set element on Symbol in strict mode", this);
         } else {
            Symbol symbol = (Symbol)target;
            JSObject.setWithReceiver(JSSymbol.create(root.context, this.getRealm(), symbol), index, value, receiver, root.isStrict, this.classProfile, root);
         }
      }

      @Override
      public boolean guard(Object target) {
         return target instanceof Symbol;
      }
   }

   private abstract static class ToPropertyKeyCachedWriteElementTypeCacheNode extends WriteElementNode.WriteElementTypeCacheNode {
      @Node.Child
      private JSToPropertyKeyNode indexToPropertyKeyNode;
      protected final JSClassProfile classProfile = JSClassProfile.create();

      ToPropertyKeyCachedWriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
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
      protected void executeWithTargetAndIndexUnguarded(Object target, int index, Object value, Object receiver, WriteElementNode root) {
         this.executeWithTargetAndIndexUnguarded(target, index, value, receiver, root);
      }
   }

   private static class TypedBigIntArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedArrayWriteElementCacheNode {
      @Node.Child
      private JSToBigIntNode toBigIntNode;
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();

      TypedBigIntArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
         this.toBigIntNode = JSToBigIntNode.create();
      }

      @Override
      protected final boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         TypedArray.TypedBigIntArray typedArray = (TypedArray.TypedBigIntArray)this.cast(array);
         BigInt biValue = this.toBigIntNode.executeBigInteger(value);
         if (!JSArrayBufferView.hasDetachedBuffer(target, root.context) && this.inBoundsProfile.profile(typedArray.hasElement(target, index))) {
            typedArray.setBigInt(target, (int)index, biValue, this.interop);
         }

         return true;
      }
   }

   private static class TypedFloatArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();
      @Node.Child
      private JSToDoubleNode toDoubleNode = JSToDoubleNode.create();

      TypedFloatArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         TypedArray.TypedFloatArray typedArray = (TypedArray.TypedFloatArray)this.cast(array);
         double dValue = this.toDouble(value);
         if (!JSArrayBufferView.hasDetachedBuffer(target, root.context) && this.inBoundsProfile.profile(typedArray.hasElement(target, index))) {
            typedArray.setDouble(target, (int)index, dValue, this.interop);
         }

         return true;
      }

      private double toDouble(Object value) {
         return this.toDoubleNode.executeDouble(value);
      }
   }

   private static class TypedIntArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedIntArrayWriteElementCacheNode {
      @Node.Child
      private JSToInt32Node toIntNode = JSToInt32Node.create();

      TypedIntArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected int toInt(Object value) {
         return this.toIntNode.executeInt(value);
      }
   }

   private static class Uint32ArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedIntArrayWriteElementCacheNode {
      private final ConditionProfile toIntProfile = ConditionProfile.createBinaryProfile();
      @Node.Child
      private JSToNumberNode toNumberNode;

      Uint32ArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected int toInt(Object value) {
         return this.toIntProfile.profile(value instanceof Integer) ? (Integer)value : (int)JSRuntime.toUInt32(this.toNumber(value));
      }

      private Number toNumber(Object value) {
         if (this.toNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumberNode = this.insert(JSToNumberNode.create());
         }

         return this.toNumberNode.executeNumber(value);
      }
   }

   private static class Uint8ClampedArrayWriteElementCacheNode extends WriteElementNode.AbstractTypedIntArrayWriteElementCacheNode {
      private final ConditionProfile toIntProfile = ConditionProfile.createBinaryProfile();
      @Node.Child
      private JSToDoubleNode toDoubleNode;

      Uint8ClampedArrayWriteElementCacheNode(TypedArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected int toInt(Object value) {
         if (this.toIntProfile.profile(value instanceof Integer)) {
            return (Integer)value;
         } else {
            double doubleValue = this.toDouble(value);
            return TypedArray.Uint8ClampedArray.toInt(doubleValue);
         }
      }

      private double toDouble(Object value) {
         if (this.toDoubleNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toDoubleNode = this.insert(JSToDoubleNode.create());
         }

         return this.toDoubleNode.executeDouble(value);
      }
   }

   private static class WritableArrayWriteElementCacheNode extends WriteElementNode.ArrayClassGuardCachedArrayWriteElementCacheNode {
      private final ConditionProfile inBoundsProfile = ConditionProfile.createBinaryProfile();

      WritableArrayWriteElementCacheNode(ScriptArray arrayType, WriteElementNode.ArrayWriteElementCacheNode arrayCacheNext) {
         super(arrayType, arrayCacheNext);
      }

      @Override
      protected boolean executeSetArray(JSDynamicObject target, ScriptArray array, long index, Object value, WriteElementNode root) {
         AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
         if (this.inBoundsProfile.profile(writableArray.isInBoundsFast(target, index))) {
            JSAbstractArray.arraySetArrayType(target, writableArray.setElement(target, index, value, root.isStrict));
            return true;
         } else {
            return false;
         }
      }
   }

   abstract static class WriteElementTypeCacheNode extends JavaScriptBaseNode {
      @Node.Child
      WriteElementNode.WriteElementTypeCacheNode typeCacheNext;

      protected WriteElementTypeCacheNode(WriteElementNode.WriteElementTypeCacheNode next) {
         this.typeCacheNext = next;
      }

      protected abstract void executeWithTargetAndIndexUnguarded(Object target, Object index, Object value, Object receiver, WriteElementNode root);

      protected abstract void executeWithTargetAndIndexUnguarded(Object target, int index, Object value, Object receiver, WriteElementNode root);

      protected abstract void executeWithTargetAndIndexUnguarded(Object target, long index, Object value, Object receiver, WriteElementNode root);

      public abstract boolean guard(Object target);
   }
}
