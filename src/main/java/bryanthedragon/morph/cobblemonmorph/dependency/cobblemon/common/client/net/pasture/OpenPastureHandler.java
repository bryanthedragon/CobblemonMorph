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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/pasture/OpenPastureHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class OpenPastureHandler
implements ClientNetworkPacketHandler<OpenPasturePacket> {
    @NotNull
    public static final OpenPastureHandler INSTANCE = new OpenPastureHandler();

    private OpenPastureHandler() {
    }

    @Override
    public void handle(@NotNull OpenPasturePacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        UUID uUID = packet.getPastureId();
        int n = packet.getLimit();
        PasturePermissions pasturePermissions = packet.getPermissions();
        SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>> settableObservable = new SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>>(packet.getTetheredPokemon());
        PasturePCGUIConfiguration pcConfiguration = new PasturePCGUIConfiguration(uUID, n, settableObservable, pasturePermissions);
        ClientPC clientPC = CobblemonClient.INSTANCE.getStorage().getPcStores().get(packet.getPcId());
        Intrinsics.checkNotNull((Object)clientPC);
        client.m_91152_((Screen)new PCGUI(clientPC, CobblemonClient.INSTANCE.getStorage().getMyParty(), pcConfiguration));
    }

    @Override
    public void handleOnNettyThread(@NotNull OpenPasturePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

