/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/PlayerDialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "Ljava/util/UUID;", "playerId", "Ljava/util/UUID;", "getPlayerId", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;)V", "common"})
public final class PlayerDialogueFaceProvider
implements DialogueFaceProvider {
    @NotNull
    private final UUID playerId;

    public PlayerDialogueFaceProvider(@NotNull UUID playerId) {
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        this.playerId = playerId;
    }

    @NotNull
    public final UUID getPlayerId() {
        return this.playerId;
    }
}

