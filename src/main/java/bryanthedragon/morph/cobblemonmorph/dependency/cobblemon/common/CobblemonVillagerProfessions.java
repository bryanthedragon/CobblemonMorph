/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

@SuppressWarnings("unchecked")
public final class CobblemonVillagerProfessions extends PlatformRegistry<Registry<VillagerProfession>, ResourceKey<Registry<VillagerProfession>>, VillagerProfession>() {
    val Registry<VillagerProfession> registry = BuiltInRegistries.VILLAGER_PROFESSION;
    val Registry<VillagerProfession> resourceKey = (Registry<VillagerProfession>) Registries.VILLAGER_PROFESSION;

    val NURSE = profession(CobblemonPoiTypes.NURSE_KEY, CobblemonSounds.VILLAGER_WORK_NURSE);

    /**
     * Register a villager profession texture override for Cobblemon villagers of the given [VillagerProfession] for specified names.
     *
     * @param profession The Cobblemon [VillagerProfession] being overridden.
     * @return The profession override texture file name and a list of names that will trigger the override, as a pair.
     */
    Pair<String, Array<String>>? getNameTagOverride(VillagerProfession? profession) ? = when (profession) {NURSE -> Pair("nurse_joy", arrayOf("ジョーイ", "간호순", "祖兒", "喬伊", "乔伊", "האחות ג'וי", "الممرضة جوي", "Joy", "Joelle", "Joëlle", "Джой", "จอย")) else -> null
    }

    private VillagerProfession profession(ResourceKey<PoiType> resourceKey, SoundEvent soundEvent?)
    {
        create(resourceKey.location().path, VillagerProfession(resourceKey.location().toString(), { holder: Holder<PoiType> -> holder.`is`(resourceKey) }, { holder: Holder<PoiType> -> holder.`is`(resourceKey) }, ImmutableSet.of(), ImmutableSet.of(), soundEvent))
    }
}
