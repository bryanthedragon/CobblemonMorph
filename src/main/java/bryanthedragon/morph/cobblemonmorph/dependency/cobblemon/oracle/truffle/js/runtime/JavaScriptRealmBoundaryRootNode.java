package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.builtins.JSFunction;

public abstract class JavaScriptRealmBoundaryRootNode extends JavaScriptRootNode {
   protected JavaScriptRealmBoundaryRootNode(JavaScriptLanguage lang, SourceSection sourceSection, FrameDescriptor frameDescriptor) {
      super(lang, sourceSection, frameDescriptor);
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      JSContext context = this.getLanguage().getJSContext();
      CompilerAsserts.partialEvaluationConstant(context);
      JSRealm functionRealm = null;
      boolean enterRealm;
      if (context.neverCreatedChildRealms()) {
         assert this.getRealm() == JSFunction.getRealm(JSFrameUtil.getFunctionObject(frame));

         enterRealm = false;
      } else {
         functionRealm = JSFunction.getRealm(JSFrameUtil.getFunctionObject(frame));
         JSRealm currentRealm = this.getRealm();
         enterRealm = functionRealm != currentRealm;
      }

      JSRealm prevRealm = null;
      JSRealm mainRealm = null;
      if (enterRealm) {
         mainRealm = JSRealm.getMain(this);
         prevRealm = mainRealm.enterRealm(this, functionRealm);
      }

      Object ex;
      try {
         ex = this.executeInRealm(frame);
      } catch (StackOverflowError var11) {
         CompilerDirectives.transferToInterpreter();
         throw Errors.createRangeErrorStackOverflow(var11, this);
      } finally {
         if (enterRealm) {
            mainRealm.leaveRealm(this, prevRealm);
         }
      }

      return ex;
   }

   protected abstract Object executeInRealm(VirtualFrame frame);
}
