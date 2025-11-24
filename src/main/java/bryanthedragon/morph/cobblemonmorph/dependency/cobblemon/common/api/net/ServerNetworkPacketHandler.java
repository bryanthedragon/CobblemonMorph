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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0003J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\f\u0010\u000b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "handleOnNettyThread", "common"})
public interface ServerNetworkPacketHandler<T extends NetworkPacket<T>> {
    public void handle(@NotNull T var1, @NotNull MinecraftServer var2, @NotNull ServerPlayer var3);

    public void handleOnNettyThread(@NotNull T var1, @NotNull MinecraftServer var2, @NotNull ServerPlayer var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends NetworkPacket<T>> void handleOnNettyThread(@NotNull ServerNetworkPacketHandler<T> $this, @NotNull T packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter(packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            server.execute(() -> DefaultImpls.handleOnNettyThread$lambda$0($this, packet, server, player));
        }

        private static void handleOnNettyThread$lambda$0(ServerNetworkPacketHandler this$0, NetworkPacket $packet, MinecraftServer $server, ServerPlayer $player) {
            Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
            Intrinsics.checkNotNullParameter((Object)$packet, (String)"$packet");
            Intrinsics.checkNotNullParameter((Object)$server, (String)"$server");
            Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
            this$0.handle($packet, $server, $player);
        }
    }
}

