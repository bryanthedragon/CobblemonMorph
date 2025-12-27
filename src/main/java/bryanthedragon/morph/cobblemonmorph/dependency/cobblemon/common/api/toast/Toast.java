package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.toast

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket.Behaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Login
import java.util.ArrayList;
import java.util.Arrays
import java.util.HashSet
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import net.minecraft.util.Mth
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nToast.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Toast.kt\ncom/cobblemon/mod/common/api/toast/Toast\n+ 2 Delegates.kt\nkotlin/properties/Delegates\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,206:1\n33#2,3:207\n33#2,3:210\n33#2,3:213\n33#2,3:216\n33#2,3:219\n33#2,3:222\n1603#3,9:225\n1855#3:234\n1856#3:236\n1612#3:237\n1855#3,2:244\n1#4:235\n13579#5,2:238\n13579#5,2:240\n37#6,2:242\n*S KotlinDebug\n*F\n+ 1 Toast.kt\ncom/cobblemon/mod/common/api/toast/Toast\n*L\n49#1:207,3\n54#1:210,3\n59#1:213,3\n64#1:216,3\n69#1:219,3\n74#1:222,3\n79#1:225,9\n79#1:234\n79#1:236\n79#1:237\n165#1:244,2\n79#1:235\n110#1:238,2\n127#1:240,2\n158#1:242,2\n*E\n"])
public class Toast(title: Component,
   description: Component,
   icon: ItemStack,
   frameTexture: ResourceLocation = VANILLA_FRAME,
   progress: Float = -1.0F,
   progressColor: Int = -1675545
) {
   public final var description: Component
      public final get() {
         return this.description$delegate.getValue(this, $$delegatedProperties[1]) as Component;
      }

      public final set(<set-?>) {
         this.description$delegate.setValue(this, $$delegatedProperties[1], `<set-?>`);
      }


   public final var frameTexture: ResourceLocation
      public final get() {
         return this.frameTexture$delegate.getValue(this, $$delegatedProperties[3]) as ResourceLocation;
      }

      public final set(<set-?>) {
         this.frameTexture$delegate.setValue(this, $$delegatedProperties[3], `<set-?>`);
      }


   public final var icon: ItemStack
      public final get() {
         return this.icon$delegate.getValue(this, $$delegatedProperties[2]) as ItemStack;
      }

      public final set(<set-?>) {
         this.icon$delegate.setValue(this, $$delegatedProperties[2], `<set-?>`);
      }


   private final val listenerUuids: HashSet<UUID>

   public final val listeners: Collection<ServerPlayer>
      public final get() {
         val `$this$mapNotNull$iv`: java.lang.Iterable = this.listenerUuids;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            var var17: ServerPlayer;
            label22: {
               val it: UUID = `element$iv$iv$iv` as UUID;
               val var10000: MinecraftServer = Cobblemon.INSTANCE.getImplementation().server();
               if (var10000 != null) {
                  val var16: PlayerList = var10000.m_6846_();
                  if (var16 != null) {
                     var17 = var16.m_11259_(it);
                     break label22;
                  }
               }

               var17 = null;
            }

            if (var17 != null) {
               `destination$iv$iv`.add(var17);
            }
         }

         return `destination$iv$iv`;
      }


   public final var progress: Float
      public final get() {
         return (this.progress$delegate.getValue(this, $$delegatedProperties[4]) as java.lang.Number).floatValue();
      }

      public final set(<set-?>) {
         this.progress$delegate.setValue(this, $$delegatedProperties[4], `<set-?>`);
      }


   public final var progressColor: Int
      public final get() {
         return (this.progressColor$delegate.getValue(this, $$delegatedProperties[5]) as java.lang.Number).intValue();
      }

      public final set(<set-?>) {
         this.progressColor$delegate.setValue(this, $$delegatedProperties[5], `<set-?>`);
      }


   private final val subscription: ObservableSubscription<Login>

   public final var title: Component
      public final get() {
         return this.title$delegate.getValue(this, $$delegatedProperties[0]) as Component;
      }

      public final set(<set-?>) {
         this.title$delegate.setValue(this, $$delegatedProperties[0], `<set-?>`);
      }


   internal final val uuid: UUID

   init {
      var `this_$iv`: Delegates = Delegates.INSTANCE;
      this.title$delegate = (new Toast$special$$inlined$observable$1(title, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.description$delegate = (new Toast$special$$inlined$observable$2(description, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.icon$delegate = (new Toast$special$$inlined$observable$3(icon, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.frameTexture$delegate = (new Toast$special$$inlined$observable$4(frameTexture, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.progress$delegate = (new Toast$special$$inlined$observable$5(progress, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.progressColor$delegate = (new Toast$special$$inlined$observable$6(progressColor, this)) as ReadWriteProperty;
      val var10001: UUID = Mth.m_14002_();
      this.uuid = var10001;
      this.listenerUuids = new HashSet<>();
      this.subscription = Observable.DefaultImpls.subscribe$default(
         PlatformEvents.SERVER_PLAYER_LOGIN, null, (new Function1<ServerPlayerEvent.Login, Unit>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            public final void invoke(@NotNull ServerPlayerEvent.Login event) {
               if (Toast.access$getListenerUuids$p(this.this$0).contains(event.getPlayer().m_20148_())) {
                  Toast.access$updateFor(this.this$0, event.getPlayer(), Toast.access$toPacket(this.this$0, ToastPacket.Behaviour.SHOW_OR_UPDATE));
               }
            }
         }) as Function1, 1, null
      );
   }

   public fun addListeners(vararg listeners: ServerPlayer) {
      val packet: ToastPacket = this.toPacket(ToastPacket.Behaviour.SHOW_OR_UPDATE);

      for (Object element$iv : listeners) {
         if (this.listenerUuids.add(`element$iv`.m_20148_())) {
            this.updateFor((ServerPlayer)`element$iv`, packet);
         }
      }
   }

   public fun removeListeners(vararg listeners: ServerPlayer) {
      val packet: ToastPacket = this.toPacket(ToastPacket.Behaviour.HIDE);

      for (Object element$iv : listeners) {
         if (this.listenerUuids.remove(`element$iv`.m_20148_())) {
            this.updateFor((ServerPlayer)`element$iv`, packet);
         }
      }
   }

   public fun expireAfter(ticks: Int) {
      SchedulingFunctionsKt.afterOnServer$default(ticks, 0.0F, (new Function0<Unit>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         public final void invoke() {
            this.this$0.expire();
         }
      }) as Function0, 2, null);
   }

   public fun setNoProgress() {
      this.setProgress(-1.0F);
   }

   public fun expire() {
      val var1: Array<ServerPlayer> = this.getListeners().toArray(new ServerPlayer[0]);
      this.removeListeners(Arrays.copyOf(var1, var1.length));
      this.listenerUuids.clear();
      this.subscription.unsubscribe();
   }

   private fun launchUpdate() {
      val packet: ToastPacket = this.toPacket(ToastPacket.Behaviour.SHOW_OR_UPDATE);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToPlayer(`element$iv` as ServerPlayer, packet);
      }
   }

   private fun updateFor(player: ServerPlayer, packet: ToastPacket) {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToPlayer(player, packet);
   }

   private fun toPacket(behaviour: Behaviour): ToastPacket {
      return new ToastPacket(
         this.getTitle(), this.getDescription(), this.getIcon(), this.getFrameTexture(), this.getProgress(), this.getProgressColor(), this.uuid, behaviour
      );
   }

   public companion object {
      public final val VANILLA_FRAME: ResourceLocation
      public const val VANILLA_PROGRESS_COLOR: Int
   }
}
