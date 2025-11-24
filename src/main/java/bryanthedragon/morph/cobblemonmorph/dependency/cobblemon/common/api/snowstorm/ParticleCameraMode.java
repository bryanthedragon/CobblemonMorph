/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionX;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionY;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionZ;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXYPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXZPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterYZPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtXYZ;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtY;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraModeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateXYZCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateYCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import kotlin.Metadata;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J_\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bH&\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u00128&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "matrixWrapper", "", "prevAngle", "angle", "deltaTicks", "Lnet/minecraft/world/phys/Vec3;", "particlePosition", "cameraPosition", "Lorg/joml/Quaternionf;", "cameraAngle", "cameraYaw", "cameraPitch", "viewDirection", "getRotation", "(Lcom/cobblemon/mod/common/client/render/MatrixWrapper;FFFLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Quaternionf;FFLnet/minecraft/world/phys/Vec3;)Lorg/joml/Quaternionf;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "type", "Companion", "common"})
public interface ParticleCameraMode
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE;

    @NotNull
    public ParticleCameraModeType getType();

    @NotNull
    public Quaternionf getRotation(@NotNull MatrixWrapper var1, float var2, float var3, float var4, @NotNull Vec3 var5, @NotNull Vec3 var6, @NotNull Quaternionf var7, float var8, float var9, @NotNull Vec3 var10);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.ROTATE_XYZ, RotateXYZCameraMode.class, RotateXYZCameraMode.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.ROTATE_Y, RotateYCameraMode.class, RotateYCameraMode.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.LOOK_AT_XYZ, LookAtXYZ.class, LookAtXYZ.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.LOOK_AT_Y, LookAtY.class, LookAtY.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.DIRECTION_X, DirectionX.class, DirectionX.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.DIRECTION_Y, DirectionY.class, DirectionY.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.DIRECTION_Z, DirectionZ.class, DirectionZ.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.LOOK_AT_DIRECTION, LookAtDirection.class, LookAtDirection.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.EMITTER_XZ_PLANE, EmitterXZPlane.class, EmitterXZPlane.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.EMITTER_XY_PLANE, EmitterXYPlane.class, EmitterXYPlane.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode$Companion.$$INSTANCE.registerSubtype(ParticleCameraModeType.EMITTER_YZ_PLANE, EmitterYZPlane.class, EmitterYZPlane.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleCameraMode, ParticleCameraModeType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }
}

