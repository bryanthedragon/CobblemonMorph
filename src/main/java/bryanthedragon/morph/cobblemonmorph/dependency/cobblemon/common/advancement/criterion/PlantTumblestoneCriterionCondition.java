/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0007\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneContext;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneContext;)Z", "toJson", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "predicate", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
public final class PlantTumblestoneCriterionCondition
extends SimpleCriterionCondition<PlantTumblestoneContext> {
    public PlantTumblestoneCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        super(id, predicate);
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull PlantTumblestoneContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        TumblestoneBlock tumblestoneBlock = context.getTumbleStoneBlock();
        BlockPos blockPos2 = context.getPos();
        Level level = player.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"player.world");
        return tumblestoneBlock.canGrow(blockPos2, (BlockGetter)level);
    }
}

