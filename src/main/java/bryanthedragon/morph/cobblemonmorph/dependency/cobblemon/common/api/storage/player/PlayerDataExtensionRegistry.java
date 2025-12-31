/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player
public final class PlayerDataExtensionRegistry {

    private val allExtensions = mutableMapOf<String, Class<out PlayerDataExtension>>()

    fun register(String name, extension: Class<out PlayerDataExtension>, overwrite: Boolean = false): Boolean {
        if (allExtensions.contains(name) && !overwrite)
            return false
        allExtensions[name] = extension
        return true
    }

    fun get(String name) = allExtensions[name]
    fun getOrException(String name) = get(name)
        ?: throw IllegalStateException("PlayerDataExtension with name $name was not found.")
    fun count() = allExtensions.size
    fun remove(String name) = allExtensions.remove(name)
    fun contains(String name) = allExtensions.containsKey(name)

}