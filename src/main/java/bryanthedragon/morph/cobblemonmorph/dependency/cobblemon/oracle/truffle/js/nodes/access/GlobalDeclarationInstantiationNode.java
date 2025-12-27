package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;
import java.util.Set;

public class GlobalDeclarationInstantiationNode extends StatementNode {
   private final JSContext context;
   @Node.Children
   private final DeclareGlobalNode[] globalDeclarations;
   public static final DeclareGlobalNode[] EMPTY_DECLARATION_ARRAY = new DeclareGlobalNode[0];

   protected GlobalDeclarationInstantiationNode(JSContext context, DeclareGlobalNode[] globalDeclarations) {
      this.context = context;
      this.globalDeclarations = globalDeclarations;
   }

   public static JavaScriptNode create(JSContext context, DeclareGlobalNode[] globalDeclarations) {
      return new GlobalDeclarationInstantiationNode(context, globalDeclarations);
   }

   public static JavaScriptNode create(JSContext context, List<DeclareGlobalNode> globalDeclarations) {
      return create(context, globalDeclarations.toArray(EMPTY_DECLARATION_ARRAY));
   }

   @Override
   public Object execute(VirtualFrame frame) {
      JSRealm realm = this.getRealm();
      this.verifyDeclarations(realm);
      this.instantiateDeclarations(frame, realm);
      return EMPTY;
   }

   @ExplodeLoop
   private void verifyDeclarations(JSRealm realm) {
      for (DeclareGlobalNode declaration : this.globalDeclarations) {
         declaration.verify(this.context, realm);
      }
   }

   @ExplodeLoop
   private void instantiateDeclarations(VirtualFrame frame, JSRealm realm) {
      for (DeclareGlobalNode declaration : this.globalDeclarations) {
         declaration.executeVoid(frame, this.context, realm);
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new GlobalDeclarationInstantiationNode(this.context, cloneUninitialized(this.globalDeclarations, materializedTags));
   }

   private static DeclareGlobalNode[] cloneUninitialized(DeclareGlobalNode[] members, Set<Class<? extends Tag>> materializedTags) {
      DeclareGlobalNode[] copy = (DeclareGlobalNode[])members.clone();

      for (int i = 0; i < copy.length; i++) {
         copy[i] = copy[i].copyUninitialized(materializedTags);
      }

      return copy;
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      assert EMPTY == Undefined.instance;

      return clazz == Undefined.class;
   }
}
