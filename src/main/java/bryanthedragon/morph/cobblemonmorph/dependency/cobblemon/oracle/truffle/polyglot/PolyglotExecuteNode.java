
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.polyglot.PolyglotInteropErrors;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

abstract class PolyglotExecuteNode
extends Node {
    private static final Object[] EMPTY = new Object[0];
    @Node.Child
    private PolyglotLanguageContext.ToGuestValuesNode toGuests = PolyglotLanguageContext.ToGuestValuesNode.create();

    PolyglotExecuteNode() {
    }

    public final Object execute(PolyglotLanguageContext languageContext, Object function, Object functionArgsObject) {
        return this.execute(languageContext, function, functionArgsObject, Object.class, (Type)((Object)Object.class), Object.class, null);
    }

    public final Object execute(PolyglotLanguageContext languageContext, Object function, Object functionArgsObject, Class<?> resultClass, Type resultType, Class<?> paramClass, Type paramType) {
        Object[] argsArray = paramType != null && paramClass.isArray() ? (functionArgsObject == null ? EMPTY : (paramClass.getComponentType().isPrimitive() && !(functionArgsObject instanceof Object[]) ? PolyglotExecuteNode.copyToObjectArray(paramClass.cast(functionArgsObject)) : (Object[])functionArgsObject)) : (paramType == null && functionArgsObject == null ? EMPTY : (paramType == null && functionArgsObject instanceof Object[] ? (Object[])functionArgsObject : new Object[]{functionArgsObject}));
        Object[] functionArgs = this.toGuests.apply(languageContext, argsArray);
        return this.executeImpl(languageContext, function, functionArgs, resultClass, resultType);
    }

    @CompilerDirectives.TruffleBoundary
    private static Object[] copyToObjectArray(Object functionArgs) {
        assert (functionArgs.getClass().isArray());
        int length = Array.getLength(functionArgs);
        Object[] copy = new Object[length];
        for (int i = 0; i < length; ++i) {
            copy[i] = Array.get(functionArgs, 0);
        }
        return copy;
    }

    protected abstract Object executeImpl(PolyglotLanguageContext var1, Object var2, Object[] var3, Class<?> var4, Type var5);

    @Specialization(limit="5")
    Object doCached(PolyglotLanguageContext languageContext, Object function, Object[] functionArgs, Class<?> resultClass, Type resultType, @CachedLibrary(value="function") InteropLibrary interop, @Cached PolyglotToHostNode toHost, @Cached ConditionProfile executableCondition, @Cached ConditionProfile instantiableCondition, @Cached BranchProfile unsupportedError, @Cached BranchProfile arityError, @Cached BranchProfile unsupportedArgumentError) {
        Object result;
        block8: {
            boolean executable = executableCondition.profile(interop.isExecutable(function));
            try {
                if (executable) {
                    result = interop.execute(function, functionArgs);
                    break block8;
                }
                if (instantiableCondition.profile(interop.isInstantiable(function))) {
                    result = interop.instantiate(function, functionArgs);
                    break block8;
                }
                throw PolyglotInteropErrors.executeUnsupported(languageContext, function);
            }
            catch (UnsupportedTypeException e) {
                unsupportedArgumentError.enter();
                if (executable) {
                    throw PolyglotInteropErrors.invalidExecuteArgumentType(languageContext, function, functionArgs);
                }
                throw PolyglotInteropErrors.invalidInstantiateArgumentType(languageContext, function, functionArgs);
            }
            catch (ArityException e) {
                arityError.enter();
                if (executable) {
                    throw PolyglotInteropErrors.invalidExecuteArity(languageContext, function, functionArgs, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
                }
                throw PolyglotInteropErrors.invalidInstantiateArity(languageContext, function, functionArgs, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
            }
            catch (UnsupportedMessageException e) {
                unsupportedError.enter();
                throw PolyglotInteropErrors.executeUnsupported(languageContext, function);
            }
        }
        return toHost.execute(languageContext, result, resultClass, resultType);
    }
}

