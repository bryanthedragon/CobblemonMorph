package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingIn
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingOut
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent.ClientTickEvent
import net.minecraftforge.event.TickEvent.Phase
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

@OnlyIn(Dist.CLIENT)
@SourceDebugExtension(["SMAP\nForgeClientPlatformEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgeClientPlatformEventHandler.kt\ncom/cobblemon/mod/forge/client/ForgeClientPlatformEventHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,56:1\n14#2,5:57\n19#2:65\n14#2,5:66\n19#2:74\n14#2,5:75\n19#2:83\n14#2,5:84\n19#2:92\n14#2,5:93\n19#2:101\n13579#3:62\n13580#3:64\n13579#3:71\n13580#3:73\n13579#3:80\n13580#3:82\n13579#3:89\n13580#3:91\n13579#3:98\n13580#3:100\n14#4:63\n14#4:72\n14#4:81\n14#4:90\n14#4:99\n*S KotlinDebug\n*F\n+ 1 ForgeClientPlatformEventHandler.kt\ncom/cobblemon/mod/forge/client/ForgeClientPlatformEventHandler\n*L\n34#1:57,5\n34#1:65\n37#1:66,5\n37#1:74\n43#1:75,5\n43#1:83\n48#1:84,5\n48#1:92\n53#1:93,5\n53#1:101\n34#1:62\n34#1:64\n37#1:71\n37#1:73\n43#1:80\n43#1:82\n48#1:89\n48#1:91\n53#1:98\n53#1:100\n34#1:63\n37#1:72\n43#1:81\n48#1:90\n53#1:99\n*E\n"])
public object ForgeClientPlatformEventHandler {
   public fun register() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public fun onTick(e: ClientTickEvent) {
      if (e.phase === Phase.START) {
         val `$this$iv`: EventObservable = PlatformEvents.CLIENT_TICK_PRE;
         val `events$iv`: Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Pre> = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Pre[1];
         val var10004: Minecraft = Minecraft.m_91087_();
         `events$iv`[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Pre(var10004);
         `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

         for (Object element$iv$iv : events$iv) {
            ;
         }
      } else {
         val var12: EventObservable = PlatformEvents.CLIENT_TICK_POST;
         val var13: Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Post> = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Post[1];
         val var21: Minecraft = Minecraft.m_91087_();
         var13[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Post(var21);
         var12.emit(Arrays.copyOf(var13, var13.length));

         for (Object element$iv$iv : var13) {
            ;
         }
      }
   }

   @SubscribeEvent
   public fun onLogin(e: LoggingIn) {
      val `$this$iv`: EventObservable = PlatformEvents.CLIENT_PLAYER_LOGIN;
      val `events$iv`: Array<ClientPlayerEvent.Login> = new ClientPlayerEvent.Login[1];
      val var10004: LocalPlayer = e.getPlayer();
      `events$iv`[0] = new ClientPlayerEvent.Login(var10004);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }

   @SubscribeEvent
   public fun onLogout(e: LoggingOut) {
      val `$this$iv`: EventObservable = PlatformEvents.CLIENT_PLAYER_LOGOUT;
      val `events$iv`: Array<ClientPlayerEvent.Logout> = new ClientPlayerEvent.Logout[1];
      val var10002: ClientPlayerEvent.Logout = new ClientPlayerEvent.Logout;
      val var10004: LocalPlayer = e.getPlayer();
      if (var10004 != null) {
         var10002./* $VF: Unable to resugar constructor */<init>(var10004);
         `events$iv`[0] = var10002;
         `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

         for (Object element$iv$iv : events$iv) {
            ;
         }
      }
   }

   @SubscribeEvent
   public fun onItemTooltip(e: ItemTooltipEvent) {
      val `$this$iv`: EventObservable = PlatformEvents.CLIENT_ITEM_TOOLTIP;
      val `events$iv`: Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent> = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent[1];
      val var10004: ItemStack = e.getItemStack();
      val var10005: TooltipFlag = e.getFlags();
      val var10006: java.util.List = e.getToolTip();
      `events$iv`[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent(var10004, var10005, var10006);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }
}
