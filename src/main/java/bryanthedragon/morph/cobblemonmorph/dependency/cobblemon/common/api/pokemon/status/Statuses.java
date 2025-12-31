/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.BurnStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.FrozenStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.ParalysisStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonBadlyStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.SleepStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.ConfuseStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.AttractStatus
import net.minecraft.resources.ResourceLocation

/**
 * Main API point for Statuses
 * Get or register Statuses
 *
 * NOTE: May seem weird to have so many things called volatile statuses but the package is called nonpersistent.
 * Its because volatile is a reserved keyword in Java. Cant use it in a package name
 *
 * @author Deltric
 */
public final class Statuses {
    private val persistentStatuses = mutableListOf<Status>()
    private val volatileStatuses = mutableListOf<Status>()
    private val allStatuses = mutableListOf<Status>()

    @JvmField
    val POISON = registerStatus(PoisonStatus())
    @JvmField
    val POISON_BADLY = registerStatus(PoisonBadlyStatus())
    @JvmField
    val PARALYSIS = registerStatus(ParalysisStatus())
    @JvmField
    val SLEEP = registerStatus(SleepStatus())
    @JvmField
    val FROZEN = registerStatus(FrozenStatus())
    @JvmField
    val BURN = registerStatus(BurnStatus())
    @JvmField
    val ATTRACT = registerStatus(AttractStatus())
    @JvmField
    val CONFUSE = registerStatus(ConfuseStatus())

    @JvmStatic
    fun <T: Status> registerStatus(status: T) : T {
        if (status is PersistentStatus) {
            persistentStatuses.add(status)
        }
        else if (status is VolatileStatus) {
            volatileStatuses.add(status)
        }
        allStatuses.add(status)
        return status
    }

    @JvmStatic
    fun getStatus(name: ResourceLocation) = allStatuses.find { status -> status.name == name }
    @JvmStatic
    fun getStatus(showdownName: String) = allStatuses.find { it.showdownName == showdownName }
    @JvmStatic
    fun getPersistentStatuses() = persistentStatuses
}