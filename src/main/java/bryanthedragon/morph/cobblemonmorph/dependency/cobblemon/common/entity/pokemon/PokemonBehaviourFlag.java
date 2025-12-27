package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon

public enum PokemonBehaviourFlag {
   LOOKING,
   EXCITED,
   FLYING
   public final val bit: Int = this.ordinal() + 1
}
