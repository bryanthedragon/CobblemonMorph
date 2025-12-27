package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation

import kotlin.jvm.functions.Function1
import org.jetbrains.annotations.NotNull

public fun gradualFunction(base: Float = 1.0F, step: Float = 1.0F): (Int) -> Float {
   return (new Function1<Integer, java.lang.Float>(base, step) {
      {
         super(1);
         this.$base = `$base`;
         this.$step = `$step`;
      }

      @NotNull
      public final java.lang.Float invoke(int index) {
         return this.$base + this.$step * (float)index;
      }
   }) as (Int?) -> java.lang.Float;
}

@JvmSynthetic
fun `gradualFunction$default`(var0: Float, var1: Float, var2: Int, var3: Any): Function1 {
   if ((var2 and 1) != 0) {
      var0 = 1.0F;
   }

   if ((var2 and 2) != 0) {
      var1 = 1.0F;
   }

   return gradualFunction(var0, var1);
}

public fun cosineFunction(period: Float = 1.0F): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(period) {
      {
         super(1);
         this.$period = `$period`;
      }

      @NotNull
      public final java.lang.Float invoke(float x) {
         return (float)Math.cos((double)(x * this.$period));
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `cosineFunction$default`(var0: Float, var1: Int, var2: Any): Function1 {
   if ((var1 and 1) != 0) {
      var0 = 1.0F;
   }

   return cosineFunction(var0);
}

public fun sineFunction(period: Float = 1.0F): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(period) {
      {
         super(1);
         this.$period = `$period`;
      }

      @NotNull
      public final java.lang.Float invoke(float x) {
         return (float)Math.sin((double)(x * this.$period));
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `sineFunction$default`(var0: Float, var1: Int, var2: Any): Function1 {
   if ((var1 and 1) != 0) {
      var0 = 1.0F;
   }

   return sineFunction(var0);
}
