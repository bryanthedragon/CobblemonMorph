package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.util.List;

@ImportStatic(JSConfig.class)
public abstract class EnumerableOwnPropertyNamesNode extends JavaScriptBaseNode {
   private final boolean keys;
   private final boolean values;
   private final JSContext context;
   @Node.Child
   private JSGetOwnPropertyNode getOwnPropertyNode;
   private final ConditionProfile hasFastShapesProfile = ConditionProfile.createBinaryProfile();
   private final BranchProfile growProfile = BranchProfile.create();

   protected EnumerableOwnPropertyNamesNode(JSContext context, boolean keys, boolean values) {
      this.context = context;
      this.keys = keys;
      this.values = values;
   }

   public static EnumerableOwnPropertyNamesNode createKeys(JSContext context) {
      return EnumerableOwnPropertyNamesNodeGen.create(context, true, false);
   }

   public static EnumerableOwnPropertyNamesNode createValues(JSContext context) {
      return EnumerableOwnPropertyNamesNodeGen.create(context, false, true);
   }

   public static EnumerableOwnPropertyNamesNode createKeysValues(JSContext context) {
      return EnumerableOwnPropertyNamesNodeGen.create(context, true, true);
   }

   public abstract UnmodifiableArrayList<? extends Object> execute(Object obj);

   @Specialization
   protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNames(
      JSDynamicObject thisObj,
      @Cached JSClassProfile jsclassProfile,
      @Cached ListSizeNode listSize,
      @Cached ListGetNode listGet,
      @Cached HasOnlyShapePropertiesNode hasOnlyShapeProperties
   ) {
      JSClass jsclass = jsclassProfile.getJSClass(thisObj);
      if (this.hasFastShapesProfile.profile(this.keys && !this.values && hasOnlyShapeProperties.execute(thisObj, jsclass))) {
         return JSShape.getEnumerablePropertyNames(thisObj.getShape());
      } else {
         boolean isProxy = JSProxy.isJSProxy(thisObj);
         List<Object> ownKeys = jsclass.ownPropertyKeys(thisObj);
         int ownKeysSize = listSize.execute(ownKeys);
         SimpleArrayList<Object> properties = new SimpleArrayList<>();

         for (int i = 0; i < ownKeysSize; i++) {
            Object key = listGet.execute(ownKeys, i);
            if (Strings.isTString(key)) {
               PropertyDescriptor desc = this.getOwnProperty(thisObj, key);
               if (desc != null && desc.getEnumerable()) {
                  Object element;
                  if (this.keys && !this.values) {
                     element = key;
                  } else {
                     Object value = !desc.isAccessorDescriptor() && !isProxy ? desc.getValue() : jsclass.get(thisObj, key);
                     if (!this.keys && this.values) {
                        element = value;
                     } else {
                        assert this.keys && this.values;

                        element = this.createKeyValuePair(key, value);
                     }
                  }

                  properties.add(element, this.growProfile);
               }
            }
         }

         return new UnmodifiableArrayList<>(properties.toArray());
      }
   }

   private Object createKeyValuePair(Object key, Object value) {
      return JSArray.createConstant(this.context, this.getRealm(), new Object[]{key, value});
   }

   protected PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      if (this.getOwnPropertyNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getOwnPropertyNode = this.insert(JSGetOwnPropertyNode.create(this.values, true, false, false, false));
      }

      return this.getOwnPropertyNode.execute(thisObj, key);
   }

   @Specialization(guards = "isForeignObject(obj)", limit = "InteropLibraryLimit")
   protected UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNamesForeign(
      Object obj,
      @CachedLibrary("obj") InteropLibrary interop,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary members,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary asString,
      @Cached ImportValueNode importValue,
      @Cached BranchProfile errorBranch
   ) {
      try {
         long arraySize = 0L;
         if (interop.hasArrayElements(obj)) {
            arraySize = interop.getArraySize(obj);
         }

         Object keysObj = null;
         long memberCount = 0L;
         if (interop.hasMembers(obj)) {
            keysObj = interop.getMembers(obj);
            memberCount = members.getArraySize(keysObj);
         }

         long size = arraySize + memberCount;
         if (arraySize < 0L || memberCount < 0L || size < 0L || size >= 2147483647L) {
            errorBranch.enter();
            throw Errors.createRangeErrorInvalidArrayLength();
         }

         if (size > 0L) {
            SimpleArrayList<Object> list = new SimpleArrayList<>((int)size);

            for (long i = 0L; i < arraySize; i++) {
               TruffleString key = Strings.fromLong(i);
               Object element;
               if (this.values) {
                  Object value = importValue.executeWithTarget(interop.readArrayElement(obj, i));
                  if (this.keys) {
                     element = this.createKeyValuePair(key, value);
                  } else {
                     element = value;
                  }
               } else {
                  element = key;
               }

               list.addUnchecked(element);
            }

            for (int i = 0; i < memberCount; i++) {
               Object objectKey = members.readArrayElement(keysObj, i);

               assert InteropLibrary.getUncached().isString(objectKey);

               TruffleString key = Strings.interopAsTruffleString(asString, objectKey);
               Object element;
               if (this.values) {
                  String javaStringKey = Strings.toJavaString(key);
                  Object value = importValue.executeWithTarget(interop.readMember(obj, javaStringKey));
                  if (this.keys) {
                     element = this.createKeyValuePair(key, value);
                  } else {
                     element = value;
                  }
               } else {
                  element = key;
               }

               list.addUnchecked(element);
            }

            return new UnmodifiableArrayList<>(list.toArray());
         }
      } catch (InvalidArrayIndexException | UnknownIdentifierException | UnsupportedMessageException var21) {
      }

      return new UnmodifiableArrayList<>(ScriptArray.EMPTY_OBJECT_ARRAY);
   }
}
