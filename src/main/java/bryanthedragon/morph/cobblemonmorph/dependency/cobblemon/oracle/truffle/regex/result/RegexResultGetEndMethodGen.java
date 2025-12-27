package com.oracle.truffle.regex.result;

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
import com.oracle.truffle.regex.AbstractRegexObjectGen;
import com.oracle.truffle.regex.runtime.nodes.ToIntNode;
import com.oracle.truffle.regex.runtime.nodes.ToIntNodeGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegexResult.RegexResultGetEndMethod.class)
final class RegexResultGetEndMethodGen {
   private RegexResultGetEndMethodGen() {
   }

   static {
      LibraryExport.register(RegexResult.RegexResultGetEndMethod.class, new RegexResultGetEndMethodGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexResult.RegexResultGetEndMethod.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexResult.RegexResultGetEndMethod.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexResult.RegexResultGetEndMethod;

         InteropLibrary uncached = new RegexResultGetEndMethodGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexResult.RegexResultGetEndMethod;

         return new RegexResultGetEndMethodGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(RegexResult.RegexResultGetEndMethod.class)
      private static final class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private ToIntNode toIntNode_;
         @Node.Child
         private RegexResult.RegexResultGetEndNode getEndNode_;

         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((RegexResult.RegexResultGetEndMethod)receiver).isExecutable();
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            RegexResult.RegexResultGetEndMethod arg0Value = (RegexResult.RegexResultGetEndMethod)arg0Value_;
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               return arg0Value.execute(arg1Value, this.toIntNode_, this.getEndNode_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arg0Value, arg1Value);
            }
         }

         private int executeAndSpecialize(RegexResult.RegexResultGetEndMethod arg0Value, Object[] arg1Value) throws ArityException, UnsupportedTypeException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            int var6;
            try {
               int state_0 = this.state_0_;
               this.toIntNode_ = super.insert(ToIntNode.create());
               this.getEndNode_ = super.insert(RegexResultFactory.RegexResultGetEndNodeGen.create());
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.execute(arg1Value, this.toIntNode_, this.getEndNode_);
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
            return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }
      }

      @GeneratedBy(RegexResult.RegexResultGetEndMethod.class)
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

            return ((RegexResult.RegexResultGetEndMethod)receiver).isExecutable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws ArityException, UnsupportedTypeException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            RegexResult.RegexResultGetEndMethod arg0Value = (RegexResult.RegexResultGetEndMethod)arg0Value_;
            return arg0Value.execute(arg1Value, ToIntNodeGen.getUncached(), RegexResultFactory.RegexResultGetEndNodeGen.getUncached());
         }
      }
   }
}
