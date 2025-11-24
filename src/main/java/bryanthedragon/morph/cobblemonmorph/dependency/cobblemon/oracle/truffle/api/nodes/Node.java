
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ReplaceObserver;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.BytecodeOSRNode;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.NodeAccessor;
import com.oracle.truffle.api.nodes.NodeClass;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeInterface;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Node
implements NodeInterface,
Cloneable {
    @CompilerDirectives.CompilationFinal
    private volatile Node parent;
    private static final int PARENT_LIMIT = 100000;
    private static final ReentrantLock GIL_LOCK = new ReentrantLock(false);

    protected Node() {
        CompilerAsserts.neverPartOfCompilation("do not create a Node from compiled code");
        assert (NodeClass.get(this.getClass()) != null);
    }

    final NodeClass getNodeClass() {
        return NodeClass.get(this.getClass());
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    public NodeCost getCost() {
        NodeInfo info = this.getClass().getAnnotation(NodeInfo.class);
        if (info != null) {
            return info.cost();
        }
        return NodeCost.MONOMORPHIC;
    }

    public SourceSection getSourceSection() {
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    public SourceSection getEncapsulatingSourceSection() {
        for (Node current = this; current != null; current = current.getParent()) {
            SourceSection currentSection = current.getSourceSection();
            if (currentSection == null) continue;
            return currentSection;
        }
        return null;
    }

    public boolean isAdoptable() {
        return true;
    }

    protected final <T extends Node> T[] insert(T[] newChildren) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        if (newChildren != null) {
            for (T newChild : newChildren) {
                this.adoptHelper((Node)newChild);
            }
        }
        return newChildren;
    }

    protected final <T extends Node> T insert(T newChild) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        if (newChild != null) {
            this.adoptHelper(newChild);
        }
        return newChild;
    }

    protected final void notifyInserted(Node node) {
        RootNode rootNode = node.getRootNode();
        if (rootNode == null) {
            throw new IllegalStateException("Node is not yet adopted and cannot be updated.");
        }
        NodeAccessor.INSTRUMENT.onNodeInserted(rootNode, node);
    }

    public final void adoptChildren() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        NodeUtil.adoptChildrenHelper(this);
    }

    final void adoptHelper(Node newChild) {
        assert (newChild != null);
        if (newChild == this) {
            throw new IllegalStateException("The parent of a node can never be the node itself.");
        }
        if (newChild.isAdoptable()) {
            assert (this.checkSameLanguages(newChild));
            newChild.parent = this;
            NodeUtil.adoptChildrenHelper(newChild);
        }
    }

    int adoptChildrenAndCount() {
        CompilerAsserts.neverPartOfCompilation();
        return 1 + NodeUtil.adoptChildrenAndCountHelper(this);
    }

    int adoptAndCountHelper(Node newChild) {
        assert (newChild != null);
        if (newChild == this) {
            throw new IllegalStateException("The parent of a node can never be the node itself.");
        }
        int count = 1;
        if (newChild.isAdoptable()) {
            assert (this.checkSameLanguages(newChild));
            newChild.parent = this;
            count += NodeUtil.adoptChildrenAndCountHelper(newChild);
        }
        return count;
    }

    private boolean checkSameLanguages(Node newChild) {
        if (newChild instanceof ExecutableNode && !(newChild instanceof RootNode)) {
            RootNode root = this.getRootNode();
            if (root == null) {
                throw new IllegalStateException("Cannot adopt ExecutableNode " + newChild + " as a child of node without a root.");
            }
            LanguageInfo pl = root.getLanguageInfo();
            LanguageInfo cl = ((ExecutableNode)newChild).getLanguageInfo();
            if (cl != pl) {
                throw new IllegalArgumentException("Can not adopt ExecutableNode under a different language. Parent " + this + " is of " + Node.langId(pl) + ", child " + newChild + " is of " + Node.langId(cl));
            }
        }
        return true;
    }

    private static String langId(LanguageInfo languageInfo) {
        if (languageInfo == null) {
            return null;
        }
        return languageInfo.getId();
    }

    private void adoptUnadoptedHelper(final Node newChild) {
        assert (this.isAdoptable());
        assert (newChild != null);
        if (newChild == this) {
            throw new IllegalStateException("The parent of a node can never be the node itself.");
        }
        newChild.parent = this;
        NodeUtil.forEachChild(newChild, new NodeVisitor(){

            @Override
            public boolean visit(Node child) {
                if (child != null && child.getParent() == null) {
                    newChild.adoptUnadoptedHelper(child);
                }
                return true;
            }
        });
    }

    public Map<String, Object> getDebugProperties() {
        HashMap<String, Object> properties2 = new HashMap<String, Object>();
        return properties2;
    }

    public final Node getParent() {
        return this.parent;
    }

    public final <T extends Node> T replace(final T newNode, final CharSequence reason) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.atomic(new Runnable(){

            @Override
            public void run() {
                Node.this.replaceHelper(newNode, reason);
            }
        });
        return newNode;
    }

    public final <T extends Node> T replace(T newNode) {
        return this.replace(newNode, "");
    }

    final void replaceHelper(Node newNode, CharSequence reason) {
        CompilerAsserts.neverPartOfCompilation("do not call Node.replaceHelper from compiled code");
        assert (this.inAtomicBlock());
        if (this.getParent() == null) {
            throw new IllegalStateException("This node cannot be replaced, because it does not yet have a parent.");
        }
        if (newNode.isAdoptable()) {
            newNode.parent = this.parent;
        }
        if (!NodeUtil.replaceChild(this.parent, this, newNode, true)) {
            this.parent.adoptUnadoptedHelper(newNode);
        }
        this.reportReplace(this, newNode, reason);
        this.onReplace(newNode, reason);
    }

    public final boolean isSafelyReplaceableBy(Node newNode) {
        return NodeUtil.isReplacementSafe(this.getParent(), this, newNode);
    }

    private void reportReplace(Node oldNode, Node newNode, CharSequence reason) {
        for (Node node = this; node != null; node = node.getParent()) {
            RootCallTarget target;
            boolean consumed = false;
            if (node instanceof ReplaceObserver) {
                consumed = ((ReplaceObserver)((Object)node)).nodeReplaced(oldNode, newNode, reason);
            } else if (node instanceof BytecodeOSRNode) {
                NodeAccessor.RUNTIME.onOSRNodeReplaced((BytecodeOSRNode)((Object)node), oldNode, newNode, reason);
            } else if (node instanceof RootNode && (target = ((RootNode)node).getCallTargetWithoutInitialization()) instanceof ReplaceObserver) {
                consumed = ((ReplaceObserver)((Object)target)).nodeReplaced(oldNode, newNode, reason);
            }
            if (consumed) break;
        }
        if (TruffleOptions.TraceRewrites) {
            NodeUtil.traceRewrite(this, newNode, reason);
        }
    }

    protected void onReplace(Node newNode, CharSequence reason) {
    }

    public final void accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            NodeUtil.forEachChildRecursive(this, nodeVisitor);
        }
    }

    public final Iterable<Node> getChildren() {
        return new Iterable<Node>(){

            @Override
            public Iterator<Node> iterator() {
                return Node.this.getNodeClass().makeIterator(Node.this);
            }
        };
    }

    public Node copy() {
        CompilerAsserts.neverPartOfCompilation("do not call Node.copy from compiled code");
        try {
            return (Node)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }

    public Node deepCopy() {
        return NodeUtil.deepCopyImpl(this);
    }

    public final RootNode getRootNode() {
        if (CompilerDirectives.isPartialEvaluationConstant(this)) {
            return this.getRootNodeImpl();
        }
        return this.getRootBoundary();
    }

    @CompilerDirectives.TruffleBoundary
    private RootNode getRootBoundary() {
        return this.getRootNodeImpl();
    }

    @ExplodeLoop
    private RootNode getRootNodeImpl() {
        Node prev;
        Node node = this;
        int parentsVisited = 0;
        do {
            if (parentsVisited++ > 100000) {
                assert (false) : "getRootNode() did not terminate in 100000 iterations.";
                return null;
            }
            prev = node;
        } while ((node = node.parent) != null);
        if (prev instanceof RootNode) {
            return (RootNode)prev;
        }
        return null;
    }

    protected final void reportPolymorphicSpecialize() {
        CompilerAsserts.neverPartOfCompilation();
        NodeAccessor.RUNTIME.reportPolymorphicSpecialize(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        for (Class<?> enclosing = this.getClass().getEnclosingClass(); enclosing != null; enclosing = enclosing.getEnclosingClass()) {
            sb.insert(0, enclosing.getSimpleName() + ".");
        }
        Map<String, Object> properties2 = this.getDebugProperties();
        boolean hasProperties = false;
        for (Map.Entry<String, Object> entry : properties2.entrySet()) {
            sb.append(hasProperties ? "," : "<");
            hasProperties = true;
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        if (hasProperties) {
            sb.append(">");
        }
        sb.append("@").append(Integer.toHexString(this.hashCode()));
        return sb.toString();
    }

    public final void atomic(Runnable closure) {
        Lock lock = this.getLock();
        try {
            lock.lock();
            closure.run();
        }
        finally {
            lock.unlock();
        }
    }

    public final <T> T atomic(Callable<T> closure) {
        Lock lock = this.getLock();
        try {
            lock.lock();
            T t = closure.call();
            return t;
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }

    protected final Lock getLock() {
        RootNode root = this.getRootNode();
        if (root == null) {
            return GIL_LOCK;
        }
        return root.getLazyLock();
    }

    public String getDescription() {
        NodeInfo info = this.getClass().getAnnotation(NodeInfo.class);
        if (info != null) {
            return info.description();
        }
        return "";
    }

    @ExplodeLoop
    private ExecutableNode getExecutableNode() {
        Node node;
        for (node = this; node != null; node = node.getParent()) {
            if (!(node instanceof ExecutableNode)) continue;
            return (ExecutableNode)node;
        }
        if (node == null) {
            this.checkAdoptable();
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    private void checkAdoptable() {
        if (this.isAdoptable()) {
            throw new IllegalStateException("Node must be adopted before a reference can be looked up.");
        }
    }

    private boolean inAtomicBlock() {
        return ((ReentrantLock)this.getLock()).isHeldByCurrentThread();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface Child {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface Children {
    }
}

