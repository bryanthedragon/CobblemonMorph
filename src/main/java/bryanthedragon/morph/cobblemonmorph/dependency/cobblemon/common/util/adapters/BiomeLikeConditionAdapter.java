package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.functions.Function1
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome

public object BiomeLikeConditionAdapter : RegistryLikeAdapter<Biome> {
   public open val registryLikeConditions: MutableList<(JsonElement) -> RegistryLikeCondition<Biome>?>

   override fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): RegistryLikeCondition<Biome> {
      return RegistryLikeAdapter.DefaultImpls.deserialize(this, json, type, ctx);
   }

   @JvmStatic
   fun {
      val var0: Array<Array<Function1>> = new Function1[2];
      val var10002: RegistryLikeTagCondition.Companion = RegistryLikeTagCondition.Companion;
      val var10003: ResourceKey = Registries.f_256952_;
      var0[0] = var10002.resolver(var10003, <unrepresentable>.INSTANCE);
      var0[1] = RegistryLikeIdentifierCondition.Companion.resolver(<unrepresentable>.INSTANCE);
      registryLikeConditions = CollectionsKt.mutableListOf(var0);
   }
}
