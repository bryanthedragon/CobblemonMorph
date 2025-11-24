
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
import com.oracle.truffle.host.HostClassCache;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostTargetMapping;
import com.oracle.truffle.host.HostTargetMappingNodeGen;
import com.oracle.truffle.host.HostToTypeNode;
import java.util.function.Function;
import java.util.function.Predicate;

@GenerateUncached
abstract class HostTargetMappingNode
extends Node {
    public static final Object NO_RESULT = new Object();

    HostTargetMappingNode() {
    }

    abstract Object execute(Object var1, Class<?> var2, HostContext var3, InteropLibrary var4, boolean var5, int var6, int var7);

    @Specialization(guards={"targetType != null"})
    @ExplodeLoop
    protected Object doCached(Object operand, Class<?> targetType, HostContext context, InteropLibrary interop, boolean checkOnly, int startPriority, int endPriority, @Cached(value="getMappings(context, targetType)", dimensions=1) HostTargetMapping[] mappings, @Cached(value="createMappingNodes(mappings)") SingleMappingNode[] mappingNodes) {
        assert (startPriority <= endPriority);
        Object result = NO_RESULT;
        if (mappingNodes != null) {
            for (int i = 0; i < mappingNodes.length; ++i) {
                HostTargetMapping mapping = mappings[i];
                if (mapping.hostPriority >= startPriority && (mapping.hostPriority > endPriority || (result = mappingNodes[i].execute(operand, mappings[i], context, interop, checkOnly)) != NO_RESULT)) break;
            }
        }
        return result;
    }

    @Specialization(replaces={"doCached"})
    @CompilerDirectives.TruffleBoundary
    protected Object doUncached(Object operand, Class<?> targetType, HostContext hostContext, InteropLibrary interop, boolean checkOnly, int startPriority, int endPriority) {
        assert (startPriority <= endPriority);
        Object result = NO_RESULT;
        HostTargetMapping[] mappings = HostTargetMappingNode.getMappings(hostContext, targetType);
        if (mappings != null) {
            SingleMappingNode uncachedNode = HostTargetMappingNodeGen.SingleMappingNodeGen.getUncached();
            for (int i = 0; i < mappings.length; ++i) {
                HostTargetMapping mapping = mappings[i];
                if (mapping.hostPriority >= startPriority && (mapping.hostPriority > endPriority || (result = uncachedNode.execute(operand, mappings[i], hostContext, interop, checkOnly)) != NO_RESULT)) break;
            }
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    static HostTargetMapping[] getMappings(HostContext hostContext, Class<?> targetType) {
        if (hostContext == null) {
            return HostClassCache.EMPTY_MAPPINGS;
        }
        return hostContext.getHostClassCache().getMappings(targetType);
    }

    @CompilerDirectives.TruffleBoundary
    static SingleMappingNode[] createMappingNodes(HostTargetMapping[] mappings) {
        if (mappings == null) {
            return null;
        }
        SingleMappingNode[] nodes = new SingleMappingNode[mappings.length];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = HostTargetMappingNodeGen.SingleMappingNodeGen.create();
        }
        return nodes;
    }

    static HostTargetMappingNode create() {
        return HostTargetMappingNodeGen.create();
    }

    static HostTargetMappingNode getUncached() {
        return HostTargetMappingNodeGen.getUncached();
    }

    @GenerateUncached
    static abstract class SingleMappingNode
    extends Node {
        SingleMappingNode() {
        }

        abstract Object execute(Object var1, HostTargetMapping var2, HostContext var3, InteropLibrary var4, boolean var5);

        @Specialization
        protected Object doDefault(Object receiver, HostTargetMapping cachedMapping, HostContext context, InteropLibrary interop, boolean checkOnly, @Cached ConditionProfile acceptsProfile, @Cached(value="allowsImplementation(context, cachedMapping.sourceType)", allowUncached=true) boolean allowsImplementation, @Cached HostToTypeNode toHostRecursive) {
            CompilerAsserts.partialEvaluationConstant(checkOnly);
            Object convertedValue = NO_RESULT;
            if (acceptsProfile.profile(HostToTypeNode.canConvert(receiver, cachedMapping.sourceType, cachedMapping.sourceType, allowsImplementation, context, 8, interop, null))) {
                if (!checkOnly || cachedMapping.accepts != null) {
                    convertedValue = toHostRecursive.execute(context, receiver, cachedMapping.sourceType, cachedMapping.sourceType, false);
                }
            } else {
                return NO_RESULT;
            }
            if (cachedMapping.accepts != null && !SingleMappingNode.checkPredicate(context, convertedValue, cachedMapping.accepts)) {
                return NO_RESULT;
            }
            if (checkOnly) {
                return Boolean.TRUE;
            }
            return SingleMappingNode.convert(context, cachedMapping.converter, convertedValue);
        }

        static boolean allowsImplementation(HostContext context, Class<?> type) {
            return HostToTypeNode.allowsImplementation(context, type);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object convert(HostContext context, Function<Object, Object> converter, Object value2) {
            try {
                return converter.apply(value2);
            }
            catch (ClassCastException t) {
                throw HostEngineException.classCast(context.access, t.getMessage());
            }
            catch (Throwable t) {
                throw context.hostToGuestException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static boolean checkPredicate(HostContext context, Object convertedValue, Predicate<Object> predicate) {
            try {
                return predicate.test(convertedValue);
            }
            catch (Throwable t) {
                throw context.hostToGuestException(t);
            }
        }
    }
}

