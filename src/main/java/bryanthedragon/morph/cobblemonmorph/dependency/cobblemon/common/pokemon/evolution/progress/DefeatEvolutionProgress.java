package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DefeatRequirement
import com.google.gson.JsonObject
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nDefeatEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefeatEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,87:1\n1747#2,2:88\n1747#2,3:90\n1749#2:93\n*S KotlinDebug\n*F\n+ 1 DefeatEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress\n*L\n43#1:88,2\n44#1:90,3\n43#1:93\n*E\n"])
public class DefeatEvolutionProgress : EvolutionProgress<DefeatEvolutionProgress.Progress> {
   private final var progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress.Progress =
      new DefeatEvolutionProgress.Progress(new PokemonProperties(), 0)

   public override fun id(): ResourceLocation {
      return ID;
   }

   public open fun currentProgress(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress.Progress {
      return this.progress;
   }

   public open fun updateProgress(progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress.Progress) {
      this.progress = progress;
   }

   public override fun reset() {
      this.progress = new DefeatEvolutionProgress.Progress(new PokemonProperties(), 0);
   }

   public override fun shouldKeep(pokemon: Pokemon): Boolean {
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

                  val requirement: EvolutionRequirement = var10.next() as EvolutionRequirement;
                  if (requirement is DefeatRequirement
                     && StringsKt.equals(
                        (requirement as DefeatRequirement).getTarget().getOriginalString(), this.progress.getTarget().getOriginalString(), true
                     )) {
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

   public open fun loadFromNBT(nbt: CompoundTag) {
      val var10000: PokemonProperties.Companion = PokemonProperties.Companion;
      val var10001: java.lang.String = nbt.m_128461_("target");
      this.updateProgress(
         new DefeatEvolutionProgress.Progress(PokemonProperties.Companion.parse$default(var10000, var10001, null, null, 6, null), nbt.m_128451_("amount"))
      );
   }

   public open fun saveToNBT(): CompoundTag {
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128359_("target", this.currentProgress().getTarget().getOriginalString());
      nbt.m_128405_("amount", this.currentProgress().getAmount());
      return nbt;
   }

   public open fun loadFromJson(json: JsonObject) {
      val var10000: PokemonProperties.Companion = PokemonProperties.Companion;
      val var10001: java.lang.String = json.get("target").getAsString();
      this.updateProgress(
         new DefeatEvolutionProgress.Progress(PokemonProperties.Companion.parse$default(var10000, var10001, null, null, 6, null), json.get("amount").getAsInt())
      );
   }

   public open fun saveToJson(): JsonObject {
      val jObject: JsonObject = new JsonObject();
      jObject.addProperty("target", this.currentProgress().getTarget().getOriginalString());
      jObject.addProperty("amount", this.currentProgress().getAmount());
      return jObject;
   }

   public companion object {
      private const val AMOUNT: String
      public final val ID: ResourceLocation
      private const val TARGET: String
   }

   public data Progress(target: PokemonProperties, amount: Int) {
      public final val amount: Int
      public final val target: PokemonProperties

      init {
         this.target = target;
         this.amount = amount;
      }

      public operator fun component1(): PokemonProperties {
         return this.target;
      }

      public operator fun component2(): Int {
         return this.amount;
      }

      public fun copy(target: PokemonProperties = this.target, amount: Int = this.amount): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress.Progress {
         return new DefeatEvolutionProgress.Progress(target, amount);
      }

      public override fun toString(): String {
         return "Progress(target=${this.target}, amount=${this.amount})";
      }

      public override fun hashCode(): Int {
         return this.target.hashCode() * 31 + Integer.hashCode(this.amount);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is DefeatEvolutionProgress.Progress) {
            return false;
         } else {
            val var2: DefeatEvolutionProgress.Progress = other as DefeatEvolutionProgress.Progress;
            if (!(this.target == (other as DefeatEvolutionProgress.Progress).target)) {
               return false;
            } else {
               return this.amount == var2.amount;
            }
         }
      }
   }
}
