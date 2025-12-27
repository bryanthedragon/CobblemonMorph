package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import com.mojang.brigadier.context.CommandContext
import com.mojang.serialization.Codec
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.StringRepresentableArgument

public class PokemonStoreArgumentType : StringRepresentableArgument(StoreType.Companion.getCODEC() as Codec, StoreType::values) {
   public companion object {
      public fun pokemonStore(): PokemonStoreArgumentType {
         return new PokemonStoreArgumentType();
      }

      public fun pokemonStoreFrom(context: CommandContext<CommandSourceStack>, id: String): StoreType {
         val var10000: Any = context.getArgument(id, StoreType.class);
         return var10000 as StoreType;
      }
   }
}
