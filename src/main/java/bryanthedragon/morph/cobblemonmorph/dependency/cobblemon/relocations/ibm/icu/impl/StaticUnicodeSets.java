package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Map;

public class StaticUnicodeSets {
   private static final Map<StaticUnicodeSets.Key, UnicodeSet> unicodeSets = new EnumMap<>(StaticUnicodeSets.Key.class);

   public static UnicodeSet get(StaticUnicodeSets.Key key) {
      UnicodeSet candidate = unicodeSets.get(key);
      return candidate == null ? UnicodeSet.EMPTY : candidate;
   }

   public static StaticUnicodeSets.Key chooseFrom(String str, StaticUnicodeSets.Key key1) {
      return get(key1).contains(str) ? key1 : null;
   }

   public static StaticUnicodeSets.Key chooseFrom(String str, StaticUnicodeSets.Key key1, StaticUnicodeSets.Key key2) {
      return get(key1).contains(str) ? key1 : chooseFrom(str, key2);
   }

   public static StaticUnicodeSets.Key chooseCurrency(String str) {
      if (get(StaticUnicodeSets.Key.DOLLAR_SIGN).contains(str)) {
         return StaticUnicodeSets.Key.DOLLAR_SIGN;
      } else if (get(StaticUnicodeSets.Key.POUND_SIGN).contains(str)) {
         return StaticUnicodeSets.Key.POUND_SIGN;
      } else if (get(StaticUnicodeSets.Key.RUPEE_SIGN).contains(str)) {
         return StaticUnicodeSets.Key.RUPEE_SIGN;
      } else if (get(StaticUnicodeSets.Key.YEN_SIGN).contains(str)) {
         return StaticUnicodeSets.Key.YEN_SIGN;
      } else {
         return get(StaticUnicodeSets.Key.WON_SIGN).contains(str) ? StaticUnicodeSets.Key.WON_SIGN : null;
      }
   }

   private static UnicodeSet computeUnion(StaticUnicodeSets.Key k1, StaticUnicodeSets.Key k2) {
      return new UnicodeSet().addAll(get(k1)).addAll(get(k2)).freeze();
   }

   private static UnicodeSet computeUnion(StaticUnicodeSets.Key k1, StaticUnicodeSets.Key k2, StaticUnicodeSets.Key k3) {
      return new UnicodeSet().addAll(get(k1)).addAll(get(k2)).addAll(get(k3)).freeze();
   }

   private static void saveSet(StaticUnicodeSets.Key key, String unicodeSetPattern) {
      assert unicodeSets.get(key) == null;

      unicodeSets.put(key, new UnicodeSet(unicodeSetPattern).freeze());
   }

   static {
      unicodeSets.put(StaticUnicodeSets.Key.EMPTY, new UnicodeSet("[]").freeze());
      unicodeSets.put(StaticUnicodeSets.Key.DEFAULT_IGNORABLES, new UnicodeSet("[[:Zs:][\\u0009][:Bidi_Control:][:Variation_Selector:]]").freeze());
      unicodeSets.put(StaticUnicodeSets.Key.STRICT_IGNORABLES, new UnicodeSet("[[:Bidi_Control:]]").freeze());
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", ULocale.ROOT);
      rb.getAllItemsWithFallback("parse", new StaticUnicodeSets.ParseDataSink());

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.COMMA);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.STRICT_COMMA);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.PERIOD);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.STRICT_PERIOD);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.APOSTROPHE_SIGN);

      UnicodeSet otherGrouping = new UnicodeSet("[٬‘\\u0020\\u00A0\\u2000-\\u200A\\u202F\\u205F\\u3000]");
      otherGrouping.addAll(unicodeSets.get(StaticUnicodeSets.Key.APOSTROPHE_SIGN));
      unicodeSets.put(StaticUnicodeSets.Key.OTHER_GROUPING_SEPARATORS, otherGrouping.freeze());
      unicodeSets.put(
         StaticUnicodeSets.Key.ALL_SEPARATORS,
         computeUnion(StaticUnicodeSets.Key.COMMA, StaticUnicodeSets.Key.PERIOD, StaticUnicodeSets.Key.OTHER_GROUPING_SEPARATORS)
      );
      unicodeSets.put(
         StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS,
         computeUnion(StaticUnicodeSets.Key.STRICT_COMMA, StaticUnicodeSets.Key.STRICT_PERIOD, StaticUnicodeSets.Key.OTHER_GROUPING_SEPARATORS)
      );

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.MINUS_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.PLUS_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.PERCENT_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.PERMILLE_SIGN);

      unicodeSets.put(StaticUnicodeSets.Key.INFINITY_SIGN, new UnicodeSet("[∞]").freeze());

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.DOLLAR_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.POUND_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.RUPEE_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.YEN_SIGN);

      assert unicodeSets.containsKey(StaticUnicodeSets.Key.WON_SIGN);

      unicodeSets.put(StaticUnicodeSets.Key.DIGITS, new UnicodeSet("[:digit:]").freeze());
      unicodeSets.put(StaticUnicodeSets.Key.DIGITS_OR_ALL_SEPARATORS, computeUnion(StaticUnicodeSets.Key.DIGITS, StaticUnicodeSets.Key.ALL_SEPARATORS));
      unicodeSets.put(
         StaticUnicodeSets.Key.DIGITS_OR_STRICT_ALL_SEPARATORS, computeUnion(StaticUnicodeSets.Key.DIGITS, StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS)
      );
   }

   public static enum Key {
      EMPTY,
      DEFAULT_IGNORABLES,
      STRICT_IGNORABLES,
      COMMA,
      PERIOD,
      STRICT_COMMA,
      STRICT_PERIOD,
      APOSTROPHE_SIGN,
      OTHER_GROUPING_SEPARATORS,
      ALL_SEPARATORS,
      STRICT_ALL_SEPARATORS,
      MINUS_SIGN,
      PLUS_SIGN,
      PERCENT_SIGN,
      PERMILLE_SIGN,
      INFINITY_SIGN,
      DOLLAR_SIGN,
      POUND_SIGN,
      RUPEE_SIGN,
      YEN_SIGN,
      WON_SIGN,
      DIGITS,
      DIGITS_OR_ALL_SEPARATORS,
      DIGITS_OR_STRICT_ALL_SEPARATORS;
   }

   static class ParseDataSink extends UResource.Sink {
      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table contextsTable = value.getTable();

         for (int i = 0; contextsTable.getKeyAndValue(i, key, value); i++) {
            if (!key.contentEquals("date")) {
               assert key.contentEquals("general") || key.contentEquals("number");

               UResource.Table strictnessTable = value.getTable();

               for (int j = 0; strictnessTable.getKeyAndValue(j, key, value); j++) {
                  boolean isLenient = key.contentEquals("lenient");
                  UResource.Array array = value.getArray();

                  for (int k = 0; k < array.getSize(); k++) {
                     array.getValue(k, value);
                     String str = value.toString();
                     if (str.indexOf(46) != -1) {
                        StaticUnicodeSets.saveSet(isLenient ? StaticUnicodeSets.Key.PERIOD : StaticUnicodeSets.Key.STRICT_PERIOD, str);
                     } else if (str.indexOf(44) != -1) {
                        StaticUnicodeSets.saveSet(isLenient ? StaticUnicodeSets.Key.COMMA : StaticUnicodeSets.Key.STRICT_COMMA, str);
                     } else if (str.indexOf(43) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.PLUS_SIGN, str);
                     } else if (str.indexOf(45) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.MINUS_SIGN, str);
                     } else if (str.indexOf(36) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.DOLLAR_SIGN, str);
                     } else if (str.indexOf(163) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.POUND_SIGN, str);
                     } else if (str.indexOf(8377) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.RUPEE_SIGN, str);
                     } else if (str.indexOf(165) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.YEN_SIGN, str);
                     } else if (str.indexOf(8361) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.WON_SIGN, str);
                     } else if (str.indexOf(37) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.PERCENT_SIGN, str);
                     } else if (str.indexOf(8240) != -1) {
                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.PERMILLE_SIGN, str);
                     } else {
                        if (str.indexOf(8217) == -1) {
                           throw new AssertionError("Unknown class of parse lenients: " + str);
                        }

                        StaticUnicodeSets.saveSet(StaticUnicodeSets.Key.APOSTROPHE_SIGN, str);
                     }
                  }
               }
            }
         }
      }
   }
}
