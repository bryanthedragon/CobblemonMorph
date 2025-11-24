
package org.graalvm.word;

import org.graalvm.word.ComparableWord;

public interface UnsignedWord
extends ComparableWord {
    public UnsignedWord add(UnsignedWord var1);

    public UnsignedWord subtract(UnsignedWord var1);

    public UnsignedWord multiply(UnsignedWord var1);

    public UnsignedWord unsignedDivide(UnsignedWord var1);

    public UnsignedWord unsignedRemainder(UnsignedWord var1);

    public UnsignedWord shiftLeft(UnsignedWord var1);

    public UnsignedWord unsignedShiftRight(UnsignedWord var1);

    public UnsignedWord and(UnsignedWord var1);

    public UnsignedWord or(UnsignedWord var1);

    public UnsignedWord xor(UnsignedWord var1);

    public UnsignedWord not();

    public boolean equal(UnsignedWord var1);

    public boolean notEqual(UnsignedWord var1);

    public boolean belowThan(UnsignedWord var1);

    public boolean belowOrEqual(UnsignedWord var1);

    public boolean aboveThan(UnsignedWord var1);

    public boolean aboveOrEqual(UnsignedWord var1);

    public UnsignedWord add(int var1);

    public UnsignedWord subtract(int var1);

    public UnsignedWord multiply(int var1);

    public UnsignedWord unsignedDivide(int var1);

    public UnsignedWord unsignedRemainder(int var1);

    public UnsignedWord shiftLeft(int var1);

    public UnsignedWord unsignedShiftRight(int var1);

    public UnsignedWord and(int var1);

    public UnsignedWord or(int var1);

    public UnsignedWord xor(int var1);

    public boolean equal(int var1);

    public boolean notEqual(int var1);

    public boolean belowThan(int var1);

    public boolean belowOrEqual(int var1);

    public boolean aboveThan(int var1);

    public boolean aboveOrEqual(int var1);
}

