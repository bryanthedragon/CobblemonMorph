/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleEffect;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\b\tB\u0007\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "Lnet/minecraft/core/particles/ParticleType;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "Lcom/mojang/serialization/Codec;", "getCodec", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "Companion", "Factory", "common"})
public final class SnowstormParticleType
extends ParticleType<SnowstormParticleEffect> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Codec<SnowstormParticleEffect> CODEC;

    public SnowstormParticleType() {
        super(true, SnowstormParticleEffect.Companion.getPARAMETERS_FACTORY());
    }

    @NotNull
    public Codec<SnowstormParticleEffect> m_7652_() {
        return CODEC;
    }

    private static final BedrockParticleEffect CODEC$lambda$1$lambda$0(SnowstormParticleEffect it) {
        return it.getEffect();
    }

    private static final App CODEC$lambda$1(RecordCodecBuilder.Instance instance) {
        return instance.group((App)BedrockParticleEffect.Companion.getCODEC().fieldOf("effect").forGetter(SnowstormParticleType::CODEC$lambda$1$lambda$0)).apply((Applicative)instance, SnowstormParticleEffect::new);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(SnowstormParticleType::CODEC$lambda$1);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ParticleEffect)\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleType$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<SnowstormParticleEffect> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016JO\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleType$Factory;", "Lnet/minecraft/client/particle/ParticleProvider;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "parameters", "Lnet/minecraft/client/multiplayer/ClientLevel;", "world", "", "x", "y", "z", "velocityX", "velocityY", "velocityZ", "Lnet/minecraft/client/particle/Particle;", "createParticle", "(Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;", "Lnet/minecraft/client/particle/SpriteSet;", "spriteProvider", "Lnet/minecraft/client/particle/SpriteSet;", "getSpriteProvider", "()Lnet/minecraft/client/particle/SpriteSet;", "<init>", "(Lnet/minecraft/client/particle/SpriteSet;)V", "common"})
    public static final class Factory
    implements ParticleProvider<SnowstormParticleEffect> {
        @NotNull
        private final SpriteSet spriteProvider;

        public Factory(@NotNull SpriteSet spriteProvider) {
            Intrinsics.checkNotNullParameter((Object)spriteProvider, (String)"spriteProvider");
            this.spriteProvider = spriteProvider;
        }

        @NotNull
        public final SpriteSet getSpriteProvider() {
            return this.spriteProvider;
        }

        @NotNull
        public Particle createParticle(@NotNull SnowstormParticleEffect parameters, @NotNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Intrinsics.checkNotNullParameter((Object)parameters, (String)"parameters");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            ParticleStorm particleStorm = ParticleStorm.Companion.getContextStorm();
            Intrinsics.checkNotNull((Object)((Object)particleStorm));
            return new SnowstormParticle(particleStorm, world, x, y, z, new Vec3(velocityX, velocityY, velocityZ), false);
        }
    }
}

