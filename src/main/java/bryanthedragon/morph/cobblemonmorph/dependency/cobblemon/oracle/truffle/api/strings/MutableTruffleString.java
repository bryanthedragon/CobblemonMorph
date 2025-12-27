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
import com.oracle.truffle.api.profiles.ValueProfile;
import java.util.Arrays;

public final class MutableTruffleString extends AbstractTruffleString {
   private int codePointLength = -1;
   private byte codeRange = (byte)TSCodeRange.getUnknown();

   private MutableTruffleString(Object data, int offset, int length, int stride, TruffleString.Encoding encoding) {
      super(data, offset, length, stride, encoding, 0);

      assert data instanceof byte[] || data instanceof AbstractTruffleString.NativePointer;

      if (TruffleString.Encoding.isFixedWidth(encoding)) {
         this.codePointLength = encoding.isSupported() ? length : length / JCodings.getInstance().minLength(encoding.jCoding);
      }
   }

   private static MutableTruffleString create(Object data, int offset, int length, TruffleString.Encoding encoding) {
      MutableTruffleString string = new MutableTruffleString(data, offset, length, encoding.naturalStride, encoding);
      if (AbstractTruffleString.DEBUG_ALWAYS_CREATE_JAVA_STRING) {
         string.toJavaStringUncached();
      }

      return string;
   }

   int codePointLength() {
      return this.codePointLength;
   }

   int codeRange() {
      return this.codeRange;
   }

   void updateCachedAttributes(int newCodePointLength, int newCodeRange) {
      assert newCodePointLength >= 0;

      assert TSCodeRange.isCodeRange(newCodeRange);

      this.codePointLength = newCodePointLength;
      this.codeRange = (byte)newCodeRange;
   }

   void invalidateCachedAttributes() {
      if (!TruffleString.Encoding.isFixedWidth(this.encoding())) {
         this.codePointLength = -1;
      }

      this.codeRange = (byte)TSCodeRange.getUnknown();
      this.hashCode = 0;
      if (this.data() instanceof AbstractTruffleString.NativePointer) {
         ((AbstractTruffleString.NativePointer)this.data()).invalidateCachedByteArray();
      }
   }

   public void notifyExternalMutation() {
      this.invalidateCachedAttributes();
   }

   @CompilerDirectives.TruffleBoundary
   public static MutableTruffleString fromByteArrayUncached(byte[] value, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy) {
      return MutableTruffleString.FromByteArrayNode.getUncached().execute(value, byteOffset, byteLength, encoding, copy);
   }

   @CompilerDirectives.TruffleBoundary
   public static MutableTruffleString fromNativePointerUncached(
      Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy
   ) {
      return MutableTruffleString.FromNativePointerNode.getUncached().execute(pointerObject, byteOffset, byteLength, encoding, copy);
   }

   @CompilerDirectives.TruffleBoundary
   public void writeByteUncached(int byteIndex, byte value, TruffleString.Encoding expectedEncoding) {
      MutableTruffleString.WriteByteNode.getUncached().execute(this, byteIndex, value, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public MutableTruffleString concatUncached(AbstractTruffleString b, TruffleString.Encoding expectedEncoding) {
      return MutableTruffleString.ConcatNode.getUncached().execute(this, b, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public MutableTruffleString substringUncached(int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding) {
      return MutableTruffleString.SubstringNode.getUncached().execute(this, byteOffset, byteLength, expectedEncoding);
   }

   @CompilerDirectives.TruffleBoundary
   public MutableTruffleString substringByteIndexUncached(int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding) {
      return MutableTruffleString.SubstringByteIndexNode.getUncached().execute(this, byteOffset, byteLength, expectedEncoding);
   }

   static MutableTruffleString createCopying(AbstractTruffleString a, TruffleString.Encoding encoding, TruffleString.CopyToByteArrayNode copyToByteArrayNode) {
      return createCopying(a, encoding, encoding, a.byteLength(encoding), copyToByteArrayNode);
   }

   static MutableTruffleString createCopying(
      AbstractTruffleString a,
      TruffleString.Encoding expectedEncoding,
      TruffleString.Encoding targetEncoding,
      TruffleString.CopyToByteArrayNode copyToByteArrayNode
   ) {
      int byteLength = a.byteLength(expectedEncoding);
      checkByteLength(byteLength, targetEncoding);
      return createCopying(a, expectedEncoding, targetEncoding, byteLength, copyToByteArrayNode);
   }

   static MutableTruffleString createCopying(
      AbstractTruffleString a,
      TruffleString.Encoding expectedEncoding,
      TruffleString.Encoding targetEncoding,
      int byteLength,
      TruffleString.CopyToByteArrayNode copyToByteArrayNode
   ) {
      byte[] array = new byte[byteLength];
      copyToByteArrayNode.execute(a, 0, array, 0, byteLength, expectedEncoding);
      return create(array, 0, byteLength >> targetEncoding.naturalStride, targetEncoding);
   }

   @GeneratePackagePrivate
   @ImportStatic({TStringGuards.class, TStringAccessor.class})
   @GenerateUncached
   public abstract static class AsManagedNode extends Node {
      AsManagedNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization(guards = "!a.isNative()")
      static MutableTruffleString mutable(MutableTruffleString a, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);
         return a;
      }

      @Specialization(guards = "a.isNative() || a.isImmutable()")
      static MutableTruffleString fromTruffleString(
         AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         return MutableTruffleString.createCopying(a, expectedEncoding, copyToByteArrayNode);
      }

      public static MutableTruffleString.AsManagedNode create() {
         return MutableTruffleStringFactory.AsManagedNodeGen.create();
      }

      public static MutableTruffleString.AsManagedNode getUncached() {
         return MutableTruffleStringFactory.AsManagedNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic({TStringGuards.class, TStringAccessor.class})
   @GenerateUncached
   public abstract static class AsMutableTruffleStringNode extends Node {
      AsMutableTruffleStringNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static MutableTruffleString mutable(MutableTruffleString a, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);
         return a;
      }

      @Specialization
      static MutableTruffleString fromTruffleString(
         TruffleString a, TruffleString.Encoding expectedEncoding, @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         return MutableTruffleString.createCopying(a, expectedEncoding, copyToByteArrayNode);
      }

      public static MutableTruffleString.AsMutableTruffleStringNode create() {
         return MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.create();
      }

      public static MutableTruffleString.AsMutableTruffleStringNode getUncached() {
         return MutableTruffleStringFactory.AsMutableTruffleStringNodeGen.getUncached();
      }
   }

   @GenerateUncached
   abstract static class CalcLazyAttributesNode extends Node {
      abstract void execute(MutableTruffleString a);

      @Specialization
      void calc(
         MutableTruffleString a,
         @Cached("createClassProfile()") ValueProfile dataClassProfile,
         @Cached ConditionProfile asciiBytesLatinProfile,
         @Cached ConditionProfile utf8Profile,
         @Cached ConditionProfile utf8BrokenProfile,
         @Cached ConditionProfile utf16Profile,
         @Cached ConditionProfile utf16S0Profile,
         @Cached ConditionProfile utf32Profile,
         @Cached ConditionProfile utf32S0Profile,
         @Cached ConditionProfile utf32S1Profile,
         @Cached ConditionProfile exoticMaterializeNativeProfile,
         @Cached ConditionProfile exoticValidProfile,
         @Cached ConditionProfile exoticFixedWidthProfile
      ) {
         Object data = dataClassProfile.profile(a.data());
         int encoding = a.encoding();
         int offset = a.offset();
         int length = a.length();
         int codePointLength;
         int codeRange;
         if (utf16Profile.profile(TStringGuards.isUTF16(encoding))) {
            if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
               codeRange = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
               codePointLength = length;
            } else {
               assert TStringGuards.isStride1(a);

               long attrs = TStringOps.calcStringAttributesUTF16(this, data, offset, length, false);
               codePointLength = StringAttributes.getCodePointLength(attrs);
               codeRange = StringAttributes.getCodeRange(attrs);
            }
         } else if (utf32Profile.profile(TStringGuards.isUTF32(encoding))) {
            if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
               codeRange = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
            } else if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
               codeRange = TStringOps.calcStringAttributesBMP(this, data, offset, length);
            } else {
               assert TStringGuards.isStride2(a);

               codeRange = TStringOps.calcStringAttributesUTF32(this, data, offset, length);
            }

            codePointLength = length;
         } else if (utf8Profile.profile(TStringGuards.isUTF8(encoding))) {
            long attrs = TStringOps.calcStringAttributesUTF8(this, data, offset, length, false, false, utf8BrokenProfile);
            codeRange = StringAttributes.getCodeRange(attrs);
            codePointLength = StringAttributes.getCodePointLength(attrs);
         } else if (asciiBytesLatinProfile.profile(TStringGuards.isAsciiBytesOrLatin1(encoding))) {
            int cr = TStringOps.calcStringAttributesLatin1(this, data, offset, length);
            codeRange = TStringGuards.is8Bit(cr) ? TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding) : cr;
            codePointLength = length;
         } else {
            if (data instanceof AbstractTruffleString.NativePointer) {
               ((AbstractTruffleString.NativePointer)data).materializeByteArray(a, exoticMaterializeNativeProfile);
            }

            long attrs = JCodings.getInstance()
               .calcStringAttributes(this, data, offset, length, TruffleString.Encoding.get(encoding), exoticValidProfile, exoticFixedWidthProfile);
            codeRange = StringAttributes.getCodeRange(attrs);
            codePointLength = StringAttributes.getCodePointLength(attrs);
         }

         a.updateCachedAttributes(codePointLength, codeRange);
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ConcatNode extends Node {
      ConcatNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding);

      @Specialization
      static MutableTruffleString concat(
         AbstractTruffleString a,
         AbstractTruffleString b,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.ConcatMaterializeBytesNode materializeBytesNode,
         @Cached BranchProfile outOfMemoryProfile
      ) {
         a.checkEncoding(expectedEncoding);
         b.checkEncoding(expectedEncoding);
         int length = TruffleString.ConcatNode.addByteLengths(a, b, expectedEncoding.naturalStride, outOfMemoryProfile);
         int offset = 0;
         byte[] array = materializeBytesNode.execute(
            a, toIndexableNodeA.execute(a, a.data()), b, toIndexableNodeB.execute(b, b.data()), expectedEncoding, length, expectedEncoding.naturalStride
         );
         return MutableTruffleString.create(array, offset, length, expectedEncoding);
      }

      public static MutableTruffleString.ConcatNode create() {
         return MutableTruffleStringFactory.ConcatNodeGen.create();
      }

      public static MutableTruffleString.ConcatNode getUncached() {
         return MutableTruffleStringFactory.ConcatNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ForceEncodingNode extends Node {
      ForceEncodingNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding);

      @Specialization(guards = "a.isCompatibleTo(targetEncoding)")
      static MutableTruffleString compatible(MutableTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding) {
         a.checkEncoding(expectedEncoding);
         return a;
      }

      @Specialization(guards = "!a.isCompatibleTo(targetEncoding) || a.isImmutable()")
      static MutableTruffleString reinterpret(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         TruffleString.Encoding targetEncoding,
         @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         a.checkEncoding(expectedEncoding);
         return MutableTruffleString.createCopying(a, expectedEncoding, targetEncoding, copyToByteArrayNode);
      }

      public static MutableTruffleString.ForceEncodingNode create() {
         return MutableTruffleStringFactory.ForceEncodingNodeGen.create();
      }

      public static MutableTruffleString.ForceEncodingNode getUncached() {
         return MutableTruffleStringFactory.ForceEncodingNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromByteArrayNode extends Node {
      FromByteArrayNode() {
      }

      public abstract MutableTruffleString execute(byte[] value, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy);

      @Specialization
      static MutableTruffleString fromByteArray(byte[] value, int byteOffset, int byteLength, TruffleString.Encoding enc, boolean copy) {
         AbstractTruffleString.checkArrayRange(value, byteOffset, byteLength);
         AbstractTruffleString.checkByteLength(byteLength, enc);
         byte[] array;
         int offset;
         if (copy) {
            array = Arrays.copyOfRange(value, byteOffset, byteOffset + byteLength);
            offset = 0;
         } else {
            array = value;
            offset = byteOffset;
         }

         return MutableTruffleString.create(array, offset, byteLength >> enc.naturalStride, enc);
      }

      public static MutableTruffleString.FromByteArrayNode create() {
         return MutableTruffleStringFactory.FromByteArrayNodeGen.create();
      }

      public static MutableTruffleString.FromByteArrayNode getUncached() {
         return MutableTruffleStringFactory.FromByteArrayNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic({TStringGuards.class, TStringAccessor.class})
   @GenerateUncached
   public abstract static class FromNativePointerNode extends Node {
      FromNativePointerNode() {
      }

      public abstract MutableTruffleString execute(Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy);

      @Specialization
      MutableTruffleString fromNativePointer(
         Object pointerObject,
         int byteOffset,
         int byteLength,
         TruffleString.Encoding enc,
         boolean copy,
         @Cached(value = "createInteropLibrary()", uncached = "getUncachedInteropLibrary()") Node interopLibrary
      ) {
         AbstractTruffleString.checkByteLength(byteLength, enc);
         AbstractTruffleString.NativePointer nativePointer = AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteOffset);
         Object array;
         int offset;
         if (copy) {
            array = TStringOps.arraycopyOfWithStride(this, nativePointer, byteOffset, byteLength, 0, byteLength, 0);
            offset = 0;
         } else {
            array = nativePointer;
            offset = byteOffset;
         }

         return MutableTruffleString.create(array, offset, byteLength >> enc.naturalStride, enc);
      }

      public static MutableTruffleString.FromNativePointerNode create() {
         return MutableTruffleStringFactory.FromNativePointerNodeGen.create();
      }

      public static MutableTruffleString.FromNativePointerNode getUncached() {
         return MutableTruffleStringFactory.FromNativePointerNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class SubstringByteIndexNode extends Node {
      SubstringByteIndexNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, int byteOffset, int byteLength, TruffleString.Encoding expectedEncoding);

      @Specialization
      static MutableTruffleString substringByteIndex(
         AbstractTruffleString a,
         int byteOffset,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         return createSubstring(a, byteOffset, byteLength, expectedEncoding, copyToByteArrayNode);
      }

      static MutableTruffleString createSubstring(
         AbstractTruffleString a,
         int byteOffset,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         a.checkEncoding(expectedEncoding);
         AbstractTruffleString.checkByteLength(byteLength, expectedEncoding);
         a.boundsCheckRegionRaw(AbstractTruffleString.rawIndex(byteOffset, expectedEncoding), AbstractTruffleString.rawIndex(byteLength, expectedEncoding));
         byte[] array = new byte[byteLength];
         copyToByteArrayNode.execute(a, byteOffset, array, 0, byteLength, expectedEncoding);
         return MutableTruffleString.create(array, 0, byteLength >> expectedEncoding.naturalStride, expectedEncoding);
      }

      public static MutableTruffleString.SubstringByteIndexNode create() {
         return MutableTruffleStringFactory.SubstringByteIndexNodeGen.create();
      }

      public static MutableTruffleString.SubstringByteIndexNode getUncached() {
         return MutableTruffleStringFactory.SubstringByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class SubstringNode extends Node {
      SubstringNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, int fromIndex, int length, TruffleString.Encoding expectedEncoding);

      @Specialization
      static MutableTruffleString substring(
         AbstractTruffleString a,
         int fromIndex,
         int length,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.CodePointIndexToRawNode translateIndexNode,
         @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode
      ) {
         a.checkEncoding(expectedEncoding);
         a.boundsCheckRegion(fromIndex, length, getCodePointLengthNode);
         Object arrayA = toIndexableNode.execute(a, a.data());
         int codeRangeA = getCodeRangeANode.execute(a);
         int fromIndexRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, 0, fromIndex, length == 0);
         int lengthRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, length, true);
         int stride = expectedEncoding.naturalStride;
         return MutableTruffleString.SubstringByteIndexNode.createSubstring(
            a, fromIndexRaw << stride, lengthRaw << stride, expectedEncoding, copyToByteArrayNode
         );
      }

      public static MutableTruffleString.SubstringNode create() {
         return MutableTruffleStringFactory.SubstringNodeGen.create();
      }

      public static MutableTruffleString.SubstringNode getUncached() {
         return MutableTruffleStringFactory.SubstringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class SwitchEncodingNode extends Node {
      SwitchEncodingNode() {
      }

      public abstract MutableTruffleString execute(AbstractTruffleString a, TruffleString.Encoding encoding);

      @Specialization(guards = "a.isCompatibleTo(encoding)")
      static MutableTruffleString compatibleMutable(MutableTruffleString a, TruffleString.Encoding encoding) {
         return a;
      }

      @Specialization(guards = "!a.isCompatibleTo(encoding) || a.isImmutable()")
      static MutableTruffleString transcodeAndCopy(
         AbstractTruffleString a,
         TruffleString.Encoding encoding,
         @Cached TruffleString.SwitchEncodingNode switchEncodingNode,
         @Cached MutableTruffleString.AsMutableTruffleStringNode asMutableTruffleStringNode
      ) {
         TruffleString switched = switchEncodingNode.execute(a, encoding);
         return asMutableTruffleStringNode.execute(switched, encoding);
      }

      public static MutableTruffleString.SwitchEncodingNode create() {
         return MutableTruffleStringFactory.SwitchEncodingNodeGen.create();
      }

      public static MutableTruffleString.SwitchEncodingNode getUncached() {
         return MutableTruffleStringFactory.SwitchEncodingNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class WriteByteNode extends Node {
      WriteByteNode() {
      }

      public abstract void execute(MutableTruffleString a, int byteIndex, byte value, TruffleString.Encoding expectedEncoding);

      @Specialization
      static void writeByte(MutableTruffleString a, int byteIndex, byte value, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);
         int byteLength = a.length() << a.stride();
         TruffleString.boundsCheckI(byteIndex, byteLength);
         TStringOps.writeS0(a.data(), a.offset(), byteLength, byteIndex, value);
         if (!TSCodeRange.is7Bit(a.codeRange) || value < 0) {
            a.invalidateCachedAttributes();
         }
      }

      public static MutableTruffleString.WriteByteNode create() {
         return MutableTruffleStringFactory.WriteByteNodeGen.create();
      }

      public static MutableTruffleString.WriteByteNode getUncached() {
         return MutableTruffleStringFactory.WriteByteNodeGen.getUncached();
      }
   }
}
