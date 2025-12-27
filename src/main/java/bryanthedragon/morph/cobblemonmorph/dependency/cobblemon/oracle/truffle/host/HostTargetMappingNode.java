package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.util.function.Function;
import java.util.function.Predicate;

@GenerateUncached
abstract class HostTargetMappingNode extends Node {
   public static final Object NO_RESULT = new Object();

   abstract Object execute(
      Object value, Class<?> targetType, HostContext hostContext, InteropLibrary interop, boolean checkOnly, int startPriority, int endPriority
   );

   @Specialization(guards = "targetType != null")
   @ExplodeLoop
   protected Object doCached(
      Object operand,
      Class<?> targetType,
      HostContext context,
      InteropLibrary interop,
      boolean checkOnly,
      int startPriority,
      int endPriority,
      @Cached(value = "getMappings(context, targetType)", dimensions = 1) HostTargetMapping[] mappings,
      @Cached("createMappingNodes(mappings)") HostTargetMappingNode.SingleMappingNode[] mappingNodes
   ) {
      assert startPriority <= endPriority;

      Object result = NO_RESULT;
      if (mappingNodes != null) {
         for (int i = 0; i < mappingNodes.length; i++) {
            HostTargetMapping mapping = mappings[i];
            if (mapping.hostPriority >= startPriority) {
               if (mapping.hostPriority > endPriority) {
                  break;
               }

               result = mappingNodes[i].execute(operand, mappings[i], context, interop, checkOnly);
               if (result != NO_RESULT) {
                  break;
               }
            }
         }
      }

      return result;
   }

   @Specialization(replaces = "doCached")
   @CompilerDirectives.TruffleBoundary
   protected Object doUncached(
      Object operand, Class<?> targetType, HostContext hostContext, InteropLibrary interop, boolean checkOnly, int startPriority, int endPriority
   ) {
      assert startPriority <= endPriority;

      Object result = NO_RESULT;
      HostTargetMapping[] mappings = getMappings(hostContext, targetType);
      if (mappings != null) {
         HostTargetMappingNode.SingleMappingNode uncachedNode = HostTargetMappingNodeGen.SingleMappingNodeGen.getUncached();

         for (int i = 0; i < mappings.length; i++) {
            HostTargetMapping mapping = mappings[i];
            if (mapping.hostPriority >= startPriority) {
               if (mapping.hostPriority > endPriority) {
                  break;
               }

               result = uncachedNode.execute(operand, mappings[i], hostContext, interop, checkOnly);
               if (result != NO_RESULT) {
                  break;
               }
            }
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   static HostTargetMapping[] getMappings(HostContext hostContext, Class<?> targetType) {
      return hostContext == null ? HostClassCache.EMPTY_MAPPINGS : hostContext.getHostClassCache().getMappings(targetType);
   }

   @CompilerDirectives.TruffleBoundary
   static HostTargetMappingNode.SingleMappingNode[] createMappingNodes(HostTargetMapping[] mappings) {
      if (mappings == null) {
         return null;
      } else {
         HostTargetMappingNode.SingleMappingNode[] nodes = new HostTargetMappingNode.SingleMappingNode[mappings.length];

         for (int i = 0; i < nodes.length; i++) {
            nodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
         }

         return nodes;
      }
   }

   static HostTargetMappingNode create() {
      return HostTargetMappingNodeGen.create();
   }

   static HostTargetMappingNode getUncached() {
      return HostTargetMappingNodeGen.getUncached();
   }

   @GenerateUncached
   abstract static class SingleMappingNode extends Node {
      abstract Object execute(Object receiver, HostTargetMapping targetMapping, HostContext context, InteropLibrary interop, boolean checkOnly);

      @Specialization
      protected Object doDefault(
         Object receiver,
         HostTargetMapping cachedMapping,
         HostContext context,
         InteropLibrary interop,
         boolean checkOnly,
         @Cached ConditionProfile acceptsProfile,
         @Cached(value = "allowsImplementation(context, cachedMapping.sourceType)", allowUncached = true) boolean allowsImplementation,
         @Cached HostToTypeNode toHostRecursive
      ) {
         CompilerAsserts.partialEvaluationConstant(checkOnly);
         Object convertedValue = HostTargetMappingNode.NO_RESULT;
         if (!acceptsProfile.profile(
            HostToTypeNode.canConvert(receiver, cachedMapping.sourceType, cachedMapping.sourceType, allowsImplementation, context, 8, interop, null)
         )) {
            return HostTargetMappingNode.NO_RESULT;
         } else {
            if (!checkOnly || cachedMapping.accepts != null) {
               convertedValue = toHostRecursive.execute(context, receiver, cachedMapping.sourceType, cachedMapping.sourceType, false);
            }

            if (cachedMapping.accepts != null && !checkPredicate(context, convertedValue, cachedMapping.accepts)) {
               return HostTargetMappingNode.NO_RESULT;
            } else {
               return checkOnly ? Boolean.TRUE : convert(context, cachedMapping.converter, convertedValue);
            }
         }
      }

      static boolean allowsImplementation(HostContext context, Class<?> type) {
         return HostToTypeNode.allowsImplementation(context, type);
      }

      @CompilerDirectives.TruffleBoundary
      private static Object convert(HostContext context, Function<Object, Object> converter, Object value) {
         try {
            return converter.apply(value);
         } catch (ClassCastException var4) {
            throw HostEngineException.classCast(context.access, var4.getMessage());
         } catch (Throwable var5) {
            throw context.hostToGuestException(var5);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static boolean checkPredicate(HostContext context, Object convertedValue, Predicate<Object> predicate) {
         try {
            return predicate.test(convertedValue);
         } catch (Throwable var4) {
            throw context.hostToGuestException(var4);
         }
      }
   }
}
