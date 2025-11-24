
package com.oracle.js.parser.ir;

import com.oracle.truffle.api.strings.TruffleString;

public final class Symbol
implements Comparable<Symbol> {
    public static final int IS_LET = 1;
    public static final int IS_CONST = 2;
    public static final int IS_VAR = 4;
    public static final int KINDMASK = 7;
    public static final int IS_GLOBAL = 8;
    public static final int IS_PARAM = 16;
    public static final int IS_THIS = 32;
    public static final int IS_INTERNAL = 64;
    public static final int IS_FUNCTION_SELF = 128;
    public static final int IS_HOISTABLE_DECLARATION = 256;
    public static final int IS_PROGRAM_LEVEL = 512;
    public static final int HAS_BEEN_DECLARED = 1024;
    public static final int IS_HOISTED_BLOCK_FUNCTION = 2048;
    public static final int IS_VAR_REDECLARED_HERE = 4096;
    public static final int IS_DECLARED_IN_SWITCH_BLOCK = 8192;
    public static final int IS_IMPORT_BINDING = 16384;
    public static final int IS_CATCH_PARAMETER = 32768;
    public static final int IS_BLOCK_FUNCTION_DECLARATION = 65536;
    public static final int IS_PRIVATE_NAME = 131072;
    public static final int IS_PRIVATE_NAME_STATIC = 262144;
    public static final int IS_PRIVATE_NAME_METHOD = 524288;
    public static final int IS_PRIVATE_NAME_ACCESSOR = 0x100000;
    public static final int IS_ARGUMENTS = 0x200000;
    public static final int IS_USED = 0x400000;
    public static final int IS_CLOSED_OVER = 0x800000;
    public static final int IS_USED_IN_INNER_SCOPE = 0x1000000;
    public static final int IS_SUPER = 0x2000000;
    public static final int IS_NEW_TARGET = 0x4000000;
    private final String name;
    private final TruffleString nameTS;
    private int flags;

    public Symbol(TruffleString name, int flags) {
        this.nameTS = name;
        this.name = name.toJavaStringUncached();
        this.flags = flags;
        assert ((flags & 7) != 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(' ');
        if (this.isLet()) {
            sb.append('L');
        } else if (this.isConst()) {
            sb.append('C');
        } else if (this.isVar()) {
            if (this.isGlobal()) {
                sb.append('G');
            } else if (this.isParam()) {
                sb.append('P');
            } else {
                sb.append('V');
            }
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Symbol other) {
        return this.getName().compareTo(other.getName());
    }

    public boolean isHoistableDeclaration() {
        return (this.flags & 0x100) != 0;
    }

    public boolean isVar() {
        return (this.flags & 4) != 0;
    }

    public boolean isGlobal() {
        return (this.flags & 8) != 0;
    }

    public boolean isParam() {
        return (this.flags & 0x10) != 0;
    }

    public boolean isProgramLevel() {
        return (this.flags & 0x200) != 0;
    }

    public boolean isConst() {
        return (this.flags & 2) != 0;
    }

    public boolean isInternal() {
        return (this.flags & 0x40) != 0;
    }

    public boolean isThis() {
        return (this.flags & 0x20) != 0;
    }

    public boolean isSuper() {
        return (this.flags & 0x2000000) != 0;
    }

    public boolean isNewTarget() {
        return (this.flags & 0x4000000) != 0;
    }

    public boolean isLet() {
        return (this.flags & 1) != 0;
    }

    public boolean isFunctionSelf() {
        return (this.flags & 0x80) != 0;
    }

    public boolean isBlockScoped() {
        return this.isLet() || this.isConst();
    }

    public boolean hasBeenDeclared() {
        return (this.flags & 0x400) != 0;
    }

    public int getFlags() {
        return this.flags;
    }

    public String getName() {
        return this.name;
    }

    public TruffleString getNameTS() {
        return this.nameTS;
    }

    public boolean isDeclaredInSwitchBlock() {
        return (this.flags & 0x2000) != 0;
    }

    public boolean isImportBinding() {
        return (this.flags & 0x4000) != 0;
    }

    public boolean isCatchParameter() {
        return (this.flags & 0x8000) != 0;
    }

    public boolean isVarRedeclaredHere() {
        return (this.flags & 0x1000) != 0;
    }

    public boolean isHoistedBlockFunctionDeclaration() {
        return (this.flags & 0x800) != 0;
    }

    public void setHoistedBlockFunctionDeclaration() {
        assert (this.isBlockScoped());
        this.flags |= 0x800;
    }

    public boolean isBlockFunctionDeclaration() {
        return (this.flags & 0x10000) != 0;
    }

    public boolean isPrivateName() {
        return (this.flags & 0x20000) != 0;
    }

    public boolean isPrivateNameStatic() {
        return (this.flags & 0x40000) != 0;
    }

    public boolean isPrivateField() {
        return this.isPrivateName() && !this.isPrivateMethod() && !this.isPrivateAccessor();
    }

    public boolean isPrivateMethod() {
        return (this.flags & 0x80000) != 0;
    }

    public boolean isPrivateAccessor() {
        return (this.flags & 0x100000) != 0;
    }

    public boolean isArguments() {
        return (this.flags & 0x200000) != 0;
    }

    public boolean isUsed() {
        return (this.flags & 0x400000) != 0;
    }

    public void setUsed() {
        this.flags |= 0x400000;
    }

    public boolean isClosedOver() {
        return (this.flags & 0x800000) != 0;
    }

    public void setClosedOver() {
        this.flags |= 0x800000;
    }

    public boolean isUsedInInnerScope() {
        return (this.flags & 0x1000000) != 0;
    }

    public void setUsedInInnerScope() {
        this.flags |= 0x1000000;
    }
}

