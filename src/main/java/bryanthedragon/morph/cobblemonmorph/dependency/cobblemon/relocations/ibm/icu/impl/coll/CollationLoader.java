package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationLoader {
   private static volatile String rootRules = null;

   private CollationLoader() {
   }

   private static void loadRootRules() {
      if (rootRules == null) {
         synchronized (CollationLoader.class) {
            if (rootRules == null) {
               UResourceBundle rootBundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", ULocale.ROOT);
               rootRules = rootBundle.getString("UCARules");
            }
         }
      }
   }

   public static String getRootRules() {
      loadRootRules();
      return rootRules;
   }

   static String loadRules(ULocale locale, String collationType) {
      UResourceBundle bundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", locale);
      UResourceBundle data = ((ICUResourceBundle)bundle).getWithFallback("collations/" + CollationLoader.ASCII.toLowerCase(collationType));
      return data.getString("Sequence");
   }

   private static final UResourceBundle findWithFallback(UResourceBundle table, String entryName) {
      return ((ICUResourceBundle)table).findWithFallback(entryName);
   }

   public static CollationTailoring loadTailoring(ULocale locale, Output<ULocale> outValidLocale) {
      CollationTailoring root = CollationRoot.getRoot();
      String localeName = locale.getName();
      if (localeName.length() != 0 && !localeName.equals("root")) {
         UResourceBundle bundle = null;

         try {
            bundle = ICUResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", locale, ICUResourceBundle.OpenType.LOCALE_ROOT
            );
         } catch (MissingResourceException var20) {
            outValidLocale.value = ULocale.ROOT;
            return root;
         }

         ULocale validLocale = bundle.getULocale();
         String validLocaleName = validLocale.getName();
         if (validLocaleName.length() == 0 || validLocaleName.equals("root")) {
            validLocale = ULocale.ROOT;
         }

         outValidLocale.value = validLocale;

         UResourceBundle collations;
         try {
            collations = bundle.get("collations");
            if (collations == null) {
               return root;
            }
         } catch (MissingResourceException var21) {
            return root;
         }

         String type = locale.getKeywordValue("collation");
         String defaultType = "standard";
         String defT = ((ICUResourceBundle)collations).findStringWithFallback("default");
         if (defT != null) {
            defaultType = defT;
         }

         if (type != null && !type.equals("default")) {
            type = CollationLoader.ASCII.toLowerCase(type);
         } else {
            type = defaultType;
         }

         UResourceBundle data = findWithFallback(collations, type);
         if (data == null && type.length() > 6 && type.startsWith("search")) {
            type = "search";
            data = findWithFallback(collations, type);
         }

         if (data == null && !type.equals(defaultType)) {
            type = defaultType;
            data = findWithFallback(collations, defaultType);
         }

         if (data == null && !type.equals("standard")) {
            type = "standard";
            data = findWithFallback(collations, type);
         }

         if (data == null) {
            return root;
         } else {
            ULocale actualLocale = data.getULocale();
            String actualLocaleName = actualLocale.getName();
            if (actualLocaleName.length() == 0 || actualLocaleName.equals("root")) {
               actualLocale = ULocale.ROOT;
               if (type.equals("standard")) {
                  return root;
               }
            }

            CollationTailoring t = new CollationTailoring(root.settings);
            t.actualLocale = actualLocale;
            UResourceBundle binary = data.get("%%CollationBin");
            ByteBuffer inBytes = binary.getBinary();

            try {
               CollationDataReader.read(root, inBytes, t);
            } catch (IOException var19) {
               throw new ICUUncheckedIOException("Failed to load collation tailoring data for locale:" + actualLocale + " type:" + type, var19);
            }

            try {
               t.setRulesResource(data.get("Sequence"));
            } catch (MissingResourceException var18) {
            }

            if (!type.equals(defaultType)) {
               outValidLocale.value = validLocale.setKeywordValue("collation", type);
            }

            if (!actualLocale.equals(validLocale)) {
               UResourceBundle actualBundle = UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/coll", actualLocale);
               defT = ((ICUResourceBundle)actualBundle).findStringWithFallback("collations/default");
               if (defT != null) {
                  defaultType = defT;
               }
            }

            if (!type.equals(defaultType)) {
               t.actualLocale = t.actualLocale.setKeywordValue("collation", type);
            }

            return t;
         }
      } else {
         outValidLocale.value = ULocale.ROOT;
         return root;
      }
   }

   private static final class ASCII {
      static String toLowerCase(String s) {
         for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('A' <= c && c <= 'Z') {
               StringBuilder sb = new StringBuilder(s.length());
               sb.append(s, 0, i).append((char)(c + ' '));

               while (++i < s.length()) {
                  c = s.charAt(i);
                  if ('A' <= c && c <= 'Z') {
                     c = (char)(c + ' ');
                  }

                  sb.append(c);
               }

               return sb.toString();
            }
         }

         return s;
      }
   }
}
