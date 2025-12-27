package com.oracle.truffle.js.runtime.doubleconv;

class DiyFp {
   private long f_;
   private int e_;
   static final int kSignificandSize = 64;
   static final long kUint64MSB = Long.MIN_VALUE;

   DiyFp() {
      this.f_ = 0L;
      this.e_ = 0;
   }

   DiyFp(final long f, final int e) {
      this.f_ = f;
      this.e_ = e;
   }

   void subtract(final DiyFp other) {
      assert this.e_ == other.e_;

      assert Long.compareUnsigned(this.f_, other.f_) >= 0;

      this.f_ = this.f_ - other.f_;
   }

   static DiyFp minus(final DiyFp a, final DiyFp b) {
      DiyFp result = new DiyFp(a.f_, a.e_);
      result.subtract(b);
      return result;
   }

   final void multiply(final DiyFp other) {
      long kM32 = 4294967295L;
      long a = this.f_ >>> 32;
      long b = this.f_ & 4294967295L;
      long c = other.f_ >>> 32;
      long d = other.f_ & 4294967295L;
      long ac = a * c;
      long bc = b * c;
      long ad = a * d;
      long bd = b * d;
      long tmp = (bd >>> 32) + (ad & 4294967295L) + (bc & 4294967295L);
      tmp += 2147483648L;
      long result_f = ac + (ad >>> 32) + (bc >>> 32) + (tmp >>> 32);
      this.e_ = this.e_ + other.e_ + 64;
      this.f_ = result_f;
   }

   static DiyFp times(final DiyFp a, final DiyFp b) {
      DiyFp result = new DiyFp(a.f_, a.e_);
      result.multiply(b);
      return result;
   }

   void normalize() {
      assert this.f_ != 0L;

      long significand = this.f_;
      int exponent = this.e_;

      for (long k10MSBits = -18014398509481984L; (significand & -18014398509481984L) == 0L; exponent -= 10) {
         significand <<= 10;
      }

      while ((significand & Long.MIN_VALUE) == 0L) {
         significand <<= 1;
         exponent--;
      }

      this.f_ = significand;
      this.e_ = exponent;
   }

   static DiyFp normalize(final DiyFp a) {
      DiyFp result = new DiyFp(a.f_, a.e_);
      result.normalize();
      return result;
   }

   long f() {
      return this.f_;
   }

   int e() {
      return this.e_;
   }

   void setF(final long new_value) {
      this.f_ = new_value;
   }

   void setE(final int new_value) {
      this.e_ = new_value;
   }

   @Override
   public String toString() {
      return "DiyFp[f=" + this.f_ + ", e=" + this.e_ + "]";
   }
}
