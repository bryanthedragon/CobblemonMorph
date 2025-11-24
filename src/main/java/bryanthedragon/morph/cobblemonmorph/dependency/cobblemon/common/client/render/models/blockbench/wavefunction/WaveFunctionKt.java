/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\"\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aE\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0007\u0010\b\u001a5\u0010\f\u001a\u00020\u00002\u0016\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0000\u00a2\u0006\u0004\b\f\u0010\r\u001a1\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\b\b\u0002\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u000e\u001a\u00020\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a-\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0010\u001a;\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0014\u001aE\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0015\u0010\b\u001aE\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\b\b\u0002\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0016\u0010\b\u001aI\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001aA\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a9\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\u001e\u001a\u00020\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001f\u001a9\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\u001e\u001a\u00020\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001f\u001aL\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0016\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006H\u0086\u0002\u00a2\u0006\u0004\b \u0010\u0019\u001aA\u0010!\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0000\u00a2\u0006\u0004\b!\u0010\u001d\u001a9\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010\"\u001a\u00020\u0000\u00a2\u0006\u0004\b\"\u0010\u001f\u001a9\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0006\u0010#\u001a\u00020\u0000\u00a2\u0006\u0004\b$\u0010\u001f\u001aL\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006*\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u00062\u0016\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005j\u0002`\u0006H\u0086\u0002\u00a2\u0006\u0004\b%\u0010\u0019*:\u0010'\"\u001a\u0012\u0004\u0012\u00020\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000&0\u00052\u001a\u0012\u0004\u0012\u00020\u0000\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000&0\u0005*F\u0010)\" \u0012\u0004\u0012\u00020\u0000\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000(0\u00052 \u0012\u0004\u0012\u00020\u0000\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000(0\u0005*\"\u0010*\"\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u00052\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0005\u00a8\u0006+"}, d2={"", "amplitude", "period", "phaseShift", "verticalShift", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "cosineFunction", "(FFFF)Lkotlin/jvm/functions/Function1;", "function", "t0", "t1", "gradient", "(Lkotlin/jvm/functions/Function1;FF)F", "yIntercept", "linearFunction", "(FF)Lkotlin/jvm/functions/Function1;", "peak", "parabolaFunction", "tightness", "(FFF)Lkotlin/jvm/functions/Function1;", "sineFunction", "triangleFunction", "func", "aggregate", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "min", "max", "clamp", "(Lkotlin/jvm/functions/Function1;FF)Lkotlin/jvm/functions/Function1;", "other", "(Lkotlin/jvm/functions/Function1;F)Lkotlin/jvm/functions/Function1;", "plus", "rerange", "shift", "dilation", "timeDilate", "times", "Lkotlin/Pair;", "CoFunction", "Lkotlin/Triple;", "TriFunction", "WaveFunction", "common"})
public final class WaveFunctionKt {
    public static final float gradient(@NotNull Function1<? super Float, Float> function, float t0, float t1) {
        Intrinsics.checkNotNullParameter(function, (String)"function");
        return (((Number)function.invoke((Object)Float.valueOf(t1))).floatValue() - ((Number)function.invoke((Object)Float.valueOf(t0))).floatValue()) / (t1 - t0);
    }

    @NotNull
    public static final Function1<Float, Float> plus(@NotNull Function1<? super Float, Float> $this$plus, @NotNull Function1<? super Float, Float> other) {
        Intrinsics.checkNotNullParameter($this$plus, (String)"<this>");
        Intrinsics.checkNotNullParameter(other, (String)"other");
        return (Function1)new Function1<Float, Float>($this$plus, other){
            final /* synthetic */ Function1<Float, Float> $this_plus;
            final /* synthetic */ Function1<Float, Float> $other;
            {
                this.$this_plus = $receiver;
                this.$other = $other;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(((Number)this.$this_plus.invoke((Object)Float.valueOf(t))).floatValue() + ((Number)this.$other.invoke((Object)Float.valueOf(t))).floatValue());
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> times(@NotNull Function1<? super Float, Float> $this$times, @NotNull Function1<? super Float, Float> other) {
        Intrinsics.checkNotNullParameter($this$times, (String)"<this>");
        Intrinsics.checkNotNullParameter(other, (String)"other");
        return (Function1)new Function1<Float, Float>($this$times, other){
            final /* synthetic */ Function1<Float, Float> $this_times;
            final /* synthetic */ Function1<Float, Float> $other;
            {
                this.$this_times = $receiver;
                this.$other = $other;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(((Number)this.$this_times.invoke((Object)Float.valueOf(t))).floatValue() * ((Number)this.$other.invoke((Object)Float.valueOf(t))).floatValue());
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> rerange(@NotNull Function1<? super Float, Float> $this$rerange, float min2, float max2) {
        Intrinsics.checkNotNullParameter($this$rerange, (String)"<this>");
        return (Function1)new Function1<Float, Float>(min2, max2, $this$rerange){
            final /* synthetic */ float $min;
            final /* synthetic */ float $max;
            final /* synthetic */ Function1<Float, Float> $this_rerange;
            {
                this.$min = $min;
                this.$max = $max;
                this.$this_rerange = $receiver;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                float f;
                boolean bl = this.$min <= t ? t <= this.$max : false;
                if (bl) {
                    float newTime = (t - this.$min) / (this.$max - this.$min);
                    f = ((Number)this.$this_rerange.invoke((Object)Float.valueOf(newTime))).floatValue();
                } else {
                    f = ((Number)this.$this_rerange.invoke((Object)Float.valueOf(0.0f))).floatValue();
                }
                return Float.valueOf(f);
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> shift(@NotNull Function1<? super Float, Float> $this$shift, float shift2) {
        Intrinsics.checkNotNullParameter($this$shift, (String)"<this>");
        return (Function1)new Function1<Float, Float>($this$shift, shift2){
            final /* synthetic */ Function1<Float, Float> $this_shift;
            final /* synthetic */ float $shift;
            {
                this.$this_shift = $receiver;
                this.$shift = $shift;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return (Float)this.$this_shift.invoke((Object)Float.valueOf(t + this.$shift));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> timeDilate(@NotNull Function1<? super Float, Float> $this$timeDilate, float dilation) {
        Intrinsics.checkNotNullParameter($this$timeDilate, (String)"<this>");
        return (Function1)new Function1<Float, Float>($this$timeDilate, dilation){
            final /* synthetic */ Function1<Float, Float> $this_timeDilate;
            final /* synthetic */ float $dilation;
            {
                this.$this_timeDilate = $receiver;
                this.$dilation = $dilation;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return (Float)this.$this_timeDilate.invoke((Object)Float.valueOf(t * this.$dilation));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> min(@NotNull Function1<? super Float, Float> $this$min, float other) {
        Intrinsics.checkNotNullParameter($this$min, (String)"<this>");
        return (Function1)new Function1<Float, Float>($this$min, other){
            final /* synthetic */ Function1<Float, Float> $this_min;
            final /* synthetic */ float $other;
            {
                this.$this_min = $receiver;
                this.$other = $other;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(Math.min(((Number)this.$this_min.invoke((Object)Float.valueOf(t))).floatValue(), this.$other));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> max(@NotNull Function1<? super Float, Float> $this$max, float other) {
        Intrinsics.checkNotNullParameter($this$max, (String)"<this>");
        return (Function1)new Function1<Float, Float>($this$max, other){
            final /* synthetic */ Function1<Float, Float> $this_max;
            final /* synthetic */ float $other;
            {
                this.$this_max = $receiver;
                this.$other = $other;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(Math.max(((Number)this.$this_max.invoke((Object)Float.valueOf(t))).floatValue(), this.$other));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> clamp(@NotNull Function1<? super Float, Float> $this$clamp, float min2, float max2) {
        Intrinsics.checkNotNullParameter($this$clamp, (String)"<this>");
        return (Function1)new Function1<Float, Float>($this$clamp, min2, max2){
            final /* synthetic */ Function1<Float, Float> $this_clamp;
            final /* synthetic */ float $min;
            final /* synthetic */ float $max;
            {
                this.$this_clamp = $receiver;
                this.$min = $min;
                this.$max = $max;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(RangesKt.coerceIn((float)((Number)this.$this_clamp.invoke((Object)Float.valueOf(t))).floatValue(), (float)this.$min, (float)this.$max));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> aggregate(@NotNull Function1<? super Float, Float> $this$aggregate, @NotNull Function1<? super Float, Float> func) {
        Intrinsics.checkNotNullParameter($this$aggregate, (String)"<this>");
        Intrinsics.checkNotNullParameter(func, (String)"func");
        return (Function1)new Function1<Float, Float>(func, $this$aggregate){
            final /* synthetic */ Function1<Float, Float> $func;
            final /* synthetic */ Function1<Float, Float> $this_aggregate;
            {
                this.$func = $func;
                this.$this_aggregate = $receiver;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return (Float)this.$func.invoke(this.$this_aggregate.invoke((Object)Float.valueOf(t)));
            }
        };
    }

    @NotNull
    public static final Function1<Float, Float> linearFunction(float gradient, float yIntercept) {
        return (Function1)new Function1<Float, Float>(gradient, yIntercept){
            final /* synthetic */ float $gradient;
            final /* synthetic */ float $yIntercept;
            {
                this.$gradient = $gradient;
                this.$yIntercept = $yIntercept;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf(this.$gradient * t + this.$yIntercept);
            }
        };
    }

    public static /* synthetic */ Function1 linearFunction$default(float f, float f2, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 0.0f;
        }
        return WaveFunctionKt.linearFunction(f, f2);
    }

    @NotNull
    public static final Function1<Float, Float> sineFunction(float amplitude, float period, float phaseShift, float verticalShift) {
        return (Function1)new Function1<Float, Float>(period, phaseShift, amplitude, verticalShift){
            final /* synthetic */ float $period;
            final /* synthetic */ float $phaseShift;
            final /* synthetic */ float $amplitude;
            final /* synthetic */ float $verticalShift;
            {
                this.$period = $period;
                this.$phaseShift = $phaseShift;
                this.$amplitude = $amplitude;
                this.$verticalShift = $verticalShift;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf((float)Math.sin((float)Math.PI * 2 / this.$period * (t - this.$phaseShift)) * this.$amplitude + this.$verticalShift);
            }
        };
    }

    public static /* synthetic */ Function1 sineFunction$default(float f, float f2, float f3, float f4, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 1.0f;
        }
        if ((n & 4) != 0) {
            f3 = 0.0f;
        }
        if ((n & 8) != 0) {
            f4 = 0.0f;
        }
        return WaveFunctionKt.sineFunction(f, f2, f3, f4);
    }

    @NotNull
    public static final Function1<Float, Float> cosineFunction(float amplitude, float period, float phaseShift, float verticalShift) {
        return (Function1)new Function1<Float, Float>(period, phaseShift, amplitude, verticalShift){
            final /* synthetic */ float $period;
            final /* synthetic */ float $phaseShift;
            final /* synthetic */ float $amplitude;
            final /* synthetic */ float $verticalShift;
            {
                this.$period = $period;
                this.$phaseShift = $phaseShift;
                this.$amplitude = $amplitude;
                this.$verticalShift = $verticalShift;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                return Float.valueOf((float)Math.cos((float)Math.PI * 2 / this.$period * (t - this.$phaseShift)) * this.$amplitude + this.$verticalShift);
            }
        };
    }

    public static /* synthetic */ Function1 cosineFunction$default(float f, float f2, float f3, float f4, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 1.0f;
        }
        if ((n & 4) != 0) {
            f3 = 0.0f;
        }
        if ((n & 8) != 0) {
            f4 = 0.0f;
        }
        return WaveFunctionKt.cosineFunction(f, f2, f3, f4);
    }

    @NotNull
    public static final Function1<Float, Float> triangleFunction(float amplitude, float period, float phaseShift, float verticalShift) {
        return (Function1)new Function1<Float, Float>(period, phaseShift, amplitude, verticalShift){
            final /* synthetic */ float $period;
            final /* synthetic */ float $phaseShift;
            final /* synthetic */ float $amplitude;
            final /* synthetic */ float $verticalShift;
            {
                this.$period = $period;
                this.$phaseShift = $phaseShift;
                this.$amplitude = $amplitude;
                this.$verticalShift = $verticalShift;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                float time;
                for (time = t; time < 0.0f; time += this.$period) {
                }
                float timeTerm = (time + (float)3 * this.$period / (float)4 - this.$phaseShift) % this.$period - this.$period / (float)2;
                float value2 = (float)4 * this.$amplitude / this.$period * Math.abs(timeTerm) - this.$amplitude + this.$verticalShift;
                return Float.valueOf(value2);
            }
        };
    }

    public static /* synthetic */ Function1 triangleFunction$default(float f, float f2, float f3, float f4, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 1.0f;
        }
        if ((n & 4) != 0) {
            f3 = 0.0f;
        }
        if ((n & 8) != 0) {
            f4 = 0.0f;
        }
        return WaveFunctionKt.triangleFunction(f, f2, f3, f4);
    }

    @NotNull
    public static final Function1<Float, Float> parabolaFunction(float peak, float period) {
        float f = (float)-4 * peak / (float)Math.pow(period, 2);
        float f2 = period / (float)2;
        return WaveFunctionKt.parabolaFunction(f, f2, peak);
    }

    @NotNull
    public static final Function1<Float, Float> parabolaFunction(float tightness, float phaseShift, float verticalShift) {
        float root2;
        float b = (float)-2 * phaseShift * tightness;
        float a = tightness;
        float c = tightness * phaseShift * phaseShift + verticalShift;
        float root1 = (-b - Mth.m_14116_((float)(b * b - (float)4 * a * c))) / ((float)2 * tightness);
        float tMin = root1 < (root2 = (-b + Mth.m_14116_((float)(b * b - (float)4 * a * c))) / ((float)2 * tightness)) ? root1 : root2;
        float tMax = root1 < root2 ? root2 : root1;
        float period = tMax - tMin;
        return (Function1)new Function1<Float, Float>(tMin, period, tMax, tightness, phaseShift, verticalShift){
            final /* synthetic */ float $tMin;
            final /* synthetic */ float $period;
            final /* synthetic */ float $tMax;
            final /* synthetic */ float $tightness;
            final /* synthetic */ float $phaseShift;
            final /* synthetic */ float $verticalShift;
            {
                this.$tMin = $tMin;
                this.$period = $period;
                this.$tMax = $tMax;
                this.$tightness = $tightness;
                this.$phaseShift = $phaseShift;
                this.$verticalShift = $verticalShift;
                super(1);
            }

            @NotNull
            public final Float invoke(float t) {
                float time;
                for (time = t; time < this.$tMin; time += this.$period) {
                }
                while (time > this.$tMax) {
                    time -= this.$period;
                }
                return Float.valueOf(this.$tightness * (float)Math.pow(time - this.$phaseShift, 2) + this.$verticalShift);
            }
        };
    }

    public static /* synthetic */ Function1 parabolaFunction$default(float f, float f2, float f3, int n, Object object) {
        if ((n & 1) != 0) {
            f = -1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 0.0f;
        }
        if ((n & 4) != 0) {
            f3 = 1.0f;
        }
        return WaveFunctionKt.parabolaFunction(f, f2, f3);
    }
}

