
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import java.util.List;

public abstract class JavaScriptRootNode
extends RootNode {
    private static final FrameDescriptor SHARED_EMPTY_FRAMEDESCRIPTOR = FrameDescriptor.newBuilder(0).build();
    public static final FrameDescriptor MODULE_DUMMY_FRAMEDESCRIPTOR = FrameDescriptor.newBuilder(0).build();
    private final SourceSection sourceSection;

    protected JavaScriptRootNode() {
        this(null, null, null);
    }

    protected JavaScriptRootNode(JavaScriptLanguage lang, SourceSection sourceSection, FrameDescriptor frameDescriptor) {
        super(lang, JavaScriptRootNode.substituteNullWithSharedEmptyFrameDescriptor(frameDescriptor));
        this.sourceSection = sourceSection == null ? JSFunction.BUILTIN_SOURCE_SECTION : sourceSection;
    }

    private static FrameDescriptor substituteNullWithSharedEmptyFrameDescriptor(FrameDescriptor frameDescriptor) {
        return frameDescriptor == null ? SHARED_EMPTY_FRAMEDESCRIPTOR : frameDescriptor;
    }

    @Override
    public SourceSection getSourceSection() {
        return this.sourceSection;
    }

    @Override
    public boolean isInternal() {
        SourceSection sc = this.getSourceSection();
        if (sc != null) {
            return sc.getSource().isInternal();
        }
        return false;
    }

    public boolean isFunction() {
        return false;
    }

    public boolean isResumption() {
        return false;
    }

    @Override
    public boolean isCaptureFramesForTrace() {
        return this.isFunction() || this.isResumption();
    }

    @Override
    protected boolean countsTowardsStackTraceLimit() {
        return false;
    }

    public static List<TruffleStackTraceElement> findAsynchronousFrames(JavaScriptRootNode rootNode, Frame frame) {
        return rootNode.findAsynchronousFrames(frame);
    }

    protected final JSRealm getRealm() {
        return JSRealm.get(this);
    }

    protected final JavaScriptLanguage getLanguage() {
        return JavaScriptLanguage.get(this);
    }
}

