package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GeneratePackagePrivate;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.IntValueProfile;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.BitSet;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;

public final class TruffleString extends AbstractTruffleString {
   private static final VarHandle NEXT_UPDATER = initializeNextUpdater();
   private static final byte FLAG_CACHE_HEAD = -128;
   private final int codePointLength;
   private final byte codeRange;
   TruffleString next;

   @CompilerDirectives.TruffleBoundary
   private static VarHandle initializeNextUpdater() {
      try {
         return MethodHandles.lookup().findVarHandle(TruffleString.class, "next", TruffleString.class);
      } catch (IllegalAccessException | NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }

   private TruffleString(
      Object data, int offset, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead
   ) {
      super(data, offset, length, stride, encoding, isCacheHead ? -128 : 0);

      assert codePointLength >= 0;

      assert validateCodeRange(encoding, codeRange);

      this.codePointLength = codePointLength;
      this.codeRange = (byte)codeRange;
   }

   private static TruffleString create(
      Object data, int offset, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead
   ) {
      TruffleString string = new TruffleString(data, offset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
      if (AbstractTruffleString.DEBUG_ALWAYS_CREATE_JAVA_STRING) {
         string.toJavaStringUncached();
      }

      return string;
   }

   private static boolean validateCodeRange(TruffleString.Encoding encoding, int codeRange) {
      assert isByte(codeRange);

      assert TSCodeRange.isCodeRange(codeRange);

      assert !TStringGuards.isAscii(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.isBrokenFixedWidth(codeRange);

      assert !TStringGuards.isLatin1(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.is8Bit(codeRange);

      assert !TStringGuards.isUTF8(encoding)
         || !TStringGuards.is8Bit(codeRange)
            && !TStringGuards.is16Bit(codeRange)
            && !TStringGuards.isValidFixedWidth(codeRange)
            && !TStringGuards.isBrokenFixedWidth(codeRange);

      assert !TStringGuards.isUTF16(encoding) || !TStringGuards.isValidFixedWidth(codeRange) && !TStringGuards.isBrokenFixedWidth(codeRange);

      assert !TStringGuards.isUTF32(encoding) || !TStringGuards.isValidMultiByte(codeRange) && !TStringGuards.isBrokenMultiByte(codeRange);

      assert !TStringGuards.isBytes(encoding) || TStringGuards.is7Bit(codeRange) || TStringGuards.isValidFixedWidth(codeRange);

      return true;
   }

   static TruffleString createFromByteArray(byte[] bytes, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange) {
      return createFromByteArray(bytes, length, stride, encoding, codePointLength, codeRange, true);
   }

   static TruffleString createFromByteArray(
      byte[] bytes, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead
   ) {
      return createFromArray(bytes, 0, length, stride, encoding, codePointLength, codeRange, isCacheHead);
   }

   static TruffleString createFromArray(Object bytes, int offset, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange) {
      return createFromArray(bytes, offset, length, stride, encoding, codePointLength, codeRange, true);
   }

   static TruffleString createFromArray(
      Object bytes, int offset, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead
   ) {
      assert bytes instanceof byte[] || TStringGuards.isInlinedJavaString(bytes) || bytes instanceof AbstractTruffleString.NativePointer;

      assert offset >= 0;

      assert bytes instanceof AbstractTruffleString.NativePointer || (long)offset + ((long)length << stride) <= TStringOps.byteLength(bytes);

      assert attrsAreCorrect(bytes, encoding, offset, length, codePointLength, codeRange, stride);

      if (DEBUG_NON_ZERO_OFFSET && bytes instanceof byte[]) {
         int byteLength = Math.toIntExact((long)length << stride);
         byte[] copy = new byte[byteLength + byteLength];
         System.arraycopy(bytes, offset, copy, byteLength, byteLength);
         return create(copy, byteLength, length, stride, encoding, codePointLength, codeRange, isCacheHead);
      } else {
         return create(bytes, offset, length, stride, encoding, codePointLength, codeRange, isCacheHead);
      }
   }

   static TruffleString createConstant(byte[] bytes, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange) {
      return createConstant(bytes, length, stride, encoding, codePointLength, codeRange, true);
   }

   static TruffleString createConstant(
      byte[] bytes, int length, int stride, TruffleString.Encoding encoding, int codePointLength, int codeRange, boolean isCacheHead
   ) {
      TruffleString ret = createFromByteArray(bytes, length, stride, encoding, codePointLength, codeRange, isCacheHead);
      ret.hashCode();
      return ret;
   }

   static TruffleString createLazyLong(long value, TruffleString.Encoding encoding) {
      int length = NumberConversion.stringLengthLong(value);
      return create(new AbstractTruffleString.LazyLong(value), 0, length, 0, encoding, length, TSCodeRange.get7Bit(), true);
   }

   static TruffleString createLazyConcat(TruffleString a, TruffleString b, TruffleString.Encoding encoding, int length, int stride) {
      assert !TSCodeRange.isBrokenMultiByte(a.codeRange());

      assert !TSCodeRange.isBrokenMultiByte(b.codeRange());

      assert a.isLooselyCompatibleTo(encoding);

      assert b.isLooselyCompatibleTo(encoding);

      assert length == a.length() + b.length();

      int codeRange = TSCodeRange.commonCodeRange(a.codeRange(), b.codeRange());
      return create(new AbstractTruffleString.LazyConcat(a, b), 0, length, stride, encoding, a.codePointLength() + b.codePointLength(), codeRange, true);
   }

   static TruffleString createWrapJavaString(String str, int codePointLength, int codeRange) {
      int stride = TStringUnsafe.getJavaStringStride(str);
      return create(str, 0, str.length(), stride, TruffleString.Encoding.UTF_16, codePointLength, codeRange, false);
   }

   private static boolean attrsAreCorrect(Object bytes, TruffleString.Encoding encoding, int offset, int length, int codePointLength, int codeRange, int stride) {
      CompilerAsserts.neverPartOfCompilation();
      if (length == 0) {
         int length0CodeRange = TStringGuards.is7BitCompatible(encoding)
            ? TSCodeRange.get7Bit()
            : (JCodings.getInstance().isSingleByte(encoding.jCoding) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte());
         return TStringOps.byteLength(bytes) == 0L && offset == 0 && codePointLength == 0 && codeRange == length0CodeRange && stride == 0;
      } else {
         int knownCodeRange = TSCodeRange.getUnknown();
         if (TStringGuards.isUTF16Or32(encoding) && stride == 0) {
            knownCodeRange = TSCodeRange.get8Bit();
         } else if (TStringGuards.isUTF32(encoding) && stride == 1) {
            knownCodeRange = TSCodeRange.get16Bit();
         }

         if (bytes instanceof AbstractTruffleString.NativePointer) {
            ((AbstractTruffleString.NativePointer)bytes).materializeByteArray(length << stride, ConditionProfile.getUncached());
         }

         long attrs = TStringInternalNodes.CalcStringAttributesNode.getUncached().execute(null, bytes, offset, length, stride, encoding, knownCodeRange);
         int cpLengthCalc = StringAttributes.getCodePointLength(attrs);
         int codeRangeCalc = StringAttributes.getCodeRange(attrs);

         assert cpLengthCalc == codePointLength : "inconsistent codePointLength: " + cpLengthCalc + " != " + codePointLength;

         assert codeRangeCalc == codeRange : "inconsistent codeRange: " + TSCodeRange.toString(codeRangeCalc) + " != " + TSCodeRange.toString(codeRange);

         return attrs == StringAttributes.create(codePointLength, codeRange);
      }
   }

   boolean isLooselyCompatibleTo(TruffleString.Encoding expectedEncoding) {
      return this.isLooselyCompatibleTo(expectedEncoding.id, expectedEncoding.maxCompatibleCodeRange, this.codeRange());
   }

   int codePointLength() {
      return this.codePointLength;
   }

   int codeRange() {
      return this.codeRange;
   }

   boolean isCacheHead() {
      assert (this.flags() & -128) != 0 == this.flags() < 0;

      return this.flags() < 0;
   }

   TruffleString getCacheHead() {
      assert this.cacheRingIsValid();

      TruffleString cur = this.next;
      if (cur == null) {
         assert this.isCacheHead();

         return this;
      } else {
         while (!cur.isCacheHead()) {
            cur = cur.next;
         }

         return cur;
      }
   }

   @CompilerDirectives.TruffleBoundary
   void cacheInsert(TruffleString entry) {
      assert !entry.isCacheHead();

      TruffleString cacheHead = this.getCacheHead();

      assert !cacheEntryEquals(cacheHead, entry);

      TruffleString cacheHeadNext;
      do {
         cacheHeadNext = cacheHead.next;
         if (hasDuplicateEncoding(cacheHead, cacheHeadNext, entry)) {
            return;
         }

         entry.next = cacheHeadNext == null ? cacheHead : cacheHeadNext;
      } while (!setNextAtomic(cacheHead, cacheHeadNext, entry));
   }

   void cacheInsertFirstBeforePublished(TruffleString entry) {
      assert !entry.isCacheHead();

      assert this.isCacheHead();

      assert this.next == null;

      entry.next = this;
      this.next = entry;
   }

   private static boolean hasDuplicateEncoding(TruffleString cacheHead, TruffleString start, TruffleString insertEntry) {
      if (start == null) {
         return false;
      } else {
         for (TruffleString current = start; current != cacheHead; current = current.next) {
            if (cacheEntryEquals(insertEntry, current)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean cacheEntryEquals(TruffleString a, TruffleString b) {
      return b.encoding() == a.encoding() && (!TStringGuards.isUTF16(a.encoding()) || b.isJavaString() == a.isJavaString());
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean setNextAtomic(TruffleString cacheHead, TruffleString currentNext, TruffleString newNext) {
      return NEXT_UPDATER.compareAndSet((TruffleString)cacheHead, (TruffleString)currentNext, (TruffleString)newNext);
   }

   private boolean cacheRingIsValid() {
      CompilerAsserts.neverPartOfCompilation();
      TruffleString head = null;
      TruffleString cur = this;
      boolean javaStringVisited = false;
      BitSet visitedEncodings = new BitSet(TruffleString.Encoding.values().length);
      EconomicSet<TruffleString> visited = EconomicSet.create(Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE);

      do {
         if (cur.isCacheHead()) {
            assert head == null : "multiple cache heads";

            head = cur;
         }

         if (cur.isJavaString()) {
            assert !javaStringVisited : "duplicate cached java string";

            javaStringVisited = true;
         } else {
            assert !visitedEncodings.get(cur.encoding()) : "duplicate encoding";

            visitedEncodings.set(cur.encoding());
         }

         assert visited.add(cur) : "not a ring structure";

         cur = cur.next;
      } while (cur != this && cur != null);

      return true;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromCodePointUncached(int codepoint, TruffleString.Encoding encoding) {
      return TruffleString.FromCodePointNode.getUncached().execute(codepoint, encoding);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromCodePointUncached(int codepoint, TruffleString.Encoding encoding, boolean allowUTF16Surrogates) {
      return TruffleString.FromCodePointNode.getUncached().execute(codepoint, encoding, allowUTF16Surrogates);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromLongUncached(long value, TruffleString.Encoding encoding, boolean lazy) {
      return TruffleString.FromLongNode.getUncached().execute(value, encoding, lazy);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromByteArrayUncached(byte[] value, TruffleString.Encoding encoding) {
      return TruffleString.FromByteArrayNode.getUncached().execute(value, encoding);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromByteArrayUncached(byte[] value, TruffleString.Encoding encoding, boolean copy) {
      return TruffleString.FromByteArrayNode.getUncached().execute(value, encoding, copy);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromByteArrayUncached(byte[] value, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy) {
      return TruffleString.FromByteArrayNode.getUncached().execute(value, byteOffset, byteLength, encoding, copy);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromCharArrayUTF16Uncached(char[] value) {
      return TruffleString.FromCharArrayUTF16Node.getUncached().execute(value);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromCharArrayUTF16Uncached(char[] value, int charOffset, int charLength) {
      return TruffleString.FromCharArrayUTF16Node.getUncached().execute(value, charOffset, charLength);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromJavaStringUncached(String s, TruffleString.Encoding encoding) {
      return TruffleString.FromJavaStringNode.getUncached().execute(s, encoding);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromJavaStringUncached(String s, int charOffset, int length, TruffleString.Encoding encoding, boolean copy) {
      return TruffleString.FromJavaStringNode.getUncached().execute(s, charOffset, length, encoding, copy);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromIntArrayUTF32Uncached(int[] value) {
      return TruffleString.FromIntArrayUTF32Node.getUncached().execute(value);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromIntArrayUTF32Uncached(int[] value, int intOffset, int intLength) {
      return TruffleString.FromIntArrayUTF32Node.getUncached().execute(value, intOffset, intLength);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString fromNativePointerUncached(Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy) {
      return TruffleString.FromNativePointerNode.getUncached().execute(pointerObject, byteOffset, byteLength, encoding, copy);
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AsManagedNode extends Node {
      AsManagedNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization(guards = "!a.isNative()")
      static TruffleString managedImmutable(TruffleString a, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);

         assert !(a.data() instanceof AbstractTruffleString.NativePointer);

         return a;
      }

      @Specialization(guards = "a.isNative() || a.isMutable()")
      static TruffleString nativeOrMutable(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode
      ) {
         a.checkEncoding(expectedEncoding);
         Object data = a.data();

         assert data instanceof byte[] || data instanceof AbstractTruffleString.NativePointer;

         return fromBufferWithStringCompactionNode.execute(
            data, a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), getCodeRangeNode.execute(a)
         );
      }

      public static TruffleString.AsManagedNode create() {
         return TruffleStringFactory.AsManagedNodeGen.create();
      }

      public static TruffleString.AsManagedNode getUncached() {
         return TruffleStringFactory.AsManagedNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class AsTruffleStringNode extends Node {
      AsTruffleStringNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString value, TruffleString.Encoding expectedEncoding);

      @Specialization
      static TruffleString immutable(TruffleString a, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);
         return a;
      }

      @Specialization
      static TruffleString fromMutableString(
         MutableTruffleString a,
         TruffleString.Encoding expectedEncoding,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode
      ) {
         int codeRange = getCodeRangeNode.execute(a);
         a.looseCheckEncoding(expectedEncoding, codeRange);
         return fromBufferWithStringCompactionNode.execute(
            a.data(), a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), codeRange
         );
      }

      public static TruffleString.AsTruffleStringNode create() {
         return TruffleStringFactory.AsTruffleStringNodeGen.create();
      }

      public static TruffleString.AsTruffleStringNode getUncached() {
         return TruffleStringFactory.AsTruffleStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ByteIndexOfAnyByteNode extends Node {
      ByteIndexOfAnyByteNode() {
      }

      public abstract int execute(AbstractTruffleString a, int fromByteIndex, int maxByteIndex, byte[] values, TruffleString.Encoding expectedEncoding);

      @Specialization
      int indexOfRaw(
         AbstractTruffleString a,
         int fromByteIndex,
         int maxByteIndex,
         byte[] values,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode
      ) {
         if (TStringGuards.isUTF16Or32(expectedEncoding)) {
            throw InternalErrors.illegalArgument("UTF-16 and UTF-32 not supported!");
         } else {
            a.checkEncoding(expectedEncoding);
            if (a.isEmpty()) {
               return -1;
            } else {
               a.boundsCheckRaw(fromByteIndex, maxByteIndex);
               if (fromByteIndex != maxByteIndex && (!TSCodeRange.is7Bit(getCodeRangeNode.execute(a)) || !noneIsAscii(this, values))) {
                  assert TStringGuards.isStride0(a);

                  Object arrayA = toIndexableNode.execute(a, a.data());
                  return TStringOps.indexOfAnyByte(this, a, arrayA, fromByteIndex, maxByteIndex, values);
               } else {
                  return -1;
               }
            }
         }
      }

      private static boolean noneIsAscii(Node location, byte[] values) {
         for (int i = 0; i < values.length; i++) {
            if (Byte.toUnsignedInt(values[i]) <= 127) {
               return false;
            }

            TStringConstants.truffleSafePointPoll(location, i + 1);
         }

         return true;
      }

      public static TruffleString.ByteIndexOfAnyByteNode create() {
         return TruffleStringFactory.ByteIndexOfAnyByteNodeGen.create();
      }

      public static TruffleString.ByteIndexOfAnyByteNode getUncached() {
         return TruffleStringFactory.ByteIndexOfAnyByteNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ByteIndexOfCodePointNode extends Node {
      ByteIndexOfCodePointNode() {
      }

      public abstract int execute(AbstractTruffleString a, int codepoint, int fromByteIndex, int toByteIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int doIndexOf(
         AbstractTruffleString a,
         int codepoint,
         int fromByteIndex,
         int toByteIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.IndexOfCodePointRawNode indexOfNode
      ) {
         a.checkEncoding(expectedEncoding);
         if (a.isEmpty()) {
            return -1;
         } else {
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(fromIndex, toIndex);
            return AbstractTruffleString.byteIndex(
               indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex),
               expectedEncoding
            );
         }
      }

      public static TruffleString.ByteIndexOfCodePointNode create() {
         return TruffleStringFactory.ByteIndexOfCodePointNodeGen.create();
      }

      public static TruffleString.ByteIndexOfCodePointNode getUncached() {
         return TruffleStringFactory.ByteIndexOfCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ByteIndexOfStringNode extends Node {
      ByteIndexOfStringNode() {
      }

      public final int execute(AbstractTruffleString a, AbstractTruffleString b, int fromByteIndex, int toByteIndex, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, b, fromByteIndex, toByteIndex, null, expectedEncoding);
      }

      public final int execute(AbstractTruffleString a, TruffleString.WithMask b, int fromByteIndex, int toByteIndex, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, b.string, fromByteIndex, toByteIndex, b.mask, expectedEncoding);
      }

      abstract int execute(
         AbstractTruffleString a, AbstractTruffleString b, int fromByteIndex, int toByteIndex, byte[] mask, TruffleString.Encoding expectedEncoding
      );

      @Specialization
      static int indexOfString(
         AbstractTruffleString a,
         AbstractTruffleString b,
         int fromByteIndex,
         int toByteIndex,
         byte[] mask,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.IndexOfStringRawNode indexOfStringNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         if (mask != null && TStringGuards.isUnsupportedEncoding(expectedEncoding) && !TStringGuards.isFixedWidth(codeRangeA)) {
            throw InternalErrors.unsupportedOperation();
         } else if (b.isEmpty()) {
            return fromByteIndex;
         } else if (a.isEmpty()) {
            return -1;
         } else {
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(fromIndex, toIndex);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, toIndex - fromIndex)
               ? -1
               : AbstractTruffleString.byteIndex(
                  indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, mask, expectedEncoding), expectedEncoding
               );
         }
      }

      public static TruffleString.ByteIndexOfStringNode create() {
         return TruffleStringFactory.ByteIndexOfStringNodeGen.create();
      }

      public static TruffleString.ByteIndexOfStringNode getUncached() {
         return TruffleStringFactory.ByteIndexOfStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ByteIndexToCodePointIndexNode extends Node {
      ByteIndexToCodePointIndexNode() {
      }

      public abstract int execute(AbstractTruffleString a, int byteOffset, int byteIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int translate(
         AbstractTruffleString a,
         int byteOffset,
         int byteIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.RawIndexToCodePointIndexNode rawIndexToCodePointIndexNode
      ) {
         a.checkEncoding(expectedEncoding);
         int rawOffset = AbstractTruffleString.rawIndex(byteOffset, expectedEncoding);
         int rawIndex = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
         a.boundsCheckRegionRaw(rawOffset, rawIndex);
         if (byteIndex == 0) {
            return 0;
         } else {
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            return rawIndexToCodePointIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, a.offset() + byteOffset, rawIndex);
         }
      }

      public static TruffleString.ByteIndexToCodePointIndexNode create() {
         return TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.create();
      }

      public static TruffleString.ByteIndexToCodePointIndexNode getUncached() {
         return TruffleStringFactory.ByteIndexToCodePointIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ByteLengthOfCodePointNode extends Node {
      ByteLengthOfCodePointNode() {
      }

      public final int execute(AbstractTruffleString a, int byteIndex, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, byteIndex, expectedEncoding, TruffleString.ErrorHandling.BEST_EFFORT);
      }

      public abstract int execute(AbstractTruffleString a, int byteIndex, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling);

      @Specialization
      static int translate(
         AbstractTruffleString a,
         int byteIndex,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ErrorHandling errorHandling,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.ByteLengthOfCodePointNode byteLengthOfCodePointNode
      ) {
         CompilerAsserts.partialEvaluationConstant(errorHandling);
         a.checkEncoding(expectedEncoding);
         int rawIndex = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
         a.boundsCheckRaw(rawIndex);
         Object arrayA = toIndexableNode.execute(a, a.data());
         int codeRangeA = getCodeRangeNode.execute(a);
         return byteLengthOfCodePointNode.execute(a, arrayA, codeRangeA, expectedEncoding, rawIndex, errorHandling);
      }

      public static TruffleString.ByteLengthOfCodePointNode create() {
         return TruffleStringFactory.ByteLengthOfCodePointNodeGen.create();
      }

      public static TruffleString.ByteLengthOfCodePointNode getUncached() {
         return TruffleStringFactory.ByteLengthOfCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CharIndexOfAnyCharUTF16Node extends Node {
      CharIndexOfAnyCharUTF16Node() {
      }

      public abstract int execute(AbstractTruffleString a, int fromCharIndex, int maxCharIndex, char[] values);

      @Specialization
      int indexOfRaw(
         AbstractTruffleString a,
         int fromCharIndex,
         int maxCharIndex,
         char[] values,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringOpsNodes.IndexOfAnyCharNode indexOfNode
      ) {
         a.checkEncoding(TruffleString.Encoding.UTF_16);
         if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheckRaw(fromCharIndex, maxCharIndex);
            int codeRangeA = getCodeRangeNode.execute(a);
            return fromCharIndex != maxCharIndex && (!TSCodeRange.isFixedWidth(codeRangeA) || !noneInCodeRange(this, codeRangeA, values))
               ? indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), fromCharIndex, maxCharIndex, values)
               : -1;
         }
      }

      private static boolean noneInCodeRange(Node location, int codeRange, char[] values) {
         for (int i = 0; i < values.length; i++) {
            if (TSCodeRange.isInCodeRange(values[i], codeRange)) {
               return false;
            }

            TStringConstants.truffleSafePointPoll(location, i + 1);
         }

         return true;
      }

      public static TruffleString.CharIndexOfAnyCharUTF16Node create() {
         return TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.create();
      }

      public static TruffleString.CharIndexOfAnyCharUTF16Node getUncached() {
         return TruffleStringFactory.CharIndexOfAnyCharUTF16NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CodePointAtByteIndexNode extends Node {
      CodePointAtByteIndexNode() {
      }

      public final int execute(AbstractTruffleString a, int i, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, i, expectedEncoding, TruffleString.ErrorHandling.BEST_EFFORT);
      }

      public abstract int execute(AbstractTruffleString a, int i, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling);

      @Specialization
      static int readCodePoint(
         AbstractTruffleString a,
         int byteIndex,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ErrorHandling errorHandling,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.CodePointAtRawNode readCodePointNode
      ) {
         CompilerAsserts.partialEvaluationConstant(errorHandling);
         int i = AbstractTruffleString.rawIndex(byteIndex, expectedEncoding);
         a.checkEncoding(expectedEncoding);
         a.boundsCheckRaw(i);
         return readCodePointNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, i, errorHandling);
      }

      public static TruffleString.CodePointAtByteIndexNode create() {
         return TruffleStringFactory.CodePointAtByteIndexNodeGen.create();
      }

      public static TruffleString.CodePointAtByteIndexNode getUncached() {
         return TruffleStringFactory.CodePointAtByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CodePointAtIndexNode extends Node {
      CodePointAtIndexNode() {
      }

      public final int execute(AbstractTruffleString a, int i, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, i, expectedEncoding, TruffleString.ErrorHandling.BEST_EFFORT);
      }

      public abstract int execute(AbstractTruffleString a, int i, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling);

      @Specialization
      static int readCodePoint(
         AbstractTruffleString a,
         int i,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ErrorHandling errorHandling,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.CodePointAtNode readCodePointNode
      ) {
         CompilerAsserts.partialEvaluationConstant(errorHandling);
         a.checkEncoding(expectedEncoding);
         a.boundsCheck(i, getCodePointLengthNode);
         Object arrayA = toIndexableNode.execute(a, a.data());
         return readCodePointNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, i, errorHandling);
      }

      public static TruffleString.CodePointAtIndexNode create() {
         return TruffleStringFactory.CodePointAtIndexNodeGen.create();
      }

      public static TruffleString.CodePointAtIndexNode getUncached() {
         return TruffleStringFactory.CodePointAtIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CodePointIndexToByteIndexNode extends Node {
      CodePointIndexToByteIndexNode() {
      }

      public abstract int execute(AbstractTruffleString a, int byteOffset, int codepointIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int translate(
         AbstractTruffleString a,
         int byteOffset,
         int codepointIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.CodePointIndexToRawNode codePointIndexToRawNode
      ) {
         a.checkEncoding(expectedEncoding);
         a.boundsCheckRegion(0, codepointIndex, getCodePointLengthNode);
         int rawOffset = AbstractTruffleString.rawIndex(byteOffset, expectedEncoding);
         a.boundsCheckRawLength(rawOffset);
         if (codepointIndex == 0) {
            return 0;
         } else {
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            return codePointIndexToRawNode.execute(a, arrayA, codeRangeA, expectedEncoding, rawOffset, codepointIndex, true) << expectedEncoding.naturalStride;
         }
      }

      public static TruffleString.CodePointIndexToByteIndexNode create() {
         return TruffleStringFactory.CodePointIndexToByteIndexNodeGen.create();
      }

      public static TruffleString.CodePointIndexToByteIndexNode getUncached() {
         return TruffleStringFactory.CodePointIndexToByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CodePointLengthNode extends Node {
      CodePointLengthNode() {
      }

      public abstract int execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int get(
         AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode
      ) {
         a.checkEncoding(expectedEncoding);
         return getCodePointLengthNode.execute(a);
      }

      public static TruffleString.CodePointLengthNode create() {
         return TruffleStringFactory.CodePointLengthNodeGen.create();
      }

      public static TruffleString.CodePointLengthNode getUncached() {
         return TruffleStringFactory.CodePointLengthNodeGen.getUncached();
      }
   }

   public static enum CodeRange {
      ASCII,
      LATIN_1,
      BMP,
      VALID,
      BROKEN;

      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final TruffleString.CodeRange[] CODE_RANGES = new TruffleString.CodeRange[]{ASCII, LATIN_1, BMP, VALID, BROKEN, VALID, BROKEN};
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final TruffleString.CodeRange[] BYTE_CODE_RANGES = new TruffleString.CodeRange[]{ASCII, VALID, VALID, VALID, BROKEN, VALID, BROKEN};

      public boolean isSubsetOf(TruffleString.CodeRange other) {
         return this.ordinal() <= other.ordinal();
      }

      public boolean isSupersetOf(TruffleString.CodeRange other) {
         return this.ordinal() >= other.ordinal();
      }

      static TruffleString.CodeRange get(int codeRange) {
         return CODE_RANGES[codeRange];
      }

      static TruffleString.CodeRange getByteCodeRange(int codeRange, TruffleString.Encoding encoding) {
         return codeRange == TSCodeRange.get7Bit() && TStringGuards.isUTF16Or32(encoding) ? VALID : BYTE_CODE_RANGES[codeRange];
      }

      static boolean equals(int codeRange, TruffleString.CodeRange codeRangeEnum) {
         return codeRange == codeRangeEnum.ordinal()
            || codeRangeEnum == VALID && TStringGuards.isValidMultiByte(codeRange)
            || codeRangeEnum == BROKEN && TStringGuards.isBrokenMultiByte(codeRange);
      }

      static {
         assert get(TSCodeRange.get7Bit()) == ASCII;

         assert get(TSCodeRange.get8Bit()) == LATIN_1;

         assert get(TSCodeRange.get16Bit()) == BMP;

         assert get(TSCodeRange.getValidFixedWidth()) == VALID;

         assert get(TSCodeRange.getBrokenFixedWidth()) == BROKEN;

         assert get(TSCodeRange.getValidMultiByte()) == VALID;

         assert get(TSCodeRange.getBrokenMultiByte()) == BROKEN;

         assert equals(TSCodeRange.get7Bit(), ASCII);

         assert equals(TSCodeRange.get8Bit(), LATIN_1);

         assert equals(TSCodeRange.get16Bit(), BMP);

         assert equals(TSCodeRange.getValidFixedWidth(), VALID);

         assert equals(TSCodeRange.getBrokenFixedWidth(), BROKEN);

         assert equals(TSCodeRange.getValidMultiByte(), VALID);

         assert equals(TSCodeRange.getBrokenMultiByte(), BROKEN);

         assert TSCodeRange.getUnknown() == CODE_RANGES.length;
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class CodeRangeEqualsNode extends Node {
      CodeRangeEqualsNode() {
      }

      public abstract boolean execute(AbstractTruffleString a, TruffleString.CodeRange codeRange);

      @Specialization
      static boolean codeRangeEquals(AbstractTruffleString a, TruffleString.CodeRange codeRange, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
         return TruffleString.CodeRange.equals(getCodeRangeNode.execute(a), codeRange);
      }

      public static TruffleString.CodeRangeEqualsNode create() {
         return TruffleStringFactory.CodeRangeEqualsNodeGen.create();
      }

      public static TruffleString.CodeRangeEqualsNode getUncached() {
         return TruffleStringFactory.CodeRangeEqualsNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CompareBytesNode extends Node {
      CompareBytesNode() {
      }

      public abstract int execute(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding);

      @Specialization
      int compare(
         AbstractTruffleString a,
         AbstractTruffleString b,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode
      ) {
         AbstractTruffleString.nullCheck(expectedEncoding);
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         Object aData = toIndexableNodeA.execute(a, a.data());
         Object bData = toIndexableNodeB.execute(b, b.data());
         if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0) {
            int cmp = Byte.compareUnsigned(((byte[])aData)[a.offset()], ((byte[])bData)[b.offset()]);
            if (cmp != 0) {
               return cmp;
            }
         }

         return a == b ? 0 : TStringOpsNodes.memcmpBytes(this, a, aData, b, bData);
      }

      public static TruffleString.CompareBytesNode create() {
         return TruffleStringFactory.CompareBytesNodeGen.create();
      }

      public static TruffleString.CompareBytesNode getUncached() {
         return TruffleStringFactory.CompareBytesNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CompareCharsUTF16Node extends Node {
      CompareCharsUTF16Node() {
      }

      public abstract int execute(AbstractTruffleString a, AbstractTruffleString b);

      @Specialization
      int compare(
         AbstractTruffleString a,
         AbstractTruffleString b,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(TruffleString.Encoding.UTF_16, codeRangeA);
         b.looseCheckEncoding(TruffleString.Encoding.UTF_16, codeRangeB);
         Object aData = toIndexableNodeA.execute(a, a.data());
         Object bData = toIndexableNodeB.execute(b, b.data());
         if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0) {
            int cmp = Byte.compareUnsigned(((byte[])aData)[a.offset()], ((byte[])bData)[b.offset()]);
            if (cmp != 0) {
               return cmp;
            }
         }

         return a == b ? 0 : TStringOpsNodes.memcmp(this, a, aData, b, bData);
      }

      public static TruffleString.CompareCharsUTF16Node create() {
         return TruffleStringFactory.CompareCharsUTF16NodeGen.create();
      }

      public static TruffleString.CompareCharsUTF16Node getUncached() {
         return TruffleStringFactory.CompareCharsUTF16NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CompareIntsUTF32Node extends Node {
      CompareIntsUTF32Node() {
      }

      public abstract int execute(AbstractTruffleString a, AbstractTruffleString b);

      @Specialization
      int compare(
         AbstractTruffleString a,
         AbstractTruffleString b,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(TruffleString.Encoding.UTF_32, codeRangeA);
         b.looseCheckEncoding(TruffleString.Encoding.UTF_32, codeRangeB);
         Object aData = toIndexableNodeA.execute(a, a.data());
         Object bData = toIndexableNodeB.execute(b, b.data());
         if (aData instanceof byte[] && bData instanceof byte[] && (a.stride() | b.stride()) == 0 && a.length() != 0 && b.length() != 0) {
            int cmp = Byte.compareUnsigned(((byte[])aData)[a.offset()], ((byte[])bData)[b.offset()]);
            if (cmp != 0) {
               return cmp;
            }
         }

         return a == b ? 0 : TStringOpsNodes.memcmp(this, a, aData, b, bData);
      }

      public static TruffleString.CompareIntsUTF32Node create() {
         return TruffleStringFactory.CompareIntsUTF32NodeGen.create();
      }

      public static TruffleString.CompareIntsUTF32Node getUncached() {
         return TruffleStringFactory.CompareIntsUTF32NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ConcatNode extends Node {
      ConcatNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding, boolean lazy);

      @Specialization(guards = "isEmpty(a)")
      static TruffleString aEmpty(AbstractTruffleString a, TruffleString b, TruffleString.Encoding expectedEncoding, boolean lazy) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            b.looseCheckEncoding(expectedEncoding, b.codeRange());
            return b.switchEncodingUncached(expectedEncoding);
         } else {
            b.checkEncoding(expectedEncoding);
            return b;
         }
      }

      @Specialization(guards = "isEmpty(a)")
      static TruffleString aEmptyMutable(
         AbstractTruffleString a,
         MutableTruffleString b,
         TruffleString.Encoding expectedEncoding,
         boolean lazy,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode
      ) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            b.looseCheckEncoding(expectedEncoding, TStringInternalNodes.GetCodeRangeNode.getUncached().execute(b));
            return b.switchEncodingUncached(expectedEncoding);
         } else {
            int codeRange = getCodeRangeNode.execute(b);
            b.looseCheckEncoding(expectedEncoding, codeRange);
            return fromBufferWithStringCompactionNode.execute(
               b.data(), b.offset(), b.length() << b.stride(), expectedEncoding, getCodePointLengthNode.execute(b), codeRange
            );
         }
      }

      @Specialization(guards = "isEmpty(b)")
      static TruffleString bEmpty(TruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding, boolean lazy) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            a.looseCheckEncoding(expectedEncoding, a.codeRange());
            return a.switchEncodingUncached(expectedEncoding);
         } else {
            a.checkEncoding(expectedEncoding);
            return a;
         }
      }

      @Specialization(guards = "isEmpty(b)")
      static TruffleString bEmptyMutable(
         MutableTruffleString a,
         AbstractTruffleString b,
         TruffleString.Encoding expectedEncoding,
         boolean lazy,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode fromBufferWithStringCompactionNode
      ) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            a.looseCheckEncoding(expectedEncoding, TStringInternalNodes.GetCodeRangeNode.getUncached().execute(a));
            return a.switchEncodingUncached(expectedEncoding);
         } else {
            int codeRange = getCodeRangeNode.execute(a);
            a.looseCheckEncoding(expectedEncoding, codeRange);
            return fromBufferWithStringCompactionNode.execute(
               a.data(), a.offset(), a.length() << a.stride(), expectedEncoding, getCodePointLengthNode.execute(a), codeRange
            );
         }
      }

      @Specialization(guards = {"!isEmpty(a)", "!isEmpty(b)"})
      static TruffleString doConcat(
         AbstractTruffleString a,
         AbstractTruffleString b,
         TruffleString.Encoding encoding,
         boolean lazy,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.StrideFromCodeRangeNode getStrideNode,
         @Cached TStringInternalNodes.ConcatEagerNode concatEagerNode,
         @Cached TruffleString.AsTruffleStringNode asTruffleStringANode,
         @Cached TruffleString.AsTruffleStringNode asTruffleStringBNode,
         @Cached BranchProfile outOfMemoryProfile,
         @Cached ConditionProfile lazyProfile
      ) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(encoding, codeRangeA);
         b.looseCheckEncoding(encoding, codeRangeB);
         int commonCodeRange = TSCodeRange.commonCodeRange(codeRangeA, codeRangeB);

         assert !TStringGuards.isBrokenMultiByte(codeRangeA) && !TStringGuards.isBrokenMultiByte(codeRangeB)
            || TStringGuards.isBrokenMultiByte(commonCodeRange);

         int targetStride = getStrideNode.execute(commonCodeRange, encoding);
         int length = addByteLengths(a, b, targetStride, outOfMemoryProfile);
         boolean valid = !TStringGuards.isBrokenMultiByte(commonCodeRange);
         if (lazyProfile.profile(lazy && valid && (a.isImmutable() || b.isImmutable()) && length << targetStride >= 40)) {
            return AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS
               ? TruffleString.createLazyConcat(asTruffleStringLoose(a, encoding), asTruffleStringLoose(b, encoding), encoding, length, targetStride)
               : TruffleString.createLazyConcat(
                  asTruffleStringANode.execute(a, encoding), asTruffleStringBNode.execute(b, encoding), encoding, length, targetStride
               );
         } else {
            return concatEagerNode.execute(a, b, encoding, length, targetStride, commonCodeRange);
         }
      }

      static int addByteLengths(AbstractTruffleString a, AbstractTruffleString b, int targetStride, BranchProfile outOfMemoryProfile) {
         long length = (long)a.length() + b.length();
         if (length << targetStride > 2147483639L) {
            outOfMemoryProfile.enter();
            throw InternalErrors.outOfMemory();
         } else {
            return (int)length;
         }
      }

      private static TruffleString asTruffleStringLoose(AbstractTruffleString a, TruffleString.Encoding encoding) {
         return a.isImmutable()
            ? (TruffleString)a
            : TStringInternalNodes.FromBufferWithStringCompactionKnownAttributesNode.getUncached()
               .execute(
                  a.data(),
                  a.offset(),
                  a.length() << a.stride(),
                  encoding,
                  TStringInternalNodes.GetCodePointLengthNode.getUncached().execute(a),
                  TStringInternalNodes.GetCodeRangeNode.getUncached().execute(a)
               );
      }

      public static TruffleString.ConcatNode create() {
         return TruffleStringFactory.ConcatNodeGen.create();
      }

      public static TruffleString.ConcatNode getUncached() {
         return TruffleStringFactory.ConcatNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class CopyToByteArrayNode extends Node {
      CopyToByteArrayNode() {
      }

      public final byte[] execute(AbstractTruffleString string, TruffleString.Encoding expectedEncoding) {
         int byteLength = string.byteLength(expectedEncoding);
         byte[] copy = new byte[byteLength];
         this.execute(string, 0, copy, 0, byteLength, expectedEncoding);
         return copy;
      }

      public abstract void execute(
         AbstractTruffleString a, int byteFromIndexA, byte[] dst, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding
      );

      @Specialization
      void doCopy(
         AbstractTruffleString a,
         int byteFromIndexA,
         byte[] arrayB,
         int byteFromIndexB,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached ConditionProfile utf16Profile,
         @Cached ConditionProfile utf16S0Profile,
         @Cached ConditionProfile utf32Profile,
         @Cached ConditionProfile utf32S0Profile,
         @Cached ConditionProfile utf32S1Profile
      ) {
         AbstractTruffleString.boundsCheckRegionI(byteFromIndexB, byteLength, arrayB.length);
         doCopyInternal(
            this,
            a,
            byteFromIndexA,
            arrayB,
            byteFromIndexB,
            byteLength,
            expectedEncoding,
            toIndexableNode,
            utf16Profile,
            utf16S0Profile,
            utf32Profile,
            utf32S0Profile,
            utf32S1Profile
         );
      }

      private static void doCopyInternal(
         Node location,
         AbstractTruffleString a,
         int byteFromIndexA,
         Object arrayB,
         int byteFromIndexB,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ToIndexableNode toIndexableNode,
         ConditionProfile utf16Profile,
         ConditionProfile utf16S0Profile,
         ConditionProfile utf32Profile,
         ConditionProfile utf32S0Profile,
         ConditionProfile utf32S1Profile
      ) {
         if (byteLength != 0) {
            a.checkEncoding(expectedEncoding);
            int offsetA = a.offset();
            int offsetB = 0;
            Object arrayA = toIndexableNode.execute(a, a.data());
            if (utf16Profile.profile(TStringGuards.isUTF16(expectedEncoding))) {
               a.boundsCheckByteIndexUTF16(byteFromIndexA);
               AbstractTruffleString.checkByteLengthUTF16(byteLength);
               int fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
               int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
               int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
               a.boundsCheckRegionRaw(fromIndexA, length);
               if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                  TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, fromIndexA, arrayB, 0, 1, fromIndexB, length);
                  return;
               }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(expectedEncoding))) {
               a.boundsCheckByteIndexUTF32(byteFromIndexA);
               AbstractTruffleString.checkByteLengthUTF32(byteLength);
               int fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
               int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
               int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
               a.boundsCheckRegionRaw(fromIndexA, length);
               if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
                  TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, fromIndexA, arrayB, 0, 2, fromIndexB, length);
                  return;
               }

               if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
                  TStringOps.arraycopyWithStride(location, arrayA, offsetA, 1, fromIndexA, arrayB, 0, 2, fromIndexB, length);
                  return;
               }
            }

            int byteLengthA = a.length() << a.stride();
            AbstractTruffleString.boundsCheckRegionI(byteFromIndexA, byteLength, byteLengthA);
            TStringOps.arraycopyWithStride(location, arrayA, offsetA, 0, byteFromIndexA, arrayB, 0, 0, byteFromIndexB, byteLength);
         }
      }

      public static TruffleString.CopyToByteArrayNode create() {
         return TruffleStringFactory.CopyToByteArrayNodeGen.create();
      }

      public static TruffleString.CopyToByteArrayNode getUncached() {
         return TruffleStringFactory.CopyToByteArrayNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringAccessor.class)
   @GenerateUncached
   public abstract static class CopyToNativeMemoryNode extends Node {
      CopyToNativeMemoryNode() {
      }

      public abstract void execute(
         AbstractTruffleString a, int byteFromIndexA, Object pointerObject, int byteFromIndexDst, int byteLength, TruffleString.Encoding expectedEncoding
      );

      @Specialization
      void doCopy(
         AbstractTruffleString a,
         int byteFromIndexA,
         Object pointerObject,
         int byteFromIndexB,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         @Cached(value = "createInteropLibrary()", uncached = "getUncachedInteropLibrary()") Node interopLibrary,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached ConditionProfile utf16Profile,
         @Cached ConditionProfile utf16S0Profile,
         @Cached ConditionProfile utf32Profile,
         @Cached ConditionProfile utf32S0Profile,
         @Cached ConditionProfile utf32S1Profile
      ) {
         TruffleString.CopyToByteArrayNode.doCopyInternal(
            this,
            a,
            byteFromIndexA,
            AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteFromIndexB),
            byteFromIndexB,
            byteLength,
            expectedEncoding,
            toIndexableNode,
            utf16Profile,
            utf16S0Profile,
            utf32Profile,
            utf32S0Profile,
            utf32S1Profile
         );
      }

      public static TruffleString.CopyToNativeMemoryNode create() {
         return TruffleStringFactory.CopyToNativeMemoryNodeGen.create();
      }

      public static TruffleString.CopyToNativeMemoryNode getUncached() {
         return TruffleStringFactory.CopyToNativeMemoryNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CreateBackwardCodePointIteratorNode extends Node {
      CreateBackwardCodePointIteratorNode() {
      }

      public final TruffleStringIterator execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, expectedEncoding, TruffleString.ErrorHandling.BEST_EFFORT);
      }

      public abstract TruffleStringIterator execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling);

      @Specialization
      static TruffleStringIterator createIterator(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ErrorHandling errorHandling,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode
      ) {
         CompilerAsserts.partialEvaluationConstant(errorHandling);
         a.checkEncoding(expectedEncoding);
         return AbstractTruffleString.backwardIterator(a, toIndexableNode.execute(a, a.data()), getCodeRangeANode.execute(a), expectedEncoding, errorHandling);
      }

      public static TruffleString.CreateBackwardCodePointIteratorNode create() {
         return TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.create();
      }

      public static TruffleString.CreateBackwardCodePointIteratorNode getUncached() {
         return TruffleStringFactory.CreateBackwardCodePointIteratorNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class CreateCodePointIteratorNode extends Node {
      CreateCodePointIteratorNode() {
      }

      public final TruffleStringIterator execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, expectedEncoding, TruffleString.ErrorHandling.BEST_EFFORT);
      }

      public abstract TruffleStringIterator execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.ErrorHandling errorHandling);

      @Specialization
      static TruffleStringIterator createIterator(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         TruffleString.ErrorHandling errorHandling,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode
      ) {
         CompilerAsserts.partialEvaluationConstant(errorHandling);
         a.checkEncoding(expectedEncoding);
         return AbstractTruffleString.forwardIterator(a, toIndexableNode.execute(a, a.data()), getCodeRangeANode.execute(a), expectedEncoding, errorHandling);
      }

      public static TruffleString.CreateCodePointIteratorNode create() {
         return TruffleStringFactory.CreateCodePointIteratorNodeGen.create();
      }

      public static TruffleString.CreateCodePointIteratorNode getUncached() {
         return TruffleStringFactory.CreateCodePointIteratorNodeGen.getUncached();
      }
   }

   public static enum Encoding {
      UTF_32LE(TStringGuards.littleEndian() ? 0 : 97, "UTF_32LE", TStringGuards.littleEndian() ? 2 : 0),
      UTF_32BE(TStringGuards.littleEndian() ? 97 : 0, "UTF_32BE", TStringGuards.littleEndian() ? 0 : 2),
      UTF_16LE(TStringGuards.littleEndian() ? 1 : 98, "UTF_16LE", TStringGuards.littleEndian() ? 1 : 0),
      UTF_16BE(TStringGuards.littleEndian() ? 98 : 1, "UTF_16BE", TStringGuards.littleEndian() ? 0 : 1),
      ISO_8859_1(2, "ISO_8859_1"),
      UTF_8(3, "UTF_8"),
      US_ASCII(4, "US_ASCII"),
      BYTES(5, "BYTES"),
      Big5(6, "Big5"),
      Big5_HKSCS(7, "Big5_HKSCS"),
      Big5_UAO(8, "Big5_UAO"),
      CP51932(9, "CP51932"),
      CP850(10, "CP850"),
      CP852(11, "CP852"),
      CP855(12, "CP855"),
      CP949(13, "CP949"),
      CP950(14, "CP950"),
      CP951(15, "CP951"),
      EUC_JIS_2004(16, "EUC_JIS_2004"),
      EUC_JP(17, "EUC_JP"),
      EUC_KR(18, "EUC_KR"),
      EUC_TW(19, "EUC_TW"),
      Emacs_Mule(20, "Emacs_Mule"),
      EucJP_ms(21, "EucJP_ms"),
      GB12345(22, "GB12345"),
      GB18030(23, "GB18030"),
      GB1988(24, "GB1988"),
      GB2312(25, "GB2312"),
      GBK(26, "GBK"),
      IBM437(27, "IBM437"),
      IBM737(28, "IBM737"),
      IBM775(29, "IBM775"),
      IBM852(30, "IBM852"),
      IBM855(31, "IBM855"),
      IBM857(32, "IBM857"),
      IBM860(33, "IBM860"),
      IBM861(34, "IBM861"),
      IBM862(35, "IBM862"),
      IBM863(36, "IBM863"),
      IBM864(37, "IBM864"),
      IBM865(38, "IBM865"),
      IBM866(39, "IBM866"),
      IBM869(40, "IBM869"),
      ISO_8859_10(41, "ISO_8859_10"),
      ISO_8859_11(42, "ISO_8859_11"),
      ISO_8859_13(43, "ISO_8859_13"),
      ISO_8859_14(44, "ISO_8859_14"),
      ISO_8859_15(45, "ISO_8859_15"),
      ISO_8859_16(46, "ISO_8859_16"),
      ISO_8859_2(47, "ISO_8859_2"),
      ISO_8859_3(48, "ISO_8859_3"),
      ISO_8859_4(49, "ISO_8859_4"),
      ISO_8859_5(50, "ISO_8859_5"),
      ISO_8859_6(51, "ISO_8859_6"),
      ISO_8859_7(52, "ISO_8859_7"),
      ISO_8859_8(53, "ISO_8859_8"),
      ISO_8859_9(54, "ISO_8859_9"),
      KOI8_R(55, "KOI8_R"),
      KOI8_U(56, "KOI8_U"),
      MacCentEuro(57, "MacCentEuro"),
      MacCroatian(58, "MacCroatian"),
      MacCyrillic(59, "MacCyrillic"),
      MacGreek(60, "MacGreek"),
      MacIceland(61, "MacIceland"),
      MacJapanese(62, "MacJapanese"),
      MacRoman(63, "MacRoman"),
      MacRomania(64, "MacRomania"),
      MacThai(65, "MacThai"),
      MacTurkish(66, "MacTurkish"),
      MacUkraine(67, "MacUkraine"),
      SJIS_DoCoMo(68, "SJIS_DoCoMo"),
      SJIS_KDDI(69, "SJIS_KDDI"),
      SJIS_SoftBank(70, "SJIS_SoftBank"),
      Shift_JIS(71, "Shift_JIS"),
      Stateless_ISO_2022_JP(72, "Stateless_ISO_2022_JP"),
      Stateless_ISO_2022_JP_KDDI(73, "Stateless_ISO_2022_JP_KDDI"),
      TIS_620(74, "TIS_620"),
      UTF8_DoCoMo(75, "UTF8_DoCoMo"),
      UTF8_KDDI(76, "UTF8_KDDI"),
      UTF8_MAC(77, "UTF8_MAC"),
      UTF8_SoftBank(78, "UTF8_SoftBank"),
      Windows_1250(79, "Windows_1250"),
      Windows_1251(80, "Windows_1251"),
      Windows_1252(81, "Windows_1252"),
      Windows_1253(82, "Windows_1253"),
      Windows_1254(83, "Windows_1254"),
      Windows_1255(84, "Windows_1255"),
      Windows_1256(85, "Windows_1256"),
      Windows_1257(86, "Windows_1257"),
      Windows_1258(87, "Windows_1258"),
      Windows_31J(88, "Windows_31J"),
      Windows_874(89, "Windows_874"),
      CP50220(90, "CP50220"),
      CP50221(91, "CP50221"),
      IBM037(92, "IBM037"),
      ISO_2022_JP(93, "ISO_2022_JP"),
      ISO_2022_JP_2(94, "ISO_2022_JP_2"),
      ISO_2022_JP_KDDI(95, "ISO_2022_JP_KDDI"),
      UTF_7(96, "UTF_7");

      public static final TruffleString.Encoding UTF_32 = TStringGuards.littleEndian() ? UTF_32LE : UTF_32BE;
      public static final TruffleString.Encoding UTF_16 = TStringGuards.littleEndian() ? UTF_16LE : UTF_16BE;
      final byte id;
      final String name;
      final JCodings.Encoding jCoding;
      final byte maxCompatibleCodeRange;
      final byte naturalStride;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final TruffleString.Encoding[] ENCODINGS_TABLE = new TruffleString.Encoding[values().length];
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final JCodings.Encoding[] J_CODINGS_TABLE = new JCodings.Encoding[values().length];
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final byte[] MAX_COMPATIBLE_CODE_RANGE = new byte[values().length];
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private static final TruffleString[] EMPTY_STRINGS = new TruffleString[values().length];
      private static final EconomicMap<String, TruffleString.Encoding> J_CODINGS_NAME_MAP = EconomicMap.create(values().length);

      private Encoding(int id, String name) {
         this(id, name, 0);
      }

      private Encoding(int id, String name, int naturalStride) {
         assert id <= 127;

         assert Stride.isStride(naturalStride);

         this.id = (byte)id;
         this.name = name;
         this.jCoding = JCodings.ENABLED ? JCodings.getInstance().get(name) : null;
         if (this.is16BitCompatible()) {
            this.maxCompatibleCodeRange = (byte)(TSCodeRange.get16Bit() + 1);
         } else if (this.is8BitCompatible()) {
            this.maxCompatibleCodeRange = (byte)(TSCodeRange.get8Bit() + 1);
         } else if (this.is7BitCompatible()) {
            this.maxCompatibleCodeRange = (byte)(TSCodeRange.get7Bit() + 1);
         } else {
            this.maxCompatibleCodeRange = 0;
         }

         this.naturalStride = (byte)naturalStride;
      }

      private static TruffleString createEmpty(TruffleString.Encoding encoding) {
         if ((!encoding.is7BitCompatible() || AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) && encoding != US_ASCII) {
            TruffleString ret = TruffleString.createConstant(new byte[0], 0, 0, encoding, 0, TSCodeRange.getAsciiCodeRange(encoding), false);
            EMPTY_STRINGS[US_ASCII.id].cacheInsert(ret);
            return ret;
         } else {
            return EMPTY_STRINGS[US_ASCII.id];
         }
      }

      public TruffleString getEmpty() {
         return EMPTY_STRINGS[this.id];
      }

      public static TruffleString.Encoding fromJCodingName(String name) {
         TruffleString.Encoding encoding = J_CODINGS_NAME_MAP.get(name, null);
         if (encoding == null) {
            throw InternalErrors.unknownEncoding(name);
         } else {
            return encoding;
         }
      }

      static TruffleString.Encoding get(int encoding) {
         return ENCODINGS_TABLE[encoding];
      }

      static JCodings.Encoding getJCoding(int encoding) {
         assert J_CODINGS_TABLE[encoding] == get(encoding).jCoding;

         return J_CODINGS_TABLE[encoding];
      }

      static int getMaxCompatibleCodeRange(int encoding) {
         return MAX_COMPATIBLE_CODE_RANGE[encoding];
      }

      boolean is7BitCompatible() {
         return is7BitCompatible(this.id);
      }

      boolean is8BitCompatible() {
         return is8BitCompatible(this.id);
      }

      boolean is16BitCompatible() {
         return is16BitCompatible(this.id);
      }

      boolean isSupported() {
         return isSupported(this.id);
      }

      boolean isUnsupported() {
         return isUnsupported(this.id);
      }

      static boolean is7BitCompatible(int encoding) {
         return encoding < 90;
      }

      static boolean is8BitCompatible(int encoding) {
         return encoding < 3;
      }

      static boolean is16BitCompatible(int encoding) {
         return encoding < 2;
      }

      static boolean isSupported(int encoding) {
         return encoding < 6;
      }

      static boolean isUnsupported(int encoding) {
         return encoding >= 6;
      }

      static boolean isFixedWidth(int encoding) {
         return JCodings.getInstance().isFixedWidth(getJCoding(encoding));
      }

      static boolean isFixedWidth(TruffleString.Encoding encoding) {
         return JCodings.getInstance().isFixedWidth(encoding.jCoding);
      }

      static {
         for (TruffleString.Encoding e : values()) {
            assert ENCODINGS_TABLE[e.id] == null;

            ENCODINGS_TABLE[e.id] = e;

            assert J_CODINGS_TABLE[e.id] == null;

            J_CODINGS_TABLE[e.id] = e.jCoding;
            MAX_COMPATIBLE_CODE_RANGE[e.id] = e.maxCompatibleCodeRange;
            if (JCodings.ENABLED) {
               J_CODINGS_NAME_MAP.put(JCodings.getInstance().name(e.jCoding), e);
            }
         }

         assert UTF_16.naturalStride == 1;

         assert UTF_32.naturalStride == 2;

         EMPTY_STRINGS[US_ASCII.id] = TruffleString.createConstant(new byte[0], 0, 0, US_ASCII, 0, TSCodeRange.get7Bit());

         for (TruffleString.Encoding e : values()) {
            if (e != US_ASCII) {
               assert EMPTY_STRINGS[e.id] == null;

               if (e.isSupported() || JCodings.ENABLED) {
                  EMPTY_STRINGS[e.id] = createEmpty(e);
               }
            }
         }
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class EqualNode extends Node {
      EqualNode() {
      }

      public abstract boolean execute(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding);

      @Specialization(guards = "identical(a, b)")
      static boolean sameObject(AbstractTruffleString a, AbstractTruffleString b, TruffleString.Encoding expectedEncoding) {
         return true;
      }

      @Specialization(guards = "!identical(a, b)")
      boolean check(
         AbstractTruffleString a,
         AbstractTruffleString b,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached ConditionProfile lengthAndCodeRangeCheckProfile,
         @Cached BranchProfile compareHashProfile,
         @Cached ConditionProfile checkFirstByteProfile
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         return checkContentEquals(
            a, codeRangeA, b, codeRangeB, toIndexableNodeA, toIndexableNodeB, lengthAndCodeRangeCheckProfile, compareHashProfile, checkFirstByteProfile, this
         );
      }

      static boolean checkContentEquals(
         AbstractTruffleString a,
         int codeRangeA,
         AbstractTruffleString b,
         int codeRangeB,
         TruffleString.ToIndexableNode toIndexableNodeA,
         TruffleString.ToIndexableNode toIndexableNodeB,
         ConditionProfile lengthAndCodeRangeCheckProfile,
         BranchProfile compareHashProfile,
         ConditionProfile checkFirstByteProfile,
         TruffleString.EqualNode equalNode
      ) {
         assert TSCodeRange.isKnown(codeRangeA, codeRangeB);

         int lengthCMP = a.length();
         if (lengthAndCodeRangeCheckProfile.profile(lengthCMP != b.length() || codeRangeA != codeRangeB)) {
            return false;
         } else {
            if (a.isHashCodeCalculated() && b.isHashCodeCalculated()) {
               compareHashProfile.enter();
               if (a.getHashCodeUnsafe() != b.getHashCodeUnsafe()) {
                  return false;
               }
            }

            if (lengthCMP == 0) {
               return true;
            } else {
               Object arrayA = toIndexableNodeA.execute(a, a.data());
               Object arrayB = toIndexableNodeB.execute(b, b.data());
               int strideA = a.stride();
               int strideB = b.stride();
               if (checkFirstByteProfile.profile(arrayA instanceof byte[] && arrayB instanceof byte[] && (strideA | strideB) == 0)) {
                  if (((byte[])arrayA)[a.offset()] != ((byte[])arrayB)[b.offset()]) {
                     return false;
                  }

                  if (lengthCMP == 1) {
                     return true;
                  }
               }

               return TStringOps.regionEqualsWithOrMaskWithStride(equalNode, a, arrayA, strideA, 0, b, arrayB, strideB, 0, null, lengthCMP);
            }
         }
      }

      public static TruffleString.EqualNode create() {
         return TruffleStringFactory.EqualNodeGen.create();
      }

      public static TruffleString.EqualNode getUncached() {
         return TruffleStringFactory.EqualNodeGen.getUncached();
      }
   }

   public static enum ErrorHandling {
      BEST_EFFORT,
      RETURN_NEGATIVE;
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class ForceEncodingNode extends Node {
      ForceEncodingNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding);

      @Specialization(guards = "isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)")
      static TruffleString compatibleImmutable(TruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding) {
         assert !a.isJavaString();

         return a;
      }

      @Specialization(guards = "isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)")
      static TruffleString compatibleMutable(
         MutableTruffleString a,
         TruffleString.Encoding expectedEncoding,
         TruffleString.Encoding targetEncoding,
         @Cached TruffleString.AsTruffleStringNode asTruffleStringNode
      ) {
         return asTruffleStringNode.execute(a, targetEncoding);
      }

      @Specialization(guards = "!isCompatibleAndNotCompacted(a, expectedEncoding, targetEncoding)")
      static TruffleString reinterpret(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         TruffleString.Encoding targetEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached ConditionProfile managedProfile,
         @Cached ConditionProfile inflateProfile,
         @Cached TruffleString.CopyToByteArrayNode copyToByteArrayNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode,
         @Cached TStringInternalNodes.FromNativePointerNode fromNativePointerNode
      ) {
         Object arrayA = toIndexableNode.execute(a, a.data());
         int byteLength = a.length() << expectedEncoding.naturalStride;
         if (!managedProfile.profile(arrayA instanceof byte[] || a.isMutable())) {
            assert arrayA instanceof AbstractTruffleString.NativePointer;

            return fromNativePointerNode.execute((AbstractTruffleString.NativePointer)arrayA, a.offset(), byteLength, targetEncoding, true);
         } else {
            Object arrayNoCompaction;
            int offset;
            if (inflateProfile.profile(TStringGuards.isUTF16Or32(expectedEncoding) && a.stride() != expectedEncoding.naturalStride)) {
               byte[] inflated = new byte[byteLength];
               copyToByteArrayNode.execute(a, 0, inflated, 0, byteLength, expectedEncoding);
               arrayNoCompaction = inflated;
               offset = 0;
            } else {
               arrayNoCompaction = arrayA;
               offset = a.offset();
            }

            return fromBufferWithStringCompactionNode.execute(arrayNoCompaction, offset, byteLength, targetEncoding, a.isMutable(), true);
         }
      }

      static boolean isCompatibleAndNotCompacted(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, TruffleString.Encoding targetEncoding) {
         return expectedEncoding.naturalStride == targetEncoding.naturalStride
            && (a.encoding() == targetEncoding.id || a.stride() == targetEncoding.naturalStride && a.isCompatibleTo(targetEncoding));
      }

      public static TruffleString.ForceEncodingNode create() {
         return TruffleStringFactory.ForceEncodingNodeGen.create();
      }

      public static TruffleString.ForceEncodingNode getUncached() {
         return TruffleStringFactory.ForceEncodingNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromByteArrayNode extends Node {
      FromByteArrayNode() {
      }

      public final TruffleString execute(byte[] value, TruffleString.Encoding encoding) {
         return this.execute(value, encoding, true);
      }

      public final TruffleString execute(byte[] value, TruffleString.Encoding encoding, boolean copy) {
         return this.execute(value, 0, value.length, encoding, copy);
      }

      public abstract TruffleString execute(byte[] value, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy);

      @Specialization
      static TruffleString fromByteArray(
         byte[] value,
         int byteOffset,
         int byteLength,
         TruffleString.Encoding enc,
         boolean copy,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode
      ) {
         AbstractTruffleString.checkArrayRange(value, byteOffset, byteLength);
         return fromBufferWithStringCompactionNode.execute(value, byteOffset, byteLength, enc, copy, true);
      }

      public static TruffleString.FromByteArrayNode create() {
         return TruffleStringFactory.FromByteArrayNodeGen.create();
      }

      public static TruffleString.FromByteArrayNode getUncached() {
         return TruffleStringFactory.FromByteArrayNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromCharArrayUTF16Node extends Node {
      FromCharArrayUTF16Node() {
      }

      public final TruffleString execute(char[] value) {
         return this.execute(value, 0, value.length);
      }

      public abstract TruffleString execute(char[] value, int charOffset, int charLength);

      @Specialization
      TruffleString doNonEmpty(
         char[] value, int charOffset, int charLength, @Cached ConditionProfile utf16CompactProfile, @Cached BranchProfile outOfMemoryProfile
      ) {
         AbstractTruffleString.checkArrayRange(value.length, charOffset, charLength);
         if (charLength == 0) {
            return TruffleString.Encoding.UTF_16.getEmpty();
         } else if (charLength == 1 && value[charOffset] <= 255) {
            return TStringConstants.getSingleByte(TruffleString.Encoding.UTF_16, value[charOffset]);
         } else {
            int offsetV = charOffset << 1;
            if (value.length <= 1073741819 && offsetV >= 0) {
               long attrs = TStringOps.calcStringAttributesUTF16C(this, value, offsetV, charLength);
               int codePointLength = StringAttributes.getCodePointLength(attrs);
               int codeRange = StringAttributes.getCodeRange(attrs);
               int stride = Stride.fromCodeRangeUTF16(codeRange);
               byte[] array = new byte[charLength << stride];
               if (utf16CompactProfile.profile(stride == 0)) {
                  TStringOps.arraycopyWithStrideCB(this, value, offsetV, array, 0, 0, charLength);
               } else {
                  TStringOps.arraycopyWithStrideCB(this, value, offsetV, array, 0, 1, charLength);
               }

               return TruffleString.createFromArray(array, 0, charLength, stride, TruffleString.Encoding.UTF_16, codePointLength, codeRange);
            } else {
               outOfMemoryProfile.enter();
               throw InternalErrors.outOfMemory();
            }
         }
      }

      public static TruffleString.FromCharArrayUTF16Node create() {
         return TruffleStringFactory.FromCharArrayUTF16NodeGen.create();
      }

      public static TruffleString.FromCharArrayUTF16Node getUncached() {
         return TruffleStringFactory.FromCharArrayUTF16NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class FromCodePointNode extends Node {
      FromCodePointNode() {
      }

      public final TruffleString execute(int codepoint, TruffleString.Encoding encoding) {
         return this.execute(codepoint, encoding, encoding == TruffleString.Encoding.UTF_16);
      }

      public abstract TruffleString execute(int codepoint, TruffleString.Encoding encoding, boolean allowUTF16Surrogates);

      @Specialization
      static TruffleString fromCodePoint(
         int c,
         TruffleString.Encoding enc,
         boolean allowUTF16Surrogates,
         @Cached ConditionProfile bytesProfile,
         @Cached ConditionProfile utf8Profile,
         @Cached ConditionProfile utf16Profile,
         @Cached ConditionProfile utf32Profile,
         @Cached ConditionProfile exoticProfile,
         @Cached ConditionProfile bmpProfile,
         @Cached BranchProfile invalidCodePoint
      ) {
         assert !allowUTF16Surrogates || TStringGuards.isUTF16Or32(enc) : "allowUTF16Surrogates is only supported on UTF-16 and UTF-32";

         CompilerAsserts.partialEvaluationConstant(allowUTF16Surrogates);
         if (TStringGuards.is7BitCompatible(enc) && Integer.compareUnsigned(c, 127) <= 0) {
            return TStringConstants.getSingleByteAscii(enc, c);
         } else if (TStringGuards.is8BitCompatible(enc) && Integer.compareUnsigned(c, 255) <= 0) {
            assert TStringGuards.isSupportedEncoding(enc);

            return TStringConstants.getSingleByte(enc, c);
         } else if (bytesProfile.profile(TStringGuards.isBytes(enc))) {
            if (Integer.compareUnsigned(c, 255) > 0) {
               invalidCodePoint.enter();
               return null;
            } else {
               return TStringConstants.getSingleByte(TruffleString.Encoding.BYTES, c);
            }
         } else {
            byte[] bytes;
            int length;
            int stride;
            int codeRange;
            if (utf8Profile.profile(TStringGuards.isUTF8(enc))) {
               if (!Encodings.isValidUnicodeCodepoint(c)) {
                  invalidCodePoint.enter();
                  return null;
               }

               assert c > 127;

               bytes = Encodings.utf8Encode(c);
               length = bytes.length;
               stride = 0;
               codeRange = TSCodeRange.getValidMultiByte();
            } else if (utf16Profile.profile(TStringGuards.isUTF16(enc))) {
               if (Integer.toUnsignedLong(c) > 1114111L) {
                  invalidCodePoint.enter();
                  return null;
               }

               assert c > 255;

               bytes = new byte[c <= 65535 ? 2 : 4];
               stride = 1;
               if (bmpProfile.profile(c <= 65535)) {
                  length = 1;
                  if (Encodings.isUTF16Surrogate(c)) {
                     if (!allowUTF16Surrogates) {
                        invalidCodePoint.enter();
                        return null;
                     }

                     codeRange = TSCodeRange.getBrokenMultiByte();
                  } else {
                     codeRange = TSCodeRange.get16Bit();
                  }

                  TStringOps.writeToByteArray(bytes, 1, 0, c);
               } else {
                  length = 2;
                  codeRange = TSCodeRange.getValidMultiByte();
                  Encodings.utf16EncodeSurrogatePair(c, bytes, 0);
               }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(enc))) {
               if (Integer.toUnsignedLong(c) > 1114111L) {
                  invalidCodePoint.enter();
                  return null;
               }

               assert c > 255;

               if (c <= 65535) {
                  if (Encodings.isUTF16Surrogate(c)) {
                     if (!allowUTF16Surrogates) {
                        invalidCodePoint.enter();
                        return null;
                     }

                     codeRange = TSCodeRange.getBrokenFixedWidth();
                  } else {
                     codeRange = TSCodeRange.get16Bit();
                  }
               } else {
                  codeRange = TSCodeRange.getValidFixedWidth();
               }

               boolean compact1 = TSCodeRange.is16Bit(codeRange);
               bytes = new byte[compact1 ? 2 : 4];
               length = 1;
               if (bmpProfile.profile(compact1)) {
                  stride = 1;
                  TStringOps.writeToByteArray(bytes, 1, 0, c);
               } else {
                  stride = 2;
                  TStringOps.writeToByteArray(bytes, 2, 0, c);
               }
            } else {
               if (!exoticProfile.profile(!TStringGuards.isSupportedEncoding(enc))) {
                  if ($assertionsDisabled
                     || TStringGuards.isAscii(enc) && Integer.compareUnsigned(c, 127) > 0
                     || TStringGuards.isLatin1(enc) && Integer.compareUnsigned(c, 255) > 0) {
                     invalidCodePoint.enter();
                     return null;
                  }

                  throw new AssertionError();
               }

               assert !TStringGuards.isBytes(enc);

               JCodings.Encoding jCodingsEnc = JCodings.getInstance().get(enc);
               length = JCodings.getInstance().getCodePointLength(jCodingsEnc, c);
               stride = 0;
               codeRange = JCodings.getInstance().isSingleByte(jCodingsEnc) ? TSCodeRange.getValidFixedWidth() : TSCodeRange.getValidMultiByte();
               if (length < 1) {
                  invalidCodePoint.enter();
                  return null;
               }

               bytes = new byte[length];
               int ret = JCodings.getInstance().writeCodePoint(jCodingsEnc, c, bytes, 0);
               if (ret != length
                  || JCodings.getInstance().getCodePointLength(jCodingsEnc, bytes, 0, length) != ret
                  || JCodings.getInstance().readCodePoint(jCodingsEnc, bytes, 0, length) != c) {
                  invalidCodePoint.enter();
                  return null;
               }
            }

            return TruffleString.createFromByteArray(bytes, length, stride, enc, 1, codeRange);
         }
      }

      public static TruffleString.FromCodePointNode create() {
         return TruffleStringFactory.FromCodePointNodeGen.create();
      }

      public static TruffleString.FromCodePointNode getUncached() {
         return TruffleStringFactory.FromCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromIntArrayUTF32Node extends Node {
      FromIntArrayUTF32Node() {
      }

      public final TruffleString execute(int[] value) {
         return this.execute(value, 0, value.length);
      }

      public abstract TruffleString execute(int[] value, int intOffset, int intLength);

      @Specialization
      TruffleString doNonEmpty(
         int[] value,
         int intOffset,
         int length,
         @Cached ConditionProfile utf32Compact0Profile,
         @Cached ConditionProfile utf32Compact1Profile,
         @Cached BranchProfile outOfMemoryProfile
      ) {
         AbstractTruffleString.checkArrayRange(value.length, intOffset, length);
         if (length == 0) {
            return TruffleString.Encoding.UTF_32.getEmpty();
         } else if (length == 1 && value[intOffset] <= 255) {
            return TStringConstants.getSingleByte(TruffleString.Encoding.UTF_32, value[intOffset]);
         } else {
            int offsetV = intOffset << 2;
            if (length <= 536870909 && offsetV >= 0) {
               int codeRange = TStringOps.calcStringAttributesUTF32I(this, value, offsetV, length);
               int stride = Stride.fromCodeRangeUTF32(codeRange);
               byte[] array = new byte[length << stride];
               if (utf32Compact0Profile.profile(stride == 0)) {
                  TStringOps.arraycopyWithStrideIB(this, value, offsetV, array, 0, 0, length);
               } else if (utf32Compact1Profile.profile(stride == 1)) {
                  TStringOps.arraycopyWithStrideIB(this, value, offsetV, array, 0, 1, length);
               } else {
                  TStringOps.arraycopyWithStrideIB(this, value, offsetV, array, 0, 2, length);
               }

               return TruffleString.createFromArray(array, 0, length, stride, TruffleString.Encoding.UTF_32, length, codeRange);
            } else {
               outOfMemoryProfile.enter();
               throw InternalErrors.outOfMemory();
            }
         }
      }

      public static TruffleString.FromIntArrayUTF32Node create() {
         return TruffleStringFactory.FromIntArrayUTF32NodeGen.create();
      }

      public static TruffleString.FromIntArrayUTF32Node getUncached() {
         return TruffleStringFactory.FromIntArrayUTF32NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromJavaStringNode extends Node {
      FromJavaStringNode() {
      }

      public final TruffleString execute(String value, TruffleString.Encoding encoding) {
         return this.execute(value, 0, value.length(), encoding, false);
      }

      public abstract TruffleString execute(String value, int charOffset, int length, TruffleString.Encoding encoding, boolean copy);

      @Specialization
      static TruffleString doUTF16(
         String javaString,
         int charOffset,
         int length,
         TruffleString.Encoding encoding,
         final boolean copy,
         @Cached TStringInternalNodes.FromJavaStringUTF16Node fromJavaStringUTF16Node,
         @Cached TruffleString.SwitchEncodingNode switchEncodingNode,
         @Cached ConditionProfile utf16Profile
      ) {
         if (javaString.isEmpty()) {
            return encoding.getEmpty();
         } else {
            TruffleString utf16String = fromJavaStringUTF16Node.execute(javaString, charOffset, length, copy);
            return utf16Profile.profile(encoding == TruffleString.Encoding.UTF_16) ? utf16String : switchEncodingNode.execute(utf16String, encoding);
         }
      }

      public static TruffleString.FromJavaStringNode create() {
         return TruffleStringFactory.FromJavaStringNodeGen.create();
      }

      public static TruffleString.FromJavaStringNode getUncached() {
         return TruffleStringFactory.FromJavaStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class FromLongNode extends Node {
      FromLongNode() {
      }

      public abstract TruffleString execute(long value, TruffleString.Encoding encoding, boolean lazy);

      @Specialization(guards = {"is7BitCompatible(enc)", "lazy"})
      static TruffleString doLazy(long value, TruffleString.Encoding enc, boolean lazy) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         return TruffleString.createLazyLong(value, enc);
      }

      @Specialization(guards = {"is7BitCompatible(enc)", "!lazy"})
      static TruffleString doEager(long value, TruffleString.Encoding enc, boolean lazy) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         int length = NumberConversion.stringLengthLong(value);
         return TruffleString.createFromByteArray(NumberConversion.longToString(value, length), length, 0, enc, length, TSCodeRange.get7Bit());
      }

      @Specialization(guards = "!is7BitCompatible(enc)")
      static TruffleString unsupported(long value, TruffleString.Encoding enc, boolean lazy) {
         CompilerAsserts.partialEvaluationConstant(lazy);
         throw InternalErrors.unsupportedOperation(nonAsciiCompatibleMessage(enc));
      }

      @CompilerDirectives.TruffleBoundary
      private static String nonAsciiCompatibleMessage(TruffleString.Encoding enc) {
         return "Encoding " + enc + " is not ASCII-compatible";
      }

      public static TruffleString.FromLongNode create() {
         return TruffleStringFactory.FromLongNodeGen.create();
      }

      public static TruffleString.FromLongNode getUncached() {
         return TruffleStringFactory.FromLongNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic({TStringGuards.class, TStringAccessor.class})
   @GenerateUncached
   public abstract static class FromNativePointerNode extends Node {
      FromNativePointerNode() {
      }

      public abstract TruffleString execute(Object pointerObject, int byteOffset, int byteLength, TruffleString.Encoding encoding, boolean copy);

      @Specialization
      TruffleString fromNativePointer(
         Object pointerObject,
         int byteOffset,
         int byteLength,
         TruffleString.Encoding enc,
         boolean copy,
         @Cached(value = "createInteropLibrary()", uncached = "getUncachedInteropLibrary()") Node interopLibrary,
         @Cached TStringInternalNodes.FromNativePointerNode fromNativePointerNode,
         @Cached TStringInternalNodes.FromBufferWithStringCompactionNode fromBufferWithStringCompactionNode
      ) {
         AbstractTruffleString.NativePointer pointer = AbstractTruffleString.NativePointer.create(this, pointerObject, interopLibrary, byteOffset);
         return copy
            ? fromBufferWithStringCompactionNode.execute(pointer, byteOffset, byteLength, enc, true, true)
            : fromNativePointerNode.execute(pointer, byteOffset, byteLength, enc, true);
      }

      public static TruffleString.FromNativePointerNode create() {
         return TruffleStringFactory.FromNativePointerNodeGen.create();
      }

      public static TruffleString.FromNativePointerNode getUncached() {
         return TruffleStringFactory.FromNativePointerNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class GetByteCodeRangeNode extends Node {
      GetByteCodeRangeNode() {
      }

      public abstract TruffleString.CodeRange execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static TruffleString.CodeRange getCodeRange(
         AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode
      ) {
         a.checkEncoding(expectedEncoding);
         return TruffleString.CodeRange.getByteCodeRange(getCodeRangeNode.execute(a), expectedEncoding);
      }

      public static TruffleString.GetByteCodeRangeNode create() {
         return TruffleStringFactory.GetByteCodeRangeNodeGen.create();
      }

      public static TruffleString.GetByteCodeRangeNode getUncached() {
         return TruffleStringFactory.GetByteCodeRangeNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class GetCodeRangeNode extends Node {
      GetCodeRangeNode() {
      }

      public abstract TruffleString.CodeRange execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static TruffleString.CodeRange getCodeRange(
         AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode
      ) {
         a.checkEncoding(expectedEncoding);
         return TruffleString.CodeRange.get(getCodeRangeNode.execute(a));
      }

      public static TruffleString.GetCodeRangeNode create() {
         return TruffleStringFactory.GetCodeRangeNodeGen.create();
      }

      public static TruffleString.GetCodeRangeNode getUncached() {
         return TruffleStringFactory.GetCodeRangeNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class GetInternalByteArrayNode extends Node {
      GetInternalByteArrayNode() {
      }

      public abstract InternalByteArray execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      InternalByteArray getInternalByteArray(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached ConditionProfile utf16Profile,
         @Cached ConditionProfile utf16S0Profile,
         @Cached ConditionProfile utf32Profile,
         @Cached ConditionProfile utf32S0Profile,
         @Cached ConditionProfile utf32S1Profile,
         @Cached ConditionProfile isByteArrayProfile
      ) {
         if (a.isEmpty()) {
            return InternalByteArray.EMPTY;
         } else {
            a.checkEncoding(expectedEncoding);
            Object arrayA = toIndexableNode.execute(a, a.data());
            if (utf16Profile.profile(TStringGuards.isUTF16(expectedEncoding))) {
               if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
                  return this.inflate(a, arrayA, 0, 1);
               }
            } else if (utf32Profile.profile(TStringGuards.isUTF32(expectedEncoding))) {
               if (utf32S0Profile.profile(TStringGuards.isStride0(a))) {
                  return this.inflate(a, arrayA, 0, 2);
               }

               if (utf32S1Profile.profile(TStringGuards.isStride1(a))) {
                  return this.inflate(a, arrayA, 1, 2);
               }
            }

            int byteLength = a.length() << a.stride();
            return isByteArrayProfile.profile(arrayA instanceof byte[])
               ? new InternalByteArray((byte[])arrayA, a.offset(), byteLength)
               : new InternalByteArray(TStringOps.arraycopyOfWithStride(this, arrayA, a.offset(), byteLength, 0, byteLength, 0), 0, byteLength);
         }
      }

      private InternalByteArray inflate(AbstractTruffleString a, Object arrayA, int strideA, int strideB) {
         assert a.stride() == strideA;

         CompilerAsserts.partialEvaluationConstant(strideA);
         CompilerAsserts.partialEvaluationConstant(strideB);
         return new InternalByteArray(
            TStringOps.arraycopyOfWithStride(this, arrayA, a.offset(), a.length(), strideA, a.length(), strideB), 0, a.length() << strideB
         );
      }

      public static TruffleString.GetInternalByteArrayNode create() {
         return TruffleStringFactory.GetInternalByteArrayNodeGen.create();
      }

      public static TruffleString.GetInternalByteArrayNode getUncached() {
         return TruffleStringFactory.GetInternalByteArrayNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class GetInternalNativePointerNode extends Node {
      GetInternalNativePointerNode() {
      }

      public abstract Object execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static Object getNativePointer(AbstractTruffleString a, TruffleString.Encoding expectedEncoding) {
         a.checkEncoding(expectedEncoding);
         if (!a.isNative()) {
            throw InternalErrors.unsupportedOperation("string is not backed by a native pointer!");
         } else {
            return ((AbstractTruffleString.NativePointer)a.data()).getPointerObject();
         }
      }

      public static TruffleString.GetInternalNativePointerNode create() {
         return TruffleStringFactory.GetInternalNativePointerNodeGen.create();
      }

      public static TruffleString.GetInternalNativePointerNode getUncached() {
         return TruffleStringFactory.GetInternalNativePointerNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class HashCodeNode extends Node {
      HashCodeNode() {
      }

      public abstract int execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int calculateHash(
         AbstractTruffleString a,
         TruffleString.Encoding expectedEncoding,
         @Cached ConditionProfile cacheMiss,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringOpsNodes.CalculateHashCodeNode calculateHashCodeNode
      ) {
         a.checkEncoding(expectedEncoding);
         int h = a.hashCode;
         if (cacheMiss.profile(h == 0)) {
            h = calculateHashCodeNode.execute(a, toIndexableNode.execute(a, a.data()));
            if (h == 0) {
               h--;
            }

            a.hashCode = h;
         }

         return h;
      }

      public static TruffleString.HashCodeNode create() {
         return TruffleStringFactory.HashCodeNodeGen.create();
      }

      public static TruffleString.HashCodeNode getUncached() {
         return TruffleStringFactory.HashCodeNodeGen.getUncached();
      }
   }

   public static final class IllegalByteArrayLengthException extends IllegalArgumentException {
      private static final long serialVersionUID = 2871353611734808666L;

      IllegalByteArrayLengthException(String msg) {
         super(msg);
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class IndexOfCodePointNode extends Node {
      IndexOfCodePointNode() {
      }

      public abstract int execute(AbstractTruffleString a, int codepoint, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int doIndexOf(
         AbstractTruffleString a,
         int codepoint,
         int fromIndex,
         int toIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.IndexOfCodePointNode indexOfNode
      ) {
         a.checkEncoding(expectedEncoding);
         if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheck(fromIndex, toIndex, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return indexOfNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex);
         }
      }

      public static TruffleString.IndexOfCodePointNode create() {
         return TruffleStringFactory.IndexOfCodePointNodeGen.create();
      }

      public static TruffleString.IndexOfCodePointNode getUncached() {
         return TruffleStringFactory.IndexOfCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class IndexOfStringNode extends Node {
      IndexOfStringNode() {
      }

      public abstract int execute(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int indexOfString(
         AbstractTruffleString a,
         AbstractTruffleString b,
         int fromIndex,
         int toIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.IndexOfStringNode indexOfStringNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         if (b.isEmpty()) {
            return fromIndex;
         } else if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheck(fromIndex, toIndex, getCodePointLengthANode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, toIndex - fromIndex, getCodePointLengthBNode)
               ? -1
               : indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, expectedEncoding);
         }
      }

      public static TruffleString.IndexOfStringNode create() {
         return TruffleStringFactory.IndexOfStringNodeGen.create();
      }

      public static TruffleString.IndexOfStringNode getUncached() {
         return TruffleStringFactory.IndexOfStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class IntIndexOfAnyIntUTF32Node extends Node {
      IntIndexOfAnyIntUTF32Node() {
      }

      public abstract int execute(AbstractTruffleString a, int fromIntIndex, int maxIntIndex, int[] values);

      @Specialization
      int indexOfRaw(
         AbstractTruffleString a,
         int fromIntIndex,
         int maxIntIndex,
         int[] values,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringOpsNodes.IndexOfAnyIntNode indexOfNode
      ) {
         a.checkEncoding(TruffleString.Encoding.UTF_32);
         if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheckRaw(fromIntIndex, maxIntIndex);
            return fromIntIndex != maxIntIndex && !noneInCodeRange(this, getCodeRangeNode.execute(a), values)
               ? indexOfNode.execute(a, toIndexableNode.execute(a, a.data()), fromIntIndex, maxIntIndex, values)
               : -1;
         }
      }

      private static boolean noneInCodeRange(Node location, int codeRange, int[] values) {
         for (int i = 0; i < values.length; i++) {
            if (TSCodeRange.isInCodeRange(values[i], codeRange)) {
               return false;
            }

            TStringConstants.truffleSafePointPoll(location, i + 1);
         }

         return true;
      }

      public static TruffleString.IntIndexOfAnyIntUTF32Node create() {
         return TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.create();
      }

      public static TruffleString.IntIndexOfAnyIntUTF32Node getUncached() {
         return TruffleStringFactory.IntIndexOfAnyIntUTF32NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class IsValidNode extends Node {
      IsValidNode() {
      }

      public abstract boolean execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static boolean isValid(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode) {
         a.checkEncoding(expectedEncoding);
         int codeRange = getCodeRangeNode.execute(a);
         return !TStringGuards.isBrokenMultiByte(codeRange) && !TStringGuards.isBrokenFixedWidth(codeRange);
      }

      public static TruffleString.IsValidNode create() {
         return TruffleStringFactory.IsValidNodeGen.create();
      }

      public static TruffleString.IsValidNode getUncached() {
         return TruffleStringFactory.IsValidNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class LastByteIndexOfCodePointNode extends Node {
      LastByteIndexOfCodePointNode() {
      }

      public abstract int execute(AbstractTruffleString a, int codepoint, int fromByteIndex, int toByteIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int doIndexOf(
         AbstractTruffleString a,
         int codepoint,
         int fromByteIndex,
         int toByteIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.LastIndexOfCodePointRawNode lastIndexOfNode
      ) {
         a.checkEncoding(expectedEncoding);
         if (a.isEmpty()) {
            return -1;
         } else {
            int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toByteIndex, expectedEncoding);
            a.boundsCheckRaw(toIndex, fromIndex);
            return AbstractTruffleString.byteIndex(
               lastIndexOfNode.execute(a, toIndexableNode.execute(a, a.data()), getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex),
               expectedEncoding
            );
         }
      }

      public static TruffleString.LastByteIndexOfCodePointNode create() {
         return TruffleStringFactory.LastByteIndexOfCodePointNodeGen.create();
      }

      public static TruffleString.LastByteIndexOfCodePointNode getUncached() {
         return TruffleStringFactory.LastByteIndexOfCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class LastByteIndexOfStringNode extends Node {
      LastByteIndexOfStringNode() {
      }

      public final int execute(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, b, fromIndex, toIndex, null, expectedEncoding);
      }

      public final int execute(AbstractTruffleString a, TruffleString.WithMask b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding) {
         return this.execute(a, b.string, fromIndex, toIndex, b.mask, expectedEncoding);
      }

      abstract int execute(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, byte[] mask, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int lastByteIndexOfString(
         AbstractTruffleString a,
         AbstractTruffleString b,
         int fromIndexB,
         int toIndexB,
         byte[] mask,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.LastIndexOfStringRawNode indexOfStringNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         if (mask != null && TStringGuards.isUnsupportedEncoding(expectedEncoding) && !TStringGuards.isFixedWidth(codeRangeA)) {
            throw InternalErrors.unsupportedOperation();
         } else if (b.isEmpty()) {
            return fromIndexB;
         } else if (a.isEmpty()) {
            return -1;
         } else {
            int fromIndex = AbstractTruffleString.rawIndex(fromIndexB, expectedEncoding);
            int toIndex = AbstractTruffleString.rawIndex(toIndexB, expectedEncoding);
            a.boundsCheckRaw(toIndex, fromIndex);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, mask, fromIndex - toIndex)
               ? -1
               : AbstractTruffleString.byteIndex(
                  indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, mask, expectedEncoding), expectedEncoding
               );
         }
      }

      public static TruffleString.LastByteIndexOfStringNode create() {
         return TruffleStringFactory.LastByteIndexOfStringNodeGen.create();
      }

      public static TruffleString.LastByteIndexOfStringNode getUncached() {
         return TruffleStringFactory.LastByteIndexOfStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class LastIndexOfCodePointNode extends Node {
      LastIndexOfCodePointNode() {
      }

      public abstract int execute(AbstractTruffleString a, int codepoint, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int doIndexOf(
         AbstractTruffleString a,
         int codepoint,
         int fromIndex,
         int toIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.LastIndexOfCodePointNode lastIndexOfNode
      ) {
         a.checkEncoding(expectedEncoding);
         if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheck(toIndex, fromIndex, getCodePointLengthNode);
            Object arrayA = toIndexableNode.execute(a, a.data());
            return lastIndexOfNode.execute(a, arrayA, getCodeRangeNode.execute(a), expectedEncoding, codepoint, fromIndex, toIndex);
         }
      }

      public static TruffleString.LastIndexOfCodePointNode create() {
         return TruffleStringFactory.LastIndexOfCodePointNodeGen.create();
      }

      public static TruffleString.LastIndexOfCodePointNode getUncached() {
         return TruffleStringFactory.LastIndexOfCodePointNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class LastIndexOfStringNode extends Node {
      LastIndexOfStringNode() {
      }

      public abstract int execute(AbstractTruffleString a, AbstractTruffleString b, int fromIndex, int toIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int lastIndexOfString(
         AbstractTruffleString a,
         AbstractTruffleString b,
         int fromIndex,
         int toIndex,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.LastIndexOfStringNode indexOfStringNode
      ) {
         int codeRangeA = getCodeRangeANode.execute(a);
         int codeRangeB = getCodeRangeBNode.execute(b);
         a.looseCheckEncoding(expectedEncoding, codeRangeA);
         b.looseCheckEncoding(expectedEncoding, codeRangeB);
         if (b.isEmpty()) {
            return fromIndex;
         } else if (a.isEmpty()) {
            return -1;
         } else {
            a.boundsCheck(toIndex, fromIndex, getCodePointLengthANode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringGuards.indexOfCannotMatch(codeRangeA, b, codeRangeB, fromIndex - toIndex, getCodePointLengthBNode)
               ? -1
               : indexOfStringNode.execute(a, arrayA, codeRangeA, b, arrayB, codeRangeB, fromIndex, toIndex, expectedEncoding);
         }
      }

      public static TruffleString.LastIndexOfStringNode create() {
         return TruffleStringFactory.LastIndexOfStringNodeGen.create();
      }

      public static TruffleString.LastIndexOfStringNode getUncached() {
         return TruffleStringFactory.LastIndexOfStringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class MaterializeNode extends Node {
      MaterializeNode() {
      }

      public abstract void execute(AbstractTruffleString a, TruffleString.Encoding expectedEncoding);

      @Specialization
      static void doMaterialize(AbstractTruffleString a, TruffleString.Encoding expectedEncoding, @Cached TruffleString.ToIndexableNode toIndexableNode) {
         a.checkEncoding(expectedEncoding);
         toIndexableNode.execute(a, a.data());

         assert a.isMaterialized(expectedEncoding);
      }

      public static TruffleString.MaterializeNode create() {
         return TruffleStringFactory.MaterializeNodeGen.create();
      }

      public static TruffleString.MaterializeNode getUncached() {
         return TruffleStringFactory.MaterializeNodeGen.getUncached();
      }
   }

   public static final class NumberFormatException extends Exception {
      private static final long serialVersionUID = 102938855488837538L;
      private final AbstractTruffleString string;
      private final int regionOffset;
      private final int regionLength;
      private final TruffleString.NumberFormatException.Reason reason;

      NumberFormatException(AbstractTruffleString string, TruffleString.NumberFormatException.Reason reason) {
         this(string, -1, -1, reason);
      }

      NumberFormatException(AbstractTruffleString string, int regionOffset, int regionLength, TruffleString.NumberFormatException.Reason reason) {
         this.string = string;
         this.regionOffset = regionOffset;
         this.regionLength = regionLength;
         this.reason = reason;
      }

      TruffleString.NumberFormatException.Reason getReason() {
         return this.reason;
      }

      AbstractTruffleString getString() {
         return this.string;
      }

      int getRegionByteOffset() {
         return this.regionOffset < 0 ? this.regionOffset : this.regionOffset << this.string.stride();
      }

      int getRegionByteLength() {
         return this.regionLength < 0 ? this.regionLength : this.regionLength << this.string.stride();
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String getMessage() {
         StringBuilder sb = new StringBuilder();
         sb.append("error parsing \"").append(this.getString()).append("\": ");
         sb.append(this.getReason().message);
         if (this.regionOffset >= 0) {
            if (this.regionLength == 1) {
               sb.append(" at byte index ").append(this.getRegionByteOffset());
            } else {
               sb.append(" from byte index ").append(this.getRegionByteOffset()).append(" to ").append(this.getRegionByteOffset() + this.getRegionByteLength());
            }
         }

         return sb.toString();
      }

      @Override
      public Throwable fillInStackTrace() {
         return this;
      }

      static enum Reason {
         EMPTY("no digits found"),
         INVALID_CODEPOINT("invalid codepoint"),
         LONE_SIGN("lone '+' or '-'"),
         OVERFLOW("overflow"),
         MALFORMED_HEX_ESCAPE("malformed hex escape sequence"),
         MULTIPLE_DECIMAL_POINTS("multiple decimal points"),
         UNSUPPORTED_RADIX("unsupported radix");

         private final String message;

         private Reason(String message) {
            this.message = message;
         }

         public String getMessage() {
            return this.message;
         }
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ParseDoubleNode extends Node {
      ParseDoubleNode() {
      }

      public abstract double execute(AbstractTruffleString a) throws TruffleString.NumberFormatException;

      @Specialization(guards = "isLazyLongSafeInteger(a)")
      static double doLazyLong(AbstractTruffleString a) {
         return ((AbstractTruffleString.LazyLong)a.data()).value;
      }

      @Specialization(guards = "!isLazyLongSafeInteger(a)")
      static double parseDouble(
         AbstractTruffleString a, @Cached TruffleString.ToIndexableNode toIndexableNode, @Cached TStringInternalNodes.ParseDoubleNode parseDoubleNode
      ) throws TruffleString.NumberFormatException {
         return parseDoubleNode.execute(a, toIndexableNode.execute(a, a.data()));
      }

      static boolean isLazyLongSafeInteger(AbstractTruffleString a) {
         return a.isLazyLong() && NumberConversion.isSafeInteger(((AbstractTruffleString.LazyLong)a.data()).value);
      }

      public static TruffleString.ParseDoubleNode create() {
         return TruffleStringFactory.ParseDoubleNodeGen.create();
      }

      public static TruffleString.ParseDoubleNode getUncached() {
         return TruffleStringFactory.ParseDoubleNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ParseIntNode extends Node {
      ParseIntNode() {
      }

      public abstract int execute(AbstractTruffleString a, int radix) throws TruffleString.NumberFormatException;

      @Specialization(guards = {"a.isLazyLong()", "radix == 10"})
      static int doLazyLong(AbstractTruffleString a, int radix, @Cached BranchProfile errorProfile) throws TruffleString.NumberFormatException {
         long value = ((AbstractTruffleString.LazyLong)a.data()).value;
         if (value >= -2147483648L && value <= 2147483647L) {
            return (int)value;
         } else {
            errorProfile.enter();
            throw NumberConversion.numberFormatException(a, TruffleString.NumberFormatException.Reason.OVERFLOW);
         }
      }

      @Specialization(guards = "!a.isLazyLong() || radix != 10")
      static int doParse(
         AbstractTruffleString a,
         int radix,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.ParseIntNode parseIntNode,
         @Cached("createIdentityProfile()") IntValueProfile radixProfile
      ) throws TruffleString.NumberFormatException {
         int codeRangeA = getCodeRangeANode.execute(a);
         return parseIntNode.execute(a, toIndexableNode.execute(a, a.data()), codeRangeA, TruffleString.Encoding.get(a.encoding()), radixProfile.profile(radix));
      }

      public static TruffleString.ParseIntNode create() {
         return TruffleStringFactory.ParseIntNodeGen.create();
      }

      public static TruffleString.ParseIntNode getUncached() {
         return TruffleStringFactory.ParseIntNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ParseLongNode extends Node {
      ParseLongNode() {
      }

      public abstract long execute(AbstractTruffleString a, int radix) throws TruffleString.NumberFormatException;

      @Specialization(guards = {"a.isLazyLong()", "radix == 10"})
      static long doLazyLong(AbstractTruffleString a, int radix) {
         return ((AbstractTruffleString.LazyLong)a.data()).value;
      }

      @Specialization(guards = "!a.isLazyLong() || radix != 10")
      static long doParse(
         AbstractTruffleString a,
         int radix,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.ParseLongNode parseLongNode,
         @Cached("createIdentityProfile()") IntValueProfile radixProfile
      ) throws TruffleString.NumberFormatException {
         int codeRangeA = getCodeRangeANode.execute(a);
         return parseLongNode.execute(
            a, toIndexableNode.execute(a, a.data()), codeRangeA, TruffleString.Encoding.get(a.encoding()), radixProfile.profile(radix)
         );
      }

      public static TruffleString.ParseLongNode create() {
         return TruffleStringFactory.ParseLongNodeGen.create();
      }

      public static TruffleString.ParseLongNode getUncached() {
         return TruffleStringFactory.ParseLongNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ReadByteNode extends Node {
      ReadByteNode() {
      }

      public abstract int execute(AbstractTruffleString a, int byteIndex, TruffleString.Encoding expectedEncoding);

      @Specialization
      static int doRead(
         AbstractTruffleString a,
         int i,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.ReadByteNode readByteNode
      ) {
         a.checkEncoding(expectedEncoding);
         Object arrayA = toIndexableNode.execute(a, a.data());
         return readByteNode.execute(a, arrayA, i, expectedEncoding);
      }

      public static TruffleString.ReadByteNode create() {
         return TruffleStringFactory.ReadByteNodeGen.create();
      }

      public static TruffleString.ReadByteNode getUncached() {
         return TruffleStringFactory.ReadByteNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class ReadCharUTF16Node extends Node {
      ReadCharUTF16Node() {
      }

      public abstract char execute(AbstractTruffleString a, int charIndex);

      @Specialization
      static char doRead(AbstractTruffleString a, int i, @Cached TruffleString.ToIndexableNode toIndexableNode, @Cached ConditionProfile utf16S0Profile) {
         a.checkEncoding(TruffleString.Encoding.UTF_16);
         a.boundsCheckRaw(i);
         Object arrayA = toIndexableNode.execute(a, a.data());
         if (utf16S0Profile.profile(TStringGuards.isStride0(a))) {
            return (char)TStringOps.readS0(a, arrayA, i);
         } else {
            assert TStringGuards.isStride1(a);

            return TStringOps.readS1(a, arrayA, i);
         }
      }

      public static TruffleString.ReadCharUTF16Node create() {
         return TruffleStringFactory.ReadCharUTF16NodeGen.create();
      }

      public static TruffleString.ReadCharUTF16Node getUncached() {
         return TruffleStringFactory.ReadCharUTF16NodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class RegionEqualByteIndexNode extends Node {
      RegionEqualByteIndexNode() {
      }

      public final boolean execute(
         AbstractTruffleString a, int fromByteIndexA, AbstractTruffleString b, int fromByteIndexB, int length, TruffleString.Encoding expectedEncoding
      ) {
         return this.execute(a, fromByteIndexA, b, fromByteIndexB, length, null, expectedEncoding);
      }

      public final boolean execute(
         AbstractTruffleString a, int fromByteIndexA, TruffleString.WithMask b, int fromByteIndexB, int length, TruffleString.Encoding expectedEncoding
      ) {
         return this.execute(a, fromByteIndexA, b.string, fromByteIndexB, length, b.mask, expectedEncoding);
      }

      abstract boolean execute(
         AbstractTruffleString a, int fromIndexA, AbstractTruffleString b, int fromIndexB, int length, byte[] mask, TruffleString.Encoding expectedEncoding
      );

      @Specialization
      boolean regionEquals(
         AbstractTruffleString a,
         int byteFromIndexA,
         AbstractTruffleString b,
         int byteFromIndexB,
         int byteLength,
         byte[] mask,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode
      ) {
         if (byteLength == 0) {
            return true;
         } else {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            int fromIndexA = AbstractTruffleString.rawIndex(byteFromIndexA, expectedEncoding);
            int fromIndexB = AbstractTruffleString.rawIndex(byteFromIndexB, expectedEncoding);
            int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
            a.boundsCheckRegionRaw(fromIndexA, length);
            b.boundsCheckRegionRaw(fromIndexB, length);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return TStringOps.regionEqualsWithOrMaskWithStride(this, a, arrayA, a.stride(), fromIndexA, b, arrayB, b.stride(), fromIndexB, mask, length);
         }
      }

      public static TruffleString.RegionEqualByteIndexNode create() {
         return TruffleStringFactory.RegionEqualByteIndexNodeGen.create();
      }

      public static TruffleString.RegionEqualByteIndexNode getUncached() {
         return TruffleStringFactory.RegionEqualByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class RegionEqualNode extends Node {
      RegionEqualNode() {
      }

      public abstract boolean execute(
         AbstractTruffleString a, int fromIndexA, AbstractTruffleString b, int fromIndexB, int length, TruffleString.Encoding expectedEncoding
      );

      @Specialization
      static boolean regionEquals(
         AbstractTruffleString a,
         int fromIndexA,
         AbstractTruffleString b,
         int fromIndexB,
         int length,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.ToIndexableNode toIndexableNodeA,
         @Cached TruffleString.ToIndexableNode toIndexableNodeB,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthANode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthBNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeBNode,
         @Cached TStringInternalNodes.RegionEqualsNode regionEqualsNode
      ) {
         if (length == 0) {
            return true;
         } else {
            int codeRangeA = getCodeRangeANode.execute(a);
            int codeRangeB = getCodeRangeBNode.execute(b);
            a.looseCheckEncoding(expectedEncoding, codeRangeA);
            b.looseCheckEncoding(expectedEncoding, codeRangeB);
            a.boundsCheckRegion(fromIndexA, length, getCodePointLengthANode);
            b.boundsCheckRegion(fromIndexB, length, getCodePointLengthBNode);
            Object arrayA = toIndexableNodeA.execute(a, a.data());
            Object arrayB = toIndexableNodeB.execute(b, b.data());
            return regionEqualsNode.execute(a, arrayA, codeRangeA, fromIndexA, b, arrayB, codeRangeB, fromIndexB, length, expectedEncoding);
         }
      }

      public static TruffleString.RegionEqualNode create() {
         return TruffleStringFactory.RegionEqualNodeGen.create();
      }

      public static TruffleString.RegionEqualNode getUncached() {
         return TruffleStringFactory.RegionEqualNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class RepeatNode extends Node {
      RepeatNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, int n, TruffleString.Encoding expectedEncoding);

      @Specialization
      TruffleString repeat(
         AbstractTruffleString a,
         int n,
         TruffleString.Encoding expectedEncoding,
         @Cached TruffleString.AsTruffleStringNode asTruffleStringNode,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.CalcStringAttributesNode calcStringAttributesNode,
         @Cached ConditionProfile brokenProfile,
         @Cached BranchProfile outOfMemoryProfile
      ) {
         a.checkEncoding(expectedEncoding);
         if (n < 0) {
            throw InternalErrors.illegalArgument("n must be positive");
         } else if (a.isEmpty() || n == 0) {
            return expectedEncoding.getEmpty();
         } else if (n == 1) {
            return asTruffleStringNode.execute(a, expectedEncoding);
         } else {
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeNode.execute(a);
            int codePointLengthA = getCodePointLengthNode.execute(a);
            int byteLengthA = a.length() << a.stride();
            long byteLength = (long)byteLengthA * n;
            if (Long.compareUnsigned(byteLength, 2147483639L) > 0) {
               outOfMemoryProfile.enter();
               throw InternalErrors.outOfMemory();
            } else {
               byte[] array = new byte[(int)byteLength];
               int offsetB = 0;

               for (int i = 0; i < n; i++) {
                  TStringOps.arraycopyWithStride(this, arrayA, a.offset(), 0, 0, array, offsetB, 0, 0, byteLengthA);
                  offsetB += byteLengthA;
                  TStringConstants.truffleSafePointPoll(this, i + 1);
               }

               int length = (int)(byteLength >> a.stride());
               if (brokenProfile.profile(TStringGuards.isBrokenFixedWidth(codeRangeA) || TStringGuards.isBrokenMultiByte(codeRangeA))) {
                  long attrs = calcStringAttributesNode.execute(null, array, 0, length, a.stride(), expectedEncoding, TSCodeRange.getUnknown());
                  codeRangeA = StringAttributes.getCodeRange(attrs);
                  codePointLengthA = StringAttributes.getCodePointLength(attrs);
               } else {
                  codePointLengthA *= n;
               }

               return TruffleString.createFromByteArray(array, length, a.stride(), expectedEncoding, codePointLengthA, codeRangeA);
            }
         }
      }

      public static TruffleString.RepeatNode create() {
         return TruffleStringFactory.RepeatNodeGen.create();
      }

      public static TruffleString.RepeatNode getUncached() {
         return TruffleStringFactory.RepeatNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class SubstringByteIndexNode extends Node {
      SubstringByteIndexNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, int fromByteIndex, int byteLength, TruffleString.Encoding expectedEncoding, boolean lazy);

      static boolean isSame(int v0, int v1) {
         return v0 == v1;
      }

      @Specialization(guards = "isSame(byteLength, 0)")
      static TruffleString substringEmpty(AbstractTruffleString a, int fromByteIndex, int byteLength, TruffleString.Encoding expectedEncoding, boolean lazy) {
         a.checkEncoding(expectedEncoding);
         int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
         a.boundsCheckRegionRaw(fromIndex, 0);
         return expectedEncoding.getEmpty();
      }

      @Specialization(guards = "byteLength != 0")
      static TruffleString substringRaw(
         AbstractTruffleString a,
         int fromByteIndex,
         int byteLength,
         TruffleString.Encoding expectedEncoding,
         boolean lazy,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.SubstringNode substringNode
      ) {
         a.checkEncoding(expectedEncoding);
         int codeRangeA = getCodeRangeANode.execute(a);
         int fromIndex = AbstractTruffleString.rawIndex(fromByteIndex, expectedEncoding);
         int length = AbstractTruffleString.rawIndex(byteLength, expectedEncoding);
         a.boundsCheckRegionRaw(fromIndex, length);
         return substringNode.execute(a, toIndexableNode.execute(a, a.data()), codeRangeA, expectedEncoding, fromIndex, length, lazy && a.isImmutable());
      }

      public static TruffleString.SubstringByteIndexNode create() {
         return TruffleStringFactory.SubstringByteIndexNodeGen.create();
      }

      public static TruffleString.SubstringByteIndexNode getUncached() {
         return TruffleStringFactory.SubstringByteIndexNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   public abstract static class SubstringNode extends Node {
      SubstringNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, int fromIndex, int length, TruffleString.Encoding expectedEncoding, boolean lazy);

      @Specialization
      static TruffleString substring(
         AbstractTruffleString a,
         int fromIndex,
         int length,
         TruffleString.Encoding expectedEncoding,
         boolean lazy,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeANode,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.CodePointIndexToRawNode translateIndexNode,
         @Cached TStringInternalNodes.SubstringNode substringNode
      ) {
         a.checkEncoding(expectedEncoding);
         a.boundsCheckRegion(fromIndex, length, getCodePointLengthNode);
         if (length == 0) {
            return expectedEncoding.getEmpty();
         } else {
            Object arrayA = toIndexableNode.execute(a, a.data());
            int codeRangeA = getCodeRangeANode.execute(a);
            int fromIndexRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, 0, fromIndex, false);
            int lengthRaw = translateIndexNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, length, true);
            return substringNode.execute(a, arrayA, codeRangeA, expectedEncoding, fromIndexRaw, lengthRaw, lazy && a.isImmutable());
         }
      }

      public static TruffleString.SubstringNode create() {
         return TruffleStringFactory.SubstringNodeGen.create();
      }

      public static TruffleString.SubstringNode getUncached() {
         return TruffleStringFactory.SubstringNodeGen.getUncached();
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class SwitchEncodingNode extends Node {
      SwitchEncodingNode() {
      }

      public abstract TruffleString execute(AbstractTruffleString a, TruffleString.Encoding encoding);

      @Specialization(guards = "a.isCompatibleTo(encoding)")
      static TruffleString compatibleImmutable(TruffleString a, TruffleString.Encoding encoding) {
         assert !a.isJavaString();

         return a;
      }

      @Specialization(guards = "a.isCompatibleTo(encoding)")
      static TruffleString compatibleMutable(
         MutableTruffleString a, TruffleString.Encoding encoding, @Cached TruffleString.AsTruffleStringNode asTruffleStringNode
      ) {
         return asTruffleStringNode.execute(a, encoding);
      }

      @Specialization(guards = "!a.isCompatibleTo(encoding)")
      static TruffleString transCode(
         TruffleString a,
         TruffleString.Encoding encoding,
         @Cached ConditionProfile cacheHit,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached @Cached.Shared("transCodeNode") TStringInternalNodes.TransCodeNode transCodeNode
      ) {
         if (a.isEmpty()) {
            return encoding.getEmpty();
         } else {
            TruffleString cur = a.next;

            assert !a.isJavaString();

            if (cur != null) {
               while (cur != a && cur.encoding() != encoding.id || TStringGuards.isUTF16(encoding) && cur.isJavaString()) {
                  cur = cur.next;
               }

               if (cacheHit.profile(cur.encoding() == encoding.id)) {
                  assert !cur.isJavaString();

                  return cur;
               }
            }

            TruffleString transCoded = transCodeNode.execute(a, toIndexableNode.execute(a, a.data()), a.codePointLength(), a.codeRange(), encoding);
            if (!transCoded.isCacheHead()) {
               a.cacheInsert(transCoded);
            }

            return transCoded;
         }
      }

      @Specialization(guards = "!a.isCompatibleTo(encoding)")
      TruffleString transCodeMutable(
         MutableTruffleString a,
         TruffleString.Encoding encoding,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached @Cached.Shared("transCodeNode") TStringInternalNodes.TransCodeNode transCodeNode,
         @Cached ConditionProfile isCompatibleProfile
      ) {
         if (a.isEmpty()) {
            return encoding.getEmpty();
         } else {
            int codePointLengthA = getCodePointLengthNode.execute(a);
            int codeRangeA = getCodeRangeNode.execute(a);
            if (isCompatibleProfile.profile(codeRangeA < encoding.maxCompatibleCodeRange)) {
               int strideDst = Stride.fromCodeRange(codeRangeA, encoding);
               byte[] arrayDst = new byte[a.length() << strideDst];
               TStringOps.arraycopyWithStride(this, a.data(), a.offset(), a.stride(), 0, arrayDst, 0, strideDst, 0, a.length());
               return TruffleString.createFromByteArray(arrayDst, a.length(), strideDst, encoding, codePointLengthA, codeRangeA);
            } else {
               return transCodeNode.execute(a, a.data(), codePointLengthA, codeRangeA, encoding);
            }
         }
      }

      public static TruffleString.SwitchEncodingNode create() {
         return TruffleStringFactory.SwitchEncodingNodeGen.create();
      }

      public static TruffleString.SwitchEncodingNode getUncached() {
         return TruffleStringFactory.SwitchEncodingNodeGen.getUncached();
      }
   }

   @ImportStatic(TStringGuards.class)
   abstract static class ToIndexableNode extends Node {
      abstract Object execute(AbstractTruffleString a, Object data);

      static TruffleString.ToIndexableNode create() {
         return TruffleStringFactory.ToIndexableNodeFactory.ToIndexableImplNodeGen.create();
      }

      static TruffleString.ToIndexableNode getUncached() {
         return TruffleString.ToIndexableNode.Uncached.INSTANCE;
      }

      abstract static class ToIndexableImplNode extends TruffleString.ToIndexableNode {
         @Specialization
         static byte[] doByteArray(AbstractTruffleString a, byte[] data) {
            return data;
         }

         @Specialization(guards = "isSupportedEncoding(a.encoding())")
         static AbstractTruffleString.NativePointer doNativeSupported(AbstractTruffleString a, AbstractTruffleString.NativePointer data) {
            return data;
         }

         @Specialization(guards = "!isSupportedEncoding(a.encoding())")
         static AbstractTruffleString.NativePointer doNativeUnsupported(
            AbstractTruffleString a, AbstractTruffleString.NativePointer data, @Cached ConditionProfile materializeProfile
         ) {
            data.materializeByteArray(a, materializeProfile);
            return data;
         }

         @Specialization
         byte[] doLazyConcat(AbstractTruffleString a, AbstractTruffleString.LazyConcat data) {
            return doLazyConcatIntl(this, a);
         }

         private static byte[] doLazyConcatIntl(TruffleString.ToIndexableNode location, AbstractTruffleString a) {
            a.setData(AbstractTruffleString.LazyConcat.flatten(location, (TruffleString)a));
            return (byte[])a.data();
         }

         @Specialization
         static byte[] doLazyLong(AbstractTruffleString a, AbstractTruffleString.LazyLong data, @Cached ConditionProfile materializeProfile) {
            if (materializeProfile.profile(data.bytes == null)) {
               data.setBytes((TruffleString)a, NumberConversion.longToString(data.value, a.length()));
            }

            return data.bytes;
         }
      }

      @DenyReplace
      private static final class Uncached extends TruffleString.ToIndexableNode {
         private static final TruffleString.ToIndexableNode.Uncached INSTANCE = new TruffleString.ToIndexableNode.Uncached();

         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(AbstractTruffleString a, Object data) {
            return data instanceof byte[] ? data : slowPath(a, data);
         }

         private static Object slowPath(AbstractTruffleString a, Object data) {
            if (data instanceof AbstractTruffleString.NativePointer) {
               return TStringGuards.isSupportedEncoding(a.encoding())
                  ? TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeSupported(a, (AbstractTruffleString.NativePointer)data)
                  : TruffleString.ToIndexableNode.ToIndexableImplNode.doNativeUnsupported(
                     a, (AbstractTruffleString.NativePointer)data, ConditionProfile.getUncached()
                  );
            } else if (data instanceof AbstractTruffleString.LazyConcat) {
               return TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyConcatIntl(INSTANCE, a);
            } else if (data instanceof AbstractTruffleString.LazyLong) {
               return TruffleString.ToIndexableNode.ToIndexableImplNode.doLazyLong(a, (AbstractTruffleString.LazyLong)data, ConditionProfile.getUncached());
            } else {
               throw new UnsupportedSpecializationException(INSTANCE, new Node[]{null, null}, a, data);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratePackagePrivate
   @GenerateUncached
   public abstract static class ToJavaStringNode extends Node {
      ToJavaStringNode() {
      }

      public abstract String execute(AbstractTruffleString a);

      @Specialization
      static String doUTF16(
         TruffleString a,
         @Cached ConditionProfile cacheHit,
         @Cached TruffleString.ToIndexableNode toIndexableNode,
         @Cached TStringInternalNodes.ToJavaStringNode toJavaStringNode
      ) {
         if (a.isEmpty()) {
            return "";
         } else {
            TruffleString cur = a.next;
            if (cur != null) {
               while (cur != a && !cur.isJavaString()) {
                  cur = cur.next;
               }

               if (cacheHit.profile(cur.isJavaString())) {
                  return (String)cur.data();
               }
            }

            cur = a.next;
            if (cur != null) {
               while (cur != a && !cur.isCompatibleTo(TruffleString.Encoding.UTF_16)) {
                  cur = cur.next;
               }
            } else {
               cur = a;
            }

            if (cur.isJavaString()) {
               return (String)cur.data();
            } else {
               TruffleString s = toJavaStringNode.execute(cur, toIndexableNode.execute(cur, cur.data()));
               a.cacheInsert(s);
               return (String)s.data();
            }
         }
      }

      @Specialization
      static String doMutable(
         MutableTruffleString a,
         @Cached TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNode,
         @Cached TStringInternalNodes.GetCodeRangeNode getCodeRangeNode,
         @Cached TStringInternalNodes.TransCodeNode transCodeNode,
         @Cached TStringInternalNodes.CreateJavaStringNode createJavaStringNode
      ) {
         if (a.isEmpty()) {
            return "";
         } else {
            AbstractTruffleString utf16String;
            int codeRangeA;
            if (!TStringGuards.isUTF16(a.encoding()) && (codeRangeA = getCodeRangeNode.execute(a)) >= TruffleString.Encoding.UTF_16.maxCompatibleCodeRange) {
               utf16String = transCodeNode.execute(a, a.data(), getCodePointLengthNode.execute(a), codeRangeA, TruffleString.Encoding.UTF_16);
            } else {
               utf16String = a;
            }

            return createJavaStringNode.execute(utf16String, utf16String.data());
         }
      }

      public static TruffleString.ToJavaStringNode create() {
         return TruffleStringFactory.ToJavaStringNodeGen.create();
      }

      public static TruffleString.ToJavaStringNode getUncached() {
         return TruffleStringFactory.ToJavaStringNodeGen.getUncached();
      }
   }

   public static final class WithMask {
      final AbstractTruffleString string;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final byte[] mask;

      WithMask(AbstractTruffleString string, byte[] mask) {
         this.string = string;
         this.mask = mask;
      }

      public static TruffleString.WithMask createUncached(AbstractTruffleString a, byte[] mask, TruffleString.Encoding expectedEncoding) {
         return TruffleString.WithMask.CreateNode.getUncached().execute(a, mask, expectedEncoding);
      }

      public static TruffleString.WithMask createUTF16Uncached(AbstractTruffleString a, char[] mask) {
         return TruffleString.WithMask.CreateUTF16Node.getUncached().execute(a, mask);
      }

      public static TruffleString.WithMask createUTF32Uncached(AbstractTruffleString a, int[] mask) {
         return TruffleString.WithMask.CreateUTF32Node.getUncached().execute(a, mask);
      }

      private static void checkMaskLength(AbstractTruffleString string, int length) {
         if (length != string.length()) {
            throw InternalErrors.illegalArgument("mask length does not match string length!");
         }
      }

      @GeneratePackagePrivate
      @ImportStatic(TStringGuards.class)
      @GenerateUncached
      public abstract static class CreateNode extends Node {
         CreateNode() {
         }

         public abstract TruffleString.WithMask execute(AbstractTruffleString a, byte[] mask, TruffleString.Encoding expectedEncoding);

         @Specialization
         TruffleString.WithMask doCreate(AbstractTruffleString a, byte[] mask, TruffleString.Encoding expectedEncoding) {
            if (expectedEncoding != TruffleString.Encoding.UTF_16 && expectedEncoding != TruffleString.Encoding.UTF_32) {
               a.checkEncoding(expectedEncoding);
               TruffleString.WithMask.checkMaskLength(a, mask.length);

               assert TStringGuards.isStride0(a);

               return new TruffleString.WithMask(a, Arrays.copyOf(mask, mask.length));
            } else {
               throw InternalErrors.illegalArgument("use a CreateUTF16Node for UTF-16, and CreateUTF32Node for UTF-32");
            }
         }

         public static TruffleString.WithMask.CreateNode create() {
            return TruffleStringFactory.WithMaskFactory.CreateNodeGen.create();
         }

         public static TruffleString.WithMask.CreateNode getUncached() {
            return TruffleStringFactory.WithMaskFactory.CreateNodeGen.getUncached();
         }
      }

      @GeneratePackagePrivate
      @ImportStatic(TStringGuards.class)
      @GenerateUncached
      public abstract static class CreateUTF16Node extends Node {
         CreateUTF16Node() {
         }

         public abstract TruffleString.WithMask execute(AbstractTruffleString a, char[] mask);

         @Specialization
         TruffleString.WithMask doCreate(AbstractTruffleString a, char[] mask) {
            a.checkEncoding(TruffleString.Encoding.UTF_16);
            TruffleString.WithMask.checkMaskLength(a, mask.length);
            byte[] maskBytes = new byte[a.length() << a.stride()];
            if (a.stride() == 0) {
               TStringOps.arraycopyWithStrideCB(this, mask, 0, maskBytes, 0, 0, mask.length);
            } else {
               TStringOps.arraycopyWithStrideCB(this, mask, 0, maskBytes, 0, 1, mask.length);
            }

            return new TruffleString.WithMask(a, maskBytes);
         }

         public static TruffleString.WithMask.CreateUTF16Node create() {
            return TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.create();
         }

         public static TruffleString.WithMask.CreateUTF16Node getUncached() {
            return TruffleStringFactory.WithMaskFactory.CreateUTF16NodeGen.getUncached();
         }
      }

      @GeneratePackagePrivate
      @ImportStatic(TStringGuards.class)
      @GenerateUncached
      public abstract static class CreateUTF32Node extends Node {
         CreateUTF32Node() {
         }

         public abstract TruffleString.WithMask execute(AbstractTruffleString a, int[] mask);

         @Specialization
         TruffleString.WithMask doCreate(AbstractTruffleString a, int[] mask) {
            a.checkEncoding(TruffleString.Encoding.UTF_32);
            TruffleString.WithMask.checkMaskLength(a, mask.length);
            byte[] maskBytes = new byte[a.length() << a.stride()];
            if (a.stride() == 0) {
               TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 0, mask.length);
            } else if (a.stride() == 1) {
               TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 1, mask.length);
            } else {
               TStringOps.arraycopyWithStrideIB(this, mask, 0, maskBytes, 0, 2, mask.length);
            }

            return new TruffleString.WithMask(a, maskBytes);
         }

         public static TruffleString.WithMask.CreateUTF32Node create() {
            return TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.create();
         }

         public static TruffleString.WithMask.CreateUTF32Node getUncached() {
            return TruffleStringFactory.WithMaskFactory.CreateUTF32NodeGen.getUncached();
         }
      }
   }
}
