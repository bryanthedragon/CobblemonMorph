package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import net.minecraft.resources.ResourceLocation

public object PokeBallModelRepository : VaryingModelRepository<EmptyPokeBallEntity, PokeBallModel> {
   public open val animationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/animations")
   public open val fallback: ResourceLocation = PokeBalls.INSTANCE.getPOKE_BALL().getName()
   public open val isForLivingEntityRenderer: Boolean
   public open val modelDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/models")
   public open val poserDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/posers")
   public open val title: String = "Poké Ball"
   public open val type: String = "poke_balls"
   public open val variationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/variations")

   public override fun loadJsonPoser(json: String): (Bone) -> PokeBallModel {
      throw new NotImplementedError("An operation is not implemented: Not yet implemented");
   }

   public override fun registerInBuiltPosers() {
      this.inbuilt("azure_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("beast_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("cherish_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("citrine_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("dive_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("dream_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("dusk_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("fast_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("friend_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("great_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("heal_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("heavy_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("level_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("love_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("lure_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("luxury_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("master_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("moon_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("nest_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("net_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("park_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("poke_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("premier_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("quick_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("repeat_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("roseate_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("safari_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("slate_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("sport_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("strange_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("timer_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ultra_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("verdant_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_poke_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_citrine_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_verdant_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_azure_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_roseate_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_slate_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_ivory_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_great_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_ultra_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_feather_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_wing_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_jet_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_heavy_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_leaden_ball", <unrepresentable>.INSTANCE);
      this.inbuilt("ancient_gigaton_ball", <unrepresentable>.INSTANCE);
   }
}
