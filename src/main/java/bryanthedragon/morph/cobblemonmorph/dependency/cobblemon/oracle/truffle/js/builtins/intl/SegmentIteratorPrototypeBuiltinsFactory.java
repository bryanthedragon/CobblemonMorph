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
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.CreateSegmentDataObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentIteratorObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SegmentIteratorPrototypeBuiltins.class)
public final class SegmentIteratorPrototypeBuiltinsFactory {
   @GeneratedBy(SegmentIteratorPrototypeBuiltins.SegmentIteratorNextNode.class)
   public static final class SegmentIteratorNextNodeGen extends SegmentIteratorPrototypeBuiltins.SegmentIteratorNextNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private CreateSegmentDataObjectNode segmentIterator_createNextValueNode_;

      private SegmentIteratorNextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSegmentIteratorObject) {
            JSSegmentIteratorObject arguments0Value__ = (JSSegmentIteratorObject)arguments0Value_;
            return this.doSegmentIterator(frameValue, arguments0Value__, this.segmentIterator_createNextValueNode_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSSegmentIterator(arguments0Value_)) {
            return this.doIncompatibleReceiver(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue, Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSSegmentIteratorObject)) {
               if (JSGuards.isJSSegmentIterator(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doIncompatibleReceiver(arguments0Value);
            }

            JSSegmentIteratorObject arguments0Value_ = (JSSegmentIteratorObject)arguments0Value;
            this.segmentIterator_createNextValueNode_ = super.insert(CreateSegmentDataObjectNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doSegmentIterator(frameValue, arguments0Value_, this.segmentIterator_createNextValueNode_);
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
         Object[] s = new Object[]{"doSegmentIterator", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.segmentIterator_createNextValueNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doIncompatibleReceiver", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SegmentIteratorPrototypeBuiltins.SegmentIteratorNextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SegmentIteratorPrototypeBuiltinsFactory.SegmentIteratorNextNodeGen(context, builtin, arguments);
      }
   }
}
