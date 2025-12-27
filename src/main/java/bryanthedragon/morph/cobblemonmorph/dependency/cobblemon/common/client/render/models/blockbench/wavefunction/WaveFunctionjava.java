package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction

import kotlin.jvm.functions.Function1
import net.minecraft.util.Mth
import org.jetbrains.annotations.NotNull

public fun gradient(function: (Float) -> Float, t0: Float, t1: Float): Float {
   return ((function.invoke(t1) as java.lang.Number).floatValue() - (function.invoke(t0) as java.lang.Number).floatValue()) / (t1 - t0);
}

public operator fun ((Float) -> Float).plus(other: (Float) -> Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$plus`, other) {
      {
         super(1);
         this.$this_plus = `$receiver`;
         this.$other = `$other`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return (this.$this_plus.invoke(t) as java.lang.Number).floatValue() + (this.$other.invoke(t) as java.lang.Number).floatValue();
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public operator fun ((Float) -> Float).times(other: (Float) -> Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$times`, other) {
      {
         super(1);
         this.$this_times = `$receiver`;
         this.$other = `$other`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return (this.$this_times.invoke(t) as java.lang.Number).floatValue() * (this.$other.invoke(t) as java.lang.Number).floatValue();
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).rerange(min: Float, max: Float): (Float) -> Float {
   return (
      new Function1<java.lang.Float, java.lang.Float>(min, max, `$this$rerange`) {
         {
            super(1);
            this.$min = `$min`;
            this.$max = `$max`;
            this.$this_rerange = `$receiver`;
         }

         @NotNull
         public final java.lang.Float invoke(float t) {
            return if (this.$min <= t && t <= this.$max)
               (this.$this_rerange.invoke((t - this.$min) / (this.$max - this.$min)) as java.lang.Number).floatValue()
               else
               (this.$this_rerange.invoke(0.0F) as java.lang.Number).floatValue();
         }
      }
   ) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).shift(shift: Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$shift`, shift) {
      {
         super(1);
         this.$this_shift = `$receiver`;
         this.$shift = `$shift`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return this.$this_shift.invoke(t + this.$shift) as java.lang.Float;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).timeDilate(dilation: Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$timeDilate`, dilation) {
      {
         super(1);
         this.$this_timeDilate = `$receiver`;
         this.$dilation = `$dilation`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return this.$this_timeDilate.invoke(t * this.$dilation) as java.lang.Float;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).min(other: Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$min`, other) {
      {
         super(1);
         this.$this_min = `$receiver`;
         this.$other = `$other`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return Math.min((this.$this_min.invoke(t) as java.lang.Number).floatValue(), this.$other);
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).max(other: Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$max`, other) {
      {
         super(1);
         this.$this_max = `$receiver`;
         this.$other = `$other`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return Math.max((this.$this_max.invoke(t) as java.lang.Number).floatValue(), this.$other);
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).clamp(min: Float, max: Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(`$this$clamp`, min, max) {
      {
         super(1);
         this.$this_clamp = `$receiver`;
         this.$min = `$min`;
         this.$max = `$max`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return RangesKt.coerceIn((this.$this_clamp.invoke(t) as java.lang.Number).floatValue(), this.$min, this.$max);
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun ((Float) -> Float).aggregate(func: (Float) -> Float): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(func, `$this$aggregate`) {
      {
         super(1);
         this.$func = `$func`;
         this.$this_aggregate = `$receiver`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return this.$func.invoke(this.$this_aggregate.invoke(t)) as java.lang.Float;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

public fun linearFunction(gradient: Float = 1.0F, yIntercept: Float = 0.0F): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(gradient, yIntercept) {
      {
         super(1);
         this.$gradient = `$gradient`;
         this.$yIntercept = `$yIntercept`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return this.$gradient * t + this.$yIntercept;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `linearFunction$default`(var0: Float, var1: Float, var2: Int, var3: Any): Function1 {
   if ((var2 and 1) != 0) {
      var0 = 1.0F;
   }

   if ((var2 and 2) != 0) {
      var1 = 0.0F;
   }

   return linearFunction(var0, var1);
}

public fun sineFunction(amplitude: Float = 1.0F, period: Float = 1.0F, phaseShift: Float = 0.0F, verticalShift: Float = 0.0F): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(period, phaseShift, amplitude, verticalShift) {
      {
         super(1);
         this.$period = `$period`;
         this.$phaseShift = `$phaseShift`;
         this.$amplitude = `$amplitude`;
         this.$verticalShift = `$verticalShift`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return (float)Math.sin((double)((float) (Math.PI * 2) / this.$period * (t - this.$phaseShift))) * this.$amplitude + this.$verticalShift;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `sineFunction$default`(var0: Float, var1: Float, var2: Float, var3: Float, var4: Int, var5: Any): Function1 {
   if ((var4 and 1) != 0) {
      var0 = 1.0F;
   }

   if ((var4 and 2) != 0) {
      var1 = 1.0F;
   }

   if ((var4 and 4) != 0) {
      var2 = 0.0F;
   }

   if ((var4 and 8) != 0) {
      var3 = 0.0F;
   }

   return sineFunction(var0, var1, var2, var3);
}

public fun cosineFunction(amplitude: Float = 1.0F, period: Float = 1.0F, phaseShift: Float = 0.0F, verticalShift: Float = 0.0F): (Float) -> Float {
   return (new Function1<java.lang.Float, java.lang.Float>(period, phaseShift, amplitude, verticalShift) {
      {
         super(1);
         this.$period = `$period`;
         this.$phaseShift = `$phaseShift`;
         this.$amplitude = `$amplitude`;
         this.$verticalShift = `$verticalShift`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         return (float)Math.cos((double)((float) (Math.PI * 2) / this.$period * (t - this.$phaseShift))) * this.$amplitude + this.$verticalShift;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `cosineFunction$default`(var0: Float, var1: Float, var2: Float, var3: Float, var4: Int, var5: Any): Function1 {
   if ((var4 and 1) != 0) {
      var0 = 1.0F;
   }

   if ((var4 and 2) != 0) {
      var1 = 1.0F;
   }

   if ((var4 and 4) != 0) {
      var2 = 0.0F;
   }

   if ((var4 and 8) != 0) {
      var3 = 0.0F;
   }

   return cosineFunction(var0, var1, var2, var3);
}

public fun triangleFunction(amplitude: Float = 1.0F, period: Float = 1.0F, phaseShift: Float = 0.0F, verticalShift: Float = 0.0F): (Float) -> Float {
   return (
      new Function1<java.lang.Float, java.lang.Float>(period, phaseShift, amplitude, verticalShift) {
         {
            super(1);
            this.$period = `$period`;
            this.$phaseShift = `$phaseShift`;
            this.$amplitude = `$amplitude`;
            this.$verticalShift = `$verticalShift`;
         }

         @NotNull
         public final java.lang.Float invoke(float t) {
            var time: Float = t;

            while (time < 0.0F) {
               time += this.$period;
            }

            return (float)4
                  * this.$amplitude
                  / this.$period
                  * Math.abs((time + (float)3 * this.$period / (float)4 - this.$phaseShift) % this.$period - this.$period / (float)2)
               - this.$amplitude
               + this.$verticalShift;
         }
      }
   ) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `triangleFunction$default`(var0: Float, var1: Float, var2: Float, var3: Float, var4: Int, var5: Any): Function1 {
   if ((var4 and 1) != 0) {
      var0 = 1.0F;
   }

   if ((var4 and 2) != 0) {
      var1 = 1.0F;
   }

   if ((var4 and 4) != 0) {
      var2 = 0.0F;
   }

   if ((var4 and 8) != 0) {
      var3 = 0.0F;
   }

   return triangleFunction(var0, var1, var2, var3);
}

public fun parabolaFunction(peak: Float, period: Float): (Float) -> Float {
   return parabolaFunction((float)-4 * peak / (float)Math.pow((double)period, (double)2), period / (float)2, peak);
}

public fun parabolaFunction(tightness: Float = -1.0F, phaseShift: Float = 0.0F, verticalShift: Float = 1.0F): (Float) -> Float {
   val b: Float = -2 * phaseShift * tightness;
   val c: Float = tightness * phaseShift * phaseShift + verticalShift;
   val root1: Float = (-b - Mth.m_14116_(b * b - (float)4 * tightness * c)) / (2 * tightness);
   val root2: Float = (-b + Mth.m_14116_(b * b - (float)4 * tightness * c)) / (2 * tightness);
   val tMin: Float = if (root1 < root2) root1 else root2;
   val tMax: Float = if (root1 < root2) root2 else root1;
   val period: Float = tMax - tMin;
   return (new Function1<java.lang.Float, java.lang.Float>(tMin, period, tMax, tightness, phaseShift, verticalShift) {
      {
         super(1);
         this.$tMin = `$tMin`;
         this.$period = `$period`;
         this.$tMax = `$tMax`;
         this.$tightness = `$tightness`;
         this.$phaseShift = `$phaseShift`;
         this.$verticalShift = `$verticalShift`;
      }

      @NotNull
      public final java.lang.Float invoke(float t) {
         var time: Float = t;

         while (time < this.$tMin) {
            time += this.$period;
         }

         while (time > this.$tMax) {
            time -= this.$period;
         }

         return this.$tightness * (float)Math.pow((double)(time - this.$phaseShift), (double)2) + this.$verticalShift;
      }
   }) as (java.lang.Float?) -> java.lang.Float;
}

@JvmSynthetic
fun `parabolaFunction$default`(var0: Float, var1: Float, var2: Float, var3: Int, var4: Any): Function1 {
   if ((var3 and 1) != 0) {
      var0 = -1.0F;
   }

   if ((var3 and 2) != 0) {
      var1 = 0.0F;
   }

   if ((var3 and 4) != 0) {
      var2 = 1.0F;
   }

   return parabolaFunction(var0, var1, var2);
}
