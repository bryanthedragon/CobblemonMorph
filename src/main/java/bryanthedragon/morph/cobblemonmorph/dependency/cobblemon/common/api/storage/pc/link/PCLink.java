/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isPermitted", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPc", "()Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Ljava/util/UUID;", "playerID", "Ljava/util/UUID;", "getPlayerID", "()Ljava/util/UUID;", "<init>", "(Lcom/cobblemon/mod/common/api/storage/pc/PCStore;Ljava/util/UUID;)V", "common"})
public class PCLink {
    @NotNull
    private final PCStore pc;
    @NotNull
    private final UUID playerID;

    public PCLink(@NotNull PCStore pc, @NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        this.pc = pc;
        this.playerID = playerID;
    }

    @NotNull
    public PCStore getPc() {
        return this.pc;
    }

    @NotNull
    public UUID getPlayerID() {
        return this.playerID;
    }

    public boolean isPermitted(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return true;
    }
}

