package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.LanguageInfo;

public final class AllocationEvent {
   private final LanguageInfo language;
   private final Object value;
   private final long oldSize;
   private final long newSize;

   AllocationEvent(LanguageInfo language, Object value, long oldSize, long newSize) {
      this.language = language;
      this.value = value;
      this.oldSize = oldSize;
      this.newSize = newSize;
   }

   public LanguageInfo getLanguage() {
      return this.language;
   }

   public long getOldSize() {
      return this.oldSize;
   }

   public long getNewSize() {
      return this.newSize;
   }

   public Object getValue() {
      return this.value;
   }
}
