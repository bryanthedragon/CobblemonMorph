
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.SymbolFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Map;

public final class SymbolFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<SymbolFunction> {
    public static final JSBuiltinsContainer BUILTINS = new SymbolFunctionBuiltins();

    protected SymbolFunctionBuiltins() {
        super(JSSymbol.CLASS_NAME, SymbolFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SymbolFunction builtinEnum) {
        switch (builtinEnum) {
            case for_: {
                return SymbolFunctionBuiltinsFactory.SymbolForNodeGen.create(context, builtin, SymbolFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case keyFor: {
                return SymbolFunctionBuiltinsFactory.SymbolKeyForNodeGen.create(context, builtin, SymbolFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class SymbolKeyForNode
    extends JSBuiltinNode {
        public SymbolKeyForNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isSymbol(symbol)"})
        protected Object symbolKeyFor(Symbol symbol) {
            return SymbolKeyForNode.getKeyFor(this.getContext().getSymbolRegistry(), symbol);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object getKeyFor(Map<TruffleString, Symbol> symbolRegistry, Symbol symbol) {
            for (Map.Entry<TruffleString, Symbol> entry : symbolRegistry.entrySet()) {
                if (!symbol.equals(entry.getValue())) continue;
                return entry.getKey();
            }
            return Undefined.instance;
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"!isSymbol(argument)"})
        protected static Symbol valueOf(Object argument) {
            throw Errors.createTypeErrorFormat("Not a symbol: %s", JSRuntime.safeToString(argument));
        }
    }

    public static abstract class SymbolForNode
    extends JSBuiltinNode {
        @Node.Child
        private JSToStringNode toStringNode = JSToStringNode.create();

        public SymbolForNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Symbol symbolFor(Object key) {
            TruffleString stringKey = this.toStringNode.executeString(key);
            return SymbolForNode.getOrCreateSymbol(this.getContext().getSymbolRegistry(), stringKey);
        }

        @CompilerDirectives.TruffleBoundary
        private static Symbol getOrCreateSymbol(Map<TruffleString, Symbol> symbolRegistry, TruffleString stringKey) {
            Symbol symbol = symbolRegistry.get(stringKey);
            if (symbol == null) {
                symbol = Symbol.create(stringKey);
                symbolRegistry.put(stringKey, symbol);
            }
            return symbol;
        }
    }

    public static enum SymbolFunction implements BuiltinEnum<SymbolFunction>
    {
        for_(1),
        keyFor(1);

        private final int length;

        private SymbolFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

