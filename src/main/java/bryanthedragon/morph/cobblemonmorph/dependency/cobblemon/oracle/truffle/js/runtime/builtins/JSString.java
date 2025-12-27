package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.StringFunctionBuiltins;
import com.oracle.truffle.js.builtins.StringPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import com.oracle.truffle.js.runtime.util.IteratorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JSString extends JSPrimitive implements JSConstructorFactory.WithFunctions {
   public static final JSString INSTANCE = new JSString();
   public static final TruffleString TYPE_NAME = Strings.STRING;
   public static final TruffleString CLASS_NAME = Strings.UC_STRING;
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("String.prototype");
   public static final TruffleString CLASS_NAME_EXTENSIONS = Strings.constant("StringExtensions");
   public static final TruffleString LENGTH = Strings.constant("length");
   public static final TruffleString ITERATOR_CLASS_NAME = Strings.constant("String Iterator");
   public static final TruffleString ITERATOR_PROTOTYPE_NAME = Strings.constant("String Iterator.prototype");
   public static final HiddenKey ITERATED_STRING_ID = new HiddenKey("IteratedString");
   public static final HiddenKey STRING_ITERATOR_NEXT_INDEX_ID = new HiddenKey("StringIteratorNextIndex");
   public static final TruffleString REGEXP_ITERATOR_CLASS_NAME = Strings.constant("RegExp String Iterator");
   public static final TruffleString REGEXP_ITERATOR_PROTOTYPE_NAME = Strings.constant("RegExp String Iterator.prototype");
   public static final HiddenKey REGEXP_ITERATOR_ITERATING_REGEXP_ID = new HiddenKey("IteratingRegExp");
   public static final HiddenKey REGEXP_ITERATOR_ITERATED_STRING_ID = new HiddenKey("IteratedString");
   public static final HiddenKey REGEXP_ITERATOR_GLOBAL_ID = new HiddenKey("Global");
   public static final HiddenKey REGEXP_ITERATOR_UNICODE_ID = new HiddenKey("Unicode");
   public static final HiddenKey REGEXP_ITERATOR_DONE_ID = new HiddenKey("Done");
   private static final PropertyProxy LENGTH_PROXY = new JSString.StringLengthProxyProperty();
   public static final TruffleString TRIM_START = Strings.constant("trimStart");
   public static final TruffleString TRIM_END = Strings.constant("trimEnd");
   public static final TruffleString TRIM_LEFT = Strings.constant("trimLeft");
   public static final TruffleString TRIM_RIGHT = Strings.constant("trimRight");

   private JSString() {
   }

   public static JSStringObject create(JSContext context, JSRealm realm, TruffleString value) {
      JSStringObject stringObj = JSStringObject.create(realm, context.getStringFactory(), value);
      return context.trackAllocation(stringObj);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      if (super.hasOwnProperty(thisObj, key)) {
         return true;
      } else {
         long index = JSRuntime.propertyKeyToArrayIndex(key);
         return index >= 0L && index < getStringLength(thisObj);
      }
   }

   public static TruffleString getString(JSDynamicObject obj) {
      assert isJSString(obj);

      return ((JSStringObject)obj).getString();
   }

   @CompilerDirectives.TruffleBoundary
   public static int getStringLength(JSDynamicObject obj) {
      assert isJSString(obj);

      return Strings.length(getString(obj));
   }

   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      return index >= 0L && index < getStringLength(thisObj) ? true : super.hasOwnProperty(thisObj, Strings.fromLong(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      long value = JSRuntime.propertyKeyToArrayIndex(key);
      return 0L <= value && value < getStringLength(store)
         ? Strings.substring(JavaScriptLanguage.get(encapsulatingNode).getJSContext(), getString(store), (int)value, 1)
         : super.getOwnHelper(store, thisObj, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      return 0L <= index && index < getStringLength(store)
         ? Strings.substring(JavaScriptLanguage.get(encapsulatingNode).getJSContext(), getString(store), (int)index, 1)
         : super.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      } else {
         long index = JSRuntime.propertyKeyToArrayIndex(key);
         if (index < 0L || index >= getStringLength(thisObj)) {
            return super.set(thisObj, key, value, receiver, isStrict, encapsulatingNode);
         } else if (isStrict) {
            throw Errors.createTypeErrorNotWritableIndex(index, thisObj, encapsulatingNode);
         } else {
            return true;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, Strings.fromLong(index), value, receiver, isStrict, encapsulatingNode);
      } else if (index < getStringLength(thisObj)) {
         if (isStrict) {
            throw Errors.createTypeErrorNotWritableIndex(index, thisObj, encapsulatingNode);
         } else {
            return true;
         }
      } else {
         return super.set(thisObj, index, value, receiver, isStrict, encapsulatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      int len = getStringLength(thisObj);
      List<Object> indices = strings ? ScriptArray.makeRangeList(0L, len) : Collections.emptyList();
      List<Object> keyList = thisObj.getShape().getKeyList();
      if (keyList.isEmpty()) {
         return indices;
      } else {
         List<Object> list = new ArrayList<>(keyList.size());
         if (strings) {
            keyList.forEach(k -> {
               if (Strings.isTString(k) && JSRuntime.isArrayIndexString((TruffleString)k)) {
                  assert JSRuntime.propertyKeyToArrayIndex(k) >= (long)len;

                  list.add(k);
               }
            });
            Collections.sort(list, JSRuntime::comparePropertyKeys);
            keyList.forEach(k -> {
               if (Strings.isTString(k) && !JSRuntime.isArrayIndexString((TruffleString)k)) {
                  list.add(k);
               }
            });
         }

         if (symbols) {
            keyList.forEach(k -> {
               if (k instanceof Symbol) {
                  list.add(k);
               }
            });
         }

         return IteratorUtil.concatLists(indices, list);
      }
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      long idx = JSRuntime.propertyKeyToArrayIndex(key);
      if (!JSRuntime.isArrayIndex(idx) || 0L > idx || idx >= getStringLength(thisObj)) {
         return deletePropertyDefault(thisObj, key, isStrict);
      } else if (isStrict) {
         throw Errors.createTypeError("cannot delete index");
      } else {
         return false;
      }
   }

   @Override
   public JSDynamicObject createPrototype(final JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      Shape protoShape = JSShape.createPrototypeShape(ctx, INSTANCE, realm.getObjectPrototype());
      JSObject prototype = JSStringObject.create(protoShape, Strings.EMPTY_STRING);
      JSObjectUtil.setOrVerifyPrototype(ctx, prototype, realm.getObjectPrototype());
      JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
      JSObjectUtil.putDataProperty(ctx, prototype, LENGTH, 0, JSAttributes.notConfigurableNotEnumerableNotWritable());
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, StringPrototypeBuiltins.BUILTINS);
      if (ctx.isOptionNashornCompatibilityMode() || ctx.getParserOptions().getEcmaScriptVersion() >= 10) {
         JSObjectUtil.putFunctionsFromContainer(realm, prototype, StringPrototypeBuiltins.EXTENSION_BUILTINS);
      }

      if (ctx.isOptionAnnexB()) {
         Object trimStart = JSObject.get(prototype, TRIM_START);
         Object trimEnd = JSObject.get(prototype, TRIM_END);
         JSObjectUtil.putDataProperty(ctx, prototype, TRIM_LEFT, trimStart, JSAttributes.configurableNotEnumerableWritable());
         JSObjectUtil.putDataProperty(ctx, prototype, TRIM_RIGHT, trimEnd, JSAttributes.configurableNotEnumerableWritable());
      }

      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
      return Shape.newBuilder(initialShape).addConstantProperty(LENGTH, LENGTH_PROXY, JSAttributes.notConfigurableNotEnumerableNotWritable() | 16).build();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, StringFunctionBuiltins.BUILTINS);
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
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()
         ? Strings.concatAll(Strings.BRACKET_OPEN, CLASS_NAME, Strings.SPACE, getString(obj), Strings.BRACKET_CLOSE)
         : JSRuntime.objectToDisplayString(
            obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{getString(obj)}
         );
   }

   public static boolean isJSString(Object obj) {
      return obj instanceof JSStringObject;
   }

   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor desc = ordinaryGetOwnProperty(thisObj, key);
      return desc == null ? stringGetIndexProperty(thisObj, key) : desc;
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      assert JSRuntime.isPropertyKey(key) : key.getClass().getName();

      long idx = JSRuntime.propertyKeyToArrayIndex(key);
      return idx >= 0L && idx < getStringLength(thisObj)
         ? DefinePropertyUtil.isCompatiblePropertyDescriptor(this.isExtensible(thisObj), desc, stringGetIndexProperty(thisObj, key), doThrow)
         : super.defineOwnProperty(thisObj, key, desc, doThrow);
   }

   @CompilerDirectives.TruffleBoundary
   public static PropertyDescriptor stringGetIndexProperty(JSDynamicObject thisObj, Object key) {
      assert isJSString(thisObj);

      long index = JSRuntime.propertyKeyToArrayIndex(key);
      if (index < 0L) {
         return null;
      } else {
         TruffleString s = getString(thisObj);
         int len = Strings.length(s);
         if (len <= index) {
            return null;
         } else {
            TruffleString resultStr = Strings.substring(JavaScriptLanguage.get(null).getJSContext(), s, (int)index, 1);
            return PropertyDescriptor.createData(resultStr, true, false, false);
         }
      }
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getStringPrototype();
   }

   public static final class StringLengthProxyProperty extends PropertyProxy {
      @Override
      public Object get(JSDynamicObject store) {
         return JSString.getStringLength(store);
      }
   }
}
