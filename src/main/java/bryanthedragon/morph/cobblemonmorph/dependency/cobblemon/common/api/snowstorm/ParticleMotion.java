/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParametricParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import kotlin.Metadata;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018J/\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\t\u0010\nJ/\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\fH&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0017\u001a\u00020\u00148&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lnet/minecraft/world/phys/Vec3;", "particlePos", "emitterPos", "getInitialVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "velocity", "", "minSpeed", "getParticleDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "getVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "type", "Companion", "common"})
public interface ParticleMotion
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion$Companion.$$INSTANCE;

    @NotNull
    public ParticleMotionType getType();

    @NotNull
    public Vec3 getInitialVelocity(@NotNull MoLangRuntime var1, @NotNull ParticleStorm var2, @NotNull Vec3 var3, @NotNull Vec3 var4);

    @NotNull
    public Vec3 getVelocity(@NotNull MoLangRuntime var1, @NotNull SnowstormParticle var2, @NotNull Vec3 var3);

    @NotNull
    public Vec3 getParticleDirection(@NotNull MoLangRuntime var1, @NotNull ParticleStorm var2, @NotNull Vec3 var3, float var4);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion$Companion.$$INSTANCE.registerSubtype(ParticleMotionType.DYNAMIC, DynamicParticleMotion.class, DynamicParticleMotion.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion$Companion.$$INSTANCE.registerSubtype(ParticleMotionType.STATIC, StaticParticleMotion.class, StaticParticleMotion.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion$Companion.$$INSTANCE.registerSubtype(ParticleMotionType.PARAMETRIC, ParametricParticleMotion.class, ParametricParticleMotion.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleMotion, ParticleMotionType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

