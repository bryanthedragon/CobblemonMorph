package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.LayoutFactory;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import org.graalvm.collections.Pair;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableMapCursor;

public class CoreLayoutFactory implements LayoutFactory {
   @Override
   public final Property createProperty(Object id, Location location) {
      return this.createProperty(id, location, 0);
   }

   @Override
   public final Property createProperty(Object id, Location location, int flags) {
      return new PropertyImpl(id, location, flags);
   }

   @Override
   public int getPriority() {
      return 10;
   }

   protected void resetNativeImageState() {
      DefaultLayout.resetNativeImageState();
   }

   protected void registerLayoutClass(Class<? extends DynamicObject> subclass) {
      DefaultLayout.registerLayoutClass(subclass);
   }

   public LayoutImpl createLayout(Class<? extends DynamicObject> layoutClass, int implicitCastFlags) {
      return DefaultLayout.createCoreLayout(layoutClass, implicitCastFlags);
   }

   @Override
   public Shape createShape(Object builderArgs) {
      Object[] args = (Object[])builderArgs;
      Class<? extends DynamicObject> layoutClass = (Class<? extends DynamicObject>)args[0];
      int implicitCastFlags = (Integer)args[1];
      LayoutImpl impl = this.createLayout(layoutClass, implicitCastFlags);
      Object dynamicType = args[2];
      Object sharedData = args[3];
      int shapeFlags = (Integer)args[4];
      UnmodifiableEconomicMap<Object, Pair<Object, Integer>> constantProperties = (UnmodifiableEconomicMap<Object, Pair<Object, Integer>>)args[5];
      Assumption singleContextAssumption = (Assumption)args[6];
      ShapeImpl shape = impl.newShape(dynamicType, sharedData, shapeFlags, singleContextAssumption);
      if (constantProperties != null) {
         UnmodifiableMapCursor<Object, Pair<Object, Integer>> cursor = constantProperties.getEntries();

         while (cursor.advance()) {
            shape = shape.addProperty(
               Property.create(cursor.getKey(), impl.createAllocator().constantLocation(cursor.getValue().getLeft()), cursor.getValue().getRight())
            );
         }
      }

      return shape;
   }
}
