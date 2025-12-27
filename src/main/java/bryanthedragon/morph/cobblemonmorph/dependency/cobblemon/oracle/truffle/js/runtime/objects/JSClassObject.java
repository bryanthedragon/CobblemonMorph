package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import java.util.List;

public abstract class JSClassObject extends JSObject {
   protected JSClassObject(Shape shape) {
      super(shape);
   }

   @Override
   public TruffleString getClassName() {
      return this.getJSClass().getClassName(this);
   }

   @Override
   public JSDynamicObject getPrototypeOf() {
      return this.getJSClass().getPrototypeOf(this);
   }

   @Override
   public boolean setPrototypeOf(JSDynamicObject newPrototype) {
      return this.getJSClass().setPrototypeOf(this, newPrototype);
   }

   @Override
   public boolean isExtensible() {
      return this.getJSClass().isExtensible(this);
   }

   @Override
   public boolean preventExtensions(boolean doThrow) {
      return this.getJSClass().preventExtensions(this, doThrow);
   }

   @Override
   public PropertyDescriptor getOwnProperty(Object propertyKey) {
      return this.getJSClass().getOwnProperty(this, propertyKey);
   }

   @Override
   public boolean defineOwnProperty(Object key, PropertyDescriptor value, boolean doThrow) {
      return this.getJSClass().defineOwnProperty(this, key, value, doThrow);
   }

   @Override
   public boolean hasProperty(Object key) {
      return this.getJSClass().hasProperty(this, key);
   }

   @Override
   public boolean hasProperty(long index) {
      return this.getJSClass().hasProperty(this, index);
   }

   @Override
   public boolean hasOwnProperty(Object key) {
      return this.getJSClass().hasOwnProperty(this, key);
   }

   @Override
   public boolean hasOwnProperty(long index) {
      return this.getJSClass().hasOwnProperty(this, index);
   }

   @Override
   public Object getHelper(Object receiver, Object key, Node encapsulatingNode) {
      return this.getJSClass().getHelper(this, receiver, key, encapsulatingNode);
   }

   @Override
   public Object getHelper(Object receiver, long index, Node encapsulatingNode) {
      return this.getJSClass().getHelper(this, receiver, index, encapsulatingNode);
   }

   @Override
   public Object getOwnHelper(Object receiver, Object key, Node encapsulatingNode) {
      return this.getJSClass().getOwnHelper(this, receiver, key, encapsulatingNode);
   }

   @Override
   public Object getOwnHelper(Object receiver, long index, Node encapsulatingNode) {
      return this.getJSClass().getOwnHelper(this, receiver, index, encapsulatingNode);
   }

   @Override
   public Object getMethodHelper(Object receiver, Object key, Node encapsulatingNode) {
      return this.getJSClass().getMethodHelper(this, receiver, key, encapsulatingNode);
   }

   @Override
   public boolean set(Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return this.getJSClass().set(this, key, value, receiver, isStrict, encapsulatingNode);
   }

   @Override
   public boolean set(long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return this.getJSClass().set(this, index, value, receiver, isStrict, encapsulatingNode);
   }

   @Override
   public boolean delete(Object key, boolean isStrict) {
      return this.getJSClass().delete(this, key, isStrict);
   }

   @Override
   public boolean delete(long index, boolean isStrict) {
      return this.getJSClass().delete(this, index, isStrict);
   }

   @Override
   public List<Object> getOwnPropertyKeys(boolean strings, boolean symbols) {
      return this.getJSClass().getOwnPropertyKeys(this, strings, symbols);
   }

   @Override
   public boolean hasOnlyShapeProperties() {
      return this.getJSClass().hasOnlyShapeProperties(this);
   }

   @Override
   public TruffleString toDisplayStringImpl(boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return this.getJSClass().toDisplayStringImpl(this, allowSideEffects, format, depth);
   }

   @Override
   public TruffleString getBuiltinToStringTag() {
      return this.getJSClass().getBuiltinToStringTag(this);
   }

   @Override
   public boolean setIntegrityLevel(boolean freeze, boolean doThrow) {
      return this.getJSClass().setIntegrityLevel(this, freeze, doThrow);
   }

   @Override
   public boolean testIntegrityLevel(boolean frozen) {
      return this.getJSClass().testIntegrityLevel(this, frozen);
   }
}
