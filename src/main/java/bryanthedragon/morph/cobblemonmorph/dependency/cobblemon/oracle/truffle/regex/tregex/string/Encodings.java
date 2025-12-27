package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.SequentialMatchers;

public final class Encodings {
   private static final CodePointSet FULL_UNICODE_SET = CodePointSet.createNoDedup(0, 1114111);
   public static final Encodings.Encoding UTF_8 = new Encodings.Encoding.UTF8();
   public static final Encodings.Encoding UTF_16 = new Encodings.Encoding.UTF16();
   public static final Encodings.Encoding UTF_32 = new Encodings.Encoding.UTF32();
   public static final Encodings.Encoding UTF_16_RAW = new Encodings.Encoding.UTF16Raw();
   public static final Encodings.Encoding LATIN_1 = new Encodings.Encoding.Latin1(TruffleString.Encoding.ISO_8859_1);
   public static final Encodings.Encoding BYTES = new Encodings.Encoding.Latin1(TruffleString.Encoding.BYTES);
   public static final Encodings.Encoding ASCII = new Encodings.Encoding.Ascii();
   public static final String[] ALL_NAMES = new String[]{
      UTF_8.getName(), UTF_16.getName(), UTF_16_RAW.getName(), UTF_32.getName(), ASCII.getName(), LATIN_1.getName(), "BYTES"
   };

   public static Encodings.Encoding getEncoding(String name) {
      switch (name) {
         case "UTF-8":
            return UTF_8;
         case "UTF-16":
            return UTF_16;
         case "UTF-32":
            return UTF_32;
         case "UTF-16-RAW":
            return UTF_16_RAW;
         case "BYTES":
            return BYTES;
         case "LATIN-1":
            return LATIN_1;
         default:
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere("Unknown Encoding \"" + name + "\"");
      }
   }

   public abstract static class Encoding {
      public abstract String getName();

      public abstract TruffleString.Encoding getTStringEncoding();

      public int getStride() {
         return 0;
      }

      public int getMinValue() {
         return 0;
      }

      public abstract int getMaxValue();

      public abstract CodePointSet getFullSet();

      public abstract int getEncodedSize(int codepoint);

      public abstract boolean isFixedCodePointWidth(CodePointSet set);

      public abstract boolean isUnicode();

      public abstract AbstractStringBuffer createStringBuffer(int capacity);

      public abstract DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet loopCPS);

      public abstract int getNumberOfCodeRanges();

      public SequentialMatchers.Builder createMatchersBuilder() {
         return new SequentialMatchers.Builder(this.getNumberOfCodeRanges());
      }

      public abstract void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer);

      public abstract SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder);

      public static final class Ascii extends Encodings.Encoding {
         @Override
         public String getName() {
            return "ASCII";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return TruffleString.Encoding.US_ASCII;
         }

         @Override
         public int getMaxValue() {
            return 127;
         }

         @Override
         public CodePointSet getFullSet() {
            return Constants.ASCII_RANGE;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            return 1;
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            return true;
         }

         @Override
         public boolean isUnicode() {
            return false;
         }

         @Override
         public AbstractStringBuffer createStringBuffer(int capacity) {
            return new StringBufferASCII(capacity);
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            return new DFAStateNode.IndexOfAnyByteCall(cps.inverseToByteArray(this));
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 1;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            matchersBuilder.getBuffer(0).set(i, CharMatchers.createMatcher(cps, compilationBuffer));
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.SimpleSequentialMatchers(matchersBuilder.materialize(0), matchersBuilder.getNoMatchSuccessor());
         }
      }

      public static final class Latin1 extends Encodings.Encoding {
         private TruffleString.Encoding tsEncoding;

         private Latin1(TruffleString.Encoding tsEncoding) {
            this.tsEncoding = tsEncoding;
         }

         @Override
         public String getName() {
            return "LATIN-1";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return this.tsEncoding;
         }

         @Override
         public int getMaxValue() {
            return 255;
         }

         @Override
         public CodePointSet getFullSet() {
            return Constants.BYTE_RANGE;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            return 1;
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            return true;
         }

         @Override
         public boolean isUnicode() {
            return false;
         }

         public StringBufferLATIN1 createStringBuffer(int capacity) {
            return new StringBufferLATIN1(capacity);
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            return new DFAStateNode.IndexOfAnyByteCall(cps.inverseToByteArray(this));
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 1;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            matchersBuilder.getBuffer(0).set(i, CharMatchers.createMatcher(cps, compilationBuffer));
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.SimpleSequentialMatchers(matchersBuilder.materialize(0), matchersBuilder.getNoMatchSuccessor());
         }
      }

      public static final class UTF16 extends Encodings.Encoding {
         private UTF16() {
         }

         @Override
         public String getName() {
            return "UTF-16";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return TruffleString.Encoding.UTF_16;
         }

         @Override
         public int getStride() {
            return 1;
         }

         @Override
         public int getMaxValue() {
            return 1114111;
         }

         @Override
         public CodePointSet getFullSet() {
            return Encodings.FULL_UNICODE_SET;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            return codepoint < 65536 ? 1 : 2;
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            if (set.isEmpty()) {
               return true;
            } else {
               int min = set.getMin();
               int max = set.getMax();
               return min >= 65536 || max <= 65536;
            }
         }

         @Override
         public boolean isUnicode() {
            return true;
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            if (cps.inverseGetMax(this) <= 65535) {
               char[] indexOfChars = cps.inverseToCharArray(this);

               for (char c : indexOfChars) {
                  if (Constants.SURROGATES.contains(c)) {
                     return null;
                  }
               }

               return new DFAStateNode.IndexOfAnyCharCall(indexOfChars);
            } else if (cps.inverseValueCount(this) == 1) {
               StringBufferUTF16 sb = this.createStringBuffer(2);
               sb.append(cps.inverseGetMin(this));
               return new DFAStateNode.IndexOfStringCall(sb.materialize(), null);
            } else {
               return null;
            }
         }

         public static boolean isHighSurrogate(int c, boolean forward) {
            return forward ? isHighSurrogate(c) : isLowSurrogate(c);
         }

         public static boolean isLowSurrogate(int c, boolean forward) {
            return forward ? isLowSurrogate(c) : isHighSurrogate(c);
         }

         public static boolean isHighSurrogate(int c) {
            assert c <= 65535;

            return c >> 10 == 54;
         }

         public static boolean isLowSurrogate(int c) {
            assert c <= 65535;

            return c >> 10 == 55;
         }

         public StringBufferUTF16 createStringBuffer(int capacity) {
            return new StringBufferUTF16(capacity);
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 4;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            matchersBuilder.createSplitMatcher(
               i,
               cps,
               compilationBuffer,
               Constants.ASCII_RANGE,
               Constants.BYTE_RANGE,
               Constants.BMP_RANGE_WITHOUT_LATIN1,
               Constants.ASTRAL_SYMBOLS_AND_LONE_SURROGATES
            );
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.UTF16Or32SequentialMatchers(
               matchersBuilder.materialize(0),
               matchersBuilder.materialize(1),
               matchersBuilder.materialize(2),
               matchersBuilder.materialize(3),
               matchersBuilder.getNoMatchSuccessor()
            );
         }
      }

      public static final class UTF16Raw extends Encodings.Encoding {
         private static final CodePointSet UTF16_RAW_FULL_SET = CodePointSet.createNoDedup(0, 65535);

         private UTF16Raw() {
         }

         @Override
         public String getName() {
            return "UTF-16-RAW";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return TruffleString.Encoding.UTF_16;
         }

         @Override
         public int getStride() {
            return 1;
         }

         @Override
         public int getMaxValue() {
            return 65535;
         }

         @Override
         public CodePointSet getFullSet() {
            return UTF16_RAW_FULL_SET;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            return codepoint < 65536 ? 1 : 2;
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            return true;
         }

         @Override
         public boolean isUnicode() {
            return true;
         }

         public StringBufferUTF16 createStringBuffer(int capacity) {
            return new StringBufferUTF16(capacity);
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            return new DFAStateNode.IndexOfAnyCharCall(cps.inverseToCharArray(this));
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 3;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            assert cps.getMax() <= this.getMaxValue();

            matchersBuilder.createSplitMatcher(i, cps, compilationBuffer, Constants.ASCII_RANGE, Constants.BYTE_RANGE, Constants.BMP_RANGE_WITHOUT_LATIN1);
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.UTF16RawSequentialMatchers(
               matchersBuilder.materialize(0), matchersBuilder.materialize(1), matchersBuilder.materialize(2), matchersBuilder.getNoMatchSuccessor()
            );
         }
      }

      public static final class UTF32 extends Encodings.Encoding {
         private UTF32() {
         }

         @Override
         public String getName() {
            return "UTF-32";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return TruffleString.Encoding.UTF_32;
         }

         @Override
         public int getStride() {
            return 2;
         }

         @Override
         public int getMaxValue() {
            return 1114111;
         }

         @Override
         public CodePointSet getFullSet() {
            return Encodings.FULL_UNICODE_SET;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            return 1;
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            return true;
         }

         @Override
         public boolean isUnicode() {
            return true;
         }

         public StringBufferUTF32 createStringBuffer(int capacity) {
            return new StringBufferUTF32(capacity);
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            return new DFAStateNode.IndexOfAnyIntCall(cps.inverseToIntArray(this));
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 4;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            matchersBuilder.createSplitMatcher(
               i,
               cps,
               compilationBuffer,
               Constants.ASCII_RANGE,
               Constants.BYTE_RANGE,
               Constants.BMP_WITHOUT_LATIN1_WITHOUT_SURROGATES,
               Constants.ASTRAL_SYMBOLS_AND_LONE_SURROGATES
            );
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.UTF16Or32SequentialMatchers(
               matchersBuilder.materialize(0),
               matchersBuilder.materialize(1),
               matchersBuilder.materialize(2),
               matchersBuilder.materialize(3),
               matchersBuilder.getNoMatchSuccessor()
            );
         }
      }

      public static final class UTF8 extends Encodings.Encoding {
         @Override
         public String getName() {
            return "UTF-8";
         }

         @Override
         public TruffleString.Encoding getTStringEncoding() {
            return TruffleString.Encoding.UTF_8;
         }

         @Override
         public int getMaxValue() {
            return 1114111;
         }

         @Override
         public CodePointSet getFullSet() {
            return Encodings.FULL_UNICODE_SET;
         }

         @Override
         public int getEncodedSize(int codepoint) {
            if (codepoint < 128) {
               return 1;
            } else if (codepoint < 2048) {
               return 2;
            } else {
               return codepoint < 65536 ? 3 : 4;
            }
         }

         @Override
         public boolean isFixedCodePointWidth(CodePointSet set) {
            if (set.isEmpty()) {
               return true;
            } else {
               int min = set.getMin();
               int max = set.getMax();
               return (min >= 128 || max < 128) && (min >= 2048 || max < 2048) && (min >= 65536 || max <= 65536);
            }
         }

         @Override
         public boolean isUnicode() {
            return true;
         }

         public StringBufferUTF8 createStringBuffer(int capacity) {
            return new StringBufferUTF8(capacity);
         }

         @Override
         public DFAStateNode.IndexOfCall extractIndexOfCall(CodePointSet cps) {
            if (cps.inverseGetMax(this) <= 127) {
               byte[] indexOfChars = cps.inverseToByteArray(this);
               return new DFAStateNode.IndexOfAnyByteCall(indexOfChars);
            } else if (cps.inverseValueCount(this) == 1) {
               StringBufferUTF8 sb = this.createStringBuffer(4);
               sb.append(cps.inverseGetMin(this));
               return new DFAStateNode.IndexOfStringCall(sb.materialize(), new StringUTF8(new byte[sb.length()]));
            } else {
               return null;
            }
         }

         @Override
         public int getNumberOfCodeRanges() {
            return 4;
         }

         @Override
         public void createMatcher(SequentialMatchers.Builder matchersBuilder, int i, CodePointSet cps, CompilationBuffer compilationBuffer) {
            matchersBuilder.createSplitMatcher(
               i, cps, compilationBuffer, Constants.ASCII_RANGE, Constants.UTF8_TWO_BYTE_RANGE, Constants.UTF8_THREE_BYTE_RANGE, Constants.ASTRAL_SYMBOLS
            );
         }

         @Override
         public SequentialMatchers toMatchers(SequentialMatchers.Builder matchersBuilder) {
            return new SequentialMatchers.UTF8SequentialMatchers(
               matchersBuilder.materialize(0),
               matchersBuilder.materialize(1),
               matchersBuilder.materialize(2),
               matchersBuilder.materialize(3),
               matchersBuilder.getNoMatchSuccessor()
            );
         }
      }
   }
}
