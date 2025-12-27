package com.oracle.truffle.regex.literal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(LiteralRegexExecNode.class)
public final class LiteralRegexExecNodeGen extends LiteralRegexExecNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.MaterializeNode tString_materializeNode_;
   @CompilerDirectives.CompilationFinal
   private ValueProfile truffleObject_inputClassProfile_;

   private LiteralRegexExecNodeGen(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
      super(language, ast, implNode);
   }

   @Override
   public RegexResult execute(VirtualFrame frameValue, Object arg0Value, int arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            return this.doByteArray(arg0Value_, arg1Value);
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return this.doString(arg0Value_, arg1Value);
         }

         if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doTString(arg0Value_, arg1Value, this.tString_materializeNode_);
         }

         if ((state_0 & 8) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value)) {
            return this.doTruffleObject(arg0Value, arg1Value, this.truffleObject_inputClassProfile_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private RegexResult executeAndSpecialize(Object arg0Value, int arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      RegexResult var7;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doByteArray(arg0Value_, arg1Value);
         }

         if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_, arg1Value);
         }

         if (!(arg0Value instanceof TruffleString)) {
            if (!TRegexGuards.neitherByteArrayNorString(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            this.truffleObject_inputClassProfile_ = ValueProfile.createClassProfile();
            int var12;
            this.state_0_ = var12 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doTruffleObject(arg0Value, arg1Value, this.truffleObject_inputClassProfile_);
         }

         TruffleString arg0Value_ = (TruffleString)arg0Value;
         this.tString_materializeNode_ = super.insert(TruffleString.MaterializeNode.create());
         int var11;
         this.state_0_ = var11 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         var7 = this.doTString(arg0Value_, arg1Value, this.tString_materializeNode_);
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

   public static LiteralRegexExecNode create(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
      return new LiteralRegexExecNodeGen(language, ast, implNode);
   }
}
