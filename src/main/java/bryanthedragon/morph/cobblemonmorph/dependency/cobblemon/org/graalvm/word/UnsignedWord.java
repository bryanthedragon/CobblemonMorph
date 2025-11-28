package org.graalvm.word;

public interface UnsignedWord extends ComparableWord {
   UnsignedWord add(UnsignedWord val);

   UnsignedWord subtract(UnsignedWord val);

   UnsignedWord multiply(UnsignedWord val);

   UnsignedWord unsignedDivide(UnsignedWord val);

   UnsignedWord unsignedRemainder(UnsignedWord val);

   UnsignedWord shiftLeft(UnsignedWord n);

   UnsignedWord unsignedShiftRight(UnsignedWord n);

   UnsignedWord and(UnsignedWord val);

   UnsignedWord or(UnsignedWord val);

   UnsignedWord xor(UnsignedWord val);

   UnsignedWord not();

   boolean equal(UnsignedWord val);

   boolean notEqual(UnsignedWord val);

   boolean belowThan(UnsignedWord val);

   boolean belowOrEqual(UnsignedWord val);

   boolean aboveThan(UnsignedWord val);

   boolean aboveOrEqual(UnsignedWord val);

   UnsignedWord add(int val);

   UnsignedWord subtract(int val);

   UnsignedWord multiply(int val);

   UnsignedWord unsignedDivide(int val);

   UnsignedWord unsignedRemainder(int val);

   UnsignedWord shiftLeft(int n);

   UnsignedWord unsignedShiftRight(int n);

   UnsignedWord and(int val);

   UnsignedWord or(int val);

   UnsignedWord xor(int val);

   boolean equal(int val);

   boolean notEqual(int val);

   boolean belowThan(int val);

   boolean belowOrEqual(int val);

   boolean aboveThan(int val);

   boolean aboveOrEqual(int val);
}
