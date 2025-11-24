/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.ClientPlayerNetworkEvent$LoggingIn
 *  net.minecraftforge.client.event.ClientPlayerNetworkEvent$LoggingOut
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

@OnlyIn(value=Dist.CLIENT)
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c7\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0011J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0007H\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\nH\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\rH\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/forge/client/ForgeClientPlatformEventHandler;", "", "Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;", "e", "", "onItemTooltip", "(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V", "Lnet/minecraftforge/client/event/ClientPlayerNetworkEvent$LoggingIn;", "onLogin", "(Lnet/minecraftforge/client/event/ClientPlayerNetworkEvent$LoggingIn;)V", "Lnet/minecraftforge/client/event/ClientPlayerNetworkEvent$LoggingOut;", "onLogout", "(Lnet/minecraftforge/client/event/ClientPlayerNetworkEvent$LoggingOut;)V", "Lnet/minecraftforge/event/TickEvent$ClientTickEvent;", "onTick", "(Lnet/minecraftforge/event/TickEvent$ClientTickEvent;)V", "register", "()V", "<init>", "forge"})
@SourceDebugExtension(value={"SMAP\nForgeClientPlatformEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgeClientPlatformEventHandler.kt\ncom/cobblemon/mod/forge/client/ForgeClientPlatformEventHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,56:1\n14#2,5:57\n19#2:65\n14#2,5:66\n19#2:74\n14#2,5:75\n19#2:83\n14#2,5:84\n19#2:92\n14#2,5:93\n19#2:101\n13579#3:62\n13580#3:64\n13579#3:71\n13580#3:73\n13579#3:80\n13580#3:82\n13579#3:89\n13580#3:91\n13579#3:98\n13580#3:100\n14#4:63\n14#4:72\n14#4:81\n14#4:90\n14#4:99\n*S KotlinDebug\n*F\n+ 1 ForgeClientPlatformEventHandler.kt\ncom/cobblemon/mod/forge/client/ForgeClientPlatformEventHandler\n*L\n34#1:57,5\n34#1:65\n37#1:66,5\n37#1:74\n43#1:75,5\n43#1:83\n48#1:84,5\n48#1:92\n53#1:93,5\n53#1:101\n34#1:62\n34#1:64\n37#1:71\n37#1:73\n43#1:80\n43#1:82\n48#1:89\n48#1:91\n53#1:98\n53#1:100\n34#1:63\n37#1:72\n43#1:81\n48#1:90\n53#1:99\n*E\n"})
public final class ForgeClientPlatformEventHandler {
    @NotNull
    public static final ForgeClientPlatformEventHandler INSTANCE = new ForgeClientPlatformEventHandler();

    private ForgeClientPlatformEventHandler() {
    }

    public final void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onTick(@NotNull TickEvent.ClientTickEvent e) {
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        if (e.phase == TickEvent.Phase.START) {
            void events$iv;
            void $this$iv;
            EventObservable<ClientTickEvent.Pre> eventObservable = PlatformEvents.CLIENT_TICK_PRE;
            ClientTickEvent.Pre[] preArray = new ClientTickEvent.Pre[1];
            Minecraft minecraft = Minecraft.m_91087_();
            Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance()");
            preArray[0] = new ClientTickEvent.Pre(minecraft);
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
            void $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = ((void)$this$forEach$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void var10_18 = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                void it = var10_18;
            }
        } else {
            EventObservable<ClientTickEvent.Post> $this$iv = PlatformEvents.CLIENT_TICK_POST;
            ClientTickEvent.Post[] events$iv = new ClientTickEvent.Post[1];
            Minecraft minecraft = Minecraft.m_91087_();
            Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance()");
            events$iv[0] = new ClientTickEvent.Post(minecraft);
            boolean $i$f$post = false;
            $this$iv.emit((ClientTickEvent.Post[])Arrays.copyOf(events$iv, events$iv.length));
            ClientTickEvent.Post[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                ClientTickEvent.Post element$iv$iv;
                ClientTickEvent.Post it = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onLogin(@NotNull ClientPlayerNetworkEvent.LoggingIn e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ClientPlayerEvent.Login> eventObservable = PlatformEvents.CLIENT_PLAYER_LOGIN;
        ClientPlayerEvent.Login[] loginArray = new ClientPlayerEvent.Login[1];
        LocalPlayer localPlayer = e.getPlayer();
        Intrinsics.checkNotNullExpressionValue((Object)localPlayer, (String)"e.player");
        loginArray[0] = new ClientPlayerEvent.Login(localPlayer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onLogout(@NotNull ClientPlayerNetworkEvent.LoggingOut e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<ClientPlayerEvent.Logout> eventObservable = PlatformEvents.CLIENT_PLAYER_LOGOUT;
        ClientPlayerEvent.Logout[] logoutArray = new ClientPlayerEvent.Logout[1];
        LocalPlayer localPlayer = e.getPlayer();
        if (localPlayer == null) {
            return;
        }
        logoutArray[0] = new ClientPlayerEvent.Logout(localPlayer);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public final void onItemTooltip(@NotNull ItemTooltipEvent e) {
        void events$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)e, (String)"e");
        EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent> eventObservable = PlatformEvents.CLIENT_ITEM_TOOLTIP;
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent[] itemTooltipEventArray = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent[1];
        ItemStack itemStack = e.getItemStack();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"e.itemStack");
        TooltipFlag tooltipFlag = e.getFlags();
        Intrinsics.checkNotNullExpressionValue((Object)tooltipFlag, (String)"e.flags");
        List list = e.getToolTip();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"e.toolTip");
        itemTooltipEventArray[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent(itemStack, tooltipFlag, list);
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
        void $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = ((void)$this$forEach$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var10_10 = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            void it = var10_10;
        }
    }
}

