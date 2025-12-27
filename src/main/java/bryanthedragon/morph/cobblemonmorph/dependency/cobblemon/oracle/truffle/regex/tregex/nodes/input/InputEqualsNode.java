package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.Arrays;

@ImportStatic(TRegexGuards.class)
public abstract class InputEqualsNode extends Node {
   public static InputEqualsNode create() {
      return InputEqualsNodeGen.create();
   }

   public abstract boolean execute(Object input, Object string, Object mask, Encodings.Encoding encoding);

   @Specialization(guards = "mask == null")
   public boolean doBytes(byte[] input, byte[] string, Object mask, Encodings.Encoding encoding) {
      return Arrays.equals(input, string);
   }

   @Specialization(guards = "mask != null")
   public boolean doBytesMask(byte[] input, byte[] string, byte[] mask, Encodings.Encoding encoding) {
      return input.length == string.length && ArrayUtils.regionEqualsWithOrMask(input, 0, string, 0, mask.length, mask);
   }

   @Specialization(guards = "mask == null")
   public boolean doString(String input, String string, Object mask, Encodings.Encoding encoding) {
      return input.equals(string);
   }

   @Specialization(guards = "mask != null")
   public boolean doStringMask(String input, String string, String mask, Encodings.Encoding encoding) {
      return input.length() == string.length() && ArrayUtils.regionEqualsWithOrMask(input, 0, string, 0, mask.length(), mask);
   }

   @Specialization(guards = "mask == null")
   public boolean doTString(
      TruffleString input, TruffleString string, Object mask, Encodings.Encoding encoding, @Cached TruffleString.RegionEqualByteIndexNode equalsNode
   ) {
      int len1 = input.byteLength(encoding.getTStringEncoding());
      int len2 = string.byteLength(encoding.getTStringEncoding());
      return len1 == len2 && equalsNode.execute(input, 0, string, 0, len2, encoding.getTStringEncoding());
   }

   @Specialization(guards = "mask != null")
   public boolean doTStringMask(
      TruffleString input,
      TruffleString string,
      TruffleString.WithMask mask,
      Encodings.Encoding encoding,
      @Cached TruffleString.RegionEqualByteIndexNode equalsNode
   ) {
      int len1 = input.byteLength(encoding.getTStringEncoding());
      int len2 = string.byteLength(encoding.getTStringEncoding());
      return len1 == len2 && equalsNode.execute(input, 0, mask, 0, len2, encoding.getTStringEncoding());
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask == null"})
   public boolean doTruffleObjBytes(
      Object input, byte[] string, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode
   ) {
      return equalsTruffleObj(input, string, null, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask != null"})
   public boolean doTruffleObjBytesMask(
      Object input, byte[] string, byte[] mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode
   ) {
      return equalsTruffleObj(input, string, mask, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask == null"})
   public boolean doTruffleObjString(
      Object input, String string, Object mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode
   ) {
      return equalsTruffleObj(input, string, null, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask != null"})
   public boolean doTruffleObjStringMask(
      Object input, String string, String mask, Encodings.Encoding encoding, @Cached InputLengthNode lengthNode, @Cached InputReadNode charAtNode
   ) {
      return equalsTruffleObj(input, string, mask, encoding, lengthNode, charAtNode);
   }

   private static boolean equalsTruffleObj(
      Object input, String string, String mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode
   ) {
      if (lengthNode.execute(input, encoding) != string.length()) {
         return false;
      } else {
         for (int i = 0; i < string.length(); i++) {
            if (InputReadNode.readWithMask(input, i, mask, i, encoding, charAtNode) != string.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean equalsTruffleObj(
      Object input, byte[] string, byte[] mask, Encodings.Encoding encoding, InputLengthNode lengthNode, InputReadNode charAtNode
   ) {
      if (lengthNode.execute(input, encoding) != string.length) {
         return false;
      } else {
         for (int i = 0; i < string.length; i++) {
            if (InputReadNode.readWithMask(input, i, mask, i, encoding, charAtNode) != Byte.toUnsignedInt(string[i])) {
               return false;
            }
         }

         return true;
      }
   }
}
