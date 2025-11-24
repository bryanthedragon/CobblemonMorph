/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization;

import kotlin.Metadata;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/serialization/BufferSerializer;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "toClient", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;Z)V", "common"})
public interface BufferSerializer {
    public void saveToBuffer(@NotNull FriendlyByteBuf var1, boolean var2);

    public void loadFromBuffer(@NotNull FriendlyByteBuf var1);
}

