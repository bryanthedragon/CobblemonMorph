package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.access.JSTargetableWrapperNode;
import com.oracle.truffle.js.nodes.access.VarWrapperNode;
import com.oracle.truffle.js.nodes.control.GeneratorWrapperNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.instrumentation.JSInputGeneratingNodeWrapper;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.runtime.util.DebugCounter;

public final class JSNodeUtil {
   static final DebugCounter NODE_CREATE_COUNT = DebugCounter.create("NodeCreateCount");
   static final DebugCounter NODE_REPLACE_COUNT = DebugCounter.create("NodeReplaceCount");
   private static final SlowPathException SLOW_PATH_EXCEPTION = new SlowPathException();

   private JSNodeUtil() {
   }

   public static SlowPathException slowPathException() {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      return SLOW_PATH_EXCEPTION;
   }

   static String formatTags(JavaScriptNode node) {
      CompilerAsserts.neverPartOfCompilation();
      StringBuilder sb = new StringBuilder(4);
      if (node.hasTag(StandardTags.StatementTag.class)) {
         sb.append('S');
      }

      if (node.hasTag(StandardTags.CallTag.class)) {
         sb.append('C');
      }

      if (node.hasTag(StandardTags.RootTag.class)) {
         sb.append('R');
      }

      if (node.hasTag(StandardTags.RootBodyTag.class)) {
         sb.append('B');
      }

      if (node.hasTag(StandardTags.ExpressionTag.class)) {
         sb.append('E');
      }

      return sb.toString();
   }

   public static boolean hasImportantTag(JavaScriptNode node) {
      return node.hasImportantTag();
   }

   public static String resolveName(RootNode root) {
      return root instanceof FunctionRootNode ? ((FunctionRootNode)root).getName() : "unknown";
   }

   public static String formatSourceSection(Node node) {
      CompilerAsserts.neverPartOfCompilation();
      if (node == null) {
         return "<unknown>";
      } else {
         SourceSection section = node.getSourceSection();
         boolean estimated = false;
         if (section == null) {
            section = node.getEncapsulatingSourceSection();
            estimated = true;
         }

         if (section != null && section.isAvailable()) {
            String sourceName = section.getSource().getName();
            int startLine = section.getStartLine();
            return String.format("%s:%d%s", sourceName, startLine, estimated ? "~" : "");
         } else {
            return "<unknown source>";
         }
      }
   }

   public static boolean hasExactlyOneRootBodyTag(JavaScriptNode body) {
      CompilerAsserts.neverPartOfCompilation();
      return NodeUtil.countNodes(
            body,
            node -> !(node instanceof GeneratorWrapperNode) && node instanceof JavaScriptNode && ((JavaScriptNode)node).hasTag(StandardTags.RootBodyTag.class)
         )
         == 1;
   }

   public static boolean isWrapperNode(JavaScriptNode node) {
      return node instanceof InstrumentableNode.WrapperNode
         || node instanceof VarWrapperNode
         || node instanceof JSInputGeneratingNodeWrapper
         || node instanceof JSTaggedExecutionNode
         || node instanceof JSTargetableWrapperNode;
   }

   public static JavaScriptNode getWrappedNode(JavaScriptNode node) {
      JavaScriptNode unwrapped = node;
      if (node instanceof InstrumentableNode.WrapperNode) {
         InstrumentableNode.WrapperNode wrapper = (InstrumentableNode.WrapperNode)node;
         unwrapped = (JavaScriptNode)wrapper.getDelegateNode();
      }

      if (unwrapped instanceof JSInputGeneratingNodeWrapper) {
         unwrapped = ((JSInputGeneratingNodeWrapper)unwrapped).getDelegateNode();
      }

      if (unwrapped instanceof JSTaggedExecutionNode) {
         unwrapped = ((JSTaggedExecutionNode)unwrapped).getDelegateNode();
      }

      if (unwrapped instanceof VarWrapperNode) {
         unwrapped = ((VarWrapperNode)unwrapped).getDelegateNode();
      }

      if (unwrapped instanceof JSTargetableWrapperNode) {
         unwrapped = ((JSTargetableWrapperNode)unwrapped).getDelegate();
      }

      if (unwrapped instanceof InstrumentableNode.WrapperNode) {
         InstrumentableNode.WrapperNode wrapper = (InstrumentableNode.WrapperNode)unwrapped;
         unwrapped = (JavaScriptNode)wrapper.getDelegateNode();
      }

      assert !isWrapperNode(unwrapped);

      return unwrapped;
   }

   public static boolean isTaggedNode(Node node) {
      return node instanceof JSTaggedExecutionNode
         || node instanceof InstrumentableNode.WrapperNode && ((InstrumentableNode.WrapperNode)node).getDelegateNode() instanceof JSTaggedExecutionNode;
   }

   public static boolean isInputGeneratingNode(Node node) {
      return node instanceof JSInputGeneratingNodeWrapper
         || node instanceof InstrumentableNode.WrapperNode && ((InstrumentableNode.WrapperNode)node).getDelegateNode() instanceof JSInputGeneratingNodeWrapper;
   }
}
