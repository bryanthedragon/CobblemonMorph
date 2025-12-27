package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;

interface JCodings {
   boolean ENABLED = !TruffleOptions.AOT || TStringAccessor.getNeedsAllEncodings();
   JCodings INSTANCE = (JCodings)(ENABLED ? new JCodingsImpl() : new JCodingsDisabled());

   static JCodings getInstance() {
      return INSTANCE;
   }

   static byte[] asByteArray(Object array) {
      return array instanceof AbstractTruffleString.NativePointer ? ((AbstractTruffleString.NativePointer)array).getBytes() : (byte[])array;
   }

   JCodings.Encoding get(String encodingName);

   JCodings.Encoding get(TruffleString.Encoding encoding);

   String name(JCodings.Encoding jCoding);

   int minLength(JCodings.Encoding enc);

   int maxLength(JCodings.Encoding e);

   boolean isFixedWidth(JCodings.Encoding enc);

   boolean isSingleByte(JCodings.Encoding enc);

   @CompilerDirectives.TruffleBoundary
   int getCodePointLength(JCodings.Encoding jCoding, int codepoint);

   @CompilerDirectives.TruffleBoundary
   int getPreviousCodePointIndex(JCodings.Encoding jCoding, byte[] array, int arrayBegin, int index, int arrayEnd);

   @CompilerDirectives.TruffleBoundary
   int getCodePointLength(JCodings.Encoding jCoding, byte[] array, int index, int arrayLength);

   @CompilerDirectives.TruffleBoundary
   int readCodePoint(JCodings.Encoding jCoding, byte[] array, int index, int arrayEnd);

   @CompilerDirectives.TruffleBoundary
   int writeCodePoint(JCodings.Encoding jCoding, int codepoint, byte[] array, int index);

   @CompilerDirectives.TruffleBoundary
   int codePointIndexToRaw(Node location, AbstractTruffleString a, byte[] arrayA, int extraOffsetRaw, int index, boolean isLength, JCodings.Encoding jCoding);

   int decode(AbstractTruffleString a, byte[] arrayA, int rawIndex, JCodings.Encoding jCoding, TruffleString.ErrorHandling errorHandling);

   long calcStringAttributes(
      Node location,
      Object array,
      int offset,
      int length,
      TruffleString.Encoding encoding,
      ConditionProfile validCharacterProfile,
      ConditionProfile fixedWidthProfile
   );

   TruffleString transcode(
      Node location,
      AbstractTruffleString a,
      Object arrayA,
      int codePointLengthA,
      TruffleString.Encoding targetEncoding,
      BranchProfile outOfMemoryProfile,
      ConditionProfile nativeProfile,
      TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode
   );

   public interface Encoding {
   }
}
