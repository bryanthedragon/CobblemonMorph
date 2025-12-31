/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Mark
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Marks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import java.util.concurrent.CompletableFuture

public class MarkArgumentType: ArgumentType<Mark> {

    override fun parse(reader: StringReader): Mark {
        try {
            return reader.asIdentifierDefaultingNamespace().let { Marks.getByIdentifier(it) } ?: throw Exception()
        } catch (Exception e) {
            throw SimpleCommandExceptionType(INVALID_MARK).createWithContext(reader)
        }
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return SharedSuggestionProvider.suggest(Marks.identifiers(), builder)
    }

    override fun getExamples() = EXAMPLES

    final class Companion {

        val EXAMPLES: List<String> = listOf("cobblemon:ribbon_event")
        val INVALID_MARK: MutableComponent = Component.translatable("cobblemon.command.mark.invalid")

        fun mark() = MarkArgumentType()

        fun <S> getMark(context: CommandContext<S>, String name): Mark {
            return context.getArgument(name, Mark.class)
        }
    }
}
