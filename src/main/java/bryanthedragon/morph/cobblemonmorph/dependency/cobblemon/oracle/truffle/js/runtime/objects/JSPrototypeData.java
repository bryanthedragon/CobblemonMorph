package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Arrays;

public final class JSPrototypeData {
   private static final Shape[] EMPTY_SHAPE_ARRAY = new Shape[0];
   private Shape[] protoChildTrees = EMPTY_SHAPE_ARRAY;
   private static final VarHandle PROTO_CHILD_TREES_VAR_HANDLE;

   private static Shape lookupShapeByType(Shape[] shapes, JSClass jsclass) {
      for (Shape shape : shapes) {
         if (JSShape.getJSClassNoCast(shape) == jsclass) {
            return shape;
         }
      }

      return null;
   }

   public Shape getProtoChildTree(JSClass jsclass) {
      return lookupShapeByType(this.getProtoChildTrees(), jsclass);
   }

   public Shape getOrAddProtoChildTree(JSClass jsclass, Shape newRootShape) {
      CompilerAsserts.neverPartOfCompilation();

      Shape[] oldArray;
      Shape[] newArray;
      do {
         oldArray = this.getProtoChildTrees();
         Shape existingRootShape = lookupShapeByType(oldArray, jsclass);
         if (existingRootShape != null) {
            return existingRootShape;
         }

         newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
         newArray[oldArray.length] = newRootShape;
      } while (!PROTO_CHILD_TREES_VAR_HANDLE.compareAndSet((JSPrototypeData)this, (Shape[])oldArray, (Shape[])newArray));

      return newRootShape;
   }

   private Shape[] getProtoChildTrees() {
      return (Shape[])PROTO_CHILD_TREES_VAR_HANDLE.getVolatile((JSPrototypeData)this);
   }

   static {
      Lookup lookup = MethodHandles.lookup();

      try {
         PROTO_CHILD_TREES_VAR_HANDLE = lookup.findVarHandle(JSPrototypeData.class, "protoChildTrees", Shape[].class);
      } catch (IllegalAccessException | NoSuchFieldException var2) {
         throw Errors.shouldNotReachHere(var2);
      }
   }
}
