/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u0086\b\u0018\u0000 ,2\u00020\u0001:\u0001,B\t\b\u0016\u00a2\u0006\u0004\b%\u0010&B!\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020'\u0012\u0006\u0010\b\u001a\u00020'\u0012\u0006\u0010\t\u001a\u00020'\u00a2\u0006\u0004\b%\u0010(B\u0011\b\u0016\u0012\u0006\u0010)\u001a\u00020\u001c\u00a2\u0006\u0004\b%\u0010*B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u00a2\u0006\u0004\b%\u0010+J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0004J.\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0018\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0000H\u0086\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0002H\u0086\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u0019H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\r\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010 \u001a\u0004\b\"\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010 \u001a\u0004\b#\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010 \u001a\u0004\b$\u0010\u0004\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "", "", "component1", "()F", "component2", "component3", "x", "y", "z", "copy", "(FFF)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "right", "plus", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "scalar", "times", "(F)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/phys/Vec3;", "toVec3d", "()Lnet/minecraft/world/phys/Vec3;", "w", "F", "getW", "getX", "getY", "getZ", "<init>", "()V", "", "(DDD)V", "vec3d", "(Lnet/minecraft/world/phys/Vec3;)V", "(FFF)V", "Companion", "common"})
public final class GeometricPoint {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    public GeometricPoint(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0f;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final float getZ() {
        return this.z;
    }

    public final float getW() {
        return this.w;
    }

    @NotNull
    public final GeometricPoint plus(@NotNull GeometricPoint right) {
        Intrinsics.checkNotNullParameter((Object)right, (String)"right");
        return Companion.add(this, right);
    }

    @NotNull
    public final GeometricPoint times(float scalar) {
        return Companion.multiply(this, scalar);
    }

    @NotNull
    public final Vec3 toVec3d() {
        return new Vec3((double)this.x, (double)this.y, (double)this.z);
    }

    public GeometricPoint() {
        this(0.0f, 0.0f, 0.0f);
    }

    public GeometricPoint(double x, double y, double z) {
        this((float)x, (float)y, (float)z);
    }

    public GeometricPoint(@NotNull Vec3 vec3d) {
        Intrinsics.checkNotNullParameter((Object)vec3d, (String)"vec3d");
        this(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_);
    }

    public final float component1() {
        return this.x;
    }

    public final float component2() {
        return this.y;
    }

    public final float component3() {
        return this.z;
    }

    @NotNull
    public final GeometricPoint copy(float x, float y, float z) {
        return new GeometricPoint(x, y, z);
    }

    public static /* synthetic */ GeometricPoint copy$default(GeometricPoint geometricPoint, float f, float f2, float f3, int n, Object object) {
        if ((n & 1) != 0) {
            f = geometricPoint.x;
        }
        if ((n & 2) != 0) {
            f2 = geometricPoint.y;
        }
        if ((n & 4) != 0) {
            f3 = geometricPoint.z;
        }
        return geometricPoint.copy(f, f2, f3);
    }

    @NotNull
    public String toString() {
        return "GeometricPoint(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
    }

    public int hashCode() {
        int result = Float.hashCode(this.x);
        result = result * 31 + Float.hashCode(this.y);
        result = result * 31 + Float.hashCode(this.z);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeometricPoint)) {
            return false;
        }
        GeometricPoint geometricPoint = (GeometricPoint)other;
        if (Float.compare(this.x, geometricPoint.x) != 0) {
            return false;
        }
        if (Float.compare(this.y, geometricPoint.y) != 0) {
            return false;
        }
        return Float.compare(this.z, geometricPoint.z) == 0;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u001d\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\n\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint$Companion;", "", "Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "left", "right", "add", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "point", "", "scalar", "multiply", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;F)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final GeometricPoint add(@NotNull GeometricPoint left, @NotNull GeometricPoint right) {
            Intrinsics.checkNotNullParameter((Object)left, (String)"left");
            Intrinsics.checkNotNullParameter((Object)right, (String)"right");
            return new GeometricPoint(left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ());
        }

        @NotNull
        public final GeometricPoint multiply(@NotNull GeometricPoint point, float scalar) {
            Intrinsics.checkNotNullParameter((Object)point, (String)"point");
            return new GeometricPoint(point.getX() * scalar, point.getY() * scalar, point.getZ() * scalar);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

