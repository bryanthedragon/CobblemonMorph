package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

public class ModelBone {
   public final var cubes: List<Cube>?
   public final var locators: Map<String, LocatorBone>?
   public final var name: String = ""
   public final var parent: String?
   public final var pivot: List<Float> = CollectionsKt.emptyList()
   public final var rotation: List<Float>?
}
