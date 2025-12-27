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
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@ReportPolymorphism
@GenerateUncached
abstract class HostExecuteNode extends Node {
   static final int LIMIT = 3;
   private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

   static HostExecuteNode create() {
      return HostExecuteNodeGen.create();
   }

   public abstract Object execute(HostMethodDesc method, Object obj, Object[] args, HostContext hostContext) throws UnsupportedTypeException, ArityException;

   static HostToTypeNode[] createToHost(int argsLength) {
      HostToTypeNode[] toJava = new HostToTypeNode[argsLength];

      for (int i = 0; i < argsLength; i++) {
         toJava[i] = HostToTypeNodeGen.create();
      }

      return toJava;
   }

   @ExplodeLoop
   @Specialization(guards = {"!method.isVarArgs()", "method == cachedMethod"}, limit = "LIMIT")
   Object doFixed(
      HostMethodDesc.SingleMethod method,
      Object obj,
      Object[] args,
      HostContext hostContext,
      @Cached("method") HostMethodDesc.SingleMethod cachedMethod,
      @Cached("createToHost(method.getParameterCount())") HostToTypeNode[] toJavaNodes,
      @Cached HostContext.ToGuestValueNode toGuest,
      @Cached("createClassProfile()") ValueProfile receiverProfile,
      @Cached BranchProfile errorBranch,
      @Cached BranchProfile seenDynamicScope,
      @Cached(value = "hostContext.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws ArityException, UnsupportedTypeException {
      int arity = cachedMethod.getParameterCount();
      if (args.length != arity) {
         errorBranch.enter();
         throw ArityException.create(arity, arity, args.length);
      } else {
         Class<?>[] types = cachedMethod.getParameterTypes();
         Type[] genericTypes = cachedMethod.getGenericParameterTypes();
         Object[] convertedArguments = new Object[args.length];
         HostMethodScope scope = HostMethodScope.openStatic(cachedMethod);

         Object var24;
         try {
            try {
               for (int i = 0; i < toJavaNodes.length; i++) {
                  Object operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                  convertedArguments[i] = toJavaNodes[i].execute(hostContext, operand, types[i], genericTypes[i], true);
               }
            } catch (RuntimeException var22) {
               errorBranch.enter();
               if (cache.language.access.isEngineException(var22)) {
                  throw HostInteropErrors.unsupportedTypeException(args, cache.language.access.unboxEngineException(var22));
               }

               throw var22;
            }

            var24 = doInvoke(cachedMethod, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
         } finally {
            HostMethodScope.closeStatic(scope, cachedMethod, seenDynamicScope);
         }

         return var24;
      }
   }

   @Specialization(guards = {"method.isVarArgs()", "method == cachedMethod"}, limit = "LIMIT")
   Object doVarArgs(
      HostMethodDesc.SingleMethod method,
      Object obj,
      Object[] args,
      HostContext hostContext,
      @Cached("method") HostMethodDesc.SingleMethod cachedMethod,
      @Cached HostToTypeNode toJavaNode,
      @Cached HostContext.ToGuestValueNode toGuest,
      @Cached("createClassProfile()") ValueProfile receiverProfile,
      @Cached BranchProfile errorBranch,
      @Cached BranchProfile seenDynamicScope,
      @Cached("asVarArgs(args, cachedMethod, hostContext)") boolean asVarArgs,
      @Cached(value = "hostContext.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws ArityException, UnsupportedTypeException {
      int parameterCount = cachedMethod.getParameterCount();
      int minArity = parameterCount - 1;
      if (args.length < minArity) {
         errorBranch.enter();
         throw ArityException.create(minArity, -1, args.length);
      } else {
         Class<?>[] types = cachedMethod.getParameterTypes();
         Type[] genericTypes = cachedMethod.getGenericParameterTypes();
         Object[] convertedArguments = new Object[args.length];
         HostMethodScope scope = HostMethodScope.openStatic(cachedMethod);

         Object operand;
         try {
            try {
               for (int i = 0; i < minArity; i++) {
                  Object operandx = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                  convertedArguments[i] = toJavaNode.execute(hostContext, operandx, types[i], genericTypes[i], true);
               }

               if (!asVarArgs) {
                  operand = HostMethodScope.addToScopeStatic(scope, cachedMethod, minArity, args[minArity]);
                  convertedArguments[minArity] = toJavaNode.execute(hostContext, operand, types[minArity], genericTypes[minArity], true);
               } else {
                  for (int i = minArity; i < args.length; i++) {
                     Class<?> expectedType = types[minArity].getComponentType();
                     Type expectedGenericType = getGenericComponentType(genericTypes[minArity]);
                     Object operandx = HostMethodScope.addToScopeStatic(scope, cachedMethod, i, args[i]);
                     convertedArguments[i] = toJavaNode.execute(hostContext, operandx, expectedType, expectedGenericType, true);
                  }

                  convertedArguments = createVarArgsArray(cachedMethod, convertedArguments, parameterCount);
               }
            } catch (RuntimeException var26) {
               errorBranch.enter();
               if (cache.language.access.isEngineException(var26)) {
                  throw HostInteropErrors.unsupportedTypeException(args, cache.language.access.unboxEngineException(var26));
               }

               throw var26;
            }

            operand = doInvoke(cachedMethod, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
         } finally {
            HostMethodScope.closeStatic(scope, cachedMethod, seenDynamicScope);
         }

         return operand;
      }
   }

   @Specialization(replaces = {"doFixed", "doVarArgs"})
   static Object doSingleUncached(
      HostMethodDesc.SingleMethod method,
      Object obj,
      Object[] args,
      HostContext hostContext,
      @Cached.Shared("toHost") @Cached HostToTypeNode toJavaNode,
      @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
      @Cached.Shared("varArgsProfile") @Cached ConditionProfile isVarArgsProfile,
      @Cached.Shared("hostMethodProfile") @Cached HostExecuteNode.HostMethodProfileNode methodProfile,
      @Cached.Shared("errorBranch") @Cached BranchProfile errorBranch,
      @Cached.Shared("seenScope") @Cached BranchProfile seenScope,
      @Cached.Shared("cache") @Cached(value = "hostContext.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws ArityException, UnsupportedTypeException {
      int parameterCount = method.getParameterCount();
      int minArity;
      int maxArity;
      boolean arityError;
      if (isVarArgsProfile.profile(method.isVarArgs())) {
         minArity = parameterCount - 1;
         maxArity = -1;
         arityError = args.length < minArity;
      } else {
         minArity = parameterCount;
         maxArity = method.getParameterCount();
         arityError = args.length != parameterCount;
      }

      if (arityError) {
         errorBranch.enter();
         throw ArityException.create(minArity, maxArity, args.length);
      } else {
         HostMethodScope scope = HostMethodScope.openDynamic(method, args.length, seenScope);

         Object e;
         try {
            Object[] convertedArguments;
            try {
               convertedArguments = prepareArgumentsUncached(method, args, hostContext, toJavaNode, scope, isVarArgsProfile);
            } catch (RuntimeException var21) {
               errorBranch.enter();
               if (cache.language.access.isEngineException(var21)) {
                  throw HostInteropErrors.unsupportedTypeException(args, cache.language.access.unboxEngineException(var21));
               }

               throw var21;
            }

            e = doInvoke(methodProfile.execute(method), obj, convertedArguments, cache, hostContext, toGuest);
         } finally {
            HostMethodScope.closeDynamic(scope, method);
         }

         return e;
      }
   }

   @ExplodeLoop
   @Specialization(guards = {"method == cachedMethod", "checkArgTypes(args, cachedArgTypes, interop, hostContext, asVarArgs)"}, limit = "LIMIT")
   final Object doOverloadedCached(
      HostMethodDesc.OverloadedMethod method,
      Object obj,
      Object[] args,
      HostContext hostContext,
      @Cached("method") HostMethodDesc.OverloadedMethod cachedMethod,
      @Cached HostToTypeNode toJavaNode,
      @Cached HostContext.ToGuestValueNode toGuest,
      @CachedLibrary(limit = "LIMIT") InteropLibrary interop,
      @Cached("createArgTypesArray(args)") HostExecuteNode.TypeCheckNode[] cachedArgTypes,
      @Cached("selectOverload(method, args, hostContext, cachedArgTypes)") HostMethodDesc.SingleMethod overload,
      @Cached("asVarArgs(args, overload, hostContext)") boolean asVarArgs,
      @Cached("createClassProfile()") ValueProfile receiverProfile,
      @Cached BranchProfile errorBranch,
      @Cached BranchProfile seenVariableScope,
      @Cached(value = "hostContext.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws ArityException, UnsupportedTypeException {
      assert overload == this.selectOverload(method, args, hostContext);

      Class<?>[] types = overload.getParameterTypes();
      Type[] genericTypes = overload.getGenericParameterTypes();
      Object[] convertedArguments = new Object[cachedArgTypes.length];
      HostMethodScope scope = HostMethodScope.openStatic(overload);

      Object var31;
      try {
         try {
            if (!asVarArgs) {
               for (int i = 0; i < cachedArgTypes.length; i++) {
                  Object operand = HostMethodScope.addToScopeStatic(scope, overload, i, args[i]);
                  convertedArguments[i] = toJavaNode.execute(hostContext, operand, types[i], genericTypes[i], true);
               }
            } else {
               assert overload.isVarArgs();

               int parameterCount = overload.getParameterCount();

               for (int i = 0; i < cachedArgTypes.length; i++) {
                  Class<?> expectedType = i < parameterCount - 1 ? types[i] : types[parameterCount - 1].getComponentType();
                  Type expectedGenericType = i < parameterCount - 1 ? genericTypes[i] : getGenericComponentType(genericTypes[parameterCount - 1]);
                  Object operand = HostMethodScope.addToScopeStatic(scope, overload, i, args[i]);
                  convertedArguments[i] = toJavaNode.execute(hostContext, operand, expectedType, expectedGenericType, true);
               }

               convertedArguments = createVarArgsArray(overload, convertedArguments, parameterCount);
            }
         } catch (RuntimeException var28) {
            errorBranch.enter();
            if (cache.language.access.isEngineException(var28)) {
               throw HostInteropErrors.unsupportedTypeException(args, cache.language.access.unboxEngineException(var28));
            }

            throw var28;
         }

         var31 = doInvoke(overload, receiverProfile.profile(obj), convertedArguments, cache, hostContext, toGuest);
      } finally {
         HostMethodScope.closeStatic(scope, overload, seenVariableScope);
      }

      return var31;
   }

   @Specialization(replaces = "doOverloadedCached")
   final Object doOverloadedUncached(
      HostMethodDesc.OverloadedMethod method,
      Object obj,
      Object[] args,
      HostContext hostContext,
      @Cached.Shared("toHost") @Cached HostToTypeNode toJavaNode,
      @Cached.Shared("toGuest") @Cached HostContext.ToGuestValueNode toGuest,
      @Cached.Shared("varArgsProfile") @Cached ConditionProfile isVarArgsProfile,
      @Cached.Shared("hostMethodProfile") @Cached HostExecuteNode.HostMethodProfileNode methodProfile,
      @Cached.Shared("errorBranch") @Cached BranchProfile errorBranch,
      @Cached.Shared("seenScope") @Cached BranchProfile seenScope,
      @Cached.Shared("cache") @Cached(value = "hostContext.getGuestToHostCache()", allowUncached = true) GuestToHostCodeCache cache
   ) throws ArityException, UnsupportedTypeException {
      HostMethodDesc.SingleMethod overload = this.selectOverload(method, args, hostContext);
      HostMethodScope scope = HostMethodScope.openDynamic(overload, args.length, seenScope);

      Object e;
      try {
         Object[] convertedArguments;
         try {
            convertedArguments = prepareArgumentsUncached(overload, args, hostContext, toJavaNode, scope, isVarArgsProfile);
         } catch (RuntimeException var19) {
            errorBranch.enter();
            if (cache.language.access.isEngineException(var19)) {
               throw HostInteropErrors.unsupportedTypeException(args, cache.language.access.unboxEngineException(var19));
            }

            throw var19;
         }

         e = doInvoke(methodProfile.execute(overload), obj, convertedArguments, cache, hostContext, toGuest);
      } finally {
         HostMethodScope.closeDynamic(scope, overload);
      }

      return e;
   }

   private static Object[] prepareArgumentsUncached(
      HostMethodDesc.SingleMethod method,
      Object[] args,
      HostContext context,
      HostToTypeNode toJavaNode,
      HostMethodScope scope,
      ConditionProfile isVarArgsProfile
   ) {
      Class<?>[] types = method.getParameterTypes();
      Type[] genericTypes = method.getGenericParameterTypes();
      Object[] convertedArguments = new Object[args.length];
      if (isVarArgsProfile.profile(method.isVarArgs()) && asVarArgs(args, method, context)) {
         int parameterCount = method.getParameterCount();

         for (int i = 0; i < args.length; i++) {
            Class<?> expectedType = i < parameterCount - 1 ? types[i] : types[parameterCount - 1].getComponentType();
            Type expectedGenericType = i < parameterCount - 1 ? genericTypes[i] : getGenericComponentType(genericTypes[parameterCount - 1]);
            Object operand = HostMethodScope.addToScopeDynamic(scope, args[i]);
            convertedArguments[i] = toJavaNode.execute(context, operand, expectedType, expectedGenericType, true);
         }

         convertedArguments = createVarArgsArray(method, convertedArguments, parameterCount);
      } else {
         for (int i = 0; i < args.length; i++) {
            Object operand = HostMethodScope.addToScopeDynamic(scope, args[i]);
            convertedArguments[i] = toJavaNode.execute(context, operand, types[i], genericTypes[i], true);
         }
      }

      return convertedArguments;
   }

   static HostExecuteNode.TypeCheckNode[] createArgTypesArray(Object[] args) {
      HostExecuteNode.TypeCheckNode[] nodes = new HostExecuteNode.TypeCheckNode[args.length];
      Arrays.fill(nodes, HostExecuteNode.NullCheckNode.INSTANCE);
      return nodes;
   }

   private void fillArgTypesArray(
      Object[] args,
      HostExecuteNode.TypeCheckNode[] cachedArgTypes,
      HostMethodDesc.SingleMethod selected,
      boolean varArgs,
      List<HostMethodDesc.SingleMethod> applicable,
      int priority,
      HostContext context
   ) {
      if (cachedArgTypes != null) {
         HostClassCache cache = context.getHostClassCache();
         boolean multiple = applicable.size() > 1;

         for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class<?> targetType = getParameterType(selected.getParameterTypes(), i, varArgs);
            Set<HostTargetMapping> otherPossibleMappings = null;
            if (multiple) {
               for (HostMethodDesc.SingleMethod other : applicable) {
                  if (other != selected && other.isVarArgs() == varArgs) {
                     Class<?> paramType = getParameterType(other.getParameterTypes(), i, varArgs);
                     if (paramType != targetType
                        && !HostToTypeNode.canConvert(
                           arg, paramType, paramType, null, context, priority, InteropLibrary.getFactory().getUncached(), HostTargetMappingNode.getUncached()
                        )) {
                        HostTargetMapping[] otherMappings = cache.getMappings(paramType);
                        if (otherPossibleMappings == null) {
                           otherPossibleMappings = new LinkedHashSet<>();
                        }

                        for (HostTargetMapping mapping : otherMappings) {
                           otherPossibleMappings.add(mapping);
                        }
                     }
                  }
               }
            }

            HostExecuteNode.TypeCheckNode argType;
            if (arg == null) {
               argType = HostExecuteNode.NullCheckNode.INSTANCE;
            } else if (multiple && HostToTypeNode.isPrimitiveTarget(targetType)) {
               argType = createPrimitiveTargetCheck(applicable, selected, arg, targetType, i, priority, varArgs);
            } else if (arg instanceof HostObject) {
               argType = new HostExecuteNode.JavaObjectType(((HostObject)arg).getObjectClass());
            } else {
               argType = new HostExecuteNode.DirectTypeCheck(arg.getClass());
            }

            HostTargetMapping[] mappings = cache.getMappings(targetType);
            if (mappings.length > 0 || otherPossibleMappings != null) {
               HostTargetMapping[] otherMappings = otherPossibleMappings != null
                  ? otherPossibleMappings.toArray(HostClassCache.EMPTY_MAPPINGS)
                  : HostClassCache.EMPTY_MAPPINGS;
               argType = new HostExecuteNode.TargetMappingType(argType, mappings, otherMappings, priority);
            }

            cachedArgTypes[i] = this.insert(argType);
         }

         assert checkArgTypes(args, cachedArgTypes, InteropLibrary.getFactory().getUncached(), context, false) : Arrays.toString((Object[])cachedArgTypes);
      }
   }

   private static HostExecuteNode.TypeCheckNode createPrimitiveTargetCheck(
      List<HostMethodDesc.SingleMethod> applicable,
      HostMethodDesc.SingleMethod selected,
      Object arg,
      Class<?> targetType,
      int parameterIndex,
      int priority,
      boolean varArgs
   ) {
      Collection<Class<?>> otherPossibleTypes = new ArrayList<>();

      for (HostMethodDesc.SingleMethod other : applicable) {
         if (other != selected && other.isVarArgs() == varArgs) {
            Class<?> paramType = getParameterType(other.getParameterTypes(), parameterIndex, varArgs);
            if (paramType != targetType
               && !otherPossibleTypes.contains(paramType)
               && (HostToTypeNode.isPrimitiveTarget(paramType) || HostToTypeNode.isPrimitiveTarget(targetType))
               && isAssignableFrom(targetType, paramType)
               && !isSubtypeOf(arg, paramType)) {
               otherPossibleTypes.add(paramType);
            }
         }
      }

      return new HostExecuteNode.PrimitiveType(targetType, otherPossibleTypes.toArray(EMPTY_CLASS_ARRAY), priority);
   }

   @ExplodeLoop
   static boolean checkArgTypes(Object[] args, HostExecuteNode.TypeCheckNode[] argTypes, InteropLibrary interop, HostContext context, boolean dummy) {
      if (args.length != argTypes.length) {
         return false;
      } else {
         for (int i = 0; i < argTypes.length; i++) {
            HostExecuteNode.TypeCheckNode argType = argTypes[i];
            if (!argType.execute(args[i], interop, context)) {
               return false;
            }
         }

         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static boolean asVarArgs(Object[] args, HostMethodDesc.SingleMethod overload, HostContext hostContext) {
      if (overload.isVarArgs()) {
         int parameterCount = overload.getParameterCount();
         if (args.length == parameterCount) {
            Class<?> varArgParamType = overload.getParameterTypes()[parameterCount - 1];
            return !HostToTypeNode.canConvert(
               args[parameterCount - 1],
               varArgParamType,
               overload.getGenericParameterTypes()[parameterCount - 1],
               null,
               hostContext,
               3,
               InteropLibrary.getFactory().getUncached(),
               HostTargetMappingNode.getUncached()
            );
         } else {
            assert args.length != parameterCount;

            return true;
         }
      } else {
         return false;
      }
   }

   static Class<?> primitiveTypeToBoxedType(Class<?> primitiveType) {
      assert primitiveType.isPrimitive();

      if (primitiveType == boolean.class) {
         return Boolean.class;
      } else if (primitiveType == byte.class) {
         return Byte.class;
      } else if (primitiveType == short.class) {
         return Short.class;
      } else if (primitiveType == char.class) {
         return Character.class;
      } else if (primitiveType == int.class) {
         return Integer.class;
      } else if (primitiveType == long.class) {
         return Long.class;
      } else if (primitiveType == float.class) {
         return Float.class;
      } else if (primitiveType == double.class) {
         return Double.class;
      } else {
         throw new IllegalArgumentException();
      }
   }

   static Class<?> boxedTypeToPrimitiveType(Class<?> primitiveType) {
      if (primitiveType == Boolean.class) {
         return boolean.class;
      } else if (primitiveType == Byte.class) {
         return byte.class;
      } else if (primitiveType == Short.class) {
         return short.class;
      } else if (primitiveType == Character.class) {
         return char.class;
      } else if (primitiveType == Integer.class) {
         return int.class;
      } else if (primitiveType == Long.class) {
         return long.class;
      } else if (primitiveType == Float.class) {
         return float.class;
      } else {
         return primitiveType == Double.class ? double.class : null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   HostMethodDesc.SingleMethod selectOverload(HostMethodDesc.OverloadedMethod method, Object[] args, HostContext hostContext) throws ArityException, UnsupportedTypeException {
      return this.selectOverload(method, args, hostContext, null);
   }

   @CompilerDirectives.TruffleBoundary
   HostMethodDesc.SingleMethod selectOverload(
      HostMethodDesc.OverloadedMethod method, Object[] args, HostContext hostContext, HostExecuteNode.TypeCheckNode[] cachedArgTypes
   ) throws ArityException, UnsupportedTypeException {
      HostMethodDesc.SingleMethod[] overloads = method.getOverloads();
      List<HostMethodDesc.SingleMethod> applicableByArity = new ArrayList<>();
      int minOverallArity = Integer.MAX_VALUE;
      int maxOverallArity = 0;
      boolean anyVarArgs = false;

      assert overloads.length > 0;

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
      } else {
         for (int priority : HostToTypeNode.PRIORITIES) {
            HostMethodDesc.SingleMethod best = this.findBestCandidate(applicableByArity, args, hostContext, false, priority, cachedArgTypes);
            if (best != null) {
               return best;
            }

            if (anyVarArgs) {
               best = this.findBestCandidate(applicableByArity, args, hostContext, true, priority, cachedArgTypes);
               if (best != null) {
                  return best;
               }
            }
         }

         throw noApplicableOverloadsException(overloads, args);
      }
   }

   private HostMethodDesc.SingleMethod findBestCandidate(
      List<HostMethodDesc.SingleMethod> applicableByArity,
      Object[] args,
      HostContext hostContext,
      boolean varArgs,
      int priority,
      HostExecuteNode.TypeCheckNode[] cachedArgTypes
   ) throws UnsupportedTypeException {
      List<HostMethodDesc.SingleMethod> candidates = new ArrayList<>();
      if (!varArgs) {
         for (HostMethodDesc.SingleMethod candidate : applicableByArity) {
            int paramCount = candidate.getParameterCount();
            if (!candidate.isOnlyVisibleFromJniName() && (!candidate.isVarArgs() || paramCount == args.length)) {
               assert paramCount == args.length;

               Class<?>[] parameterTypes = candidate.getParameterTypes();
               Type[] genericParameterTypes = candidate.getGenericParameterTypes();
               boolean applicable = true;

               for (int i = 0; i < paramCount; i++) {
                  if (!HostToTypeNode.canConvert(
                     args[i],
                     parameterTypes[i],
                     genericParameterTypes[i],
                     null,
                     hostContext,
                     priority,
                     InteropLibrary.getFactory().getUncached(args[i]),
                     HostTargetMappingNode.getUncached()
                  )) {
                     applicable = false;
                     break;
                  }
               }

               if (applicable) {
                  candidates.add(candidate);
               }
            }
         }
      } else {
         for (HostMethodDesc.SingleMethod candidatex : applicableByArity) {
            if (candidatex.isVarArgs() && !candidatex.isOnlyVisibleFromJniName()) {
               int parameterCount = candidatex.getParameterCount();
               Class<?>[] parameterTypes = candidatex.getParameterTypes();
               Type[] genericParameterTypes = candidatex.getGenericParameterTypes();
               boolean applicable = true;

               for (int ix = 0; ix < parameterCount - 1; ix++) {
                  if (!HostToTypeNode.canConvert(
                     args[ix],
                     parameterTypes[ix],
                     genericParameterTypes[ix],
                     null,
                     hostContext,
                     priority,
                     InteropLibrary.getFactory().getUncached(args[ix]),
                     HostTargetMappingNode.getUncached()
                  )) {
                     applicable = false;
                     break;
                  }
               }

               if (applicable) {
                  Class<?> varArgsComponentType = parameterTypes[parameterCount - 1].getComponentType();
                  Type varArgsGenericComponentType = genericParameterTypes[parameterCount - 1];
                  if (varArgsGenericComponentType instanceof GenericArrayType) {
                     GenericArrayType arrayType = (GenericArrayType)varArgsGenericComponentType;
                     varArgsGenericComponentType = arrayType.getGenericComponentType();
                  } else {
                     varArgsGenericComponentType = varArgsComponentType;
                  }

                  for (int ixx = parameterCount - 1; ixx < args.length; ixx++) {
                     if (!HostToTypeNode.canConvert(
                        args[ixx],
                        varArgsComponentType,
                        varArgsGenericComponentType,
                        null,
                        hostContext,
                        priority,
                        InteropLibrary.getFactory().getUncached(args[ixx]),
                        HostTargetMappingNode.getUncached()
                     )) {
                        applicable = false;
                        break;
                     }
                  }

                  if (applicable) {
                     candidates.add(candidatex);
                  }
               }
            }
         }
      }

      if (!candidates.isEmpty()) {
         if (candidates.size() == 1) {
            HostMethodDesc.SingleMethod best = candidates.get(0);
            if (cachedArgTypes != null) {
               this.fillArgTypesArray(args, cachedArgTypes, best, varArgs, applicableByArity, priority, hostContext);
            }

            return best;
         } else {
            HostMethodDesc.SingleMethod best = findMostSpecificOverload(hostContext, candidates, args, varArgs, priority);
            if (best != null) {
               if (cachedArgTypes != null) {
                  this.fillArgTypesArray(args, cachedArgTypes, best, varArgs, applicableByArity, priority, hostContext);
               }

               return best;
            } else {
               throw ambiguousOverloadsException(candidates, args);
            }
         }
      } else {
         return null;
      }
   }

   private static HostMethodDesc.SingleMethod findMostSpecificOverload(
      HostContext context, List<HostMethodDesc.SingleMethod> candidates, Object[] args, boolean varArgs, int priority
   ) {
      assert candidates.size() >= 2;

      if (candidates.size() == 2) {
         int res = compareOverloads(context, candidates.get(0), candidates.get(1), args, varArgs, priority);
         return res == 0 ? null : (res < 0 ? candidates.get(0) : candidates.get(1));
      } else {
         Iterator<HostMethodDesc.SingleMethod> candIt = candidates.iterator();
         List<HostMethodDesc.SingleMethod> best = new LinkedList<>();
         best.add(candIt.next());

         while (candIt.hasNext()) {
            HostMethodDesc.SingleMethod cand = candIt.next();
            boolean add = false;
            Iterator<HostMethodDesc.SingleMethod> bestIt = best.iterator();

            while (bestIt.hasNext()) {
               int res = compareOverloads(context, cand, bestIt.next(), args, varArgs, priority);
               if (res == 0) {
                  add = true;
               } else if (res < 0) {
                  bestIt.remove();
                  add = true;
               } else {
                  assert res > 0;
               }
            }

            if (add) {
               best.add(cand);
            }
         }

         assert !best.isEmpty();

         return best.size() == 1 ? best.get(0) : null;
      }
   }

   private static int compareOverloads(
      HostContext context, HostMethodDesc.SingleMethod m1, HostMethodDesc.SingleMethod m2, Object[] args, boolean varArgs, int priority
   ) {
      int res = 0;

      assert !varArgs || m1.isVarArgs() && m2.isVarArgs();

      assert varArgs || m1.getParameterCount() == m2.getParameterCount() && args.length == m1.getParameterCount();

      for (int i = 0; i < args.length; i++) {
         Class<?> t1 = getParameterType(m1.getParameterTypes(), i, varArgs);
         Class<?> t2 = getParameterType(m2.getParameterTypes(), i, varArgs);
         if (t1 != t2) {
            int r = compareByPriority(context, t1, t2, args[i], priority);
            if (r == 0) {
               r = compareAssignable(t1, t2);
               if (r == 0) {
                  continue;
               }
            }

            if (res == 0) {
               res = r;
            } else if (res != r) {
               res = 0;
               break;
            }
         }
      }

      return res;
   }

   private static Class<?> getParameterType(Class<?>[] parameterTypes, int i, boolean varArgs) {
      return varArgs && i >= parameterTypes.length - 1 ? parameterTypes[parameterTypes.length - 1].getComponentType() : parameterTypes[i];
   }

   private static int compareByPriority(HostContext context, Class<?> t1, Class<?> t2, Object arg, int priority) {
      if (priority <= 1) {
         return 0;
      } else {
         InteropLibrary argInterop = InteropLibrary.getFactory().getUncached(arg);
         HostTargetMappingNode mapping = HostTargetMappingNode.getUncached();

         for (int p : HostToTypeNode.PRIORITIES) {
            if (p > priority) {
               break;
            }

            boolean p1 = HostToTypeNode.canConvert(arg, t1, t1, null, context, p, argInterop, mapping);
            boolean p2 = HostToTypeNode.canConvert(arg, t2, t2, null, context, p, argInterop, mapping);
            if (p1 != p2) {
               return p1 ? -1 : 1;
            }
         }

         return 0;
      }
   }

   private static int compareAssignable(Class<?> t1, Class<?> t2) {
      if (isAssignableFrom(t1, t2)) {
         return 1;
      } else {
         return isAssignableFrom(t2, t1) ? -1 : 0;
      }
   }

   private static boolean isAssignableFrom(Class<?> toType, Class<?> fromType) {
      if (toType.isAssignableFrom(fromType)) {
         return true;
      } else {
         boolean fromIsPrimitive = fromType.isPrimitive();
         boolean toIsPrimitive = toType.isPrimitive();
         Class<?> fromAsPrimitive = fromIsPrimitive ? fromType : boxedTypeToPrimitiveType(fromType);
         Class<?> toAsPrimitive = toIsPrimitive ? toType : boxedTypeToPrimitiveType(toType);
         if (toAsPrimitive != null && fromAsPrimitive != null) {
            if (toAsPrimitive == fromAsPrimitive) {
               assert fromIsPrimitive != toIsPrimitive;

               return fromIsPrimitive;
            }

            if (isWideningPrimitiveConversion(toAsPrimitive, fromAsPrimitive)) {
               return true;
            }
         } else {
            if (fromAsPrimitive == char.class && (toType == String.class || toType == CharSequence.class)) {
               return true;
            }

            if (toAsPrimitive == null && fromAsPrimitive != null && toType.isAssignableFrom(primitiveTypeToBoxedType(fromAsPrimitive))) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isSubtypeOf(Object argument, Class<?> parameterType) {
      Object value = argument;
      if (argument instanceof HostObject) {
         value = ((HostObject)argument).obj;
      }

      if (!parameterType.isPrimitive()) {
         return value == null || parameterType.isInstance(value) && !(value instanceof TruffleObject);
      } else {
         if (value != null) {
            Class<?> boxedToPrimitive = boxedTypeToPrimitiveType(value.getClass());
            if (boxedToPrimitive != null) {
               return boxedToPrimitive == parameterType || isWideningPrimitiveConversion(parameterType, boxedToPrimitive);
            }
         }

         return false;
      }
   }

   private static boolean isWideningPrimitiveConversion(Class<?> toType, Class<?> fromType) {
      assert toType.isPrimitive();

      if (fromType == byte.class) {
         return toType == short.class || toType == int.class || toType == long.class || toType == float.class || toType == double.class;
      } else if (fromType == short.class) {
         return toType == int.class || toType == long.class || toType == float.class || toType == double.class;
      } else if (fromType == char.class) {
         return toType == int.class || toType == long.class || toType == float.class || toType == double.class;
      } else if (fromType == int.class) {
         return toType == long.class || toType == float.class || toType == double.class;
      } else if (fromType != long.class) {
         return fromType == float.class ? toType == double.class : false;
      } else {
         return toType == float.class || toType == double.class;
      }
   }

   private static RuntimeException ambiguousOverloadsException(List<HostMethodDesc.SingleMethod> candidates, Object[] args) throws UnsupportedTypeException {
      String message = String.format(
         "Multiple applicable overloads found for method name %s (candidates: %s, arguments: %s)",
         candidates.get(0).getName(),
         candidates,
         arrayToStringWithTypes(args)
      );
      throw UnsupportedTypeException.create(args, message);
   }

   private static RuntimeException noApplicableOverloadsException(HostMethodDesc.SingleMethod[] overloads, Object[] args) throws UnsupportedTypeException {
      String message = String.format(
         "no applicable overload found (overloads: %s, arguments: %s)", Arrays.toString((Object[])overloads), arrayToStringWithTypes(args)
      );
      throw UnsupportedTypeException.create(args, message);
   }

   private static Type getGenericComponentType(Type type) {
      return (Type)(type instanceof GenericArrayType ? ((GenericArrayType)type).getGenericComponentType() : ((Class)type).getComponentType());
   }

   @CompilerDirectives.TruffleBoundary
   private static Object[] createVarArgsArray(HostMethodDesc.SingleMethod method, Object[] args, int parameterCount) {
      Class<?>[] parameterTypes = method.getParameterTypes();
      Object[] arguments = new Object[parameterCount];

      for (int i = 0; i < parameterCount - 1; i++) {
         arguments[i] = args[i];
      }

      Class<?> varArgsType = parameterTypes[parameterCount - 1].getComponentType();
      Object varArgs = Array.newInstance(varArgsType, args.length - parameterCount + 1);
      int i = parameterCount - 1;

      for (int j = 0; i < args.length; j++) {
         Array.set(varArgs, j, args[i]);
         i++;
      }

      arguments[parameterCount - 1] = varArgs;
      return arguments;
   }

   private static Object doInvoke(
      HostMethodDesc.SingleMethod method,
      Object obj,
      Object[] arguments,
      GuestToHostCodeCache cache,
      HostContext hostContext,
      HostContext.ToGuestValueNode toGuest
   ) {
      assert cache == hostContext.getGuestToHostCache();

      assert arguments.length == method.getParameterCount();

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

   static final class DirectTypeCheck extends HostExecuteNode.TypeCheckNode {
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

   @GenerateUncached
   abstract static class HostMethodProfileNode extends Node {
      public abstract HostMethodDesc.SingleMethod execute(HostMethodDesc.SingleMethod method);

      @Specialization
      static HostMethodDesc.SingleMethod mono(HostMethodDesc.SingleMethod.MHBase method) {
         return method;
      }

      @Specialization
      static HostMethodDesc.SingleMethod mono(HostMethodDesc.SingleMethod.ReflectBase method) {
         return method;
      }

      @Specialization(replaces = "mono")
      static HostMethodDesc.SingleMethod poly(HostMethodDesc.SingleMethod method) {
         return method;
      }
   }

   static final class JavaObjectType extends HostExecuteNode.TypeCheckNode {
      final Class<?> clazz;

      JavaObjectType(Class<?> clazz) {
         this.clazz = clazz;
      }

      @Override
      boolean execute(Object arg, InteropLibrary interop, HostContext context) {
         return arg instanceof HostObject && ((HostObject)arg).getObjectClass() == this.clazz;
      }

      @Override
      public int hashCode() {
         return this.clazz == null ? 0 : this.clazz.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof HostExecuteNode.JavaObjectType)) {
            return false;
         } else {
            HostExecuteNode.JavaObjectType other = (HostExecuteNode.JavaObjectType)obj;
            return Objects.equals(this.clazz, other.clazz);
         }
      }

      @Override
      public String toString() {
         return "JavaObject[" + this.clazz.getTypeName() + "]";
      }
   }

   static final class NullCheckNode extends HostExecuteNode.TypeCheckNode {
      static final HostExecuteNode.NullCheckNode INSTANCE = new HostExecuteNode.NullCheckNode();

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

   static final class PrimitiveType extends HostExecuteNode.TypeCheckNode {
      final Class<?> targetType;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final Class<?>[] otherTypes;
      final int priority;

      PrimitiveType(Class<?> targetType, Class<?>[] otherTypes, int priority) {
         this.targetType = targetType;
         this.otherTypes = otherTypes;
         this.priority = priority;
      }

      @Override
      public int hashCode() {
         return this.targetType == null ? 0 : this.targetType.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof HostExecuteNode.PrimitiveType)) {
            return false;
         } else {
            HostExecuteNode.PrimitiveType other = (HostExecuteNode.PrimitiveType)obj;
            return Objects.equals(this.targetType, other.targetType) && Arrays.equals((Object[])this.otherTypes, (Object[])other.otherTypes);
         }
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

      @ExplodeLoop
      @Override
      public boolean execute(Object value, InteropLibrary interop, HostContext context) {
         for (Class<?> otherType : this.otherTypes) {
            if (HostToTypeNode.canConvert(value, otherType, otherType, null, context, this.priority, interop, null)) {
               return false;
            }
         }

         return HostToTypeNode.canConvert(value, this.targetType, this.targetType, null, context, this.priority, interop, null);
      }
   }

   static final class TargetMappingType extends HostExecuteNode.TypeCheckNode {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final HostTargetMapping[] mappings;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final HostTargetMapping[] otherMappings;
      @Node.Child
      HostExecuteNode.TypeCheckNode fallback;
      @Node.Children
      final HostTargetMappingNode.SingleMappingNode[] mappingNodes;
      @Node.Children
      final HostTargetMappingNode.SingleMappingNode[] otherMappingNodes;
      final int priority;

      TargetMappingType(HostExecuteNode.TypeCheckNode fallback, HostTargetMapping[] mappings, HostTargetMapping[] otherMappings, int priority) {
         this.fallback = fallback;
         this.priority = priority;
         this.mappings = mappings;
         this.otherMappings = otherMappings;
         this.mappingNodes = new HostTargetMappingNode.SingleMappingNode[mappings.length];

         for (int i = 0; i < mappings.length; i++) {
            this.mappingNodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
         }

         this.otherMappingNodes = new HostTargetMappingNode.SingleMappingNode[otherMappings.length];

         for (int i = 0; i < otherMappings.length; i++) {
            this.otherMappingNodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
         }
      }

      @ExplodeLoop
      @Override
      boolean execute(Object test, InteropLibrary interop, HostContext context) {
         for (int i = 0; i < this.otherMappingNodes.length; i++) {
            HostTargetMapping mapping = this.otherMappings[i];
            if (mapping.hostPriority > this.priority) {
               break;
            }

            Object result = this.otherMappingNodes[i].execute(test, mapping, context, interop, true);
            if (result == Boolean.TRUE) {
               return false;
            }
         }

         for (int i = 0; i < this.mappingNodes.length; i++) {
            HostTargetMapping mappingx = this.mappings[i];
            if (mappingx.hostPriority > this.priority) {
               break;
            }

            Object result = this.mappingNodes[i].execute(test, mappingx, context, interop, true);
            if (result == Boolean.TRUE) {
               return true;
            }
         }

         return this.fallback.execute(test, interop, context);
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof HostExecuteNode.TargetMappingType)) {
            return false;
         } else {
            HostExecuteNode.TargetMappingType other = (HostExecuteNode.TargetMappingType)obj;
            return Arrays.equals((Object[])this.mappings, (Object[])other.mappings);
         }
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode((Object[])this.mappings);
      }
   }

   abstract static class TypeCheckNode extends Node {
      abstract boolean execute(Object test, InteropLibrary interop, HostContext context);
   }
}
