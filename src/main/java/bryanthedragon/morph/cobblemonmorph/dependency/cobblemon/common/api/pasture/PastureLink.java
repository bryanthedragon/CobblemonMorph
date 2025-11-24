/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000eR\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/pasture/PastureLink;", "", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getPC", "()Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lnet/minecraft/resources/ResourceLocation;", "dimension", "Lnet/minecraft/resources/ResourceLocation;", "getDimension", "()Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/UUID;", "linkId", "Ljava/util/UUID;", "getLinkId", "()Ljava/util/UUID;", "pcId", "getPcId", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "permissions", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "getPermissions", "()Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;)V", "common"})
public final class PastureLink {
    @NotNull
    private final UUID linkId;
    @NotNull
    private final UUID pcId;
    @NotNull
    private final ResourceLocation dimension;
    @NotNull
    private final BlockPos pos;
    @NotNull
    private final PasturePermissions permissions;

    public PastureLink(@NotNull UUID linkId, @NotNull UUID pcId, @NotNull ResourceLocation dimension, @NotNull BlockPos pos, @NotNull PasturePermissions permissions) {
        Intrinsics.checkNotNullParameter((Object)linkId, (String)"linkId");
        Intrinsics.checkNotNullParameter((Object)pcId, (String)"pcId");
        Intrinsics.checkNotNullParameter((Object)dimension, (String)"dimension");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)permissions, (String)"permissions");
        this.linkId = linkId;
        this.pcId = pcId;
        this.dimension = dimension;
        this.pos = pos;
        this.permissions = permissions;
    }

    @NotNull
    public final UUID getLinkId() {
        return this.linkId;
    }

    @NotNull
    public final UUID getPcId() {
        return this.pcId;
    }

    @NotNull
    public final ResourceLocation getDimension() {
        return this.dimension;
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    @NotNull
    public final PasturePermissions getPermissions() {
        return this.permissions;
    }

    @NotNull
    public final PCStore getPC() {
        return Cobblemon.INSTANCE.getStorage().getPC(this.pcId);
    }
}

