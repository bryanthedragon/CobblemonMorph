package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.Message
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public object SpawnPokemon {
   private const val ALIAS: String = "pokespawn"
   private const val AT_ALIAS: String = "pokespawnat"
   private const val AT_NAME: String = "spawnpokemonat"
   private final val FAILED_SPAWN_EXCEPTION: SimpleCommandExceptionType
   private final val INVALID_POS_EXCEPTION: SimpleCommandExceptionType
   private const val NAME: String = "spawnpokemon"
   private final val NO_SPECIES_EXCEPTION: SimpleCommandExceptionType
   private const val POSITION: String = "pos"
   private const val PROPERTIES: String = "properties"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      var var10001: LiteralArgumentBuilder = Commands.m_82127_("spawnpokemon");
      val contextPositionCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(SpawnPokemon::register$lambda$0)) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(contextPositionCommand, "pokespawn"));
      var10001 = Commands.m_82127_("spawnpokemonat");
      val argumentPositionCommand: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("pos", Vec3Argument.m_120841_() as ArgumentType)
                  .then(Commands.m_82129_("properties", PokemonPropertiesArgumentType.Companion.properties()).executes(SpawnPokemon::register$lambda$1))
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(argumentPositionCommand, "pokespawnat"));
   }

   private fun execute(context: CommandContext<CommandSourceStack>, pos: Vec3): Int {
      val world: ServerLevel = (context.getSource() as CommandSourceStack).m_81372_();
      if (!Level.m_46741_(Vec3ExtensionsKt.toBlockPos(pos))) {
         val var8: CommandSyntaxException = INVALID_POS_EXCEPTION.create();
         throw var8 as java.lang.Throwable;
      } else {
         val properties: PokemonProperties = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, "properties");
         if (properties.getSpecies() == null) {
            val var7: CommandSyntaxException = NO_SPECIES_EXCEPTION.create();
            throw var7 as java.lang.Throwable;
         } else {
            val pokemonEntity: PokemonEntity = properties.createEntity(world as Level);
            pokemonEntity.m_7678_(pos.f_82479_, pos.f_82480_, pos.f_82481_, pokemonEntity.m_146908_(), pokemonEntity.m_146909_());
            pokemonEntity.m_20088_().m_135381_(PokemonEntity.Companion.getSPAWN_DIRECTION(), pokemonEntity.m_217043_().m_188501_() * 360.0F);
            if (world.m_7967_(pokemonEntity as Entity)) {
               return 1;
            } else {
               val var10000: CommandSyntaxException = FAILED_SPAWN_EXCEPTION.create();
               throw var10000 as java.lang.Throwable;
            }
         }
      }
   }

   @JvmStatic
   fun `register$lambda$0`(`this$0`: SpawnPokemon, context: CommandContext): Int {
      val var10002: Vec3 = (context.getSource() as CommandSourceStack).m_81371_();
      return `this$0`.execute(context, var10002);
   }

   @JvmStatic
   fun `register$lambda$1`(context: CommandContext): Int {
      val var10000: SpawnPokemon = INSTANCE;
      val var10002: Vec3 = Vec3Argument.m_120844_(context, "pos");
      return var10000.execute(context, var10002);
   }

   @JvmStatic
   fun {
      var var10002: MutableComponent = LocalizationUtilsKt.commandLang("spawnpokemon.nospecies");
      NO_SPECIES_EXCEPTION = new SimpleCommandExceptionType(TextKt.red(var10002) as Message);
      var10002 = Component.m_237113_("Invalid position");
      INVALID_POS_EXCEPTION = new SimpleCommandExceptionType(TextKt.red(var10002) as Message);
      var10002 = Component.m_237113_("Unable to spawn at the given position");
      FAILED_SPAWN_EXCEPTION = new SimpleCommandExceptionType(TextKt.red(var10002) as Message);
   }
}
