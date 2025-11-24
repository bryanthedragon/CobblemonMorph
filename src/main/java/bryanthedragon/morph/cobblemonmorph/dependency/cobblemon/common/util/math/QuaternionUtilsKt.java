/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a)\u0010\u0005\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\u0019\u0010\t\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\n\u001a\u0019\u0010\f\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0000\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"Lorg/joml/Quaternionf;", "", "x", "y", "z", "fromEulerXYZ", "(Lorg/joml/Quaternionf;FFF)Lorg/joml/Quaternionf;", "Lorg/joml/Vector3f;", "vector", "fromEulerXYZDegrees", "(Lorg/joml/Quaternionf;Lorg/joml/Vector3f;)Lorg/joml/Quaternionf;", "other", "hamiltonProduct", "(Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;)Lorg/joml/Quaternionf;", "common"})
public final class QuaternionUtilsKt {
    @NotNull
    public static final Quaternionf fromEulerXYZDegrees(@NotNull Quaternionf $this$fromEulerXYZDegrees, @NotNull Vector3f vector) {
        Intrinsics.checkNotNullParameter((Object)$this$fromEulerXYZDegrees, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)vector, (String)"vector");
        return QuaternionUtilsKt.fromEulerXYZ($this$fromEulerXYZDegrees, (float)Math.toRadians(vector.x), (float)Math.toRadians(vector.y), (float)Math.toRadians(vector.z));
    }

    @NotNull
    public static final Quaternionf fromEulerXYZ(@NotNull Quaternionf $this$fromEulerXYZ, float x, float y, float z) {
        Intrinsics.checkNotNullParameter((Object)$this$fromEulerXYZ, (String)"<this>");
        QuaternionUtilsKt.hamiltonProduct($this$fromEulerXYZ, new Quaternionf((float)Math.sin(x / 2.0f), 0.0f, 0.0f, (float)Math.cos(x / 2.0f)));
        QuaternionUtilsKt.hamiltonProduct($this$fromEulerXYZ, new Quaternionf(0.0f, (float)Math.sin(y / 2.0f), 0.0f, (float)Math.cos(y / 2.0f)));
        QuaternionUtilsKt.hamiltonProduct($this$fromEulerXYZ, new Quaternionf(0.0f, 0.0f, (float)Math.sin(z / 2.0f), (float)Math.cos(z / 2.0f)));
        return $this$fromEulerXYZ;
    }

    @NotNull
    public static final Quaternionf hamiltonProduct(@NotNull Quaternionf $this$hamiltonProduct, @NotNull Quaternionf other) {
        Intrinsics.checkNotNullParameter((Object)$this$hamiltonProduct, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        float f = $this$hamiltonProduct.x;
        float g = $this$hamiltonProduct.y;
        float h = $this$hamiltonProduct.z;
        float i = $this$hamiltonProduct.w;
        float j = other.x;
        float k = other.y;
        float l = other.z;
        float m = other.w;
        $this$hamiltonProduct.x = i * j + f * m + g * l - h * k;
        $this$hamiltonProduct.y = i * k - f * l + g * m + h * j;
        $this$hamiltonProduct.z = i * l + f * k - g * j + h * m;
        $this$hamiltonProduct.w = i * m - f * j - g * k - h * l;
        return $this$hamiltonProduct;
    }
}

