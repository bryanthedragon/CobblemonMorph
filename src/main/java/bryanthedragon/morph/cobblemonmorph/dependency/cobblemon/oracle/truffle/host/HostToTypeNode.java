
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.host.HostClassDesc;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostInteropErrors;
import com.oracle.truffle.host.HostInteropReflect;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostTargetMappingNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import com.oracle.truffle.host.HostUtil;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

@GenerateUncached
abstract class HostToTypeNode
extends Node {
    static final int LIMIT = 5;
    static final int HIGHEST = 0;
    static final int STRICT = 1;
    static final int LOOSE = 2;
    static final int COERCE = 3;
    static final int FUNCTION_PROXY = 4;
    static final int OBJECT_PROXY_IFACE = 5;
    static final int OBJECT_PROXY_CLASS = 6;
    static final int HOST_PROXY = 7;
    static final int LOWEST = 8;
    static final int[] PRIORITIES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

    HostToTypeNode() {
    }

    public abstract Object execute(HostContext var1, Object var2, Class<?> var3, Type var4, boolean var5);

    @Specialization(guards={"targetType == cachedTargetType"}, limit="LIMIT")
    protected Object doCached(HostContext context, Object operand, Class<?> targetType, Type genericType, boolean useCustomTargetTypes, @CachedLibrary(value="operand") InteropLibrary interop, @Cached(value="targetType") Class<?> cachedTargetType, @Cached(value="isPrimitiveTarget(cachedTargetType)") boolean primitiveTarget, @Cached(value="allowsImplementation(context, targetType)") boolean allowsImplementation, @Cached HostTargetMappingNode targetMapping, @Cached BranchProfile error) {
        return HostToTypeNode.convertImpl(operand, cachedTargetType, genericType, allowsImplementation, primitiveTarget, context, interop, useCustomTargetTypes, targetMapping, error);
    }

    @CompilerDirectives.TruffleBoundary
    static boolean allowsImplementation(HostContext hostContext, Class<?> type) {
        if (hostContext == null) {
            return false;
        }
        if (!HostInteropReflect.isAbstractType(type)) {
            return false;
        }
        HostClassDesc classDesc = hostContext.getHostClassCache().forClass(type);
        return classDesc.isAllowsImplementation() && classDesc.isAllowedTargetType();
    }

    @Specialization(replaces={"doCached"})
    @CompilerDirectives.TruffleBoundary
    protected static Object doGeneric(HostContext context, Object operand, Class<?> targetType, Type genericType, boolean useTargetMapping) {
        return HostToTypeNode.convertImpl(operand, targetType, genericType, HostToTypeNode.allowsImplementation(context, targetType), HostToTypeNode.isPrimitiveTarget(targetType), context, InteropLibrary.getUncached(operand), useTargetMapping, HostTargetMappingNode.getUncached(), BranchProfile.getUncached());
    }

    @CompilerDirectives.TruffleBoundary
    private static String toString(Object value2) {
        return value2.toString();
    }

    private static Object convertImpl(Object value2, Class<?> targetType, Type genericType, boolean allowsImplementation, boolean primitiveTargetType, HostContext context, InteropLibrary interop, boolean useCustomTargetTypes, HostTargetMappingNode targetMapping, BranchProfile error) {
        Object convertedValue;
        Object result;
        if (useCustomTargetTypes && (result = targetMapping.execute(value2, targetType, context, interop, false, 0, 1)) != HostTargetMappingNode.NO_RESULT) {
            return result;
        }
        if (primitiveTargetType && (convertedValue = HostUtil.convertLossLess(value2, targetType, interop)) != null) {
            return convertedValue;
        }
        HostLanguage language = HostLanguage.get(interop);
        if (HostObject.isJavaInstance(language, targetType, value2)) {
            return HostObject.valueOf(language, value2);
        }
        if (useCustomTargetTypes && (convertedValue = targetMapping.execute(value2, targetType, context, interop, false, 2, 2)) != HostTargetMappingNode.NO_RESULT) {
            return convertedValue;
        }
        if (primitiveTargetType && (convertedValue = HostUtil.convertLossy(value2, targetType, interop)) != null) {
            return convertedValue;
        }
        if (targetType == Value.class && context != null) {
            return value2 instanceof Value ? value2 : context.asValue(interop, value2);
        }
        if (interop.isNull(value2)) {
            if (targetType.isPrimitive()) {
                throw HostInteropErrors.nullCoercion(context, value2, targetType);
            }
            return null;
        }
        if (value2 instanceof TruffleObject) {
            convertedValue = HostToTypeNode.asJavaObject(context, (TruffleObject)value2, targetType, genericType, allowsImplementation);
            if (convertedValue != null) {
                return convertedValue;
            }
        } else if (value2 instanceof TruffleString && targetType.isAssignableFrom(String.class)) {
            try {
                return interop.asString(value2);
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
        if (!targetType.isInstance(value2)) {
            Object result2;
            if (useCustomTargetTypes && (result2 = targetMapping.execute(value2, targetType, context, interop, false, 3, 8)) != HostTargetMappingNode.NO_RESULT) {
                return result2;
            }
            error.enter();
            throw HostInteropErrors.cannotConvertPrimitive(context, value2, targetType);
        }
        convertedValue = value2;
        return targetType.cast(convertedValue);
    }

    static boolean canConvert(Object value2, Class<?> targetType, Type genericType, Boolean allowsImplementation, HostContext hostContext, int priority, InteropLibrary interop, HostTargetMappingNode targetMapping) {
        Object convertedValue;
        Object convertedValue2;
        if (targetMapping != null && targetMapping.execute(value2, targetType, hostContext, interop, true, 0, priority) == Boolean.TRUE) {
            return true;
        }
        if (priority <= 0) {
            return false;
        }
        if (interop.isNull(value2)) {
            return !targetType.isPrimitive();
        }
        if (targetType == Value.class && hostContext != null) {
            return true;
        }
        if (HostToTypeNode.isPrimitiveTarget(targetType) && (convertedValue2 = HostUtil.convertLossLess(value2, targetType, interop)) != null) {
            return true;
        }
        HostLanguage language = HostLanguage.get(interop);
        if (HostObject.isJavaInstance(language, targetType, value2)) {
            return true;
        }
        if (priority <= 1) {
            return false;
        }
        if (targetType == Object.class) {
            return true;
        }
        if (targetType == List.class) {
            return interop.hasArrayElements(value2);
        }
        if (targetType == Map.class) {
            return interop.hasMembers(value2);
        }
        if (targetType == Function.class) {
            return interop.isExecutable(value2) || interop.isInstantiable(value2);
        }
        if (targetType == LocalDate.class) {
            return interop.isDate(value2);
        }
        if (targetType == LocalTime.class) {
            return interop.isTime(value2);
        }
        if (targetType == LocalDateTime.class) {
            return interop.isDate(value2) && interop.isTime(value2);
        }
        if (targetType == ZonedDateTime.class || targetType == Date.class || targetType == Instant.class) {
            return interop.isInstant(value2);
        }
        if (targetType == ZoneId.class) {
            return interop.isTimeZone(value2);
        }
        if (targetType == Duration.class) {
            return interop.isDuration(value2);
        }
        if (targetType == PolyglotException.class) {
            return interop.isException(value2);
        }
        if (priority <= 2) {
            return false;
        }
        if (targetType.isArray()) {
            return interop.hasArrayElements(value2);
        }
        if (HostToTypeNode.isPrimitiveTarget(targetType) && (convertedValue = HostUtil.convertLossy(value2, targetType, interop)) != null) {
            return true;
        }
        if (value2 instanceof TruffleObject) {
            if (priority < 7 && HostObject.isInstance(language, value2)) {
                return false;
            }
            if (priority >= 4 && HostInteropReflect.isFunctionalInterface(targetType) && (interop.isExecutable(value2) || interop.isInstantiable(value2)) && HostToTypeNode.checkAllowsImplementation(targetType, allowsImplementation, hostContext)) {
                return true;
            }
            return (priority >= 5 && targetType.isInterface() || priority >= 6 && HostInteropReflect.isAbstractType(targetType)) && interop.hasMembers(value2) && HostToTypeNode.checkAllowsImplementation(targetType, allowsImplementation, hostContext);
        }
        assert (!(value2 instanceof TruffleObject));
        return targetType.isInstance(value2);
    }

    private static boolean checkAllowsImplementation(Class<?> targetType, Boolean allowsImplementation, HostContext hostContext) {
        boolean implementations = allowsImplementation == null ? HostToTypeNode.allowsImplementation(hostContext, targetType) : allowsImplementation;
        return implementations;
    }

    static boolean isPrimitiveTarget(Class<?> clazz) {
        return clazz == Integer.TYPE || clazz == Integer.class || clazz == Boolean.TYPE || clazz == Boolean.class || clazz == Byte.TYPE || clazz == Byte.class || clazz == Short.TYPE || clazz == Short.class || clazz == Long.TYPE || clazz == Long.class || clazz == Float.TYPE || clazz == Float.class || clazz == Double.TYPE || clazz == Double.class || clazz == Character.TYPE || clazz == Character.class || clazz == Number.class || CharSequence.class.isAssignableFrom(clazz);
    }

    static Object convertToObject(HostContext hostContext, Object value2, InteropLibrary interop) {
        try {
            if (interop.isNull(value2)) {
                return null;
            }
            if (interop.isString(value2)) {
                return interop.asString(value2);
            }
            if (interop.isBoolean(value2)) {
                return interop.asBoolean(value2);
            }
            if (interop.isNumber(value2)) {
                Object result = HostUtil.convertToNumber(value2, interop);
                if (result != null) {
                    return result;
                }
            } else {
                if (interop.hasArrayElements(value2)) {
                    return HostToTypeNode.asJavaObject(hostContext, value2, List.class, null, false);
                }
                if (interop.hasHashEntries(value2) || interop.hasMembers(value2)) {
                    return HostToTypeNode.asJavaObject(hostContext, value2, Map.class, null, false);
                }
                if (interop.hasIterator(value2)) {
                    return HostToTypeNode.asJavaObject(hostContext, value2, Iterable.class, null, false);
                }
                if (interop.isIterator(value2)) {
                    return HostToTypeNode.asJavaObject(hostContext, value2, Iterator.class, null, false);
                }
                if (interop.isExecutable(value2) || interop.isInstantiable(value2)) {
                    return HostToTypeNode.asJavaObject(hostContext, value2, Function.class, null, false);
                }
            }
            return hostContext.language.access.toValue(hostContext.internalContext, value2);
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @CompilerDirectives.TruffleBoundary
    private static <T> T asJavaObject(HostContext hostContext, Object value2, Class<T> targetType, Type genericType, boolean allowsImplementation) {
        Object obj;
        InteropLibrary interop = InteropLibrary.getFactory().getUncached(value2);
        assert (!interop.isNull(value2));
        if (HostObject.isJavaInstance(hostContext.language, targetType, value2)) {
            obj = HostObject.valueOf(hostContext.language, value2);
        } else if (targetType == Object.class) {
            obj = HostToTypeNode.convertToObject(hostContext, value2, interop);
        } else if (targetType == List.class) {
            if (!interop.hasArrayElements(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have array elements.");
            boolean implementsFunction = HostToTypeNode.shouldImplementFunction(value2, interop);
            TypeAndClass<?> elementType = HostToTypeNode.getGenericParameterType(genericType, 0);
            obj = hostContext.language.access.toList(hostContext.internalContext, value2, implementsFunction, elementType.clazz, elementType.type);
        } else if (targetType == Map.class) {
            boolean hasKeys;
            TypeAndClass<?> keyType = HostToTypeNode.getGenericParameterType(genericType, 0);
            TypeAndClass<?> valueType = HostToTypeNode.getGenericParameterType(genericType, 1);
            boolean hasHashEntries = interop.hasHashEntries(value2);
            if (!hasHashEntries && !HostToTypeNode.isSupportedMapKeyType(keyType.clazz)) {
                throw HostToTypeNode.newInvalidKeyTypeException(keyType.clazz, hostContext);
            }
            boolean hasSize = Number.class.isAssignableFrom(keyType.clazz) && interop.hasArrayElements(value2);
            boolean bl = hasKeys = (keyType.clazz == Object.class || keyType.clazz == String.class) && interop.hasMembers(value2);
            if (!hasKeys && !hasSize && !hasHashEntries) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have members, array elements or hash entries.");
            boolean implementsFunction = HostToTypeNode.shouldImplementFunction(value2, interop);
            obj = hostContext.language.access.toMap(hostContext.internalContext, value2, implementsFunction, keyType.clazz, keyType.type, valueType.clazz, valueType.type);
        } else if (targetType == Map.Entry.class) {
            if (!interop.hasArrayElements(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have array elements.");
            TypeAndClass<?> keyType = HostToTypeNode.getGenericParameterType(genericType, 0);
            TypeAndClass<?> valueType = HostToTypeNode.getGenericParameterType(genericType, 1);
            boolean implementsFunction = HostToTypeNode.shouldImplementFunction(value2, interop);
            obj = hostContext.language.access.toMapEntry(hostContext.internalContext, value2, implementsFunction, keyType.clazz, keyType.type, valueType.clazz, valueType.type);
        } else if (targetType == Function.class) {
            TypeAndClass<?> paramType = HostToTypeNode.getGenericParameterType(genericType, 0);
            TypeAndClass<?> returnType = HostToTypeNode.getGenericParameterType(genericType, 1);
            if (interop.isExecutable(value2) || interop.isInstantiable(value2)) {
                obj = hostContext.language.access.toFunction(hostContext.internalContext, value2, returnType.clazz, returnType.type, paramType.clazz, paramType.type);
            } else {
                if (!interop.hasMembers(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must be executable or instantiable.");
                obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value2);
            }
        } else if (targetType.isArray()) {
            if (!interop.hasArrayElements(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have array elements.");
            obj = HostToTypeNode.truffleObjectToArray(hostContext, interop, value2, targetType, genericType);
        } else {
            if (targetType == LocalDate.class) {
                if (!interop.isDate(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have date and time information.");
                try {
                    obj = interop.asDate(value2);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            if (targetType == LocalTime.class) {
                if (!interop.isTime(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have date and time information.");
                try {
                    obj = interop.asTime(value2);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            if (targetType == LocalDateTime.class) {
                LocalTime time;
                LocalDate date;
                if (!interop.isDate(value2) || !interop.isTime(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have date and time information.");
                try {
                    date = interop.asDate(value2);
                    time = interop.asTime(value2);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
                obj = HostToTypeNode.createDateTime(date, time);
            } else if (targetType == ZonedDateTime.class) {
                ZoneId timeZone;
                LocalTime time;
                LocalDate date;
                if (!interop.isDate(value2) || !interop.isTime(value2) || !interop.isTimeZone(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have date, time and time-zone information.");
                try {
                    date = interop.asDate(value2);
                    time = interop.asTime(value2);
                    timeZone = interop.asTimeZone(value2);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
                obj = HostToTypeNode.createZonedDateTime(date, time, timeZone);
            } else {
                if (targetType == ZoneId.class) {
                    if (!interop.isTimeZone(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have time-zone information.");
                    try {
                        obj = interop.asTimeZone(value2);
                    }
                    catch (UnsupportedMessageException e) {
                        throw CompilerDirectives.shouldNotReachHere(e);
                    }
                }
                if (targetType == Instant.class || targetType == Date.class) {
                    Instant instantValue;
                    if (!interop.isDate(value2) || !interop.isTime(value2) || !interop.isTimeZone(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have date, time and time-zone information.");
                    try {
                        instantValue = interop.asInstant(value2);
                    }
                    catch (UnsupportedMessageException e) {
                        throw CompilerDirectives.shouldNotReachHere(e);
                    }
                    obj = targetType == Date.class ? Date.from(instantValue) : targetType.cast(instantValue);
                } else if (targetType == Duration.class) {
                    if (!interop.isDuration(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have duration information.");
                    try {
                        obj = interop.asDuration(value2);
                    }
                    catch (UnsupportedMessageException e) {
                        throw CompilerDirectives.shouldNotReachHere(e);
                    }
                } else if (targetType == PolyglotException.class) {
                    if (!interop.isException(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must be an exception.");
                    obj = HostToTypeNode.asPolyglotException(hostContext, value2, interop);
                } else if (targetType == Iterable.class) {
                    if (interop.hasIterator(value2)) {
                        boolean implementsFunction = HostToTypeNode.shouldImplementFunction(value2, interop);
                        TypeAndClass<?> elementType = HostToTypeNode.getGenericParameterType(genericType, 0);
                        obj = hostContext.language.access.toIterable(hostContext.internalContext, value2, implementsFunction, elementType.clazz, elementType.type);
                    } else {
                        if (!allowsImplementation || !interop.hasMembers(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have an iterator.");
                        obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value2);
                    }
                } else if (targetType == Iterator.class) {
                    if (interop.isIterator(value2)) {
                        boolean implementsFunction = HostToTypeNode.shouldImplementFunction(value2, interop);
                        TypeAndClass<?> elementType = HostToTypeNode.getGenericParameterType(genericType, 0);
                        obj = hostContext.language.access.toIterator(hostContext.internalContext, value2, implementsFunction, elementType.clazz, elementType.type);
                    } else {
                        if (!allowsImplementation || !interop.hasMembers(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must be an iterator.");
                        obj = hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value2);
                    }
                } else {
                    if (!allowsImplementation || !HostInteropReflect.isAbstractType(targetType)) return null;
                    if (HostInteropReflect.isFunctionalInterface(targetType) && (interop.isExecutable(value2) || interop.isInstantiable(value2))) {
                        obj = hostContext.language.access.toFunctionProxy(hostContext.internalContext, targetType, value2);
                    } else {
                        if (!interop.hasMembers(value2)) throw HostInteropErrors.cannotConvert(hostContext, value2, targetType, "Value must have members.");
                        obj = targetType.isInterface() ? hostContext.language.access.toObjectProxy(hostContext.internalContext, targetType, value2) : HostInteropReflect.newAdapterInstance(hostContext, targetType, value2);
                    }
                }
            }
        }
        assert (targetType.isInstance(obj));
        return targetType.cast(obj);
    }

    private static Object asPolyglotException(HostContext hostContext, Object value2, InteropLibrary interop) {
        try {
            interop.throwException(value2);
            throw UnsupportedMessageException.create();
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        catch (ThreadDeath e) {
            throw e;
        }
        catch (Throwable e) {
            return hostContext.language.access.toPolyglotException(hostContext.internalContext, e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static ZonedDateTime createZonedDateTime(LocalDate date, LocalTime time, ZoneId timeZone) {
        return ZonedDateTime.of(date, time, timeZone);
    }

    @CompilerDirectives.TruffleBoundary
    private static LocalDateTime createDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    private static boolean shouldImplementFunction(Object truffleObject, InteropLibrary interop) {
        boolean executable = interop.isExecutable(truffleObject);
        boolean instantiable = false;
        if (!executable) {
            instantiable = interop.isInstantiable(truffleObject);
        }
        boolean implementsFunction = executable || instantiable;
        return implementsFunction;
    }

    private static boolean isSupportedMapKeyType(Class<?> keyType) {
        return keyType == Object.class || keyType == String.class || keyType == Long.class || keyType == Integer.class || keyType == Number.class;
    }

    @CompilerDirectives.TruffleBoundary
    private static RuntimeException newInvalidKeyTypeException(Type targetType, HostContext context) {
        String message = "Unsupported Map key type: " + targetType;
        return HostEngineException.classCast(context.access, message);
    }

    private static TypeAndClass<?> getGenericParameterType(Type genericType, int index) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parametrizedType = (ParameterizedType)genericType;
            Type[] typeArguments = parametrizedType.getActualTypeArguments();
            Class elementClass = Object.class;
            if (index < typeArguments.length) {
                Type elementType = typeArguments[index];
                if (elementType instanceof ParameterizedType) {
                    elementType = ((ParameterizedType)elementType).getRawType();
                }
                if (elementType instanceof Class) {
                    elementClass = (Class)elementType;
                }
                return new TypeAndClass(typeArguments[index], elementClass);
            }
        }
        return TypeAndClass.ANY;
    }

    private static Type getGenericArrayComponentType(Type genericType) {
        Type genericComponentType = null;
        if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)genericType;
            genericComponentType = genericArrayType.getGenericComponentType();
        }
        return genericComponentType;
    }

    private static Object truffleObjectToArray(HostContext hostContext, InteropLibrary interop, Object receiver, Class<?> arrayType, Type genericArrayType) {
        long size;
        Class<?> componentType = arrayType.getComponentType();
        try {
            size = interop.getArraySize(receiver);
        }
        catch (UnsupportedMessageException e1) {
            assert (false) : "unexpected language behavior";
            size = 0L;
        }
        size = Math.min(size, Integer.MAX_VALUE);
        Object array = Array.newInstance(componentType, (int)size);
        Type genericComponentType = HostToTypeNode.getGenericArrayComponentType(genericArrayType);
        int i = 0;
        while ((long)i < size) {
            Object guestValue;
            try {
                guestValue = interop.readArrayElement(receiver, i);
            }
            catch (InvalidArrayIndexException e) {
                throw HostInteropErrors.invalidArrayIndex(hostContext, receiver, componentType, i);
            }
            catch (UnsupportedMessageException e) {
                throw HostInteropErrors.arrayReadUnsupported(hostContext, receiver, componentType);
            }
            Object hostValue = HostToTypeNodeGen.getUncached().execute(hostContext, guestValue, componentType, genericComponentType, true);
            Array.set(array, i, hostValue);
            ++i;
        }
        return array;
    }

    static final class TypeAndClass<T> {
        static final TypeAndClass<Object> ANY = new TypeAndClass<Object>(null, Object.class);
        final Type type;
        final Class<T> clazz;

        TypeAndClass(Type type, Class<T> clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        public String toString() {
            return "[" + this.clazz + ": " + Objects.toString(this.type) + "]";
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.clazz == null ? 0 : this.clazz.hashCode());
            result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TypeAndClass)) {
                return false;
            }
            TypeAndClass other = (TypeAndClass)obj;
            return Objects.equals(this.clazz, other.clazz) && Objects.equals(this.type, other.type);
        }
    }
}

