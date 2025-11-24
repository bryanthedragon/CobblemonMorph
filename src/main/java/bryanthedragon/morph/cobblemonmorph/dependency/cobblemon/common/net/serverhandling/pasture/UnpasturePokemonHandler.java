/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpasturePokemonPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/pasture/UnpasturePokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpasturePokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpasturePokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nUnpasturePokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UnpasturePokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pasture/UnpasturePokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"})
public final class UnpasturePokemonHandler
implements ServerNetworkPacketHandler<UnpasturePokemonPacket> {
    @NotNull
    public static final UnpasturePokemonHandler INSTANCE = new UnpasturePokemonHandler();

    private UnpasturePokemonHandler() {
    }

    @Override
    public void handle(@NotNull UnpasturePokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Object v2;
        PokemonPastureBlockEntity pastureBlockEntity;
        block5: {
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
            pastureBlockEntity = pokemonPastureBlockEntity;
            Iterable iterable = pastureBlockEntity.getTetheredPokemon();
            for (Object t : iterable) {
                PokemonPastureBlockEntity.Tethering it = (PokemonPastureBlockEntity.Tethering)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getPokemonId(), (Object)packet.getPokemonId())) continue;
                v2 = t;
                break block5;
            }
            v2 = null;
        }
        PokemonPastureBlockEntity.Tethering tethered = v2;
        if (tethered != null && Intrinsics.areEqual((Object)tethered.getPlayerId(), (Object)player.m_20148_())) {
            pastureBlockEntity.releasePokemon(tethered.getPokemonId());
            CobblemonNetwork.INSTANCE.sendPacket(player, new PokemonUnpasturedPacket(packet.getPokemonId()));
        } else {
            CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull UnpasturePokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

