
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.GlobalConstantNode;
import com.oracle.truffle.js.nodes.access.GlobalObjectNode;
import com.oracle.truffle.js.nodes.access.GlobalScopeNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

public class GlobalPropertyNode
extends JSTargetableNode
implements ReadNode {
    private final TruffleString propertyName;
    private final JSContext context;
    @Node.Child
    private PropertyGetNode cache;
    @Node.Child
    private JavaScriptNode globalObjectNode;

    protected GlobalPropertyNode(JSContext context, TruffleString propertyName, JavaScriptNode globalObjectNode) {
        this.propertyName = propertyName;
        this.context = context;
        this.globalObjectNode = globalObjectNode;
        this.cache = PropertyGetNode.create(propertyName, true, context);
    }

    public static JSTargetableNode createPropertyNode(JSContext ctx, TruffleString propertyName) {
        if (ctx != null && ctx.isOptionNashornCompatibilityMode()) {
            if (Strings.equals(propertyName, Strings.GLOBAL__LINE__)) {
                return new GlobalConstantNode(propertyName, new GlobalConstantNode.LineNumberNode());
            }
            if (Strings.equals(propertyName, Strings.GLOBAL__FILE__)) {
                return new GlobalConstantNode(propertyName, new GlobalConstantNode.FileNameNode());
            }
            if (Strings.equals(propertyName, Strings.GLOBAL__DIR__)) {
                return new GlobalConstantNode(propertyName, new GlobalConstantNode.DirNameNode());
            }
        }
        return new GlobalPropertyNode(ctx, propertyName, null);
    }

    public static JSTargetableNode createLexicalGlobal(JSContext ctx, TruffleString propertyName, boolean checkTDZ) {
        JavaScriptNode globalScope = checkTDZ ? GlobalScopeNode.createWithTDZCheck(ctx, propertyName) : GlobalScopeNode.create(ctx);
        return new GlobalPropertyNode(ctx, propertyName, globalScope);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if ((tag == JSTags.ReadVariableTag.class || tag == StandardTags.ReadVariableTag.class) && this.isScopeAccess()) {
            return true;
        }
        if (tag == JSTags.ReadPropertyTag.class && !this.isScopeAccess()) {
            return true;
        }
        return super.hasTag(tag);
    }

    private boolean isScopeAccess() {
        return this.globalObjectNode instanceof GlobalScopeNode;
    }

    @Override
    public Object getNodeObject() {
        if (this.isScopeAccess()) {
            NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.getPropertyKey());
            descriptor.addProperty("readVariableName", this.getPropertyKey());
            return descriptor;
        }
        return JSTags.createNodeObjectDescriptor("key", this.getPropertyKey());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.ReadPropertyTag.class) && !this.isScopeAccess() && this.globalObjectNode == null) {
            GlobalObjectNode global = GlobalObjectNode.create();
            GlobalPropertyNode materialized = new GlobalPropertyNode(this.context, this.propertyName, global);
            if (this.cache != null && this.cache.isMethod()) {
                materialized.cache.setMethod();
            }
            GlobalPropertyNode.transferSourceSectionAndTags(this, materialized);
            GlobalPropertyNode.transferSourceSection(this, global);
            return materialized;
        }
        return this;
    }

    @Override
    public Object executeWithTarget(VirtualFrame frame, Object target) {
        return this.cache.getValue(target);
    }

    @Override
    public final Object evaluateTarget(VirtualFrame frame) {
        if (this.globalObjectNode != null) {
            return this.globalObjectNode.execute(frame);
        }
        return this.getRealm().getGlobalObject();
    }

    @Override
    public JavaScriptNode getTarget() {
        return this.getGlobalObjectNode();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.cache.getValue(this.evaluateTarget(frame));
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return this.cache.getValueInt(this.evaluateTarget(frame));
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return this.cache.getValueDouble(this.evaluateTarget(frame));
    }

    public Object getPropertyKey() {
        return this.propertyName;
    }

    public void setMethod() {
        this.cache.setMethod();
    }

    public void setPropertyAssumptionCheckEnabled(boolean enabled) {
        this.cache.setPropertyAssumptionCheckEnabled(enabled);
    }

    private JavaScriptNode getGlobalObjectNode() {
        if (this.globalObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.globalObjectNode = this.insert(GlobalObjectNode.create());
        }
        return this.globalObjectNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        GlobalPropertyNode copy = new GlobalPropertyNode(this.context, this.propertyName, GlobalPropertyNode.cloneUninitialized(this.globalObjectNode, materializedTags));
        if (this.cache != null && this.cache.isMethod()) {
            copy.cache.setMethod();
        }
        return copy;
    }

    @Override
    public String expressionToString() {
        return this.getPropertyKey().toString();
    }
}

