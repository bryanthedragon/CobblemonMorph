package com.cobblemon.mod.relocations.ibm.icu.util;

import java.util.Date;

public interface DateRule {
   Date firstAfter(Date var1);

   Date firstBetween(Date var1, Date var2);

   boolean isOn(Date var1);

   boolean isBetween(Date var1, Date var2);
}
