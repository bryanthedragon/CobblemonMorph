
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
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
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
        JSShapeData.sortProperties(ownProperties);
        return ownProperties.toArray(EMPTY_PROPERTY_ARRAY);
    }

    private static TruffleString[] createEnumerablePropertyNamesArray(Shape shape) {
        CompilerAsserts.neverPartOfCompilation();
        enumerablePropertyListAllocCount.inc();
        ArrayList ownProperties = new ArrayList();
        shape.getPropertyList().forEach(property -> {
            if (JSProperty.isEnumerable(property) && Strings.isTString(property.getKey())) {
                ownProperties.add((TruffleString)property.getKey());
            }
        });
        JSShapeData.sortPropertyKeys(ownProperties);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static JSShapeData getShapeData(Shape shape) {
        JSContext context;
        CompilerAsserts.neverPartOfCompilation();
        JSContext jSContext = context = JSShape.getJSContext(shape);
        synchronized (jSContext) {
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
        assert (shape.getPropertyCount() != 0);
        return JSShapeData.getPropertiesArray(JSShapeData.getShapeData(shape), shape);
    }

    private static Property[] getPropertiesArray(JSShapeData shapeData, Shape shape) {
        Property[] propertyArray = shapeData.propertyArray;
        if (propertyArray == null) {
            propertyArray = JSShapeData.createPropertiesArray(shape);
            assert (propertyArray.length == shape.getPropertyCount());
            shapeData.propertyArray = propertyArray;
        }
        return propertyArray;
    }

    private static int getSymbolsStart(JSShapeData shapeData, Property[] propertyArray) {
        int symbolsStart = shapeData.symbolsStartPos;
        if (symbolsStart == -1) {
            shapeData.symbolsStartPos = symbolsStart = JSShapeData.getSymbolsStart(propertyArray);
        }
        return symbolsStart;
    }

    private static int getSymbolsStart(Property[] propertyArray) {
        Property prev;
        int pos;
        for (pos = propertyArray.length; pos > 0 && (prev = propertyArray[pos - 1]).getKey() instanceof Symbol; --pos) {
        }
        return pos;
    }

    static UnmodifiableArrayList<Property> getProperties(Shape shape) {
        return JSShapeData.asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_PROPERTY_ARRAY : JSShapeData.getPropertiesArray(shape));
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString[] getEnumerablePropertyNamesArray(Shape shape) {
        assert (shape.getPropertyCount() != 0);
        return JSShapeData.getEnumerablePropertyNamesArray(JSShapeData.getShapeData(shape), shape);
    }

    private static TruffleString[] getEnumerablePropertyNamesArray(JSShapeData shapeData, Shape shape) {
        TruffleString[] enumeratePropertyNames = shapeData.enumerablePropertyNames;
        if (enumeratePropertyNames == null) {
            enumeratePropertyNames = JSShapeData.createEnumerablePropertyNamesArray(shape);
            shapeData.enumerablePropertyNames = enumeratePropertyNames;
        }
        return enumeratePropertyNames;
    }

    static UnmodifiableArrayList<TruffleString> getEnumerablePropertyNames(Shape shape) {
        return JSShapeData.asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_STRING_ARRAY : JSShapeData.getEnumerablePropertyNamesArray(shape));
    }

    @CompilerDirectives.TruffleBoundary
    private static Property[] getPropertiesArrayIfHasEnumerablePropertyNames(Shape shape) {
        assert (shape.getPropertyCount() != 0);
        JSShapeData shapeData = JSShapeData.getShapeData(shape);
        if (JSShapeData.getEnumerablePropertyNamesArray(shapeData, shape).length == 0) {
            return EMPTY_PROPERTY_ARRAY;
        }
        return JSShapeData.getPropertiesArray(shapeData, shape);
    }

    static UnmodifiableArrayList<Property> getPropertiesIfHasEnumerablePropertyNames(Shape shape) {
        return JSShapeData.asUnmodifiableList(shape.getPropertyCount() == 0 ? EMPTY_PROPERTY_ARRAY : JSShapeData.getPropertiesArrayIfHasEnumerablePropertyNames(shape));
    }

    static <T> UnmodifiablePropertyKeyList<T> getPropertyKeyList(Shape shape, boolean strings, boolean symbols) {
        int end2;
        int start2;
        Property[] propertyArray;
        CompilerAsserts.neverPartOfCompilation();
        if (shape.getPropertyCount() == 0) {
            propertyArray = EMPTY_PROPERTY_ARRAY;
            start2 = 0;
            end2 = 0;
        } else {
            JSShapeData shapeData = JSShapeData.getShapeData(shape);
            propertyArray = JSShapeData.getPropertiesArray(shapeData, shape);
            start2 = 0;
            end2 = propertyArray.length;
            if (!strings || !symbols) {
                int symbolsStart = JSShapeData.getSymbolsStart(shapeData, propertyArray);
                if (!strings) {
                    start2 = symbolsStart;
                }
                if (!symbols) {
                    end2 = symbolsStart;
                }
            }
        }
        return UnmodifiablePropertyKeyList.create(propertyArray, start2, end2);
    }

    private static <T> UnmodifiableArrayList<T> asUnmodifiableList(T[] array) {
        return new UnmodifiableArrayList<T>(array);
    }
}

