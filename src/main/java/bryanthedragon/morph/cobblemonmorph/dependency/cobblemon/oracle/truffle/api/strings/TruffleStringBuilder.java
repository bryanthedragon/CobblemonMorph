package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.util.Arrays;

public final class TruffleStringBuilder {
   private final TruffleString.Encoding encoding;
   private byte[] buf;
   int stride;
   private int length;
   private int codePointLength;
   private int codeRange;

   private TruffleStringBuilder(TruffleString.Encoding encoding) {
      this(encoding, 16);
   }

   private TruffleStringBuilder(TruffleString.Encoding encoding, int initialSize) {
      this.encoding = encoding;
      this.buf = new byte[initialSize];
      this.codeRange = TStringGuards.is7BitCompatible(encoding) ? TSCodeRange.get7Bit() : TSCodeRange.getUnknown();
   }

   private int bufferLength() {
      return this.buf.length >> this.stride;
   }

   public boolean isEmpty() {
      return this.length == 0;
   }

   public int byteLength() {
      return this.length << this.encoding.naturalStride;
   }

   TruffleString.Encoding getEncoding() {
      return this.encoding;
   }

   int getStride() {
      return this.stride;
   }

   int getCodeRange() {
      return this.codeRange;
   }

   private void updateCodeRange(int newCodeRange) {
      this.codeRange = TSCodeRange.commonCodeRange(this.codeRange, newCodeRange);
   }

   private void appendLength(int addLength) {
      this.appendLength(addLength, addLength);
   }

   private void appendLength(int addLength, int addCodePointLength) {
      this.length += addLength;
      this.codePointLength += addCodePointLength;
   }

   public static TruffleStringBuilder create(TruffleString.Encoding encoding) {
      return new TruffleStringBuilder(encoding);
   }

   public static TruffleStringBuilder create(TruffleString.Encoding encoding, int initialCapacity) {
      return new TruffleStringBuilder(encoding, initialCapacity);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendByteUncached(byte value) {
      TruffleStringBuilder.AppendByteNode.getUncached().execute(this, value);
   }

   static int utf16Stride(TruffleStringBuilder sb, int value) {
      return value <= 255 ? sb.stride : 1;
   }

   static int utf16CodeRange(int value) {
      if (value <= 127) {
         return TSCodeRange.get7Bit();
      } else if (value <= 255) {
         return TSCodeRange.get8Bit();
      } else if (Encodings.isUTF16Surrogate(value)) {
         return TSCodeRange.getBrokenMultiByte();
      } else {
         return value <= 65535 ? TSCodeRange.get16Bit() : TSCodeRange.getValidMultiByte();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void appendCharUTF16Uncached(char value) {
      TruffleStringBuilder.AppendCharUTF16Node.getUncached().execute(this, value);
   }

   static int utf32Stride(TruffleStringBuilder sb, int value) {
      return Math.max(sb.stride, value <= 255 ? 0 : (value <= 65535 && !Encodings.isUTF16Surrogate(value) ? 1 : 2));
   }

   static int utf32CodeRange(int value) {
      if (value <= 127) {
         return TSCodeRange.get7Bit();
      } else if (value <= 255) {
         return TSCodeRange.get8Bit();
      } else if (Encodings.isUTF16Surrogate(value)) {
         return TSCodeRange.getBrokenFixedWidth();
      } else {
         return value <= 65535 ? TSCodeRange.get16Bit() : TSCodeRange.getValidFixedWidth();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void appendCodePointUncached(int codepoint) {
      TruffleStringBuilder.AppendCodePointNode.getUncached().execute(this, codepoint);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendCodePointUncached(int codepoint, int repeat) {
      TruffleStringBuilder.AppendCodePointNode.getUncached().execute(this, codepoint, repeat);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendCodePointUncached(int codepoint, int repeat, boolean allowUTF16Surrogates) {
      TruffleStringBuilder.AppendCodePointNode.getUncached().execute(this, codepoint, repeat, allowUTF16Surrogates);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendIntNumberUncached(int value) {
      TruffleStringBuilder.AppendIntNumberNode.getUncached().execute(this, value);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendLongNumberUncached(long value) {
      TruffleStringBuilder.AppendLongNumberNode.getUncached().execute(this, value);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendStringUncached(TruffleString a) {
      TruffleStringBuilder.AppendStringNode.getUncached().execute(this, a);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendSubstringByteIndexUncached(TruffleString a, int fromByteIndex, int byteLength) {
      TruffleStringBuilder.AppendSubstringByteIndexNode.getUncached().execute(this, a, fromByteIndex, byteLength);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendJavaStringUTF16Uncached(String a) {
      TruffleStringBuilder.AppendJavaStringUTF16Node.getUncached().execute(this, a);
   }

   @CompilerDirectives.TruffleBoundary
   public void appendJavaStringUTF16Uncached(String a, int fromCharIndex, int charLength) {
      TruffleStringBuilder.AppendJavaStringUTF16Node.getUncached().execute(this, a, fromCharIndex, charLength);
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleString toStringUncached() {
      return TruffleStringBuilder.ToStringNode.getUncached().execute(this);
   }

   void ensureCapacityS0(int appendLength, ConditionProfile bufferGrowProfile, BranchProfile errorProfile) {
      assert this.stride == 0;

      long newLength = (long)this.length + appendLength;
      if (bufferGrowProfile.profile(newLength > this.bufferLength())) {
         long newBufferLength = ((long)this.bufferLength() << 1) + 2L;

         assert newLength >= 0L;

         int maxLength = 2147483639;
         if (newLength > 2147483639L) {
            errorProfile.enter();
            throw InternalErrors.outOfMemory();
         }

         newBufferLength = Math.min(newBufferLength, 2147483639L);
         newBufferLength = Math.max(newBufferLength, newLength);
         this.buf = Arrays.copyOf(this.buf, (int)newBufferLength);
      }
   }

   void ensureCapacity(Node location, int appendLength, int curStride, int newStride, ConditionProfile bufferGrowProfile, BranchProfile errorProfile) {
      assert curStride == this.stride;

      assert newStride >= this.stride;

      long newLength = (long)this.length + appendLength;
      if (bufferGrowProfile.profile(curStride != newStride || newLength > this.bufferLength())) {
         long newBufferLength = newLength > this.bufferLength() ? ((long)this.bufferLength() << 1) + 2L : this.bufferLength();

         assert newLength >= 0L;

         int maxLength = 2147483639 >> newStride;
         if (newLength > maxLength) {
            errorProfile.enter();
            throw InternalErrors.outOfMemory();
         }

         newBufferLength = Math.min(newBufferLength, (long)maxLength);
         newBufferLength = Math.max(newBufferLength, newLength);
         this.buf = TStringOps.arraycopyOfWithStride(location, this.buf, 0, this.length, curStride, (int)newBufferLength, newStride);
         this.stride = newStride;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return TruffleStringBuilder.ToStringNode.getUncached().execute(this).toJavaStringUncached();
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class AppendArrayIntlNode extends Node {
      abstract void execute(TruffleStringBuilder sb, Object array, int offsetA, int lengthA, int strideA, int strideNew);

      @Specialization(guards = {"sb.stride == cachedStrideSB", "strideA == cachedStrideA", "strideNew == cachedStrideNew"}, limit = "9")
      void doCached(
         TruffleStringBuilder sb,
         Object array,
         int offsetA,
         int lengthA,
         int strideA,
         int strideNew,
         @Cached("sb.stride") int cachedStrideSB,
         @Cached("strideA") int cachedStrideA,
         @Cached("strideNew") int cachedStrideNew,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         doAppend(this, sb, array, offsetA, lengthA, cachedStrideSB, cachedStrideA, cachedStrideNew, bufferGrowProfile, errorProfile);
      }

      @Specialization(replaces = "doCached")
      void doUncached(
         TruffleStringBuilder sb,
         Object array,
         int offsetA,
         int lengthA,
         int strideA,
         int strideNew,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         doAppend(this, sb, array, offsetA, lengthA, sb.stride, strideA, strideNew, bufferGrowProfile, errorProfile);
      }

      private static void doAppend(
         Node location,
         TruffleStringBuilder sb,
         Object array,
         int offsetA,
         int lengthA,
         int cachedStrideSB,
         int cachedStrideA,
         int cachedStrideNew,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      ) {
         sb.ensureCapacity(location, lengthA, cachedStrideSB, cachedStrideNew, bufferGrowProfile, errorProfile);

         assert sb.stride == cachedStrideNew;

         TStringOps.arraycopyWithStride(location, array, offsetA, cachedStrideA, 0, sb.buf, 0, cachedStrideNew, sb.length, lengthA);
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendByteNode extends Node {
      AppendByteNode() {
      }

      public abstract void execute(TruffleStringBuilder sb, byte value);

      @Specialization
      static void append(TruffleStringBuilder sb, byte value, @Cached ConditionProfile bufferGrowProfile, @Cached BranchProfile errorProfile) {
         if (TStringGuards.isUTF16Or32(sb.encoding)) {
            throw InternalErrors.unsupportedOperation("appendByte is not supported for UTF-16 and UTF-32, use appendChar and appendInt instead");
         } else {
            sb.ensureCapacityS0(1, bufferGrowProfile, errorProfile);
            sb.buf[sb.length++] = value;
            if (value < 0) {
               sb.codeRange = TSCodeRange.asciiLatinBytesNonAsciiCodeRange(sb.encoding);
            }

            sb.codePointLength++;
         }
      }

      public static TruffleStringBuilder.AppendByteNode create() {
         return TruffleStringBuilderFactory.AppendByteNodeGen.create();
      }

      public static TruffleStringBuilder.AppendByteNode getUncached() {
         return TruffleStringBuilderFactory.AppendByteNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic({TStringGuards.class, TruffleStringBuilder.class})
   @GenerateUncached
   public abstract static class AppendCharUTF16Node extends Node {
      AppendCharUTF16Node() {
      }

      public abstract void execute(TruffleStringBuilder sb, char value);

      @Specialization(guards = {"cachedCurStride == sb.stride", "cachedNewStride == utf16Stride(sb, value)"}, limit = "9")
      void doCached(
         TruffleStringBuilder sb,
         char value,
         @Cached("sb.stride") int cachedCurStride,
         @Cached("utf16Stride(sb, value)") int cachedNewStride,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         this.doAppend(sb, value, cachedCurStride, cachedNewStride, bufferGrowProfile, errorProfile);
      }

      @Specialization(replaces = "doCached")
      void doUncached(TruffleStringBuilder sb, char value, @Cached ConditionProfile bufferGrowProfile, @Cached BranchProfile errorProfile) {
         this.doAppend(sb, value, sb.stride, TruffleStringBuilder.utf16Stride(sb, value), bufferGrowProfile, errorProfile);
      }

      private void doAppend(
         TruffleStringBuilder sb, char value, int cachedCurStride, int cachedNewStride, ConditionProfile bufferGrowProfile, BranchProfile errorProfile
      ) {
         if (!TStringGuards.isUTF16(sb.encoding)) {
            throw InternalErrors.unsupportedOperation("appendChar is meant for UTF-16 strings only");
         } else {
            sb.ensureCapacity(this, 1, cachedCurStride, cachedNewStride, bufferGrowProfile, errorProfile);
            sb.updateCodeRange(TruffleStringBuilder.utf16CodeRange(value));
            TStringOps.writeToByteArray(sb.buf, cachedNewStride, sb.length, value);
            sb.appendLength(1);
         }
      }

      public static TruffleStringBuilder.AppendCharUTF16Node create() {
         return TruffleStringBuilderFactory.AppendCharUTF16NodeGen.create();
      }

      public static TruffleStringBuilder.AppendCharUTF16Node getUncached() {
         return TruffleStringBuilderFactory.AppendCharUTF16NodeGen.getUncached();
      }
   }

   @ImportStatic({TStringGuards.class, TruffleStringBuilder.class})
   @GenerateUncached
   abstract static class AppendCodePointIntlNode extends Node {
      abstract void execute(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding encoding,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      );

      @Specialization(guards = "isAsciiBytesOrLatin1(enc)")
      static void bytes(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      ) {
         if (c > 255) {
            throw InternalErrors.invalidCodePoint(c);
         } else {
            sb.ensureCapacityS0(n, bufferGrowProfile, errorProfile);
            if (c > 127) {
               sb.updateCodeRange(TSCodeRange.asciiLatinBytesNonAsciiCodeRange(sb.encoding));
            }

            Arrays.fill(sb.buf, sb.length, sb.length + n, (byte)c);
            sb.length += n;
         }
      }

      @Specialization(guards = "isUTF8(enc)")
      static void utf8(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      ) {
         if (Encodings.isUTF16Surrogate(c)) {
            throw InternalErrors.invalidCodePoint(c);
         } else {
            int length = Encodings.utf8EncodedSize(c);
            sb.ensureCapacityS0(length * n, bufferGrowProfile, errorProfile);

            for (int i = 0; i < n; i++) {
               Encodings.utf8Encode(c, sb.buf, sb.length, length);
               sb.length += length;
            }

            if (c > 127) {
               sb.updateCodeRange(TSCodeRange.getValidMultiByte());
            }
         }
      }

      @Specialization(guards = {"isUTF16(enc)", "cachedCurStride == sb.stride", "cachedNewStride == utf16Stride(sb, c)"}, limit = "9")
      void utf16Cached(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile,
         @Cached("sb.stride") int cachedCurStride,
         @Cached("utf16Stride(sb, c)") int cachedNewStride,
         @Cached ConditionProfile bmpProfile
      ) {
         this.doUTF16(sb, c, n, allowUTF16Surrogates, bufferGrowProfile, errorProfile, cachedCurStride, cachedNewStride, bmpProfile);
      }

      @Specialization(guards = "isUTF16(enc)", replaces = "utf16Cached")
      void utf16Uncached(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile,
         @Cached ConditionProfile bmpProfile
      ) {
         this.doUTF16(sb, c, n, allowUTF16Surrogates, bufferGrowProfile, errorProfile, sb.stride, TruffleStringBuilder.utf16Stride(sb, c), bmpProfile);
      }

      private void doUTF16(
         TruffleStringBuilder sb,
         int c,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile,
         int cachedCurStride,
         int cachedNewStride,
         ConditionProfile bmpProfile
      ) {
         if (!allowUTF16Surrogates && Encodings.isUTF16Surrogate(c)) {
            throw InternalErrors.invalidCodePoint(c);
         } else {
            sb.ensureCapacity(this, c <= 65535 ? n : n << 1, cachedCurStride, cachedNewStride, bufferGrowProfile, errorProfile);
            sb.updateCodeRange(TruffleStringBuilder.utf16CodeRange(c));
            if (bmpProfile.profile(c <= 65535)) {
               arrayFill(sb, c, n, cachedNewStride);
            } else {
               for (int i = 0; i < n; i++) {
                  Encodings.utf16EncodeSurrogatePair(c, sb.buf, sb.length);
                  sb.length += 2;
               }
            }
         }
      }

      @Specialization(guards = {"isUTF32(enc)", "cachedCurStride == sb.stride", "cachedNewStride == utf32Stride(sb, c)"}, limit = "9")
      void utf32Cached(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile,
         @Cached("sb.stride") int cachedCurStride,
         @Cached("utf32Stride(sb, c)") int cachedNewStride
      ) {
         this.doUTF32(sb, c, n, allowUTF16Surrogates, bufferGrowProfile, errorProfile, cachedCurStride, cachedNewStride);
      }

      @Specialization(guards = "isUTF32(enc)", replaces = "utf32Cached")
      void utf32Uncached(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      ) {
         this.doUTF32(sb, c, n, allowUTF16Surrogates, bufferGrowProfile, errorProfile, sb.stride, TruffleStringBuilder.utf32Stride(sb, c));
      }

      void doUTF32(
         TruffleStringBuilder sb,
         int c,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile,
         int cachedCurStride,
         int cachedNewStride
      ) {
         if (!allowUTF16Surrogates && Encodings.isUTF16Surrogate(c)) {
            throw InternalErrors.invalidCodePoint(c);
         } else {
            sb.ensureCapacity(this, n, cachedCurStride, cachedNewStride, bufferGrowProfile, errorProfile);
            sb.updateCodeRange(TruffleStringBuilder.utf32CodeRange(c));
            arrayFill(sb, c, n, cachedNewStride);
         }
      }

      @Specialization(guards = "isUnsupportedEncoding(enc)")
      static void unsupported(
         TruffleStringBuilder sb,
         int c,
         TruffleString.Encoding enc,
         int n,
         boolean allowUTF16Surrogates,
         ConditionProfile bufferGrowProfile,
         BranchProfile errorProfile
      ) {
         JCodings.Encoding jCodingsEnc = JCodings.getInstance().get(enc);
         int length = JCodings.getInstance().getCodePointLength(jCodingsEnc, c);
         if (!enc.is7BitCompatible() || c > 127) {
            sb.updateCodeRange(JCodings.getInstance().isSingleByte(jCodingsEnc) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte());
         }

         if (length < 1) {
            throw InternalErrors.invalidCodePoint(c);
         } else {
            sb.ensureCapacityS0(length * n, bufferGrowProfile, errorProfile);

            for (int i = 0; i < n; i++) {
               int ret = JCodings.getInstance().writeCodePoint(jCodingsEnc, c, sb.buf, sb.length);
               if (ret != length
                  || JCodings.getInstance().getCodePointLength(jCodingsEnc, sb.buf, sb.length, sb.length + length) != ret
                  || JCodings.getInstance().readCodePoint(jCodingsEnc, sb.buf, sb.length, sb.length + length) != c) {
                  throw InternalErrors.invalidCodePoint(c);
               }

               sb.length += length;
            }
         }
      }

      private static void arrayFill(TruffleStringBuilder sb, int c, int n, int stride) {
         byte[] buf = sb.buf;
         int from = sb.length;
         int to = from + n;

         for (int i = from; i < to; i++) {
            TStringOps.writeToByteArray(buf, stride, i, c);
         }

         sb.length = to;
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendCodePointNode extends Node {
      AppendCodePointNode() {
      }

      public final void execute(TruffleStringBuilder sb, int codepoint) {
         this.execute(sb, codepoint, 1);
      }

      public final void execute(TruffleStringBuilder sb, int codepoint, int repeat) {
         this.execute(sb, codepoint, repeat, false);
      }

      public abstract void execute(TruffleStringBuilder sb, int codepoint, int repeat, boolean allowUTF16Surrogates);

      @Specialization
      static void append(
         TruffleStringBuilder sb,
         int c,
         int repeat,
         boolean allowUTF16Surrogates,
         @Cached TruffleStringBuilder.AppendCodePointIntlNode appendCodePointIntlNode,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         assert !allowUTF16Surrogates || TStringGuards.isUTF16Or32(sb.encoding) : "allowUTF16Surrogates is only supported on UTF-16 and UTF-32";

         if (c < 0 || c > 1114111) {
            throw InternalErrors.invalidCodePoint(c);
         } else if (repeat < 1) {
            throw InternalErrors.illegalArgument("number of repetitions must be at least 1");
         } else {
            appendCodePointIntlNode.execute(sb, c, sb.encoding, repeat, allowUTF16Surrogates, bufferGrowProfile, errorProfile);
            sb.codePointLength += repeat;
         }
      }

      public static TruffleStringBuilder.AppendCodePointNode create() {
         return TruffleStringBuilderFactory.AppendCodePointNodeGen.create();
      }

      public static TruffleStringBuilder.AppendCodePointNode getUncached() {
         return TruffleStringBuilderFactory.AppendCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendIntNumberNode extends Node {
      AppendIntNumberNode() {
      }

      public abstract void execute(TruffleStringBuilder sb, int value);

      @Specialization(guards = "cachedStride == sb.stride")
      void doAppend(
         TruffleStringBuilder sb,
         int value,
         @Cached(value = "sb.stride", allowUncached = true) int cachedStride,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         if (!TStringGuards.is7BitCompatible(sb.encoding)) {
            throw InternalErrors.unsupportedOperation("appendIntNumber is supported on ASCII-compatible encodings only");
         } else {
            int len = NumberConversion.stringLengthInt(value);
            sb.ensureCapacity(this, len, cachedStride, cachedStride, bufferGrowProfile, errorProfile);
            NumberConversion.writeIntToBytes(this, value, sb.buf, cachedStride, sb.length, len);
            sb.appendLength(len);
         }
      }

      public static TruffleStringBuilder.AppendIntNumberNode create() {
         return TruffleStringBuilderFactory.AppendIntNumberNodeGen.create();
      }

      public static TruffleStringBuilder.AppendIntNumberNode getUncached() {
         return TruffleStringBuilderFactory.AppendIntNumberNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendJavaStringUTF16Node extends Node {
      AppendJavaStringUTF16Node() {
      }

      public final void execute(TruffleStringBuilder sb, String a) {
         this.execute(sb, a, 0, a.length());
      }

      public abstract void execute(TruffleStringBuilder sb, String a, int fromCharIndex, int charLength);

      @Specialization
      void append(
         TruffleStringBuilder sb,
         String javaString,
         int fromIndex,
         int lengthStr,
         @Cached TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode,
         @Cached ConditionProfile stride0Profile
      ) {
         if (!TStringGuards.isUTF16(sb)) {
            throw InternalErrors.unsupportedOperation("appendJavaString is supported on UTF-16 only, use appendString for other encodings");
         } else if (lengthStr != 0) {
            AbstractTruffleString.boundsCheckRegionI(fromIndex, lengthStr, javaString.length());
            byte[] arrayStr = TStringUnsafe.getJavaStringArray(javaString);
            int strideStr = TStringUnsafe.getJavaStringStride(javaString);
            int offsetStr = fromIndex << strideStr;
            int appendCodePointLength;
            if (stride0Profile.profile(strideStr == 0)) {
               if (TStringGuards.is7Bit(sb.codeRange)) {
                  sb.updateCodeRange(TStringOps.calcStringAttributesLatin1(this, arrayStr, offsetStr, lengthStr));
               }

               appendCodePointLength = lengthStr;
            } else if (!TStringGuards.isBrokenMultiByteOrUnknown(sb.codeRange)) {
               long attrs = TStringOps.calcStringAttributesUTF16(this, arrayStr, offsetStr, lengthStr, false);
               sb.updateCodeRange(StringAttributes.getCodeRange(attrs));
               appendCodePointLength = StringAttributes.getCodePointLength(attrs);
            } else {
               appendCodePointLength = 0;
            }

            appendArrayIntlNode.execute(sb, arrayStr, offsetStr, lengthStr, strideStr, Stride.fromCodeRangeUTF16(sb.codeRange));
            sb.appendLength(lengthStr, appendCodePointLength);
         }
      }

      public static TruffleStringBuilder.AppendJavaStringUTF16Node create() {
         return TruffleStringBuilderFactory.AppendJavaStringUTF16NodeGen.create();
      }

      public static TruffleStringBuilder.AppendJavaStringUTF16Node getUncached() {
         return TruffleStringBuilderFactory.AppendJavaStringUTF16NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendLongNumberNode extends Node {
      AppendLongNumberNode() {
      }

      public abstract void execute(TruffleStringBuilder sb, long value);

      @Specialization(guards = "cachedStride == sb.stride")
      void doAppend(
         TruffleStringBuilder sb,
         long value,
         @Cached(value = "sb.stride", allowUncached = true) int cachedStride,
         @Cached ConditionProfile bufferGrowProfile,
         @Cached BranchProfile errorProfile
      ) {
         if (!TStringGuards.is7BitCompatible(sb.encoding)) {
            throw InternalErrors.unsupportedOperation("appendIntNumber is supported on ASCII-compatible encodings only");
         } else {
            int len = NumberConversion.stringLengthLong(value);
            sb.ensureCapacity(this, len, cachedStride, cachedStride, bufferGrowProfile, errorProfile);
            NumberConversion.writeLongToBytes(this, value, sb.buf, cachedStride, sb.length, len);
            sb.appendLength(len);
         }
      }

      public static TruffleStringBuilder.AppendLongNumberNode create() {
         return TruffleStringBuilderFactory.AppendLongNumberNodeGen.create();
      }

      public static TruffleStringBuilder.AppendLongNumberNode getUncached() {
         return TruffleStringBuilderFactory.AppendLongNumberNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendStringNode extends Node {
      AppendStringNode() {
      }

      public abstract void execute(TruffleStringBuilder sb, AbstractTruffleString a);

      @Specialization
      static void append(
         TruffleStringBuilder sb,
         AbstractTruffleString a,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode
      ) {
         if (a.length() != 0) {
            a.checkEncoding(sb.encoding);
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            sb.updateCodeRange(codeRangeA);
            int newStride = Math.max(sb.stride, Stride.fromCodeRange(codeRangeA, sb.encoding));
            appendArrayIntlNode.execute(sb, arrayA, a.offset(), a.length(), a.stride(), newStride);
            sb.appendLength(a.length(), getCodePointLengthNode.execute(a));
         }
      }

      public static TruffleStringBuilder.AppendStringNode create() {
         return TruffleStringBuilderFactory.AppendStringNodeGen.create();
      }

      public static TruffleStringBuilder.AppendStringNode getUncached() {
         return TruffleStringBuilderFactory.AppendStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AppendSubstringByteIndexNode extends Node {
      AppendSubstringByteIndexNode() {
      }

      public abstract void execute(TruffleStringBuilder sb, AbstractTruffleString a, int fromByteIndex, int byteLength);

      @Specialization
      static void append(
         TruffleStringBuilder sb,
         AbstractTruffleString a,
         int fromByteIndex,
         int byteLength,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TruffleStringBuilder.AppendArrayIntlNode appendArrayIntlNode,
         @Cached TStringInternalNodes.CalcStringAttributesNode calcAttributesNode,
         @Cached ConditionProfile calcAttrsProfile
      ) {
         if (byteLength != 0) {
            a.checkEncoding(sb.encoding);
            int fromIndex = TruffleString.rawIndex(fromByteIndex, sb.encoding);
            int length = TruffleString.rawIndex(byteLength, sb.encoding);
            a.boundsCheckRegionRaw(fromIndex, length);
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            int codeRange;
            int codePointLength;
            if (fromIndex == 0 && length == a.length()) {
               codeRange = codeRangeA;
               codePointLength = getCodePointLengthNode.execute(a);
            } else if (TStringGuards.isFixedWidth(codeRangeA) && !TSCodeRange.isMoreGeneralThan(codeRangeA, sb.codeRange)) {
               codeRange = codeRangeA;
               codePointLength = length;
            } else if (calcAttrsProfile.profile(!TStringGuards.isBrokenMultiByteOrUnknown(sb.codeRange) && !TStringGuards.isBrokenFixedWidth(sb.codeRange))) {
               long attrs = calcAttributesNode.execute(a, arrayA, a.offset() + (fromIndex << a.stride()), length, a.stride(), sb.encoding, codeRangeA);
               codeRange = StringAttributes.getCodeRange(attrs);
               codePointLength = StringAttributes.getCodePointLength(attrs);
            } else {
               codeRange = TSCodeRange.getUnknown();
               codePointLength = 0;
            }

            sb.updateCodeRange(codeRange);
            appendArrayIntlNode.execute(sb, arrayA, a.offset() + (fromIndex << a.stride()), length, a.stride(), Stride.fromCodeRange(sb.codeRange, sb.encoding));
            sb.appendLength(length, codePointLength);
         }
      }

      public static TruffleStringBuilder.AppendSubstringByteIndexNode create() {
         return TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.create();
      }

      public static TruffleStringBuilder.AppendSubstringByteIndexNode getUncached() {
         return TruffleStringBuilderFactory.AppendSubstringByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ToStringNode extends Node {
      ToStringNode() {
      }

      public final TruffleString execute(TruffleStringBuilder sb) {
         return this.execute(sb, false);
      }

      public abstract TruffleString execute(TruffleStringBuilder sb, boolean lazy);

      @Specialization
      static TruffleString createString(
         TruffleStringBuilder sb,
         boolean lazy,
         @Cached ConditionProfile calcAttributesProfile,
         @Cached TStringInternalNodes.CalcStringAttributesNode calcAttributesNode
      ) {
         if (sb.length == 0) {
            return sb.encoding.getEmpty();
         } else {
            int codeRange;
            int codePointLength;
            if (calcAttributesProfile.profile(TStringGuards.isBrokenMultiByteOrUnknown(sb.codeRange))) {
               long attrs = calcAttributesNode.execute(null, sb.buf, 0, sb.length, sb.stride, sb.encoding, TSCodeRange.getUnknown());
               codeRange = StringAttributes.getCodeRange(attrs);
               codePointLength = StringAttributes.getCodePointLength(attrs);
            } else {
               codeRange = sb.codeRange;
               codePointLength = sb.codePointLength;
            }

            int byteLength = sb.length << sb.stride;
            byte[] bytes = !lazy && sb.buf.length != byteLength ? Arrays.copyOf(sb.buf, byteLength) : sb.buf;
            return TruffleString.createFromByteArray(bytes, sb.length, sb.stride, sb.encoding, codePointLength, codeRange);
         }
      }

      public static TruffleStringBuilder.ToStringNode create() {
         return TruffleStringBuilderFactory.ToStringNodeGen.create();
      }

      public static TruffleStringBuilder.ToStringNode getUncached() {
         return TruffleStringBuilderFactory.ToStringNodeGen.getUncached();
      }
   }
}
