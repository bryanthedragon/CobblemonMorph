package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(StringIteratorPrototypeBuiltins.class)
public final class StringIteratorPrototypeBuiltinsFactory {
   @GeneratedBy(StringIteratorPrototypeBuiltins.StringIteratorNextNode.class)
   public static final class StringIteratorNextNodeGen extends StringIteratorPrototypeBuiltins.StringIteratorNextNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.FromCodePointNode stringIterator_fromCodePointNode_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode stringIterator_substringNode_;

      private StringIteratorNextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(Object arguments0Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (this.isStringIterator(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (this.isStringIterator(arguments0Value__)) {
               return this.doStringIterator(frameValue, arguments0Value__, this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_);
            }
         }

         if ((state_0 & 2) != 0 && this.fallbackGuard_(arguments0Value_)) {
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (this.isStringIterator(arguments0Value_)) {
                  this.stringIterator_fromCodePointNode_ = super.insert(TruffleString.FromCodePointNode.create());
                  this.stringIterator_substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doStringIterator(frameValue, arguments0Value_, this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_);
               }
            }

            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doIncompatibleReceiver(arguments0Value);
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
         Object[] s = new Object[]{"doStringIterator", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.stringIterator_fromCodePointNode_, this.stringIterator_substringNode_));
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

      public static StringIteratorPrototypeBuiltins.StringIteratorNextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringIteratorPrototypeBuiltinsFactory.StringIteratorNextNodeGen(context, builtin, arguments);
      }
   }
}
