package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class HostContext {
   @CompilerDirectives.CompilationFinal
   Object internalContext;
   final Map<String, Class<?>> classCache = new HashMap<>();
   final Object topScope = new HostContext.TopScopeObject(this);
   volatile HostClassLoader classloader;
   final HostLanguage language;
   private ClassLoader contextClassLoader;
   private Predicate<String> classFilter;
   private boolean hostClassLoadingAllowed;
   private boolean hostLookupAllowed;
   final TruffleLanguage.Env env;
   final AbstractPolyglotImpl.AbstractHostAccess access;
   final HostException stackoverflowError = new HostException(new StackOverflowError() {
      @Override
      public Throwable fillInStackTrace() {
         return this;
      }
   }, this);
   final ClassValue<Map<List<Class<?>>, HostAdapterFactory.AdapterResult>> adapterCache = new ClassValue<Map<List<Class<?>>, HostAdapterFactory.AdapterResult>>(
      
   ) {
      protected Map<List<Class<?>>, HostAdapterFactory.AdapterResult> computeValue(Class<?> type) {
         return new ConcurrentHashMap<>();
      }
   };
   private static final TruffleLanguage.ContextReference<HostContext> REFERENCE = TruffleLanguage.ContextReference.create(HostLanguage.class);

   HostContext(HostLanguage hostLanguage, TruffleLanguage.Env env) {
      this.language = hostLanguage;
      this.access = hostLanguage.access;
      this.env = env;
   }

   void initialize(Object internalContext, ClassLoader cl, Predicate<String> clFilter, boolean hostCLAllowed, boolean hostLookupAllowed) {
      if ((this.classloader == null || this.classFilter == null) && !this.hostClassLoadingAllowed && !this.hostLookupAllowed) {
         this.internalContext = internalContext;
         this.contextClassLoader = cl;
         this.classFilter = clFilter;
         this.hostClassLoadingAllowed = hostCLAllowed;
         this.hostLookupAllowed = hostLookupAllowed;
      } else {
         throw new AssertionError("must not be used during context preinitialization");
      }
   }

   public HostClassCache getHostClassCache() {
      return this.language.hostClassCache;
   }

   GuestToHostCodeCache getGuestToHostCache() {
      GuestToHostCodeCache cache = (GuestToHostCodeCache)HostAccessor.ENGINE.getGuestToHostCodeCache(this.internalContext);
      if (cache == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         cache = (GuestToHostCodeCache)HostAccessor.ENGINE.installGuestToHostCodeCache(this.internalContext, new GuestToHostCodeCache(this.language));
      }

      return cache;
   }

   @CompilerDirectives.TruffleBoundary
   Class<?> findClass(String className) {
      this.checkHostAccessAllowed();
      Class<?> loadedClass = this.classCache.get(className);
      if (loadedClass == null) {
         loadedClass = this.findClassImpl(className);
         this.classCache.put(className, loadedClass);
      }

      assert loadedClass != null;

      return loadedClass;
   }

   private void checkHostAccessAllowed() {
      if (!this.hostLookupAllowed) {
         throw new HostLanguage.HostLanguageException(String.format("Host class access is not allowed."));
      }
   }

   HostClassLoader getClassloader() {
      if (this.classloader == null) {
         ClassLoader parentClassLoader = this.contextClassLoader;
         this.classloader = new HostClassLoader(this, parentClassLoader);
      }

      return this.classloader;
   }

   private Class<?> findClassImpl(String className) {
      this.validateClass(className);
      if (className.endsWith("[]")) {
         Class<?> componentType = this.findClass(className.substring(0, className.length() - 2));
         return Array.newInstance(componentType, 0).getClass();
      } else {
         Class<?> primitiveType = getPrimitiveTypeByName(className);
         if (primitiveType != null) {
            return primitiveType;
         } else {
            try {
               ClassLoader classLoader = this.getClassloader();
               Class<?> foundClass = classLoader.loadClass(className);
               Object currentModule = getUnnamedModule(classLoader);
               if (verifyModuleVisibility(currentModule, foundClass)) {
                  return foundClass;
               } else {
                  throw new HostLanguage.HostLanguageException(String.format("Access to host class %s is not allowed or does not exist.", className));
               }
            } catch (ClassNotFoundException var6) {
               throw new HostLanguage.HostLanguageException(String.format("Access to host class %s is not allowed or does not exist.", className));
            } catch (LinkageError var7) {
               throw new HostLanguage.HostLanguageException(String.format("Access to host class %s is not allowed or does not exist.", className));
            }
         }
      }
   }

   void validateClass(String className) {
      if (this.classFilter != null && !this.classFilter.test(className)) {
         throw new HostLanguage.HostLanguageException(String.format("Access to host class %s is not allowed.", className));
      }
   }

   static Object getUnnamedModule(ClassLoader classLoader) {
      return classLoader == null ? null : classLoader.getUnnamedModule();
   }

   static boolean verifyModuleVisibility(Object module, Class<?> memberClass) {
      Module lookupModule = (Module)module;
      if (lookupModule == null) {
         return true;
      } else {
         Module memberModule = memberClass.getModule();
         if (lookupModule == memberModule) {
            return true;
         } else {
            String pkg = memberClass.getPackageName();
            if (lookupModule.isNamed()) {
               return memberModule.isNamed() ? memberModule.isExported(pkg, lookupModule) : false;
            } else {
               return memberModule.isNamed() ? memberModule.isExported(pkg) : true;
            }
         }
      }
   }

   private static Class<?> getPrimitiveTypeByName(String className) {
      switch (className) {
         case "boolean":
            return boolean.class;
         case "byte":
            return byte.class;
         case "char":
            return char.class;
         case "double":
            return double.class;
         case "float":
            return float.class;
         case "int":
            return int.class;
         case "long":
            return long.class;
         case "short":
            return short.class;
         default:
            return null;
      }
   }

   void addToHostClasspath(TruffleFile classpathEntry) {
      this.checkHostAccessAllowed();
      if (TruffleOptions.AOT) {
         throw new HostLanguage.HostLanguageException(String.format("Cannot add classpath entry %s in native mode.", classpathEntry.getName()));
      } else if (!this.hostClassLoadingAllowed) {
         throw new HostLanguage.HostLanguageException(String.format("Host class loading is not allowed."));
      } else {
         this.getClassloader().addClasspathRoot(classpathEntry);
      }
   }

   @CompilerDirectives.TruffleBoundary
   <T extends Throwable> RuntimeException hostToGuestException(T e) {
      assert !(e instanceof HostException) : "host exceptions not expected here";

      if (e instanceof ThreadDeath) {
         throw (ThreadDeath)e;
      } else {
         if (e instanceof PolyglotException) {
            this.language.access.rethrowPolyglotException(this.internalContext, (PolyglotException)e);
         }

         try {
            return new HostException(e, this);
         } catch (StackOverflowError var3) {
            return this.stackoverflowError;
         }
      }
   }

   Value asValue(Node node, Object value) {
      HostLanguage l = HostLanguage.get(node);
      return l.access.toValue(this.internalContext, value);
   }

   Object toGuestValue(Class<?> receiver) {
      return HostObject.forClass(receiver, this);
   }

   Object toGuestValue(Node node, Object hostValue) {
      HostLanguage l = HostLanguage.get(node);
      HostContext context = get(node);

      assert context == this;

      Object result = l.access.toGuestValue(context.internalContext, hostValue);
      return l.service.toGuestValue(context, result, false);
   }

   static boolean isGuestPrimitive(Object receiver) {
      return receiver instanceof Integer
         || receiver instanceof Double
         || receiver instanceof Long
         || receiver instanceof Float
         || receiver instanceof Boolean
         || receiver instanceof Character
         || receiver instanceof Byte
         || receiver instanceof Short
         || receiver instanceof String
         || receiver instanceof TruffleString;
   }

   static HostContext get(Node node) {
      return REFERENCE.get(node);
   }

   public void disposeClassLoader() {
      HostClassLoader cl = this.classloader;
      if (cl != null) {
         try {
            cl.close();
         } catch (IOException var3) {
         }

         this.classloader = null;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class ClassNamesObject implements TruffleObject {
      final ArrayList<String> names;

      private ClassNamesObject(Set<String> names) {
         this.names = new ArrayList<>(names);
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object readArrayElement(long index) throws InvalidArrayIndexException {
         if (index >= 0L && index <= 2147483647L) {
            try {
               return this.names.get((int)index);
            } catch (IndexOutOfBoundsException var4) {
               throw InvalidArrayIndexException.create(index);
            }
         } else {
            throw InvalidArrayIndexException.create(index);
         }
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      long getArraySize() {
         return this.names.size();
      }

      @ExportMessage
      boolean isArrayElementReadable(long index) {
         return index >= 0L && index < this.getArraySize();
      }
   }

   @GenerateUncached
   abstract static class ToGuestValueNode extends Node {
      abstract Object execute(HostContext context, Object receiver);

      @Specialization(guards = "receiver == null")
      Object doNull(HostContext context, Object receiver) {
         return context.toGuestValue(this, receiver);
      }

      @Specialization(guards = {"receiver != null", "receiver.getClass() == cachedReceiver"}, limit = "3")
      Object doCached(HostContext context, Object receiver, @Cached("receiver.getClass()") Class<?> cachedReceiver) {
         return context.toGuestValue(this, cachedReceiver.cast(receiver));
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      Object doUncached(HostContext context, Object receiver) {
         return context.toGuestValue(this, receiver);
      }
   }

   static final class ToGuestValuesNode extends Node {
      @Node.Children
      private volatile HostContext.ToGuestValueNode[] toGuestValue;
      @CompilerDirectives.CompilationFinal
      private volatile boolean needsCopy = false;
      @CompilerDirectives.CompilationFinal
      private volatile boolean generic = false;

      private ToGuestValuesNode() {
      }

      public Object[] apply(HostContext context, Object[] args) {
         HostContext.ToGuestValueNode[] nodes = this.toGuestValue;
         if (nodes == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            nodes = new HostContext.ToGuestValueNode[args.length];

            for (int i = 0; i < nodes.length; i++) {
               nodes[i] = HostContextFactory.ToGuestValueNodeGen.create();
            }

            this.toGuestValue = this.insert(nodes);
         }

         if (args.length == nodes.length) {
            return nodes.length == 0 ? args : this.fastToGuestValuesUnroll(nodes, context, args);
         } else {
            if (!this.generic || nodes.length != 1) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               nodes = Arrays.copyOf(nodes, 1);
               if (nodes[0] == null) {
                  nodes[0] = HostContextFactory.ToGuestValueNodeGen.create();
               }

               this.toGuestValue = this.insert(nodes);
               this.generic = true;
            }

            return args.length == 0 ? args : this.fastToGuestValues(nodes[0], context, args);
         }
      }

      @ExplodeLoop
      private Object[] fastToGuestValuesUnroll(HostContext.ToGuestValueNode[] nodes, HostContext context, Object[] args) {
         Object[] newArgs = this.needsCopy ? new Object[nodes.length] : args;

         for (int i = 0; i < nodes.length; i++) {
            Object arg = args[i];
            Object newArg = nodes[i].execute(context, arg);
            if (this.needsCopy) {
               newArgs[i] = newArg;
            } else if (arg != newArg) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               newArgs = new Object[nodes.length];
               System.arraycopy(args, 0, newArgs, 0, args.length);
               newArgs[i] = newArg;
               this.needsCopy = true;
            }
         }

         return newArgs;
      }

      private Object[] fastToGuestValues(HostContext.ToGuestValueNode node, HostContext context, Object[] args) {
         assert this.toGuestValue[0] != null;

         Object[] newArgs = this.needsCopy ? new Object[args.length] : args;

         for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Object newArg = node.execute(context, arg);
            if (this.needsCopy) {
               newArgs[i] = newArg;
            } else if (arg != newArg) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               newArgs = new Object[args.length];
               System.arraycopy(args, 0, newArgs, 0, args.length);
               newArgs[i] = newArg;
               this.needsCopy = true;
            }
         }

         return newArgs;
      }

      public static HostContext.ToGuestValuesNode create() {
         return new HostContext.ToGuestValuesNode();
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class TopScopeObject implements TruffleObject {
      private final HostContext context;

      private TopScopeObject(HostContext context) {
         this.context = context;
      }

      @ExportMessage
      boolean hasLanguage() {
         return true;
      }

      @ExportMessage
      Class<? extends TruffleLanguage<?>> getLanguage() {
         return HostLanguage.class;
      }

      @ExportMessage
      boolean isScope() {
         return true;
      }

      @ExportMessage
      boolean hasMembers() {
         return true;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object getMembers(boolean includeInternal) {
         return new HostContext.ClassNamesObject(this.context.classCache.keySet());
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      boolean isMemberReadable(String member) {
         return this.context.findClass(member) != null;
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      Object readMember(String member) {
         return HostObject.forStaticClass(this.context.findClass(member), this.context);
      }

      @ExportMessage
      Object toDisplayString(boolean allowSideEffects) {
         return "Static Scope";
      }
   }
}
