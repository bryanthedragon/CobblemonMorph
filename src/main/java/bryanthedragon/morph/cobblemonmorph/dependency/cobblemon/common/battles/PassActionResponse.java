/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ)\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ!\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/battles/PassActionResponse;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activeBattlePokemon", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "showdownMoveSet", "", "forceSwitch", "isValid", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Z", "", "toShowdownString", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;)Ljava/lang/String;", "<init>", "()V", "common"})
public final class PassActionResponse
extends ShowdownActionResponse {
    @NotNull
    public static final PassActionResponse INSTANCE = new PassActionResponse();

    private PassActionResponse() {
        super(ShowdownActionResponseType.PASS);
    }

    @Override
    public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        return true;
    }

    @Override
    @NotNull
    public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        return "pass";
    }
}

