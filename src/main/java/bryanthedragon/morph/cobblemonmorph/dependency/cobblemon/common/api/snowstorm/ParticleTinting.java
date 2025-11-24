/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.GradientParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTintingType;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00078&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lorg/joml/Vector4f;", "getTint", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lorg/joml/Vector4f;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "type", "Companion", "common"})
public interface ParticleTinting
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting$Companion.$$INSTANCE;

    @NotNull
    public ParticleTintingType getType();

    @NotNull
    public Vector4f getTint(@NotNull MoLangRuntime var1);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting$Companion.$$INSTANCE.registerSubtype(ParticleTintingType.EXPRESSION, ExpressionParticleTinting.class, ExpressionParticleTinting.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting$Companion.$$INSTANCE.registerSubtype(ParticleTintingType.GRADIENT, GradientParticleTinting.class, GradientParticleTinting.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleTinting, ParticleTintingType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

