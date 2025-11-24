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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/link/PermissiblePcLink;", "Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isPermitted", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lcom/cobblemon/mod/common/api/permission/Permission;", "permission", "Lcom/cobblemon/mod/common/api/permission/Permission;", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "<init>", "(Lcom/cobblemon/mod/common/api/storage/pc/PCStore;Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/permission/Permission;)V", "common"})
public final class PermissiblePcLink
extends PCLink {
    @NotNull
    private final Permission permission;

    public PermissiblePcLink(@NotNull PCStore pc, @NotNull ServerPlayer player, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        super(pc, uUID);
        this.permission = permission2;
    }

    @Override
    public boolean isPermitted(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        boolean result = Cobblemon.INSTANCE.getPermissionValidator().hasPermission(player, this.permission);
        if (!result) {
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            PCLinkManager.INSTANCE.removeLink(uUID);
        }
        return result;
    }
}

