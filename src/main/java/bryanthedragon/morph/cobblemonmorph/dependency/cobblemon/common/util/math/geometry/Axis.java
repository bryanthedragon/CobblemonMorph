/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.ImmutableArray;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.TransformationMatrix;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B!\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\f\u0010\nR\u0017\u0010\r\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000e\u0010\nj\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/util/math/geometry/Axis;", "", "", "angle", "Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "getRotationMatrix", "(F)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "x", "F", "getX", "()F", "y", "getY", "z", "getZ", "<init>", "(Ljava/lang/String;IFFF)V", "X_AXIS", "Y_AXIS", "Z_AXIS", "common"})
@SourceDebugExtension(value={"SMAP\nAxis.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Axis.kt\ncom/cobblemon/mod/common/util/math/geometry/Axis\n+ 2 ImmutableArray.kt\ncom/cobblemon/mod/common/util/collections/ImmutableArrayKt\n*L\n1#1,59:1\n16#2:60\n16#2:61\n16#2:62\n16#2:63\n16#2:64\n16#2:65\n16#2:66\n16#2:67\n16#2:68\n16#2:69\n16#2:70\n16#2:71\n16#2:72\n16#2:73\n16#2:74\n*S KotlinDebug\n*F\n+ 1 Axis.kt\ncom/cobblemon/mod/common/util/math/geometry/Axis\n*L\n35#1:60\n36#1:61\n37#1:62\n38#1:63\n34#1:64\n43#1:65\n44#1:66\n45#1:67\n46#1:68\n42#1:69\n51#1:70\n52#1:71\n53#1:72\n54#1:73\n50#1:74\n*E\n"})
public final class Axis
extends Enum<Axis> {
    private final float x;
    private final float y;
    private final float z;
    public static final /* enum */ Axis X_AXIS = new Axis(1.0f, 0.0f, 0.0f);
    public static final /* enum */ Axis Y_AXIS = new Axis(0.0f, 1.0f, 0.0f);
    public static final /* enum */ Axis Z_AXIS = new Axis(0.0f, 0.0f, 1.0f);
    private static final /* synthetic */ Axis[] $VALUES;

    private Axis(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    @NotNull
    public final TransformationMatrix getRotationMatrix(float angle) {
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1 -> {
                Float[] values$iv;
                ImmutableArray[] var2_2 = new ImmutableArray[4];
                Float[] var3_5 = new Float[]{Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                boolean $i$f$immutableArrayOf = false;
                var2_2[0] = new ImmutableArray(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf((float)Math.cos(angle)), Float.valueOf(-((float)Math.sin(angle))), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                var2_2[1] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf((float)Math.sin(angle)), Float.valueOf((float)Math.cos(angle)), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                var2_2[2] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)};
                $i$f$immutableArrayOf = false;
                var2_2[3] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                ImmutableArray[] values$iv = var2_2;
                boolean $i$f$immutableArrayOf = false;
                TransformationMatrix v0 = new TransformationMatrix(new ImmutableArray<ImmutableArray<Float>>(Arrays.copyOf(values$iv, values$iv.length)));
                yield v0;
            }
            case 2 -> {
                Float[] values$iv;
                ImmutableArray[] values$iv = new ImmutableArray[4];
                Float[] $i$f$immutableArrayOf = new Float[]{Float.valueOf((float)Math.cos(angle)), Float.valueOf(0.0f), Float.valueOf((float)Math.sin(angle)), Float.valueOf(0.0f)};
                boolean $i$f$immutableArrayOf = false;
                values$iv[0] = new ImmutableArray(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[1] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(-((float)Math.sin(angle))), Float.valueOf(0.0f), Float.valueOf((float)Math.cos(angle)), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[2] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[3] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                boolean $i$f$immutableArrayOf2 = false;
                TransformationMatrix v2 = new TransformationMatrix(new ImmutableArray<ImmutableArray<Float>>(Arrays.copyOf(values$iv, values$iv.length)));
                yield v2;
            }
            case 3 -> {
                Float[] values$iv;
                ImmutableArray[] values$iv = new ImmutableArray[4];
                Float[] $i$f$immutableArrayOf2 = new Float[]{Float.valueOf((float)Math.cos(angle)), Float.valueOf(-((float)Math.sin(angle))), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                boolean $i$f$immutableArrayOf = false;
                values$iv[0] = new ImmutableArray(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf((float)Math.sin(angle)), Float.valueOf((float)Math.cos(angle)), Float.valueOf(0.0f), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[1] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[2] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                values$iv = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)};
                $i$f$immutableArrayOf = false;
                values$iv[3] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
                boolean $i$f$immutableArrayOf3 = false;
                TransformationMatrix v3 = new TransformationMatrix(new ImmutableArray<ImmutableArray<Float>>(Arrays.copyOf(values$iv, values$iv.length)));
                yield v3;
            }
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    public static /* synthetic */ TransformationMatrix getRotationMatrix$default(Axis axis, float f, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getRotationMatrix");
        }
        if ((n & 1) != 0) {
            f = 0.0f;
        }
        return axis.getRotationMatrix(f);
    }

    public static Axis[] values() {
        return (Axis[])$VALUES.clone();
    }

    public static Axis valueOf(String value2) {
        return Enum.valueOf(Axis.class, value2);
    }

    static {
        $VALUES = axisArray = new Axis[]{Axis.X_AXIS, Axis.Y_AXIS, Axis.Z_AXIS};
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Axis.values().length];
            try {
                nArray[Axis.X_AXIS.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Axis.Y_AXIS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Axis.Z_AXIS.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

