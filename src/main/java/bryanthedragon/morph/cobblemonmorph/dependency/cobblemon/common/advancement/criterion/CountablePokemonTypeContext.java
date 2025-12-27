package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

public open class CountablePokemonTypeContext(times: Int, type: String) : CountableContext(times) {
   public final var type: String

   init {
      this.type = type;
   }
}
