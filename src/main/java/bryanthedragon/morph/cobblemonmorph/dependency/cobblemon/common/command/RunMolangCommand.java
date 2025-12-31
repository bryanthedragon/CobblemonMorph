/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.commandLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.entity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.player
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.string
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
public final class RunMolangCommand {
    private const val NAME = "runmolang"
    private const val MOLANG = "molang"
    private const val PLAYER = "player"
    private const val NPC = "npc"
    private const val POKEMON = "pokemon"

    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal(NAME)
            .permission(CobblemonPermissions.RUN_MOLANG)
            .then(
                argument(MOLANG, StringArgumentType.string())
                    .executes { execute(it, it.string(MOLANG), null, null, null) }
                    .then(argument(PLAYER, EntityArgument.player())
                        .executes { execute(it, it.string(MOLANG), it.player(PLAYER), null, null) }
                        .then(argument(NPC, EntityArgument.entity())
                            .executes { execute(it, it.string(MOLANG), it.player(PLAYER), it.entity(
                                NPC
                            ), null) }
                            .then(argument(POKEMON, EntityArgument.entity())
                                .executes { execute(it, it.string(MOLANG), it.player(PLAYER), it.entity(
                                    NPC
                                ), it.entity(POKEMON)) }
                            )
                        )
                        .then(argument(POKEMON, EntityArgument.entity())
                            .executes { execute(it, it.string(MOLANG), it.player(PLAYER), null, it.entity(
                                POKEMON
                            )) }
                        )
                    )
                    .then(argument(NPC, EntityArgument.entity())
                        .executes { execute(it, it.string(MOLANG), null, it.entity(NPC), null) }
                        .then(argument(POKEMON, EntityArgument.entity())
                            .executes { execute(it, it.string(MOLANG), null, it.entity(NPC), it.entity(
                                POKEMON
                            )) }
                        )
                    )
                    .then(argument(POKEMON, StringArgumentType.string())
                        .executes { execute(it, it.string(MOLANG), null, null, it.entity(POKEMON)) }
                    )
            )
        )
    }

    private fun execute(context: CommandContext<CommandSourceStack>, moString lang, ServerPlayer player?, npc: Entity?, pokemon: Entity? = null): Int {
        try {
            val runtime = MoLangRuntime().setup()
            val entity = context.source.entity

            (npc as? NPCEntity)?.let { runtime.withQueryValue("npc", npc.struct) }
            (pokemon as? PokemonEntity)?.let { runtime.withQueryValue("pokemon", pokemon.struct) }
            player?.let { runtime.withQueryValue("player", player.asMoLangValue()) }
            entity?.let { runtime.withQueryValue("entity", entity.asMostSpecificMoLangValue()) }

            val out = molang.asExpressionLike().resolve(runtime)
            context.source.sendSuccess({ commandLang("molang.out", out.asString()) }, true)

        } catch (Exception e) {
            e.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }
}