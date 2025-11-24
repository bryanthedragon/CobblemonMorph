/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin._Assertions
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.ImmutableArray;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Axis;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.GeometricNormal;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.GeometricPoint;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\b\u0018\u0000 %2\u00020\u0001:\u0001%B\u001d\b\u0000\u0012\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0002\u00a2\u0006\u0004\b#\u0010$J\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J&\u0010\u0007\u001a\u00020\u00002\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u000e\u001a\u00020\rH\u0086\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0018\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0086\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\u0015\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0086\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0019J\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u0000H\u0086\u0002\u00a2\u0006\u0004\b\u0015\u0010\u001bJ\u0018\u0010\u0015\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u001cH\u0086\u0002\u00a2\u0006\u0004\b\u0015\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b\u001f\u0010 R#\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010!\u001a\u0004\b\"\u0010\u0005\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "", "Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "", "component1", "()Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "values", "copy", "(Lcom/cobblemon/mod/common/util/collections/ImmutableArray;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "row", "get", "(I)Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "hashCode", "()I", "Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;", "normal", "times", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;", "Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "point", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "right", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "Lnet/minecraft/world/phys/Vec3;", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "getValues", "<init>", "(Lcom/cobblemon/mod/common/util/collections/ImmutableArray;)V", "Companion", "common"})
public final class TransformationMatrix {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ImmutableArray<ImmutableArray<Float>> values;
    @NotNull
    private static final TransformationMatrix identityMatrix = new TransformationMatrix(bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.TransformationMatrix$Companion.access$toImmutable(Companion, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.TransformationMatrix$Companion.access$identityArray(Companion)));

    public TransformationMatrix(@NotNull ImmutableArray<ImmutableArray<Float>> values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        this.values = values;
    }

    @NotNull
    public final ImmutableArray<ImmutableArray<Float>> getValues() {
        return this.values;
    }

    @NotNull
    public final ImmutableArray<Float> get(int row) {
        return this.values.get(row);
    }

    @NotNull
    public final TransformationMatrix times(@NotNull TransformationMatrix right) {
        Intrinsics.checkNotNullParameter((Object)right, (String)"right");
        return Companion.combine(this, right);
    }

    @NotNull
    public final GeometricPoint times(@NotNull GeometricPoint point) {
        Intrinsics.checkNotNullParameter((Object)point, (String)"point");
        return Companion.transform(this, point);
    }

    @NotNull
    public final GeometricNormal times(@NotNull GeometricNormal normal2) {
        Intrinsics.checkNotNullParameter((Object)normal2, (String)"normal");
        return Companion.transform(this, normal2);
    }

    @NotNull
    public final Vec3 times(@NotNull Vec3 point) {
        Intrinsics.checkNotNullParameter((Object)point, (String)"point");
        return Companion.transform(this, new GeometricPoint(point)).toVec3d();
    }

    @NotNull
    public String toString() {
        return StringsKt.trimIndent((String)("\n            " + this.get(0).get(0) + " " + this.get(0).get(1) + " " + this.get(0).get(2) + " " + this.get(0).get(3) + "\n            " + this.get(1).get(0) + " " + this.get(1).get(1) + " " + this.get(1).get(2) + " " + this.get(1).get(3) + "\n            " + this.get(2).get(0) + " " + this.get(2).get(1) + " " + this.get(2).get(2) + " " + this.get(2).get(3) + "\n            " + this.get(3).get(0) + " " + this.get(3).get(1) + " " + this.get(3).get(2) + " " + this.get(3).get(3) + "\n        "));
    }

    @NotNull
    public final ImmutableArray<ImmutableArray<Float>> component1() {
        return this.values;
    }

    @NotNull
    public final TransformationMatrix copy(@NotNull ImmutableArray<ImmutableArray<Float>> values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        return new TransformationMatrix(values);
    }

    public static /* synthetic */ TransformationMatrix copy$default(TransformationMatrix transformationMatrix, ImmutableArray immutableArray, int n, Object object) {
        if ((n & 1) != 0) {
            immutableArray = transformationMatrix.values;
        }
        return transformationMatrix.copy(immutableArray);
    }

    public int hashCode() {
        return this.values.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TransformationMatrix)) {
            return false;
        }
        TransformationMatrix transformationMatrix = (TransformationMatrix)other;
        return Intrinsics.areEqual(this.values, transformationMatrix.values);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b:\u0010;J\u001d\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nJ#\u0010\r\u001a\u00020\b2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\u000bH\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001b\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\u000bH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J/\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ)\u0010!\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u001f2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b!\u0010\"J!\u0010!\u001a\u00020\u00022\u0006\u0010#\u001a\u00020\u001a2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b!\u0010$J!\u0010&\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\b2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b&\u0010'J1\u0010&\u001a\u00020\u00022\u0006\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\b2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b&\u0010+J\u001d\u0010.\u001a\u00020,2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010-\u001a\u00020,\u00a2\u0006\u0004\b.\u0010/J\u001d\u0010.\u001a\u00020\u00182\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010-\u001a\u00020\u0018\u00a2\u0006\u0004\b.\u00100J!\u00101\u001a\u00020\u00022\u0006\u0010-\u001a\u00020\u00182\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b1\u00102J+\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0303*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\u000bH\u0002\u00a2\u0006\u0004\b4\u00105R\u0017\u00106\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix$Companion;", "", "Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "left", "right", "combine", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "matrix", "", "determinant", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)F", "", "values", "determinant3x3", "([[Ljava/lang/Float;)F", "identityArray", "()[[Ljava/lang/Float;", "invert", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "", "row", "col", "multiplyRowCol", "(IILcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)F", "Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "translation", "Lorg/joml/Vector3f;", "rotation", "of", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;Lorg/joml/Vector3f;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "angle", "Lcom/cobblemon/mod/common/util/math/geometry/Axis;", "axis", "rotate", "(FLcom/cobblemon/mod/common/util/math/geometry/Axis;Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "angles", "(Lorg/joml/Vector3f;Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "scalar", "scale", "(FLcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "scalarX", "scalarY", "scalarZ", "(FFFLcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;", "point", "transform", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricNormal;", "(Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;)Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;", "translate", "(Lcom/cobblemon/mod/common/util/math/geometry/GeometricPoint;Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;)Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "toImmutable", "([[Ljava/lang/Float;)Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "identityMatrix", "Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "getIdentityMatrix", "()Lcom/cobblemon/mod/common/util/math/geometry/TransformationMatrix;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nTransformationMatrix.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TransformationMatrix.kt\ncom/cobblemon/mod/common/util/math/geometry/TransformationMatrix$Companion\n+ 2 ImmutableArray.kt\ncom/cobblemon/mod/common/util/collections/ImmutableArrayKt\n*L\n1#1,412:1\n16#2:413\n16#2:414\n16#2:415\n16#2:416\n16#2:417\n*S KotlinDebug\n*F\n+ 1 TransformationMatrix.kt\ncom/cobblemon/mod/common/util/math/geometry/TransformationMatrix$Companion\n*L\n403#1:413\n404#1:414\n405#1:415\n406#1:416\n402#1:417\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final TransformationMatrix getIdentityMatrix() {
            return identityMatrix;
        }

        private final Float[][] identityArray() {
            Float[][] floatArray = new Float[4][];
            Float[] floatArray2 = new Float[]{Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
            floatArray[0] = floatArray2;
            floatArray2 = new Float[]{Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
            floatArray[1] = floatArray2;
            floatArray2 = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.0f)};
            floatArray[2] = floatArray2;
            floatArray2 = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)};
            floatArray[3] = floatArray2;
            return floatArray;
        }

        @NotNull
        public final TransformationMatrix of(@NotNull GeometricPoint translation, @NotNull Vector3f rotation) {
            Intrinsics.checkNotNullParameter((Object)translation, (String)"translation");
            Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.TransformationMatrix$Companion.translate$default(this, translation, null, 2, null).times(bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.TransformationMatrix$Companion.rotate$default(this, rotation, null, 2, null));
        }

        @NotNull
        public final TransformationMatrix translate(@NotNull GeometricPoint point, @Nullable TransformationMatrix matrix) {
            Intrinsics.checkNotNullParameter((Object)point, (String)"point");
            Float[][] values = this.identityArray();
            values[0][3] = Float.valueOf(point.getX());
            values[1][3] = Float.valueOf(point.getY());
            values[2][3] = Float.valueOf(point.getZ());
            TransformationMatrix transformation = new TransformationMatrix(this.toImmutable(values));
            TransformationMatrix transformationMatrix = matrix;
            return transformationMatrix == null ? transformation : transformationMatrix.times(transformation);
        }

        public static /* synthetic */ TransformationMatrix translate$default(Companion companion, GeometricPoint geometricPoint, TransformationMatrix transformationMatrix, int n, Object object) {
            if ((n & 2) != 0) {
                transformationMatrix = null;
            }
            return companion.translate(geometricPoint, transformationMatrix);
        }

        @NotNull
        public final TransformationMatrix rotate(float angle, @NotNull Axis axis, @Nullable TransformationMatrix matrix) {
            Intrinsics.checkNotNullParameter((Object)((Object)axis), (String)"axis");
            TransformationMatrix transformation = axis.getRotationMatrix(angle);
            TransformationMatrix transformationMatrix = matrix;
            return transformationMatrix == null ? transformation : transformationMatrix.times(transformation);
        }

        public static /* synthetic */ TransformationMatrix rotate$default(Companion companion, float f, Axis axis, TransformationMatrix transformationMatrix, int n, Object object) {
            if ((n & 4) != 0) {
                transformationMatrix = null;
            }
            return companion.rotate(f, axis, transformationMatrix);
        }

        @NotNull
        public final TransformationMatrix rotate(@NotNull Vector3f angles, @Nullable TransformationMatrix matrix) {
            Intrinsics.checkNotNullParameter((Object)angles, (String)"angles");
            TransformationMatrix rotationX = Axis.X_AXIS.getRotationMatrix(angles.x);
            TransformationMatrix rotationY = Axis.Y_AXIS.getRotationMatrix(angles.y);
            TransformationMatrix rotationZ = Axis.Z_AXIS.getRotationMatrix(angles.z);
            TransformationMatrix transformation = rotationZ.times(rotationY).times(rotationX);
            TransformationMatrix transformationMatrix = matrix;
            return transformationMatrix == null ? transformation : transformationMatrix.times(transformation);
        }

        public static /* synthetic */ TransformationMatrix rotate$default(Companion companion, Vector3f vector3f, TransformationMatrix transformationMatrix, int n, Object object) {
            if ((n & 2) != 0) {
                transformationMatrix = null;
            }
            return companion.rotate(vector3f, transformationMatrix);
        }

        @NotNull
        public final TransformationMatrix scale(float scalar, @Nullable TransformationMatrix matrix) {
            return this.scale(scalar, scalar, scalar, matrix);
        }

        public static /* synthetic */ TransformationMatrix scale$default(Companion companion, float f, TransformationMatrix transformationMatrix, int n, Object object) {
            if ((n & 2) != 0) {
                transformationMatrix = null;
            }
            return companion.scale(f, transformationMatrix);
        }

        @NotNull
        public final TransformationMatrix scale(float scalarX, float scalarY, float scalarZ, @Nullable TransformationMatrix matrix) {
            Float[][] values = this.identityArray();
            values[0][0] = Float.valueOf(scalarX);
            values[1][1] = Float.valueOf(scalarY);
            values[2][2] = Float.valueOf(scalarZ);
            TransformationMatrix transformation = new TransformationMatrix(this.toImmutable(values));
            TransformationMatrix transformationMatrix = matrix;
            return transformationMatrix == null ? transformation : transformationMatrix.times(transformation);
        }

        public static /* synthetic */ TransformationMatrix scale$default(Companion companion, float f, float f2, float f3, TransformationMatrix transformationMatrix, int n, Object object) {
            if ((n & 8) != 0) {
                transformationMatrix = null;
            }
            return companion.scale(f, f2, f3, transformationMatrix);
        }

        @NotNull
        public final GeometricPoint transform(@NotNull TransformationMatrix matrix, @NotNull GeometricPoint point) {
            Intrinsics.checkNotNullParameter((Object)matrix, (String)"matrix");
            Intrinsics.checkNotNullParameter((Object)point, (String)"point");
            float x = ((Number)matrix.get(0).get(0)).floatValue() * point.getX() + ((Number)matrix.get(0).get(1)).floatValue() * point.getY() + ((Number)matrix.get(0).get(2)).floatValue() * point.getZ() + ((Number)matrix.get(0).get(3)).floatValue();
            float y = ((Number)matrix.get(1).get(0)).floatValue() * point.getX() + ((Number)matrix.get(1).get(1)).floatValue() * point.getY() + ((Number)matrix.get(1).get(2)).floatValue() * point.getZ() + ((Number)matrix.get(1).get(3)).floatValue();
            float z = ((Number)matrix.get(2).get(0)).floatValue() * point.getX() + ((Number)matrix.get(2).get(1)).floatValue() * point.getY() + ((Number)matrix.get(2).get(2)).floatValue() * point.getZ() + ((Number)matrix.get(2).get(3)).floatValue();
            return new GeometricPoint(x, y, z);
        }

        @NotNull
        public final GeometricNormal transform(@NotNull TransformationMatrix matrix, @NotNull GeometricNormal point) {
            Intrinsics.checkNotNullParameter((Object)matrix, (String)"matrix");
            Intrinsics.checkNotNullParameter((Object)point, (String)"point");
            float x = ((Number)matrix.get(0).get(0)).floatValue() * point.getX() + ((Number)matrix.get(0).get(1)).floatValue() * point.getY() + ((Number)matrix.get(0).get(2)).floatValue() * point.getZ();
            float y = ((Number)matrix.get(1).get(0)).floatValue() * point.getX() + ((Number)matrix.get(1).get(1)).floatValue() * point.getY() + ((Number)matrix.get(1).get(2)).floatValue() * point.getZ();
            float z = ((Number)matrix.get(2).get(0)).floatValue() * point.getX() + ((Number)matrix.get(2).get(1)).floatValue() * point.getY() + ((Number)matrix.get(2).get(2)).floatValue() * point.getZ();
            return new GeometricNormal(x, y, z);
        }

        @Nullable
        public final TransformationMatrix invert(@NotNull TransformationMatrix matrix) {
            Intrinsics.checkNotNullParameter((Object)matrix, (String)"matrix");
            float determinant = this.determinant(matrix);
            if (determinant == 0.0f) {
                return null;
            }
            float inverseDeterminant = 1.0f / determinant;
            Float[][] floatArray = new Float[3][];
            Float[] floatArray2 = new Float[]{matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray[0] = floatArray2;
            floatArray2 = new Float[]{matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray[1] = floatArray2;
            floatArray2 = new Float[]{matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray[2] = floatArray2;
            float t00 = this.determinant3x3(floatArray);
            floatArray2 = new Float[3][];
            Float[] floatArray3 = new Float[]{matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray2[0] = floatArray3;
            floatArray3 = new Float[]{matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray2[1] = floatArray3;
            floatArray3 = new Float[]{matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray2[2] = floatArray3;
            float t01 = -this.determinant3x3((Float[][])floatArray2);
            floatArray3 = new Float[3][];
            Float[] floatArray4 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)};
            floatArray3[0] = floatArray4;
            floatArray4 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)};
            floatArray3[1] = floatArray4;
            floatArray4 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)};
            floatArray3[2] = floatArray4;
            float t02 = this.determinant3x3((Float[][])floatArray3);
            floatArray4 = new Float[3][];
            Float[] floatArray5 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)};
            floatArray4[0] = floatArray5;
            floatArray5 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)};
            floatArray4[1] = floatArray5;
            floatArray5 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)};
            floatArray4[2] = floatArray5;
            float t03 = -this.determinant3x3((Float[][])floatArray4);
            floatArray5 = new Float[3][];
            Float[] floatArray6 = new Float[]{matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray5[0] = floatArray6;
            floatArray6 = new Float[]{matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray5[1] = floatArray6;
            floatArray6 = new Float[]{matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray5[2] = floatArray6;
            float t10 = -this.determinant3x3((Float[][])floatArray5);
            floatArray6 = new Float[3][];
            Float[] floatArray7 = new Float[]{matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray6[0] = floatArray7;
            floatArray7 = new Float[]{matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray6[1] = floatArray7;
            floatArray7 = new Float[]{matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray6[2] = floatArray7;
            float t11 = this.determinant3x3((Float[][])floatArray6);
            floatArray7 = new Float[3][];
            Float[] floatArray8 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)};
            floatArray7[0] = floatArray8;
            floatArray8 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)};
            floatArray7[1] = floatArray8;
            floatArray8 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)};
            floatArray7[2] = floatArray8;
            float t12 = -this.determinant3x3((Float[][])floatArray7);
            floatArray8 = new Float[3][];
            Float[] floatArray9 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)};
            floatArray8[0] = floatArray9;
            floatArray9 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)};
            floatArray8[1] = floatArray9;
            floatArray9 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)};
            floatArray8[2] = floatArray9;
            float t13 = this.determinant3x3((Float[][])floatArray8);
            floatArray9 = new Float[3][];
            Float[] floatArray10 = new Float[]{matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray9[0] = floatArray10;
            floatArray10 = new Float[]{matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray9[1] = floatArray10;
            floatArray10 = new Float[]{matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray9[2] = floatArray10;
            float t20 = this.determinant3x3((Float[][])floatArray9);
            floatArray10 = new Float[3][];
            Float[] floatArray11 = new Float[]{matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray10[0] = floatArray11;
            floatArray11 = new Float[]{matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray10[1] = floatArray11;
            floatArray11 = new Float[]{matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)};
            floatArray10[2] = floatArray11;
            float t21 = -this.determinant3x3((Float[][])floatArray10);
            floatArray11 = new Float[3][];
            Float[] floatArray12 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)};
            floatArray11[0] = floatArray12;
            floatArray12 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)};
            floatArray11[1] = floatArray12;
            floatArray12 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)};
            floatArray11[2] = floatArray12;
            float t22 = this.determinant3x3((Float[][])floatArray11);
            floatArray12 = new Float[3][];
            Float[] floatArray13 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)};
            floatArray12[0] = floatArray13;
            floatArray13 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)};
            floatArray12[1] = floatArray13;
            floatArray13 = new Float[]{matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)};
            floatArray12[2] = floatArray13;
            float t23 = -this.determinant3x3((Float[][])floatArray12);
            floatArray13 = new Float[3][];
            Float[] floatArray14 = new Float[]{matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray13[0] = floatArray14;
            floatArray14 = new Float[]{matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray13[1] = floatArray14;
            floatArray14 = new Float[]{matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray13[2] = floatArray14;
            float t30 = -this.determinant3x3((Float[][])floatArray13);
            floatArray14 = new Float[3][];
            Float[] floatArray15 = new Float[]{matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)};
            floatArray14[0] = floatArray15;
            floatArray15 = new Float[]{matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)};
            floatArray14[1] = floatArray15;
            floatArray15 = new Float[]{matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)};
            floatArray14[2] = floatArray15;
            float t31 = this.determinant3x3((Float[][])floatArray14);
            floatArray15 = new Float[3][];
            Float[] floatArray16 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)};
            floatArray15[0] = floatArray16;
            floatArray16 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)};
            floatArray15[1] = floatArray16;
            floatArray16 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)};
            floatArray15[2] = floatArray16;
            float t32 = -this.determinant3x3((Float[][])floatArray15);
            floatArray16 = new Float[3][];
            Float[] floatArray17 = new Float[]{matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)};
            floatArray16[0] = floatArray17;
            floatArray17 = new Float[]{matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)};
            floatArray16[1] = floatArray17;
            floatArray17 = new Float[]{matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)};
            floatArray16[2] = floatArray17;
            float t33 = this.determinant3x3((Float[][])floatArray16);
            Float[][] values = this.identityArray();
            values[0][0] = Float.valueOf(t00 * inverseDeterminant);
            values[0][1] = Float.valueOf(t01 * inverseDeterminant);
            values[0][2] = Float.valueOf(t02 * inverseDeterminant);
            values[0][3] = Float.valueOf(t03 * inverseDeterminant);
            values[1][0] = Float.valueOf(t10 * inverseDeterminant);
            values[1][1] = Float.valueOf(t11 * inverseDeterminant);
            values[1][2] = Float.valueOf(t12 * inverseDeterminant);
            values[1][3] = Float.valueOf(t13 * inverseDeterminant);
            values[2][0] = Float.valueOf(t20 * inverseDeterminant);
            values[2][1] = Float.valueOf(t21 * inverseDeterminant);
            values[2][2] = Float.valueOf(t22 * inverseDeterminant);
            values[2][3] = Float.valueOf(t23 * inverseDeterminant);
            values[3][0] = Float.valueOf(t30 * inverseDeterminant);
            values[3][1] = Float.valueOf(t31 * inverseDeterminant);
            values[3][2] = Float.valueOf(t32 * inverseDeterminant);
            values[3][3] = Float.valueOf(t33 * inverseDeterminant);
            return new TransformationMatrix(this.toImmutable(values));
        }

        public final float determinant(@NotNull TransformationMatrix matrix) {
            Intrinsics.checkNotNullParameter((Object)matrix, (String)"matrix");
            float determinant = ((Number)matrix.get(0).get(0)).floatValue() * (((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue() + ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() + ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue() - ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() - ((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue() - ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue());
            determinant -= ((Number)matrix.get(1).get(0)).floatValue() * (((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue() + ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() + ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue() - ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() - ((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue() - ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue());
            determinant += ((Number)matrix.get(2).get(0)).floatValue() * (((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue() + ((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() + ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() - ((Number)matrix.get(3).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() - ((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(3).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() - ((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(3).get(3)).floatValue());
            return determinant -= ((Number)matrix.get(3).get(0)).floatValue() * (((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue() + ((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() + ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() - ((Number)matrix.get(2).get(1)).floatValue() * ((Number)matrix.get(1).get(2)).floatValue() * ((Number)matrix.get(0).get(3)).floatValue() - ((Number)matrix.get(0).get(1)).floatValue() * ((Number)matrix.get(2).get(2)).floatValue() * ((Number)matrix.get(1).get(3)).floatValue() - ((Number)matrix.get(1).get(1)).floatValue() * ((Number)matrix.get(0).get(2)).floatValue() * ((Number)matrix.get(2).get(3)).floatValue());
        }

        private final float determinant3x3(Float[][] values) {
            int n;
            int n2 = n = ((Object[])values).length == 3 ? 1 : 0;
            if (_Assertions.ENABLED && n == 0) {
                String string = "Assertion failed";
                throw new AssertionError((Object)string);
            }
            int n3 = ((Object[])values).length;
            for (n = 0; n < n3; ++n) {
                boolean bl;
                Float[] row = values[n];
                boolean bl2 = bl = row.length == 3;
                if (!_Assertions.ENABLED || bl) continue;
                String string = "Assertion failed";
                throw new AssertionError((Object)string);
            }
            return values[0][0].floatValue() * (values[1][1].floatValue() * values[2][2].floatValue() - values[1][2].floatValue() * values[2][1].floatValue()) + values[0][1].floatValue() * (values[1][2].floatValue() * values[2][0].floatValue() - values[1][0].floatValue() * values[2][2].floatValue()) + values[0][2].floatValue() * (values[1][0].floatValue() * values[2][1].floatValue() - values[1][1].floatValue() * values[2][0].floatValue());
        }

        @NotNull
        public final TransformationMatrix combine(@NotNull TransformationMatrix left, @NotNull TransformationMatrix right) {
            Intrinsics.checkNotNullParameter((Object)left, (String)"left");
            Intrinsics.checkNotNullParameter((Object)right, (String)"right");
            Float[][] floatArray = new Float[4][];
            for (int i = 0; i < 4; ++i) {
                int n = i;
                int n2 = 0;
                Float[] floatArray2 = new Float[4];
                int n3 = n;
                Float[][] floatArray3 = floatArray;
                while (n2 < 4) {
                    int n4 = n2++;
                    floatArray2[n4] = Float.valueOf(0.0f);
                }
                floatArray3[n3] = floatArray2;
            }
            Float[][] values = floatArray;
            for (int row = 0; row < 4; ++row) {
                for (int col = 0; col < 4; ++col) {
                    values[row][col] = Float.valueOf(this.multiplyRowCol(row, col, left, right));
                }
            }
            return new TransformationMatrix(this.toImmutable(values));
        }

        private final float multiplyRowCol(int row, int col, TransformationMatrix left, TransformationMatrix right) {
            float sum = 0.0f;
            for (int i = 0; i < 4; ++i) {
                sum += ((Number)left.get(row).get(i)).floatValue() * ((Number)right.get(i).get(col)).floatValue();
            }
            return sum;
        }

        private final ImmutableArray<ImmutableArray<Float>> toImmutable(Float[][] $this$toImmutable) {
            ImmutableArray[] immutableArrayArray = new ImmutableArray[4];
            Float[] floatArray = $this$toImmutable[0];
            Float[] values$iv = Arrays.copyOf(floatArray, floatArray.length);
            boolean $i$f$immutableArrayOf = false;
            immutableArrayArray[0] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
            values$iv = $this$toImmutable[1];
            values$iv = Arrays.copyOf(values$iv, values$iv.length);
            $i$f$immutableArrayOf = false;
            immutableArrayArray[1] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
            values$iv = $this$toImmutable[2];
            values$iv = Arrays.copyOf(values$iv, values$iv.length);
            $i$f$immutableArrayOf = false;
            immutableArrayArray[2] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
            values$iv = $this$toImmutable[3];
            values$iv = Arrays.copyOf(values$iv, values$iv.length);
            $i$f$immutableArrayOf = false;
            immutableArrayArray[3] = new ImmutableArray<Float>(Arrays.copyOf(values$iv, values$iv.length));
            ImmutableArray[] values$iv2 = immutableArrayArray;
            boolean $i$f$immutableArrayOf2 = false;
            return new ImmutableArray<ImmutableArray<Float>>(Arrays.copyOf(values$iv2, values$iv2.length));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static final /* synthetic */ ImmutableArray access$toImmutable(Companion $this, Float[][] $receiver) {
            return $this.toImmutable($receiver);
        }

        public static final /* synthetic */ Float[][] access$identityArray(Companion $this) {
            return $this.identityArray();
        }
    }
}

