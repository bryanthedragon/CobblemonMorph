package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class TradePokemonCriterionCondition(id: ResourceLocation, entity: ContextAwarePredicate) : SimpleCriterionCondition(id, entity) {
   public final var received: String = "any"
   public final var receivedHeldItem: String = "any"
   public final var traded: String = "any"
   public final var tradedHeldItem: String = "any"

   public override fun toJson(json: JsonObject) {
      json.addProperty("traded", this.traded);
      json.addProperty("received", this.received);
      json.addProperty("traded_held_item", this.tradedHeldItem);
      json.addProperty("received_held_item", this.receivedHeldItem);
   }

   public override fun fromJson(json: JsonObject) {
      var var10001: JsonElement = json.get("traded");
      var var2: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var2 == null) {
         var2 = "any";
      }

      this.traded = var2;
      var10001 = json.get("received");
      var var4: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var4 == null) {
         var4 = "any";
      }

      this.received = var4;
      var10001 = json.get("traded_held_item");
      var var6: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var6 == null) {
         var6 = "minecraft:air";
      }

      this.tradedHeldItem = var6;
      var10001 = json.get("received_held_item");
      var var8: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var8 == null) {
         var8 = "minecraft:air";
      }

      this.receivedHeldItem = var8;
   }

   public open fun matches(player: ServerPlayer, context: TradePokemonContext): Boolean {
      val heldItem1: ResourceLocation = context.getTraded().heldItem().m_41720_().m_204114_().m_205785_().m_135782_();
      val heldItem2: ResourceLocation = context.getReceived().heldItem().m_41720_().m_204114_().m_205785_().m_135782_();
      return (
            context.getTraded().getSpecies().getResourceIdentifier()
                  == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.traded, null, 1, null)
               || this.traded == "any"
         )
         && (
            context.getReceived().getSpecies().getResourceIdentifier()
                  == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.received, null, 1, null)
               || this.received == "any"
         )
         && (
            heldItem1 == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.tradedHeldItem, null, 1, null)
               || heldItem1 == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("minecraft:air", null, 1, null)
         )
         && (
            heldItem2 == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.receivedHeldItem, null, 1, null)
               || heldItem2 == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("minecraft:air", null, 1, null)
         );
   }
}
