/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.struct.QueryStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.StringValue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.addFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readSizedInt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeSizedInt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import kotlin.properties.Delegates;

import java.util.function.Function;

/**
 * Representing a Move based on some template and with current PP and the number of raised PP stages.
 */
public open class Move(
    val template: MoveTemplate,
    currentPp: Int,
    raisedPpStages: Int = 0
) {
    private var emit = true
    val observable = SimpleObservable<Move>()

    var currentPp: Int by Delegates.observable(currentPp) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            update()
        }
    }

    var raisedPpStages: Int by Delegates.observable(raisedPpStages) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            update()
        }
    }

    fun doThenUpdate(action: () -> Unit) {
        val oldEmit = emit
        emit = false
        action()
        emit = oldEmit
        update()
    }

    fun update() {
        if (emit) {
            observable.emit(this)
        }
    }

    val String name
        get() = template.name

    val MutableComponent displayName
        get() = template.displayName

    val description: MutableComponent
        get() = template.description

    val type: ElementalType
        get() = template.elementalType

    val damageCategory: DamageCategory
        get() = template.damageCategory

    val power: Double
        get() = template.power

    val accuracy: Double
        get() = template.accuracy

    val effectChances: Array<Double>
        get() = template.effectChances

    val maxPp: Int
        get() = template.pp + raisedPpStages * template.pp / 5

    val struct: QueryStruct = ObjectValue(this).also {
        it.addFunctions(
            hashMapOf(
                "name" to Function { StringValue(name) },
                "display_name" to Function { StringValue(displayName.string) },
                "description" to Function { StringValue(description.string) },
                "type" to Function { StringValue(type.showdownId) },
                "damage_category" to Function { StringValue(damageCategory.name) },
                "power" to Function { DoubleValue(power) },
                "accuracy" to Function { DoubleValue(accuracy) },
                "max_pp" to Function { DoubleValue(maxPp.toDouble()) }
            )
        )
    }

    /** Raises the max PP and rescales the current PP value. Returns false if it was already maxed out. */
    fun raiseMaxPP(amount: Int): Boolean {
        val oldPp = maxPp
        doThenUpdate {
            val ppRatio = currentPp / maxPp.toFloat()
            raisedPpStages += amount
            if (raisedPpStages > 3) {
                raisedPpStages = 3
            }
            currentPp = ceil(ppRatio * maxPp).toInt()
        }
        return oldPp != maxPp
    }

    fun saveToNBT(CompoundTag nbt): CompoundTag {
        nbt.putString(DataKeys.POKEMON_MOVESET_MOVENAME, name)
        nbt.putInt(DataKeys.POKEMON_MOVESET_MOVEPP, currentPp)
        nbt.putInt(DataKeys.POKEMON_MOVESET_RAISED_PP_STAGES, raisedPpStages)
        return nbt
    }

    fun saveToJSON(JsonObject json): JsonObject {
        json.addProperty(DataKeys.POKEMON_MOVESET_MOVENAME, name)
        json.addProperty(DataKeys.POKEMON_MOVESET_MOVEPP, currentPp)
        json.addProperty(DataKeys.POKEMON_MOVESET_RAISED_PP_STAGES, raisedPpStages)
        return json
    }

    fun copy() = loadFromJSON(saveToJSON(JsonObject()))

    fun saveToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(name)
        buffer.writeSizedInt(IntSize.U_BYTE, currentPp)
        buffer.writeSizedInt(IntSize.U_BYTE, raisedPpStages)
    }

    final class Companion {
        fun loadFromNBT(CompoundTag nbt): Move {
            val moveName = nbt.getString(DataKeys.POKEMON_MOVESET_MOVENAME)
            val template = Moves.getByNameOrDummy(moveName)
            return template.create(nbt.getInt(DataKeys.POKEMON_MOVESET_MOVEPP), nbt.getInt(DataKeys.POKEMON_MOVESET_RAISED_PP_STAGES))
        }

        fun loadFromJSON(JsonObject json): Move {
            val moveName = json.get(DataKeys.POKEMON_MOVESET_MOVENAME).asString
            val template = Moves.getByNameOrDummy(moveName)
            val currentPp = json.get(DataKeys.POKEMON_MOVESET_MOVEPP).asInt
            val raisedPpStages = json.get(DataKeys.POKEMON_MOVESET_RAISED_PP_STAGES)?.asInt ?: 0
            return Move(template, currentPp, raisedPpStages)
        }

        fun loadFromBuffer(RegistryFriendlyByteBuf buffer): Move {
            val moveName = buffer.readString()
            val currentPp = buffer.readSizedInt(IntSize.U_BYTE)
            val raisedPpStages = buffer.readSizedInt(IntSize.U_BYTE)
            val template = Moves.getByNameOrDummy(moveName)
            return template.create(currentPp, raisedPpStages)
        }

        @JvmStatic
        val CODEC: Codec<Move> = RecordCodecBuilder.create { it.group(
            MoveTemplate.BY_STRING_CODEC.fieldOf(DataKeys.POKEMON_MOVESET_MOVENAME).forGetter(Move::template),
            Codec.intRange(0, Int.MAX_VALUE).fieldOf(DataKeys.POKEMON_MOVESET_MOVEPP).forGetter(Move::currentPp),
            Codec.intRange(0, 3).fieldOf(DataKeys.POKEMON_MOVESET_RAISED_PP_STAGES).forGetter(Move::raisedPpStages)
        ).apply(it) { template, currentPp, raisedPpStages -> template.create(currentPp, raisedPpStages) } }

    }
}