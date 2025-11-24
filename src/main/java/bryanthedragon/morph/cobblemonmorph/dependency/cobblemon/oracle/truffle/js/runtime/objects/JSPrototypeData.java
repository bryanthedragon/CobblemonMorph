/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSShape;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

public final class JSPrototypeData {
    private static final Shape[] EMPTY_SHAPE_ARRAY = new Shape[0];
    private Shape[] protoChildTrees = EMPTY_SHAPE_ARRAY;
    private static final VarHandle PROTO_CHILD_TREES_VAR_HANDLE;

    private static Shape lookupShapeByType(Shape[] shapes, JSClass jsclass) {
        for (Shape shape : shapes) {
            if (JSShape.getJSClassNoCast(shape) != jsclass) continue;
            return shape;
        }
        return null;
    }

    public Shape getProtoChildTree(JSClass jsclass) {
        return JSPrototypeData.lookupShapeByType(this.getProtoChildTrees(), jsclass);
    }

    public Shape getOrAddProtoChildTree(JSClass jsclass, Shape newRootShape) {
        Shape[] newArray;
        Shape[] oldArray;
        CompilerAsserts.neverPartOfCompilation();
        do {
            Shape existingRootShape;
            if ((existingRootShape = JSPrototypeData.lookupShapeByType(oldArray = this.getProtoChildTrees(), jsclass)) != null) {
                return existingRootShape;
            }
            newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
            newArray[oldArray.length] = newRootShape;
        } while (!PROTO_CHILD_TREES_VAR_HANDLE.compareAndSet(this, oldArray, newArray));
        return newRootShape;
    }

    private Shape[] getProtoChildTrees() {
        return PROTO_CHILD_TREES_VAR_HANDLE.getVolatile(this);
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            PROTO_CHILD_TREES_VAR_HANDLE = lookup.findVarHandle(JSPrototypeData.class, "protoChildTrees", Shape[].class);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            throw Errors.shouldNotReachHere(e);
        }
    }
}

