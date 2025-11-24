/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonNicknamedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NicknameUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetNicknamePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/pokemon/update/SetNicknameHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/SetNicknamePacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/SetNicknamePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSetNicknameHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetNicknameHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/update/SetNicknameHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,49:1\n1#2:50\n40#3:51\n41#3,6:55\n47#3:63\n17#4,2:52\n19#4:62\n13579#5:54\n13580#5:61\n*S KotlinDebug\n*F\n+ 1 SetNicknameHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/update/SetNicknameHandler\n*L\n35#1:51\n35#1:55,6\n35#1:63\n35#1:52,2\n35#1:62\n35#1:54\n35#1:61\n*E\n"})
public final class SetNicknameHandler
implements ServerNetworkPacketHandler<SetNicknamePacket> {
    @NotNull
    public static final SetNicknameHandler INSTANCE = new SetNicknameHandler();

    private SetNicknameHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull SetNicknamePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        void this_$iv$iv;
        MutableComponent mutableComponent;
        EventObservable it;
        PokemonStore pokemonStore;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (packet.isParty()) {
            pokemonStore = PlayerExtensionsKt.party(player);
        } else {
            PCStore pCStore = PCLinkManager.INSTANCE.getPC(player);
            if (pCStore == null) {
                SetNicknameHandler $this$handle_u24lambda_u240 = this;
                boolean bl = false;
                new ClosePCPacket(null).sendToPlayer(player);
                return;
            }
            pokemonStore = pCStore;
        }
        PokemonStore pokemonStore2 = pokemonStore;
        Pokemon pokemon = pokemonStore2.get(packet.getPokemonUUID());
        if (pokemon == null) {
            return;
        }
        Pokemon pokemon2 = pokemon;
        CancelableObservable<PokemonNicknamedEvent> cancelableObservable = CobblemonEvents.POKEMON_NICKNAMED;
        ServerPlayer serverPlayer = player;
        Pokemon pokemon3 = pokemon2;
        String string = packet.getNickname();
        if (string != null) {
            String string2 = string;
            Pokemon pokemon4 = pokemon3;
            ServerPlayer serverPlayer2 = serverPlayer;
            boolean bl = false;
            MutableComponent mutableComponent2 = Component.m_237113_((String)((Object)it));
            serverPlayer = serverPlayer2;
            pokemon3 = pokemon4;
            mutableComponent = mutableComponent2;
        } else {
            mutableComponent = null;
        }
        MutableComponent mutableComponent3 = mutableComponent;
        Pokemon pokemon5 = pokemon3;
        ServerPlayer serverPlayer3 = serverPlayer;
        PokemonNicknamedEvent pokemonNicknamedEvent = new PokemonNicknamedEvent(serverPlayer3, pokemon5, mutableComponent3);
        CancelableObservable<PokemonNicknamedEvent> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        it = this_$iv;
        Cancelable[] bl = new Cancelable[]{pokemonNicknamedEvent};
        Cancelable[] events$iv$iv = bl;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            PokemonNicknamedEvent it2;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl2 = false;
            if (it$iv.isCanceled()) {
                it2 = (PokemonNicknamedEvent)it$iv;
                boolean bl3 = false;
                CobblemonNetwork.INSTANCE.sendPacket(player, new NicknameUpdatePacket((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(pokemon2){
                    final /* synthetic */ Pokemon $pokemon;
                    {
                        this.$pokemon = $pokemon;
                        super(0);
                    }

                    @NotNull
                    public final Pokemon invoke() {
                        return this.$pokemon;
                    }
                }), pokemon2.getNickname()));
                continue;
            }
            it2 = (PokemonNicknamedEvent)it$iv;
            boolean bl4 = false;
            pokemon2.setNickname(it2.getNickname());
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull SetNicknamePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

