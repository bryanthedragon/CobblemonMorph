
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.Shape;

@Deprecated(since="22.2")
public interface DoubleLocation {
    @Deprecated(since="22.2")
    public double getDouble(DynamicObject var1, Shape var2);

    @Deprecated(since="22.2")
    public double getDouble(DynamicObject var1, boolean var2);

    @Deprecated(since="22.2")
    public void setDouble(DynamicObject var1, double var2) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setDouble(DynamicObject var1, double var2, Shape var4) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setDouble(DynamicObject var1, double var2, Shape var4, Shape var5);

    @Deprecated(since="22.2")
    public Class<Double> getType();
}

