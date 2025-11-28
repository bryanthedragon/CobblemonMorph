package org.graalvm.word;

public interface SignedWord extends ComparableWord {
   SignedWord add(SignedWord val);

   SignedWord subtract(SignedWord val);

   SignedWord multiply(SignedWord val);

   SignedWord signedDivide(SignedWord val);

   SignedWord signedRemainder(SignedWord val);

   SignedWord shiftLeft(UnsignedWord n);

   SignedWord signedShiftRight(UnsignedWord n);

   SignedWord and(SignedWord val);

   SignedWord or(SignedWord val);

   SignedWord xor(SignedWord val);

   SignedWord not();

   boolean equal(SignedWord val);

   boolean notEqual(SignedWord val);

   boolean lessThan(SignedWord val);

   boolean lessOrEqual(SignedWord val);

   boolean greaterThan(SignedWord val);

   boolean greaterOrEqual(SignedWord val);

   SignedWord add(int val);

   SignedWord subtract(int val);

   SignedWord multiply(int val);

   SignedWord signedDivide(int val);

   SignedWord signedRemainder(int val);

   SignedWord shiftLeft(int n);

   SignedWord signedShiftRight(int n);

   SignedWord and(int val);

   SignedWord or(int val);

   SignedWord xor(int val);

   boolean equal(int val);

   boolean notEqual(int val);

   boolean lessThan(int val);

   boolean lessOrEqual(int val);

   boolean greaterThan(int val);

   boolean greaterOrEqual(int val);
}
