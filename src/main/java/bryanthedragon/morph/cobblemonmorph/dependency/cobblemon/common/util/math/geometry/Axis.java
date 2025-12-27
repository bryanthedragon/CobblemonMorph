package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.ImmutableArray
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nAxis.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Axis.kt\ncom/cobblemon/mod/common/util/math/geometry/Axis\n+ 2 ImmutableArray.kt\ncom/cobblemon/mod/common/util/collections/ImmutableArrayKt\n*L\n1#1,59:1\n16#2:60\n16#2:61\n16#2:62\n16#2:63\n16#2:64\n16#2:65\n16#2:66\n16#2:67\n16#2:68\n16#2:69\n16#2:70\n16#2:71\n16#2:72\n16#2:73\n16#2:74\n*S KotlinDebug\n*F\n+ 1 Axis.kt\ncom/cobblemon/mod/common/util/math/geometry/Axis\n*L\n35#1:60\n36#1:61\n37#1:62\n38#1:63\n34#1:64\n43#1:65\n44#1:66\n45#1:67\n46#1:68\n42#1:69\n51#1:70\n52#1:71\n53#1:72\n54#1:73\n50#1:74\n*E\n"])
public enum Axis(x: Float, y: Float, z: Float) {
   X_AXIS(1.0F, 0.0F, 0.0F),
   Y_AXIS(0.0F, 1.0F, 0.0F),
   Z_AXIS(0.0F, 0.0F, 1.0F)
   public final val x: Float
   public final val y: Float
   public final val z: Float

   init {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public fun getRotationMatrix(angle: Float = 0.0F): TransformationMatrix {
      var var10000: TransformationMatrix;
      switch (Axis.WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            val var6: Array<ImmutableArray> = new ImmutableArray[4];
            var var16: Array<java.lang.Float> = new java.lang.Float[]{1.0F, 0.0F, 0.0F, 0.0F};
            var6[0] = new ImmutableArray<>(Arrays.copyOf(var16, var16.length));
            var16 = new java.lang.Float[]{0.0F, (float)Math.cos((double)angle), -((float)Math.sin((double)angle)), 0.0F};
            var6[1] = new ImmutableArray<>(Arrays.copyOf(var16, var16.length));
            var16 = new java.lang.Float[]{0.0F, (float)Math.sin((double)angle), (float)Math.cos((double)angle), 0.0F};
            var6[2] = new ImmutableArray<>(Arrays.copyOf(var16, var16.length));
            var16 = new java.lang.Float[]{0.0F, 0.0F, 0.0F, 1.0F};
            var6[3] = new ImmutableArray<>(Arrays.copyOf(var16, var16.length));
            var10000 = new TransformationMatrix(new ImmutableArray<>(Arrays.copyOf(var6, var6.length)));
            break;
         case 2:
            val var5: Array<ImmutableArray> = new ImmutableArray[4];
            var var11: Array<java.lang.Float> = new java.lang.Float[]{(float)Math.cos((double)angle), 0.0F, (float)Math.sin((double)angle), 0.0F};
            var5[0] = new ImmutableArray<>(Arrays.copyOf(var11, var11.length));
            var11 = new java.lang.Float[]{0.0F, 1.0F, 0.0F, 0.0F};
            var5[1] = new ImmutableArray<>(Arrays.copyOf(var11, var11.length));
            var11 = new java.lang.Float[]{-((float)Math.sin((double)angle)), 0.0F, (float)Math.cos((double)angle), 0.0F};
            var5[2] = new ImmutableArray<>(Arrays.copyOf(var11, var11.length));
            var11 = new java.lang.Float[]{0.0F, 0.0F, 0.0F, 1.0F};
            var5[3] = new ImmutableArray<>(Arrays.copyOf(var11, var11.length));
            var10000 = new TransformationMatrix(new ImmutableArray<>(Arrays.copyOf(var5, var5.length)));
            break;
         case 3:
            val `values$iv`: Array<ImmutableArray> = new ImmutableArray[4];
            var `$i$f$immutableArrayOf`: Array<java.lang.Float> = new java.lang.Float[]{
               (float)Math.cos((double)angle), -((float)Math.sin((double)angle)), 0.0F, 0.0F
            };
            `values$iv`[0] = new ImmutableArray<>(Arrays.copyOf(`$i$f$immutableArrayOf`, `$i$f$immutableArrayOf`.length));
            `$i$f$immutableArrayOf` = new java.lang.Float[]{(float)Math.sin((double)angle), (float)Math.cos((double)angle), 0.0F, 0.0F};
            `values$iv`[1] = new ImmutableArray<>(Arrays.copyOf(`$i$f$immutableArrayOf`, `$i$f$immutableArrayOf`.length));
            `$i$f$immutableArrayOf` = new java.lang.Float[]{0.0F, 0.0F, 1.0F, 0.0F};
            `values$iv`[2] = new ImmutableArray<>(Arrays.copyOf(`$i$f$immutableArrayOf`, `$i$f$immutableArrayOf`.length));
            `$i$f$immutableArrayOf` = new java.lang.Float[]{0.0F, 0.0F, 0.0F, 1.0F};
            `values$iv`[3] = new ImmutableArray<>(Arrays.copyOf(`$i$f$immutableArrayOf`, `$i$f$immutableArrayOf`.length));
            var10000 = new TransformationMatrix(new ImmutableArray<>(Arrays.copyOf(`values$iv`, `values$iv`.length)));
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }
}
