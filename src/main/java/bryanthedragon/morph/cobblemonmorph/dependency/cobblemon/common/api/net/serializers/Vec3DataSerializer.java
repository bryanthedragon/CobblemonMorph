/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/net/serializers/Vec3DataSerializer;", "Lnet/minecraft/network/syncher/EntityDataSerializer;", "Lnet/minecraft/world/phys/Vec3;", "vec", "copy", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "read", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/world/phys/Vec3;", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/world/phys/Vec3;)V", "<init>", "()V", "common"})
public final class Vec3DataSerializer
implements EntityDataSerializer<Vec3> {
    @NotNull
    public static final Vec3DataSerializer INSTANCE = new Vec3DataSerializer();

    private Vec3DataSerializer() {
    }

    public void write(@NotNull FriendlyByteBuf buffer, @NotNull Vec3 vec) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)vec, (String)"vec");
        buffer.writeDouble(vec.f_82479_);
        buffer.writeDouble(vec.f_82480_);
        buffer.writeDouble(vec.f_82481_);
    }

    @NotNull
    public Vec3 read(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        return new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    @NotNull
    public Vec3 copy(@NotNull Vec3 vec) {
        Intrinsics.checkNotNullParameter((Object)vec, (String)"vec");
        return new Vec3(vec.f_82479_, vec.f_82480_, vec.f_82481_);
    }
}

