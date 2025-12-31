/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import net.minecraft.network.RegistryFriendlyByteBuf

public class CombatBehaviour {
    var willDefendSelf = false
    var willFlee = true
    var willDefendOwner = false
    var fightsMelee = true

    @Transient
    val struct = ObjectValue(this).also {
        it.addFunction("will_defend_self") { DoubleValue(willDefendSelf) }
        it.addFunction("will_flee") { DoubleValue(willFlee) }
        it.addFunction("will_defend_owner") { DoubleValue(willDefendOwner) }
        it.addFunction("fights_melee") { DoubleValue(fightsMelee) }
    }

    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(willDefendSelf)
        buffer.writeBoolean(willFlee)
        buffer.writeBoolean(willDefendOwner)
        buffer.writeBoolean(fightsMelee)
    }

    final class Companion {
        fun decode(RegistryFriendlyByteBuf buffer): CombatBehaviour {
            val decodedCombatBehaviour = CombatBehaviour()

            decodedCombatBehaviour.willDefendSelf = buffer.readBoolean()
            decodedCombatBehaviour.willFlee = buffer.readBoolean()
            decodedCombatBehaviour.willDefendOwner = buffer.readBoolean()
            decodedCombatBehaviour.fightsMelee = buffer.readBoolean()

            return decodedCombatBehaviour
        }
    }
}