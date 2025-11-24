/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.RandomStrollGoal
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u001d\u0010\n\u001a\u0004\u0018\u00010\t2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0004\b\f\u0010\rJ\u0011\u0010\u000e\u001a\u0004\u0018\u00010\tH\u0014\u00a2\u0006\u0004\b\u000e\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0004R\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonWanderAroundGoal;", "Lnet/minecraft/world/entity/ai/goal/RandomStrollGoal;", "", "canMove", "()Z", "canStart", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/material/Fluid;", "fluidTag", "Lnet/minecraft/world/phys/Vec3;", "getFluidTarget", "(Lnet/minecraft/tags/TagKey;)Lnet/minecraft/world/phys/Vec3;", "getLandTarget", "()Lnet/minecraft/world/phys/Vec3;", "getWanderTarget", "shouldContinue", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonWanderAroundGoal.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonWanderAroundGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonWanderAroundGoal\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,149:1\n1#2:150\n*E\n"})
public final class PokemonWanderAroundGoal
extends RandomStrollGoal {
    @NotNull
    private final PokemonEntity entity;

    public PokemonWanderAroundGoal(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super((PathfinderMob)entity2, entity2.getBehaviour().getMoving().getWanderSpeed());
        this.entity = entity2;
        this.f_25730_ = this.entity.getBehaviour().getMoving().getWanderChance();
    }

    @NotNull
    public final PokemonEntity getEntity() {
        return this.entity;
    }

    public final boolean canMove() {
        MoveBehaviour moving2 = this.entity.getBehaviour().getMoving();
        return moving2.getWalk().getCanWalk() || moving2.getFly().getCanFly() || moving2.getSwim().getCanBreatheUnderwater() && this.f_25725_.m_204029_(FluidTags.f_13131_);
    }

    public boolean m_8036_() {
        return super.m_8036_() && this.canMove() && !this.entity.isBusy() && (this.entity.m_21805_() == null || this.entity.getTethering() != null);
    }

    public boolean m_8045_() {
        return super.m_8045_() && this.canMove() && !this.entity.isBusy();
    }

    @Nullable
    protected Vec3 m_7037_() {
        MoveBehaviour moving2 = this.entity.getBehaviour().getMoving();
        if (this.entity.m_204029_(FluidTags.f_13131_) && moving2.getSwim().getCanBreatheUnderwater()) {
            TagKey tagKey = FluidTags.f_13131_;
            Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"WATER");
            Vec3 vec3 = this.getFluidTarget((TagKey<Fluid>)tagKey);
            if (vec3 != null) {
                Vec3 it = vec3;
                boolean bl = false;
                return it;
            }
        } else {
            if (this.entity.m_204029_(FluidTags.f_13132_) && moving2.getSwim().getCanBreatheUnderlava()) {
                TagKey tagKey = FluidTags.f_13132_;
                Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"LAVA");
                return this.getFluidTarget((TagKey<Fluid>)tagKey);
            }
            if (this.entity.m_20069_() && moving2.getSwim().getCanWalkOnWater()) {
                TagKey tagKey = FluidTags.f_13131_;
                Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"WATER");
                Vec3 vec3 = this.getFluidTarget((TagKey<Fluid>)tagKey);
                if (vec3 != null) {
                    Vec3 it = vec3;
                    boolean bl = false;
                    return it;
                }
            } else if (this.entity.m_20077_() && moving2.getSwim().getCanWalkOnLava()) {
                TagKey tagKey = FluidTags.f_13132_;
                Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"LAVA");
                Vec3 vec3 = this.getFluidTarget((TagKey<Fluid>)tagKey);
                if (vec3 != null) {
                    Vec3 it = vec3;
                    boolean bl = false;
                    return it;
                }
            }
        }
        if (moving2.getWalk().getAvoidsLand()) {
            return null;
        }
        return this.getLandTarget();
    }

    @Nullable
    public final Vec3 getLandTarget() {
        Function1 roamDistanceCondition2 = (Function1)new Function1<BlockPos, Boolean>(this){
            final /* synthetic */ PokemonWanderAroundGoal this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull BlockPos it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                PokemonPastureBlockEntity.Tethering tethering = this.this$0.getEntity().getTethering();
                return !(tethering != null ? !tethering.canRoamTo(it) : false);
            }
        };
        Iterable iterable = BlockPos.m_235641_((RandomSource)this.entity.m_217043_(), (int)64, (int)(this.entity.m_146903_() - 10), (int)this.entity.m_146904_(), (int)(this.entity.m_146907_() - 10), (int)(this.entity.m_146903_() + 10), (int)this.entity.m_146904_(), (int)(this.entity.m_146907_() + 10));
        Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"iterateRandomly(entity.r\u2026ockY, entity.blockZ + 10)");
        Iterable iterable2 = iterable;
        Function2 condition2 = (Function2)new Function2<BlockState, BlockPos, Boolean>(this, (Function1<? super BlockPos, Boolean>)roamDistanceCondition2){
            final /* synthetic */ PokemonWanderAroundGoal this$0;
            final /* synthetic */ Function1<BlockPos, Boolean> $roamDistanceCondition;
            {
                this.this$0 = $receiver;
                this.$roamDistanceCondition = $roamDistanceCondition;
                super(2);
            }

            @NotNull
            public final Boolean invoke(@NotNull BlockState blockState, @NotNull BlockPos pos) {
                Intrinsics.checkNotNullParameter((Object)blockState, (String)"<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                return WorldExtensionsKt.canFit((Entity)this.this$0.getEntity(), pos) && (Boolean)this.$roamDistanceCondition.invoke((Object)pos) != false;
            }
        };
        Iterator iterator = iterable2.iterator();
        block0: while (iterator.hasNext()) {
            BlockPos.MutableBlockPos pos = ((BlockPos)iterator.next()).m_122032_();
            BlockState blockState = this.entity.m_9236_().m_8055_((BlockPos)pos);
            int maxSteps = 16;
            int steps = 0;
            boolean good = false;
            if (!blockState.m_280296_() && !blockState.m_278721_()) {
                pos.m_122184_(0, -1, 0);
                boolean previousWasAir = true;
                while (steps++ < maxSteps && pos.m_123342_() > this.entity.m_9236_().m_141937_()) {
                    if (pos.m_123342_() <= this.entity.m_9236_().m_141937_()) continue block0;
                    blockState = this.entity.m_9236_().m_8055_((BlockPos)pos);
                    if (blockState.m_280296_() && !blockState.m_204336_(BlockTags.f_13035_) && previousWasAir) {
                        pos.m_122184_(0, 1, 0);
                        blockState = this.entity.m_9236_().m_8055_((BlockPos)pos);
                        good = true;
                        break;
                    }
                    previousWasAir = blockState.m_60795_();
                    pos.m_122184_(0, -1, 0);
                }
            } else {
                boolean previousWasSolid = blockState.m_280296_() && !blockState.m_204336_(BlockTags.f_13035_);
                pos.m_122184_(0, 1, 0);
                while (steps++ < maxSteps) {
                    if (pos.m_123342_() >= this.entity.m_9236_().m_151558_()) continue block0;
                    blockState = this.entity.m_9236_().m_8055_((BlockPos)pos);
                    if (blockState.m_60795_() && previousWasSolid) {
                        good = true;
                        break;
                    }
                    previousWasSolid = blockState.m_280296_() && !blockState.m_204336_(BlockTags.f_13035_);
                    pos.m_122184_(0, 1, 0);
                }
            }
            if (!good) continue;
            BlockState blockState2 = blockState;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"blockState");
            Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
            if (!((Boolean)condition2.invoke((Object)blockState2, (Object)pos)).booleanValue()) continue;
            return BlockPosExtensionsKt.toVec3d((BlockPos)pos);
        }
        return null;
    }

    @Nullable
    public final Vec3 getFluidTarget(@NotNull TagKey<Fluid> fluidTag) {
        Intrinsics.checkNotNullParameter(fluidTag, (String)"fluidTag");
        Function1 roamDistanceCondition2 = (Function1)new Function1<BlockPos, Boolean>(this){
            final /* synthetic */ PokemonWanderAroundGoal this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull BlockPos it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                PokemonPastureBlockEntity.Tethering tethering = this.this$0.getEntity().getTethering();
                return !(tethering != null ? !tethering.canRoamTo(it) : false);
            }
        };
        boolean walksOnFloor = !this.entity.getBehaviour().getMoving().getSwim().canSwimInFluid(fluidTag);
        Iterable iterable = BlockPos.m_235650_((RandomSource)this.entity.m_217043_(), (int)32, (BlockPos)this.entity.m_20183_(), (int)12);
        Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"iterateRandomly(entity.r\u2026 32, entity.blockPos, 12)");
        Iterable iterable2 = iterable;
        Function2 condition2 = (Function2)new Function2<BlockState, BlockPos, Boolean>((Function1<? super BlockPos, Boolean>)roamDistanceCondition2, fluidTag, this){
            final /* synthetic */ Function1<BlockPos, Boolean> $roamDistanceCondition;
            final /* synthetic */ TagKey<Fluid> $fluidTag;
            final /* synthetic */ PokemonWanderAroundGoal this$0;
            {
                this.$roamDistanceCondition = $roamDistanceCondition;
                this.$fluidTag = $fluidTag;
                this.this$0 = $receiver;
                super(2);
            }

            @NotNull
            public final Boolean invoke(@NotNull BlockState blockState, @NotNull BlockPos pos) {
                Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                return (Boolean)this.$roamDistanceCondition.invoke((Object)pos) != false && blockState.m_60819_().m_205070_(this.$fluidTag) && WorldExtensionsKt.canFit((Entity)this.this$0.getEntity(), pos);
            }
        };
        if (walksOnFloor) {
            condition2 = (Function2)new Function2<BlockState, BlockPos, Boolean>(this, (Function1<? super BlockPos, Boolean>)roamDistanceCondition2, fluidTag){
                final /* synthetic */ PokemonWanderAroundGoal this$0;
                final /* synthetic */ Function1<BlockPos, Boolean> $roamDistanceCondition;
                final /* synthetic */ TagKey<Fluid> $fluidTag;
                {
                    this.this$0 = $receiver;
                    this.$roamDistanceCondition = $roamDistanceCondition;
                    this.$fluidTag = $fluidTag;
                    super(2);
                }

                @NotNull
                public final Boolean invoke(@NotNull BlockState blockState, @NotNull BlockPos blockPos2) {
                    Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
                    Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
                    BlockPos down = blockPos2.m_7495_();
                    BlockState below = this.this$0.getEntity().m_9236_().m_8055_(down);
                    return (Boolean)this.$roamDistanceCondition.invoke((Object)blockPos2) != false && blockState.m_60819_().m_205070_(this.$fluidTag) && below.m_60796_((BlockGetter)this.this$0.getEntity().m_9236_(), down) && WorldExtensionsKt.canFit((Entity)this.this$0.getEntity(), blockPos2);
                }
            };
        }
        if (this.entity.m_9236_().m_46859_(this.entity.m_20183_().m_7494_()) && (Intrinsics.areEqual(fluidTag, (Object)FluidTags.f_13131_) && this.entity.getBehaviour().getMoving().getSwim().getCanWalkOnWater() || Intrinsics.areEqual(fluidTag, (Object)FluidTags.f_13132_) && this.entity.getBehaviour().getMoving().getSwim().getCanWalkOnLava())) {
            Iterable iterable3 = BlockPos.m_235641_((RandomSource)this.entity.m_217043_(), (int)16, (int)(this.entity.m_146903_() - 16), (int)this.entity.m_146904_(), (int)(this.entity.m_146907_() - 16), (int)(this.entity.m_146903_() + 16), (int)this.entity.m_146904_(), (int)(this.entity.m_146907_() + 16));
            Intrinsics.checkNotNullExpressionValue((Object)iterable3, (String)"iterateRandomly(entity.r\u2026ockY, entity.blockZ + 16)");
            iterable2 = iterable3;
        }
        for (BlockPos pos : iterable2) {
            BlockState blockState = this.entity.m_9236_().m_8055_(pos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"blockState");
            if (!((Boolean)condition2.invoke((Object)blockState, (Object)pos)).booleanValue()) continue;
            return BlockPosExtensionsKt.toVec3d(pos);
        }
        return null;
    }
}

