package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;

@GenerateUncached
public abstract class KeyInfoNode extends JavaScriptBaseNode {
   public static final int READABLE = 1;
   public static final int MODIFIABLE = 2;
   public static final int INSERTABLE = 4;
   public static final int INVOCABLE = 8;
   public static final int REMOVABLE = 16;
   public static final int READ_SIDE_EFFECTS = 32;
   public static final int WRITE_SIDE_EFFECTS = 64;
   public static final int WRITABLE = 6;

   KeyInfoNode() {
   }

   public abstract boolean execute(JSDynamicObject receiver, String key, int query);

   @Specialization(guards = {"!isJSProxy(target)", "property != null"}, limit = "2")
   static boolean cachedOwnProperty(
      JSDynamicObject target,
      String key,
      int query,
      @CachedLibrary("target") DynamicObjectLibrary objectLibrary,
      @Bind("objectLibrary.getProperty(target, key)") Property property,
      @Cached IsCallableNode isCallable,
      @Cached BranchProfile proxyBranch,
      @Cached TruffleString.FromJavaStringNode fromJavaStringNode
   ) {
      TruffleString tStringKey = Strings.fromJavaString(fromJavaStringNode, key);
      if (JSProperty.isAccessor(property)) {
         Accessor accessor = (Accessor)Properties.getOrDefault(objectLibrary, target, tStringKey, null);
         if ((query & 1) != 0 && accessor.hasGetter()) {
            return true;
         } else if ((query & 2) != 0 && accessor.hasSetter()) {
            return true;
         } else if ((query & 32) != 0 && accessor.hasGetter()) {
            return true;
         } else {
            return (query & 64) != 0 && accessor.hasSetter() ? true : (query & 16) != 0 && JSProperty.isConfigurable(property);
         }
      } else {
         assert JSProperty.isData(property);

         if ((query & 1) != 0) {
            return true;
         } else if ((query & 2) != 0 && JSProperty.isWritable(property)) {
            return true;
         } else {
            if ((query & 8) != 0) {
               Object value = Properties.getOrDefault(objectLibrary, target, tStringKey, Undefined.instance);
               if (JSProperty.isProxy(property)) {
                  proxyBranch.enter();
                  value = ((PropertyProxy)value).get(target);
               }

               if (isCallable.executeBoolean(value)) {
                  return true;
               }
            }

            return (query & 16) != 0 && JSProperty.isConfigurable(property);
         }
      }
   }

   @Specialization(replaces = "cachedOwnProperty")
   static boolean member(
      JSDynamicObject target,
      String key,
      int query,
      @Cached GetPrototypeNode getPrototype,
      @Cached IsCallableNode isCallable,
      @Cached IsExtensibleNode isExtensible,
      @Cached TruffleString.FromJavaStringNode fromJavaStringNode
   ) {
      TruffleString tStringKey = Strings.fromJavaString(fromJavaStringNode, key);
      PropertyDescriptor desc = null;
      boolean isProxy = false;
      JSDynamicObject proto = target;

      while (proto != Null.instance) {
         desc = JSObject.getOwnProperty(proto, tStringKey);
         if (JSProxy.isJSProxy(proto)) {
            isProxy = true;
            break;
         }

         if (desc != null) {
            break;
         }

         proto = getPrototype.execute(proto);
      }

      if (desc == null) {
         return (query & 4) != 0 && isExtensible.executeBoolean(target);
      } else {
         boolean hasGet = desc.hasGet() && desc.getGet() != Undefined.instance;
         boolean hasSet = desc.hasSet() && desc.getSet() != Undefined.instance;
         boolean readable = hasGet || !hasSet;
         boolean writable = hasSet || !hasGet && desc.getIfHasWritable(true);
         boolean readSideEffects = isProxy || hasGet;
         boolean writeSideEffects = isProxy || hasSet;
         if ((query & 1) != 0 && readable) {
            return true;
         } else if ((query & 2) != 0 && writable) {
            return true;
         } else if ((query & 32) != 0 && readSideEffects) {
            return true;
         } else if ((query & 64) != 0 && writeSideEffects) {
            return true;
         } else {
            return (query & 8) != 0 && desc.isDataDescriptor() && isCallable.executeBoolean(desc.getValue())
               ? true
               : (query & 16) != 0 && desc.getConfigurable();
         }
      }
   }
}
