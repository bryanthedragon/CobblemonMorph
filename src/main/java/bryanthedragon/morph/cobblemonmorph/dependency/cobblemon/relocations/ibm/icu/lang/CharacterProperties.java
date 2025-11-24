
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.lang;

import com.cobblemon.mod.relocations.ibm.icu.impl.CharacterPropertiesImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.EmojiProps;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointMap;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.MutableCodePointTrie;

public final class CharacterProperties {
    private static final UnicodeSet[] sets = new UnicodeSet[72];
    private static final CodePointMap[] maps = new CodePointMap[25];

    private CharacterProperties() {
    }

    private static UnicodeSet makeSet(int property) {
        UnicodeSet set2 = new UnicodeSet();
        if (65 <= property && property <= 71) {
            EmojiProps.INSTANCE.addStrings(property, set2);
            if (property != 65 && property != 71) {
                return set2.freeze();
            }
        }
        UnicodeSet inclusions = CharacterPropertiesImpl.getInclusionsForProperty(property);
        int numRanges = inclusions.getRangeCount();
        int startHasProperty = -1;
        for (int i = 0; i < numRanges; ++i) {
            int rangeEnd = inclusions.getRangeEnd(i);
            for (int c = inclusions.getRangeStart(i); c <= rangeEnd; ++c) {
                if (UCharacter.hasBinaryProperty(c, property)) {
                    if (startHasProperty >= 0) continue;
                    startHasProperty = c;
                    continue;
                }
                if (startHasProperty < 0) continue;
                set2.add(startHasProperty, c - 1);
                startHasProperty = -1;
            }
        }
        if (startHasProperty >= 0) {
            set2.add(startHasProperty, 0x10FFFF);
        }
        return set2.freeze();
    }

    private static CodePointMap makeMap(int property) {
        int nullValue = property == 4106 ? 103 : 0;
        MutableCodePointTrie mutableTrie = new MutableCodePointTrie(nullValue, nullValue);
        UnicodeSet inclusions = CharacterPropertiesImpl.getInclusionsForProperty(property);
        int numRanges = inclusions.getRangeCount();
        int start2 = 0;
        int value2 = nullValue;
        for (int i = 0; i < numRanges; ++i) {
            int rangeEnd = inclusions.getRangeEnd(i);
            for (int c = inclusions.getRangeStart(i); c <= rangeEnd; ++c) {
                int nextValue = UCharacter.getIntPropertyValue(c, property);
                if (value2 == nextValue) continue;
                if (value2 != nullValue) {
                    mutableTrie.setRange(start2, c - 1, value2);
                }
                start2 = c;
                value2 = nextValue;
            }
        }
        if (value2 != 0) {
            mutableTrie.setRange(start2, 0x10FFFF, value2);
        }
        CodePointTrie.Type type = property == 4096 || property == 4101 ? CodePointTrie.Type.FAST : CodePointTrie.Type.SMALL;
        int max2 = UCharacter.getIntPropertyMaxValue(property);
        CodePointTrie.ValueWidth valueWidth = max2 <= 255 ? CodePointTrie.ValueWidth.BITS_8 : (max2 <= 65535 ? CodePointTrie.ValueWidth.BITS_16 : CodePointTrie.ValueWidth.BITS_32);
        return mutableTrie.buildImmutable(type, valueWidth);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final UnicodeSet getBinaryPropertySet(int property) {
        if (property < 0 || 72 <= property) {
            throw new IllegalArgumentException("" + property + " is not a constant for a UProperty binary property");
        }
        UnicodeSet[] unicodeSetArray = sets;
        synchronized (sets) {
            UnicodeSet set2 = sets[property];
            if (set2 == null) {
                CharacterProperties.sets[property] = set2 = CharacterProperties.makeSet(property);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return set2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final CodePointMap getIntPropertyMap(int property) {
        if (property < 4096 || 4121 <= property) {
            throw new IllegalArgumentException("" + property + " is not a constant for a UProperty int property");
        }
        CodePointMap[] codePointMapArray = maps;
        synchronized (maps) {
            CodePointMap map = maps[property - 4096];
            if (map == null) {
                CharacterProperties.maps[property - 4096] = map = CharacterProperties.makeMap(property);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return map;
        }
    }
}

