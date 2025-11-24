/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionLevel;
import kotlin.Metadata;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/permission/Permission;", "", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "getLevel", "()Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "level", "", "getLiteral", "()Ljava/lang/String;", "literal", "common"})
public interface Permission {
    @NotNull
    public ResourceLocation getIdentifier();

    @NotNull
    public String getLiteral();

    @NotNull
    public PermissionLevel getLevel();
}

