/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/effect/SpawnSnowstormEntityParticleHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormEntityParticlePacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormEntityParticlePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class SpawnSnowstormEntityParticleHandler
implements ClientNetworkPacketHandler<SpawnSnowstormEntityParticlePacket> {
    @NotNull
    public static final SpawnSnowstormEntityParticleHandler INSTANCE = new SpawnSnowstormEntityParticleHandler();

    private SpawnSnowstormEntityParticleHandler() {
    }

    @Override
    public void handle(@NotNull SpawnSnowstormEntityParticlePacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
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
        Entity entity2 = world.m_6815_(packet.getEntityId());
        Poseable poseable = entity2 instanceof Poseable ? (Poseable)entity2 : null;
        if (poseable == null) {
            return;
        }
        Poseable entity3 = poseable;
        Entity cfr_ignored_0 = (Entity)entity3;
        EntitySideDelegate<?> entitySideDelegate = entity3.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState<*>");
        PoseableEntityState state = (PoseableEntityState)((Object)entitySideDelegate);
        MatrixWrapper matrixWrapper = state.getLocatorStates().get(packet.getLocator());
        if (matrixWrapper == null) {
            MatrixWrapper matrixWrapper2 = state.getLocatorStates().get("root");
            Intrinsics.checkNotNull((Object)matrixWrapper2);
            matrixWrapper = matrixWrapper2;
        }
        MatrixWrapper matrixWrapper3 = matrixWrapper;
        MoLangRuntime particleRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
        MoLangEnvironment moLangEnvironment = particleRuntime.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"particleRuntime.environment");
        MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null).addFunction("entity", arg_0 -> SpawnSnowstormEntityParticleHandler.handle$lambda$0(state, arg_0));
        ParticleStorm storm2 = new ParticleStorm(effect, matrixWrapper3, world, (Function0)new Function0<Vec3>(entity3){
            final /* synthetic */ Poseable $entity;
            {
                this.$entity = $entity;
                super(0);
            }

            @NotNull
            public final Vec3 invoke() {
                Vec3 vec3 = ((Entity)this.$entity).m_20184_();
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.velocity");
                return vec3;
            }
        }, (Function0)new Function0<Boolean>(entity3){
            final /* synthetic */ Poseable $entity;
            {
                this.$entity = $entity;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !((Entity)this.$entity).m_213877_();
            }
        }, (Function0)new Function0<Boolean>(entity3){
            final /* synthetic */ Poseable $entity;
            {
                this.$entity = $entity;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !((Entity)this.$entity).m_20145_();
            }
        }, null, particleRuntime, (Entity)entity3, 64, null);
        storm2.spawn();
    }

    @Override
    public void handleOnNettyThread(@NotNull SpawnSnowstormEntityParticlePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }

    private static final Object handle$lambda$0(PoseableEntityState $state, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)$state, (String)"$state");
        MoLangEnvironment moLangEnvironment = $state.getRuntime().getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"state.runtime.environment");
        return MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null);
    }
}

