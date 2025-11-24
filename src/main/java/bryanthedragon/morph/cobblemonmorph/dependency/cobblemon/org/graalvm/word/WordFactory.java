
package org.graalvm.word;

import org.graalvm.word.PointerBase;
import org.graalvm.word.SignedWord;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordBase;
import org.graalvm.word.impl.WordBoxFactory;
import org.graalvm.word.impl.WordFactoryOpcode;
import org.graalvm.word.impl.WordFactoryOperation;

public final class WordFactory {
    private WordFactory() {
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.ZERO)
    public static <T extends WordBase> T zero() {
        return WordBoxFactory.box(0L);
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.ZERO)
    public static <T extends PointerBase> T nullPointer() {
        return (T)((PointerBase)WordBoxFactory.box(0L));
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.FROM_UNSIGNED)
    public static <T extends UnsignedWord> T unsigned(long val) {
        return (T)((UnsignedWord)WordBoxFactory.box(val));
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.FROM_UNSIGNED)
    public static <T extends PointerBase> T pointer(long val) {
        return (T)((PointerBase)WordBoxFactory.box(val));
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.FROM_UNSIGNED)
    public static <T extends UnsignedWord> T unsigned(int val) {
        return (T)((UnsignedWord)WordBoxFactory.box((long)val & 0xFFFFFFFFL));
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.FROM_SIGNED)
    public static <T extends SignedWord> T signed(long val) {
        return (T)((SignedWord)WordBoxFactory.box(val));
    }

    @WordFactoryOperation(opcode=WordFactoryOpcode.FROM_SIGNED)
    public static <T extends SignedWord> T signed(int val) {
        return (T)((SignedWord)WordBoxFactory.box(val));
    }
}

