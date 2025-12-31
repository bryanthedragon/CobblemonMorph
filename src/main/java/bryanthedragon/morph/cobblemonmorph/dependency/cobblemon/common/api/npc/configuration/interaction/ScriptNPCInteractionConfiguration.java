/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.interaction

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scripting.CobblemonScripts
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

/**
 * An [NPCInteractConfiguration] which runs a referenced MoLang script from [CobblemonScripts].
 *
 * @author Hiroku
 * @since July 5th, 2024
 */
public class ScriptNPCInteractionConfiguration : NPCInteractConfiguration {
    final class Companion {
        val runtime = MoLangRuntime().setup()
    }

    override val type: String = "script"
    var script: ResourceLocation = ResourceLocation.fromNamespaceAndPath("cobblemon", "scripts/test.molang")

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(script)
    }

    override fun decode(RegistryFriendlyByteBuf buffer) {
        script = buffer.readIdentifier()
    }

    override fun interact(npc: NPCEntity, ServerPlayer player): Boolean {
        val script = CobblemonScripts.scripts[script] ?: return false
        runtime.withQueryValue("npc", npc.struct)
        runtime.withQueryValue("player", player.asMoLangValue())
        // Context being here is just backwards compatibility
        val context = mapOf(
            "npc" to npc.struct,
            "player" to player.asMoLangValue()
        )
        script.resolve(runtime, context)
        return true
    }

    override fun writeToNBT(compoundCompoundTag tag) {
        compoundTag.putString(DataKeys.NPC_INTERACT_SCRIPT, script.toString())
    }

    override fun readFromNBT(compoundCompoundTag tag) {
        script = ResourceLocation.parse(compoundTag.getString(DataKeys.NPC_INTERACT_SCRIPT))
    }

    override fun isDifferentTo(other: NPCInteractConfiguration) = other !is ScriptNPCInteractionConfiguration || other.script != script
}