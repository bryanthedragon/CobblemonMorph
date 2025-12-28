/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClasses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCBattleConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import io.netty.buffer.ByteBuf
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

class NPCConfigurationDTO : Encodable, Decodable {
    var npcUUID: UUID = UUID.randomUUID()
    var npcName: MutableComponent = "".text()
    var npcClass: ResourceLocation = cobblemonResource("default")
    var resourceIdentifier: ResourceLocation = npcClass
    var battle: NPCBattleConfiguration? = null
    var interactionInherited: Boolean = false
    var interaction: NPCInteractConfiguration? = null
    var aspects: MutableSet<String> = mutableSetOf()
    var behaviours: MutableSet<ResourceLocation> = mutableSetOf()
    var registeredVariables: MutableList<MoLangConfigVariable> = mutableListOf()
    var variables: MutableMap<String, String> = mutableMapOf()

    constructor()

    constructor(npcEntity: NPCEntity) {
        npcUUID = npcEntity.uuid
        npcName = npcEntity.name.copy()
        npcClass = npcEntity.npc.id
        resourceIdentifier = npcEntity.resourceIdentifier
        battle = npcEntity.battle
        interactionInherited = npcEntity.interaction == null
        interaction = npcEntity.interaction ?: npcEntity.npc.interaction
        aspects = npcEntity.appliedAspects
        behaviours = npcEntity.behaviours.toMutableSet()
        registeredVariables = npcEntity.registeredVariables.toMutableList()
        variables = npcEntity.config.map.map { it.key to it.value.asString() }.toMap().toMutableMap()
    }

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(npcUUID)
        buffer.writeText(npcName)
        buffer.writeIdentifier(npcClass)
        buffer.writeIdentifier(resourceIdentifier)
        buffer.writeNullable(battle) { _, value -> value.encode(buffer) }
        buffer.writeBoolean(interactionInherited)
        buffer.writeNullable(interaction) { _, value ->
            buffer.writeString(value.type)
            value.encode(buffer)
        }
        buffer.writeCollection(aspects, ByteBuf::writeString)
        buffer.writeCollection(behaviours, ByteBuf::writeIdentifier)
        buffer.writeCollection(registeredVariables) { _, it -> it.encode(buffer) }
        buffer.writeMap(
            variables,
            { _, it -> buffer.writeString(it) },
            { _, it -> buffer.writeString(it) },
        )
    }

    override fun decode(buffer: RegistryFriendlyByteBuf) {
        npcUUID = buffer.readUUID()
        npcName = buffer.readText().copy()
        npcClass = buffer.readIdentifier()
        resourceIdentifier = buffer.readIdentifier()
        battle = buffer.readNullable { NPCBattleConfiguration().apply { decode(buffer) } }
        interactionInherited = buffer.readBoolean()
        interaction = buffer.readNullable {
            val type = buffer.readString()
            val configType = NPCInteractConfiguration.types[type] ?: return@readNullable null
            configType.clazz.getConstructor().newInstance().also { it.decode(buffer) }
        }
        aspects = buffer.readList { buffer.readString() }.toMutableSet()
        behaviours = buffer.readList { buffer.readIdentifier() }.toMutableSet()
        registeredVariables = buffer.readList { MoLangConfigVariable.decode(buffer) }.toMutableList()
        variables = buffer.readMap(
            { buffer.readString() },
            { buffer.readString() },
        ).toMutableMap()
    }

    fun apply(entity: NPCEntity) {
        val npcClass =  NPCClasses.getByIdentifier(npcClass) ?: return Cobblemon.LOGGER.error("Failed to apply NPC class $npcClass")
        entity.customName = npcName.copy()
        entity.npc = npcClass
        entity.battle = battle
        if (!interactionInherited) {
            entity.interaction = interaction
        } else {
            entity.interaction = null
        }
        entity.appliedAspects.clear()
        entity.appliedAspects.addAll(aspects)
        entity.updateAspects()
        variables.forEach { (key, value) ->
            val variable = entity.registeredVariables.find { it.variableName == key } ?: return@forEach
            entity.config.setDirectly(key, variable.type.toMoValue(value))
        }
        entity.remakeBrain()
    }
}