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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CustomViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.FromMotionViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirectionType;
import kotlin.Metadata;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ'\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\f\u001a\u00020\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "lastDirection", "currentVelocity", "getDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "type", "Companion", "common"})
public interface ParticleViewDirection
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection$Companion.$$INSTANCE;

    @NotNull
    public ParticleViewDirectionType getType();

    @NotNull
    public Vec3 getDirection(@NotNull MoLangRuntime var1, @NotNull Vec3 var2, @NotNull Vec3 var3);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection$Companion.$$INSTANCE.registerSubtype(ParticleViewDirectionType.CUSTOM, CustomViewDirection.class, CustomViewDirection.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection$Companion.$$INSTANCE.registerSubtype(ParticleViewDirectionType.FROM_MOTION, FromMotionViewDirection.class, FromMotionViewDirection.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleViewDirection, ParticleViewDirectionType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

