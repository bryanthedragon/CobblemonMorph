package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

public open class SidemodExperienceSource(sidemodId: String) : ExperienceSource {
   public final val sidemodId: String

   init {
      this.sidemodId = sidemodId;
   }

   override fun isBattle(): Boolean {
      return ExperienceSource.DefaultImpls.isBattle(this);
   }

   override fun isInteraction(): Boolean {
      return ExperienceSource.DefaultImpls.isInteraction(this);
   }

   override fun isCommand(): Boolean {
      return ExperienceSource.DefaultImpls.isCommand(this);
   }

   override fun isSidemod(): Boolean {
      return ExperienceSource.DefaultImpls.isSidemod(this);
   }
}
