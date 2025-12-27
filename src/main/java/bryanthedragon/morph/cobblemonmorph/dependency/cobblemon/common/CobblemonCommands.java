package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.BedrockParticleCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ChangeScaleAndSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ChangeWalkSpeed
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.CheckSpawnsCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClearPCCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClearPartyCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClickTextCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.CobblemonInfoCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.FriendshipCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GetNBT
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GiveAllPokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GivePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.HealPokemonCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.HeldItemCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.LevelUp
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.OpenDialogueCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.OpenStarterScreenCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PcCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokeboxCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokemonEditCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokemonRestartCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.QueryLearnsetCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ReloadShowdownCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnAllPokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnPokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnPokemonFromPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.StopBattleCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TakePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TeachCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestPartySlotCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestPcSlotCommand
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestStoreCommand

import com.mojang.brigadier.CommandDispatcher

import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.command.CommandManager.RegistrationEnvironment

public object CobblemonCommands {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>, registry: CommandBuildContext, selection: RegistrationEnvironment) {
      SpawnPokemon.INSTANCE.register(dispatcher);
      GivePokemon.INSTANCE.register(dispatcher);
      TakePokemon.INSTANCE.register(dispatcher);
      ChangeScaleAndSize.INSTANCE.register(dispatcher);
      ChangeWalkSpeed.INSTANCE.register(dispatcher);
      TestCommand.INSTANCE.register(dispatcher);
      ReloadShowdownCommand.INSTANCE.register(dispatcher);
      ClickTextCommand.INSTANCE.register(dispatcher);
      PokemonEditCommand.INSTANCE.register(dispatcher);
      TeachCommand.INSTANCE.register(dispatcher);
      LevelUp.INSTANCE.register(dispatcher);
      HealPokemonCommand.INSTANCE.register(dispatcher);
      StopBattleCommand.INSTANCE.register(dispatcher);
      CheckSpawnsCommand.INSTANCE.register(dispatcher);
      GetNBT.INSTANCE.register(dispatcher);
      OpenStarterScreenCommand.INSTANCE.register(dispatcher);
      SpawnAllPokemon.INSTANCE.register(dispatcher);
      FriendshipCommand.INSTANCE.register(dispatcher);
      GiveAllPokemon.INSTANCE.register(dispatcher);
      HeldItemCommand.INSTANCE.register(dispatcher, registry);
      PcCommand.INSTANCE.register(dispatcher);
      SpawnPokemonFromPool.INSTANCE.register(dispatcher);
      PokeboxCommand.INSTANCE.register(dispatcher);
      TestStoreCommand.INSTANCE.register(dispatcher);
      QueryLearnsetCommand.INSTANCE.register(dispatcher);
      TestPcSlotCommand.INSTANCE.register(dispatcher);
      TestPartySlotCommand.INSTANCE.register(dispatcher);
      ClearPartyCommand.INSTANCE.register(dispatcher);
      ClearPCCommand.INSTANCE.register(dispatcher);
      PokemonRestartCommand.INSTANCE.register(dispatcher);
      BedrockParticleCommand.INSTANCE.register(dispatcher);
      OpenDialogueCommand.INSTANCE.register(dispatcher);
      CobblemonInfoCommand.INSTANCE.register(dispatcher);
   }
}
