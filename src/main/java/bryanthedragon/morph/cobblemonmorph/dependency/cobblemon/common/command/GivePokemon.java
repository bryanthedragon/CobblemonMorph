/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.alias
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.player
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer
final class GivePokemon {

    private const val NAME = "givepokemon"
    private const val ALIAS = "pokegive"
    private const val NAME_OTHER = "${NAME}other"
    private const val ALIAS_OTHER = "${ALIAS}other"
    private const val PLAYER = "player"
    private const val PROPERTIES = "properties"

    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val selfCommand = dispatcher.register(literal(NAME)
            .permission(CobblemonPermissions.GIVE_POKEMON_SELF)
            .then(argument(PROPERTIES, PokemonPropertiesArgumentType.properties())
                .executes { execute(it, it.source.playerOrException) }))
        dispatcher.register(selfCommand.alias(ALIAS))

        val otherCommand = dispatcher.register(literal(NAME_OTHER)
            .permission(CobblemonPermissions.GIVE_POKEMON_OTHER)
            .then(argument(PLAYER, EntityArgument.player())
                .then(argument(PROPERTIES, PokemonPropertiesArgumentType.properties())
                    .executes { execute(it, it.player()) })))
        dispatcher.register(otherCommand.alias(ALIAS_OTHER))
    }

    private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
        try {
            val pokemonProperties = PokemonPropertiesArgumentType.getPokemonProperties(context, PROPERTIES)
            if (pokemonProperties.species == null) {
                player.sendSystemMessage(commandLang("${NAME}.nospecies").red())
                return Command.SINGLE_SUCCESS
            }
            val pokemon = pokemonProperties.create()
            val party = Cobblemon.storage.getParty(player)
            party.add(pokemon)
            context.source.sendSuccess({ commandLang("${NAME}.give", pokemon.species.translatedName, player.name) }, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }
}