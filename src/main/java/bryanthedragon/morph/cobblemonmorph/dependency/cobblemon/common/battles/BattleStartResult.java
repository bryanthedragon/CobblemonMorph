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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ#\u0010\u0006\u001a\u00020\u00002\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J#\u0010\t\u001a\u00020\u00002\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\u0007\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/battles/BattleStartResult;", "", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/battles/ErroredBattleStart;", "", "action", "ifErrored", "(Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "ifSuccessful", "<init>", "()V", "common"})
public abstract class BattleStartResult {
    @NotNull
    public BattleStartResult ifSuccessful(@NotNull Function1<? super PokemonBattle, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return this;
    }

    @NotNull
    public BattleStartResult ifErrored(@NotNull Function1<? super ErroredBattleStart, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        return this;
    }
}

