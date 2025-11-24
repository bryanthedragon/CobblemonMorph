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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OnceEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetimeType;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u000e\u001a\u00020\u000b8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "started", "", "emitterAge", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterAction;", "getAction", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;ZD)Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterAction;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "type", "Companion", "common"})
public interface ParticleEmitterLifetime
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime$Companion.$$INSTANCE;

    @NotNull
    public ParticleEmitterLifetimeType getType();

    @NotNull
    public ParticleEmitterAction getAction(@NotNull MoLangRuntime var1, boolean var2, double var3);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime$Companion.$$INSTANCE.registerSubtype(ParticleEmitterLifetimeType.ONCE, OnceEmitterLifetime.class, OnceEmitterLifetime.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime$Companion.$$INSTANCE.registerSubtype(ParticleEmitterLifetimeType.EXPRESSION, ExpressionEmitterLifetime.class, ExpressionEmitterLifetime.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime$Companion.$$INSTANCE.registerSubtype(ParticleEmitterLifetimeType.LOOPING, LoopingEmitterLifetime.class, LoopingEmitterLifetime.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleEmitterLifetime, ParticleEmitterLifetimeType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

