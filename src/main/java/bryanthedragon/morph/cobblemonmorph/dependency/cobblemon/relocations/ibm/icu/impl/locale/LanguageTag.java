package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageTag {
   private static final boolean JDKIMPL = false;
   public static final String SEP = "-";
   public static final String PRIVATEUSE = "x";
   public static String UNDETERMINED = "und";
   public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
   private String _language = "";
   private String _script = "";
   private String _region = "";
   private String _privateuse = "";
   private List<String> _extlangs = Collections.emptyList();
   private List<String> _variants = Collections.emptyList();
   private List<String> _extensions = Collections.emptyList();
   private static final Map<AsciiUtil.CaseInsensitiveKey, String[]> LEGACY = new HashMap<>();

   private LanguageTag() {
   }

   public static LanguageTag parse(String languageTag, ParseStatus sts) {
      if (sts == null) {
         sts = new ParseStatus();
      } else {
         sts.reset();
      }

      boolean isLegacy = false;
      String[] gfmap = LEGACY.get(new AsciiUtil.CaseInsensitiveKey(languageTag));
      int dash = 2;

      while (gfmap == null && (dash = languageTag.indexOf(45, dash + 1)) != -1) {
         gfmap = LEGACY.get(new AsciiUtil.CaseInsensitiveKey(languageTag.substring(0, dash)));
      }

      StringTokenIterator itr;
      if (gfmap != null) {
         if (gfmap[0].length() == languageTag.length()) {
            itr = new StringTokenIterator(gfmap[1], "-");
         } else {
            itr = new StringTokenIterator(gfmap[1] + languageTag.substring(dash), "-");
         }

         isLegacy = true;
      } else {
         itr = new StringTokenIterator(languageTag, "-");
      }

      LanguageTag tag = new LanguageTag();
      if (tag.parseLanguage(itr, sts)) {
         if (tag._language.length() <= 3) {
            tag.parseExtlangs(itr, sts);
         }

         tag.parseScript(itr, sts);
         tag.parseRegion(itr, sts);
         tag.parseVariants(itr, sts);
         tag.parseExtensions(itr, sts);
      }

      tag.parsePrivateuse(itr, sts);
      if (isLegacy) {
         assert itr.isDone();

         assert !sts.isError();

         sts._parseLength = languageTag.length();
      } else if (!itr.isDone() && !sts.isError()) {
         String s = itr.current();
         sts._errorIndex = itr.currentStart();
         if (s.length() == 0) {
            sts._errorMsg = "Empty subtag";
         } else {
            sts._errorMsg = "Invalid subtag: " + s;
         }
      }

      return tag;
   }

   private boolean parseLanguage(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;
         String s = itr.current();
         if (isLanguage(s)) {
            found = true;
            this._language = s;
            sts._parseLength = itr.currentEnd();
            itr.next();
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parseExtlangs(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;

         while (!itr.isDone()) {
            String s = itr.current();
            if (!isExtlang(s)) {
               break;
            }

            found = true;
            if (this._extlangs.isEmpty()) {
               this._extlangs = new ArrayList<>(3);
            }

            this._extlangs.add(s);
            sts._parseLength = itr.currentEnd();
            itr.next();
            if (this._extlangs.size() == 3) {
               break;
            }
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parseScript(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;
         String s = itr.current();
         if (isScript(s)) {
            found = true;
            this._script = s;
            sts._parseLength = itr.currentEnd();
            itr.next();
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parseRegion(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;
         String s = itr.current();
         if (isRegion(s)) {
            found = true;
            this._region = s;
            sts._parseLength = itr.currentEnd();
            itr.next();
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parseVariants(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;

         while (!itr.isDone()) {
            String s = itr.current();
            if (!isVariant(s)) {
               break;
            }

            found = true;
            if (this._variants.isEmpty()) {
               this._variants = new ArrayList<>(3);
            }

            s = s.toUpperCase();
            if (!this._variants.contains(s)) {
               this._variants.add(s);
            }

            sts._parseLength = itr.currentEnd();
            itr.next();
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parseExtensions(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found;
         for (found = false; !itr.isDone(); found = true) {
            String s = itr.current();
            if (!isExtensionSingleton(s)) {
               break;
            }

            int start = itr.currentStart();
            String singleton = s.toLowerCase();
            StringBuilder sb = new StringBuilder(singleton);
            itr.next();

            while (!itr.isDone()) {
               s = itr.current();
               if (!isExtensionSubtag(s)) {
                  break;
               }

               sb.append("-").append(s);
               sts._parseLength = itr.currentEnd();
               itr.next();
            }

            if (sts._parseLength <= start) {
               sts._errorIndex = start;
               sts._errorMsg = "Incomplete extension '" + singleton + "'";
               break;
            }

            if (this._extensions.size() == 0) {
               this._extensions = new ArrayList<>(4);
            }

            boolean alreadyHas = false;

            for (String extension : this._extensions) {
               alreadyHas |= extension.charAt(0) == sb.charAt(0);
            }

            if (!alreadyHas) {
               this._extensions.add(sb.toString());
            }
         }

         return found;
      } else {
         return false;
      }
   }

   private boolean parsePrivateuse(StringTokenIterator itr, ParseStatus sts) {
      if (!itr.isDone() && !sts.isError()) {
         boolean found = false;
         String s = itr.current();
         if (isPrivateusePrefix(s)) {
            int start = itr.currentStart();
            StringBuilder sb = new StringBuilder(s);
            itr.next();

            while (!itr.isDone()) {
               s = itr.current();
               if (!isPrivateuseSubtag(s)) {
                  break;
               }

               sb.append("-").append(s);
               sts._parseLength = itr.currentEnd();
               itr.next();
            }

            if (sts._parseLength <= start) {
               sts._errorIndex = start;
               sts._errorMsg = "Incomplete privateuse";
            } else {
               this._privateuse = sb.toString();
               found = true;
            }
         }

         return found;
      } else {
         return false;
      }
   }

   public static LanguageTag parseLocale(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
      LanguageTag tag = new LanguageTag();
      String language = baseLocale.getLanguage();
      String script = baseLocale.getScript();
      String region = baseLocale.getRegion();
      String variant = baseLocale.getVariant();
      boolean hasSubtag = false;
      String privuseVar = null;
      if (language.length() > 0 && isLanguage(language)) {
         if (language.equals("iw")) {
            language = "he";
         } else if (language.equals("ji")) {
            language = "yi";
         } else if (language.equals("in")) {
            language = "id";
         }

         tag._language = language;
      }

      if (script.length() > 0 && isScript(script)) {
         tag._script = canonicalizeScript(script);
         hasSubtag = true;
      }

      if (region.length() > 0 && isRegion(region)) {
         tag._region = canonicalizeRegion(region);
         hasSubtag = true;
      }

      if (variant.length() > 0) {
         List<String> variants = null;
         StringTokenIterator varitr = new StringTokenIterator(variant, "_");

         while (!varitr.isDone()) {
            String var = varitr.current();
            if (!isVariant(var)) {
               break;
            }

            if (variants == null) {
               variants = new ArrayList<>();
            }

            variants.add(canonicalizeVariant(var));
            varitr.next();
         }

         if (variants != null) {
            tag._variants = variants;
            hasSubtag = true;
         }

         if (!varitr.isDone()) {
            StringBuilder buf = new StringBuilder();

            while (!varitr.isDone()) {
               String prvv = varitr.current();
               if (!isPrivateuseSubtag(prvv)) {
                  break;
               }

               if (buf.length() > 0) {
                  buf.append("-");
               }

               prvv = AsciiUtil.toLowerString(prvv);
               buf.append(prvv);
               varitr.next();
            }

            if (buf.length() > 0) {
               privuseVar = buf.toString();
            }
         }
      }

      List<String> extensions = null;
      String privateuse = null;

      for (Character locextKey : localeExtensions.getKeys()) {
         Extension ext = localeExtensions.getExtension(locextKey);
         if (isPrivateusePrefixChar(locextKey)) {
            privateuse = ext.getValue();
         } else {
            if (extensions == null) {
               extensions = new ArrayList<>();
            }

            extensions.add(locextKey.toString() + "-" + ext.getValue());
         }
      }

      if (extensions != null) {
         tag._extensions = extensions;
         hasSubtag = true;
      }

      if (privuseVar != null) {
         if (privateuse == null) {
            privateuse = "lvariant-" + privuseVar;
         } else {
            privateuse = privateuse + "-" + "lvariant" + "-" + privuseVar.replace("_", "-");
         }
      }

      if (privateuse != null) {
         tag._privateuse = privateuse;
      }

      if (tag._language.length() == 0 && (hasSubtag || privateuse == null)) {
         tag._language = UNDETERMINED;
      }

      return tag;
   }

   public String getLanguage() {
      return this._language;
   }

   public List<String> getExtlangs() {
      return Collections.unmodifiableList(this._extlangs);
   }

   public String getScript() {
      return this._script;
   }

   public String getRegion() {
      return this._region;
   }

   public List<String> getVariants() {
      return Collections.unmodifiableList(this._variants);
   }

   public List<String> getExtensions() {
      return Collections.unmodifiableList(this._extensions);
   }

   public String getPrivateuse() {
      return this._privateuse;
   }

   public static boolean isLanguage(String s) {
      return s.length() >= 2 && s.length() <= 8 && AsciiUtil.isAlphaString(s);
   }

   public static boolean isExtlang(String s) {
      return s.length() == 3 && AsciiUtil.isAlphaString(s);
   }

   public static boolean isScript(String s) {
      return s.length() == 4 && AsciiUtil.isAlphaString(s);
   }

   public static boolean isRegion(String s) {
      return s.length() == 2 && AsciiUtil.isAlphaString(s) || s.length() == 3 && AsciiUtil.isNumericString(s);
   }

   public static boolean isVariant(String s) {
      int len = s.length();
      if (len >= 5 && len <= 8) {
         return AsciiUtil.isAlphaNumericString(s);
      } else {
         return len != 4
            ? false
            : AsciiUtil.isNumeric(s.charAt(0))
               && AsciiUtil.isAlphaNumeric(s.charAt(1))
               && AsciiUtil.isAlphaNumeric(s.charAt(2))
               && AsciiUtil.isAlphaNumeric(s.charAt(3));
      }
   }

   public static boolean isTKey(String s) {
      return s.length() == 2 && AsciiUtil.isAlpha(s.charAt(0)) && AsciiUtil.isNumeric(s.charAt(1));
   }

   public static boolean isExtensionSingleton(String s) {
      return s.length() == 1 && AsciiUtil.isAlphaNumericString(s) && !AsciiUtil.caseIgnoreMatch("x", s);
   }

   public static boolean isExtensionSingletonChar(char c) {
      return isExtensionSingleton(String.valueOf(c));
   }

   public static boolean isExtensionSubtag(String s) {
      return s.length() >= 2 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
   }

   public static boolean isPrivateusePrefix(String s) {
      return s.length() == 1 && AsciiUtil.caseIgnoreMatch("x", s);
   }

   public static boolean isPrivateusePrefixChar(char c) {
      return AsciiUtil.caseIgnoreMatch("x", String.valueOf(c));
   }

   public static boolean isPrivateuseSubtag(String s) {
      return s.length() >= 1 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
   }

   public static String canonicalizeLanguage(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizeExtlang(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizeScript(String s) {
      return AsciiUtil.toTitleString(s);
   }

   public static String canonicalizeRegion(String s) {
      return AsciiUtil.toUpperString(s);
   }

   public static String canonicalizeVariant(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizeExtension(String s) {
      s = AsciiUtil.toLowerString(s);
      if (s.startsWith("u-")) {
         while (s.endsWith("-true")) {
            s = s.substring(0, s.length() - 5);
         }

         int found;
         while ((found = s.indexOf("-true-")) > 0) {
            s = s.substring(0, found) + s.substring(found + 5);
         }

         while (s.endsWith("-yes")) {
            s = s.substring(0, s.length() - 4);
         }

         while ((found = s.indexOf("-yes-")) > 0) {
            s = s.substring(0, found) + s.substring(found + 4);
         }
      }

      return s;
   }

   public static String canonicalizeExtensionSingleton(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizeExtensionSubtag(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizePrivateuse(String s) {
      return AsciiUtil.toLowerString(s);
   }

   public static String canonicalizePrivateuseSubtag(String s) {
      return AsciiUtil.toLowerString(s);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this._language.length() > 0) {
         sb.append(this._language);

         for (String extlang : this._extlangs) {
            sb.append("-").append(extlang);
         }

         if (this._script.length() > 0) {
            sb.append("-").append(this._script);
         }

         if (this._region.length() > 0) {
            sb.append("-").append(this._region);
         }

         for (String variant : this._variants) {
            sb.append("-").append(variant);
         }

         for (String extension : this._extensions) {
            sb.append("-").append(extension);
         }
      }

      if (this._privateuse.length() > 0) {
         if (sb.length() > 0) {
            sb.append("-");
         }

         sb.append(this._privateuse);
      }

      return sb.toString();
   }

   static {
      String[][] entries = new String[][]{
         {"art-lojban", "jbo"},
         {"cel-gaulish", "xtg"},
         {"en-GB-oed", "en-GB-x-oed"},
         {"i-ami", "ami"},
         {"i-bnn", "bnn"},
         {"i-default", "en-x-i-default"},
         {"i-enochian", "und-x-i-enochian"},
         {"i-hak", "hak"},
         {"i-klingon", "tlh"},
         {"i-lux", "lb"},
         {"i-mingo", "see-x-i-mingo"},
         {"i-navajo", "nv"},
         {"i-pwn", "pwn"},
         {"i-tao", "tao"},
         {"i-tay", "tay"},
         {"i-tsu", "tsu"},
         {"no-bok", "nb"},
         {"no-nyn", "nn"},
         {"sgn-BE-FR", "sfb"},
         {"sgn-BE-NL", "vgt"},
         {"sgn-CH-DE", "sgg"},
         {"zh-guoyu", "cmn"},
         {"zh-hakka", "hak"},
         {"zh-min", "nan-x-zh-min"},
         {"zh-min-nan", "nan"},
         {"zh-xiang", "hsn"}
      };

      for (String[] e : entries) {
         LEGACY.put(new AsciiUtil.CaseInsensitiveKey(e[0]), e);
      }
   }
}
