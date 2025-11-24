/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0010\u001a\u00020\u0002\u0012\u0006\u0010\u0011\u001a\u00020\u0005\u0012\u0006\u0010\u0012\u001a\u00020\b\u0012\u0006\u0010\u0013\u001a\u00020\b\u0012\u0006\u0010\u0014\u001a\u00020\b\u0012\u0006\u0010\u0015\u001a\u00020\b\u0012\u0006\u0010\u0016\u001a\u00020\b\u0012\u0006\u0010\u0017\u001a\u00020\b\u00a2\u0006\u0004\b0\u00101J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000b\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\nJ\u0010\u0010\f\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\nJ\u0010\u0010\r\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\nJ\u0010\u0010\u000e\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000e\u0010\nJ\u0010\u0010\u000f\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\nJ`\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\b2\b\b\u0002\u0010\u0013\u001a\u00020\b2\b\b\u0002\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\b2\b\b\u0002\u0010\u0016\u001a\u00020\b2\b\b\u0002\u0010\u0017\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001a\u0010\u001c\u001a\u00020\u001b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\r\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010!\u001a\u00020\bH\u00d6\u0001\u00a2\u0006\u0004\b!\u0010\nJ\u0010\u0010#\u001a\u00020\"H\u00d6\u0001\u00a2\u0006\u0004\b#\u0010$R\u0017\u0010\u0012\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010%\u001a\u0004\b&\u0010\nR\u0017\u0010\u0013\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010%\u001a\u0004\b'\u0010\nR\u0017\u0010\u0014\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010%\u001a\u0004\b(\u0010\nR\u0017\u0010\u0010\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010)\u001a\u0004\b*\u0010\u0004R\u0017\u0010\u0016\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010%\u001a\u0004\b+\u0010\nR\u0017\u0010\u0015\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010%\u001a\u0004\b,\u0010\nR\u0017\u0010\u0017\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010%\u001a\u0004\b-\u0010\nR\u0017\u0010\u0011\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010.\u001a\u0004\b/\u0010\u0007\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "component1", "()Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "Lnet/minecraft/server/level/ServerLevel;", "component2", "()Lnet/minecraft/server/level/ServerLevel;", "", "component3", "()I", "component4", "component5", "component6", "component7", "component8", "cause", "world", "baseX", "baseY", "baseZ", "length", "height", "width", "copy", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;IIIIII)Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "other", "", "equals", "(Ljava/lang/Object;)Z", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "()Lnet/minecraft/world/phys/Vec3;", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getBaseX", "getBaseY", "getBaseZ", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "getCause", "getHeight", "getLength", "getWidth", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;IIIIII)V", "common"})
public final class SpawningArea {
    @NotNull
    private final SpawnCause cause;
    @NotNull
    private final ServerLevel world;
    private final int baseX;
    private final int baseY;
    private final int baseZ;
    private final int length;
    private final int height;
    private final int width;

    public SpawningArea(@NotNull SpawnCause cause, @NotNull ServerLevel world, int baseX, int baseY, int baseZ, int length, int height, int width) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this.cause = cause;
        this.world = world;
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.length = length;
        this.height = height;
        this.width = width;
    }

    @NotNull
    public final SpawnCause getCause() {
        return this.cause;
    }

    @NotNull
    public final ServerLevel getWorld() {
        return this.world;
    }

    public final int getBaseX() {
        return this.baseX;
    }

    public final int getBaseY() {
        return this.baseY;
    }

    public final int getBaseZ() {
        return this.baseZ;
    }

    public final int getLength() {
        return this.length;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    @NotNull
    public final Vec3 getCenter() {
        return new Vec3((double)this.baseX + (double)this.length / 2.0, (double)this.baseY + (double)this.height / 2.0, (double)this.baseZ + (double)this.width / 2.0);
    }

    @NotNull
    public final SpawnCause component1() {
        return this.cause;
    }

    @NotNull
    public final ServerLevel component2() {
        return this.world;
    }

    public final int component3() {
        return this.baseX;
    }

    public final int component4() {
        return this.baseY;
    }

    public final int component5() {
        return this.baseZ;
    }

    public final int component6() {
        return this.length;
    }

    public final int component7() {
        return this.height;
    }

    public final int component8() {
        return this.width;
    }

    @NotNull
    public final SpawningArea copy(@NotNull SpawnCause cause, @NotNull ServerLevel world, int baseX, int baseY, int baseZ, int length, int height, int width) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return new SpawningArea(cause, world, baseX, baseY, baseZ, length, height, width);
    }

    public static /* synthetic */ SpawningArea copy$default(SpawningArea spawningArea, SpawnCause spawnCause, ServerLevel serverLevel, int n, int n2, int n3, int n4, int n5, int n6, int n7, Object object) {
        if ((n7 & 1) != 0) {
            spawnCause = spawningArea.cause;
        }
        if ((n7 & 2) != 0) {
            serverLevel = spawningArea.world;
        }
        if ((n7 & 4) != 0) {
            n = spawningArea.baseX;
        }
        if ((n7 & 8) != 0) {
            n2 = spawningArea.baseY;
        }
        if ((n7 & 0x10) != 0) {
            n3 = spawningArea.baseZ;
        }
        if ((n7 & 0x20) != 0) {
            n4 = spawningArea.length;
        }
        if ((n7 & 0x40) != 0) {
            n5 = spawningArea.height;
        }
        if ((n7 & 0x80) != 0) {
            n6 = spawningArea.width;
        }
        return spawningArea.copy(spawnCause, serverLevel, n, n2, n3, n4, n5, n6);
    }

    @NotNull
    public String toString() {
        return "SpawningArea(cause=" + this.cause + ", world=" + this.world + ", baseX=" + this.baseX + ", baseY=" + this.baseY + ", baseZ=" + this.baseZ + ", length=" + this.length + ", height=" + this.height + ", width=" + this.width + ")";
    }

    public int hashCode() {
        int result = this.cause.hashCode();
        result = result * 31 + this.world.hashCode();
        result = result * 31 + Integer.hashCode(this.baseX);
        result = result * 31 + Integer.hashCode(this.baseY);
        result = result * 31 + Integer.hashCode(this.baseZ);
        result = result * 31 + Integer.hashCode(this.length);
        result = result * 31 + Integer.hashCode(this.height);
        result = result * 31 + Integer.hashCode(this.width);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpawningArea)) {
            return false;
        }
        SpawningArea spawningArea = (SpawningArea)other;
        if (!Intrinsics.areEqual((Object)this.cause, (Object)spawningArea.cause)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.world, (Object)spawningArea.world)) {
            return false;
        }
        if (this.baseX != spawningArea.baseX) {
            return false;
        }
        if (this.baseY != spawningArea.baseY) {
            return false;
        }
        if (this.baseZ != spawningArea.baseZ) {
            return false;
        }
        if (this.length != spawningArea.length) {
            return false;
        }
        if (this.height != spawningArea.height) {
            return false;
        }
        return this.width == spawningArea.width;
    }
}

