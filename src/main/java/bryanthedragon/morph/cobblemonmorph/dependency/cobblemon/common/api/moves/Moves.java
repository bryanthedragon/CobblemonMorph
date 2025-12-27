package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.MovesRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nMoves.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Moves.kt\ncom/cobblemon/mod/common/api/moves/Moves\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,120:1\n37#2,2:121\n1855#3,2:123\n*S KotlinDebug\n*F\n+ 1 Moves.kt\ncom/cobblemon/mod/common/api/moves/Moves\n*L\n89#1:121,2\n112#1:123,2\n*E\n"])
public object Moves : DataRegistry {
   private final val allMoves: MutableMap<String, MoveTemplate> = (new LinkedHashMap()) as java.util.Map
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("moves")
   private final val idMapping: MutableMap<Int, MoveTemplate> = (new LinkedHashMap()) as java.util.Map
   public open val observable: SimpleObservable<Moves> = new SimpleObservable()
   public open val type: PackType = PackType.SERVER_DATA

   public override fun reload(manager: ResourceManager) {
      allMoves.clear();
      idMapping.clear();
      val movesJson: JsonArray = ShowdownService.Companion.getService().getMoves();
      var i: Int = 0;

      for (int var4 = movesJson.size(); i < var4; i++) {
         val jsMove: JsonObject = movesJson.get(i).getAsJsonObject();
         val id: java.lang.String = jsMove.get("id").getAsString();

         try {
            val e: Int = jsMove.get("num").getAsInt();
            val var10000: ElementalTypes = ElementalTypes.INSTANCE;
            var var10001: java.lang.String = jsMove.get("type").getAsString();
            val elementalType: ElementalType = var10000.getOrException(var10001);
            val var34: DamageCategories = DamageCategories.INSTANCE;
            var10001 = jsMove.get("category").getAsString();
            val damageCategory: DamageCategory = var34.getOrException(var10001);
            val power: Double = jsMove.get("basePower").getAsDouble();
            val var35: MoveTarget.Companion = MoveTarget.Companion;
            var10001 = jsMove.get("target").getAsString();
            val target: MoveTarget = var35.fromShowdownId(var10001);
            val accuracyJson: JsonPrimitive = jsMove.get("accuracy").getAsJsonPrimitive();
            val accuracy: Double = if (accuracyJson.isNumber()) accuracyJson.getAsDouble() else -1.0;
            val pp: Int = jsMove.get("pp").getAsInt();
            val priority: Int = jsMove.get("priority").getAsInt();
            val var36: JsonElement = jsMove.get("critRatio");
            val critRatio: Double = if (var36 != null) var36.getAsDouble() else 1.0;
            val effectChances: ArrayList = new ArrayList();
            val secondariesMember: JsonElement = jsMove.get("secondaries");
            val secondaryMember: JsonElement = jsMove.get("secondary");
            if (secondariesMember != null && secondariesMember is JsonArray) {
               var actionEffect: Int = 0;

               for (int move = ((JsonArray)secondariesMember).size(); j < move; j++) {
                  val `$this$toTypedArray$iv`: JsonObject = (secondariesMember as JsonArray).get(actionEffect).getAsJsonObject();
                  if (`$this$toTypedArray$iv`.has("chance")) {
                     effectChances.add(`$this$toTypedArray$iv`.get("chance").getAsDouble());
                  }
               }
            } else if (secondaryMember != null && secondaryMember is JsonObject && (secondaryMember as JsonObject).has("chance")) {
               effectChances.add((secondaryMember as JsonObject).get("chance").getAsDouble());
            }

            val var37: java.util.Map = ActionEffects.INSTANCE.getActionEffects();
            var var38: ActionEffectTimeline = var37.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(id, null, 1, null)) as ActionEffectTimeline;
            if (var38 == null) {
               val `$i$f$toTypedArray`: Moves = this;
               var38 = ActionEffects.INSTANCE
                  .getActionEffects()
                  .get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("generic_move", null, 1, null));
            }

            this.register(
               new MoveTemplate(
                  id, e, elementalType, damageCategory, power, target, accuracy, pp, priority, critRatio, effectChances.toArray(new java.lang.Double[0]), var38
               )
            );
         } catch (var28: Exception) {
            Cobblemon.INSTANCE.getLOGGER().error("Caught exception trying to resolve the move '{}'", id, var28);
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} moves", allMoves.size());
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
      new MovesRegistrySyncPacket(this.all()).sendToPlayer(player);
   }

   public fun getByName(name: String): MoveTemplate? {
      val var10000: java.util.Map = allMoves;
      val var10001: java.lang.String = name.toLowerCase(Locale.ROOT);
      return var10000.get(var10001) as MoveTemplate;
   }

   public fun getByNumericalId(id: Int): MoveTemplate? {
      return idMapping.get(id);
   }

   public fun getByNameOrDummy(name: String): MoveTemplate {
      val var10000: java.util.Map = allMoves;
      var var10001: java.lang.String = name.toLowerCase(Locale.ROOT);
      var var2: MoveTemplate = var10000.get(var10001) as MoveTemplate;
      if (var2 == null) {
         val var3: MoveTemplate.Companion = MoveTemplate.Companion;
         var10001 = name.toLowerCase(Locale.ROOT);
         var2 = var3.dummy(var10001);
      }

      return var2;
   }

   public fun getExceptional(): MoveTemplate {
      var var10000: MoveTemplate = this.getByName("tackle");
      if (var10000 == null) {
         var10000 = CollectionsKt.random(allMoves.values(), Random.Default as Random) as MoveTemplate;
      }

      return var10000;
   }

   public fun count(): Int {
      return allMoves.size();
   }

   public fun names(): Collection<String> {
      return CollectionsKt.toSet(allMoves.keySet());
   }

   public fun all(): List<MoveTemplate> {
      return CollectionsKt.toList(allMoves.values());
   }

   internal fun receiveSyncPacket(moves: Collection<MoveTemplate>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.register(`element$iv` as MoveTemplate);
      }
   }

   private fun register(move: MoveTemplate) {
      allMoves.put(move.getName(), move);
      idMapping.put(move.getNum(), move);
   }
}
