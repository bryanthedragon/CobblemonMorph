/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer
public final class FriendshipCommand {

    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(Commands.literal("friendship")
            .permission(CobblemonPermissions.FRIENDSHIP)
            .then(
                Commands.argument("slot", PartySlotArgumentType.partySlot())
                    .executes { execute(it.source, it.source.playerOrException, PartySlotArgumentType.getPokemon(it, "slot")) }
            ))
    }

    private fun execute(source: CommandSourceStack, target: ServerPlayer, Pokemon pokemon) : Int {
        source.sendSuccess({ commandLang("friendship", pokemon.getDisplayName(), pokemon.friendship) }, true)
        return Command.SINGLE_SUCCESS
    }

}