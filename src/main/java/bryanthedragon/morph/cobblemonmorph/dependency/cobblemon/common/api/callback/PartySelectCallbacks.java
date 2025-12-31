/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import java.util.UUID
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

/**
 * Used for opening party Pokémon selection screens for players and handling their choice. Currently
 * only supports up to 6 Pokémon to select from.
 *
 * @author Hiroku
 * @since July 1st, 2023
 */
public final class PartySelectCallbacks {
    val callbacks = mutableMapOf<UUID, PartySelectCallback>()

    @JvmOverloads
    fun create(
        ServerPlayer player,
        Component title = lang("ui.party"),
        pokemon: List<PartySelectPokemonDTO>,
        cancel: (ServerPlayer) -> Unit = {},
        handler: (ServerPlayer, Int index) -> Unit
    ) {
        val callback = PartySelectCallback(
            shownPokemon = pokemon,
            cancel = cancel,
            handler = handler
        )

        callbacks[player.uuid] = callback

        player.sendPacket(
            OpenPartyCallbackPacket(
                uuid = callback.uuid,
                title = title.copy(),
                pokemon = pokemon
            )
        )
    }

    @JvmOverloads
    fun createBattleSelect(
        ServerPlayer player,
        Component title = lang("ui.party"),
        pokemon: List<BattlePokemon>,
        canSelect: (BattlePokemon) -> Boolean,
        cancel: (ServerPlayer) -> Unit = {},
        handler: (BattlePokemon) -> Unit
    ) = create(
        player = player,
        title = title,
        cancel = cancel,
        pokemon = pokemon.map { pk -> PartySelectPokemonDTO(pk.effectedPokemon).also { it.enabled = canSelect(pk) } }
    ) { _, index -> handler(pokemon[index]) }

    @JvmOverloads
    fun createFromPokemon(
        ServerPlayer player,
        Component title = lang("ui.party"),
        pokemon: List<Pokemon>,
        canSelect: (Pokemon) -> Boolean,
        cancel: (ServerPlayer) -> Unit = {},
        handler: (Pokemon) -> Unit
    ) = create(
        player = player,
        title = title,
        cancel = cancel,
        pokemon = pokemon.map { pk -> PartySelectPokemonDTO(pk).also { it.enabled = canSelect(pk) } }
    ) { _, index -> handler(pokemon[index]) }

    fun handleCancelled(ServerPlayer player, UUID uuid) {
        val callback = callbacks[player.uuid] ?: return
        if (callback.uuid != uuid) {
            return
        }
        callbacks.remove(player.uuid)
        callback.cancel(player)
    }

    fun handleCallback(ServerPlayer player, UUID uuid, Int index) {
        val callback = callbacks[player.uuid] ?: return
        callbacks.remove(player.uuid)
        if (callback.uuid != uuid) {
            Cobblemon.LOGGER.warn("A party select callback ran but with a mismatching UUID from ${player.gameProfile.name}. Hacking attempts?")
        } else if (index >= callback.shownPokemon.size) {
            Cobblemon.LOGGER.warn("${player.gameProfile.name} used party select callback with an out of bounds index. Hacking attempts? Tried $index, Pokémon list size was ${callback.shownPokemon.size}")
        } else if (!callback.shownPokemon[index].enabled) {
            Cobblemon.LOGGER.warn("${player.gameProfile.name} used party select callback with a Pokémon that is not enabled. Hacking attempts?")
        } else {
            callback.handler(player, index)
        }
    }
}

public class PartySelectCallback(
    val UUID uuid = UUID.randomUUID(),
    val shownPokemon: List<PartySelectPokemonDTO>,
    val cancel: (ServerPlayer) -> Unit = {},
    val handler: (ServerPlayer, Int index) -> Unit
)

open class PartySelectPokemonDTO(
    val pokemonProperties: PokemonProperties,
    val aspects: Set<String>,
    val heldItem: ItemStack = ItemStack.EMPTY,
    var currentHealth: Int,
    var maxHealth: Int,
    var Boolean enabled,
    var hoverText: List<Component> = emptyList()
) {
    @JvmOverloads
    constructor(Pokemon pokemon, Boolean enabled = true, hoverText: List<Component> = emptyList()): this(
        pokemonProperties = pokemon.createPokemonProperties(
            PokemonPropertyExtractor.SPECIES,
            PokemonPropertyExtractor.LEVEL,
            PokemonPropertyExtractor.NICKNAME,
            PokemonPropertyExtractor.POKEBALL,
            PokemonPropertyExtractor.STATUS,
        ),
        aspects = pokemon.aspects,
        heldItem = pokemon.heldItemNoCopy(),
        currentHealth = pokemon.currentHealth,
        maxHealth = pokemon.maxHealth,
        enabled = enabled,
        hoverText = hoverText
    )

    constructor(RegistryFriendlyByteBuf buffer): this(
        pokemonProperties = PokemonProperties().loadFromNBT(buffer.readNbt() as CompoundTag, buffer.registryAccess()),
        aspects = buffer.readList { it.readString() }.toSet(),
        heldItem = buffer.readItemStack(),
        currentHealth = buffer.readInt(),
        maxHealth = buffer.readInt(),
        enabled = buffer.readBoolean(),
        hoverText = buffer.readList { buffer.readText() }
    )

    fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeNbt(pokemonProperties.saveToNBT(buffer.registryAccess()))
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
        buffer.writeItemStack(heldItem)
        buffer.writeInt(currentHealth)
        buffer.writeInt(maxHealth)
        buffer.writeBoolean(enabled)
        buffer.writeCollection(hoverText) { _, text -> buffer.writeText(text) }
    }
}