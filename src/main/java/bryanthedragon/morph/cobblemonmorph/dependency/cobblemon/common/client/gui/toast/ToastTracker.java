/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.toasts.Toast
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast.CobblemonToast;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bR0\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/gui/toast/ToastTracker;", "", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;Lnet/minecraft/client/Minecraft;)V", "Ljava/util/HashMap;", "Ljava/util/UUID;", "Lcom/cobblemon/mod/common/client/gui/toast/CobblemonToast;", "Lkotlin/collections/HashMap;", "toasts", "Ljava/util/HashMap;", "<init>", "()V", "common"})
public final class ToastTracker {
    @NotNull
    public static final ToastTracker INSTANCE = new ToastTracker();
    @NotNull
    private static final HashMap<UUID, CobblemonToast> toasts = new HashMap();

    private ToastTracker() {
    }

    public final void handle(@NotNull ToastPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        boolean needsQueue = false;
        CobblemonToast toast = toasts.get(packet.getUuid());
        if (toast == null) {
            toast = new CobblemonToast(packet);
            ((Map)toasts).put(packet.getUuid(), toast);
            needsQueue = true;
        }
        toast.updateFrom$common(packet);
        if (needsQueue) {
            client.m_91300_().m_94922_((Toast)toast);
        }
    }
}

