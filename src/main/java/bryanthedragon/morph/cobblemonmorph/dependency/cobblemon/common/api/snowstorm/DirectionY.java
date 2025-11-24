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
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraModeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B\u0007\u00a2\u0006\u0004\b$\u0010%JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ_\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001dR\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DirectionY;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraMode;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "matrixWrapper", "", "prevAngle", "angle", "deltaTicks", "Lnet/minecraft/world/phys/Vec3;", "particlePosition", "cameraPosition", "Lorg/joml/Quaternionf;", "cameraAngle", "cameraYaw", "cameraPitch", "viewDirection", "getRotation", "(Lcom/cobblemon/mod/common/client/render/MatrixWrapper;FFFLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Quaternionf;FFLnet/minecraft/world/phys/Vec3;)Lorg/joml/Quaternionf;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleCameraModeType;", "<init>", "()V", "Companion", "common"})
public final class DirectionY
implements ParticleCameraMode {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ParticleCameraModeType type = ParticleCameraModeType.DIRECTION_Y;
    @NotNull
    private static final Codec<DirectionY> CODEC;

    @Override
    @NotNull
    public ParticleCameraModeType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Quaternionf getRotation(@NotNull MatrixWrapper matrixWrapper, float prevAngle, float angle, float deltaTicks, @NotNull Vec3 particlePosition, @NotNull Vec3 cameraPosition, @NotNull Quaternionf cameraAngle, float cameraYaw, float cameraPitch, @NotNull Vec3 viewDirection) {
        Intrinsics.checkNotNullParameter((Object)matrixWrapper, (String)"matrixWrapper");
        Intrinsics.checkNotNullParameter((Object)particlePosition, (String)"particlePosition");
        Intrinsics.checkNotNullParameter((Object)cameraPosition, (String)"cameraPosition");
        Intrinsics.checkNotNullParameter((Object)cameraAngle, (String)"cameraAngle");
        Intrinsics.checkNotNullParameter((Object)viewDirection, (String)"viewDirection");
        Quaternionf rotation = new Quaternionf(0.0f, 0.0f, 0.0f, 1.0f);
        double y = Math.atan2(viewDirection.f_82479_, viewDirection.f_82481_);
        double x = Math.atan2(viewDirection.f_82480_, Math.sqrt(Math.pow(viewDirection.f_82479_, 2.0) + Math.pow(viewDirection.f_82481_, 2.0)));
        Quaternionf quaternionf = Axis.f_252529_.m_252977_((float)x - 1.5707964f);
        Intrinsics.checkNotNullExpressionValue((Object)quaternionf, (String)"POSITIVE_X.rotationDegre\u2026loat() - PI.toFloat()/2f)");
        QuaternionUtilsKt.hamiltonProduct(rotation, quaternionf);
        Quaternionf quaternionf2 = Axis.f_252436_.m_252977_((float)y - (float)Math.PI);
        Intrinsics.checkNotNullExpressionValue((Object)quaternionf2, (String)"POSITIVE_Y.rotationDegre\u2026toFloat() - PI.toFloat())");
        QuaternionUtilsKt.hamiltonProduct(rotation, quaternionf2);
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

    private static final String CODEC$lambda$2$lambda$0(DirectionY it) {
        return it.getType().name();
    }

    private static final DirectionY CODEC$lambda$2$lambda$1(String it) {
        return new DirectionY();
    }

    private static final App CODEC$lambda$2(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(DirectionY::CODEC$lambda$2$lambda$0)).apply((Applicative)instance, DirectionY::CODEC$lambda$2$lambda$1);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(DirectionY::CODEC$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026 DirectionY() }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DirectionY$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/DirectionY;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<DirectionY> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

