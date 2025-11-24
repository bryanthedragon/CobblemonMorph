/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartResult;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0004\b\f\u0010\rJ#\u0010\u0006\u001a\u00020\u00012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/SuccessfulBattleStart;", "Lcom/cobblemon/mod/common/battles/BattleStartResult;", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "", "action", "ifSuccessful", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "common"})
public final class SuccessfulBattleStart
extends BattleStartResult {
    @NotNull
    private final PokemonBattle battle;

    public SuccessfulBattleStart(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        this.battle = battle2;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @Override
    @NotNull
    public BattleStartResult ifSuccessful(@NotNull Function1<? super PokemonBattle, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        action2.invoke((Object)this.battle);
        return this;
    }
}

