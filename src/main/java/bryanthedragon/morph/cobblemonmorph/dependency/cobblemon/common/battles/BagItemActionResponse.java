/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0004\b\u001b\u0010\u001cJ)\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ!\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0012\u001a\u0004\u0018\u00010\n8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/battles/BagItemActionResponse;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "data", "Ljava/lang/String;", "getData", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getTarget", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "<init>", "(Lcom/cobblemon/mod/common/item/battle/BagItem;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)V", "common"})
public final class BagItemActionResponse
extends ShowdownActionResponse {
    @NotNull
    private final BagItem bagItem;
    @NotNull
    private final BattlePokemon target;
    @Nullable
    private final String data;

    public BagItemActionResponse(@NotNull BagItem bagItem2, @NotNull BattlePokemon target, @Nullable String data) {
        Intrinsics.checkNotNullParameter((Object)bagItem2, (String)"bagItem");
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        super(ShowdownActionResponseType.FORCE_PASS);
        this.bagItem = bagItem2;
        this.target = target;
        this.data = data;
    }

    public /* synthetic */ BagItemActionResponse(BagItem bagItem2, BattlePokemon battlePokemon, String string, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            string = null;
        }
        this(bagItem2, battlePokemon, string);
    }

    @NotNull
    public final BagItem getBagItem() {
        return this.bagItem;
    }

    @NotNull
    public final BattlePokemon getTarget() {
        return this.target;
    }

    @Nullable
    public final String getData() {
        return this.data;
    }

    @Override
    public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        if (forceSwitch) {
            return false;
        }
        if (showdownMoveSet == null) {
            return false;
        }
        return activeBattlePokemon.getActor().getExpectingPassActions().size() > 0;
    }

    @Override
    @NotNull
    public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        return "useitem " + this.target.getUuid() + " " + this.bagItem.getItemName() + " " + this.bagItem.getShowdownInput(this.target.getActor(), this.target, this.data);
    }
}

