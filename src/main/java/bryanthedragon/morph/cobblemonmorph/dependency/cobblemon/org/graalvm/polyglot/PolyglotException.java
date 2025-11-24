
package org.graalvm.polyglot;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class PolyglotException
extends RuntimeException {
    final AbstractPolyglotImpl.AbstractExceptionDispatch dispatch;
    final Object impl;

    PolyglotException(String message, AbstractPolyglotImpl.AbstractExceptionDispatch dispatch, Object receiver) {
        super(message);
        this.dispatch = dispatch;
        this.impl = receiver;
        dispatch.onCreate(receiver, this);
        super.setStackTrace(this.getStackTrace());
    }

    @Override
    public void printStackTrace() {
        this.dispatch.printStackTrace(this.impl, System.err);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        this.dispatch.printStackTrace(this.impl, s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        this.dispatch.printStackTrace(this.impl, s);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return this.dispatch.getStackTrace(this.impl);
    }

    @Override
    public String getMessage() {
        return this.dispatch.getMessage(this.impl);
    }

    public SourceSection getSourceLocation() {
        return this.dispatch.getSourceLocation(this.impl);
    }

    public boolean equals(Object obj) {
        if (obj instanceof PolyglotException) {
            return this.impl.equals(((PolyglotException)obj).impl);
        }
        return false;
    }

    public int hashCode() {
        return this.impl.hashCode();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        for (int i = 0; i < stackTrace.length; ++i) {
            if (stackTrace[i] != null) continue;
            throw new NullPointerException("stackTrace[" + i + "]");
        }
    }

    public Iterable<StackFrame> getPolyglotStackTrace() {
        return this.dispatch.getPolyglotStackTrace(this.impl);
    }

    public boolean isHostException() {
        return this.dispatch.isHostException(this.impl);
    }

    public boolean isGuestException() {
        return !this.dispatch.isHostException(this.impl);
    }

    public Throwable asHostException() {
        return this.dispatch.asHostException(this.impl);
    }

    public boolean isInternalError() {
        return this.dispatch.isInternalError(this.impl);
    }

    public boolean isResourceExhausted() {
        return this.dispatch.isResourceExhausted(this.impl);
    }

    public boolean isCancelled() {
        return this.dispatch.isCancelled(this.impl);
    }

    public boolean isInterrupted() {
        return this.dispatch.isInterrupted(this.impl);
    }

    public boolean isExit() {
        return this.dispatch.isExit(this.impl);
    }

    public boolean isSyntaxError() {
        return this.dispatch.isSyntaxError(this.impl);
    }

    public boolean isIncompleteSource() {
        return this.dispatch.isIncompleteSource(this.impl);
    }

    public Value getGuestObject() {
        return this.dispatch.getGuestObject(this.impl);
    }

    public int getExitStatus() {
        return this.dispatch.getExitStatus(this.impl);
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        throw new NotSerializableException(PolyglotException.class.getSimpleName() + " serialization is not supported.");
    }

    public final class StackFrame {
        final AbstractPolyglotImpl.AbstractStackFrameImpl impl;

        StackFrame(AbstractPolyglotImpl.AbstractStackFrameImpl impl) {
            this.impl = impl;
        }

        public boolean isHostFrame() {
            return this.impl.isHostFrame();
        }

        public boolean isGuestFrame() {
            return !this.impl.isHostFrame();
        }

        public StackTraceElement toHostFrame() {
            return this.impl.toHostFrame();
        }

        public SourceSection getSourceLocation() {
            return this.impl.getSourceLocation();
        }

        public String getRootName() {
            return this.impl.getRootName();
        }

        public Language getLanguage() {
            return this.impl.getLanguage();
        }

        public String toString() {
            return this.impl.toStringImpl(0);
        }
    }
}

