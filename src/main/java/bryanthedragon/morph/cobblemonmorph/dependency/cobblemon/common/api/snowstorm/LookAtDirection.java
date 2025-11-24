/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.math.Axis
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.AxisAngle4d
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraModeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 42\u00020\u0001:\u00014B\u0007\u00a2\u0006\u0004\b2\u00103JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ_\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001dR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0017\u0010)\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b)\u0010&\u001a\u0004\b*\u0010(R\u001a\u0010,\u001a\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b0\u0010&\u001a\u0004\b1\u0010(\u00a8\u00065"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LookAtDirection;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "matrixWrapper", "", "prevAngle", "angle", "deltaTicks", "Lnet/minecraft/world/phys/Vec3;", "particlePosition", "cameraPosition", "Lorg/joml/Quaternionf;", "cameraAngle", "cameraYaw", "cameraPitch", "viewDirection", "getRotation", "(Lcom/cobblemon/mod/common/client/render/MatrixWrapper;FFFLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Quaternionf;FFLnet/minecraft/world/phys/Vec3;)Lorg/joml/Quaternionf;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lorg/joml/AxisAngle4d;", "axisAngle", "Lorg/joml/AxisAngle4d;", "getAxisAngle", "()Lorg/joml/AxisAngle4d;", "Lorg/joml/Vector3f;", "cameraPositionF", "Lorg/joml/Vector3f;", "getCameraPositionF", "()Lorg/joml/Vector3f;", "particlePositionF", "getParticlePositionF", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "viewDirectionF", "getViewDirectionF", "<init>", "()V", "Companion", "common"})
public final class LookAtDirection
implements ParticleCameraMode {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ParticleCameraModeType type = ParticleCameraModeType.LOOK_AT_DIRECTION;
    @NotNull
    private final Vector3f viewDirectionF = new Vector3f(0.0f, 0.0f, 0.0f);
    @NotNull
    private final Vector3f particlePositionF = new Vector3f(0.0f, 0.0f, 0.0f);
    @NotNull
    private final Vector3f cameraPositionF = new Vector3f(0.0f, 0.0f, 0.0f);
    @NotNull
    private final AxisAngle4d axisAngle = new AxisAngle4d();
    @NotNull
    private static final Codec<LookAtDirection> CODEC;

    @Override
    @NotNull
    public ParticleCameraModeType getType() {
        return this.type;
    }

    @NotNull
    public final Vector3f getViewDirectionF() {
        return this.viewDirectionF;
    }

    @NotNull
    public final Vector3f getParticlePositionF() {
        return this.particlePositionF;
    }

    @NotNull
    public final Vector3f getCameraPositionF() {
        return this.cameraPositionF;
    }

    @NotNull
    public final AxisAngle4d getAxisAngle() {
        return this.axisAngle;
    }

    @Override
    @NotNull
    public Quaternionf getRotation(@NotNull MatrixWrapper matrixWrapper, float prevAngle, float angle, float deltaTicks, @NotNull Vec3 particlePosition, @NotNull Vec3 cameraPosition, @NotNull Quaternionf cameraAngle, float cameraYaw, float cameraPitch, @NotNull Vec3 viewDirection) {
        Intrinsics.checkNotNullParameter((Object)matrixWrapper, (String)"matrixWrapper");
        Intrinsics.checkNotNullParameter((Object)particlePosition, (String)"particlePosition");
        Intrinsics.checkNotNullParameter((Object)cameraPosition, (String)"cameraPosition");
        Intrinsics.checkNotNullParameter((Object)cameraAngle, (String)"cameraAngle");
        Intrinsics.checkNotNullParameter((Object)viewDirection, (String)"viewDirection");
        Vec3ExtensionsKt.set(this.viewDirectionF, viewDirection);
        Vec3ExtensionsKt.set(this.particlePositionF, particlePosition);
        Vec3ExtensionsKt.set(this.cameraPositionF, cameraPosition);
        new Quaternionf().rotateTo((Vector3fc)this.particlePositionF.sub((Vector3fc)this.cameraPositionF, new Vector3f()), (Vector3fc)this.particlePositionF.add((Vector3fc)this.viewDirectionF, new Vector3f()).sub((Vector3fc)this.cameraPositionF)).get(this.axisAngle);
        Vector3f correctY = new Vector3f((float)this.axisAngle.x, (float)this.axisAngle.y, (float)this.axisAngle.z);
        Quaternionf rotation = new Quaternionf().rotateTo((Vector3fc)new Vector3f(1.0f, 0.0f, 0.0f), (Vector3fc)this.viewDirectionF);
        Vector3f currentY = new Vector3f(0.0f, 1.0f, 0.0f).rotate((Quaternionfc)rotation);
        rotation.premul((Quaternionfc)new Quaternionf().rotateTo((Vector3fc)currentY, (Vector3fc)correctY));
        float particleAngle = angle == 0.0f ? 0.0f : Mth.m_14179_((float)deltaTicks, (float)prevAngle, (float)angle);
        Intrinsics.checkNotNullExpressionValue((Object)rotation, (String)"rotation");
        Quaternionf quaternionf = Axis.f_252403_.m_252977_(particleAngle);
        Intrinsics.checkNotNullExpressionValue((Object)quaternionf, (String)"POSITIVE_Z.rotationDegrees(particleAngle)");
        QuaternionUtilsKt.hamiltonProduct(rotation, quaternionf);
        return rotation;
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    private static final String CODEC$lambda$2$lambda$0(LookAtDirection it) {
        return it.getType().name();
    }

    private static final LookAtDirection CODEC$lambda$2$lambda$1(String it) {
        return new LookAtDirection();
    }

    private static final App CODEC$lambda$2(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(LookAtDirection::CODEC$lambda$2$lambda$0)).apply((Applicative)instance, LookAtDirection::CODEC$lambda$2$lambda$1);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(LookAtDirection::CODEC$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026AtDirection() }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LookAtDirection$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/LookAtDirection;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<LookAtDirection> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

