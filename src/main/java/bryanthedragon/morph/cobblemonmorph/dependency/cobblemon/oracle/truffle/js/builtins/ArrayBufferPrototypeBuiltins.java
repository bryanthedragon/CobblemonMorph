package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.nio.ByteBuffer;

public final class ArrayBufferPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<ArrayBufferPrototypeBuiltins.ArrayBufferPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new ArrayBufferPrototypeBuiltins();

   protected ArrayBufferPrototypeBuiltins() {
      super(JSArrayBuffer.PROTOTYPE_NAME, ArrayBufferPrototypeBuiltins.ArrayBufferPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ArrayBufferPrototypeBuiltins.ArrayBufferPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case slice:
            return ArrayBufferPrototypeBuiltinsFactory.JSArrayBufferSliceNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case byteLength:
            return ArrayBufferPrototypeBuiltinsFactory.ByteLengthGetterNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum ArrayBufferPrototype implements BuiltinEnum<ArrayBufferPrototypeBuiltins.ArrayBufferPrototype> {
      byteLength(0),
      slice(2);

      private final int length;

      private ArrayBufferPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isGetter() {
         return this == byteLength;
      }
   }

   @ImportStatic({JSArrayBuffer.class, JSConfig.class})
   public abstract static class ByteLengthGetterNode extends JSBuiltinNode {
      public ByteLengthGetterNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSHeapArrayBuffer(thisObj)")
      protected int heapArrayBuffer(Object thisObj) {
         byte[] byteArray = JSArrayBuffer.getByteArray(thisObj);
         return !this.getContext().getTypedArrayNotDetachedAssumption().isValid() && byteArray == null ? 0 : byteArray.length;
      }

      @Specialization(guards = "isJSDirectArrayBuffer(thisObj)")
      protected int directArrayBuffer(Object thisObj) {
         ByteBuffer byteBuffer = JSArrayBuffer.getDirectByteBuffer(thisObj);
         return !this.getContext().getTypedArrayNotDetachedAssumption().isValid() && byteBuffer == null ? 0 : byteBuffer.capacity();
      }

      @Specialization(guards = "isJSInteropArrayBuffer(thisObj)")
      protected int interopArrayBuffer(Object thisObj, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
         Object buffer = JSArrayBuffer.getInteropBuffer(thisObj);
         if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && buffer == null) {
            return 0;
         } else {
            try {
               long bufferSize = interop.getBufferSize(buffer);

               assert JSRuntime.longIsRepresentableAsInt(bufferSize);

               return (int)bufferSize;
            } catch (UnsupportedMessageException var6) {
               return 0;
            }
         }
      }

      @Fallback
      protected static int error(Object thisObj) {
         throw Errors.createTypeErrorArrayBufferExpected();
      }
   }

   public abstract static class JSArrayBufferAbstractSliceNode extends ArrayBufferPrototypeBuiltins.JSArrayBufferOperation {
      @Node.Child
      private ArrayPrototypeBuiltins.ArraySpeciesConstructorNode arraySpeciesCreateNode;

      public JSArrayBufferAbstractSliceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected int getStart(Object start, int len) {
         long relativeStart = this.toInteger(start);
         return relativeStart < 0L ? (int)Math.max(len + relativeStart, 0L) : (int)Math.min(relativeStart, (long)len);
      }

      protected int getEnd(Object end, int len) {
         long relativeEnd = end == Undefined.instance ? len : this.toInteger(end);
         return relativeEnd < 0L ? (int)Math.max(len + relativeEnd, 0L) : (int)Math.min(relativeEnd, (long)len);
      }

      protected static int clampIndex(int index, int lowerBound, int upperBound) {
         return clamp(index >= 0 ? index : index + upperBound, lowerBound, upperBound);
      }

      private static int clamp(int index, int lowerBound, int upperBound) {
         return Math.max(Math.min(index, upperBound), lowerBound);
      }

      public ArrayPrototypeBuiltins.ArraySpeciesConstructorNode getArraySpeciesConstructorNode() {
         if (this.arraySpeciesCreateNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.arraySpeciesCreateNode = this.insert(ArrayPrototypeBuiltins.ArraySpeciesConstructorNode.create(this.getContext(), true));
         }

         return this.arraySpeciesCreateNode;
      }
   }

   public abstract static class JSArrayBufferOperation extends JSBuiltinNode {
      @Node.Child
      private JSToIntegerAsLongNode toIntegerNode;

      public JSArrayBufferOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected long toInteger(Object thisObject) {
         if (this.toIntegerNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toIntegerNode = this.insert(JSToIntegerAsLongNode.create());
         }

         return this.toIntegerNode.executeLong(thisObject);
      }
   }

   @ImportStatic({JSArrayBuffer.class, JSConfig.class})
   public abstract static class JSArrayBufferSliceNode extends ArrayBufferPrototypeBuiltins.JSArrayBufferAbstractSliceNode {
      private final BranchProfile errorBranch = BranchProfile.create();

      public JSArrayBufferSliceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSHeapArrayBuffer(thisObj)")
      protected JSDynamicObject sliceIntInt(JSDynamicObject thisObj, int begin, int end) {
         byte[] byteArray = JSArrayBuffer.getByteArray(thisObj);
         int clampedBegin = clampIndex(begin, 0, byteArray.length);
         int clampedEnd = clampIndex(end, clampedBegin, byteArray.length);
         int newLen = Math.max(clampedEnd - clampedBegin, 0);
         JSDynamicObject resObj = this.constructNewArrayBuffer(thisObj, newLen);
         this.checkErrors(resObj, thisObj, newLen, false);
         byte[] newByteArray = JSArrayBuffer.getByteArray(resObj);
         System.arraycopy(byteArray, clampedBegin, newByteArray, 0, newLen);
         return resObj;
      }

      private JSDynamicObject constructNewArrayBuffer(JSDynamicObject thisObj, int newLen) {
         JSDynamicObject defaultConstructor = this.getRealm().getArrayBufferConstructor();
         JSDynamicObject constr = this.getArraySpeciesConstructorNode().speciesConstructor(thisObj, defaultConstructor);
         return (JSDynamicObject)this.getArraySpeciesConstructorNode().construct(constr, newLen);
      }

      private void checkErrors(Object resObj, Object thisObj, int newLen, boolean direct) {
         if ((!direct || JSArrayBuffer.isJSDirectArrayBuffer(resObj)) && (direct || JSArrayBuffer.isJSHeapArrayBuffer(resObj))) {
            if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(resObj)) {
               this.errorBranch.enter();
               throw Errors.createTypeErrorDetachedBuffer();
            } else if (resObj == thisObj) {
               this.errorBranch.enter();
               throw Errors.createTypeError("SameValue(new, O) is forbidden");
            } else if ((!direct || JSArrayBuffer.getDirectByteLength(resObj) >= newLen) && (direct || JSArrayBuffer.getHeapByteLength(resObj) >= newLen)) {
               if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(thisObj)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorDetachedBuffer();
               }
            } else {
               this.errorBranch.enter();
               throw Errors.createTypeError("insufficient length constructed");
            }
         } else {
            this.errorBranch.enter();
            throw Errors.createTypeErrorArrayBufferExpected();
         }
      }

      @Specialization(guards = "isJSHeapArrayBuffer(thisObj)", replaces = "sliceIntInt")
      protected JSDynamicObject slice(JSDynamicObject thisObj, Object begin0, Object end0) {
         int len = JSArrayBuffer.getByteArray(thisObj).length;
         int begin = this.getStart(begin0, len);
         int finalEnd = this.getEnd(end0, len);
         return this.sliceIntInt(thisObj, begin, finalEnd);
      }

      @Specialization(guards = "isJSDirectArrayBuffer(thisObj)")
      protected JSDynamicObject sliceDirectIntInt(JSDynamicObject thisObj, int begin, int end) {
         ByteBuffer byteBuffer = JSArrayBuffer.getDirectByteBuffer(thisObj);
         int byteLength = JSArrayBuffer.getDirectByteLength(thisObj);
         int clampedBegin = clampIndex(begin, 0, byteLength);
         int clampedEnd = clampIndex(end, clampedBegin, byteLength);
         int newLen = clampedEnd - clampedBegin;
         JSDynamicObject resObj = this.constructNewArrayBuffer(thisObj, newLen);
         this.checkErrors(resObj, thisObj, newLen, true);
         ByteBuffer resBuffer = JSArrayBuffer.getDirectByteBuffer(resObj);
         Boundaries.byteBufferPutSlice(resBuffer, 0, byteBuffer, clampedBegin, clampedEnd);
         return resObj;
      }

      @Specialization(guards = "isJSDirectArrayBuffer(thisObj)", replaces = "sliceDirectIntInt")
      protected JSDynamicObject sliceDirect(JSDynamicObject thisObj, Object begin0, Object end0) {
         int len = JSArrayBuffer.getDirectByteLength(thisObj);
         int begin = this.getStart(begin0, len);
         int end = this.getEnd(end0, len);
         return this.sliceDirectIntInt(thisObj, begin, end);
      }

      @Specialization(guards = "isJSInteropArrayBuffer(thisObj)")
      protected Object sliceInterop(
         JSDynamicObject thisObj,
         Object begin0,
         Object end0,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("srcBufferLib") InteropLibrary srcBufferLib,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("dstBufferLib") InteropLibrary dstBufferLib
      ) {
         Object interopBuffer = JSArrayBuffer.getInteropBuffer(thisObj);
         int length = ConstructorBuiltins.ConstructArrayBufferNode.getBufferSizeSafe(interopBuffer, srcBufferLib, this.errorBranch);
         int begin = this.getStart(begin0, length);
         int end = this.getEnd(end0, length);
         int clampedBegin = clampIndex(begin, 0, length);
         int clampedEnd = clampIndex(end, clampedBegin, length);
         int newLen = Math.max(clampedEnd - clampedBegin, 0);
         Object resObj = this.constructNewArrayBuffer(thisObj, newLen);
         this.checkErrors(resObj, thisObj, newLen, this.getContext().isOptionDirectByteBuffer());
         this.copyInteropBufferElements(thisObj, resObj, clampedBegin, newLen, srcBufferLib, dstBufferLib);
         return resObj;
      }

      private void copyInteropBufferElements(
         Object srcBuffer, Object dstBuffer, int srcBufferOffset, int len, InteropLibrary srcBufferLib, InteropLibrary dstBufferLib
      ) {
         try {
            for (int i = 0; i < len; i++) {
               dstBufferLib.writeBufferByte(dstBuffer, i, srcBufferLib.readBufferByte(srcBuffer, srcBufferOffset + i));
            }
         } catch (InvalidBufferOffsetException | UnsupportedMessageException var8) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorInteropException(dstBuffer, var8, "buffer access", null);
         }
      }

      @Specialization(guards = {"!isJSSharedArrayBuffer(thisObj)", "hasBufferElements(thisObj, srcBufferLib)"})
      protected Object sliceTruffleBuffer(
         Object thisObj,
         Object begin0,
         Object end0,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("srcBufferLib") InteropLibrary srcBufferLib,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("dstBufferLib") InteropLibrary dstBufferLib
      ) {
         return this.sliceInterop(JSArrayBuffer.createInteropArrayBuffer(this.getContext(), this.getRealm(), thisObj), begin0, end0, srcBufferLib, dstBufferLib);
      }

      @Fallback
      protected static JSDynamicObject error(Object thisObj, Object begin0, Object end0) {
         throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
      }

      static boolean hasBufferElements(Object buffer, InteropLibrary interop) {
         return interop.hasBufferElements(buffer);
      }
   }
}
