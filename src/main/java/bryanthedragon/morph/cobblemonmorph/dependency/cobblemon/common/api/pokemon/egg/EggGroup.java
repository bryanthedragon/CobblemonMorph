package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg

public enum EggGroup(showdownID: String) {
   MONSTER("Monster"),
   WATER_1("Water 1"),
   BUG("Bug"),
   FLYING("Flying"),
   FIELD("Field"),
   FAIRY("Fairy"),
   GRASS("Grass"),
   HUMAN_LIKE("Human-Like"),
   WATER_3("Water 3"),
   MINERAL("Mineral"),
   AMORPHOUS("Amorphous"),
   WATER_2("Water 2"),
   DITTO("Ditto"),
   DRAGON("Dragon"),
   UNDISCOVERED("Undiscovered")
   internal final val showdownID: String

   init {
      this.showdownID = showdownID;
   }
}
