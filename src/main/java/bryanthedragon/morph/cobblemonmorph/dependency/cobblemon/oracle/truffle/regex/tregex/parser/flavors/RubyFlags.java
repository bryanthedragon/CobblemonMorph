
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.AbstractConstantKeysObject;
import com.oracle.truffle.regex.util.TruffleReadOnlyKeysArray;

@ExportLibrary(value=InteropLibrary.class)
public final class RubyFlags
extends AbstractConstantKeysObject {
    private static final String PROP_EXTENDED = "EXTENDED";
    private static final String PROP_IGNORECASE = "IGNORECASE";
    private static final String PROP_MULTILINE = "MULTILINE";
    private static final TruffleReadOnlyKeysArray KEYS = new TruffleReadOnlyKeysArray("EXTENDED", "IGNORECASE", "MULTILINE");
    private final int value;
    private final Mode mode;
    private static final String FLAGS = "mixadu";
    private static final String BIT_FLAGS = "mixy";
    private static final String COMPILE_TIME_FLAGS = "mix";
    private static final String TYPE_FLAGS = "adu";
    public static final RubyFlags EMPTY_INSTANCE = new RubyFlags("");

    public RubyFlags(String source) {
        int bits = 0;
        for (int i = 0; i < source.length(); ++i) {
            assert (RubyFlags.isBitFlag(source.charAt(i)));
            bits |= RubyFlags.maskForFlag(source.charAt(i));
        }
        this.value = bits;
        this.mode = Mode.Default;
    }

    private RubyFlags(int value2, Mode mode) {
        this.value = value2;
        this.mode = mode;
    }

    private static int maskForFlag(int flagChar) {
        return 1 << RubyFlags.indexOfUnrolled(BIT_FLAGS, flagChar);
    }

    public boolean hasFlag(int flagChar) {
        if (RubyFlags.isTypeFlag(flagChar)) {
            return this.mode.equals((Object)Mode.fromFlagChar(flagChar));
        }
        return (this.value & RubyFlags.maskForFlag(flagChar)) != 0;
    }

    public boolean isIgnoreCase() {
        return this.hasFlag(105);
    }

    public boolean isMultiline() {
        return this.hasFlag(109);
    }

    public boolean isExtended() {
        return this.hasFlag(120);
    }

    public boolean isSticky() {
        return this.hasFlag(121);
    }

    public boolean isAscii() {
        return this.hasFlag(97);
    }

    public boolean isDefault() {
        return this.hasFlag(100);
    }

    public boolean isUnicode() {
        return this.hasFlag(117);
    }

    public RubyFlags addFlag(int flagChar) {
        if (RubyFlags.isTypeFlag(flagChar)) {
            return new RubyFlags(this.value, Mode.fromFlagChar(flagChar));
        }
        return new RubyFlags(this.value | RubyFlags.maskForFlag(flagChar), this.mode);
    }

    public RubyFlags delFlag(int flagChar) {
        assert (RubyFlags.isBitFlag(flagChar));
        return new RubyFlags(this.value & ~RubyFlags.maskForFlag(flagChar), this.mode);
    }

    public static boolean isValidFlagChar(int candidateChar) {
        return RubyFlags.indexOfUnrolled(FLAGS, candidateChar) >= 0;
    }

    public static boolean isBitFlag(int candidateChar) {
        return RubyFlags.indexOfUnrolled(BIT_FLAGS, candidateChar) >= 0;
    }

    public static boolean isTypeFlag(int candidateChar) {
        return RubyFlags.indexOfUnrolled(TYPE_FLAGS, candidateChar) >= 0;
    }

    @ExplodeLoop
    private static int indexOfUnrolled(String flags, int candidateChar) {
        for (int i = 0; i < flags.length(); ++i) {
            char c = flags.charAt(i);
            if (candidateChar != c) continue;
            return i;
        }
        return -1;
    }

    public boolean equals(Object other) {
        return other instanceof RubyFlags && this.value == ((RubyFlags)other).value;
    }

    public int hashCode() {
        return this.value;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringBuilder out = new StringBuilder(COMPILE_TIME_FLAGS.length());
        for (int i = 0; i < COMPILE_TIME_FLAGS.length(); ++i) {
            char flag = COMPILE_TIME_FLAGS.charAt(i);
            if (!this.hasFlag(flag)) continue;
            out.append(flag);
        }
        return out.toString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    @ExportMessage
    public Object toDisplayString(boolean allowSideEffects) {
        return "TRegexRubyFlags{flags=" + this.toString() + "}";
    }

    @Override
    public TruffleReadOnlyKeysArray getKeys() {
        return KEYS;
    }

    @Override
    public boolean isMemberReadableImpl(String symbol) {
        switch (symbol) {
            case "EXTENDED": 
            case "IGNORECASE": 
            case "MULTILINE": {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object readMemberImpl(String symbol) throws UnknownIdentifierException {
        switch (symbol) {
            case "EXTENDED": {
                return this.isExtended();
            }
            case "IGNORECASE": {
                return this.isIgnoreCase();
            }
            case "MULTILINE": {
                return this.isMultiline();
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw UnknownIdentifierException.create(symbol);
    }

    private static enum Mode {
        Ascii,
        Default,
        Unicode;

        public static final Mode[] VALUES;

        public static Mode fromFlagChar(int ch) {
            return VALUES[RubyFlags.indexOfUnrolled(RubyFlags.TYPE_FLAGS, ch)];
        }

        static {
            VALUES = Mode.values();
        }
    }
}

