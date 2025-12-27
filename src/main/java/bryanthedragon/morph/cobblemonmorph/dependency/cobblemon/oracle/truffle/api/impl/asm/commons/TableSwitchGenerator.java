package com.oracle.truffle.api.impl.asm.commons;

import com.oracle.truffle.api.impl.asm.Label;

public interface TableSwitchGenerator {
   void generateCase(int var1, Label var2);

   void generateDefault();
}
