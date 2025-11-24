
package org.graalvm.word;

import org.graalvm.word.ComparableWord;
import org.graalvm.word.UnsignedWord;

public interface SignedWord
extends ComparableWord {
    public SignedWord add(SignedWord var1);

    public SignedWord subtract(SignedWord var1);

    public SignedWord multiply(SignedWord var1);

    public SignedWord signedDivide(SignedWord var1);

    public SignedWord signedRemainder(SignedWord var1);

    public SignedWord shiftLeft(UnsignedWord var1);

    public SignedWord signedShiftRight(UnsignedWord var1);

    public SignedWord and(SignedWord var1);

    public SignedWord or(SignedWord var1);

    public SignedWord xor(SignedWord var1);

    public SignedWord not();

    public boolean equal(SignedWord var1);

    public boolean notEqual(SignedWord var1);

    public boolean lessThan(SignedWord var1);

    public boolean lessOrEqual(SignedWord var1);

    public boolean greaterThan(SignedWord var1);

    public boolean greaterOrEqual(SignedWord var1);

    public SignedWord add(int var1);

    public SignedWord subtract(int var1);

    public SignedWord multiply(int var1);

    public SignedWord signedDivide(int var1);

    public SignedWord signedRemainder(int var1);

    public SignedWord shiftLeft(int var1);

    public SignedWord signedShiftRight(int var1);

    public SignedWord and(int var1);

    public SignedWord or(int var1);

    public SignedWord xor(int var1);

    public boolean equal(int var1);

    public boolean notEqual(int var1);

    public boolean lessThan(int var1);

    public boolean lessOrEqual(int var1);

    public boolean greaterThan(int var1);

    public boolean greaterOrEqual(int var1);
}

