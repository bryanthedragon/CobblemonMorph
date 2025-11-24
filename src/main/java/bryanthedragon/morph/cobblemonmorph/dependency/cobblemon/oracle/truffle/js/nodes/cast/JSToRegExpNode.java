
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToRegExpNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.CreateRegExpNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;

public abstract class JSToRegExpNode
extends JavaScriptBaseNode {
    protected final JSContext context;
    @Node.Child
    private CreateRegExpNode createRegExpNode;

    protected JSToRegExpNode(JSContext context) {
        this.context = context;
    }

    public abstract JSRegExpObject execute(Object var1);

    public static JSToRegExpNode create(JSContext context) {
        return JSToRegExpNodeGen.create(context);
    }

    @Specialization
    protected JSRegExpObject returnRegExp(JSRegExpObject regExp) {
        return regExp;
    }

    @Specialization(guards={"!isJSRegExp(patternObj)"})
    protected JSRegExpObject createRegExp(Object patternObj, @Cached(value="createUndefinedToEmpty()") JSToStringNode toStringNode, @Cached(value="create(context)") CompileRegexNode compileRegexNode) {
        TruffleString pattern = toStringNode.executeString(patternObj);
        Object regex = compileRegexNode.compile(pattern);
        return this.getCreateRegExpNode().createRegExp(regex);
    }

    private CreateRegExpNode getCreateRegExpNode() {
        if (this.createRegExpNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.createRegExpNode = this.insert(CreateRegExpNode.create(this.context));
        }
        return this.createRegExpNode;
    }
}

