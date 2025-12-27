package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.CreateSegmentDataObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentsObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SegmentsPrototypeBuiltins.class)
public final class SegmentsPrototypeBuiltinsFactory {
   @GeneratedBy(SegmentsPrototypeBuiltins.SegmentsContainingNode.class)
   public static final class SegmentsContainingNodeGen extends SegmentsPrototypeBuiltins.SegmentsContainingNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.SegmentsData segments_cache;

      private SegmentsContainingNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmentsObject) {
               JSSegmentsObject arguments0Value__ = (JSSegmentsObject)arguments0Value_;
               SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.SegmentsData s0_ = this.segments_cache;
               if (s0_ != null) {
                  return this.doSegments(arguments0Value__, arguments1Value_, s0_.toIntegerNode_, s0_.createResultNode_, s0_.toJavaStringNode_);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSegments(arguments0Value_)) {
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

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSSegmentsObject)) {
               if (JSGuards.isJSSegments(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
               }

               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doOther(arguments0Value, arguments1Value);
            }

            JSSegmentsObject arguments0Value_ = (JSSegmentsObject)arguments0Value;
            SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.SegmentsData s0_ = super.insert(
               new SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.SegmentsData()
            );
            s0_.toIntegerNode_ = s0_.insertAccessor(JSToIntegerAsIntNode.create());
            s0_.createResultNode_ = s0_.insertAccessor(CreateSegmentDataObjectNode.create(this.getContext()));
            s0_.toJavaStringNode_ = s0_.insertAccessor(TruffleString.ToJavaStringNode.create());
            VarHandle.storeStoreFence();
            this.segments_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doSegments(arguments0Value_, arguments1Value, s0_.toIntegerNode_, s0_.createResultNode_, s0_.toJavaStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
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
         Object[] s = new Object[]{"doSegments", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.SegmentsData s0_ = this.segments_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toIntegerNode_, s0_.createResultNode_, s0_.toJavaStringNode_));
            }

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

      public static SegmentsPrototypeBuiltins.SegmentsContainingNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(SegmentsPrototypeBuiltins.SegmentsContainingNode.class)
      private static final class SegmentsData extends Node {
         @Node.Child
         JSToIntegerAsIntNode toIntegerNode_;
         @Node.Child
         CreateSegmentDataObjectNode createResultNode_;
         @Node.Child
         TruffleString.ToJavaStringNode toJavaStringNode_;

         SegmentsData() {
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

   @GeneratedBy(SegmentsPrototypeBuiltins.SegmentsIteratorNode.class)
   public static final class SegmentsIteratorNodeGen extends SegmentsPrototypeBuiltins.SegmentsIteratorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private SegmentsIteratorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmentsObject) {
            JSSegmentsObject arguments0Value__ = (JSSegmentsObject)arguments0Value_;
            return this.doSegments(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSSegments(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSSegmentsObject) {
            JSSegmentsObject arguments0Value_ = (JSSegmentsObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doSegments(arguments0Value_);
         } else if (!JSGuards.isJSSegments(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doSegments", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
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

      public static SegmentsPrototypeBuiltins.SegmentsIteratorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SegmentsPrototypeBuiltinsFactory.SegmentsIteratorNodeGen(context, builtin, arguments);
      }
   }
}
