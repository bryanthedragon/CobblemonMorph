package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntityDimensionsAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.adapters.MoveTemplateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.adapter.ShoulderEffectAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroupAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.adapters.ElementalTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonEvolutionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonPreEvolutionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonRequirementAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.NbtItemPredicateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.AbilityPoolAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.AbilityTemplateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DropEntryAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EggGroupAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LazySetAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LearnsetAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.NbtCompoundAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegistryElementAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.StructureLikeConditionAdapter
import com.google.common.collect.HashBasedTable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.HashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.SpreadBuilder
import kotlin.random.Random
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.item.Item
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.phys.AABB

@SourceDebugExtension(["SMAP\nPokemonSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,261:1\n215#2,2:262\n*S KotlinDebug\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies\n*L\n178#1:262,2\n*E\n"])
public object PokemonSpecies : JsonDataRegistry<Species> {
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("species")
   public final val implemented: MutableList<Species> = (new ArrayList()) as java.util.List
   public open val observable: SimpleObservable<PokemonSpecies> = new SimpleObservable()
   public open val resourcePath: String = "species"

   public final val species: Collection<Species>
      public final get() {
         val var10000: java.util.Collection = speciesByIdentifier.values();
         return var10000;
      }


   private final val speciesByDex: HashBasedTable<String, Int, Species> = HashBasedTable.create()
   private final val speciesByIdentifier: HashMap<ResourceLocation, Species> = new HashMap()
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<Species>

   public fun getByName(name: String): Species? {
      return this.getByIdentifier(MiscUtilsKt.cobblemonResource(name));
   }

   public fun getByPokedexNumber(ndex: Int, namespace: String = "cobblemon"): Species? {
      return speciesByDex.get(namespace, ndex) as Species;
   }

   public fun getByIdentifier(identifier: ResourceLocation): Species? {
      return speciesByIdentifier.get(identifier);
   }

   public fun count(): Int {
      return speciesByIdentifier.size();
   }

   public fun random(): Species {
      return CollectionsKt.random(implemented, Random.Default as Random) as Species;
   }

   public override fun reload(data: Map<ResourceLocation, Species>) {
      speciesByIdentifier.clear();
      implemented.clear();
      speciesByDex.clear();

      for (Entry element$iv : data.entrySet()) {
         val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
         val species: Species = `element$iv`.getValue() as Species;
         species.setResourceIdentifier(identifier);
         var var10000: Species = speciesByIdentifier.put(identifier, species);
         if (var10000 != null) {
            var10000 = speciesByDex.remove(var10000.getResourceIdentifier().m_135827_(), var10000.getNationalPokedexNumber()) as Species;
         }

         speciesByDex.put(species.getResourceIdentifier().m_135827_(), species.getNationalPokedexNumber(), species);
         if (species.getImplemented()) {
            implemented.add(species);
         }
      }
   }

   public override fun sync(player: ServerPlayer) {
      new SpeciesRegistrySyncPacket(CollectionsKt.toList(this.getSpecies())).sendToPlayer(player);
   }

   private fun createShowdownName(species: Species): String {
      return if (species.getResourceIdentifier().m_135827_() == "cobblemon")
         species.getName()
         else
         "${species.getResourceIdentifier().m_135827_()}:${species.getName()}";
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var9: Gson = new GsonBuilder()
         .registerTypeAdapter(Stat::class.java, Cobblemon.INSTANCE.getStatProvider().getTypeAdapter())
         .registerTypeAdapter(ElementalType::class.java, ElementalTypeAdapter.INSTANCE)
         .registerTypeAdapter(AbilityTemplate::class.java, AbilityTemplateAdapter.INSTANCE)
         .registerTypeAdapter(ShoulderEffect::class.java, ShoulderEffectAdapter.INSTANCE)
         .registerTypeAdapter(MoveTemplate::class.java, MoveTemplateAdapter.INSTANCE)
         .registerTypeAdapter(ExperienceGroup::class.java, ExperienceGroupAdapter.INSTANCE)
         .registerTypeAdapter(EntityDimensions::class.java, EntityDimensionsAdapter.INSTANCE)
         .registerTypeAdapter(Learnset::class.java, LearnsetAdapter.INSTANCE)
         .registerTypeAdapter(Evolution::class.java, CobblemonEvolutionAdapter.INSTANCE)
         .registerTypeAdapter(AABB::class.java, BoxAdapter.INSTANCE)
         .registerTypeAdapter(AbilityPool::class.java, AbilityPoolAdapter.INSTANCE)
         .registerTypeAdapter(EvolutionRequirement::class.java, CobblemonRequirementAdapter.INSTANCE)
         .registerTypeAdapter(PreEvolution::class.java, CobblemonPreEvolutionAdapter.INSTANCE)
         .registerTypeAdapter(
            TypeToken.getParameterized(java.util.Set::class.java, new Type[]{Evolution.class}).getType(), new LazySetAdapter(Evolution::class)
         )
         .registerTypeAdapter(IntRange::class.java, IntRangeAdapter.INSTANCE)
         .registerTypeAdapter(PokemonProperties::class.java, PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter())
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(TimeRange::class.java, new IntRangesAdapter<>(TimeRange.Companion.getTimeRanges(), <unrepresentable>.INSTANCE))
         .registerTypeAdapter(ItemDropMethod::class.java, ItemDropMethod.Companion.getAdapter())
         .registerTypeAdapter(SleepDepth::class.java, SleepDepth.Companion.getAdapter())
         .registerTypeAdapter(DropEntry::class.java, DropEntryAdapter.INSTANCE)
         .registerTypeAdapter(CompoundTag::class.java, NbtCompoundAdapter.INSTANCE)
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Biome.class}).getType(), BiomeLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Block.class}).getType(), BlockLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Item.class}).getType(), ItemLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Structure.class}).getType(), StructureLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(EggGroup::class.java, EggGroupAdapter.INSTANCE)
         .registerTypeAdapter(MobEffect::class.java, new RegistryElementAdapter(<unrepresentable>.INSTANCE))
         .registerTypeAdapter(NbtItemPredicate::class.java, NbtItemPredicateAdapter.INSTANCE)
         .disableHtmlEscaping()
         .enableComplexMapKeySerialization()
         .create();
      gson = var9;
      val var10: TypeToken = TypeToken.get(Species.class);
      typeToken = var10;
      Observable.DefaultImpls.subscribe$default(SpeciesAdditions.INSTANCE.getObservable(), null, <unrepresentable>.INSTANCE, 1, null);
   }

   @SourceDebugExtension(["SMAP\nPokemonSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies$ShowdownSpecies\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,261:1\n1549#2:262\n1620#2,3:263\n1549#2:268\n1620#2,2:269\n1622#2:272\n1549#2:273\n1620#2,3:274\n37#3,2:266\n1#4:271\n*S KotlinDebug\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies$ShowdownSpecies\n*L\n208#1:262\n208#1:263,3\n216#1:268\n216#1:269,2\n216#1:272\n221#1:273\n221#1:274,3\n209#1:266,2\n*E\n"])
   internal class ShowdownSpecies(species: Species, form: FormData?) {
      public final val abilities: Map<String, String>
      public final val baseSpecies: String
      public final val baseStats: Map<String, Int>
      public final val canGigantamax: String?
      public final val cannotDynamax: Boolean
      public final val eggGroups: List<String>
      public final val evos: List<String>
      public final val forme: String?
      public final val formeOrder: List<String>
      public final val gender: String?
      public final val genderRatio: Map<String, Float>?
      public final val heightm: Float
      public final val maxHP: Int?
      public final val name: String
      public final val nfe: Boolean
      public final val num: Int
      public final val otherFormes: List<String>
      public final val preevo: String?
      public final val requiredItem: String?
      public final val requiredItems: List<String>?
      public final val requiredMove: String?
      public final val types: List<String>
      public final val weightkg: Float

      init {
         this.num = species.getNationalPokedexNumber();
         this.name = if (form != null)
            "${PokemonSpecies.access$createShowdownName(PokemonSpecies.INSTANCE, species)}-${form.getName()}"
            else
            PokemonSpecies.access$createShowdownName(PokemonSpecies.INSTANCE, species);
         this.baseSpecies = if (form != null) PokemonSpecies.access$createShowdownName(PokemonSpecies.INSTANCE, species) else this.name;
         this.forme = if (form != null) form.getName() else null;
         var var10000: PokemonSpecies.ShowdownSpecies = this;
         var var10001: java.util.List;
         if (form == null && !species.getForms().isEmpty()) {
            val `$this$map$iv`: java.lang.Iterable = species.getForms();
            val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

            for (Object item$iv$iv : $this$map$iv) {
               `destination$iv$iv`.add("${this.name}-${(`item$iv$iv` as FormData).getName()}");
            }

            var10001 = `destination$iv$iv` as java.util.List;
            var10000 = this;
         } else {
            var10001 = CollectionsKt.emptyList();
         }

         var10000.otherFormes = var10001;
         if (form == null && !this.otherFormes.isEmpty()) {
            val var18: SpreadBuilder = new SpreadBuilder(2);
            var18.add(this.name);
            var18.addSpread(this.otherFormes.toArray(new java.lang.String[0]));
            var10001 = CollectionsKt.arrayListOf(var18.toArray(new java.lang.String[var18.size()]));
         } else {
            var10001 = CollectionsKt.emptyList();
         }

         label299: {
            this.formeOrder = var10001;
            this.abilities = MapsKt.mapOf(
               new Pair[]{TuplesKt.to("0", "No Ability"), TuplesKt.to("1", "No Ability"), TuplesKt.to("H", "No Ability"), TuplesKt.to("S", "No Ability")}
            );
            if (form != null) {
               var10001 = form.getTypes();
               if (var10001 != null) {
                  break label299;
               }
            }

            var10001 = species.getTypes();
         }

         var var30: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10001, 10));

         val var20: java.lang.Iterable;
         for (Object item$iv$iv : var20) {
            val var12: java.lang.String = (var37 as ElementalType).getName();
            val var48: java.lang.String;
            if (var12.length() > 0) {
               val var44: StringBuilder = new StringBuilder();
               val var45: java.lang.String = java.lang.String.valueOf(var12.charAt(0));
               val var46: java.lang.String = var45.toUpperCase(Locale.ROOT);
               val var47: StringBuilder = var44.append((Object)var46);
               val var52: java.lang.String = var12.substring(1);
               var48 = var47.append(var52).toString();
            } else {
               var48 = var12;
            }

            var30.add(var48);
         }

         label288: {
            this.types = var30 as MutableList<java.lang.String>;
            var10000 = this;
            if (form != null) {
               var53 = form.getPreEvolution();
               if (var53 != null) {
                  break label288;
               }
            }

            var53 = species.getPreEvolution();
         }

         val var54: java.lang.String;
         if (var53 != null) {
            var54 = if (var53.getForm() == var53.getSpecies().getStandardForm())
               PokemonSpecies.access$createShowdownName(PokemonSpecies.INSTANCE, var53.getSpecies())
               else
               "${PokemonSpecies.access$createShowdownName(PokemonSpecies.INSTANCE, var53.getSpecies())}-${var53.getForm().getName()}";
            var10000 = this;
         } else {
            var54 = null;
         }

         label282: {
            var10000.preevo = var54;
            if (form != null) {
               var55 = form.getEvolutions();
               if (var55 != null) {
                  break label282;
               }
            }

            var55 = species.getEvolutions();
         }

         label276: {
            this.evos = (java.util.List<java.lang.String>)(if (var55.isEmpty())
               CollectionsKt.emptyList()
               else
               CollectionsKt.arrayListOf(new java.lang.String[]{""}));
            this.nfe = !this.evos.isEmpty();
            if (form != null) {
               var57 = form.getEggGroups();
               if (var57 != null) {
                  break label276;
               }
            }

            var57 = species.getEggGroups();
         }

         var20 = var57;
         var30 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var57, 10));

         for (Object item$iv$iv : var20) {
            var30.add((var38 as EggGroup).getShowdownID$common());
         }

         var var61: Int;
         label264: {
            this.eggGroups = var30 as MutableList<java.lang.String>;
            val var23: Float = if (form != null) form.getMaleRatio() else species.getMaleRatio();
            this.gender = if (var23 == 0.0F) "F" else (if (var23 == 1.0F) "M" else (if (var23 == -1.0F || var23 == 1.125F) "N" else null));
            this.genderRatio = if (this.gender == null)
               MapsKt.mapOf(
                  new Pair[]{
                     TuplesKt.to("maleRatio", if (form != null) form.getMaleRatio() else species.getMaleRatio()),
                     TuplesKt.to("femaleRation", 1.0F - (if (form != null) form.getMaleRatio() else species.getMaleRatio()))
                  }
               )
               else
               null;
            var25 = new Pair[6];
            if (form != null) {
               val var10004: java.util.Map = form.getBaseStats();
               if (var10004 != null) {
                  val var59: Int = var10004.get(Stats.HP) as Int;
                  if (var59 != null) {
                     var61 = var59;
                     break label264;
                  }
               }
            }

            val var60: Int = species.getBaseStats().get(Stats.HP);
            var61 = (int)(var60 ?: 1);
         }

         label258: {
            var25[0] = TuplesKt.to("hp", var61);
            if (form != null) {
               val var62: java.util.Map = form.getBaseStats();
               if (var62 != null) {
                  val var63: Int = var62.get(Stats.ATTACK) as Int;
                  if (var63 != null) {
                     var61 = var63;
                     break label258;
                  }
               }
            }

            val var64: Int = species.getBaseStats().get(Stats.ATTACK);
            var61 = (int)(var64 ?: 1);
         }

         label252: {
            var25[1] = TuplesKt.to("atk", var61);
            if (form != null) {
               val var66: java.util.Map = form.getBaseStats();
               if (var66 != null) {
                  val var67: Int = var66.get(Stats.DEFENCE) as Int;
                  if (var67 != null) {
                     var61 = var67;
                     break label252;
                  }
               }
            }

            val var68: Int = species.getBaseStats().get(Stats.DEFENCE);
            var61 = (int)(var68 ?: 1);
         }

         label246: {
            var25[2] = TuplesKt.to("def", var61);
            if (form != null) {
               val var70: java.util.Map = form.getBaseStats();
               if (var70 != null) {
                  val var71: Int = var70.get(Stats.SPECIAL_ATTACK) as Int;
                  if (var71 != null) {
                     var61 = var71;
                     break label246;
                  }
               }
            }

            val var72: Int = species.getBaseStats().get(Stats.SPECIAL_ATTACK);
            var61 = (int)(var72 ?: 1);
         }

         label240: {
            var25[3] = TuplesKt.to("spa", var61);
            if (form != null) {
               val var74: java.util.Map = form.getBaseStats();
               if (var74 != null) {
                  val var75: Int = var74.get(Stats.SPECIAL_DEFENCE) as Int;
                  if (var75 != null) {
                     var61 = var75;
                     break label240;
                  }
               }
            }

            val var76: Int = species.getBaseStats().get(Stats.SPECIAL_DEFENCE);
            var61 = (int)(var76 ?: 1);
         }

         label234: {
            var25[4] = TuplesKt.to("spd", var61);
            if (form != null) {
               val var78: java.util.Map = form.getBaseStats();
               if (var78 != null) {
                  val var79: Int = var78.get(Stats.SPEED) as Int;
                  if (var79 != null) {
                     var61 = var79;
                     break label234;
                  }
               }
            }

            val var80: Int = species.getBaseStats().get(Stats.SPEED);
            var61 = (int)(var80 ?: 1);
         }

         var25[5] = TuplesKt.to("spe", var61);
         this.baseStats = MapsKt.mapOf(var25);
         this.heightm = (if (form != null) form.getHeight() else species.getHeight()) / 10;
         this.weightkg = (if (form != null) form.getWeight() else species.getWeight()) / 10;
         this.maxHP = if (species.showdownId() == "shedinja") 1 else null;
         this.canGigantamax = if ((if (form != null) form.getGigantamaxMove() else null) != null) form.getGigantamaxMove().getName() else null;
         this.cannotDynamax = if (form != null) form.getDynamaxBlocked() else species.getDynamaxBlocked();
         this.requiredMove = if (form != null) form.getRequiredMove() else null;
         this.requiredItem = if (form != null) form.getRequiredItem() else null;
         this.requiredItems = if (form != null) form.getRequiredItems() else null;
      }
   }
}
