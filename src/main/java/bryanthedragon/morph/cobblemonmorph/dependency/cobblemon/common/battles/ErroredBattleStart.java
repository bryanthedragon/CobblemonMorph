package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/ErroredBattleStart\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,285:1\n800#2,11:286\n1855#2,2:297\n1855#2,2:299\n800#2,11:301\n1726#2,3:312\n1603#2,9:316\n1855#2:325\n1856#2:327\n1612#2:328\n1#3:315\n1#3:326\n76#4:329\n96#4,5:330\n*S KotlinDebug\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/ErroredBattleStart\n*L\n253#1:286,11\n253#1:297,2\n258#1:299,2\n262#1:301,11\n269#1:312,3\n278#1:316,9\n278#1:325\n278#1:327\n278#1:328\n278#1:326\n284#1:329\n284#1:330,5\n*E\n"])
public open class ErroredBattleStart(generalErrors: MutableSet<BattleStartError> = (new LinkedHashSet()) as java.util.Set,
      participantErrors: BattleActorErrors = new BattleActorErrors()
   )
   : BattleStartResult {
   public final val actorsToBlame: Iterable<BattleActor>
      public final get() {
         val var10000: java.util.Set = this.participantErrors.keySet();
         return var10000;
      }


   public final val errors: Iterable<BattleStartError>
      public final get() {
         val `$this$flatMap$iv`: java.util.Map = this.participantErrors;
         val var10: java.util.Set = this.generalErrors;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Entry element$iv$iv : $this$flatMap$iv.entrySet()) {
            CollectionsKt.addAll(`destination$iv$iv`, `element$iv$iv`.getValue() as java.util.Set);
         }

         return SetsKt.plus(var10, `destination$iv$iv` as java.util.List);
      }


   public final val generalErrors: MutableSet<BattleStartError>

   public final val isEmpty: Boolean
      public final get() {
         if (this.generalErrors.isEmpty()) {
            val var10000: java.util.Collection = this.participantErrors.values();
            val `$this$all$iv`: java.lang.Iterable = var10000;
            var var7: Boolean;
            if ((var10000 as java.util.Collection).isEmpty()) {
               var7 = true;
            } else {
               val var3: java.util.Iterator = `$this$all$iv`.iterator();

               while (true) {
                  if (!var3.hasNext()) {
                     var7 = true;
                     break;
                  }

                  if (!(var3.next() as java.util.Set).isEmpty()) {
                     var7 = false;
                     break;
                  }
               }
            }

            if (var7) {
               return true;
            }
         }

         return false;
      }


   public final val participantErrors: BattleActorErrors

   public final val playersToBlame: Iterable<ServerPlayer>
      public final get() {
         val var10000: java.util.Set = this.participantErrors.keySet();
         val `$this$mapNotNull$iv`: java.lang.Iterable = var10000;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            val var16: ServerPlayer = PlayerExtensionsKt.getPlayer((`element$iv$iv$iv` as BattleActor).getUuid());
            if (var16 != null) {
               `destination$iv$iv`.add(var16);
            }
         }

         return `destination$iv$iv`;
      }


   init {
      this.generalErrors = generalErrors;
      this.participantErrors = participantErrors;
   }

   public override fun ifErrored(action: (ErroredBattleStart) -> Unit): BattleStartResult {
      action.invoke(this);
      return this;
   }

   public fun sendTo(entity: Entity, transformer: (MutableComponent) -> MutableComponent = <unrepresentable>.INSTANCE as Function1) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         entity.m_213846_(transformer.invoke((`element$iv` as BattleStartError).getMessageFor(entity)) as Component);
      }
   }

   public fun isPlayerToBlame(player: ServerPlayer): Boolean {
      if (this.generalErrors.isEmpty() && this.participantErrors.size() == 1) {
         val var10000: java.util.Set = this.participantErrors.entrySet();
         if (((CollectionsKt.first(var10000) as Entry).getKey() as BattleActor).getUuid() == player.m_20148_()) {
            return true;
         }
      }

      return false;
   }

   public fun isSomePlayerToBlame(): Boolean {
      return this.generalErrors.isEmpty() && !this.participantErrors.isEmpty();
   }

   open fun ErroredBattleStart() {
      this(null, null, 3, null);
   }
}
