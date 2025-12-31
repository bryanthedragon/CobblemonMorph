/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems.bagItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.ItemStack
import java.io.File

/**
 * A registry for [BagItem]s that could be parsed from [ItemStack]s. This registry is used as the resource loading
 * mechanism for bag item scripts, but add to [bagItems] to map from stacks to [BagItem]s.
 *
 * @author Hiroku
 * @since June 26th, 2023
 */
public final class BagItems : DataRegistry {
    override val id = cobblemonResource("bag_items")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<BagItems>()
    override fun sync(ServerPlayer player) {}

    val bagItems = PrioritizedList<BagItemLike>()
    internal val bagItemsScripts = mutableMapOf<String, String>() // itemId to JavaScript

    @JvmStatic
    fun getConvertibleForStack(ItemStack stack): BagItemLike? {
        return bagItems.firstOrNull { it.getBagItem(stack) != null }
    }

    override fun reload(ResourceManager manager) {
        this.bagItemsScripts.clear()
        ShowdownService.service.resetRegistryData("bagItem")
        manager.listResources("bag_items") { it.path.endsWith(".js") }.forEach { (identifier, resource) ->
            resource.open().use { stream ->
                stream.bufferedReader().use { reader ->
                    val resolvedIdentifier = ResourceLocation.fromNamespaceAndPath(identifier.namespace, File(identifier.path).nameWithoutExtension)
                    val js = reader.readText()
                    bagItemsScripts[resolvedIdentifier.path] = js
                }
            }
        }
        ShowdownService.service.sendRegistryData(bagItemsScripts, "bagItem")

        this.observable.emit(this)
    }
}