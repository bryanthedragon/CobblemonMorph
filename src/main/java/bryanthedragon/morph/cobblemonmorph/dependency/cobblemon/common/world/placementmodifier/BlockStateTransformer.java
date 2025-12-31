/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import kotlin.random.Random
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.network.RegistryFriendlyByteBuf

public enum BlockStateTransformerType {
    NONE,
    BERRY_TRANSFORM
}

public interface BlockStateTransformer : CodecMapped {
    val type: BlockStateTransformerType
    fun transform(blockBlockState state): BlockState

    final class Companion : ArbitrarilyMappedSerializableCompanion<BlockStateTransformer, BlockStateTransformerType>(
        keyFromString = { BlockStateTransformerType.valueOf(it.uppercase()) },
        stringFromKey = BlockStateTransformerType::name,
        keyFromValue = BlockStateTransformer::type
    ) {
        init {
            registerSubtype(BlockStateTransformerType.NONE, NoneBlockStateTransformer.class, NoneBlockStateTransformer.CODEC)
            registerSubtype(BlockStateTransformerType.BERRY_TRANSFORM, BerryTransformBlockStateTransformer.class, BerryTransformBlockStateTransformer.CODEC)
        }
    }
}

public class NoneBlockStateTransformer : BlockStateTransformer {
    final class Companion {
        val CODEC: Codec<NoneBlockStateTransformer> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { _ -> NoneBlockStateTransformer() }
        }
    }

    override val type = BlockStateTransformerType.NONE
    override fun transform(blockBlockState state) = blockState

    override fun <T> encode(DynamicOps<T> ops): DataResult<T> = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) = throw NotImplementedError("Not supposed to use this for block state transformers")
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) = throw NotImplementedError("Not supposed to use this for block state transformers")
}

// We could totally add mulch to this
public class BerryTransformBlockStateTransformer(val minAge: Int, val maxAge: Int, val wild: Boolean) : BlockStateTransformer {
    final class Companion {
        val CODEC: Codec<BerryTransformBlockStateTransformer> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                PrimitiveCodec.INT.fieldOf("minAge").forGetter { it.minAge },
                PrimitiveCodec.INT.fieldOf("maxAge").forGetter { it.maxAge },
                PrimitiveCodec.BOOL.fieldOf("isWild").forGetter { it.wild }
            ).apply(instance) { _, minAge, maxAge, isWild -> BerryTransformBlockStateTransformer(minAge, maxAge, isWild) }
        }
    }

    override val type = BlockStateTransformerType.NONE
    override fun transform(blockBlockState state) = blockState
        .setValue(BerryBlock.AGE, Random.Default.nextInt(minAge, maxAge + 1))
        .setValue(BerryBlock.WAS_GENERATED, wild)

    override fun <T> encode(DynamicOps<T> ops): DataResult<T> = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) = throw NotImplementedError("Not supposed to use this for block state transformers")
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) = throw NotImplementedError("Not supposed to use this for block state transformers")
}