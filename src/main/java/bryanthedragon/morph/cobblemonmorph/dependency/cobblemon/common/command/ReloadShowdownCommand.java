/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HeldItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
public final class ReloadShowdownCommand {

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val command = Commands.literal("reloadshowdown")
            .requires { it.hasPermission(4) }
            .executes(::execute)
        dispatcher.register(command)
    }

    private fun execute(context: CommandContext<CommandSourceStack>): Int {
        try {
            ShowdownService.service.closeConnection()
            ShowdownService.service.openConnection()
            ShowdownService.service.resetAllRegistries()
            ShowdownService.service.sendRegistryData(Abilities.abilityScripts, "ability")
            ShowdownService.service.sendRegistryData(BagItems.bagItemsScripts, "bagItem")
            ShowdownService.service.sendRegistryData(HeldItems.heldItemsScripts, "heldItem")
            ShowdownService.service.sendRegistryData(Moves.moveScripts, "move")
            ShowdownService.service.sendRegistryData(PokemonSpecies.allShowdownSpecies(), "species")
            context.source.sendSystemMessage(Component.literal("Reloaded showdown"))
        } catch (Exception e) {
            e.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }

}