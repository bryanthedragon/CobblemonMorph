/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0006\u0012\b\b\u0002\u0010 \u001a\u00020\t\u00a2\u0006\u0004\b!\u0010\"J\u0011\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\"\u0010\u001a\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\r\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilState;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lnet/minecraft/world/entity/Entity;", "", "getEntity", "()Ljava/lang/Void;", "", "peekAge", "()I", "", "partialTicks", "", "updatePartialTicks", "(F)V", "", "growthState", "Ljava/lang/String;", "getGrowthState", "()Ljava/lang/String;", "setGrowthState", "(Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "totalPartialTicks", "F", "getTotalPartialTicks", "()F", "setTotalPartialTicks", "startAge", "startPartialTicks", "<init>", "(IF)V", "common"})
public final class FossilState
extends PoseableEntityState<Entity> {
    private float totalPartialTicks;
    @NotNull
    private String growthState;
    @NotNull
    private final SchedulingTracker schedulingTracker;

    public FossilState(int startAge, float startPartialTicks) {
        this.setAge(startAge >= 0 ? startAge : (int)((double)200.0f * Math.random()));
        this.setCurrentPartialTicks((float)startAge > 0.0f ? startPartialTicks : 0.0f);
        this.growthState = "Embryo";
        this.schedulingTracker = new SchedulingTracker();
    }

    public /* synthetic */ FossilState(int n, float f, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = -1;
        }
        if ((n2 & 2) != 0) {
            f = 0.0f;
        }
        this(n, f);
    }

    public final float getTotalPartialTicks() {
        return this.totalPartialTicks;
    }

    public final void setTotalPartialTicks(float f) {
        this.totalPartialTicks = f;
    }

    @Override
    @Nullable
    public Void getEntity() {
        return null;
    }

    public final int peekAge() {
        return this.getAge();
    }

    @NotNull
    public final String getGrowthState() {
        return this.growthState;
    }

    public final void setGrowthState(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.growthState = string;
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks / (float)2);
        this.totalPartialTicks += partialTicks / (float)2;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    public FossilState() {
        this(0, 0.0f, 3, null);
    }
}

