package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.CaseMapImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.UCaseProps;
import java.util.Locale;

public abstract class CaseMap {
   @Deprecated
   protected int internalOptions;

   private CaseMap(int opt) {
      this.internalOptions = opt;
   }

   private static int getCaseLocale(Locale locale) {
      if (locale == null) {
         locale = Locale.getDefault();
      }

      return UCaseProps.getCaseLocale(locale);
   }

   public static CaseMap.Lower toLower() {
      return CaseMap.Lower.DEFAULT;
   }

   public static CaseMap.Upper toUpper() {
      return CaseMap.Upper.DEFAULT;
   }

   public static CaseMap.Title toTitle() {
      return CaseMap.Title.DEFAULT;
   }

   public static CaseMap.Fold fold() {
      return CaseMap.Fold.DEFAULT;
   }

   public abstract CaseMap omitUnchangedText();

   public static final class Fold extends CaseMap {
      private static final CaseMap.Fold DEFAULT = new CaseMap.Fold(0);
      private static final CaseMap.Fold TURKIC = new CaseMap.Fold(1);
      private static final CaseMap.Fold OMIT_UNCHANGED = new CaseMap.Fold(16384);
      private static final CaseMap.Fold TURKIC_OMIT_UNCHANGED = new CaseMap.Fold(16385);

      private Fold(int opt) {
         super(opt);
      }

      public CaseMap.Fold omitUnchangedText() {
         return (this.internalOptions & 1) == 0 ? OMIT_UNCHANGED : TURKIC_OMIT_UNCHANGED;
      }

      public CaseMap.Fold turkic() {
         return (this.internalOptions & 16384) == 0 ? TURKIC : TURKIC_OMIT_UNCHANGED;
      }

      public String apply(CharSequence src) {
         return CaseMapImpl.fold(this.internalOptions, src);
      }

      public <A extends Appendable> A apply(CharSequence src, A dest, Edits edits) {
         return CaseMapImpl.fold(this.internalOptions, src, dest, edits);
      }
   }

   public static final class Lower extends CaseMap {
      private static final CaseMap.Lower DEFAULT = new CaseMap.Lower(0);
      private static final CaseMap.Lower OMIT_UNCHANGED = new CaseMap.Lower(16384);

      private Lower(int opt) {
         super(opt);
      }

      public CaseMap.Lower omitUnchangedText() {
         return OMIT_UNCHANGED;
      }

      public String apply(Locale locale, CharSequence src) {
         return CaseMapImpl.toLower(CaseMap.getCaseLocale(locale), this.internalOptions, src);
      }

      public <A extends Appendable> A apply(Locale locale, CharSequence src, A dest, Edits edits) {
         return CaseMapImpl.toLower(CaseMap.getCaseLocale(locale), this.internalOptions, src, dest, edits);
      }
   }

   public static final class Title extends CaseMap {
      private static final CaseMap.Title DEFAULT = new CaseMap.Title(0);
      private static final CaseMap.Title OMIT_UNCHANGED = new CaseMap.Title(16384);

      private Title(int opt) {
         super(opt);
      }

      public CaseMap.Title wholeString() {
         return new CaseMap.Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 32));
      }

      public CaseMap.Title sentences() {
         return new CaseMap.Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 64));
      }

      public CaseMap.Title omitUnchangedText() {
         return this.internalOptions != 0 && this.internalOptions != 16384 ? new CaseMap.Title(this.internalOptions | 16384) : OMIT_UNCHANGED;
      }

      public CaseMap.Title noLowercase() {
         return new CaseMap.Title(this.internalOptions | 256);
      }

      public CaseMap.Title noBreakAdjustment() {
         return new CaseMap.Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 512));
      }

      public CaseMap.Title adjustToCased() {
         return new CaseMap.Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 1024));
      }

      public String apply(Locale locale, BreakIterator iter, CharSequence src) {
         if (iter == null && locale == null) {
            locale = Locale.getDefault();
         }

         iter = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, iter);
         iter.setText(src);
         return CaseMapImpl.toTitle(CaseMap.getCaseLocale(locale), this.internalOptions, iter, src);
      }

      public <A extends Appendable> A apply(Locale locale, BreakIterator iter, CharSequence src, A dest, Edits edits) {
         if (iter == null && locale == null) {
            locale = Locale.getDefault();
         }

         iter = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, iter);
         iter.setText(src);
         return CaseMapImpl.toTitle(CaseMap.getCaseLocale(locale), this.internalOptions, iter, src, dest, edits);
      }
   }

   public static final class Upper extends CaseMap {
      private static final CaseMap.Upper DEFAULT = new CaseMap.Upper(0);
      private static final CaseMap.Upper OMIT_UNCHANGED = new CaseMap.Upper(16384);

      private Upper(int opt) {
         super(opt);
      }

      public CaseMap.Upper omitUnchangedText() {
         return OMIT_UNCHANGED;
      }

      public String apply(Locale locale, CharSequence src) {
         return CaseMapImpl.toUpper(CaseMap.getCaseLocale(locale), this.internalOptions, src);
      }

      public <A extends Appendable> A apply(Locale locale, CharSequence src, A dest, Edits edits) {
         return CaseMapImpl.toUpper(CaseMap.getCaseLocale(locale), this.internalOptions, src, dest, edits);
      }
   }
}
