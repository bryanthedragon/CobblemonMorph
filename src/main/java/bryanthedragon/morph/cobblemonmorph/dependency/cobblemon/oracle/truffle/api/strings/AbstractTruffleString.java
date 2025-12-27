package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;

public abstract class AbstractTruffleString {
   static final boolean DEBUG_STRICT_ENCODING_CHECKS = Boolean.getBoolean("truffle.strings.debug-strict-encoding-checks");
   static final boolean DEBUG_NON_ZERO_OFFSET = Boolean.getBoolean("truffle.strings.debug-non-zero-offset-arrays");
   static final boolean DEBUG_ALWAYS_CREATE_JAVA_STRING = Boolean.getBoolean("truffle.strings.debug-always-create-java-string");
   private Object data;
   private final int offset;
   private final int length;
   private final byte encoding;
   private final byte stride;
   private final byte flags;
   int hashCode = 0;

   AbstractTruffleString(Object data, int offset, int length, int stride, TruffleString.Encoding encoding, int flags) {
      validateData(data, offset, length, stride);

      assert isByte(stride);

      assert isByte(flags);

      assert TStringGuards.isSupportedEncoding(encoding) || TStringAccessor.ENGINE.requireLanguageWithAllEncodings(encoding);

      this.data = data;
      this.encoding = encoding.id;
      this.offset = offset;
      this.length = length;
      this.stride = (byte)stride;
      this.flags = (byte)flags;
   }

   static boolean isByte(int i) {
      return -128 <= i && i <= 127;
   }

   private static void validateData(Object data, int offset, int length, int stride) {
      if (data instanceof byte[]) {
         TStringOps.validateRegion((byte[])data, offset, length, stride);
      } else if (data instanceof String) {
         TStringOps.validateRegion(TStringUnsafe.getJavaStringArray((String)data), offset, length, stride);
      } else if (!(data instanceof AbstractTruffleString.LazyLong) && !(data instanceof AbstractTruffleString.LazyConcat)) {
         if (!(data instanceof AbstractTruffleString.NativePointer)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
         }

         validateDataNative(offset, length, stride);
      } else {
         validateDataLazy(offset, length, stride);
      }
   }

   private static void validateDataLazy(int offset, int length, int stride) {
      if (!Stride.isStride(stride) || offset != 0 || Integer.toUnsignedLong(length) << stride > 2147483647L) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   private static void validateDataNative(int offset, int length, int stride) {
      if (!Stride.isStride(stride) || offset < 0 || Integer.toUnsignedLong(length) << stride > 2147483647L) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public final boolean isEmpty() {
      return this.length() == 0;
   }

   public final int byteLength(TruffleString.Encoding expectedEncoding) {
      this.checkEncoding(expectedEncoding);
      return this.length() << expectedEncoding.naturalStride;
   }

   public final boolean isCompatibleTo(TruffleString.Encoding expectedEncoding) {
      return this.isCompatibleTo(expectedEncoding.id, expectedEncoding.maxCompatibleCodeRange);
   }

   public final boolean isManaged() {
      return !this.isNative();
   }

   public final boolean isNative() {
      return this.data instanceof AbstractTruffleString.NativePointer;
   }

   public final boolean isImmutable() {
      return this instanceof TruffleString;
   }

   public final boolean isMutable() {
      assert this instanceof TruffleString || this instanceof MutableTruffleString;

      return !this.isImmutable();
   }

   final boolean isCompatibleTo(int enc, int maxCompatibleCodeRange) {
      return this.encoding() == enc
         | (!DEBUG_STRICT_ENCODING_CHECKS && this instanceof TruffleString && ((TruffleString)this).codeRange() < maxCompatibleCodeRange);
   }

   final Object data() {
      return this.data;
   }

   final void setData(byte[] array) {
      if (this.offset() == 0 && this.length() << this.stride() == array.length) {
         this.data = array;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   final int offset() {
      return this.offset;
   }

   final int byteArrayOffset() {
      assert this.data instanceof byte[] || this.data instanceof AbstractTruffleString.NativePointer || this.data instanceof AbstractTruffleString.LazyLong;

      assert !(this.data instanceof AbstractTruffleString.NativePointer) || this.offset() == ((AbstractTruffleString.NativePointer)this.data).offset();

      return this.data instanceof AbstractTruffleString.NativePointer ? 0 : this.offset();
   }

   final int length() {
      return this.length;
   }

   final int encoding() {
      return this.encoding;
   }

   final int stride() {
      return this.stride;
   }

   final int flags() {
      return this.flags;
   }

   final int getHashCodeUnsafe() {
      assert this.isHashCodeCalculated();

      return this.hashCode;
   }

   final boolean isHashCodeCalculated() {
      return this.hashCode != 0;
   }

   final boolean isMaterialized(TruffleString.Encoding expectedEncoding) {
      return this.data instanceof byte[]
         || this.isLazyLong() && ((AbstractTruffleString.LazyLong)this.data).bytes != null
         || this.isNative() && (TStringGuards.isSupportedEncoding(expectedEncoding) || ((AbstractTruffleString.NativePointer)this.data).byteArrayIsValid);
   }

   final boolean isLazyConcat() {
      return this.data instanceof AbstractTruffleString.LazyConcat;
   }

   final boolean isLazyLong() {
      return this.data instanceof AbstractTruffleString.LazyLong;
   }

   final boolean isJavaString() {
      return this.data instanceof String;
   }

   static TruffleStringIterator forwardIterator(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding) {
      return forwardIterator(a, arrayA, codeRangeA, encoding, TruffleString.ErrorHandling.BEST_EFFORT);
   }

   static TruffleStringIterator forwardIterator(
      AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, TruffleString.ErrorHandling errorHandling
   ) {
      return new TruffleStringIterator(a, arrayA, codeRangeA, encoding, errorHandling, 0);
   }

   static TruffleStringIterator backwardIterator(AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding) {
      return backwardIterator(a, arrayA, codeRangeA, encoding, TruffleString.ErrorHandling.BEST_EFFORT);
   }

   static TruffleStringIterator backwardIterator(
      AbstractTruffleString a, Object arrayA, int codeRangeA, TruffleString.Encoding encoding, TruffleString.ErrorHandling errorHandling
   ) {
      return new TruffleStringIterator(a, arrayA, codeRangeA, encoding, errorHandling, a.length());
   }

   final void checkEncoding(TruffleString.Encoding expectedEncoding) {
      if (!this.isCompatibleTo(expectedEncoding)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw InternalErrors.wrongEncoding(expectedEncoding);
      }
   }

   final void looseCheckEncoding(TruffleString.Encoding expectedEncoding, int codeRange) {
      if (!this.isLooselyCompatibleTo(expectedEncoding.id, expectedEncoding.maxCompatibleCodeRange, codeRange)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw InternalErrors.wrongEncoding(expectedEncoding);
      }
   }

   boolean isLooselyCompatibleTo(int expectedEncoding, int maxCompatibleCodeRange, int codeRange) {
      return this.encoding() == expectedEncoding || codeRange < maxCompatibleCodeRange;
   }

   static int rawIndex(int byteIndex, TruffleString.Encoding expectedEncoding) {
      if (TStringGuards.isUTF16(expectedEncoding) && (byteIndex & 1) != 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw InternalErrors.illegalArgument("misaligned byte index on UTF-16 string");
      } else if (TStringGuards.isUTF32(expectedEncoding) && (byteIndex & 3) != 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw InternalErrors.illegalArgument("misaligned byte index on UTF-32 string");
      } else {
         return byteIndex >> expectedEncoding.naturalStride;
      }
   }

   static int byteIndex(int rawIndex, TruffleString.Encoding expectedEncoding) {
      assert rawIndex < 0 || rawIndex << expectedEncoding.naturalStride >= 0;

      assert rawIndex >= 0 || rawIndex << expectedEncoding.naturalStride < 0;

      return rawIndex << expectedEncoding.naturalStride;
   }

   final void boundsCheck(int index, TStringInternalNodes.GetCodePointLengthNode codePointLengthNode) {
      boundsCheckI(index, codePointLengthNode.execute(this));
   }

   final void boundsCheck(int fromIndex, int toIndex, TStringInternalNodes.GetCodePointLengthNode codePointLengthNode) {
      boundsCheckI(fromIndex, toIndex, codePointLengthNode.execute(this));
   }

   final void boundsCheckRegion(int fromIndex, int regionLength, TStringInternalNodes.GetCodePointLengthNode codePointLengthNode) {
      boundsCheckRegionI(fromIndex, regionLength, codePointLengthNode.execute(this));
   }

   final void boundsCheckByteIndexS0(int byteIndex) {
      assert this.stride() == 0;

      boundsCheckI(byteIndex, this.length());
   }

   final void boundsCheckByteIndexUTF16(int byteIndex) {
      boundsCheckI(byteIndex, this.length() << TruffleString.Encoding.UTF_16.naturalStride);
   }

   final void boundsCheckByteIndexUTF32(int byteIndex) {
      boundsCheckI(byteIndex, this.length() << TruffleString.Encoding.UTF_32.naturalStride);
   }

   final void boundsCheckRaw(int index) {
      boundsCheckI(index, this.length());
   }

   final void boundsCheckRawLength(int index) {
      if (Integer.compareUnsigned(index, this.length()) > 0) {
         throw InternalErrors.indexOutOfBounds();
      }
   }

   final void boundsCheckRaw(int fromIndex, int toIndex) {
      boundsCheckI(fromIndex, toIndex, this.length());
   }

   final void boundsCheckRegionRaw(int fromIndex, int regionLength) {
      boundsCheckRegionI(fromIndex, regionLength, this.length());
   }

   static void boundsCheckI(int index, int arrayLength) {
      assert arrayLength >= 0;

      if (Integer.compareUnsigned(index, arrayLength) >= 0) {
         throw InternalErrors.indexOutOfBounds();
      }
   }

   static void boundsCheckI(int fromIndex, int toIndex, int arrayLength) {
      assert arrayLength >= 0;

      if (Integer.compareUnsigned(fromIndex, arrayLength) >= 0 || Integer.compareUnsigned(toIndex, arrayLength) > 0) {
         throw InternalErrors.indexOutOfBounds();
      }
   }

   static void boundsCheckRegionI(int fromIndex, int regionLength, int arrayLength) {
      assert arrayLength >= 0;

      if (Integer.toUnsignedLong(fromIndex) + Integer.toUnsignedLong(regionLength) > arrayLength) {
         throw InternalErrors.indexOutOfBounds();
      }
   }

   static void nullCheck(Object o) {
      if (o == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new NullPointerException("unexpected null pointer");
      }
   }

   static void checkByteLength(int byteLength, TruffleString.Encoding encoding) {
      if (TStringGuards.isUTF16(encoding)) {
         TruffleString.checkByteLengthUTF16(byteLength);
      } else if (TStringGuards.isUTF32(encoding)) {
         TruffleString.checkByteLengthUTF32(byteLength);
      }
   }

   static void checkByteLengthUTF16(int byteLength) {
      if ((byteLength & 1) != 0) {
         throw InternalErrors.illegalByteArrayLength("UTF-16 string byte length is not a multiple of 2");
      }
   }

   static void checkByteLengthUTF32(int byteLength) {
      if ((byteLength & 3) != 0) {
         throw InternalErrors.illegalByteArrayLength("UTF-32 string byte length is not a multiple of 4");
      }
   }

   static void checkArrayRange(byte[] value, int byteOffset, int byteLength) {
      checkArrayRange(value.length, byteOffset, byteLength);
   }

   static void checkArrayRange(int arrayLength, int byteOffset, int byteLength) {
      if (Integer.toUnsignedLong(byteOffset) + Integer.toUnsignedLong(byteLength) > arrayLength) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw InternalErrors.substringOutOfBounds();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString asTruffleStringUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.AsTruffleStringNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString asManagedTruffleStringUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.AsManagedNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final MutableTruffleString asMutableTruffleStringUncached(TruffleString.Encoding expectedEncoding) {
      return MutableTruffleString.AsMutableTruffleStringNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final MutableTruffleString asManagedMutableTruffleStringUncached(TruffleString.Encoding expectedEncoding) {
      return MutableTruffleString.AsManagedNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString.CodeRange getCodeRangeUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.GetCodeRangeNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString.CodeRange getByteCodeRangeUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.GetByteCodeRangeNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean codeRangeEqualsUncached(TruffleString.CodeRange otherCodeRange) {
      return TruffleString.CodeRangeEqualsNode.getUncached().execute(this, otherCodeRange);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean isValidUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.IsValidNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointLengthUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.CodePointLengthNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int hashCodeUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.HashCodeNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int readByteUncached(int i, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ReadByteNode.getUncached().execute(this, i, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int readCharUTF16Uncached(int i) {
      return TruffleString.ReadCharUTF16Node.getUncached().execute(this, i);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteLengthOfCodePointUncached(int byteIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteLengthOfCodePointNode.getUncached().execute(this, byteIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteLengthOfCodePointUncached(int byteIndex, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling) {
      return TruffleString.ByteLengthOfCodePointNode.getUncached().execute(this, byteIndex, expectedEncoding, errorHandling);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteIndexToCodePointIndexUncached(int byteOffset, int byteIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteIndexToCodePointIndexNode.getUncached().execute(this, byteOffset, byteIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointIndexToByteIndexUncached(int byteOffset, int codepointIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.CodePointIndexToByteIndexNode.getUncached().execute(this, byteOffset, codepointIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointAtIndexUncached(int i, TruffleString.Encoding expectedEncoding) {
      return TruffleString.CodePointAtIndexNode.getUncached().execute(this, i, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointAtIndexUncached(int i, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling) {
      return TruffleString.CodePointAtIndexNode.getUncached().execute(this, i, expectedEncoding, errorHandling);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointAtByteIndexUncached(int i, TruffleString.Encoding expectedEncoding) {
      return TruffleString.CodePointAtByteIndexNode.getUncached().execute(this, i, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int codePointAtByteIndexUncached(int i, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling) {
      return TruffleString.CodePointAtByteIndexNode.getUncached().execute(this, i, expectedEncoding, errorHandling);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteIndexOfAnyByteUncached(int fromByteIndex, int maxByteIndex, byte[] values, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteIndexOfAnyByteNode.getUncached().execute(this, fromByteIndex, maxByteIndex, values, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int charIndexOfAnyCharUTF16Uncached(int fromCharIndex, int maxCharIndex, char[] values) {
      return TruffleString.CharIndexOfAnyCharUTF16Node.getUncached().execute(this, fromCharIndex, maxCharIndex, values);
   }

   @CompilerDirectives.TruffleBoundary
   public final int intIndexOfAnyIntUTF32Uncached(int fromIntIndex, int maxIntIndex, int[] values) {
      return TruffleString.IntIndexOfAnyIntUTF32Node.getUncached().execute(this, fromIntIndex, maxIntIndex, values);
   }

   @CompilerDirectives.TruffleBoundary
   public final int indexOfCodePointUncached(int cp, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.IndexOfCodePointNode.getUncached().execute(this, cp, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteIndexOfCodePointUncached(int cp, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteIndexOfCodePointNode.getUncached().execute(this, cp, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int lastIndexOfCodePointUncached(int cp, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.LastIndexOfCodePointNode.getUncached().execute(this, cp, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int lastByteIndexOfCodePointUncached(int cp, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.LastByteIndexOfCodePointNode.getUncached().execute(this, cp, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int indexOfStringUncached(AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.IndexOfStringNode.getUncached().execute(this, b, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteIndexOfStringUncached(AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteIndexOfStringNode.getUncached().execute(this, b, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int byteIndexOfStringUncached(TruffleString.WithMask b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.ByteIndexOfStringNode.getUncached().execute(this, b.string, fromIndex, toIndex, b.mask, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int lastIndexOfStringUncached(AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.LastIndexOfStringNode.getUncached().execute(this, b, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int lastByteIndexOfStringUncached(AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.LastByteIndexOfStringNode.getUncached().execute(this, b, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int lastByteIndexOfStringUncached(TruffleString.WithMask b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
      return TruffleString.LastByteIndexOfStringNode.getUncached().execute(this, b, fromIndex, toIndex, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int compareBytesUncached(AbstractTruffleString b, TruffleString.Encoding expectedEncoding) {
      return TruffleString.CompareBytesNode.getUncached().execute(this, b, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int compareCharsUTF16Uncached(AbstractTruffleString b) {
      return TruffleString.CompareCharsUTF16Node.getUncached().execute(this, b);
   }

   @CompilerDirectives.TruffleBoundary
   public final int compareIntsUTF32Uncached(AbstractTruffleString b) {
      return TruffleString.CompareIntsUTF32Node.getUncached().execute(this, b);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean regionEqualsUncached(int fromIndexA, AbstractTruffleString b, int fromIndexB, int regionLength, TruffleString.Encoding expectedEncoding) {
      return TruffleString.RegionEqualNode.getUncached().execute(this, fromIndexA, b, fromIndexB, regionLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean regionEqualByteIndexUncached(
      int fromByteIndexA, AbstractTruffleString b, int fromByteIndexB, int byteLength, TruffleString.Encoding expectedEncoding
   ) {
      return TruffleString.RegionEqualByteIndexNode.getUncached().execute(this, fromByteIndexA, b, fromByteIndexB, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean regionEqualByteIndexUncached(
      int fromByteIndexA, TruffleString.WithMask b, int fromByteIndexB, int byteLength, TruffleString.Encoding expectedEncoding
   ) {
      return TruffleString.RegionEqualByteIndexNode.getUncached().execute(this, fromByteIndexA, b, fromByteIndexB, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString concatUncached(AbstractTruffleString b, TruffleString.Encoding expectedEncoding, boolean lazy) {
      return TruffleString.ConcatNode.getUncached().execute(this, b, expectedEncoding, lazy);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString repeatUncached(int n, TruffleString.Encoding expectedEncoding) {
      return TruffleString.RepeatNode.getUncached().execute(this, n, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString substringUncached(int fromIndex, int substringLength, TruffleString.Encoding expectedEncoding, boolean lazy) {
      return TruffleString.SubstringNode.getUncached().execute(this, fromIndex, substringLength, expectedEncoding, lazy);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString substringByteIndexUncached(int fromByteIndex, int byteLength, TruffleString.Encoding expectedEncoding, boolean lazy) {
      return TruffleString.SubstringByteIndexNode.getUncached().execute(this, fromByteIndex, byteLength, expectedEncoding, lazy);
   }

   @CompilerDirectives.TruffleBoundary
   public final boolean equalsUncached(AbstractTruffleString b, TruffleString.Encoding expectedEncoding) {
      return TruffleString.EqualNode.getUncached().execute(this, b, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final int parseIntUncached() throws TruffleString.NumberFormatException {
      return this.parseIntUncached(10);
   }

   @CompilerDirectives.TruffleBoundary
   public final int parseIntUncached(int radix) throws TruffleString.NumberFormatException {
      return TruffleString.ParseIntNode.getUncached().execute(this, radix);
   }

   @CompilerDirectives.TruffleBoundary
   public final long parseLongUncached() throws TruffleString.NumberFormatException {
      return this.parseLongUncached(10);
   }

   @CompilerDirectives.TruffleBoundary
   public final long parseLongUncached(int radix) throws TruffleString.NumberFormatException {
      return TruffleString.ParseLongNode.getUncached().execute(this, radix);
   }

   @CompilerDirectives.TruffleBoundary
   public final double parseDoubleUncached() throws TruffleString.NumberFormatException {
      return TruffleString.ParseDoubleNode.getUncached().execute(this);
   }

   @CompilerDirectives.TruffleBoundary
   public final InternalByteArray getInternalByteArrayUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.GetInternalByteArrayNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final Object getInternalNativePointerUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.GetInternalNativePointerNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final byte[] copyToByteArrayUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.CopyToByteArrayNode.getUncached().execute(this, expectedEncoding);
   }

   @Deprecated(since = "22.3")
   @CompilerDirectives.TruffleBoundary
   public final void copyToByteArrayNodeUncached(int byteFromIndexA, byte[] dst, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding) {
      this.copyToByteArrayUncached(byteFromIndexA, dst, byteFromIndexDst, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final void copyToByteArrayUncached(int byteFromIndexA, byte[] dst, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding) {
      TruffleString.CopyToByteArrayNode.getUncached().execute(this, byteFromIndexA, dst, byteFromIndexDst, byteLength, expectedEncoding);
   }

   @Deprecated(since = "22.3")
   @CompilerDirectives.TruffleBoundary
   public final void copyToNativeMemoryNodeUncached(
      int byteFromIndexA, Object pointerObject, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding
   ) {
      this.copyToNativeMemoryUncached(byteFromIndexA, pointerObject, byteFromIndexDst, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final void copyToNativeMemoryUncached(
      int byteFromIndexA, Object pointerObject, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding
   ) {
      TruffleString.CopyToNativeMemoryNode.getUncached().execute(this, byteFromIndexA, pointerObject, byteFromIndexDst, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final String toJavaStringUncached() {
      return TruffleString.ToJavaStringNode.getUncached().execute(this);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString switchEncodingUncached(TruffleString.Encoding targetEncoding) {
      return TruffleString.SwitchEncodingNode.getUncached().execute(this, targetEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleString forceEncodingUncached(TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding) {
      return TruffleString.ForceEncodingNode.getUncached().execute(this, expectedEncoding, targetEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleStringIterator createCodePointIteratorUncached(TruffleString.Encoding expectedEncoding) {
      return TruffleString.CreateCodePointIteratorNode.getUncached().execute(this, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public final TruffleStringIterator createBackwardCodePointIteratorUncached(TruffleString.Encoding expectedEncoding) {
      TruffleStringIterator it = TruffleString.CreateCodePointIteratorNode.getUncached().execute(this, expectedEncoding);
      it.setRawIndex(this.length());
      return it;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof AbstractTruffleString)) {
         return false;
      } else {
         AbstractTruffleString b = (AbstractTruffleString)obj;
         int codeRangeA = TStringInternalNodes.GetCodeRangeNode.getUncached().execute(this);
         int codeRangeB = TStringInternalNodes.GetCodeRangeNode.getUncached().execute(b);
         int enc = this.encoding();
         if (enc != b.encoding()) {
            if (!b.isLooselyCompatibleTo(enc, TruffleString.Encoding.getMaxCompatibleCodeRange(enc), codeRangeB)) {
               enc = b.encoding();
            }

            if (!this.isLooselyCompatibleTo(enc, TruffleString.Encoding.getMaxCompatibleCodeRange(enc), codeRangeA)) {
               return false;
            }
         }

         return TruffleString.EqualNode.checkContentEquals(
            this,
            codeRangeA,
            b,
            codeRangeB,
            TruffleString.ToIndexableNode.getUncached(),
            TruffleString.ToIndexableNode.getUncached(),
            ConditionProfile.getUncached(),
            BranchProfile.getUncached(),
            ConditionProfile.getUncached(),
            TruffleString.EqualNode.getUncached()
         );
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final int hashCode() {
      return !this.isHashCodeCalculated() ? this.hashCodeUncached(TruffleString.Encoding.get(this.encoding())) : this.hashCode;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final String toString() {
      if (this.encoding == TruffleString.Encoding.BYTES.id && !TStringGuards.is7Bit(TStringInternalNodes.GetCodeRangeNode.getUncached().execute(this))) {
         StringBuilder sb = new StringBuilder(this.length);
         TruffleStringIterator it = this.createCodePointIteratorUncached(TruffleString.Encoding.BYTES);

         while (it.hasNext()) {
            int c = it.nextUncached();
            if (c <= 127) {
               sb.append((char)c);
            } else {
               sb.append(String.format("\\x%02X", c));
            }
         }

         return sb.toString();
      } else {
         return this.toJavaStringUncached();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public final String toStringDebug() {
      return String.format(
         "TString(%s, %s, off: %d, len: %d, str: %d, cpLen: %d, \"%s\")",
         TruffleString.Encoding.get(this.encoding()),
         TSCodeRange.toString(this instanceof TruffleString ? ((TruffleString)this).codeRange() : ((MutableTruffleString)this).codeRange()),
         this.offset(),
         this.length(),
         this.stride(),
         this instanceof TruffleString ? ((TruffleString)this).codePointLength() : ((MutableTruffleString)this).codePointLength(),
         this.toJavaStringUncached()
      );
   }

   static final class LazyConcat {
      private final TruffleString left;
      private final TruffleString right;

      LazyConcat(TruffleString left, TruffleString right) {
         this.left = left;
         this.right = right;
      }

      @CompilerDirectives.TruffleBoundary
      static byte[] flatten(Node location, TruffleString a) {
         byte[] dst = new byte[a.length() << a.stride()];
         flatten(location, a, 0, a.length(), dst, 0, a.stride());
         return dst;
      }

      @CompilerDirectives.TruffleBoundary
      private static void flatten(Node location, TruffleString src, int srcBegin, int srcEnd, byte[] dst, int dstBegin, int dstStride) {
         TruffleString str = src;
         int from = srcBegin;
         int to = srcEnd;
         int dstFrom = dstBegin;

         while ($assertionsDisabled || 0 <= from && from <= to && to <= str.length()) {
            Object data = str.data();
            if (!(data instanceof AbstractTruffleString.LazyConcat)) {
               copy(location, str, dst, dstFrom, dstStride);
               return;
            }

            TruffleString left = ((AbstractTruffleString.LazyConcat)data).left;
            TruffleString right = ((AbstractTruffleString.LazyConcat)data).right;
            int mid = left.length();
            if (to - mid >= mid - from) {
               if (from < mid) {
                  if (left.isLazyConcat()) {
                     flatten(location, left, from, mid, dst, dstFrom, dstStride);
                  } else {
                     copy(location, left, dst, dstFrom, dstStride);
                  }

                  dstFrom += mid - from;
                  from = 0;
               } else {
                  from -= mid;
               }

               to -= mid;
               str = right;
            } else {
               if (to > mid) {
                  if (right.isLazyConcat()) {
                     flatten(location, right, 0, to - mid, dst, dstFrom + mid - from, dstStride);
                  } else {
                     copy(location, right, dst, dstFrom + mid - from, dstStride);
                  }

                  to = mid;
               }

               str = left;
            }
         }

         throw new AssertionError();
      }

      @CompilerDirectives.TruffleBoundary
      private static void copy(Node location, TruffleString src, byte[] dst, int dstFrom, int dstStride) {
         Object arrayA = TruffleString.ToIndexableNode.getUncached().execute(src, src.data());
         TStringOps.arraycopyWithStride(location, arrayA, src.offset(), src.stride(), 0, dst, 0, dstStride, dstFrom, src.length());
      }
   }

   static final class LazyLong {
      final long value;
      byte[] bytes;

      LazyLong(long value) {
         this.value = value;
      }

      void setBytes(TruffleString a, byte[] bytes) {
         if (a.offset() == 0 && a.length() == bytes.length) {
            this.bytes = bytes;
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw CompilerDirectives.shouldNotReachHere();
         }
      }
   }

   static final class NativePointer {
      private final Object pointerObject;
      final long pointer;
      private byte[] bytes;
      private final int offset;
      private volatile boolean byteArrayIsValid = false;

      private NativePointer(Object pointerObject, long pointer, int offset) {
         this.pointerObject = pointerObject;
         this.pointer = pointer;
         this.offset = offset;
      }

      static AbstractTruffleString.NativePointer create(Node nodeThis, Object pointerObject, Node interopLibrary, int offset) {
         if (!TStringAccessor.isNativeAccessAllowed(nodeThis)) {
            throw InternalErrors.nativeAccessRequired();
         } else {
            return new AbstractTruffleString.NativePointer(pointerObject, TStringAccessor.INTEROP.unboxPointer(interopLibrary, pointerObject), offset);
         }
      }

      AbstractTruffleString.NativePointer copy(int newOffset) {
         return new AbstractTruffleString.NativePointer(this.pointerObject, this.pointer, newOffset);
      }

      Object getPointerObject() {
         return this.pointerObject;
      }

      byte[] getBytes() {
         assert this.bytes != null;

         return this.bytes;
      }

      int offset() {
         return this.offset;
      }

      void materializeByteArray(AbstractTruffleString a, ConditionProfile profile) {
         this.materializeByteArray(a.length() << a.stride(), profile);
      }

      void materializeByteArray(int byteLength, ConditionProfile profile) {
         if (profile.profile(!this.byteArrayIsValid)) {
            if (this.bytes == null) {
               this.bytes = new byte[byteLength];
            }

            TStringUnsafe.copyFromNative(this.pointer, this.offset, this.bytes, 0L, byteLength);
            this.byteArrayIsValid = true;
         }
      }

      void invalidateCachedByteArray() {
         this.byteArrayIsValid = false;
      }
   }
}
