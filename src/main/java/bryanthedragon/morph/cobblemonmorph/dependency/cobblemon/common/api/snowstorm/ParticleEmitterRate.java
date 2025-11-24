/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InstantParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRateType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SteadyParticleEmitterRate;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ'\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "started", "", "currentlyActive", "getEmitCount", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;ZI)I", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "type", "Companion", "common"})
public interface ParticleEmitterRate
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRate$Companion.$$INSTANCE;
    @NotNull
    public static final String OVERFLOW_VARIABLE = "emitter_overflow";

    @NotNull
    public ParticleEmitterRateType getType();

    public int getEmitCount(@NotNull MoLangRuntime var1, boolean var2, int var3);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\u0005\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "", "OVERFLOW_VARIABLE", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleEmitterRate, ParticleEmitterRateType> {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        public static final String OVERFLOW_VARIABLE = "emitter_overflow";

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
            $$INSTANCE.registerSubtype(ParticleEmitterRateType.INSTANT, InstantParticleEmitterRate.class, InstantParticleEmitterRate.Companion.getCODEC());
            $$INSTANCE.registerSubtype(ParticleEmitterRateType.STEADY, SteadyParticleEmitterRate.class, SteadyParticleEmitterRate.Companion.getCODEC());
        }
    }
}

