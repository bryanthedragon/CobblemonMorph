package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName = "try-catch")
public class TryCatchNode extends StatementNode implements ResumableNode.WithObjectState {
   @Node.Child
   private JavaScriptNode tryBlock;
   @Node.Child
   private JavaScriptNode catchBlock;
   @Node.Child
   private JSWriteFrameSlotNode writeErrorVar;
   @Node.Child
   private BlockScopeNode blockScope;
   @Node.Child
   private JavaScriptNode destructuring;
   @Node.Child
   private JavaScriptNode conditionExpression;
   @Node.Child
   private TryCatchNode.GetErrorObjectNode getErrorObjectNode;
   private final JSContext context;

   protected TryCatchNode(
      JSContext context,
      JavaScriptNode tryBlock,
      JavaScriptNode catchBlock,
      JSWriteFrameSlotNode writeErrorVar,
      BlockScopeNode blockScope,
      JavaScriptNode destructuring,
      JavaScriptNode conditionExpression
   ) {
      this.context = context;
      this.tryBlock = tryBlock;
      this.writeErrorVar = writeErrorVar;
      this.catchBlock = catchBlock;
      this.blockScope = blockScope;
      this.destructuring = destructuring;
      this.conditionExpression = conditionExpression == null ? null : JSToBooleanUnaryNode.create(conditionExpression);
   }

   public static TryCatchNode create(
      JSContext context,
      JavaScriptNode tryBlock,
      JavaScriptNode catchBlock,
      JSWriteFrameSlotNode writeErrorVar,
      BlockScopeNode blockScope,
      JavaScriptNode destructuring,
      JavaScriptNode conditionExpression
   ) {
      return new TryCatchNode(context, tryBlock, catchBlock, writeErrorVar, blockScope, destructuring, conditionExpression);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag != JSTags.ControlFlowRootTag.class && tag != StandardTags.TryBlockTag.class ? super.hasTag(tag) : true;
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.ExceptionHandler.name());
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         this.context,
         cloneUninitialized(this.tryBlock, materializedTags),
         cloneUninitialized(this.catchBlock, materializedTags),
         cloneUninitialized(this.writeErrorVar, materializedTags),
         cloneUninitialized(this.blockScope, materializedTags),
         cloneUninitialized(this.destructuring, materializedTags),
         cloneUninitialized(this.conditionExpression, materializedTags)
      );
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.tryBlock.isResultAlwaysOfType(clazz) && this.catchBlock.isResultAlwaysOfType(clazz);
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      Throwable throwable;
      try {
         return this.tryBlock.execute(frame);
      } catch (ControlFlowException var4) {
         throw var4;
      } catch (AbstractTruffleException var5) {
         throwable = var5;
      } catch (StackOverflowError var6) {
         throwable = var6;
      }

      return this.executeCatch(frame, throwable);
   }

   @Override
   public final void executeVoid(VirtualFrame frame) {
      Throwable throwable;
      try {
         this.tryBlock.executeVoid(frame);
         return;
      } catch (ControlFlowException var4) {
         throw var4;
      } catch (AbstractTruffleException var5) {
         throwable = var5;
      } catch (StackOverflowError var6) {
         throwable = var6;
      }

      this.executeCatch(frame, throwable);
   }

   private Object executeCatch(VirtualFrame frame, Throwable ex) {
      if (this.blockScope != null) {
         this.blockScope.appendScopeFrame(frame);
      }

      Object var3;
      try {
         if (!this.prepareCatch(frame, ex)) {
            throw JSRuntime.rethrow(ex);
         }

         var3 = this.catchBlock.execute(frame);
      } finally {
         if (this.blockScope != null) {
            this.blockScope.exitScope(frame);
         }
      }

      return var3;
   }

   private boolean prepareCatch(VirtualFrame frame, Throwable ex) {
      if (this.writeErrorVar != null) {
         if (this.getErrorObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
         }

         Object exceptionObject = this.getErrorObjectNode.execute(ex);
         this.writeErrorVar.executeWrite(frame, exceptionObject);
         if (this.destructuring != null) {
            this.destructuring.execute(frame);
         }
      }

      return this.conditionExpression == null || executeConditionAsBoolean(frame, this.conditionExpression);
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      Object state = this.getStateAndReset(frame, stateSlot);
      if (state == Undefined.instance) {
         Throwable throwable;
         try {
            return this.tryBlock.execute(frame);
         } catch (ControlFlowException var12) {
            throw var12;
         } catch (AbstractTruffleException var13) {
            throwable = var13;
         } catch (StackOverflowError var14) {
            throwable = var14;
         }

         if (this.blockScope != null) {
            this.blockScope.appendScopeFrame(frame);
         }

         if (!this.prepareCatch(frame, throwable)) {
            throw JSRuntime.rethrow(throwable);
         }
      } else if (this.blockScope != null) {
         this.blockScope.setBlockScope(frame, state);
      }

      boolean yield = false;

      Object e;
      try {
         e = this.catchBlock.execute(frame);
      } catch (YieldException var15) {
         yield = true;
         if (this.blockScope == null) {
            this.setState(frame, stateSlot, 1);
         } else {
            state = this.blockScope.getBlockScope(frame);

            assert state != Undefined.instance;

            this.setState(frame, stateSlot, state);
         }

         throw var15;
      } finally {
         if (this.blockScope != null) {
            this.blockScope.exitScope(frame, yield);
         }
      }

      return e;
   }

   public abstract static class GetErrorObjectNode extends JavaScriptBaseNode {
      @Node.Child
      private InitErrorObjectNode initErrorObjectNode;
      private final JSContext context;

      protected GetErrorObjectNode(JSContext context) {
         this.context = context;
         this.initErrorObjectNode = InitErrorObjectNode.create(context, context.isOptionNashornCompatibilityMode());
      }

      public static TryCatchNode.GetErrorObjectNode create(JSContext context) {
         return TryCatchNodeFactory.GetErrorObjectNodeGen.create(context);
      }

      public abstract Object execute(Throwable ex);

      @Specialization
      final Object doJSException(JSException ex) {
         TruffleStackTrace.fillIn(ex);
         return this.getOrCreateErrorFromJSException(ex);
      }

      @Specialization
      static Object doUserScriptException(UserScriptException ex) {
         return ex.getErrorObject();
      }

      @Specialization
      final Object doStackOverflowError(StackOverflowError ex) {
         JSException rangeError = Errors.createRangeErrorStackOverflow(ex, this);
         return this.getOrCreateErrorFromJSException(rangeError);
      }

      @Fallback
      static Object doOther(Throwable ex) {
         assert !(ex instanceof GraalJSException) && ex instanceof AbstractTruffleException : ex;

         return ex;
      }

      private Object getOrCreateErrorFromJSException(JSException exception) {
         JSDynamicObject errorObj = exception.getErrorObjectLazy();
         if (errorObj == null) {
            errorObj = this.createErrorFromJSException(exception);
         }

         return errorObj;
      }

      private JSErrorObject createErrorFromJSException(JSException exception) {
         JSRealm errorRealm = exception.getRealm();
         String message = exception.getRawMessage();

         assert message != null;

         JSErrorObject errorObj = newErrorObject(this.context, errorRealm, exception.getErrorType());
         this.initErrorObjectNode.execute(errorObj, exception, Strings.fromJavaString(message));
         exception.setErrorObject(errorObj);
         return errorObj;
      }

      @CompilerDirectives.TruffleBoundary
      private static JSErrorObject newErrorObject(JSContext context, JSRealm realm, JSErrorType errorType) {
         return JSError.createErrorObject(context, realm, errorType);
      }
   }
}
