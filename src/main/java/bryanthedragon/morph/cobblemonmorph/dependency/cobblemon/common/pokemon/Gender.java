package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

public enum Gender(showdownName: String) {
   MALE("M"),
   FEMALE("F"),
   GENDERLESS("N")
   public final val showdownName: String

   init {
      this.showdownName = showdownName;
   }
}
