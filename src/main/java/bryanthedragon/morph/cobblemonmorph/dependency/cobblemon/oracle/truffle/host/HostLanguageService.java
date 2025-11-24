
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.host.GuestToHostCodeCache;
import com.oracle.truffle.host.HostAccessor;
import com.oracle.truffle.host.HostAdapterFactory;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostException;
import com.oracle.truffle.host.HostFunction;
import com.oracle.truffle.host.HostLanguage;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostMethodScope;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostProxy;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import java.lang.reflect.Type;
import java.util.function.Predicate;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

public class HostLanguageService
extends AbstractPolyglotImpl.AbstractHostLanguageService {
    final HostLanguage language;

    HostLanguageService(AbstractPolyglotImpl polyglot, HostLanguage language) {
        super(polyglot);
        this.language = language;
    }

    @Override
    public void release() {
    }

    @Override
    public void initializeHostContext(Object internalContext, Object receiver, HostAccess hostAccess, ClassLoader cl, Predicate<String> clFilter, boolean hostCLAllowed, boolean hostLookupAllowed) {
        HostContext context = (HostContext)receiver;
        ClassLoader useCl = cl;
        if (useCl == null) {
            useCl = TruffleOptions.AOT ? null : Thread.currentThread().getContextClassLoader();
        }
        this.language.initializeHostAccess(hostAccess, useCl);
        context.initialize(internalContext, useCl, clFilter, hostCLAllowed, hostLookupAllowed);
    }

    @Override
    public void addToHostClassPath(Object receiver, Object truffleFile) {
        HostContext context = (HostContext)receiver;
        context.addToHostClasspath((TruffleFile)truffleFile);
    }

    @Override
    public Object findDynamicClass(Object receiver, String classValue) {
        HostContext context = (HostContext)receiver;
        Class<?> found = context.findClass(classValue);
        if (found == null) {
            return null;
        }
        return HostObject.forClass(found, context);
    }

    @Override
    public void throwHostLanguageException(String message) {
        throw new HostLanguage.HostLanguageException(message);
    }

    @Override
    public Object findStaticClass(Object receiver, String classValue) {
        HostContext context = (HostContext)receiver;
        Class<?> found = context.findClass(classValue);
        if (found == null) {
            return null;
        }
        return HostObject.forStaticClass(found, context);
    }

    @Override
    public Object createToHostTypeNode() {
        return HostToTypeNodeGen.create();
    }

    @Override
    public <T> T toHostType(Object hostNode, Object hostContext, Object value2, Class<T> targetType, Type genericType) {
        HostContext context = (HostContext)hostContext;
        HostToTypeNode node = (HostToTypeNode)hostNode;
        if (node == null) {
            node = HostToTypeNodeGen.getUncached();
        }
        return (T)node.execute(context, value2, targetType, genericType, true);
    }

    @Override
    public Object asHostStaticClass(Object context, Class<?> value2) {
        return HostObject.forStaticClass(value2, (HostContext)context);
    }

    @Override
    public Object toGuestValue(Object hostContext, Object hostValue, boolean asValue) {
        HostContext context = (HostContext)hostContext;
        assert (this.validHostValue(hostValue, context)) : "polyglot unboxing should be a no-op at this point.";
        if (HostContext.isGuestPrimitive(hostValue)) {
            return hostValue;
        }
        if (hostValue instanceof Proxy) {
            return HostProxy.toProxyGuestObject(context, (Proxy)hostValue);
        }
        if (!asValue && hostValue instanceof HostMethodScope.ScopedObject) {
            return ((HostMethodScope.ScopedObject)hostValue).unwrapForGuest();
        }
        if (hostValue instanceof TruffleObject) {
            return hostValue;
        }
        if (hostValue instanceof Class) {
            return HostObject.forClass((Class)hostValue, context);
        }
        if (hostValue == null) {
            return HostObject.NULL;
        }
        return HostObject.forObject(hostValue, context);
    }

    private boolean validHostValue(Object hostValue, HostContext context) {
        Object unboxed = this.language.access.toGuestValue(context.internalContext, hostValue);
        return unboxed == hostValue;
    }

    @Override
    public boolean isHostValue(Object value2) {
        Object obj = HostLanguage.unwrapIfScoped(this.language, value2);
        return obj instanceof HostObject || obj instanceof HostFunction || obj instanceof HostException || obj instanceof HostProxy;
    }

    @Override
    public Object unboxHostObject(Object hostValue) {
        return HostObject.valueOf(this.language, hostValue);
    }

    @Override
    public Object unboxProxyObject(Object hostValue) {
        return HostProxy.toProxyHostObject(this.language, hostValue);
    }

    @Override
    public Throwable unboxHostException(Throwable hostValue) {
        if (hostValue instanceof HostException) {
            return ((HostException)hostValue).getOriginal();
        }
        return null;
    }

    @Override
    public Object toHostObject(Object hostContext, Object value2) {
        HostContext context = (HostContext)hostContext;
        return HostObject.forObject(value2, context);
    }

    @Override
    public Object asHostDynamicClass(Object context, Class<?> value2) {
        return null;
    }

    @Override
    public boolean isHostException(Object exception) {
        return exception instanceof HostException;
    }

    @Override
    public boolean isHostFunction(Object value2) {
        return HostFunction.isInstance(this.language, value2);
    }

    @Override
    public boolean isHostObject(Object value2) {
        return HostObject.isInstance(this.language, value2);
    }

    @Override
    public boolean isHostProxy(Object value2) {
        return HostProxy.isProxyGuestObject(this.language, value2);
    }

    @Override
    public boolean isHostSymbol(Object obj) {
        Object o = HostLanguage.unwrapIfScoped(this.language, obj);
        if (o instanceof HostObject) {
            return ((HostObject)o).isStaticClass();
        }
        return false;
    }

    @Override
    public Object createHostAdapter(Object context, Object[] hostTypes, Object classOverrides) {
        CompilerAsserts.neverPartOfCompilation();
        HostContext hostContext = (HostContext)context;
        Class[] javaTypes = new Class[hostTypes.length];
        for (int i = 0; i < hostTypes.length; ++i) {
            HostObject hostType;
            Object type = hostTypes[i];
            if (!(type instanceof HostObject) || !(hostType = (HostObject)type).isDefaultClass()) {
                throw HostEngineException.illegalArgument(hostContext.getHostClassCache().polyglotHostAccess, "Types must be host symbols or host classes.");
            }
            javaTypes[i] = hostType.asClass();
        }
        HostAdapterFactory.AdapterResult adapter2 = HostAdapterFactory.getAdapterClassFor(hostContext, javaTypes, classOverrides);
        if (!adapter2.isSuccess()) {
            throw adapter2.throwException();
        }
        return HostObject.forStaticClass(adapter2.getAdapterClass(), hostContext);
    }

    @Override
    public RuntimeException toHostException(Object context, Throwable exception) {
        HostContext hostContext = (HostContext)context;
        return new HostException(exception, hostContext);
    }

    @Override
    public Object migrateValue(Object targetContext, Object value2, Object valueContext) {
        assert (targetContext != valueContext);
        if (value2 instanceof TruffleObject) {
            assert (value2 instanceof TruffleObject);
            if (HostObject.isInstance(this.language, value2)) {
                return HostObject.withContext(this.language, value2, (HostContext)HostAccessor.ENGINE.getHostContext(targetContext));
            }
            if (value2 instanceof HostProxy) {
                return HostProxy.withContext(value2, (HostContext)HostAccessor.ENGINE.getHostContext(targetContext));
            }
            if (valueContext == null) {
                assert (value2 instanceof TruffleObject);
                return value2;
            }
            return null;
        }
        assert (InteropLibrary.isValidValue(value2));
        return value2;
    }

    @Override
    public Error toHostResourceError(Throwable hostException) {
        Throwable t = this.unboxHostException(hostException);
        if (t instanceof StackOverflowError || t instanceof OutOfMemoryError) {
            return (Error)t;
        }
        return null;
    }

    @Override
    public int findNextGuestToHostStackTraceElement(StackTraceElement firstElement, StackTraceElement[] hostStack, int nextElementIndex) {
        StackTraceElement element = firstElement;
        int index = nextElementIndex;
        while (HostLanguageService.isGuestToHostReflectiveCall(element) && index < hostStack.length) {
            element = hostStack[index++];
        }
        if (HostLanguageService.isGuestToHostCallFromHostInterop(element)) {
            return index - nextElementIndex;
        }
        return -1;
    }

    @Override
    public void pin(Object receiver) {
        HostMethodScope.pin(receiver);
    }

    @Override
    public void hostExit(int exitCode) {
        System.exit(exitCode);
    }

    private static boolean isGuestToHostCallFromHostInterop(StackTraceElement element) {
        assert (HostLanguageService.assertClassNameUnchanged(HostObject.GuestToHostCalls.class, "com.oracle.truffle.host.HostObject$GuestToHostCalls"));
        assert (HostLanguageService.assertClassNameUnchanged(GuestToHostCodeCache.class, "com.oracle.truffle.host.GuestToHostCodeCache"));
        assert (HostLanguageService.assertClassNameUnchanged(HostMethodDesc.SingleMethod.class, "com.oracle.truffle.host.HostMethodDesc$SingleMethod"));
        switch (element.getClassName()) {
            case "com.oracle.truffle.host.HostMethodDesc$SingleMethod$MHBase": {
                return element.getMethodName().equals("invokeHandle");
            }
            case "com.oracle.truffle.host.HostMethodDesc$SingleMethod$MethodReflectImpl": {
                return element.getMethodName().equals("reflectInvoke");
            }
            case "com.oracle.truffle.host.HostObject$GuestToHostCalls": {
                return true;
            }
        }
        return element.getClassName().startsWith("com.oracle.truffle.host.GuestToHostCodeCache$") && element.getMethodName().equals("executeImpl");
    }

    private static boolean assertClassNameUnchanged(Class<?> c, String name) {
        if (c.getName().equals(name)) {
            return true;
        }
        throw new AssertionError((Object)("Class name is outdated. Expected " + name + " but got " + c.getName()));
    }

    private static boolean isGuestToHostReflectiveCall(StackTraceElement element) {
        switch (element.getClassName()) {
            case "sun.reflect.NativeMethodAccessorImpl": 
            case "sun.reflect.DelegatingMethodAccessorImpl": 
            case "jdk.internal.reflect.NativeMethodAccessorImpl": 
            case "jdk.internal.reflect.DelegatingMethodAccessorImpl": 
            case "java.lang.reflect.Method": {
                return element.getMethodName().startsWith("invoke");
            }
        }
        return false;
    }
}

