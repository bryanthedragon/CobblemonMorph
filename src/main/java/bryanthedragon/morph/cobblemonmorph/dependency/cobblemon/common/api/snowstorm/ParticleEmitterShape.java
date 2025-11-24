/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BoxParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DiscParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EntityBoundingBoxParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShapeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.PointParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SphereParticleEmitterShape;
import kotlin.Metadata;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ!\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ!\u0010\t\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H&\u00a2\u0006\u0004\b\t\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/Vec3;", "getNewParticlePosition", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "type", "Companion", "common"})
public interface ParticleEmitterShape
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE;

    @NotNull
    public ParticleEmitterShapeType getType();

    @NotNull
    public Vec3 getNewParticlePosition(@NotNull MoLangRuntime var1, @Nullable Entity var2);

    @NotNull
    public Vec3 getCenter(@NotNull MoLangRuntime var1, @Nullable Entity var2);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE.registerSubtype(ParticleEmitterShapeType.SPHERE, SphereParticleEmitterShape.class, SphereParticleEmitterShape.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE.registerSubtype(ParticleEmitterShapeType.POINT, PointParticleEmitterShape.class, PointParticleEmitterShape.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE.registerSubtype(ParticleEmitterShapeType.BOX, BoxParticleEmitterShape.class, BoxParticleEmitterShape.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE.registerSubtype(ParticleEmitterShapeType.DISC, DiscParticleEmitterShape.class, DiscParticleEmitterShape.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape$Companion.$$INSTANCE.registerSubtype(ParticleEmitterShapeType.ENTITY_BOUNDING_BOX, EntityBoundingBoxParticleEmitterShape.class, EntityBoundingBoxParticleEmitterShape.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleEmitterShape, ParticleEmitterShapeType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

