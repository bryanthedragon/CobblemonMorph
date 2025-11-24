/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0003\u001a\u0019\u0010\u0003\u001a\u00020\u0002*\u00020\u00002\u0006\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a5\u0010\t\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u00000\b0\u0007*\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0000\u00a2\u0006\u0004\b\t\u0010\n\u001a\u0011\u0010\f\u001a\u00020\u000b*\u00020\u0000\u00a2\u0006\u0004\b\f\u0010\r\u001a\u0011\u0010\u000f\u001a\u00020\u000e*\u00020\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\"\u0014\u0010\u0012\u001a\u00020\u00118\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"", "otherVersion", "", "isLaterVersion", "(Ljava/lang/String;Ljava/lang/String;)Z", "delimiter", "assigner", "", "Lkotlin/Pair;", "splitMap", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/util/List;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "toPokemon", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "toProperties", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "QUOTE", "C", "common"})
public final class StringExtensionsKt {
    public static final char QUOTE = '\"';

    @NotNull
    public static final List<Pair<String, String>> splitMap(@NotNull String $this$splitMap, @NotNull String delimiter, @NotNull String assigner) {
        Intrinsics.checkNotNullParameter((Object)$this$splitMap, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)delimiter, (String)"delimiter");
        Intrinsics.checkNotNullParameter((Object)assigner, (String)"assigner");
        List result = new ArrayList();
        String[] stringArray = new String[]{delimiter};
        List split = StringsKt.split$default((CharSequence)$this$splitMap, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        String joiner = null;
        for (String argument : split) {
            String key;
            String value2;
            if (joiner != null && StringsKt.endsWith$default((CharSequence)argument, (char)'\"', (boolean)false, (int)2, null)) {
                String string = argument.substring(0, argument.length() - 1);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                joiner = (String)joiner + delimiter + string;
                String[] stringArray2 = new String[]{assigner};
                List components = StringsKt.split$default((CharSequence)joiner, (String[])stringArray2, (boolean)false, (int)0, (int)6, null);
                Intrinsics.checkNotNullExpressionValue((Object)((String)components.get(0)).toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                value2 = StringsKt.contains$default((CharSequence)joiner, (CharSequence)assigner, (boolean)false, (int)2, null) ? (String)components.get(1) : null;
                joiner = null;
                result.add(TuplesKt.to((Object)key, (Object)value2));
                continue;
            }
            if (joiner == null) {
                if (StringsKt.contains$default((CharSequence)argument, (CharSequence)assigner, (boolean)false, (int)2, null)) {
                    int equalsIndex = StringsKt.indexOf$default((CharSequence)argument, (String)assigner, (int)0, (boolean)false, (int)6, null);
                    String string = argument.substring(0, equalsIndex);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    Intrinsics.checkNotNullExpressionValue((Object)string.toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    Intrinsics.checkNotNullExpressionValue((Object)argument.substring(equalsIndex + 1), (String)"this as java.lang.String).substring(startIndex)");
                    if (StringsKt.startsWith$default((CharSequence)value2, (char)'\"', (boolean)false, (int)2, null)) {
                        if (StringsKt.endsWith$default((CharSequence)value2, (char)'\"', (boolean)false, (int)2, null)) {
                            String string2 = value2.substring(1, value2.length() - 1);
                            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                            result.add(TuplesKt.to((Object)key, (Object)string2));
                            continue;
                        }
                        String string3 = value2.substring(1);
                        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).substring(startIndex)");
                        joiner = key + assigner + string3;
                        continue;
                    }
                    result.add(TuplesKt.to((Object)key, (Object)value2));
                    continue;
                }
                if (StringsKt.startsWith$default((CharSequence)argument, (char)'\"', (boolean)false, (int)2, null) && StringsKt.endsWith$default((CharSequence)argument, (char)'\"', (boolean)false, (int)2, null)) {
                    String string = argument.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    String string4 = string.substring(1, argument.length() - 1);
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    result.add(TuplesKt.to((Object)string4, null));
                    continue;
                }
                if (StringsKt.contains$default((CharSequence)argument, (char)'\"', (boolean)false, (int)2, null)) continue;
                String string = argument.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                result.add(TuplesKt.to((Object)string, null));
                continue;
            }
            joiner = joiner + delimiter + argument;
        }
        return result;
    }

    public static final boolean isLaterVersion(@NotNull String $this$isLaterVersion, @NotNull String otherVersion) {
        Intrinsics.checkNotNullParameter((Object)$this$isLaterVersion, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)otherVersion, (String)"otherVersion");
        if ($this$isLaterVersion == otherVersion) {
            return false;
        }
        String[] stringArray = new String[]{"."};
        List splits1 = StringsKt.split$default((CharSequence)$this$isLaterVersion, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        String[] stringArray2 = new String[]{"."};
        List splits2 = StringsKt.split$default((CharSequence)otherVersion, (String[])stringArray2, (boolean)false, (int)0, (int)6, null);
        String smaller = splits1.size() > splits2.size() ? $this$isLaterVersion : otherVersion;
        String[] stringArray3 = new String[]{"."};
        int n = StringsKt.split$default((CharSequence)smaller, (String[])stringArray3, (boolean)false, (int)0, (int)6, null).size();
        for (int i = 0; i < n; ++i) {
            try {
                int v1 = Integer.parseInt((String)splits1.get(i));
                int v2 = Integer.parseInt((String)splits2.get(i));
                if (v1 > v2) {
                    return true;
                }
                if (v2 <= v1) continue;
                return false;
            }
            catch (NumberFormatException e) {
                Cobblemon.INSTANCE.getLOGGER().error("Tried comparing versions " + $this$isLaterVersion + " and " + otherVersion + " but at least one of them isn't formatted like a version.");
                return false;
            }
        }
        return !Intrinsics.areEqual((Object)smaller, (Object)$this$isLaterVersion);
    }

    @NotNull
    public static final PokemonProperties toProperties(@NotNull String $this$toProperties) {
        Intrinsics.checkNotNullParameter((Object)$this$toProperties, (String)"<this>");
        return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, $this$toProperties, null, null, 6, null);
    }

    @NotNull
    public static final Pokemon toPokemon(@NotNull String $this$toPokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$toPokemon, (String)"<this>");
        return StringExtensionsKt.toProperties($this$toPokemon).create();
    }
}

