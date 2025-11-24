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
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpastureAllPokemonPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/pasture/UnpastureAllPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpastureAllPokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpastureAllPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nUnpastureAllPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UnpastureAllPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpastureAllPokemonHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,28:1\n1549#2:29\n1620#2,3:30\n1855#2,2:33\n*S KotlinDebug\n*F\n+ 1 UnpastureAllPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpastureAllPokemonHandler\n*L\n26#1:29\n26#1:30,3\n26#1:33,2\n*E\n"})
public final class UnpastureAllPokemonHandler
implements ServerNetworkPacketHandler<UnpastureAllPokemonPacket> {
    @NotNull
    public static final UnpastureAllPokemonHandler INSTANCE = new UnpastureAllPokemonHandler();

    private UnpastureAllPokemonHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull UnpastureAllPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        void $this$forEach$iv;
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PastureLink pastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
        if (pastureLink == null) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
            return;
        }
        PastureLink pastureLink2 = pastureLink;
        BlockEntity blockEntity = player.m_9236_().m_7702_(pastureLink2.getPos());
        PokemonPastureBlockEntity pokemonPastureBlockEntity = blockEntity instanceof PokemonPastureBlockEntity ? (PokemonPastureBlockEntity)blockEntity : null;
        if (pokemonPastureBlockEntity == null) {
            return;
        }
        PokemonPastureBlockEntity pastureBlockEntity = pokemonPastureBlockEntity;
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        Iterable $this$map$iv = pastureBlockEntity.releaseAllPokemon(uUID);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            UUID uUID2 = (UUID)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new PokemonUnpasturedPacket((UUID)p0));
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PokemonUnpasturedPacket it = (PokemonUnpasturedPacket)element$iv;
            boolean bl = false;
            it.sendToPlayer(player);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull UnpastureAllPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

