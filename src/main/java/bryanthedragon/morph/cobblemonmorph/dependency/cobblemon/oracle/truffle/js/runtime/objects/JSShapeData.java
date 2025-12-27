package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiablePropertyKeyList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JSShapeData {
   private static final Property[] EMPTY_PROPERTY_ARRAY = new Property[0];
   private static final TruffleString[] EMPTY_STRING_ARRAY = new TruffleString[0];
   private static final int UNKNOWN = -1;
   private int symbolsStartPos = -1;
   private Property[] propertyArray;
   private TruffleString[] enumerablePropertyNames;
   private static final DebugCounter enumerablePropertyListAllocCount = DebugCounter.create("Enumerable property lists allocated");
   private static final DebugCounter propertyListAllocCount = DebugCounter.create("Property lists allocated");

   private JSShapeData() {
   }

   private static Property[] createPropertiesArray(Shape shape) {
      CompilerAsserts.neverPartOfCompilation();
      propertyListAllocCount.inc();
      List<Property> ownProperties = shape.getPropertyList();
      sortProperties(ownProperties);
      return ownProperties.toArray(EMPTY_PROPERTY_ARRAY);
   }

   private static TruffleString[] createEnumerablePropertyNamesArray(Shape shape) {
      CompilerAsserts.neverPartOfCompilation();
      enumerablePropertyListAllocCount.inc();
      List<TruffleString> ownProperties = new ArrayList<>();
      shape.getPropertyList().forEach(property -> {
         if (JSProperty.isEnumerable(property) && Strings.isTString(property.getKey())) {
            ownProperties.add((TruffleString)property.getKey());
         }
      });
      sortPropertyKeys(ownProperties);
      return ownProperties.toArray(EMPTY_STRING_ARRAY);
   }

   private static void sortProperties(List<Property> ownProperties) {
      CompilerAsserts.neverPartOfCompilation();
      Collections.sort(ownProperties, (o1, o2) -> JSRuntime.comparePropertyKeys(o1.getKey(), o2.getKey()));
   }

   private static void sortPropertyKeys(List<? extends Object> ownProperties) {
      CompilerAsserts.neverPartOfCompilation();
      Collections.sort(ownProperties, JSRuntime::comparePropertyKeys);
   }

   private static JSShapeData getShapeData(Shape shape) {
      CompilerAsserts.neverPartOfCompilation();
      JSContext context = JSShape.getJSContext(shape);
      synchronized (context) {
         Map<Shape, JSShapeData> map = context.getShapeDataMap();
         JSShapeData shapeData = map.get(shape);
         if (shapeData == null) {
            shapeData = new JSShapeData();
            map.put(shape, shapeData);
         }

         return shapeData;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Property[] getPropertiesArray(Shape shape) {
      assert shape.getPropertyCount() != 0;

      return getPropertiesArray(getShapeData(shape), shape);
   }

   private static Property[] getPropertiesArray(JSShapeData shapeData, Shape shape) {
      Property[] propertyArray = shapeData.propertyArray;
      if (propertyArray == null) {
         propertyArray = createPropertiesArray(shape);

         assert propertyArray.length == shape.getPropertyCount();

         shapeData.propertyArray = propertyArray;
      }

      return propertyArray;
   }

   private static int getSymbolsStart(JSShapeData shapeData, Property[] propertyArray) {
      int symbolsStart = shapeData.symbolsStartPos;
      if (symbolsStart == -1) {
         shapeData.symbolsStartPos = symbolsStart = getSymbolsStart(propertyArray);
      }

      return symbolsStart;
   }

   private static int getSymbolsStart(Property[] propertyArray) {
      int pos;
      for (pos = propertyArray.length; pos > 0; pos--) {
         Property prev = propertyArray[pos - 1];
         if (!(prev.getKey() instanceof Symbol)) {
            break;
         }
      }

      return pos;
   }

   static UnmodifiableArrayList<Property> getProperties(Shape shape) {
      return asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_PROPERTY_ARRAY : getPropertiesArray(shape));
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString[] getEnumerablePropertyNamesArray(Shape shape) {
      assert shape.getPropertyCount() != 0;

      return getEnumerablePropertyNamesArray(getShapeData(shape), shape);
   }

   private static TruffleString[] getEnumerablePropertyNamesArray(JSShapeData shapeData, Shape shape) {
      TruffleString[] enumeratePropertyNames = shapeData.enumerablePropertyNames;
      if (enumeratePropertyNames == null) {
         enumeratePropertyNames = createEnumerablePropertyNamesArray(shape);
         shapeData.enumerablePropertyNames = enumeratePropertyNames;
      }

      return enumeratePropertyNames;
   }

   static UnmodifiableArrayList<TruffleString> getEnumerablePropertyNames(Shape shape) {
      return asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_STRING_ARRAY : getEnumerablePropertyNamesArray(shape));
   }

   @CompilerDirectives.TruffleBoundary
   private static Property[] getPropertiesArrayIfHasEnumerablePropertyNames(Shape shape) {
      assert shape.getPropertyCount() != 0;

      JSShapeData shapeData = getShapeData(shape);
      return getEnumerablePropertyNamesArray(shapeData, shape).length == 0 ? EMPTY_PROPERTY_ARRAY : getPropertiesArray(shapeData, shape);
   }

   static UnmodifiableArrayList<Property> getPropertiesIfHasEnumerablePropertyNames(Shape shape) {
      return asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_PROPERTY_ARRAY : getPropertiesArrayIfHasEnumerablePropertyNames(shape));
   }

   static <T> UnmodifiablePropertyKeyList<T> getPropertyKeyList(Shape shape, boolean strings, boolean symbols) {
      CompilerAsserts.neverPartOfCompilation();
      Property[] propertyArray;
      int start;
      int end;
      if (shape.getPropertyCount() == 0) {
         propertyArray = EMPTY_PROPERTY_ARRAY;
         start = 0;
         end = 0;
      } else {
         JSShapeData shapeData = getShapeData(shape);
         propertyArray = getPropertiesArray(shapeData, shape);
         start = 0;
         end = propertyArray.length;
         if (!strings || !symbols) {
            int symbolsStart = getSymbolsStart(shapeData, propertyArray);
            if (!strings) {
               start = symbolsStart;
            }

            if (!symbols) {
               end = symbolsStart;
            }
         }
      }

      return UnmodifiablePropertyKeyList.create(propertyArray, start, end);
   }

   private static <T> UnmodifiableArrayList<T> asUnmodifiableList(T[] array) {
      return new UnmodifiableArrayList<>(array);
   }
}
