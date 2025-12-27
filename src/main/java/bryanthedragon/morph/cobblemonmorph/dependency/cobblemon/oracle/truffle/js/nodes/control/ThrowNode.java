package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

@NodeInfo(shortName = "throw")
public class ThrowNode extends StatementNode {
   @Node.Child
   private JavaScriptNode exceptionNode;
   @Node.Child
   private PropertyGetNode getErrorNode;
   @Node.Child
   private InteropLibrary interopNode;
   @Node.Child
   private ErrorStackTraceLimitNode stackTraceLimitNode;
   private final JSContext context;
   private final ConditionProfile isError = ConditionProfile.createBinaryProfile();

   protected ThrowNode(JavaScriptNode exceptionNode, JSContext context) {
      this.exceptionNode = exceptionNode;
      this.context = context;
   }

   public static ThrowNode create(JavaScriptNode exceptionNode, JSContext context) {
      return new ThrowNode(exceptionNode, context);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowBranchTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Throw.name());
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object exceptionObject = this.exceptionNode.execute(frame);
      if (this.isError.profile(JSError.isJSError(exceptionObject))) {
         JSDynamicObject jsobject = (JSDynamicObject)exceptionObject;
         if (this.context.isOptionNashornCompatibilityMode()) {
            this.setLineAndColumnNumber(jsobject);
         }

         throw this.getException(jsobject);
      } else {
         this.tryRethrowInterop(exceptionObject);
         throw UserScriptException.create(exceptionObject, this, this.stackTraceLimitNode().executeInt());
      }
   }

   private void tryRethrowInterop(Object exceptionObject) {
      InteropLibrary interop = this.interopNode;
      if (interop == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.interopNode = interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
      }

      if (interop.isException(exceptionObject)) {
         try {
            interop.throwException(exceptionObject);
         } catch (UnsupportedMessageException var4) {
            throw Errors.createTypeErrorInteropException(exceptionObject, var4, "throwException", this);
         }
      }
   }

   private GraalJSException getException(JSDynamicObject errorObj) {
      if (this.getErrorNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getErrorNode = this.insert(PropertyGetNode.create(JSError.EXCEPTION_PROPERTY_NAME, JSObject.getJSContext(errorObj)));
      }

      Object exception = this.getErrorNode.getValue(errorObj);
      return (GraalJSException)exception;
   }

   private ErrorStackTraceLimitNode stackTraceLimitNode() {
      ErrorStackTraceLimitNode node = this.stackTraceLimitNode;
      if (node == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.stackTraceLimitNode = node = this.insert(ErrorStackTraceLimitNode.create());
      }

      return node;
   }

   @CompilerDirectives.TruffleBoundary
   private void setLineAndColumnNumber(JSDynamicObject jsobject) {
      if (this.hasSourceSection()) {
         SourceSection sourceSection = this.getSourceSection();
         JSError.setLineNumber(this.context, jsobject, sourceSection.getStartLine());
         JSError.setColumnNumber(this.context, jsobject, sourceSection.getStartColumn());
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.exceptionNode, materializedTags), this.context);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return true;
   }
}
