package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import org.graalvm.collections.EconomicSet;

@ExportLibrary(value = DynamicObjectLibrary.class, receiverType = DynamicObject.class, priority = 10, transitionLimit = "5")
abstract class DynamicObjectLibraryImpl {
   static final int KEY_LIMIT = 3;

   static boolean keyEquals(Object cachedKey, Object key) {
      if (cachedKey instanceof String) {
         return cachedKey == key || key instanceof String && ((String)cachedKey).equals(key);
      } else if (cachedKey instanceof HiddenKey) {
         return key == cachedKey;
      } else {
         return cachedKey instanceof Long ? key instanceof Long && ((Long)cachedKey).equals(key) : cachedKey == key || keyEqualsBoundary(cachedKey, key);
      }
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   static boolean keyEqualsBoundary(Object cachedKey, Object key) {
      return Objects.equals(cachedKey, key);
   }

   @ExportMessage
   static boolean accepts(DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape) {
      return object.getShape() == cachedShape;
   }

   @ExportMessage
   static Shape getShape(DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape) {
      return cachedShape;
   }

   @ExportMessage
   static Object getOrDefault(
      DynamicObject object,
      Object key,
      Object defaultValue,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.getOrDefault(object, cachedShape, key, defaultValue);
   }

   @ExportMessage
   static int getIntOrDefault(
      DynamicObject object,
      Object key,
      Object defaultValue,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) throws UnexpectedResultException {
      return keyCache.getIntOrDefault(object, cachedShape, key, defaultValue);
   }

   @ExportMessage
   static double getDoubleOrDefault(
      DynamicObject object,
      Object key,
      Object defaultValue,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) throws UnexpectedResultException {
      return keyCache.getDoubleOrDefault(object, cachedShape, key, defaultValue);
   }

   @ExportMessage
   static long getLongOrDefault(
      DynamicObject object,
      Object key,
      Object defaultValue,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) throws UnexpectedResultException {
      return keyCache.getLongOrDefault(object, cachedShape, key, defaultValue);
   }

   @ExportMessage
   static boolean containsKey(
      DynamicObject object,
      Object key,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.containsKey(object, cachedShape, key);
   }

   @ExportMessage
   static void put(
      DynamicObject object,
      Object key,
      Object value,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.put(object, cachedShape, key, value, 0L);
   }

   @ExportMessage
   static void putInt(
      DynamicObject object,
      Object key,
      int value,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.putInt(object, cachedShape, key, value, 0L);
   }

   @ExportMessage
   static void putLong(
      DynamicObject object,
      Object key,
      long value,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.putLong(object, cachedShape, key, value, 0L);
   }

   @ExportMessage
   static void putDouble(
      DynamicObject object,
      Object key,
      double value,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.putDouble(object, cachedShape, key, value, 0L);
   }

   @ExportMessage
   static boolean putIfPresent(
      DynamicObject object,
      Object key,
      Object value,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.put(object, cachedShape, key, value, 17179869184L);
   }

   @ExportMessage
   static void putWithFlags(
      DynamicObject object,
      Object key,
      Object value,
      int flags,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.put(object, cachedShape, key, value, Flags.propertyFlagsToPutFlags(flags) | 34359738368L);
   }

   @ExportMessage
   static void putConstant(
      DynamicObject object,
      Object key,
      Object value,
      int flags,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      keyCache.put(object, cachedShape, key, value, Flags.propertyFlagsToPutFlags(flags) | 34359738368L | 68719476736L);
   }

   @ExportMessage
   public static Property getProperty(
      DynamicObject object,
      Object key,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.getProperty(object, cachedShape, key);
   }

   @ExportMessage
   public static boolean setPropertyFlags(
      DynamicObject object,
      Object key,
      int propertyFlags,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.setPropertyFlags(object, cachedShape, key, propertyFlags);
   }

   @CompilerDirectives.TruffleBoundary
   static ShapeImpl changePropertyFlags(ShapeImpl shape, PropertyImpl cachedProperty, int propertyFlags) {
      return shape.replaceProperty(cachedProperty, cachedProperty.copyWithFlags(propertyFlags));
   }

   @ExportMessage
   public static boolean removeKey(
      DynamicObject object,
      Object key,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached.Shared("keyCache") @Cached("create(object.getShape(), key)") DynamicObjectLibraryImpl.KeyCacheNode keyCache
   ) {
      return keyCache.removeKey(object, cachedShape, key);
   }

   @ExportMessage
   public static Object getDynamicType(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return cachedShape.getDynamicType();
   }

   @ExportMessage
   public static boolean setDynamicType(
      DynamicObject object,
      Object objectType,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached DynamicObjectLibraryImpl.SetDynamicTypeNode setCache
   ) {
      return setCache.execute(object, cachedShape, objectType);
   }

   @ExportMessage
   public static int getShapeFlags(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return cachedShape.getFlags();
   }

   @ExportMessage
   public static boolean setShapeFlags(
      DynamicObject object,
      int flags,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached DynamicObjectLibraryImpl.SetFlagsNode setCache
   ) {
      return setCache.execute(object, cachedShape, flags);
   }

   @ExportMessage
   public static boolean isShared(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return cachedShape.isShared();
   }

   @ExportMessage
   public static void markShared(
      DynamicObject object,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached DynamicObjectLibraryImpl.MakeSharedNode setCache
   ) {
      setCache.execute(object, cachedShape);
   }

   @ExportMessage
   public static boolean updateShape(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return cachedShape.isValid() ? false : updateShapeImpl(object);
   }

   @CompilerDirectives.TruffleBoundary
   static boolean updateShapeImpl(DynamicObject object) {
      return ((ShapeImpl)object.getShape()).getLayoutStrategy().updateShape(object);
   }

   @ExportMessage
   public static boolean resetShape(
      DynamicObject object,
      Shape otherShape,
      @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape,
      @Cached DynamicObjectLibraryImpl.ResetShapeNode setCache
   ) {
      return setCache.execute(object, cachedShape, otherShape);
   }

   @ExportMessage
   public static Object[] getKeyArray(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return ((ShapeImpl)cachedShape).getKeyArray();
   }

   @ExportMessage
   public static Property[] getPropertyArray(
      DynamicObject object, @Cached.Shared("cachedShape") @Cached(value = "object.getShape()", allowUncached = true) Shape cachedShape
   ) {
      return ((ShapeImpl)cachedShape).getPropertyArray();
   }

   static LocationImpl getLocation(Property existing) {
      return (LocationImpl)existing.getLocation();
   }

   @CompilerDirectives.TruffleBoundary
   protected static boolean putUncached(DynamicObject object, Object key, Object value, long putFlags) {
      Shape s = LayoutImpl.ACCESS.getShape(object);
      Property existingProperty = s.getProperty(key);
      if (existingProperty == null && Flags.isSetExisting(putFlags)) {
         return false;
      } else if (existingProperty != null && !Flags.isUpdateFlags(putFlags) && existingProperty.getLocation().canStore(value)) {
         getLocation(existingProperty).setSafe(object, value, false, false);
         return true;
      } else {
         return putUncachedSlow(object, key, value, putFlags);
      }
   }

   private static boolean putUncachedSlow(DynamicObject object, Object key, Object value, long putFlags) {
      CompilerAsserts.neverPartOfCompilation();
      updateShapeImpl(object);

      ShapeImpl oldShape;
      Shape newShape;
      Property property;
      do {
         oldShape = (ShapeImpl)LayoutImpl.ACCESS.getShape(object);
         Property existingProperty = oldShape.getProperty(key);
         if (existingProperty == null) {
            if (Flags.isSetExisting(putFlags)) {
               return false;
            }

            LayoutStrategy strategy = oldShape.getLayoutStrategy();
            newShape = strategy.defineProperty(oldShape, key, value, Flags.getPropertyFlags(putFlags), null, existingProperty, putFlags);
            property = newShape.getProperty(key);
         } else if (Flags.isUpdateFlags(putFlags) && Flags.getPropertyFlags(putFlags) != existingProperty.getFlags()) {
            LayoutStrategy strategy = oldShape.getLayoutStrategy();
            newShape = strategy.defineProperty(oldShape, key, value, Flags.getPropertyFlags(putFlags), null, existingProperty, putFlags);
            property = newShape.getProperty(key);
         } else if (existingProperty.getLocation().canStore(value)) {
            newShape = oldShape;
            property = existingProperty;
         } else {
            LayoutStrategy strategy = oldShape.getLayoutStrategy();
            newShape = strategy.defineProperty(oldShape, key, value, existingProperty.getFlags(), null, existingProperty, putFlags);
            property = newShape.getProperty(key);
         }
      } while (updateShapeImpl(object));

      assert LayoutImpl.ACCESS.getShape(object) == oldShape;

      LocationImpl location = getLocation(property);
      if (oldShape != newShape) {
         LayoutImpl.ACCESS.grow(object, oldShape, newShape);
         location.setSafe(object, value, false, true);
         LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
         updateShapeImpl(object);
      } else {
         location.setSafe(object, value, false, false);
      }

      return true;
   }

   static DynamicObjectLibraryImpl.RemovePlan prepareRemove(ShapeImpl shapeBefore, ShapeImpl shapeAfter) {
      assert !shapeBefore.isShared();

      LayoutStrategy strategy = shapeBefore.getLayoutStrategy();
      List<DynamicObjectLibraryImpl.Move> moves = new ArrayList<>();
      boolean canMoveInPlace = shapeAfter.getObjectArrayCapacity() <= shapeBefore.getObjectArrayCapacity()
         && shapeAfter.getPrimitiveArrayCapacity() <= shapeBefore.getPrimitiveArrayCapacity();

      for (Property to : shapeAfter.getPropertyListInternal(false)) {
         Property from = shapeBefore.getProperty(to.getKey());
         LocationImpl fromLoc = getLocation(from);
         LocationImpl toLoc = getLocation(to);
         if (!LocationImpl.isSameLocation(toLoc, fromLoc)) {
            assert !toLoc.isValue();

            int fromOrd = strategy.getLocationOrdinal(fromLoc);
            int toOrd = strategy.getLocationOrdinal(toLoc);
            DynamicObjectLibraryImpl.Move move = new DynamicObjectLibraryImpl.Move(fromLoc, toLoc, fromOrd, toOrd);
            canMoveInPlace = canMoveInPlace && fromOrd > toOrd;
            moves.add(move);
         }
      }

      if (canMoveInPlace && !isSorted(moves)) {
         Collections.sort(moves);
      }

      return new DynamicObjectLibraryImpl.RemovePlan(moves.toArray(new DynamicObjectLibraryImpl.Move[0]), canMoveInPlace, shapeBefore, shapeAfter);
   }

   private static boolean isSorted(List<DynamicObjectLibraryImpl.Move> moves) {
      for (int i = 1; i < moves.size(); i++) {
         DynamicObjectLibraryImpl.Move m1 = moves.get(i - 1);
         DynamicObjectLibraryImpl.Move m2 = moves.get(i);
         if (m1.compareTo(m2) > 0) {
            return false;
         }
      }

      return true;
   }

   static <T extends DynamicObjectLibraryImpl.CacheData<T>> T filterValid(T cache) {
      if (cache == null) {
         return null;
      } else {
         T filteredNext = filterValid(cache.next);
         if (cache.isValid()) {
            return filteredNext == cache.next ? cache : cache.withNext(filteredNext);
         } else {
            return filteredNext;
         }
      }
   }

   static final class AnyKey extends DynamicObjectLibraryImpl.KeyCacheNode {
      @Node.Child
      private DynamicObjectLibraryImpl.KeyCacheEntry keyCache;

      AnyKey(DynamicObjectLibraryImpl.KeyCacheEntry keyCache) {
         this.keyCache = keyCache;
      }

      public static DynamicObjectLibraryImpl.KeyCacheNode create() {
         return new DynamicObjectLibraryImpl.AnyKey(null);
      }

      public static DynamicObjectLibraryImpl.KeyCacheNode create(Object key, Shape cachedShape) {
         return new DynamicObjectLibraryImpl.AnyKey(DynamicObjectLibraryImpl.SpecificKey.create(key, cachedShape, null, true));
      }

      @ExplodeLoop
      @Override
      public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.getOrDefault(object, cachedShape, key, defaultValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.getOrDefault(object, cachedShape, key, defaultValue);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().getOrDefault(object, cachedShape, key, defaultValue);
      }

      @ExplodeLoop
      @Override
      public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.getIntOrDefault(object, cachedShape, key, defaultValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.getIntOrDefault(object, cachedShape, key, defaultValue);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().getIntOrDefault(object, cachedShape, key, defaultValue);
      }

      @ExplodeLoop
      @Override
      public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.getLongOrDefault(object, cachedShape, key, defaultValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.getLongOrDefault(object, cachedShape, key, defaultValue);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().getLongOrDefault(object, cachedShape, key, defaultValue);
      }

      @ExplodeLoop
      @Override
      public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.getDoubleOrDefault(object, cachedShape, key, defaultValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.getDoubleOrDefault(object, cachedShape, key, defaultValue);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().getDoubleOrDefault(object, cachedShape, key, defaultValue);
      }

      @ExplodeLoop
      @Override
      public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.put(object, cachedShape, key, value, putFlags);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.put(object, cachedShape, key, value, putFlags);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().put(object, cachedShape, key, value, putFlags);
      }

      @ExplodeLoop
      @Override
      public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.containsKey(object, cachedShape, key);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.containsKey(object, cachedShape, key);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().containsKey(object, cachedShape, key);
      }

      @ExplodeLoop
      @Override
      public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.getProperty(object, cachedShape, key);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.getProperty(object, cachedShape, key);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().getProperty(object, cachedShape, key);
      }

      @ExplodeLoop
      @Override
      public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.setPropertyFlags(object, cachedShape, key, propertyFlags);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.setPropertyFlags(object, cachedShape, key, propertyFlags);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().setPropertyFlags(object, cachedShape, key, propertyFlags);
      }

      @ExplodeLoop
      @Override
      public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
         DynamicObjectLibraryImpl.KeyCacheEntry start = this.keyCache;
         if (start != DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
            for (DynamicObjectLibraryImpl.KeyCacheEntry c = start; c != null; c = c.next) {
               if (c.acceptsKey(key)) {
                  return c.removeKey(object, cachedShape, key);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoKeyCache(key, cachedShape);
            if (impl != null) {
               return impl.removeKey(object, cachedShape, key);
            }
         }

         return DynamicObjectLibraryImpl.Generic.instance().removeKey(object, cachedShape, key);
      }

      private DynamicObjectLibraryImpl.KeyCacheNode insertIntoKeyCache(Object key, Shape cachedShape) {
         CompilerAsserts.neverPartOfCompilation();
         Lock lock = this.getLock();
         lock.lock();

         try {
            DynamicObjectLibraryImpl.KeyCacheEntry tail = this.keyCache;
            int cachedCount = 0;
            boolean generic = false;
            boolean useIdentity = true;

            for (DynamicObjectLibraryImpl.KeyCacheEntry c = tail; c != null; c = c.next) {
               if (c == DynamicObjectLibraryImpl.KeyCacheNode.getUncached()) {
                  generic = true;
                  break;
               }

               cachedCount++;
               if (c.acceptsKey(key)) {
                  return c;
               }

               if (!c.isIdentity()) {
                  useIdentity = false;
               }
            }

            if (cachedCount > 1 && useIdentity && hasDuplicateCacheKeys(tail, key)) {
               tail = null;
               cachedCount = 0;
               useIdentity = false;
            }

            if (cachedCount >= 3) {
               generic = true;
               this.keyCache = DynamicObjectLibraryImpl.KeyCacheNode.getUncached();
            }

            if (!generic) {
               DynamicObjectLibraryImpl.SpecificKey newEntry = DynamicObjectLibraryImpl.SpecificKey.create(key, cachedShape, tail, useIdentity);
               this.insert(newEntry);
               this.keyCache = newEntry;
               return this;
            } else {
               return null;
            }
         } finally {
            lock.unlock();
         }
      }

      private static boolean hasDuplicateCacheKeys(DynamicObjectLibraryImpl.KeyCacheEntry tail, Object key) {
         EconomicSet<Object> keySet = EconomicSet.create();

         for (DynamicObjectLibraryImpl.KeyCacheEntry c = tail; c != null; c = c.next) {
            if (c instanceof DynamicObjectLibraryImpl.SpecificKey) {
               DynamicObjectLibraryImpl.SpecificKey cacheEntry = (DynamicObjectLibraryImpl.SpecificKey)c;
               if (!keySet.add(cacheEntry.cachedKey)) {
                  return true;
               }
            }
         }

         return !keySet.add(key);
      }
   }

   abstract static class CacheData<T extends DynamicObjectLibraryImpl.CacheData<T>> {
      final T next;

      CacheData(T next) {
         this.next = next;
      }

      protected boolean isValid() {
         return true;
      }

      protected abstract T withNext(T newNext);
   }

   static final class Generic extends DynamicObjectLibraryImpl.KeyCacheEntry {
      private static final DynamicObjectLibraryImpl.Generic INSTANCE = new DynamicObjectLibraryImpl.Generic();

      Generic() {
         super(null);
      }

      static DynamicObjectLibraryImpl.Generic instance() {
         return INSTANCE;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
         Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
         return existing != null ? DynamicObjectLibraryImpl.getLocation(existing).get(object, false) : defaultValue;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
         return existing != null ? DynamicObjectLibraryImpl.getLocation(existing).getInt(object, false) : LocationImpl.expectInteger(defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
         return existing != null ? DynamicObjectLibraryImpl.getLocation(existing).getLong(object, false) : LocationImpl.expectLong(defaultValue);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
         Property existing = LayoutImpl.ACCESS.getShape(object).getProperty(key);
         return existing != null ? DynamicObjectLibraryImpl.getLocation(existing).getDouble(object, false) : LocationImpl.expectDouble(defaultValue);
      }

      @Override
      public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags) {
         return DynamicObjectLibraryImpl.putUncached(object, key, value, putFlags);
      }

      @Override
      public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
         Property existing = this.getProperty(object, cachedShape, key);
         return existing != null;
      }

      @Override
      public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
         return LayoutImpl.ACCESS.getShape(object).getProperty(key);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
         ShapeImpl oldShape = (ShapeImpl)LayoutImpl.ACCESS.getShape(object);
         Property existingProperty = oldShape.getProperty(key);
         if (existingProperty == null) {
            return false;
         } else {
            if (existingProperty.getFlags() != propertyFlags) {
               DynamicObjectLibraryImpl.updateShapeImpl(object);
               Shape newShape = DynamicObjectLibraryImpl.changePropertyFlags(oldShape, (PropertyImpl)existingProperty, propertyFlags);
               if (newShape != oldShape) {
                  LayoutImpl.ACCESS.setShape(object, newShape);
                  DynamicObjectLibraryImpl.updateShapeImpl(object);
               }
            }

            return true;
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean removeKey(DynamicObject obj, Shape cachedShape, Object key) {
         ShapeImpl oldShape = (ShapeImpl)cachedShape;
         Property property = oldShape.getProperty(key);
         if (property == null) {
            return false;
         } else {
            Map<Object, Object> archive = null;

            assert (archive = LayoutImpl.ACCESS.archive(obj)) != null;

            ShapeImpl newShape = oldShape.removeProperty(property);

            assert oldShape != newShape;

            assert LayoutImpl.ACCESS.getShape(obj) == oldShape;

            if (!oldShape.isShared()) {
               DynamicObjectLibraryImpl.RemovePlan plan = DynamicObjectLibraryImpl.prepareRemove(oldShape, newShape);
               plan.execute(obj);
            } else {
               LayoutImpl.ACCESS.setShape(obj, newShape);
            }

            assert LayoutImpl.ACCESS.verifyValues(obj, archive);

            return true;
         }
      }
   }

   abstract static class KeyCacheEntry extends DynamicObjectLibraryImpl.KeyCacheNode {
      @Node.Child
      DynamicObjectLibraryImpl.KeyCacheEntry next;

      KeyCacheEntry(DynamicObjectLibraryImpl.KeyCacheEntry next) {
         this.next = next;
      }

      public boolean acceptsKey(Object key) {
         return true;
      }
   }

   abstract static class KeyCacheNode extends Node {
      public abstract Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue);

      public abstract int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException;

      public abstract long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException;

      public abstract double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException;

      public abstract boolean put(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags);

      public abstract boolean containsKey(DynamicObject object, Shape cachedShape, Object key);

      public abstract Property getProperty(DynamicObject object, Shape cachedShape, Object key);

      public abstract boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags);

      public abstract boolean removeKey(DynamicObject object, Shape cachedShape, Object key);

      public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value, long putFlags) {
         return this.put(object, cachedShape, key, value, putFlags);
      }

      public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value, long putFlags) {
         return this.put(object, cachedShape, key, value, putFlags);
      }

      public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value, long putFlags) {
         return this.put(object, cachedShape, key, value, putFlags);
      }

      boolean isIdentity() {
         return false;
      }

      static DynamicObjectLibraryImpl.KeyCacheNode create(Shape cachedShape, Object key) {
         return (DynamicObjectLibraryImpl.KeyCacheNode)(key == null ? getUncached() : DynamicObjectLibraryImpl.AnyKey.create(key, cachedShape));
      }

      static DynamicObjectLibraryImpl.KeyCacheEntry getUncached() {
         return DynamicObjectLibraryImpl.Generic.instance();
      }
   }

   @GenerateUncached
   abstract static class MakeSharedNode extends Node {
      abstract void execute(DynamicObject object, Shape cachedShape);

      @Specialization
      static void doCached(DynamicObject object, Shape cachedShape, @Cached(value = "makeSharedShape(cachedShape)", allowUncached = true) Shape newShape) {
         assert newShape != cachedShape
            && ((ShapeImpl)cachedShape).getObjectArrayCapacity() == ((ShapeImpl)newShape).getObjectArrayCapacity()
            && ((ShapeImpl)cachedShape).getPrimitiveArrayCapacity() == ((ShapeImpl)newShape).getPrimitiveArrayCapacity();

         LayoutImpl.ACCESS.setShape(object, newShape);
      }

      static Shape makeSharedShape(Shape inputShape) {
         return ((ShapeImpl)inputShape).makeSharedShape();
      }
   }

   private static final class Move implements Comparable<DynamicObjectLibraryImpl.Move> {
      private final LocationImpl fromLoc;
      private final LocationImpl toLoc;
      private final int fromOrd;
      private final int toOrd;

      Move(LocationImpl fromLoc, LocationImpl toLoc, int fromOrd, int toOrd) {
         this.fromLoc = fromLoc;
         this.toLoc = toLoc;
         this.fromOrd = fromOrd;
         this.toOrd = toOrd;
      }

      void perform(DynamicObject obj) {
         this.performSet(obj, this.performGet(obj));
      }

      Object performGet(DynamicObject obj) {
         return this.fromLoc.get(obj, false);
      }

      void performSet(DynamicObject obj, Object value) {
         this.toLoc.setSafe(obj, value, false, true);
      }

      void clear(DynamicObject obj) {
         this.fromLoc.clear(obj);
      }

      @Override
      public String toString() {
         CompilerAsserts.neverPartOfCompilation();
         return this.fromLoc + " => " + this.toLoc;
      }

      public int compareTo(DynamicObjectLibraryImpl.Move other) {
         int order = Integer.compare(this.fromOrd, other.fromOrd);

         assert order == Integer.compare(this.toOrd, other.toOrd);

         return -order;
      }
   }

   static class MutateCacheData extends DynamicObjectLibraryImpl.CacheData<DynamicObjectLibraryImpl.MutateCacheData> {
      static final DynamicObjectLibraryImpl.MutateCacheData GENERIC = new DynamicObjectLibraryImpl.MutateCacheData(null, null, null);
      final Shape newShape;
      final Assumption newShapeValidAssumption;

      MutateCacheData(DynamicObjectLibraryImpl.MutateCacheData next, Shape newShape, Assumption newShapeValidAssumption) {
         super(next);
         this.newShape = newShape;
         this.newShapeValidAssumption = newShapeValidAssumption;
      }

      @Override
      protected boolean isValid() {
         Assumption newShapeValid = this.newShapeValidAssumption;
         return newShapeValid == Assumption.NEVER_VALID || newShapeValid == Assumption.ALWAYS_VALID || newShapeValid.isValid();
      }

      protected void maybeUpdateShape(DynamicObject store) {
         if (this.newShapeValidAssumption == Assumption.NEVER_VALID) {
            DynamicObjectLibraryImpl.updateShapeImpl(store);
         }
      }

      protected DynamicObjectLibraryImpl.MutateCacheData withNext(DynamicObjectLibraryImpl.MutateCacheData newNext) {
         return new DynamicObjectLibraryImpl.MutateCacheData(this.next, this.newShape, this.newShapeValidAssumption);
      }
   }

   static class PutCacheData extends DynamicObjectLibraryImpl.MutateCacheData {
      final long putFlags;
      final Property property;

      PutCacheData(long putFlags, Shape newShape, Assumption newShapeValidAssumption, Property property, DynamicObjectLibraryImpl.MutateCacheData next) {
         super(next, newShape, newShapeValidAssumption);
         this.putFlags = putFlags;
         this.property = property;
      }

      @Override
      protected DynamicObjectLibraryImpl.MutateCacheData withNext(DynamicObjectLibraryImpl.MutateCacheData newNext) {
         return new DynamicObjectLibraryImpl.PutCacheData(this.putFlags, this.newShape, this.newShapeValidAssumption, this.property, newNext);
      }
   }

   private static final class RemovePlan {
      private static final int MAX_UNROLL = 32;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final DynamicObjectLibraryImpl.Move[] moves;
      private final boolean canMoveInPlace;
      private final Shape shapeBefore;
      private final Shape shapeAfter;

      RemovePlan(DynamicObjectLibraryImpl.Move[] moves, boolean canMoveInPlace, Shape shapeBefore, Shape shapeAfter) {
         this.moves = moves;
         this.canMoveInPlace = canMoveInPlace;
         this.shapeBefore = shapeBefore;
         this.shapeAfter = shapeAfter;
      }

      void execute(DynamicObject object) {
         CompilerAsserts.partialEvaluationConstant(this.moves.length);
         if (CompilerDirectives.inCompiledCode() && this.moves.length <= 32) {
            this.perform(object);
         } else {
            this.performBoundary(object);
         }
      }

      @ExplodeLoop
      void perform(DynamicObject object) {
         CompilerAsserts.partialEvaluationConstant(this.moves.length);
         if (this.canMoveInPlace) {
            for (int i = this.moves.length - 1; i >= 0; i--) {
               this.moves[i].perform(object);
               if (i == 0) {
                  this.moves[i].clear(object);
               }
            }

            LayoutImpl.ACCESS.trimToSize(object, this.shapeBefore, this.shapeAfter);
            LayoutImpl.ACCESS.setShape(object, this.shapeAfter);
         } else {
            Object[] tempValues = new Object[this.moves.length];

            for (int ix = this.moves.length - 1; ix >= 0; ix--) {
               tempValues[ix] = this.moves[ix].performGet(object);
               this.moves[ix].clear(object);
            }

            LayoutImpl.ACCESS.resize(object, this.shapeBefore, this.shapeAfter);

            for (int ix = this.moves.length - 1; ix >= 0; ix--) {
               this.moves[ix].performSet(object, tempValues[ix]);
            }

            LayoutImpl.ACCESS.setShape(object, this.shapeAfter);
         }
      }

      @CompilerDirectives.TruffleBoundary
      void performBoundary(DynamicObject object) {
         this.perform(object);
      }
   }

   static class RemovePropertyCacheData extends DynamicObjectLibraryImpl.MutateCacheData {
      final DynamicObjectLibraryImpl.RemovePlan removePlan;

      RemovePropertyCacheData(
         Shape newShape, Assumption newShapeValidAssumption, DynamicObjectLibraryImpl.RemovePlan removePlan, DynamicObjectLibraryImpl.MutateCacheData next
      ) {
         super(next, newShape, newShapeValidAssumption);
         this.removePlan = removePlan;
      }

      @Override
      protected DynamicObjectLibraryImpl.MutateCacheData withNext(DynamicObjectLibraryImpl.MutateCacheData newNext) {
         return new DynamicObjectLibraryImpl.RemovePropertyCacheData(this.newShape, this.newShapeValidAssumption, this.removePlan, newNext);
      }
   }

   @GenerateUncached
   abstract static class ResetShapeNode extends Node {
      abstract boolean execute(DynamicObject object, Shape cachedShape, Shape newShape);

      @Specialization(guards = "otherShape == cachedOtherShape")
      static boolean doCached(
         DynamicObject object,
         Shape cachedShape,
         Shape otherShape,
         @Cached(value = "verifyResetShape(cachedShape, otherShape)", allowUncached = true) Shape cachedOtherShape
      ) {
         if (cachedShape == cachedOtherShape) {
            return false;
         } else {
            LayoutImpl.ACCESS.resize(object, cachedShape, cachedOtherShape);
            LayoutImpl.ACCESS.setShape(object, cachedOtherShape);
            return true;
         }
      }

      static Shape verifyResetShape(Shape currentShape, Shape otherShape) {
         if (((ShapeImpl)otherShape).hasInstanceProperties()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Shape must not contain any instance properties.");
         } else {
            if (currentShape != otherShape) {
               LayoutImpl.ACCESS.invalidateAllPropertyAssumptions(currentShape);
            }

            return otherShape;
         }
      }
   }

   @GenerateUncached
   abstract static class SetDynamicTypeNode extends Node {
      abstract boolean execute(DynamicObject object, Shape cachedShape, Object objectType);

      @Specialization(guards = "objectType == newObjectType", limit = "3")
      static boolean doCached(
         DynamicObject object,
         Shape cachedShape,
         Object objectType,
         @Cached(value = "objectType", allowUncached = true) Object newObjectType,
         @Cached(value = "shapeSetDynamicType(cachedShape, newObjectType)", allowUncached = true) Shape newShape
      ) {
         if (newShape != cachedShape) {
            LayoutImpl.ACCESS.setShape(object, newShape);
            return true;
         } else {
            return false;
         }
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(DynamicObject object, Shape cachedShape, Object objectType) {
         Shape newShape = shapeSetDynamicType(cachedShape, objectType);
         if (newShape != cachedShape) {
            LayoutImpl.ACCESS.setShape(object, newShape);
            return true;
         } else {
            return false;
         }
      }

      static Shape shapeSetDynamicType(Shape shape, Object newType) {
         return ((ShapeImpl)shape).setDynamicType(newType);
      }
   }

   @GenerateUncached
   abstract static class SetFlagsNode extends Node {
      abstract boolean execute(DynamicObject object, Shape cachedShape, int flags);

      @Specialization(guards = "flags == newFlags", limit = "3")
      static boolean doCached(
         DynamicObject object,
         Shape cachedShape,
         int flags,
         @Cached(value = "flags", allowUncached = true) int newFlags,
         @Cached(value = "shapeSetFlags(cachedShape, newFlags)", allowUncached = true) Shape newShape
      ) {
         if (newShape != cachedShape) {
            LayoutImpl.ACCESS.setShape(object, newShape);
            return true;
         } else {
            return false;
         }
      }

      @Specialization(replaces = "doCached")
      static boolean doUncached(DynamicObject object, Shape cachedShape, int flags) {
         Shape newShape = shapeSetFlags(cachedShape, flags);
         if (newShape != cachedShape) {
            LayoutImpl.ACCESS.setShape(object, newShape);
            return true;
         } else {
            return false;
         }
      }

      static Shape shapeSetFlags(Shape shape, int newFlags) {
         return ((ShapeImpl)shape).setFlags(newFlags);
      }
   }

   static class SetPropertyFlagsCacheData extends DynamicObjectLibraryImpl.MutateCacheData {
      final Property property;

      SetPropertyFlagsCacheData(Shape newShape, Assumption newShapeValidAssumption, Property property, DynamicObjectLibraryImpl.MutateCacheData next) {
         super(next, newShape, newShapeValidAssumption);
         this.property = property;
      }

      @Override
      protected DynamicObjectLibraryImpl.MutateCacheData withNext(DynamicObjectLibraryImpl.MutateCacheData newNext) {
         return new DynamicObjectLibraryImpl.SetPropertyFlagsCacheData(this.newShape, this.newShapeValidAssumption, this.property, newNext);
      }
   }

   abstract static class SpecificKey extends DynamicObjectLibraryImpl.KeyCacheEntry {
      final Object cachedKey;
      @CompilerDirectives.CompilationFinal
      DynamicObjectLibraryImpl.MutateCacheData cache;

      SpecificKey(Object key, DynamicObjectLibraryImpl.KeyCacheEntry next) {
         super(next);
         this.cachedKey = key;
      }

      static DynamicObjectLibraryImpl.SpecificKey create(Object key, Shape shape, DynamicObjectLibraryImpl.KeyCacheEntry next, boolean useIdentity) {
         if (key != null) {
            Property property = shape.getProperty(key);
            if (property != null) {
               return (DynamicObjectLibraryImpl.SpecificKey)(useIdentity
                  ? new DynamicObjectLibraryImpl.SpecificKey.ExistingKeyIdentity(key, property, next)
                  : new DynamicObjectLibraryImpl.SpecificKey.ExistingKey(key, property, next));
            }
         }

         return (DynamicObjectLibraryImpl.SpecificKey)(useIdentity
            ? new DynamicObjectLibraryImpl.SpecificKey.MissingKeyIdentity(key, next)
            : new DynamicObjectLibraryImpl.SpecificKey.MissingKey(key, next));
      }

      protected final boolean assertCachedKeyAndShapeForRead(DynamicObject object, Shape cachedShape, Object key) {
         assert object.getShape() == cachedShape || cachedShape.isShared();

         assert DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key);

         return true;
      }

      protected final boolean assertCachedKeyAndShapeForWrite(DynamicObject object, Shape cachedShape, Object key) {
         assert object.getShape() == cachedShape;

         assert DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key);

         return true;
      }

      @Override
      public boolean acceptsKey(Object key) {
         return DynamicObjectLibraryImpl.keyEquals(this.cachedKey, key);
      }

      @ExplodeLoop
      protected boolean putImpl(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags, Property oldProperty) {
         Shape oldShape = cachedShape;
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start != DynamicObjectLibraryImpl.MutateCacheData.GENERIC && cachedShape.isValid()) {
            DynamicObjectLibraryImpl.MutateCacheData c = start;

            while (c != null && c.isValid()) {
               label46: {
                  if (c instanceof DynamicObjectLibraryImpl.PutCacheData && ((DynamicObjectLibraryImpl.PutCacheData)c).putFlags == putFlags) {
                     Property newProperty = ((DynamicObjectLibraryImpl.PutCacheData)c).property;
                     if (newProperty == null) {
                        assert Flags.isSetExisting(putFlags);

                        return false;
                     }

                     LocationImpl location = DynamicObjectLibraryImpl.getLocation(newProperty);
                     boolean guardCondition = object.getShape() == oldShape;
                     if (location.canStore(value)) {
                        Shape newShape = c.newShape;
                        if (newShape != oldShape) {
                           LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                           location.setSafe(object, value, guardCondition, true);
                           LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                           break label46;
                        }

                        if (!location.isFinal()) {
                           location.setSafe(object, value, guardCondition, false);
                           break label46;
                        }
                     }
                  }

                  c = c.next;
                  continue;
               }

               c.maybeUpdateShape(object);
               return true;
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value, putFlags, oldProperty);
            return impl.put(object, cachedShape, key, value, putFlags);
         } else {
            return DynamicObjectLibraryImpl.putUncached(object, key, value, putFlags);
         }
      }

      @ExplodeLoop
      protected boolean putIntImpl(DynamicObject object, Shape cachedShape, Object key, int value, long putFlags, Property oldProperty) {
         Shape oldShape = cachedShape;
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start != DynamicObjectLibraryImpl.MutateCacheData.GENERIC && cachedShape.isValid()) {
            DynamicObjectLibraryImpl.MutateCacheData c = start;

            while (c != null && c.isValid()) {
               label85: {
                  label84: {
                     label83: {
                        label82: {
                           if (c instanceof DynamicObjectLibraryImpl.PutCacheData && ((DynamicObjectLibraryImpl.PutCacheData)c).putFlags == putFlags) {
                              Property newProperty = ((DynamicObjectLibraryImpl.PutCacheData)c).property;
                              if (newProperty == null) {
                                 assert Flags.isSetExisting(putFlags);

                                 return false;
                              }

                              LocationImpl location = DynamicObjectLibraryImpl.getLocation(newProperty);
                              Shape newShape = c.newShape;
                              boolean guardCondition = object.getShape() == oldShape;
                              if (location.isIntLocation()) {
                                 if (newShape != oldShape) {
                                    LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                    location.setIntSafe(object, value, guardCondition, true);
                                    LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                    break label85;
                                 }

                                 if (!location.isFinal()) {
                                    location.setIntSafe(object, value, guardCondition, false);
                                    break label85;
                                 }
                              } else if (location.isImplicitCastIntToLong()) {
                                 if (newShape != oldShape) {
                                    LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                    location.setLongSafe(object, value, guardCondition, true);
                                    LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                    break label84;
                                 }

                                 if (!location.isFinal()) {
                                    location.setLongSafe(object, value, guardCondition, false);
                                    break label84;
                                 }
                              } else if (location.isImplicitCastIntToDouble()) {
                                 if (newShape != oldShape) {
                                    LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                    location.setDoubleSafe(object, value, guardCondition, true);
                                    LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                    break label83;
                                 }

                                 if (!location.isFinal()) {
                                    location.setDoubleSafe(object, value, guardCondition, false);
                                    break label83;
                                 }
                              } else if (location.canStore(value)) {
                                 if (newShape != oldShape) {
                                    LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                                    location.setSafe(object, value, guardCondition, true);
                                    LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                                    break label82;
                                 }

                                 if (!location.isFinal()) {
                                    location.setSafe(object, value, guardCondition, false);
                                    break label82;
                                 }
                              }
                           }

                           c = c.next;
                           continue;
                        }

                        c.maybeUpdateShape(object);
                        return true;
                     }

                     c.maybeUpdateShape(object);
                     return true;
                  }

                  c.maybeUpdateShape(object);
                  return true;
               }

               c.maybeUpdateShape(object);
               return true;
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value, putFlags, oldProperty);
            return impl.putInt(object, cachedShape, key, value, putFlags);
         } else {
            return DynamicObjectLibraryImpl.putUncached(object, key, value, putFlags);
         }
      }

      @ExplodeLoop
      protected boolean putLongImpl(DynamicObject object, Shape cachedShape, Object key, long value, long putFlags, Property oldProperty) {
         Shape oldShape = cachedShape;
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start == DynamicObjectLibraryImpl.MutateCacheData.GENERIC) {
            return DynamicObjectLibraryImpl.putUncached(object, key, value, putFlags);
         } else {
            DynamicObjectLibraryImpl.MutateCacheData c = start;

            while (c != null) {
               label59: {
                  label58: {
                     if (c instanceof DynamicObjectLibraryImpl.PutCacheData && ((DynamicObjectLibraryImpl.PutCacheData)c).putFlags == putFlags) {
                        Property newProperty = ((DynamicObjectLibraryImpl.PutCacheData)c).property;
                        if (newProperty == null) {
                           assert Flags.isSetExisting(putFlags);

                           return false;
                        }

                        LocationImpl location = DynamicObjectLibraryImpl.getLocation(newProperty);
                        boolean guardCondition = object.getShape() == oldShape;
                        if (location.isLongLocation()) {
                           Shape newShape = c.newShape;
                           if (newShape != oldShape) {
                              LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                              location.setLongSafe(object, value, guardCondition, true);
                              LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                              break label59;
                           }

                           if (!location.isFinal()) {
                              location.setLongSafe(object, value, guardCondition, false);
                              break label59;
                           }
                        } else if (location.canStore(value)) {
                           Shape newShapex = c.newShape;
                           if (newShapex != oldShape) {
                              LayoutImpl.ACCESS.grow(object, oldShape, newShapex);
                              location.setSafe(object, value, guardCondition, true);
                              LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShapex);
                              break label58;
                           }

                           if (!location.isFinal()) {
                              location.setSafe(object, value, guardCondition, false);
                              break label58;
                           }
                        }
                     }

                     c = c.next;
                     continue;
                  }

                  c.maybeUpdateShape(object);
                  return true;
               }

               c.maybeUpdateShape(object);
               return true;
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value, putFlags, oldProperty);
            return impl.putLong(object, cachedShape, key, value, putFlags);
         }
      }

      @ExplodeLoop
      protected boolean putDoubleImpl(DynamicObject object, Shape cachedShape, Object key, double value, long putFlags, Property oldProperty) {
         Shape oldShape = cachedShape;
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start == DynamicObjectLibraryImpl.MutateCacheData.GENERIC) {
            return DynamicObjectLibraryImpl.putUncached(object, key, value, putFlags);
         } else {
            DynamicObjectLibraryImpl.MutateCacheData c = start;

            while (c != null) {
               label59: {
                  label58: {
                     if (c instanceof DynamicObjectLibraryImpl.PutCacheData && ((DynamicObjectLibraryImpl.PutCacheData)c).putFlags == putFlags) {
                        Property newProperty = ((DynamicObjectLibraryImpl.PutCacheData)c).property;
                        if (newProperty == null) {
                           assert Flags.isSetExisting(putFlags);

                           return false;
                        }

                        LocationImpl location = DynamicObjectLibraryImpl.getLocation(newProperty);
                        boolean guardCondition = object.getShape() == oldShape;
                        if (location.isDoubleLocation()) {
                           Shape newShape = c.newShape;
                           if (newShape != oldShape) {
                              LayoutImpl.ACCESS.grow(object, oldShape, newShape);
                              location.setDoubleSafe(object, value, guardCondition, true);
                              LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShape);
                              break label59;
                           }

                           if (!location.isFinal()) {
                              location.setDoubleSafe(object, value, guardCondition, false);
                              break label59;
                           }
                        } else if (newProperty.getLocation().canStore(value)) {
                           Shape newShapex = c.newShape;
                           if (newShapex != oldShape) {
                              LayoutImpl.ACCESS.grow(object, oldShape, newShapex);
                              location.setSafe(object, value, guardCondition, true);
                              LayoutImpl.ACCESS.setShapeWithStoreFence(object, newShapex);
                              break label58;
                           }

                           if (!location.isFinal()) {
                              location.setSafe(object, value, guardCondition, false);
                              break label58;
                           }
                        }
                     }

                     c = c.next;
                     continue;
                  }

                  c.maybeUpdateShape(object);
                  return true;
               }

               c.maybeUpdateShape(object);
               return true;
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoPutCache(object, cachedShape, value, putFlags, oldProperty);
            return impl.putDouble(object, cachedShape, key, value, putFlags);
         }
      }

      protected DynamicObjectLibraryImpl.KeyCacheNode insertIntoPutCache(
         DynamicObject object, Shape cachedShape, Object value, long putFlags, Property property
      ) {
         CompilerAsserts.neverPartOfCompilation();
         if (!cachedShape.isValid()) {
            return DynamicObjectLibraryImpl.Generic.instance();
         } else {
            Lock lock = this.getLock();
            lock.lock();

            DynamicObjectLibraryImpl.Generic newProperty;
            try {
               DynamicObjectLibraryImpl.MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
               ShapeImpl oldShape = (ShapeImpl)cachedShape;
               ShapeImpl newShape = this.getNewShape(object, value, putFlags, property, oldShape);
               if (oldShape.isValid()) {
                  Property newPropertyx;
                  if (newShape == oldShape) {
                     newPropertyx = property;
                  } else {
                     newPropertyx = newShape.getProperty(this.cachedKey);

                     assert newPropertyx.getLocation().canStore(value);
                  }

                  Assumption newShapeValid = getShapeValidAssumption(oldShape, newShape);
                  this.cache = new DynamicObjectLibraryImpl.PutCacheData(putFlags, newShape, newShapeValid, newPropertyx, tail);
                  return this;
               }

               newProperty = DynamicObjectLibraryImpl.Generic.instance();
            } finally {
               lock.unlock();
            }

            return newProperty;
         }
      }

      private ShapeImpl getNewShape(DynamicObject object, Object value, long putFlags, Property property, ShapeImpl oldShape) {
         if (property == null) {
            if (Flags.isSetExisting(putFlags)) {
               return oldShape;
            } else {
               int propertyFlags = Flags.getPropertyFlags(putFlags);
               LayoutStrategy strategy = oldShape.getLayoutStrategy();
               return strategy.defineProperty(oldShape, this.cachedKey, value, propertyFlags, null, putFlags);
            }
         } else if (Flags.isUpdateFlags(putFlags) && Flags.getPropertyFlags(putFlags) != property.getFlags()) {
            int propertyFlags = Flags.getPropertyFlags(putFlags);
            LayoutStrategy strategy = oldShape.getLayoutStrategy();
            return strategy.defineProperty(oldShape, this.cachedKey, value, propertyFlags, null, putFlags);
         } else {
            Location location = property.getLocation();
            if (!location.isDeclared() && !location.canStore(value)) {
               assert oldShape == LayoutImpl.ACCESS.getShape(object);

               LayoutStrategy strategy = oldShape.getLayoutStrategy();
               ShapeImpl newShape = strategy.definePropertyGeneralize(oldShape, property, value, null, putFlags);

               assert newShape != oldShape;

               return newShape;
            } else if (location.isDeclared()) {
               LayoutStrategy strategy = oldShape.getLayoutStrategy();
               return strategy.defineProperty(oldShape, this.cachedKey, value, property.getFlags(), null, putFlags);
            } else {
               assert location.canStore(value);

               return oldShape;
            }
         }
      }

      @ExplodeLoop
      protected boolean setPropertyFlagsImpl(DynamicObject object, Shape cachedShape, Object key, int propertyFlags, Property cachedProperty) {
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start != DynamicObjectLibraryImpl.MutateCacheData.GENERIC && cachedShape.isValid()) {
            for (DynamicObjectLibraryImpl.MutateCacheData c = start; c != null && c.isValid(); c = c.next) {
               if (c instanceof DynamicObjectLibraryImpl.SetPropertyFlagsCacheData
                  && ((DynamicObjectLibraryImpl.SetPropertyFlagsCacheData)c).property.getFlags() == propertyFlags) {
                  if (cachedProperty == null) {
                     return false;
                  }

                  if (cachedProperty.getFlags() != propertyFlags) {
                     Shape newShape = c.newShape;
                     if (newShape != cachedShape) {
                        LayoutImpl.ACCESS.setShape(object, newShape);
                        c.maybeUpdateShape(object);
                     }
                  }

                  return true;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoSetPropertyFlagsCache(cachedShape, propertyFlags, cachedProperty);
            return impl.setPropertyFlags(object, cachedShape, key, propertyFlags);
         } else {
            return DynamicObjectLibraryImpl.Generic.instance().setPropertyFlags(object, cachedShape, key, propertyFlags);
         }
      }

      protected DynamicObjectLibraryImpl.KeyCacheNode insertIntoSetPropertyFlagsCache(Shape cachedShape, int propertyFlags, Property cachedProperty) {
         CompilerAsserts.neverPartOfCompilation();
         if (!cachedShape.isValid()) {
            return DynamicObjectLibraryImpl.Generic.instance();
         } else {
            Lock lock = this.getLock();
            lock.lock();

            DynamicObjectLibraryImpl.Generic newProperty;
            try {
               DynamicObjectLibraryImpl.MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
               ShapeImpl oldShape = (ShapeImpl)cachedShape;
               ShapeImpl newShape = DynamicObjectLibraryImpl.changePropertyFlags(oldShape, (PropertyImpl)cachedProperty, propertyFlags);
               if (oldShape.isValid()) {
                  Property newPropertyx;
                  if (newShape == oldShape) {
                     newPropertyx = cachedProperty;
                  } else {
                     newPropertyx = newShape.getProperty(this.cachedKey);
                  }

                  Assumption newShapeValid = getShapeValidAssumption(oldShape, newShape);
                  this.cache = new DynamicObjectLibraryImpl.SetPropertyFlagsCacheData(newShape, newShapeValid, newPropertyx, tail);
                  return this;
               }

               newProperty = DynamicObjectLibraryImpl.Generic.instance();
            } finally {
               lock.unlock();
            }

            return newProperty;
         }
      }

      @ExplodeLoop
      protected boolean removeKeyImpl(DynamicObject object, Shape cachedShape, Object key, Property cachedProperty) {
         DynamicObjectLibraryImpl.MutateCacheData start = this.cache;
         if (start != DynamicObjectLibraryImpl.MutateCacheData.GENERIC && cachedShape.isValid()) {
            for (DynamicObjectLibraryImpl.MutateCacheData c = start; c != null && c.isValid(); c = c.next) {
               if (c instanceof DynamicObjectLibraryImpl.RemovePropertyCacheData) {
                  if (cachedProperty == null) {
                     return false;
                  }

                  Shape newShape = c.newShape;

                  assert newShape != cachedShape;

                  Map<Object, Object> archive = null;

                  assert (archive = LayoutImpl.ACCESS.archive(object)) != null;

                  if (!cachedShape.isShared()) {
                     ((DynamicObjectLibraryImpl.RemovePropertyCacheData)c).removePlan.execute(object);
                  } else {
                     LayoutImpl.ACCESS.setShape(object, newShape);
                  }

                  assert LayoutImpl.ACCESS.verifyValues(object, archive);

                  c.maybeUpdateShape(object);
                  return true;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicObjectLibraryImpl.KeyCacheNode impl = this.insertIntoRemoveKeyCache(cachedShape, cachedProperty);
            return impl.removeKey(object, cachedShape, key);
         } else {
            return DynamicObjectLibraryImpl.Generic.instance().removeKey(object, cachedShape, key);
         }
      }

      protected DynamicObjectLibraryImpl.KeyCacheNode insertIntoRemoveKeyCache(Shape cachedShape, Property cachedProperty) {
         CompilerAsserts.neverPartOfCompilation();
         if (!cachedShape.isValid()) {
            return DynamicObjectLibraryImpl.Generic.instance();
         } else {
            Lock lock = this.getLock();
            lock.lock();

            DynamicObjectLibraryImpl.Generic removePlan;
            try {
               DynamicObjectLibraryImpl.MutateCacheData tail = DynamicObjectLibraryImpl.filterValid(this.cache);
               ShapeImpl oldShape = (ShapeImpl)cachedShape;
               ShapeImpl newShape = oldShape.removeProperty(cachedProperty);
               if (oldShape.isValid()) {
                  DynamicObjectLibraryImpl.RemovePlan removePlanx = null;
                  if (!oldShape.isShared()) {
                     removePlanx = DynamicObjectLibraryImpl.prepareRemove(oldShape, newShape);
                  }

                  Assumption newShapeValid = getShapeValidAssumption(oldShape, newShape);
                  this.cache = new DynamicObjectLibraryImpl.RemovePropertyCacheData(newShape, newShapeValid, removePlanx, tail);
                  return this;
               }

               removePlan = DynamicObjectLibraryImpl.Generic.instance();
            } finally {
               lock.unlock();
            }

            return removePlan;
         }
      }

      private static Assumption getShapeValidAssumption(Shape oldShape, Shape newShape) {
         if (oldShape == newShape) {
            return Assumption.ALWAYS_VALID;
         } else {
            return newShape.isValid() ? newShape.getValidAssumption() : Assumption.NEVER_VALID;
         }
      }

      static class ExistingKey extends DynamicObjectLibraryImpl.SpecificKey {
         final Property cachedProperty;

         ExistingKey(Object key, Property property, DynamicObjectLibraryImpl.KeyCacheEntry next) {
            super(key, next);
            this.cachedProperty = property;
         }

         private static boolean guard(DynamicObject object, Shape cachedShape) {
            return object.getShape() == cachedShape;
         }

         @Override
         public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).get(object, guard(object, cachedShape));
         }

         @Override
         public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getInt(object, guard(object, cachedShape));
         }

         @Override
         public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getLong(object, guard(object, cachedShape));
         }

         @Override
         public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return DynamicObjectLibraryImpl.getLocation(this.cachedProperty).getDouble(object, guard(object, cachedShape));
         }

         @Override
         public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putImpl(object, cachedShape, key, value, putFlags, this.cachedProperty);
         }

         @Override
         public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putIntImpl(object, cachedShape, key, value, putFlags, this.cachedProperty);
         }

         @Override
         public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putLongImpl(object, cachedShape, key, value, putFlags, this.cachedProperty);
         }

         @Override
         public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putDoubleImpl(object, cachedShape, key, value, putFlags, this.cachedProperty);
         }

         @Override
         public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return true;
         }

         @Override
         public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return this.cachedProperty;
         }

         @Override
         public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.setPropertyFlagsImpl(object, cachedShape, key, propertyFlags, this.cachedProperty);
         }

         @Override
         public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.removeKeyImpl(object, cachedShape, key, this.cachedProperty);
         }
      }

      static final class ExistingKeyIdentity extends DynamicObjectLibraryImpl.SpecificKey.ExistingKey {
         ExistingKeyIdentity(Object key, Property property, DynamicObjectLibraryImpl.KeyCacheEntry next) {
            super(key, property, next);
         }

         @Override
         public boolean acceptsKey(Object key) {
            return this.cachedKey == key;
         }

         @Override
         boolean isIdentity() {
            return true;
         }
      }

      static class MissingKey extends DynamicObjectLibraryImpl.SpecificKey {
         MissingKey(Object key, DynamicObjectLibraryImpl.KeyCacheEntry next) {
            super(key, next);
         }

         @Override
         public Object getOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return defaultValue;
         }

         @Override
         public boolean put(DynamicObject object, Shape cachedShape, Object key, Object value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putImpl(object, cachedShape, key, value, putFlags, null);
         }

         @Override
         public boolean putInt(DynamicObject object, Shape cachedShape, Object key, int value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putIntImpl(object, cachedShape, key, value, putFlags, null);
         }

         @Override
         public boolean putLong(DynamicObject object, Shape cachedShape, Object key, long value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putLongImpl(object, cachedShape, key, value, putFlags, null);
         }

         @Override
         public boolean putDouble(DynamicObject object, Shape cachedShape, Object key, double value, long putFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return this.putDoubleImpl(object, cachedShape, key, value, putFlags, null);
         }

         @Override
         public boolean containsKey(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return false;
         }

         @Override
         public Property getProperty(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForRead(object, cachedShape, key);

            return null;
         }

         @Override
         public int getIntOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            return LocationImpl.expectInteger(defaultValue);
         }

         @Override
         public long getLongOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            return LocationImpl.expectLong(defaultValue);
         }

         @Override
         public double getDoubleOrDefault(DynamicObject object, Shape cachedShape, Object key, Object defaultValue) throws UnexpectedResultException {
            return LocationImpl.expectDouble(defaultValue);
         }

         @Override
         public boolean setPropertyFlags(DynamicObject object, Shape cachedShape, Object key, int propertyFlags) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return false;
         }

         @Override
         public boolean removeKey(DynamicObject object, Shape cachedShape, Object key) {
            CompilerAsserts.partialEvaluationConstant(cachedShape);

            assert this.assertCachedKeyAndShapeForWrite(object, cachedShape, key);

            return false;
         }
      }

      static final class MissingKeyIdentity extends DynamicObjectLibraryImpl.SpecificKey.MissingKey {
         MissingKeyIdentity(Object key, DynamicObjectLibraryImpl.KeyCacheEntry next) {
            super(key, next);
         }

         @Override
         public boolean acceptsKey(Object key) {
            return this.cachedKey == key;
         }

         @Override
         boolean isIdentity() {
            return true;
         }
      }
   }
}
