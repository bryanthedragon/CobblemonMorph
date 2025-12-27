package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/SwitchActionResponse\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n1747#3,3:454\n350#3,7:457\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/SwitchActionResponse\n*L\n212#1:454,3\n218#1:457,7\n*E\n"])
public data SwitchActionResponse(newPokemonId: UUID) : ShowdownActionResponse(ShowdownActionResponseType.SWITCH) {
   public final var newPokemonId: UUID

   init {
      this.newPokemonId = newPokemonId;
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf) {
      super.saveToBuffer(buffer);
      buffer.m_130077_(this.newPokemonId);
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionResponse {
      super.loadFromBuffer(buffer);
      val var10001: UUID = buffer.m_130259_();
      this.newPokemonId = var10001;
      return this;
   }

   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      val var7: java.util.Iterator = activeBattlePokemon.getActor().getPokemonList().iterator();

      var var10000: Any;
      while (true) {
         if (var7.hasNext()) {
            val `element$iv`: Any = var7.next();
            if (!((`element$iv` as BattlePokemon).getUuid() == this.newPokemonId)) {
               continue;
            }

            var10000 = (ShowdownActionRequest)`element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      val pokemon: BattlePokemon = var10000 as BattlePokemon;
      val var16: Boolean;
      if (var10000 as BattlePokemon == null) {
         var16 = false;
      } else {
         label66: {
            var10000 = activeBattlePokemon.getActor().getRequest();
            if (var10000 != null) {
               val var18: ShowdownSide = var10000.getSide();
               if (var18 != null) {
                  val var19: java.util.List = var18.getPokemon();
                  if (var19 != null) {
                     val var20: ShowdownPokemon = var19.get(0) as ShowdownPokemon;
                     if (var20 != null) {
                        var10000 = var20.getReviving();
                        break label66;
                     }
                  }
               }
            }

            var10000 = null;
         }

         if (!var10000 && pokemon.getHealth() <= 0) {
            var16 = false;
         } else if (showdownMoveSet != null && showdownMoveSet.getTrapped()) {
            var16 = false;
         } else {
            val `$this$any$iv`: java.lang.Iterable = activeBattlePokemon.getActor().getSide().getActivePokemon();
            var var23: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var23 = false;
            } else {
               label86: {
                  for (Object element$iv : $this$any$iv) {
                     val var22: BattlePokemon = (var13 as ActiveBattlePokemon).getBattlePokemon();
                     if ((if (var22 != null) var22.getUuid() else null) == this.newPokemonId) {
                        var23 = true;
                        break label86;
                     }
                  }

                  var23 = false;
               }
            }

            var16 = !var23;
         }
      }

      return var16;
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      val `$this$indexOfFirst$iv`: java.util.List = activeBattlePokemon.getActor().getPokemonList();
      var `index$iv`: Int = 0;
      val var6: java.util.Iterator = `$this$indexOfFirst$iv`.iterator();

      var var10000: Int;
      while (true) {
         if (!var6.hasNext()) {
            var10000 = -1;
            break;
         }

         if ((var6.next() as BattlePokemon).getUuid() == this.newPokemonId) {
            var10000 = `index$iv`;
            break;
         }

         `index$iv`++;
      }

      return "switch ${var10000 + 1}";
   }

   public operator fun component1(): UUID {
      return this.newPokemonId;
   }

   public fun copy(newPokemonId: UUID = this.newPokemonId): SwitchActionResponse {
      return new SwitchActionResponse(newPokemonId);
   }

   public override fun toString(): String {
      return "SwitchActionResponse(newPokemonId=${this.newPokemonId})";
   }

   public override fun hashCode(): Int {
      return this.newPokemonId.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is SwitchActionResponse) {
         return false;
      } else {
         return this.newPokemonId == (other as SwitchActionResponse).newPokemonId;
      }
   }
}
