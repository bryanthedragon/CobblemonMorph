package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import java.util.Set;

@ImportStatic(JSGlobal.class)
public abstract class DeclareGlobalNode extends JavaScriptBaseNode {
   protected final TruffleString varName;
   @Node.Child
   private HasPropertyCacheNode hasLexicalBindingNode;
   protected final BranchProfile errorProfile = BranchProfile.create();

   protected DeclareGlobalNode(TruffleString varName) {
      this.varName = varName;
   }

   public abstract void executeVoid(VirtualFrame frame, JSContext context, JSRealm realm);

   public void verify(JSContext context, JSRealm realm) {
      if (this.hasLexicalBindingNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.hasLexicalBindingNode = this.insert(HasPropertyCacheNode.create(this.varName, context, true));
      }

      if (this.hasLexicalBindingNode.hasProperty(realm.getGlobalScope())) {
         this.errorProfile.enter();
         throw Errors.createSyntaxErrorVariableAlreadyDeclared(this.varName, this);
      }
   }

   public boolean isLexicallyDeclared() {
      return false;
   }

   public boolean isGlobalFunctionDeclaration() {
      return false;
   }

   protected abstract DeclareGlobalNode copyUninitialized(Set<Class<? extends Tag>> materializedTags);
}
