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

   @Override
   public String toString() {
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

      String rootName = this.getRootName();
      if (rootName != null) {
         b.append("rootName=").append(rootName).append(", ");
      }

      List<Value> inputValues = this.getInputValues();
      if (inputValues != null) {
         b.append("inputValues=").append(inputValues).append(", ");
      }

      Value returnValue = this.getReturnValue();
      if (returnValue != null) {
         b.append("returnValue=").append(returnValue).append(", ");
      }

      PolyglotException exception = this.getException();
      if (exception != null) {
         b.append("exception=").append(exception).append(", ");
      }

      b.append("location=").append(this.getLocation());
      b.append("]");
      return b.toString();
   }
}
