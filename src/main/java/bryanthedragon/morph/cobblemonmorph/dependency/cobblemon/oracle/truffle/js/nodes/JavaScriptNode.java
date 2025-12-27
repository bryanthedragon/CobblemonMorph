package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.interop.ScopeVariables;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import java.util.Set;

@ExportLibrary(NodeLibrary.class)
@GenerateWrapper
public abstract class JavaScriptNode extends JavaScriptBaseNode implements InstrumentableNode {
   private Object source;
   private int charIndex;
   private int charLength;
   private static final int STATEMENT_TAG_BIT = Integer.MIN_VALUE;
   private static final int CALL_TAG_BIT = 1073741824;
   private static final int CHAR_LENGTH_MASK = 1073741823;
   private static final int ROOT_BODY_TAG_BIT = Integer.MIN_VALUE;
   private static final int EXPRESSION_TAG_BIT = 1073741824;
   private static final int CHAR_INDEX_MASK = 1073741823;
   protected static final String INTERMEDIATE_VALUE = "(intermediate value)";

   protected JavaScriptNode() {
   }

   protected JavaScriptNode(SourceSection sourceSection) {
      this.setSourceSection(sourceSection);
   }

   @Override
   public boolean isInstrumentable() {
      return this.hasSourceSection();
   }

   @Override
   public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
      return new JavaScriptNodeWrapper(this, probe);
   }

   public abstract Object execute(VirtualFrame frame);

   public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      Object o = this.execute(frame);
      if (o instanceof Integer) {
         return (Integer)o;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(o);
      }
   }

   public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      Object o = this.execute(frame);
      if (o instanceof Double) {
         return (Double)o;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(o);
      }
   }

   public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
      Object o = this.execute(frame);
      if (o instanceof Boolean) {
         return (Boolean)o;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(o);
      }
   }

   public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
      return JSTypesGen.expectLong(this.execute(frame));
   }

   public SafeInteger executeSafeInteger(VirtualFrame frame) throws UnexpectedResultException {
      return JSTypesGen.expectSafeInteger(this.execute(frame));
   }

   public void executeVoid(VirtualFrame frame) {
      this.execute(frame);
   }

   public JavaScriptNode copy() {
      CompilerAsserts.neverPartOfCompilation("cannot call JavaScriptNode.copy() in compiled code");
      return (JavaScriptNode)super.copy();
   }

   @Override
   public String toString() {
      CompilerAsserts.neverPartOfCompilation("cannot call JavaScriptNode.toString() in compiled code");
      String simpleName = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1);
      StringBuilder sb = new StringBuilder(simpleName);
      sb.append('@').append(Integer.toHexString(System.identityHashCode(this)));
      sb.append(" ").append(JSNodeUtil.formatSourceSection(this));
      String tagsString = JSNodeUtil.formatTags(this);
      if (!tagsString.isEmpty()) {
         sb.append("[").append(tagsString).append("]");
      }

      RootNode rootNode = this.getRootNode();
      if (rootNode != null) {
         sb.append(" '").append(JSNodeUtil.resolveName(rootNode)).append("'");
      }

      String expressionString = this.expressionToString();
      if (expressionString != null) {
         sb.append(" (").append(expressionString).append(")");
      }

      return sb.toString();
   }

   @Override
   protected void onReplace(Node newNode, CharSequence reason) {
      super.onReplace(newNode, reason);
      transferSourceSectionAndTags(this, (JavaScriptNode)newNode);
   }

   public static void transferSourceSectionAndTags(JavaScriptNode fromNode, JavaScriptNode toNode) {
      if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
         toNode.source = fromNode.source;
         toNode.charIndex = fromNode.charIndex | toNode.charIndex & -1073741824;
         toNode.charLength = fromNode.charLength | toNode.charLength & -1073741824;
      }
   }

   public static void transferSourceSectionAddExpressionTag(JavaScriptNode fromNode, JavaScriptNode toNode) {
      if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
         toNode.source = fromNode.source;
         toNode.charIndex = fromNode.charIndex & 1073741823;
         toNode.charLength = fromNode.charLength & 1073741823;
         toNode.addExpressionTag();
      }
   }

   public static void transferSourceSection(JavaScriptNode fromNode, JavaScriptNode toNode) {
      if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
         toNode.source = fromNode.source;
         toNode.charIndex = fromNode.charIndex & 1073741823;
         toNode.charLength = fromNode.charLength & 1073741823;
      }
   }

   public final boolean hasSourceSection() {
      return this.source != null;
   }

   @Override
   public final SourceSection getSourceSection() {
      if (this.hasSourceSection()) {
         Object src = this.source;
         if (src instanceof SourceSection) {
            return (SourceSection)src;
         } else {
            SourceSection section = ((Source)src).createSection(this.charIndex & 1073741823, this.charLength & 1073741823);
            this.source = section;
            return section;
         }
      } else {
         return null;
      }
   }

   public final void setSourceSection(SourceSection section) {
      CompilerAsserts.neverPartOfCompilation();
      if (this.hasSourceSection()) {
         this.checkSameSourceSection(section);
      }

      this.source = section;
   }

   public final void setSourceSection(Source source, int charIndex, int charLength) {
      CompilerAsserts.neverPartOfCompilation();
      checkValidSourceSection(source, charIndex, charLength);
      if (this.hasSourceSection()) {
         this.checkSameSourceSection(source.createSection(charIndex, charLength));
      }

      assert charIndex <= 1073741823 && charLength <= 1073741823;

      this.charIndex = charIndex | this.charIndex & -1073741824;
      this.charLength = charLength | this.charLength & -1073741824;
      this.source = source;
   }

   private static void checkValidSourceSection(Source source, int charIndex, int charLength) {
      if (charIndex < 0) {
         throw new IllegalArgumentException("charIndex < 0");
      } else if (charLength < 0) {
         throw new IllegalArgumentException("length < 0");
      } else {
         assert charIndex + charLength <= source.getCharacters().length();
      }
   }

   private void checkSameSourceSection(SourceSection newSection) {
      SourceSection sourceSection = this.getSourceSection();
      if (sourceSection != null && !sourceSection.equals(newSection)) {
         throw new IllegalStateException(String.format("Source section is already assigned. Old: %s, new: %s", sourceSection, newSection));
      }
   }

   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return false;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if (tag == StandardTags.StatementTag.class) {
         return (this.charLength & -2147483648) != 0;
      } else if (tag == StandardTags.CallTag.class) {
         return (this.charLength & 1073741824) != 0;
      } else if (tag == StandardTags.RootBodyTag.class) {
         return (this.charIndex & -2147483648) != 0;
      } else {
         return tag == StandardTags.ExpressionTag.class ? (this.charIndex & 1073741824) != 0 : false;
      }
   }

   public final void addStatementTag() {
      this.charLength |= Integer.MIN_VALUE;
   }

   public final void addCallTag() {
      this.charLength |= 1073741824;
   }

   public final void addRootBodyTag() {
      this.charIndex |= Integer.MIN_VALUE;
   }

   public final void addExpressionTag() {
      this.charIndex |= 1073741824;
   }

   final boolean hasImportantTag() {
      return (this.charIndex & -2147483648) != 0
         || (this.charIndex & 1073741824) != 0
         || (this.charLength & -2147483648) != 0
         || (this.charLength & 1073741824) != 0;
   }

   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      if (this instanceof InstrumentableNode.WrapperNode) {
         InstrumentableNode.WrapperNode wrapperNode = (InstrumentableNode.WrapperNode)this;
         return cloneUninitialized((JavaScriptNode)wrapperNode.getDelegateNode(), materializedTags);
      } else {
         throw Errors.notImplemented(this.getClass().getSimpleName() + ".copyUninitialized()");
      }
   }

   public static <T extends JavaScriptNode> T cloneUninitialized(T node, Set<Class<? extends Tag>> materializedTags) {
      if (node == null) {
         return null;
      } else {
         T copy = node;
         if (materializedTags != null && node.isInstrumentable()) {
            copy = (T)node.materializeInstrumentableNodes(materializedTags);
         }

         if (node == copy) {
            copy = (T)node.copyUninitialized(materializedTags);

            assert copy.getClass() == node.getClass() || node instanceof JSBuiltinNode || node instanceof InstrumentableNode.WrapperNode : node.getClass()
               + " => "
               + copy.getClass();

            transferSourceSectionAndTags(node, copy);
         }

         return copy;
      }
   }

   public static <T extends JavaScriptNode> T[] cloneUninitialized(T[] nodeArray, Set<Class<? extends Tag>> materializedTags) {
      if (nodeArray == null) {
         return null;
      } else {
         T[] copy = (T[])nodeArray.clone();

         for (int i = 0; i < copy.length; i++) {
            copy[i] = cloneUninitialized(copy[i], materializedTags);
         }

         return copy;
      }
   }

   public void removeSourceSection() {
      this.source = null;
   }

   public String expressionToString() {
      return null;
   }

   @ExportMessage
   boolean accepts(@Cached(value = "this", adopt = false) JavaScriptNode cachedNode) {
      return this == cachedNode;
   }

   @ExportMessage
   final boolean hasScope(Frame frame) {
      return this.getParent() != null;
   }

   @ExportMessage
   final Object getScope(
      Frame frame,
      boolean nodeEnter,
      @Cached(value = "findBlockScopeNode(this)", allowUncached = true, adopt = false) Node blockNode,
      @Cached(value = "findFrameScopeNode(blockNode)", allowUncached = true, adopt = false) Node frameBlockNode
   ) throws UnsupportedMessageException {
      if (!this.hasScope(frame)) {
         throw UnsupportedMessageException.create();
      } else {
         Frame functionFrame;
         Frame scopeFrame;
         if (frame != null) {
            RootNode rootNode = this.getRootNode();
            if (rootNode instanceof JavaScriptRootNode
               && ((JavaScriptRootNode)rootNode).isResumption()
               && frame.getFrameDescriptor() == rootNode.getFrameDescriptor()) {
               functionFrame = JSArguments.getResumeExecutionContext(frame.getArguments());
            } else if (rootNode.getFrameDescriptor() == JavaScriptRootNode.MODULE_DUMMY_FRAMEDESCRIPTOR) {
               functionFrame = ((JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0)).getEnvironment();
            } else {
               functionFrame = frame.materialize();
            }

            if (frameBlockNode instanceof BlockScopeNode.FrameBlockScopeNode) {
               Object maybeScopeFrame = ((BlockScopeNode.FrameBlockScopeNode)frameBlockNode).getBlockScope((VirtualFrame)functionFrame);
               if (maybeScopeFrame instanceof Frame) {
                  scopeFrame = (Frame)maybeScopeFrame;
               } else {
                  scopeFrame = functionFrame;
               }
            } else {
               scopeFrame = functionFrame;
            }
         } else {
            functionFrame = null;
            scopeFrame = null;
         }

         return ScopeVariables.create(scopeFrame, nodeEnter, blockNode, functionFrame);
      }
   }

   @ExportMessage
   final boolean hasReceiverMember(Frame frame) {
      return frame != null;
   }

   @ExportMessage
   final Object getReceiverMember(Frame frame) throws UnsupportedMessageException {
      if (frame == null) {
         throw UnsupportedMessageException.create();
      } else {
         return ScopeVariables.RECEIVER_MEMBER;
      }
   }

   @ExportMessage
   boolean hasRootInstance(Frame frame) {
      return frame != null;
   }

   @ExportMessage
   Object getRootInstance(Frame frame) throws UnsupportedMessageException {
      if (frame == null) {
         throw UnsupportedMessageException.create();
      } else {
         return JSFrameUtil.getFunctionObject(frame);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Node findBlockScopeNode(Node node) {
      if (node == null) {
         return null;
      } else {
         Node parent = node;

         for (Node n = node; n != null; n = n.getParent()) {
            if (n instanceof BlockScopeNode) {
               return n;
            }

            parent = n;
         }

         assert parent instanceof RootNode : "Node " + node + " is not adopted.";

         return parent;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static Node findFrameScopeNode(Node node) {
      if (node == null) {
         return null;
      } else {
         Node parent = node;

         for (Node n = node; n != null; n = n.getParent()) {
            if (n instanceof BlockScopeNode.FrameBlockScopeNode) {
               return n;
            }

            parent = n;
         }

         assert parent instanceof RootNode : "Node " + node + " is not adopted.";

         return parent;
      }
   }
}
