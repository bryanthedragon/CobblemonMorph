package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Shape;

public final class ShapeBasic extends ShapeImpl {
   ShapeBasic(Layout layout, Object sharedData, Object objectType, int flags, Assumption singleContextAssumption) {
      super(layout, objectType, sharedData, flags, singleContextAssumption);
   }

   ShapeBasic(
      Layout layout,
      Object sharedData,
      ShapeImpl parent,
      Object objectType,
      PropertyMap propertyMap,
      Transition transition,
      Shape.Allocator allocator,
      int flags
   ) {
      super(layout, parent, objectType, sharedData, propertyMap, transition, allocator, flags);
   }

   @Override
   protected ShapeImpl createShape(
      Layout layout,
      Object sharedData,
      ShapeImpl parent,
      Object objectType,
      PropertyMap propertyMap,
      Transition transition,
      Shape.Allocator allocator,
      int flags
   ) {
      return new ShapeBasic(layout, sharedData, parent, objectType, propertyMap, transition, allocator, flags);
   }
}
