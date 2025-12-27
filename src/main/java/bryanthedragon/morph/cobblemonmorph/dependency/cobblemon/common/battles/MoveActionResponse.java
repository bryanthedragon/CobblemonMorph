package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/MoveActionResponse\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n350#3,7:454\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/MoveActionResponse\n*L\n146#1:454,7\n*E\n"])
public data MoveActionResponse(moveName: String, targetPnx: String? = null, gimmickID: String? = null) : ShowdownActionResponse(
      ShowdownActionResponseType.MOVE
   ) {
   public final var gimmickID: String?
   public final var moveName: String
   public final var targetPnx: String?

   init {
      this.moveName = moveName;
      this.targetPnx = targetPnx;
      this.gimmickID = gimmickID;
   }

   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      if (!forceSwitch && showdownMoveSet != null) {
         val pnx: java.util.Iterator = showdownMoveSet.getMoves().iterator();

         var var10000: Any;
         while (true) {
            if (pnx.hasNext()) {
               val var9: Any = pnx.next();
               if (!((var9 as InBattleMove).getId() == this.moveName)) {
                  continue;
               }

               var10000 = (InBattleMove)var9;
               break;
            }

            var10000 = null;
            break;
         }

         var10000 = var10000;
         if (var10000 == null) {
            return false;
         } else {
            val gimmickMove: InBattleGimmickMove = var10000.getGimmickMove();
            if ((gimmickMove == null || gimmickMove.getDisabled()) && !var10000.canBeUsed()) {
               return false;
            } else {
               label59: {
                  if (gimmickMove != null) {
                     var18 = gimmickMove.getTarget();
                     if (var18 != null) {
                        break label59;
                     }
                  }

                  var18 = var10000.getTarget();
               }

               val var19: java.util.List = var18.getTargetList().invoke(activeBattlePokemon) as java.util.List;
               if (var19 != null) {
                  val var20: java.util.List = if (!var19.isEmpty()) var19 else null;
                  if (var20 != null) {
                     if (this.targetPnx == null) {
                        return false;
                     }

                     val var14: java.lang.String = this.targetPnx;
                     val var16: ActiveBattlePokemon = activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(var14).component2() as ActiveBattlePokemon;
                     if (var20.contains(var16) && var16.getBattlePokemon() != null) {
                        val var21: BattlePokemon = var16.getBattlePokemon();
                        if (var21.getHealth() > 0) {
                           return true;
                        }
                     }

                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      val pnx: java.lang.String = this.targetPnx;
      val `$this$indexOfFirst$iv`: java.util.List = showdownMoveSet.getMoves();
      var digit: Int = 0;
      val it: java.util.Iterator = `$this$indexOfFirst$iv`.iterator();

      var var10000: Int;
      while (true) {
         if (!it.hasNext()) {
            var10000 = -1;
            break;
         }

         if ((it.next() as InBattleMove).getId() == this.moveName) {
            var10000 = digit;
            break;
         }

         digit++;
      }

      val var17: java.lang.String = if (pnx != null)
         "move ${var10000 + 1} ${(activeBattlePokemon.getActor().getBattle().getActorAndActiveSlotFromPNX(pnx).component2() as ActiveBattlePokemon)
            .getSignedDigitRelativeTo(activeBattlePokemon)}"
         else
         "move ${var10000 + 1}";
      if (this.gimmickID != null) {
         val var15: java.lang.String = this.gimmickID;
         val var10001: java.lang.String = " ${this.gimmickID}";
         if (var10001 != null) {
            return "$var17$var10001";
         }
      }

      return "$var17";
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf) {
      super.saveToBuffer(buffer);
      buffer.m_130070_(this.moveName);
      buffer.m_236821_(this.targetPnx, MoveActionResponse::saveToBuffer$lambda$4);
      buffer.m_236821_(this.gimmickID, MoveActionResponse::saveToBuffer$lambda$5);
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionResponse {
      super.loadFromBuffer(buffer);
      val var10001: java.lang.String = buffer.m_130277_();
      this.moveName = var10001;
      this.targetPnx = buffer.m_236868_(MoveActionResponse::loadFromBuffer$lambda$6) as java.lang.String;
      this.gimmickID = buffer.m_236868_(MoveActionResponse::loadFromBuffer$lambda$7) as java.lang.String;
      return this;
   }

   public operator fun component1(): String {
      return this.moveName;
   }

   public operator fun component2(): String? {
      return this.targetPnx;
   }

   public operator fun component3(): String? {
      return this.gimmickID;
   }

   public fun copy(moveName: String = this.moveName, targetPnx: String? = this.targetPnx, gimmickID: String? = this.gimmickID): MoveActionResponse {
      return new MoveActionResponse(moveName, targetPnx, gimmickID);
   }

   public override fun toString(): String {
      return "MoveActionResponse(moveName=${this.moveName}, targetPnx=${this.targetPnx}, gimmickID=${this.gimmickID})";
   }

   public override fun hashCode(): Int {
      return (this.moveName.hashCode() * 31 + (if (this.targetPnx == null) 0 else this.targetPnx.hashCode())) * 31
         + (if (this.gimmickID == null) 0 else this.gimmickID.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is MoveActionResponse) {
         return false;
      } else {
         val var2: MoveActionResponse = other as MoveActionResponse;
         if (!(this.moveName == (other as MoveActionResponse).moveName)) {
            return false;
         } else if (!(this.targetPnx == var2.targetPnx)) {
            return false;
         } else {
            return this.gimmickID == var2.gimmickID;
         }
      }
   }

   @JvmStatic
   fun `saveToBuffer$lambda$4`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, targetPnx: java.lang.String) {
      `$buffer`.m_130070_(targetPnx);
   }

   @JvmStatic
   fun `saveToBuffer$lambda$5`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, gimmickID: java.lang.String) {
      `$buffer`.m_130070_(gimmickID);
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$6`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$7`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }
}
