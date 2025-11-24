
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.Set;

public class IteratorCloseWrapperNode
extends JavaScriptNode {
    @Node.Child
    private JavaScriptNode blockNode;
    @Node.Child
    private JavaScriptNode iteratorNode;
    @Node.Child
    private IteratorCloseNode iteratorCloseNode;
    private final JSContext context;
    private final BranchProfile throwBranch = BranchProfile.create();
    private final BranchProfile exitBranch = BranchProfile.create();
    private final BranchProfile notDoneBranch = BranchProfile.create();

    protected IteratorCloseWrapperNode(JSContext context, JavaScriptNode block, JavaScriptNode iterator) {
        this.context = context;
        this.blockNode = block;
        this.iteratorNode = iterator;
    }

    public static JavaScriptNode create(JSContext context, JavaScriptNode block, JavaScriptNode iterator) {
        return new IteratorCloseWrapperNode(context, block, iterator);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object result;
        try {
            result = this.blockNode.execute(frame);
        }
        catch (YieldException e) {
            throw e;
        }
        catch (ControlFlowException e) {
            this.exitBranch.enter();
            IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
            if (!iteratorRecord.isDone()) {
                this.iteratorClose().executeVoid(iteratorRecord.getIterator());
            }
            throw e;
        }
        catch (AbstractTruffleException e) {
            this.throwBranch.enter();
            IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
            if (!iteratorRecord.isDone()) {
                this.iteratorClose().executeAbrupt(iteratorRecord.getIterator());
            }
            throw e;
        }
        IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
        if (!iteratorRecord.isDone()) {
            this.notDoneBranch.enter();
            this.iteratorClose().executeVoid(iteratorRecord.getIterator());
        }
        return result;
    }

    private IteratorRecord getIteratorRecord(VirtualFrame frame) {
        return (IteratorRecord)this.iteratorNode.execute(frame);
    }

    private IteratorCloseNode iteratorClose() {
        if (this.iteratorCloseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.context));
        }
        return this.iteratorCloseNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new IteratorCloseWrapperNode(this.context, IteratorCloseWrapperNode.cloneUninitialized(this.blockNode, materializedTags), IteratorCloseWrapperNode.cloneUninitialized(this.iteratorNode, materializedTags));
    }
}

