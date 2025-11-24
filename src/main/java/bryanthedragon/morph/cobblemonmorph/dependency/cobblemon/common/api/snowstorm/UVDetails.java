/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\"\u0010\u0005\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0005\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0006\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\n\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\"\u0010\u0003\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\n\u001a\u0004\b\u0011\u0010\f\"\u0004\b\u0012\u0010\u000eR\"\u0010\u0004\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\n\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "", "", "startU", "startV", "endU", "endV", "set", "(DDDD)Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "", "F", "getEndU", "()F", "setEndU", "(F)V", "getEndV", "setEndV", "getStartU", "setStartU", "getStartV", "setStartV", "<init>", "()V", "common"})
public final class UVDetails {
    private float startU;
    private float startV;
    private float endU;
    private float endV;

    public final float getStartU() {
        return this.startU;
    }

    public final void setStartU(float f) {
        this.startU = f;
    }

    public final float getStartV() {
        return this.startV;
    }

    public final void setStartV(float f) {
        this.startV = f;
    }

    public final float getEndU() {
        return this.endU;
    }

    public final void setEndU(float f) {
        this.endU = f;
    }

    public final float getEndV() {
        return this.endV;
    }

    public final void setEndV(float f) {
        this.endV = f;
    }

    @NotNull
    public final UVDetails set(double startU, double startV, double endU, double endV) {
        this.startU = (float)startU;
        this.startV = (float)startV;
        this.endU = (float)endU;
        this.endV = (float)endV;
        return this;
    }
}

