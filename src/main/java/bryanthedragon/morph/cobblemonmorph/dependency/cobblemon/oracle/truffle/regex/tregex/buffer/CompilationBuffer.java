package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TBitSet;
import org.graalvm.collections.EconomicMap;

public class CompilationBuffer {
   private final Encodings.Encoding encoding;
   private ObjectArrayBuffer<Object> objectBuffer1;
   private ObjectArrayBuffer<Object> objectBuffer2;
   private ObjectArrayBuffer<Object> objectBuffer3;
   private ByteArrayBuffer byteArrayBuffer;
   private ShortArrayBuffer shortArrayBuffer1;
   private ShortArrayBuffer shortArrayBuffer2;
   private IntRangesBuffer intRangesBuffer1;
   private IntRangesBuffer intRangesBuffer2;
   private IntRangesBuffer intRangesBuffer3;
   private CodePointSetAccumulator codePointSetAccumulator1;
   private CodePointSetAccumulator codePointSetAccumulator2;
   private TBitSet byteSizeBitSet;
   private EconomicMap<CodePointSet, CharMatcher> matcherDeduplicationMap;
   private EconomicMap<DFACaptureGroupPartialTransition, DFACaptureGroupPartialTransition> lazyTransitionDeduplicationMap;

   public CompilationBuffer(Encodings.Encoding encoding) {
      this.encoding = encoding;
   }

   public Encodings.Encoding getEncoding() {
      return this.encoding;
   }

   public <T> ObjectArrayBuffer<T> getObjectBuffer1() {
      if (this.objectBuffer1 == null) {
         this.objectBuffer1 = new ObjectArrayBuffer<>();
      }

      this.objectBuffer1.clear();
      return (ObjectArrayBuffer<T>)this.objectBuffer1;
   }

   public <T> ObjectArrayBuffer<T> getObjectBuffer2() {
      if (this.objectBuffer2 == null) {
         this.objectBuffer2 = new ObjectArrayBuffer<>();
      }

      this.objectBuffer2.clear();
      return (ObjectArrayBuffer<T>)this.objectBuffer2;
   }

   public <T> ObjectArrayBuffer<T> getObjectBuffer3() {
      if (this.objectBuffer3 == null) {
         this.objectBuffer3 = new ObjectArrayBuffer<>();
      }

      this.objectBuffer3.clear();
      return (ObjectArrayBuffer<T>)this.objectBuffer3;
   }

   public ByteArrayBuffer getByteArrayBuffer() {
      if (this.byteArrayBuffer == null) {
         this.byteArrayBuffer = new ByteArrayBuffer();
      }

      this.byteArrayBuffer.clear();
      return this.byteArrayBuffer;
   }

   public ShortArrayBuffer getShortArrayBuffer1() {
      if (this.shortArrayBuffer1 == null) {
         this.shortArrayBuffer1 = new ShortArrayBuffer();
      }

      this.shortArrayBuffer1.clear();
      return this.shortArrayBuffer1;
   }

   public ShortArrayBuffer getShortArrayBuffer2() {
      if (this.shortArrayBuffer2 == null) {
         this.shortArrayBuffer2 = new ShortArrayBuffer();
      }

      this.shortArrayBuffer2.clear();
      return this.shortArrayBuffer2;
   }

   public IntRangesBuffer getIntRangesBuffer1() {
      if (this.intRangesBuffer1 == null) {
         this.intRangesBuffer1 = new IntRangesBuffer(64);
      }

      this.intRangesBuffer1.clear();
      return this.intRangesBuffer1;
   }

   public IntRangesBuffer getIntRangesBuffer2() {
      if (this.intRangesBuffer2 == null) {
         this.intRangesBuffer2 = new IntRangesBuffer(64);
      }

      this.intRangesBuffer2.clear();
      return this.intRangesBuffer2;
   }

   public IntRangesBuffer getIntRangesBuffer3() {
      if (this.intRangesBuffer3 == null) {
         this.intRangesBuffer3 = new IntRangesBuffer(64);
      }

      this.intRangesBuffer3.clear();
      return this.intRangesBuffer3;
   }

   public CodePointSetAccumulator getCodePointSetAccumulator1() {
      if (this.codePointSetAccumulator1 == null) {
         this.codePointSetAccumulator1 = new CodePointSetAccumulator();
      }

      this.codePointSetAccumulator1.clear();
      return this.codePointSetAccumulator1;
   }

   public CodePointSetAccumulator getCodePointSetAccumulator2() {
      if (this.codePointSetAccumulator2 == null) {
         this.codePointSetAccumulator2 = new CodePointSetAccumulator();
      }

      this.codePointSetAccumulator2.clear();
      return this.codePointSetAccumulator2;
   }

   public TBitSet getByteSizeBitSet() {
      if (this.byteSizeBitSet == null) {
         this.byteSizeBitSet = new TBitSet(256);
      }

      this.byteSizeBitSet.clear();
      return this.byteSizeBitSet;
   }

   public EconomicMap<CodePointSet, CharMatcher> getMatcherDeduplicationMap() {
      if (this.matcherDeduplicationMap == null) {
         this.matcherDeduplicationMap = EconomicMap.create();
      }

      return this.matcherDeduplicationMap;
   }

   public EconomicMap<DFACaptureGroupPartialTransition, DFACaptureGroupPartialTransition> getLazyTransitionDeduplicationMap() {
      if (this.lazyTransitionDeduplicationMap == null) {
         this.lazyTransitionDeduplicationMap = EconomicMap.create();
      }

      return this.lazyTransitionDeduplicationMap;
   }
}
