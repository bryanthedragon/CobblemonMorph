package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class EvalNode extends JavaScriptNode {
   private final JSContext context;
   @Node.Child
   @Executed
   protected JavaScriptNode functionNode;
   @Node.Child
   protected AbstractFunctionArgumentsNode arguments;
   @Node.Child
   protected EvalNode.DirectEvalNode directEvalNode;

   protected EvalNode(JSContext context, JavaScriptNode function, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, int blockScopeSlot) {
      this(context, function, JSFunctionArgumentsNode.create(context, args), EvalNode.DirectEvalNode.create(context, thisObject, env, blockScopeSlot));
   }

   protected EvalNode(JSContext context, JavaScriptNode functionNode, AbstractFunctionArgumentsNode arguments, EvalNode.DirectEvalNode directEvalNode) {
      this.context = context;
      this.functionNode = functionNode;
      this.arguments = arguments;
      this.directEvalNode = directEvalNode;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.EvalCallTag.class ? true : super.hasTag(tag);
   }

   @Specialization(guards = "!isEvalOverridden(evalFunction)")
   protected Object evalNotOverridden(VirtualFrame frame, Object evalFunction) {
      int argCount = this.arguments.getCount(frame);
      Object[] args = new Object[argCount];
      args = this.arguments.executeFillObjectArray(frame, args, 0);
      Object source = args.length == 0 ? Undefined.instance : args[0];
      return this.directEvalNode.executeWithSource(frame, source);
   }

   @Specialization(guards = "isEvalOverridden(evalFunction)")
   protected Object evalOverridden(VirtualFrame frame, Object evalFunction, @Cached("createCall()") JSFunctionCallNode redirectCall) {
      int argCount = this.arguments.getCount(frame);
      Object[] args = JSArguments.createInitial(Undefined.instance, evalFunction, argCount);
      args = this.arguments.executeFillObjectArray(frame, args, 2);
      return redirectCall.executeCall(args);
   }

   protected final boolean isEvalOverridden(Object function) {
      return function != this.getRealm().getEvalFunctionObject();
   }

   public static EvalNode create(
      JSContext context, JavaScriptNode functionNode, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, JSFrameSlot blockScopeSlot
   ) {
      return EvalNodeGen.create(context, functionNode, args, thisObject, env, blockScopeSlot != null ? blockScopeSlot.getIndex() : -1);
   }

   @CompilerDirectives.TruffleBoundary
   private static String formatEvalOrigin(Node callNode, JSContext context) {
      if (callNode == null) {
         return null;
      } else {
         SourceSection sourceSection = callNode.getEncapsulatingSourceSection();
         if (sourceSection == null) {
            return null;
         } else {
            String sourceName = sourceSection.getSource().getName();
            String callerName = callNode.getRootNode().getName();
            if (callerName == null || callerName.startsWith(":")) {
               callerName = Strings.toJavaString(JSError.getAnonymousFunctionNameStackTrace(context));
            }

            return sourceName.startsWith("eval at ")
               ? "eval at " + callerName + " (" + sourceName + ")"
               : "eval at " + callerName + " (" + sourceName + ":" + sourceSection.getStartLine() + ":" + sourceSection.getStartColumn() + ")";
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static String findAndFormatEvalOrigin(Node evalNode, JSContext context) {
      String evalOrigin = formatEvalOrigin(evalNode, context);
      return evalOrigin != null ? evalOrigin : Truffle.getRuntime().iterateFrames(frameInstance -> formatEvalOrigin(frameInstance.getCallNode(), context));
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return EvalNodeGen.create(
         this.context,
         cloneUninitialized(this.functionNode, materializedTags),
         AbstractFunctionArgumentsNode.cloneUninitialized(this.arguments, materializedTags),
         this.directEvalNode.copyUninitialized(materializedTags)
      );
   }

   protected abstract static class DirectEvalNode extends JavaScriptBaseNode {
      private final JSContext context;
      private final Object currEnv;
      @Node.Child
      private JavaScriptNode thisNode;
      @Node.Child
      private IndirectCallNode callNode;
      private final int blockScopeSlot;

      protected DirectEvalNode(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
         assert currEnv != null;

         this.context = context;
         this.currEnv = currEnv;
         this.thisNode = thisNode;
         this.callNode = IndirectCallNode.create();
         this.blockScopeSlot = blockScopeSlot;
      }

      protected static EvalNode.DirectEvalNode create(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
         return EvalNodeGen.DirectEvalNodeGen.create(context, thisNode, currEnv, blockScopeSlot);
      }

      public abstract Object executeWithSource(VirtualFrame frame, Object source);

      @Specialization
      protected int directEvalInt(int source) {
         return source;
      }

      @Specialization
      protected SafeInteger directEvalSafeInteger(SafeInteger source) {
         return source;
      }

      @Specialization
      protected long directEvalLong(long source) {
         return source;
      }

      @Specialization
      protected double directEvalDouble(double source) {
         return source;
      }

      @Specialization
      protected boolean directEvalBoolean(boolean source) {
         return source;
      }

      @Specialization
      protected Symbol directEvalSymbol(Symbol source) {
         return source;
      }

      @Specialization
      protected BigInt directEvalBigInt(BigInt source) {
         return source;
      }

      @Specialization
      protected JSDynamicObject directEvalJSType(JSDynamicObject source) {
         return source;
      }

      @Specialization
      protected Object directEvalCharSequence(VirtualFrame frame, TruffleString source) {
         return this.directEvalImpl(frame, source);
      }

      private Object directEvalImpl(VirtualFrame frame, TruffleString sourceCode) {
         Source source = this.sourceFromString(sourceCode);
         JSRealm realm = this.getRealm();
         Object evalThis = this.thisNode.execute(frame);
         ScriptNode script = this.context.getEvaluator().parseDirectEval(this.context, this.getParent(), source, this.currEnv);
         MaterializedFrame blockScopeFrame;
         if (this.blockScopeSlot >= 0) {
            Object maybeFrame = frame.getObject(this.blockScopeSlot);
            blockScopeFrame = JSFrameUtil.castMaterializedFrame(maybeFrame);
         } else {
            blockScopeFrame = frame.materialize();
         }

         return script.runEval(this.callNode, realm, evalThis, blockScopeFrame);
      }

      @Specialization(guards = "isForeignObject(sourceCode)", limit = "3")
      protected Object directEvalForeignObject(VirtualFrame frame, Object sourceCode, @CachedLibrary("sourceCode") InteropLibrary interop) {
         if (interop.isString(sourceCode)) {
            try {
               return this.directEvalImpl(frame, interop.asTruffleString(sourceCode));
            } catch (UnsupportedMessageException var5) {
               throw Errors.createTypeErrorInteropException(sourceCode, var5, "asString", this);
            }
         } else {
            return sourceCode;
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Source sourceFromString(TruffleString sourceCode) {
         String evalSourceName = null;
         if (this.context.isOptionV8CompatibilityMode()) {
            evalSourceName = EvalNode.formatEvalOrigin(this, this.context);
         }

         if (evalSourceName == null) {
            evalSourceName = "<eval>";
         }

         return Source.newBuilder("js", Strings.toJavaString(sourceCode), evalSourceName).build();
      }

      protected EvalNode.DirectEvalNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return create(this.context, JavaScriptNode.cloneUninitialized(this.thisNode, materializedTags), this.currEnv, this.blockScopeSlot);
      }
   }
}
