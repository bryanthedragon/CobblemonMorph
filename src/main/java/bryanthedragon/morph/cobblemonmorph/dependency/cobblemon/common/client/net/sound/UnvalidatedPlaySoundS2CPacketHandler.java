/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.sound;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound.UnvalidatedPlaySoundS2CPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c0\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/sound/UnvalidatedPlaySoundS2CPacketHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/sound/UnvalidatedPlaySoundS2CPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/sound/UnvalidatedPlaySoundS2CPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class UnvalidatedPlaySoundS2CPacketHandler
implements ClientNetworkPacketHandler<UnvalidatedPlaySoundS2CPacket> {
    @NotNull
    public static final UnvalidatedPlaySoundS2CPacketHandler INSTANCE = new UnvalidatedPlaySoundS2CPacketHandler();

    private UnvalidatedPlaySoundS2CPacketHandler() {
    }

    @Override
    public void handle(@NotNull UnvalidatedPlaySoundS2CPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        client.m_201446_(() -> UnvalidatedPlaySoundS2CPacketHandler.handle$lambda$0(client, packet));
    }

    @Override
    public void handleOnNettyThread(@NotNull UnvalidatedPlaySoundS2CPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }

    private static final void handle$lambda$0(Minecraft $client, UnvalidatedPlaySoundS2CPacket $packet) {
        block1: {
            Intrinsics.checkNotNullParameter((Object)$client, (String)"$client");
            Intrinsics.checkNotNullParameter((Object)$packet, (String)"$packet");
            if ($client.m_91106_().m_120384_($packet.getSound()) == null) break block1;
            ClientLevel clientLevel = $client.f_91073_;
            if (clientLevel != null) {
                clientLevel.m_6263_((Player)$client.f_91074_, $packet.getX(), $packet.getY(), $packet.getZ(), SoundEvent.m_262824_((ResourceLocation)$packet.getSound()), $packet.getCategory(), $packet.getVolume(), $packet.getPitch());
            }
        }
    }
}

