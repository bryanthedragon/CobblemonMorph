package com.cobblemon.mod.relocations.ibm.icu.util;

import java.util.Date;

class Range {
   public Date start;
   public DateRule rule;

   public Range(Date start, DateRule rule) {
      this.start = start;
      this.rule = rule;
   }
}
