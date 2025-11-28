package com.cobblemon.mod.relocations.ibm.icu.util;

import com.cobblemon.mod.relocations.ibm.icu.impl.locale.LSR;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.LocaleDistance;
import com.cobblemon.mod.relocations.ibm.icu.impl.locale.XLikelySubtags;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class LocaleMatcher {
   private static final LSR UND_LSR = new LSR("und", "", "", 7);
   private static final ULocale UND_ULOCALE = new ULocale("und");
   private static final Locale UND_LOCALE = new Locale("und");
   private static final Locale EMPTY_LOCALE = new Locale("");
   private static final boolean TRACE_MATCHER = false;
   private final int thresholdDistance;
   private final int demotionPerDesiredLocale;
   private final LocaleMatcher.FavorSubtag favorSubtag;
   private final LocaleMatcher.Direction direction;
   private final ULocale[] supportedULocales;
   private final Locale[] supportedLocales;
   private final Map<LSR, Integer> supportedLsrToIndex;
   private final LSR[] supportedLSRs;
   private final int[] supportedIndexes;
   private final int supportedLSRsLength;
   private final ULocale defaultULocale;
   private final Locale defaultLocale;

   public static LocaleMatcher.Builder builder() {
      return new LocaleMatcher.Builder();
   }

   public LocaleMatcher(LocalePriorityList supportedLocales) {
      this(builder().setSupportedULocales(supportedLocales.getULocales()));
   }

   public LocaleMatcher(String supportedLocales) {
      this(builder().setSupportedLocales(supportedLocales));
   }

   private LocaleMatcher(LocaleMatcher.Builder builder) {
      ULocale udef = builder.defaultLocale;
      Locale def = null;
      LSR defLSR = null;
      if (udef != null) {
         def = udef.toLocale();
         defLSR = getMaximalLsrOrUnd(udef);
      }

      int supportedLocalesLength = builder.supportedLocales != null ? builder.supportedLocales.size() : 0;
      this.supportedULocales = new ULocale[supportedLocalesLength];
      this.supportedLocales = new Locale[supportedLocalesLength];
      LSR[] lsrs = new LSR[supportedLocalesLength];
      int i = 0;
      if (supportedLocalesLength > 0) {
         for (ULocale locale : builder.supportedLocales) {
            this.supportedULocales[i] = locale;
            this.supportedLocales[i] = locale.toLocale();
            lsrs[i] = getMaximalLsrOrUnd(locale);
            i++;
         }
      }

      this.supportedLsrToIndex = new HashMap<>(supportedLocalesLength);
      this.supportedLSRs = new LSR[supportedLocalesLength];
      this.supportedIndexes = new int[supportedLocalesLength];
      int suppLength = 0;
      byte[] order = new byte[supportedLocalesLength];
      int numParadigms = 0;
      i = 0;

      for (ULocale locale : this.supportedULocales) {
         LSR lsr = lsrs[i];
         if (defLSR == null && builder.withDefault) {
            assert i == 0;

            udef = locale;
            def = this.supportedLocales[0];
            defLSR = lsr;
            suppLength = this.putIfAbsent(lsr, 0, suppLength);
         } else if (defLSR != null && lsr.isEquivalentTo(defLSR)) {
            suppLength = this.putIfAbsent(lsr, i, suppLength);
         } else if (LocaleDistance.INSTANCE.isParadigmLSR(lsr)) {
            order[i] = 2;
            numParadigms++;
         } else {
            order[i] = 3;
         }

         i++;
      }

      int paradigmLimit = suppLength + numParadigms;

      for (int var17 = 0; var17 < supportedLocalesLength && suppLength < paradigmLimit; var17++) {
         if (order[var17] == 2) {
            suppLength = this.putIfAbsent(lsrs[var17], var17, suppLength);
         }
      }

      for (int var18 = 0; var18 < supportedLocalesLength; var18++) {
         if (order[var18] == 3) {
            suppLength = this.putIfAbsent(lsrs[var18], var18, suppLength);
         }
      }

      this.supportedLSRsLength = suppLength;
      this.defaultULocale = udef;
      this.defaultLocale = def;
      this.demotionPerDesiredLocale = builder.demotion == LocaleMatcher.Demotion.NONE ? 0 : LocaleDistance.INSTANCE.getDefaultDemotionPerDesiredLocale();
      this.favorSubtag = builder.favor;
      this.direction = builder.direction;
      int threshold;
      if (builder.thresholdDistance >= 0) {
         threshold = builder.thresholdDistance;
      } else if (builder.maxDistanceDesired != null) {
         int indexAndDistance = LocaleDistance.INSTANCE
            .getBestIndexAndDistance(
               getMaximalLsrOrUnd(builder.maxDistanceDesired),
               new LSR[]{getMaximalLsrOrUnd(builder.maxDistanceSupported)},
               1,
               LocaleDistance.shiftDistance(100),
               this.favorSubtag,
               this.direction
            );
         threshold = LocaleDistance.getDistanceFloor(indexAndDistance) + 1;
      } else {
         threshold = LocaleDistance.INSTANCE.getDefaultScriptDistance();
      }

      this.thresholdDistance = threshold;
   }

   private final int putIfAbsent(LSR lsr, int i, int suppLength) {
      if (!this.supportedLsrToIndex.containsKey(lsr)) {
         this.supportedLsrToIndex.put(lsr, i);
         this.supportedLSRs[suppLength] = lsr;
         this.supportedIndexes[suppLength++] = i;
      }

      return suppLength;
   }

   private static final LSR getMaximalLsrOrUnd(ULocale locale) {
      return locale.equals(UND_ULOCALE) ? UND_LSR : XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(locale);
   }

   private static final LSR getMaximalLsrOrUnd(Locale locale) {
      return !locale.equals(UND_LOCALE) && !locale.equals(EMPTY_LOCALE) ? XLikelySubtags.INSTANCE.makeMaximizedLsrFrom(locale) : UND_LSR;
   }

   public ULocale getBestMatch(ULocale desiredLocale) {
      LSR desiredLSR = getMaximalLsrOrUnd(desiredLocale);
      int suppIndex = this.getBestSuppIndex(desiredLSR, null);
      return suppIndex >= 0 ? this.supportedULocales[suppIndex] : this.defaultULocale;
   }

   public ULocale getBestMatch(Iterable<ULocale> desiredLocales) {
      Iterator<ULocale> desiredIter = desiredLocales.iterator();
      if (!desiredIter.hasNext()) {
         return this.defaultULocale;
      } else {
         LocaleMatcher.ULocaleLsrIterator lsrIter = new LocaleMatcher.ULocaleLsrIterator(desiredIter);
         LSR desiredLSR = lsrIter.next();
         int suppIndex = this.getBestSuppIndex(desiredLSR, lsrIter);
         return suppIndex >= 0 ? this.supportedULocales[suppIndex] : this.defaultULocale;
      }
   }

   public ULocale getBestMatch(String desiredLocaleList) {
      return this.getBestMatch(LocalePriorityList.add(desiredLocaleList).build());
   }

   public Locale getBestLocale(Locale desiredLocale) {
      LSR desiredLSR = getMaximalLsrOrUnd(desiredLocale);
      int suppIndex = this.getBestSuppIndex(desiredLSR, null);
      return suppIndex >= 0 ? this.supportedLocales[suppIndex] : this.defaultLocale;
   }

   public Locale getBestLocale(Iterable<Locale> desiredLocales) {
      Iterator<Locale> desiredIter = desiredLocales.iterator();
      if (!desiredIter.hasNext()) {
         return this.defaultLocale;
      } else {
         LocaleMatcher.LocaleLsrIterator lsrIter = new LocaleMatcher.LocaleLsrIterator(desiredIter);
         LSR desiredLSR = lsrIter.next();
         int suppIndex = this.getBestSuppIndex(desiredLSR, lsrIter);
         return suppIndex >= 0 ? this.supportedLocales[suppIndex] : this.defaultLocale;
      }
   }

   private LocaleMatcher.Result defaultResult() {
      return new LocaleMatcher.Result(null, this.defaultULocale, null, this.defaultLocale, -1, -1);
   }

   private LocaleMatcher.Result makeResult(ULocale desiredLocale, LocaleMatcher.ULocaleLsrIterator lsrIter, int suppIndex) {
      if (suppIndex < 0) {
         return this.defaultResult();
      } else {
         return desiredLocale != null
            ? new LocaleMatcher.Result(desiredLocale, this.supportedULocales[suppIndex], null, this.supportedLocales[suppIndex], 0, suppIndex)
            : new LocaleMatcher.Result(
               lsrIter.remembered, this.supportedULocales[suppIndex], null, this.supportedLocales[suppIndex], lsrIter.bestDesiredIndex, suppIndex
            );
      }
   }

   private LocaleMatcher.Result makeResult(Locale desiredLocale, LocaleMatcher.LocaleLsrIterator lsrIter, int suppIndex) {
      if (suppIndex < 0) {
         return this.defaultResult();
      } else {
         return desiredLocale != null
            ? new LocaleMatcher.Result(null, this.supportedULocales[suppIndex], desiredLocale, this.supportedLocales[suppIndex], 0, suppIndex)
            : new LocaleMatcher.Result(
               null, this.supportedULocales[suppIndex], lsrIter.remembered, this.supportedLocales[suppIndex], lsrIter.bestDesiredIndex, suppIndex
            );
      }
   }

   public LocaleMatcher.Result getBestMatchResult(ULocale desiredLocale) {
      LSR desiredLSR = getMaximalLsrOrUnd(desiredLocale);
      int suppIndex = this.getBestSuppIndex(desiredLSR, null);
      return this.makeResult(desiredLocale, null, suppIndex);
   }

   public LocaleMatcher.Result getBestMatchResult(Iterable<ULocale> desiredLocales) {
      Iterator<ULocale> desiredIter = desiredLocales.iterator();
      if (!desiredIter.hasNext()) {
         return this.defaultResult();
      } else {
         LocaleMatcher.ULocaleLsrIterator lsrIter = new LocaleMatcher.ULocaleLsrIterator(desiredIter);
         LSR desiredLSR = lsrIter.next();
         int suppIndex = this.getBestSuppIndex(desiredLSR, lsrIter);
         return this.makeResult(null, lsrIter, suppIndex);
      }
   }

   public LocaleMatcher.Result getBestLocaleResult(Locale desiredLocale) {
      LSR desiredLSR = getMaximalLsrOrUnd(desiredLocale);
      int suppIndex = this.getBestSuppIndex(desiredLSR, null);
      return this.makeResult(desiredLocale, null, suppIndex);
   }

   public LocaleMatcher.Result getBestLocaleResult(Iterable<Locale> desiredLocales) {
      Iterator<Locale> desiredIter = desiredLocales.iterator();
      if (!desiredIter.hasNext()) {
         return this.defaultResult();
      } else {
         LocaleMatcher.LocaleLsrIterator lsrIter = new LocaleMatcher.LocaleLsrIterator(desiredIter);
         LSR desiredLSR = lsrIter.next();
         int suppIndex = this.getBestSuppIndex(desiredLSR, lsrIter);
         return this.makeResult(null, lsrIter, suppIndex);
      }
   }

   private int getBestSuppIndex(LSR desiredLSR, LocaleMatcher.LsrIterator remainingIter) {
      int desiredIndex = 0;
      int bestSupportedLsrIndex = -1;
      StringBuilder sb = null;
      int bestShiftedDistance = LocaleDistance.shiftDistance(this.thresholdDistance);

      while (true) {
         Integer index = this.supportedLsrToIndex.get(desiredLSR);
         if (index != null) {
            int suppIndex = index;
            if (remainingIter != null) {
               remainingIter.rememberCurrent(desiredIndex);
            }

            return suppIndex;
         }

         int bestIndexAndDistance = LocaleDistance.INSTANCE
            .getBestIndexAndDistance(desiredLSR, this.supportedLSRs, this.supportedLSRsLength, bestShiftedDistance, this.favorSubtag, this.direction);
         if (bestIndexAndDistance >= 0) {
            bestShiftedDistance = LocaleDistance.getShiftedDistance(bestIndexAndDistance);
            if (remainingIter != null) {
               remainingIter.rememberCurrent(desiredIndex);
            }

            bestSupportedLsrIndex = LocaleDistance.getIndex(bestIndexAndDistance);
         }

         if ((bestShiftedDistance -= LocaleDistance.shiftDistance(this.demotionPerDesiredLocale)) <= 0 || remainingIter == null || !remainingIter.hasNext()) {
            if (bestSupportedLsrIndex < 0) {
               return -1;
            }

            return this.supportedIndexes[bestSupportedLsrIndex];
         }

         desiredLSR = remainingIter.next();
         desiredIndex++;
      }
   }

   public boolean isMatch(Locale desired, Locale supported) {
      int indexAndDistance = LocaleDistance.INSTANCE
         .getBestIndexAndDistance(
            getMaximalLsrOrUnd(desired),
            new LSR[]{getMaximalLsrOrUnd(supported)},
            1,
            LocaleDistance.shiftDistance(this.thresholdDistance),
            this.favorSubtag,
            this.direction
         );
      return indexAndDistance >= 0;
   }

   public boolean isMatch(ULocale desired, ULocale supported) {
      int indexAndDistance = LocaleDistance.INSTANCE
         .getBestIndexAndDistance(
            getMaximalLsrOrUnd(desired),
            new LSR[]{getMaximalLsrOrUnd(supported)},
            1,
            LocaleDistance.shiftDistance(this.thresholdDistance),
            this.favorSubtag,
            this.direction
         );
      return indexAndDistance >= 0;
   }

   @Deprecated
   public double match(ULocale desired, ULocale desiredMax, ULocale supported, ULocale supportedMax) {
      int indexAndDistance = LocaleDistance.INSTANCE
         .getBestIndexAndDistance(
            getMaximalLsrOrUnd(desired),
            new LSR[]{getMaximalLsrOrUnd(supported)},
            1,
            LocaleDistance.shiftDistance(this.thresholdDistance),
            this.favorSubtag,
            this.direction
         );
      double distance = LocaleDistance.getDistanceDouble(indexAndDistance);
      return (100.0 - distance) / 100.0;
   }

   public ULocale canonicalize(ULocale locale) {
      return XLikelySubtags.INSTANCE.canonicalize(locale);
   }

   @Override
   public String toString() {
      StringBuilder s = new StringBuilder().append("{LocaleMatcher");
      if (this.supportedLSRsLength > 0) {
         s.append(" supportedLSRs={").append(this.supportedLSRs[0]);

         for (int i = 1; i < this.supportedLSRsLength; i++) {
            s.append(", ").append(this.supportedLSRs[i]);
         }

         s.append('}');
      }

      s.append(" default=").append(this.defaultULocale);
      if (this.favorSubtag != null) {
         s.append(" favor=").append(this.favorSubtag);
      }

      if (this.direction != null) {
         s.append(" direction=").append(this.direction);
      }

      if (this.thresholdDistance >= 0) {
         s.append(String.format(" threshold=%d", this.thresholdDistance));
      }

      s.append(String.format(" demotion=%d", this.demotionPerDesiredLocale));
      return s.append('}').toString();
   }

   public static final class Builder {
      private List<ULocale> supportedLocales;
      private int thresholdDistance = -1;
      private LocaleMatcher.Demotion demotion;
      private ULocale defaultLocale;
      private boolean withDefault = true;
      private LocaleMatcher.FavorSubtag favor;
      private LocaleMatcher.Direction direction;
      private ULocale maxDistanceDesired;
      private ULocale maxDistanceSupported;

      private Builder() {
      }

      public LocaleMatcher.Builder setSupportedLocales(String locales) {
         return this.setSupportedULocales(LocalePriorityList.add(locales).build().getULocales());
      }

      public LocaleMatcher.Builder setSupportedULocales(Collection<ULocale> locales) {
         this.supportedLocales = new ArrayList<>(locales);
         return this;
      }

      public LocaleMatcher.Builder setSupportedLocales(Collection<Locale> locales) {
         this.supportedLocales = new ArrayList<>(locales.size());

         for (Locale locale : locales) {
            this.supportedLocales.add(ULocale.forLocale(locale));
         }

         return this;
      }

      public LocaleMatcher.Builder addSupportedULocale(ULocale locale) {
         if (this.supportedLocales == null) {
            this.supportedLocales = new ArrayList<>();
         }

         this.supportedLocales.add(locale);
         return this;
      }

      public LocaleMatcher.Builder addSupportedLocale(Locale locale) {
         return this.addSupportedULocale(ULocale.forLocale(locale));
      }

      public LocaleMatcher.Builder setNoDefaultLocale() {
         this.defaultLocale = null;
         this.withDefault = false;
         return this;
      }

      public LocaleMatcher.Builder setDefaultULocale(ULocale defaultLocale) {
         this.defaultLocale = defaultLocale;
         this.withDefault = true;
         return this;
      }

      public LocaleMatcher.Builder setDefaultLocale(Locale defaultLocale) {
         this.defaultLocale = ULocale.forLocale(defaultLocale);
         this.withDefault = true;
         return this;
      }

      public LocaleMatcher.Builder setFavorSubtag(LocaleMatcher.FavorSubtag subtag) {
         this.favor = subtag;
         return this;
      }

      public LocaleMatcher.Builder setDemotionPerDesiredLocale(LocaleMatcher.Demotion demotion) {
         this.demotion = demotion;
         return this;
      }

      public LocaleMatcher.Builder setDirection(LocaleMatcher.Direction direction) {
         this.direction = direction;
         return this;
      }

      public LocaleMatcher.Builder setMaxDistance(Locale desired, Locale supported) {
         if (desired != null && supported != null) {
            return this.setMaxDistance(ULocale.forLocale(desired), ULocale.forLocale(supported));
         } else {
            throw new IllegalArgumentException("desired/supported locales must not be null");
         }
      }

      public LocaleMatcher.Builder setMaxDistance(ULocale desired, ULocale supported) {
         if (desired != null && supported != null) {
            this.maxDistanceDesired = desired;
            this.maxDistanceSupported = supported;
            return this;
         } else {
            throw new IllegalArgumentException("desired/supported locales must not be null");
         }
      }

      @Deprecated
      public LocaleMatcher.Builder internalSetThresholdDistance(int thresholdDistance) {
         if (thresholdDistance > 100) {
            thresholdDistance = 100;
         }

         this.thresholdDistance = thresholdDistance;
         return this;
      }

      public LocaleMatcher build() {
         return new LocaleMatcher(this);
      }

      @Override
      public String toString() {
         StringBuilder s = new StringBuilder().append("{LocaleMatcher.Builder");
         if (this.supportedLocales != null && !this.supportedLocales.isEmpty()) {
            s.append(" supported={").append(this.supportedLocales).append('}');
         }

         if (this.defaultLocale != null) {
            s.append(" default=").append(this.defaultLocale);
         }

         if (this.favor != null) {
            s.append(" distance=").append(this.favor);
         }

         if (this.thresholdDistance >= 0) {
            s.append(String.format(" threshold=%d", this.thresholdDistance));
         }

         if (this.demotion != null) {
            s.append(" demotion=").append(this.demotion);
         }

         return s.append('}').toString();
      }
   }

   public static enum Demotion {
      NONE,
      REGION;
   }

   public static enum Direction {
      WITH_ONE_WAY,
      ONLY_TWO_WAY;
   }

   public static enum FavorSubtag {
      LANGUAGE,
      SCRIPT;
   }

   private static final class LocaleLsrIterator extends LocaleMatcher.LsrIterator {
      private Iterator<Locale> locales;
      private Locale current;
      private Locale remembered;

      LocaleLsrIterator(Iterator<Locale> locales) {
         this.locales = locales;
      }

      @Override
      public boolean hasNext() {
         return this.locales.hasNext();
      }

      public LSR next() {
         this.current = this.locales.next();
         return LocaleMatcher.getMaximalLsrOrUnd(this.current);
      }

      @Override
      public void rememberCurrent(int desiredIndex) {
         this.bestDesiredIndex = desiredIndex;
         this.remembered = this.current;
      }
   }

   private abstract static class LsrIterator implements Iterator<LSR> {
      int bestDesiredIndex = -1;

      private LsrIterator() {
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }

      public abstract void rememberCurrent(int var1);
   }

   public static final class Result {
      private final ULocale desiredULocale;
      private final ULocale supportedULocale;
      private final Locale desiredLocale;
      private final Locale supportedLocale;
      private final int desiredIndex;
      private final int supportedIndex;

      private Result(ULocale udesired, ULocale usupported, Locale desired, Locale supported, int desIndex, int suppIndex) {
         this.desiredULocale = udesired;
         this.supportedULocale = usupported;
         this.desiredLocale = desired;
         this.supportedLocale = supported;
         this.desiredIndex = desIndex;
         this.supportedIndex = suppIndex;
      }

      public ULocale getDesiredULocale() {
         return this.desiredULocale == null && this.desiredLocale != null ? ULocale.forLocale(this.desiredLocale) : this.desiredULocale;
      }

      public Locale getDesiredLocale() {
         return this.desiredLocale == null && this.desiredULocale != null ? this.desiredULocale.toLocale() : this.desiredLocale;
      }

      public ULocale getSupportedULocale() {
         return this.supportedULocale;
      }

      public Locale getSupportedLocale() {
         return this.supportedLocale;
      }

      public int getDesiredIndex() {
         return this.desiredIndex;
      }

      public int getSupportedIndex() {
         return this.supportedIndex;
      }

      public ULocale makeResolvedULocale() {
         ULocale bestDesired = this.getDesiredULocale();
         if (this.supportedULocale != null && bestDesired != null && !this.supportedULocale.equals(bestDesired)) {
            ULocale.Builder b = new ULocale.Builder().setLocale(this.supportedULocale);
            String region = bestDesired.getCountry();
            if (!region.isEmpty()) {
               b.setRegion(region);
            }

            String variants = bestDesired.getVariant();
            if (!variants.isEmpty()) {
               b.setVariant(variants);
            }

            for (char extensionKey : bestDesired.getExtensionKeys()) {
               b.setExtension(extensionKey, bestDesired.getExtension(extensionKey));
            }

            return b.build();
         } else {
            return this.supportedULocale;
         }
      }

      public Locale makeResolvedLocale() {
         ULocale resolved = this.makeResolvedULocale();
         return resolved != null ? resolved.toLocale() : null;
      }
   }

   private static final class ULocaleLsrIterator extends LocaleMatcher.LsrIterator {
      private Iterator<ULocale> locales;
      private ULocale current;
      private ULocale remembered;

      ULocaleLsrIterator(Iterator<ULocale> locales) {
         this.locales = locales;
      }

      @Override
      public boolean hasNext() {
         return this.locales.hasNext();
      }

      public LSR next() {
         this.current = this.locales.next();
         return LocaleMatcher.getMaximalLsrOrUnd(this.current);
      }

      @Override
      public void rememberCurrent(int desiredIndex) {
         this.bestDesiredIndex = desiredIndex;
         this.remembered = this.current;
      }
   }
}
