/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.EvCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J+\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tRD\u0010\u000e\u001a2\u0012\u0004\u0012\u00020\n\u0012(\u0012&\u0012\f\u0012\n \r*\u0004\u0018\u00010\f0\f \r*\u0012\u0012\f\u0012\n \r*\u0004\u0018\u00010\f0\f\u0018\u00010\u000b0\u000b0\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/Generation8EvCalculator;", "Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "opponentPokemon", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "calculate", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/util/Map;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stats;", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/item/Item;", "kotlin.jvm.PlatformType", "powerItems", "Ljava/util/Map;", "<init>", "()V", "common"})
public final class Generation8EvCalculator
implements EvCalculator {
    @NotNull
    public static final Generation8EvCalculator INSTANCE = new Generation8EvCalculator();
    @NotNull
    private static final Map<Stats, TagKey<Item>> powerItems;

    private Generation8EvCalculator() {
    }

    @Override
    @NotNull
    public Map<Stat, Integer> calculate(@NotNull BattlePokemon battlePokemon, @NotNull BattlePokemon opponentPokemon) {
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
        Intrinsics.checkNotNullParameter((Object)opponentPokemon, (String)"opponentPokemon");
        ItemStack heldItem2 = battlePokemon.getEffectedPokemon().heldItemNoCopy$common();
        Map evYield = new LinkedHashMap();
        for (Map.Entry<Stat, Integer> entry : opponentPokemon.getOriginalPokemon().getForm().getEvYield().entrySet()) {
            Stat stat = entry.getKey();
            int value2 = ((Number)entry.getValue()).intValue();
            int boost = !heldItem2.m_41619_() && heldItem2.m_204117_(powerItems.get(stat)) ? 8 : 0;
            evYield.put(stat, ((Number)evYield.getOrDefault(stat, 0)).intValue() + value2 + boost);
        }
        return evYield;
    }

    @Override
    @NotNull
    public Map<Stat, Integer> calculate(@NotNull BattlePokemon battlePokemon) {
        return EvCalculator.DefaultImpls.calculate(this, battlePokemon);
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)Stats.SPEED, CobblemonItemTags.POWER_ANKLET), TuplesKt.to((Object)Stats.SPECIAL_DEFENCE, CobblemonItemTags.POWER_BAND), TuplesKt.to((Object)Stats.DEFENCE, CobblemonItemTags.POWER_BELT), TuplesKt.to((Object)Stats.ATTACK, CobblemonItemTags.POWER_BRACER), TuplesKt.to((Object)Stats.SPECIAL_ATTACK, CobblemonItemTags.POWER_LENS), TuplesKt.to((Object)Stats.HP, CobblemonItemTags.POWER_WEIGHT)};
        powerItems = MapsKt.mapOf((Pair[])pairArray);
    }
}

