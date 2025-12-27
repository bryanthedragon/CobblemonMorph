package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic(ClassElementDefinitionRecord.class)
public abstract class DefineMethodPropertyNode extends Node {
   public static DefineMethodPropertyNode create() {
      return DefineMethodPropertyNodeGen.create();
   }

   public abstract void executeDefine(JSDynamicObject homeObject, ClassElementDefinitionRecord record, boolean enumerable);

   @Specialization(guards = "record.isPrivate()")
   public void doPrivate(JSDynamicObject homeObject, ClassElementDefinitionRecord record, boolean enumerable) {
   }

   @Specialization(guards = {"record.isMethod()", "!record.isPrivate()"})
   public void doMethod(JSDynamicObject homeObject, ClassElementDefinitionRecord record, boolean enumerable) {
      PropertyDescriptor desc = PropertyDescriptor.createData(record.getValue(), enumerable, true, true);
      JSRuntime.definePropertyOrThrow(homeObject, record.getKey(), desc);
   }

   @Specialization(guards = {"record.isGetter()", "!record.isPrivate()"})
   public void doGetter(JSDynamicObject homeObject, ClassElementDefinitionRecord record, boolean enumerable) {
      Object otherAccessor = Undefined.instance;
      PropertyDescriptor ownProperty = JSObject.getOwnProperty(homeObject, record.getKey());
      if (ownProperty != null) {
         otherAccessor = ownProperty.getSet();
      }

      PropertyDescriptor desc = PropertyDescriptor.createAccessor(record.getValue(), otherAccessor, enumerable, true);
      JSRuntime.definePropertyOrThrow(homeObject, record.getKey(), desc);
   }

   @Specialization(guards = {"record.isSetter()", "!record.isPrivate()"})
   public void doSetter(JSDynamicObject homeObject, ClassElementDefinitionRecord record, boolean enumerable) {
      Object otherAccessor = Undefined.instance;
      PropertyDescriptor ownProperty = JSObject.getOwnProperty(homeObject, record.getKey());
      if (ownProperty != null) {
         otherAccessor = ownProperty.getGet();
      }

      PropertyDescriptor desc = PropertyDescriptor.createAccessor(otherAccessor, record.getValue(), enumerable, true);
      JSRuntime.definePropertyOrThrow(homeObject, record.getKey(), desc);
   }

   @Specialization(guards = {"record.isAutoAccessor()", "!record.isPrivate()"})
   public void doAutoAccessor(JSDynamicObject homeObject, ClassElementDefinitionRecord.AutoAccessor record, boolean enumerable) {
      PropertyDescriptor desc = PropertyDescriptor.createAccessor(record.getGetter(), record.getSetter(), enumerable, true);
      JSRuntime.definePropertyOrThrow(homeObject, record.getKey(), desc);
   }
}
