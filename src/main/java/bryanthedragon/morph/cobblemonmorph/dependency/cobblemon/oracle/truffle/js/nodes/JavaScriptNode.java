
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
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
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNodeWrapper;
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

@ExportLibrary(value=NodeLibrary.class)
@GenerateWrapper
public abstract class JavaScriptNode
extends JavaScriptBaseNode
implements InstrumentableNode {
    private Object source;
    private int charIndex;
    private int charLength;
    private static final int STATEMENT_TAG_BIT = Integer.MIN_VALUE;
    private static final int CALL_TAG_BIT = 0x40000000;
    private static final int CHAR_LENGTH_MASK = 0x3FFFFFFF;
    private static final int ROOT_BODY_TAG_BIT = Integer.MIN_VALUE;
    private static final int EXPRESSION_TAG_BIT = 0x40000000;
    private static final int CHAR_INDEX_MASK = 0x3FFFFFFF;
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

    public abstract Object execute(VirtualFrame var1);

    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        Object o = this.execute(frame);
        if (o instanceof Integer) {
            return (Integer)o;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(o);
    }

    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        Object o = this.execute(frame);
        if (o instanceof Double) {
            return (Double)o;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(o);
    }

    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        Object o = this.execute(frame);
        if (o instanceof Boolean) {
            return (Boolean)o;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(o);
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

    @Override
    public JavaScriptNode copy() {
        CompilerAsserts.neverPartOfCompilation("cannot call JavaScriptNode.copy() in compiled code");
        return (JavaScriptNode)super.copy();
    }

    @Override
    public String toString() {
        String expressionString;
        RootNode rootNode;
        CompilerAsserts.neverPartOfCompilation("cannot call JavaScriptNode.toString() in compiled code");
        String simpleName = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(46) + 1);
        StringBuilder sb = new StringBuilder(simpleName);
        sb.append('@').append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ").append(JSNodeUtil.formatSourceSection(this));
        String tagsString = JSNodeUtil.formatTags(this);
        if (!tagsString.isEmpty()) {
            sb.append("[").append(tagsString).append("]");
        }
        if ((rootNode = this.getRootNode()) != null) {
            sb.append(" '").append(JSNodeUtil.resolveName(rootNode)).append("'");
        }
        if ((expressionString = this.expressionToString()) != null) {
            sb.append(" (").append(expressionString).append(")");
        }
        return sb.toString();
    }

    @Override
    protected void onReplace(Node newNode, CharSequence reason) {
        super.onReplace(newNode, reason);
        JavaScriptNode.transferSourceSectionAndTags(this, (JavaScriptNode)newNode);
    }

    public static void transferSourceSectionAndTags(JavaScriptNode fromNode, JavaScriptNode toNode) {
        if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
            toNode.source = fromNode.source;
            toNode.charIndex = fromNode.charIndex | toNode.charIndex & 0xC0000000;
            toNode.charLength = fromNode.charLength | toNode.charLength & 0xC0000000;
        }
    }

    public static void transferSourceSectionAddExpressionTag(JavaScriptNode fromNode, JavaScriptNode toNode) {
        if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
            toNode.source = fromNode.source;
            toNode.charIndex = fromNode.charIndex & 0x3FFFFFFF;
            toNode.charLength = fromNode.charLength & 0x3FFFFFFF;
            toNode.addExpressionTag();
        }
    }

    public static void transferSourceSection(JavaScriptNode fromNode, JavaScriptNode toNode) {
        if (!toNode.hasSourceSection() && fromNode.hasSourceSection()) {
            toNode.source = fromNode.source;
            toNode.charIndex = fromNode.charIndex & 0x3FFFFFFF;
            toNode.charLength = fromNode.charLength & 0x3FFFFFFF;
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
            }
            SourceSection section = ((Source)src).createSection(this.charIndex & 0x3FFFFFFF, this.charLength & 0x3FFFFFFF);
            this.source = section;
            return section;
        }
        return null;
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
        JavaScriptNode.checkValidSourceSection(source, charIndex, charLength);
        if (this.hasSourceSection()) {
            this.checkSameSourceSection(source.createSection(charIndex, charLength));
        }
        assert (charIndex <= 0x3FFFFFFF && charLength <= 0x3FFFFFFF);
        this.charIndex = charIndex | this.charIndex & 0xC0000000;
        this.charLength = charLength | this.charLength & 0xC0000000;
        this.source = source;
    }

    private static void checkValidSourceSection(Source source, int charIndex, int charLength) {
        if (charIndex < 0) {
            throw new IllegalArgumentException("charIndex < 0");
        }
        if (charLength < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        assert (charIndex + charLength <= source.getCharacters().length());
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
            return (this.charLength & Integer.MIN_VALUE) != 0;
        }
        if (tag == StandardTags.CallTag.class) {
            return (this.charLength & 0x40000000) != 0;
        }
        if (tag == StandardTags.RootBodyTag.class) {
            return (this.charIndex & Integer.MIN_VALUE) != 0;
        }
        if (tag == StandardTags.ExpressionTag.class) {
            return (this.charIndex & 0x40000000) != 0;
        }
        return false;
    }

    public final void addStatementTag() {
        this.charLength |= Integer.MIN_VALUE;
    }

    public final void addCallTag() {
        this.charLength |= 0x40000000;
    }

    public final void addRootBodyTag() {
        this.charIndex |= Integer.MIN_VALUE;
    }

    public final void addExpressionTag() {
        this.charIndex |= 0x40000000;
    }

    final boolean hasImportantTag() {
        return (this.charIndex & Integer.MIN_VALUE) != 0 || (this.charIndex & 0x40000000) != 0 || (this.charLength & Integer.MIN_VALUE) != 0 || (this.charLength & 0x40000000) != 0;
    }

    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        if (this instanceof InstrumentableNode.WrapperNode) {
            InstrumentableNode.WrapperNode wrapperNode = (InstrumentableNode.WrapperNode)((Object)this);
            return JavaScriptNode.cloneUninitialized((JavaScriptNode)wrapperNode.getDelegateNode(), materializedTags);
        }
        throw Errors.notImplemented(this.getClass().getSimpleName() + ".copyUninitialized()");
    }

    public static <T extends JavaScriptNode> T cloneUninitialized(T node, Set<Class<? extends Tag>> materializedTags) {
        if (node == null) {
            return null;
        }
        Object copy = node;
        if (materializedTags != null && node.isInstrumentable()) {
            copy = (JavaScriptNode)node.materializeInstrumentableNodes(materializedTags);
        }
        if (node == copy) {
            copy = node.copyUninitialized(materializedTags);
            assert (copy.getClass() == node.getClass() || node instanceof JSBuiltinNode || node instanceof InstrumentableNode.WrapperNode) : node.getClass() + " => " + copy.getClass();
            JavaScriptNode.transferSourceSectionAndTags(node, (JavaScriptNode)copy);
        }
        return (T)copy;
    }

    public static <T extends JavaScriptNode> T[] cloneUninitialized(T[] nodeArray, Set<Class<? extends Tag>> materializedTags) {
        if (nodeArray == null) {
            return null;
        }
        JavaScriptNode[] copy = (JavaScriptNode[])nodeArray.clone();
        for (int i = 0; i < copy.length; ++i) {
            copy[i] = JavaScriptNode.cloneUninitialized(copy[i], materializedTags);
        }
        return copy;
    }

    public void removeSourceSection() {
        this.source = null;
    }

    public String expressionToString() {
        return null;
    }

    @ExportMessage
    boolean accepts(@Cached(value="this", adopt=false) JavaScriptNode cachedNode) {
        return this == cachedNode;
    }

    @ExportMessage
    final boolean hasScope(Frame frame) {
        return this.getParent() != null;
    }

    @ExportMessage
    final Object getScope(Frame frame, boolean nodeEnter, @Cached(value="findBlockScopeNode(this)", allowUncached=true, adopt=false) Node blockNode, @Cached(value="findFrameScopeNode(blockNode)", allowUncached=true, adopt=false) Node frameBlockNode) throws UnsupportedMessageException {
        if (this.hasScope(frame)) {
            Frame scopeFrame;
            MaterializedFrame functionFrame;
            if (frame != null) {
                Object maybeScopeFrame;
                RootNode rootNode = this.getRootNode();
                functionFrame = rootNode instanceof JavaScriptRootNode && ((JavaScriptRootNode)rootNode).isResumption() && frame.getFrameDescriptor() == rootNode.getFrameDescriptor() ? JSArguments.getResumeExecutionContext(frame.getArguments()) : (rootNode.getFrameDescriptor() == JavaScriptRootNode.MODULE_DUMMY_FRAMEDESCRIPTOR ? ((JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0)).getEnvironment() : frame.materialize());
                scopeFrame = frameBlockNode instanceof BlockScopeNode.FrameBlockScopeNode ? ((maybeScopeFrame = ((BlockScopeNode.FrameBlockScopeNode)frameBlockNode).getBlockScope(functionFrame)) instanceof Frame ? (Frame)maybeScopeFrame : functionFrame) : functionFrame;
            } else {
                functionFrame = null;
                scopeFrame = null;
            }
            return ScopeVariables.create(scopeFrame, nodeEnter, blockNode, functionFrame);
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    final boolean hasReceiverMember(Frame frame) {
        return frame != null;
    }

    @ExportMessage
    final Object getReceiverMember(Frame frame) throws UnsupportedMessageException {
        if (frame == null) {
            throw UnsupportedMessageException.create();
        }
        return ScopeVariables.RECEIVER_MEMBER;
    }

    @ExportMessage
    boolean hasRootInstance(Frame frame) {
        return frame != null;
    }

    @ExportMessage
    Object getRootInstance(Frame frame) throws UnsupportedMessageException {
        if (frame == null) {
            throw UnsupportedMessageException.create();
        }
        return JSFrameUtil.getFunctionObject(frame);
    }

    @CompilerDirectives.TruffleBoundary
    public static Node findBlockScopeNode(Node node) {
        Node parent;
        if (node == null) {
            return null;
        }
        for (Node n = parent = node; n != null; n = n.getParent()) {
            if (n instanceof BlockScopeNode) {
                return n;
            }
            parent = n;
        }
        assert (parent instanceof RootNode) : "Node " + node + " is not adopted.";
        return parent;
    }

    @CompilerDirectives.TruffleBoundary
    static Node findFrameScopeNode(Node node) {
        Node parent;
        if (node == null) {
            return null;
        }
        for (Node n = parent = node; n != null; n = n.getParent()) {
            if (n instanceof BlockScopeNode.FrameBlockScopeNode) {
                return n;
            }
            parent = n;
        }
        assert (parent instanceof RootNode) : "Node " + node + " is not adopted.";
        return parent;
    }
}

