package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.text.Format;
import java.text.Format.Field;

public abstract class UFormat extends Format {
   private static final long serialVersionUID = -4964390515840164416L;
   private ULocale validLocale;
   private ULocale actualLocale;

   public final ULocale getLocale(ULocale.Type type) {
      return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
   }

   final void setLocale(ULocale valid, ULocale actual) {
      if (valid == null != (actual == null)) {
         throw new IllegalArgumentException();
      } else {
         this.validLocale = valid;
         this.actualLocale = actual;
      }
   }

   public abstract static class SpanField extends Field {
      private static final long serialVersionUID = -4732719509273350606L;

      protected SpanField(String name) {
         super(name);
      }
   }
}
