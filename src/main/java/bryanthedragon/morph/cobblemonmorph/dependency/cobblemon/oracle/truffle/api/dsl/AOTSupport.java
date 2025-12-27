package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;

public final class AOTSupport {
   private AOTSupport() {
   }

   public static void prepareForAOT(RootNode root) {
      root.atomic(new Runnable() {
         @Override
         public void run() {
            final TruffleLanguage<?> language = DSLAccessor.nodeAccessor().getLanguage(root);
            NodeUtil.forEachChild(root, new NodeVisitor() {
               @Override
               public boolean visit(Node node) {
                  if (node instanceof GenerateAOT.Provider) {
                     GenerateAOT.Provider provider = (GenerateAOT.Provider)node;
                     if (node.isAdoptable() && node.getRootNode() == null) {
                        throw new AssertionError("Node is not yet adopted before prepare " + node);
                     }

                     provider.prepareForAOT(language, root);
                  }

                  return NodeUtil.forEachChild(node, this);
               }
            });
         }
      });
   }
}
