/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.effectiveName
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
final class ClearPartyCommand {

    private const val NAME = "clearparty"
    private const val PLAYER = "player"

    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val command = Commands.literal(NAME)
             .permission(CobblemonPermissions.CLEAR_PARTY)
             .then(Commands.argument(PLAYER, EntityArgument.players()).executes(::execute))
        dispatcher.register(command)
    }

    private fun execute(context: CommandContext<CommandSourceStack>) : Int {
        val target = EntityArgument.getPlayer(context, "player")
        val party = target.party()

        val pokemonList = party.filterNotNull()
        if (pokemonList.isEmpty()) {
            context.source.sendFailure(commandLang("$NAME.nonethere", target.effectiveName()))
            return 0
        }

        for (pokemon in pokemonList) {
            party.remove(pokemon)
        }

        context.source.sendSuccess({ commandLang("$NAME.cleared", target.effectiveName()) }, true)
        return Command.SINGLE_SUCCESS
    }
}