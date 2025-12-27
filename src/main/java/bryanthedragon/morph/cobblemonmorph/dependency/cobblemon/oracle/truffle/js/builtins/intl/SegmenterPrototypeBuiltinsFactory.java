package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SegmenterPrototypeBuiltins.class)
public final class SegmenterPrototypeBuiltinsFactory {
   @GeneratedBy(SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode.class)
   public static final class JSSegmenterResolvedOptionsNodeGen
      extends SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSegmenterResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmenterObject) {
            JSSegmenterObject arguments0Value__ = (JSSegmenterObject)arguments0Value_;
            return this.doResolvedOptions(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSSegmenter(arguments0Value_)) {
            return this.doResolvedOptions(arguments0Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSegmenterObject) {
            JSSegmenterObject arguments0Value_ = (JSSegmenterObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doResolvedOptions(arguments0Value_);
         } else if (!JSGuards.isJSSegmenter(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doResolvedOptions(arguments0Value);
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
         Object[] s = new Object[]{"doResolvedOptions", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doResolvedOptions", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SegmenterPrototypeBuiltins.JSSegmenterResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SegmenterPrototypeBuiltinsFactory.JSSegmenterResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SegmenterPrototypeBuiltins.JSSegmenterSegmentNode.class)
   public static final class JSSegmenterSegmentNodeGen extends SegmenterPrototypeBuiltins.JSSegmenterSegmentNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode segmenter_toStringNode_;

      private JSSegmenterSegmentNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmenterObject) {
               JSSegmenterObject arguments0Value__ = (JSSegmenterObject)arguments0Value_;
               return this.doSegmenter(arguments0Value__, arguments1Value_, this.segmenter_toStringNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSegmenter(arguments0Value_)) {
               return this.doOther(arguments0Value_, arguments1Value_);
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

         Object var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSSegmenterObject)) {
               if (JSGuards.isJSSegmenter(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               }

               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doOther(arguments0Value, arguments1Value);
            }

            JSSegmenterObject arguments0Value_ = (JSSegmenterObject)arguments0Value;
            this.segmenter_toStringNode_ = super.insert(JSToStringNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doSegmenter(arguments0Value_, arguments1Value, this.segmenter_toStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
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
         Object[] s = new Object[]{"doSegmenter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.segmenter_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SegmenterPrototypeBuiltins.JSSegmenterSegmentNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SegmenterPrototypeBuiltinsFactory.JSSegmenterSegmentNodeGen(context, builtin, arguments);
      }
   }
}
