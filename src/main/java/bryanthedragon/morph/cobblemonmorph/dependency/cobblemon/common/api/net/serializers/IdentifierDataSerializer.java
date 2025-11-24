/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/net/serializers/IdentifierDataSerializer;", "Lnet/minecraft/network/syncher/EntityDataSerializer;", "Lnet/minecraft/resources/ResourceLocation;", "value", "copy", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/FriendlyByteBuf;", "buf", "read", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/resources/ResourceLocation;", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/resources/ResourceLocation;)V", "<init>", "()V", "common"})
public final class IdentifierDataSerializer
implements EntityDataSerializer<ResourceLocation> {
    @NotNull
    public static final IdentifierDataSerializer INSTANCE = new IdentifierDataSerializer();

    private IdentifierDataSerializer() {
    }

    @NotNull
    public ResourceLocation copy(@NotNull ResourceLocation value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        return new ResourceLocation(value2.m_135827_(), value2.m_135815_());
    }

    @NotNull
    public ResourceLocation read(@NotNull FriendlyByteBuf buf) {
        Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
        return new ResourceLocation(buf.m_130277_(), buf.m_130277_());
    }

    public void write(@NotNull FriendlyByteBuf buf, @NotNull ResourceLocation value2) {
        Intrinsics.checkNotNullParameter((Object)buf, (String)"buf");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        buf.m_130070_(value2.m_135827_());
        buf.m_130070_(value2.m_135815_());
    }
}

