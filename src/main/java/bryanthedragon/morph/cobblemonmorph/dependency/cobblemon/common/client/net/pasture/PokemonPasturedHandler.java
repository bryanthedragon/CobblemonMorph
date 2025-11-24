/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonPasturedPacket;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/pasture/PokemonPasturedHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonPasturedPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonPasturedPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class PokemonPasturedHandler
implements ClientNetworkPacketHandler<PokemonPasturedPacket> {
    @NotNull
    public static final PokemonPasturedHandler INSTANCE = new PokemonPasturedHandler();

    private PokemonPasturedHandler() {
    }

    @Override
    public void handle(@NotNull PokemonPasturedPacket packet, @NotNull Minecraft client) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            Screen screen = Minecraft.m_91087_().f_91080_;
            PCGUI pCGUI = screen instanceof PCGUI ? (PCGUI)screen : null;
            PCGUIConfiguration pCGUIConfiguration = pCGUI != null ? pCGUI.getConfiguration() : null;
            PasturePCGUIConfiguration pastureGuiConfiguration = pCGUIConfiguration instanceof PasturePCGUIConfiguration ? (PasturePCGUIConfiguration)pCGUIConfiguration : null;
            Object object = pastureGuiConfiguration;
            if (object == null || (object = ((PasturePCGUIConfiguration)object).getPasturedPokemon()) == null) break block0;
            ((SettableObservable)object).set(CollectionsKt.plus((Collection)pastureGuiConfiguration.getPasturedPokemon().get(), (Object)packet.getPasturePokemonDTO()));
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull PokemonPasturedPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

