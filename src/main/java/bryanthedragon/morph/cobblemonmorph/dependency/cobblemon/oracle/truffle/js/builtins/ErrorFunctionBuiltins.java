
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.ErrorFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.ErrorStackTraceLimitNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class ErrorFunctionBuiltins
extends JSBuiltinsContainer.Lambda {
    public static final JSBuiltinsContainer BUILTINS = new ErrorFunctionBuiltins();

    protected ErrorFunctionBuiltins() {
        super(JSError.CLASS_NAME);
        this.defineFunction(Strings.CAPTURE_STACK_TRACE, 2, JSAttributes.getDefault(), (context, builtin) -> ErrorFunctionBuiltinsFactory.ErrorCaptureStackTraceNodeGen.create(context, builtin, ErrorFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context)));
    }

    public static abstract class ErrorCaptureStackTraceNode
    extends JSBuiltinNode {
        @Node.Child
        private ErrorStackTraceLimitNode stackTraceLimitNode;
        @Node.Child
        private InitErrorObjectNode initErrorObjectNode;
        private final BranchProfile errorProfile = BranchProfile.create();

        public ErrorCaptureStackTraceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.initErrorObjectNode = InitErrorObjectNode.create(context);
            this.stackTraceLimitNode = ErrorStackTraceLimitNode.create();
        }

        @Specialization
        protected Object captureStackTrace(VirtualFrame frame, Object object, Object skipUpTo) {
            if (!JSRuntime.isObject(object)) {
                this.errorProfile.enter();
                throw Errors.createTypeError("invalid_argument");
            }
            JSObject obj = (JSObject)object;
            if (!JSObject.isExtensible(obj)) {
                this.errorProfile.enter();
                throw Errors.createTypeError("Cannot define property:stack, object is not extensible.");
            }
            int stackTraceLimit = this.stackTraceLimitNode.executeInt();
            boolean customSkip = JSFunction.isJSFunction(skipUpTo);
            JSFunctionObject skipFramesUpTo = customSkip ? (JSFunctionObject)skipUpTo : (JSFunctionObject)JSArguments.getFunctionObject(frame.getArguments());
            UserScriptException ex = UserScriptException.createCapture(obj, this.getContext().isOptionNashornCompatibilityMode() ? this : null, stackTraceLimit, skipFramesUpTo, customSkip);
            this.initErrorObjectNode.execute(obj, ex, null);
            return Undefined.instance;
        }

        @Override
        public boolean countsTowardsStackTraceLimit() {
            return false;
        }
    }
}

