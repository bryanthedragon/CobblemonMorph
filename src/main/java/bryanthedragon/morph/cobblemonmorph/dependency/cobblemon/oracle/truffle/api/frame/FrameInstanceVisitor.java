
package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.frame.FrameInstance;

public interface FrameInstanceVisitor<T> {
    public T visitFrame(FrameInstance var1);
}

