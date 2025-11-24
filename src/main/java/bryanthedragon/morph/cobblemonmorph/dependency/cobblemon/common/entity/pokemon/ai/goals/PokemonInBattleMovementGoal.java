/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0011\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\nR\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u0007R\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonInBattleMovementGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getClosestPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "", "start", "()V", "tick", "entity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "", "range", "I", "getRange", "()I", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;I)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonInBattleMovementGoal.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonInBattleMovementGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonInBattleMovementGoal\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1#2:41\n1#2:55\n1747#3,3:42\n1603#3,9:45\n1855#3:54\n1856#3:56\n1612#3:57\n2333#3,14:58\n*S KotlinDebug\n*F\n+ 1 PokemonInBattleMovementGoal.kt\ncom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonInBattleMovementGoal\n*L\n29#1:55\n28#1:42,3\n29#1:45,9\n29#1:54\n29#1:56\n29#1:57\n29#1:58,14\n*E\n"})
public final class PokemonInBattleMovementGoal
extends Goal {
    @NotNull
    private final PokemonEntity entity;
    private final int range;

    public PokemonInBattleMovementGoal(@NotNull PokemonEntity entity2, int range) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.entity = entity2;
        this.range = range;
    }

    @NotNull
    public final PokemonEntity getEntity() {
        return this.entity;
    }

    public final int getRange() {
        return this.range;
    }

    public boolean m_8036_() {
        return this.entity.isBattling() && this.getClosestPokemonEntity() != null && this.entity.getCurrentPoseType() != PoseType.SLEEP;
    }

    public void m_8056_() {
        super.m_8056_();
        this.entity.getNavigation().m_26573_();
    }

    /*
     * WARNING - void declaration
     */
    private final PokemonEntity getClosestPokemonEntity() {
        UUID uUID = this.entity.getBattleId();
        if (uUID != null) {
            UUID it = uUID;
            boolean bl = false;
            PokemonBattle pokemonBattle = BattleRegistry.INSTANCE.getBattle(it);
            if (pokemonBattle != null) {
                PokemonEntity pokemonEntity;
                BattleSide battleSide;
                BattleSide battleSide2;
                Object it2;
                Iterable<BattleSide> iterable;
                block15: {
                    PokemonBattle battle2 = pokemonBattle;
                    boolean bl2 = false;
                    iterable = battle2.getSides();
                    Iterator<BattleSide> iterator = iterable.iterator();
                    while (iterator.hasNext()) {
                        boolean bl3;
                        BattleSide battleSide3;
                        block14: {
                            BattleSide it3 = battleSide3 = iterator.next();
                            boolean bl4 = false;
                            Iterable $this$any$iv = it3.getActivePokemon();
                            boolean $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl3 = false;
                            } else {
                                for (Object element$iv : $this$any$iv) {
                                    it2 = (ActiveBattlePokemon)element$iv;
                                    boolean bl5 = false;
                                    BattlePokemon battlePokemon = ((ActiveBattlePokemon)it2).getBattlePokemon();
                                    if (!Intrinsics.areEqual((Object)(battlePokemon != null ? battlePokemon.getEffectedPokemon() : null), (Object)this.entity.getPokemon())) continue;
                                    bl3 = true;
                                    break block14;
                                }
                                bl3 = false;
                            }
                        }
                        if (!bl3) continue;
                        battleSide2 = battleSide3;
                        break block15;
                    }
                    battleSide2 = null;
                }
                BattleSide battleSide4 = battleSide2;
                if (battleSide4 != null && (battleSide = battleSide4.getOppositeSide()) != null && (iterable = battleSide.getActivePokemon()) != null) {
                    Object v4;
                    void $this$mapNotNullTo$iv$iv;
                    Iterable<BattleSide> $this$mapNotNull$iv = iterable;
                    boolean $i$f$mapNotNull = false;
                    Iterable<BattleSide> bl4 = $this$mapNotNull$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$mapNotNullTo = false;
                    void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                    boolean $i$f$forEach = false;
                    it2 = $this$forEach$iv$iv$iv.iterator();
                    while (it2.hasNext()) {
                        PokemonEntity it$iv$iv;
                        Object element$iv$iv$iv;
                        Object element$iv$iv = element$iv$iv$iv = it2.next();
                        boolean bl6 = false;
                        ActiveBattlePokemon it4 = (ActiveBattlePokemon)element$iv$iv;
                        boolean bl7 = false;
                        BattlePokemon battlePokemon = it4.getBattlePokemon();
                        if ((battlePokemon != null ? battlePokemon.getEntity() : null) == null) continue;
                        it$iv$iv = it$iv$iv;
                        boolean bl8 = false;
                        destination$iv$iv.add(it$iv$iv);
                    }
                    Iterable $this$minByOrNull$iv = (List)destination$iv$iv;
                    boolean $i$f$minByOrNull = false;
                    Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                    if (!iterator$iv.hasNext()) {
                        v4 = null;
                    } else {
                        Object minElem$iv = iterator$iv.next();
                        if (!iterator$iv.hasNext()) {
                            v4 = minElem$iv;
                        } else {
                            PokemonEntity it5 = (PokemonEntity)minElem$iv;
                            boolean bl9 = false;
                            float minValue$iv = it5.m_20270_((Entity)this.entity);
                            do {
                                Object e$iv = iterator$iv.next();
                                PokemonEntity it6 = (PokemonEntity)e$iv;
                                $i$a$-minByOrNull-PokemonInBattleMovementGoal$getClosestPokemonEntity$2$3 = false;
                                float v$iv = it6.m_20270_((Entity)this.entity);
                                if (Float.compare(minValue$iv, v$iv) <= 0) continue;
                                minElem$iv = e$iv;
                                minValue$iv = v$iv;
                            } while (iterator$iv.hasNext());
                            v4 = minElem$iv;
                        }
                    }
                    pokemonEntity = v4;
                } else {
                    pokemonEntity = null;
                }
                return pokemonEntity;
            }
        }
        return null;
    }

    public void m_8037_() {
        PokemonEntity closestPokemonEntity = this.getClosestPokemonEntity();
        if (closestPokemonEntity != null) {
            this.entity.m_21563_().m_24946_(closestPokemonEntity.m_20185_(), closestPokemonEntity.m_20188_(), closestPokemonEntity.m_20189_());
        }
    }
}

