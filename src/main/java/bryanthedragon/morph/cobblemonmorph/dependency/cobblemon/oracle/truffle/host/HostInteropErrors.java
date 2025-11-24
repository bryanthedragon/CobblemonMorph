
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostToTypeNode;
import java.lang.reflect.Type;
import java.util.Arrays;

final class HostInteropErrors {
    private HostInteropErrors() {
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException nullCoercion(HostContext context, Object nullValue, Type targetType) {
        throw HostEngineException.nullPointer(context.access, String.format("Cannot convert null value %s to Java type '%s'.", HostInteropErrors.getValueInfo(context, nullValue), targetType.getTypeName()));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException cannotConvertPrimitive(HostContext context, Object value2, Class<?> targetType) {
        String reason = HostToTypeNode.isPrimitiveTarget(targetType) ? "Invalid or lossy primitive coercion." : "Unsupported target type.";
        return HostEngineException.classCast(context.access, String.format("Cannot convert %s to Java type '%s': %s", HostInteropErrors.getValueInfo(context, value2), targetType.getTypeName(), reason));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException cannotConvert(HostContext context, Object value2, Type targetType, String reason) {
        return HostEngineException.classCast(context.access, String.format("Cannot convert %s to Java type '%s': %s", HostInteropErrors.getValueInfo(context, value2), targetType.getTypeName(), reason));
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidArrayIndex(HostContext context, Object receiver, Type componentType, int index) {
        String message = String.format("Invalid array index %s for %s[] %s.", index, HostInteropErrors.formatComponentType(componentType), HostInteropErrors.getValueInfo(context, receiver));
        throw HostEngineException.arrayIndexOutOfBounds(context.access, message);
    }

    private static Object formatComponentType(Type componentType) {
        return componentType == null || componentType == Object.class ? "Object" : componentType.getTypeName();
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException arrayReadUnsupported(HostContext context, Object receiver, Type componentType) {
        String message = String.format("Unsupported array read operation for %s[] %s.", HostInteropErrors.formatComponentType(componentType), HostInteropErrors.getValueInfo(context, receiver));
        throw HostEngineException.unsupported(context.access, message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidExecuteArgumentType(HostContext context, Object receiver, Object[] arguments) {
        String[] formattedArgs = HostInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument when executing %s with arguments %s.", HostInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs));
        throw HostEngineException.illegalArgument(context.access, message);
    }

    @CompilerDirectives.TruffleBoundary
    static RuntimeException invalidExecuteArity(HostContext context, Object receiver, Object[] arguments, int minArity, int maxArity, int actual) {
        String[] formattedArgs = HostInteropErrors.formatArgs(context, arguments);
        String message = String.format("Invalid argument count when executing %s with arguments %s. %s", HostInteropErrors.getValueInfo(context, receiver), Arrays.asList(formattedArgs), HostInteropErrors.formatExpectedArguments(minArity, maxArity, actual));
        throw HostEngineException.illegalArgument(context.access, message);
    }

    private static String[] formatArgs(HostContext context, Object[] arguments) {
        String[] formattedArgs = new String[arguments.length];
        for (int i = 0; i < arguments.length; ++i) {
            formattedArgs[i] = HostInteropErrors.getValueInfo(context, arguments[i]);
        }
        return formattedArgs;
    }

    static String getValueInfo(HostContext context, Object value2) {
        return context.language.access.getValueInfo(context.internalContext, value2);
    }

    @CompilerDirectives.TruffleBoundary
    static UnsupportedTypeException unsupportedTypeException(Object[] args, Throwable e) {
        return UnsupportedTypeException.create(args, e.getMessage());
    }

    @CompilerDirectives.TruffleBoundary
    static UnsupportedTypeException unsupportedTypeException(Object arg, Throwable e) {
        return UnsupportedTypeException.create(new Object[]{arg}, e.getMessage());
    }

    static String formatExpectedArguments(int expectedMinArity, int expectedMaxArity, int actualArity) {
        String actual = actualArity < 0 ? "unknown" : String.valueOf(actualArity);
        Object expected = expectedMinArity == expectedMaxArity ? String.valueOf(expectedMinArity) : (expectedMaxArity < 0 ? expectedMinArity + "+" : expectedMinArity + "-" + expectedMaxArity);
        return String.format("Expected %s argument(s) but got %s.", expected, actual);
    }
}

