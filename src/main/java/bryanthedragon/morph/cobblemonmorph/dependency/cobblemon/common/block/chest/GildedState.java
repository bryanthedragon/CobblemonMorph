/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import kotlin.Metadata;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0011\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/block/chest/GildedState;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lnet/minecraft/world/entity/Entity;", "", "getEntity", "()Ljava/lang/Void;", "", "partialTicks", "", "updatePartialTicks", "(F)V", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "<init>", "()V", "common"})
public final class GildedState
extends PoseableEntityState<Entity> {
    @NotNull
    private final SchedulingTracker schedulingTracker;

    public GildedState() {
        this.setPose("CLOSED");
        this.schedulingTracker = new SchedulingTracker();
    }

    @Override
    @Nullable
    public Void getEntity() {
        return null;
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks);
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }
}

