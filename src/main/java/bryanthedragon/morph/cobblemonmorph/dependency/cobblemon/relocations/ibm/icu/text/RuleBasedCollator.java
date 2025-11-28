package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ClassLoaderUtil;
import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.BOCSU;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationCompare;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationData;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationFastLatin;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationIterator;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationKeys;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationLoader;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationRoot;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationSettings;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationTailoring;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.ContractionsAndExpansions;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.FCDUTF16CollationIterator;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.SharedObject;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.TailoredSet;
import com.cobblemon.mod.relocations.ibm.icu.impl.coll.UTF16CollationIterator;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.VersionInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class RuleBasedCollator extends Collator {
   private Lock frozenLock;
   private RuleBasedCollator.CollationBuffer collationBuffer;
   CollationData data;
   SharedObject.Reference<CollationSettings> settings;
   CollationTailoring tailoring;
   private ULocale validLocale;
   private boolean actualLocaleIsSameAsValid;

   public RuleBasedCollator(String rules) throws Exception {
      if (rules == null) {
         throw new IllegalArgumentException("Collation rules can not be null");
      } else {
         this.validLocale = ULocale.ROOT;
         this.internalBuildTailoring(rules);
      }
   }

   private final void internalBuildTailoring(String rules) throws Exception {
      CollationTailoring base = CollationRoot.getRoot();
      ClassLoader classLoader = ClassLoaderUtil.getClassLoader(this.getClass());

      CollationTailoring t;
      try {
         Class<?> builderClass = classLoader.loadClass("com.cobblemon.mod.relocations.ibm.icu.impl.coll.CollationBuilder");
         Object builder = builderClass.getConstructor(CollationTailoring.class).newInstance(base);
         Method parseAndBuild = builderClass.getMethod("parseAndBuild", String.class);
         t = (CollationTailoring)parseAndBuild.invoke(builder, rules);
      } catch (InvocationTargetException var8) {
         throw (Exception)var8.getTargetException();
      }

      t.actualLocale = null;
      this.adoptTailoring(t);
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      return this.isFrozen() ? this : this.cloneAsThawed();
   }

   private final void initMaxExpansions() {
      synchronized (this.tailoring) {
         if (this.tailoring.maxExpansions == null) {
            this.tailoring.maxExpansions = CollationElementIterator.computeMaxExpansions(this.tailoring.data);
         }
      }
   }

   public CollationElementIterator getCollationElementIterator(String source) {
      this.initMaxExpansions();
      return new CollationElementIterator(source, this);
   }

   public CollationElementIterator getCollationElementIterator(CharacterIterator source) {
      this.initMaxExpansions();
      CharacterIterator newsource = (CharacterIterator)source.clone();
      return new CollationElementIterator(newsource, this);
   }

   public CollationElementIterator getCollationElementIterator(UCharacterIterator source) {
      this.initMaxExpansions();
      return new CollationElementIterator(source, this);
   }

   @Override
   public boolean isFrozen() {
      return this.frozenLock != null;
   }

   @Override
   public Collator freeze() {
      if (!this.isFrozen()) {
         this.frozenLock = new ReentrantLock();
         if (this.collationBuffer == null) {
            this.collationBuffer = new RuleBasedCollator.CollationBuffer(this.data);
         }
      }

      return this;
   }

   public RuleBasedCollator cloneAsThawed() {
      try {
         RuleBasedCollator result = (RuleBasedCollator)super.clone();
         result.settings = this.settings.clone();
         result.collationBuffer = null;
         result.frozenLock = null;
         return result;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   private void checkNotFrozen() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen RuleBasedCollator");
      }
   }

   private final CollationSettings getOwnedSettings() {
      return this.settings.copyOnWrite();
   }

   private final CollationSettings getDefaultSettings() {
      return this.tailoring.settings.readOnly();
   }

   @Deprecated
   public void setHiraganaQuaternary(boolean flag) {
      this.checkNotFrozen();
   }

   @Deprecated
   public void setHiraganaQuaternaryDefault() {
      this.checkNotFrozen();
   }

   public void setUpperCaseFirst(boolean upperfirst) {
      this.checkNotFrozen();
      if (upperfirst != this.isUpperCaseFirst()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirst(upperfirst ? 768 : 0);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setLowerCaseFirst(boolean lowerfirst) {
      this.checkNotFrozen();
      if (lowerfirst != this.isLowerCaseFirst()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirst(lowerfirst ? 512 : 0);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public final void setCaseFirstDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirstDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setAlternateHandlingDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setAlternateHandlingDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setCaseLevelDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(1024, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setDecompositionDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(1, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setFrenchCollationDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(2048, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setStrengthDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setStrengthDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setNumericCollationDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(2, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setFrenchCollation(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.isFrenchCollation()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(2048, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setAlternateHandlingShifted(boolean shifted) {
      this.checkNotFrozen();
      if (shifted != this.isAlternateHandlingShifted()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setAlternateHandlingShifted(shifted);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setCaseLevel(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.isCaseLevel()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(1024, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   @Override
   public void setDecomposition(int decomposition) {
      this.checkNotFrozen();
      boolean flag;
      switch (decomposition) {
         case 16:
            flag = false;
            break;
         case 17:
            flag = true;
            break;
         default:
            throw new IllegalArgumentException("Wrong decomposition mode.");
      }

      if (flag != this.settings.readOnly().getFlag(1)) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(1, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   @Override
   public void setStrength(int newStrength) {
      this.checkNotFrozen();
      if (newStrength != this.getStrength()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setStrength(newStrength);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public RuleBasedCollator setMaxVariable(int group) {
      int value;
      if (group == -1) {
         value = -1;
      } else {
         if (4096 > group || group > 4099) {
            throw new IllegalArgumentException("illegal max variable group " + group);
         }

         value = group - 4096;
      }

      int oldValue = this.settings.readOnly().getMaxVariable();
      if (value == oldValue) {
         return this;
      } else {
         CollationSettings defaultSettings = this.getDefaultSettings();
         if (this.settings.readOnly() == defaultSettings && value < 0) {
            return this;
         } else {
            CollationSettings ownedSettings = this.getOwnedSettings();
            if (group == -1) {
               group = 4096 + defaultSettings.getMaxVariable();
            }

            long varTop = this.data.getLastPrimaryForGroup(group);

            assert varTop != 0L;

            ownedSettings.setMaxVariable(value, defaultSettings.options);
            ownedSettings.variableTop = varTop;
            this.setFastLatinOptions(ownedSettings);
            return this;
         }
      }
   }

   @Override
   public int getMaxVariable() {
      return 4096 + this.settings.readOnly().getMaxVariable();
   }

   @Deprecated
   @Override
   public int setVariableTop(String varTop) {
      this.checkNotFrozen();
      if (varTop != null && varTop.length() != 0) {
         boolean numeric = this.settings.readOnly().isNumeric();
         long ce1;
         long ce2;
         if (this.settings.readOnly().dontCheckFCD()) {
            UTF16CollationIterator ci = new UTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci.nextCE();
            ce2 = ci.nextCE();
         } else {
            FCDUTF16CollationIterator ci = new FCDUTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci.nextCE();
            ce2 = ci.nextCE();
         }

         if (ce1 != 4311744768L && ce2 == 4311744768L) {
            this.internalSetVariableTop(ce1 >>> 32);
            return (int)this.settings.readOnly().variableTop;
         } else {
            throw new IllegalArgumentException("Variable top argument string must map to exactly one collation element");
         }
      } else {
         throw new IllegalArgumentException("Variable top argument string can not be null or zero in length.");
      }
   }

   @Deprecated
   @Override
   public void setVariableTop(int varTop) {
      this.checkNotFrozen();
      this.internalSetVariableTop(varTop & 4294967295L);
   }

   private void internalSetVariableTop(long varTop) {
      if (varTop != this.settings.readOnly().variableTop) {
         int group = this.data.getGroupForPrimary(varTop);
         if (group < 4096 || 4099 < group) {
            throw new IllegalArgumentException("The variable top must be a primary weight in the space/punctuation/symbols/currency symbols range");
         }

         long v = this.data.getLastPrimaryForGroup(group);

         assert v != 0L && v >= varTop;

         if (v != this.settings.readOnly().variableTop) {
            CollationSettings ownedSettings = this.getOwnedSettings();
            ownedSettings.setMaxVariable(group - 4096, this.getDefaultSettings().options);
            ownedSettings.variableTop = v;
            this.setFastLatinOptions(ownedSettings);
         }
      }
   }

   public void setNumericCollation(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.getNumericCollation()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(2, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   @Override
   public void setReorderCodes(int... order) {
      this.checkNotFrozen();
      int length = order != null ? order.length : 0;
      if (length == 1 && order[0] == 103) {
         length = 0;
      }

      if (length == 0 ? this.settings.readOnly().reorderCodes.length != 0 : !Arrays.equals(order, this.settings.readOnly().reorderCodes)) {
         CollationSettings defaultSettings = this.getDefaultSettings();
         if (length == 1 && order[0] == -1) {
            if (this.settings.readOnly() != defaultSettings) {
               CollationSettings ownedSettings = this.getOwnedSettings();
               ownedSettings.copyReorderingFrom(defaultSettings);
               this.setFastLatinOptions(ownedSettings);
            }
         } else {
            CollationSettings ownedSettings = this.getOwnedSettings();
            if (length == 0) {
               ownedSettings.resetReordering();
            } else {
               ownedSettings.setReordering(this.data, (int[])order.clone());
            }

            this.setFastLatinOptions(ownedSettings);
         }
      }
   }

   private void setFastLatinOptions(CollationSettings ownedSettings) {
      ownedSettings.fastLatinOptions = CollationFastLatin.getOptions(this.data, ownedSettings, ownedSettings.fastLatinPrimaries);
   }

   public String getRules() {
      return this.tailoring.getRules();
   }

   public String getRules(boolean fullrules) {
      return !fullrules ? this.tailoring.getRules() : CollationLoader.getRootRules() + this.tailoring.getRules();
   }

   @Override
   public UnicodeSet getTailoredSet() {
      UnicodeSet tailored = new UnicodeSet();
      if (this.data.base != null) {
         new TailoredSet(tailored).forData(this.data);
      }

      return tailored;
   }

   public void getContractionsAndExpansions(UnicodeSet contractions, UnicodeSet expansions, boolean addPrefixes) throws Exception {
      if (contractions != null) {
         contractions.clear();
      }

      if (expansions != null) {
         expansions.clear();
      }

      new ContractionsAndExpansions(contractions, expansions, null, addPrefixes).forData(this.data);
   }

   @Deprecated
   void internalAddContractions(int c, UnicodeSet set) {
      new ContractionsAndExpansions(set, null, null, false).forCodePoint(this.data, c);
   }

   @Override
   public CollationKey getCollationKey(String source) {
      if (source == null) {
         return null;
      } else {
         RuleBasedCollator.CollationBuffer buffer = null;

         CollationKey var3;
         try {
            buffer = this.getCollationBuffer();
            var3 = this.getCollationKey(source, buffer);
         } finally {
            this.releaseCollationBuffer(buffer);
         }

         return var3;
      }
   }

   private CollationKey getCollationKey(String source, RuleBasedCollator.CollationBuffer buffer) {
      buffer.rawCollationKey = this.getRawCollationKey(source, buffer.rawCollationKey, buffer);
      return new CollationKey(source, buffer.rawCollationKey);
   }

   @Override
   public RawCollationKey getRawCollationKey(String source, RawCollationKey key) {
      if (source == null) {
         return null;
      } else {
         RuleBasedCollator.CollationBuffer buffer = null;

         RawCollationKey var4;
         try {
            buffer = this.getCollationBuffer();
            var4 = this.getRawCollationKey(source, key, buffer);
         } finally {
            this.releaseCollationBuffer(buffer);
         }

         return var4;
      }
   }

   private RawCollationKey getRawCollationKey(CharSequence source, RawCollationKey key, RuleBasedCollator.CollationBuffer buffer) {
      if (key == null) {
         key = new RawCollationKey(this.simpleKeyLengthEstimate(source));
      } else if (key.bytes == null) {
         key.bytes = new byte[this.simpleKeyLengthEstimate(source)];
      }

      RuleBasedCollator.CollationKeyByteSink sink = new RuleBasedCollator.CollationKeyByteSink(key);
      this.writeSortKey(source, sink, buffer);
      key.size = sink.NumberOfBytesAppended();
      return key;
   }

   private int simpleKeyLengthEstimate(CharSequence source) {
      return 2 * source.length() + 10;
   }

   private void writeSortKey(CharSequence s, RuleBasedCollator.CollationKeyByteSink sink, RuleBasedCollator.CollationBuffer buffer) {
      boolean numeric = this.settings.readOnly().isNumeric();
      if (this.settings.readOnly().dontCheckFCD()) {
         buffer.leftUTF16CollIter.setText(numeric, s, 0);
         CollationKeys.writeSortKeyUpToQuaternary(
            buffer.leftUTF16CollIter, this.data.compressibleBytes, this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true
         );
      } else {
         buffer.leftFCDUTF16Iter.setText(numeric, s, 0);
         CollationKeys.writeSortKeyUpToQuaternary(
            buffer.leftFCDUTF16Iter, this.data.compressibleBytes, this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true
         );
      }

      if (this.settings.readOnly().getStrength() == 15) {
         this.writeIdenticalLevel(s, sink);
      }

      sink.Append(0);
   }

   private void writeIdenticalLevel(CharSequence s, RuleBasedCollator.CollationKeyByteSink sink) {
      int nfdQCYesLimit = this.data.nfcImpl.decompose(s, 0, s.length(), null);
      sink.Append(1);
      sink.key_.size = sink.NumberOfBytesAppended();
      int prev = 0;
      if (nfdQCYesLimit != 0) {
         prev = BOCSU.writeIdenticalLevelRun(prev, s, 0, nfdQCYesLimit, sink.key_);
      }

      if (nfdQCYesLimit < s.length()) {
         int destLengthEstimate = s.length() - nfdQCYesLimit;
         StringBuilder nfd = new StringBuilder();
         this.data.nfcImpl.decompose(s, nfdQCYesLimit, s.length(), nfd, destLengthEstimate);
         BOCSU.writeIdenticalLevelRun(prev, nfd, 0, nfd.length(), sink.key_);
      }

      sink.setBufferAndAppended(sink.key_.bytes, sink.key_.size);
   }

   @Deprecated
   public long[] internalGetCEs(CharSequence str) {
      RuleBasedCollator.CollationBuffer buffer = null;

      long[] var7;
      try {
         buffer = this.getCollationBuffer();
         boolean numeric = this.settings.readOnly().isNumeric();
         CollationIterator iter;
         if (this.settings.readOnly().dontCheckFCD()) {
            buffer.leftUTF16CollIter.setText(numeric, str, 0);
            iter = buffer.leftUTF16CollIter;
         } else {
            buffer.leftFCDUTF16Iter.setText(numeric, str, 0);
            iter = buffer.leftFCDUTF16Iter;
         }

         int length = iter.fetchCEs() - 1;

         assert length >= 0 && iter.getCE(length) == 4311744768L;

         long[] ces = new long[length];
         System.arraycopy(iter.getCEs(), 0, ces, 0, length);
         var7 = ces;
      } finally {
         this.releaseCollationBuffer(buffer);
      }

      return var7;
   }

   @Override
   public int getStrength() {
      return this.settings.readOnly().getStrength();
   }

   @Override
   public int getDecomposition() {
      return (this.settings.readOnly().options & 1) != 0 ? 17 : 16;
   }

   public boolean isUpperCaseFirst() {
      return this.settings.readOnly().getCaseFirst() == 768;
   }

   public boolean isLowerCaseFirst() {
      return this.settings.readOnly().getCaseFirst() == 512;
   }

   public boolean isAlternateHandlingShifted() {
      return this.settings.readOnly().getAlternateHandling();
   }

   public boolean isCaseLevel() {
      return (this.settings.readOnly().options & 1024) != 0;
   }

   public boolean isFrenchCollation() {
      return (this.settings.readOnly().options & 2048) != 0;
   }

   @Deprecated
   public boolean isHiraganaQuaternary() {
      return false;
   }

   @Override
   public int getVariableTop() {
      return (int)this.settings.readOnly().variableTop;
   }

   public boolean getNumericCollation() {
      return (this.settings.readOnly().options & 2) != 0;
   }

   @Override
   public int[] getReorderCodes() {
      return (int[])this.settings.readOnly().reorderCodes.clone();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         RuleBasedCollator o = (RuleBasedCollator)obj;
         if (!this.settings.readOnly().equals(o.settings.readOnly())) {
            return false;
         } else if (this.data == o.data) {
            return true;
         } else {
            boolean thisIsRoot = this.data.base == null;
            boolean otherIsRoot = o.data.base == null;

            assert !thisIsRoot || !otherIsRoot;

            if (thisIsRoot != otherIsRoot) {
               return false;
            } else {
               String theseRules = this.tailoring.getRules();
               String otherRules = o.tailoring.getRules();
               if ((thisIsRoot || theseRules.length() != 0) && (otherIsRoot || otherRules.length() != 0) && theseRules.equals(otherRules)) {
                  return true;
               } else {
                  UnicodeSet thisTailored = this.getTailoredSet();
                  UnicodeSet otherTailored = o.getTailoredSet();
                  return thisTailored.equals(otherTailored);
               }
            }
         }
      }
   }

   @Override
   public int hashCode() {
      int h = this.settings.readOnly().hashCode();
      if (this.data.base == null) {
         return h;
      } else {
         UnicodeSet set = this.getTailoredSet();
         UnicodeSetIterator iter = new UnicodeSetIterator(set);

         while (iter.next() && iter.codepoint != -1) {
            h ^= this.data.getCE32(iter.codepoint);
         }

         return h;
      }
   }

   @Override
   public int compare(String source, String target) {
      return this.doCompare(source, target);
   }

   private static final int compareNFDIter(Normalizer2Impl nfcImpl, RuleBasedCollator.NFDIterator left, RuleBasedCollator.NFDIterator right) {
      while (true) {
         int leftCp = left.nextCodePoint();
         int rightCp = right.nextCodePoint();
         if (leftCp == rightCp) {
            if (leftCp < 0) {
               return 0;
            }
         } else {
            if (leftCp < 0) {
               leftCp = -2;
            } else if (leftCp == 65534) {
               leftCp = -1;
            } else {
               leftCp = left.nextDecomposedCodePoint(nfcImpl, leftCp);
            }

            if (rightCp < 0) {
               rightCp = -2;
            } else if (rightCp == 65534) {
               rightCp = -1;
            } else {
               rightCp = right.nextDecomposedCodePoint(nfcImpl, rightCp);
            }

            if (leftCp < rightCp) {
               return -1;
            }

            if (leftCp > rightCp) {
               return 1;
            }
         }
      }
   }

   @Deprecated
   @Override
   protected int doCompare(CharSequence left, CharSequence right) {
      if (left == right) {
         return 0;
      } else {
         int equalPrefixLength = 0;

         while (true) {
            if (equalPrefixLength == left.length()) {
               if (equalPrefixLength == right.length()) {
                  return 0;
               }
               break;
            }

            if (equalPrefixLength == right.length() || left.charAt(equalPrefixLength) != right.charAt(equalPrefixLength)) {
               break;
            }

            equalPrefixLength++;
         }

         CollationSettings roSettings = this.settings.readOnly();
         boolean numeric = roSettings.isNumeric();
         if (equalPrefixLength > 0
            && (
               equalPrefixLength != left.length() && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric)
                  || equalPrefixLength != right.length() && this.data.isUnsafeBackward(right.charAt(equalPrefixLength), numeric)
            )) {
            do {
               equalPrefixLength--;
            } while (equalPrefixLength > 0 && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric));
         }

         int fastLatinOptions = roSettings.fastLatinOptions;
         int result;
         if (fastLatinOptions < 0
            || equalPrefixLength != left.length() && left.charAt(equalPrefixLength) > 383
            || equalPrefixLength != right.length() && right.charAt(equalPrefixLength) > 383) {
            result = -2;
         } else {
            result = CollationFastLatin.compareUTF16(this.data.fastLatinTable, roSettings.fastLatinPrimaries, fastLatinOptions, left, right, equalPrefixLength);
         }

         if (result == -2) {
            RuleBasedCollator.CollationBuffer buffer = null;

            try {
               buffer = this.getCollationBuffer();
               if (roSettings.dontCheckFCD()) {
                  buffer.leftUTF16CollIter.setText(numeric, left, equalPrefixLength);
                  buffer.rightUTF16CollIter.setText(numeric, right, equalPrefixLength);
                  result = CollationCompare.compareUpToQuaternary(buffer.leftUTF16CollIter, buffer.rightUTF16CollIter, roSettings);
               } else {
                  buffer.leftFCDUTF16Iter.setText(numeric, left, equalPrefixLength);
                  buffer.rightFCDUTF16Iter.setText(numeric, right, equalPrefixLength);
                  result = CollationCompare.compareUpToQuaternary(buffer.leftFCDUTF16Iter, buffer.rightFCDUTF16Iter, roSettings);
               }
            } finally {
               this.releaseCollationBuffer(buffer);
            }
         }

         if (result == 0 && roSettings.getStrength() >= 15) {
            RuleBasedCollator.CollationBuffer buffer = null;

            int var10;
            try {
               buffer = this.getCollationBuffer();
               Normalizer2Impl nfcImpl = this.data.nfcImpl;
               if (!roSettings.dontCheckFCD()) {
                  buffer.leftFCDUTF16NFDIter.setText(nfcImpl, left, equalPrefixLength);
                  buffer.rightFCDUTF16NFDIter.setText(nfcImpl, right, equalPrefixLength);
                  return compareNFDIter(nfcImpl, buffer.leftFCDUTF16NFDIter, buffer.rightFCDUTF16NFDIter);
               }

               buffer.leftUTF16NFDIter.setText(left, equalPrefixLength);
               buffer.rightUTF16NFDIter.setText(right, equalPrefixLength);
               var10 = compareNFDIter(nfcImpl, buffer.leftUTF16NFDIter, buffer.rightUTF16NFDIter);
            } finally {
               this.releaseCollationBuffer(buffer);
            }

            return var10;
         } else {
            return result;
         }
      }
   }

   RuleBasedCollator(CollationTailoring t, ULocale vl) {
      this.data = t.data;
      this.settings = t.settings.clone();
      this.tailoring = t;
      this.validLocale = vl;
      this.actualLocaleIsSameAsValid = false;
   }

   private void adoptTailoring(CollationTailoring t) {
      assert this.settings == null && this.data == null && this.tailoring == null;

      this.data = t.data;
      this.settings = t.settings.clone();
      this.tailoring = t;
      this.validLocale = t.actualLocale;
      this.actualLocaleIsSameAsValid = false;
   }

   final boolean isUnsafe(int c) {
      return this.data.isUnsafeBackward(c, this.settings.readOnly().isNumeric());
   }

   @Override
   public VersionInfo getVersion() {
      int version = this.tailoring.version;
      int rtVersion = VersionInfo.UCOL_RUNTIME_VERSION.getMajor();
      return VersionInfo.getInstance((version >>> 24) + (rtVersion << 4) + (rtVersion >> 4), version >> 16 & 0xFF, version >> 8 & 0xFF, version & 0xFF);
   }

   @Override
   public VersionInfo getUCAVersion() {
      VersionInfo v = this.getVersion();
      return VersionInfo.getInstance(v.getMinor() >> 3, v.getMinor() & 7, v.getMilli() >> 6, 0);
   }

   private final RuleBasedCollator.CollationBuffer getCollationBuffer() {
      if (this.isFrozen()) {
         this.frozenLock.lock();
      } else if (this.collationBuffer == null) {
         this.collationBuffer = new RuleBasedCollator.CollationBuffer(this.data);
      }

      return this.collationBuffer;
   }

   private final void releaseCollationBuffer(RuleBasedCollator.CollationBuffer buffer) {
      if (this.isFrozen()) {
         this.frozenLock.unlock();
      }
   }

   @Override
   public ULocale getLocale(ULocale.Type type) {
      if (type == ULocale.ACTUAL_LOCALE) {
         return this.actualLocaleIsSameAsValid ? this.validLocale : this.tailoring.actualLocale;
      } else if (type == ULocale.VALID_LOCALE) {
         return this.validLocale;
      } else {
         throw new IllegalArgumentException("unknown ULocale.Type " + type);
      }
   }

   @Override
   void setLocale(ULocale valid, ULocale actual) {
      assert valid == null == (actual == null);

      if (Objects.equals(actual, this.tailoring.actualLocale)) {
         this.actualLocaleIsSameAsValid = false;
      } else {
         assert Objects.equals(actual, valid);

         this.actualLocaleIsSameAsValid = true;
      }

      this.validLocale = valid;
   }

   private static final class CollationBuffer {
      UTF16CollationIterator leftUTF16CollIter;
      UTF16CollationIterator rightUTF16CollIter;
      FCDUTF16CollationIterator leftFCDUTF16Iter;
      FCDUTF16CollationIterator rightFCDUTF16Iter;
      RuleBasedCollator.UTF16NFDIterator leftUTF16NFDIter;
      RuleBasedCollator.UTF16NFDIterator rightUTF16NFDIter;
      RuleBasedCollator.FCDUTF16NFDIterator leftFCDUTF16NFDIter;
      RuleBasedCollator.FCDUTF16NFDIterator rightFCDUTF16NFDIter;
      RawCollationKey rawCollationKey;

      private CollationBuffer(CollationData data) {
         this.leftUTF16CollIter = new UTF16CollationIterator(data);
         this.rightUTF16CollIter = new UTF16CollationIterator(data);
         this.leftFCDUTF16Iter = new FCDUTF16CollationIterator(data);
         this.rightFCDUTF16Iter = new FCDUTF16CollationIterator(data);
         this.leftUTF16NFDIter = new RuleBasedCollator.UTF16NFDIterator();
         this.rightUTF16NFDIter = new RuleBasedCollator.UTF16NFDIterator();
         this.leftFCDUTF16NFDIter = new RuleBasedCollator.FCDUTF16NFDIterator();
         this.rightFCDUTF16NFDIter = new RuleBasedCollator.FCDUTF16NFDIterator();
      }
   }

   private static final class CollationKeyByteSink extends CollationKeys.SortKeyByteSink {
      private RawCollationKey key_;

      CollationKeyByteSink(RawCollationKey key) {
         super(key.bytes);
         this.key_ = key;
      }

      @Override
      protected void AppendBeyondCapacity(byte[] bytes, int start, int n, int length) {
         if (this.Resize(n, length)) {
            System.arraycopy(bytes, start, this.buffer_, length, n);
         }
      }

      @Override
      protected boolean Resize(int appendCapacity, int length) {
         int newCapacity = 2 * this.buffer_.length;
         int altCapacity = length + 2 * appendCapacity;
         if (newCapacity < altCapacity) {
            newCapacity = altCapacity;
         }

         if (newCapacity < 200) {
            newCapacity = 200;
         }

         byte[] newBytes = new byte[newCapacity];
         System.arraycopy(this.buffer_, 0, newBytes, 0, length);
         this.buffer_ = this.key_.bytes = newBytes;
         return true;
      }
   }

   private static final class FCDUTF16NFDIterator extends RuleBasedCollator.UTF16NFDIterator {
      private StringBuilder str;

      FCDUTF16NFDIterator() {
      }

      void setText(Normalizer2Impl nfcImpl, CharSequence seq, int start) {
         this.reset();
         int spanLimit = nfcImpl.makeFCD(seq, start, seq.length(), null);
         if (spanLimit == seq.length()) {
            this.s = seq;
            this.pos = start;
         } else {
            if (this.str == null) {
               this.str = new StringBuilder();
            } else {
               this.str.setLength(0);
            }

            this.str.append(seq, start, spanLimit);
            Normalizer2Impl.ReorderingBuffer buffer = new Normalizer2Impl.ReorderingBuffer(nfcImpl, this.str, seq.length() - start);
            nfcImpl.makeFCD(seq, spanLimit, seq.length(), buffer);
            this.s = this.str;
            this.pos = 0;
         }
      }
   }

   private abstract static class NFDIterator {
      private String decomp;
      private int index;

      NFDIterator() {
      }

      final void reset() {
         this.index = -1;
      }

      final int nextCodePoint() {
         if (this.index >= 0) {
            if (this.index != this.decomp.length()) {
               int c = Character.codePointAt(this.decomp, this.index);
               this.index = this.index + Character.charCount(c);
               return c;
            }

            this.index = -1;
         }

         return this.nextRawCodePoint();
      }

      final int nextDecomposedCodePoint(Normalizer2Impl nfcImpl, int c) {
         if (this.index >= 0) {
            return c;
         } else {
            this.decomp = nfcImpl.getDecomposition(c);
            if (this.decomp == null) {
               return c;
            } else {
               c = Character.codePointAt(this.decomp, 0);
               this.index = Character.charCount(c);
               return c;
            }
         }
      }

      protected abstract int nextRawCodePoint();
   }

   private static class UTF16NFDIterator extends RuleBasedCollator.NFDIterator {
      protected CharSequence s;
      protected int pos;

      UTF16NFDIterator() {
      }

      void setText(CharSequence seq, int start) {
         this.reset();
         this.s = seq;
         this.pos = start;
      }

      @Override
      protected int nextRawCodePoint() {
         if (this.pos == this.s.length()) {
            return -1;
         } else {
            int c = Character.codePointAt(this.s, this.pos);
            this.pos = this.pos + Character.charCount(c);
            return c;
         }
      }
   }
}
