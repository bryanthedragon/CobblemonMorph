package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

@SourceDebugExtension(["SMAP\nChangeWalkSpeed.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChangeWalkSpeed.kt\ncom/cobblemon/mod/common/command/ChangeWalkSpeed\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,48:1\n1#2:49\n*E\n"])
public object ChangeWalkSpeed {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("changewalkspeed");
      dispatcher.register(
         ((PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getCHANGE_WALK_SPEED(), false, 2, null) as LiteralArgumentBuilder)
               .then(
                  Commands.m_82129_("pokemon", PokemonArgumentType.Companion.pokemon())
                     .then(Commands.m_82129_("walkSpeed", FloatArgumentType.floatArg() as ArgumentType).executes(this::execute))
               ) as LiteralArgumentBuilder)
            .executes(this::execute) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val pkm: Species = PokemonArgumentType.Companion.getPokemon(context, "pokemon");
      pkm.getBehaviour().getMoving().getWalk().setWalkSpeed(FloatArgumentType.getFloat(context, "walkSpeed"));
      pkm.getForms().clear();
      val var10000: java.util.List = pkm.getForms();
      val var4: FormData = new FormData(
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         -1,
         3,
         null
      );
      var4.initialize(pkm);
      var10000.add(var4);
      return 1;
   }
}
