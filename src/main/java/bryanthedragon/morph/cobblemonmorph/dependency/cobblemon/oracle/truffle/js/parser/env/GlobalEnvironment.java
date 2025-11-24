
package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.parser.env.DerivedEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Map;
import java.util.StringJoiner;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class GlobalEnvironment
extends DerivedEnvironment {
    private static final UnmodifiableEconomicMap<TruffleString, DeclarationKind> PREDEFINED_IMMUTABLE_GLOBALS = GlobalEnvironment.initPredefinedImmutableGlobals();
    private final EconomicMap<TruffleString, DeclarationKind> declarations = EconomicMap.create(PREDEFINED_IMMUTABLE_GLOBALS);

    public GlobalEnvironment(Environment parent, NodeFactory factory, JSContext context) {
        super(parent, factory, context);
    }

    @Override
    public JSFrameSlot findBlockFrameSlot(Object name) {
        return null;
    }

    public void addLexicalDeclaration(TruffleString name, boolean isConst) {
        this.declarations.putIfAbsent(name, isConst ? DeclarationKind.Const : DeclarationKind.Let);
    }

    public boolean hasLexicalDeclaration(TruffleString name) {
        DeclarationKind decl = (DeclarationKind)((Object)this.declarations.get(name));
        return decl != null && decl.isLexical();
    }

    public boolean hasConstDeclaration(TruffleString name) {
        DeclarationKind decl = (DeclarationKind)((Object)this.declarations.get(name));
        return decl != null && decl.isConst();
    }

    public void addVarDeclaration(TruffleString name) {
        this.declarations.putIfAbsent(name, DeclarationKind.Var);
    }

    public boolean hasVarDeclaration(TruffleString name) {
        DeclarationKind decl = (DeclarationKind)((Object)this.declarations.get(name));
        return decl != null && !decl.isLexical();
    }

    public static boolean isGlobalObjectConstant(TruffleString name) {
        return PREDEFINED_IMMUTABLE_GLOBALS.containsKey(name);
    }

    private static UnmodifiableEconomicMap<TruffleString, DeclarationKind> initPredefinedImmutableGlobals() {
        EconomicMap<TruffleString, DeclarationKind> map = EconomicMap.create();
        map.put(Strings.UNDEFINED, DeclarationKind.Var);
        map.put(Strings.NAN, DeclarationKind.Var);
        map.put(Strings.INFINITY, DeclarationKind.Var);
        return map;
    }

    public boolean hasBeenDeclared(TruffleString name) {
        DeclarationKind decl = (DeclarationKind)((Object)this.declarations.get(name));
        if (decl != null) {
            return decl.isDeclared();
        }
        return false;
    }

    public void setHasBeenDeclared(TruffleString name, boolean declared) {
        DeclarationKind decl = (DeclarationKind)((Object)this.declarations.get(name));
        if (decl != null && decl.isLexical() && decl.isDeclared() != declared) {
            this.declarations.put(name, decl.withDeclared(declared));
        }
    }

    @Override
    protected String toStringImpl(Map<String, Integer> state) {
        return "Global" + new StringJoiner(", ", "{", "}").add(GlobalEnvironment.joinElements(this.declarations.getKeys())).toString();
    }

    private static final class DeclarationKind
    extends Enum<DeclarationKind> {
        public static final /* enum */ DeclarationKind Var = new DeclarationKind(false, false, true);
        public static final /* enum */ DeclarationKind Let = new DeclarationKind(true, false, false);
        public static final /* enum */ DeclarationKind LetDeclared = new DeclarationKind(true, false, true);
        public static final /* enum */ DeclarationKind Const = new DeclarationKind(true, true, false);
        public static final /* enum */ DeclarationKind ConstDeclared = new DeclarationKind(true, true, true);
        private final boolean isLexical;
        private final boolean isConst;
        private final boolean isDeclared;
        private static final /* synthetic */ DeclarationKind[] $VALUES;

        public static DeclarationKind[] values() {
            return (DeclarationKind[])$VALUES.clone();
        }

        public static DeclarationKind valueOf(String name) {
            return Enum.valueOf(DeclarationKind.class, name);
        }

        private DeclarationKind(boolean isLexical, boolean isConst, boolean isDeclared) {
            this.isLexical = isLexical;
            this.isConst = isConst;
            this.isDeclared = isDeclared;
        }

        public final boolean isLexical() {
            return this.isLexical;
        }

        public final boolean isConst() {
            return this.isConst;
        }

        public final boolean isDeclared() {
            return this.isDeclared;
        }

        public final DeclarationKind withDeclared(boolean declared) {
            assert (this.isLexical() || declared);
            if (!this.isLexical() || this.isDeclared() == declared) {
                return this;
            }
            if (declared) {
                return this.isConst() ? ConstDeclared : LetDeclared;
            }
            return this.isConst() ? Const : Let;
        }

        static {
            $VALUES = new DeclarationKind[]{Var, Let, LetDeclared, Const, ConstDeclared};
        }
    }
}

