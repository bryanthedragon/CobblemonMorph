package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters.RequirementAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AnyRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AreaRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AttackDefenceRatioRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BattleCriticalHitsRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BiomeRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BlocksTraveledRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DamageTakenRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DefeatRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.FriendshipRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.HeldItemRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.LevelRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoonPhaseRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoveSetRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoveTypeRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PartyMemberRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PlayerHasAdvancementRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PokemonPropertiesRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PropertyRangeRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.RecoilRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StatCompareRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StatEqualRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StructureRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.TimeRangeRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.UseMoveRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.WeatherRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.WorldRequirement
import com.google.common.collect.HashBiMap
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import java.util.Locale
import kotlin.reflect.KClass

public object CobblemonRequirementAdapter : RequirementAdapter {
   private const val VARIANT: String = "variant"
   private final val types: HashBiMap<String, KClass<out EvolutionRequirement>> = HashBiMap.create()

   public override fun <T : EvolutionRequirement> registerType(id: String, type: KClass<Any>) {
      val var10000: HashBiMap = types;
      val var3: java.util.Map = var10000 as java.util.Map;
      val var5: java.lang.String = id.toLowerCase(Locale.ROOT);
      var3.put(var5, type);
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EvolutionRequirement {
      var var10000: java.lang.String = json.getAsJsonObject().get("variant").getAsString();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val var7: KClass = types.get(var10000) as KClass;
      if (var7 == null) {
         throw new IllegalArgumentException("Cannot resolve evolution requirement type for variant $var10000");
      } else {
         val var8: Any = context.deserialize(json, JvmClassMappingKt.getJavaClass(var7));
         return var8 as EvolutionRequirement;
      }
   }

   public open fun serialize(src: EvolutionRequirement, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      val json: JsonObject = context.serialize(src, src.getClass()).getAsJsonObject();
      val var10000: java.lang.String = types.inverse().get(src.getClass()::class) as java.lang.String;
      if (var10000 == null) {
         throw new IllegalArgumentException("Cannot resolve evolution requirement for type ${(src.getClass()::class).getQualifiedName()}");
      } else {
         json.addProperty("variant", var10000);
         return json as JsonElement;
      }
   }

   @JvmStatic
   fun {
      INSTANCE.registerType("area", AreaRequirement::class);
      INSTANCE.registerType("biome", BiomeRequirement::class);
      INSTANCE.registerType("friendship", FriendshipRequirement::class);
      INSTANCE.registerType("held_item", HeldItemRequirement::class);
      INSTANCE.registerType("world", WorldRequirement::class);
      INSTANCE.registerType("has_move", MoveSetRequirement::class);
      INSTANCE.registerType("has_move_type", MoveTypeRequirement::class);
      INSTANCE.registerType("party_member", PartyMemberRequirement::class);
      INSTANCE.registerType("properties", PokemonPropertiesRequirement::class);
      INSTANCE.registerType("time_range", TimeRangeRequirement::class);
      INSTANCE.registerType("level", LevelRequirement::class);
      INSTANCE.registerType("weather", WeatherRequirement::class);
      INSTANCE.registerType("stat_compare", StatCompareRequirement::class);
      INSTANCE.registerType("stat_equal", StatEqualRequirement::class);
      INSTANCE.registerType("attack_defence_ratio", AttackDefenceRatioRequirement::class);
      INSTANCE.registerType("battle_critical_hits", BattleCriticalHitsRequirement::class);
      INSTANCE.registerType("damage_taken", DamageTakenRequirement::class);
      INSTANCE.registerType("use_move", UseMoveRequirement::class);
      INSTANCE.registerType("moon_phase", MoonPhaseRequirement::class);
      INSTANCE.registerType("recoil", RecoilRequirement::class);
      INSTANCE.registerType("defeat", DefeatRequirement::class);
      INSTANCE.registerType("blocks_traveled", BlocksTraveledRequirement::class);
      INSTANCE.registerType("structure", StructureRequirement::class);
      INSTANCE.registerType("any", AnyRequirement::class);
      INSTANCE.registerType("property_range", PropertyRangeRequirement::class);
      INSTANCE.registerType(PlayerHasAdvancementRequirement.Companion.getADAPTER_VARIANT(), PlayerHasAdvancementRequirement::class);
   }
}
