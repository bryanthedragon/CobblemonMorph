package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public final class JSFunctionData {
   @CompilerDirectives.CompilationFinal
   private volatile CallTarget callTarget;
   @CompilerDirectives.CompilationFinal
   private volatile CallTarget constructTarget;
   @CompilerDirectives.CompilationFinal
   private volatile CallTarget constructNewTarget;
   private final JSContext context;
   @CompilerDirectives.CompilationFinal
   private TruffleString name;
   private final int length;
   private final int flags;
   private static final int IS_CONSTRUCTOR = 1;
   private static final int IS_DERIVED = 2;
   private static final int IS_STRICT = 4;
   private static final int IS_BUILTIN = 8;
   private static final int NEEDS_PARENT_FRAME = 16;
   private static final int IS_GENERATOR = 32;
   private static final int IS_ASYNC = 64;
   private static final int IS_CLASS_CONSTRUCTOR = 128;
   private static final int STRICT_FUNCTION_PROPERTIES = 256;
   private static final int NEEDS_NEW_TARGET = 512;
   private static final int IS_BOUND = 1024;
   private volatile RootNode rootNode;
   private volatile JSFunctionData.Initializer lazyInit;
   private static final AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> UPDATER_CALL_TARGET = AtomicReferenceFieldUpdater.newUpdater(
      JSFunctionData.class, CallTarget.class, "callTarget"
   );
   private static final AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> UPDATER_CONSTRUCT_TARGET = AtomicReferenceFieldUpdater.newUpdater(
      JSFunctionData.class, CallTarget.class, "constructTarget"
   );
   private static final AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> UPDATER_CONSTRUCT_NEW_TARGET = AtomicReferenceFieldUpdater.newUpdater(
      JSFunctionData.class, CallTarget.class, "constructNewTarget"
   );
   private static final AtomicReferenceFieldUpdater<JSFunctionData, RootNode> UPDATER_ROOT_TARGET = AtomicReferenceFieldUpdater.newUpdater(
      JSFunctionData.class, RootNode.class, "rootNode"
   );

   private JSFunctionData(
      JSContext context, CallTarget callTarget, CallTarget constructTarget, CallTarget constructNewTarget, int length, TruffleString name, int flags
   ) {
      this.context = context;
      this.callTarget = callTarget;
      this.constructTarget = constructTarget;
      this.constructNewTarget = constructNewTarget;
      this.name = name;
      this.length = length;
      this.flags = flags;
   }

   public static JSFunctionData create(
      JSContext context, CallTarget callTarget, CallTarget constructTarget, CallTarget constructNewTarget, int length, TruffleString name, int flags
   ) {
      return new JSFunctionData(context, callTarget, constructTarget, constructNewTarget, length, name, flags);
   }

   public static JSFunctionData create(
      JSContext context,
      CallTarget callTarget,
      CallTarget constructTarget,
      CallTarget constructNewTarget,
      int length,
      TruffleString name,
      boolean isConstructor,
      boolean isDerived,
      boolean isStrict,
      boolean isBuiltin,
      boolean needsParentFrame,
      boolean isGenerator,
      boolean isAsync,
      boolean isClassConstructor,
      boolean strictFunctionProperties,
      boolean needsNewTarget,
      boolean isBound
   ) {
      int flags = (isConstructor ? 1 : 0)
         | (isDerived ? 2 : 0)
         | (isStrict ? 4 : 0)
         | (isBuiltin ? 8 : 0)
         | (needsParentFrame ? 16 : 0)
         | (isGenerator ? 32 : 0)
         | (isAsync ? 64 : 0)
         | (isClassConstructor ? 128 : 0)
         | (strictFunctionProperties ? 256 : 0)
         | (needsNewTarget ? 512 : 0)
         | (isBound ? 1024 : 0);
      return create(context, callTarget, constructTarget, constructNewTarget, length, name, flags);
   }

   public static JSFunctionData create(
      JSContext context,
      CallTarget callTarget,
      CallTarget constructTarget,
      int length,
      TruffleString name,
      boolean isConstructor,
      boolean isDerived,
      boolean strictMode,
      boolean isBuiltin
   ) {
      assert callTarget != null && constructTarget != null;

      return create(
         context,
         callTarget,
         constructTarget,
         constructTarget,
         length,
         name,
         isConstructor,
         isDerived,
         strictMode,
         isBuiltin,
         false,
         false,
         false,
         false,
         hasStrictProperties(context, strictMode, isBuiltin),
         false,
         false
      );
   }

   public static JSFunctionData createCallOnly(JSContext context, CallTarget callTarget, int length, TruffleString name) {
      assert callTarget != null;

      CallTarget constructTarget = context.getNotConstructibleCallTarget();
      return create(context, callTarget, constructTarget, length, name, false, false, false, true);
   }

   public static JSFunctionData create(
      JSContext context, int length, TruffleString name, boolean isConstructor, boolean isDerived, boolean strictMode, boolean isBuiltin
   ) {
      return create(
         context,
         null,
         null,
         null,
         length,
         name,
         isConstructor,
         isDerived,
         strictMode,
         isBuiltin,
         false,
         false,
         false,
         false,
         hasStrictProperties(context, strictMode, isBuiltin),
         false,
         false
      );
   }

   public static JSFunctionData create(JSContext context, CallTarget callTarget, int length, TruffleString name) {
      assert callTarget != null;

      return create(context, callTarget, callTarget, callTarget, length, name, true, false, false, false, false, false, false, false, false, false, false);
   }

   private static boolean hasStrictProperties(JSContext context, boolean strictMode, boolean isBuiltin) {
      return isBuiltin ? context.getEcmaScriptVersion() >= 6 : strictMode;
   }

   public CallTarget getCallTarget() {
      CallTarget result = this.callTarget;
      if (result != null) {
         return result;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.ensureInitialized(JSFunctionData.Target.Call);
      }
   }

   public CallTarget getConstructTarget() {
      CallTarget result = this.constructTarget;
      if (result != null) {
         return result;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.ensureInitialized(JSFunctionData.Target.Construct);
      }
   }

   public CallTarget getConstructNewTarget() {
      CallTarget result = this.constructNewTarget;
      if (result != null) {
         return result;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.ensureInitialized(JSFunctionData.Target.ConstructNewTarget);
      }
   }

   public JSContext getContext() {
      return this.context;
   }

   public TruffleString getName() {
      return this.name;
   }

   public void setName(TruffleString name) {
      this.name = name;
   }

   public int getLength() {
      return this.length;
   }

   public boolean isConstructor() {
      return (this.flags & 1) != 0;
   }

   public boolean isStrict() {
      return (this.flags & 4) != 0;
   }

   public boolean hasStrictFunctionProperties() {
      return (this.flags & 256) != 0;
   }

   public boolean isBuiltin() {
      return (this.flags & 8) != 0;
   }

   public boolean needsParentFrame() {
      return (this.flags & 16) != 0;
   }

   public boolean isGenerator() {
      return (this.flags & 32) != 0;
   }

   public boolean isAsync() {
      return (this.flags & 64) != 0;
   }

   public boolean isAsyncGenerator() {
      return this.isGenerator() && this.isAsync();
   }

   public boolean isDerived() {
      return (this.flags & 2) != 0;
   }

   public boolean isClassConstructor() {
      return (this.flags & 128) != 0;
   }

   public boolean isPrototypeNotWritable() {
      return this.isClassConstructor();
   }

   public boolean requiresNew() {
      return this.isClassConstructor();
   }

   public boolean needsNewTarget() {
      return (this.flags & 512) != 0;
   }

   public boolean isBound() {
      return (this.flags & 1024) != 0;
   }

   public int getFlags() {
      return this.flags;
   }

   public CallTarget getCallTarget(BranchProfile initBranch) {
      CallTarget result = this.callTarget;
      if (CompilerDirectives.injectBranchProbability(0.9999, result != null)) {
         return result;
      } else {
         initBranch.enter();
         return (CallTarget)this.ensureInitializedCall();
      }
   }

   public CallTarget getConstructTarget(BranchProfile initBranch) {
      CallTarget result = this.constructTarget;
      if (CompilerDirectives.injectBranchProbability(0.9999, result != null)) {
         return result;
      } else {
         initBranch.enter();
         return (CallTarget)this.ensureInitializedConstruct();
      }
   }

   public CallTarget getConstructNewTarget(BranchProfile initBranch) {
      CallTarget result = this.constructNewTarget;
      if (CompilerDirectives.injectBranchProbability(0.9999, result != null)) {
         return result;
      } else {
         initBranch.enter();
         return (CallTarget)this.ensureInitializedConstructNewTarget();
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object ensureInitializedCall() {
      return this.ensureInitialized(JSFunctionData.Target.Call);
   }

   @CompilerDirectives.TruffleBoundary
   private Object ensureInitializedConstruct() {
      return this.ensureInitialized(JSFunctionData.Target.Construct);
   }

   @CompilerDirectives.TruffleBoundary
   private Object ensureInitializedConstructNewTarget() {
      return this.ensureInitialized(JSFunctionData.Target.ConstructNewTarget);
   }

   public CallTarget setCallTarget(CallTarget callTarget) {
      return this.setAndGetCallTarget(UPDATER_CALL_TARGET, callTarget);
   }

   public CallTarget setConstructTarget(CallTarget constructTarget) {
      return this.setAndGetCallTarget(UPDATER_CONSTRUCT_TARGET, constructTarget);
   }

   public CallTarget setConstructNewTarget(CallTarget constructNewTarget) {
      return this.setAndGetCallTarget(UPDATER_CONSTRUCT_NEW_TARGET, constructNewTarget);
   }

   public CallTarget getRootTarget() {
      return this.rootNode.getCallTarget();
   }

   public RootNode getRootNode() {
      return this.rootNode;
   }

   public RootNode setRootNode(RootNode rootNode) {
      CompilerAsserts.neverPartOfCompilation();
      Objects.requireNonNull(rootNode);
      if (UPDATER_ROOT_TARGET.compareAndSet(this, null, rootNode)) {
         return rootNode;
      } else {
         throw Errors.shouldNotReachHere("RootNode created more than once");
      }
   }

   private CallTarget setAndGetCallTarget(AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> updater, CallTarget newTarget) {
      CompilerAsserts.neverPartOfCompilation();
      Objects.requireNonNull(newTarget);
      return updater.compareAndSet(this, null, newTarget) ? newTarget : updater.get(this);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "(" + this.name + ")";
   }

   public void setLazyInit(JSFunctionData.Initializer lazyInit) {
      assert this.lazyInit == null || this.rootNode != null && lazyInit instanceof JSFunctionData.CallTargetInitializer;

      this.lazyInit = lazyInit;
   }

   public boolean hasLazyInit() {
      return this.lazyInit != null;
   }

   private CallTarget ensureInitialized(JSFunctionData.Target target) {
      CompilerAsserts.neverPartOfCompilation();
      JSFunctionData.Initializer init = this.lazyInit;
      RootNode root = this.rootNode;
      if (root == null) {
         synchronized (this.context) {
            root = this.rootNode;
            if (root == null) {
               init.initializeRoot(this);
               root = this.rootNode;
            }
         }
      }

      assert root != null;

      AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> updater = target.getUpdater();
      CallTarget result = updater.get(this);
      if (result != null) {
         return result;
      } else {
         JSFunctionData.CallTargetInitializer callTargetInit;
         if (init instanceof JSFunctionData.CallTargetInitializer) {
            callTargetInit = (JSFunctionData.CallTargetInitializer)init;
         } else {
            callTargetInit = (JSFunctionData.CallTargetInitializer)root;
         }

         callTargetInit.initializeCallTarget(this, target, root.getCallTarget());
         result = updater.get(this);
         return Objects.requireNonNull(result);
      }
   }

   public void materialize() {
      CompilerAsserts.neverPartOfCompilation();

      assert !this.isBuiltin();

      if (this.rootNode == null) {
         JSFunctionData.Initializer init = this.lazyInit;
         synchronized (this.context) {
            if (this.rootNode == null) {
               init.initializeRoot(this);
            }
         }
      }

      this.rootNode.getCallTarget();
   }

   public interface CallTargetInitializer extends JSFunctionData.Initializer {
      void initializeCallTarget(JSFunctionData functionData, JSFunctionData.Target target, CallTarget rootTarget);

      @Override
      default void initializeRoot(JSFunctionData functionData) {
         if (!<unrepresentable>.$assertionsDisabled && functionData.rootNode == null) {
            throw new AssertionError();
         }
      }

      default void initializeCallTargets(JSFunctionData functionData) {
         CallTarget rootTarget = functionData.rootNode.getCallTarget();
         this.initializeCallTarget(functionData, JSFunctionData.Target.Call, rootTarget);
         this.initializeCallTarget(functionData, JSFunctionData.Target.Construct, rootTarget);
         this.initializeCallTarget(functionData, JSFunctionData.Target.ConstructNewTarget, rootTarget);
      }

      static {
         if (<unrepresentable>.$assertionsDisabled) {
         }
      }
   }

   public interface Initializer {
      void initializeRoot(JSFunctionData functionData);
   }

   public static enum Target {
      Call(JSFunctionData.UPDATER_CALL_TARGET),
      Construct(JSFunctionData.UPDATER_CONSTRUCT_TARGET),
      ConstructNewTarget(JSFunctionData.UPDATER_CONSTRUCT_NEW_TARGET);

      private final AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> updater;

      private Target(AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> updater) {
         this.updater = updater;
      }

      AtomicReferenceFieldUpdater<JSFunctionData, CallTarget> getUpdater() {
         return this.updater;
      }
   }
}
