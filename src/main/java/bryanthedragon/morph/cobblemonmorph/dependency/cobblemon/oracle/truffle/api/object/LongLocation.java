
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.Shape;

@Deprecated(since="22.2")
public interface LongLocation {
    @Deprecated(since="22.2")
    public long getLong(DynamicObject var1, Shape var2);

    @Deprecated(since="22.2")
    public long getLong(DynamicObject var1, boolean var2);

    @Deprecated(since="22.2")
    public void setLong(DynamicObject var1, long var2) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setLong(DynamicObject var1, long var2, Shape var4) throws FinalLocationException;

    @Deprecated(since="22.2")
    public void setLong(DynamicObject var1, long var2, Shape var4, Shape var5);

    @Deprecated(since="22.2")
    public Class<Long> getType();
}

