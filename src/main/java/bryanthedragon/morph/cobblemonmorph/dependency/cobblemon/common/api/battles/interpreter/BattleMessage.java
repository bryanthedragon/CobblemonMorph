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
import kotlin.Metadata;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 D2\u00020\u0001:\u0001DB\u000f\u0012\u0006\u0010)\u001a\u00020\u000b\u00a2\u0006\u0004\bB\u0010CJ+\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\t\u0010\nJ-\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\t\u0010\rJ-\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0014\u0010\u0015J)\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0017J!\u0010\u0019\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0018\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0015\u0010\"\u001a\u00020!2\u0006\u0010 \u001a\u00020\u000b\u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010%\u001a\u0004\u0018\u00010$2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b%\u0010&J\u0017\u0010'\u001a\u0004\u0018\u00010\u000b2\u0006\u0010 \u001a\u00020\u000b\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010*\u001a\u00020\u00002\u0006\u0010)\u001a\u00020\u000b\u00a2\u0006\u0004\b*\u0010+J#\u0010,\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b,\u0010-J\u001f\u0010.\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b.\u0010\u0015J\u0017\u00100\u001a\u00020\u000b2\u0006\u0010/\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b0\u0010(R$\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u000b01j\b\u0012\u0004\u0012\u00020\u000b`28\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b3\u00104R$\u00106\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u000b8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109R\u0014\u0010;\u001a\u00020:8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010<R0\u0010?\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0=j\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b`>8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010@R$\u0010)\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u000b8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b)\u00107\u001a\u0004\bA\u00109\u00a8\u0006E"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "", "", "index", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "actorAndActivePokemon", "(ILcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lkotlin/Pair;", "", "pnx", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lkotlin/Pair;", "argumentName", "actorAndActivePokemonFromOptional", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/lang/String;)Lkotlin/Pair;", "argumentAt", "(I)Ljava/lang/String;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "(ILcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemonID", "(Ljava/lang/String;Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "optionalArg", "battlePokemonFromOptional", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "effect", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "effectAt", "(I)Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "name", "", "hasOptionalArgument", "(Ljava/lang/String;)Z", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "moveAt", "(I)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "optionalArgument", "(Ljava/lang/String;)Ljava/lang/String;", "rawMessage", "parse", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "pnxAndUuid", "(I)Lkotlin/Pair;", "pokemonByUuid", "message", "push", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "args", "Ljava/util/ArrayList;", "<set-?>", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "Lkotlin/text/Regex;", "optionalArgumentMatcher", "Lkotlin/text/Regex;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "optionalArguments", "Ljava/util/HashMap;", "getRawMessage", "<init>", "(Ljava/lang/String;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBattleMessage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMessage.kt\ncom/cobblemon/mod/common/api/battles/interpreter/BattleMessage\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,262:1\n1#2:263\n1360#3:264\n1446#3,5:265\n*S KotlinDebug\n*F\n+ 1 BattleMessage.kt\ncom/cobblemon/mod/common/api/battles/interpreter/BattleMessage\n*L\n123#1:264\n123#1:265,5\n*E\n"})
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
    public final BattleMessage parse(@NotNull String rawMessage) {
        Intrinsics.checkNotNullParameter((Object)rawMessage, (String)"rawMessage");
        String message = ((Object)StringsKt.trim((CharSequence)rawMessage)).toString();
        this.id = "";
        this.args.clear();
        this.optionalArguments.clear();
        this.rawMessage = message;
        if (!StringsKt.startsWith$default((String)message, (String)SEPARATOR, (boolean)false, (int)2, null) || Intrinsics.areEqual((Object)message, (Object)SEPARATOR)) {
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
    public final BattlePokemon pokemonByUuid(int index, @NotNull PokemonBattle battle2) {
        Object v3;
        block2: {
            void $this$flatMapTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            String string = this.argumentAt(index);
            Object object = string;
            if (string == null) return null;
            String it = object;
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
        BattlePokemon battlePokemon = v3;
        return battlePokemon;
    }

    @Nullable
    public final Pair<BattleActor, ActiveBattlePokemon> actorAndActivePokemon(int index, @NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.pnxAndUuid(index);
        if (pair == null) {
            return null;
        }
        String pnx = (String)pair.component1();
        return this.actorAndActivePokemon(pnx, battle2);
    }

    @Nullable
    public final BattlePokemon battlePokemon(int index, @NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.pnxAndUuid(index);
        if (pair == null) {
            return null;
        }
        Pair<String, String> pair2 = pair;
        String actorID = (String)pair2.component1();
        String pokemonID = (String)pair2.component2();
        return this.battlePokemon(actorID, pokemonID, battle2);
    }

    @Nullable
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
                Object it2 = bl = object2;
                boolean bl2 = false;
                object2 = it2.size() == 2 ? bl : null;
                if (object2 != null) break block6;
            }
            return null;
        }
        Object pokemonID = object2;
        object = pokemonID.get(0);
        String it = (String)object;
        boolean bl = false;
        String string = (String)(PNX_MATCHER.matches((CharSequence)it) || PN_MATCHER.matches((CharSequence)it) ? object : null);
        if (string == null) {
            return null;
        }
        String pnx = string;
        String uuid2 = ((Object)StringsKt.trim((CharSequence)((String)pokemonID.get(1)))).toString();
        return this.battlePokemon(pnx, uuid2, battle2);
    }

    public static /* synthetic */ BattlePokemon battlePokemonFromOptional$default(BattleMessage battleMessage, PokemonBattle pokemonBattle, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = "of";
        }
        return battleMessage.battlePokemonFromOptional(pokemonBattle, string);
    }

    @Nullable
    public final Pair<String, String> pnxAndUuid(int index) {
        Object it;
        Object object;
        Object object2;
        block5: {
            block4: {
                object2 = this.argumentAt(index);
                if (object2 == null) break block4;
                it = object = object2;
                boolean bl = false;
                object2 = it.length() >= 2 ? object : null;
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(it = new String[]{":"}), (boolean)false, (int)0, (int)6, null)) == null) break block4;
                Object it2 = bl = object2;
                boolean bl2 = false;
                object2 = it2.size() == 2 ? bl : null;
                if (object2 != null) break block5;
            }
            return null;
        }
        Object argument = object2;
        object = argument.get(0);
        it = (String)object;
        boolean bl = false;
        String string = (String)(PNX_MATCHER.matches((CharSequence)it) || PN_MATCHER.matches((CharSequence)it) ? object : null);
        if (string == null) {
            return null;
        }
        String pnx = string;
        String uuid2 = ((Object)StringsKt.trim((CharSequence)((String)argument.get(1)))).toString();
        return TuplesKt.to((Object)pnx, (Object)uuid2);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0004\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage$Companion;", "", "", "OPTIONAL_ARG_END", "Ljava/lang/String;", "OPTIONAL_ARG_START", "Lkotlin/text/Regex;", "PNX_MATCHER", "Lkotlin/text/Regex;", "getPNX_MATCHER", "()Lkotlin/text/Regex;", "PN_MATCHER", "getPN_MATCHER", "SEPARATOR", "<init>", "()V", "common"})
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

