
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LocationFactory;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.object.Flags;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.PropertyImpl;
import com.oracle.truffle.object.PropertyMap;
import com.oracle.truffle.object.ShapeImpl;
import com.oracle.truffle.object.Transition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public abstract class LayoutStrategy {
    protected LayoutStrategy() {
    }

    protected abstract int getLocationOrdinal(Location var1);

    protected abstract boolean updateShape(DynamicObject var1);

    protected abstract ShapeImpl ensureValid(ShapeImpl var1);

    protected abstract ShapeImpl ensureSpace(ShapeImpl var1, Location var2);

    public abstract ShapeImpl.BaseAllocator createAllocator(LayoutImpl var1);

    public abstract ShapeImpl.BaseAllocator createAllocator(ShapeImpl var1);

    protected ShapeImpl defineProperty(ShapeImpl shape, Object key, Object value2, int flags, LocationFactory locationFactory) {
        return this.defineProperty(shape, key, value2, flags, locationFactory, 0L);
    }

    protected ShapeImpl defineProperty(ShapeImpl shape, Object key, Object value2, int flags, LocationFactory locationFactory, long putFlags) {
        ShapeImpl oldShape = shape;
        if (!oldShape.isValid()) {
            oldShape = this.ensureValid(oldShape);
        }
        Property existing = oldShape.getProperty(key);
        return this.defineProperty(oldShape, key, value2, flags, locationFactory, existing, putFlags);
    }

    protected ShapeImpl defineProperty(ShapeImpl oldShape, Object key, Object value2, int propertyFlags, LocationFactory locationFactory, Property existing, long putFlags) {
        if (existing == null) {
            if (Flags.isSeparateShape(putFlags)) {
                return this.definePropertySeparateShape(oldShape, key, value2, propertyFlags, putFlags, locationFactory);
            }
            return this.defineNewProperty(oldShape, key, value2, propertyFlags, putFlags, locationFactory);
        }
        if (existing.getFlags() == propertyFlags) {
            if (existing.getLocation().canStore(value2)) {
                return oldShape;
            }
            return this.definePropertyGeneralize(oldShape, existing, value2, locationFactory, putFlags);
        }
        return this.definePropertyChangeFlags(oldShape, existing, value2, propertyFlags, putFlags);
    }

    private ShapeImpl defineNewProperty(ShapeImpl oldShape, Object key, Object value2, int propertyFlags, long putFlags, LocationFactory locationFactory) {
        Transition.AddPropertyTransition addTransition;
        ShapeImpl cachedShape;
        Class<?> locationType;
        if (!Flags.isConstant(putFlags) && !Flags.isDeclaration(putFlags) && locationFactory == null && (locationType = this.detectLocationType(value2)) != null && (cachedShape = oldShape.queryTransition(addTransition = new Transition.AddPropertyTransition(key, propertyFlags, locationType))) != null) {
            return this.ensureValid(cachedShape);
        }
        Location location = this.createLocationForValue(oldShape, value2, putFlags, locationFactory);
        Property property = Property.create(key, location, propertyFlags);
        return this.addProperty(oldShape, property);
    }

    protected Class<?> detectLocationType(Object value2) {
        if (value2 instanceof Integer) {
            return Integer.TYPE;
        }
        if (value2 instanceof Double) {
            return Double.TYPE;
        }
        if (value2 instanceof Long) {
            return Long.TYPE;
        }
        if (value2 instanceof Boolean) {
            return Boolean.TYPE;
        }
        return Object.class;
    }

    private Location createLocationForValue(ShapeImpl oldShape, Object value2, long putFlags, LocationFactory locationFactory) {
        if (locationFactory != null) {
            return locationFactory.createLocation(oldShape, value2);
        }
        return this.createLocationForValue(oldShape, value2, putFlags);
    }

    protected abstract Location createLocationForValue(ShapeImpl var1, Object var2, long var3);

    protected ShapeImpl definePropertyChangeFlags(ShapeImpl oldShape, Property existing, Object value2, int propertyFlags, long putFlags) {
        assert (existing.getFlags() != propertyFlags);
        oldShape.onPropertyTransition(existing);
        if (existing.getLocation().canStore(value2)) {
            Property newProperty = Property.create(existing.getKey(), existing.getLocation(), propertyFlags);
            return this.replaceProperty(oldShape, existing, newProperty);
        }
        return this.generalizePropertyWithFlags(oldShape, existing, value2, propertyFlags, putFlags);
    }

    protected ShapeImpl definePropertyGeneralize(ShapeImpl oldShape, Property oldProperty, Object value2, LocationFactory locationFactory, long putFlags) {
        oldShape.onPropertyTransition(oldProperty);
        if (Flags.isSeparateShape(putFlags)) {
            Location newLocation = this.createLocationForValue(oldShape, value2, putFlags, locationFactory);
            Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
            return this.separateReplaceProperty(oldShape, oldProperty, newProperty);
        }
        if (oldProperty.getLocation().isValue()) {
            Location newLocation = this.createLocationForValue(oldShape, value2, putFlags, locationFactory);
            Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
            return this.directReplaceProperty(oldShape, oldProperty, newProperty);
        }
        return this.generalizeProperty(oldProperty, value2, oldShape, oldShape, putFlags);
    }

    protected ShapeImpl generalizeProperty(Property oldProperty, Object value2, ShapeImpl currentShape, ShapeImpl nextShape, long putFlags) {
        Location oldLocation = oldProperty.getLocation();
        Location newLocation = currentShape.allocator().locationForValueUpcast(value2, oldLocation, putFlags);
        Property newProperty = ((PropertyImpl)oldProperty).relocate(newLocation);
        nextShape.onPropertyTransition(oldProperty);
        return this.replaceProperty(nextShape, oldProperty, newProperty);
    }

    protected ShapeImpl generalizePropertyWithFlags(ShapeImpl currentShape, Property oldProperty, Object value2, int propertyFlags, long putFlags) {
        assert (!oldProperty.getLocation().canStore(value2));
        Location newLocation = currentShape.allocator().locationForValueUpcast(value2, oldProperty.getLocation(), putFlags);
        Property newProperty = Property.create(oldProperty.getKey(), newLocation, propertyFlags);
        return this.replaceProperty(currentShape, oldProperty, newProperty);
    }

    protected void propertySetFallback(Property property, DynamicObject store, Object value2, ShapeImpl currentShape) {
        ShapeImpl oldShape = currentShape;
        ShapeImpl newShape = this.defineProperty(oldShape, property.getKey(), value2, property.getFlags(), null, 0L);
        Property newProperty = newShape.getProperty(property.getKey());
        assert (store.getShape() == oldShape);
        try {
            ((LocationImpl)newProperty.getLocation()).set(store, value2, oldShape, newShape);
        }
        catch (IncompatibleLocationException ex) {
            throw CompilerDirectives.shouldNotReachHere(ex);
        }
    }

    private ShapeImpl definePropertySeparateShape(ShapeImpl oldShape, Object key, Object value2, int propertyFlags, long putFlags, LocationFactory locationFactory) {
        Location location = this.createLocationForValue(oldShape, value2, putFlags, locationFactory);
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
        }
        if (direct) {
            return LayoutStrategy.directRemoveProperty(shape, property, transition);
        }
        return this.indirectRemoveProperty(shape, property, transition);
    }

    protected Transition.RemovePropertyTransition newRemovePropertyTransition(Property property, boolean direct) {
        return new Transition.RemovePropertyTransition(property, this.toLocationOrType(property.getLocation()), direct);
    }

    private ShapeImpl indirectRemoveProperty(ShapeImpl shape, Property property, Transition.RemovePropertyTransition transition) {
        ShapeImpl owningShape = LayoutStrategy.getShapeFromProperty(shape, property.getKey());
        if (owningShape == null) {
            return null;
        }
        ArrayList<Transition> transitionList = new ArrayList<Transition>();
        ShapeImpl current = shape;
        while (current != owningShape) {
            Transition transitionFromParent = current.getTransitionFromParent();
            if (!(transitionFromParent instanceof Transition.DirectReplacePropertyTransition) || !((Transition.DirectReplacePropertyTransition)transitionFromParent).getPropertyBefore().getKey().equals(property.getKey())) {
                transitionList.add(transitionFromParent);
            }
            current = current.parent;
        }
        ShapeImpl newShape = owningShape.parent;
        ListIterator iterator = transitionList.listIterator(transitionList.size());
        while (iterator.hasPrevious()) {
            Transition previous = (Transition)iterator.previous();
            newShape = this.applyTransition(newShape, previous, true);
        }
        shape.addIndirectTransition(transition, newShape);
        return newShape;
    }

    private static ShapeImpl directRemoveProperty(ShapeImpl shape, Property property, Transition.RemovePropertyTransition transition) {
        PropertyMap newPropertyMap = shape.getPropertyMap().removeCopy(property);
        ShapeImpl newShape = shape.createShape(shape.getLayout(), shape.sharedData, shape, shape.objectType, newPropertyMap, transition, shape.allocator(), shape.flags);
        shape.addDirectTransition(transition, newShape);
        return newShape;
    }

    protected ShapeImpl directReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty) {
        return this.directReplaceProperty(shape, oldProperty, newProperty, true);
    }

    protected ShapeImpl directReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty, boolean ensureValid) {
        assert (oldProperty.getKey().equals(newProperty.getKey()));
        if (oldProperty.equals(newProperty)) {
            return shape;
        }
        shape.onPropertyTransition(oldProperty);
        Transition.DirectReplacePropertyTransition replacePropertyTransition = new Transition.DirectReplacePropertyTransition(oldProperty, newProperty);
        ShapeImpl cachedShape = shape.queryTransition(replacePropertyTransition);
        if (cachedShape != null) {
            return ensureValid ? this.ensureValid(cachedShape) : cachedShape;
        }
        PropertyMap newPropertyMap = shape.getPropertyMap().replaceCopy(oldProperty, newProperty);
        ShapeImpl.BaseAllocator allocator = shape.allocator().addLocation(newProperty.getLocation());
        ShapeImpl newShape = shape.createShape(shape.getLayout(), shape.sharedData, shape, shape.objectType, newPropertyMap, replacePropertyTransition, allocator, shape.flags);
        assert (((PropertyImpl)newProperty).isSame(newShape.getProperty(newProperty.getKey()))) : newShape.getProperty(newProperty.getKey());
        shape.addDirectTransition(replacePropertyTransition, newShape);
        if (!shape.isValid()) {
            newShape.invalidateValidAssumption();
            return ensureValid ? this.ensureValid(newShape) : newShape;
        }
        return newShape;
    }

    protected ShapeImpl separateReplaceProperty(ShapeImpl shape, Property oldProperty, Property newProperty) {
        ShapeImpl newRoot;
        ShapeImpl newShape = newRoot = shape.createShape(shape.getLayout(), shape.sharedData, null, shape.objectType, PropertyMap.empty(), null, shape.getLayout().createAllocator(), shape.flags);
        boolean found = false;
        Iterator<Property> iterator = shape.getPropertyMap().orderedValueIterator();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            if (!found && p.equals(oldProperty)) {
                p = newProperty;
                found = true;
            }
            newShape = newShape.addProperty(newProperty);
        }
        assert (found);
        assert (newShape.isValid());
        return newShape;
    }

    protected ShapeImpl createSeparateShape(ShapeImpl shape) {
        ShapeImpl newRoot;
        ShapeImpl newShape = newRoot = shape.createShape(shape.getLayout(), shape.sharedData, null, shape.objectType, PropertyMap.empty(), null, shape.getLayout().createAllocator(), shape.flags);
        Iterator<Property> iterator = shape.getPropertyMap().orderedValueIterator();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            newShape = newShape.addProperty(p);
        }
        assert (newShape.isValid());
        return newShape;
    }

    protected ShapeImpl addProperty(ShapeImpl shape, Property property) {
        return this.addProperty(shape, property, true);
    }

    protected ShapeImpl addProperty(ShapeImpl shape, Property property, boolean ensureValid) {
        assert (!shape.hasProperty(property.getKey())) : "duplicate property " + property.getKey();
        shape.onPropertyTransition(property);
        Transition.AddPropertyTransition addTransition = this.newAddPropertyTransition(property);
        ShapeImpl cachedShape = shape.queryTransition(addTransition);
        if (cachedShape != null) {
            return ensureValid ? this.ensureValid(cachedShape) : cachedShape;
        }
        ShapeImpl oldShape = this.ensureSpace(shape, property.getLocation());
        ShapeImpl newShape = ShapeImpl.makeShapeWithAddedProperty(oldShape, addTransition);
        oldShape.addDirectTransition(addTransition, newShape);
        if (!oldShape.isValid()) {
            newShape.invalidateValidAssumption();
            return ensureValid ? this.ensureValid(newShape) : newShape;
        }
        return newShape;
    }

    protected Transition.AddPropertyTransition newAddPropertyTransition(Property property) {
        return new Transition.AddPropertyTransition(property, this.toLocationOrType(property.getLocation()));
    }

    protected Object toLocationOrType(Location location) {
        Class<?> type;
        if (location instanceof LocationImpl && (type = ((LocationImpl)location).getType()) != null) {
            return type;
        }
        return location;
    }

    protected ShapeImpl applyTransition(ShapeImpl shape, Transition transition, boolean append) {
        if (transition instanceof Transition.AddPropertyTransition) {
            ShapeImpl newShape;
            Property property = ((Transition.AddPropertyTransition)transition).getProperty();
            if (append) {
                Property newProperty = ((PropertyImpl)property).relocate(shape.allocator().moveLocation(property.getLocation()));
                newShape = this.addProperty(shape, newProperty, true);
            } else {
                newShape = this.addProperty(shape, property, false);
            }
            return newShape;
        }
        if (transition instanceof Transition.ObjectTypeTransition) {
            return shape.setDynamicType(((Transition.ObjectTypeTransition)transition).getObjectType());
        }
        if (transition instanceof Transition.ObjectFlagsTransition) {
            return shape.setFlags(((Transition.ObjectFlagsTransition)transition).getObjectFlags());
        }
        if (transition instanceof Transition.DirectReplacePropertyTransition) {
            Property oldProperty = ((Transition.DirectReplacePropertyTransition)transition).getPropertyBefore();
            Property newProperty = ((Transition.DirectReplacePropertyTransition)transition).getPropertyAfter();
            if (append) {
                boolean sameLocation = oldProperty.getLocation().equals(newProperty.getLocation());
                oldProperty = shape.getProperty(oldProperty.getKey());
                Location newLocation = sameLocation ? oldProperty.getLocation() : shape.allocator().moveLocation(newProperty.getLocation());
                newProperty = ((PropertyImpl)newProperty).relocate(newLocation);
            }
            return this.directReplaceProperty(shape, oldProperty, newProperty, append);
        }
        throw new UnsupportedOperationException(transition.getClass().getName());
    }

    protected static ShapeImpl getShapeFromProperty(ShapeImpl shape, Object propertyName) {
        ShapeImpl root = shape.getRoot();
        for (ShapeImpl current = shape; current != root; current = current.getParent()) {
            if (!(current.getTransitionFromParent() instanceof Transition.AddPropertyTransition) || !((Transition.AddPropertyTransition)current.getTransitionFromParent()).getPropertyKey().equals(propertyName)) continue;
            return current;
        }
        return null;
    }
}

