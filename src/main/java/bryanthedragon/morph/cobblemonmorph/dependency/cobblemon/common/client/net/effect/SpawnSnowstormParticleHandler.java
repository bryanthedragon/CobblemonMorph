/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Matrix4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormParticlePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/effect/SpawnSnowstormParticleHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormParticlePacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormParticlePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class SpawnSnowstormParticleHandler
implements ClientNetworkPacketHandler<SpawnSnowstormParticlePacket> {
    @NotNull
    public static final SpawnSnowstormParticleHandler INSTANCE = new SpawnSnowstormParticleHandler();

    private SpawnSnowstormParticleHandler() {
    }

    @Override
    public void handle(@NotNull SpawnSnowstormParticlePacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        MatrixWrapper wrapper = new MatrixWrapper();
        PoseStack matrix = new PoseStack();
        matrix.m_85837_(packet.getPosition().f_82479_, packet.getPosition().f_82480_, packet.getPosition().f_82481_);
        Matrix4f matrix4f = matrix.m_85850_().m_252922_();
        Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrix.peek().positionMatrix");
        wrapper.updateMatrix(matrix4f);
        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
        if (clientLevel == null) {
            return;
        }
        ClientLevel world = clientLevel;
        BedrockParticleEffect bedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(packet.getEffectId());
        if (bedrockParticleEffect == null) {
            return;
        }
        BedrockParticleEffect effect = bedrockParticleEffect;
        new ParticleStorm(effect, wrapper, world, null, null, null, null, null, null, 504, null).spawn();
    }

    @Override
    public void handleOnNettyThread(@NotNull SpawnSnowstormParticlePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

