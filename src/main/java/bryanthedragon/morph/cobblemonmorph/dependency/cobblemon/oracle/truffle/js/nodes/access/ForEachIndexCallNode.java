package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.LoopConditionProfile;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayLastElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayPreviousElementIndexNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public abstract class ForEachIndexCallNode extends JavaScriptBaseNode {
   @Node.Child
   private IsArrayNode isArrayNode = IsArrayNode.createIsAnyArray();
   protected final JSClassProfile targetClassProfile = JSClassProfile.create();
   protected final LoopConditionProfile loopCond = LoopConditionProfile.createCountingProfile();
   @Node.Child
   private ForEachIndexCallNode.CallbackNode callbackNode;
   @Node.Child
   protected ForEachIndexCallNode.MaybeResultNode maybeResultNode;
   @Node.Child
   private ReadElementNode.ReadElementArrayDispatchNode readElementNode;
   @Node.Child
   private JSArrayFirstElementIndexNode firstElementIndexNode;
   @Node.Child
   private JSArrayLastElementIndexNode lastElementIndexNode;
   @Node.Child
   private JSHasPropertyNode hasPropertyNode;
   @Node.Child
   private ImportValueNode toJSTypeNode;
   @Node.Child
   private InteropLibrary interop;
   protected final JSContext context;
   protected final boolean checkHasProperty;

   protected ForEachIndexCallNode(
      JSContext context,
      ForEachIndexCallNode.CallbackNode callbackArgumentsNode,
      ForEachIndexCallNode.MaybeResultNode maybeResultNode,
      boolean checkHasProperty
   ) {
      this.callbackNode = callbackArgumentsNode;
      this.maybeResultNode = maybeResultNode;
      this.context = context;
      this.checkHasProperty = checkHasProperty;
      this.readElementNode = ReadElementNode.ReadElementArrayDispatchNode.create();
   }

   public static ForEachIndexCallNode create(
      JSContext context,
      ForEachIndexCallNode.CallbackNode callbackArgumentsNode,
      ForEachIndexCallNode.MaybeResultNode maybeResultNode,
      boolean forward,
      boolean checkHasProperty
   ) {
      return (ForEachIndexCallNode)(forward
         ? new ForEachIndexCallNode.ForwardForEachIndexCallNode(context, callbackArgumentsNode, maybeResultNode, checkHasProperty)
         : new ForEachIndexCallNode.BackwardForEachIndexCallNode(context, callbackArgumentsNode, maybeResultNode, checkHasProperty));
   }

   public final Object executeForEachIndex(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
      boolean isArray = this.isArrayNode.execute(target);
      return isArray && this.context.getArrayPrototypeNoElementsAssumption().isValid()
         ? this.executeForEachIndexFast((JSDynamicObject)target, callback, callbackThisArg, fromIndex, length, initialResult)
         : this.executeForEachIndexSlow(target, callback, callbackThisArg, fromIndex, length, initialResult);
   }

   protected abstract Object executeForEachIndexFast(
      JSDynamicObject target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult
   );

   protected abstract Object executeForEachIndexSlow(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult);

   protected final long firstElementIndex(JSDynamicObject target, long length) {
      if (this.firstElementIndexNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.firstElementIndexNode = this.insert(JSArrayFirstElementIndexNode.create(this.context));
      }

      return this.firstElementIndexNode.executeLong(target, length);
   }

   protected final long lastElementIndex(JSDynamicObject target, long length) {
      if (this.lastElementIndexNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.lastElementIndexNode = this.insert(JSArrayLastElementIndexNode.create(this.context));
      }

      return this.lastElementIndexNode.executeLong(target, length);
   }

   protected final InteropLibrary getInterop() {
      if (this.interop == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
      }

      return this.interop;
   }

   protected Object foreignRead(Object target, long index, boolean isForeignArray) {
      if (this.toJSTypeNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toJSTypeNode = this.insert(ImportValueNode.create());
      }

      return isForeignArray
         ? JSInteropUtil.readArrayElementOrDefault(target, index, Undefined.instance, this.getInterop(), this.toJSTypeNode, this)
         : JSInteropUtil.readMemberOrDefault(target, Strings.fromLong(index), Undefined.instance, this.getInterop(), this.toJSTypeNode, this);
   }

   protected Object getElement(Object target, long index, boolean isForeign, boolean isForeignArray) {
      if (!isForeign) {
         assert JSDynamicObject.isJSDynamicObject(target);

         return JSObject.get((JSDynamicObject)target, index, this.targetClassProfile);
      } else {
         return this.foreignRead(target, index, isForeignArray);
      }
   }

   protected final boolean hasDetachedBuffer(Object view) {
      return !this.context.getTypedArrayNotDetachedAssumption().isValid()
         && JSArrayBufferView.isJSArrayBufferView(view)
         && JSArrayBufferView.hasDetachedBuffer((JSDynamicObject)view);
   }

   protected final Object callback(long index, Object value, Object target, Object callback, Object callbackThisArg, Object currentResult) {
      if (this.callbackNode == null) {
         TruffleSafepoint.poll(this);
         return callbackThisArg;
      } else {
         return this.callbackNode.apply(index, value, target, callback, callbackThisArg, currentResult);
      }
   }

   protected final Object readElementInBounds(JSDynamicObject target, long index) {
      return this.readElementNode.executeArrayGet(target, JSObject.getArray(target), index, target, Undefined.instance, this.context);
   }

   protected final boolean hasProperty(Object target, long index) {
      if (this.hasPropertyNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.hasPropertyNode = this.insert(JSHasPropertyNode.create());
      }

      return this.hasPropertyNode.executeBoolean(target, index);
   }

   protected static final class BackwardForEachIndexCallNode extends ForEachIndexCallNode {
      @Node.Child
      protected JSArrayPreviousElementIndexNode previousElementIndexNode;

      public BackwardForEachIndexCallNode(
         JSContext context,
         ForEachIndexCallNode.CallbackNode callbackArgumentsNode,
         ForEachIndexCallNode.MaybeResultNode maybeResultNode,
         boolean checkHasProperty
      ) {
         super(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
      }

      @Override
      protected Object executeForEachIndexFast(
         JSDynamicObject target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult
      ) {
         assert fromIndex < length;

         long index = this.previousElementIndex(target, fromIndex + 1L);
         Object currentResult = initialResult;

         long count;
         for (count = 0L;
            this.loopCond.profile(index >= 0L && index >= this.firstElementIndex(target, length))
               && (!this.checkHasProperty || !this.hasDetachedBuffer(target));
            index = this.previousElementIndex(target, index)
         ) {
            Object value = this.readElementInBounds(target, index);
            Object callbackResult = this.callback(index, value, target, callback, callbackThisArg, currentResult);
            ForEachIndexCallNode.MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value, callbackResult, currentResult);
            currentResult = maybeResult.get();
            if (maybeResult.isPresent()) {
               break;
            }

            count++;
         }

         ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, count);
         return currentResult;
      }

      @Override
      protected Object executeForEachIndexSlow(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
         Object currentResult = initialResult;
         boolean isForeign = JSRuntime.isForeignObject(target);
         boolean isForeignArray = isForeign && this.getInterop().hasArrayElements(target);

         for (long index = fromIndex; index >= 0L; index--) {
            if (!this.checkHasProperty || this.hasProperty(target, index)) {
               Object value = this.getElement(target, index, isForeign, isForeignArray);
               Object callbackResult = this.callback(index, value, target, callback, callbackThisArg, currentResult);
               ForEachIndexCallNode.MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value, callbackResult, currentResult);
               currentResult = maybeResult.get();
               if (maybeResult.isPresent()) {
                  break;
               }
            }
         }

         ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, fromIndex);
         return currentResult;
      }

      private long previousElementIndex(JSDynamicObject target, long currentIndex) {
         if (this.previousElementIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.previousElementIndexNode = this.insert(JSArrayPreviousElementIndexNode.create(this.context));
         }

         return this.previousElementIndexNode.executeLong(target, currentIndex);
      }
   }

   public abstract static class CallbackNode extends JavaScriptBaseNode {
      public abstract Object apply(long index, Object value, Object target, Object callback, Object callbackThisArg, Object currentResult);
   }

   protected static final class ForwardForEachIndexCallNode extends ForEachIndexCallNode {
      private final ConditionProfile fromIndexZero = ConditionProfile.createBinaryProfile();
      @Node.Child
      private JSArrayNextElementIndexNode nextElementIndexNode;

      public ForwardForEachIndexCallNode(
         JSContext context,
         ForEachIndexCallNode.CallbackNode callbackArgumentsNode,
         ForEachIndexCallNode.MaybeResultNode maybeResultNode,
         boolean checkHasProperty
      ) {
         super(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
      }

      @Override
      protected Object executeForEachIndexFast(
         JSDynamicObject target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult
      ) {
         long index = this.fromIndexZero.profile(fromIndex == 0L)
            ? this.firstElementIndex(target, length)
            : this.nextElementIndex(target, fromIndex - 1L, length);
         Object currentResult = initialResult;

         long count;
         for (count = 0L;
            this.loopCond.profile(index < length && index <= this.lastElementIndex(target, length))
               && (!this.checkHasProperty || !this.hasDetachedBuffer(target));
            index = this.nextElementIndex(target, index, length)
         ) {
            Object value = this.readElementInBounds(target, index);
            Object callbackResult = this.callback(index, value, target, callback, callbackThisArg, currentResult);
            ForEachIndexCallNode.MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value, callbackResult, currentResult);
            currentResult = maybeResult.get();
            if (maybeResult.isPresent()) {
               break;
            }

            count++;
         }

         ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, count);
         return currentResult;
      }

      @Override
      protected Object executeForEachIndexSlow(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
         Object currentResult = initialResult;
         boolean isForeign = JSRuntime.isForeignObject(target);
         boolean isForeignArray = isForeign && this.getInterop().hasArrayElements(target);

         for (long index = fromIndex; index < length; index++) {
            if (!this.checkHasProperty || this.hasProperty(target, index)) {
               Object value = this.getElement(target, index, isForeign, isForeignArray);
               Object callbackResult = this.callback(index, value, target, callback, callbackThisArg, currentResult);
               ForEachIndexCallNode.MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value, callbackResult, currentResult);
               currentResult = maybeResult.get();
               if (maybeResult.isPresent()) {
                  break;
               }
            }
         }

         ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, length - fromIndex);
         return currentResult;
      }

      private long nextElementIndex(JSDynamicObject target, long currentIndex, long length) {
         if (this.nextElementIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.nextElementIndexNode = this.insert(JSArrayNextElementIndexNode.create(this.context));
         }

         return this.nextElementIndexNode.executeLong(target, currentIndex, length);
      }
   }

   @CompilerDirectives.ValueType
   public static final class MaybeResult<T> {
      private final T result;
      private final boolean resultPresent;

      public MaybeResult(T result, boolean resultPresent) {
         this.result = result;
         this.resultPresent = resultPresent;
      }

      public static <T> ForEachIndexCallNode.MaybeResult<T> returnResult(T result) {
         return new ForEachIndexCallNode.MaybeResult<>(result, true);
      }

      public static <T> ForEachIndexCallNode.MaybeResult<T> continueResult(T result) {
         return new ForEachIndexCallNode.MaybeResult<>(result, false);
      }

      public boolean isPresent() {
         return this.resultPresent;
      }

      public T get() {
         return this.result;
      }
   }

   public abstract static class MaybeResultNode extends JavaScriptBaseNode {
      public abstract ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value, Object callbackResult, Object currentResult);
   }
}
