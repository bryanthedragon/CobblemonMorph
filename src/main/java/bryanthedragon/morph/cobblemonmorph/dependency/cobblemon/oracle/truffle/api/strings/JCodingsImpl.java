package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.util.Arrays;
import org.graalvm.collections.EconomicMap;
import org.graalvm.shadowed.org.jcodings.EncodingDB;
import org.graalvm.shadowed.org.jcodings.Ptr;
import org.graalvm.shadowed.org.jcodings.transcode.EConv;
import org.graalvm.shadowed.org.jcodings.transcode.EConvResult;
import org.graalvm.shadowed.org.jcodings.transcode.TranscoderDB;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

final class JCodingsImpl implements JCodings {
   private static final int MAX_J_CODINGS_INDEX_VALUE = 127;
   @CompilerDirectives.CompilationFinal
   private static final EconomicMap<String, JCodingsImpl.EncodingWrapper> J_CODINGS_MAP = createJCodingsMap();
   private static final byte[] CONVERSION_REPLACEMENT = new byte[]{63};
   private static final byte[] CONVERSION_REPLACEMENT_UTF_8 = new byte[]{-17, -65, -67};
   private static final byte[] CONVERSION_REPLACEMENT_UTF_16 = TStringGuards.littleEndian() ? new byte[]{-3, -1} : new byte[]{-1, -3};
   private static final byte[] CONVERSION_REPLACEMENT_UTF_32 = TStringGuards.littleEndian() ? new byte[]{-3, -1, 0, 0} : new byte[]{0, 0, -1, -3};

   @CompilerDirectives.TruffleBoundary
   private static EconomicMap<String, JCodingsImpl.EncodingWrapper> createJCodingsMap() {
      CaseInsensitiveBytesHash<EncodingDB.Entry> encodings = EncodingDB.getEncodings();
      if (encodings.size() > 127) {
         throw new RuntimeException(
            String.format("Assumption broken: org.graalvm.shadowed.org.jcodings has more than %d encodings (actual: %d)!", 127, encodings.size())
         );
      } else {
         EconomicMap<String, JCodingsImpl.EncodingWrapper> allEncodings = EconomicMap.create(encodings.size());

         for (EncodingDB.Entry entry : encodings) {
            org.graalvm.shadowed.org.jcodings.Encoding enc = entry.getEncoding();
            int i = enc.getIndex();
            if (i < 0 || i >= encodings.size()) {
               throw new RuntimeException(
                  String.format(
                     "Assumption broken: index of org.graalvm.shadowed.org.jcodings encoding \"%s\" is greater than number of encodings (index: %d, number of encodings: %d)!",
                     enc,
                     i,
                     encodings.size()
                  )
               );
            }

            allEncodings.put(toEnumName(enc.toString()), new JCodingsImpl.EncodingWrapper(enc));
         }

         return allEncodings;
      }
   }

   @Override
   public JCodings.Encoding get(String encodingName) {
      return J_CODINGS_MAP.get(encodingName);
   }

   @Override
   public JCodings.Encoding get(TruffleString.Encoding encoding) {
      return encoding.jCoding;
   }

   @Override
   public String name(JCodings.Encoding jCoding) {
      return unwrap(jCoding).toString();
   }

   @Override
   public int minLength(JCodings.Encoding jCoding) {
      return unwrap(jCoding).minLength();
   }

   @Override
   public int maxLength(JCodings.Encoding jCoding) {
      return unwrap(jCoding).maxLength();
   }

   @Override
   public boolean isFixedWidth(JCodings.Encoding jCoding) {
      return unwrap(jCoding).isFixedWidth();
   }

   @Override
   public boolean isSingleByte(JCodings.Encoding jCoding) {
      return unwrap(jCoding).isSingleByte();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getCodePointLength(JCodings.Encoding jCoding, int codepoint) {
      return unwrap(jCoding).codeToMbcLength(codepoint);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getPreviousCodePointIndex(JCodings.Encoding jCoding, byte[] array, int arrayBegin, int index, int arrayEnd) {
      return unwrap(jCoding).prevCharHead(array, arrayBegin, index, arrayEnd);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getCodePointLength(JCodings.Encoding jCoding, byte[] array, int index, int arrayLength) {
      return unwrap(jCoding).length(array, index, arrayLength);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int readCodePoint(JCodings.Encoding jCoding, byte[] array, int index, int arrayEnd) {
      return unwrap(jCoding).mbcToCode(array, index, arrayEnd);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int writeCodePoint(JCodings.Encoding jCoding, int codepoint, byte[] array, int index) {
      return unwrap(jCoding).codeToMbc(codepoint, array, index);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int codePointIndexToRaw(
      Node location, AbstractTruffleString a, byte[] arrayA, int extraOffsetRaw, int index, boolean isLength, JCodings.Encoding jCoding
   ) {
      if (this.isFixedWidth(jCoding)) {
         return index * this.minLength(jCoding);
      } else {
         int offset = a.byteArrayOffset() + extraOffsetRaw;
         int end = a.byteArrayOffset() + a.length();
         int cpi = 0;
         int i = 0;

         while (i < a.length() - extraOffsetRaw) {
            if (cpi == index) {
               return i;
            }

            int length = unwrap(jCoding).length(arrayA, offset + i, end);
            if (length < 1) {
               if (length < -1) {
                  if (isLength) {
                     return a.length() - extraOffsetRaw;
                  }

                  throw InternalErrors.indexOutOfBounds();
               }

               i++;
            } else {
               i += length;
            }

            TStringConstants.truffleSafePointPoll(location, ++cpi);
         }

         return TStringInternalNodes.CodePointIndexToRawNode.atEnd(a, extraOffsetRaw, index, isLength, cpi);
      }
   }

   @Override
   public int decode(AbstractTruffleString a, byte[] arrayA, int rawIndex, JCodings.Encoding jCoding, TruffleString.ErrorHandling errorHandling) {
      int p = a.byteArrayOffset() + rawIndex;
      int end = a.byteArrayOffset() + a.length();
      int length = this.getCodePointLength(jCoding, arrayA, p, end);
      return length < 1 ? Encodings.invalidCodepointReturnValue(errorHandling) : this.readCodePoint(jCoding, arrayA, p, end);
   }

   @Override
   public long calcStringAttributes(
      Node location,
      Object array,
      int offset,
      int length,
      TruffleString.Encoding encoding,
      ConditionProfile validCharacterProfile,
      ConditionProfile fixedWidthProfile
   ) {
      if (TStringGuards.is7BitCompatible(encoding) && TStringOps.calcStringAttributesLatin1(location, array, offset, length) == TSCodeRange.get7Bit()) {
         return StringAttributes.create(length, TSCodeRange.get7Bit());
      } else {
         byte[] bytes = JCodings.asByteArray(array);
         int offsetBytes = array instanceof AbstractTruffleString.NativePointer ? offset - ((AbstractTruffleString.NativePointer)array).offset() : offset;
         JCodings.Encoding enc = this.get(encoding);
         int codeRange = this.isSingleByte(enc) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte();
         int characters = 0;
         int p = offsetBytes;
         int end = offsetBytes + length;

         for (int loopCount = 0; p < end; characters++) {
            int lengthOfCurrentCharacter = this.getCodePointLength(enc, bytes, p, end);
            if (validCharacterProfile.profile(lengthOfCurrentCharacter > 0 && p + lengthOfCurrentCharacter <= end)) {
               p += lengthOfCurrentCharacter;
            } else {
               codeRange = this.isSingleByte(enc) ? TSCodeRange.getBrokenFixedWidth() : TSCodeRange.getBrokenMultiByte();
               if (fixedWidthProfile.profile(this.isFixedWidth(enc))) {
                  characters = (length + this.minLength(enc) - 1) / this.minLength(enc);
                  return StringAttributes.create(characters, codeRange);
               }

               p += this.minLength(enc);
            }

            TStringConstants.truffleSafePointPoll(location, ++loopCount);
         }

         return StringAttributes.create(characters, codeRange);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static EConv getEconvTranscoder(JCodings.Encoding jCodingSrc, JCodings.Encoding jCodingDst) {
      return TranscoderDB.open(unwrap(jCodingSrc).getName(), unwrap(jCodingDst).getName(), 34);
   }

   @CompilerDirectives.TruffleBoundary
   private static void econvSetReplacement(JCodings.Encoding jCodingDst, EConv econv, byte[] replacement) {
      econv.setReplacement(replacement, 0, replacement.length, unwrap(jCodingDst).getName());
   }

   @CompilerDirectives.TruffleBoundary
   private static EConvResult econvConvert(byte[] arrayA, byte[] buffer, EConv econv, Ptr srcPtr, Ptr dstPtr, int inStop) {
      return econv.convert(arrayA, srcPtr, inStop, buffer, dstPtr, buffer.length, 0);
   }

   @Override
   public TruffleString transcode(
      Node location,
      AbstractTruffleString a,
      Object arrayA,
      int codePointLengthA,
      TruffleString.Encoding targetEncoding,
      BranchProfile outOfMemoryProfile,
      ConditionProfile nativeProfile,
      TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode
   ) {
      TruffleString.Encoding encoding = TruffleString.Encoding.get(a.encoding());
      JCodings.Encoding jCodingSrc;
      if (TStringGuards.isUTF16Or32(encoding) && TStringGuards.isStride0(a)) {
         jCodingSrc = TruffleString.Encoding.ISO_8859_1.jCoding;
      } else if (TStringGuards.isUTF32(encoding) && TStringGuards.isStride1(a)) {
         jCodingSrc = TruffleString.Encoding.UTF_16.jCoding;
      } else {
         jCodingSrc = JCodings.getInstance().get(encoding);
      }

      JCodings.Encoding jCodingDst = JCodings.getInstance().get(targetEncoding);
      byte[] buffer = new byte[(int)Math.min(2147483639L, (long)codePointLengthA * JCodings.getInstance().maxLength(jCodingDst))];
      int length = 0;
      EConv econv = getEconvTranscoder(jCodingSrc, jCodingDst);
      boolean undefinedConversion = false;
      if (econv == null) {
         undefinedConversion = true;
         int loopCount = 0;

         for (int i = 0; i < codePointLengthA; i++) {
            int ret = JCodings.getInstance()
               .writeCodePoint(jCodingDst, !TStringGuards.isUTF8(targetEncoding) && !TStringGuards.isUTF16Or32(targetEncoding) ? 63 : '�', buffer, length);

            assert ret > 0;

            length += ret;
            TStringConstants.truffleSafePointPoll(location, ++loopCount);
         }
      } else {
         byte[] replacement;
         if (TStringGuards.isUTF8(targetEncoding)) {
            replacement = CONVERSION_REPLACEMENT_UTF_8;
         } else if (TStringGuards.isUTF16(targetEncoding)) {
            replacement = CONVERSION_REPLACEMENT_UTF_16;
         } else if (TStringGuards.isUTF32(targetEncoding)) {
            replacement = CONVERSION_REPLACEMENT_UTF_32;
         } else {
            replacement = CONVERSION_REPLACEMENT;
         }

         Ptr srcPtr = new Ptr();
         Ptr dstPtr = new Ptr();
         srcPtr.p = a.byteArrayOffset();
         dstPtr.p = 0;
         int inStop = a.byteArrayOffset() + (a.length() << a.stride());
         if (arrayA instanceof AbstractTruffleString.NativePointer) {
            ((AbstractTruffleString.NativePointer)arrayA).materializeByteArray(a, nativeProfile);
         }

         byte[] bytes = JCodings.asByteArray(arrayA);

         for (EConvResult result = econvConvert(bytes, buffer, econv, srcPtr, dstPtr, inStop);
            !result.isFinished();
            result = econvConvert(bytes, buffer, econv, srcPtr, dstPtr, inStop)
         ) {
            if (result.isUndefinedConversion()) {
               undefinedConversion = true;
               econvSetReplacement(jCodingDst, econv, replacement);
            } else {
               if (!result.isDestinationBufferFull()) {
                  throw CompilerDirectives.shouldNotReachHere();
               }

               if (buffer.length == 2147483639) {
                  outOfMemoryProfile.enter();
                  throw InternalErrors.outOfMemory();
               }

               buffer = Arrays.copyOf(buffer, (int)Math.min(2147483639L, (long)buffer.length << 1));
            }
         }

         length = dstPtr.p;
      }

      AbstractTruffleString.checkArrayRange(buffer, 0, length);
      return fromBufferWithStringCompactionNode.execute(
         buffer, 0, length, targetEncoding, length != buffer.length || targetEncoding.isSupported(), undefinedConversion || a.isMutable()
      );
   }

   @CompilerDirectives.TruffleBoundary
   private static String toEnumName(String encodingName) {
      if ("ASCII-8BIT".equals(encodingName)) {
         return "BYTES";
      } else {
         String capitalized = encodingName;
         if (Character.isLowerCase(encodingName.charAt(0))) {
            capitalized = Character.toUpperCase(encodingName.charAt(0)) + encodingName.substring(1);
         }

         return capitalized.replace('-', '_');
      }
   }

   private static org.graalvm.shadowed.org.jcodings.Encoding unwrap(JCodings.Encoding wrapped) {
      return ((JCodingsImpl.EncodingWrapper)wrapped).encoding;
   }

   private static final class EncodingWrapper implements JCodings.Encoding {
      private final org.graalvm.shadowed.org.jcodings.Encoding encoding;

      private EncodingWrapper(org.graalvm.shadowed.org.jcodings.Encoding encoding) {
         this.encoding = encoding;
      }
   }
}
