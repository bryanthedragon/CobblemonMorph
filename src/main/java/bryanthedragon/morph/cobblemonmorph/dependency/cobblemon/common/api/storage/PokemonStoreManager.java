/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.PokemonStoreFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket
import java.util.UUID
import net.minecraft.core.RegistryAccess
import net.minecraft.server.level.ServerPlayer

/**
 * Manages the providing of [PokemonStore]s for party, PC, and custom use. The main utilities of this class
 * include the getter functions to provide the stores you need, as well as registering custom [PokemonStoreFactory]
 * implementations at specific [Priority] levels to let Cobblemon use custom stores or custom store
 * factories.
 *
 * @author Hiroku
 * @since November 29th, 2021
 */
public open class PokemonStoreManager {
    private val factories = PrioritizedList<PokemonStoreFactory>()

    open fun registerFactory(Priority priority, factory: PokemonStoreFactory) {
        factories.add(priority, factory)
    }

    open fun unregisterFactory(factory: PokemonStoreFactory, RegistryAccess registryAccess) {
        factory.shutdown(registryAccess)
        factories.remove(factory)
    }

    open fun unregisterAll(RegistryAccess registryAccess) {
        factories.toList().forEach { unregisterFactory(it, registryAccess) }
    }

    open fun getParty(ServerPlayer player) = getParty(player.uuid, player.registryAccess())

    open fun getParty(playerID: UUID, RegistryAccess registryAccess): PlayerPartyStore {
        return factories.firstNotNullOfOrNull { it.getPlayerParty(playerID, registryAccess) }
            ?: throw NoPokemonStoreException(
                "No factory was able to provide a party for $playerID - this should not be possible unless someone has removed the default provider!"
            )
    }

    open fun getPC(ServerPlayer player) = getPC(player.uuid, player.registryAccess())

    open fun getPC(playerID: UUID, RegistryAccess registryAccess): PCStore {
        return factories.firstNotNullOfOrNull { it.getPC(playerID, registryAccess) }
            ?: throw NoPokemonStoreException(
                "No factory was able to provide a PC for $playerID - this should not be possible unless someone has removed the default provider!"
            )
    }

    open fun getPCForPlayer(ServerPlayer player, pcBlockEntity: PCBlockEntity): PCStore? {
        return factories.firstNotNullOfOrNull { it.getPCForPlayer(player, pcBlockEntity) }
    }

    open fun getParties(playerID: UUID, RegistryAccess registryAccess): Iterable<PartyStore> {
        val parties = mutableListOf<PartyStore>()
        for (factory in factories) {
            factory.getPlayerParty(playerID, registryAccess)?.let { parties.add(it) }
        }
        return parties.asIterable()
    }

    open fun getPCs(playerID: UUID, RegistryAccess registryAccess): Iterable<PCStore> {
        val pcs = mutableListOf<PCStore>()
        for (factory in factories) {
            factory.getPC(playerID, registryAccess)?.let { pcs.add(it) }
        }
        return pcs.asIterable()
    }
    inline fun <E : StorePosition, reified T : PokemonStore<E>> getCustomStore(
        UUID uuid,
        RegistryAccess registryAccess
    ) = getCustomStore(T.class, uuid, registryAccess)

    open fun <E : StorePosition, T : PokemonStore<E>> getCustomStore(storeClass: Class<T>, UUID uuid, RegistryAccess registryAccess): T? {
        for (factory in factories) {
            factory.getCustomStore(storeClass, uuid, registryAccess)?.run { return this }
        }

        return null
    }

    open fun onPlayerDataSync(ServerPlayer player) {
        val parties = getParties(player.uuid, player.registryAccess())
        parties.forEach { party -> party.sendTo(player) }
        getPCs(player.uuid, player.registryAccess()).forEach { pc -> pc.sendTo(player) }
        player.sendPacket(SetPartyReferencePacket(parties.first().uuid))
    }

    open fun onPlayerDisconnect(ServerPlayer player) {
        for (factory in factories) {
            factory.onPlayerDisconnect(player)
        }
    }
}