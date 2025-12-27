package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.InternalSlotId;
import java.util.Objects;
import java.util.OptionalInt;

public final class JSFrameUtil {
   public static final MaterializedFrame NULL_MATERIALIZED_FRAME = Truffle.getRuntime().createMaterializedFrame(JSArguments.createNullArguments());
   public static final Object DEFAULT_VALUE = Undefined.instance;
   private static final TruffleString THIS_SLOT_ID = Strings.constant("<this>");
   private static final Class<? extends MaterializedFrame> MATERIALIZED_FRAME_CLASS = (Class<? extends MaterializedFrame>)NULL_MATERIALIZED_FRAME.getClass();
   private static final int IS_LET = 1;
   private static final int IS_CONST = 2;
   private static final int HAS_TDZ = 3;
   private static final int IS_HOISTABLE_DECLARATION = 256;
   private static final int IS_IMPORT_BINDING = 16384;
   private static final int IS_PRIVATE_NAME = 131072;
   private static final int IS_PRIVATE_NAME_STATIC = 262144;
   private static final int IS_PRIVATE_METHOD_OR_ACCESSOR = 1572864;
   private static final int IS_PARAM = 16;
   private static final int IS_ARGUMENTS = 2097152;
   private static final int IS_CLOSED_OVER = 8388608;
   public static final int IS_HOISTED_FROM_BLOCK = Integer.MIN_VALUE;
   public static final int SYMBOL_FLAG_MASK = -2135015149;

   private JSFrameUtil() {
   }

   public static Object getThisObj(Frame frame) {
      return JSArguments.getThisObject(frame.getArguments());
   }

   public static JSFunctionObject getFunctionObject(Frame frame) {
      return (JSFunctionObject.Unbound)JSArguments.getFunctionObject(frame.getArguments());
   }

   public static Object getFunctionObjectNoCast(Frame frame) {
      return JSArguments.getFunctionObject(frame.getArguments());
   }

   public static Object[] getArgumentsArray(Frame frame) {
      return JSArguments.extractUserArguments(frame.getArguments());
   }

   public static int getFlags(JSFrameSlot frameSlot) {
      return frameSlot.getFlags();
   }

   public static int getFlags(FrameDescriptor desc, int index) {
      return getFlagsFromInfo(desc.getSlotInfo(index));
   }

   public static int getFlagsFromInfo(Object info) {
      return info instanceof Integer ? (Integer)info : 0;
   }

   public static boolean hasTemporalDeadZone(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 3) != 0;
   }

   public static boolean hasTemporalDeadZone(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & 3) != 0;
   }

   public static boolean needsTemporalDeadZoneCheck(JSFrameSlot frameSlot, int frameLevel) {
      return hasTemporalDeadZone(frameSlot) && frameLevel != 0;
   }

   public static boolean isConst(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 2) != 0;
   }

   public static boolean isLet(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 1) != 0;
   }

   public static boolean isConst(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & 2) != 0;
   }

   public static boolean isLet(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & 1) != 0;
   }

   public static boolean isHoistable(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & 256) != 0;
   }

   public static boolean isImportBinding(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 16384) != 0;
   }

   public static boolean isImportBinding(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & 16384) != 0;
   }

   public static boolean isPrivateName(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 131072) != 0;
   }

   public static boolean needsPrivateBrandCheck(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 1572864) != 0;
   }

   public static boolean isPrivateNameStatic(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 262144) != 0;
   }

   public static boolean isParam(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 16) != 0;
   }

   public static boolean isArguments(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 2097152) != 0;
   }

   public static boolean isClosedOver(JSFrameSlot frameSlot) {
      return (getFlags(frameSlot) & 8388608) != 0;
   }

   public static boolean isHoistedFromBlock(FrameDescriptor desc, int index) {
      return (getFlags(desc, index) & -2147483648) != 0;
   }

   public static MaterializedFrame getParentFrame(Frame frame) {
      return JSArguments.getEnclosingFrame(frame.getArguments());
   }

   public static MaterializedFrame castMaterializedFrame(Object frame) {
      return CompilerDirectives.castExact(Objects.requireNonNull(frame), MATERIALIZED_FRAME_CLASS);
   }

   public static boolean isInternal(FrameDescriptor desc, int index) {
      return isInternalIdentifier(desc.getSlotName(index));
   }

   public static boolean isInternalIdentifier(Object identifier) {
      CompilerAsserts.neverPartOfCompilation();
      if (identifier instanceof TruffleString) {
         TruffleString name = (TruffleString)identifier;
         return Strings.startsWith(name, Strings.COLON)
            ? true
            : Strings.startsWith(name, Strings.ANGLE_BRACKET_OPEN) && Strings.endsWith(name, Strings.ANGLE_BRACKET_CLOSE);
      } else {
         return identifier instanceof InternalSlotId ? true : true;
      }
   }

   public static TruffleString getPublicName(Object identifier) {
      CompilerAsserts.neverPartOfCompilation();
      if (identifier instanceof TruffleString) {
         TruffleString name = (TruffleString)identifier;
         if (Strings.startsWith(name, Strings.COLON)) {
            return Strings.lazySubstring(name, 1);
         } else {
            return Strings.startsWith(name, Strings.ANGLE_BRACKET_OPEN) && Strings.endsWith(name, Strings.ANGLE_BRACKET_CLOSE)
               ? Strings.lazySubstring(name, 1, Strings.length(name) - 2)
               : name;
         }
      } else {
         return Strings.fromObject(identifier);
      }
   }

   public static boolean isThisSlot(FrameDescriptor desc, int index) {
      return isThisSlotIdentifier(desc.getSlotName(index));
   }

   public static boolean isThisSlotIdentifier(Object identifier) {
      return THIS_SLOT_ID.equals(identifier);
   }

   private static int findFrameSlotIndex(FrameDescriptor frameDescriptor, Object identifier) {
      CompilerAsserts.neverPartOfCompilation();

      for (int i = 0; i < frameDescriptor.getNumberOfSlots(); i++) {
         if (identifier.equals(frameDescriptor.getSlotName(i))) {
            return i;
         }
      }

      return -1;
   }

   public static int findRequiredFrameSlotIndex(FrameDescriptor frameDescriptor, Object identifier) {
      int index = findFrameSlotIndex(frameDescriptor, identifier);

      assert index >= 0 : identifier + " not found in " + frameDescriptor;

      return index;
   }

   public static OptionalInt findOptionalFrameSlotIndex(FrameDescriptor frameDescriptor, Object identifier) {
      int index = findFrameSlotIndex(frameDescriptor, identifier);
      return index >= 0 ? OptionalInt.of(index) : OptionalInt.empty();
   }
}
