
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.GlobalObjectNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

public class GlobalConstantNode
extends JSTargetableNode
implements ReadNode {
    @Node.Child
    private GlobalObjectNode globalObjectNode = GlobalObjectNode.create();
    @Node.Child
    private JSConstantNode constantNode;
    private final TruffleString propertyName;

    protected GlobalConstantNode(TruffleString propertyName, JSConstantNode constantNode) {
        this.constantNode = constantNode;
        this.propertyName = propertyName;
    }

    public static JSTargetableNode createGlobalConstant(TruffleString propertyName, Object value2) {
        return new GlobalConstantNode(propertyName, JSConstantNode.create(value2));
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ReadPropertyTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("key", this.propertyName);
    }

    @Override
    public Object executeWithTarget(VirtualFrame frame, Object target) {
        return this.execute(frame);
    }

    @Override
    public Object evaluateTarget(VirtualFrame frame) {
        return this.globalObjectNode.executeDynamicObject();
    }

    @Override
    public JavaScriptNode getTarget() {
        return this.globalObjectNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.constantNode.execute(frame);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return this.constantNode.executeInt(frame);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return this.constantNode.executeDouble(frame);
    }

    public Object getValue() {
        return this.constantNode.getValue();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return String.format("%s(property=%s, value=%s)", super.toString(), this.propertyName, this.constantNode.getValue());
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new GlobalConstantNode(this.propertyName, GlobalConstantNode.cloneUninitialized(this.constantNode, materializedTags));
    }

    static final class DirNameNode
    extends JSConstantNode {
        DirNameNode() {
        }

        @Override
        public TruffleString execute(VirtualFrame frame) {
            return this.getDirName();
        }

        @CompilerDirectives.TruffleBoundary
        private TruffleString getDirName() {
            TruffleFile filePath;
            TruffleFile parentPath;
            Object dirPath;
            TruffleLanguage.Env env;
            String fileSeparator;
            Source source = this.getEncapsulatingSourceSection().getSource();
            if (source.isInternal() || source.isInteractive()) {
                return Strings.EMPTY_STRING;
            }
            String path = source.getPath();
            String string = path = path == null ? source.getName() : path;
            if (path.startsWith("file:")) {
                path = path.substring("file:".length());
            }
            if ("\\".equals(fileSeparator = (env = this.getRealm().getEnv()).getFileNameSeparator()) && path.startsWith("/")) {
                path = path.substring(1);
            }
            Object object = dirPath = (parentPath = (filePath = env.getPublicTruffleFile(path).getAbsoluteFile()).getParent()) == null ? "" : parentPath.getPath();
            if (!((String)dirPath).isEmpty() && ((String)dirPath).charAt(((String)dirPath).length() - 1) != '/' && !fileSeparator.equals(String.valueOf(((String)dirPath).charAt(((String)dirPath).length() - 1)))) {
                dirPath = (String)dirPath + fileSeparator;
            }
            return Strings.fromJavaString((String)dirPath);
        }

        @Override
        public Object getValue() {
            return this.getDirName();
        }
    }

    static final class FileNameNode
    extends JSConstantNode {
        @CompilerDirectives.CompilationFinal
        private TruffleString filename = null;

        FileNameNode() {
        }

        @Override
        public TruffleString execute(VirtualFrame frame) {
            return this.getFileName();
        }

        private TruffleString getFileName() {
            if (this.filename == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Source source = this.getEncapsulatingSourceSection().getSource();
                String path = source.getPath();
                this.filename = Strings.fromJavaString(path == null ? source.getName() : path);
            }
            return this.filename;
        }

        @Override
        public TruffleString getValue() {
            return this.getFileName();
        }
    }

    static final class LineNumberNode
    extends JSConstantNode {
        LineNumberNode() {
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.getLineNumber();
        }

        @Override
        public int executeInt(VirtualFrame frame) {
            return this.getLineNumber();
        }

        @Override
        public double executeDouble(VirtualFrame frame) {
            return this.getLineNumber();
        }

        @CompilerDirectives.TruffleBoundary
        private int getLineNumber() {
            return this.getEncapsulatingSourceSection().getStartLine();
        }

        @Override
        public Object getValue() {
            return this.getLineNumber();
        }
    }
}

