package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonStoreArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.StoreType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nTestStoreCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestStoreCommand.kt\ncom/cobblemon/mod/common/command/TestStoreCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1774#2,4:50\n*S KotlinDebug\n*F\n+ 1 TestStoreCommand.kt\ncom/cobblemon/mod/common/command/TestStoreCommand\n*L\n46#1:50,4\n*E\n"])
public object TestStoreCommand {
   private const val NAME: String = "teststore"
   private const val PLAYER: String = "player"
   private const val PROPERTIES: String = "properties"
   private const val STORE: String = "store"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("teststore");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getTEST_STORE(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType)
                  .then(
                     Commands.m_82129_("store", PokemonStoreArgumentType.Companion.pokemonStore() as ArgumentType)
                        .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(this::execute))
                  )
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = CommandContextExtensionsKt.player(context, "player");
      val storeType: StoreType = PokemonStoreArgumentType.Companion.pokemonStoreFrom(context, "store");
      val properties: PokemonProperties = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, "properties");
      val var10000: Function1 = storeType.getStoreFetcher();
      val `$this$count$iv`: java.lang.Iterable = var10000.invoke(player) as java.lang.Iterable;
      val var12: Int;
      if (`$this$count$iv` is java.util.Collection && (`$this$count$iv` as java.util.Collection).isEmpty()) {
         var12 = 0;
      } else {
         val `count$iv`: Int = 0;

         for (Object element$iv : $this$count$iv) {
            if (properties.matches(`element$iv` as Pokemon)) {
               if (++`count$iv` < 0) {
                  CollectionsKt.throwCountOverflow();
               }
            }
         }

         var12 = `count$iv`;
      }

      return var12;
   }
}
