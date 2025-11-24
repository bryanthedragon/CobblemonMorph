/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix3f
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.DoubleRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.FloatRange;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000V\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a%\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\u001d\u0010\n\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004\u00a2\u0006\u0004\b\n\u0010\u000b\u001a\u0019\u0010\u000e\u001a\u00020\f*\u00020\f2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a\u0019\u0010\u0011\u001a\u00020\u0010*\u00020\f2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\u0013\u0010\u0013\u001a\u00020\u0000*\u0004\u0018\u00010\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a\u0013\u0010\u0013\u001a\u00020\u0015*\u0004\u0018\u00010\u0015\u00a2\u0006\u0004\b\u0013\u0010\u0016\u001a\u0013\u0010\u0013\u001a\u00020\u0017*\u0004\u0018\u00010\u0017\u00a2\u0006\u0004\b\u0013\u0010\u0018\u001a\u0013\u0010\u0019\u001a\u00020\u0000*\u0004\u0018\u00010\u0000\u00a2\u0006\u0004\b\u0019\u0010\u0014\u001a\u0013\u0010\u0019\u001a\u00020\u0015*\u0004\u0018\u00010\u0015\u00a2\u0006\u0004\b\u0019\u0010\u0016\u001a\u0013\u0010\u0019\u001a\u00020\u0017*\u0004\u0018\u00010\u0017\u00a2\u0006\u0004\b\u0019\u0010\u0018\u001a\u001c\u0010\u001b\u001a\u00020\u0017*\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0017H\u0086\u0004\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\u001d\u0010\u001e\u001a\u00020\u0015*\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u001d\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a!\u0010#\u001a\u00020\u0000*\u00020\u00002\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 \u00a2\u0006\u0004\b#\u0010$\u001a9\u0010#\u001a\u00020\u0000*\u00020\u00002\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u001d2\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u001d\u00a2\u0006\u0004\b#\u0010%\u001a!\u0010#\u001a\u00020\u0015*\u00020\u00152\u0006\u0010!\u001a\u00020&2\u0006\u0010\"\u001a\u00020&\u00a2\u0006\u0004\b#\u0010'\u001a9\u0010#\u001a\u00020\u0015*\u00020\u00152\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u001d2\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u001d\u00a2\u0006\u0004\b#\u0010(\u001a9\u0010#\u001a\u00020\u0017*\u00020\u00172\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u001d2\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u001d\u00a2\u0006\u0004\b#\u0010)\u001a!\u0010#\u001a\u00020\u0017*\u00020\u00172\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f\u00a2\u0006\u0004\b#\u0010*\u001a\u001c\u0010,\u001a\u00020\u0004*\u00020\t2\u0006\u0010+\u001a\u00020\u0004H\u0086\u0002\u00a2\u0006\u0004\b,\u0010-\u001a#\u0010/\u001a\u0014\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000.*\u00020\u0017\u00a2\u0006\u0004\b/\u00100\u00a8\u00061"}, d2={"", "radius", "theta", "psi", "Lnet/minecraft/world/phys/Vec3;", "convertSphericalToCartesian", "(DDD)Lnet/minecraft/world/phys/Vec3;", "from", "to", "Lorg/joml/Matrix3f;", "getRotationMatrix", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lorg/joml/Matrix3f;", "Lkotlin/ranges/IntRange;", "other", "intersection", "(Lkotlin/ranges/IntRange;Lkotlin/ranges/IntRange;)Lkotlin/ranges/IntRange;", "", "intersects", "(Lkotlin/ranges/IntRange;Lkotlin/ranges/IntRange;)Z", "orMax", "(Ljava/lang/Double;)D", "", "(Ljava/lang/Float;)F", "", "(Ljava/lang/Integer;)I", "orMin", "power", "pow", "(II)I", "Lkotlin/Pair;", "random", "(Lkotlin/Pair;)F", "Lcom/cobblemon/mod/common/util/math/DoubleRange;", "start", "end", "remap", "(DLcom/cobblemon/mod/common/util/math/DoubleRange;Lcom/cobblemon/mod/common/util/math/DoubleRange;)D", "(DLkotlin/Pair;Lkotlin/Pair;)D", "Lcom/cobblemon/mod/common/util/math/FloatRange;", "(FLcom/cobblemon/mod/common/util/math/FloatRange;Lcom/cobblemon/mod/common/util/math/FloatRange;)F", "(FLkotlin/Pair;Lkotlin/Pair;)F", "(ILkotlin/Pair;Lkotlin/Pair;)I", "(ILkotlin/ranges/IntRange;Lkotlin/ranges/IntRange;)I", "vec3d", "times", "(Lorg/joml/Matrix3f;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lkotlin/Triple;", "toRGB", "(I)Lkotlin/Triple;", "common"})
public final class SimpleMathExtensionsKt {
    public static final int pow(int $this$pow, int power) {
        return (int)Math.pow($this$pow, power);
    }

    public static final double orMax(@Nullable Double $this$orMax) {
        Double d = $this$orMax;
        return d != null ? d : Double.MAX_VALUE;
    }

    public static final double orMin(@Nullable Double $this$orMin) {
        Double d = $this$orMin;
        return d != null ? d : -2.147483648E9;
    }

    public static final float orMax(@Nullable Float $this$orMax) {
        Float f = $this$orMax;
        return f != null ? f.floatValue() : 2.14748365E9f;
    }

    public static final float orMin(@Nullable Float $this$orMin) {
        Float f = $this$orMin;
        return f != null ? f.floatValue() : -2.14748365E9f;
    }

    public static final int orMax(@Nullable Integer $this$orMax) {
        Integer n = $this$orMax;
        return n != null ? n : Integer.MAX_VALUE;
    }

    public static final int orMin(@Nullable Integer $this$orMin) {
        Integer n = $this$orMin;
        return n != null ? n : Integer.MIN_VALUE;
    }

    @NotNull
    public static final Triple<Double, Double, Double> toRGB(int $this$toRGB) {
        double r = (double)($this$toRGB >> 16 & 0xFF) / 255.0;
        double g = (double)($this$toRGB >> 8 & 0xFF) / 255.0;
        double b = (double)($this$toRGB & 0xFF) / 255.0;
        return new Triple((Object)r, (Object)g, (Object)b);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean intersects(@NotNull IntRange $this$intersects, @NotNull IntRange other) {
        boolean bl;
        boolean bl2;
        Intrinsics.checkNotNullParameter((Object)$this$intersects, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        int n = other.getFirst();
        int n2 = other.getLast();
        int n3 = $this$intersects.getStart();
        if (n <= n3) {
            if (n3 <= n2) {
                return true;
            }
            bl2 = false;
        } else {
            bl2 = false;
        }
        if (bl2) return true;
        n = other.getFirst();
        n2 = other.getLast();
        n3 = $this$intersects.getEndInclusive();
        if (n <= n3) {
            if (n3 <= n2) {
                return true;
            }
            bl = false;
        } else {
            bl = false;
        }
        if (bl) return true;
        n = $this$intersects.getFirst();
        n2 = $this$intersects.getLast();
        n3 = other.getStart();
        if (n > n3) return false;
        if (n3 > n2) return false;
        return true;
    }

    @NotNull
    public static final IntRange intersection(@NotNull IntRange $this$intersection, @NotNull IntRange other) {
        Intrinsics.checkNotNullParameter((Object)$this$intersection, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        int intersectionStart = Math.max(other.getStart(), $this$intersection.getStart());
        int intersectionEnd = Math.min(other.getEndInclusive(), $this$intersection.getEndInclusive());
        return new IntRange(intersectionStart, intersectionEnd);
    }

    public static final float random(@NotNull Pair<Float, Float> $this$random) {
        Intrinsics.checkNotNullParameter($this$random, (String)"<this>");
        return Random.Default.nextFloat() * (((Number)$this$random.getSecond()).floatValue() - ((Number)$this$random.getFirst()).floatValue()) + ((Number)$this$random.getFirst()).floatValue();
    }

    public static final float remap(float $this$remap, @NotNull Pair<Float, Float> from, @NotNull Pair<Float, Float> to) {
        Intrinsics.checkNotNullParameter(from, (String)"from");
        Intrinsics.checkNotNullParameter(to, (String)"to");
        float fromMin = ((Number)from.component1()).floatValue();
        float fromMax = ((Number)from.component2()).floatValue();
        float toMin = ((Number)to.component1()).floatValue();
        float toMax = ((Number)to.component2()).floatValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    public static final float remap(float $this$remap, @NotNull FloatRange start2, @NotNull FloatRange end2) {
        Intrinsics.checkNotNullParameter((Object)start2, (String)"start");
        Intrinsics.checkNotNullParameter((Object)end2, (String)"end");
        Pair pair = TuplesKt.to((Object)start2.getStart(), (Object)start2.getEndInclusive());
        float fromMin = ((Number)pair.component1()).floatValue();
        float fromMax = ((Number)pair.component2()).floatValue();
        Pair pair2 = TuplesKt.to((Object)end2.getStart(), (Object)end2.getEndInclusive());
        float toMin = ((Number)pair2.component1()).floatValue();
        float toMax = ((Number)pair2.component2()).floatValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    public static final int remap(int $this$remap, @NotNull Pair<Integer, Integer> from, @NotNull Pair<Integer, Integer> to) {
        Intrinsics.checkNotNullParameter(from, (String)"from");
        Intrinsics.checkNotNullParameter(to, (String)"to");
        int fromMin = ((Number)from.component1()).intValue();
        int fromMax = ((Number)from.component2()).intValue();
        int toMin = ((Number)to.component1()).intValue();
        int toMax = ((Number)to.component2()).intValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    public static final int remap(int $this$remap, @NotNull IntRange start2, @NotNull IntRange end2) {
        Intrinsics.checkNotNullParameter((Object)start2, (String)"start");
        Intrinsics.checkNotNullParameter((Object)end2, (String)"end");
        Pair pair = TuplesKt.to((Object)start2.getFirst(), (Object)start2.getLast());
        int fromMin = ((Number)pair.component1()).intValue();
        int fromMax = ((Number)pair.component2()).intValue();
        Pair pair2 = TuplesKt.to((Object)end2.getFirst(), (Object)end2.getLast());
        int toMin = ((Number)pair2.component1()).intValue();
        int toMax = ((Number)pair2.component2()).intValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    public static final double remap(double $this$remap, @NotNull Pair<Double, Double> from, @NotNull Pair<Double, Double> to) {
        Intrinsics.checkNotNullParameter(from, (String)"from");
        Intrinsics.checkNotNullParameter(to, (String)"to");
        double fromMin = ((Number)from.component1()).doubleValue();
        double fromMax = ((Number)from.component2()).doubleValue();
        double toMin = ((Number)to.component1()).doubleValue();
        double toMax = ((Number)to.component2()).doubleValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    public static final double remap(double $this$remap, @NotNull DoubleRange start2, @NotNull DoubleRange end2) {
        Intrinsics.checkNotNullParameter((Object)start2, (String)"start");
        Intrinsics.checkNotNullParameter((Object)end2, (String)"end");
        Pair pair = TuplesKt.to((Object)start2.getStart(), (Object)start2.getEndInclusive());
        double fromMin = ((Number)pair.component1()).doubleValue();
        double fromMax = ((Number)pair.component2()).doubleValue();
        Pair pair2 = TuplesKt.to((Object)end2.getStart(), (Object)end2.getEndInclusive());
        double toMin = ((Number)pair2.component1()).doubleValue();
        double toMax = ((Number)pair2.component2()).doubleValue();
        return ($this$remap - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }

    @NotNull
    public static final Vec3 convertSphericalToCartesian(double radius, double theta, double psi) {
        return new Vec3(radius * Math.cos(theta) * Math.sin(psi), radius * Math.sin(theta) * Math.sin(psi), radius * Math.cos(psi));
    }

    @NotNull
    public static final Matrix3f getRotationMatrix(@NotNull Vec3 from, @NotNull Vec3 to) {
        Intrinsics.checkNotNullParameter((Object)from, (String)"from");
        Intrinsics.checkNotNullParameter((Object)to, (String)"to");
        Quaternionf q = new Quaternionf(0.0, 0.0, 0.0, 1.0);
        q.rotateTo((Vector3fc)new Vector3f((float)from.f_82479_, (float)from.f_82480_, (float)from.f_82481_), (Vector3fc)new Vector3f((float)to.f_82479_, (float)to.f_82480_, (float)to.f_82481_));
        Matrix3f m = new Matrix3f();
        Matrix3f matrix3f = m.identity().rotation((Quaternionfc)q);
        Intrinsics.checkNotNullExpressionValue((Object)matrix3f, (String)"m.identity().rotation(q)");
        return matrix3f;
    }

    @NotNull
    public static final Vec3 times(@NotNull Matrix3f $this$times, @NotNull Vec3 vec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$times, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)vec3d, (String)"vec3d");
        Vector3f vec3f = new Vector3f();
        $this$times.transform((float)vec3d.f_82479_, (float)vec3d.f_82480_, (float)vec3d.f_82481_, vec3f);
        return new Vec3((double)vec3f.x, (double)vec3f.y, (double)vec3f.z);
    }
}

