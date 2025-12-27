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
public abstract class InputIndexOfNode extends Node {
   public static InputIndexOfNode create() {
      return InputIndexOfNodeGen.create();
   }

   public abstract int execute(Object input, int fromIndex, int maxIndex, Object chars, Encodings.Encoding encoding);

   @Specialization
   public int doBytes(byte[] input, int fromIndex, int maxIndex, byte[] bytes, Encodings.Encoding encoding) {
      return ArrayUtils.indexOf(input, fromIndex, maxIndex, bytes);
   }

   @Specialization
   public int doChars(String input, int fromIndex, int maxIndex, char[] chars, Encodings.Encoding encoding) {
      return ArrayUtils.indexOf(input, fromIndex, maxIndex, chars);
   }

   @Specialization
   public int doTStringBytes(
      TruffleString input,
      int fromIndex,
      int maxIndex,
      byte[] bytes,
      Encodings.Encoding encoding,
      @Cached TruffleString.ByteIndexOfAnyByteNode indexOfRawValueNode
   ) {
      return indexOfRawValueNode.execute(input, fromIndex, maxIndex, bytes, encoding.getTStringEncoding());
   }

   @Specialization
   public int doTStringChars(
      TruffleString input,
      int fromIndex,
      int maxIndex,
      char[] chars,
      Encodings.Encoding encoding,
      @Cached TruffleString.CharIndexOfAnyCharUTF16Node indexOfRawValueNode
   ) {
      assert encoding == Encodings.UTF_16 || encoding == Encodings.UTF_16_RAW;

      return indexOfRawValueNode.execute(input, fromIndex, maxIndex, chars);
   }

   @Specialization
   public int doTStringInts(
      TruffleString input,
      int fromIndex,
      int maxIndex,
      int[] ints,
      Encodings.Encoding encoding,
      @Cached TruffleString.IntIndexOfAnyIntUTF32Node indexOfRawValueNode
   ) {
      assert encoding == Encodings.UTF_32;

      return indexOfRawValueNode.execute(input, fromIndex, maxIndex, ints);
   }

   @Specialization(guards = "neitherByteArrayNorString(input)")
   public int doTruffleObjBytes(Object input, int fromIndex, int maxIndex, byte[] bytes, Encodings.Encoding encoding, @Cached InputReadNode charAtNode) {
      for (int i = fromIndex; i < maxIndex; i++) {
         int c = charAtNode.execute(input, i, encoding);

         for (byte v : bytes) {
            if (c == Byte.toUnsignedInt(v)) {
               return i;
            }
         }
      }

      return -1;
   }

   @Specialization(guards = "neitherByteArrayNorString(input)")
   public int doTruffleObjChars(Object input, int fromIndex, int maxIndex, char[] chars, Encodings.Encoding encoding, @Cached InputReadNode charAtNode) {
      for (int i = fromIndex; i < maxIndex; i++) {
         int c = charAtNode.execute(input, i, encoding);

         for (char v : chars) {
            if (c == v) {
               return i;
            }
         }
      }

      return -1;
   }
}
