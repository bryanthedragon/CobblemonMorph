package com.cobblemon.mod.relocations.ibm.icu.impl.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUDebug;
import com.cobblemon.mod.relocations.ibm.icu.text.CollationElementIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.Collator;
import com.cobblemon.mod.relocations.ibm.icu.text.RbnfLenientScanner;
import com.cobblemon.mod.relocations.ibm.icu.text.RbnfLenientScannerProvider;
import com.cobblemon.mod.relocations.ibm.icu.text.RuleBasedCollator;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RbnfScannerProviderImpl implements RbnfLenientScannerProvider {
   private static final boolean DEBUG = ICUDebug.enabled("rbnf");
   private Map<String, RbnfLenientScanner> cache = new HashMap<>();

   @Deprecated
   @Override
   public RbnfLenientScanner get(ULocale locale, String extras) {
      RbnfLenientScanner result = null;
      String key = locale.toString() + "/" + extras;
      synchronized (this.cache) {
         result = this.cache.get(key);
         if (result != null) {
            return result;
         }
      }

      result = this.createScanner(locale, extras);
      synchronized (this.cache) {
         this.cache.put(key, result);
         return result;
      }
   }

   @Deprecated
   protected RbnfLenientScanner createScanner(ULocale locale, String extras) {
      RuleBasedCollator collator = null;

      try {
         collator = (RuleBasedCollator)Collator.getInstance(locale.toLocale());
         if (extras != null) {
            String rules = collator.getRules() + extras;
            collator = new RuleBasedCollator(rules);
         }

         collator.setDecomposition(17);
      } catch (Exception var5) {
         if (DEBUG) {
            var5.printStackTrace();
            System.out.println("++++");
         }

         collator = null;
      }

      return new RbnfScannerProviderImpl.RbnfLenientScannerImpl(collator);
   }

   private static class RbnfLenientScannerImpl implements RbnfLenientScanner {
      private final RuleBasedCollator collator;

      private RbnfLenientScannerImpl(RuleBasedCollator rbc) {
         this.collator = rbc;
      }

      @Override
      public boolean allIgnorable(String s) {
         CollationElementIterator iter = this.collator.getCollationElementIterator(s);
         int o = iter.next();

         while (o != -1 && CollationElementIterator.primaryOrder(o) == 0) {
            o = iter.next();
         }

         return o == -1;
      }

      @Override
      public int[] findText(String str, String key, int startingAt) {
         int p = startingAt;

         for (int keyLen = 0; p < str.length() && keyLen == 0; p++) {
            keyLen = this.prefixLength(str.substring(p), key);
            if (keyLen != 0) {
               return new int[]{p, keyLen};
            }
         }

         return new int[]{-1, 0};
      }

      public int[] findText2(String str, String key, int startingAt) {
         CollationElementIterator strIter = this.collator.getCollationElementIterator(str);
         CollationElementIterator keyIter = this.collator.getCollationElementIterator(key);
         int keyStart = -1;
         strIter.setOffset(startingAt);
         int oStr = strIter.next();
         int oKey = keyIter.next();

         while (oKey != -1) {
            while (oStr != -1 && CollationElementIterator.primaryOrder(oStr) == 0) {
               oStr = strIter.next();
            }

            while (oKey != -1 && CollationElementIterator.primaryOrder(oKey) == 0) {
               oKey = keyIter.next();
            }

            if (oStr == -1) {
               return new int[]{-1, 0};
            }

            if (oKey == -1) {
               break;
            }

            if (CollationElementIterator.primaryOrder(oStr) == CollationElementIterator.primaryOrder(oKey)) {
               keyStart = strIter.getOffset();
               oStr = strIter.next();
               oKey = keyIter.next();
            } else if (keyStart != -1) {
               keyStart = -1;
               keyIter.reset();
            } else {
               oStr = strIter.next();
            }
         }

         return new int[]{keyStart, strIter.getOffset() - keyStart};
      }

      @Override
      public int prefixLength(String str, String prefix) {
         CollationElementIterator strIter = this.collator.getCollationElementIterator(str);
         CollationElementIterator prefixIter = this.collator.getCollationElementIterator(prefix);
         int oStr = strIter.next();

         for (int oPrefix = prefixIter.next(); oPrefix != -1; oPrefix = prefixIter.next()) {
            while (CollationElementIterator.primaryOrder(oStr) == 0 && oStr != -1) {
               oStr = strIter.next();
            }

            while (CollationElementIterator.primaryOrder(oPrefix) == 0 && oPrefix != -1) {
               oPrefix = prefixIter.next();
            }

            if (oPrefix == -1) {
               break;
            }

            if (oStr == -1) {
               return 0;
            }

            if (CollationElementIterator.primaryOrder(oStr) != CollationElementIterator.primaryOrder(oPrefix)) {
               return 0;
            }

            oStr = strIter.next();
         }

         int result = strIter.getOffset();
         if (oStr != -1) {
            result--;
         }

         return result;
      }
   }
}
