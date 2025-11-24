/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleOptions$Deserializer
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.CobblemonParticles;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u000f\u0012\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "Lnet/minecraft/core/particles/ParticleOptions;", "", "asString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "getType", "()Lcom/cobblemon/mod/common/particle/SnowstormParticleType;", "Lnet/minecraft/network/FriendlyByteBuf;", "buf", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "effect", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "getEffect", "()Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "<init>", "(Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;)V", "Companion", "common"})
public final class SnowstormParticleEffect
implements ParticleOptions {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BedrockParticleEffect effect;
    @NotNull
    private static final ParticleOptions.Deserializer<SnowstormParticleEffect> PARAMETERS_FACTORY = (ParticleOptions.Deserializer)new ParticleOptions.Deserializer<SnowstormParticleEffect>(){

        @NotNull
        public SnowstormParticleEffect read(@NotNull ParticleType<SnowstormParticleEffect> particleType, @NotNull StringReader stringReader) throws CommandSyntaxException {
            Intrinsics.checkNotNullParameter(particleType, (String)"particleType");
            Intrinsics.checkNotNullParameter((Object)stringReader, (String)"stringReader");
            stringReader.expect(' ');
            return new SnowstormParticleEffect(new BedrockParticleEffect(null, null, null, null, null, null, 63, null));
        }

        @NotNull
        public SnowstormParticleEffect read(@NotNull ParticleType<SnowstormParticleEffect> particleType, @NotNull FriendlyByteBuf packetByteBuf) {
            BedrockParticleEffect bedrockParticleEffect;
            Intrinsics.checkNotNullParameter(particleType, (String)"particleType");
            Intrinsics.checkNotNullParameter((Object)packetByteBuf, (String)"packetByteBuf");
            BedrockParticleEffect it = bedrockParticleEffect = new BedrockParticleEffect(null, null, null, null, null, null, 63, null);
            boolean bl = false;
            it.readFromBuffer(packetByteBuf);
            BedrockParticleEffect bedrockParticleEffect2 = bedrockParticleEffect;
            return new SnowstormParticleEffect(bedrockParticleEffect2);
        }
    };

    public SnowstormParticleEffect(@NotNull BedrockParticleEffect effect) {
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        this.effect = effect;
    }

    @NotNull
    public final BedrockParticleEffect getEffect() {
        return this.effect;
    }

    @NotNull
    public SnowstormParticleType getType() {
        return CobblemonParticles.INSTANCE.getSNOWSTORM_PARTICLE_TYPE();
    }

    public void m_7711_(@NotNull FriendlyByteBuf buf) {
        Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
        this.effect.writeToBuffer(buf);
    }

    @NotNull
    public String m_5942_() {
        return "";
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect$Companion;", "", "Lnet/minecraft/particle/ParticleEffect$Factory;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "PARAMETERS_FACTORY", "Lnet/minecraft/core/particles/ParticleOptions$Deserializer;", "getPARAMETERS_FACTORY", "()Lnet/minecraft/core/particles/ParticleOptions$Deserializer;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ParticleOptions.Deserializer<SnowstormParticleEffect> getPARAMETERS_FACTORY() {
            return PARAMETERS_FACTORY;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

