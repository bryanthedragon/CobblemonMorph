
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.Shape;

@Deprecated(since="22.2")
public interface IntLocation {
    @Deprecated(since="22.2")
    public int getInt(DynamicObject var1, Shape var2);

    @Deprecated(since="22.2")
    public int getInt(DynamicObject var1, boolean var2);

    @Deprecated(since="22.2")
    public void setInt(DynamicObject var1, int var2) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setInt(DynamicObject var1, int var2, Shape var3) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setInt(DynamicObject var1, int var2, Shape var3, Shape var4);

    @Deprecated(since="22.2")
    public Class<Integer> getType();
}

