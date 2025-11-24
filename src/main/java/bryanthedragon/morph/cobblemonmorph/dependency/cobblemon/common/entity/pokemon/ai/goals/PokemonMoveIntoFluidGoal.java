/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.SwimBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonMoveIntoFluidGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "", "start", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "mob", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonMoveIntoFluidGoal.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonMoveIntoFluidGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonMoveIntoFluidGoal\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n1747#2,3:112\n*S KotlinDebug\n*F\n+ 1 PokemonMoveIntoFluidGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonMoveIntoFluidGoal\n*L\n61#1:112,3\n*E\n"})
public final class PokemonMoveIntoFluidGoal
extends Goal {
    @NotNull
    private final PokemonEntity mob;

    public PokemonMoveIntoFluidGoal(@NotNull PokemonEntity mob) {
        Intrinsics.checkNotNullParameter((Object)mob, (String)"mob");
        this.mob = mob;
    }

    public boolean m_8036_() {
        int n;
        boolean avoidsLand = this.mob.getBehaviour().getMoving().getWalk().getAvoidsLand();
        if (!avoidsLand || this.mob.m_269323_() != null) {
            return false;
        }
        boolean allowsWater = this.mob.getBehaviour().getMoving().getSwim().getCanSwimInWater();
        boolean allowsLava = this.mob.getBehaviour().getMoving().getSwim().getCanSwimInLava();
        BlockPos pos = this.mob.m_20183_();
        FluidState fluid = this.mob.m_9236_().m_6425_(pos);
        List allowedFluids = new ArrayList();
        if (allowsWater) {
            TagKey tagKey = FluidTags.f_13131_;
            Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"WATER");
            allowedFluids.add(tagKey);
        }
        if (allowsLava) {
            TagKey tagKey = FluidTags.f_13132_;
            Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"LAVA");
            allowedFluids.add(tagKey);
        }
        IntRange allX = new IntRange((int)Math.floor(this.mob.m_20191_().f_82288_), (int)Math.ceil(this.mob.m_20191_().f_82291_));
        IntRange allZ = new IntRange((int)Math.floor(this.mob.m_20191_().f_82290_), (int)Math.ceil(this.mob.m_20191_().f_82293_));
        boolean onSolid = false;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int blockX = allX.getFirst();
        if (blockX <= (n = allX.getLast())) {
            while (true) {
                int n2;
                int blockZ;
                if ((blockZ = allZ.getFirst()) <= (n2 = allZ.getLast())) {
                    while (true) {
                        int n3;
                        int y;
                        if (this.mob.m_9236_().m_8055_((BlockPos)mutablePos.m_122178_(blockX, this.mob.m_146904_() - 1, blockZ)).m_280296_()) {
                            onSolid = true;
                        }
                        if ((y = this.mob.m_146904_()) <= (n3 = (int)Math.ceil(this.mob.m_20191_().f_82292_))) {
                            while (true) {
                                boolean bl;
                                block15: {
                                    FluidState fluidState = this.mob.m_9236_().m_6425_((BlockPos)mutablePos.m_122178_(blockX, y, blockZ));
                                    Iterable $this$any$iv = allowedFluids;
                                    boolean $i$f$any = false;
                                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                        bl = false;
                                    } else {
                                        for (Object element$iv : $this$any$iv) {
                                            TagKey it = (TagKey)element$iv;
                                            boolean bl2 = false;
                                            if (!fluidState.m_205070_(it)) continue;
                                            bl = true;
                                            break block15;
                                        }
                                        bl = false;
                                    }
                                }
                                if (bl) {
                                    return false;
                                }
                                if (this.mob.m_20186_() > 62.0) {
                                    // empty if block
                                }
                                if (y == n3) break;
                                ++y;
                            }
                        }
                        if (blockZ == n2) break;
                        ++blockZ;
                    }
                }
                if (blockX == n) break;
                ++blockX;
            }
        }
        return onSolid;
    }

    public void m_8056_() {
        List appropriateFluids = new ArrayList();
        SwimBehaviour swim = this.mob.getBehaviour().getMoving().getSwim();
        if (swim.getCanSwimInLava() && !swim.getHurtByLava()) {
            TagKey tagKey = FluidTags.f_13132_;
            Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"LAVA");
            appropriateFluids.add(tagKey);
        }
        if (swim.getCanSwimInWater()) {
            TagKey tagKey = FluidTags.f_13131_;
            Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"WATER");
            appropriateFluids.add(tagKey);
        }
        Iterable iterable = BlockPos.m_121976_((int)Mth.m_14107_((double)(this.mob.m_20185_() - 8.0)), (int)Mth.m_14107_((double)(this.mob.m_20186_() - 8.0)), (int)Mth.m_14107_((double)(this.mob.m_20189_() - 8.0)), (int)Mth.m_14107_((double)(this.mob.m_20185_() + 8.0)), (int)this.mob.m_146904_(), (int)Mth.m_14107_((double)(this.mob.m_20189_() + 2.0)));
        AABB box = this.mob.m_20191_();
        Entity entity2 = (Entity)this.mob;
        Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"iterable");
        BlockPos blockPos2 = EntityExtensionsKt.closestPosition(entity2, iterable, (Function1<? super BlockPos, Boolean>)((Function1)new Function1<BlockPos, Boolean>(this, (List<TagKey<Fluid>>)appropriateFluids, box){
            final /* synthetic */ PokemonMoveIntoFluidGoal this$0;
            final /* synthetic */ List<TagKey<Fluid>> $appropriateFluids;
            final /* synthetic */ AABB $box;
            {
                this.this$0 = $receiver;
                this.$appropriateFluids = $appropriateFluids;
                this.$box = $box;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull BlockPos pos) {
                boolean bl;
                block4: {
                    Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                    PokemonPastureBlockEntity.Tethering tethering = PokemonMoveIntoFluidGoal.access$getMob$p(this.this$0).getTethering();
                    boolean bl2 = tethering != null ? !tethering.canRoamTo(pos) : false;
                    if (bl2) {
                        return false;
                    }
                    FluidState fluid = PokemonMoveIntoFluidGoal.access$getMob$p(this.this$0).m_9236_().m_6425_(pos);
                    Iterable $this$any$iv = this.$appropriateFluids;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            TagKey it = (TagKey)element$iv;
                            boolean bl3 = false;
                            if (!fluid.m_205070_(it)) continue;
                            bl = true;
                            break block4;
                        }
                        bl = false;
                    }
                }
                return bl && PokemonMoveIntoFluidGoal.access$getMob$p(this.this$0).m_9236_().m_45772_(AABB.m_165882_((Vec3)new Vec3((double)pos.m_123341_(), (double)pos.m_123342_() - 1.0, (double)pos.m_123343_()), (double)this.$box.m_82362_(), (double)this.$box.m_82376_(), (double)this.$box.m_82385_()));
            }
        }));
        if (blockPos2 != null) {
            this.mob.getNavigation().m_26519_(blockPos2.m_123341_(), blockPos2.m_123342_(), blockPos2.m_123343_(), 1.0);
        }
    }

    public static final /* synthetic */ PokemonEntity access$getMob$p(PokemonMoveIntoFluidGoal $this) {
        return $this.mob;
    }
}

