package com.oracle.truffle.api.source;

import org.graalvm.polyglot.io.ByteSequence;

class ByteSequenceWrapper implements ByteSequence {
   private final ByteSequence delegate;

   ByteSequenceWrapper(ByteSequence delegate) {
      this.delegate = delegate;
   }

   @Override
   public int length() {
      return this.delegate.length();
   }

   @Override
   public byte byteAt(int index) {
      return this.delegate.byteAt(index);
   }

   @Override
   public ByteSequence subSequence(int start, int end) {
      return this.delegate.subSequence(start, end);
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof ByteSequenceWrapper ? this.delegate.equals(((ByteSequenceWrapper)obj).delegate) : this.delegate.equals(obj);
   }

   @Override
   public int hashCode() {
      return this.delegate.hashCode();
   }

   @Override
   public String toString() {
      return this.delegate.toString();
   }
}
