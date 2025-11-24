
package com.oracle.truffle.js.nodes.module;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.module.ReadImportBindingNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespaceObject;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import java.util.Set;

@ImportStatic(value={Strings.class})
public abstract class ReadImportBindingNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    JavaScriptNode resolutionNode;

    ReadImportBindingNode(JavaScriptNode resolutionNode) {
        this.resolutionNode = resolutionNode;
    }

    public static JavaScriptNode create(JavaScriptNode resolutionNode) {
        return ReadImportBindingNodeGen.create(resolutionNode);
    }

    @Specialization(guards={"frameDescriptor == resolution.getModule().getFrameDescriptor()", "equals(equalNode, bindingName, resolution.getBindingName())"}, limit="1")
    static Object doCached(ExportResolution.Resolved resolution, @Cached(value="resolution.getModule().getFrameDescriptor()") FrameDescriptor frameDescriptor, @Cached(value="resolution.getBindingName()") TruffleString bindingName, @Cached(value="create(frameDescriptor, findImportedSlotIndex(bindingName, resolution.getModule()))") JSReadFrameSlotNode readFrameSlot, @Cached TruffleString.EqualNode equalNode) {
        JSModuleRecord module = resolution.getModule();
        assert (module.getStatus().compareTo(JSModuleRecord.Status.Linked) >= 0) : module.getStatus();
        MaterializedFrame environment = JSFrameUtil.castMaterializedFrame(module.getEnvironment());
        Object value2 = readFrameSlot.execute(environment);
        assert (value2 != null);
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(replaces={"doCached"})
    final Object doUncached(ExportResolution.Resolved resolution) {
        JSModuleRecord module = resolution.getModule();
        assert (module.getStatus().compareTo(JSModuleRecord.Status.Linked) >= 0) : module.getStatus();
        TruffleString bindingName = resolution.getBindingName();
        FrameDescriptor moduleFrameDescriptor = module.getFrameDescriptor();
        int slotIndex = ReadImportBindingNode.findImportedSlotIndex(bindingName, module);
        boolean hasTemporalDeadZone = JSFrameUtil.hasTemporalDeadZone(moduleFrameDescriptor, slotIndex);
        MaterializedFrame environment = JSFrameUtil.castMaterializedFrame(module.getEnvironment());
        if (hasTemporalDeadZone && environment.getTag(slotIndex) == FrameSlotKind.Illegal.tag) {
            throw Errors.createReferenceErrorNotDefined(bindingName, this);
        }
        Object value2 = environment.getValue(slotIndex);
        assert (value2 != null);
        return value2;
    }

    static int findImportedSlotIndex(TruffleString bindingName, JSModuleRecord module) {
        return JSFrameUtil.findRequiredFrameSlotIndex(module.getFrameDescriptor(), bindingName);
    }

    @Specialization
    static Object doNamespace(JSModuleNamespaceObject namespace) {
        return namespace;
    }

    @Fallback
    static Object doUnresolved(Object uninitialized) {
        throw Errors.createReferenceError("Unresolved import");
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ReadImportBindingNode.create(ReadImportBindingNode.cloneUninitialized(this.resolutionNode, materializedTags));
    }
}

