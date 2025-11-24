/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/net/serializers/PoseTypeDataSerializer;", "Lnet/minecraft/network/syncher/EntityDataSerializer;", "Lcom/cobblemon/mod/common/entity/PoseType;", "value", "copy", "(Lcom/cobblemon/mod/common/entity/PoseType;)Lcom/cobblemon/mod/common/entity/PoseType;", "Lnet/minecraft/network/FriendlyByteBuf;", "buf", "read", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/entity/PoseType;", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/entity/PoseType;)V", "<init>", "()V", "common"})
public final class PoseTypeDataSerializer
implements EntityDataSerializer<PoseType> {
    @NotNull
    public static final PoseTypeDataSerializer INSTANCE = new PoseTypeDataSerializer();

    private PoseTypeDataSerializer() {
    }

    @NotNull
    public PoseType read(@NotNull FriendlyByteBuf buf) {
        Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
        return PoseType.values()[buf.readInt()];
    }

    @NotNull
    public PoseType copy(@NotNull PoseType value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)value2), (String)"value");
        return value2;
    }

    public void write(@NotNull FriendlyByteBuf buf, @NotNull PoseType value2) {
        Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
        Intrinsics.checkNotNullParameter((Object)((Object)value2), (String)"value");
        buf.writeInt(value2.ordinal());
    }
}

