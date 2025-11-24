
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.debug.DebugException;
import com.oracle.truffle.api.debug.DebugScope;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

public final class DebugStackTraceElement {
    private final DebuggerSession session;
    final TruffleStackTraceElement traceElement;
    private final StackTraceElement hostTraceElement;
    private StackTraceElement stackTraceElement;

    DebugStackTraceElement(DebuggerSession session, TruffleStackTraceElement traceElement) {
        this.session = session;
        this.traceElement = traceElement;
        this.hostTraceElement = null;
    }

    DebugStackTraceElement(DebuggerSession session, StackTraceElement hostTraceElement) {
        this.session = session;
        this.traceElement = null;
        this.hostTraceElement = hostTraceElement;
    }

    public boolean isInternal() {
        if (this.isHost()) {
            return false;
        }
        RootNode root = this.findCurrentRoot();
        if (root == null) {
            return true;
        }
        return root.isInternal();
    }

    public boolean isHost() {
        return this.hostTraceElement != null;
    }

    public StackTraceElement getHostTraceElement() {
        return this.hostTraceElement;
    }

    public String getName() {
        if (this.hostTraceElement != null) {
            return this.hostTraceElement.getClassName() + "." + this.hostTraceElement.getMethodName();
        }
        try {
            Object guestObject = this.traceElement.getGuestObject();
            if (InteropLibrary.getUncached().hasExecutableName(guestObject)) {
                try {
                    return InteropLibrary.getUncached().asString(InteropLibrary.getUncached().getExecutableName(guestObject));
                }
                catch (UnsupportedMessageException ex) {
                    throw CompilerDirectives.shouldNotReachHere(ex);
                }
            }
            return null;
        }
        catch (ThreadDeath td) {
            throw td;
        }
        catch (Throwable ex) {
            RootNode root = this.findCurrentRoot();
            LanguageInfo languageInfo = root != null ? root.getLanguageInfo() : null;
            throw DebugException.create(this.session, ex, languageInfo);
        }
    }

    public SourceSection getSourceSection() {
        if (this.isHost()) {
            return null;
        }
        Node node = this.traceElement.getLocation();
        if (node != null) {
            return this.session.resolveSection(node);
        }
        return null;
    }

    public DebugScope getScope() {
        MaterializedFrame frame;
        if (this.isHost()) {
            return null;
        }
        Node node = this.traceElement.getLocation();
        if (node == null) {
            return null;
        }
        RootNode root = node.getRootNode();
        if (root.getLanguageInfo() == null) {
            return null;
        }
        Frame elementFrame = this.traceElement.getFrame();
        MaterializedFrame materializedFrame = frame = elementFrame != null ? elementFrame.materialize() : null;
        if (!NodeLibrary.getUncached().hasScope(node, frame)) {
            return null;
        }
        try {
            Object scope = NodeLibrary.getUncached().getScope(node, frame, true);
            return new DebugScope(scope, this.session, null, node, frame, root);
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    private LanguageInfo getLanguage() {
        if (this.isHost()) {
            return null;
        }
        RootNode root = this.findCurrentRoot();
        LanguageInfo language = root != null ? root.getLanguageInfo() : null;
        return language;
    }

    private RootNode findCurrentRoot() {
        if (this.isHost()) {
            return null;
        }
        Node node = this.traceElement.getLocation();
        if (node != null) {
            return node.getRootNode();
        }
        RootCallTarget target = this.traceElement.getTarget();
        return target.getRootNode();
    }

    StackTraceElement toTraceElement() {
        if (this.stackTraceElement == null) {
            if (this.hostTraceElement != null) {
                this.stackTraceElement = this.hostTraceElement;
            } else {
                Object methodName;
                String declaringClass;
                block10: {
                    LanguageInfo language = this.getLanguage();
                    declaringClass = language != null ? "<" + language.getId() + ">" : "<unknown>";
                    try {
                        Object guestObject = this.traceElement.getGuestObject();
                        if (InteropLibrary.getUncached().hasExecutableName(guestObject)) {
                            try {
                                methodName = InteropLibrary.getUncached().asString(InteropLibrary.getUncached().getExecutableName(guestObject));
                                break block10;
                            }
                            catch (UnsupportedMessageException ex) {
                                throw CompilerDirectives.shouldNotReachHere(ex);
                            }
                        }
                        methodName = "";
                    }
                    catch (AssertionError | ThreadDeath td) {
                        throw td;
                    }
                    catch (Throwable ex) {
                        if (InteropLibrary.getUncached().isException(ex)) {
                            methodName = "Error in generating method name: " + ex.getLocalizedMessage();
                        }
                        throw ex;
                    }
                }
                SourceSection sourceLocation = this.getSourceSection();
                String fileName = sourceLocation != null ? sourceLocation.getSource().getName() : "Unknown";
                int startLine = sourceLocation != null ? sourceLocation.getStartLine() : -1;
                this.stackTraceElement = new StackTraceElement(declaringClass, (String)methodName, fileName, startLine);
            }
        }
        return this.stackTraceElement;
    }
}

