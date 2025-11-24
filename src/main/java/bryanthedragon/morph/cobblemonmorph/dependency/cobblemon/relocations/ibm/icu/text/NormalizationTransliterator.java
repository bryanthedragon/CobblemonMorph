
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Norm2AllModes;
import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;
import com.cobblemon.mod.relocations.ibm.icu.text.Normalizer2;
import com.cobblemon.mod.relocations.ibm.icu.text.Replaceable;
import com.cobblemon.mod.relocations.ibm.icu.text.SourceTargetUtility;
import com.cobblemon.mod.relocations.ibm.icu.text.Transform;
import com.cobblemon.mod.relocations.ibm.icu.text.Transliterator;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import java.util.HashMap;
import java.util.Map;

final class NormalizationTransliterator
extends Transliterator {
    private final Normalizer2 norm2;
    static final Map<Normalizer2, SourceTargetUtility> SOURCE_CACHE = new HashMap<Normalizer2, SourceTargetUtility>();

    static void register() {
        Transliterator.registerFactory("Any-NFC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("NFC", Normalizer2.getNFCInstance());
            }
        });
        Transliterator.registerFactory("Any-NFD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("NFD", Normalizer2.getNFDInstance());
            }
        });
        Transliterator.registerFactory("Any-NFKC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("NFKC", Normalizer2.getNFKCInstance());
            }
        });
        Transliterator.registerFactory("Any-NFKD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("NFKD", Normalizer2.getNFKDInstance());
            }
        });
        Transliterator.registerFactory("Any-FCD", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("FCD", Norm2AllModes.getFCDNormalizer2());
            }
        });
        Transliterator.registerFactory("Any-FCC", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String ID) {
                return new NormalizationTransliterator("FCC", Norm2AllModes.getNFCInstance().fcc);
            }
        });
        Transliterator.registerSpecialInverse("NFC", "NFD", true);
        Transliterator.registerSpecialInverse("NFKC", "NFKD", true);
        Transliterator.registerSpecialInverse("FCC", "NFD", false);
        Transliterator.registerSpecialInverse("FCD", "FCD", false);
    }

    private NormalizationTransliterator(String id, Normalizer2 n2) {
        super(id, null);
        this.norm2 = n2;
    }

    @Override
    protected void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean isIncremental) {
        int start2 = offsets.start;
        int limit = offsets.limit;
        if (start2 >= limit) {
            return;
        }
        StringBuilder segment = new StringBuilder();
        StringBuilder normalized = new StringBuilder();
        int c = text.char32At(start2);
        do {
            int prev = start2;
            segment.setLength(0);
            do {
                segment.appendCodePoint(c);
            } while ((start2 += Character.charCount(c)) < limit && !this.norm2.hasBoundaryBefore(c = text.char32At(start2)));
            if (start2 == limit && isIncremental && !this.norm2.hasBoundaryAfter(c)) {
                start2 = prev;
                break;
            }
            this.norm2.normalize((CharSequence)segment, normalized);
            if (Normalizer2Impl.UTF16Plus.equal(segment, normalized)) continue;
            text.replace(prev, start2, normalized.toString());
            int delta = normalized.length() - (start2 - prev);
            start2 += delta;
            limit += delta;
        } while (start2 < limit);
        offsets.start = start2;
        offsets.contextLimit += limit - offsets.limit;
        offsets.limit = limit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        SourceTargetUtility cache;
        Map<Normalizer2, SourceTargetUtility> map = SOURCE_CACHE;
        synchronized (map) {
            cache = SOURCE_CACHE.get(this.norm2);
            if (cache == null) {
                cache = new SourceTargetUtility(new NormalizingTransform(this.norm2), this.norm2);
                SOURCE_CACHE.put(this.norm2, cache);
            }
        }
        cache.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
    }

    static class NormalizingTransform
    implements Transform<String, String> {
        final Normalizer2 norm2;

        public NormalizingTransform(Normalizer2 norm2) {
            this.norm2 = norm2;
        }

        @Override
        public String transform(String source) {
            return this.norm2.normalize(source);
        }
    }
}

