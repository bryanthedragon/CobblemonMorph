/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/dialogue/DialogueClosedHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueClosedPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueClosedPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class DialogueClosedHandler
implements ClientNetworkPacketHandler<DialogueClosedPacket> {
    @NotNull
    public static final DialogueClosedHandler INSTANCE = new DialogueClosedHandler();

    private DialogueClosedHandler() {
    }

    @Override
    public void handle(@NotNull DialogueClosedPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        Screen screen = client.f_91080_;
        DialogueScreen dialogueScreen = screen instanceof DialogueScreen ? (DialogueScreen)screen : null;
        if (dialogueScreen == null) {
            return;
        }
        DialogueScreen currentScreen = dialogueScreen;
        if (packet.getDialogueId() == null || Intrinsics.areEqual((Object)currentScreen.getDialogueId(), (Object)packet.getDialogueId())) {
            client.m_91152_(null);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull DialogueClosedPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

