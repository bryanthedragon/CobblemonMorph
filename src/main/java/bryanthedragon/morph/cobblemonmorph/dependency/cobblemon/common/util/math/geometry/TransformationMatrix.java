package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.ImmutableArray
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

public data TransformationMatrix internal constructor(values: ImmutableArray<ImmutableArray<Float>>) {
   public final val values: ImmutableArray<ImmutableArray<Float>>

   init {
      this.values = values;
   }

   public operator fun get(row: Int): ImmutableArray<Float> {
      return this.values.get(row);
   }

   public operator fun times(right: TransformationMatrix): TransformationMatrix {
      return Companion.combine(this, right);
   }

   public operator fun times(point: GeometricPoint): GeometricPoint {
      return Companion.transform(this, point);
   }

   public operator fun times(normal: GeometricNormal): GeometricNormal {
      return Companion.transform(this, normal);
   }

   public operator fun times(point: Vec3): Vec3 {
      return Companion.transform(this, new GeometricPoint(point)).toVec3d();
   }

   public override fun toString(): String {
      return StringsKt.trimIndent(
         "\n            ${this.get(0).get(0)} ${this.get(0).get(1)} ${this.get(0).get(2)} ${this.get(0).get(3)}\n            ${this.get(1).get(0)} ${this.get(1)
            .get(1)} ${this.get(1).get(2)} ${this.get(1).get(3)}\n            ${this.get(2).get(0)} ${this.get(2).get(1)} ${this.get(2).get(2)} ${this.get(2)
            .get(3)}\n            ${this.get(3).get(0)} ${this.get(3).get(1)} ${this.get(3).get(2)} ${this.get(3).get(3)}\n        "
      );
   }

   public operator fun component1(): ImmutableArray<ImmutableArray<Float>> {
      return this.values;
   }

   public fun copy(values: ImmutableArray<ImmutableArray<Float>> = this.values): TransformationMatrix {
      return new TransformationMatrix(values);
   }

   public override fun hashCode(): Int {
      return this.values.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is TransformationMatrix) {
         return false;
      } else {
         return this.values == (other as TransformationMatrix).values;
      }
   }

   @SourceDebugExtension(["SMAP\nTransformationMatrix.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TransformationMatrix.kt\ncom/cobblemon/mod/common/util/math/geometry/TransformationMatrix$Companion\n+ 2 ImmutableArray.kt\ncom/cobblemon/mod/common/util/collections/ImmutableArrayKt\n*L\n1#1,412:1\n16#2:413\n16#2:414\n16#2:415\n16#2:416\n16#2:417\n*S KotlinDebug\n*F\n+ 1 TransformationMatrix.kt\ncom/cobblemon/mod/common/util/math/geometry/TransformationMatrix$Companion\n*L\n403#1:413\n404#1:414\n405#1:415\n406#1:416\n402#1:417\n*E\n"])
   public companion object {
      public final val identityMatrix: TransformationMatrix

      private fun identityArray(): Array<Array<Float>> {
         return new java.lang.Float[][]{{1.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, 0.0F, 1.0F}};
      }

      public fun of(translation: GeometricPoint, rotation: Vector3f): TransformationMatrix {
         return translate$default(this, translation, null, 2, null).times(rotate$default(this, rotation, null, 2, null));
      }

      public fun translate(point: GeometricPoint, matrix: TransformationMatrix? = null): TransformationMatrix {
         val values: Array<Array<java.lang.Float>> = this.identityArray();
         values[0][3] = point.getX();
         values[1][3] = point.getY();
         values[2][3] = point.getZ();
         val transformation: TransformationMatrix = new TransformationMatrix(this.toImmutable(values));
         return if (matrix == null) transformation else matrix.times(transformation);
      }

      public fun rotate(angle: Float, axis: Axis, matrix: TransformationMatrix? = null): TransformationMatrix {
         val transformation: TransformationMatrix = axis.getRotationMatrix(angle);
         return if (matrix == null) transformation else matrix.times(transformation);
      }

      public fun rotate(angles: Vector3f, matrix: TransformationMatrix? = null): TransformationMatrix {
         val transformation: TransformationMatrix = Axis.Z_AXIS
            .getRotationMatrix(angles.z)
            .times(Axis.Y_AXIS.getRotationMatrix(angles.y))
            .times(Axis.X_AXIS.getRotationMatrix(angles.x));
         return if (matrix == null) transformation else matrix.times(transformation);
      }

      public fun scale(scalar: Float, matrix: TransformationMatrix? = null): TransformationMatrix {
         return this.scale(scalar, scalar, scalar, matrix);
      }

      public fun scale(scalarX: Float, scalarY: Float, scalarZ: Float, matrix: TransformationMatrix? = null): TransformationMatrix {
         val values: Array<Array<java.lang.Float>> = this.identityArray();
         values[0][0] = scalarX;
         values[1][1] = scalarY;
         values[2][2] = scalarZ;
         val transformation: TransformationMatrix = new TransformationMatrix(this.toImmutable(values));
         return if (matrix == null) transformation else matrix.times(transformation);
      }

      public fun transform(matrix: TransformationMatrix, point: GeometricPoint): GeometricPoint {
         return new GeometricPoint(
            matrix.get(0).get(0).floatValue() * point.getX()
               + matrix.get(0).get(1).floatValue() * point.getY()
               + matrix.get(0).get(2).floatValue() * point.getZ()
               + matrix.get(0).get(3).floatValue(),
            matrix.get(1).get(0).floatValue() * point.getX()
               + matrix.get(1).get(1).floatValue() * point.getY()
               + matrix.get(1).get(2).floatValue() * point.getZ()
               + matrix.get(1).get(3).floatValue(),
            matrix.get(2).get(0).floatValue() * point.getX()
               + matrix.get(2).get(1).floatValue() * point.getY()
               + matrix.get(2).get(2).floatValue() * point.getZ()
               + matrix.get(2).get(3).floatValue()
         );
      }

      public fun transform(matrix: TransformationMatrix, point: GeometricNormal): GeometricNormal {
         return new GeometricNormal(
            matrix.get(0).get(0).floatValue() * point.getX()
               + matrix.get(0).get(1).floatValue() * point.getY()
               + matrix.get(0).get(2).floatValue() * point.getZ(),
            matrix.get(1).get(0).floatValue() * point.getX()
               + matrix.get(1).get(1).floatValue() * point.getY()
               + matrix.get(1).get(2).floatValue() * point.getZ(),
            matrix.get(2).get(0).floatValue() * point.getX()
               + matrix.get(2).get(1).floatValue() * point.getY()
               + matrix.get(2).get(2).floatValue() * point.getZ()
         );
      }

      public fun invert(matrix: TransformationMatrix): TransformationMatrix? {
         val determinant: Float = this.determinant(matrix);
         if (determinant == 0.0F) {
            return null;
         } else {
            val inverseDeterminant: Float = 1 / determinant;
            val t00: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)},
                  {matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var22: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)},
                  {matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var26: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)}
               }
            );
            val var30: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)}
               }
            );
            val var34: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)},
                  {matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var38: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)},
                  {matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var42: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)}
               }
            );
            val var46: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)}
               }
            );
            val var50: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(1).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var54: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(3), matrix.get(2).get(3), matrix.get(3).get(3)}
               }
            );
            val var58: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(3).get(3)}
               }
            );
            val var62: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)},
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)},
                  {matrix.get(0).get(3), matrix.get(1).get(3), matrix.get(2).get(3)}
               }
            );
            val var66: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(1).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(1).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(1).get(2), matrix.get(2).get(2), matrix.get(3).get(2)}
               }
            );
            val var70: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(2).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(1), matrix.get(2).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(2), matrix.get(2).get(2), matrix.get(3).get(2)}
               }
            );
            val var74: Float = -this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(3).get(0)},
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(3).get(1)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(3).get(2)}
               }
            );
            val var78: Float = this.determinant3x3(
               new java.lang.Float[][]{
                  {matrix.get(0).get(0), matrix.get(1).get(0), matrix.get(2).get(0)},
                  {matrix.get(0).get(1), matrix.get(1).get(1), matrix.get(2).get(1)},
                  {matrix.get(0).get(2), matrix.get(1).get(2), matrix.get(2).get(2)}
               }
            );
            val var82: Array<Array<java.lang.Float>> = this.identityArray();
            var82[0][0] = t00 * inverseDeterminant;
            var82[0][1] = var22 * inverseDeterminant;
            var82[0][2] = var26 * inverseDeterminant;
            var82[0][3] = var30 * inverseDeterminant;
            var82[1][0] = var34 * inverseDeterminant;
            var82[1][1] = var38 * inverseDeterminant;
            var82[1][2] = var42 * inverseDeterminant;
            var82[1][3] = var46 * inverseDeterminant;
            var82[2][0] = var50 * inverseDeterminant;
            var82[2][1] = var54 * inverseDeterminant;
            var82[2][2] = var58 * inverseDeterminant;
            var82[2][3] = var62 * inverseDeterminant;
            var82[3][0] = var66 * inverseDeterminant;
            var82[3][1] = var70 * inverseDeterminant;
            var82[3][2] = var74 * inverseDeterminant;
            var82[3][3] = var78 * inverseDeterminant;
            return new TransformationMatrix(this.toImmutable(var82));
         }
      }

      public fun determinant(matrix: TransformationMatrix): Float {
         return matrix.get(0).get(0).floatValue()
               * (
                  matrix.get(1).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(3).get(3).floatValue()
                     + matrix.get(2).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     + matrix.get(3).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(2).get(3).floatValue()
                     - matrix.get(3).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     - matrix.get(1).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(2).get(3).floatValue()
                     - matrix.get(2).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(3).get(3).floatValue()
               )
            - matrix.get(1).get(0).floatValue()
               * (
                  matrix.get(0).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(3).get(3).floatValue()
                     + matrix.get(2).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     + matrix.get(3).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(2).get(3).floatValue()
                     - matrix.get(3).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     - matrix.get(0).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(2).get(3).floatValue()
                     - matrix.get(2).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(3).get(3).floatValue()
               )
            + matrix.get(2).get(0).floatValue()
               * (
                  matrix.get(0).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(3).get(3).floatValue()
                     + matrix.get(1).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     + matrix.get(3).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     - matrix.get(3).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     - matrix.get(0).get(1).floatValue() * matrix.get(3).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     - matrix.get(1).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(3).get(3).floatValue()
               )
            - matrix.get(3).get(0).floatValue()
               * (
                  matrix.get(0).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(2).get(3).floatValue()
                     + matrix.get(1).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     + matrix.get(2).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     - matrix.get(2).get(1).floatValue() * matrix.get(1).get(2).floatValue() * matrix.get(0).get(3).floatValue()
                     - matrix.get(0).get(1).floatValue() * matrix.get(2).get(2).floatValue() * matrix.get(1).get(3).floatValue()
                     - matrix.get(1).get(1).floatValue() * matrix.get(0).get(2).floatValue() * matrix.get(2).get(3).floatValue()
               );
      }

      private fun determinant3x3(values: Array<Array<Float>>): Float {
         if (_Assertions.ENABLED && (values as Array<Any>).length != 3) {
            throw new AssertionError("Assertion failed");
         } else {
            for (java.lang.Float[] row : (Object[])values) {
               if (_Assertions.ENABLED && row.length != 3) {
                  throw new AssertionError("Assertion failed");
               }
            }

            return values[0][0] * (values[1][1] * values[2][2] - values[1][2] * values[2][1])
               + values[0][1] * (values[1][2] * values[2][0] - values[1][0] * values[2][2])
               + values[0][2] * (values[1][0] * values[2][1] - values[1][1] * values[2][0]);
         }
      }

      public fun combine(left: TransformationMatrix, right: TransformationMatrix): TransformationMatrix {
         var row: Int = 0;

         val col: Array<Array<java.lang.Float>>;
         for (col = new java.lang.Float[4][]; row < 4; row++) {
            var var7: Int = 0;

            val var8: Array<java.lang.Float>;
            for (var8 = new java.lang.Float[4]; var7 < 4; var7++) {
               var8[var7] = 0.0F;
            }

            col[row] = var8;
         }

         val values: Array<Array<java.lang.Float>> = col;

         for (int rowx = 0; rowx < 4; rowx++) {
            for (int colx = 0; colx < 4; colx++) {
               values[rowx][colx] = this.multiplyRowCol(rowx, colx, left, right);
            }
         }

         return new TransformationMatrix(this.toImmutable(values));
      }

      private fun multiplyRowCol(row: Int, col: Int, left: TransformationMatrix, right: TransformationMatrix): Float {
         var sum: Float = 0.0F;

         for (int i = 0; i < 4; i++) {
            sum += left.get(row).get(i).floatValue() * right.get(i).get(col).floatValue();
         }

         return sum;
      }

      private fun Array<Array<Float>>.toImmutable(): ImmutableArray<ImmutableArray<Float>> {
         val `values$iv`: Array<ImmutableArray> = new ImmutableArray[4];
         var var5: Array<Any> = Arrays.copyOf(`$this$toImmutable`[0], `$this$toImmutable`[0].length);
         `values$iv`[0] = new ImmutableArray<>(Arrays.copyOf(var5, var5.length));
         var5 = Arrays.copyOf(`$this$toImmutable`[1], `$this$toImmutable`[1].length);
         `values$iv`[1] = new ImmutableArray<>(Arrays.copyOf(var5, var5.length));
         var5 = Arrays.copyOf(`$this$toImmutable`[2], `$this$toImmutable`[2].length);
         `values$iv`[2] = new ImmutableArray<>(Arrays.copyOf(var5, var5.length));
         var5 = Arrays.copyOf(`$this$toImmutable`[3], `$this$toImmutable`[3].length);
         `values$iv`[3] = new ImmutableArray<>(Arrays.copyOf(var5, var5.length));
         return new ImmutableArray<>(Arrays.copyOf(`values$iv`, `values$iv`.length));
      }
   }
}
