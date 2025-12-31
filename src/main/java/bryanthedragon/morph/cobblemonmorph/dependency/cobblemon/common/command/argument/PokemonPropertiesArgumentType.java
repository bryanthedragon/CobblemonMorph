/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.SharedSuggestionProvider
import java.util.concurrent.CompletableFuture

public class PokemonPropertiesArgumentType: ArgumentType<PokemonProperties> {

    final class Companion {
        val EXAMPLES: List<String> = listOf("eevee")
        private val ASSIGNER = "="

        fun properties() = PokemonPropertiesArgumentType()

        fun <S> getPokemonProperties(context: CommandContext<S>, String name): PokemonProperties {
            return context.getArgument(name, PokemonProperties.class)
        }
    }

    override fun parse(reader: StringReader): PokemonProperties {
        val properties = reader.remaining
        reader.cursor = reader.totalLength
        return PokemonProperties.parse(properties)
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val sections = builder.remainingLowerCase.split(" ")
        if (sections.isEmpty())
            return this.suggestSpeciesAndPropertyKeys(builder)
        val currentSection = sections.last()
        /**
         * We already have a property defined and a potential value, let's try to suggest values based on provided [CustomPokemonPropertyType.examples]
         */
        if (currentSection.contains(ASSIGNER)) {
            val propertyKey = currentSection.substringBefore(ASSIGNER)
            val currentValue = currentSection.substringAfter(ASSIGNER)
            return PropertiesCompletionProvider.suggestValues(propertyKey, currentValue, builder)
        }
        // We will always assume a species identifier has the priority as the first command argument as that's the most traditional approach as such lets provide property keys for the suggestion
        else if (sections.size >= 2) {
            val usedKeys = sections.filter { it.contains("=") }.map { it.substringBefore("=") }.toSet()
            return PropertiesCompletionProvider.suggestKeys(currentSection, usedKeys, builder)
        }
        return this.suggestSpeciesAndPropertyKeys(builder)
    }

    private fun suggestSpeciesAndPropertyKeys(builder: SuggestionsBuilder) = SharedSuggestionProvider.suggest(this.collectSpeciesIdentifiers() + PropertiesCompletionProvider.keys(), builder)

    private fun collectSpeciesIdentifiers() = PokemonSpecies.species.map { if (it.resourceIdentifier.namespace == Cobblemon.MODID) it.resourceIdentifier.path else it.resourceIdentifier.toString() }

    override fun getExamples() = EXAMPLES
}