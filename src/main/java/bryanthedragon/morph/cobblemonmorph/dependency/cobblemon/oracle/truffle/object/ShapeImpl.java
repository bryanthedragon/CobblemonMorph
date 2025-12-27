package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectFactory;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LocationFactory;
import com.oracle.truffle.api.object.ObjectType;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.graalvm.collections.EconomicMap;

public abstract class ShapeImpl extends Shape {
   protected final int flags;
   protected final LayoutImpl layout;
   protected final Object objectType;
   protected final ShapeImpl parent;
   protected final PropertyMap propertyMap;
   protected final Object sharedData;
   private final ShapeImpl root;
   protected final int objectArraySize;
   protected final int objectArrayCapacity;
   protected final int objectFieldSize;
   protected final int primitiveFieldSize;
   protected final int primitiveArraySize;
   protected final int primitiveArrayCapacity;
   protected final int depth;
   protected final int propertyCount;
   protected final Assumption validAssumption;
   @CompilerDirectives.CompilationFinal
   protected volatile Assumption leafAssumption;
   private volatile Object transitionMap;
   private final Transition transitionFromParent;
   private volatile ShapeImpl.PropertyAssumptions sharedPropertyAssumptions;
   private static final AtomicReferenceFieldUpdater<ShapeImpl, Object> TRANSITION_MAP_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      ShapeImpl.class, Object.class, "transitionMap"
   );
   private static final AtomicReferenceFieldUpdater<ShapeImpl, Assumption> LEAF_ASSUMPTION_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      ShapeImpl.class, Assumption.class, "leafAssumption"
   );
   private static final AtomicReferenceFieldUpdater<ShapeImpl, ShapeImpl.PropertyAssumptions> PROPERTY_ASSUMPTIONS_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
      ShapeImpl.class, ShapeImpl.PropertyAssumptions.class, "sharedPropertyAssumptions"
   );
   protected static final int FLAG_SHARED_SHAPE = 65536;
   protected static final int FLAG_ALLOW_PROPERTY_ASSUMPTIONS = 131072;
   protected static final int FLAG_HAS_INSTANCE_PROPERTIES = 262144;
   protected static final int OBJECT_FLAGS_MASK = 255;
   protected static final int OBJECT_FLAGS_SHIFT = 0;
   private static final DebugCounter shapeCount = DebugCounter.create("Shapes allocated total");
   private static final DebugCounter shapeCloneCount = DebugCounter.create("Shapes allocated cloned");
   private static final DebugCounter shapeCacheHitCount = DebugCounter.create("Shape cache hits");
   private static final DebugCounter shapeCacheMissCount = DebugCounter.create("Shape cache misses");
   static final DebugCounter shapeCacheExpunged = DebugCounter.create("Shape cache expunged");
   static final DebugCounter shapeCacheWeakKeys = DebugCounter.create("Shape cache weak keys");
   static final DebugCounter propertyAssumptionsCreated = DebugCounter.create("Property assumptions created");
   static final DebugCounter propertyAssumptionsRemoved = DebugCounter.create("Property assumptions removed");

   private ShapeImpl(
      Layout layout,
      ShapeImpl parent,
      Object objectType,
      Object sharedData,
      PropertyMap propertyMap,
      Transition transitionFromParent,
      int objectArraySize,
      int objectFieldSize,
      int primitiveFieldSize,
      int primitiveArraySize,
      int flags,
      Assumption singleContextAssumption
   ) {
      this.layout = (LayoutImpl)layout;
      this.objectType = Objects.requireNonNull(objectType);
      this.propertyMap = Objects.requireNonNull(propertyMap);
      this.root = parent != null ? parent.getRoot() : this;
      this.parent = parent;
      this.objectArraySize = objectArraySize;
      this.objectArrayCapacity = capacityFromSize(objectArraySize);
      this.objectFieldSize = objectFieldSize;
      this.primitiveFieldSize = primitiveFieldSize;
      this.primitiveArraySize = primitiveArraySize;
      this.primitiveArrayCapacity = capacityFromSize(primitiveArraySize);
      if (parent != null) {
         this.propertyCount = makePropertyCount(parent, propertyMap, transitionFromParent);
         this.depth = parent.depth + 1;
      } else {
         this.propertyCount = 0;
         this.depth = 0;
      }

      this.validAssumption = createValidAssumption();
      int allFlags = flags;
      if ((flags & 262144) == 0 && (objectFieldSize != 0 || objectArraySize != 0 || primitiveFieldSize != 0 || primitiveArraySize != 0)) {
         allFlags = flags | 262144;
      }

      this.flags = allFlags;
      this.transitionFromParent = transitionFromParent;
      this.sharedData = sharedData;

      assert parent == null || this.sharedData == parent.sharedData;

      this.sharedPropertyAssumptions = parent == null && (flags & 131072) != 0 && singleContextAssumption != null
         ? new ShapeImpl.PropertyAssumptions(singleContextAssumption)
         : null;
      shapeCount.inc();
      if (ObjectStorageOptions.DumpShapes) {
         Debug.trackShape(this);
      }
   }

   protected ShapeImpl(
      Layout layout,
      ShapeImpl parent,
      Object objectType,
      Object sharedData,
      PropertyMap propertyMap,
      Transition transition,
      Shape.Allocator allocator,
      int flags
   ) {
      this(
         layout,
         parent,
         objectType,
         sharedData,
         propertyMap,
         transition,
         ((ShapeImpl.BaseAllocator)allocator).objectArraySize,
         ((ShapeImpl.BaseAllocator)allocator).objectFieldSize,
         ((ShapeImpl.BaseAllocator)allocator).primitiveFieldSize,
         ((ShapeImpl.BaseAllocator)allocator).primitiveArraySize,
         flags,
         null
      );
   }

   protected abstract ShapeImpl createShape(
      Layout layout, Object sharedData, ShapeImpl parent, Object objectType, PropertyMap propertyMap, Transition transition, Shape.Allocator allocator, int id
   );

   protected ShapeImpl(Layout layout, Object dynamicType, Object sharedData, int flags, Assumption constantObjectAssumption) {
      this(layout, null, dynamicType, sharedData, PropertyMap.empty(), null, 0, 0, 0, 0, flags, constantObjectAssumption);
   }

   private static int makePropertyCount(ShapeImpl parent, PropertyMap propertyMap, Transition transitionFromParent) {
      int thisSize = propertyMap.size();
      int parentSize = parent.propertyMap.size();
      if (thisSize > parentSize) {
         Property lastProperty = propertyMap.getLastProperty();
         if (!lastProperty.isHidden()) {
            return parent.propertyCount + 1;
         }
      } else if (thisSize < parentSize
         && transitionFromParent instanceof Transition.RemovePropertyTransition
         && !(((Transition.RemovePropertyTransition)transitionFromParent).getPropertyKey() instanceof HiddenKey)) {
         return parent.propertyCount - 1;
      }

      return parent.propertyCount;
   }

   @Override
   public final Property getLastProperty() {
      return this.propertyMap.getLastProperty();
   }

   @Override
   public final int getFlags() {
      return getObjectFlags(this.flags);
   }

   public final int getFlagsInternal() {
      return this.flags;
   }

   private static int capacityFromSize(int size) {
      if (size == 0) {
         return 0;
      } else if (size <= 4) {
         return 4;
      } else if (size <= 8) {
         return 8;
      } else {
         int hi = Integer.highestOneBit(size);
         int cap = hi;
         if (hi < size) {
            cap = hi + (hi >>> 1);
            if (cap < size) {
               cap = hi << 1;
               if (cap < size) {
                  cap = size;
               }
            }
         }

         return cap;
      }
   }

   public final int getObjectArraySize() {
      return this.objectArraySize;
   }

   public final int getObjectFieldSize() {
      return this.objectFieldSize;
   }

   public final int getPrimitiveFieldSize() {
      return this.primitiveFieldSize;
   }

   public final int getObjectArrayCapacity() {
      return this.objectArrayCapacity;
   }

   public final int getPrimitiveArrayCapacity() {
      return this.primitiveArrayCapacity;
   }

   public final int getPrimitiveArraySize() {
      return this.primitiveArraySize;
   }

   public final boolean hasPrimitiveArray() {
      return this.getLayout().hasPrimitiveExtensionArray();
   }

   @Override
   protected boolean hasInstanceProperties() {
      return (this.flags & 262144) != 0;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Property getProperty(Object key) {
      return this.propertyMap.get(key);
   }

   public final PropertyMap getPropertyMap() {
      return this.propertyMap;
   }

   public final void addDirectTransition(Transition transition, ShapeImpl next) {
      assert next.getParent() == this && transition.isDirect();

      this.addTransitionInternal(transition, next);
   }

   public final void addIndirectTransition(Transition transition, ShapeImpl next) {
      assert !this.isShared();

      assert next.getParent() != this && !transition.isDirect();

      this.addTransitionInternal(transition, next);
   }

   private void addTransitionInternal(Transition transition, ShapeImpl successor) {
      CompilerAsserts.neverPartOfCompilation();

      Object prev;
      Object next;
      do {
         prev = TRANSITION_MAP_UPDATER.get(this);
         if (prev == null) {
            this.invalidateLeafAssumption();
            next = newSingleEntry(transition, successor);
         } else if (isSingleEntry(prev)) {
            StrongKeyWeakValueEntry<Object, ShapeImpl> entry = asSingleEntry(prev);
            ShapeImpl exSucc = entry.getValue();
            Transition exTra;
            if (exSucc != null && (exTra = unwrapKey(entry.getKey())) != null) {
               next = newTransitionMap(exTra, exSucc, transition, successor);
            } else {
               next = newSingleEntry(transition, successor);
            }
         } else {
            next = addToTransitionMap(transition, successor, asTransitionMap(prev));
         }
      } while (prev != next && !TRANSITION_MAP_UPDATER.compareAndSet(this, prev, next));
   }

   private static Object newTransitionMap(Transition firstTransition, ShapeImpl firstShape, Transition secondTransition, ShapeImpl secondShape) {
      TransitionMap<Transition, ShapeImpl> map = newTransitionMap();
      addToTransitionMap(firstTransition, firstShape, map);
      addToTransitionMap(secondTransition, secondShape, map);
      return map;
   }

   private static Object addToTransitionMap(Transition transition, ShapeImpl successor, TransitionMap<Transition, ShapeImpl> map) {
      if (transition.hasConstantLocation()) {
         map.putWeakKey(transition, successor);
      } else {
         map.put(transition, successor);
      }

      return map;
   }

   private static TransitionMap<Transition, ShapeImpl> newTransitionMap() {
      return new TransitionMap<>();
   }

   private static Transition unwrapKey(Object key) {
      return key instanceof WeakKey ? (Transition)((WeakKey)key).get() : (Transition)key;
   }

   private static TransitionMap<Transition, ShapeImpl> asTransitionMap(Object map) {
      return (TransitionMap<Transition, ShapeImpl>)map;
   }

   private static boolean isTransitionMap(Object trans) {
      return trans instanceof TransitionMap;
   }

   private static Object newSingleEntry(Transition transition, ShapeImpl successor) {
      Object key = transition;
      if (transition.hasConstantLocation()) {
         key = new WeakKey<>(transition);
      }

      return new StrongKeyWeakValueEntry<>(key, successor);
   }

   private static boolean isSingleEntry(Object trans) {
      return trans instanceof StrongKeyWeakValueEntry;
   }

   private static StrongKeyWeakValueEntry<Object, ShapeImpl> asSingleEntry(Object trans) {
      return (StrongKeyWeakValueEntry<Object, ShapeImpl>)trans;
   }

   @Deprecated
   public final Map<Transition, ShapeImpl> getTransitionMapForRead() {
      final Map<Transition, ShapeImpl> snapshot = new HashMap<>();
      this.forEachTransition(new BiConsumer<Transition, ShapeImpl>() {
         public void accept(Transition t, ShapeImpl s) {
            snapshot.put(t, s);
         }
      });
      return snapshot;
   }

   public final void forEachTransition(BiConsumer<Transition, ShapeImpl> consumer) {
      Object trans = this.transitionMap;
      if (trans != null) {
         if (isSingleEntry(trans)) {
            StrongKeyWeakValueEntry<Object, ShapeImpl> entry = asSingleEntry(trans);
            ShapeImpl shape = entry.getValue();
            if (shape != null) {
               Transition key = unwrapKey(entry.getKey());
               if (key != null) {
                  consumer.accept(key, shape);
               }
            }
         } else {
            assert isTransitionMap(trans);

            TransitionMap<Transition, ShapeImpl> map = asTransitionMap(trans);
            map.forEach(consumer);
         }
      }
   }

   private ShapeImpl queryTransitionImpl(Transition transition) {
      Object trans = this.transitionMap;
      if (trans == null) {
         return null;
      } else if (isSingleEntry(trans)) {
         StrongKeyWeakValueEntry<Object, ShapeImpl> entry = asSingleEntry(trans);
         ShapeImpl shape = entry.getValue();
         if (shape != null) {
            Transition key = unwrapKey(entry.getKey());
            if (key != null && transition.equals(key)) {
               return shape;
            }
         }

         return null;
      } else {
         assert isTransitionMap(trans);

         TransitionMap<Transition, ShapeImpl> map = asTransitionMap(trans);
         return map.get(transition);
      }
   }

   public final ShapeImpl queryTransition(Transition transition) {
      ShapeImpl cachedShape = this.queryTransitionImpl(transition);
      if (cachedShape != null) {
         shapeCacheHitCount.inc();
         return cachedShape;
      } else {
         shapeCacheMissCount.inc();
         return null;
      }
   }

   public final <R> R iterateTransitions(BiFunction<Transition, ShapeImpl, R> consumer) {
      Object trans = this.transitionMap;
      if (trans == null) {
         return null;
      } else if (isSingleEntry(trans)) {
         StrongKeyWeakValueEntry<Object, ShapeImpl> entry = asSingleEntry(trans);
         ShapeImpl shape = entry.getValue();
         if (shape != null) {
            Transition key = unwrapKey(entry.getKey());
            if (key != null) {
               return consumer.apply(key, shape);
            }
         }

         return null;
      } else {
         assert isTransitionMap(trans);

         TransitionMap<Transition, ShapeImpl> map = asTransitionMap(trans);
         return map.iterateEntries(consumer);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public ShapeImpl addProperty(Property property) {
      return this.getLayoutStrategy().addProperty(this, property);
   }

   @CompilerDirectives.TruffleBoundary
   protected void onPropertyTransition(Property property) {
      this.onPropertyTransitionWithKey(property.getKey());
   }

   final void onPropertyTransitionWithKey(Object propertyKey) {
      if (this.allowPropertyAssumptions()) {
         ShapeImpl.PropertyAssumptions propertyAssumptions = this.getPropertyAssumptions();
         if (propertyAssumptions != null) {
            propertyAssumptions.invalidatePropertyAssumption(propertyKey);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public ShapeImpl defineProperty(Object key, Object value, int propertyFlags) {
      return this.getLayoutStrategy().defineProperty(this, key, value, propertyFlags, null);
   }

   @CompilerDirectives.TruffleBoundary
   public ShapeImpl defineProperty(Object key, Object value, int propertyFlags, LocationFactory locationFactory) {
      return this.getLayoutStrategy().defineProperty(this, key, value, propertyFlags, locationFactory);
   }

   protected ShapeImpl cloneRoot(ShapeImpl from, Object newSharedData) {
      return this.createShape(from.layout, newSharedData, null, from.objectType, from.propertyMap, null, from.allocator(), from.flags);
   }

   protected final ShapeImpl cloneOnto(ShapeImpl newParent) {
      ShapeImpl newShape = this.createShape(
         newParent.layout, newParent.sharedData, newParent, this.objectType, this.propertyMap, this.transitionFromParent, this.allocator(), newParent.flags
      );
      shapeCloneCount.inc();
      newParent.addDirectTransition(this.transitionFromParent, newShape);
      return newShape;
   }

   public final Transition getTransitionFromParent() {
      return this.transitionFromParent;
   }

   protected static ShapeImpl makeShapeWithAddedProperty(ShapeImpl parent, Transition.AddPropertyTransition addTransition) {
      Property addend = addTransition.getProperty();
      ShapeImpl.BaseAllocator allocator = parent.allocator().addLocation(addend.getLocation());
      PropertyMap newPropertyMap = parent.propertyMap.putCopy(addend);
      ShapeImpl newShape = parent.createShape(
         parent.layout, parent.sharedData, parent, parent.objectType, newPropertyMap, addTransition, allocator, parent.flags
      );

      assert newShape.hasPrimitiveArray() || ((LocationImpl)addend.getLocation()).primitiveArrayCount() == 0;

      assert newShape.depth == allocator.depth;

      return newShape;
   }

   public boolean isRelated(Shape other) {
      return this == other ? true : this.getRoot() == this.getRoot();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final List<Property> getPropertyList() {
      return Arrays.asList(this.getPropertyArray());
   }

   @CompilerDirectives.TruffleBoundary
   public final Property[] getPropertyArray() {
      Property[] props = new Property[this.getPropertyCount()];
      int i = props.length;
      Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();

      while (it.hasNext()) {
         Property currentProperty = it.next();
         if (!currentProperty.isHidden()) {
            props[--i] = currentProperty;
         }
      }

      return props;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final List<Property> getPropertyListInternal(boolean ascending) {
      Property[] props = new Property[this.propertyMap.size()];
      int i = ascending ? props.length : 0;
      Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();

      while (it.hasNext()) {
         Property current = it.next();
         if (ascending) {
            props[--i] = current;
         } else {
            props[i++] = current;
         }
      }

      return Arrays.asList(props);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final List<Object> getKeyList() {
      return Arrays.asList(this.getKeyArray());
   }

   @CompilerDirectives.TruffleBoundary
   public final Object[] getKeyArray() {
      Object[] props = new Object[this.getPropertyCount()];
      int i = props.length;
      Iterator<Property> it = this.propertyMap.reverseOrderedValueIterator();

      while (it.hasNext()) {
         Property currentProperty = it.next();
         if (!currentProperty.isHidden()) {
            props[--i] = currentProperty.getKey();
         }
      }

      return props;
   }

   @Override
   public Iterable<Object> getKeys() {
      return this.getKeyList();
   }

   @Override
   public final boolean isValid() {
      return this.getValidAssumption().isValid();
   }

   @Override
   public final Assumption getValidAssumption() {
      return this.validAssumption;
   }

   private static Assumption createValidAssumption() {
      return Truffle.getRuntime().createAssumption("valid shape");
   }

   public final void invalidateValidAssumption() {
      this.getValidAssumption().invalidate();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final boolean isLeaf() {
      Assumption assumption = this.leafAssumption;
      return assumption == null || assumption.isValid();
   }

   @Override
   public final Assumption getLeafAssumption() {
      Assumption assumption = this.leafAssumption;
      if (assumption != null) {
         return assumption;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();

         Assumption prev;
         Assumption next;
         do {
            prev = LEAF_ASSUMPTION_UPDATER.get(this);
            if (prev != null) {
               return prev;
            }

            boolean isLeafShape = this.transitionMap == null;
            next = isLeafShape ? createLeafAssumption() : Assumption.NEVER_VALID;
         } while (!LEAF_ASSUMPTION_UPDATER.compareAndSet(this, prev, next));

         return next;
      }
   }

   private static Assumption createLeafAssumption() {
      return Truffle.getRuntime().createAssumption("leaf shape");
   }

   @CompilerDirectives.TruffleBoundary
   protected void invalidateLeafAssumption() {
      while (true) {
         Assumption prev = LEAF_ASSUMPTION_UPDATER.get(this);
         if (prev != Assumption.NEVER_VALID) {
            if (prev != null) {
               prev.invalidate();
            }

            if (!LEAF_ASSUMPTION_UPDATER.compareAndSet(this, prev, Assumption.NEVER_VALID)) {
               continue;
            }
         }

         return;
      }
   }

   @Override
   public String toString() {
      return this.toStringLimit(Integer.MAX_VALUE);
   }

   @CompilerDirectives.TruffleBoundary
   public String toStringLimit(int limit) {
      StringBuilder sb = new StringBuilder();
      sb.append('@');
      sb.append(Integer.toHexString(this.hashCode()));
      if (!this.isValid()) {
         sb.append('!');
      }

      sb.append("{");
      Iterator<Property> iterator = this.propertyMap.reverseOrderedValueIterator();

      while (iterator.hasNext()) {
         Property p = iterator.next();
         sb.append(p);
         if (iterator.hasNext()) {
            sb.append(",");
         }

         if (sb.length() >= limit) {
            sb.append("...");
            break;
         }

         sb.append("\n");
      }

      sb.append("}");
      return sb.toString();
   }

   public final ShapeImpl getParent() {
      return this.parent;
   }

   public final int getDepth() {
      return this.depth;
   }

   @Override
   public final boolean hasProperty(Object name) {
      return this.getProperty(name) != null;
   }

   @CompilerDirectives.TruffleBoundary
   public final ShapeImpl removeProperty(Property prop) {
      this.onPropertyTransition(prop);
      return this.getLayoutStrategy().removeProperty(this, prop);
   }

   public final ShapeImpl.BaseAllocator allocator() {
      return this.getLayoutStrategy().createAllocator(this);
   }

   @CompilerDirectives.TruffleBoundary
   public ShapeImpl replaceProperty(Property oldProperty, Property newProperty) {
      assert oldProperty.getKey().equals(newProperty.getKey());

      this.onPropertyTransition(oldProperty);
      return this.getLayoutStrategy().replaceProperty(this, oldProperty, newProperty);
   }

   public static ShapeImpl findCommonAncestor(ShapeImpl left, ShapeImpl right) {
      if (!left.isRelated(right)) {
         throw new IllegalArgumentException("shapes must have the same root");
      } else if (left == right) {
         return left;
      } else {
         int leftLength = left.depth;
         int rightLength = right.depth;
         ShapeImpl leftPtr = left;

         ShapeImpl rightPtr;
         for (rightPtr = right; leftLength > rightLength; leftLength--) {
            leftPtr = leftPtr.parent;
         }

         while (rightLength > leftLength) {
            rightPtr = rightPtr.parent;
            rightLength--;
         }

         while (leftPtr != rightPtr) {
            leftPtr = leftPtr.parent;
            rightPtr = rightPtr.parent;
         }

         return leftPtr;
      }
   }

   @Override
   public final int getPropertyCount() {
      return this.propertyCount;
   }

   public static List<Property> diff(Shape oldShape, Shape newShape) {
      List<Property> oldList = oldShape.getPropertyListInternal(false);
      List<Property> newList = newShape.getPropertyListInternal(false);
      List<Property> diff = new ArrayList<>(oldList);
      diff.addAll(newList);
      List<Property> intersection = new ArrayList<>(oldList);
      intersection.retainAll(newList);
      diff.removeAll(intersection);
      return diff;
   }

   @Override
   public ObjectType getObjectType() {
      return (ObjectType)this.objectType;
   }

   @Override
   public Object getDynamicType() {
      return this.objectType;
   }

   @CompilerDirectives.TruffleBoundary
   protected ShapeImpl setDynamicType(Object newObjectType) {
      Objects.requireNonNull(newObjectType, "dynamicType");
      if (this.getDynamicType() == newObjectType) {
         return this;
      } else {
         Transition.ObjectTypeTransition transition = new Transition.ObjectTypeTransition(newObjectType);
         ShapeImpl cachedShape = this.queryTransition(transition);
         if (cachedShape != null) {
            return cachedShape;
         } else {
            ShapeImpl newShape = this.createShape(this.layout, this.sharedData, this, newObjectType, this.propertyMap, transition, this.allocator(), this.flags);
            this.addDirectTransition(transition, newShape);
            return newShape;
         }
      }
   }

   public ShapeImpl getRoot() {
      return this.root;
   }

   @Override
   public final boolean check(DynamicObject subject) {
      return subject.getShape() == this;
   }

   public final LayoutImpl getLayout() {
      return this.layout;
   }

   public final LayoutStrategy getLayoutStrategy() {
      return this.getLayout().getStrategy();
   }

   @Override
   public final Object getSharedData() {
      return this.sharedData;
   }

   final Object getSharedDataInternal() {
      return this.sharedData;
   }

   final boolean allowPropertyAssumptions() {
      return (this.flags & 131072) != 0;
   }

   private ShapeImpl.PropertyAssumptions getOrCreatePropertyAssumptions() {
      CompilerAsserts.neverPartOfCompilation();

      assert this.allowPropertyAssumptions();

      ShapeImpl.PropertyAssumptions ass = this.root.sharedPropertyAssumptions;
      if (ass == null) {
         ass = new ShapeImpl.PropertyAssumptions(null);
         if (!PROPERTY_ASSUMPTIONS_UPDATER.compareAndSet(this.root, null, ass)) {
            ass = this.getPropertyAssumptions();
         }
      }

      assert ass != null;

      return ass;
   }

   private ShapeImpl.PropertyAssumptions getPropertyAssumptions() {
      CompilerAsserts.neverPartOfCompilation();

      assert this.allowPropertyAssumptions();

      return this.root.sharedPropertyAssumptions;
   }

   @CompilerDirectives.TruffleBoundary
   protected void invalidateAllPropertyAssumptions() {
      assert this.allowPropertyAssumptions();

      ShapeImpl.PropertyAssumptions propertyAssumptions = this.getPropertyAssumptions();
      if (propertyAssumptions != null) {
         propertyAssumptions.invalidateAllPropertyAssumptions();
      }
   }

   protected Assumption getSingleContextAssumption() {
      ShapeImpl.PropertyAssumptions propertyAssumptions = this.getPropertyAssumptions();
      return propertyAssumptions != null ? propertyAssumptions.getSingleContextAssumption() : null;
   }

   @CompilerDirectives.TruffleBoundary
   public final ShapeImpl createSeparateShape(Object newSharedData) {
      return this.parent == null ? this.cloneRoot(this, newSharedData) : this.cloneOnto(this.parent.createSeparateShape(newSharedData));
   }

   @CompilerDirectives.TruffleBoundary
   public final ShapeImpl changeType(ObjectType newObjectType) {
      return this.setDynamicType(newObjectType);
   }

   @CompilerDirectives.TruffleBoundary
   protected ShapeImpl setFlags(int newShapeFlags) {
      checkObjectFlags(newShapeFlags);
      if (this.getFlags() == newShapeFlags) {
         return this;
      } else {
         Transition.ObjectFlagsTransition transition = new Transition.ObjectFlagsTransition(newShapeFlags);
         ShapeImpl cachedShape = this.queryTransition(transition);
         if (cachedShape != null) {
            return cachedShape;
         } else {
            int newFlags = newShapeFlags | this.flags & -256;
            ShapeImpl newShape = this.createShape(this.layout, this.sharedData, this, this.objectType, this.propertyMap, transition, this.allocator(), newFlags);
            this.addDirectTransition(transition, newShape);
            return newShape;
         }
      }
   }

   @Override
   public final Iterable<Property> getProperties() {
      return this.getPropertyList();
   }

   @Override
   public final DynamicObject newInstance() {
      throw DefaultLayout.unsupported();
   }

   @Override
   public final DynamicObjectFactory createFactory() {
      throw DefaultLayout.unsupported();
   }

   @Override
   public Object getMutex() {
      return this.getRoot();
   }

   @Override
   public Shape tryMerge(Shape other) {
      return null;
   }

   @Override
   public boolean isShared() {
      return (this.flags & 65536) != 0;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Shape makeSharedShape() {
      if (this.isShared()) {
         throw new UnsupportedOperationException("makeSharedShape() can only be called on non-shared shapes.");
      } else {
         Transition transition = new Transition.ShareShapeTransition();
         ShapeImpl cachedShape = this.queryTransition(transition);
         if (cachedShape != null) {
            return cachedShape;
         } else {
            ShapeImpl newShape = this.createShape(
               this.layout, this.sharedData, this, this.objectType, this.propertyMap, transition, this.allocator(), this.flags | 65536
            );
            this.addDirectTransition(transition, newShape);
            return newShape;
         }
      }
   }

   protected static int getObjectFlags(int flags) {
      return (flags & 0xFF) >>> 0;
   }

   protected static int checkObjectFlags(int flags) {
      if ((flags & -256) != 0) {
         throw new IllegalArgumentException("flags must be in the range [0, 255]");
      } else {
         return flags;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Assumption getPropertyAssumption(Object key) {
      if (this.allowPropertyAssumptions()) {
         Assumption propertyAssumption = this.getOrCreatePropertyAssumptions().getPropertyAssumption(key);
         if (propertyAssumption != null && propertyAssumption.isValid()) {
            return propertyAssumption;
         }
      }

      return Assumption.NEVER_VALID;
   }

   protected boolean testPropertyFlags(IntPredicate predicate) {
      for (Property p : this.getProperties()) {
         if (!predicate.test(p.getFlags())) {
            return false;
         }
      }

      return true;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean allPropertiesMatch(Predicate<Property> predicate) {
      for (Property p : this.getProperties()) {
         if (!predicate.test(p)) {
            return false;
         }
      }

      return true;
   }

   public abstract static class BaseAllocator extends Shape.Allocator implements LocationImpl.LocationVisitor, Cloneable {
      protected final LayoutImpl layout;
      protected int objectArraySize;
      protected int objectFieldSize;
      protected int primitiveFieldSize;
      protected int primitiveArraySize;
      protected int depth;
      protected boolean shared;

      protected BaseAllocator(LayoutImpl layout) {
         this.layout = layout;
      }

      protected BaseAllocator(ShapeImpl shape) {
         this(shape.getLayout());
         this.objectArraySize = shape.objectArraySize;
         this.objectFieldSize = shape.objectFieldSize;
         this.primitiveFieldSize = shape.primitiveFieldSize;
         this.primitiveArraySize = shape.primitiveArraySize;
         this.depth = shape.depth;
         this.shared = shape.isShared();
      }

      protected abstract Location moveLocation(Location oldLocation);

      @Deprecated
      protected abstract Location newObjectLocation(boolean useFinal, boolean nonNull);

      @Deprecated
      protected abstract Location newTypedObjectLocation(boolean useFinal, Class<?> type, boolean nonNull);

      @Deprecated
      protected abstract Location newIntLocation(boolean useFinal);

      @Deprecated
      protected abstract Location newDoubleLocation(boolean useFinal);

      @Deprecated
      protected abstract Location newLongLocation(boolean useFinal);

      @Deprecated
      protected abstract Location newBooleanLocation(boolean useFinal);

      @Deprecated
      @Override
      public Location constantLocation(Object value) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public Location declaredLocation(Object value) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      protected Location locationForValue(Object value, boolean useFinal, boolean nonNull) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      protected Location locationForValueUpcast(Object value, Location oldLocation) {
         return this.locationForValueUpcast(value, oldLocation, 0L);
      }

      protected Location locationForValueUpcast(Object value, Location oldLocation, long putFlags) {
         throw new UnsupportedOperationException();
      }

      @Override
      protected Location locationForType(Class<?> type, boolean useFinal, boolean nonNull) {
         throw new UnsupportedOperationException();
      }

      protected <T extends Location> T advance(T location0) {
         if (location0 instanceof LocationImpl) {
            LocationImpl location = (LocationImpl)location0;
            location.accept(this);

            assert this.layout.hasPrimitiveExtensionArray() || this.primitiveArraySize == 0;
         }

         this.depth++;
         return location0;
      }

      public ShapeImpl.BaseAllocator addLocation(Location location) {
         this.advance(location);
         return this;
      }

      @Override
      public void visitObjectField(int index, int count) {
         this.objectFieldSize = Math.max(this.objectFieldSize, index + count);
      }

      @Override
      public void visitObjectArray(int index, int count) {
         this.objectArraySize = Math.max(this.objectArraySize, index + count);
      }

      @Override
      public void visitPrimitiveArray(int index, int count) {
         this.primitiveArraySize = Math.max(this.primitiveArraySize, index + count);
      }

      @Override
      public void visitPrimitiveField(int index, int count) {
         this.primitiveFieldSize = Math.max(this.primitiveFieldSize, index + count);
      }

      public final ShapeImpl.BaseAllocator copy() {
         return this.clone();
      }

      protected final ShapeImpl.BaseAllocator clone() {
         try {
            return (ShapeImpl.BaseAllocator)super.clone();
         } catch (CloneNotSupportedException var2) {
            throw CompilerDirectives.shouldNotReachHere(var2);
         }
      }

      @Deprecated
      public Location existingLocationForValue(Object value, Location oldLocation, ShapeImpl oldShape) {
         assert oldShape.getLayout() == this.layout;

         Location newLocation;
         if (oldLocation.canStore(value)) {
            newLocation = oldLocation;
         } else {
            newLocation = oldShape.allocator().locationForValueUpcast(value, oldLocation);
         }

         return newLocation;
      }
   }

   static final class PropertyAssumptions {
      private final EconomicMap<Object, Assumption> stablePropertyAssumptions;
      private final Assumption singleContextAssumption;

      PropertyAssumptions(Assumption singleContextAssumption) {
         this.singleContextAssumption = singleContextAssumption;
         this.stablePropertyAssumptions = EconomicMap.create();
      }

      synchronized Assumption getPropertyAssumption(Object propertyName) {
         CompilerAsserts.neverPartOfCompilation();
         EconomicMap<Object, Assumption> map = this.stablePropertyAssumptions;
         Assumption assumption = map.get(propertyName);
         if (assumption != null) {
            return assumption;
         } else {
            assumption = Truffle.getRuntime().createAssumption(propertyName.toString());
            map.put(propertyName, assumption);
            ShapeImpl.propertyAssumptionsCreated.inc();
            return assumption;
         }
      }

      synchronized void invalidatePropertyAssumption(Object propertyName) {
         CompilerAsserts.neverPartOfCompilation();
         EconomicMap<Object, Assumption> map = this.stablePropertyAssumptions;
         Assumption assumption = map.get(propertyName);
         if (assumption != null && assumption != Assumption.NEVER_VALID) {
            assumption.invalidate("invalidatePropertyAssumption");
            map.put(propertyName, Assumption.NEVER_VALID);
            ShapeImpl.propertyAssumptionsRemoved.inc();
         }
      }

      synchronized void invalidateAllPropertyAssumptions() {
         CompilerAsserts.neverPartOfCompilation();

         for (Assumption assumption : this.stablePropertyAssumptions.getValues()) {
            assumption.invalidate("invalidateAllPropertyAssumptions");
         }

         this.stablePropertyAssumptions.clear();
      }

      Assumption getSingleContextAssumption() {
         return this.singleContextAssumption;
      }
   }
}
