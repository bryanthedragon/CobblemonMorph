/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function5
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallback;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b,\u0010-J\u00a1\u0001\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\u001e\u0010\n\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00060\u00070\u00062\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\f0\u000b2H\u0010\u0014\u001aD\u0012\u0004\u0012\u00020\u0002\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\b\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000eH\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u00ad\u0001\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00170\u00062\u001a\b\u0002\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00060\u000b2\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u001a0\u000b2\u001a\b\u0002\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001a0\u001c2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\f0\u000b2\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u001cH\u0007\u00a2\u0006\u0004\b\u001e\u0010\u001fJ-\u0010\"\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010!\u001a\u00020 2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f\u00a2\u0006\u0004\b\"\u0010#J\u001d\u0010$\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010!\u001a\u00020 \u00a2\u0006\u0004\b$\u0010%R#\u0010(\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020'0&8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/api/callback/PartyMoveSelectCallbacks;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/network/chat/MutableComponent;", "partyTitle", "", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "pokemon", "Lkotlin/Function1;", "", "cancel", "Lkotlin/Function5;", "", "Lkotlin/ParameterName;", "name", "pokemonIndex", "moveIndex", "handler", "create", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function5;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/moves/Move;", "moves", "", "canSelectPokemon", "Lkotlin/Function2;", "canSelectMove", "createFromPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "Ljava/util/UUID;", "uuid", "handleCallback", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;II)V", "handleCancelled", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;)V", "", "Lcom/cobblemon/mod/common/api/callback/PartyMoveSelectCallback;", "callbacks", "Ljava/util/Map;", "getCallbacks", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPartyMoveSelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyMoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartyMoveSelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,119:1\n1549#2:120\n1620#2,3:121\n*S KotlinDebug\n*F\n+ 1 PartyMoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartyMoveSelectCallbacks\n*L\n63#1:120\n63#1:121,3\n*E\n"})
public final class PartyMoveSelectCallbacks {
    @NotNull
    public static final PartyMoveSelectCallbacks INSTANCE = new PartyMoveSelectCallbacks();
    @NotNull
    private static final Map<UUID, PartyMoveSelectCallback> callbacks = new LinkedHashMap();

    private PartyMoveSelectCallbacks() {
    }

    @NotNull
    public final Map<UUID, PartyMoveSelectCallback> getCallbacks() {
        return callbacks;
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pair<? extends PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>> pokemon, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallback callback = new PartyMoveSelectCallback(null, pokemon, cancel2, handler, 1, null);
        Map<UUID, PartyMoveSelectCallback> map = callbacks;
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        map.put(uUID, callback);
        CobblemonNetwork.INSTANCE.sendPacket(player, new OpenPartyMoveCallbackPacket(callback.getUuid(), partyTitle, callback.getPokemon()));
    }

    public static /* synthetic */ void create$default(PartyMoveSelectCallbacks partyMoveSelectCallbacks, ServerPlayer serverPlayer, MutableComponent mutableComponent, List list, Function1 function1, Function5 function5, int n, Object object) {
        if ((n & 2) != 0) {
            MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.party", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.party\")");
            mutableComponent = mutableComponent2;
        }
        if ((n & 8) != 0) {
            function1 = create.1.INSTANCE;
        }
        partyMoveSelectCallbacks.create(serverPlayer, mutableComponent, list, (Function1<? super ServerPlayer, Unit>)function1, (Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit>)function5);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, ? extends List<? extends Move>> moves, @NotNull Function1<? super Pokemon, Boolean> canSelectPokemon, @NotNull Function2<? super Pokemon, ? super Move, Boolean> canSelectMove, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelectPokemon, (String)"canSelectPokemon");
        Intrinsics.checkNotNullParameter(canSelectMove, (String)"canSelectMove");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        List pokemonList = new ArrayList();
        for (Pokemon pokemon2 : pokemon) {
            void $this$mapTo$iv$iv;
            boolean enabled = (Boolean)canSelectPokemon.invoke((Object)pokemon2);
            Iterable $this$map$iv = (Iterable)moves.invoke((Object)pokemon2);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Move move = (Move)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new MoveSelectDTO((Move)it, (boolean)((Boolean)canSelectMove.invoke((Object)pokemon2, (Object)it))));
            }
            List moveList = (List)destination$iv$iv;
            pokemonList.add(TuplesKt.to((Object)new PartySelectPokemonDTO(pokemon2, enabled), (Object)moveList));
        }
        this.create(player, partyTitle, pokemonList, cancel2, (Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit>)((Function5)new Function5<ServerPlayer, Integer, PartySelectPokemonDTO, Integer, MoveSelectDTO, Unit>(handler, pokemon){
            final /* synthetic */ Function2<Pokemon, Move, Unit> $handler;
            final /* synthetic */ List<Pokemon> $pokemon;
            {
                this.$handler = $handler;
                this.$pokemon = $pokemon;
                super(5);
            }

            public final void invoke(@NotNull ServerPlayer serverPlayer, int pkIndex, @NotNull PartySelectPokemonDTO partySelectPokemonDTO, int moveIndex, @NotNull MoveSelectDTO moveSelectDTO) {
                Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)partySelectPokemonDTO, (String)"<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter((Object)moveSelectDTO, (String)"<anonymous parameter 4>");
                Move move = this.$pokemon.get(pkIndex).getMoveSet().get(moveIndex);
                if (move == null) {
                    return;
                }
                this.$handler.invoke((Object)this.$pokemon.get(pkIndex), (Object)move);
            }
        }));
    }

    public static /* synthetic */ void createFromPokemon$default(PartyMoveSelectCallbacks partyMoveSelectCallbacks, ServerPlayer serverPlayer, MutableComponent mutableComponent, List list, Function1 function1, Function1 function12, Function2 function2, Function1 function13, Function2 function22, int n, Object object) {
        if ((n & 2) != 0) {
            MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.party", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.party\")");
            mutableComponent = mutableComponent2;
        }
        if ((n & 8) != 0) {
            function1 = createFromPokemon.1.INSTANCE;
        }
        if ((n & 0x10) != 0) {
            function12 = createFromPokemon.2.INSTANCE;
        }
        if ((n & 0x20) != 0) {
            function2 = createFromPokemon.3.INSTANCE;
        }
        if ((n & 0x40) != 0) {
            function13 = createFromPokemon.4.INSTANCE;
        }
        partyMoveSelectCallbacks.createFromPokemon(serverPlayer, mutableComponent, list, (Function1<? super Pokemon, ? extends List<? extends Move>>)function1, (Function1<? super Pokemon, Boolean>)function12, (Function2<? super Pokemon, ? super Move, Boolean>)function2, (Function1<? super ServerPlayer, Unit>)function13, (Function2<? super Pokemon, ? super Move, Unit>)function22);
    }

    public final void handleCancelled(@NotNull ServerPlayer player, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        PartyMoveSelectCallback partyMoveSelectCallback = callbacks.get(player.m_20148_());
        if (partyMoveSelectCallback == null) {
            return;
        }
        PartyMoveSelectCallback callback = partyMoveSelectCallback;
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            return;
        }
        callbacks.remove(player.m_20148_());
        callback.getCancel().invoke((Object)player);
    }

    public final void handleCallback(@NotNull ServerPlayer player, @NotNull UUID uuid2, int pokemonIndex, int moveIndex) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        PartyMoveSelectCallback partyMoveSelectCallback = callbacks.get(player.m_20148_());
        if (partyMoveSelectCallback == null) {
            return;
        }
        PartyMoveSelectCallback callback = partyMoveSelectCallback;
        callbacks.remove(player.m_20148_());
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            Cobblemon.INSTANCE.getLOGGER().warn("A party move select callback ran but with a mismatching UUID from " + player.m_36316_().getName() + ". Hacking attempts?");
            return;
        }
        if (pokemonIndex >= callback.getPokemon().size()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party move select callback with a Pok\u00e9mon index that was out of bounds. Hacking attempts? Tried " + pokemonIndex + ", possible size was " + callback.getPokemon().size());
            return;
        }
        Pair<PartySelectPokemonDTO, List<MoveSelectDTO>> pair = callback.getPokemon().get(pokemonIndex);
        PartySelectPokemonDTO pokemon = (PartySelectPokemonDTO)pair.component1();
        List moves = (List)pair.component2();
        if (!pokemon.getEnabled()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party move select callback with a Pok\u00e9mon that is not enabled. Hacking attempts?");
            return;
        }
        if (moveIndex >= moves.size()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party move select callback with a move index that was out of bounds. Hacking attempts? Tried " + pokemonIndex + "-" + moveIndex + ", possible size was " + moves.size());
            return;
        }
        MoveSelectDTO move = (MoveSelectDTO)moves.get(moveIndex);
        if (!move.getEnabled()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party move select callback with a move that is not enabled. Hacking attempts?");
        } else {
            callback.getHandler().invoke((Object)player, (Object)pokemonIndex, (Object)pokemon, (Object)moveIndex, (Object)move);
        }
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pair<? extends PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>> pokemon, @NotNull Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.create$default(this, player, partyTitle, pokemon, null, handler, 8, null);
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<? extends Pair<? extends PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>> pokemon, @NotNull Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.create$default(this, player, null, pokemon, null, handler, 10, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, ? extends List<? extends Move>> moves, @NotNull Function1<? super Pokemon, Boolean> canSelectPokemon, @NotNull Function2<? super Pokemon, ? super Move, Boolean> canSelectMove, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelectPokemon, (String)"canSelectPokemon");
        Intrinsics.checkNotNullParameter(canSelectMove, (String)"canSelectMove");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.createFromPokemon$default(this, player, partyTitle, pokemon, moves, canSelectPokemon, canSelectMove, null, handler, 64, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, ? extends List<? extends Move>> moves, @NotNull Function1<? super Pokemon, Boolean> canSelectPokemon, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(canSelectPokemon, (String)"canSelectPokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.createFromPokemon$default(this, player, partyTitle, pokemon, moves, canSelectPokemon, null, null, handler, 96, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, ? extends List<? extends Move>> moves, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.createFromPokemon$default(this, player, partyTitle, pokemon, moves, null, null, null, handler, 112, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pokemon> pokemon, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.createFromPokemon$default(this, player, partyTitle, pokemon, null, null, null, null, handler, 120, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull List<? extends Pokemon> pokemon, @NotNull Function2<? super Pokemon, ? super Move, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartyMoveSelectCallbacks.createFromPokemon$default(this, player, null, pokemon, null, null, null, null, handler, 122, null);
    }
}

