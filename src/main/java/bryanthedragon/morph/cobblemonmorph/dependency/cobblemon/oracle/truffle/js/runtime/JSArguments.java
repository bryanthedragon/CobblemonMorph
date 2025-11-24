
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.Null;

public final class JSArguments {
    public static final Object[] EMPTY_ARGUMENTS_ARRAY = new Object[0];
    public static final int RUNTIME_ARGUMENT_COUNT = 2;
    private static final int THIS_OBJECT_INDEX = 0;
    private static final int FUNCTION_OBJECT_INDEX = 1;
    private static final int NEW_TARGET_INDEX = 2;
    private static final int RESUME_EXECUTION_CONTEXT = 2;
    private static final int RESUME_GENERATOR_OR_PROMISE = 3;
    private static final int RESUME_COMPLETION_TYPE = 4;
    private static final int RESUME_COMPLETION_VALUE = 5;

    private JSArguments() {
    }

    static Object[] createNullArguments() {
        return JSArguments.createZeroArg(Null.instance, null);
    }

    public static Object[] create(Object target, Object function, Object ... userArguments) {
        Object[] arguments = JSArguments.createInitial(target, function, userArguments.length);
        JSArguments.setUserArguments(arguments, 0, userArguments);
        return arguments;
    }

    public static Object[] createInitial(Object target, Object function, int userArgumentCount) {
        Object[] result = new Object[2 + userArgumentCount];
        result[0] = target;
        result[1] = function;
        return result;
    }

    public static Object[] createZeroArg(Object target, Object function) {
        return JSArguments.createInitial(target, function, 0);
    }

    public static Object[] createOneArg(Object target, Object function, Object userArgument) {
        Object[] arguments = JSArguments.createInitial(target, function, 1);
        JSArguments.setUserArgument(arguments, 0, userArgument);
        return arguments;
    }

    public static Object getThisObject(Object[] arguments) {
        return arguments[0];
    }

    public static Object getFunctionObject(Object[] arguments) {
        return arguments[1];
    }

    public static Object getUserArgument(Object[] arguments, int index) {
        return arguments[index + 2];
    }

    public static void setUserArgument(Object[] arguments, int index, Object value2) {
        arguments[index + 2] = value2;
    }

    public static int getUserArgumentCount(Object[] arguments) {
        return arguments.length - 2;
    }

    public static void setUserArguments(Object[] arguments, int index, Object ... userArguments) {
        JSArguments.arraycopy(userArguments, 0, arguments, 2 + index, userArguments.length);
    }

    public static Object[] extractUserArguments(Object[] arguments) {
        Object[] userArguments = new Object[arguments.length - 2];
        JSArguments.arraycopy(arguments, 2, userArguments, 0, userArguments.length);
        return userArguments;
    }

    public static Object[] extractUserArguments(Object[] arguments, int skip) {
        return JSArguments.extractUserArguments(arguments, skip, 0);
    }

    public static Object[] extractUserArguments(Object[] arguments, int skip, int skipEnd) {
        int offset = 2 + skip;
        Object[] userArguments = new Object[arguments.length - offset - skipEnd];
        JSArguments.arraycopy(arguments, offset, userArguments, 0, userArguments.length);
        return userArguments;
    }

    public static MaterializedFrame getEnclosingFrame(Object[] arguments) {
        return ((JSFunctionObject.Unbound)JSArguments.getFunctionObject(arguments)).getEnclosingFrame();
    }

    public static void arraycopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    public static Object[] createWithNewTarget(Object target, Object function, Object newTarget, Object ... userArguments) {
        Object[] arguments = JSArguments.createInitialWithNewTarget(target, function, newTarget, userArguments.length);
        JSArguments.setUserArguments(arguments, 1, userArguments);
        return arguments;
    }

    public static Object[] createInitialWithNewTarget(Object target, Object function, Object newTarget, int userArgumentCount) {
        Object[] arguments = JSArguments.createInitial(target, function, userArgumentCount + 1);
        arguments[2] = newTarget;
        return arguments;
    }

    public static Object getNewTarget(Object[] arguments) {
        return arguments[2];
    }

    public static Object[] createResumeArguments(Object executionContext, Object generatorOrPromiseCapability, Completion.Type completionType, Object completionValue) {
        MaterializedFrame contextFrame = JSFrameUtil.castMaterializedFrame(executionContext);
        Object[] arguments = contextFrame.getArguments();
        return new Object[]{arguments[0], arguments[1], contextFrame, generatorOrPromiseCapability, completionType, completionValue};
    }

    public static Object[] createResumeArguments(Object executionContext, Object generator, Completion completion) {
        return JSArguments.createResumeArguments(executionContext, generator, completion.getType(), completion.getValue());
    }

    public static MaterializedFrame getResumeExecutionContext(Object[] arguments) {
        return JSFrameUtil.castMaterializedFrame(arguments[2]);
    }

    public static Object getResumeGeneratorOrPromiseCapability(Object[] arguments) {
        return arguments[3];
    }

    public static Completion.Type getResumeCompletionType(Object[] arguments) {
        return (Completion.Type)((Object)arguments[4]);
    }

    public static Object getResumeCompletionValue(Object[] arguments) {
        return arguments[5];
    }

    public static Completion getResumeCompletion(Object[] arguments) {
        return Completion.create(JSArguments.getResumeCompletionType(arguments), JSArguments.getResumeCompletionValue(arguments));
    }
}

