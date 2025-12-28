/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.datafixer

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.mojang.datafixers.DSL.TypeReference
import net.minecraft.util.datafix.fixes.References
final class CobblemonTypeReferences {

    // If we ever want to use vanilla types we can simply add them here too
    private val types = hashSetOf<TypeReference>(
        References.ENTITY
    )

    @JvmStatic
    val POKEMON = this.create("pokemon")

    fun types(): Set<TypeReference> = this.types

    private fun create(name: String): TypeReference {
        val identifier = cobblemonResource(name)
        val reference = References.reference(identifier.toString())
        this.types += reference
        return reference
    }

}