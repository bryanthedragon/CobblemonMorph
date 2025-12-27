package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LocationFactory;
import com.oracle.truffle.api.object.Property;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class LayoutStrategy {
   protected LayoutStrategy() {
   }

   protected abstract int getLocationOrdinal(Location location);

   protected abstract boolean updateShape(DynamicObject object);

   protected abstract ShapeImpl ensureValid(ShapeImpl newShape);

   protected abstract ShapeImpl ensureSpace(ShapeImpl shape, Location location);

   public abstract ShapeImpl.BaseAllocator createAllocator(LayoutImpl shape);

   public abstract ShapeImpl.BaseAllocator createAllocator(ShapeImpl shape);

   protected ShapeImpl defineProperty(ShapeImpl shape, Object key, Object value, int flags, LocationFactory locationFactory) {
      return this.defineProperty(shape, key, value, flags, locationFactory, 0L);
   }

   protected ShapeImpl defineProperty(ShapeImpl shape, Object key, Object value, int flags, LocationFactory locationFactory, long putFlags) {
      ShapeImpl oldShape = shape;
      if (!shape.isValid()) {
         oldShape = this.ensureValid(shape);
      }

      Property existing = oldShape.getProperty(key);
      return this.defineProperty(oldShape, key, value, flags, locationFactory, existing, putFlags);
   }

   protected ShapeImpl defineProperty(
      ShapeImpl oldShape, Object key, Object value, int propertyFlags, LocationFactory locationFactory, Property existing, long putFlags
   ) {
      if (existing == null) {
         return Flags.isSeparateShape(putFlags)
            ? this.definePropertySeparateShape(oldShape, key, value, propertyFlags, putFlags, locationFactory)
            : this.defineNewProperty(oldShape, key, value, propertyFlags, putFlags, locationFactory);
      } else if (existing.getFlags() == propertyFlags) {
         return existing.getLocation().canStore(value) ? oldShape : this.definePropertyGeneralize(oldShape, existing, value, locationFactory, putFlags);
      } else {
         return this.definePropertyChangeFlags(oldShape, existing, value, propertyFlags, putFlags);
      }
   }

   private ShapeImpl defineNewProperty(ShapeImpl oldShape, Object key, Object value, int propertyFlags, long putFlags, LocationFactory locationFactory) {
      if (!Flags.isConstant(putFlags) && !Flags.isDeclaration(putFlags) && locationFactory == null) {
         Class<?> locationType = this.detectLocationType(value);
         if (locationType != null) {
            Transition.AddPropertyTransition addTransition = new Transition.AddPropertyTransition(key, propertyFlags, locationType);
            ShapeImpl cachedShape = oldShape.queryTransition(addTransition);
            if (cachedShape != null) {
               return this.ensureValid(cachedShape);
            }
         }
      }

      Location location = this.createLocationForValue(oldShape, value, putFlags, locationFactory);
      Property property = Property.create(key, location, propertyFlags);
      return this.addProperty(oldShape, property);
   }

   protected Class<?> detectLocationType(Object value) {
      if (value instanceof Integer) {
         return int.class;
      } else if (value instanceof Double) {
         return double.class;
      } else if (value instanceof Long) {
         return long.class;
      } else {
         return value instanceof Boolean ? boolean.class : Object.class;
      }
   }

   private Location createLocationForValue(ShapeImpl oldShape, Object value, long putFlags, LocationFactory locationFactory) {
      return locationFactory != null ? locationFactory.createLocation(oldShape, value) : this.createLocationForValue(oldShape, value, putFlags);
   }

   protected abstract Location createLocationForValue(ShapeImpl shape, Object value, long putFlags);

   protected ShapeImpl definePropertyChangeFlags(ShapeImpl oldShape, Property existing, Object value, int propertyFlags, long putFlags) {
      assert existing.getFlags() != propertyFlags;

      oldShape.onPropertyTransition(existing);
      if (existing.getLocation().canStore(value)) {
         Property newProperty = Property.create(existing.getKey(), existing.getLocation(), propertyFlags);
         return this.replaceProperty(oldShape, existing, newProperty);
      } else {
         return this.generalizePropertyWithFlags(oldShape, existing, value, propertyFlags, putFlags);
      }
   }

   protected ShapeImpl definePropertyGeneralize(ShapeImpl oldShape, Property oldProperty, Object value, LocationFactory locationFactory, long putFlags) {
      oldShape.onPropertyTransition(oldProperty);
      if (Flags.isSeparateShape(putFlags)) {
         Location newLocation = this.createLocationForValue(oldShape, value, putFlags, locationFactory);
         Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
         return this.separateReplaceProperty(oldShape, oldProperty, newProperty);
      } else if (oldProperty.getLocation().isValue()) {
         Location newLocation = this.createLocationForValue(oldShape, value, putFlags, locationFactory);
         Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
         return this.directReplaceProperty(oldShape, oldProperty, newProperty);
      } else {
         return this.generalizeProperty(oldProperty, value, oldShape, oldShape, putFlags);
      }
   }

   protected ShapeImpl generalizeProperty(Property oldProperty, Object value, ShapeImpl currentShape, ShapeImpl nextShape, long putFlags) {
      Location oldLocation = oldProperty.getLocation();
      Location newLocation = currentShape.allocator().locationForValueUpcast(value, oldLocation, putFlags);
      Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
      nextShape.onPropertyTransition(oldProperty);
      return this.replaceProperty(nextShape, oldProperty, newProperty);
   }

   protected ShapeImpl generalizePropertyWithFlags(ShapeImpl currentShape, Property oldProperty, Object value, int propertyFlags, long putFlags) {
      assert !oldProperty.getLocation().canStore(value);

      Location newLocation = currentShape.allocator().locationForValueUpcast(value, oldProperty.getLocation(), putFlags);
      Property newProperty = Property.create(oldProperty.getKey(), newLocation, propertyFlags);
      return this.replaceProperty(currentShape, oldProperty, newProperty);
   }

   protected void propertySetFallback(Property property, DynamicObject store, Object value, ShapeImpl currentShape) {
      ShapeImpl oldShape = currentShape;
      ShapeImpl newShape = this.defineProperty(currentShape, property.getKey(), value, property.getFlags(), null, 0L);
      Property newProperty = newShape.getProperty(property.getKey());

      assert store.getShape() == currentShape;

      try {
         ((LocationImpl)newProperty.getLocation()).set(store, value, oldShape, newShape);
      } catch (IncompatibleLocationException var9) {
         throw CompilerDirectives.shouldNotReachHere(var9);
      }
   }

   private ShapeImpl definePropertySeparateShape(
      ShapeImpl oldShape, Object key, Object value, int propertyFlags, long putFlags, LocationFactory locationFactory
   ) {
      Location location = this.createLocationForValue(oldShape, value, putFlags, locationFactory);
      Property property = Property.create(key, location, propertyFlags);
      return this.createSeparateShape(oldShape).addProperty(property);
   }

   protected ShapeImpl replaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty) {
      return this.directReplaceProperty(shape, oldProperty, newProperty);
   }

   protected ShapeImpl removeProperty(ShapeImpl shape, Property property) {
      boolean direct = shape.isShared();
      Transition.RemovePropertyTransition transition = this.newRemovePropertyTransition(property, direct);
      ShapeImpl cachedShape = shape.queryTransition(transition);
      if (cachedShape != null) {
         return this.ensureValid(cachedShape);
      } else {
         return direct ? directRemoveProperty(shape, property, transition) : this.indirectRemoveProperty(shape, property, transition);
      }
   }

   protected Transition.RemovePropertyTransition newRemovePropertyTransition(Property property, boolean direct) {
      return new Transition.RemovePropertyTransition(property, this.toLocationOrType(property.getLocation()), direct);
   }

   private ShapeImpl indirectRemoveProperty(ShapeImpl shape, Property property, Transition.RemovePropertyTransition transition) {
      ShapeImpl owningShape = getShapeFromProperty(shape, property.getKey());
      if (owningShape == null) {
         return null;
      } else {
         List<Transition> transitionList = new ArrayList<>();

         for (ShapeImpl current = shape; current != owningShape; current = current.parent) {
            Transition transitionFromParent = current.getTransitionFromParent();
            if (!(transitionFromParent instanceof Transition.DirectReplacePropertyTransition)
               || !((Transition.DirectReplacePropertyTransition)transitionFromParent).getPropertyBefore().getKey().equals(property.getKey())) {
               transitionList.add(transitionFromParent);
            }
         }

         ShapeImpl newShape = owningShape.parent;
         ListIterator<Transition> iterator = transitionList.listIterator(transitionList.size());

         while (iterator.hasPrevious()) {
            Transition previous = iterator.previous();
            newShape = this.applyTransition(newShape, previous, true);
         }

         shape.addIndirectTransition(transition, newShape);
         return newShape;
      }
   }

   private static ShapeImpl directRemoveProperty(ShapeImpl shape, Property property, Transition.RemovePropertyTransition transition) {
      PropertyMap newPropertyMap = shape.getPropertyMap().removeCopy(property);
      ShapeImpl newShape = shape.createShape(
         shape.getLayout(), shape.sharedData, shape, shape.objectType, newPropertyMap, transition, shape.allocator(), shape.flags
      );
      shape.addDirectTransition(transition, newShape);
      return newShape;
   }

   protected ShapeImpl directReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty) {
      return this.directReplaceProperty(shape, oldProperty, newProperty, true);
   }

   protected ShapeImpl directReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty, boolean ensureValid) {
      assert oldProperty.getKey().equals(newProperty.getKey());

      if (oldProperty.equals(newProperty)) {
         return shape;
      } else {
         shape.onPropertyTransition(oldProperty);
         Transition replacePropertyTransition = new Transition.DirectReplacePropertyTransition(oldProperty, newProperty);
         ShapeImpl cachedShape = shape.queryTransition(replacePropertyTransition);
         if (cachedShape != null) {
            return ensureValid ? this.ensureValid(cachedShape) : cachedShape;
         } else {
            PropertyMap newPropertyMap = shape.getPropertyMap().replaceCopy(oldProperty, newProperty);
            ShapeImpl.BaseAllocator allocator = shape.allocator().addLocation(newProperty.getLocation());
            ShapeImpl newShape = shape.createShape(
               shape.getLayout(), shape.sharedData, shape, shape.objectType, newPropertyMap, replacePropertyTransition, allocator, shape.flags
            );

            assert ((PropertyImpl)newProperty).isSame(newShape.getProperty(newProperty.getKey())) : newShape.getProperty(newProperty.getKey());

            shape.addDirectTransition(replacePropertyTransition, newShape);
            if (!shape.isValid()) {
               newShape.invalidateValidAssumption();
               return ensureValid ? this.ensureValid(newShape) : newShape;
            } else {
               return newShape;
            }
         }
      }
   }

   protected ShapeImpl separateReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty) {
      ShapeImpl newRoot = shape.createShape(
         shape.getLayout(), shape.sharedData, null, shape.objectType, PropertyMap.empty(), null, shape.getLayout().createAllocator(), shape.flags
      );
      ShapeImpl newShape = newRoot;
      boolean found = false;
      Iterator<Property> iterator = shape.getPropertyMap().orderedValueIterator();

      while (iterator.hasNext()) {
         Property p = iterator.next();
         if (!found && p.equals(oldProperty)) {
            found = true;
         }

         newShape = newShape.addProperty(newProperty);
      }

      assert found;

      assert newShape.isValid();

      return newShape;
   }

   protected ShapeImpl createSeparateShape(ShapeImpl shape) {
      ShapeImpl newRoot = shape.createShape(
         shape.getLayout(), shape.sharedData, null, shape.objectType, PropertyMap.empty(), null, shape.getLayout().createAllocator(), shape.flags
      );
      ShapeImpl newShape = newRoot;
      Iterator<Property> iterator = shape.getPropertyMap().orderedValueIterator();

      while (iterator.hasNext()) {
         Property p = iterator.next();
         newShape = newShape.addProperty(p);
      }

      assert newShape.isValid();

      return newShape;
   }

   protected ShapeImpl addProperty(ShapeImpl shape, Property property) {
      return this.addProperty(shape, property, true);
   }

   protected ShapeImpl addProperty(ShapeImpl shape, Property property, boolean ensureValid) {
      assert !shape.hasProperty(property.getKey()) : "duplicate property " + property.getKey();

      shape.onPropertyTransition(property);
      Transition.AddPropertyTransition addTransition = this.newAddPropertyTransition(property);
      ShapeImpl cachedShape = shape.queryTransition(addTransition);
      if (cachedShape != null) {
         return ensureValid ? this.ensureValid(cachedShape) : cachedShape;
      } else {
         ShapeImpl oldShape = this.ensureSpace(shape, property.getLocation());
         ShapeImpl newShape = ShapeImpl.makeShapeWithAddedProperty(oldShape, addTransition);
         oldShape.addDirectTransition(addTransition, newShape);
         if (!oldShape.isValid()) {
            newShape.invalidateValidAssumption();
            return ensureValid ? this.ensureValid(newShape) : newShape;
         } else {
            return newShape;
         }
      }
   }

   protected Transition.AddPropertyTransition newAddPropertyTransition(Property property) {
      return new Transition.AddPropertyTransition(property, this.toLocationOrType(property.getLocation()));
   }

   protected Object toLocationOrType(Location location) {
      if (location instanceof LocationImpl) {
         Class<?> type = ((LocationImpl)location).getType();
         if (type != null) {
            return type;
         }
      }

      return location;
   }

   protected ShapeImpl applyTransition(ShapeImpl shape, Transition transition, boolean append) {
      if (transition instanceof Transition.AddPropertyTransition) {
         Property property = ((Transition.AddPropertyTransition)transition).getProperty();
         ShapeImpl newShape;
         if (append) {
            Property newProperty = ((PropertyImpl)property).relocate(shape.allocator().moveLocation(property.getLocation()));
            newShape = this.addProperty(shape, newProperty, true);
         } else {
            newShape = this.addProperty(shape, property, false);
         }

         return newShape;
      } else if (transition instanceof Transition.ObjectTypeTransition) {
         return shape.setDynamicType(((Transition.ObjectTypeTransition)transition).getObjectType());
      } else if (transition instanceof Transition.ObjectFlagsTransition) {
         return shape.setFlags(((Transition.ObjectFlagsTransition)transition).getObjectFlags());
      } else if (transition instanceof Transition.DirectReplacePropertyTransition) {
         Property oldProperty = ((Transition.DirectReplacePropertyTransition)transition).getPropertyBefore();
         Property newProperty = ((Transition.DirectReplacePropertyTransition)transition).getPropertyAfter();
         if (append) {
            boolean sameLocation = oldProperty.getLocation().equals(newProperty.getLocation());
            oldProperty = shape.getProperty(oldProperty.getKey());
            Location newLocation;
            if (sameLocation) {
               newLocation = oldProperty.getLocation();
            } else {
               newLocation = shape.allocator().moveLocation(newProperty.getLocation());
            }

            newProperty = ((PropertyImpl)newProperty).relocate(newLocation);
         }

         return this.directReplaceProperty(shape, oldProperty, newProperty, append);
      } else {
         throw new UnsupportedOperationException(transition.getClass().getName());
      }
   }

   protected static ShapeImpl getShapeFromProperty(ShapeImpl shape, Object propertyName) {
      ShapeImpl current = shape;

      for (ShapeImpl root = shape.getRoot(); current != root; current = current.getParent()) {
         if (current.getTransitionFromParent() instanceof Transition.AddPropertyTransition
            && ((Transition.AddPropertyTransition)current.getTransitionFromParent()).getPropertyKey().equals(propertyName)) {
            return current;
         }
      }

      return null;
   }
}
