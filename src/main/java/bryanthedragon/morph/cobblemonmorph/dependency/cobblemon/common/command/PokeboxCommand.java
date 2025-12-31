/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer

/**
 * Send Pokemon to the PC.
 *
 * Two similar commands with the syntax:
 *
 * `/pokebox <player> <slot> [box]` Sends a single Pokemon to the PC, allowing for a specific box to be selected.
 *  If a box is selected and is full, no action will take place.
 *
 * `/pokeboxall <player> [box]` Sends all party pokemon to the PC, allowing for a specific box to be selected.
 *  If a box is selected and would not beable to house all of the party Pokemon, no action will take place.
 */
public final class PokeboxCommand {
    private val BOX_DOES_NOT_EXIST = { boxNo: Int -> commandLang("pokebox.box_does_not_exist", boxNo) }
    private val BOX_IS_FULL_EXCEPTION = { boxNo: Int -> commandLang("pokebox.box_is_full", boxNo) }
    private val STORAGE_IS_FULL_EXCEPTION = commandLang("pokebox.storage_is_full")
    private val LAST_POKE_MESSAGE = commandLang("pokebox.last_pokemon")

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal("pokebox")
            .permission(CobblemonPermissions.POKEBOX)
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("slot", PartySlotArgumentType.partySlot())
                    .then(Commands.argument("box", IntegerArgumentType.integer(1))
                        .executes { context ->
                            val player = context.player()
                            val pokemon = PartySlotArgumentType.getPokemonOf(context, "slot", player)
                            val box = IntegerArgumentType.getInteger(context, "box")
                            execute(context, player, listOf(pokemon), box)
                        })
                    .executes { context ->
                        val player = context.player()
                        val pokemon = PartySlotArgumentType.getPokemonOf(context, "slot", player)
                        execute(context, player, listOf(pokemon))
                    })
            )
        )

        dispatcher.register(literal("pokeboxall")
            .permission(CobblemonPermissions.POKEBOX)
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("box", IntegerArgumentType.integer(1))
                    .executes { context ->
                        val player = context.player()
                        val box = IntegerArgumentType.getInteger(context, "box")
                        execute(context, player, player.party().toList(), box)
                    })
                .executes { context ->
                    val player = context.player()
                    execute(context, player, player.party().toList())
                }))
    }

    private fun execute(
        context: CommandContext<CommandSourceStack>,
        ServerPlayer player,
        pokemons: Collection<Pokemon>,
        box: Int? = null,
    ): Int {
        val playerPc = player.pc()
        val playerParty = player.party()

        // If specifying a box, first check that the box exists and can sufficiently hold all the pokemon to be moved.
        if (box != null) {
            if (playerPc.boxes.size < box) {
                throw SimpleCommandExceptionType(BOX_DOES_NOT_EXIST(box).red()).create()
            }

            val pcBox = playerPc.boxes[box - 1]

            if (pcBox.unoccupiedSlots < pokemons.size) {
                throw SimpleCommandExceptionType(BOX_IS_FULL_EXCEPTION(box).red()).create()
            }
        }

        // Operate in reverse so that the party "lead" pokemon would be kept
        pokemons.reversed().forEach { pokemon ->
            if (ServerSettings.preventCompletePartyDeposit && playerParty.occupied() == 1) {
                context.source.sendSuccess({ LAST_POKE_MESSAGE.red() }, false)
                return pokemons.size - 1
            }

            // If PCStore and PCBox both implemented PokemonStore we could make this code a lot cleaner via the same interface
            val pcPosition = if (box == null) {
                playerPc.getFirstAvailablePosition()
                    ?: throw SimpleCommandExceptionType(STORAGE_IS_FULL_EXCEPTION.red()).create()

            } else {
                val pcBox = playerPc.boxes.get(box - 1)
                pcBox.getFirstAvailablePosition()
                    ?: throw SimpleCommandExceptionType(BOX_IS_FULL_EXCEPTION(box).red()).create()

            }

            playerParty.remove(pokemon)
            playerPc[pcPosition] = pokemon

            // Let the client(s) know about the change to party
            playerParty.sendPacketToObservers(
                RemoveClientPokemonPacket(player.party(), pokemon.uuid)
            )
        }

        // Let the call know how many Pokemon were moved to the PC
        return pokemons.size
    }
}
