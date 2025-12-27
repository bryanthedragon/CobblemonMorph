package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

public interface ExperienceSource {
   public open fun isBattle(): Boolean {
   }

   public open fun isInteraction(): Boolean {
   }

   public open fun isCommand(): Boolean {
   }

   public open fun isSidemod(): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun isBattle(`$this`: ExperienceSource): Boolean {
         return `$this` is BattleExperienceSource;
      }

      @JvmStatic
      fun isInteraction(`$this`: ExperienceSource): Boolean {
         return `$this` is CandyExperienceSource;
      }

      @JvmStatic
      fun isCommand(`$this`: ExperienceSource): Boolean {
         return `$this` is CommandExperienceSource;
      }

      @JvmStatic
      fun isSidemod(`$this`: ExperienceSource): Boolean {
         return `$this` is SidemodExperienceSource;
      }
   }
}
