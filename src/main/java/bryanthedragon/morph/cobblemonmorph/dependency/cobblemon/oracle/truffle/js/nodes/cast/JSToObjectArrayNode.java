package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ImportStatic(JSConfig.class)
public abstract class JSToObjectArrayNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final boolean nullOrUndefinedAsEmptyArray;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected JSToObjectArrayNode(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
      this.context = Objects.requireNonNull(context);
      this.nullOrUndefinedAsEmptyArray = nullOrUndefinedAsEmptyArray;
   }

   public abstract Object[] executeObjectArray(Object value);

   public static JSToObjectArrayNode create(JSContext context) {
      return create(context, false);
   }

   public static JSToObjectArrayNode create(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
      return JSToObjectArrayNodeGen.create(context, nullOrUndefinedAsEmptyArray);
   }

   public static JavaScriptNode create(JSContext context, JavaScriptNode operand) {
      class Unary extends JSUnaryNode {
         @Node.Child
         private JSToObjectArrayNode toObjectArray = JSToObjectArrayNode.create(context);

         Unary(JavaScriptNode operandNode) {
            super(operandNode);
         }

         @Override
         public Object execute(VirtualFrame frame, Object operandValue) {
            return this.toObjectArray.executeObjectArray(operandValue);
         }

         @Override
         public Object execute(VirtualFrame frame) {
            return this.execute(frame, this.operandNode.execute(frame));
         }

         @Override
         protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new Unary(cloneUninitialized(this.getOperand(), materializedTags));
         }
      }

      return new Unary(operand);
   }

   @Specialization
   protected Object[] toArray(JSObject obj, @Cached("create(context)") JSGetLengthNode getLengthNode, @Cached("create(context)") ReadElementNode readNode) {
      long len = getLengthNode.executeLong(obj);
      if (len > this.context.getContextOptions().getMaxApplyArgumentLength()) {
         this.errorBranch.enter();
         throw Errors.createRangeErrorTooManyArguments();
      } else {
         int iLen = (int)len;

         assert JSRuntime.longIsRepresentableAsInt(len);

         Object[] arr = new Object[iLen];

         for (int index = 0; index < iLen; index++) {
            Object value = readNode.executeWithTargetAndIndex(obj, index);
            arr[index] = value;
         }

         return arr;
      }
   }

   @Specialization(guards = "isUndefined(value)")
   protected Object[] doUndefined(Object value) {
      return this.emptyArrayOrObjectError(value);
   }

   @Specialization(guards = "isJSNull(value)")
   protected Object[] doNull(Object value) {
      return this.emptyArrayOrObjectError(value);
   }

   @Specialization
   protected Object[] toArrayString(TruffleString value) {
      return this.notAnObjectError(value);
   }

   @Specialization
   protected Object[] toArrayInt(int value) {
      return this.notAnObjectError(value);
   }

   @Specialization
   protected Object[] toArrayDouble(double value) {
      return this.notAnObjectError(value);
   }

   @Specialization
   protected Object[] toArrayBoolean(boolean value) {
      return this.notAnObjectError(value);
   }

   private Object[] emptyArrayOrObjectError(Object value) {
      return this.nullOrUndefinedAsEmptyArray ? ScriptArray.EMPTY_OBJECT_ARRAY : this.notAnObjectError(value);
   }

   private Object[] notAnObjectError(Object value) {
      this.errorBranch.enter();
      if (this.context.isOptionNashornCompatibilityMode()) {
         throw Errors.createTypeError("Function.prototype.apply expects an Array for second argument");
      } else {
         throw Errors.createTypeErrorNotAnObject(value);
      }
   }

   @Specialization
   protected Object[] passArray(Object[] array) {
      if (array.length > this.context.getContextOptions().getMaxApplyArgumentLength()) {
         this.errorBranch.enter();
         throw Errors.createRangeErrorTooManyArguments();
      } else {
         return array;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization(guards = "isList(value)")
   protected Object[] doList(Object value) {
      List<?> list = (List<?>)value;
      if (list.size() > this.context.getContextOptions().getMaxApplyArgumentLength()) {
         this.errorBranch.enter();
         throw Errors.createRangeErrorTooManyArguments();
      } else {
         return list.toArray();
      }
   }

   @Specialization(guards = "isForeignObject(obj)", limit = "InteropLibraryLimit")
   protected Object[] doForeignObject(
      Object obj,
      @CachedLibrary("obj") InteropLibrary interop,
      @Cached("create()") BranchProfile hasPropertiesBranch,
      @Cached("create()") ImportValueNode foreignConvertNode
   ) {
      try {
         if (!interop.hasArrayElements(obj)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("foreign Object reports not to have a SIZE");
         } else {
            long len = interop.getArraySize(obj);
            if (len > this.context.getContextOptions().getMaxApplyArgumentLength()) {
               this.errorBranch.enter();
               throw Errors.createRangeErrorTooManyArguments();
            } else {
               int iLen = (int)len;
               Object[] arr = new Object[iLen];
               if (len > 0L) {
                  hasPropertiesBranch.enter();

                  for (int i = 0; i < iLen; i++) {
                     arr[i] = foreignConvertNode.executeWithTarget(interop.readArrayElement(obj, i));
                  }
               }

               return arr;
            }
         }
      } catch (UnsupportedMessageException | InvalidArrayIndexException var10) {
         this.errorBranch.enter();
         throw Errors.createTypeErrorNotAnObject(obj);
      }
   }

   @Fallback
   protected Object[] doFallback(Object value) {
      assert !JSRuntime.isObject(value);

      return this.notAnObjectError(value);
   }
}
