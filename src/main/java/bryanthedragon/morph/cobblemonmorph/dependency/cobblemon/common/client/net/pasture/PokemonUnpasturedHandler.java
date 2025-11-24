/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/pasture/PokemonUnpasturedHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonUnpasturedPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonUnpasturedPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonUnpasturedHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonUnpasturedHandler.kt\ncom/cobblemon/mod/common/client/net/pasture/PokemonUnpasturedHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,29:1\n819#2:30\n847#2,2:31\n*S KotlinDebug\n*F\n+ 1 PokemonUnpasturedHandler.kt\ncom/cobblemon/mod/common/client/net/pasture/PokemonUnpasturedHandler\n*L\n27#1:30\n27#1:31,2\n*E\n"})
public final class PokemonUnpasturedHandler
implements ClientNetworkPacketHandler<PokemonUnpasturedPacket> {
    @NotNull
    public static final PokemonUnpasturedHandler INSTANCE = new PokemonUnpasturedHandler();

    private PokemonUnpasturedHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull PokemonUnpasturedPacket packet, @NotNull Minecraft client) {
        block1: {
            void $this$filterNotTo$iv$iv;
            void $this$filterNot$iv;
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            Screen screen = Minecraft.m_91087_().f_91080_;
            PCGUI pCGUI = screen instanceof PCGUI ? (PCGUI)screen : null;
            PCGUIConfiguration pCGUIConfiguration = pCGUI != null ? pCGUI.getConfiguration() : null;
            PasturePCGUIConfiguration pastureGuiConfiguration = pCGUIConfiguration instanceof PasturePCGUIConfiguration ? (PasturePCGUIConfiguration)pCGUIConfiguration : null;
            Object object = pastureGuiConfiguration;
            if (object == null || (object = ((PasturePCGUIConfiguration)object).getPasturedPokemon()) == null) break block1;
            Iterable iterable = pastureGuiConfiguration.getPasturedPokemon().get();
            Object object2 = object;
            boolean $i$f$filterNot = false;
            void var7_9 = $this$filterNot$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterNotTo = false;
            for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
                OpenPasturePacket.PasturePokemonDataDTO it = (OpenPasturePacket.PasturePokemonDataDTO)element$iv$iv;
                boolean bl = false;
                if (Intrinsics.areEqual((Object)it.getPokemonId(), (Object)packet.getPokemonId())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            ((SettableObservable)object2).set((List)destination$iv$iv);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull PokemonUnpasturedPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

