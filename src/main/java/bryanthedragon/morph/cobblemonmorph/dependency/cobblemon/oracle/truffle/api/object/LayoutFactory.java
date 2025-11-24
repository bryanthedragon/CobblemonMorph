
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;

@Deprecated(since="22.2")
public interface LayoutFactory {
    @Deprecated(since="22.2")
    default public Layout createLayout(Layout.Builder layoutBuilder) {
        throw new UnsupportedOperationException();
    }

    @Deprecated(since="22.2")
    public Property createProperty(Object var1, Location var2);

    @Deprecated(since="22.2")
    public Property createProperty(Object var1, Location var2, int var3);

    default public Shape createShape(Object arg0) {
        throw new UnsupportedOperationException();
    }

    public int getPriority();
}

