package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.util.Pair;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotBuiltins.class)
public final class PolyglotBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotBuiltins.PolyglotConstructNode.class)
   static final class PolyglotConstructNodeGen extends PolyglotBuiltins.PolyglotConstructNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ExportValueNode new_exportValue_;
      @Node.Child
      private InteropLibrary new_interop_;

      private PolyglotConstructNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
               TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
               return this.doNew(arguments0Value__, arguments1Value__, this.new_exportValue_, this.new_interop_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_, arguments1Value__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if (!JSGuards.isTruffleObject(arguments0Value_)) {
                  return this.unsupported(arguments0Value_, arguments1Value__);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments1Value instanceof Object[]) {
               Object[] arguments1Value_ = (Object[])arguments1Value;
               if (arguments0Value instanceof TruffleObject) {
                  TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
                  this.new_exportValue_ = super.insert(ExportValueNode.create());
                  this.new_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doNew(arguments0Value_, arguments1Value_, this.new_exportValue_, this.new_interop_);
               }

               if (!JSGuards.isTruffleObject(arguments0Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupported(arguments0Value, arguments1Value_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doNew", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.new_exportValue_, this.new_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotConstructNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotConstructNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotEvalFileNode.class)
   static final class PolyglotEvalFileNodeGen extends PolyglotBuiltins.PolyglotEvalFileNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TruffleString.ToJavaStringNode toJavaStringNode;
      @Node.Child
      private IndirectCallNode callNode;
      @CompilerDirectives.CompilationFinal
      private TruffleString evalFileCachedLanguage_cachedLanguage_;
      @CompilerDirectives.CompilationFinal
      private Pair<String, String> evalFileCachedLanguage_languagePair_;

      private PolyglotEvalFileNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && arguments0Value__.equals(this.evalFileCachedLanguage_cachedLanguage_)) {
                  return this.evalFileCachedLanguage(
                     arguments0Value__,
                     arguments1Value__,
                     this.evalFileCachedLanguage_cachedLanguage_,
                     this.toJavaStringNode,
                     this.evalFileCachedLanguage_languagePair_,
                     this.callNode
                  );
               }

               if ((state_0 & 2) != 0) {
                  return this.evalFileString(arguments0Value__, arguments1Value__, this.toJavaStringNode, this.callNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isString(arguments0Value_) && JSGuards.isString(arguments1Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         } else {
            return this.eval(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (exclude == 0) {
                     boolean EvalFileCachedLanguage_duplicateFound_ = false;
                     if ((state_0 & 1) != 0 && arguments0Value_.equals(this.evalFileCachedLanguage_cachedLanguage_)) {
                        EvalFileCachedLanguage_duplicateFound_ = true;
                     }

                     if (!EvalFileCachedLanguage_duplicateFound_ && (state_0 & 1) == 0) {
                        this.evalFileCachedLanguage_cachedLanguage_ = arguments0Value_;
                        this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
                        this.evalFileCachedLanguage_languagePair_ = this.getLanguageIdAndMimeType(this.toJavaStringNode, arguments0Value_);
                        this.callNode = super.insert(this.callNode == null ? IndirectCallNode.create() : this.callNode);
                        this.state_0_ = state_0 |= 1;
                        EvalFileCachedLanguage_duplicateFound_ = true;
                     }

                     if (EvalFileCachedLanguage_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.evalFileCachedLanguage(
                           arguments0Value_,
                           arguments1Value_,
                           this.evalFileCachedLanguage_cachedLanguage_,
                           this.toJavaStringNode,
                           this.evalFileCachedLanguage_languagePair_,
                           this.callNode
                        );
                     }
                  }

                  this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
                  this.callNode = super.insert(this.callNode == null ? IndirectCallNode.create() : this.callNode);
                  int var17;
                  this.exclude_ = var17 = exclude | 1;
                  state_0 &= -2;
                  int var16;
                  this.state_0_ = var16 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.evalFileString(arguments0Value_, arguments1Value_, this.toJavaStringNode, this.callNode);
               }
            }

            if (JSGuards.isString(arguments0Value) && JSGuards.isString(arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.eval(arguments0Value, arguments1Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"evalFileCachedLanguage", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(
               Arrays.asList(this.evalFileCachedLanguage_cachedLanguage_, this.toJavaStringNode, this.evalFileCachedLanguage_languagePair_, this.callNode)
            );
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"evalFileString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toJavaStringNode, this.callNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"eval", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotEvalFileNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotEvalFileNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotEvalNode.class)
   static final class PolyglotEvalNodeGen extends PolyglotBuiltins.PolyglotEvalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TruffleString.ToJavaStringNode toJavaStringNode;
      @Node.Child
      private IndirectCallNode callNode;
      @CompilerDirectives.CompilationFinal
      private TruffleString evalCachedLanguage_cachedLanguage_;
      @CompilerDirectives.CompilationFinal
      private Pair<String, String> evalCachedLanguage_languagePair_;

      private PolyglotEvalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && arguments0Value__.equals(this.evalCachedLanguage_cachedLanguage_)) {
                  return this.evalCachedLanguage(
                     arguments0Value__,
                     arguments1Value__,
                     this.evalCachedLanguage_cachedLanguage_,
                     this.toJavaStringNode,
                     this.evalCachedLanguage_languagePair_,
                     this.callNode
                  );
               }

               if ((state_0 & 2) != 0) {
                  return this.evalString(arguments0Value__, arguments1Value__, this.toJavaStringNode, this.callNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isString(arguments0Value_) && JSGuards.isString(arguments1Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         } else {
            return this.eval(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (exclude == 0) {
                     boolean EvalCachedLanguage_duplicateFound_ = false;
                     if ((state_0 & 1) != 0 && arguments0Value_.equals(this.evalCachedLanguage_cachedLanguage_)) {
                        EvalCachedLanguage_duplicateFound_ = true;
                     }

                     if (!EvalCachedLanguage_duplicateFound_ && (state_0 & 1) == 0) {
                        this.evalCachedLanguage_cachedLanguage_ = arguments0Value_;
                        this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
                        this.evalCachedLanguage_languagePair_ = this.getLanguageIdAndMimeType(this.toJavaStringNode, arguments0Value_);
                        this.callNode = super.insert(this.callNode == null ? IndirectCallNode.create() : this.callNode);
                        this.state_0_ = state_0 |= 1;
                        EvalCachedLanguage_duplicateFound_ = true;
                     }

                     if (EvalCachedLanguage_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.evalCachedLanguage(
                           arguments0Value_,
                           arguments1Value_,
                           this.evalCachedLanguage_cachedLanguage_,
                           this.toJavaStringNode,
                           this.evalCachedLanguage_languagePair_,
                           this.callNode
                        );
                     }
                  }

                  this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
                  this.callNode = super.insert(this.callNode == null ? IndirectCallNode.create() : this.callNode);
                  int var17;
                  this.exclude_ = var17 = exclude | 1;
                  state_0 &= -2;
                  int var16;
                  this.state_0_ = var16 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.evalString(arguments0Value_, arguments1Value_, this.toJavaStringNode, this.callNode);
               }
            }

            if (JSGuards.isString(arguments0Value) && JSGuards.isString(arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.eval(arguments0Value, arguments1Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"evalCachedLanguage", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.evalCachedLanguage_cachedLanguage_, this.toJavaStringNode, this.evalCachedLanguage_languagePair_, this.callNode));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"evalString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toJavaStringNode, this.callNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"eval", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotEvalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotEvalNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotExecuteNode.class)
   static final class PolyglotExecuteNodeGen extends PolyglotBuiltins.PolyglotExecuteNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ExportValueNode execute_exportValue_;
      @Node.Child
      private InteropLibrary execute_interop_;

      private PolyglotExecuteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
               TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
               return this.execute(arguments0Value__, arguments1Value__, this.execute_exportValue_, this.execute_interop_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_, arguments1Value__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if (!JSGuards.isTruffleObject(arguments0Value_)) {
                  return this.unsupported(arguments0Value_, arguments1Value__);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments1Value instanceof Object[]) {
               Object[] arguments1Value_ = (Object[])arguments1Value;
               if (arguments0Value instanceof TruffleObject) {
                  TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
                  this.execute_exportValue_ = super.insert(ExportValueNode.create());
                  this.execute_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.execute(arguments0Value_, arguments1Value_, this.execute_exportValue_, this.execute_interop_);
               }

               if (!JSGuards.isTruffleObject(arguments0Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupported(arguments0Value, arguments1Value_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"execute", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.execute_exportValue_, this.execute_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotExecuteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotExecuteNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotExportNode.class)
   static final class PolyglotExportNodeGen extends PolyglotBuiltins.PolyglotExportNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary interop;

      private PolyglotExportNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.doString(arguments0Value__, arguments1Value_, this.interop);
            }

            if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleObject) {
               TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
               if (!JSGuards.isString(arguments0Value__)) {
                  return this.doMaybeUnbox(arguments0Value__, arguments1Value_, this.interop);
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isString(arguments0Value_)) {
               return this.doInvalid(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               int var13;
               this.state_0_ = var13 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doString(arguments0Value_, arguments1Value, this.interop);
            } else {
               if (arguments0Value instanceof TruffleObject) {
                  TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
                  if (!JSGuards.isString(arguments0Value_)) {
                     this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                     int var12;
                     this.state_0_ = var12 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doMaybeUnbox(arguments0Value_, arguments1Value, this.interop);
                  }
               }

               if (JSGuards.isString(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               } else {
                  int var11;
                  this.state_0_ = var11 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doInvalid(arguments0Value, arguments1Value);
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doMaybeUnbox", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doInvalid", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotExportNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotExportNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotGetSizeNode.class)
   static final class PolyglotGetSizeNodeGen extends PolyglotBuiltins.PolyglotGetSizeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary getSize_interop_;

      private PolyglotGetSizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.getSize(arguments0Value__, this.getSize_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleObject)) {
               if (JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value);
            }

            TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
            this.getSize_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.getSize(arguments0Value_, this.getSize_interop_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"getSize", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getSize_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotGetSizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotGetSizeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotHasKeysNode.class)
   static final class PolyglotHasKeysNodeGen extends PolyglotBuiltins.PolyglotHasKeysNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary hasKeys_interop_;

      private PolyglotHasKeysNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.hasKeys(arguments0Value__, this.hasKeys_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.hasKeys(arguments0Value__, this.hasKeys_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleObject)) {
               if (JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value);
            }

            TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
            this.hasKeys_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.hasKeys(arguments0Value_, this.hasKeys_interop_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"hasKeys", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.hasKeys_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotHasKeysNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotHasKeysNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotHasSizeNode.class)
   static final class PolyglotHasSizeNodeGen extends PolyglotBuiltins.PolyglotHasSizeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary truffleObject_interop_;

      private PolyglotHasSizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_x = (TruffleObject)arguments0Value;
               this.truffleObject_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.truffleObject(arguments0Value_x, this.truffleObject_interop_);
            }

            if (!JSGuards.isJavaPrimitive(arguments0Value)) {
               if (JSGuards.isTruffleObject(arguments0Value) || JSGuards.isJavaPrimitive(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value);
            }

            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = this.primitive(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.truffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"primitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotHasSizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotHasSizeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotImportNode.class)
   static final class PolyglotImportNodeGen extends PolyglotBuiltins.PolyglotImportNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private ImportValueNode importValue;

      private PolyglotImportNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.doString(arguments0Value__, this.interop, this.importValue);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleObject) {
               TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
               if (!JSGuards.isString(arguments0Value__)) {
                  return this.doMaybeUnbox(arguments0Value__, this.interop, this.importValue);
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isString(arguments0Value_) && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.doInvalid(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doString(arguments0Value_, this.interop, this.importValue);
            } else {
               if (arguments0Value instanceof TruffleObject) {
                  TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
                  if (!JSGuards.isString(arguments0Value_)) {
                     this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                     this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                     int var11;
                     this.state_0_ = var11 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doMaybeUnbox(arguments0Value_, this.interop, this.importValue);
                  }
               }

               if (JSGuards.isString(arguments0Value) || JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               } else {
                  int var10;
                  this.state_0_ = var10 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doInvalid(arguments0Value);
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop, this.importValue));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doMaybeUnbox", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop, this.importValue));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doInvalid", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotImportNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotImportNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.class)
   static final class PolyglotIsBoxedPrimitiveNodeGen extends PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data truffleObject0_cache;

      private PolyglotIsBoxedPrimitiveNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            if ((state_0 & 1) != 0) {
               for (PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.interop_.accepts(arguments0Value__)) {
                     return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value__, s0_.interop_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.truffleObject1Boundary(state_0, arguments0Value__);
            }
         }

         if ((state_0 & 12) != 0) {
            if ((state_0 & 4) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.primitive(arguments0Value_);
            }

            if ((state_0 & 8) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.unsupported(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object truffleObject1Boundary(int state_0, TruffleObject arguments0Value__) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Boolean var6;
         try {
            InteropLibrary truffleObject1_interop__ = PolyglotBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value__);
            var6 = PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value__, truffleObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            if ((state_0 & 1) != 0) {
               for (PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.interop_.accepts(arguments0Value__)) {
                     return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value__, s0_.interop_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.truffleObject1Boundary0(state_0, arguments0Value__);
            }
         }

         if ((state_0 & 12) != 0) {
            if ((state_0 & 4) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.primitive(arguments0Value_);
            }

            if ((state_0 & 8) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.unsupported(arguments0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private boolean truffleObject1Boundary0(int state_0, TruffleObject arguments0Value__) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         boolean var6;
         try {
            InteropLibrary truffleObject1_interop__ = PolyglotBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value__);
            var6 = PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value__, truffleObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.interop_.accepts(arguments0Value_)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 5) {
                     s0_ = super.insert(new PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data(this.truffleObject0_cache));
                     s0_.interop_ = s0_.insertAccessor(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value_));
                     VarHandle.storeStoreFence();
                     this.truffleObject0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value_, s0_.interop_);
                  }
               }

               InteropLibrary truffleObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  truffleObject1_interop__ = PolyglotBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.truffleObject0_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.truffleObject(arguments0Value_, truffleObject1_interop__);
               } finally {
                  encapsulating_.set(prev_);
               }
            } else if (JSGuards.isJavaPrimitive(arguments0Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.primitive(arguments0Value);
            } else if (JSGuards.isTruffleObject(arguments0Value) || JSGuards.isJavaPrimitive(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               int var22;
               this.state_0_ = var22 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.unsupported(arguments0Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"primitive", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(PolyglotBuiltins.PolyglotIsBoxedPrimitiveNode.class)
      private static final class TruffleObject0Data extends Node {
         @Node.Child
         PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         TruffleObject0Data(PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.TruffleObject0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotIsExecutableNode.class)
   static final class PolyglotIsExecutableNodeGen extends PolyglotBuiltins.PolyglotIsExecutableNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary truffleObject_interop_;

      private PolyglotIsExecutableNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsExecutableNode.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsExecutableNode.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsExecutableNode.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsExecutableNode.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsExecutableNode.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsExecutableNode.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_x = (TruffleObject)arguments0Value;
               this.truffleObject_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsExecutableNode.truffleObject(arguments0Value_x, this.truffleObject_interop_);
            }

            if (!JSGuards.isJavaPrimitive(arguments0Value)) {
               if (JSGuards.isTruffleObject(arguments0Value) || JSGuards.isJavaPrimitive(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsExecutableNode.unsupported(arguments0Value);
            }

            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = PolyglotBuiltins.PolyglotIsExecutableNode.primitive(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.truffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"primitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotIsExecutableNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotIsExecutableNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotIsInstantiableNode.class)
   static final class PolyglotIsInstantiableNodeGen extends PolyglotBuiltins.PolyglotIsInstantiableNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary isInstantiable_interop_;

      private PolyglotIsInstantiableNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsInstantiableNode.isInstantiable(arguments0Value__, this.isInstantiable_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return PolyglotBuiltins.PolyglotIsInstantiableNode.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsInstantiableNode.isInstantiable(arguments0Value__, this.isInstantiable_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return PolyglotBuiltins.PolyglotIsInstantiableNode.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleObject)) {
               if (JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsInstantiableNode.unsupported(arguments0Value);
            }

            TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
            this.isInstantiable_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = PolyglotBuiltins.PolyglotIsInstantiableNode.isInstantiable(arguments0Value_, this.isInstantiable_interop_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"isInstantiable", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isInstantiable_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotIsInstantiableNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotIsInstantiableNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotIsNullNode.class)
   static final class PolyglotIsNullNodeGen extends PolyglotBuiltins.PolyglotIsNullNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary truffleObject_interop_;

      private PolyglotIsNullNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsNullNode.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsNullNode.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsNullNode.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return PolyglotBuiltins.PolyglotIsNullNode.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsNullNode.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return PolyglotBuiltins.PolyglotIsNullNode.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_x = (TruffleObject)arguments0Value;
               this.truffleObject_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsNullNode.truffleObject(arguments0Value_x, this.truffleObject_interop_);
            }

            if (!JSGuards.isJavaPrimitive(arguments0Value)) {
               if (JSGuards.isTruffleObject(arguments0Value) || JSGuards.isJavaPrimitive(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotIsNullNode.unsupported(arguments0Value);
            }

            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = PolyglotBuiltins.PolyglotIsNullNode.primitive(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.truffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"primitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotIsNullNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotIsNullNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotKeysNode.class)
   static final class PolyglotKeysNodeGen extends PolyglotBuiltins.PolyglotKeysNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private PolyglotKeysNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.keys(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof TruffleObject) {
            TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.keys(arguments0Value_);
         } else if (!JSGuards.isTruffleObject(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.unsupported(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"keys", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotKeysNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotKeysNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotReadNode.class)
   static final class PolyglotReadNodeGen extends PolyglotBuiltins.PolyglotReadNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ImportValueNode importValue;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private InteropLibrary unsupportedKey_keyInterop_;

      private PolyglotReadNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.arrayElementInt(arguments0Value__, arguments1Value_, this.importValue, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 15) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.member(arguments0Value__, arguments1Value__, this.importValue, this.interop);
            }

            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.arrayElementInt(arguments0Value__, arguments1Value__, this.importValue, this.interop);
            }

            if ((state_0 & 4) != 0 && arguments1Value_ instanceof Number) {
               Number arguments1Value__ = (Number)arguments1Value_;
               if (JSGuards.isNumber(arguments1Value__)) {
                  return this.arrayElement(arguments0Value__, arguments1Value__, this.importValue, this.interop);
               }
            }

            if ((state_0 & 8) != 0 && !JSGuards.isString(arguments1Value_) && !JSGuards.isNumber(arguments1Value_)) {
               return this.unsupportedKey(arguments0Value__, arguments1Value_, this.importValue, this.interop, this.unsupportedKey_keyInterop_);
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 15) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_, arguments1Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 15) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  int var18;
                  this.state_0_ = var18 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.member(arguments0Value_, arguments1Value_, this.importValue, this.interop);
               }

               if (exclude == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  int var17;
                  this.state_0_ = var17 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.arrayElementInt(arguments0Value_, arguments1Value_, this.importValue, this.interop);
               }

               if (arguments1Value instanceof Number) {
                  Number arguments1Value_ = (Number)arguments1Value;
                  if (JSGuards.isNumber(arguments1Value_)) {
                     this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                     this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                     int var19;
                     this.exclude_ = var19 = exclude | 1;
                     state_0 &= -3;
                     int var16;
                     this.state_0_ = var16 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.arrayElement(arguments0Value_, arguments1Value_, this.importValue, this.interop);
                  }
               }

               if (!JSGuards.isString(arguments1Value) && !JSGuards.isNumber(arguments1Value)) {
                  this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  this.unsupportedKey_keyInterop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var14;
                  this.state_0_ = var14 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupportedKey(arguments0Value_, arguments1Value, this.importValue, this.interop, this.unsupportedKey_keyInterop_);
               }
            }

            if (JSGuards.isTruffleObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var13;
               this.state_0_ = var13 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value, arguments1Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"member", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.importValue, this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"arrayElementInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.importValue, this.interop));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"arrayElement", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.importValue, this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"unsupportedKey", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.importValue, this.interop, this.unsupportedKey_keyInterop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotReadNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotReadNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotRemoveNode.class)
   static final class PolyglotRemoveNodeGen extends PolyglotBuiltins.PolyglotRemoveNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private TruffleString.ToJavaStringNode member_toJavaStringNode_;
      @Node.Child
      private InteropLibrary unsupportedKey_keyInterop_;
      @Node.Child
      private TruffleString.ToJavaStringNode unsupportedKey_toJavaStringNode_;

      private PolyglotRemoveNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.arrayElementInt(arguments0Value__, arguments1Value_, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 15) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.member(arguments0Value__, arguments1Value__, this.interop, this.member_toJavaStringNode_);
            }

            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.arrayElementInt(arguments0Value__, arguments1Value__, this.interop);
            }

            if ((state_0 & 4) != 0 && arguments1Value_ instanceof Number) {
               Number arguments1Value__ = (Number)arguments1Value_;
               if (JSGuards.isNumber(arguments1Value__)) {
                  return this.arrayElement(arguments0Value__, arguments1Value__, this.interop);
               }
            }

            if ((state_0 & 8) != 0 && !JSGuards.isString(arguments1Value_) && !JSGuards.isNumber(arguments1Value_)) {
               return this.unsupportedKey(
                  arguments0Value__, arguments1Value_, this.interop, this.unsupportedKey_keyInterop_, this.unsupportedKey_toJavaStringNode_
               );
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 8) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            return (state_0 & 21) == 0 && (state_0 & 23) != 0
               ? this.executeBoolean_int2(state_0, frameValue)
               : this.executeBoolean_generic3(state_0, frameValue);
         }
      }

      private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, var6.getResult()));
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.arrayElementInt(arguments0Value__, arguments1Value_, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 7) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.member(arguments0Value__, arguments1Value__, this.interop, this.member_toJavaStringNode_);
            }

            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.arrayElementInt(arguments0Value__, arguments1Value__, this.interop);
            }

            if ((state_0 & 4) != 0 && arguments1Value_ instanceof Number) {
               Number arguments1Value__ = (Number)arguments1Value_;
               if (JSGuards.isNumber(arguments1Value__)) {
                  return this.arrayElement(arguments0Value__, arguments1Value__, this.interop);
               }
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return this.unsupported(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 8) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  this.member_toJavaStringNode_ = super.insert(TruffleString.ToJavaStringNode.create());
                  int var18;
                  this.state_0_ = var18 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.member(arguments0Value_, arguments1Value_, this.interop, this.member_toJavaStringNode_);
               }

               if (exclude == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  int var17;
                  this.state_0_ = var17 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.arrayElementInt(arguments0Value_, arguments1Value_, this.interop);
               }

               if (arguments1Value instanceof Number) {
                  Number arguments1Value_ = (Number)arguments1Value;
                  if (JSGuards.isNumber(arguments1Value_)) {
                     this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                     int var19;
                     this.exclude_ = var19 = exclude | 1;
                     state_0 &= -3;
                     int var16;
                     this.state_0_ = var16 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.arrayElement(arguments0Value_, arguments1Value_, this.interop);
                  }
               }

               if (!JSGuards.isString(arguments1Value) && !JSGuards.isNumber(arguments1Value)) {
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  this.unsupportedKey_keyInterop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  this.unsupportedKey_toJavaStringNode_ = super.insert(TruffleString.ToJavaStringNode.create());
                  int var14;
                  this.state_0_ = var14 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupportedKey(
                     arguments0Value_, arguments1Value, this.interop, this.unsupportedKey_keyInterop_, this.unsupportedKey_toJavaStringNode_
                  );
               }
            }

            if (JSGuards.isTruffleObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var13;
               this.state_0_ = var13 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value, arguments1Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"member", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop, this.member_toJavaStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"arrayElementInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"arrayElement", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"unsupportedKey", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop, this.unsupportedKey_keyInterop_, this.unsupportedKey_toJavaStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotRemoveNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotRemoveNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotToJSValueNode.class)
   static final class PolyglotToJSValueNodeGen extends PolyglotBuiltins.PolyglotToJSValueNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary toJSValue0_interop_;

      private PolyglotToJSValueNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.toJSValue(arguments0Value__, this.toJSValue0_interop_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
            return PolyglotBuiltins.PolyglotToJSValueNode.toJSValue(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleObject)) {
               if (JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return PolyglotBuiltins.PolyglotToJSValueNode.toJSValue(arguments0Value);
            }

            TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
            this.toJSValue0_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.toJSValue(arguments0Value_, this.toJSValue0_interop_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"toJSValue", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toJSValue0_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toJSValue", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotToJSValueNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotToJSValueNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotToPolyglotValueNode.class)
   static final class PolyglotToPolyglotValueNodeGen extends PolyglotBuiltins.PolyglotToPolyglotValueNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private PolyglotToPolyglotValueNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.execute(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"execute", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotToPolyglotValueNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotToPolyglotValueNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotUnboxValueNode.class)
   static final class PolyglotUnboxValueNodeGen extends PolyglotBuiltins.PolyglotUnboxValueNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary truffleObject_interop_;

      private PolyglotUnboxValueNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.truffleObject(arguments0Value__, this.truffleObject_interop_);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0 && JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.primitive(arguments0Value_);
               }

               if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
                  return this.unsupported(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 3) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 4) != 0 && !JSGuards.isTruffleObject(arguments0Value_) && !JSGuards.isJavaPrimitive(arguments0Value_)) {
               return this.unsupported(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 3) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleObject arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleObject) {
               arguments0Value_ = (TruffleObject)arguments0Value;
               this.truffleObject_interop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.truffleObject(arguments0Value_, this.truffleObject_interop_);
            }

            if (!JSGuards.isJavaPrimitive(arguments0Value)) {
               if (JSGuards.isTruffleObject(arguments0Value) || JSGuards.isJavaPrimitive(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value);
            }

            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = (TruffleObject)this.primitive(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"truffleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.truffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"primitive", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotUnboxValueNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotUnboxValueNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PolyglotBuiltins.PolyglotWriteNode.class)
   static final class PolyglotWriteNodeGen extends PolyglotBuiltins.PolyglotWriteNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ExportValueNode exportValue;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private TruffleString.ToJavaStringNode member_toJavaStringNode_;
      @Node.Child
      private InteropLibrary unsupportedKey_keyInterop_;
      @Node.Child
      private TruffleString.ToJavaStringNode unsupportedKey_toJavaStringNode_;

      private PolyglotWriteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var7.getResult(), arguments2Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleObject) {
            TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
            return this.arrayElementInt(arguments0Value__, arguments1Value_, arguments2Value_, this.exportValue, this.interop);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 15) != 0 && arguments0Value_ instanceof TruffleObject) {
               TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
               if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  return this.member(arguments0Value__, arguments1Value__, arguments2Value_, this.exportValue, this.interop, this.member_toJavaStringNode_);
               }

               if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
                  int arguments1Value__ = (Integer)arguments1Value_;
                  return this.arrayElementInt(arguments0Value__, arguments1Value__, arguments2Value_, this.exportValue, this.interop);
               }

               if ((state_0 & 4) != 0 && arguments1Value_ instanceof Number) {
                  Number arguments1Value__ = (Number)arguments1Value_;
                  if (JSGuards.isNumber(arguments1Value__)) {
                     return this.arrayElement(arguments0Value__, arguments1Value__, arguments2Value_, this.exportValue, this.interop);
                  }
               }

               if ((state_0 & 8) != 0 && !JSGuards.isString(arguments1Value_) && !JSGuards.isNumber(arguments1Value_)) {
                  return this.unsupportedKey(
                     arguments0Value__,
                     arguments1Value_,
                     arguments2Value_,
                     this.exportValue,
                     this.interop,
                     this.unsupportedKey_keyInterop_,
                     this.unsupportedKey_toJavaStringNode_
                  );
               }
            }

            if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 15) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 16) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.unsupported(arguments0Value_, arguments1Value_, arguments2Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 15) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleObject) {
               TruffleObject arguments0Value_ = (TruffleObject)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  this.member_toJavaStringNode_ = super.insert(TruffleString.ToJavaStringNode.create());
                  int var19;
                  this.state_0_ = var19 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.member(arguments0Value_, arguments1Value_, arguments2Value, this.exportValue, this.interop, this.member_toJavaStringNode_);
               }

               if (exclude == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  int var18;
                  this.state_0_ = var18 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.arrayElementInt(arguments0Value_, arguments1Value_, arguments2Value, this.exportValue, this.interop);
               }

               if (arguments1Value instanceof Number) {
                  Number arguments1Value_ = (Number)arguments1Value;
                  if (JSGuards.isNumber(arguments1Value_)) {
                     this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                     this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                     int var20;
                     this.exclude_ = var20 = exclude | 1;
                     state_0 &= -3;
                     int var17;
                     this.state_0_ = var17 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.arrayElement(arguments0Value_, arguments1Value_, arguments2Value, this.exportValue, this.interop);
                  }
               }

               if (!JSGuards.isString(arguments1Value) && !JSGuards.isNumber(arguments1Value)) {
                  this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                  this.interop = super.insert(this.interop == null ? PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  this.unsupportedKey_keyInterop_ = super.insert(PolyglotBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  this.unsupportedKey_toJavaStringNode_ = super.insert(TruffleString.ToJavaStringNode.create());
                  int var15;
                  this.state_0_ = var15 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.unsupportedKey(
                     arguments0Value_,
                     arguments1Value,
                     arguments2Value,
                     this.exportValue,
                     this.interop,
                     this.unsupportedKey_keyInterop_,
                     this.unsupportedKey_toJavaStringNode_
                  );
               }
            }

            if (JSGuards.isTruffleObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.unsupported(arguments0Value, arguments1Value, arguments2Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"member", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.exportValue, this.interop, this.member_toJavaStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"arrayElementInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.exportValue, this.interop));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"arrayElement", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.exportValue, this.interop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"unsupportedKey", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.exportValue, this.interop, this.unsupportedKey_keyInterop_, this.unsupportedKey_toJavaStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"unsupported", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static PolyglotBuiltins.PolyglotWriteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PolyglotBuiltinsFactory.PolyglotWriteNodeGen(context, builtin, arguments);
      }
   }
}
