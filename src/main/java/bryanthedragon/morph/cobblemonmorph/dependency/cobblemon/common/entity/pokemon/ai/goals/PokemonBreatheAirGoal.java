/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.BreathAirGoal
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\rR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonBreatheAirGoal;", "Lnet/minecraft/world/entity/ai/goal/BreathAirGoal;", "", "canStart", "()Z", "Lnet/minecraft/world/level/LevelReader;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "isAirPos", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "", "moveToBreathable", "()V", "start", "tick", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
public final class PokemonBreatheAirGoal
extends BreathAirGoal {
    @NotNull
    private final PokemonEntity pokemonEntity;

    public PokemonBreatheAirGoal(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        super((PathfinderMob)pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    public boolean m_8036_() {
        boolean canSwimUnderwater = this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanBreatheUnderwater();
        return this.pokemonEntity.m_20077_() && this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanSwimInLava() ? true : (this.pokemonEntity.m_20069_() && (this.pokemonEntity.getBehaviour().getMoving().getSwim().getAvoidsWater() || !canSwimUnderwater && (double)this.pokemonEntity.m_217043_().m_188501_() < 0.1) && this.pokemonEntity.m_21805_() == null ? true : super.m_8036_());
    }

    public void m_8056_() {
        this.moveToBreathable();
    }

    public void m_8037_() {
        if (this.pokemonEntity.getNavigation().m_26571_()) {
            this.moveToBreathable();
        }
    }

    public final void moveToBreathable() {
        FormPokemonBehaviour behaviour = this.pokemonEntity.getBehaviour();
        if (!behaviour.getMoving().getSwim().getCanBreatheUnderwater()) {
            Iterable iterable = BlockPos.m_121976_((int)Mth.m_14107_((double)(this.pokemonEntity.m_20185_() - 8.0)), (int)this.pokemonEntity.m_146904_(), (int)Mth.m_14107_((double)(this.pokemonEntity.m_20189_() - 8.0)), (int)Mth.m_14107_((double)(this.pokemonEntity.m_20185_() + 8.0)), (int)Mth.m_14107_((double)(this.pokemonEntity.m_20186_() + 2.0)), (int)Mth.m_14107_((double)(this.pokemonEntity.m_20189_() + 8.0)));
            Entity entity2 = (Entity)this.pokemonEntity;
            Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"iterable");
            BlockPos blockPos2 = EntityExtensionsKt.closestPosition(entity2, iterable, (Function1<? super BlockPos, Boolean>)((Function1)new Function1<BlockPos, Boolean>(this){
                final /* synthetic */ PokemonBreatheAirGoal this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull BlockPos it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    Level level = this.this$0.getPokemonEntity().m_9236_();
                    Intrinsics.checkNotNullExpressionValue((Object)level, (String)"pokemonEntity.world");
                    return PokemonBreatheAirGoal.access$isAirPos(this.this$0, (LevelReader)level, it);
                }
            }));
            if (blockPos2 == null) {
                return;
            }
            BlockPos blockPos3 = blockPos2;
            this.pokemonEntity.getNavigation().m_26519_(blockPos3.m_123341_(), blockPos3.m_123342_() + 1, blockPos3.m_123343_(), 1.0);
        }
    }

    private final boolean isAirPos(LevelReader world, BlockPos pos) {
        BlockState blockState = world.m_8055_(pos);
        BlockState aboveState = world.m_8055_(pos.m_7494_());
        boolean notFluid = world.m_6425_(pos.m_7494_()).m_76178_() || blockState.m_60713_(Blocks.f_50628_);
        boolean canPathfindThroughAbove = aboveState.m_60647_((BlockGetter)world, pos.m_7494_(), PathComputationType.LAND);
        boolean solidBelow = !blockState.m_60647_((BlockGetter)world, pos, PathComputationType.LAND);
        return notFluid && canPathfindThroughAbove && solidBelow;
    }

    public static final /* synthetic */ boolean access$isAirPos(PokemonBreatheAirGoal $this, LevelReader world, BlockPos pos) {
        return $this.isAirPos(world, pos);
    }
}

