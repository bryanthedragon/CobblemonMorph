
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotExecuteNode;
import com.oracle.truffle.polyglot.PolyglotFunctionProxyHandlerFactory;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotObjectProxyHandler;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Objects;

final class PolyglotFunctionProxyHandler
implements InvocationHandler,
PolyglotWrapper {
    final Object functionObj;
    final PolyglotLanguageContext languageContext;
    private final Method functionMethod;
    private final CallTarget target;

    PolyglotFunctionProxyHandler(Object obj, Method functionMethod, PolyglotLanguageContext languageContext) {
        this.functionObj = obj;
        this.languageContext = languageContext;
        this.functionMethod = functionMethod;
        this.target = FunctionProxyNode.lookup(languageContext, obj.getClass(), functionMethod);
    }

    @CompilerDirectives.TruffleBoundary
    static <T> T create(Class<T> functionalType, Object function, PolyglotLanguageContext languageContext) {
        assert (PolyglotFunctionProxyHandler.isFunctionalInterface(functionalType));
        Method functionalInterfaceMethod = PolyglotFunctionProxyHandler.functionalInterfaceMethod(functionalType);
        PolyglotFunctionProxyHandler handler = new PolyglotFunctionProxyHandler(function, functionalInterfaceMethod, languageContext);
        Object obj = Proxy.newProxyInstance(functionalType.getClassLoader(), new Class[]{functionalType}, handler);
        return functionalType.cast(obj);
    }

    static Method functionalInterfaceMethod(Class<?> functionalInterface) {
        if (!functionalInterface.isInterface()) {
            return null;
        }
        Method found = null;
        for (Method m : functionalInterface.getMethods()) {
            if (!Modifier.isAbstract(m.getModifiers()) || PolyglotFunctionProxyHandler.isObjectMethodOverride(m)) continue;
            if (found != null) {
                return null;
            }
            found = m;
        }
        return found;
    }

    static boolean isObjectMethodOverride(Method m) {
        return m.getParameterCount() == 0 && (m.getName().equals("hashCode") || m.getName().equals("toString")) || m.getParameterCount() == 1 && m.getName().equals("equals") && m.getParameterTypes()[0] == Object.class;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isFunctionalInterface(Class<?> type) {
        if (!type.isInterface() || type == TruffleObject.class) {
            return false;
        }
        if (type.getAnnotation(FunctionalInterface.class) != null) {
            return true;
        }
        return PolyglotFunctionProxyHandler.functionalInterfaceMethod(type) != null;
    }

    @Override
    public Object getGuestObject() {
        return this.functionObj;
    }

    @Override
    public PolyglotContextImpl getContext() {
        return this.languageContext.context;
    }

    @Override
    public PolyglotLanguageContext getLanguageContext() {
        return this.languageContext;
    }

    public int hashCode() {
        return PolyglotWrapper.hashCode(this.languageContext, this.functionObj);
    }

    public boolean equals(Object o) {
        if (o instanceof PolyglotFunctionProxyHandler) {
            return PolyglotWrapper.equals(this.languageContext, this.functionObj, ((PolyglotFunctionProxyHandler)o).functionObj);
        }
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        Object[] resolvedArguments;
        CompilerAsserts.neverPartOfCompilation();
        Object[] objectArray = resolvedArguments = arguments == null ? PolyglotObjectProxyHandler.EMPTY : arguments;
        if (method.equals(this.functionMethod)) {
            return this.target.call(this.languageContext, this.functionObj, this.spreadVarArgsArray(resolvedArguments));
        }
        return PolyglotFunctionProxyHandler.invokeDefault(this, proxy, method, resolvedArguments);
    }

    private Object[] spreadVarArgsArray(Object[] arguments) {
        if (!this.functionMethod.isVarArgs()) {
            return arguments;
        }
        if (arguments.length == 1) {
            return (Object[])arguments[0];
        }
        int allButOne = arguments.length - 1;
        Object[] last = (Object[])arguments[allButOne];
        Object[] merge = new Object[allButOne + last.length];
        System.arraycopy(arguments, 0, merge, 0, allButOne);
        System.arraycopy(last, 0, merge, allButOne, last.length);
        return merge;
    }

    static Object invokeDefault(PolyglotWrapper host, Object proxy, Method method, Object[] arguments) throws Throwable {
        MethodHandle mh;
        if (method.getDeclaringClass() == Object.class) {
            switch (method.getName()) {
                case "equals": {
                    return PolyglotWrapper.equalsProxy(host, arguments[0]);
                }
                case "hashCode": {
                    return PolyglotWrapper.hashCode(host.getLanguageContext(), host.getGuestObject());
                }
                case "toString": {
                    return PolyglotWrapper.toString(host);
                }
            }
            throw new UnsupportedOperationException(method.getName());
        }
        if (TruffleOptions.AOT) {
            throw new UnsupportedOperationException("calling default method " + method.getName() + " is not yet supported on SubstrateVM");
        }
        Class<?> declaringClass = method.getDeclaringClass();
        assert (declaringClass.isInterface()) : declaringClass;
        try {
            Truffle.class.getModule().addReads(declaringClass.getModule());
            mh = MethodHandles.lookup().findSpecial(declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
        }
        catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(method.getName(), e);
        }
        return mh.bindTo(proxy).invokeWithArguments(arguments);
    }

    @ImportStatic(value={PolyglotObjectProxyHandler.ProxyInvokeNode.class})
    static abstract class FunctionProxyNode
    extends HostToGuestRootNode {
        final Class<?> receiverClass;
        final Method method;

        FunctionProxyNode(PolyglotLanguageInstance languageInstance, Class<?> receiverType, Method method) {
            super(languageInstance);
            this.receiverClass = receiverType;
            this.method = method;
        }

        protected Class<? extends TruffleObject> getReceiverType() {
            return this.receiverClass;
        }

        @Override
        public final String getName() {
            return "FunctionalInterfaceProxy<" + this.receiverClass + ", " + this.method + ">";
        }

        @Specialization
        protected Object doCached(PolyglotLanguageContext languageContext, TruffleObject function, Object[] args, @Cached(value="getMethodReturnType(method)") Class<?> returnClass, @Cached(value="getMethodGenericReturnType(method)") Type returnType, @Cached PolyglotExecuteNode executeNode) {
            return executeNode.execute(languageContext, function, args[2], returnClass, returnType, Object[].class, (Type)((Object)Object[].class));
        }

        public int hashCode() {
            int result = 1;
            result = 31 * result + Objects.hashCode(this.receiverClass);
            result = 31 * result + Objects.hashCode(this.method);
            return result;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FunctionProxyNode)) {
                return false;
            }
            FunctionProxyNode other = (FunctionProxyNode)obj;
            return this.receiverClass == other.receiverClass && this.method.equals(other.method);
        }

        static CallTarget lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Method method) {
            FunctionProxyNode node = PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.create(languageContext.getLanguageInstance(), receiverClass, method);
            CallTarget target = FunctionProxyNode.lookupHostCodeCache(languageContext, node, CallTarget.class);
            if (target == null) {
                target = FunctionProxyNode.installHostCodeCache(languageContext, node, node.getCallTarget(), CallTarget.class);
            }
            return target;
        }
    }
}

