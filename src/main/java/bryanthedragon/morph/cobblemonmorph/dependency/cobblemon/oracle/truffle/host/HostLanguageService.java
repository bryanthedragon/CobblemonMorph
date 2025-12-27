package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import java.lang.reflect.Type;
import java.util.function.Predicate;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

public class HostLanguageService extends AbstractPolyglotImpl.AbstractHostLanguageService {
   final HostLanguage language;

   HostLanguageService(AbstractPolyglotImpl polyglot, HostLanguage language) {
      super(polyglot);
      this.language = language;
   }

   @Override
   public void release() {
   }

   @Override
   public void initializeHostContext(
      Object internalContext,
      Object receiver,
      HostAccess hostAccess,
      ClassLoader cl,
      Predicate<String> clFilter,
      boolean hostCLAllowed,
      boolean hostLookupAllowed
   ) {
      HostContext context = (HostContext)receiver;
      ClassLoader useCl = cl;
      if (cl == null) {
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
      return found == null ? null : HostObject.forClass(found, context);
   }

   @Override
   public void throwHostLanguageException(String message) {
      throw new HostLanguage.HostLanguageException(message);
   }

   @Override
   public Object findStaticClass(Object receiver, String classValue) {
      HostContext context = (HostContext)receiver;
      Class<?> found = context.findClass(classValue);
      return found == null ? null : HostObject.forStaticClass(found, context);
   }

   @Override
   public Object createToHostTypeNode() {
      return HostToTypeNodeGen.create();
   }

   @Override
   public <T> T toHostType(Object hostNode, Object hostContext, Object value, Class<T> targetType, Type genericType) {
      HostContext context = (HostContext)hostContext;
      HostToTypeNode node = (HostToTypeNode)hostNode;
      if (node == null) {
         node = HostToTypeNodeGen.getUncached();
      }

      return (T)node.execute(context, value, targetType, genericType, true);
   }

   @Override
   public Object asHostStaticClass(Object context, Class<?> value) {
      return HostObject.forStaticClass(value, (HostContext)context);
   }

   @Override
   public Object toGuestValue(Object hostContext, Object hostValue, boolean asValue) {
      HostContext context = (HostContext)hostContext;

      assert this.validHostValue(hostValue, context) : "polyglot unboxing should be a no-op at this point.";

      if (HostContext.isGuestPrimitive(hostValue)) {
         return hostValue;
      } else if (hostValue instanceof Proxy) {
         return HostProxy.toProxyGuestObject(context, (Proxy)hostValue);
      } else if (!asValue && hostValue instanceof HostMethodScope.ScopedObject) {
         return ((HostMethodScope.ScopedObject)hostValue).unwrapForGuest();
      } else if (hostValue instanceof TruffleObject) {
         return hostValue;
      } else if (hostValue instanceof Class) {
         return HostObject.forClass((Class<?>)hostValue, context);
      } else {
         return hostValue == null ? HostObject.NULL : HostObject.forObject(hostValue, context);
      }
   }

   private boolean validHostValue(Object hostValue, HostContext context) {
      Object unboxed = this.language.access.toGuestValue(context.internalContext, hostValue);
      return unboxed == hostValue;
   }

   @Override
   public boolean isHostValue(Object value) {
      Object obj = HostLanguage.unwrapIfScoped(this.language, value);
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
      return hostValue instanceof HostException ? ((HostException)hostValue).getOriginal() : null;
   }

   @Override
   public Object toHostObject(Object hostContext, Object value) {
      HostContext context = (HostContext)hostContext;
      return HostObject.forObject(value, context);
   }

   @Override
   public Object asHostDynamicClass(Object context, Class<?> value) {
      return null;
   }

   @Override
   public boolean isHostException(Object exception) {
      return exception instanceof HostException;
   }

   @Override
   public boolean isHostFunction(Object value) {
      return HostFunction.isInstance(this.language, value);
   }

   @Override
   public boolean isHostObject(Object value) {
      return HostObject.isInstance(this.language, value);
   }

   @Override
   public boolean isHostProxy(Object value) {
      return HostProxy.isProxyGuestObject(this.language, value);
   }

   @Override
   public boolean isHostSymbol(Object obj) {
      Object o = HostLanguage.unwrapIfScoped(this.language, obj);
      return o instanceof HostObject ? ((HostObject)o).isStaticClass() : false;
   }

   @Override
   public Object createHostAdapter(Object context, Object[] hostTypes, Object classOverrides) {
      CompilerAsserts.neverPartOfCompilation();
      HostContext hostContext = (HostContext)context;
      Class<?>[] javaTypes = new Class[hostTypes.length];
      int i = 0;

      while (true) {
         if (i >= hostTypes.length) {
            HostAdapterFactory.AdapterResult adapter = HostAdapterFactory.getAdapterClassFor(hostContext, javaTypes, classOverrides);
            if (!adapter.isSuccess()) {
               throw adapter.throwException();
            }

            return HostObject.forStaticClass(adapter.getAdapterClass(), hostContext);
         }

         Object type = hostTypes[i];
         if (!(type instanceof HostObject)) {
            break;
         }

         HostObject hostType = (HostObject)type;
         if (!hostType.isDefaultClass()) {
            break;
         }

         javaTypes[i] = hostType.asClass();
         i++;
      }

      throw HostEngineException.illegalArgument(hostContext.getHostClassCache().polyglotHostAccess, "Types must be host symbols or host classes.");
   }

   @Override
   public RuntimeException toHostException(Object context, Throwable exception) {
      HostContext hostContext = (HostContext)context;
      return new HostException(exception, hostContext);
   }

   @Override
   public Object migrateValue(Object targetContext, Object value, Object valueContext) {
      assert targetContext != valueContext;

      if (value instanceof TruffleObject) {
         assert value instanceof TruffleObject;

         if (HostObject.isInstance(this.language, value)) {
            return HostObject.withContext(this.language, value, (HostContext)HostAccessor.ENGINE.getHostContext(targetContext));
         } else if (value instanceof HostProxy) {
            return HostProxy.withContext(value, (HostContext)HostAccessor.ENGINE.getHostContext(targetContext));
         } else if (valueContext == null) {
            assert value instanceof TruffleObject;

            return value;
         } else {
            return null;
         }
      } else {
         assert InteropLibrary.isValidValue(value);

         return value;
      }
   }

   @Override
   public Error toHostResourceError(Throwable hostException) {
      Throwable t = this.unboxHostException(hostException);
      return !(t instanceof StackOverflowError) && !(t instanceof OutOfMemoryError) ? null : (Error)t;
   }

   @Override
   public int findNextGuestToHostStackTraceElement(StackTraceElement firstElement, StackTraceElement[] hostStack, int nextElementIndex) {
      StackTraceElement element = firstElement;
      int index = nextElementIndex;

      while (isGuestToHostReflectiveCall(element) && index < hostStack.length) {
         element = hostStack[index++];
      }

      return isGuestToHostCallFromHostInterop(element) ? index - nextElementIndex : -1;
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
      assert assertClassNameUnchanged(HostObject.GuestToHostCalls.class, "com.oracle.truffle.host.HostObject$GuestToHostCalls");

      assert assertClassNameUnchanged(GuestToHostCodeCache.class, "com.oracle.truffle.host.GuestToHostCodeCache");

      assert assertClassNameUnchanged(HostMethodDesc.SingleMethod.class, "com.oracle.truffle.host.HostMethodDesc$SingleMethod");

      String var1 = element.getClassName();
      switch (var1) {
         case "com.oracle.truffle.host.HostMethodDesc$SingleMethod$MHBase":
            return element.getMethodName().equals("invokeHandle");
         case "com.oracle.truffle.host.HostMethodDesc$SingleMethod$MethodReflectImpl":
            return element.getMethodName().equals("reflectInvoke");
         case "com.oracle.truffle.host.HostObject$GuestToHostCalls":
            return true;
         default:
            return element.getClassName().startsWith("com.oracle.truffle.host.GuestToHostCodeCache$") && element.getMethodName().equals("executeImpl");
      }
   }

   private static boolean assertClassNameUnchanged(Class<?> c, String name) {
      if (c.getName().equals(name)) {
         return true;
      } else {
         throw new AssertionError("Class name is outdated. Expected " + name + " but got " + c.getName());
      }
   }

   private static boolean isGuestToHostReflectiveCall(StackTraceElement element) {
      String var1 = element.getClassName();
      switch (var1) {
         case "sun.reflect.NativeMethodAccessorImpl":
         case "sun.reflect.DelegatingMethodAccessorImpl":
         case "jdk.internal.reflect.NativeMethodAccessorImpl":
         case "jdk.internal.reflect.DelegatingMethodAccessorImpl":
         case "java.lang.reflect.Method":
            return element.getMethodName().startsWith("invoke");
         default:
            return false;
      }
   }
}
