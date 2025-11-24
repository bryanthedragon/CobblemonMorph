/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import kotlin.Metadata;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH&\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", "", "Lnet/minecraft/commands/SharedSuggestionProvider;", "source", "Lcom/cobblemon/mod/common/api/permission/Permission;", "permission", "", "hasPermission", "(Lnet/minecraft/commands/SharedSuggestionProvider;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "Lnet/minecraft/server/level/ServerPlayer;", "player", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "", "initialize", "()V", "common"})
public interface PermissionValidator {
    public void initialize();

    public boolean hasPermission(@NotNull ServerPlayer var1, @NotNull Permission var2);

    public boolean hasPermission(@NotNull SharedSuggestionProvider var1, @NotNull Permission var2);
}

