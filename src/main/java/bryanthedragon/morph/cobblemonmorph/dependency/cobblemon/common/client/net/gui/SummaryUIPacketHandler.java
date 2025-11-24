/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.SummaryUIPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/gui/SummaryUIPacketHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSummaryUIPacketHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacketHandler.kt\ncom/cobblemon/mod/common/client/net/gui/SummaryUIPacketHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,28:1\n1549#2:29\n1620#2,3:30\n*S KotlinDebug\n*F\n+ 1 SummaryUIPacketHandler.kt\ncom/cobblemon/mod/common/client/net/gui/SummaryUIPacketHandler\n*L\n21#1:29\n21#1:30,3\n*E\n"})
public final class SummaryUIPacketHandler
implements ClientNetworkPacketHandler<SummaryUIPacket> {
    @NotNull
    public static final SummaryUIPacketHandler INSTANCE = new SummaryUIPacketHandler();

    private SummaryUIPacketHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull SummaryUIPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        try {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = packet.getPokemon();
            Summary.Companion companion = Summary.Companion;
            boolean $i$f$map = false;
            void var5_7 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PokemonDTO pokemonDTO = (PokemonDTO)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.create());
            }
            Summary.Companion.open$default(companion, (List)destination$iv$iv, packet.getEditable(), 0, 4, null);
        }
        catch (Exception e) {
            Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the SummaryUI packet handler", (Throwable)e);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull SummaryUIPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

