
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;
import java.util.List;
import org.graalvm.collections.EconomicSet;

public class ForInIterator {
    public JSDynamicObject object;
    public Shape objectShape;
    public boolean objectWasVisited;
    public EconomicSet<Object> visitedKeys;
    public List<?> remainingKeys;
    public int remainingKeysSize;
    public int remainingKeysIndex;
    public Shape[] visitedShapes;
    public int visitedShapesSize;
    public boolean fastOwnKeys;
    public int protoDepth;
    public final boolean iterateValues;

    public ForInIterator(JSDynamicObject obj, boolean iterateValues) {
        this.object = obj;
        this.iterateValues = iterateValues;
        this.visitedShapes = new Shape[4];
    }

    public void addVisitedShape(Shape shape, BranchProfile growBranch) {
        if (this.visitedShapesSize >= this.visitedShapes.length) {
            growBranch.enter();
            this.visitedShapes = Arrays.copyOf(this.visitedShapes, this.visitedShapes.length * 2);
        }
        this.visitedShapes[this.visitedShapesSize++] = shape;
    }

    public boolean addVisitedKey(Object key) {
        if (this.visitedKeys == null) {
            this.visitedKeys = Boundaries.economicSetCreate();
        }
        return Boundaries.economicSetAdd(this.visitedKeys, key);
    }

    public boolean isVisitedKey(Object key) {
        return this.visitedShapesSize > 0 && ForInIterator.visitedShapeSetContainsKey(this.visitedShapes, this.visitedShapesSize, key) || this.visitedKeys != null && Boundaries.economicSetContains(this.visitedKeys, key);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean visitedShapeSetContainsKey(Shape[] visitedShapes, int size, Object key) {
        for (int i = 0; i < size; ++i) {
            Shape visitedShape = visitedShapes[i];
            if (!visitedShape.hasProperty(key)) continue;
            return true;
        }
        return false;
    }
}

