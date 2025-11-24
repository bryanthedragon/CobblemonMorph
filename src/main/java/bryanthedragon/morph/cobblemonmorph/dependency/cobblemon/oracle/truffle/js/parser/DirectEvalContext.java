
package com.oracle.truffle.js.parser;

import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.js.parser.env.Environment;

public final class DirectEvalContext {
    final Scope scope;
    final Environment env;
    final ClassNode enclosingClass;

    DirectEvalContext(Scope scope, Environment env, ClassNode enclosingClass) {
        this.scope = scope;
        this.env = env;
        this.enclosingClass = enclosingClass;
    }
}

