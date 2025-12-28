/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.SpectateBattleHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
final class SpectateBattleCommand {
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val command = literal("spectateBattle")
            .permission(CobblemonPermissions.SPECTATE_BATTLE)
            .then(argument("player", EntityArgument.player())
                .executes(::execute))

        dispatcher.register(command)
    }

    private fun execute(context: CommandContext<CommandSourceStack>): Int {
        val player = context.source.playerOrException

        val target = EntityArgument.getPlayer(context, "player")

        SpectateBattleHandler.spectateBattle(target, player)

        return Command.SINGLE_SUCCESS
    }
}