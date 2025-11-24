/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0014\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0004B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00028\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/net/data/DataRegistrySyncPacketHandler;", "P", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "T", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class DataRegistrySyncPacketHandler<P, T extends DataRegistrySyncPacket<P, T>>
implements ClientNetworkPacketHandler<T> {
    @Override
    public void handle(@NotNull T packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ((DataRegistrySyncPacket)packet).getEntries$common().clear();
        ArrayList arrayList = ((DataRegistrySyncPacket)packet).getEntries$common();
        FriendlyByteBuf friendlyByteBuf = ((DataRegistrySyncPacket)packet).getBuffer();
        Intrinsics.checkNotNull((Object)friendlyByteBuf);
        List list = friendlyByteBuf.m_236845_(arg_0 -> packet.decodeEntry(arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"packet.buffer!!.readList(packet::decodeEntry)");
        arrayList.addAll(CollectionsKt.filterNotNull((Iterable)list));
        FriendlyByteBuf friendlyByteBuf2 = ((DataRegistrySyncPacket)packet).getBuffer();
        Intrinsics.checkNotNull((Object)friendlyByteBuf2);
        friendlyByteBuf2.release();
        ((DataRegistrySyncPacket)packet).synchronizeDecoded(((DataRegistrySyncPacket)packet).getEntries$common());
    }

    @Override
    public void handleOnNettyThread(@NotNull T packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

