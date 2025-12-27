package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNodeGen;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import com.oracle.truffle.regex.runtime.nodes.ToLongNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexObject.RegexObjectExecBooleanMethod.class)
final class RegexObjectExecBooleanMethodGen {
   private RegexObjectExecBooleanMethodGen() {
   }

   static {
      LibraryExport.register(RegexObject.RegexObjectExecBooleanMethod.class, new RegexObjectExecBooleanMethodGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexObject.RegexObjectExecBooleanMethod.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexObject.RegexObjectExecBooleanMethod.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexObject.RegexObjectExecBooleanMethod;

         InteropLibrary uncached = new RegexObjectExecBooleanMethodGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexObject.RegexObjectExecBooleanMethod;

         return new RegexObjectExecBooleanMethodGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(RegexObject.RegexObjectExecBooleanMethod.class)
      private static final class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private RegexObjectExecBooleanMethodGen.InteropLibraryExports.Cached.ExecuteData execute_cache;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((RegexObject.RegexObjectExecBooleanMethod)receiver).isExecutable();
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexObject.RegexObjectExecBooleanMethod arg0Value = (RegexObject.RegexObjectExecBooleanMethod)arg0Value_;
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               RegexObjectExecBooleanMethodGen.InteropLibraryExports.Cached.ExecuteData s0_ = this.execute_cache;
               if (s0_ != null) {
                  return arg0Value.execute(arg1Value, s0_.expectStringOrTruffleObjectNode_, s0_.toLongNode_, s0_.execNode_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private boolean executeAndSpecialize(RegexObject.RegexObjectExecBooleanMethod arg0Value, Object[] arg1Value) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var7;
            try {
               int state_0 = this.state_0_;
               RegexObjectExecBooleanMethodGen.InteropLibraryExports.Cached.ExecuteData s0_ = super.insert(
                  new RegexObjectExecBooleanMethodGen.InteropLibraryExports.Cached.ExecuteData()
               );
               s0_.expectStringOrTruffleObjectNode_ = s0_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
               s0_.toLongNode_ = s0_.insertAccessor(ToLongNode.create());
               s0_.execNode_ = s0_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
               VarHandle.storeStoreFence();
               this.execute_cache = s0_;
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var7 = arg0Value.execute(arg1Value, s0_.expectStringOrTruffleObjectNode_, s0_.toLongNode_, s0_.execNode_);
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
            return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @GeneratedBy(RegexObject.RegexObjectExecBooleanMethod.class)
         private static final class ExecuteData extends Node {
            @Node.Child
            ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
            @Node.Child
            ToLongNode toLongNode_;
            @Node.Child
            RegexObject.ExecCompiledRegexNode execNode_;

            ExecuteData() {
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

      @GeneratedBy(RegexObject.RegexObjectExecBooleanMethod.class)
      @DenyReplace
      private static final class Uncached extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
         protected Uncached(Object receiver) {
            super(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return super.accepts(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexObject.RegexObjectExecBooleanMethod)receiver).isExecutable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexObject.RegexObjectExecBooleanMethod arg0Value = (RegexObject.RegexObjectExecBooleanMethod)arg0Value_;
            return arg0Value.execute(
               arg1Value,
               ExpectStringOrTruffleObjectNodeGen.getUncached(),
               ToLongNodeGen.getUncached(),
               RegexObjectFactory.ExecCompiledRegexNodeGen.getUncached()
            );
         }
      }
   }
}
