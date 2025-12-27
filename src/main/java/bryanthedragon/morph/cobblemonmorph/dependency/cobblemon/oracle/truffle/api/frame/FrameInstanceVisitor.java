package com.oracle.truffle.api.frame;

public interface FrameInstanceVisitor<T> {
   T visitFrame(FrameInstance frameInstance);
}
