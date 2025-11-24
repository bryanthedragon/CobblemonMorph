/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.ReleasePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/storage/pc/ReleasePartyPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/storage/party/ReleasePartyPokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/storage/party/ReleasePartyPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nReleasePartyPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReleasePartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/ReleasePartyPokemonHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,41:1\n40#2:42\n41#2,4:46\n46#2:59\n47#2:62\n17#3,2:43\n14#3,5:50\n19#3:58\n19#3:61\n13579#4:45\n13579#4:55\n13580#4:57\n13580#4:60\n14#5:56\n*S KotlinDebug\n*F\n+ 1 ReleasePartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/ReleasePartyPokemonHandler\n*L\n28#1:42\n28#1:46,4\n28#1:59\n28#1:62\n28#1:43,2\n34#1:50,5\n34#1:58\n28#1:61\n28#1:45\n34#1:55\n34#1:57\n28#1:60\n34#1:56\n*E\n"})
public final class ReleasePartyPokemonHandler
implements ServerNetworkPacketHandler<ReleasePartyPokemonPacket> {
    @NotNull
    public static final ReleasePartyPokemonHandler INSTANCE = new ReleasePartyPokemonHandler();

    private ReleasePartyPokemonHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull ReleasePartyPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PlayerPartyStore party = PlayerExtensionsKt.party(player);
        Pokemon pokemon = party.get(packet.getPosition());
        if (pokemon == null) {
            return;
        }
        Pokemon pokemon2 = pokemon;
        if (!Intrinsics.areEqual((Object)pokemon2.getUuid(), (Object)packet.getPokemonID())) {
            return;
        }
        CancelableObservable<ReleasePokemonEvent.Pre> cancelableObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_PRE;
        ReleasePokemonEvent.Pre pre = new ReleasePokemonEvent.Pre(player, pokemon2, party);
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
                party.set(packet.getPosition(), pokemon2);
                continue;
            }
            preEvent = (ReleasePokemonEvent.Pre)it$iv;
            boolean bl3 = false;
            if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit() && CollectionsKt.filterNotNull((Iterable)party).size() <= 1) {
                return;
            }
            party.remove(pokemon2);
            EventObservable<ReleasePokemonEvent.Post> eventObservable2 = CobblemonEvents.POKEMON_RELEASED_EVENT_POST;
            ReleasePokemonEvent.Post[] postArray = new ReleasePokemonEvent.Post[]{new ReleasePokemonEvent.Post(player, pokemon2, party)};
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
    public void handleOnNettyThread(@NotNull ReleasePartyPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

