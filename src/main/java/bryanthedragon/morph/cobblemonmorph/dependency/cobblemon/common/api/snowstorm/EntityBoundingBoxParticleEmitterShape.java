/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShapeType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 '2\u00020\u0001:\u0001'B\u0011\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b&\u0010 JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\f\u001a\n \u0006*\u0004\u0018\u00010\u000b0\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0004\b\f\u0010\rJ)\u0010\u0011\u001a\n \u0006*\u0004\u0018\u00010\u00100\u00102\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u0018R\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\"\u001a\u00020!8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EntityBoundingBoxParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/phys/AABB;", "getBox", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/AABB;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/Vec3;", "getNewParticlePosition", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "surfaceOnly", "Z", "getSurfaceOnly", "()Z", "setSurfaceOnly", "(Z)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "<init>", "Companion", "common"})
public final class EntityBoundingBoxParticleEmitterShape
implements ParticleEmitterShape {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private boolean surfaceOnly;
    @NotNull
    private final ParticleEmitterShapeType type;
    @NotNull
    private static final Codec<EntityBoundingBoxParticleEmitterShape> CODEC;

    public EntityBoundingBoxParticleEmitterShape(boolean surfaceOnly) {
        this.surfaceOnly = surfaceOnly;
        this.type = ParticleEmitterShapeType.ENTITY_BOUNDING_BOX;
    }

    public /* synthetic */ EntityBoundingBoxParticleEmitterShape(boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = true;
        }
        this(bl);
    }

    public final boolean getSurfaceOnly() {
        return this.surfaceOnly;
    }

    public final void setSurfaceOnly(boolean bl) {
        this.surfaceOnly = bl;
    }

    @Override
    @NotNull
    public ParticleEmitterShapeType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Vec3 getNewParticlePosition(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Vec3 vec3;
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        AABB box = this.getBox(entity2);
        Vec3 center = this.getCenter(runtime2, entity2);
        Vec3 sizes = new Vec3(box.f_82291_ - box.f_82288_, box.f_82292_ - box.f_82289_, box.f_82293_ - box.f_82290_);
        if (this.surfaceOnly) {
            switch (Random.Default.nextInt(6)) {
                case 0: {
                    vec3 = new Vec3(-0.5 * sizes.f_82479_, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 1: {
                    vec3 = new Vec3(0.5 * sizes.f_82479_, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 2: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, -0.5 * sizes.f_82480_, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 3: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, 0.5 * sizes.f_82480_, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 4: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, -0.5 * sizes.f_82481_);
                    break;
                }
                default: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, 0.5 * sizes.f_82481_);
                    break;
                }
            }
        } else {
            vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / (double)2, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / (double)2, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / (double)2);
        }
        Vec3 disposition = vec3;
        Vec3 vec32 = center.m_82549_(disposition);
        Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"center.add(disposition)");
        return vec32;
    }

    @Override
    public Vec3 getCenter(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return this.getBox(entity2).m_82399_();
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    public final AABB getBox(@Nullable Entity entity2) {
        Entity entity3 = entity2;
        if (entity3 == null || (entity3 = entity3.m_20191_()) == null) {
            entity3 = AABB.m_165882_((Vec3)new Vec3(0.0, 0.0, 0.0), (double)1.0, (double)2.0, (double)1.0);
        }
        return entity3;
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.surfaceOnly = buffer.readBoolean();
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.surfaceOnly);
    }

    private static final String CODEC$lambda$3$lambda$0(EntityBoundingBoxParticleEmitterShape it) {
        return it.getType().name();
    }

    private static final Boolean CODEC$lambda$3$lambda$1(EntityBoundingBoxParticleEmitterShape it) {
        return it.surfaceOnly;
    }

    private static final EntityBoundingBoxParticleEmitterShape CODEC$lambda$3$lambda$2(String string, Boolean surfaceOnly) {
        Intrinsics.checkNotNullExpressionValue((Object)surfaceOnly, (String)"surfaceOnly");
        return new EntityBoundingBoxParticleEmitterShape(surfaceOnly);
    }

    private static final App CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$0), (App)PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$1)).apply((Applicative)instance, EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$2);
    }

    public EntityBoundingBoxParticleEmitterShape() {
        this(false, 1, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026= surfaceOnly)}\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EntityBoundingBoxParticleEmitterShape$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/EntityBoundingBoxParticleEmitterShape;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<EntityBoundingBoxParticleEmitterShape> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

