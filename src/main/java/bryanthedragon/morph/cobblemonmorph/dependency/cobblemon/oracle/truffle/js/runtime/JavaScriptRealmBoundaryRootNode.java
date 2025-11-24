
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;

public abstract class JavaScriptRealmBoundaryRootNode
extends JavaScriptRootNode {
    protected JavaScriptRealmBoundaryRootNode(JavaScriptLanguage lang, SourceSection sourceSection, FrameDescriptor frameDescriptor) {
        super(lang, sourceSection, frameDescriptor);
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        boolean enterRealm;
        JSContext context = this.getLanguage().getJSContext();
        CompilerAsserts.partialEvaluationConstant(context);
        JSRealm functionRealm = null;
        if (context.neverCreatedChildRealms()) {
            assert (this.getRealm() == JSFunction.getRealm(JSFrameUtil.getFunctionObject(frame)));
            enterRealm = false;
        } else {
            JSRealm currentRealm;
            functionRealm = JSFunction.getRealm(JSFrameUtil.getFunctionObject(frame));
            enterRealm = functionRealm != (currentRealm = this.getRealm());
        }
        JSRealm prevRealm = null;
        JSRealm mainRealm = null;
        if (enterRealm) {
            mainRealm = JSRealm.getMain(this);
            prevRealm = mainRealm.enterRealm(this, functionRealm);
        }
        try {
            Object object = this.executeInRealm(frame);
            return object;
        }
        catch (StackOverflowError ex) {
            CompilerDirectives.transferToInterpreter();
            throw Errors.createRangeErrorStackOverflow(ex, this);
        }
        finally {
            if (enterRealm) {
                mainRealm.leaveRealm(this, prevRealm);
            }
        }
    }

    protected abstract Object executeInRealm(VirtualFrame var1);
}

