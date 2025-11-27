/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import java.util.UUID;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public final class PlayerDialogueFaceProvider implements DialogueFaceProvider {
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

