
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;

@Deprecated(since="22.2")
public interface DynamicObjectFactory {
    public DynamicObject newInstance(Object ... var1);

    public Shape getShape();
}

