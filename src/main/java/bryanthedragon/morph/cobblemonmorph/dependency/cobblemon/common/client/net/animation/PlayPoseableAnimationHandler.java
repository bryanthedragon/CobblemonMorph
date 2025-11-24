/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/animation/PlayPoseableAnimationHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/animation/PlayPoseableAnimationPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/animation/PlayPoseableAnimationPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class PlayPoseableAnimationHandler
implements ClientNetworkPacketHandler<PlayPoseableAnimationPacket> {
    @NotNull
    public static final PlayPoseableAnimationHandler INSTANCE = new PlayPoseableAnimationHandler();

    private PlayPoseableAnimationHandler() {
    }

    @Override
    public void handle(@NotNull PlayPoseableAnimationPacket packet, @NotNull Minecraft client) {
        EntitySideDelegate<?> delegate;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ClientLevel clientLevel = client.f_91073_;
        if (clientLevel == null) {
            return;
        }
        ClientLevel world = clientLevel;
        Entity entity2 = world.m_6815_(packet.getEntityId());
        if (entity2 == null) {
            return;
        }
        Entity entity3 = entity2;
        if (entity3 instanceof Poseable && (delegate = ((Poseable)entity3).getDelegate()) instanceof PoseableEntityState) {
            for (String expr : packet.getExpressions()) {
                MoLangRuntime moLangRuntime = ((PoseableEntityState)((Object)delegate)).getRuntime();
                Expression expression = MoLangExtensionsKt.asExpression(expr);
                Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expr.asExpression()");
                MoLangExtensionsKt.resolve(moLangRuntime, expression);
            }
            ((PoseableEntityState)((Object)delegate)).addFirstAnimation(packet.getAnimation());
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull PlayPoseableAnimationPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

