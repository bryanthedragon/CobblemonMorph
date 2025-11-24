/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001d\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/mojang/serialization/Codec;", "Lorg/joml/Vector3f;", "VECTOR3F_CODEC", "Lcom/mojang/serialization/Codec;", "getVECTOR3F_CODEC", "()Lcom/mojang/serialization/Codec;", "common"})
public final class VecCodecsKt {
    @NotNull
    private static final Codec<Vector3f> VECTOR3F_CODEC;

    @NotNull
    public static final Codec<Vector3f> getVECTOR3F_CODEC() {
        return VECTOR3F_CODEC;
    }

    private static final Float VECTOR3F_CODEC$lambda$4$lambda$0(Vector3f it) {
        return Float.valueOf(it.x);
    }

    private static final Float VECTOR3F_CODEC$lambda$4$lambda$1(Vector3f it) {
        return Float.valueOf(it.y);
    }

    private static final Float VECTOR3F_CODEC$lambda$4$lambda$2(Vector3f it) {
        return Float.valueOf(it.z);
    }

    private static final Vector3f VECTOR3F_CODEC$lambda$4$lambda$3(Float x, Float y, Float z) {
        Intrinsics.checkNotNullExpressionValue((Object)x, (String)"x");
        float f = x.floatValue();
        Intrinsics.checkNotNullExpressionValue((Object)y, (String)"y");
        float f2 = y.floatValue();
        Intrinsics.checkNotNullExpressionValue((Object)z, (String)"z");
        return new Vector3f(f, f2, z.floatValue());
    }

    private static final App VECTOR3F_CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)Codec.FLOAT.fieldOf("x").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$0), (App)Codec.FLOAT.fieldOf("y").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$1), (App)Codec.FLOAT.fieldOf("z").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$2)).apply((Applicative)instance, VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$3);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(VecCodecsKt::VECTOR3F_CODEC$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"<clinit>");
        VECTOR3F_CODEC = codec2;
    }
}

