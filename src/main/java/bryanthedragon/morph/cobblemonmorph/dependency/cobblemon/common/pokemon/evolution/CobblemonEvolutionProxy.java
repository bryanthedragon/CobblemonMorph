package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionProxy
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller.ClientEvolutionController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller.ServerEvolutionController
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

public class CobblemonEvolutionProxy(pokemon: Pokemon, clientSide: Boolean) : EvolutionProxy<EvolutionDisplay, Evolution> {
   private final val clientSide: Boolean
   private final val controller: EvolutionController<out EvolutionLike>
   private final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.clientSide = clientSide;
      this.controller = (EvolutionController<? extends EvolutionLike>)(if (this.clientSide)
         new ClientEvolutionController(this.pokemon)
         else
         new ServerEvolutionController(this.pokemon));
   }

   public override fun isClient(): Boolean {
      return this.clientSide;
   }

   public override fun current(): EvolutionController<out EvolutionLike> {
      return this.controller;
   }

   public override fun client(): EvolutionController<EvolutionDisplay> {
      val var1: EvolutionController = this.controller;
      val var10000: EvolutionController = if (this.controller is EvolutionController) this.controller else null;
      if ((if (this.controller is EvolutionController) this.controller else null) == null) {
         throw new ClassCastException("Cannot use the client implementation from the server side");
      } else {
         return var10000;
      }
   }

   public override fun server(): EvolutionController<Evolution> {
      val var1: EvolutionController = this.controller;
      val var10000: EvolutionController = if (this.controller is EvolutionController) this.controller else null;
      if ((if (this.controller is EvolutionController) this.controller else null) == null) {
         throw new ClassCastException("Cannot use the server implementation from the client side");
      } else {
         return var10000;
      }
   }

   public override fun saveToNBT(): Tag {
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128365_("Pending", this.current().saveToNBT());
      return nbt as Tag;
   }

   public override fun loadFromNBT(nbt: Tag) {
      val var10000: CompoundTag = nbt as? CompoundTag;
      if ((nbt as? CompoundTag) != null) {
         val var3: EvolutionController = this.current();
         val var10001: Tag = var10000.m_128423_("Pending");
         if (var10001 != null) {
            var3.loadFromNBT(var10001);
         }
      }
   }

   public override fun saveToJson(): JsonElement {
      val json: JsonObject = new JsonObject();
      json.add("Pending", this.current().saveToJson());
      return json as JsonElement;
   }

   public override fun loadFromJson(json: JsonElement) {
      val var10000: JsonObject = json as? JsonObject;
      if ((json as? JsonObject) != null) {
         val var3: EvolutionController = this.current();
         var var10001: JsonElement = var10000.get("Pending");
         if (var10001 == null) {
            var10001 = (new JsonObject()) as JsonElement;
         }

         var3.loadFromJson(var10001);
      }
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf, toClient: Boolean) {
      this.current().saveToBuffer(buffer, toClient);
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf) {
      this.current().loadFromBuffer(buffer);
   }
}
