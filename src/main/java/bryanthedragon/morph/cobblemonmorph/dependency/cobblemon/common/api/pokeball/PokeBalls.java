package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects.CaptureEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects.FriendshipEarningBoostEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.BaseStatModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.CatchRateModifiers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.GuaranteedModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.LabelModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.MultiplierModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nPokeBalls.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokeBalls.kt\ncom/cobblemon/mod/common/api/pokeball/PokeBalls\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,258:1\n467#2,7:259\n*S KotlinDebug\n*F\n+ 1 PokeBalls.kt\ncom/cobblemon/mod/common/api/pokeball/PokeBalls\n*L\n234#1:259,7\n*E\n"])
public object PokeBalls : JsonDataRegistry<PokeBall> {
   public final val ANCIENT_AZURE_BALL: PokeBall
      public final get() {
         return this.byName("ancient_azure_ball");
      }


   public final val ANCIENT_CITRINE_BALL: PokeBall
      public final get() {
         return this.byName("ancient_citrine_ball");
      }


   public final val ANCIENT_FEATHER_BALL: PokeBall
      public final get() {
         return this.byName("ancient_feather_ball");
      }


   public final val ANCIENT_GIGATON_BALL: PokeBall
      public final get() {
         return this.byName("ancient_gigaton_ball");
      }


   public final val ANCIENT_GREAT_BALL: PokeBall
      public final get() {
         return this.byName("ancient_great_ball");
      }


   public final val ANCIENT_HEAVY_BALL: PokeBall
      public final get() {
         return this.byName("ancient_heavy_ball");
      }


   public final val ANCIENT_IVORY_BALL: PokeBall
      public final get() {
         return this.byName("ancient_ivory_ball");
      }


   public final val ANCIENT_JET_BALL: PokeBall
      public final get() {
         return this.byName("ancient_jet_ball");
      }


   public final val ANCIENT_LEADEN_BALL: PokeBall
      public final get() {
         return this.byName("ancient_leaden_ball");
      }


   public final val ANCIENT_ORIGIN_BALL: PokeBall
      public final get() {
         return this.byName("ancient_origin_ball");
      }


   public final val ANCIENT_POKE_BALL: PokeBall
      public final get() {
         return this.byName("ancient_poke_ball");
      }


   public final val ANCIENT_ROSEATE_BALL: PokeBall
      public final get() {
         return this.byName("ancient_roseate_ball");
      }


   public final val ANCIENT_SLATE_BALL: PokeBall
      public final get() {
         return this.byName("ancient_slate_ball");
      }


   public final val ANCIENT_ULTRA_BALL: PokeBall
      public final get() {
         return this.byName("ancient_ultra_ball");
      }


   public final val ANCIENT_VERDANT_BALL: PokeBall
      public final get() {
         return this.byName("ancient_verdant_ball");
      }


   public final val ANCIENT_WING_BALL: PokeBall
      public final get() {
         return this.byName("ancient_wing_ball");
      }


   public final val AZURE_BALL: PokeBall
      public final get() {
         return this.byName("azure_ball");
      }


   public final val BEAST_BALL: PokeBall
      public final get() {
         return this.byName("beast_ball");
      }


   public final val CHERISH_BALL: PokeBall
      public final get() {
         return this.byName("cherish_ball");
      }


   public final val CITRINE_BALL: PokeBall
      public final get() {
         return this.byName("citrine_ball");
      }


   public final val DIVE_BALL: PokeBall
      public final get() {
         return this.byName("dive_ball");
      }


   public final val DREAM_BALL: PokeBall
      public final get() {
         return this.byName("dream_ball");
      }


   public final val DUSK_BALL: PokeBall
      public final get() {
         return this.byName("dusk_ball");
      }


   public final val FAST_BALL: PokeBall
      public final get() {
         return this.byName("fast_ball");
      }


   public final val FRIEND_BALL: PokeBall
      public final get() {
         return this.byName("friend_ball");
      }


   public final val GREAT_BALL: PokeBall
      public final get() {
         return this.byName("great_ball");
      }


   public final val HEAL_BALL: PokeBall
      public final get() {
         return this.byName("heal_ball");
      }


   public final val HEAVY_BALL: PokeBall
      public final get() {
         return this.byName("heavy_ball");
      }


   public final val LEVEL_BALL: PokeBall
      public final get() {
         return this.byName("level_ball");
      }


   public final val LOVE_BALL: PokeBall
      public final get() {
         return this.byName("love_ball");
      }


   public final val LURE_BALL: PokeBall
      public final get() {
         return this.byName("lure_ball");
      }


   public final val LUXURY_BALL: PokeBall
      public final get() {
         return this.byName("luxury_ball");
      }


   public final val MASTER_BALL: PokeBall
      public final get() {
         return this.byName("master_ball");
      }


   public final val MOON_BALL: PokeBall
      public final get() {
         return this.byName("moon_ball");
      }


   public final val NEST_BALL: PokeBall
      public final get() {
         return this.byName("nest_ball");
      }


   public final val NET_BALL: PokeBall
      public final get() {
         return this.byName("net_ball");
      }


   public final val PARK_BALL: PokeBall
      public final get() {
         return this.byName("park_ball");
      }


   public final val POKE_BALL: PokeBall
      public final get() {
         return this.byName("poke_ball");
      }


   public final val PREMIER_BALL: PokeBall
      public final get() {
         return this.byName("premier_ball");
      }


   public final val QUICK_BALL: PokeBall
      public final get() {
         return this.byName("quick_ball");
      }


   public final val REPEAT_BALL: PokeBall
      public final get() {
         return this.byName("repeat_ball");
      }


   public final val ROSEATE_BALL: PokeBall
      public final get() {
         return this.byName("roseate_ball");
      }


   public final val SAFARI_BALL: PokeBall
      public final get() {
         return this.byName("safari_ball");
      }


   public final val SLATE_BALL: PokeBall
      public final get() {
         return this.byName("slate_ball");
      }


   public final val SPORT_BALL: PokeBall
      public final get() {
         return this.byName("sport_ball");
      }


   public final val TIMER_BALL: PokeBall
      public final get() {
         return this.byName("timer_ball");
      }


   public final val ULTRA_BALL: PokeBall
      public final get() {
         return this.byName("ultra_ball");
      }


   public final val VERDANT_BALL: PokeBall
      public final get() {
         return this.byName("verdant_ball");
      }


   private final val custom: HashMap<ResourceLocation, PokeBall> = new HashMap()
   private final val defaults: HashMap<ResourceLocation, PokeBall> = new HashMap()
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("pokeballs")
   public open val observable: SimpleObservable<PokeBalls> = new SimpleObservable()
   public open val resourcePath: String = "pokeballs"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<PokeBall>

   public override fun reload(data: Map<ResourceLocation, PokeBall>) {
      custom.clear();
   }

   public override fun sync(player: ServerPlayer) {
   }

   public fun getPokeBall(name: ResourceLocation): PokeBall? {
      var var10000: PokeBall = custom.get(name);
      if (var10000 == null) {
         var10000 = defaults.get(name);
      }

      return var10000;
   }

   public fun all(): List<PokeBall> {
      val `$this$filterKeys$iv`: java.util.Map = defaults;
      val `result$iv`: LinkedHashMap = new LinkedHashMap();

      for (Entry entry$iv : $this$filterKeys$iv.entrySet()) {
         if (!custom.containsKey(`entry$iv`.getKey() as ResourceLocation)) {
            `result$iv`.put(`entry$iv`.getKey(), `entry$iv`.getValue());
         }
      }

      val var10000: java.util.Collection = `result$iv`.values();
      val var10001: java.util.Collection = custom.values();
      return CollectionsKt.plus(var10000, var10001);
   }

   private fun createDefault(
      name: String,
      modifier: CatchRateModifier = (new MultiplierModifier(1.0F, <unrepresentable>.INSTANCE)) as CatchRateModifier,
      effects: List<CaptureEffect> = CollectionsKt.emptyList(),
      waterDragValue: Float = 0.8F,
      model2d: ResourceLocation = MiscUtilsKt.cobblemonResource(name),
      model3d: ResourceLocation = MiscUtilsKt.cobblemonResource("$name_model"),
      throwPower: Float = 1.25F,
      ancient: Boolean = false
   ): PokeBall {
      val identifier: ResourceLocation = MiscUtilsKt.cobblemonResource(name);
      val pokeball: PokeBall = new PokeBall(identifier, modifier, effects, waterDragValue, model2d, model3d, throwPower, ancient);
      defaults.put(identifier, pokeball);
      return pokeball;
   }

   private fun byName(name: String): PokeBall {
      val identifier: ResourceLocation = MiscUtilsKt.cobblemonResource(name);
      var var10000: PokeBall = custom.get(identifier);
      if (var10000 == null) {
         val var3: Any = defaults.get(identifier);
         var10000 = var3 as PokeBall;
      }

      return var10000;
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var10000: Gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
      gson = var10000;
      val var4: TypeToken = TypeToken.get(PokeBall.class);
      typeToken = var4;
      createDefault$default(INSTANCE, "poke_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "slate_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "azure_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "verdant_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "roseate_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "citrine_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "great_ball", new MultiplierModifier(1.5F, null, 2, null), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "ultra_ball", new MultiplierModifier(2.0F, null, 2, null), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "master_ball", new GuaranteedModifier(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "safari_ball", CatchRateModifiers.INSTANCE.getSAFARI(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE, "fast_ball", new BaseStatModifier(Stats.SPEED, <unrepresentable>.INSTANCE, 4.0F), null, 0.0F, null, null, 0.0F, false, 252, null
      );
      createDefault$default(INSTANCE, "level_ball", CatchRateModifiers.INSTANCE.getLEVEL(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE,
         "lure_ball",
         CatchRateModifiers.INSTANCE.typeBoosting(2.0F, ElementalTypes.INSTANCE.getWATER()),
         null,
         0.0F,
         null,
         null,
         0.0F,
         false,
         252,
         null
      );
      createDefault$default(INSTANCE, "heavy_ball", CatchRateModifiers.INSTANCE.getWEIGHT_BASED(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "love_ball", CatchRateModifiers.INSTANCE.getLOVE(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE, "friend_ball", null, CollectionsKt.listOf(CaptureEffects.INSTANCE.friendshipSetter(150)), 0.0F, null, null, 0.0F, false, 250, null
      );
      createDefault$default(INSTANCE, "moon_ball", CatchRateModifiers.INSTANCE.getMOON_PHASES(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "sport_ball", new MultiplierModifier(1.5F, null, 2, null), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE,
         "net_ball",
         CatchRateModifiers.INSTANCE.typeBoosting(3.0F, ElementalTypes.INSTANCE.getBUG(), ElementalTypes.INSTANCE.getWATER()),
         null,
         0.0F,
         null,
         null,
         0.0F,
         false,
         252,
         null
      );
      createDefault$default(INSTANCE, "dive_ball", CatchRateModifiers.INSTANCE.getSUBMERGED_IN_WATER(), null, 0.99F, null, null, 0.0F, false, 244, null);
      createDefault$default(INSTANCE, "nest_ball", CatchRateModifiers.INSTANCE.getNEST(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "repeat_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(
         INSTANCE, "timer_ball", CatchRateModifiers.INSTANCE.turnBased(<unrepresentable>.INSTANCE), null, 0.0F, null, null, 0.0F, false, 252, null
      );
      createDefault$default(
         INSTANCE, "luxury_ball", null, CollectionsKt.listOf(new FriendshipEarningBoostEffect(2.0F)), 0.0F, null, null, 0.0F, false, 250, null
      );
      createDefault$default(INSTANCE, "premier_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "dusk_ball", CatchRateModifiers.INSTANCE.getLIGHT_LEVEL(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE, "heal_ball", null, CollectionsKt.listOf(CaptureEffects.INSTANCE.getFULL_RESTORE()), 0.0F, null, null, 0.0F, false, 250, null
      );
      createDefault$default(
         INSTANCE, "quick_ball", CatchRateModifiers.INSTANCE.turnBased(<unrepresentable>.INSTANCE), null, 0.0F, null, null, 0.0F, false, 252, null
      );
      createDefault$default(INSTANCE, "cherish_ball", null, null, 0.0F, null, null, 0.0F, false, 254, null);
      createDefault$default(INSTANCE, "park_ball", CatchRateModifiers.INSTANCE.getPARK(), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(
         INSTANCE, "dream_ball", CatchRateModifiers.INSTANCE.statusBoosting(4.0F, Statuses.INSTANCE.getSLEEP()), null, 0.0F, null, null, 0.0F, false, 252, null
      );
      createDefault$default(INSTANCE, "beast_ball", new LabelModifier(5.0F, true, "ultra_beast"), null, 0.0F, null, null, 0.0F, false, 252, null);
      createDefault$default(INSTANCE, "ancient_poke_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_citrine_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_verdant_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_azure_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_roseate_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_slate_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_ivory_ball", null, null, 0.0F, null, null, 0.0F, true, 126, null);
      createDefault$default(INSTANCE, "ancient_great_ball", new MultiplierModifier(1.5F, null, 2, null), null, 0.0F, null, null, 0.0F, true, 124, null);
      createDefault$default(INSTANCE, "ancient_ultra_ball", new MultiplierModifier(2.0F, null, 2, null), null, 0.0F, null, null, 0.0F, true, 124, null);
      createDefault$default(INSTANCE, "ancient_heavy_ball", null, null, 0.0F, null, null, 0.75F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_leaden_ball", null, null, 0.0F, null, null, 0.75F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_gigaton_ball", null, null, 0.0F, null, null, 0.75F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_feather_ball", null, null, 0.0F, null, null, 2.5F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_wing_ball", null, null, 0.0F, null, null, 2.5F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_jet_ball", null, null, 0.0F, null, null, 2.5F, true, 62, null);
      createDefault$default(INSTANCE, "ancient_origin_ball", new GuaranteedModifier(), null, 0.0F, null, null, 0.0F, true, 124, null);
      CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(Priority.LOW, <unrepresentable>.INSTANCE);
   }
}
