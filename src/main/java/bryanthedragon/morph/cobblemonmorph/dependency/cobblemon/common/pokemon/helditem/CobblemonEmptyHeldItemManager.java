/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.BaseCobblemonHeldItemManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ'\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\n\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/pokemon/helditem/CobblemonEmptyHeldItemManager;", "Lcom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "battleMessage", "", "handleEndInstruction", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "handleStartInstruction", "<init>", "()V", "common"})
public final class CobblemonEmptyHeldItemManager
extends BaseCobblemonHeldItemManager {
    @NotNull
    public static final CobblemonEmptyHeldItemManager INSTANCE = new CobblemonEmptyHeldItemManager();

    private CobblemonEmptyHeldItemManager() {
    }

    @Override
    public void handleStartInstruction(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle2, @NotNull BattleMessage battleMessage) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)battleMessage, (String)"battleMessage");
    }

    @Override
    public void handleEndInstruction(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle2, @NotNull BattleMessage battleMessage) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)battleMessage, (String)"battleMessage");
    }
}

