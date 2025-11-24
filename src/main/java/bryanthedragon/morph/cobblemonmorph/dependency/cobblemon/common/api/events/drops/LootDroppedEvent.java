/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\u0004\u0018\u00010\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/events/drops/LootDroppedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "drops", "Ljava/util/List;", "getDrops", "()Ljava/util/List;", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "Lnet/minecraft/world/entity/LivingEntity;", "getEntity", "()Lnet/minecraft/world/entity/LivingEntity;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "table", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "getTable", "()Lcom/cobblemon/mod/common/api/drop/DropTable;", "<init>", "(Lcom/cobblemon/mod/common/api/drop/DropTable;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/List;)V", "common"})
public final class LootDroppedEvent
extends Cancelable {
    @NotNull
    private final DropTable table;
    @Nullable
    private final ServerPlayer player;
    @Nullable
    private final LivingEntity entity;
    @NotNull
    private final List<DropEntry> drops;

    public LootDroppedEvent(@NotNull DropTable table, @Nullable ServerPlayer player, @Nullable LivingEntity entity2, @NotNull List<DropEntry> drops) {
        Intrinsics.checkNotNullParameter((Object)table, (String)"table");
        Intrinsics.checkNotNullParameter(drops, (String)"drops");
        this.table = table;
        this.player = player;
        this.entity = entity2;
        this.drops = drops;
    }

    @NotNull
    public final DropTable getTable() {
        return this.table;
    }

    @Nullable
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @Nullable
    public final LivingEntity getEntity() {
        return this.entity;
    }

    @NotNull
    public final List<DropEntry> getDrops() {
        return this.drops;
    }
}

