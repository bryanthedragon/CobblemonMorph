package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType
import java.util.ArrayList;
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random

@SourceDebugExtension(["SMAP\nHiddenAbilityPropertyType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HiddenAbilityPropertyType.kt\ncom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,43:1\n76#2:44\n96#2,5:45\n76#2:53\n96#2,5:54\n766#3:50\n857#3,2:51\n1#4:59\n*S KotlinDebug\n*F\n+ 1 HiddenAbilityPropertyType.kt\ncom/cobblemon/mod/common/pokemon/properties/HiddenAbilityProperty\n*L\n33#1:44\n33#1:45,5\n40#1:53\n40#1:54,5\n34#1:50\n34#1:51,2\n*E\n"])
public class HiddenAbilityProperty : CustomPokemonProperty {
   public override fun asString(): String {
      return "hiddenability";
   }

   public override fun apply(pokemon: Pokemon) {
      val picked: java.util.Map = pokemon.getForm().getAbilities().getMapping();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Entry element$iv$iv : $this$flatMap$iv.entrySet()) {
         CollectionsKt.addAll(`destination$iv$iv`, `element$iv$iv`.getValue() as java.util.List);
      }

      val var12: java.lang.Iterable = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$iv : var12) {
         if ((var18 as PotentialAbility).getType() == HiddenAbilityType.INSTANCE) {
            `destination$iv$iv`.add(var18);
         }
      }

      val var10000: PotentialAbility = CollectionsKt.randomOrNull(`destination$iv$iv` as java.util.List, Random.Default as Random) as PotentialAbility;
      if (var10000 != null) {
         pokemon.updateAbility(var10000.getTemplate().create(false));
      }
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      val `$this$flatMap$iv`: java.util.Map = pokemon.getForm().getAbilities().getMapping();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Entry element$iv$iv : $this$flatMap$iv.entrySet()) {
         CollectionsKt.addAll(`destination$iv$iv`, `element$iv$iv`.getValue() as java.util.List);
      }

      val `$this$flatMapTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

      var var10000: Any;
      while (true) {
         if (`$this$flatMapTo$iv$iv`.hasNext()) {
            `destination$iv$iv` = (java.util.Collection)`$this$flatMapTo$iv$iv`.next();
            if (!((`destination$iv$iv` as PotentialAbility).getTemplate() == pokemon.getAbility().getTemplate())) {
               continue;
            }

            var10000 = `destination$iv$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return (if (var10000 as PotentialAbility != null) (var10000 as PotentialAbility).getType() else null) == HiddenAbilityType.INSTANCE;
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }
}
