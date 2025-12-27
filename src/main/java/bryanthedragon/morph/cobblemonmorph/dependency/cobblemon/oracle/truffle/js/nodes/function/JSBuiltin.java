package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.Builtin;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;

public final class JSBuiltin implements Builtin, JSFunctionData.CallTargetInitializer {
   private final TruffleString name;
   private final TruffleString fullName;
   private final Object key;
   private final int length;
   private final byte attributeFlags;
   private final byte ecmaScriptVersion;
   private final boolean annexB;
   private final BuiltinNodeFactory functionNodeFactory;
   private final BuiltinNodeFactory constructorNodeFactory;
   private final BuiltinNodeFactory newTargetConstructorNodeFactory;
   private static final int GETTER_FLAG = 8;
   private static final int SETTER_FLAG = 16;

   public JSBuiltin(
      TruffleString containerName,
      TruffleString functionName,
      Object key,
      int length,
      int attributeFlags,
      int ecmaScriptVersion,
      boolean annexB,
      BuiltinNodeFactory functionNodeFactory,
      BuiltinNodeFactory constructorNodeFactory,
      BuiltinNodeFactory newTargetConstructorFactory
   ) {
      assert isAllowedKey(key);

      assert (byte)ecmaScriptVersion == ecmaScriptVersion && (byte)attributeFlags == attributeFlags;

      this.name = key instanceof Symbol ? ((Symbol)key).toFunctionNameString() : functionName;
      this.fullName = containerName == null ? this.name : Strings.concatAll(containerName, Strings.DOT, this.name);
      this.key = key;
      this.length = length;
      this.ecmaScriptVersion = (byte)ecmaScriptVersion;
      this.attributeFlags = (byte)(attributeFlags | detectAccessor(functionName));
      this.annexB = annexB;
      this.functionNodeFactory = functionNodeFactory;
      this.constructorNodeFactory = constructorNodeFactory;
      this.newTargetConstructorNodeFactory = newTargetConstructorFactory;
   }

   public JSBuiltin(TruffleString containerName, TruffleString name, int length, int flags, BuiltinNodeFactory functionNodeFactory) {
      this(containerName, name, name, length, flags, 5, false, functionNodeFactory, null, null);
   }

   @Override
   public TruffleString getName() {
      return this.name;
   }

   public TruffleString getFullName() {
      return this.fullName;
   }

   @Override
   public Object getKey() {
      return this.key;
   }

   @Override
   public int getLength() {
      return this.length;
   }

   public boolean isConstructor() {
      return this.constructorNodeFactory != null;
   }

   public boolean hasSeparateConstructor() {
      return this.isConstructor() && this.constructorNodeFactory != this.functionNodeFactory;
   }

   public boolean hasNewTargetConstructor() {
      return this.isConstructor() && this.newTargetConstructorNodeFactory != null;
   }

   @Override
   public int getECMAScriptVersion() {
      return Math.max(5, this.ecmaScriptVersion);
   }

   @Override
   public boolean isAnnexB() {
      return this.annexB;
   }

   @Override
   public int getAttributeFlags() {
      return this.attributeFlags & 7;
   }

   @Override
   public boolean isConfigurable() {
      return (this.attributeFlags & 2) == 0;
   }

   @Override
   public boolean isWritable() {
      return (this.attributeFlags & 4) == 0;
   }

   @Override
   public boolean isEnumerable() {
      return (this.attributeFlags & 1) == 0;
   }

   @Override
   public boolean isGetter() {
      return (this.attributeFlags & 8) != 0;
   }

   @Override
   public boolean isSetter() {
      return (this.attributeFlags & 16) != 0;
   }

   public static SourceSection getSourceSection() {
      return createSourceSection();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "JSBuiltin [name=" + this.name + ", length=" + this.length + "]";
   }

   private static boolean isAllowedKey(Object key) {
      if (key instanceof Symbol) {
         return true;
      } else if (!Strings.isTString(key)) {
         return false;
      } else {
         TruffleString tsk = (TruffleString)key;
         return !Strings.isEmpty(tsk)
               && (!Strings.endsWith(tsk, Strings.UNDERSCORE) || Strings.equals(tsk, Strings.$_))
               && !Strings.startsWith(tsk, Strings.UNDERSCORE)
            || Strings.startsWith(tsk, Strings.UNDERSCORE_2) && Strings.endsWith(tsk, Strings.UNDERSCORE_2);
      }
   }

   private static int detectAccessor(TruffleString functionName) {
      if (Strings.startsWith(functionName, Strings.GET_SPC)) {
         return 8;
      } else {
         return Strings.startsWith(functionName, Strings.SET_SPC) ? 16 : 0;
      }
   }

   @Override
   public JSFunctionData createFunctionData(JSContext context) {
      JSFunctionData cached = context.getBuiltinFunctionData(this);
      if (cached != null) {
         return cached;
      } else {
         JSFunctionData functionData = JSFunctionData.create(context, this.getLength(), this.getName(), this.isConstructor(), false, false, true);
         functionData.setLazyInit(this);
         context.putBuiltinFunctionData(this, functionData);
         return functionData;
      }
   }

   JSBuiltinNode createNode(JSContext context, boolean construct, boolean newTarget) {
      JSBuiltinNode builtinNode = this.createNodeImpl(context, construct, newTarget);
      builtinNode.construct = construct;
      builtinNode.newTarget = newTarget;
      return builtinNode;
   }

   private JSBuiltinNode createNodeImpl(JSContext context, boolean construct, boolean newTarget) {
      if (newTarget && this.newTargetConstructorNodeFactory != null) {
         return this.newTargetConstructorNodeFactory.createNode(context, this);
      } else {
         return construct && this.constructorNodeFactory != null
            ? this.constructorNodeFactory.createNode(context, this)
            : this.functionNodeFactory.createNode(context, this);
      }
   }

   public static SourceSection createSourceSection() {
      return JSFunction.BUILTIN_SOURCE_SECTION;
   }

   private static void initializeFunctionData(JSFunctionData functionData, JSBuiltin builtin) {
      JSContext context = functionData.getContext();
      JSBuiltinNode functionRoot = JSBuiltinNode.createBuiltin(context, builtin, false, false);
      FrameDescriptor frameDescriptor = null;
      FunctionRootNode callRoot = FunctionRootNode.create(functionRoot, frameDescriptor, functionData, getSourceSection(), builtin.getFullName());
      functionData.setRootNode(callRoot);
   }

   private static void initializeFunctionDataCallTarget(JSFunctionData functionData, JSBuiltin builtin, JSFunctionData.Target target, CallTarget callTarget) {
      JSContext context = functionData.getContext();
      NodeFactory factory = NodeFactory.getDefaultInstance();
      FrameDescriptor frameDescriptor = null;
      if (target == JSFunctionData.Target.Call) {
         functionData.setCallTarget(callTarget);
      } else if (target == JSFunctionData.Target.Construct) {
         RootNode constructRoot;
         if (builtin.hasSeparateConstructor()) {
            JSBuiltinNode constructNode = JSBuiltinNode.createBuiltin(context, builtin, true, false);
            constructRoot = FunctionRootNode.create(constructNode, frameDescriptor, functionData, getSourceSection(), builtin.getFullName());
         } else {
            constructRoot = factory.createConstructorRootNode(functionData, callTarget, false);
         }

         functionData.setConstructTarget(constructRoot.getCallTarget());
      } else if (target == JSFunctionData.Target.ConstructNewTarget) {
         JavaScriptRootNode constructNewTargetRoot;
         if (builtin.hasNewTargetConstructor()) {
            AbstractBodyNode constructNewTargetNode = JSBuiltinNode.createBuiltin(context, builtin, true, true);
            constructNewTargetRoot = FunctionRootNode.create(constructNewTargetNode, frameDescriptor, functionData, getSourceSection(), builtin.getFullName());
         } else {
            CallTarget constructTarget = functionData.getConstructTarget();
            constructNewTargetRoot = factory.createDropNewTarget(functionData.getContext(), constructTarget);
         }

         functionData.setConstructNewTarget(constructNewTargetRoot.getCallTarget());
      }
   }

   @Override
   public void initializeRoot(JSFunctionData functionData) {
      initializeFunctionData(functionData, this);
   }

   @Override
   public void initializeCallTarget(JSFunctionData functionData, JSFunctionData.Target target, CallTarget callTarget) {
      initializeFunctionDataCallTarget(functionData, this, target, callTarget);
   }
}
