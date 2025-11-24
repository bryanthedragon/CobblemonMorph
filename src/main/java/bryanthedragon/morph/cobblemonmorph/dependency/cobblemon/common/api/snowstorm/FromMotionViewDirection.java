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
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirectionType;
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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0011\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\"\u0010\u001cJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0014R\"\u0010\u0017\u001a\u00020\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001e\u001a\u00020\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/FromMotionViewDirection;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirection;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "lastDirection", "currentVelocity", "getDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "minSpeed", "D", "getMinSpeed", "()D", "setMinSpeed", "(D)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleViewDirectionType;", "<init>", "Companion", "common"})
public final class FromMotionViewDirection
implements ParticleViewDirection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private double minSpeed;
    @NotNull
    private final ParticleViewDirectionType type;
    @NotNull
    private static final Codec<FromMotionViewDirection> CODEC;

    public FromMotionViewDirection(double minSpeed) {
        this.minSpeed = minSpeed;
        this.type = ParticleViewDirectionType.FROM_MOTION;
    }

    public /* synthetic */ FromMotionViewDirection(double d, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            d = 0.01;
        }
        this(d);
    }

    public final double getMinSpeed() {
        return this.minSpeed;
    }

    public final void setMinSpeed(double d) {
        this.minSpeed = d;
    }

    @Override
    @NotNull
    public ParticleViewDirectionType getType() {
        return this.type;
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeDouble(this.minSpeed);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.minSpeed = buffer.readDouble();
    }

    @Override
    @NotNull
    public Vec3 getDirection(@NotNull MoLangRuntime runtime2, @NotNull Vec3 lastDirection, @NotNull Vec3 currentVelocity) {
        Vec3 vec3;
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)lastDirection, (String)"lastDirection");
        Intrinsics.checkNotNullParameter((Object)currentVelocity, (String)"currentVelocity");
        if (currentVelocity.m_82553_() * (double)20 >= this.minSpeed) {
            Vec3 vec32 = currentVelocity.m_82541_();
            vec3 = vec32;
            Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"{\n            currentVel\u2026ity.normalize()\n        }");
        } else {
            vec3 = lastDirection;
        }
        return vec3;
    }

    private static final String CODEC$lambda$3$lambda$0(FromMotionViewDirection it) {
        return it.getType().name();
    }

    private static final Double CODEC$lambda$3$lambda$1(FromMotionViewDirection it) {
        return it.minSpeed;
    }

    private static final FromMotionViewDirection CODEC$lambda$3$lambda$2(String string, Double minSpeed) {
        Intrinsics.checkNotNullExpressionValue((Object)minSpeed, (String)"minSpeed");
        return new FromMotionViewDirection(minSpeed);
    }

    private static final App CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(FromMotionViewDirection::CODEC$lambda$3$lambda$0), (App)PrimitiveCodec.DOUBLE.fieldOf("minSpeed").forGetter(FromMotionViewDirection::CODEC$lambda$3$lambda$1)).apply((Applicative)instance, FromMotionViewDirection::CODEC$lambda$3$lambda$2);
    }

    public FromMotionViewDirection() {
        this(0.0, 1, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(FromMotionViewDirection::CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ion(minSpeed) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/FromMotionViewDirection$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/FromMotionViewDirection;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<FromMotionViewDirection> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

