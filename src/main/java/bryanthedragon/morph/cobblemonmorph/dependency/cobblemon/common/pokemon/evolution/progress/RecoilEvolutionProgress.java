package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.RecoilRequirement
import com.google.gson.JsonObject
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nRecoilEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecoilEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/RecoilEvolutionProgress\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"])
public class RecoilEvolutionProgress : EvolutionProgress<RecoilEvolutionProgress.Progress> {
   private final var progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress.Progress = new RecoilEvolutionProgress.Progress(0)

   public override fun id(): ResourceLocation {
      return ID;
   }

   public open fun currentProgress(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress.Progress {
      return this.progress;
   }

   public open fun updateProgress(progress: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress.Progress) {
      this.progress = progress;
   }

   public override fun reset() {
      this.updateProgress(new RecoilEvolutionProgress.Progress(0));
   }

   public override fun shouldKeep(pokemon: Pokemon): Boolean {
      return Companion.supports(pokemon);
   }

   public open fun loadFromNBT(nbt: CompoundTag) {
      this.updateProgress(new RecoilEvolutionProgress.Progress(nbt.m_128451_("recoil")));
   }

   public open fun saveToNBT(): CompoundTag {
      val var1: CompoundTag = new CompoundTag();
      var1.m_128405_("recoil", this.currentProgress().getRecoil());
      return var1;
   }

   public open fun loadFromJson(json: JsonObject) {
      this.updateProgress(new RecoilEvolutionProgress.Progress(json.get("recoil").getAsInt()));
   }

   public open fun saveToJson(): JsonObject {
      val var1: JsonObject = new JsonObject();
      var1.addProperty("recoil", this.currentProgress().getRecoil());
      return var1;
   }

   @SourceDebugExtension(["SMAP\nRecoilEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecoilEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/RecoilEvolutionProgress$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n1747#2,2:75\n1747#2,3:77\n1749#2:80\n*S KotlinDebug\n*F\n+ 1 RecoilEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/RecoilEvolutionProgress$Companion\n*L\n65#1:75,2\n66#1:77,3\n65#1:80\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation
      private const val RECOIL: String

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

                     if (var10.next() as EvolutionRequirement is RecoilRequirement) {
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

   public data Progress(recoil: Int) {
      public final val recoil: Int

      init {
         this.recoil = recoil;
      }

      public operator fun component1(): Int {
         return this.recoil;
      }

      public fun copy(recoil: Int = this.recoil): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress.Progress {
         return new RecoilEvolutionProgress.Progress(recoil);
      }

      public override fun toString(): String {
         return "Progress(recoil=${this.recoil})";
      }

      public override fun hashCode(): Int {
         return Integer.hashCode(this.recoil);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is RecoilEvolutionProgress.Progress) {
            return false;
         } else {
            return this.recoil == (other as RecoilEvolutionProgress.Progress).recoil;
         }
      }
   }
}
