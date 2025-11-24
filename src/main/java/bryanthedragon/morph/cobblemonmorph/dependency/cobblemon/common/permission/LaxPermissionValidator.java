/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/permission/LaxPermissionValidator;", "Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", "Lnet/minecraft/commands/SharedSuggestionProvider;", "source", "Lcom/cobblemon/mod/common/api/permission/Permission;", "permission", "", "hasPermission", "(Lnet/minecraft/commands/SharedSuggestionProvider;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "Lnet/minecraft/server/level/ServerPlayer;", "player", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "", "initialize", "()V", "<init>", "common"})
public final class LaxPermissionValidator
implements PermissionValidator {
    @Override
    public void initialize() {
        Cobblemon.INSTANCE.getLOGGER().info("Booting LaxPermissionValidator, permissions will be checked using Minecrafts permission level system, see https://minecraft.fandom.com/wiki/Permission_level");
    }

    @Override
    public boolean hasPermission(@NotNull ServerPlayer player, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        return player.m_20310_(permission2.getLevel().getNumericalValue());
    }

    @Override
    public boolean hasPermission(@NotNull SharedSuggestionProvider source, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        return source.m_6761_(permission2.getLevel().getNumericalValue());
    }
}

