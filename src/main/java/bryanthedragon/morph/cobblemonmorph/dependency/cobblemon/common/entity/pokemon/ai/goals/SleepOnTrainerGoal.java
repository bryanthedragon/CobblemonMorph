/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.PokemonNavigation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u000f\u0010\u000b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\tR\u0018\u0010\r\u001a\u0004\u0018\u00010\f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0016\u001a\u00020\u00158\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/SleepOnTrainerGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "cannotSleep", "shouldContinue", "", "start", "()V", "stop", "tick", "Lnet/minecraft/core/BlockPos;", "bedPos", "Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/entity/player/Player;", "owner", "Lnet/minecraft/world/entity/player/Player;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "", "ticksOnBed", "I", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSleepOnTrainerGoal.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SleepOnTrainerGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/SleepOnTrainerGoal\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n1#2:139\n1747#3,3:140\n*S KotlinDebug\n*F\n+ 1 SleepOnTrainerGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/SleepOnTrainerGoal\n*L\n61#1:140,3\n*E\n"})
public final class SleepOnTrainerGoal
extends Goal {
    @NotNull
    private final PokemonEntity pokemonEntity;
    @Nullable
    private Player owner;
    @Nullable
    private BlockPos bedPos;
    private int ticksOnBed;

    public SleepOnTrainerGoal(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        this.pokemonEntity = pokemonEntity;
    }

    /*
     * Unable to fully structure code
     */
    public boolean m_8036_() {
        block5: {
            if (!this.pokemonEntity.getPokemon().isPlayerOwned() || !this.pokemonEntity.getBehaviour().getResting().getWillSleepOnBed() || this.pokemonEntity.getPokemon().getStatus() != null) {
                return false;
            }
            livingEntity = this.pokemonEntity.m_269323_();
            if (!(livingEntity instanceof Player)) break block5;
            this.owner = (Player)livingEntity;
            if (!((Player)livingEntity).m_5803_()) {
                return false;
            }
            if (this.pokemonEntity.m_20280_((Entity)this.owner) > 100.0) {
                return false;
            }
            v0 = this.owner;
            Intrinsics.checkNotNull((Object)v0);
            blockPos = v0.m_20183_();
            blockState = this.pokemonEntity.m_9236_().m_8055_(blockPos);
            if (!blockState.m_204336_(BlockTags.f_13038_)) break block5;
            v1 = this;
            var4_4 = blockState.m_61145_((Property)BedBlock.f_54117_).orElse(null);
            if (var4_4 == null) ** GOTO lbl-1000
            var6_5 = var4_4;
            var8_6 = v1;
            $i$a$-let-SleepOnTrainerGoal$canStart$1 = false;
            v1 = var8_6;
            var5_8 = blockPos.m_121945_(direction.m_122424_());
            if (var5_8 != null) {
                v2 = var5_8;
            } else lbl-1000:
            // 2 sources

            {
                v2 = new BlockPos((Vec3i)blockPos);
            }
            v1.bedPos = v2;
            return this.cannotSleep() == false;
        }
        return false;
    }

    private final boolean cannotSleep() {
        boolean bl;
        block3: {
            List closePokemon = this.pokemonEntity.m_9236_().m_45976_(PokemonEntity.class, new AABB(this.bedPos).m_82400_(2.0));
            Intrinsics.checkNotNullExpressionValue((Object)closePokemon, (String)"closePokemon");
            Iterable $this$any$iv = closePokemon;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    PokemonEntity it = (PokemonEntity)element$iv;
                    boolean bl2 = false;
                    PersistentStatusContainer persistentStatusContainer = it.getPokemon().getStatus();
                    if (!(Intrinsics.areEqual((Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null), (Object)Statuses.INSTANCE.getSLEEP()) && !Intrinsics.areEqual((Object)it, (Object)this.pokemonEntity))) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public boolean m_8045_() {
        Player owner = this.owner;
        return owner instanceof ServerPlayer && ((ServerPlayer)owner).m_5803_() && this.bedPos != null && !this.cannotSleep();
    }

    public void m_8056_() {
        if (this.bedPos != null) {
            PokemonNavigation pokemonNavigation = this.pokemonEntity.getNavigation();
            BlockPos blockPos2 = this.bedPos;
            Intrinsics.checkNotNull((Object)blockPos2);
            double d = blockPos2.m_123341_();
            BlockPos blockPos3 = this.bedPos;
            Intrinsics.checkNotNull((Object)blockPos3);
            double d2 = blockPos3.m_123342_();
            BlockPos blockPos4 = this.bedPos;
            Intrinsics.checkNotNull((Object)blockPos4);
            pokemonNavigation.m_26519_(d, d2, blockPos4.m_123343_(), 0.7);
        }
    }

    public void m_8041_() {
        this.pokemonEntity.getPokemon().setStatus(null);
        this.ticksOnBed = 0;
        this.pokemonEntity.getNavigation().m_26573_();
    }

    public void m_8037_() {
        if (this.owner != null && this.bedPos != null) {
            if (this.pokemonEntity.m_20280_((Entity)this.owner) < 1.5) {
                ++this.ticksOnBed;
                if (this.ticksOnBed > this.m_183277_(16)) {
                    this.pokemonEntity.getPokemon().setStatus(new PersistentStatusContainer(Statuses.INSTANCE.getSLEEP(), 0, 2, null));
                } else {
                    this.pokemonEntity.m_21391_((Entity)this.owner, 45.0f, 45.0f);
                }
            } else {
                this.pokemonEntity.getPokemon().setStatus(null);
            }
        }
    }
}

