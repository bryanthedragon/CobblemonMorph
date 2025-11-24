/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallback;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010(Jn\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\t2'\u0010\u0011\u001a#\u0012\u0004\u0012\u00020\u0002\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\n0\fH\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013Jm\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00140\u00062\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\t2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\t2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018Jm\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00190\u00062\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00150\t2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\t2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0004\b\u001a\u0010\u0018J%\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\r\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001f\u0010 R#\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\"0!8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/callback/PartySelectCallbacks;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/network/chat/Component;", "title", "", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "pokemon", "Lkotlin/Function1;", "", "cancel", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "index", "handler", "create", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/Component;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "", "canSelect", "createBattleSelect", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/Component;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "createFromPokemon", "Ljava/util/UUID;", "uuid", "handleCallback", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;I)V", "handleCancelled", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;)V", "", "Lcom/cobblemon/mod/common/api/callback/PartySelectCallback;", "callbacks", "Ljava/util/Map;", "getCallbacks", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPartySelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartySelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,163:1\n1549#2:164\n1620#2,2:165\n1622#2:168\n1549#2:169\n1620#2,3:170\n1#3:167\n*S KotlinDebug\n*F\n+ 1 PartySelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartySelectCallbacks\n*L\n73#1:164\n73#1:165,2\n73#1:168\n88#1:169\n88#1:170,3\n*E\n"})
public final class PartySelectCallbacks {
    @NotNull
    public static final PartySelectCallbacks INSTANCE = new PartySelectCallbacks();
    @NotNull
    private static final Map<UUID, PartySelectCallback> callbacks = new LinkedHashMap();

    private PartySelectCallbacks() {
    }

    @NotNull
    public final Map<UUID, PartySelectCallback> getCallbacks() {
        return callbacks;
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends PartySelectPokemonDTO> pokemon, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function2<? super ServerPlayer, ? super Integer, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallback callback = new PartySelectCallback(null, pokemon, cancel2, handler, 1, null);
        Map<UUID, PartySelectCallback> map = callbacks;
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        map.put(uUID, callback);
        UUID uUID2 = callback.getUuid();
        MutableComponent mutableComponent = title.m_6881_();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"title.copy()");
        CobblemonNetwork.INSTANCE.sendPacket(player, new OpenPartyCallbackPacket(uUID2, mutableComponent, pokemon));
    }

    public static /* synthetic */ void create$default(PartySelectCallbacks partySelectCallbacks, ServerPlayer serverPlayer, Component component, List list, Function1 function1, Function2 function2, int n, Object object) {
        if ((n & 2) != 0) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
            component = (Component)mutableComponent;
        }
        if ((n & 8) != 0) {
            function1 = create.1.INSTANCE;
        }
        partySelectCallbacks.create(serverPlayer, component, list, (Function1<? super ServerPlayer, Unit>)function1, (Function2<? super ServerPlayer, ? super Integer, Unit>)function2);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends BattlePokemon> pokemon, @NotNull Function1<? super BattlePokemon, Boolean> canSelect, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function1<? super BattlePokemon, Unit> handler) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Iterable $this$map$iv = pokemon;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void pk;
            PartySelectPokemonDTO partySelectPokemonDTO;
            BattlePokemon battlePokemon = (BattlePokemon)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            PartySelectPokemonDTO it = partySelectPokemonDTO = new PartySelectPokemonDTO(pk.getEffectedPokemon(), false, 2, null);
            boolean bl2 = false;
            it.setEnabled((Boolean)canSelect.invoke((Object)pk));
            collection.add(partySelectPokemonDTO);
        }
        List list = (List)destination$iv$iv;
        this.create(player, title, list, cancel2, (Function2<? super ServerPlayer, ? super Integer, Unit>)((Function2)new Function2<ServerPlayer, Integer, Unit>(handler, pokemon){
            final /* synthetic */ Function1<BattlePokemon, Unit> $handler;
            final /* synthetic */ List<BattlePokemon> $pokemon;
            {
                this.$handler = $handler;
                this.$pokemon = $pokemon;
                super(2);
            }

            public final void invoke(@NotNull ServerPlayer serverPlayer, int index) {
                Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                this.$handler.invoke((Object)this.$pokemon.get(index));
            }
        }));
    }

    public static /* synthetic */ void createBattleSelect$default(PartySelectCallbacks partySelectCallbacks, ServerPlayer serverPlayer, Component component, List list, Function1 function1, Function1 function12, Function1 function13, int n, Object object) {
        if ((n & 2) != 0) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
            component = (Component)mutableComponent;
        }
        if ((n & 0x10) != 0) {
            function12 = createBattleSelect.1.INSTANCE;
        }
        partySelectCallbacks.createBattleSelect(serverPlayer, component, list, (Function1<? super BattlePokemon, Boolean>)function1, (Function1<? super ServerPlayer, Unit>)function12, (Function1<? super BattlePokemon, Unit>)function13);
    }

    /*
     * WARNING - void declaration
     */
    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, Boolean> canSelect, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function1<? super Pokemon, Unit> handler) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Iterable $this$map$iv = pokemon;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void pk;
            PartySelectPokemonDTO partySelectPokemonDTO;
            Pokemon pokemon2 = (Pokemon)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            PartySelectPokemonDTO it = partySelectPokemonDTO = new PartySelectPokemonDTO((Pokemon)pk, false, 2, null);
            boolean bl2 = false;
            it.setEnabled((Boolean)canSelect.invoke((Object)pk));
            collection.add(partySelectPokemonDTO);
        }
        List list = (List)destination$iv$iv;
        this.create(player, title, list, cancel2, (Function2<? super ServerPlayer, ? super Integer, Unit>)((Function2)new Function2<ServerPlayer, Integer, Unit>(handler, pokemon){
            final /* synthetic */ Function1<Pokemon, Unit> $handler;
            final /* synthetic */ List<Pokemon> $pokemon;
            {
                this.$handler = $handler;
                this.$pokemon = $pokemon;
                super(2);
            }

            public final void invoke(@NotNull ServerPlayer serverPlayer, int index) {
                Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                this.$handler.invoke((Object)this.$pokemon.get(index));
            }
        }));
    }

    public static /* synthetic */ void createFromPokemon$default(PartySelectCallbacks partySelectCallbacks, ServerPlayer serverPlayer, Component component, List list, Function1 function1, Function1 function12, Function1 function13, int n, Object object) {
        if ((n & 2) != 0) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
            component = (Component)mutableComponent;
        }
        if ((n & 0x10) != 0) {
            function12 = createFromPokemon.1.INSTANCE;
        }
        partySelectCallbacks.createFromPokemon(serverPlayer, component, list, (Function1<? super Pokemon, Boolean>)function1, (Function1<? super ServerPlayer, Unit>)function12, (Function1<? super Pokemon, Unit>)function13);
    }

    public final void handleCancelled(@NotNull ServerPlayer player, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        PartySelectCallback partySelectCallback = callbacks.get(player.m_20148_());
        if (partySelectCallback == null) {
            return;
        }
        PartySelectCallback callback = partySelectCallback;
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            return;
        }
        callbacks.remove(player.m_20148_());
        callback.getCancel().invoke((Object)player);
    }

    public final void handleCallback(@NotNull ServerPlayer player, @NotNull UUID uuid2, int index) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        PartySelectCallback partySelectCallback = callbacks.get(player.m_20148_());
        if (partySelectCallback == null) {
            return;
        }
        PartySelectCallback callback = partySelectCallback;
        callbacks.remove(player.m_20148_());
        if (!Intrinsics.areEqual((Object)callback.getUuid(), (Object)uuid2)) {
            Cobblemon.INSTANCE.getLOGGER().warn("A party select callback ran but with a mismatching UUID from " + player.m_36316_().getName() + ". Hacking attempts?");
        } else if (index >= callback.getShownPokemon().size()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party select callback with an out of bounds index. Hacking attempts? Tried " + index + ", Pok\u00e9mon list size was " + callback.getShownPokemon().size());
        } else if (!callback.getShownPokemon().get(index).getEnabled()) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_36316_().getName() + " used party select callback with a Pok\u00e9mon that is not enabled. Hacking attempts?");
        } else {
            callback.getHandler().invoke((Object)player, (Object)index);
        }
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends PartySelectPokemonDTO> pokemon, @NotNull Function2<? super ServerPlayer, ? super Integer, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.create$default(this, player, title, pokemon, null, handler, 8, null);
    }

    @JvmOverloads
    public final void create(@NotNull ServerPlayer player, @NotNull List<? extends PartySelectPokemonDTO> pokemon, @NotNull Function2<? super ServerPlayer, ? super Integer, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.create$default(this, player, null, pokemon, null, handler, 10, null);
    }

    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends BattlePokemon> pokemon, @NotNull Function1<? super BattlePokemon, Boolean> canSelect, @NotNull Function1<? super BattlePokemon, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.createBattleSelect$default(this, player, title, pokemon, canSelect, null, handler, 16, null);
    }

    @JvmOverloads
    public final void createBattleSelect(@NotNull ServerPlayer player, @NotNull List<? extends BattlePokemon> pokemon, @NotNull Function1<? super BattlePokemon, Boolean> canSelect, @NotNull Function1<? super BattlePokemon, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.createBattleSelect$default(this, player, null, pokemon, canSelect, null, handler, 18, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull Component title, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, Boolean> canSelect, @NotNull Function1<? super Pokemon, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.createFromPokemon$default(this, player, title, pokemon, canSelect, null, handler, 16, null);
    }

    @JvmOverloads
    public final void createFromPokemon(@NotNull ServerPlayer player, @NotNull List<? extends Pokemon> pokemon, @NotNull Function1<? super Pokemon, Boolean> canSelect, @NotNull Function1<? super Pokemon, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        PartySelectCallbacks.createFromPokemon$default(this, player, null, pokemon, canSelect, null, handler, 18, null);
    }
}

