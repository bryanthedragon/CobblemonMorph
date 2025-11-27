/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.MatchResult
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BattleMessage {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String id;
    @NotNull
    private String rawMessage;
    @NotNull
    private final ArrayList<String> args;
    @NotNull
    private final HashMap<String, String> optionalArguments;
    @NotNull
    private final Regex optionalArgumentMatcher;
    @NotNull
    private static final String SEPARATOR = "|";
    @NotNull
    private static final String OPTIONAL_ARG_START = "[";
    @NotNull
    private static final String OPTIONAL_ARG_END = "]";
    @NotNull
    private static final Regex PNX_MATCHER = new Regex("p\\d[a-c]");
    @NotNull
    private static final Regex PN_MATCHER = new Regex("p\\d");


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BattleMessage(@NotNull String rawMessage) {
        Intrinsics.checkNotNullParameter((Object)rawMessage, (String)"rawMessage");
        this.id = "";
        this.rawMessage = rawMessage;
        this.args = new ArrayList();
        this.optionalArguments = new HashMap();
        this.optionalArgumentMatcher = new Regex("^\\[([^]]+)]");
        this.parse(rawMessage);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getRawMessage() {
        return this.rawMessage;
    }

    @Nullable
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final String argumentAt(int index) {
        return (String)CollectionsKt.getOrNull((List)this.args, (int)index);
    }

    @Nullable
    public final String optionalArgument(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return this.optionalArguments.get(string);
    }

    public final boolean hasOptionalArgument(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return this.optionalArgument(name) != null;
    }

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final BattleMessage parse(@NotNull String rawMessage) {
        Intrinsics.checkNotNullParameter((Object)rawMessage, (String)"rawMessage");
        String message = ((Object)StringsKt.trim((CharSequence)rawMessage)).toString();
        this.id = "";
        this.args.clear();
        this.optionalArguments.clear();
        this.rawMessage = message;
        if (!message.startsWith(SEPARATOR) || Intrinsics.areEqual((Object)message, (Object)SEPARATOR)) {
            return this;
        }
        message = this.push(message);
        this.id = StringsKt.substringBefore$default((String)message, (String)SEPARATOR, null, (int)2, null);
        message = this.push(message);
        while (!StringsKt.isBlank((CharSequence)message)) {
            String currentData = StringsKt.substringBefore$default((String)message, (String)SEPARATOR, null, (int)2, null);
            MatchResult optionalArgumentID = Regex.find$default((Regex)this.optionalArgumentMatcher, (CharSequence)currentData, (int)0, (int)2, null);
            if (optionalArgumentID != null) {
                String id;
                Intrinsics.checkNotNullExpressionValue((Object)StringsKt.removeSuffix((String)StringsKt.removePrefix((String)optionalArgumentID.getValue(), (CharSequence)OPTIONAL_ARG_START), (CharSequence)OPTIONAL_ARG_END).toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                String value2 = ((Object)StringsKt.trim((CharSequence)StringsKt.substringAfter$default((String)currentData, (String)optionalArgumentID.getValue(), null, (int)2, null))).toString();
                ((Map)this.optionalArguments).put(id, value2);
            } else {
                this.args.add(currentData);
            }
            message = this.push(message);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final BattlePokemon pokemonByUuid(int index, @NotNull PokemonBattle battle2) {
        Object v3;
        block2: {
            void $this$flatMapTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            String string = this.argumentAt(index);
            Object object = string;
            if (string == null) return null;
            String it = (String) object;
            boolean bl = false;
            UUID uUID = UUID.fromString(it);
            object = uUID;
            if (uUID == null) return null;
            Object uuid2 = object;
            boolean bl2 = false;
            Iterable<BattleActor> $this$flatMap$iv = battle2.getActors();
            boolean $i$f$flatMap = false;
            Iterable<BattleActor> iterable = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                BattleActor it2 = (BattleActor)element$iv$iv;
                boolean bl3 = false;
                Iterable list$iv$iv = it2.getPokemonList();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            Iterable iterable2 = (List)destination$iv$iv;
            for (Object e : iterable2) {
                BattlePokemon it3 = (BattlePokemon)e;
                boolean bl4 = false;
                if (!Intrinsics.areEqual((Object)it3.getUuid(), (Object)uuid2)) continue;
                v3 = e;
                break block2;
            }
            v3 = null;
        }
        BattlePokemon battlePokemon = (BattlePokemon) v3;
        return battlePokemon;
    }

    @Nullable
    public final Pair<BattleActor, ActiveBattlePokemon> actorAndActivePokemon(int index, @NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.pnxAndUuidString(index);
        if (pair == null) {
            return null;
        }
        String pnx = (String)pair.component1();
        return this.actorAndActivePokemon(pnx, battle2);
    }

    @Nullable
    public final BattlePokemon battlePokemon(int index, @NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.pnxAndUuidString(index);
        if (pair == null) {
            return null;
        }
        Pair<String, String> pair2 = pair;
        String actorID = (String)pair2.component1();
        String pokemonID = (String)pair2.component2();
        return this.battlePokemon(actorID, pokemonID, battle2);
    }

    @Nullable
    @SuppressWarnings({ "unused", "unchecked" })
    public final BattlePokemon battlePokemonFromOptional(@NotNull PokemonBattle battle2, @NotNull String optionalArg) {
        Object object;
        Object object2;
        block6: {
            block5: {
                String optional;
                String string;
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)optionalArg, (String)"optionalArg");
                String string2 = this.optionalArguments.get(optionalArg);
                if (string2 == null) {
                    return null;
                }
                String it = string = (optional = string2);
                boolean bl = false;
                object2 = it.length() >= 2 ? string : null;
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(object = new String[]{":"}), (boolean)false, (int)0, (int)6, null)) == null) break block5;
                Object it2 = bl = object2 != null;
                boolean bl2 = false;
                object2 = ((ArrayList<String>) it2).size() == 2 ? bl : null;
                if (object2 != null) break block6;
            }
            return null;
        }
        Object pokemonID = object2;
        object = ((ArrayList<String>) pokemonID).get(0);
        String it = (String)object;
        boolean bl = false;
        String string = (String)(PNX_MATCHER.matches((CharSequence)it) || PN_MATCHER.matches((CharSequence)it) ? object : null);
        if (string == null) {
            return null;
        }
        String pnx = string;
        String uuid2 = ((Object)StringsKt.trim((CharSequence)((String)((ArrayList<String>) pokemonID).get(1)))).toString();
        return this.battlePokemon(pnx, uuid2, battle2);
    }

    public static /* synthetic */ BattlePokemon battlePokemonFromOptional$default(BattleMessage battleMessage, PokemonBattle pokemonBattle, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = "of";
        }
        return battleMessage.battlePokemonFromOptional(pokemonBattle, string);
    }
    
    @Nullable
    @SuppressWarnings("unchecked")
    public final Pair<Object, Object> pnxAndUuidObject(int index) {
        Object it;
        Object object;
        Object object2;
        block5: {
            block4: {
                object2 = this.argumentAt(index);
                if (object2 == null) break block4;
                it = object = object2;
                boolean bl = false;
                object2 = ((String) it).length() >= 2 ? object : null;
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(it = new String[]{":"}), (boolean)false, (int)0, (int)6, null)) == null) break block4;
                Object it2 = bl = object2 != null;
                boolean bl2 = false;
                object2 = ((ArrayList<String>) it2).size() == 2 ? bl : null;
                if (object2 != null) break block5;
            }
            return null;
        }
        Object argument = object2;
        object = ((ArrayList<String>) argument).get(0);
        it = (String)object;
        boolean bl = false;
        String string = (String)(PNX_MATCHER.matches((CharSequence)it) || PN_MATCHER.matches((CharSequence)it) ? object : null);
        if (string == null) {
            return null;
        }
        String pnx = string;
        String uuid2 = ((Object)StringsKt.trim((CharSequence)((String)((ArrayList<String>) argument).get(1)))).toString();
        return TuplesKt.to((Object)pnx, (Object)uuid2);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public final Pair<String, String> pnxAndUuidString(int index) {
        Object it;
        Object object;
        Object object2;
        block5: {
            block4: {
                object2 = this.argumentAt(index);
                if (object2 == null) break block4;
                it = object = object2;
                boolean bl = false;
                object2 = ((String) it).length() >= 2 ? object : null;
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(it = new String[]{":"}), (boolean)false, (int)0, (int)6, null)) == null) break block4;
                Object it2 = bl = object2 != null;
                boolean bl2 = false;
                object2 = ((ArrayList<String>) it2).size() == 2 ? bl : null;
                if (object2 != null) break block5;
            }
            return null;
        }
        Object argument = object2;
        object = ((ArrayList<String>) argument).get(0);
        it = (String)object;
        boolean bl = false;
        String string = (String)(PNX_MATCHER.matches((CharSequence)it) || PN_MATCHER.matches((CharSequence)it) ? object : null);
        if (string == null) {
            return null;
        }
        String pnx = string;
        String uuid2 = ((Object)StringsKt.trim((CharSequence)((String)((ArrayList<String>) argument).get(1)))).toString();
        return TuplesKt.to(pnx, uuid2);
    }


    @Nullable
    public final Effect effectAt(int index) {
        String string = this.argumentAt(index);
        if (string == null) {
            return null;
        }
        String data = string;
        return Effect.Companion.parse(data);
    }

    @Nullable
    public final Effect effect(@NotNull String argumentName) {
        Intrinsics.checkNotNullParameter((Object)argumentName, (String)"argumentName");
        String string = this.optionalArgument(argumentName);
        if (string == null) {
            return null;
        }
        String data = string;
        return Effect.Companion.parse(data);
    }

    public static /* synthetic */ Effect effect$default(BattleMessage battleMessage, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "from";
        }
        return battleMessage.effect(string);
    }

    @Nullable
    @SuppressWarnings("unused")
    public final MoveTemplate moveAt(int index) {
        String string;
        block3: {
            block2: {
                String string2;
                CharSequence charSequence;
                Regex regex;
                string = this.argumentAt(index);
                if (string == null) break block2;
                String string3 = string.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                string = string3;
                if (string3 != null && (string = (regex = new Regex("[^a-z0-9]")).replace(charSequence = (CharSequence)string, string2 = "")) != null) break block3;
            }
            return null;
        }
        String argument = string;
        return Moves.INSTANCE.getByName(argument);
    }

    @Nullable
    @SuppressWarnings("unused")
    public final Pair<BattleActor, ActiveBattlePokemon> actorAndActivePokemonFromOptional(@NotNull PokemonBattle battle2, @NotNull String argumentName) {
        String string;
        block3: {
            block2: {
                String string2;
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)argumentName, (String)"argumentName");
                string = this.optionalArgument(argumentName);
                if (string == null) break block2;
                String it = string2 = string;
                boolean bl = false;
                string = it.length() >= 3 ? string2 : null;
                if (string == null) break block2;
                String string3 = string.substring(0, 3);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                string = string3;
                if (string3 != null) break block3;
            }
            return null;
        }
        String pnx = string;
        return this.actorAndActivePokemon(pnx, battle2);
    }

    @SuppressWarnings("rawtypes")
    public static /* synthetic */ Pair actorAndActivePokemonFromOptional$default(BattleMessage battleMessage, PokemonBattle pokemonBattle, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = "of";
        }
        return battleMessage.actorAndActivePokemonFromOptional(pokemonBattle, string);
    }

    private final String push(String message) {
        return StringsKt.substringAfter((String)message, (String)SEPARATOR, (String)"");
    }

    private final Pair<BattleActor, ActiveBattlePokemon> actorAndActivePokemon(String pnx, PokemonBattle battle2) {
        Pair<BattleActor, ActiveBattlePokemon> pair;
        try {
            pair = battle2.getActorAndActiveSlotFromPNX(pnx);
        }
        catch (Exception exception) {
            pair = null;
        }
        return pair;
    }

    private final BattlePokemon battlePokemon(String pnx, String pokemonID, PokemonBattle battle2) {
        BattlePokemon battlePokemon;
        try {
            battlePokemon = battle2.getBattlePokemon(pnx, pokemonID);
        }
        catch (Exception exception) {
            battlePokemon = null;
        }
        return battlePokemon;
    }

    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Regex getPNX_MATCHER() {
            return PNX_MATCHER;
        }

        @NotNull
        public final Regex getPN_MATCHER() {
            return PN_MATCHER;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

