package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JSModuleNamespace extends JSNonProxy {
   public static final JSModuleNamespace INSTANCE = new JSModuleNamespace();
   public static final TruffleString CLASS_NAME = Strings.constant("Module");

   private JSModuleNamespace() {
   }

   public static JSModuleRecord getModule(JSDynamicObject obj) {
      assert isJSModuleNamespace(obj);

      return ((JSModuleNamespaceObject)obj).getModule();
   }

   public static Map<TruffleString, ExportResolution> getExports(JSDynamicObject obj) {
      assert isJSModuleNamespace(obj);

      return ((JSModuleNamespaceObject)obj).getExports();
   }

   public static JSModuleNamespaceObject create(JSContext context, JSRealm realm, JSModuleRecord module, Map<TruffleString, ExportResolution> exports) {
      JSObjectFactory factory = context.getModuleNamespaceFactory();
      JSModuleNamespaceObject obj = JSModuleNamespaceObject.create(realm, factory, module, exports);

      assert !JSObject.isExtensible(obj);

      return context.trackAllocation(obj);
   }

   public static Shape makeInitialShape(JSContext context) {
      Shape initialShape = JSShape.newBuilder(context, INSTANCE, Null.instance).shapeFlags(1).build();
      initialShape = Shape.newBuilder(initialShape)
         .addConstantProperty(JSObject.HIDDEN_PROTO, Null.instance, 0)
         .addConstantProperty(Symbol.SYMBOL_TO_STRING_TAG, CLASS_NAME, JSAttributes.notConfigurableNotEnumerableNotWritable())
         .build();

      assert !JSShape.isExtensible(initialShape);

      return initialShape;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return Strings.addBrackets(CLASS_NAME);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      if (!Strings.isTString(key)) {
         return super.getOwnHelper(store, thisObj, key, encapsulatingNode);
      } else {
         Map<TruffleString, ExportResolution> exports = getExports(store);
         ExportResolution binding = exports.get(key);
         return binding != null ? getBindingValue(binding) : null;
      }
   }

   static Object getBindingValue(ExportResolution binding) {
      TruffleString bindingName = binding.getBindingName();
      JSModuleRecord targetModule = binding.getModule();
      MaterializedFrame targetEnv = targetModule.getEnvironment();
      if (targetEnv == null) {
         throw Errors.createReferenceErrorNotDefined(bindingName, null);
      } else if (binding.isNamespace()) {
         return targetModule.getContext().getEvaluator().getModuleNamespace(targetModule);
      } else {
         FrameDescriptor targetEnvDesc = targetEnv.getFrameDescriptor();
         int slot = JSFrameUtil.findRequiredFrameSlotIndex(targetEnvDesc, bindingName);
         if (JSFrameUtil.hasTemporalDeadZone(targetEnvDesc, slot) && targetEnv.getTag(slot) == FrameSlotKind.Illegal.tag) {
            throw Errors.createReferenceErrorNotDefined(bindingName, null);
         } else {
            return targetEnv.getValue(slot);
         }
      }
   }

   @Override
   public boolean hasProperty(JSDynamicObject thisObj, Object key) {
      if (!Strings.isTString(key)) {
         return super.hasProperty(thisObj, key);
      } else {
         Map<TruffleString, ExportResolution> exports = getExports(thisObj);
         return Boundaries.mapContainsKey(exports, (TruffleString)key);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      if (!Strings.isTString(key)) {
         return super.hasOwnProperty(thisObj, key);
      } else {
         Map<TruffleString, ExportResolution> exports = getExports(thisObj);
         ExportResolution binding = exports.get(key);
         if (binding != null) {
            getBindingValue(binding);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      return true;
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      if (!Strings.isTString(key)) {
         return super.delete(thisObj, key, isStrict);
      } else if (Boundaries.mapContainsKey(getExports(thisObj), (TruffleString)key)) {
         if (isStrict) {
            throw Errors.createTypeErrorNotConfigurableProperty(key);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Override
   public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      return newPrototype == Null.instance;
   }

   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      if (!Strings.isTString(key)) {
         return super.defineOwnProperty(thisObj, key, desc, doThrow);
      } else {
         PropertyDescriptor current = this.getOwnProperty(thisObj, key);
         return current == null
               || desc.isAccessorDescriptor()
               || !desc.getIfHasWritable(true)
               || !desc.getIfHasEnumerable(true)
               || desc.getIfHasConfigurable(false)
               || desc.hasValue() && !JSRuntime.isSameValue(desc.getValue(), current.getValue())
            ? DefinePropertyUtil.reject(doThrow, "not allowed to defineProperty on a namespace object")
            : true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      if (!Strings.isTString(key)) {
         return super.getOwnProperty(thisObj, key);
      } else {
         Map<TruffleString, ExportResolution> exports = getExports(thisObj);
         ExportResolution binding = exports.get(key);
         if (binding != null) {
            Object value = getBindingValue(binding);
            return PropertyDescriptor.createData(value, true, true, false);
         } else {
            return null;
         }
      }
   }

   public static boolean isJSModuleNamespace(Object obj) {
      return obj instanceof JSModuleNamespaceObject;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      List<Object> symbolKeys = symbols ? symbolKeys(thisObj) : Collections.emptyList();
      if (!strings) {
         return symbolKeys;
      } else {
         Map<TruffleString, ExportResolution> exports = getExports(thisObj);
         List<Object> keys = new ArrayList<>(exports.size() + symbolKeys.size());
         keys.addAll(exports.keySet());
         keys.addAll(symbolKeys);
         return keys;
      }
   }

   private static List<Object> symbolKeys(JSDynamicObject thisObj) {
      return thisObj.getShape().getKeyList();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze, boolean doThrow) {
      if (freeze) {
         Map<TruffleString, ExportResolution> exports = getExports(obj);
         if (!exports.isEmpty()) {
            ExportResolution firstBinding = exports.values().iterator().next();
            getBindingValue(firstBinding);
            throw Errors.createTypeError("not allowed to freeze a namespace object");
         }
      } else {
         for (ExportResolution binding : getExports(obj).values()) {
            getBindingValue(binding);
         }
      }

      return true;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (isStrict) {
         throw Errors.createTypeErrorNotExtensible(thisObj, key);
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (isStrict) {
         throw Errors.createTypeErrorNotExtensible(thisObj, Strings.fromLong(index));
      } else {
         return false;
      }
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }
}
