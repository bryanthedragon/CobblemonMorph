package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(InteropLibrary.class)
public abstract class JSFunctionObject extends JSNonProxyObject {
   private final JSFunctionData functionData;
   private final MaterializedFrame enclosingFrame;
   private final JSRealm realm;
   private Object classPrototype;

   protected JSFunctionObject(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
      super(shape);
      this.functionData = functionData;
      this.enclosingFrame = enclosingFrame;
      this.realm = realm;
      this.classPrototype = classPrototype;
   }

   public final JSFunctionData getFunctionData() {
      return this.functionData;
   }

   public final MaterializedFrame getEnclosingFrame() {
      return this.enclosingFrame;
   }

   public final JSRealm getRealm() {
      return this.realm;
   }

   public final Object getClassPrototype() {
      return this.classPrototype;
   }

   public void setClassPrototype(Object classPrototype) {
      this.classPrototype = classPrototype;
   }

   public Object getLexicalThis() {
      return this.classPrototype;
   }

   @Override
   public TruffleString getClassName() {
      return JSFunction.INSTANCE.getClassName(this);
   }

   @Override
   public TruffleString getBuiltinToStringTag() {
      return JSFunction.INSTANCE.getBuiltinToStringTag(this);
   }

   @ExportMessage
   public final boolean isExecutable(@Cached IsCallableNode isCallable) {
      return isCallable.executeBoolean(this);
   }

   @ExportMessage
   public final Object execute(
      Object[] args,
      @CachedLibrary("this") InteropLibrary self,
      @Cached JSInteropExecuteNode callNode,
      @Cached.Shared("exportValue") @Cached ExportValueNode exportNode
   ) throws UnsupportedMessageException {
      JavaScriptLanguage language = JavaScriptLanguage.get(self);
      language.interopBoundaryEnter(this.realm);

      Object var7;
      try {
         Object result = callNode.execute(this, Undefined.instance, args);
         var7 = exportNode.execute(result);
      } finally {
         language.interopBoundaryExit(this.realm);
      }

      return var7;
   }

   @ExportMessage
   public final boolean isInstantiable() {
      return JSRuntime.isConstructor(this);
   }

   @ExportMessage
   public final Object instantiate(
      Object[] args,
      @CachedLibrary("this") InteropLibrary self,
      @Cached JSInteropInstantiateNode callNode,
      @Cached.Shared("exportValue") @Cached ExportValueNode exportNode
   ) throws UnsupportedMessageException {
      JavaScriptLanguage language = JavaScriptLanguage.get(self);
      language.interopBoundaryEnter(this.realm);

      Object var7;
      try {
         Object result = callNode.execute(this, args);
         var7 = exportNode.execute(result);
      } finally {
         language.interopBoundaryExit(this.realm);
      }

      return var7;
   }

   @ExportMessage
   public final boolean hasSourceLocation() {
      return getSourceLocationImpl(this) != null;
   }

   @ExportMessage
   public final SourceSection getSourceLocation() throws UnsupportedMessageException {
      SourceSection sourceSection = getSourceLocationImpl(this);
      if (sourceSection == null) {
         throw UnsupportedMessageException.create();
      } else {
         return sourceSection;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static SourceSection getSourceLocationImpl(JSDynamicObject receiver) {
      if (JSFunction.isJSFunction(receiver)) {
         CallTarget ct = JSFunction.getCallTarget(receiver);
         if (JSFunction.isBoundFunction(receiver)) {
            JSDynamicObject func = JSFunction.getBoundTargetFunction(receiver);
            ct = JSFunction.getCallTarget(func);
         }

         if (ct instanceof RootCallTarget) {
            return ((RootCallTarget)ct).getRootNode().getSourceSection();
         }
      }

      return null;
   }

   @ExportMessage
   public final boolean isMetaObject() {
      return true;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage.Repeat({@ExportMessage(name = "getMetaQualifiedName"), @ExportMessage(name = "getMetaSimpleName")})
   public final Object getMetaObjectName() {
      Object name = JSRuntime.getDataProperty(this, JSFunction.NAME);
      return Strings.isTString(name) ? name : Strings.EMPTY_STRING;
   }

   @CompilerDirectives.TruffleBoundary
   @ExportMessage
   public final boolean isMetaInstance(Object instance) {
      Object constructorPrototype = JSRuntime.getDataProperty(this, JSObject.PROTOTYPE);
      if (JSGuards.isJSObject(constructorPrototype)) {
         Object obj = instance;
         if (instance instanceof InteropFunction) {
            obj = ((InteropFunction)instance).getFunction();
         }

         if (obj instanceof JSException) {
            obj = ((JSException)obj).getErrorObject();
         }

         if (JSGuards.isJSObject(obj) && !JSProxy.isJSProxy(obj)) {
            for (JSDynamicObject proto = JSObject.getPrototype((JSDynamicObject)obj); proto != Null.instance; proto = JSObject.getPrototype(proto)) {
               if (proto == constructorPrototype) {
                  return true;
               }

               if (JSProxy.isJSProxy(proto)) {
                  break;
               }
            }
         }
      }

      return false;
   }

   public static JSFunctionObject create(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
      return new JSFunctionObject.Unbound(shape, functionData, enclosingFrame, realm, classPrototype);
   }

   public static JSFunctionObject createBound(
      Shape shape,
      JSFunctionData functionData,
      JSRealm realm,
      Object classPrototype,
      JSDynamicObject boundTargetFunction,
      Object boundThis,
      Object[] boundArguments
   ) {
      return new JSFunctionObject.Bound(shape, functionData, realm, classPrototype, boundTargetFunction, boundThis, boundArguments);
   }

   public static final class Bound extends JSFunctionObject {
      private final JSDynamicObject boundTargetFunction;
      private final Object boundThis;
      private final Object[] boundArguments;
      private final int boundLength;
      private TruffleString boundName;

      protected Bound(
         Shape shape,
         JSFunctionData functionData,
         JSRealm realm,
         Object classPrototype,
         JSDynamicObject boundTargetFunction,
         Object boundThis,
         Object[] boundArguments
      ) {
         super(shape, functionData, JSFrameUtil.NULL_MATERIALIZED_FRAME, realm, classPrototype);
         this.boundTargetFunction = boundTargetFunction;
         this.boundThis = boundThis;
         this.boundArguments = boundArguments;
         this.boundLength = this.calculateBoundLength();
      }

      public JSDynamicObject getBoundTargetFunction() {
         return this.boundTargetFunction;
      }

      public Object getBoundThis() {
         return this.boundThis;
      }

      public Object[] getBoundArguments() {
         return this.boundArguments;
      }

      public TruffleString getBoundName() {
         if (this.boundName == null) {
            this.initializeBoundName();
         }

         return this.boundName;
      }

      public void setTargetName(TruffleString targetName) {
         this.boundName = Strings.concat(Strings.BOUND_SPC, targetName);
      }

      @CompilerDirectives.TruffleBoundary
      private void initializeBoundName() {
         this.setTargetName(getFunctionName(this.boundTargetFunction));
      }

      private static TruffleString getFunctionName(JSDynamicObject function) {
         return JSFunction.isBoundFunction(function) ? ((JSFunctionObject.Bound)function).getBoundName() : JSFunction.getName(function);
      }

      public int getBoundLength() {
         return this.boundLength;
      }

      private int calculateBoundLength() {
         return Math.max(0, getBoundFunctionLength(this.boundTargetFunction) - this.boundArguments.length);
      }

      private static int getBoundFunctionLength(JSDynamicObject function) {
         return JSFunction.isBoundFunction(function) ? ((JSFunctionObject.Bound)function).getBoundLength() : JSFunction.getLength(function);
      }
   }

   public static final class Unbound extends JSFunctionObject {
      protected Unbound(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
         super(shape, functionData, enclosingFrame, realm, classPrototype);
      }
   }
}
