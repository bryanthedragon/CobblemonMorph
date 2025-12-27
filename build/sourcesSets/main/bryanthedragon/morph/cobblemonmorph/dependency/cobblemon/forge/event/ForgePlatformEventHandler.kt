package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.event

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ChangeDimensionEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent.Phase
import net.minecraftforge.event.TickEvent.ServerTickEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock
import net.minecraftforge.event.server.ServerAboutToStartEvent
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.event.server.ServerStoppingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.server.ServerLifecycleHooks

@SourceDebugExtension(["SMAP\nForgePlatformEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgePlatformEventHandler.kt\ncom/cobblemon/mod/forge/event/ForgePlatformEventHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n*L\n1#1,123:1\n14#2,5:124\n19#2:132\n14#2,5:133\n19#2:141\n14#2,5:142\n19#2:150\n14#2,5:151\n19#2:159\n14#2,5:160\n19#2:168\n14#2,5:169\n19#2:177\n14#2,5:178\n19#2:186\n14#2,5:187\n19#2:195\n17#2,2:197\n19#2:207\n17#2,2:210\n19#2:220\n17#2,2:223\n19#2:233\n14#2,5:235\n19#2:243\n13579#3:129\n13580#3:131\n13579#3:138\n13580#3:140\n13579#3:147\n13580#3:149\n13579#3:156\n13580#3:158\n13579#3:165\n13580#3:167\n13579#3:174\n13580#3:176\n13579#3:183\n13580#3:185\n13579#3:192\n13580#3:194\n13579#3:199\n13580#3:206\n13579#3:212\n13580#3:219\n13579#3:225\n13580#3:232\n13579#3:240\n13580#3:242\n14#4:130\n14#4:139\n14#4:148\n14#4:157\n14#4:166\n14#4:175\n14#4:184\n14#4:193\n14#4:241\n40#5:196\n41#5,6:200\n47#5:208\n40#5:209\n41#5,6:213\n47#5:221\n40#5:222\n41#5,6:226\n47#5:234\n*S KotlinDebug\n*F\n+ 1 ForgePlatformEventHandler.kt\ncom/cobblemon/mod/forge/event/ForgePlatformEventHandler\n*L\n39#1:124,5\n39#1:132\n44#1:133,5\n44#1:141\n50#1:142,5\n50#1:150\n55#1:151,5\n55#1:159\n61#1:160,5\n61#1:168\n64#1:169,5\n64#1:177\n71#1:178,5\n71#1:186\n77#1:187,5\n77#1:195\n83#1:197,2\n83#1:207\n96#1:210,2\n96#1:220\n109#1:223,2\n109#1:233\n120#1:235,5\n120#1:243\n39#1:129\n39#1:131\n44#1:138\n44#1:140\n50#1:147\n50#1:149\n55#1:156\n55#1:158\n61#1:165\n61#1:167\n64#1:174\n64#1:176\n71#1:183\n71#1:185\n77#1:192\n77#1:194\n83#1:199\n83#1:206\n96#1:212\n96#1:219\n109#1:225\n109#1:232\n120#1:240\n120#1:242\n39#1:130\n44#1:139\n50#1:148\n55#1:157\n61#1:166\n64#1:175\n71#1:184\n77#1:193\n120#1:241\n83#1:196\n83#1:200,6\n83#1:208\n96#1:209\n96#1:213,6\n96#1:221\n109#1:222\n109#1:226,6\n109#1:234\n*E\n"])
public object ForgePlatformEventHandler {
   public fun register() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public fun serverStarting(e: ServerAboutToStartEvent) {
      val `$this$iv`: EventObservable = PlatformEvents.SERVER_STARTING;
      val `events$iv`: Array<ServerEvent.Starting> = new ServerEvent.Starting[1];
      val var10004: MinecraftServer = e.getServer();
      `events$iv`[0] = new ServerEvent.Starting(var10004);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }

   @SubscribeEvent
   public fun serverStarted(e: ServerStartedEvent) {
      val `$this$iv`: EventObservable = PlatformEvents.SERVER_STARTED;
      val `events$iv`: Array<ServerEvent.Started> = new ServerEvent.Started[1];
      val var10004: MinecraftServer = e.getServer();
      `events$iv`[0] = new ServerEvent.Started(var10004);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }

   @SubscribeEvent
   public fun serverStopping(e: ServerStoppingEvent) {
      val `$this$iv`: EventObservable = PlatformEvents.SERVER_STOPPING;
      val `events$iv`: Array<ServerEvent.Stopping> = new ServerEvent.Stopping[1];
      val var10004: MinecraftServer = e.getServer();
      `events$iv`[0] = new ServerEvent.Stopping(var10004);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }

   @SubscribeEvent
   public fun serverStopped(e: ServerStoppedEvent) {
      val `$this$iv`: EventObservable = PlatformEvents.SERVER_STOPPED;
      val `events$iv`: Array<ServerEvent.Stopped> = new ServerEvent.Stopped[1];
      val var10004: MinecraftServer = e.getServer();
      `events$iv`[0] = new ServerEvent.Stopped(var10004);
      `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         ;
      }
   }

   @SubscribeEvent
   public fun onTick(e: ServerTickEvent) {
      if (e.phase === Phase.START) {
         val `$this$iv`: EventObservable = PlatformEvents.SERVER_TICK_PRE;
         val `events$iv`: Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre> = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre[1];
         val var10004: MinecraftServer = ServerLifecycleHooks.getCurrentServer();
         `events$iv`[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre(var10004);
         `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

         for (Object element$iv$iv : events$iv) {
            ;
         }
      } else {
         val var12: EventObservable = PlatformEvents.SERVER_TICK_POST;
         val var13: Array<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Post> = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Post[1];
         val var21: MinecraftServer = ServerLifecycleHooks.getCurrentServer();
         var13[0] = new bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Post(var21);
         var12.emit(Arrays.copyOf(var13, var13.length));

         for (Object element$iv$iv : var13) {
            ;
         }
      }
   }

   @SubscribeEvent
   public fun onLogin(e: PlayerLoggedInEvent) {
      val `events$iv`: Player = e.getEntity();
      val var10000: ServerPlayer = `events$iv` as? ServerPlayer;
      if ((`events$iv` as? ServerPlayer) != null) {
         val `$this$iv`: EventObservable = PlatformEvents.SERVER_PLAYER_LOGIN;
         val var13: Array<ServerPlayerEvent.Login> = new ServerPlayerEvent.Login[]{new ServerPlayerEvent.Login(var10000)};
         `$this$iv`.emit(Arrays.copyOf(var13, var13.length));

         for (Object element$iv$iv : var13) {
            ;
         }
      }
   }

   @SubscribeEvent
   public fun onLogout(e: PlayerLoggedOutEvent) {
      val `events$iv`: Player = e.getEntity();
      val var10000: ServerPlayer = `events$iv` as? ServerPlayer;
      if ((`events$iv` as? ServerPlayer) != null) {
         val `$this$iv`: EventObservable = PlatformEvents.SERVER_PLAYER_LOGOUT;
         val var13: Array<ServerPlayerEvent.Logout> = new ServerPlayerEvent.Logout[]{new ServerPlayerEvent.Logout(var10000)};
         `$this$iv`.emit(Arrays.copyOf(var13, var13.length));

         for (Object element$iv$iv : var13) {
            ;
         }
      }
   }

   @SubscribeEvent
   public fun onDeath(e: LivingDeathEvent) {
      val var4: LivingEntity = e.getEntity();
      val var10000: ServerPlayer = var4 as? ServerPlayer;
      if ((var4 as? ServerPlayer) != null) {
         val var3: CancelableObservable = PlatformEvents.PLAYER_DEATH;
         val var19: ServerPlayerEvent.Death = new ServerPlayerEvent.Death(var10000);
         val `this_$iv$iv`: EventObservable = var3;
         val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var19};
         `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

         for (Object element$iv$iv$iv : events$iv$iv) {
            if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: ServerPlayerEvent.Death = `element$iv$iv$iv` as ServerPlayerEvent.Death;
               e.setCanceled(true);
            } else {
               val var20: ServerPlayerEvent.Death = `element$iv$iv$iv` as ServerPlayerEvent.Death;
            }
         }
      }
   }

   @SubscribeEvent
   public fun onRightClickBlock(e: RightClickBlock) {
      val pos: Player = e.getEntity();
      val var10000: ServerPlayer = pos as? ServerPlayer;
      if ((pos as? ServerPlayer) != null) {
         val var25: InteractionHand = e.getHand();
         val var26: BlockPos = e.getPos();
         val face: Direction = e.getFace();
         val var6: CancelableObservable = PlatformEvents.RIGHT_CLICK_BLOCK;
         val var7: ServerPlayerEvent.RightClickBlock = new ServerPlayerEvent.RightClickBlock(var10000, var26, var25, face);
         val `this_$iv$iv`: EventObservable = var6;
         val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var7};
         `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

         for (Object element$iv$iv$iv : events$iv$iv) {
            if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: ServerPlayerEvent.RightClickBlock = `element$iv$iv$iv` as ServerPlayerEvent.RightClickBlock;
               e.setCanceled(true);
            } else {
               val var23: ServerPlayerEvent.RightClickBlock = `element$iv$iv$iv` as ServerPlayerEvent.RightClickBlock;
            }
         }
      }
   }

   @SubscribeEvent
   public fun onRightClickEntity(e: EntityInteract) {
      val item: Player = e.getEntity();
      val var10000: ServerPlayer = item as? ServerPlayer;
      if ((item as? ServerPlayer) != null) {
         val var25: InteractionHand = e.getHand();
         val var22: ItemStack = var10000.m_21120_(var25);
         val entity: Entity = e.getTarget();
         val var6: CancelableObservable = PlatformEvents.RIGHT_CLICK_ENTITY;
         val var7: ServerPlayerEvent.RightClickEntity = new ServerPlayerEvent.RightClickEntity(var10000, var22, var25, entity);
         val `this_$iv$iv`: EventObservable = var6;
         val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var7};
         `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

         for (Object element$iv$iv$iv : events$iv$iv) {
            if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: ServerPlayerEvent.RightClickEntity = `element$iv$iv$iv` as ServerPlayerEvent.RightClickEntity;
               e.setCanceled(true);
            } else {
               val var23: ServerPlayerEvent.RightClickEntity = `element$iv$iv$iv` as ServerPlayerEvent.RightClickEntity;
            }
         }
      }
   }

   @SubscribeEvent
   public fun onChangeDimension(e: PlayerChangedDimensionEvent) {
      val player: Player = e.getEntity();
      if (player is ServerPlayer) {
         val `$this$iv`: EventObservable = PlatformEvents.CHANGE_DIMENSION;
         val `events$iv`: Array<ChangeDimensionEvent> = new ChangeDimensionEvent[]{new ChangeDimensionEvent(player as ServerPlayer)};
         `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

         for (Object element$iv$iv : events$iv) {
            ;
         }
      }
   }
}
