/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.AreaSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.alias
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.effectiveName
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.spawner
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.literal

/**
 * Spawn Pokemon From Surrounding Pool
 *
 * `/spawnpokemonfrompool [amount]` or the alias `/forcespawn [amount]`
 *
 * This command can fail if the randomly selected spawn region has no possible [AreaSpawnablePosition]. For example if
 *   you are flying in the air
 */
public final class SpawnPokemonFromPool {
    const val NAME = "spawnpokemonfrompool"
    const val ALIAS = "forcespawn"

    private val UNABLE_TO_SPAWN = commandLang("spawnpokemonfrompool.unable_to_spawn")

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val spawnPokemonFromPoolCommand = dispatcher.register(literal(NAME)
            .permission(CobblemonPermissions.SPAWN_POKEMON)
            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                .executes { context -> execute(context, IntegerArgumentType.getInteger(context, "amount")) }
            )
            .executes { context -> execute(context, 1) }
        )

        dispatcher.register(spawnPokemonFromPoolCommand.alias(ALIAS))
    }

    private fun execute(context: CommandContext<CommandSourceStack>, amount: Int): Int {
        val player = context.source.playerOrException
        val spawner = player.spawner

        var spawnsTriggered = 0

        repeat(times = amount) {
            val spawnCause = SpawnCause(spawner = spawner, entity = player)
            val results = spawner.runForArea(spawner.getZoneInput(spawnCause) ?: return@repeat, maxSpawns = 1)
            if (results.isEmpty()) {
                player.sendSystemMessage(UNABLE_TO_SPAWN.red())
                return@repeat
            }

            results.forEach {
                if (it is EntitySpawnResult) {
                    for (entity in it.entities) {
                        spawnsTriggered++
                        player.sendSystemMessage(commandLang("spawnpokemonfrompool", entity.effectiveName()).green())
                    }
                }
            }
        }

        return spawnsTriggered
    }
}