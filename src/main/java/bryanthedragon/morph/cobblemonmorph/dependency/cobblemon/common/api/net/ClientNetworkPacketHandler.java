/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u0000*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0003J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;Lnet/minecraft/client/Minecraft;)V", "handleOnNettyThread", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "common"})
public interface ClientNetworkPacketHandler<T extends NetworkPacket<T>> {
    public void handle(@NotNull T var1, @NotNull Minecraft var2);

    public void handleOnNettyThread(@NotNull T var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends NetworkPacket<T>> void handleOnNettyThread(@NotNull ClientNetworkPacketHandler<T> $this, @NotNull T packet) {
            Intrinsics.checkNotNullParameter(packet, (String)"packet");
            Minecraft client = Minecraft.m_91087_();
            client.execute(() -> DefaultImpls.handleOnNettyThread$lambda$0($this, packet, client));
        }

        private static void handleOnNettyThread$lambda$0(ClientNetworkPacketHandler this$0, NetworkPacket $packet, Minecraft $client) {
            Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
            Intrinsics.checkNotNullParameter((Object)$packet, (String)"$packet");
            Intrinsics.checkNotNullExpressionValue((Object)$client, (String)"client");
            this$0.handle($packet, $client);
        }
    }
}

