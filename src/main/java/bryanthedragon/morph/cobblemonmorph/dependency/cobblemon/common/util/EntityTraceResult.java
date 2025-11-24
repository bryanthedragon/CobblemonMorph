/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u001d\u0012\u0006\u0010\n\u001a\u00020\t\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/util/EntityTraceResult;", "Lnet/minecraft/world/entity/Entity;", "T", "", "", "entities", "Ljava/lang/Iterable;", "getEntities", "()Ljava/lang/Iterable;", "Lnet/minecraft/world/phys/Vec3;", "location", "Lnet/minecraft/world/phys/Vec3;", "getLocation", "()Lnet/minecraft/world/phys/Vec3;", "<init>", "(Lnet/minecraft/world/phys/Vec3;Ljava/lang/Iterable;)V", "common"})
public final class EntityTraceResult<T extends Entity> {
    @NotNull
    private final Vec3 location;
    @NotNull
    private final Iterable<T> entities;

    public EntityTraceResult(@NotNull Vec3 location, @NotNull Iterable<? extends T> entities2) {
        Intrinsics.checkNotNullParameter((Object)location, (String)"location");
        Intrinsics.checkNotNullParameter(entities2, (String)"entities");
        this.location = location;
        this.entities = entities2;
    }

    @NotNull
    public final Vec3 getLocation() {
        return this.location;
    }

    @NotNull
    public final Iterable<T> getEntities() {
        return this.entities;
    }
}

