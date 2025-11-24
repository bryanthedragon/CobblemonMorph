/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.ReleasePCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/storage/party/ReleasePCPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/storage/pc/ReleasePCPokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/storage/pc/ReleasePCPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nReleasePCPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReleasePCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/party/ReleasePCPokemonHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,39:1\n40#2:40\n41#2,4:44\n46#2:57\n47#2:60\n17#3,2:41\n14#3,5:48\n19#3:56\n19#3:59\n13579#4:43\n13579#4:53\n13580#4:55\n13580#4:58\n14#5:54\n*S KotlinDebug\n*F\n+ 1 ReleasePCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/party/ReleasePCPokemonHandler\n*L\n28#1:40\n28#1:44,4\n28#1:57\n28#1:60\n28#1:41,2\n32#1:48,5\n32#1:56\n28#1:59\n28#1:43\n32#1:53\n32#1:55\n28#1:58\n32#1:54\n*E\n"})
public final class ReleasePCPokemonHandler
implements ServerNetworkPacketHandler<ReleasePCPokemonPacket> {
    @NotNull
    public static final ReleasePCPokemonHandler INSTANCE = new ReleasePCPokemonHandler();

    private ReleasePCPokemonHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull ReleasePCPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PCStore pCStore = PCLinkManager.INSTANCE.getPC(player);
        if (pCStore == null) {
            return;
        }
        PCStore pc = pCStore;
        Pokemon pokemon = pc.get(packet.getPosition());
        if (pokemon == null) {
            return;
        }
        Pokemon pokemon2 = pokemon;
        if (!Intrinsics.areEqual((Object)pokemon2.getUuid(), (Object)packet.getPokemonID())) {
            return;
        }
        CancelableObservable<ReleasePokemonEvent.Pre> cancelableObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_PRE;
        ReleasePokemonEvent.Pre pre = new ReleasePokemonEvent.Pre(player, pokemon2, pc);
        CancelableObservable<ReleasePokemonEvent.Pre> this_$iv = cancelableObservable;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = this_$iv;
        Cancelable[] cancelableArray = new Cancelable[]{pre};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            void $this$iv;
            ReleasePokemonEvent.Pre preEvent;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                preEvent = (ReleasePokemonEvent.Pre)it$iv;
                boolean bl2 = false;
                pc.set(packet.getPosition(), pokemon2);
                continue;
            }
            preEvent = (ReleasePokemonEvent.Pre)it$iv;
            boolean bl3 = false;
            pc.remove((StorePosition)packet.getPosition());
            EventObservable<ReleasePokemonEvent.Post> eventObservable2 = CobblemonEvents.POKEMON_RELEASED_EVENT_POST;
            ReleasePokemonEvent.Post[] postArray = new ReleasePokemonEvent.Post[]{new ReleasePokemonEvent.Post(player, pokemon2, pc)};
            ReleasePokemonEvent.Post[] events$iv = postArray;
            boolean $i$f$post2 = false;
            $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            ReleasePokemonEvent.Post[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach2 = false;
            int n2 = $this$forEach$iv$iv.length;
            for (int j = 0; j < n2; ++j) {
                ReleasePokemonEvent.Post element$iv$iv;
                ReleasePokemonEvent.Post post2 = element$iv$iv = $this$forEach$iv$iv[j];
                boolean bl4 = false;
                ReleasePokemonEvent.Post it = post2;
            }
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull ReleasePCPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

