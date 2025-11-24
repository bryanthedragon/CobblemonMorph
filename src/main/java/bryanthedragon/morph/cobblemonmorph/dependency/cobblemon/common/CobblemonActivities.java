/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.schedule.Activity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0006\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/CobblemonActivities;", "", "Lnet/minecraft/world/entity/schedule/Activity;", "activity", "register", "(Lnet/minecraft/world/entity/schedule/Activity;)Lnet/minecraft/world/entity/schedule/Activity;", "BATTLING_ACTIVITY", "Lnet/minecraft/world/entity/schedule/Activity;", "getBATTLING_ACTIVITY", "()Lnet/minecraft/world/entity/schedule/Activity;", "", "activities", "Ljava/util/List;", "getActivities", "()Ljava/util/List;", "<init>", "()V", "common"})
public final class CobblemonActivities {
    @NotNull
    public static final CobblemonActivities INSTANCE = new CobblemonActivities();
    @NotNull
    private static final List<Activity> activities = new ArrayList();
    @NotNull
    private static final Activity BATTLING_ACTIVITY = new Activity("pokemon_battling");

    private CobblemonActivities() {
    }

    @NotNull
    public final List<Activity> getActivities() {
        return activities;
    }

    @NotNull
    public final Activity getBATTLING_ACTIVITY() {
        return BATTLING_ACTIVITY;
    }

    @NotNull
    public final Activity register(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter((Object)activity, (String)"activity");
        activities.add(activity);
        return activity;
    }
}

