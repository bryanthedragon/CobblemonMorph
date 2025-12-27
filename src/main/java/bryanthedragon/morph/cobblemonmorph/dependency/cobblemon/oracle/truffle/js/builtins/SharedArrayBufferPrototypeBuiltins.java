package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.nio.ByteBuffer;

public final class SharedArrayBufferPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<ArrayBufferPrototypeBuiltins.ArrayBufferPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new SharedArrayBufferPrototypeBuiltins();

   protected SharedArrayBufferPrototypeBuiltins() {
      super(JSSharedArrayBuffer.PROTOTYPE_NAME, ArrayBufferPrototypeBuiltins.ArrayBufferPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ArrayBufferPrototypeBuiltins.ArrayBufferPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case byteLength:
            return SharedArrayBufferPrototypeBuiltinsFactory.ByteLengthGetterNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case slice:
            return SharedArrayBufferPrototypeBuiltinsFactory.JSSharedArrayBufferSliceNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class ByteLengthGetterNode extends JSBuiltinNode {
      public ByteLengthGetterNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSSharedArrayBuffer(thisObj)")
      protected static int sharedArrayBuffer(Object thisObj) {
         return JSArrayBuffer.getDirectByteLength(thisObj);
      }

      @Specialization(guards = "!isJSSharedArrayBuffer(thisObj)")
      protected static int error(Object thisObj) {
         throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
      }
   }

   public abstract static class JSSharedArrayBufferSliceNode extends ArrayBufferPrototypeBuiltins.JSArrayBufferAbstractSliceNode {
      private final BranchProfile errorBranch = BranchProfile.create();

      public JSSharedArrayBufferSliceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      private JSDynamicObject constructNewSharedArrayBuffer(JSDynamicObject thisObj, int newLen) {
         JSDynamicObject defaultConstructor = this.getRealm().getSharedArrayBufferConstructor();
         JSDynamicObject constr = this.getArraySpeciesConstructorNode().speciesConstructor(thisObj, defaultConstructor);
         return (JSDynamicObject)this.getArraySpeciesConstructorNode().construct(constr, newLen);
      }

      private void checkErrors(JSDynamicObject resObj, JSDynamicObject thisObj, int newLen) {
         if (!JSSharedArrayBuffer.isJSSharedArrayBuffer(resObj)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("SharedArrayBuffer expected");
         } else if (resObj == thisObj) {
            this.errorBranch.enter();
            throw Errors.createTypeError("SameValue(new, O) is forbidden");
         } else if (JSSharedArrayBuffer.getDirectByteBuffer(resObj).capacity() < newLen) {
            this.errorBranch.enter();
            throw Errors.createTypeError("insufficient length constructed");
         }
      }

      @Specialization(guards = "isJSSharedArrayBuffer(thisObj)")
      protected JSDynamicObject sliceSharedIntInt(JSDynamicObject thisObj, int begin, int end) {
         ByteBuffer byteBuffer = JSSharedArrayBuffer.getDirectByteBuffer(thisObj);
         int byteLength = JSArrayBuffer.getDirectByteLength(thisObj);
         int clampedBegin = clampIndex(begin, 0, byteLength);
         int clampedEnd = clampIndex(end, clampedBegin, byteLength);
         int newLen = clampedEnd - clampedBegin;
         JSDynamicObject resObj = this.constructNewSharedArrayBuffer(thisObj, newLen);
         this.checkErrors(resObj, thisObj, newLen);
         ByteBuffer resBuffer = JSArrayBuffer.getDirectByteBuffer(resObj);
         Boundaries.byteBufferPutSlice(resBuffer, 0, byteBuffer, clampedBegin, clampedEnd);
         return resObj;
      }

      @Specialization(guards = "isJSSharedArrayBuffer(thisObj)", replaces = "sliceSharedIntInt")
      protected JSDynamicObject sliceShared(JSDynamicObject thisObj, Object begin0, Object end0) {
         int len = JSSharedArrayBuffer.getDirectByteBuffer(thisObj).capacity();
         int begin = this.getStart(begin0, len);
         int end = this.getEnd(end0, len);
         return this.sliceSharedIntInt(thisObj, begin, end);
      }

      @Specialization(guards = "!isJSSharedArrayBuffer(thisObj)")
      protected static JSDynamicObject error(Object thisObj, Object begin0, Object end0) {
         throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
      }
   }
}
