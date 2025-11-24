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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/dialogue/DialogueOpenedHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueOpenedPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueOpenedPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class DialogueOpenedHandler
implements ClientNetworkPacketHandler<DialogueOpenedPacket> {
    @NotNull
    public static final DialogueOpenedHandler INSTANCE = new DialogueOpenedHandler();

    private DialogueOpenedHandler() {
    }

    @Override
    public void handle(@NotNull DialogueOpenedPacket packet, @NotNull Minecraft client) {
        DialogueScreen currentScreen;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        Screen screen = client.f_91080_;
        DialogueScreen dialogueScreen = currentScreen = screen instanceof DialogueScreen ? (DialogueScreen)screen : null;
        if (currentScreen != null && Intrinsics.areEqual((Object)currentScreen.getDialogueId(), (Object)packet.getDialogueDTO().getDialogueId())) {
            currentScreen.update(packet.getDialogueDTO());
        } else {
            client.m_91152_((Screen)new DialogueScreen(packet.getDialogueDTO()));
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull DialogueOpenedPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

