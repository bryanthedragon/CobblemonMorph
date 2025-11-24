/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u001e\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a'\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0002j\u0002`\u00032\b\b\u0002\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a1\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00000\u0002j\u0002`\t2\b\b\u0002\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\u0000\u00a2\u0006\u0004\b\n\u0010\u000b\u001a'\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0002j\u0002`\u00032\b\b\u0002\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\f\u0010\u0005*\"\u0010\r\"\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00000\u00022\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00000\u0002*\"\u0010\u000e\"\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u00022\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00000\u0002\u00a8\u0006\u000f"}, d2={"", "period", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/RootFunction;", "cosineFunction", "(F)Lkotlin/jvm/functions/Function1;", "base", "step", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/AmplitudeFunction;", "gradualFunction", "(FF)Lkotlin/jvm/functions/Function1;", "sineFunction", "AmplitudeFunction", "RootFunction", "common"})
public final class CascadeAnimationKt {
    @NotNull
    public static final Function1<Integer, Float> gradualFunction(float base, float step) {
        return (Function1)new Function1<Integer, Float>(base, step){
            final /* synthetic */ float $base;
            final /* synthetic */ float $step;
            {
                this.$base = $base;
                this.$step = $step;
                super(1);
            }

            @NotNull
            public final Float invoke(int index) {
                return Float.valueOf(this.$base + this.$step * (float)index);
            }
        };
    }

    public static /* synthetic */ Function1 gradualFunction$default(float f, float f2, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        if ((n & 2) != 0) {
            f2 = 1.0f;
        }
        return CascadeAnimationKt.gradualFunction(f, f2);
    }

    @NotNull
    public static final Function1<Float, Float> cosineFunction(float period) {
        return (Function1)new Function1<Float, Float>(period){
            final /* synthetic */ float $period;
            {
                this.$period = $period;
                super(1);
            }

            @NotNull
            public final Float invoke(float x) {
                return Float.valueOf((float)Math.cos(x * this.$period));
            }
        };
    }

    public static /* synthetic */ Function1 cosineFunction$default(float f, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        return CascadeAnimationKt.cosineFunction(f);
    }

    @NotNull
    public static final Function1<Float, Float> sineFunction(float period) {
        return (Function1)new Function1<Float, Float>(period){
            final /* synthetic */ float $period;
            {
                this.$period = $period;
                super(1);
            }

            @NotNull
            public final Float invoke(float x) {
                return Float.valueOf((float)Math.sin(x * this.$period));
            }
        };
    }

    public static /* synthetic */ Function1 sineFunction$default(float f, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        return CascadeAnimationKt.sineFunction(f);
    }
}

