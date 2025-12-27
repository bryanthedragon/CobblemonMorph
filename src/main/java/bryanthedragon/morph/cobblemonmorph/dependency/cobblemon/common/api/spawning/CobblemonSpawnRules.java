package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.SpawnRule
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.FilterRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.LocationRuleCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.WeightTweakRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ConditionalSpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailSelectorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnRuleComponentAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningContextSelectorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TextAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nCobblemonSpawnRules.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonSpawnRules.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawnRules\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 SpawnRuleComponent.kt\ncom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent$Companion\n+ 4 SpawnDetailSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector$Companion\n+ 5 SpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector$Companion\n*L\n1#1,84:1\n215#2,2:85\n24#3,2:87\n24#3,2:89\n24#3,2:91\n18#4,2:93\n18#5,2:95\n18#5,2:97\n*S KotlinDebug\n*F\n+ 1 CobblemonSpawnRules.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawnRules\n*L\n75#1:85,2\n60#1:87,2\n61#1:89,2\n62#1:91,2\n64#1:93,2\n66#1:95,2\n67#1:97,2\n*E\n"])
public object CobblemonSpawnRules : JsonDataRegistry<SpawnRule> {
   public open val gson: Gson =
      new GsonBuilder()
         .registerTypeAdapter(SpawnRuleComponent::class.java, SpawnRuleComponentAdapter.INSTANCE)
         .registerTypeAdapter(SpawnDetailSelector::class.java, SpawnDetailSelectorAdapter.INSTANCE)
         .registerTypeAdapter(SpawningContextSelector::class.java, SpawningContextSelectorAdapter.INSTANCE)
         .registerTypeAdapter(SpawningCondition::class.java, SpawningConditionAdapter.INSTANCE)
         .registerTypeAdapter(Expression::class.java, ExpressionAdapter.INSTANCE)
         .registerTypeAdapter(Component::class.java, TextAdapter.INSTANCE)
         .create()
         public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("spawn_rules")
   public open val observable: SimpleObservable<CobblemonSpawnRules> = new SimpleObservable()
   public open val resourcePath: String = "spawn_rules"
   public final val rules: MutableMap<ResourceLocation, SpawnRule> = (new LinkedHashMap()) as java.util.Map
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<SpawnRule> = TypeToken.get(SpawnRule.class)

   public override fun reload(data: Map<ResourceLocation, SpawnRule>) {
      rules.clear();
      rules.putAll(data);

      for (Entry element$iv : data.entrySet()) {
         (`element$iv`.getValue() as SpawnRule).setId(`element$iv`.getKey() as ResourceLocation);
      }

      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      SpawnRuleComponent.Companion.getTypes().put("weight", WeightTweakRuleComponent::class.java);
      SpawnRuleComponent.Companion.getTypes().put("filter", FilterRuleComponent::class.java);
      SpawnRuleComponent.Companion.getTypes().put("location", LocationRuleCalculator::class.java);
      SpawnDetailSelector.Companion.getTypes().put("expression", ExpressionSpawnDetailSelector::class.java);
      SpawningContextSelector.Companion.getTypes().put("expression", ExpressionSpawningContextSelector::class.java);
      SpawningContextSelector.Companion.getTypes().put("conditional", ConditionalSpawningContextSelector::class.java);
   }
}
