package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.locale.AsciiUtil;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.lang.UScript;
import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.CaseMap;
import com.cobblemon.mod.relocations.ibm.icu.text.DisplayContext;
import com.cobblemon.mod.relocations.ibm.icu.text.LocaleDisplayNames;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Map.Entry;

public class LocaleDisplayNamesImpl extends LocaleDisplayNames {
   private final ULocale locale;
   private final LocaleDisplayNames.DialectHandling dialectHandling;
   private final DisplayContext capitalization;
   private final DisplayContext nameLength;
   private final DisplayContext substituteHandling;
   private final LocaleDisplayNamesImpl.DataTable langData;
   private final LocaleDisplayNamesImpl.DataTable regionData;
   private final String separatorFormat;
   private final String format;
   private final String keyTypeFormat;
   private final char formatOpenParen;
   private final char formatReplaceOpenParen;
   private final char formatCloseParen;
   private final char formatReplaceCloseParen;
   private final CurrencyData.CurrencyDisplayInfo currencyDisplayInfo;
   private static final LocaleDisplayNamesImpl.Cache cache = new LocaleDisplayNamesImpl.Cache();
   private boolean[] capitalizationUsage = null;
   private static final Map<String, LocaleDisplayNamesImpl.CapitalizationContextUsage> contextUsageTypeMap = new HashMap<>();
   private transient BreakIterator capitalizationBrkIter = null;
   private static final CaseMap.Title TO_TITLE_WHOLE_STRING_NO_LOWERCASE = CaseMap.toTitle().wholeString().noLowercase();

   private static String toTitleWholeStringNoLowercase(ULocale locale, String s) {
      return TO_TITLE_WHOLE_STRING_NO_LOWERCASE.apply(locale.toLocale(), null, s);
   }

   public static LocaleDisplayNames getInstance(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
      synchronized (cache) {
         return cache.get(locale, dialectHandling);
      }
   }

   public static LocaleDisplayNames getInstance(ULocale locale, DisplayContext... contexts) {
      synchronized (cache) {
         return cache.get(locale, contexts);
      }
   }

   public LocaleDisplayNamesImpl(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
      this(
         locale,
         dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES,
         DisplayContext.CAPITALIZATION_NONE
      );
   }

   public LocaleDisplayNamesImpl(ULocale locale, DisplayContext... contexts) {
      LocaleDisplayNames.DialectHandling dialectHandling = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
      DisplayContext capitalization = DisplayContext.CAPITALIZATION_NONE;
      DisplayContext nameLength = DisplayContext.LENGTH_FULL;
      DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;

      for (DisplayContext contextItem : contexts) {
         switch (contextItem.type()) {
            case DIALECT_HANDLING:
               dialectHandling = contextItem.value() == DisplayContext.STANDARD_NAMES.value()
                  ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES
                  : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
               break;
            case CAPITALIZATION:
               capitalization = contextItem;
               break;
            case DISPLAY_LENGTH:
               nameLength = contextItem;
               break;
            case SUBSTITUTE_HANDLING:
               substituteHandling = contextItem;
         }
      }

      this.dialectHandling = dialectHandling;
      this.capitalization = capitalization;
      this.nameLength = nameLength;
      this.substituteHandling = substituteHandling;
      this.langData = LocaleDisplayNamesImpl.LangDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
      this.regionData = LocaleDisplayNamesImpl.RegionDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
      this.locale = ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale();
      String sep = this.langData.get("localeDisplayPattern", "separator");
      if (sep == null || "separator".equals(sep)) {
         sep = "{0}, {1}";
      }

      StringBuilder sb = new StringBuilder();
      this.separatorFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(sep, sb, 2, 2);
      String pattern = this.langData.get("localeDisplayPattern", "pattern");
      if (pattern == null || "pattern".equals(pattern)) {
         pattern = "{0} ({1})";
      }

      this.format = SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
      if (pattern.contains("（")) {
         this.formatOpenParen = '（';
         this.formatCloseParen = '）';
         this.formatReplaceOpenParen = '［';
         this.formatReplaceCloseParen = '］';
      } else {
         this.formatOpenParen = '(';
         this.formatCloseParen = ')';
         this.formatReplaceOpenParen = '[';
         this.formatReplaceCloseParen = ']';
      }

      String keyTypePattern = this.langData.get("localeDisplayPattern", "keyTypePattern");
      if (keyTypePattern == null || "keyTypePattern".equals(keyTypePattern)) {
         keyTypePattern = "{0}={1}";
      }

      this.keyTypeFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(keyTypePattern, sb, 2, 2);
      boolean needBrkIter = false;
      if (capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || capitalization == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
         this.capitalizationUsage = new boolean[LocaleDisplayNamesImpl.CapitalizationContextUsage.values().length];
         ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", locale);
         LocaleDisplayNamesImpl.CapitalizationContextSink sink = new LocaleDisplayNamesImpl.CapitalizationContextSink();

         try {
            rb.getAllItemsWithFallback("contextTransforms", sink);
         } catch (MissingResourceException var15) {
         }

         needBrkIter = sink.hasCapitalizationUsage;
      }

      if (needBrkIter || capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
         this.capitalizationBrkIter = BreakIterator.getSentenceInstance(locale);
      }

      this.currencyDisplayInfo = CurrencyData.provider.getInstance(locale, false);
   }

   @Override
   public ULocale getLocale() {
      return this.locale;
   }

   @Override
   public LocaleDisplayNames.DialectHandling getDialectHandling() {
      return this.dialectHandling;
   }

   @Override
   public DisplayContext getContext(DisplayContext.Type type) {
      DisplayContext result;
      switch (type) {
         case DIALECT_HANDLING:
            result = this.dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES;
            break;
         case CAPITALIZATION:
            result = this.capitalization;
            break;
         case DISPLAY_LENGTH:
            result = this.nameLength;
            break;
         case SUBSTITUTE_HANDLING:
            result = this.substituteHandling;
            break;
         default:
            result = DisplayContext.STANDARD_NAMES;
      }

      return result;
   }

   private String adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage usage, String name) {
      if (name != null
         && name.length() > 0
         && UCharacter.isLowerCase(name.codePointAt(0))
         && (
            this.capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE
               || this.capitalizationUsage != null && this.capitalizationUsage[usage.ordinal()]
         )) {
         synchronized (this) {
            if (this.capitalizationBrkIter == null) {
               this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }

            return UCharacter.toTitleCase(this.locale, name, this.capitalizationBrkIter, 768);
         }
      } else {
         return name;
      }
   }

   @Override
   public String localeDisplayName(ULocale locale) {
      return this.localeDisplayNameInternal(locale);
   }

   @Override
   public String localeDisplayName(Locale locale) {
      return this.localeDisplayNameInternal(ULocale.forLocale(locale));
   }

   @Override
   public String localeDisplayName(String localeId) {
      return this.localeDisplayNameInternal(new ULocale(localeId));
   }

   private String localeDisplayNameInternal(ULocale locale) {
      String resultName = null;
      String lang = locale.getLanguage();
      if (lang.isEmpty()) {
         lang = "und";
      }

      String script;
      String country;
      String variant;
      boolean hasScript;
      boolean hasCountry;
      boolean hasVariant;
      script = locale.getScript();
      country = locale.getCountry();
      variant = locale.getVariant();
      hasScript = script.length() > 0;
      hasCountry = country.length() > 0;
      hasVariant = variant.length() > 0;
      label114:
      if (this.dialectHandling == LocaleDisplayNames.DialectHandling.DIALECT_NAMES) {
         if (hasScript && hasCountry) {
            String langScriptCountry = lang + '_' + script + '_' + country;
            String result = this.localeIdName(langScriptCountry);
            if (result != null && !result.equals(langScriptCountry)) {
               resultName = result;
               hasScript = false;
               hasCountry = false;
               break label114;
            }
         }

         if (hasScript) {
            String langScript = lang + '_' + script;
            String result = this.localeIdName(langScript);
            if (result != null && !result.equals(langScript)) {
               resultName = result;
               hasScript = false;
               break label114;
            }
         }

         if (hasCountry) {
            String langCountry = lang + '_' + country;
            String result = this.localeIdName(langCountry);
            if (result != null && !result.equals(langCountry)) {
               resultName = result;
               hasCountry = false;
            }
         }
      }

      if (resultName == null) {
         String result = this.localeIdName(lang);
         if (result == null) {
            return null;
         }

         resultName = result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
      }

      StringBuilder buf = new StringBuilder();
      if (hasScript) {
         String result = this.scriptDisplayNameInContext(script, true);
         if (result == null) {
            return null;
         }

         buf.append(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen));
      }

      if (hasCountry) {
         String result = this.regionDisplayName(country, true);
         if (result == null) {
            return null;
         }

         this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
      }

      if (hasVariant) {
         String result = this.variantDisplayName(variant, true);
         if (result == null) {
            return null;
         }

         this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
      }

      Iterator<String> keys = locale.getKeywords();
      if (keys != null) {
         while (keys.hasNext()) {
            String key = keys.next();
            String value = locale.getKeywordValue(key);
            String keyDisplayName = this.keyDisplayName(key, true);
            if (keyDisplayName == null) {
               return null;
            }

            keyDisplayName = keyDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen)
               .replace(this.formatCloseParen, this.formatReplaceCloseParen);
            String valueDisplayName = this.keyValueDisplayName(key, value, true);
            if (valueDisplayName == null) {
               return null;
            }

            valueDisplayName = valueDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen)
               .replace(this.formatCloseParen, this.formatReplaceCloseParen);
            if (!valueDisplayName.equals(value)) {
               this.appendWithSep(valueDisplayName, buf);
            } else if (!key.equals(keyDisplayName)) {
               String keyValue = SimpleFormatterImpl.formatCompiledPattern(this.keyTypeFormat, keyDisplayName, valueDisplayName);
               this.appendWithSep(keyValue, buf);
            } else {
               this.appendWithSep(keyDisplayName, buf).append("=").append(valueDisplayName);
            }
         }
      }

      String resultRemainder = null;
      if (buf.length() > 0) {
         resultRemainder = buf.toString();
      }

      if (resultRemainder != null) {
         resultName = SimpleFormatterImpl.formatCompiledPattern(this.format, resultName, resultRemainder);
      }

      return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, resultName);
   }

   private String localeIdName(String localeId) {
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         String locIdName = this.langData.get("Languages%short", localeId);
         if (locIdName != null && !locIdName.equals(localeId)) {
            return locIdName;
         }
      }

      String locIdName = this.langData.get("Languages", localeId);
      if ((locIdName == null || locIdName.equals(localeId)) && localeId.indexOf(95) < 0) {
         ULocale canonLocale = ULocale.createCanonical(localeId);
         String canonLocId = canonLocale.getName();
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            locIdName = this.langData.get("Languages%short", canonLocId);
            if (locIdName != null && !locIdName.equals(canonLocId)) {
               return locIdName;
            }
         }

         locIdName = this.langData.get("Languages", canonLocId);
      }

      return locIdName;
   }

   @Override
   public String languageDisplayName(String lang) {
      if (!lang.equals("root") && lang.indexOf(95) == -1) {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            String langName = this.langData.get("Languages%short", lang);
            if (langName != null && !langName.equals(lang)) {
               return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, langName);
            }
         }

         String langName = this.langData.get("Languages", lang);
         if (langName == null || langName.equals(lang)) {
            ULocale canonLocale = ULocale.createCanonical(lang);
            String canonLocId = canonLocale.getName();
            if (this.nameLength == DisplayContext.LENGTH_SHORT) {
               langName = this.langData.get("Languages%short", canonLocId);
               if (langName != null && !langName.equals(canonLocId)) {
                  return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, langName);
               }
            }

            langName = this.langData.get("Languages", canonLocId);
         }

         return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, langName);
      } else {
         return this.substituteHandling == DisplayContext.SUBSTITUTE ? lang : null;
      }
   }

   @Override
   public String scriptDisplayName(String script) {
      String str = this.langData.get("Scripts%stand-alone", script);
      if (str == null || str.equals(script)) {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            str = this.langData.get("Scripts%short", script);
            if (str != null && !str.equals(script)) {
               return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, str);
            }
         }

         str = this.langData.get("Scripts", script);
      }

      return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, str);
   }

   private String scriptDisplayNameInContext(String script, boolean skipAdjust) {
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         String scriptName = this.langData.get("Scripts%short", script);
         if (scriptName != null && !scriptName.equals(script)) {
            return skipAdjust ? scriptName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, scriptName);
         }
      }

      String scriptName = this.langData.get("Scripts", script);
      return skipAdjust ? scriptName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, scriptName);
   }

   @Override
   public String scriptDisplayNameInContext(String script) {
      return this.scriptDisplayNameInContext(script, false);
   }

   @Override
   public String scriptDisplayName(int scriptCode) {
      return this.scriptDisplayName(UScript.getShortName(scriptCode));
   }

   private String regionDisplayName(String region, boolean skipAdjust) {
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         String regionName = this.regionData.get("Countries%short", region);
         if (regionName != null && !regionName.equals(region)) {
            return skipAdjust ? regionName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY, regionName);
         }
      }

      String regionName = this.regionData.get("Countries", region);
      return skipAdjust ? regionName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY, regionName);
   }

   @Override
   public String regionDisplayName(String region) {
      return this.regionDisplayName(region, false);
   }

   private String variantDisplayName(String variant, boolean skipAdjust) {
      String variantName = this.langData.get("Variants", variant);
      return skipAdjust ? variantName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.VARIANT, variantName);
   }

   @Override
   public String variantDisplayName(String variant) {
      return this.variantDisplayName(variant, false);
   }

   private String keyDisplayName(String key, boolean skipAdjust) {
      String keyName = this.langData.get("Keys", key);
      return skipAdjust ? keyName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.KEY, keyName);
   }

   @Override
   public String keyDisplayName(String key) {
      return this.keyDisplayName(key, false);
   }

   private String keyValueDisplayName(String key, String value, boolean skipAdjust) {
      String keyValueName = null;
      if (key.equals("currency")) {
         keyValueName = this.currencyDisplayInfo.getName(AsciiUtil.toUpperString(value));
         if (keyValueName == null) {
            keyValueName = value;
         }
      } else {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            String tmp = this.langData.get("Types%short", key, value);
            if (tmp != null && !tmp.equals(value)) {
               keyValueName = tmp;
            }
         }

         if (keyValueName == null) {
            keyValueName = this.langData.get("Types", key, value);
         }
      }

      return skipAdjust ? keyValueName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.KEYVALUE, keyValueName);
   }

   @Override
   public String keyValueDisplayName(String key, String value) {
      return this.keyValueDisplayName(key, value, false);
   }

   @Override
   public List<LocaleDisplayNames.UiListItem> getUiListCompareWholeItems(Set<ULocale> localeSet, Comparator<LocaleDisplayNames.UiListItem> comparator) {
      DisplayContext capContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
      List<LocaleDisplayNames.UiListItem> result = new ArrayList<>();
      Map<ULocale, Set<ULocale>> baseToLocales = new HashMap<>();
      ULocale.Builder builder = new ULocale.Builder();

      for (ULocale locOriginal : localeSet) {
         builder.setLocale(locOriginal);
         ULocale loc = ULocale.addLikelySubtags(locOriginal);
         ULocale base = new ULocale(loc.getLanguage());
         Set<ULocale> locales = baseToLocales.get(base);
         if (locales == null) {
            baseToLocales.put(base, locales = new HashSet<>());
         }

         locales.add(loc);
      }

      for (Entry<ULocale, Set<ULocale>> entry : baseToLocales.entrySet()) {
         ULocale base = entry.getKey();
         Set<ULocale> values = entry.getValue();
         if (values.size() == 1) {
            ULocale locale = values.iterator().next();
            result.add(this.newRow(ULocale.minimizeSubtags(locale, ULocale.Minimize.FAVOR_SCRIPT), capContext));
         } else {
            Set<String> scripts = new HashSet<>();
            Set<String> regions = new HashSet<>();
            ULocale maxBase = ULocale.addLikelySubtags(base);
            scripts.add(maxBase.getScript());
            regions.add(maxBase.getCountry());

            for (ULocale locale : values) {
               scripts.add(locale.getScript());
               regions.add(locale.getCountry());
            }

            boolean hasScripts = scripts.size() > 1;
            boolean hasRegions = regions.size() > 1;

            for (ULocale locale : values) {
               ULocale.Builder modified = builder.setLocale(locale);
               if (!hasScripts) {
                  modified.setScript("");
               }

               if (!hasRegions) {
                  modified.setRegion("");
               }

               result.add(this.newRow(modified.build(), capContext));
            }
         }
      }

      Collections.sort(result, comparator);
      return result;
   }

   private LocaleDisplayNames.UiListItem newRow(ULocale modified, DisplayContext capContext) {
      ULocale minimized = ULocale.minimizeSubtags(modified, ULocale.Minimize.FAVOR_SCRIPT);
      String tempName = modified.getDisplayName(this.locale);
      boolean titlecase = capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
      String nameInDisplayLocale = titlecase ? toTitleWholeStringNoLowercase(this.locale, tempName) : tempName;
      tempName = modified.getDisplayName(modified);
      String nameInSelf = capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? toTitleWholeStringNoLowercase(modified, tempName) : tempName;
      return new LocaleDisplayNames.UiListItem(minimized, modified, nameInDisplayLocale, nameInSelf);
   }

   public static boolean haveData(LocaleDisplayNamesImpl.DataTableType type) {
      switch (type) {
         case LANG:
            return LocaleDisplayNamesImpl.LangDataTables.impl instanceof LocaleDisplayNamesImpl.ICUDataTables;
         case REGION:
            return LocaleDisplayNamesImpl.RegionDataTables.impl instanceof LocaleDisplayNamesImpl.ICUDataTables;
         default:
            throw new IllegalArgumentException("unknown type: " + type);
      }
   }

   private StringBuilder appendWithSep(String s, StringBuilder b) {
      if (b.length() == 0) {
         b.append(s);
      } else {
         SimpleFormatterImpl.formatAndReplace(this.separatorFormat, b, null, b, s);
      }

      return b;
   }

   static {
      contextUsageTypeMap.put("languages", LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE);
      contextUsageTypeMap.put("script", LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT);
      contextUsageTypeMap.put("territory", LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY);
      contextUsageTypeMap.put("variant", LocaleDisplayNamesImpl.CapitalizationContextUsage.VARIANT);
      contextUsageTypeMap.put("key", LocaleDisplayNamesImpl.CapitalizationContextUsage.KEY);
      contextUsageTypeMap.put("keyValue", LocaleDisplayNamesImpl.CapitalizationContextUsage.KEYVALUE);
   }

   private static class Cache {
      private ULocale locale;
      private LocaleDisplayNames.DialectHandling dialectHandling;
      private DisplayContext capitalization;
      private DisplayContext nameLength;
      private DisplayContext substituteHandling;
      private LocaleDisplayNames cache;

      private Cache() {
      }

      public LocaleDisplayNames get(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
         if (dialectHandling != this.dialectHandling
            || DisplayContext.CAPITALIZATION_NONE != this.capitalization
            || DisplayContext.LENGTH_FULL != this.nameLength
            || DisplayContext.SUBSTITUTE != this.substituteHandling
            || !locale.equals(this.locale)) {
            this.locale = locale;
            this.dialectHandling = dialectHandling;
            this.capitalization = DisplayContext.CAPITALIZATION_NONE;
            this.nameLength = DisplayContext.LENGTH_FULL;
            this.substituteHandling = DisplayContext.SUBSTITUTE;
            this.cache = new LocaleDisplayNamesImpl(locale, dialectHandling);
         }

         return this.cache;
      }

      public LocaleDisplayNames get(ULocale locale, DisplayContext... contexts) {
         LocaleDisplayNames.DialectHandling dialectHandlingIn = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
         DisplayContext capitalizationIn = DisplayContext.CAPITALIZATION_NONE;
         DisplayContext nameLengthIn = DisplayContext.LENGTH_FULL;
         DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;

         for (DisplayContext contextItem : contexts) {
            switch (contextItem.type()) {
               case DIALECT_HANDLING:
                  dialectHandlingIn = contextItem.value() == DisplayContext.STANDARD_NAMES.value()
                     ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES
                     : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                  break;
               case CAPITALIZATION:
                  capitalizationIn = contextItem;
                  break;
               case DISPLAY_LENGTH:
                  nameLengthIn = contextItem;
                  break;
               case SUBSTITUTE_HANDLING:
                  substituteHandling = contextItem;
            }
         }

         if (dialectHandlingIn != this.dialectHandling
            || capitalizationIn != this.capitalization
            || nameLengthIn != this.nameLength
            || substituteHandling != this.substituteHandling
            || !locale.equals(this.locale)) {
            this.locale = locale;
            this.dialectHandling = dialectHandlingIn;
            this.capitalization = capitalizationIn;
            this.nameLength = nameLengthIn;
            this.substituteHandling = substituteHandling;
            this.cache = new LocaleDisplayNamesImpl(locale, contexts);
         }

         return this.cache;
      }
   }

   private final class CapitalizationContextSink extends UResource.Sink {
      boolean hasCapitalizationUsage = false;

      private CapitalizationContextSink() {
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table contextsTable = value.getTable();

         for (int i = 0; contextsTable.getKeyAndValue(i, key, value); i++) {
            LocaleDisplayNamesImpl.CapitalizationContextUsage usage = LocaleDisplayNamesImpl.contextUsageTypeMap.get(key.toString());
            if (usage != null) {
               int[] intVector = value.getIntVector();
               if (intVector.length >= 2) {
                  int titlecaseInt = LocaleDisplayNamesImpl.this.capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU
                     ? intVector[0]
                     : intVector[1];
                  if (titlecaseInt != 0) {
                     LocaleDisplayNamesImpl.this.capitalizationUsage[usage.ordinal()] = true;
                     this.hasCapitalizationUsage = true;
                  }
               }
            }
         }
      }
   }

   private static enum CapitalizationContextUsage {
      LANGUAGE,
      SCRIPT,
      TERRITORY,
      VARIANT,
      KEY,
      KEYVALUE;
   }

   public static class DataTable {
      final boolean nullIfNotFound;

      DataTable(boolean nullIfNotFound) {
         this.nullIfNotFound = nullIfNotFound;
      }

      ULocale getLocale() {
         return ULocale.ROOT;
      }

      String get(String tableName, String code) {
         return this.get(tableName, null, code);
      }

      String get(String tableName, String subTableName, String code) {
         return this.nullIfNotFound ? null : code;
      }
   }

   public static enum DataTableType {
      LANG,
      REGION;
   }

   abstract static class DataTables {
      public abstract LocaleDisplayNamesImpl.DataTable get(ULocale var1, boolean var2);

      public static LocaleDisplayNamesImpl.DataTables load(String className) {
         try {
            return (LocaleDisplayNamesImpl.DataTables)Class.forName(className).newInstance();
         } catch (Throwable var2) {
            return new LocaleDisplayNamesImpl.DataTables() {
               @Override
               public LocaleDisplayNamesImpl.DataTable get(ULocale locale, boolean nullIfNotFound) {
                  return new LocaleDisplayNamesImpl.DataTable(nullIfNotFound);
               }
            };
         }
      }
   }

   static class ICUDataTable extends LocaleDisplayNamesImpl.DataTable {
      private final ICUResourceBundle bundle;

      public ICUDataTable(String path, ULocale locale, boolean nullIfNotFound) {
         super(nullIfNotFound);
         this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(path, locale.getBaseName());
      }

      @Override
      public ULocale getLocale() {
         return this.bundle.getULocale();
      }

      @Override
      public String get(String tableName, String subTableName, String code) {
         return ICUResourceTableAccess.getTableString(this.bundle, tableName, subTableName, code, this.nullIfNotFound ? null : code);
      }
   }

   abstract static class ICUDataTables extends LocaleDisplayNamesImpl.DataTables {
      private final String path;

      protected ICUDataTables(String path) {
         this.path = path;
      }

      @Override
      public LocaleDisplayNamesImpl.DataTable get(ULocale locale, boolean nullIfNotFound) {
         return new LocaleDisplayNamesImpl.ICUDataTable(this.path, locale, nullIfNotFound);
      }
   }

   static class LangDataTables {
      static final LocaleDisplayNamesImpl.DataTables impl = LocaleDisplayNamesImpl.DataTables.load(
         "com.cobblemon.mod.relocations.ibm.icu.impl.ICULangDataTables"
      );
   }

   static class RegionDataTables {
      static final LocaleDisplayNamesImpl.DataTables impl = LocaleDisplayNamesImpl.DataTables.load(
         "com.cobblemon.mod.relocations.ibm.icu.impl.ICURegionDataTables"
      );
   }
}
