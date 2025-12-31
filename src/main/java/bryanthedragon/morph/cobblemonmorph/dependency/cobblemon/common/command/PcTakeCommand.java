/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.POKEMON_PER_BOX
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.server.level.ServerPlayer
public final class PcTakeCommand {
    private val IN_BATTLE_EXCEPTION = SimpleCommandExceptionType(lang("pc.inbattle").red())
    
    fun register(dispatcher : CommandDispatcher<CommandSourceStack>) {
        val command = Commands.literal("pctake")
            .permission(CobblemonPermissions.TAKE_POKEMON)
            .then(
                Commands.argument("player", EntityArgument.player())
                    .then(
                        Commands.argument("box", IntegerArgumentType.integer(1))
                            .then(
                                Commands.argument("slot", IntegerArgumentType.integer(1, 30))
                                    .executes(::execute)
                            )
                    )
            )

        dispatcher.register(command)
    }

    private fun execute(context: CommandContext<CommandSourceStack>) : Int {
        val executor = context.source.entity as? ServerPlayer
        val target = EntityArgument.getPlayer(context, "player")
        return executeWithTarget(context, target, executor)
    }

    private fun executeWithTarget(context: CommandContext<CommandSourceStack>, target: ServerPlayer, executor: ServerPlayer?) : Int {
        try {
            if (target.isInBattle()) {
                throw IN_BATTLE_EXCEPTION.create()
            }
            
            val box = IntegerArgumentType.getInteger(context, "box")
            val slot = IntegerArgumentType.getInteger(context, "slot")
            val pc = target.pc()
            
            if (box > POKEMON_PER_BOX) {
                context.source.sendFailure(commandLang("pctake.too_many_boxes", POKEMON_PER_BOX))
                return 0
            }
            
            val boxIndex = box - 1
            val slotIndex = slot - 1
            
            if (slotIndex < 0 || slotIndex >= POKEMON_PER_BOX) {
                context.source.sendFailure(commandLang("pctake.invalid_slot"))
                return 0
            }
            
            val pokemon = pc.boxes[boxIndex][slotIndex]
            if (pokemon == null) {
                context.source.sendFailure(commandLang("pctake.no_pokemon", box, slot))
                return 0
            }

            pc.remove(PCPosition(boxIndex, slotIndex))
            
            if (executor != null && executor != target) {
                val toParty = executor.party()
                toParty.add(pokemon)
                context.source.sendSuccess({ commandLang("pctake.taken_other", pokemon.species.name, target.name.string) }, true)
                return Command.SINGLE_SUCCESS
            }
            
            context.source.sendSuccess({ commandLang("pctake.removed", pokemon.species.name, box, slot) }, true)
            return Command.SINGLE_SUCCESS
        } catch (Exception e) {
            e.printStackTrace()
        }
        return Command.SINGLE_SUCCESS
    }
}