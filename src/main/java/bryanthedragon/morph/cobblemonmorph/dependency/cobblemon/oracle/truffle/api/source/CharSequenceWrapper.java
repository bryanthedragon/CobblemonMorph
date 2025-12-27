package com.oracle.truffle.api.source;

class CharSequenceWrapper implements CharSequence {
   private final CharSequence delegate;

   CharSequenceWrapper(CharSequence delegate) {
      this.delegate = delegate;
   }

   @Override
   public int length() {
      return this.delegate.length();
   }

   @Override
   public char charAt(int index) {
      return this.delegate.charAt(index);
   }

   @Override
   public CharSequence subSequence(int start, int end) {
      return this.delegate.subSequence(start, end);
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof CharSequenceWrapper ? this.delegate.equals(((CharSequenceWrapper)obj).delegate) : this.delegate.equals(obj);
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
