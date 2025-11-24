
package org.graalvm.polyglot.management;

import java.util.List;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class ExecutionEvent {
    final AbstractPolyglotImpl.AbstractExecutionEventDispatch dispatch;
    final Object receiver;

    ExecutionEvent(AbstractPolyglotImpl.AbstractExecutionEventDispatch dispatch, Object receiver) {
        this.dispatch = dispatch;
        this.receiver = receiver;
    }

    public SourceSection getLocation() {
        return this.dispatch.getExecutionEventLocation(this.receiver);
    }

    public String getRootName() {
        return this.dispatch.getExecutionEventRootName(this.receiver);
    }

    public List<Value> getInputValues() {
        return this.dispatch.getExecutionEventInputValues(this.receiver);
    }

    public Value getReturnValue() {
        return this.dispatch.getExecutionEventReturnValue(this.receiver);
    }

    public PolyglotException getException() {
        return this.dispatch.getExecutionEventException(this.receiver);
    }

    public boolean isExpression() {
        return this.dispatch.isExecutionEventExpression(this.receiver);
    }

    public boolean isStatement() {
        return this.dispatch.isExecutionEventStatement(this.receiver);
    }

    public boolean isRoot() {
        return this.dispatch.isExecutionEventRoot(this.receiver);
    }

    public String toString() {
        PolyglotException exception;
        Value returnValue;
        List<Value> inputValues;
        String rootName;
        StringBuilder b = new StringBuilder("ExecutionEvent[");
        if (this.isRoot()) {
            b.append("root").append(", ");
        }
        if (this.isStatement()) {
            b.append("statement").append(", ");
        }
        if (this.isExpression()) {
            b.append("expression").append(", ");
        }
        if ((rootName = this.getRootName()) != null) {
            b.append("rootName=").append(rootName).append(", ");
        }
        if ((inputValues = this.getInputValues()) != null) {
            b.append("inputValues=").append(inputValues).append(", ");
        }
        if ((returnValue = this.getReturnValue()) != null) {
            b.append("returnValue=").append(returnValue).append(", ");
        }
        if ((exception = this.getException()) != null) {
            b.append("exception=").append(exception).append(", ");
        }
        b.append("location=").append(this.getLocation());
        b.append("]");
        return b.toString();
    }
}

