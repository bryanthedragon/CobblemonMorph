/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/effect/RunPosableMoLangHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/effect/RunPosableMoLangPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/effect/RunPosableMoLangPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nRunPosableMoLangHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RunPosableMoLangHandler.kt\ncom/cobblemon/mod/common/client/net/effect/RunPosableMoLangHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,30:1\n1549#2:31\n1620#2,3:32\n*S KotlinDebug\n*F\n+ 1 RunPosableMoLangHandler.kt\ncom/cobblemon/mod/common/client/net/effect/RunPosableMoLangHandler\n*L\n25#1:31\n25#1:32,3\n*E\n"})
public final class RunPosableMoLangHandler
implements ClientNetworkPacketHandler<RunPosableMoLangPacket> {
    @NotNull
    public static final RunPosableMoLangHandler INSTANCE = new RunPosableMoLangHandler();

    private RunPosableMoLangHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull RunPosableMoLangPacket packet, @NotNull Minecraft client) {
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
        if (entity3 instanceof Poseable) {
            void $this$mapTo$iv$iv;
            EntitySideDelegate<?> entitySideDelegate = ((Poseable)entity3).getDelegate();
            PoseableEntityState poseableEntityState = entitySideDelegate instanceof PoseableEntityState ? (PoseableEntityState)((Object)entitySideDelegate) : null;
            if (poseableEntityState == null) {
                return;
            }
            PoseableEntityState state = poseableEntityState;
            Iterable $this$map$iv = packet.getExpressions();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(MoLangExtensionsKt.asExpression((String)it));
            }
            for (Expression expression : (List)destination$iv$iv) {
                MoLangRuntime moLangRuntime = state.getRuntime();
                Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
                MoLangExtensionsKt.resolve(moLangRuntime, expression);
            }
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull RunPosableMoLangPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

