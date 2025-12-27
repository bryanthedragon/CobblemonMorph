package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.IntToLongTypeSystem;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@TypeSystemReference(IntToLongTypeSystem.class)
@ImportStatic({JSRuntime.class, JSInteropUtil.class, JSConfig.class})
public abstract class JSHasPropertyNode extends JavaScriptBaseNode {
   private final boolean hasOwnProperty;
   private final JSClassProfile classProfile = JSClassProfile.create();
   private final ConditionProfile hasElementProfile = ConditionProfile.createBinaryProfile();
   static final int MAX_ARRAY_TYPES = 3;

   protected JSHasPropertyNode(boolean hasOwnProperty) {
      this.hasOwnProperty = hasOwnProperty;
   }

   public static JSHasPropertyNode create() {
      return JSHasPropertyNodeGen.create(false);
   }

   public static JSHasPropertyNode create(boolean hasOwnProperty) {
      return JSHasPropertyNodeGen.create(hasOwnProperty);
   }

   public abstract boolean executeBoolean(Object object, Object propertyName);

   public abstract boolean executeBoolean(Object object, long index);

   @Specialization(guards = {"isJSFastArray(object)", "isArrayIndex(index)", "cachedArrayType.isInstance(getArrayType(object))"}, limit = "MAX_ARRAY_TYPES")
   public boolean arrayLongCached(JSDynamicObject object, long index, @Cached("getArrayType(object)") ScriptArray cachedArrayType) {
      return this.checkInteger(object, index, cachedArrayType.cast(getArrayType(object)));
   }

   @Specialization(guards = {"isJSFastArray(object)", "isArrayIndex(index)"}, replaces = "arrayLongCached")
   public boolean arrayLong(JSDynamicObject object, long index) {
      return this.checkInteger(object, index, getArrayType(object));
   }

   private boolean checkInteger(JSDynamicObject object, long index, ScriptArray arrayType) {
      return this.hasElementProfile.profile(arrayType.hasElement(object, index)) ? true : this.objectLong(object, index);
   }

   @Specialization
   public boolean typedArray(JSTypedArrayObject object, long index) {
      return !JSArrayBufferView.hasDetachedBuffer(object, this.getLanguage().getJSContext())
         && index >= 0L
         && index < JSArrayBufferView.typedArrayGetLength(object);
   }

   @Specialization(guards = {"cachedObjectType != null", "cachedObjectType.isInstance(object)", "cachedName.equals(propertyName)"}, limit = "1")
   public boolean objectStringCached(
      JSDynamicObject object,
      String propertyName,
      @Cached("getCacheableObjectType(object)") JSClass cachedObjectType,
      @Cached("propertyName") String cachedName,
      @Cached("getCachedPropertyGetter(object,propertyName)") HasPropertyCacheNode hasPropertyNode
   ) {
      return hasPropertyNode.hasProperty(object);
   }

   @Specialization(guards = {"isJSArray(object)", "!isArrayIndex(cachedName)", "cachedName.equals(propertyName)"}, limit = "1")
   public boolean arrayStringCached(
      JSDynamicObject object,
      String propertyName,
      @Cached("propertyName") String cachedName,
      @Cached("getCachedPropertyGetter(object,propertyName)") HasPropertyCacheNode hasPropertyNode
   ) {
      return hasPropertyNode.hasProperty(object);
   }

   @Specialization(guards = "isJSDynamicObject(object)", replaces = {"objectStringCached", "arrayStringCached"})
   @ReportPolymorphism.Megamorphic
   public boolean objectOrArrayString(JSDynamicObject object, String propertyName) {
      return this.hasPropertyGeneric(object, propertyName);
   }

   @Specialization(guards = "isJSDynamicObject(object)")
   @ReportPolymorphism.Megamorphic
   public boolean objectSymbol(JSDynamicObject object, Symbol propertyName) {
      return this.hasPropertyGeneric(object, propertyName);
   }

   @Specialization(guards = {"isJSDynamicObject(object)", "!isJSFastArray(object)", "!isJSArrayBufferView(object)"})
   public boolean objectLong(JSDynamicObject object, long propertyIdx) {
      return this.hasOwnProperty
         ? JSObject.hasOwnProperty(object, propertyIdx, this.classProfile)
         : JSObject.hasProperty(object, propertyIdx, this.classProfile);
   }

   private boolean hasPropertyGeneric(JSDynamicObject object, Object propertyKey) {
      assert JSRuntime.isPropertyKey(propertyKey);

      return this.hasOwnProperty
         ? JSObject.hasOwnProperty(object, propertyKey, this.classProfile)
         : JSObject.hasProperty(object, propertyKey, this.classProfile);
   }

   @Specialization(guards = "isForeignObject(object)", limit = "InteropLibraryLimit")
   public boolean foreignObject(
      Object object,
      Object propertyName,
      @CachedLibrary("object") InteropLibrary interop,
      @Cached("create()") JSToStringNode toStringNode,
      @Cached("create()") ForeignObjectPrototypeNode foreignObjectPrototypeNode,
      @Cached("create()") JSHasPropertyNode hasInPrototype
   ) {
      if (propertyName instanceof Number && interop.hasArrayElements(object)) {
         long index = JSRuntime.longValue((Number)propertyName);
         return index >= 0L && index < JSInteropUtil.getArraySize(object, interop, this);
      } else if (!(propertyName instanceof Symbol) && interop.isMemberExisting(object, Strings.toJavaString(toStringNode.executeString(propertyName)))) {
         return true;
      } else if (this.getLanguage().getJSContext().getContextOptions().hasForeignObjectPrototype()) {
         JSDynamicObject prototype = foreignObjectPrototypeNode.execute(object);
         return hasInPrototype.executeBoolean(prototype, propertyName);
      } else {
         return false;
      }
   }

   @Specialization(guards = "isJSDynamicObject(object)")
   @ReportPolymorphism.Megamorphic
   public boolean objectObject(JSDynamicObject object, Object propertyName, @Cached("create()") JSToPropertyKeyNode toPropertyKeyNode) {
      Object propertyKey = toPropertyKeyNode.execute(propertyName);
      return this.hasPropertyGeneric(object, propertyKey);
   }

   protected static boolean isCacheableObjectType(JSDynamicObject obj) {
      return JSDynamicObject.isJSDynamicObject(obj)
         && !JSRuntime.isNullOrUndefined(obj)
         && !JSString.isJSString(obj)
         && !JSArray.isJSArray(obj)
         && !JSArgumentsArray.isJSArgumentsObject(obj)
         && !JSArrayBufferView.isJSArrayBufferView(obj);
   }

   protected static JSClass getCacheableObjectType(JSDynamicObject obj) {
      return isCacheableObjectType(obj) ? JSObject.getJSClass(obj) : null;
   }

   protected static ScriptArray getArrayType(JSDynamicObject object) {
      return JSAbstractArray.arrayGetArrayType(object);
   }

   protected HasPropertyCacheNode getCachedPropertyGetter(JSDynamicObject object, Object key) {
      return HasPropertyCacheNode.create(key, JSObject.getJSContext(object), this.hasOwnProperty);
   }
}
