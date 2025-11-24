/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.math.MathKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.LevelRequirement;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/StandardExperienceCalculator;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceCalculator;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "opponentPokemon", "", "participationMultiplier", "", "calculate", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;D)I", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nExperienceCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExperienceCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/experience/StandardExperienceCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,45:1\n1747#2,2:46\n1749#2:52\n1229#3,2:48\n1206#3,2:50\n*S KotlinDebug\n*F\n+ 1 ExperienceCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/experience/StandardExperienceCalculator\n*L\n35#1:46,2\n35#1:52\n37#1:48,2\n37#1:50,2\n*E\n"})
public final class StandardExperienceCalculator
implements ExperienceCalculator {
    @NotNull
    public static final StandardExperienceCalculator INSTANCE = new StandardExperienceCalculator();

    private StandardExperienceCalculator() {
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public int calculate(@NotNull BattlePokemon battlePokemon, @NotNull BattlePokemon opponentPokemon, double participationMultiplier) {
        block7: {
            block8: {
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                Intrinsics.checkNotNullParameter((Object)opponentPokemon, (String)"opponentPokemon");
                baseExp = opponentPokemon.getOriginalPokemon().getForm().getBaseExperienceYield();
                opponentLevel = opponentPokemon.getEffectedPokemon().getLevel();
                term1 = (double)(baseExp * opponentLevel) / 5.0;
                term2 = (double)true * participationMultiplier;
                victorPokemon = battlePokemon.getEffectedPokemon();
                victorLevel = victorPokemon.getLevel();
                term3 = Math.pow((2.0 * (double)opponentLevel + (double)10) / (double)(opponentLevel + victorLevel + 10), 2.5);
                nonOtBonus = 1.0;
                luckyEggMultiplier = battlePokemon.getEffectedPokemon().heldItemNoCopy$common().m_204117_(CobblemonItemTags.LUCKY_EGG) != false ? Cobblemon.INSTANCE.getConfig().getLuckyEggMultiplier() : 1.0;
                $this$any$iv = battlePokemon.getEffectedPokemon().getEvolutionProxy().server();
                $i$f$any = false;
                if (!($this$any$iv instanceof Collection) || !((Collection)$this$any$iv).isEmpty()) break block8;
                v0 = false;
                break block7;
            }
            for (T element$iv : $this$any$iv) {
                block6: {
                    block5: {
                        evolution = (Evolution)element$iv;
                        $i$a$-any-StandardExperienceCalculator$calculate$evolutionMultiplier$1 = false;
                        $this$any$iv = requirements = CollectionsKt.asSequence((Iterable)evolution.getRequirements());
                        $i$f$any = false;
                        for (E element$iv : $this$any$iv) {
                            it = (EvolutionRequirement)element$iv;
                            $i$a$-any-StandardExperienceCalculator$calculate$evolutionMultiplier$1$1 = false;
                            if (!(it instanceof LevelRequirement)) continue;
                            v1 = true;
                            break block5;
                        }
                        v1 = false;
                    }
                    if (!v1) ** GOTO lbl-1000
                    $this$all$iv = requirements;
                    $i$f$all = false;
                    for (E element$iv : $this$all$iv) {
                        it = (EvolutionRequirement)element$iv;
                        $i$a$-all-StandardExperienceCalculator$calculate$evolutionMultiplier$1$2 = false;
                        if (it.check(battlePokemon.getEffectedPokemon())) continue;
                        v2 = false;
                        break block6;
                    }
                    v2 = true;
                }
                if (v2) {
                    v3 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v3 = false;
                }
                if (!v3) continue;
                v0 = true;
                break block7;
            }
            v0 = false;
        }
        evolutionMultiplier = v0 != false ? 1.2 : 1.0;
        affectionMultiplier = battlePokemon.getEffectedPokemon().getFriendship() >= 220 ? 1.2 : 1.0;
        gimmickBoost = Cobblemon.INSTANCE.getConfig().getExperienceMultiplier();
        term4 = term1 * term2 * term3 + (double)true;
        return MathKt.roundToInt((double)(term4 * nonOtBonus * luckyEggMultiplier * evolutionMultiplier * affectionMultiplier * (double)gimmickBoost));
    }
}

