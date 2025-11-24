/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/exception/IllegalActionChoiceException;", "Ljava/lang/IllegalArgumentException;", "Lkotlin/IllegalArgumentException;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "", "message", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Ljava/lang/String;)V", "common"})
public final class IllegalActionChoiceException
extends IllegalArgumentException {
    @NotNull
    private final BattleActor actor;

    public IllegalActionChoiceException(@NotNull BattleActor actor, @NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        super(message);
        this.actor = actor;
    }

    @NotNull
    public final BattleActor getActor() {
        return this.actor;
    }
}

