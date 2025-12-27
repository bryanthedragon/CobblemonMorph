package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;

@ImportStatic(TRegexGuards.class)
public abstract class InputRegionMatchesNode extends Node {
   public static InputRegionMatchesNode create() {
      return InputRegionMatchesNodeGen.create();
   }

   public abstract boolean execute(Object input, int fromIndex1, Object match, int fromIndex2, int length, Object mask, Encodings.Encoding encoding);

   @Specialization(guards = "mask == null")
   public boolean doBytes(byte[] input, int fromIndex1, byte[] match, int fromIndex2, int length, Object mask, Encodings.Encoding encoding) {
      return ArrayUtils.regionEqualsWithOrMask(input, fromIndex1, match, fromIndex2, length, null);
   }

   @Specialization(guards = "mask != null")
   public boolean doBytesMask(byte[] input, int fromIndex1, byte[] match, int fromIndex2, int length, byte[] mask, Encodings.Encoding encoding) {
      return ArrayUtils.regionEqualsWithOrMask(input, fromIndex1, match, fromIndex2, length, mask);
   }

   @Specialization(guards = "mask == null")
   public boolean doString(String input, int fromIndex1, String match, int fromIndex2, int length, Object mask, Encodings.Encoding encoding) {
      return input.regionMatches(fromIndex1, match, fromIndex2, length);
   }

   @Specialization(guards = "mask != null")
   public boolean doJavaStringMask(String input, int fromIndex1, String match, int fromIndex2, int length, String mask, Encodings.Encoding encoding) {
      return ArrayUtils.regionEqualsWithOrMask(input, fromIndex1, match, fromIndex2, length, mask);
   }

   @Specialization(guards = "mask == null")
   public boolean doTString(
      TruffleString input,
      int fromIndex1,
      TruffleString match,
      int fromIndex2,
      int length,
      Object mask,
      Encodings.Encoding encoding,
      @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode
   ) {
      int fromByteIndexA = fromIndex1 << encoding.getStride();
      int fromByteIndexB = fromIndex2 << encoding.getStride();
      int byteLength = length << encoding.getStride();
      return input.byteLength(encoding.getTStringEncoding()) >= fromByteIndexA + byteLength
         && regionEqualsNode.execute(input, fromByteIndexA, match, fromByteIndexB, byteLength, encoding.getTStringEncoding());
   }

   @Specialization(guards = "mask != null")
   public boolean doTStringMask(
      TruffleString input,
      int fromIndex1,
      TruffleString match,
      int fromIndex2,
      int length,
      TruffleString.WithMask mask,
      Encodings.Encoding encoding,
      @Cached TruffleString.RegionEqualByteIndexNode regionEqualsNode
   ) {
      int fromByteIndexA = fromIndex1 << encoding.getStride();
      int fromByteIndexB = fromIndex2 << encoding.getStride();
      int byteLength = length << encoding.getStride();
      return input.byteLength(encoding.getTStringEncoding()) >= fromByteIndexA + byteLength
         && regionEqualsNode.execute(input, fromByteIndexA, mask, fromByteIndexB, byteLength, encoding.getTStringEncoding());
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask == null"})
   public boolean doTruffleObjBytes(
      Object input,
      int fromIndex1,
      byte[] match,
      int fromIndex2,
      int length,
      Object mask,
      Encodings.Encoding encoding,
      @Cached InputLengthNode lengthNode,
      @Cached InputReadNode charAtNode
   ) {
      return regionMatchesTruffleObj(input, fromIndex1, match, fromIndex2, length, null, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask != null"})
   public boolean doTruffleObjBytesMask(
      Object input,
      int fromIndex1,
      byte[] match,
      int fromIndex2,
      int length,
      byte[] mask,
      Encodings.Encoding encoding,
      @Cached InputLengthNode lengthNode,
      @Cached InputReadNode charAtNode
   ) {
      assert match.length == mask.length;

      return regionMatchesTruffleObj(input, fromIndex1, match, fromIndex2, length, mask, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask == null"})
   public boolean doTruffleObjString(
      Object input,
      int fromIndex1,
      String match,
      int fromIndex2,
      int length,
      Object mask,
      Encodings.Encoding encoding,
      @Cached InputLengthNode lengthNode,
      @Cached InputReadNode charAtNode
   ) {
      return regionMatchesTruffleObj(input, fromIndex1, match, fromIndex2, length, null, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "mask != null"})
   public boolean doTruffleObjStringMask(
      Object input,
      int fromIndex1,
      String match,
      int fromIndex2,
      int length,
      String mask,
      Encodings.Encoding encoding,
      @Cached InputLengthNode lengthNode,
      @Cached InputReadNode charAtNode
   ) {
      assert match.length() == mask.length();

      return regionMatchesTruffleObj(input, fromIndex1, match, fromIndex2, length, mask, encoding, lengthNode, charAtNode);
   }

   @Specialization(guards = {"neitherByteArrayNorString(input)", "neitherByteArrayNorString(match)", "mask == null"})
   public boolean doTruffleObjTruffleObj(
      Object input,
      int fromIndex1,
      Object match,
      int fromIndex2,
      int length,
      Object mask,
      Encodings.Encoding encoding,
      @Cached InputLengthNode lengthNode1,
      @Cached InputReadNode charAtNode1,
      @Cached InputLengthNode lengthNode2,
      @Cached InputReadNode charAtNode2
   ) {
      if (fromIndex1 + length <= lengthNode1.execute(input, encoding) && fromIndex2 + length <= lengthNode2.execute(match, encoding)) {
         for (int i = 0; i < length; i++) {
            if (charAtNode1.execute(input, fromIndex1 + i, encoding) != charAtNode2.execute(match, fromIndex2 + i, encoding)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean regionMatchesTruffleObj(
      Object input,
      int fromIndex1,
      byte[] match,
      int fromIndex2,
      int length,
      byte[] mask,
      Encodings.Encoding encoding,
      InputLengthNode lengthNode,
      InputReadNode charAtNode
   ) {
      if (fromIndex1 + length <= lengthNode.execute(input, encoding) && fromIndex2 + length <= match.length) {
         for (int i = 0; i < length; i++) {
            if (InputReadNode.readWithMask(input, fromIndex1 + i, mask, i, encoding, charAtNode) != Byte.toUnsignedInt(match[fromIndex2 + i])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean regionMatchesTruffleObj(
      Object input,
      int fromIndex1,
      String match,
      int fromIndex2,
      int length,
      String mask,
      Encodings.Encoding encoding,
      InputLengthNode lengthNode,
      InputReadNode charAtNode
   ) {
      if (fromIndex1 + length <= lengthNode.execute(input, encoding) && fromIndex2 + length <= match.length()) {
         for (int i = 0; i < length; i++) {
            if (InputReadNode.readWithMask(input, fromIndex1 + i, mask, i, encoding, charAtNode) != match.charAt(fromIndex2 + i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
