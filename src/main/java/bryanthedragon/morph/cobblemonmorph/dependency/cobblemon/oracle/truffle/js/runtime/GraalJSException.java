package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAllNode;
import com.oracle.truffle.js.nodes.promise.PromiseReactionJobNode;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
@ImportStatic(JSConfig.class)
public abstract class GraalJSException extends AbstractTruffleException {
   private static final long serialVersionUID = -6624166672101791072L;
   private static final GraalJSException.JSStackTraceElement[] EMPTY_STACK_TRACE = new GraalJSException.JSStackTraceElement[0];
   private GraalJSException.JSStackTraceElement[] jsStackTrace;
   private Object location;
   private int stackTraceLimit;

   protected GraalJSException(String message, Throwable cause, Node node, int stackTraceLimit) {
      super(message, cause, stackTraceLimit, node);
      this.location = node;
      this.stackTraceLimit = stackTraceLimit;
      this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
   }

   protected GraalJSException(String message, Node node, int stackTraceLimit) {
      super(message, null, stackTraceLimit, node);
      this.location = node;
      this.stackTraceLimit = stackTraceLimit;
      this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
   }

   protected GraalJSException(String message, SourceSection location, int stackTraceLimit) {
      super(message, null, stackTraceLimit, null);
      this.location = location;
      this.stackTraceLimit = stackTraceLimit;
      this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
   }

   protected static <T extends GraalJSException> T fillInStackTrace(T exception, boolean capture, JSDynamicObject skipFramesUpTo, boolean customSkip) {
      exception.fillInStackTrace(capture, skipFramesUpTo, customSkip);
      return exception;
   }

   protected static <T extends GraalJSException> T fillInStackTrace(T exception, boolean capture) {
      exception.fillInStackTrace(capture, Undefined.instance, false);
      return exception;
   }

   protected final GraalJSException fillInStackTrace(boolean capture, JSDynamicObject skipFramesUpTo, boolean customSkip) {
      assert capture || skipFramesUpTo == Undefined.instance;

      assert this.jsStackTrace == (this.stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null);

      if (capture && this.stackTraceLimit > 0) {
         this.jsStackTrace = this.getJSStackTrace(skipFramesUpTo, customSkip);
      }

      return this;
   }

   @ExportMessage
   public boolean hasSourceLocation() {
      if (this.location instanceof SourceSection) {
         return true;
      } else {
         Node locationNode = this.getLocation();
         SourceSection sourceSection = locationNode != null ? locationNode.getEncapsulatingSourceSection() : null;
         return sourceSection != null;
      }
   }

   @ExportMessage(name = "getSourceLocation")
   public SourceSection getSourceLocationInterop() throws UnsupportedMessageException {
      if (this.location instanceof SourceSection) {
         return (SourceSection)this.location;
      } else {
         Node locationNode = this.getLocation();
         SourceSection sourceSection = locationNode != null ? locationNode.getEncapsulatingSourceSection() : null;
         if (sourceSection == null) {
            throw UnsupportedMessageException.create();
         } else {
            return sourceSection;
         }
      }
   }

   public abstract Object getErrorObjectLazy();

   public abstract Object getErrorObject();

   public GraalJSException.JSStackTraceElement[] getJSStackTrace() {
      if (this.jsStackTrace != null) {
         return this.jsStackTrace;
      } else {
         this.jsStackTrace = this.materializeJSStackTrace();
         return this.jsStackTrace;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private GraalJSException.JSStackTraceElement[] materializeJSStackTrace() {
      return this.getJSStackTrace(Undefined.instance, false);
   }

   @CompilerDirectives.TruffleBoundary
   private GraalJSException.JSStackTraceElement[] getJSStackTrace(JSDynamicObject skipUpTo, boolean customSkip) {
      assert this.stackTraceLimit > 0;

      JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
      boolean nashornMode = context.isOptionNashornCompatibilityMode();
      JSDynamicObject skipFramesUpTo = nashornMode ? Undefined.instance : skipUpTo;
      boolean skippingFrames = JSFunction.isJSFunction(skipFramesUpTo);
      if (skippingFrames && customSkip) {
         FunctionRootNode.setOmitFromStackTrace(JSFunction.getFunctionData((JSFunctionObject)skipFramesUpTo));
      }

      List<TruffleStackTraceElement> stackTrace = TruffleStackTrace.getStackTrace(this);
      if (skippingFrames && customSkip) {
         FunctionRootNode.setOmitFromStackTrace(null);
      }

      if (stackTrace == null) {
         return EMPTY_STACK_TRACE;
      } else {
         GraalJSException.FrameVisitorImpl visitor = new GraalJSException.FrameVisitorImpl(
            this.getLocation(), this.stackTraceLimit, skipFramesUpTo, nashornMode
         );
         boolean asyncStackTraces = context.isOptionAsyncStackTraces();
         List<List<TruffleStackTraceElement>> asyncStacks = null;

         for (TruffleStackTraceElement element : stackTrace) {
            if (!visitor.visitFrame(element)) {
               asyncStacks = null;
               break;
            }

            if (asyncStackTraces) {
               List<TruffleStackTraceElement> asyncStack = getAsynchronousStackTrace(element);
               if (asyncStack != null && !asyncStack.isEmpty()) {
                  if (asyncStacks == null) {
                     asyncStacks = new ArrayList<>();
                  }

                  asyncStacks.add(asyncStack);
               }
            }
         }

         if (asyncStacks != null && !asyncStacks.isEmpty()) {
            for (List<TruffleStackTraceElement> asyncStack : asyncStacks) {
               visitor.async = true;

               for (TruffleStackTraceElement element : asyncStack) {
                  if (!visitor.visitFrame(element)) {
                     return visitor.getStackTrace().toArray(EMPTY_STACK_TRACE);
                  }
               }
            }
         }

         return visitor.getStackTrace().toArray(EMPTY_STACK_TRACE);
      }
   }

   private static List<TruffleStackTraceElement> getAsynchronousStackTrace(TruffleStackTraceElement element) {
      if (element.getFrame() == null) {
         return null;
      } else {
         RootNode rootNode = element.getTarget().getRootNode();
         if (rootNode.getLanguageInfo() == null) {
            return null;
         } else if (rootNode instanceof JavaScriptRootNode) {
            return rootNode instanceof PromiseReactionJobNode.PromiseReactionJobRootNode
               ? JavaScriptRootNode.findAsynchronousFrames((JavaScriptRootNode)rootNode, element.getFrame())
               : null;
         } else {
            return TruffleStackTrace.getAsynchronousStackTrace(element.getTarget(), element.getFrame());
         }
      }
   }

   public void setJSStackTrace(GraalJSException.JSStackTraceElement[] jsStackTrace) {
      this.jsStackTrace = jsStackTrace;
   }

   @CompilerDirectives.TruffleBoundary
   public static GraalJSException.JSStackTraceElement[] getJSStackTrace(Node originatingNode) {
      int stackTraceLimit = JavaScriptLanguage.get(originatingNode).getJSContext().getContextOptions().getStackTraceLimit();
      return UserScriptException.createCapture("", originatingNode, stackTraceLimit).getJSStackTrace();
   }

   private static GraalJSException.JSStackTraceElement processJSFrame(
      RootNode rootNode, Node node, Object thisObj, JSFunctionObject functionObj, boolean inStrictMode, boolean inNashornMode, boolean async, int promiseIndex
   ) {
      Node callNode = node;

      while (callNode.getSourceSection() == null) {
         callNode = callNode.getParent();
      }

      SourceSection callNodeSourceSection = callNode.getSourceSection();
      Source source = callNodeSourceSection.getSource();
      TruffleString fileName = getFileName(source);
      TruffleString functionName;
      if (JSFunction.isBuiltin(functionObj)) {
         functionName = JSFunction.getName(functionObj);
      } else if (rootNode instanceof FunctionRootNode) {
         functionName = ((FunctionRootNode)rootNode).getNameTString();
      } else {
         functionName = Strings.fromJavaString(rootNode.getName());
      }

      boolean eval = false;
      if (isEvalSource(source)) {
         functionName = Strings.EVAL;
         eval = true;
      } else if (functionName == null || isInternalFunctionName(functionName)) {
         functionName = Strings.EMPTY_STRING;
      }

      SourceSection targetSourceSection = null;
      if (!inNashornMode && callNode instanceof JavaScriptFunctionCallNode) {
         Node target = ((JavaScriptFunctionCallNode)callNode).getTarget();
         targetSourceSection = target == null ? null : target.getSourceSection();
      }

      boolean global = JSRuntime.isNullOrUndefined(thisObj) && !JSFunction.isStrict(functionObj) || isGlobalObject(thisObj, JSFunction.getRealm(functionObj));
      return new GraalJSException.JSStackTraceElement(
         fileName,
         functionName,
         callNodeSourceSection,
         thisObj,
         functionObj,
         targetSourceSection,
         inStrictMode,
         eval,
         global,
         inNashornMode,
         async,
         promiseIndex
      );
   }

   private static boolean isEvalSource(Source source) {
      return source != null && source.getName().startsWith("eval at ");
   }

   private static boolean isInternalFunctionName(TruffleString functionName) {
      return Strings.length(functionName) >= 1 && Strings.charAt(functionName, 0) == ':';
   }

   private static boolean isGlobalObject(Object object, JSRealm realm) {
      return JSDynamicObject.isJSDynamicObject(object) && realm != null && realm.getGlobalObject() == object;
   }

   private static GraalJSException.JSStackTraceElement processForeignFrame(Node node, boolean strict, boolean inNashornMode, boolean async) {
      RootNode rootNode = node.getRootNode();
      SourceSection sourceSection = rootNode.getSourceSection();
      if (sourceSection == null) {
         return null;
      } else {
         TruffleString fileName = getFileName(sourceSection.getSource());
         TruffleString functionName = Strings.fromJavaString(rootNode.getName());
         Object thisObj = null;
         Object functionObj = null;
         return new GraalJSException.JSStackTraceElement(
            fileName, functionName, sourceSection, thisObj, functionObj, null, strict, false, false, inNashornMode, async, -1
         );
      }
   }

   private static TruffleString getPrimitiveConstructorName(Object thisObj) {
      assert JSRuntime.isJSPrimitive(thisObj);

      if (thisObj instanceof Boolean) {
         return Strings.UC_BOOLEAN;
      } else if (JSRuntime.isNumber(thisObj)) {
         return Strings.UC_NUMBER;
      } else if (Strings.isTString(thisObj)) {
         return Strings.UC_STRING;
      } else {
         return thisObj instanceof Symbol ? Strings.UC_SYMBOL : null;
      }
   }

   private static int sourceSectionOffset(SourceSection callNodeSourceSection, SourceSection targetSourceSection) {
      int offset = 0;
      String code = callNodeSourceSection.getCharacters().toString();
      if (targetSourceSection != null) {
         String targetCode = targetSourceSection.getCharacters().toString();
         int index = code.indexOf(targetCode);
         if (index != -1) {
            index += targetCode.length();
            offset += index;
            code = code.substring(index);
         }
      }

      int index = code.indexOf(40);
      if (index != -1) {
         int i = --index;

         while (i >= 0 && Character.isWhitespace(code.charAt(i))) {
            i--;
         }

         if (i >= 0 && Character.isJavaIdentifierPart(code.charAt(i))) {
            do {
               i--;
            } while (i >= 0 && Character.isJavaIdentifierPart(code.charAt(i)));

            index = i;
         }

         offset += index + 1;
      }

      return offset;
   }

   private static TruffleString getFileName(Source source) {
      return source != null ? Strings.fromJavaString(source.getName()) : Strings.UNKNOWN_FILENAME;
   }

   public void printJSStackTrace() {
      System.err.println(this.getMessage());

      for (GraalJSException.JSStackTraceElement jsste : this.jsStackTrace) {
         System.err.println(jsste);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void printJSStackTrace(Node originatingNode) {
      GraalJSException.JSStackTraceElement[] jsstes = getJSStackTrace(originatingNode);

      for (GraalJSException.JSStackTraceElement jsste : jsstes) {
         System.err.println(jsste);
      }
   }

   @ExportMessage
   public final boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   public final Class<? extends TruffleLanguage<?>> getLanguage() {
      return JavaScriptLanguage.class;
   }

   @ExportMessage
   public final Object toDisplayString(boolean allowSideEffects) {
      return JSRuntime.toDisplayString(this, allowSideEffects);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   public final int identityHashCode(@CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary delegateLib) throws UnsupportedMessageException {
      return delegateLib.identityHashCode(this.getErrorObject());
   }

   private static final class FrameVisitorImpl {
      private static final int STACK_FRAME_SKIP = 0;
      private static final int STACK_FRAME_JS = 1;
      private static final int STACK_FRAME_FOREIGN = 2;
      private final List<GraalJSException.JSStackTraceElement> stackTrace = new ArrayList<>();
      private final Node originatingNode;
      private final int stackTraceLimit;
      private final JSDynamicObject skipFramesUpTo;
      private final boolean inNashornMode;
      private boolean inStrictMode;
      private boolean skippingFrames;
      private boolean first = true;
      boolean async;

      FrameVisitorImpl(Node originatingNode, int stackTraceLimit, JSDynamicObject skipFramesUpTo, boolean nashornMode) {
         this.originatingNode = originatingNode;
         this.stackTraceLimit = stackTraceLimit;
         this.skipFramesUpTo = skipFramesUpTo;
         this.skippingFrames = skipFramesUpTo != Undefined.instance;
         this.inNashornMode = nashornMode;
      }

      private int stackFrameType(Node callNode) {
         if (callNode == null) {
            return 0;
         } else {
            SourceSection sourceSection = callNode.getEncapsulatingSourceSection();
            if (sourceSection == null) {
               return 0;
            } else if (JSFunction.isBuiltinSourceSection(sourceSection)) {
               return this.inNashornMode ? 0 : 1;
            } else if (sourceSection.getSource().isInternal() || !sourceSection.isAvailable()) {
               return 0;
            } else {
               return JSRuntime.isJSRootNode(callNode.getRootNode()) ? 1 : 2;
            }
         }
      }

      private static RootNode rootNode(TruffleStackTraceElement element) {
         CallTarget callTarget = element.getTarget();
         return callTarget instanceof RootCallTarget ? ((RootCallTarget)callTarget).getRootNode() : null;
      }

      public boolean visitFrame(TruffleStackTraceElement element) {
         Node callNode = element.getLocation();
         if (this.first) {
            this.first = false;
            if (JSRuntime.isJSRootNode(rootNode(element))) {
               callNode = this.originatingNode;
            }
         }

         if (callNode == null) {
            callNode = rootNode(element);
         }

         if (callNode != null) {
            switch (this.stackFrameType(callNode)) {
               case 1:
                  RootNode rootNode = callNode.getRootNode();

                  assert JSRuntime.isJSRootNode(rootNode);

                  int promiseIndex = -1;
                  if (element.getFrame() != null) {
                     Object[] arguments;
                     if (JSRuntime.isJSFunctionRootNode(rootNode)) {
                        arguments = element.getFrame().getArguments();
                     } else if (((JavaScriptRootNode)rootNode).isResumption()) {
                        arguments = element.getFrame().getArguments();
                     } else {
                        if (!(rootNode instanceof PerformPromiseAllNode.PromiseAllMarkerRootNode)) {
                           break;
                        }

                        arguments = element.getFrame().getArguments();
                        if (JSArguments.getUserArgumentCount(arguments) > 0) {
                           Object promiseIndexArg = JSArguments.getUserArgument(arguments, 0);
                           if (promiseIndexArg instanceof Integer) {
                              promiseIndex = (Integer)promiseIndexArg;
                           }
                        }
                     }

                     Object thisObj = JSArguments.getThisObject(arguments);
                     Object functionObj = JSArguments.getFunctionObject(arguments);
                     if (JSFunction.isJSFunction(functionObj)) {
                        JSFunctionObject function = (JSFunctionObject)functionObj;
                        JSFunctionData functionData = JSFunction.getFunctionData(function);
                        if (functionData.isBuiltin()) {
                           if (JSFunction.isStrictBuiltin(function, JSRealm.get(null))) {
                              this.inStrictMode = true;
                           }
                        } else if (functionData.isStrict()) {
                           this.inStrictMode = true;
                        }

                        if (this.skippingFrames && function == this.skipFramesUpTo) {
                           this.skippingFrames = false;
                           return true;
                        }

                        JSRealm realm = JSFunction.getRealm(function);
                        if (JSFunction.isBuiltinThatShouldNotAppearInStackTrace(realm, function)) {
                           return true;
                        }

                        if (!this.skippingFrames) {
                           if (functionData.isAsync() && !functionData.isGenerator() && JSRuntime.isJSFunctionRootNode(rootNode)) {
                              return true;
                           }

                           this.stackTrace
                              .add(
                                 GraalJSException.processJSFrame(
                                    rootNode, callNode, thisObj, function, this.inStrictMode, this.inNashornMode, this.async, promiseIndex
                                 )
                              );
                        }
                     }
                  }
                  break;
               case 2:
                  if (!this.skippingFrames) {
                     GraalJSException.JSStackTraceElement elem = GraalJSException.processForeignFrame(
                        callNode, this.inStrictMode, this.inNashornMode, this.async
                     );
                     if (elem != null) {
                        this.stackTrace.add(elem);
                     }
                  }
            }
         }

         return this.stackTrace.size() < this.stackTraceLimit;
      }

      public List<GraalJSException.JSStackTraceElement> getStackTrace() {
         return this.stackTrace;
      }
   }

   @ExportMessage
   @ImportStatic(JSConfig.class)
   public static final class IsIdenticalOrUndefined {
      @Specialization
      public static TriState doException(
         GraalJSException receiver,
         GraalJSException other,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("thisLib") InteropLibrary thisLib,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("otherLib") InteropLibrary otherLib
      ) {
         if (receiver == other) {
            return TriState.TRUE;
         } else {
            Object thisObj = receiver.getErrorObjectLazy();
            if (thisObj == null) {
               return TriState.FALSE;
            } else {
               Object otherObj = other.getErrorObjectLazy();
               if (otherObj == null) {
                  return TriState.FALSE;
               } else {
                  return thisLib.hasIdentity(thisObj) && otherLib.hasIdentity(other)
                     ? TriState.valueOf(thisLib.isIdentical(thisObj, other, otherLib))
                     : TriState.UNDEFINED;
               }
            }
         }
      }

      @Specialization
      public static TriState doJSObject(GraalJSException receiver, JSDynamicObject other) {
         Object thisObj = receiver.getErrorObjectLazy();
         return thisObj == null ? TriState.FALSE : TriState.valueOf(thisObj == other);
      }

      @Specialization(guards = "!isGraalJSException(other)", replaces = "doJSObject")
      public static TriState doOther(
         GraalJSException receiver,
         Object other,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("thisLib") InteropLibrary thisLib,
         @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("otherLib") InteropLibrary otherLib
      ) {
         Object thisObj = receiver.getErrorObjectLazy();
         if (thisObj == null) {
            return other instanceof JSDynamicObject ? TriState.FALSE : TriState.UNDEFINED;
         } else {
            return thisLib.hasIdentity(thisObj) && otherLib.hasIdentity(other)
               ? TriState.valueOf(thisLib.isIdentical(thisObj, other, otherLib))
               : TriState.UNDEFINED;
         }
      }

      static boolean isGraalJSException(Object value) {
         return value instanceof GraalJSException;
      }
   }

   public static final class JSStackTraceElement {
      private final TruffleString fileName;
      private final TruffleString functionName;
      private final SourceSection sourceSection;
      private final Object thisObj;
      private final Object functionObj;
      private final SourceSection targetSourceSection;
      private final boolean strict;
      private final boolean eval;
      private final boolean global;
      private final boolean inNashornMode;
      private final boolean async;
      private final int promiseIndex;

      private JSStackTraceElement(
         TruffleString fileName,
         TruffleString functionName,
         SourceSection sourceSection,
         Object thisObj,
         Object functionObj,
         SourceSection targetSourceSection,
         boolean strict,
         boolean eval,
         boolean global,
         boolean inNashornMode,
         boolean async,
         int promiseIndex
      ) {
         CompilerAsserts.neverPartOfCompilation();
         this.fileName = fileName;
         this.functionName = functionName;
         this.sourceSection = sourceSection;
         this.thisObj = thisObj;
         this.functionObj = functionObj;
         this.targetSourceSection = targetSourceSection;
         this.strict = strict;
         this.eval = eval;
         this.global = global;
         this.inNashornMode = inNashornMode;
         this.async = async;
         this.promiseIndex = promiseIndex;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getFileName() {
         return Strings.startsWith(this.fileName, Evaluator.TS_EVAL_AT_SOURCE_NAME_PREFIX) ? Evaluator.TS_EVAL_SOURCE_NAME : this.fileName;
      }

      public TruffleString getClassName() {
         return this.getTypeName(false);
      }

      public TruffleString getTypeName() {
         return this.getTypeName(true);
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getTypeName(boolean checkGlobal) {
         if (this.inNashornMode) {
            return Strings.concatAll(Strings.ANGLE_BRACKET_OPEN, this.fileName, Strings.ANGLE_BRACKET_CLOSE);
         } else if (checkGlobal && this.global) {
            return Strings.GLOBAL;
         } else {
            Object thisObject = this.getThis();
            if (thisObject == JSFunction.CONSTRUCT) {
               return this.getFunctionName();
            } else {
               if (!JSRuntime.isNullOrUndefined(thisObject) && !this.global) {
                  if (JSDynamicObject.isJSDynamicObject(thisObject)) {
                     return JSRuntime.getConstructorName((JSDynamicObject)thisObject);
                  }

                  if (JSRuntime.isJSPrimitive(thisObject)) {
                     return GraalJSException.getPrimitiveConstructorName(thisObject);
                  }
               }

               return null;
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getFunctionName() {
         if (JSFunction.isJSFunction(this.functionObj)) {
            TruffleString dynamicName = findFunctionName((JSDynamicObject)this.functionObj);
            if (dynamicName != null
               && !Strings.isEmpty(dynamicName)
               && (
                  !this.isEval()
                     || !Strings.equals(Strings.DYNAMIC_FUNCTION_NAME, dynamicName)
                     || !JSObject.getJSContext((JSDynamicObject)this.functionObj).isOptionV8CompatibilityMode()
               )) {
               return dynamicName;
            }
         }

         return this.functionName;
      }

      private static TruffleString findFunctionName(JSDynamicObject functionObj) {
         assert JSFunction.isJSFunction(functionObj);

         PropertyDescriptor desc = JSObject.getOwnProperty(functionObj, JSFunction.NAME);
         if (desc != null && desc.isDataDescriptor()) {
            Object name = desc.getValue();
            if (Strings.isTString(name)) {
               return (TruffleString)name;
            }
         }

         return null;
      }

      @CompilerDirectives.TruffleBoundary
      public String getMethodName() {
         return Strings.toJavaString(this.getMethodName(JavaScriptLanguage.getCurrentLanguage().getJSContext()));
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getMethodName(JSContext context) {
         if (context.isOptionNashornCompatibilityMode()) {
            return JSError.correctMethodName(this.functionName, context);
         } else if (JSRuntime.isNullOrUndefined(this.thisObj) || !JSDynamicObject.isJSDynamicObject(this.thisObj)) {
            return null;
         } else if (!JSFunction.isJSFunction(this.functionObj)) {
            return null;
         } else {
            JSDynamicObject receiver = (JSDynamicObject)this.thisObj;
            JSFunctionObject function = (JSFunctionObject)this.functionObj;
            if (this.functionName != null && !Strings.isEmpty(this.functionName)) {
               TruffleString name = findMethodPropertyNameByFunctionName(receiver, this.functionName, function);
               if (name != null) {
                  return name;
               }
            }

            return findMethodPropertyName(receiver, function);
         }
      }

      private static TruffleString findMethodPropertyNameByFunctionName(JSDynamicObject receiver, TruffleString functionName, JSFunctionObject functionObj) {
         TruffleString propertyName = functionName;
         boolean accessor = false;
         if (Strings.startsWith(functionName, Strings.GET_SPC) || Strings.startsWith(functionName, Strings.SET_SPC)) {
            propertyName = Strings.lazySubstring(functionName, 4);
            accessor = true;
         }

         if (propertyName.isEmpty()) {
            return null;
         } else {
            for (JSDynamicObject current = receiver; current != Null.instance && !JSProxy.isJSProxy(current); current = JSObject.getPrototype(current)) {
               PropertyDescriptor desc = JSObject.getOwnProperty(current, propertyName);
               if (desc != null) {
                  if (desc.isAccessorDescriptor() == accessor
                     && (desc.getValue() == functionObj || desc.getGet() == functionObj || desc.getSet() == functionObj)) {
                     return propertyName;
                  }
                  break;
               }
            }

            return null;
         }
      }

      private static TruffleString findMethodPropertyName(JSDynamicObject receiver, JSDynamicObject functionObj) {
         TruffleString name = null;

         for (JSDynamicObject current = receiver; current != Null.instance && !JSProxy.isJSProxy(current); current = JSObject.getPrototype(current)) {
            for (TruffleString key : JSObject.enumerableOwnNames(current)) {
               PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
               if (desc.getValue() == functionObj || desc.getGet() == functionObj || desc.getSet() == functionObj) {
                  if (name != null) {
                     return null;
                  }

                  name = key;
               }
            }
         }

         return name;
      }

      @CompilerDirectives.TruffleBoundary
      public int getLineNumber() {
         if (this.sourceSection == null) {
            return -1;
         } else {
            int lineNumber = this.sourceSection.getStartLine();
            if (!this.inNashornMode && this.targetSourceSection != null) {
               int offset = GraalJSException.sourceSectionOffset(this.sourceSection, this.targetSourceSection);
               CharSequence chars = this.sourceSection.getCharacters();

               for (int pos = 0; pos < offset; pos++) {
                  if (chars.charAt(pos) == '\n') {
                     lineNumber++;
                  }
               }
            }

            return lineNumber;
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getLine() {
         int lineNumber = this.getLineNumber();
         return this.sourceSection != null && this.sourceSection.getSource() != null && lineNumber > 0
            ? Strings.fromJavaString(this.sourceSection.getSource().getCharacters(lineNumber).toString())
            : Strings.UNKNOWN_FILENAME;
      }

      @CompilerDirectives.TruffleBoundary
      public int getColumnNumber() {
         if (this.sourceSection == null) {
            return -1;
         } else {
            int columnNumber = this.sourceSection.getStartColumn();
            if (!this.inNashornMode && this.targetSourceSection != null) {
               int offset = GraalJSException.sourceSectionOffset(this.sourceSection, this.targetSourceSection);
               CharSequence chars = this.sourceSection.getCharacters();

               for (int pos = 0; pos < offset; pos++) {
                  if (chars.charAt(pos) == '\n') {
                     columnNumber = 1;
                  } else {
                     columnNumber++;
                  }
               }
            }

            return columnNumber;
         }
      }

      public int getPosition() {
         return this.sourceSection != null ? this.sourceSection.getCharIndex() : -1;
      }

      public Object getThis() {
         return this.thisObj;
      }

      @CompilerDirectives.TruffleBoundary
      public Object getThisOrGlobal() {
         if (this.global) {
            if (JSRuntime.isNullOrUndefined(this.thisObj)) {
               return JSFunction.getRealm((JSFunctionObject)this.functionObj).getGlobalObject();
            } else {
               assert this.thisObj == JSFunction.getRealm((JSFunctionObject)this.functionObj).getGlobalObject();

               return this.thisObj;
            }
         } else {
            return this.thisObj == JSFunction.CONSTRUCT ? Undefined.instance : this.thisObj;
         }
      }

      public Object getFunction() {
         return this.functionObj;
      }

      public boolean isStrict() {
         return this.strict;
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isConstructor() {
         if (this.thisObj == JSFunction.CONSTRUCT) {
            return true;
         } else if (!JSRuntime.isNullOrUndefined(this.thisObj) && JSDynamicObject.isJSDynamicObject(this.thisObj)) {
            Object constructor = JSRuntime.getDataProperty((JSDynamicObject)this.thisObj, JSObject.CONSTRUCTOR);
            return constructor != null && constructor == this.functionObj;
         } else {
            return false;
         }
      }

      public boolean isEval() {
         return this.eval;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString getEvalOrigin() {
         return Strings.startsWith(this.fileName, Strings.ANGLE_BRACKET_OPEN) ? null : this.fileName;
      }

      public int getPromiseIndex() {
         return this.promiseIndex;
      }

      public boolean isPromiseAll() {
         return this.promiseIndex >= 0;
      }

      public boolean isAsync() {
         return this.async;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         JSContext context = JavaScriptLanguage.getCurrentJSRealm().getContext();
         return Strings.toJavaString(this.toString(context));
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleString toString(JSContext context) {
         TruffleStringBuilder sb = Strings.builderCreate();
         if (this.isPromiseAll()) {
            Strings.builderAppend(sb, Strings.ASYNC_PROMISE_ALL_BEGIN);
            Strings.builderAppend(sb, this.promiseIndex);
            Strings.builderAppend(sb, Strings.PAREN_CLOSE);
            return Strings.builderToString(sb);
         } else {
            TruffleString className = this.getClassName();
            TruffleString methodName = JSError.correctMethodName(this.getFunctionName(), context);
            if (methodName == null || Strings.isEmpty(methodName)) {
               TruffleString name = this.getMethodName(context);
               if (name == null) {
                  methodName = JSError.getAnonymousFunctionNameStackTrace(context);
               } else {
                  methodName = name;
               }
            }

            boolean includeMethodName = className != null || !JSError.getAnonymousFunctionNameStackTrace(context).equals(methodName);
            if (includeMethodName) {
               if (this.async) {
                  Strings.builderAppend(sb, Strings.ASYNC_SPC);
               }

               if (className != null) {
                  if (className.equals(methodName)) {
                     if (this.isConstructor()) {
                        Strings.builderAppend(sb, Strings.NEW_SPACE);
                     }
                  } else {
                     Strings.builderAppend(sb, className);
                     Strings.builderAppend(sb, Strings.DOT);
                  }
               }

               Strings.builderAppend(sb, methodName);
               Strings.builderAppend(sb, Strings.SPACE_PAREN_OPEN);
            }

            if (JSFunction.isBuiltinSourceSection(this.sourceSection)) {
               Strings.builderAppend(sb, Strings.NATIVE);
            } else {
               TruffleString evalOrigin = this.getEvalOrigin();
               TruffleString sourceName = evalOrigin != null ? evalOrigin : this.getFileName();
               Strings.builderAppend(sb, sourceName);
               if (this.eval) {
                  Strings.builderAppend(sb, Strings.COMMA_ANONYMOUS_BRACKETS);
               }

               Strings.builderAppend(sb, Strings.COLON);
               Strings.builderAppend(sb, this.getLineNumber());
               Strings.builderAppend(sb, Strings.COLON);
               Strings.builderAppend(sb, this.getColumnNumber());
            }

            if (includeMethodName) {
               Strings.builderAppend(sb, Strings.PAREN_CLOSE);
            }

            return Strings.builderToString(sb);
         }
      }
   }
}
