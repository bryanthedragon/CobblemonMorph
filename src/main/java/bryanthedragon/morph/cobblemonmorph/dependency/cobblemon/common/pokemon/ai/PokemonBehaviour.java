package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

public open class PokemonBehaviour {
   public final val idle: IdleBehaviour = new IdleBehaviour()
   public final var moving: MoveBehaviour = new MoveBehaviour()
   public final val resting: RestBehaviour = new RestBehaviour()
}
