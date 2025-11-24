
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.lang.reflect.Type;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class PolyglotToHostNode
extends Node {
    PolyglotToHostNode() {
    }

    abstract Object execute(PolyglotLanguageContext var1, Object var2, Class<?> var3, Type var4);

    @Specialization
    static Object doDefault(PolyglotLanguageContext languageContext, Object value2, Class<?> targetType, Type genericType, @Cached(value="languageContext.context.engine.host") AbstractPolyglotImpl.AbstractHostLanguageService host, @Cached(value="createToHostNode(host)") Node toHostNode) {
        return host.toHostType(toHostNode, languageContext.context.getHostContextImpl(), value2, targetType, genericType);
    }

    static Node createToHostNode(AbstractPolyglotImpl.AbstractHostLanguageService host) {
        return (Node)host.createToHostTypeNode();
    }
}

