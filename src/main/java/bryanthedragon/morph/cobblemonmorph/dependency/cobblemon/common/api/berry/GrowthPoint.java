/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J$\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u00022\b\b\u0002\u0010\u0007\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\u001a\u0010\f\u001a\u00020\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0006\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0014\u001a\u0004\b\u0015\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0014\u001a\u0004\b\u0016\u0010\u0004\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "", "Lnet/minecraft/world/phys/Vec3;", "component1", "()Lnet/minecraft/world/phys/Vec3;", "component2", "position", "rotation", "copy", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/phys/Vec3;", "getPosition", "getRotation", "<init>", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)V", "common"})
public final class GrowthPoint {
    @NotNull
    private final Vec3 position;
    @NotNull
    private final Vec3 rotation;

    public GrowthPoint(@NotNull Vec3 position, @NotNull Vec3 rotation) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        this.position = position;
        this.rotation = rotation;
    }

    @NotNull
    public final Vec3 getPosition() {
        return this.position;
    }

    @NotNull
    public final Vec3 getRotation() {
        return this.rotation;
    }

    @NotNull
    public final Vec3 component1() {
        return this.position;
    }

    @NotNull
    public final Vec3 component2() {
        return this.rotation;
    }

    @NotNull
    public final GrowthPoint copy(@NotNull Vec3 position, @NotNull Vec3 rotation) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        return new GrowthPoint(position, rotation);
    }

    public static /* synthetic */ GrowthPoint copy$default(GrowthPoint growthPoint, Vec3 vec3, Vec3 vec32, int n, Object object) {
        if ((n & 1) != 0) {
            vec3 = growthPoint.position;
        }
        if ((n & 2) != 0) {
            vec32 = growthPoint.rotation;
        }
        return growthPoint.copy(vec3, vec32);
    }

    @NotNull
    public String toString() {
        return "GrowthPoint(position=" + this.position + ", rotation=" + this.rotation + ")";
    }

    public int hashCode() {
        int result = this.position.hashCode();
        result = result * 31 + this.rotation.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GrowthPoint)) {
            return false;
        }
        GrowthPoint growthPoint = (GrowthPoint)other;
        if (!Intrinsics.areEqual((Object)this.position, (Object)growthPoint.position)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.rotation, (Object)growthPoint.rotation);
    }
}

