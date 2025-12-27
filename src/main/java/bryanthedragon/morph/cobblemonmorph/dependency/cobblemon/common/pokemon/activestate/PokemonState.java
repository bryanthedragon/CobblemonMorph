package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nPokemonState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/PokemonState\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,202:1\n1#2:203\n*E\n"])
public sealed class PokemonState protected constructor() {
   public final val name: String
      public final get() {
         val var2: java.util.Iterator = states.entrySet().iterator();

         var var10000: Any;
         while (true) {
            if (var2.hasNext()) {
               val var3: Any = var2.next();
               if (!((var3 as Entry).getValue() == this.getClass())) {
                  continue;
               }

               var10000 = var3;
               break;
            }

            var10000 = null;
            break;
         }

         return (var10000 as Entry).getKey() as java.lang.String;
      }


   public open fun getIcon(pokemon: Pokemon): ResourceLocation? {
      return null;
   }

   public open fun writeToNBT(nbt: CompoundTag): CompoundTag? {
      nbt.m_128359_("StateType", this.getName());
      return nbt;
   }

   public open fun readFromNBT(nbt: CompoundTag): PokemonState {
      return this;
   }

   public open fun writeToJSON(json: JsonObject): JsonObject? {
      return json;
   }

   public open fun readFromJSON(json: JsonObject): PokemonState {
      return this;
   }

   public open fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
   }

   public open fun readFromBuffer(buffer: FriendlyByteBuf): PokemonState {
      return this;
   }

   public companion object {
      public final val states: Map<String, Class<out PokemonState>>

      public fun fromBuffer(buffer: FriendlyByteBuf): PokemonState {
         val var10000: Class = this.getStates().get(buffer.m_130277_());
         if (var10000 != null) {
            val var3: PokemonState = var10000.newInstance() as PokemonState;
            if (var3 != null) {
               val var4: PokemonState = var3.readFromBuffer(buffer);
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return new InactivePokemonState();
      }
   }
}
