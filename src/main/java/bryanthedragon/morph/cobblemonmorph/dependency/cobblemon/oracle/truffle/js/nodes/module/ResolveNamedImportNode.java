
package com.oracle.truffle.js.nodes.module;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import java.util.Set;

public class ResolveNamedImportNode
extends StatementNode {
    private final JSContext context;
    private final Module.ModuleRequest moduleRequest;
    private final TruffleString importName;
    @Node.Child
    private JavaScriptNode moduleNode;
    @Node.Child
    private JSWriteFrameSlotNode writeLocalNode;
    private final ValueProfile resolutionProfile = ValueProfile.createClassProfile();

    ResolveNamedImportNode(JSContext context, JavaScriptNode moduleNode, Module.ModuleRequest moduleRequest, TruffleString importName, JSWriteFrameSlotNode writeLocalNode) {
        this.context = context;
        this.moduleRequest = moduleRequest;
        this.moduleNode = moduleNode;
        this.importName = importName;
        this.writeLocalNode = writeLocalNode;
    }

    public static StatementNode create(JSContext context, JavaScriptNode moduleNode, Module.ModuleRequest moduleRequest, TruffleString importName, JSWriteFrameSlotNode writeLocalNode) {
        return new ResolveNamedImportNode(context, moduleNode, moduleRequest, importName, writeLocalNode);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        JSModuleRecord importedModule;
        JSModuleRecord referencingScriptOrModule = (JSModuleRecord)this.moduleNode.execute(frame);
        Evaluator evaluator = this.context.getEvaluator();
        ExportResolution resolution = this.resolutionProfile.profile(evaluator.resolveExport(importedModule = evaluator.hostResolveImportedModule(this.context, referencingScriptOrModule, this.moduleRequest), this.importName));
        if (resolution.isNull() || resolution.isAmbiguous()) {
            String message = "The requested module '%s' does not provide an export named '%s'";
            throw Errors.createSyntaxErrorFormat(message, this, this.moduleRequest.getSpecifier(), this.importName);
        }
        Object resolutionOrNamespace = resolution.isNamespace() ? evaluator.getModuleNamespace(resolution.getModule()) : resolution;
        this.writeLocalNode.executeWrite(frame, resolutionOrNamespace);
        return EMPTY;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ResolveNamedImportNode.create(this.context, ResolveNamedImportNode.cloneUninitialized(this.moduleNode, materializedTags), this.moduleRequest, this.importName, ResolveNamedImportNode.cloneUninitialized(this.writeLocalNode, materializedTags));
    }
}

