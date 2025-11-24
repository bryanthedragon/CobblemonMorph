
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionCall;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.PropertyKey;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public final class IdentNode
extends Expression
implements PropertyKey,
FunctionCall {
    private static final int PROPERTY_NAME = 1;
    private static final int INITIALIZED_HERE = 2;
    private static final int FUNCTION = 4;
    private static final int NEW_TARGET = 8;
    private static final int IS_DECLARED_HERE = 16;
    private static final int THIS = 32;
    private static final int SUPER = 64;
    private static final int DIRECT_SUPER = 128;
    private static final int REST_PARAMETER = 256;
    private static final int CATCH_PARAMETER = 512;
    private static final int IMPORT_META = 1024;
    private static final int ARGUMENTS = 2048;
    private static final int APPLY_ARGUMENTS = 4096;
    private static final int PRIVATE_IDENT = 8192;
    private static final int PRIVATE_IN_CHECK = 16384;
    private static final int IGNORED_PARAMETER = 32768;
    private final String name;
    private final TruffleString nameTS;
    private final int flags;
    private Symbol symbol;

    public IdentNode(long token, int finish, TruffleString name) {
        super(token, finish);
        this.name = name.toJavaStringUncached();
        this.nameTS = name;
        this.flags = 0;
    }

    private IdentNode(IdentNode identNode, String name, TruffleString nameTS, int flags) {
        super(identNode);
        this.name = name;
        this.nameTS = nameTS;
        this.flags = flags;
        this.symbol = identNode.symbol;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIdentNode(this)) {
            return visitor.leaveIdentNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterIdentNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(this.name);
    }

    public String getName() {
        return this.name;
    }

    public TruffleString getNameTS() {
        return this.nameTS;
    }

    @Override
    public String getPropertyName() {
        return this.getName();
    }

    @Override
    public TruffleString getPropertyNameTS() {
        return this.nameTS;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public boolean isPropertyName() {
        return (this.flags & 1) == 1;
    }

    public IdentNode setIsPropertyName() {
        if (this.isPropertyName()) {
            return this;
        }
        return new IdentNode(this, this.name, this.nameTS, this.flags | 1);
    }

    public boolean isInitializedHere() {
        return (this.flags & 2) == 2;
    }

    public IdentNode setIsInitializedHere() {
        if (this.isInitializedHere()) {
            return this;
        }
        return new IdentNode(this, this.name, this.nameTS, this.flags | 2);
    }

    public boolean isDeclaredHere() {
        return (this.flags & 0x10) != 0;
    }

    public IdentNode setIsDeclaredHere() {
        if (this.isDeclaredHere()) {
            return this;
        }
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x10);
    }

    @Override
    public boolean isFunction() {
        return (this.flags & 4) == 4;
    }

    public boolean isInternal() {
        assert (this.name != null);
        return this.getName().charAt(0) == ':';
    }

    public boolean isThis() {
        return (this.flags & 0x20) != 0;
    }

    public IdentNode setIsThis() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x20);
    }

    public boolean isSuper() {
        return (this.flags & 0x40) != 0;
    }

    public IdentNode setIsSuper() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x40);
    }

    public boolean isDirectSuper() {
        return (this.flags & 0x80) != 0;
    }

    public IdentNode setIsDirectSuper() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x40 | 0x80);
    }

    public boolean isRestParameter() {
        return (this.flags & 0x100) != 0;
    }

    public IdentNode setIsRestParameter() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x100);
    }

    public boolean isCatchParameter() {
        return (this.flags & 0x200) != 0;
    }

    public IdentNode setIsCatchParameter() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x200);
    }

    public boolean isNewTarget() {
        return (this.flags & 8) != 0;
    }

    public IdentNode setIsNewTarget() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 8);
    }

    public boolean isImportMeta() {
        return (this.flags & 0x400) != 0;
    }

    public IdentNode setIsImportMeta() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x400);
    }

    public boolean isMetaProperty() {
        return this.isNewTarget() || this.isImportMeta();
    }

    public IdentNode setIsArguments() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x800);
    }

    public boolean isArguments() {
        return (this.flags & 0x800) != 0;
    }

    public IdentNode setIsApplyArguments() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x1000);
    }

    public boolean isApplyArguments() {
        return (this.flags & 0x1000) != 0;
    }

    public IdentNode setIsPrivate() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x2000);
    }

    public boolean isPrivate() {
        return (this.flags & 0x2000) != 0;
    }

    public IdentNode setIsPrivateInCheck() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x4000);
    }

    public boolean isPrivateInCheck() {
        return (this.flags & 0x4000) != 0;
    }

    public IdentNode setIsIgnoredParameter() {
        return new IdentNode(this, this.name, this.nameTS, this.flags | 0x8000);
    }

    public boolean isIgnoredParameter() {
        return (this.flags & 0x8000) != 0;
    }
}

