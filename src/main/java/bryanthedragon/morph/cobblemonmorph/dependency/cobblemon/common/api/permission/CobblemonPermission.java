/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionLevel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c2\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0004R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\t\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u001a\u001a\u0004\b\u001b\u0010\u0007R\u001a\u0010\u001c\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\u001d\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/permission/CobblemonPermission;", "Lcom/cobblemon/mod/common/api/permission/Permission;", "", "component1", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "component2", "()Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "node", "level", "copy", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/permission/PermissionLevel;)Lcom/cobblemon/mod/common/api/permission/CobblemonPermission;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "getLevel", "literal", "Ljava/lang/String;", "getLiteral", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/permission/PermissionLevel;)V", "common"})
public final class CobblemonPermission
implements Permission {
    @NotNull
    private final String node;
    @NotNull
    private final PermissionLevel level;
    @NotNull
    private final ResourceLocation identifier;
    @NotNull
    private final String literal;

    public CobblemonPermission(@NotNull String node, @NotNull PermissionLevel level) {
        Intrinsics.checkNotNullParameter((Object)node, (String)"node");
        Intrinsics.checkNotNullParameter((Object)((Object)level), (String)"level");
        this.node = node;
        this.level = level;
        this.identifier = MiscUtils.cobblemonResource(this.node);
        this.literal = "cobblemon." + this.node;
    }

    @Override
    @NotNull
    public PermissionLevel getLevel() {
        return this.level;
    }

    @Override
    @NotNull
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    @Override
    @NotNull
    public String getLiteral() {
        return this.literal;
    }

    private final String component1() {
        return this.node;
    }

    @NotNull
    public final PermissionLevel component2() {
        return this.level;
    }

    @NotNull
    public final CobblemonPermission copy(@NotNull String node, @NotNull PermissionLevel level) {
        Intrinsics.checkNotNullParameter((Object)node, (String)"node");
        Intrinsics.checkNotNullParameter((Object)((Object)level), (String)"level");
        return new CobblemonPermission(node, level);
    }

    public static /* synthetic */ CobblemonPermission copy$default(CobblemonPermission cobblemonPermission, String string, PermissionLevel permissionLevel, int n, Object object) {
        if ((n & 1) != 0) {
            string = cobblemonPermission.node;
        }
        if ((n & 2) != 0) {
            permissionLevel = cobblemonPermission.level;
        }
        return cobblemonPermission.copy(string, permissionLevel);
    }

    @NotNull
    public String toString() {
        return "CobblemonPermission(node=" + this.node + ", level=" + this.level + ")";
    }

    public int hashCode() {
        int result = this.node.hashCode();
        result = result * 31 + this.level.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CobblemonPermission)) {
            return false;
        }
        CobblemonPermission cobblemonPermission = (CobblemonPermission)other;
        if (!Intrinsics.areEqual((Object)this.node, (Object)cobblemonPermission.node)) {
            return false;
        }
        return this.level == cobblemonPermission.level;
    }
}

