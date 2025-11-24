/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.TradeGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/trade/TradeStartedHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nTradeStartedHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeStartedHandler.kt\ncom/cobblemon/mod/common/client/net/trade/TradeStartedHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1549#2:34\n1620#2,2:35\n1622#2:38\n1#3:37\n*S KotlinDebug\n*F\n+ 1 TradeStartedHandler.kt\ncom/cobblemon/mod/common/client/net/trade/TradeStartedHandler\n*L\n29#1:34\n29#1:35,2\n29#1:38\n*E\n"})
public final class TradeStartedHandler
implements ClientNetworkPacketHandler<TradeStartedPacket> {
    @NotNull
    public static final TradeStartedHandler INSTANCE = new TradeStartedHandler();

    private TradeStartedHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull TradeStartedPacket packet, @NotNull Minecraft client) {
        Collection<TradeStartedPacket.TradeablePokemon> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ClientTrade trade2 = new ClientTrade();
        CobblemonClient.INSTANCE.setTrade(trade2);
        Iterable iterable = CobblemonClient.INSTANCE.getStorage().getMyParty();
        List list = CollectionsKt.toMutableList((Collection)packet.getTraderParty());
        MutableComponent mutableComponent = packet.getTraderName();
        UUID uUID = packet.getTraderId();
        ClientTrade clientTrade = trade2;
        Minecraft minecraft = Minecraft.m_91087_();
        boolean $i$f$map = false;
        void var6_11 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            TradeStartedPacket.TradeablePokemon tradeablePokemon;
            void it;
            Pokemon pokemon = (Pokemon)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            if (it != null) {
                void p0;
                boolean bl2 = false;
                tradeablePokemon = new TradeStartedPacket.TradeablePokemon((Pokemon)p0);
            } else {
                tradeablePokemon = null;
            }
            collection.add(tradeablePokemon);
        }
        collection = (List)destination$iv$iv;
        List list2 = CollectionsKt.toMutableList((Collection)collection);
        List list3 = list;
        MutableComponent mutableComponent2 = mutableComponent;
        UUID uUID2 = uUID;
        ClientTrade clientTrade2 = clientTrade;
        minecraft.m_91152_((Screen)new TradeGUI(clientTrade2, uUID2, mutableComponent2, list3, list2));
    }

    @Override
    public void handleOnNettyThread(@NotNull TradeStartedPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

