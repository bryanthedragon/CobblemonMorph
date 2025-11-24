/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\rR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/RangeOfMotion;", "", "", "high", "F", "getHigh", "()F", "setHigh", "(F)V", "low", "getLow", "setLow", "<init>", "(FF)V", "common"})
public final class RangeOfMotion {
    private float low;
    private float high;

    public RangeOfMotion(float low, float high) {
        this.low = low;
        this.high = high;
    }

    public final float getLow() {
        return this.low;
    }

    public final void setLow(float f) {
        this.low = f;
    }

    public final float getHigh() {
        return this.high;
    }

    public final void setHigh(float f) {
        this.high = f;
    }
}

