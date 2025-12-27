package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.UseMoveRequirement
import com.google.gson.JsonObject
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

public class UseMoveEvolutionProgress : EvolutionProgress<UseMoveEvolutionProgress.Progress> {
   private final var progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress.Progress =
      new UseMoveEvolutionProgress.Progress(MoveTemplate.Companion.dummy(""), 0)

   public override fun id(): ResourceLocation {
      return ID;
   }

   public open fun currentProgress(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress.Progress {
      return this.progress;
   }

   public open fun updateProgress(progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress.Progress) {
      this.progress = progress;
   }

   public override fun reset() {
      this.progress = new UseMoveEvolutionProgress.Progress(MoveTemplate.Companion.dummy(""), 0);
   }

   public override fun shouldKeep(pokemon: Pokemon): Boolean {
      return Companion.supports(pokemon, this.progress.getMove());
   }

   public open fun loadFromNBT(nbt: CompoundTag) {
      val moveId: java.lang.String = nbt.m_128461_("move");
      val var10000: Moves = Moves.INSTANCE;
      val var5: MoveTemplate = var10000.getByName(moveId);
      if (var5 != null) {
         this.updateProgress(new UseMoveEvolutionProgress.Progress(var5, nbt.m_128451_("amount")));
      }
   }

   public open fun saveToNBT(): CompoundTag {
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128359_("move", this.currentProgress().getMove().getName());
      nbt.m_128405_("amount", this.currentProgress().getAmount());
      return nbt;
   }

   public open fun loadFromJson(json: JsonObject) {
      val moveId: java.lang.String = json.get("move").getAsString();
      val var10000: Moves = Moves.INSTANCE;
      val var5: MoveTemplate = var10000.getByName(moveId);
      if (var5 != null) {
         this.updateProgress(new UseMoveEvolutionProgress.Progress(var5, json.get("amount").getAsInt()));
      }
   }

   public open fun saveToJson(): JsonObject {
      val jObject: JsonObject = new JsonObject();
      jObject.addProperty("move", this.currentProgress().getMove().getName());
      jObject.addProperty("amount", this.currentProgress().getAmount());
      return jObject;
   }

   @SourceDebugExtension(["SMAP\nUseMoveEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseMoveEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/UseMoveEvolutionProgress$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,94:1\n1747#2,2:95\n1747#2,3:97\n1749#2:100\n*S KotlinDebug\n*F\n+ 1 UseMoveEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/UseMoveEvolutionProgress$Companion\n*L\n85#1:95,2\n86#1:97,3\n85#1:100\n*E\n"])
   public companion object {
      private const val AMOUNT: String
      public final val ID: ResourceLocation
      private const val MOVE: String

      public fun supports(pokemon: Pokemon, move: MoveTemplate): Boolean {
         val `$this$any$iv`: java.lang.Iterable = pokemon.getForm().getEvolutions();
         var var15: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var15 = false;
         } else {
            val var5: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var5.hasNext()) {
                  var15 = false;
                  break;
               }

               val `$this$any$ivx`: java.lang.Iterable = (var5.next() as Evolution).getRequirements();
               if (`$this$any$ivx` is java.util.Collection && (`$this$any$ivx` as java.util.Collection).isEmpty()) {
                  var15 = false;
               } else {
                  val var11: java.util.Iterator = `$this$any$ivx`.iterator();

                  while (true) {
                     if (!var11.hasNext()) {
                        var15 = false;
                        break;
                     }

                     val requirement: EvolutionRequirement = var11.next() as EvolutionRequirement;
                     if (requirement is UseMoveRequirement && (requirement as UseMoveRequirement).getMove() == move) {
                        var15 = true;
                        break;
                     }
                  }
               }

               if (var15) {
                  var15 = true;
                  break;
               }
            }
         }

         return var15;
      }
   }

   public data Progress(move: MoveTemplate, amount: Int) {
      public final val amount: Int
      public final val move: MoveTemplate

      init {
         this.move = move;
         this.amount = amount;
      }

      public operator fun component1(): MoveTemplate {
         return this.move;
      }

      public operator fun component2(): Int {
         return this.amount;
      }

      public fun copy(move: MoveTemplate = this.move, amount: Int = this.amount): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress.Progress {
         return new UseMoveEvolutionProgress.Progress(move, amount);
      }

      public override fun toString(): String {
         return "Progress(move=${this.move}, amount=${this.amount})";
      }

      public override fun hashCode(): Int {
         return this.move.hashCode() * 31 + Integer.hashCode(this.amount);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is UseMoveEvolutionProgress.Progress) {
            return false;
         } else {
            val var2: UseMoveEvolutionProgress.Progress = other as UseMoveEvolutionProgress.Progress;
            if (!(this.move == (other as UseMoveEvolutionProgress.Progress).move)) {
               return false;
            } else {
               return this.amount == var2.amount;
            }
         }
      }
   }
}
