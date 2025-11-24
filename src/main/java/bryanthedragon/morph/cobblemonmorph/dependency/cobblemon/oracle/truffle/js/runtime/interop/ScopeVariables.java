
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.FrameDescriptorProvider;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.module.ReadImportBindingNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.interop.ScopeMembers;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.OptionalInt;

@ExportLibrary(value=InteropLibrary.class)
public final class ScopeVariables
implements TruffleObject {
    public static final TruffleString RECEIVER_MEMBER = Strings.THIS;
    static final int LIMIT = 4;
    final Frame frame;
    final boolean nodeEnter;
    final Node blockOrRoot;
    final Frame functionFrame;
    private ScopeMembers members;

    private ScopeVariables(Frame frame, boolean nodeEnter, Node blockOrRoot, Frame functionFrame) {
        assert (ScopeVariables.isBlockScopeOrRootNode(blockOrRoot));
        this.frame = frame;
        this.nodeEnter = nodeEnter;
        this.blockOrRoot = blockOrRoot;
        this.functionFrame = functionFrame;
    }

    static boolean isBlockScopeOrRootNode(Node blockOrRoot) {
        return blockOrRoot instanceof BlockScopeNode || blockOrRoot instanceof RootNode;
    }

    public static ScopeVariables create(Frame frame, boolean nodeEnter, Node blockOrRoot, Frame functionFrame) {
        return new ScopeVariables(frame, nodeEnter, blockOrRoot, functionFrame);
    }

    @ExportMessage
    boolean accepts(@Cached(value="this.blockOrRoot", adopt=false) Node cachedNode, @Cached(value="this.nodeEnter") boolean cachedNodeEnter) {
        return this.blockOrRoot == cachedNode && this.nodeEnter == cachedNodeEnter;
    }

    @ExportMessage
    boolean isScope() {
        return true;
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean hasScopeParent() {
        if (this.blockOrRoot instanceof BlockScopeNode) {
            Node parentBlock;
            BlockScopeNode blockScopeNode = (BlockScopeNode)this.blockOrRoot;
            while ((parentBlock = JavaScriptNode.findBlockScopeNode(blockScopeNode.getParent())) != null) {
                if (this.frame == null) {
                    return true;
                }
                if (blockScopeNode instanceof BlockScopeNode.FrameBlockScopeNode && blockScopeNode.isFunctionBlock()) {
                    if (parentBlock instanceof BlockScopeNode) {
                        blockScopeNode = (BlockScopeNode)parentBlock;
                        continue;
                    }
                } else {
                    if (parentBlock instanceof BlockScopeNode) {
                        return true;
                    }
                    if (parentBlock instanceof RootNode && this.functionFrame != null) {
                        return true;
                    }
                }
                break;
            }
        } else {
            assert (this.blockOrRoot instanceof RootNode);
            if (this.frame != null && ScopeFrameNode.isBlockScopeFrame(this.frame) && this.getParentFrame() != null) {
                return true;
            }
        }
        if (this.frame != null) {
            MaterializedFrame parentFrame = JSFrameUtil.getParentFrame(this.frame);
            return parentFrame != null && parentFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getScopeParent() throws UnsupportedMessageException {
        MaterializedFrame parentFrame;
        if (!(this.blockOrRoot instanceof BlockScopeNode)) {
            Frame parentBlockScope;
            assert (this.blockOrRoot instanceof RootNode);
            if (this.frame != null && ScopeFrameNode.isBlockScopeFrame(this.frame) && (parentBlockScope = this.getParentFrame()) != null) {
                return new ScopeVariables(parentBlockScope, true, this.blockOrRoot, null);
            }
        } else {
            Node parentBlock;
            BlockScopeNode blockScopeNode = (BlockScopeNode)this.blockOrRoot;
            Frame enclosingFrame = this.frame;
            while ((parentBlock = JavaScriptNode.findBlockScopeNode(blockScopeNode.getParent())) != null) {
                if (this.frame == null) {
                    return new ScopeVariables(null, true, parentBlock, null);
                }
                if (blockScopeNode instanceof BlockScopeNode.FrameBlockScopeNode) {
                    enclosingFrame = this.getParentFrame();
                    if (blockScopeNode.isFunctionBlock()) {
                        if (!(parentBlock instanceof BlockScopeNode)) break;
                        blockScopeNode = (BlockScopeNode)parentBlock;
                        assert (enclosingFrame != null);
                        continue;
                    }
                }
                if (parentBlock instanceof BlockScopeNode) {
                    return new ScopeVariables(enclosingFrame, true, parentBlock, this.functionFrame);
                }
                if (!(parentBlock instanceof RootNode) || this.functionFrame == null) break;
                return new ScopeVariables(this.functionFrame, true, parentBlock, this.functionFrame);
            }
        }
        if (this.frame != null && (parentFrame = JSFrameUtil.getParentFrame(this.frame)) != null && parentFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME) {
            RootNode rootNode = ((RootCallTarget)JSFunction.getCallTarget(JSFrameUtil.getFunctionObject(parentFrame))).getRootNode();
            return new ScopeVariables(parentFrame, true, rootNode, null);
        }
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    private Frame getParentFrame() {
        Object parent;
        OptionalInt parentSlot = JSFrameUtil.findOptionalFrameSlotIndex(this.frame.getFrameDescriptor(), ScopeFrameNode.PARENT_SCOPE_IDENTIFIER);
        if (parentSlot.isPresent() && (parent = this.frame.getObject(parentSlot.getAsInt())) instanceof Frame) {
            return (Frame)parent;
        }
        return null;
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object getMembers(boolean includeInternal) {
        ScopeMembers m = this.members;
        if (m == null) {
            this.members = m = new ScopeMembers(this.frame, this.blockOrRoot, this.functionFrame);
        }
        return m;
    }

    @ExportMessage
    boolean isMemberInsertable(String member) {
        return false;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean hasSourceLocation() {
        return this.blockOrRoot.getEncapsulatingSourceSection() != null;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    SourceSection getSourceLocation() throws UnsupportedMessageException {
        SourceSection sourceLocation;
        Node sourceSectionProvider = this.blockOrRoot;
        if (sourceSectionProvider instanceof BlockScopeNode && ((BlockScopeNode)sourceSectionProvider).isFunctionBlock()) {
            sourceSectionProvider = sourceSectionProvider.getRootNode();
        }
        if ((sourceLocation = sourceSectionProvider.getEncapsulatingSourceSection()) == null) {
            throw UnsupportedMessageException.create();
        }
        return sourceLocation;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    Object toDisplayString(boolean allowSideEffects) {
        RootNode root;
        if (this.blockOrRoot instanceof BlockScopeNode) {
            if (!((BlockScopeNode)this.blockOrRoot).isFunctionBlock()) return "block";
            root = this.blockOrRoot.getRootNode();
        } else {
            root = (RootNode)this.blockOrRoot;
        }
        String name = root.getName();
        if (name == null) {
            return "";
        }
        String string = name;
        return string;
    }

    static ResolvedSlot findSlot(String memberString, ScopeVariables receiver) {
        CompilerAsserts.neverPartOfCompilation();
        final TruffleString member = Strings.fromJavaString(memberString);
        if (receiver.frame == null) {
            return ScopeVariables.findSlotWithoutFrame(member, receiver.blockOrRoot);
        }
        class SlotVisitor {
            Node descNode;
            int parentSlot;
            int frameLevel;
            int scopeLevel;

            SlotVisitor() {
                this.descNode = ScopeVariables.this.blockOrRoot;
                this.parentSlot = -1;
                this.frameLevel = 0;
                this.scopeLevel = 0;
            }

            public ResolvedSlot accept(FrameDescriptor frameDescriptor, int slot, Frame targetFrame) {
                Object slotName;
                assert (targetFrame.getFrameDescriptor() == frameDescriptor);
                int effectiveScopeLevel = this.scopeLevel;
                if (targetFrame == ScopeVariables.this.functionFrame) {
                    assert (ScopeVariables.this.functionFrame.getFrameDescriptor() == frameDescriptor);
                    effectiveScopeLevel = -1;
                }
                if (ScopeFrameNode.PARENT_SCOPE_IDENTIFIER.equals(slotName = frameDescriptor.getSlotName(slot))) {
                    this.parentSlot = slot;
                } else if (ScopeFrameNode.EVAL_SCOPE_IDENTIFIER.equals(slotName)) {
                    JSDynamicObject evalScope = (JSDynamicObject)targetFrame.getObject(slot);
                    if (JSRuntime.isObject(evalScope) && DynamicObjectLibrary.getUncached().containsKey(evalScope, member)) {
                        return new DynamicScopeResolvedSlot(member, slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                    }
                } else {
                    if (JSFrameUtil.isThisSlot(frameDescriptor, slot) && RECEIVER_MEMBER.equals(member)) {
                        return new ResolvedThisSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                    }
                    if (!JSFrameUtil.isInternal(frameDescriptor, slot) && member.equals(slotName)) {
                        if (JSFrameUtil.isImportBinding(frameDescriptor, slot)) {
                            return new ResolvedImportSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                        }
                        return new ResolvedSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                    }
                }
                return null;
            }
        }
        SlotVisitor visitor = receiver.new SlotVisitor();
        Frame outerFrame = receiver.frame;
        if (receiver.functionFrame != null) {
            FrameDescriptor rootFrameDescriptor = receiver.functionFrame.getFrameDescriptor();
            while (visitor.descNode instanceof BlockScopeNode) {
                BlockScopeNode block = (BlockScopeNode)visitor.descNode;
                visitor.parentSlot = -1;
                if (block instanceof BlockScopeNode.FrameBlockScopeNode) {
                    FrameDescriptor blockFrameDescriptor = ((BlockScopeNode.FrameBlockScopeNode)block).getFrameDescriptor();
                    assert (outerFrame.getFrameDescriptor() == blockFrameDescriptor || block == receiver.blockOrRoot);
                    if (outerFrame.getFrameDescriptor() == blockFrameDescriptor) {
                        for (int i = 0; i < blockFrameDescriptor.getNumberOfSlots(); ++i) {
                            ResolvedSlot resolvedSlot = visitor.accept(blockFrameDescriptor, i, outerFrame);
                            if (resolvedSlot == null) continue;
                            return resolvedSlot;
                        }
                    }
                }
                for (int i = block.getFrameStart(); i < block.getFrameEnd(); ++i) {
                    ResolvedSlot resolvedSlot = visitor.accept(rootFrameDescriptor, i, receiver.functionFrame);
                    if (resolvedSlot == null) continue;
                    return resolvedSlot;
                }
                visitor.descNode = JavaScriptNode.findBlockScopeNode(visitor.descNode.getParent());
                if (visitor.parentSlot < 0) continue;
                Object parent = outerFrame.getObject(visitor.parentSlot);
                if (!(parent instanceof Frame)) break;
                outerFrame = (Frame)parent;
                assert (outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME);
                ++visitor.scopeLevel;
            }
            assert (receiver.functionFrame.getFrameDescriptor() == rootFrameDescriptor && visitor.frameLevel == 0);
            visitor.scopeLevel = -1;
            for (int slot = 0; slot < rootFrameDescriptor.getNumberOfSlots(); ++slot) {
                ResolvedSlot resolvedSlot;
                if (JSFrameUtil.isHoistedFromBlock(rootFrameDescriptor, slot) || (resolvedSlot = visitor.accept(rootFrameDescriptor, slot, receiver.functionFrame)) == null) continue;
                return resolvedSlot;
            }
            outerFrame = JSArguments.getEnclosingFrame(receiver.frame.getArguments());
            visitor.frameLevel = 1;
        }
        while (outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME) {
            visitor.descNode = JSFunction.getFunctionData(JSFrameUtil.getFunctionObject(outerFrame)).getRootNode();
            visitor.scopeLevel = 0;
            while (true) {
                Object parent;
                visitor.parentSlot = -1;
                for (int slot = 0; slot < outerFrame.getFrameDescriptor().getNumberOfSlots(); ++slot) {
                    ResolvedSlot resolvedSlot = visitor.accept(outerFrame.getFrameDescriptor(), slot, outerFrame);
                    if (resolvedSlot == null) continue;
                    return resolvedSlot;
                }
                if (visitor.parentSlot < 0 || !((parent = outerFrame.getObject(visitor.parentSlot)) instanceof Frame)) break;
                outerFrame = (Frame)parent;
                assert (outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME);
                ++visitor.scopeLevel;
            }
            outerFrame = JSArguments.getEnclosingFrame(outerFrame.getArguments());
            ++visitor.frameLevel;
        }
        if (receiver.frame != null && RECEIVER_MEMBER.equals(member)) {
            return new ResolvedThisSlot();
        }
        return null;
    }

    private static ResolvedSlot findSlotWithoutFrame(TruffleString member, Node blockOrRootNode) {
        CompilerAsserts.neverPartOfCompilation();
        Node descNode = blockOrRootNode;
        while (descNode != null && descNode instanceof FrameDescriptorProvider) {
            FrameDescriptor desc = ((FrameDescriptorProvider)((Object)descNode)).getFrameDescriptor();
            OptionalInt slot = JSFrameUtil.findOptionalFrameSlotIndex(desc, member);
            if (slot.isPresent()) {
                if (JSFrameUtil.isInternal(desc, slot.getAsInt())) {
                    return null;
                }
                return new ResolvedSlot();
            }
            descNode = JavaScriptNode.findBlockScopeNode(descNode.getParent());
        }
        return null;
    }

    static boolean hasSlot(String member, ScopeVariables receiver) {
        return ScopeVariables.findSlot(member, receiver) != null;
    }

    static JavaScriptNode findReadNode(ResolvedSlot slot) {
        if (slot != null) {
            return slot.createReadNode();
        }
        return null;
    }

    static WriteNode findWriteNode(ResolvedSlot slot) {
        if (slot != null && slot.isModifiable()) {
            return slot.createWriteNode();
        }
        return null;
    }

    static Object thisFromFunctionOrArguments(Object[] args) {
        Object function = JSArguments.getFunctionObject(args);
        if (JSFunction.isJSFunction(function)) {
            JSDynamicObject jsFunction = (JSDynamicObject)function;
            return ScopeVariables.isArrowFunctionWithThisCaptured(jsFunction) ? JSFunction.getLexicalThis(jsFunction) : ScopeVariables.thisFromArguments(args);
        }
        return Undefined.instance;
    }

    static Object thisFromArguments(Object[] args) {
        Object thisObject = JSArguments.getThisObject(args);
        Object function = JSArguments.getFunctionObject(args);
        if (JSFunction.isJSFunction(function) && !JSFunction.isStrict((JSDynamicObject)function)) {
            JSRealm realm = JavaScriptLanguage.getCurrentJSRealm();
            thisObject = thisObject == Undefined.instance || thisObject == Null.instance ? realm.getGlobalObject() : JSRuntime.toObject(realm.getContext(), thisObject);
        }
        return thisObject;
    }

    private static boolean isArrowFunctionWithThisCaptured(JSDynamicObject function) {
        return !JSFunction.isConstructor(function) && JSFunction.isClassPrototypeInitialized(function);
    }

    static class ResolvedImportSlot
    extends ResolvedSlot {
        ResolvedImportSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
            super(slot, frameLevel, scopeLevel, descriptor);
        }

        @Override
        JavaScriptNode createReadNode() {
            if (!this.hasSlot()) {
                return JSConstantNode.createUndefined();
            }
            return ReadImportBindingNode.create(super.createReadNode());
        }
    }

    static final class ReadThisNode
    extends JavaScriptNode {
        @Node.Child
        JavaScriptNode readThis;

        ReadThisNode(JavaScriptNode readThis) {
            this.readThis = readThis;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            if (this.readThis == null) {
                return ScopeVariables.thisFromArguments(frame.getArguments());
            }
            Object thisValue = this.readThis.execute(frame);
            if (thisValue == Undefined.instance) {
                return ScopeVariables.thisFromFunctionOrArguments(frame.getArguments());
            }
            return thisValue;
        }
    }

    static class ResolvedThisSlot
    extends ResolvedSlot {
        ResolvedThisSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
            super(slot, frameLevel, scopeLevel, descriptor);
        }

        ResolvedThisSlot() {
        }

        @Override
        JavaScriptNode createReadNode() {
            return new ReadThisNode(this.hasSlot() ? super.createReadNode() : null);
        }
    }

    static class DynamicScopeResolvedSlot
    extends ResolvedSlot {
        final Object key;

        DynamicScopeResolvedSlot(Object key, int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
            super(slot, frameLevel, scopeLevel, descriptor);
            this.key = key;
        }

        @Override
        JavaScriptNode createReadNode() {
            final JavaScriptNode readDynamicScope = super.createReadNode();
            class EvalRead
            extends JavaScriptNode {
                @Node.Child
                JavaScriptNode getDynamicScope;
                @Node.Child
                DynamicObjectLibrary objectLibrary;

                EvalRead() {
                    this.getDynamicScope = readDynamicScope;
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    JSDynamicObject scope = (JSDynamicObject)this.getDynamicScope.execute(frame);
                    if (!JSRuntime.isObject(scope)) {
                        return Undefined.instance;
                    }
                    DynamicObjectLibrary lib = this.objectLibrary;
                    if (lib == null) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        lib = this.getParent() != null ? this.insert(DynamicObjectLibrary.getFactory().createDispatched(5)) : DynamicObjectLibrary.getUncached();
                        this.objectLibrary = lib;
                    }
                    return Properties.getOrDefault(lib, scope, DynamicScopeResolvedSlot.this.key, Undefined.instance);
                }
            }
            return new EvalRead();
        }

        @Override
        WriteNode createWriteNode() {
            final JavaScriptNode readDynamicScope = super.createReadNode();
            class EvalWrite
            extends JavaScriptNode
            implements WriteNode {
                @Node.Child
                JavaScriptNode getDynamicScope;
                @Node.Child
                DynamicObjectLibrary objectLibrary;

                EvalWrite() {
                    this.getDynamicScope = readDynamicScope;
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    throw CompilerDirectives.shouldNotReachHere();
                }

                @Override
                public void executeWrite(VirtualFrame frame, Object value2) {
                    JSDynamicObject scope = (JSDynamicObject)this.getDynamicScope.execute(frame);
                    if (!JSRuntime.isObject(scope)) {
                        return;
                    }
                    DynamicObjectLibrary lib = this.objectLibrary;
                    if (lib == null) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        lib = this.getParent() != null ? this.insert(DynamicObjectLibrary.getFactory().createDispatched(5)) : DynamicObjectLibrary.getUncached();
                        this.objectLibrary = lib;
                    }
                    lib.putIfPresent(scope, DynamicScopeResolvedSlot.this.key, value2);
                }

                @Override
                public JavaScriptNode getRhs() {
                    return null;
                }
            }
            return new EvalWrite();
        }
    }

    static class ResolvedSlot {
        final int slot;
        final int frameLevel;
        final int scopeLevel;
        final FrameDescriptor descriptor;

        ResolvedSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
            this.slot = slot;
            this.frameLevel = frameLevel;
            this.scopeLevel = scopeLevel;
            this.descriptor = descriptor;
        }

        ResolvedSlot() {
            this(-1, -1, -1, null);
        }

        JavaScriptNode createReadNode() {
            if (!this.hasSlot()) {
                return JSConstantNode.createUndefined();
            }
            ScopeFrameNode scopeFrameNode = this.createScopeFrameNode();
            return JSReadFrameSlotNode.create(JSFrameSlot.fromIndexedFrameSlot(this.descriptor, this.slot), scopeFrameNode, JSFrameUtil.hasTemporalDeadZone(this.descriptor, this.slot));
        }

        WriteNode createWriteNode() {
            if (!this.hasSlot()) {
                return null;
            }
            ScopeFrameNode scopeFrameNode = this.createScopeFrameNode();
            return JSWriteFrameSlotNode.create(JSFrameSlot.fromIndexedFrameSlot(this.descriptor, this.slot), scopeFrameNode, null, JSFrameUtil.hasTemporalDeadZone(this.descriptor, this.slot));
        }

        ScopeFrameNode createScopeFrameNode() {
            if (this.isFunctionFrame()) {
                return ScopeFrameNode.createCurrent();
            }
            return ScopeFrameNode.create(this.frameLevel, this.scopeLevel, null);
        }

        boolean isModifiable() {
            return this.hasSlot() && !JSFrameUtil.isConst(this.descriptor, this.slot) && !JSFrameUtil.isThisSlot(this.descriptor, this.slot) && !JSFrameUtil.isImportBinding(this.descriptor, this.slot);
        }

        boolean hasSlot() {
            return this.slot >= 0;
        }

        boolean isFunctionFrame() {
            return this.scopeLevel < 0;
        }

        public String toString() {
            if (this.hasSlot()) {
                return this.getClass().getSimpleName() + "(" + String.valueOf(this.descriptor.getSlotName(this.slot)) + ", #" + this.slot + ", " + this.frameLevel + "/" + this.scopeLevel + ")";
            }
            return super.toString();
        }
    }

    @ExportMessage
    static final class WriteMember {
        WriteMember() {
        }

        @Specialization(guards={"cachedMember.equals(member)"}, limit="LIMIT")
        static void doCached(ScopeVariables receiver, String member, Object value2, @Cached(value="member") String cachedMember, @Cached(value="findSlot(member, receiver)") ResolvedSlot resolvedSlot, @Cached(value="findWriteNode(resolvedSlot)") WriteNode writeNode) throws UnknownIdentifierException {
            WriteMember.doWrite(receiver, cachedMember, value2, writeNode, resolvedSlot);
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static void doGeneric(ScopeVariables receiver, String member, Object value2) throws UnknownIdentifierException {
            ResolvedSlot resolvedSlot = ScopeVariables.findSlot(member, receiver);
            WriteNode writeNode = ScopeVariables.findWriteNode(resolvedSlot);
            WriteMember.doWrite(receiver, member, value2, writeNode, resolvedSlot);
        }

        private static void doWrite(ScopeVariables receiver, String member, Object value2, WriteNode writeNode, ResolvedSlot resolvedSlot) throws UnknownIdentifierException {
            Frame frame;
            if (writeNode == null) {
                throw UnknownIdentifierException.create(member);
            }
            Frame frame2 = frame = resolvedSlot.isFunctionFrame() ? receiver.functionFrame : receiver.frame;
            if (frame == null) {
                throw UnknownIdentifierException.create(member);
            }
            writeNode.executeWrite((VirtualFrame)frame, value2);
        }
    }

    @ExportMessage
    static final class ReadMember {
        ReadMember() {
        }

        @Specialization(guards={"cachedMember.equals(member)"}, limit="LIMIT")
        static Object doCached(ScopeVariables receiver, String member, @Cached(value="member") String cachedMember, @Cached(value="findSlot(member, receiver)") ResolvedSlot resolvedSlot, @Cached(value="findReadNode(resolvedSlot)") JavaScriptNode readNode) throws UnknownIdentifierException {
            return ReadMember.doRead(receiver, cachedMember, readNode, resolvedSlot);
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static Object doGeneric(ScopeVariables receiver, String member) throws UnknownIdentifierException {
            ResolvedSlot resolvedSlot = ScopeVariables.findSlot(member, receiver);
            JavaScriptNode readNode = ScopeVariables.findReadNode(resolvedSlot);
            return ReadMember.doRead(receiver, member, readNode, resolvedSlot);
        }

        private static Object doRead(ScopeVariables receiver, String member, JavaScriptNode readNode, ResolvedSlot resolvedSlot) throws UnknownIdentifierException {
            Frame frame;
            if (readNode == null) {
                throw UnknownIdentifierException.create(member);
            }
            Frame frame2 = frame = resolvedSlot.isFunctionFrame() ? receiver.functionFrame : receiver.frame;
            if (frame == null) {
                return Undefined.instance;
            }
            return readNode.execute((VirtualFrame)frame);
        }
    }

    @ExportMessage
    static final class IsMemberModifiable {
        IsMemberModifiable() {
        }

        @Specialization(guards={"cachedMember.equals(member)"}, limit="LIMIT")
        static boolean doCached(ScopeVariables receiver, String member, @Cached(value="member") String cachedMember, @Cached(value="doGeneric(receiver, member)") boolean cachedResult) {
            assert (cachedResult == IsMemberModifiable.doGeneric(receiver, member));
            return cachedResult;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static boolean doGeneric(ScopeVariables receiver, String member) {
            ResolvedSlot slot = ScopeVariables.findSlot(member, receiver);
            return slot != null && slot.isModifiable();
        }
    }

    @ExportMessage
    static final class IsMemberReadable {
        IsMemberReadable() {
        }

        @Specialization(guards={"cachedMember.equals(member)"}, limit="LIMIT")
        static boolean doCached(ScopeVariables receiver, String member, @Cached(value="member") String cachedMember, @Cached(value="doGeneric(receiver, member)") boolean cachedResult) {
            assert (cachedResult == IsMemberReadable.doGeneric(receiver, member));
            return cachedResult;
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        static boolean doGeneric(ScopeVariables receiver, String member) {
            return ScopeVariables.hasSlot(member, receiver);
        }
    }
}

