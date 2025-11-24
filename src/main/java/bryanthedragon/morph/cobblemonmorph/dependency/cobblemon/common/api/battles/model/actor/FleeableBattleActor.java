/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;

import kotlin.Metadata;
import kotlin.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u001d\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00078&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/battles/model/actor/FleeableBattleActor;", "", "Lkotlin/Pair;", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/world/phys/Vec3;", "getWorldAndPosition", "()Lkotlin/Pair;", "", "getFleeDistance", "()F", "fleeDistance", "common"})
public interface FleeableBattleActor {
    public float getFleeDistance();

    @Nullable
    public Pair<ServerLevel, Vec3> getWorldAndPosition();
}

