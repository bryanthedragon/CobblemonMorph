package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

public class MoveBehaviour {
   public final val canLook: Boolean = true
   public final val fly: FlyBehaviour = new FlyBehaviour()
   public final val looksAtEntities: Boolean = true
   public final val stepHeight: Float = 0.6F
   public final val swim: SwimBehaviour = new SwimBehaviour()
   public final val walk: WalkBehaviour = new WalkBehaviour()
   public final val wanderChance: Int = 120
   public final val wanderSpeed: Double = 1.0
}
