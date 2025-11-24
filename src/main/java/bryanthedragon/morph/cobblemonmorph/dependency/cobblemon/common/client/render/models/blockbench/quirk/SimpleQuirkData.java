/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.Entity
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.QuirkData;
import kotlin.Metadata;
import net.minecraft.world.entity.Entity;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013R\"\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirkData;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "", "nextOccurrenceSeconds", "F", "getNextOccurrenceSeconds", "()F", "setNextOccurrenceSeconds", "(F)V", "", "remainingLoops", "I", "getRemainingLoops", "()I", "setRemainingLoops", "(I)V", "<init>", "()V", "common"})
public final class SimpleQuirkData<T extends Entity>
extends QuirkData<T> {
    private float nextOccurrenceSeconds = -1.0f;
    private int remainingLoops;

    public final float getNextOccurrenceSeconds() {
        return this.nextOccurrenceSeconds;
    }

    public final void setNextOccurrenceSeconds(float f) {
        this.nextOccurrenceSeconds = f;
    }

    public final int getRemainingLoops() {
        return this.remainingLoops;
    }

    public final void setRemainingLoops(int n) {
        this.remainingLoops = n;
    }
}

