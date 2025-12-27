package com.oracle.truffle.js.runtime.java;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JavaImporter extends JSNonProxy implements JSConstructorFactory.Default, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("JavaImporter");
   private static final JavaImporter INSTANCE = new JavaImporter();

   private JavaImporter() {
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @Override
   public String toString() {
      return Strings.toJavaString(CLASS_NAME);
   }

   public static JavaImporterObject create(JSContext context, JSRealm realm, Object[] value) {
      JSObjectFactory factory = context.getJavaImporterFactory();
      JavaImporterObject obj = new JavaImporterObject(factory.getShape(realm), value);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static boolean isJavaImporter(Object obj) {
      return obj instanceof JavaImporterObject;
   }

   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object name) {
      return this.getOwnHelper(thisObj, thisObj, name, null) != null;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      if (key instanceof TruffleString) {
         TruffleString name = (TruffleString)key;
         Object[] imports = getImports(store);
         JSRealm realm = JSRealm.get(null);

         for (int i = imports.length - 1; i >= 0; i--) {
            Object anImport = imports[i];
            if (anImport instanceof JavaPackageObject) {
               JavaPackageObject javaPackage = (JavaPackageObject)anImport;
               Object found = JavaPackage.lookupClass(realm, javaPackage, name);
               if (found != null) {
                  return found;
               }
            } else {
               try {
                  if (name.equals(InteropLibrary.getUncached().asTruffleString(InteropLibrary.getUncached().getMetaSimpleName(anImport)))) {
                     return anImport;
                  }
               } catch (UnsupportedMessageException var12) {
                  throw Errors.createTypeErrorInteropException(anImport, var12, "getSimpleName", null);
               }
            }
         }
      }

      return null;
   }

   public static Object[] getImports(JSDynamicObject importer) {
      assert isJavaImporter(importer);

      return ((JavaImporterObject)importer).getImports();
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject object, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return Strings.addBrackets(this.getClassName());
   }

   @Override
   public JSDynamicObject createPrototype(final JSRealm realm, JSFunctionObject ctor) {
      JSContext context = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
      JSObjectUtil.putConstructorProperty(context, prototype, ctor);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, instance(), context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return instance().createConstructorAndPrototype(realm);
   }

   public static JavaImporter instance() {
      return INSTANCE;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getJavaImporterPrototype();
   }
}
