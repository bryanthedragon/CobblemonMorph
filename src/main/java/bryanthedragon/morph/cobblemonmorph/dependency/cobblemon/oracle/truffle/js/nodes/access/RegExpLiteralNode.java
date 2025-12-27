package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.intl.CreateRegExpNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.RegexCompilerInterface;
import java.util.Set;

public class RegExpLiteralNode extends JavaScriptNode {
   private final JSContext context;
   private final String pattern;
   private final String flags;
   @CompilerDirectives.CompilationFinal
   private Object compiledRegex;
   @Node.Child
   private CreateRegExpNode createRegExpNode;

   RegExpLiteralNode(JSContext context, String pattern, String flags) {
      this.context = context;
      this.pattern = pattern;
      this.flags = flags;
   }

   public static RegExpLiteralNode create(JSContext context, String pattern, String flags) {
      return new RegExpLiteralNode(context, pattern, flags);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      if (this.createRegExpNode == null || this.compiledRegex == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         JSRealm realm = this.getRealm();
         this.createRegExpNode = this.insert(CreateRegExpNode.create(this.context));
         this.compiledRegex = RegexCompilerInterface.compile(this.pattern, this.flags, this.context, realm, InteropLibrary.getUncached());
      }

      return this.createRegExpNode.createRegExp(this.compiledRegex);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.context, this.pattern, this.flags);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.LiteralTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.RegExpLiteral.name());
   }
}
