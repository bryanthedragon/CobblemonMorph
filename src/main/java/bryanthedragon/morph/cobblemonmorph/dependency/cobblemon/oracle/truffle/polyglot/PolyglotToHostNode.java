package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import java.lang.reflect.Type;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class PolyglotToHostNode extends Node {
   abstract Object execute(PolyglotLanguageContext languageContext, Object value, Class<?> targetType, Type genericType);

   @Specialization
   static Object doDefault(
      PolyglotLanguageContext languageContext,
      Object value,
      Class<?> targetType,
      Type genericType,
      @Cached("languageContext.context.engine.host") AbstractPolyglotImpl.AbstractHostLanguageService host,
      @Cached("createToHostNode(host)") Node toHostNode
   ) {
      return host.toHostType(toHostNode, languageContext.context.getHostContextImpl(), value, targetType, genericType);
   }

   static Node createToHostNode(AbstractPolyglotImpl.AbstractHostLanguageService host) {
      return (Node)host.createToHostTypeNode();
   }
}
