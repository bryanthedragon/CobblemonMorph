/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.fishing.PokeRodFishingBobberEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class FishingBobberPredicate(Boolean inOpenWater) extends EntitySubPredicate {
    MapCodec<FishingBobberPredicate> codec() = CODEC;
    Boolean matches(Entity entity, ServerLevel serverLevel, Vec3 vec3?) {
        return (entity as? PokeRodFishingBobberEntity)?.inOpenWater == this.inOpenWater;
    }
    final class Companion {
        val MapCodec<FishingBobberPredicate> CODEC = RecordCodecBuilder.mapCodec { instance -> instance.group(Codec.BOOL.fieldOf("in_open_water").forGetter(FishingBobberPredicate::inOpenWater)).apply(instance, ::FishingBobberPredicate)};
    }
}