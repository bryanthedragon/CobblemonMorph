/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Direction
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/pasture/PasturePokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pasture/PasturePokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pasture/PasturePokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
public final class PasturePokemonHandler
implements ServerNetworkPacketHandler<PasturePokemonPacket> {
    @NotNull
    public static final PasturePokemonHandler INSTANCE = new PasturePokemonHandler();

    private PasturePokemonHandler() {
    }

    @Override
    public void handle(@NotNull PasturePokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PastureLink pastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
        if (pastureLink == null) {
            return;
        }
        PastureLink pastureLink2 = pastureLink;
        if (!Intrinsics.areEqual((Object)pastureLink2.getLinkId(), (Object)packet.getPastureId())) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
            return;
        }
        PCStore pc = Cobblemon.INSTANCE.getStorage().getPC(pastureLink2.getPcId());
        Pokemon pokemon = pc.get(packet.getPokemonId());
        if (pokemon == null) {
            return;
        }
        Pokemon pokemon2 = pokemon;
        BlockEntity blockEntity = player.m_9236_().m_7702_(pastureLink2.getPos());
        PokemonPastureBlockEntity pokemonPastureBlockEntity = blockEntity instanceof PokemonPastureBlockEntity ? (PokemonPastureBlockEntity)blockEntity : null;
        if (pokemonPastureBlockEntity == null) {
            return;
        }
        PokemonPastureBlockEntity pastureBlockEntity = pokemonPastureBlockEntity;
        BlockState state = player.m_9236_().m_8055_(pastureLink2.getPos());
        Direction direction = (Direction)state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        if (pokemon2.getTetheringId() != null) {
            return;
        }
        int maxPerPlayer = pastureLink2.getPermissions().getMaxPokemon();
        if (pastureBlockEntity.canAddPokemon(player, pokemon2, maxPerPlayer)) {
            Intrinsics.checkNotNullExpressionValue((Object)direction, (String)"direction");
            pastureBlockEntity.tether(player, pokemon2, direction);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull PasturePokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

