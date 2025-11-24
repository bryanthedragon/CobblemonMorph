
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.host.GuestToHostCodeCache;
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostExecuteNodeGen;
import com.oracle.truffle.host.HostInteropErrors;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostMethodScope;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostTargetMapping;
import com.oracle.truffle.host.HostTargetMappingNode;
import com.oracle.truffle.host.HostTargetMappingNodeGen;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@ReportPolymorphism
@GenerateUncached
abstract class HostExecuteNode
extends Node {
    static final int LIMIT = 3;
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    HostExecuteNode() {
    }

    static HostExecuteNode create() {
        return HostExecuteNodeGen.create();
    }

    public abstract Object execute(HostMethodDesc var1, Object var2, Object[] var3, HostContext var4) throws UnsupportedTypeException, ArityException;

    static HostToTypeNode[] createToHost(int argsLength) {
        HostToTypeNode[] toJava = new HostToTypeNode[argsLength];
        for (int i = 0; i < argsLength; ++i) {
            toJava[i] = HostToTypeNodeGen.create();
        }
        return toJava;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExplodeLoop
    @Specialization(guards={"!method.isVarArgs()", "method == cachedMethod"}, limit="LIMIT")
    Object doFixed(HostMethodDesc.SingleMethod method, Object obj, Object[] args, HostContext hostContext, @Cached(value="method") HostMethodDesc.SingleMethod cachedMethod, @Cached(value="createToHost(method.getParameterCount())") HostToTypeNode[] toJavaNodes, @Cached HostContext.ToGuestValueNode toGuest, @Cached(value="createClassProfile()") ValueProfile receiverProfile, @Cached BranchProfile errorBranch, @Cached BranchProfile seenDynamicScope, @Cached(value="hostContext.getGuestToHostCache()", allowUncached=true) GuestToHostCodeCache cache) throws ArityException, UnsupportedTypeException {
        int arity = cachedMethod.getParameterCount();
        if (args.length != arity) {
            errorBranch.enter();
            throw ArityException.create(arity, arity, args.length);
        }
        Class<?>[] types = cachedMethod.getParameterTypes();
        Type[] genericTypes = cachedMethod.getGenericParameterTypes();
        Object[] convertedArguments = new Object[args.length];
        HostMethodScope scope = HostMethodScope.openStatic(cachedMethod);
        try {
            try {
                for (int i = 0; i < toJavaNodes.length; ++i) {
                    Object operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                    convertedArguments[i] = toJavaNodes[i].execute(hostContext, operand, types[i], genericTypes[i], true);
                }
            }
            catch (RuntimeException e) {
                errorBranch.enter();
                if (cache.language.access.isEngineException(e)) {
                    throw HostInteropErrors.unsupportedTypeException(args, (Throwable)cache.language.access.unboxEngineException(e));
                }
                throw e;
            }
            Object object = HostExecuteNode.doInvoke(cachedMethod, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
            return object;
        }
        finally {
            HostMethodScope.closeStatic(scope, cachedMethod, seenDynamicScope);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Specialization(guards={"method.isVarArgs()", "method == cachedMethod"}, limit="LIMIT")
    Object doVarArgs(HostMethodDesc.SingleMethod method, Object obj, Object[] args, HostContext hostContext, @Cached(value="method") HostMethodDesc.SingleMethod cachedMethod, @Cached HostToTypeNode toJavaNode, @Cached HostContext.ToGuestValueNode toGuest, @Cached(value="createClassProfile()") ValueProfile receiverProfile, @Cached BranchProfile errorBranch, @Cached BranchProfile seenDynamicScope, @Cached(value="asVarArgs(args, cachedMethod, hostContext)") boolean asVarArgs, @Cached(value="hostContext.getGuestToHostCache()", allowUncached=true) GuestToHostCodeCache cache) throws ArityException, UnsupportedTypeException {
        int parameterCount = cachedMethod.getParameterCount();
        int minArity = parameterCount - 1;
        if (args.length < minArity) {
            errorBranch.enter();
            throw ArityException.create(minArity, -1, args.length);
        }
        Class<?>[] types = cachedMethod.getParameterTypes();
        Type[] genericTypes = cachedMethod.getGenericParameterTypes();
        Object[] convertedArguments = new Object[args.length];
        HostMethodScope scope = HostMethodScope.openStatic(cachedMethod);
        try {
            try {
                int i;
                for (i = 0; i < minArity; ++i) {
                    Object operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                    convertedArguments[i] = toJavaNode.execute(hostContext, operand, types[i], genericTypes[i], true);
                }
                if (asVarArgs) {
                    for (i = minArity; i < args.length; ++i) {
                        Class<?> expectedType = types[minArity].getComponentType();
                        Type expectedGenericType = HostExecuteNode.getGenericComponentType(genericTypes[minArity]);
                        Object operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                        convertedArguments[i] = toJavaNode.execute(hostContext, operand, expectedType, expectedGenericType, true);
                    }
                    convertedArguments = HostExecuteNode.createVarArgsArray(cachedMethod, convertedArguments, parameterCount);
                } else {
                    Object operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, minArity, args[minArity]);
                    convertedArguments[minArity] = toJavaNode.execute(hostContext, operand, types[minArity], genericTypes[minArity], true);
                }
            }
            catch (RuntimeException e) {
                errorBranch.enter();
                if (cache.language.access.isEngineException(e)) {
                    throw HostInteropErrors.unsupportedTypeException(args, (Throwable)cache.language.access.unboxEngineException(e));
                }
                throw e;
            }
            Object object = HostExecuteNode.doInvoke(cachedMethod, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
            return object;
        }
        finally {
            HostMethodScope.closeStatic(scope, cachedMethod, seenDynamicScope);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Specialization(replaces={"doFixed", "doVarArgs"})
    static Object doSingleUncached(HostMethodDesc.SingleMethod method, Object obj, Object[] args, HostContext hostContext, @Cached.Shared(value="toHost") @Cached HostToTypeNode toJavaNode, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="varArgsProfile") @Cached ConditionProfile isVarArgsProfile, @Cached.Shared(value="hostMethodProfile") @Cached HostMethodProfileNode methodProfile, @Cached.Shared(value="errorBranch") @Cached BranchProfile errorBranch, @Cached.Shared(value="seenScope") @Cached BranchProfile seenScope, @Cached.Shared(value="cache") @Cached(value="hostContext.getGuestToHostCache()", allowUncached=true) GuestToHostCodeCache cache) throws ArityException, UnsupportedTypeException {
        boolean arityError;
        int maxArity;
        int minArity;
        int parameterCount = method.getParameterCount();
        if (isVarArgsProfile.profile(method.isVarArgs())) {
            minArity = parameterCount - 1;
            maxArity = -1;
            arityError = args.length < minArity;
        } else {
            minArity = parameterCount;
            maxArity = method.getParameterCount();
            boolean bl = arityError = args.length != minArity;
        }
        if (arityError) {
            errorBranch.enter();
            throw ArityException.create(minArity, maxArity, args.length);
        }
        HostMethodScope scope = HostMethodScope.openDynamic(method, args.length, seenScope);
        try {
            Object[] convertedArguments;
            try {
                convertedArguments = HostExecuteNode.prepareArgumentsUncached(method, args, hostContext, toJavaNode, scope, isVarArgsProfile);
            }
            catch (RuntimeException e) {
                errorBranch.enter();
                if (cache.language.access.isEngineException(e)) {
                    throw HostInteropErrors.unsupportedTypeException(args, (Throwable)cache.language.access.unboxEngineException(e));
                }
                throw e;
            }
            Object object = HostExecuteNode.doInvoke(methodProfile.execute(method), obj, convertedArguments, cache, hostContext, toGuest);
            return object;
        }
        finally {
            HostMethodScope.closeDynamic(scope, method);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExplodeLoop
    @Specialization(guards={"method == cachedMethod", "checkArgTypes(args, cachedArgTypes, interop, hostContext, asVarArgs)"}, limit="LIMIT")
    final Object doOverloadedCached(HostMethodDesc.OverloadedMethod method, Object obj, Object[] args, HostContext hostContext, @Cached(value="method") HostMethodDesc.OverloadedMethod cachedMethod, @Cached HostToTypeNode toJavaNode, @Cached HostContext.ToGuestValueNode toGuest, @CachedLibrary(limit="LIMIT") InteropLibrary interop, @Cached(value="createArgTypesArray(args)") TypeCheckNode[] cachedArgTypes, @Cached(value="selectOverload(method, args, hostContext, cachedArgTypes)") HostMethodDesc.SingleMethod overload, @Cached(value="asVarArgs(args, overload, hostContext)") boolean asVarArgs, @Cached(value="createClassProfile()") ValueProfile receiverProfile, @Cached BranchProfile errorBranch, @Cached BranchProfile seenVariableScope, @Cached(value="hostContext.getGuestToHostCache()", allowUncached=true) GuestToHostCodeCache cache) throws ArityException, UnsupportedTypeException {
        assert (overload == this.selectOverload(method, args, hostContext));
        Class<?>[] types = overload.getParameterTypes();
        Type[] genericTypes = overload.getGenericParameterTypes();
        Object[] convertedArguments = new Object[cachedArgTypes.length];
        HostMethodScope scope = HostMethodScope.openStatic(overload);
        try {
            try {
                if (asVarArgs) {
                    assert (overload.isVarArgs());
                    int parameterCount = overload.getParameterCount();
                    for (int i = 0; i < cachedArgTypes.length; ++i) {
                        Class<?> expectedType = i < parameterCount - 1 ? types[i] : types[parameterCount - 1].getComponentType();
                        Type expectedGenericType = i < parameterCount - 1 ? genericTypes[i] : HostExecuteNode.getGenericComponentType(genericTypes[parameterCount - 1]);
                        Object operand = HostMethodScope.addToScopeStatic(scope, overload, i, args[i]);
                        convertedArguments[i] = toJavaNode.execute(hostContext, operand, expectedType, expectedGenericType, true);
                    }
                    convertedArguments = HostExecuteNode.createVarArgsArray(overload, convertedArguments, parameterCount);
                } else {
                    for (int i = 0; i < cachedArgTypes.length; ++i) {
                        Object operand = HostMethodScope.addToScopeStatic(scope, overload, i, args[i]);
                        convertedArguments[i] = toJavaNode.execute(hostContext, operand, types[i], genericTypes[i], true);
                    }
                }
            }
            catch (RuntimeException e) {
                errorBranch.enter();
                if (cache.language.access.isEngineException(e)) {
                    throw HostInteropErrors.unsupportedTypeException(args, (Throwable)cache.language.access.unboxEngineException(e));
                }
                throw e;
            }
            Object object = HostExecuteNode.doInvoke(overload, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
            return object;
        }
        finally {
            HostMethodScope.closeStatic(scope, overload, seenVariableScope);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Specialization(replaces={"doOverloadedCached"})
    final Object doOverloadedUncached(HostMethodDesc.OverloadedMethod method, Object obj, Object[] args, HostContext hostContext, @Cached.Shared(value="toHost") @Cached HostToTypeNode toJavaNode, @Cached.Shared(value="toGuest") @Cached HostContext.ToGuestValueNode toGuest, @Cached.Shared(value="varArgsProfile") @Cached ConditionProfile isVarArgsProfile, @Cached.Shared(value="hostMethodProfile") @Cached HostMethodProfileNode methodProfile, @Cached.Shared(value="errorBranch") @Cached BranchProfile errorBranch, @Cached.Shared(value="seenScope") @Cached BranchProfile seenScope, @Cached.Shared(value="cache") @Cached(value="hostContext.getGuestToHostCache()", allowUncached=true) GuestToHostCodeCache cache) throws ArityException, UnsupportedTypeException {
        HostMethodDesc.SingleMethod overload = this.selectOverload(method, args, hostContext);
        HostMethodScope scope = HostMethodScope.openDynamic(overload, args.length, seenScope);
        try {
            Object[] convertedArguments;
            try {
                convertedArguments = HostExecuteNode.prepareArgumentsUncached(overload, args, hostContext, toJavaNode, scope, isVarArgsProfile);
            }
            catch (RuntimeException e) {
                errorBranch.enter();
                if (cache.language.access.isEngineException(e)) {
                    throw HostInteropErrors.unsupportedTypeException(args, (Throwable)cache.language.access.unboxEngineException(e));
                }
                throw e;
            }
            Object object = HostExecuteNode.doInvoke(methodProfile.execute(overload), obj, convertedArguments, cache, hostContext, toGuest);
            return object;
        }
        finally {
            HostMethodScope.closeDynamic(scope, overload);
        }
    }

    private static Object[] prepareArgumentsUncached(HostMethodDesc.SingleMethod method, Object[] args, HostContext context, HostToTypeNode toJavaNode, HostMethodScope scope, ConditionProfile isVarArgsProfile) {
        Class<?>[] types = method.getParameterTypes();
        Type[] genericTypes = method.getGenericParameterTypes();
        Object[] convertedArguments = new Object[args.length];
        if (isVarArgsProfile.profile(method.isVarArgs()) && HostExecuteNode.asVarArgs(args, method, context)) {
            int parameterCount = method.getParameterCount();
            for (int i = 0; i < args.length; ++i) {
                Class<?> expectedType = i < parameterCount - 1 ? types[i] : types[parameterCount - 1].getComponentType();
                Type expectedGenericType = i < parameterCount - 1 ? genericTypes[i] : HostExecuteNode.getGenericComponentType(genericTypes[parameterCount - 1]);
                Object operand = HostMethodScope.addToScopeDynamic(scope, args[i]);
                convertedArguments[i] = toJavaNode.execute(context, operand, expectedType, expectedGenericType, true);
            }
            convertedArguments = HostExecuteNode.createVarArgsArray(method, convertedArguments, parameterCount);
        } else {
            for (int i = 0; i < args.length; ++i) {
                Object operand = HostMethodScope.addToScopeDynamic(scope, args[i]);
                convertedArguments[i] = toJavaNode.execute(context, operand, types[i], genericTypes[i], true);
            }
        }
        return convertedArguments;
    }

    static TypeCheckNode[] createArgTypesArray(Object[] args) {
        Object[] nodes = new TypeCheckNode[args.length];
        Arrays.fill(nodes, NullCheckNode.INSTANCE);
        return nodes;
    }

    private void fillArgTypesArray(Object[] args, TypeCheckNode[] cachedArgTypes, HostMethodDesc.SingleMethod selected, boolean varArgs, List<HostMethodDesc.SingleMethod> applicable, int priority, HostContext context) {
        if (cachedArgTypes == null) {
            return;
        }
        HostClassCache cache = context.getHostClassCache();
        boolean multiple = applicable.size() > 1;
        for (int i = 0; i < args.length; ++i) {
            Object arg = args[i];
            Class<?> targetType = HostExecuteNode.getParameterType(selected.getParameterTypes(), i, varArgs);
            LinkedHashSet<HostTargetMapping> otherPossibleMappings = null;
            if (multiple) {
                for (HostMethodDesc.SingleMethod other : applicable) {
                    Class<?> paramType;
                    if (other == selected || other.isVarArgs() != varArgs || (paramType = HostExecuteNode.getParameterType(other.getParameterTypes(), i, varArgs)) == targetType || HostToTypeNode.canConvert(arg, paramType, paramType, null, context, priority, InteropLibrary.getFactory().getUncached(), HostTargetMappingNode.getUncached())) continue;
                    HostTargetMapping[] otherMappings = cache.getMappings(paramType);
                    if (otherPossibleMappings == null) {
                        otherPossibleMappings = new LinkedHashSet<HostTargetMapping>();
                    }
                    for (HostTargetMapping mapping : otherMappings) {
                        otherPossibleMappings.add(mapping);
                    }
                }
            }
            TypeCheckNode argType = arg == null ? NullCheckNode.INSTANCE : (multiple && HostToTypeNode.isPrimitiveTarget(targetType) ? HostExecuteNode.createPrimitiveTargetCheck(applicable, selected, arg, targetType, i, priority, varArgs) : (arg instanceof HostObject ? new JavaObjectType(((HostObject)arg).getObjectClass()) : new DirectTypeCheck(arg.getClass())));
            HostTargetMapping[] mappings = cache.getMappings(targetType);
            if (mappings.length > 0 || otherPossibleMappings != null) {
                HostTargetMapping[] otherMappings = otherPossibleMappings != null ? otherPossibleMappings.toArray(HostClassCache.EMPTY_MAPPINGS) : HostClassCache.EMPTY_MAPPINGS;
                argType = new TargetMappingType(argType, mappings, otherMappings, priority);
            }
            cachedArgTypes[i] = this.insert(argType);
        }
        assert (HostExecuteNode.checkArgTypes(args, cachedArgTypes, InteropLibrary.getFactory().getUncached(), context, false)) : Arrays.toString(cachedArgTypes);
    }

    private static TypeCheckNode createPrimitiveTargetCheck(List<HostMethodDesc.SingleMethod> applicable, HostMethodDesc.SingleMethod selected, Object arg, Class<?> targetType, int parameterIndex, int priority, boolean varArgs) {
        Class<?> currentTargetType = targetType;
        ArrayList otherPossibleTypes = new ArrayList();
        for (HostMethodDesc.SingleMethod other : applicable) {
            Class<?> paramType;
            if (other == selected || other.isVarArgs() != varArgs || (paramType = HostExecuteNode.getParameterType(other.getParameterTypes(), parameterIndex, varArgs)) == targetType || otherPossibleTypes.contains(paramType) || !HostToTypeNode.isPrimitiveTarget(paramType) && !HostToTypeNode.isPrimitiveTarget(targetType) || !HostExecuteNode.isAssignableFrom(targetType, paramType) || HostExecuteNode.isSubtypeOf(arg, paramType)) continue;
            otherPossibleTypes.add(paramType);
        }
        return new PrimitiveType(currentTargetType, otherPossibleTypes.toArray(EMPTY_CLASS_ARRAY), priority);
    }

    @ExplodeLoop
    static boolean checkArgTypes(Object[] args, TypeCheckNode[] argTypes, InteropLibrary interop, HostContext context, boolean dummy2) {
        if (args.length != argTypes.length) {
            return false;
        }
        for (int i = 0; i < argTypes.length; ++i) {
            TypeCheckNode argType = argTypes[i];
            if (argType.execute(args[i], interop, context)) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean asVarArgs(Object[] args, HostMethodDesc.SingleMethod overload, HostContext hostContext) {
        if (overload.isVarArgs()) {
            int parameterCount = overload.getParameterCount();
            if (args.length == parameterCount) {
                Class<?> varArgParamType = overload.getParameterTypes()[parameterCount - 1];
                return !HostToTypeNode.canConvert(args[parameterCount - 1], varArgParamType, overload.getGenericParameterTypes()[parameterCount - 1], null, hostContext, 3, InteropLibrary.getFactory().getUncached(), HostTargetMappingNode.getUncached());
            }
            assert (args.length != parameterCount);
            return true;
        }
        return false;
    }

    static Class<?> primitiveTypeToBoxedType(Class<?> primitiveType) {
        assert (primitiveType.isPrimitive());
        if (primitiveType == Boolean.TYPE) {
            return Boolean.class;
        }
        if (primitiveType == Byte.TYPE) {
            return Byte.class;
        }
        if (primitiveType == Short.TYPE) {
            return Short.class;
        }
        if (primitiveType == Character.TYPE) {
            return Character.class;
        }
        if (primitiveType == Integer.TYPE) {
            return Integer.class;
        }
        if (primitiveType == Long.TYPE) {
            return Long.class;
        }
        if (primitiveType == Float.TYPE) {
            return Float.class;
        }
        if (primitiveType == Double.TYPE) {
            return Double.class;
        }
        throw new IllegalArgumentException();
    }

    static Class<?> boxedTypeToPrimitiveType(Class<?> primitiveType) {
        if (primitiveType == Boolean.class) {
            return Boolean.TYPE;
        }
        if (primitiveType == Byte.class) {
            return Byte.TYPE;
        }
        if (primitiveType == Short.class) {
            return Short.TYPE;
        }
        if (primitiveType == Character.class) {
            return Character.TYPE;
        }
        if (primitiveType == Integer.class) {
            return Integer.TYPE;
        }
        if (primitiveType == Long.class) {
            return Long.TYPE;
        }
        if (primitiveType == Float.class) {
            return Float.TYPE;
        }
        if (primitiveType == Double.class) {
            return Double.TYPE;
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    HostMethodDesc.SingleMethod selectOverload(HostMethodDesc.OverloadedMethod method, Object[] args, HostContext hostContext) throws ArityException, UnsupportedTypeException {
        return this.selectOverload(method, args, hostContext, null);
    }

    @CompilerDirectives.TruffleBoundary
    HostMethodDesc.SingleMethod selectOverload(HostMethodDesc.OverloadedMethod method, Object[] args, HostContext hostContext, TypeCheckNode[] cachedArgTypes) throws ArityException, UnsupportedTypeException {
        HostMethodDesc.SingleMethod[] overloads = method.getOverloads();
        ArrayList<HostMethodDesc.SingleMethod> applicableByArity = new ArrayList<HostMethodDesc.SingleMethod>();
        int minOverallArity = Integer.MAX_VALUE;
        int maxOverallArity = 0;
        boolean anyVarArgs = false;
        assert (overloads.length > 0);
        for (HostMethodDesc.SingleMethod overload : overloads) {
            int paramCount = overload.getParameterCount();
            if (overload.isVarArgs()) {
                anyVarArgs = true;
                int fixedParamCount = paramCount - 1;
                if (args.length < fixedParamCount) {
                    minOverallArity = Math.min(minOverallArity, fixedParamCount);
                    maxOverallArity = Math.max(maxOverallArity, fixedParamCount);
                    continue;
                }
            } else if (args.length != paramCount) {
                minOverallArity = Math.min(minOverallArity, paramCount);
                maxOverallArity = Math.max(maxOverallArity, paramCount);
                continue;
            }
            applicableByArity.add(overload);
        }
        if (applicableByArity.isEmpty()) {
            throw ArityException.create(minOverallArity, anyVarArgs ? -1 : maxOverallArity, args.length);
        }
        for (int priority : HostToTypeNode.PRIORITIES) {
            HostMethodDesc.SingleMethod best = this.findBestCandidate(applicableByArity, args, hostContext, false, priority, cachedArgTypes);
            if (best != null) {
                return best;
            }
            if (!anyVarArgs || (best = this.findBestCandidate(applicableByArity, args, hostContext, true, priority, cachedArgTypes)) == null) continue;
            return best;
        }
        throw HostExecuteNode.noApplicableOverloadsException(overloads, args);
    }

    private HostMethodDesc.SingleMethod findBestCandidate(List<HostMethodDesc.SingleMethod> applicableByArity, Object[] args, HostContext hostContext, boolean varArgs, int priority, TypeCheckNode[] cachedArgTypes) throws UnsupportedTypeException {
        ArrayList<HostMethodDesc.SingleMethod> candidates = new ArrayList<HostMethodDesc.SingleMethod>();
        if (!varArgs) {
            for (HostMethodDesc.SingleMethod candidate : applicableByArity) {
                int paramCount = candidate.getParameterCount();
                if (candidate.isOnlyVisibleFromJniName() || candidate.isVarArgs() && paramCount != args.length) continue;
                assert (paramCount == args.length);
                Class<?>[] parameterTypes = candidate.getParameterTypes();
                Type[] genericParameterTypes = candidate.getGenericParameterTypes();
                boolean applicable = true;
                for (int i = 0; i < paramCount; ++i) {
                    if (HostToTypeNode.canConvert(args[i], parameterTypes[i], genericParameterTypes[i], null, hostContext, priority, InteropLibrary.getFactory().getUncached(args[i]), HostTargetMappingNode.getUncached())) continue;
                    applicable = false;
                    break;
                }
                if (!applicable) continue;
                candidates.add(candidate);
            }
        } else {
            for (HostMethodDesc.SingleMethod candidate : applicableByArity) {
                if (!candidate.isVarArgs() || candidate.isOnlyVisibleFromJniName()) continue;
                int parameterCount = candidate.getParameterCount();
                Class<?>[] parameterTypes = candidate.getParameterTypes();
                Type[] genericParameterTypes = candidate.getGenericParameterTypes();
                boolean applicable = true;
                for (int i = 0; i < parameterCount - 1; ++i) {
                    if (HostToTypeNode.canConvert(args[i], parameterTypes[i], genericParameterTypes[i], null, hostContext, priority, InteropLibrary.getFactory().getUncached(args[i]), HostTargetMappingNode.getUncached())) continue;
                    applicable = false;
                    break;
                }
                if (!applicable) continue;
                Class<?> varArgsComponentType = parameterTypes[parameterCount - 1].getComponentType();
                Type varArgsGenericComponentType = genericParameterTypes[parameterCount - 1];
                if (varArgsGenericComponentType instanceof GenericArrayType) {
                    GenericArrayType arrayType = (GenericArrayType)varArgsGenericComponentType;
                    varArgsGenericComponentType = arrayType.getGenericComponentType();
                } else {
                    varArgsGenericComponentType = varArgsComponentType;
                }
                for (int i = parameterCount - 1; i < args.length; ++i) {
                    if (HostToTypeNode.canConvert(args[i], varArgsComponentType, varArgsGenericComponentType, null, hostContext, priority, InteropLibrary.getFactory().getUncached(args[i]), HostTargetMappingNode.getUncached())) continue;
                    applicable = false;
                    break;
                }
                if (!applicable) continue;
                candidates.add(candidate);
            }
        }
        if (!candidates.isEmpty()) {
            HostMethodDesc.SingleMethod best;
            if (candidates.size() == 1) {
                best = (HostMethodDesc.SingleMethod)candidates.get(0);
                if (cachedArgTypes != null) {
                    this.fillArgTypesArray(args, cachedArgTypes, best, varArgs, applicableByArity, priority, hostContext);
                }
                return best;
            }
            best = HostExecuteNode.findMostSpecificOverload(hostContext, candidates, args, varArgs, priority);
            if (best != null) {
                if (cachedArgTypes != null) {
                    this.fillArgTypesArray(args, cachedArgTypes, best, varArgs, applicableByArity, priority, hostContext);
                }
                return best;
            }
            throw HostExecuteNode.ambiguousOverloadsException(candidates, args);
        }
        return null;
    }

    private static HostMethodDesc.SingleMethod findMostSpecificOverload(HostContext context, List<HostMethodDesc.SingleMethod> candidates, Object[] args, boolean varArgs, int priority) {
        assert (candidates.size() >= 2);
        if (candidates.size() == 2) {
            int res = HostExecuteNode.compareOverloads(context, candidates.get(0), candidates.get(1), args, varArgs, priority);
            return res == 0 ? null : (res < 0 ? candidates.get(0) : candidates.get(1));
        }
        Iterator<HostMethodDesc.SingleMethod> candIt = candidates.iterator();
        LinkedList<HostMethodDesc.SingleMethod> best = new LinkedList<HostMethodDesc.SingleMethod>();
        best.add(candIt.next());
        while (candIt.hasNext()) {
            HostMethodDesc.SingleMethod cand = candIt.next();
            boolean add2 = false;
            Iterator bestIt = best.iterator();
            while (bestIt.hasNext()) {
                int res = HostExecuteNode.compareOverloads(context, cand, (HostMethodDesc.SingleMethod)bestIt.next(), args, varArgs, priority);
                if (res == 0) {
                    add2 = true;
                    continue;
                }
                if (res < 0) {
                    bestIt.remove();
                    add2 = true;
                    continue;
                }
                assert (res > 0);
            }
            if (!add2) continue;
            best.add(cand);
        }
        assert (!best.isEmpty());
        if (best.size() == 1) {
            return (HostMethodDesc.SingleMethod)best.get(0);
        }
        return null;
    }

    private static int compareOverloads(HostContext context, HostMethodDesc.SingleMethod m1, HostMethodDesc.SingleMethod m2, Object[] args, boolean varArgs, int priority) {
        int res = 0;
        assert (!varArgs || m1.isVarArgs() && m2.isVarArgs());
        assert (varArgs || m1.getParameterCount() == m2.getParameterCount() && args.length == m1.getParameterCount());
        for (int i = 0; i < args.length; ++i) {
            int r;
            Class<?> t2;
            Class<?> t1 = HostExecuteNode.getParameterType(m1.getParameterTypes(), i, varArgs);
            if (t1 == (t2 = HostExecuteNode.getParameterType(m2.getParameterTypes(), i, varArgs)) || (r = HostExecuteNode.compareByPriority(context, t1, t2, args[i], priority)) == 0 && (r = HostExecuteNode.compareAssignable(t1, t2)) == 0) continue;
            if (res == 0) {
                res = r;
                continue;
            }
            if (res == r) continue;
            res = 0;
            break;
        }
        return res;
    }

    private static Class<?> getParameterType(Class<?>[] parameterTypes, int i, boolean varArgs) {
        return varArgs && i >= parameterTypes.length - 1 ? parameterTypes[parameterTypes.length - 1].getComponentType() : parameterTypes[i];
    }

    private static int compareByPriority(HostContext context, Class<?> t1, Class<?> t2, Object arg, int priority) {
        if (priority <= 1) {
            return 0;
        }
        InteropLibrary argInterop = InteropLibrary.getFactory().getUncached(arg);
        HostTargetMappingNode mapping = HostTargetMappingNode.getUncached();
        for (int p : HostToTypeNode.PRIORITIES) {
            boolean p2;
            if (p > priority) break;
            boolean p1 = HostToTypeNode.canConvert(arg, t1, t1, null, context, p, argInterop, mapping);
            if (p1 == (p2 = HostToTypeNode.canConvert(arg, t2, t2, null, context, p, argInterop, mapping))) continue;
            return p1 ? -1 : 1;
        }
        return 0;
    }

    private static int compareAssignable(Class<?> t1, Class<?> t2) {
        if (HostExecuteNode.isAssignableFrom(t1, t2)) {
            return 1;
        }
        if (HostExecuteNode.isAssignableFrom(t2, t1)) {
            return -1;
        }
        return 0;
    }

    private static boolean isAssignableFrom(Class<?> toType, Class<?> fromType) {
        Class<?> toAsPrimitive;
        if (toType.isAssignableFrom(fromType)) {
            return true;
        }
        boolean fromIsPrimitive = fromType.isPrimitive();
        boolean toIsPrimitive = toType.isPrimitive();
        Class<?> fromAsPrimitive = fromIsPrimitive ? fromType : HostExecuteNode.boxedTypeToPrimitiveType(fromType);
        Class<?> clazz = toAsPrimitive = toIsPrimitive ? toType : HostExecuteNode.boxedTypeToPrimitiveType(toType);
        if (toAsPrimitive != null && fromAsPrimitive != null) {
            if (toAsPrimitive == fromAsPrimitive) {
                assert (fromIsPrimitive != toIsPrimitive);
                return fromIsPrimitive;
            }
            if (HostExecuteNode.isWideningPrimitiveConversion(toAsPrimitive, fromAsPrimitive)) {
                return true;
            }
        } else {
            if (fromAsPrimitive == Character.TYPE && (toType == String.class || toType == CharSequence.class)) {
                return true;
            }
            if (toAsPrimitive == null && fromAsPrimitive != null && toType.isAssignableFrom(HostExecuteNode.primitiveTypeToBoxedType(fromAsPrimitive))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSubtypeOf(Object argument, Class<?> parameterType) {
        Class<?> boxedToPrimitive;
        Object value2 = argument;
        if (argument instanceof HostObject) {
            value2 = ((HostObject)argument).obj;
        }
        if (!parameterType.isPrimitive()) {
            return value2 == null || parameterType.isInstance(value2) && !(value2 instanceof TruffleObject);
        }
        if (value2 != null && (boxedToPrimitive = HostExecuteNode.boxedTypeToPrimitiveType(value2.getClass())) != null) {
            return boxedToPrimitive == parameterType || HostExecuteNode.isWideningPrimitiveConversion(parameterType, boxedToPrimitive);
        }
        return false;
    }

    private static boolean isWideningPrimitiveConversion(Class<?> toType, Class<?> fromType) {
        assert (toType.isPrimitive());
        if (fromType == Byte.TYPE) {
            return toType == Short.TYPE || toType == Integer.TYPE || toType == Long.TYPE || toType == Float.TYPE || toType == Double.TYPE;
        }
        if (fromType == Short.TYPE) {
            return toType == Integer.TYPE || toType == Long.TYPE || toType == Float.TYPE || toType == Double.TYPE;
        }
        if (fromType == Character.TYPE) {
            return toType == Integer.TYPE || toType == Long.TYPE || toType == Float.TYPE || toType == Double.TYPE;
        }
        if (fromType == Integer.TYPE) {
            return toType == Long.TYPE || toType == Float.TYPE || toType == Double.TYPE;
        }
        if (fromType == Long.TYPE) {
            return toType == Float.TYPE || toType == Double.TYPE;
        }
        if (fromType == Float.TYPE) {
            return toType == Double.TYPE;
        }
        return false;
    }

    private static RuntimeException ambiguousOverloadsException(List<HostMethodDesc.SingleMethod> candidates, Object[] args) throws UnsupportedTypeException {
        String message = String.format("Multiple applicable overloads found for method name %s (candidates: %s, arguments: %s)", candidates.get(0).getName(), candidates, HostExecuteNode.arrayToStringWithTypes(args));
        throw UnsupportedTypeException.create(args, message);
    }

    private static RuntimeException noApplicableOverloadsException(HostMethodDesc.SingleMethod[] overloads, Object[] args) throws UnsupportedTypeException {
        String message = String.format("no applicable overload found (overloads: %s, arguments: %s)", Arrays.toString(overloads), HostExecuteNode.arrayToStringWithTypes(args));
        throw UnsupportedTypeException.create(args, message);
    }

    private static Type getGenericComponentType(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType)type).getGenericComponentType() : ((Class)type).getComponentType();
    }

    @CompilerDirectives.TruffleBoundary
    private static Object[] createVarArgsArray(HostMethodDesc.SingleMethod method, Object[] args, int parameterCount) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = new Object[parameterCount];
        for (int i = 0; i < parameterCount - 1; ++i) {
            arguments[i] = args[i];
        }
        Class<?> varArgsType = parameterTypes[parameterCount - 1].getComponentType();
        Object varArgs = Array.newInstance(varArgsType, args.length - parameterCount + 1);
        int i = parameterCount - 1;
        int j = 0;
        while (i < args.length) {
            Array.set(varArgs, j, args[i]);
            ++i;
            ++j;
        }
        arguments[parameterCount - 1] = varArgs;
        return arguments;
    }

    private static Object doInvoke(HostMethodDesc.SingleMethod method, Object obj, Object[] arguments, GuestToHostCodeCache cache, HostContext hostContext, HostContext.ToGuestValueNode toGuest) {
        assert (cache == hostContext.getGuestToHostCache());
        assert (arguments.length == method.getParameterCount());
        Object ret = method.invokeGuestToHost(obj, arguments, cache, hostContext, toGuest);
        return toGuest.execute(hostContext, ret);
    }

    private static String arrayToStringWithTypes(Object[] args) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Object arg : args) {
            sj.add(arg == null ? null : arg.toString() + " (" + arg.getClass().getSimpleName() + ")");
        }
        return sj.toString();
    }

    @GenerateUncached
    static abstract class HostMethodProfileNode
    extends Node {
        HostMethodProfileNode() {
        }

        public abstract HostMethodDesc.SingleMethod execute(HostMethodDesc.SingleMethod var1);

        @Specialization
        static HostMethodDesc.SingleMethod mono(HostMethodDesc.SingleMethod.MHBase method) {
            return method;
        }

        @Specialization
        static HostMethodDesc.SingleMethod mono(HostMethodDesc.SingleMethod.ReflectBase method) {
            return method;
        }

        @Specialization(replaces={"mono"})
        static HostMethodDesc.SingleMethod poly(HostMethodDesc.SingleMethod method) {
            return method;
        }
    }

    static final class PrimitiveType
    extends TypeCheckNode {
        final Class<?> targetType;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final Class<?>[] otherTypes;
        final int priority;

        PrimitiveType(Class<?> targetType, Class<?>[] otherTypes, int priority) {
            this.targetType = targetType;
            this.otherTypes = otherTypes;
            this.priority = priority;
        }

        public int hashCode() {
            return this.targetType == null ? 0 : this.targetType.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrimitiveType)) {
                return false;
            }
            PrimitiveType other = (PrimitiveType)obj;
            return Objects.equals(this.targetType, other.targetType) && Arrays.equals(this.otherTypes, other.otherTypes);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Primitive[");
            sb.append(this.targetType.getTypeName());
            if (this.otherTypes.length > 0) {
                for (Class<?> otherType : this.otherTypes) {
                    sb.append(", !");
                    sb.append(otherType.getTypeName());
                }
            }
            sb.append(']');
            return sb.toString();
        }

        @Override
        @ExplodeLoop
        public boolean execute(Object value2, InteropLibrary interop, HostContext context) {
            for (Class<?> otherType : this.otherTypes) {
                if (!HostToTypeNode.canConvert(value2, otherType, otherType, null, context, this.priority, interop, null)) continue;
                return false;
            }
            return HostToTypeNode.canConvert(value2, this.targetType, this.targetType, null, context, this.priority, interop, null);
        }
    }

    static final class TargetMappingType
    extends TypeCheckNode {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final HostTargetMapping[] mappings;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final HostTargetMapping[] otherMappings;
        @Node.Child
        TypeCheckNode fallback;
        @Node.Children
        final HostTargetMappingNode.SingleMappingNode[] mappingNodes;
        @Node.Children
        final HostTargetMappingNode.SingleMappingNode[] otherMappingNodes;
        final int priority;

        TargetMappingType(TypeCheckNode fallback, HostTargetMapping[] mappings, HostTargetMapping[] otherMappings, int priority) {
            int i;
            this.fallback = fallback;
            this.priority = priority;
            this.mappings = mappings;
            this.otherMappings = otherMappings;
            this.mappingNodes = new HostTargetMappingNode.SingleMappingNode[mappings.length];
            for (i = 0; i < mappings.length; ++i) {
                this.mappingNodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
            }
            this.otherMappingNodes = new HostTargetMappingNode.SingleMappingNode[otherMappings.length];
            for (i = 0; i < otherMappings.length; ++i) {
                this.otherMappingNodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
            }
        }

        @Override
        @ExplodeLoop
        boolean execute(Object test, InteropLibrary interop, HostContext context) {
            Object result;
            HostTargetMapping mapping;
            int i;
            for (i = 0; i < this.otherMappingNodes.length; ++i) {
                mapping = this.otherMappings[i];
                if (mapping.hostPriority > this.priority) break;
                result = this.otherMappingNodes[i].execute(test, mapping, context, interop, true);
                if (result != Boolean.TRUE) continue;
                return false;
            }
            for (i = 0; i < this.mappingNodes.length; ++i) {
                mapping = this.mappings[i];
                if (mapping.hostPriority > this.priority) break;
                result = this.mappingNodes[i].execute(test, mapping, context, interop, true);
                if (result != Boolean.TRUE) continue;
                return true;
            }
            return this.fallback.execute(test, interop, context);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TargetMappingType)) {
                return false;
            }
            TargetMappingType other = (TargetMappingType)obj;
            return Arrays.equals(this.mappings, other.mappings);
        }

        public int hashCode() {
            return Arrays.hashCode(this.mappings);
        }
    }

    static final class JavaObjectType
    extends TypeCheckNode {
        final Class<?> clazz;

        JavaObjectType(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        boolean execute(Object arg, InteropLibrary interop, HostContext context) {
            return arg instanceof HostObject && ((HostObject)arg).getObjectClass() == this.clazz;
        }

        public int hashCode() {
            return this.clazz == null ? 0 : this.clazz.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof JavaObjectType)) {
                return false;
            }
            JavaObjectType other = (JavaObjectType)obj;
            return Objects.equals(this.clazz, other.clazz);
        }

        @Override
        public String toString() {
            return "JavaObject[" + this.clazz.getTypeName() + "]";
        }
    }

    static final class DirectTypeCheck
    extends TypeCheckNode {
        final Class<?> clazz;

        DirectTypeCheck(Class<?> clazz) {
            this.clazz = clazz;
        }

        @Override
        boolean execute(Object test, InteropLibrary interop, HostContext context) {
            return test != null && test.getClass() == this.clazz;
        }

        @Override
        public String toString() {
            return this.clazz.toString();
        }
    }

    static final class NullCheckNode
    extends TypeCheckNode {
        static final NullCheckNode INSTANCE = new NullCheckNode();

        NullCheckNode() {
        }

        @Override
        boolean execute(Object test, InteropLibrary interop, HostContext context) {
            return test == null;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }

        @Override
        public String toString() {
            return "null";
        }
    }

    static abstract class TypeCheckNode
    extends Node {
        TypeCheckNode() {
        }

        abstract boolean execute(Object var1, InteropLibrary var2, HostContext var3);
    }
}

