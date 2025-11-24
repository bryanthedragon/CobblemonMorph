/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestPlayerInteractionsPacket;
import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/RequestInteractionsHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/RequestPlayerInteractionsPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/RequestPlayerInteractionsPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
public final class RequestInteractionsHandler
implements ServerNetworkPacketHandler<RequestPlayerInteractionsPacket> {
    @NotNull
    public static final RequestInteractionsHandler INSTANCE = new RequestInteractionsHandler();

    private RequestInteractionsHandler() {
    }

    @Override
    public void handle(@NotNull RequestPlayerInteractionsPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        boolean isTargetBattling;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        EnumSet<Enum> options = EnumSet.of((Enum)PlayerInteractOptionsPacket.Options.TRADE);
        boolean bl = isTargetBattling = BattleRegistry.INSTANCE.getBattleByParticipatingPlayerId(packet.getTargetId()) != null;
        if (isTargetBattling & Cobblemon.INSTANCE.getConfig().getAllowSpectating()) {
            options.add(PlayerInteractOptionsPacket.Options.SPECTATE_BATTLE);
        } else {
            options.add(PlayerInteractOptionsPacket.Options.BATTLE);
        }
        Intrinsics.checkNotNullExpressionValue(options, (String)"options");
        new PlayerInteractOptionsPacket(options, packet.getTargetId(), packet.getTargetNumericId(), packet.getPokemonId()).sendToPlayer(player);
    }

    @Override
    public void handleOnNettyThread(@NotNull RequestPlayerInteractionsPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

