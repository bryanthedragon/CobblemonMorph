package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BattleCriticalHitsRequirement
import com.google.gson.JsonObject
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nLastBattleCriticalHitsEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LastBattleCriticalHitsEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/LastBattleCriticalHitsEvolutionProgress\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,68:1\n1#2:69\n*E\n"])
public class LastBattleCriticalHitsEvolutionProgress : EvolutionProgress<LastBattleCriticalHitsEvolutionProgress.Progress> {
   private final var progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress.Progress =
      new LastBattleCriticalHitsEvolutionProgress.Progress(0)

   public override fun id(): ResourceLocation {
      return ID;
   }

   public open fun currentProgress(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress.Progress {
      return this.progress;
   }

   public open fun updateProgress(progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress.Progress) {
      this.progress = progress;
   }

   public override fun reset() {
      this.updateProgress(new LastBattleCriticalHitsEvolutionProgress.Progress(0));
   }

   public override fun shouldKeep(pokemon: Pokemon): Boolean {
      return Companion.supports(pokemon);
   }

   public open fun loadFromNBT(nbt: CompoundTag) {
      this.updateProgress(new LastBattleCriticalHitsEvolutionProgress.Progress(nbt.m_128451_("amount")));
   }

   public open fun saveToNBT(): CompoundTag {
      val var1: CompoundTag = new CompoundTag();
      var1.m_128405_("amount", this.currentProgress().getAmount());
      return var1;
   }

   public open fun loadFromJson(json: JsonObject) {
      this.updateProgress(new LastBattleCriticalHitsEvolutionProgress.Progress(json.get("amount").getAsInt()));
   }

   public open fun saveToJson(): JsonObject {
      val var1: JsonObject = new JsonObject();
      var1.addProperty("amount", this.currentProgress().getAmount());
      return var1;
   }

   @SourceDebugExtension(["SMAP\nLastBattleCriticalHitsEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LastBattleCriticalHitsEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/LastBattleCriticalHitsEvolutionProgress$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n1747#2,2:69\n1747#2,3:71\n1749#2:74\n*S KotlinDebug\n*F\n+ 1 LastBattleCriticalHitsEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/LastBattleCriticalHitsEvolutionProgress$Companion\n*L\n59#1:69,2\n60#1:71,3\n59#1:74\n*E\n"])
   public companion object {
      private const val AMOUNT: String
      public final val ID: ResourceLocation

      public fun supports(pokemon: Pokemon): Boolean {
         val `$this$any$iv`: java.lang.Iterable = pokemon.getForm().getEvolutions();
         var var14: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var14 = false;
         } else {
            val var4: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var4.hasNext()) {
                  var14 = false;
                  break;
               }

               val `$this$any$ivx`: java.lang.Iterable = (var4.next() as Evolution).getRequirements();
               if (`$this$any$ivx` is java.util.Collection && (`$this$any$ivx` as java.util.Collection).isEmpty()) {
                  var14 = false;
               } else {
                  val var10: java.util.Iterator = `$this$any$ivx`.iterator();

                  while (true) {
                     if (!var10.hasNext()) {
                        var14 = false;
                        break;
                     }

                     if (var10.next() as EvolutionRequirement is BattleCriticalHitsRequirement) {
                        var14 = true;
                        break;
                     }
                  }
               }

               if (var14) {
                  var14 = true;
                  break;
               }
            }
         }

         return var14;
      }
   }

   public data Progress(amount: Int) {
      public final val amount: Int

      init {
         this.amount = amount;
      }

      public operator fun component1(): Int {
         return this.amount;
      }

      public fun copy(amount: Int = this.amount): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress.Progress {
         return new LastBattleCriticalHitsEvolutionProgress.Progress(amount);
      }

      public override fun toString(): String {
         return "Progress(amount=${this.amount})";
      }

      public override fun hashCode(): Int {
         return Integer.hashCode(this.amount);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is LastBattleCriticalHitsEvolutionProgress.Progress) {
            return false;
         } else {
            return this.amount == (other as LastBattleCriticalHitsEvolutionProgress.Progress).amount;
         }
      }
   }
}
