/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Matrix4f
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u001a\u0019\u0010\u0005\u001a\u00020\u0001*\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\u0019\u0010\b\u001a\u00020\u0001*\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0001\u00a2\u0006\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, d2={"Lorg/joml/Matrix4f;", "Lnet/minecraft/world/phys/Vec3;", "getOrigin", "(Lorg/joml/Matrix4f;)Lnet/minecraft/world/phys/Vec3;", "direction", "transformDirection", "(Lorg/joml/Matrix4f;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "pos", "transformPosition", "common"})
public final class Matrix4fExtensionsKt {
    @NotNull
    public static final Vec3 getOrigin(@NotNull Matrix4f $this$getOrigin) {
        Intrinsics.checkNotNullParameter((Object)$this$getOrigin, (String)"<this>");
        Vec3 vec3 = Vec3.f_82478_;
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"ZERO");
        return Matrix4fExtensionsKt.transformPosition($this$getOrigin, vec3);
    }

    @NotNull
    public static final Vec3 transformPosition(@NotNull Matrix4f $this$transformPosition, @NotNull Vec3 pos) {
        Intrinsics.checkNotNullParameter((Object)$this$transformPosition, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Vector4f vector = new Vector4f((float)pos.f_82479_, (float)pos.f_82480_, (float)pos.f_82481_, 1.0f);
        $this$transformPosition.transform(vector);
        vector.mul(1.0f / vector.w);
        return new Vec3((double)vector.x, (double)vector.y, (double)vector.z);
    }

    @NotNull
    public static final Vec3 transformDirection(@NotNull Matrix4f $this$transformDirection, @NotNull Vec3 direction) {
        Intrinsics.checkNotNullParameter((Object)$this$transformDirection, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Vector4f origin = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        $this$transformDirection.transform(origin);
        origin.mul(1.0f / origin.w);
        Vec3 originVec = new Vec3((double)origin.x, (double)origin.y, (double)origin.z);
        double magnitude = direction.m_82553_();
        Vector4f vector = new Vector4f((float)direction.f_82479_, (float)direction.f_82480_, (float)direction.f_82481_, 1.0f);
        $this$transformDirection.transform(vector);
        vector.mul(1.0f / vector.w);
        Vec3 vec3 = new Vec3((double)vector.x, (double)vector.y, (double)vector.z).m_82546_(originVec).m_82541_().m_82490_(magnitude);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"Vec3d(vector.x.toDouble(\u2026     .multiply(magnitude)");
        return vec3;
    }
}

