/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.spawn;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000*\u0014\b\u0000\u0010\u0002*\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\b\u0012\u0004\u0012\u00028\u00000\u0005B\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/net/spawn/SpawnExtraDataEntityHandler;", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;", "T", "Lnet/minecraft/world/entity/Entity;", "E", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class SpawnExtraDataEntityHandler<T extends SpawnExtraDataEntityPacket<T, E>, E extends Entity>
implements ClientNetworkPacketHandler<T> {
    @Override
    public void handle(@NotNull T packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ((SpawnExtraDataEntityPacket)packet).spawnAndApply(client);
    }

    @Override
    public void handleOnNettyThread(@NotNull T packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

