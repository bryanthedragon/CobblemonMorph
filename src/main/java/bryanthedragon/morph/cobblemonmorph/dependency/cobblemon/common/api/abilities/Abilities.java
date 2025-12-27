package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.AbilityRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonArray
import java.util.LinkedHashMap
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nAbilities.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Abilities.kt\ncom/cobblemon/mod/common/api/abilities/Abilities\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n1855#2,2:71\n*S KotlinDebug\n*F\n+ 1 Abilities.kt\ncom/cobblemon/mod/common/api/abilities/Abilities\n*L\n67#1:71,2\n*E\n"])
public object Abilities : DataRegistry {
   public final val DUMMY: AbilityTemplate = new AbilityTemplate("dummy", null, null, null, 14, null)
   private final val abilityMap: MutableMap<String, AbilityTemplate> = (new LinkedHashMap()) as java.util.Map
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("abilities")
   public open val observable: SimpleObservable<Abilities> = new SimpleObservable()
   public open val type: PackType = PackType.SERVER_DATA

   public override fun reload(manager: ResourceManager) {
      PotentialAbility.Companion.getTypes().clear();
      PotentialAbility.Companion.getTypes().add(CommonAbilityType.INSTANCE);
      PotentialAbility.Companion.getTypes().add(HiddenAbilityType.INSTANCE);
      abilityMap.clear();
      val abilitiesJson: JsonArray = ShowdownService.Companion.getService().getAbilityIds();
      var i: Int = 0;

      for (int var4 = abilitiesJson.size(); i < var4; i++) {
         val id: java.lang.String = abilitiesJson.get(i).getAsString();
         this.register(new AbilityTemplate(id, null, null, null, 14, null));
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} abilities", abilityMap.size());
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
      new AbilityRegistrySyncPacket(this.all()).sendToPlayer(player);
   }

   public fun register(ability: AbilityTemplate): AbilityTemplate {
      val var10000: java.util.Map = abilityMap;
      val var10001: java.lang.String = ability.getName().toLowerCase(Locale.ROOT);
      var10000.put(var10001, ability);
      return ability;
   }

   public fun all(): List<AbilityTemplate> {
      return CollectionsKt.toList(abilityMap.values());
   }

   public fun first(): AbilityTemplate {
      return CollectionsKt.first(abilityMap.values()) as AbilityTemplate;
   }

   public fun get(name: String): AbilityTemplate? {
      val var10000: java.util.Map = abilityMap;
      val var10001: java.lang.String = name.toLowerCase(Locale.ROOT);
      return var10000.get(var10001) as AbilityTemplate;
   }

   public fun getOrException(name: String): AbilityTemplate {
      val var10000: AbilityTemplate = this.get(name);
      if (var10000 == null) {
         throw new IllegalArgumentException("Unable to find ability of name: $name");
      } else {
         return var10000;
      }
   }

   public fun count(): Int {
      return abilityMap.size();
   }

   internal fun receiveSyncPacket(abilities: Collection<AbilityTemplate>) {
      abilityMap.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.register(`element$iv` as AbilityTemplate);
      }
   }
}
