
package com.oracle.truffle.js.nodes.binary;

import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSEqualNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.binary.JSTypeofIdenticalNodeGen;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@ImportStatic(value={Type.class, JSConfig.class})
public abstract class JSTypeofIdenticalNode
extends JSUnaryNode {
    protected final Type type;

    protected JSTypeofIdenticalNode(JavaScriptNode childNode, Type type) {
        super(childNode);
        this.type = type;
    }

    public static JSTypeofIdenticalNode create(JavaScriptNode childNode, JSConstantNode.JSConstantStringNode constStringNode) {
        return JSTypeofIdenticalNode.create(childNode, (TruffleString)constStringNode.execute(null));
    }

    public static JSTypeofIdenticalNode create(JavaScriptNode childNode, TruffleString string) {
        return JSTypeofIdenticalNodeGen.create(childNode, JSTypeofIdenticalNode.typeStringToEnum(string));
    }

    private static Type typeStringToEnum(TruffleString string) {
        if (Strings.equals(JSNumber.TYPE_NAME, string)) {
            return Type.Number;
        }
        if (Strings.equals(JSBigInt.TYPE_NAME, string)) {
            return Type.BigInt;
        }
        if (Strings.equals(JSString.TYPE_NAME, string)) {
            return Type.String;
        }
        if (Strings.equals(JSBoolean.TYPE_NAME, string)) {
            return Type.Boolean;
        }
        if (Strings.equals(JSOrdinary.TYPE_NAME, string)) {
            return Type.Object;
        }
        if (Strings.equals(Undefined.TYPE_NAME, string)) {
            return Type.Undefined;
        }
        if (Strings.equals(JSFunction.TYPE_NAME, string)) {
            return Type.Function;
        }
        if (Strings.equals(JSSymbol.TYPE_NAME, string)) {
            return Type.Symbol;
        }
        return Type.False;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.BinaryOperationTag.class || tag == JSTags.UnaryOperationTag.class || tag == JSTags.LiteralTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.BinaryOperationTag.class) || materializedTags.contains(JSTags.UnaryOperationTag.class) || materializedTags.contains(JSTags.LiteralTag.class)) {
            Object[] info = this.parseMaterializationInfo();
            if (info == null) {
                info = new Object[]{Strings.fromJavaString(this.type.name().toLowerCase()), true, true};
            }
            JavaScriptNode lhs = JSConstantNode.create(info[0]);
            JavaScriptNode rhs = TypeOfNode.create(JSTypeofIdenticalNode.cloneUninitialized(this.getOperand(), materializedTags));
            if (((Boolean)info[2]).booleanValue()) {
                JSConstantNode tmp = lhs;
                lhs = rhs;
                rhs = tmp;
            }
            JavaScriptNode materialized = (Boolean)info[1] != false ? JSIdenticalNode.createUnoptimized(lhs, rhs) : JSEqualNode.createUnoptimized(lhs, rhs);
            JSTypeofIdenticalNode.transferSourceSectionAddExpressionTag(this, lhs);
            JSTypeofIdenticalNode.transferSourceSectionAddExpressionTag(this, rhs);
            JSTypeofIdenticalNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    private JavaScriptLanguage getLanguageSafe() {
        JavaScriptLanguage language = null;
        try {
            language = this.getRootNode().getLanguage(JavaScriptLanguage.class);
            if (language == null) {
                language = this.getLanguage();
                return language;
            }
        }
        finally {
            return language;
        }
    }

    private Object[] parseMaterializationInfo() {
        boolean identity;
        TruffleString literal;
        boolean typeofAsLeftOperand;
        block10: {
            JavaScriptLanguage language = this.getLanguageSafe();
            if (language == null) {
                return null;
            }
            JSContext context = language.getJSContext();
            try {
                Expression expression = context.getEvaluator().parseExpression(context, this.getSourceSection().getCharacters().toString());
                if (expression instanceof BinaryNode) {
                    BinaryNode binaryNode = (BinaryNode)expression;
                    Expression lhs = binaryNode.getLhs();
                    Expression rhs = binaryNode.getRhs();
                    if (JSTypeofIdenticalNode.isTypeOf(lhs) && rhs instanceof LiteralNode) {
                        typeofAsLeftOperand = true;
                        literal = Strings.fromJavaString(((LiteralNode)rhs).getString());
                    } else if (JSTypeofIdenticalNode.isTypeOf(rhs) && lhs instanceof LiteralNode) {
                        typeofAsLeftOperand = false;
                        literal = Strings.fromJavaString(((LiteralNode)lhs).getString());
                    } else {
                        return null;
                    }
                    TokenType tokenType = binaryNode.tokenType();
                    if (tokenType == TokenType.EQ) {
                        identity = false;
                        break block10;
                    }
                    if (tokenType == TokenType.EQ_STRICT) {
                        identity = true;
                        break block10;
                    }
                    return null;
                }
                return null;
            }
            catch (ParserException ex) {
                return null;
            }
        }
        return new Object[]{literal, identity, typeofAsLeftOperand};
    }

    private static boolean isTypeOf(Expression expression) {
        return expression instanceof UnaryNode && ((UnaryNode)expression).tokenType() == TokenType.TYPEOF;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        return this.executeBoolean(frame);
    }

    @Override
    public abstract boolean executeBoolean(VirtualFrame var1);

    @Specialization
    protected final boolean doBoolean(boolean value2) {
        return this.type == Type.Boolean;
    }

    @Specialization
    protected final boolean doNumber(int value2) {
        return this.type == Type.Number;
    }

    @Specialization
    protected final boolean doNumber(SafeInteger value2) {
        return this.type == Type.Number;
    }

    @Specialization
    protected final boolean doNumber(long value2) {
        return this.type == Type.Number;
    }

    @Specialization
    protected final boolean doNumber(double value2) {
        return this.type == Type.Number;
    }

    @Specialization
    protected final boolean doSymbol(Symbol value2) {
        return this.type == Type.Symbol;
    }

    @Specialization
    protected final boolean doBigInt(BigInt value2) {
        return this.type == Type.BigInt;
    }

    @Specialization
    protected final boolean doString(TruffleString value2) {
        return this.type == Type.String;
    }

    @Specialization(guards={"type == Object || type == Function", "isJSFunction(value)"})
    protected final boolean doTypeObjectOrFunctionJSFunction(Object value2) {
        assert (this.type == Type.Object || this.type == Type.Function);
        return this.type == Type.Function;
    }

    @Specialization(guards={"type == Object || type == Function"})
    protected final boolean doTypeObjectOrFunctionJSProxy(JSProxyObject value2, @Cached IsCallableNode isCallableNode) {
        Object proxyTarget = JSProxy.getTargetNonProxy(value2);
        boolean callable = isCallableNode.executeBoolean(proxyTarget);
        if (this.type == Type.Object) {
            return !callable;
        }
        assert (this.type == Type.Function);
        return callable;
    }

    @Specialization(guards={"type == Object || type == Function", "!isJSFunction(value)", "!isJSProxy(value)"})
    protected final boolean doTypeObjectOrFunctionOther(JSDynamicObject value2) {
        assert (!JSGuards.isJSFunction(value2) && !JSGuards.isJSProxy(value2));
        if (this.type == Type.Object) {
            return value2 != Undefined.instance;
        }
        assert (this.type == Type.Function);
        return false;
    }

    @Specialization(guards={"type != Object", "type != Function"})
    protected final boolean doTypePrimitive(JSDynamicObject value2) {
        if (this.type == Type.Undefined) {
            return value2 == Undefined.instance;
        }
        assert (this.type == Type.Number || this.type == Type.BigInt || this.type == Type.String || this.type == Type.Boolean || this.type == Type.Symbol || this.type == Type.False);
        return false;
    }

    @Specialization(guards={"isForeignObject(value)"}, limit="InteropLibraryLimit")
    protected final boolean doForeignObject(Object value2, @CachedLibrary(value="value") InteropLibrary interop) {
        if (this.type == Type.Undefined || this.type == Type.Symbol || this.type == Type.False) {
            return false;
        }
        if (this.type == Type.Boolean) {
            return interop.isBoolean(value2);
        }
        if (this.type == Type.String) {
            return interop.isString(value2);
        }
        if (this.type == Type.Number) {
            return interop.isNumber(value2);
        }
        if (this.type == Type.Function) {
            return interop.isExecutable(value2) || interop.isInstantiable(value2) || this.isHostSymbolInNashornCompatMode(value2);
        }
        if (this.type == Type.Object) {
            return !interop.isExecutable(value2) && !interop.isInstantiable(value2) && !interop.isBoolean(value2) && !interop.isString(value2) && !interop.isNumber(value2) && !this.isHostSymbolInNashornCompatMode(value2);
        }
        return false;
    }

    private boolean isHostSymbolInNashornCompatMode(Object value2) {
        TruffleLanguage.Env env;
        return this.getLanguage().getJSContext().isOptionNashornCompatibilityMode() && (env = this.getRealm().getEnv()).isHostSymbol(value2);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSTypeofIdenticalNodeGen.create(JSTypeofIdenticalNode.cloneUninitialized(this.getOperand(), materializedTags), this.type);
    }

    public static enum Type {
        Number,
        BigInt,
        String,
        Boolean,
        Object,
        Undefined,
        Function,
        Symbol,
        False;

    }
}

