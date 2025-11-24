/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001f\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\f0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/link/ProximityPCLink;", "Lcom/cobblemon/mod/common/api/storage/pc/link/PCLink;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isPermitted", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "", "maxDistance", "D", "getMaxDistance", "()D", "Lnet/minecraft/core/BlockPos;", "kotlin.jvm.PlatformType", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/Level;", "getWorld", "()Lnet/minecraft/world/level/Level;", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "Ljava/util/UUID;", "playerID", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "pcBlockEntity", "<init>", "(Lcom/cobblemon/mod/common/api/storage/pc/PCStore;Ljava/util/UUID;Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;D)V", "common"})
public final class ProximityPCLink
extends PCLink {
    private final double maxDistance;
    @Nullable
    private final Level world;
    private final BlockPos pos;

    public ProximityPCLink(@NotNull PCStore pc, @NotNull UUID playerID, @NotNull PCBlockEntity pcBlockEntity, double maxDistance) {
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        Intrinsics.checkNotNullParameter((Object)((Object)pcBlockEntity), (String)"pcBlockEntity");
        super(pc, playerID);
        this.maxDistance = maxDistance;
        this.world = pcBlockEntity.m_58904_();
        this.pos = pcBlockEntity.m_58899_();
    }

    public /* synthetic */ ProximityPCLink(PCStore pCStore, UUID uUID, PCBlockEntity pCBlockEntity, double d, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            d = 10.0;
        }
        this(pCStore, uUID, pCBlockEntity, d);
    }

    public final double getMaxDistance() {
        return this.maxDistance;
    }

    @Nullable
    public final Level getWorld() {
        return this.world;
    }

    public final BlockPos getPos() {
        return this.pos;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean isPermitted(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!Intrinsics.areEqual((Object)player.m_9236_(), (Object)this.world)) ** GOTO lbl-1000
        v0 = player.m_20182_();
        v1 = this.pos;
        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"pos");
        if (v0.m_82509_((Position)BlockPosExtensionsKt.toVec3d(v1), this.maxDistance)) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = false;
        }
        isWithinRange = v2;
        pcStillStanding = player.m_9236_().m_7702_(this.pos) instanceof PCBlockEntity;
        if (!isWithinRange || !pcStillStanding) {
            PCLinkManager.INSTANCE.removeLink(this.getPlayerID());
        }
        return isWithinRange != false && pcStillStanding != false;
    }
}

