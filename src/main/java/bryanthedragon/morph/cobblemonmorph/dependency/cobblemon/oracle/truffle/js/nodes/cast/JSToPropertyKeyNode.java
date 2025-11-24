
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.Set;

public abstract class JSToPropertyKeyNode
extends JavaScriptBaseNode {
    public static JSToPropertyKeyNode create() {
        return JSToPropertyKeyNodeGen.create();
    }

    public abstract Object execute(Object var1);

    @Specialization
    protected TruffleString doTString(TruffleString value2) {
        return value2;
    }

    @Specialization
    protected Symbol doSymbol(Symbol value2) {
        return value2;
    }

    @Specialization(guards={"!isSymbol(value)"})
    protected Object doOther(Object value2, @Cached(value="createHintString()") JSToPrimitiveNode toPrimitiveNode, @Cached(value="create()") JSToStringNode toStringNode, @Cached(value="createBinaryProfile()") ConditionProfile isSymbol) {
        Object key = toPrimitiveNode.execute(value2);
        if (isSymbol.profile(key instanceof Symbol)) {
            return key;
        }
        return toStringNode.executeString(key);
    }

    public static abstract class JSToPropertyKeyWrapperNode
    extends JSUnaryNode {
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode;

        protected JSToPropertyKeyWrapperNode(JavaScriptNode operand) {
            super(operand);
        }

        public static JavaScriptNode create(JavaScriptNode key) {
            if (key.isResultAlwaysOfType(TruffleString.class) || key.isResultAlwaysOfType(Symbol.class)) {
                return key;
            }
            return JSToPropertyKeyNodeGen.JSToPropertyKeyWrapperNodeGen.create(key);
        }

        @Specialization
        protected Object doDefault(Object value2) {
            if (this.toPropertyKeyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
            }
            return this.toPropertyKeyNode.execute(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return JSToPropertyKeyNodeGen.JSToPropertyKeyWrapperNodeGen.create(JSToPropertyKeyWrapperNode.cloneUninitialized(this.getOperand(), materializedTags));
        }
    }
}

