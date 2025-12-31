/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mechanics;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.pot.CookingQuality;

import net.minecraft.network.RegistryFriendlyByteBuf;

/**
 * Mechanic to hold various properties that motivate aprijuice as a mechanic.
 *
 * @author Hiroku
 * @since November 7th, 2025
 */
public class AprijuicesMechanic {
    /** The points that apply to different riding stats, based on the apricorn used for the aprijuice. */
    public static final apricornStatEffects = mutableMapOf<Apricorn, Map<RidingStat, Int>>();
    /** Maps flavour values to stat points for riding stats. Aprijuice finds the highest threshold it meets. */
    public static final statPointFlavourThresholds = mutableMapOf<Int, Int>();
    /** Maps stat points for riding stats to what cooking quality that represents. It's for tooltips. */
    public static final cookingQualityPointThresholds = mutableMapOf<Int, CookingQuality>();

    internal fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeMap(this.apricornStatEffects, { _, apricorn -> buffer.writeEnum(apricorn) }, { _, ridingStats -> buffer.writeMap(ridingStats, { _, ridingStat -> buffer.writeEnum(ridingStat) }, { _, value -> buffer.writeVarInt(value) })})
        buffer.writeMap(this.statPointFlavourThresholds, { _, key -> buffer.writeVarInt(key) }, { _, value -> buffer.writeVarInt(value) })
        buffer.writeMap(this.cookingQualityPointThresholds,{ _, key -> buffer.writeVarInt(key) }, { _, cookingQuality -> buffer.writeEnum(cookingQuality) })
    }

    final class Companion {
    internal fun decode(RegistryFriendlyByteBuf buffer): AprijuicesMechanic 
    {
        public static final mechanic = AprijuicesMechanic();
        public static final decodedApricornStatEffects = buffer.readMap({ buffer.readEnum<Apricorn>(Apricorn.class) }, {buffer.readMap({ buffer.readEnum<RidingStat>(RidingStat.class) }, { buffer.readVarInt() }).toMutableMap()}).toMutableMap()
            mechanic.apricornStatEffects.clear();
            mechanic.apricornStatEffects.putAll(decodedApricornStatEffects);

            public static final decodedStatPointFlavourThresholds = buffer.readMap({ buffer.readVarInt() },{ buffer.readVarInt() });
            mechanic.statPointFlavourThresholds.clear();
            mechanic.statPointFlavourThresholds.putAll(decodedStatPointFlavourThresholds);
            public static final decodedCookingQualityPointThresholds = buffer.readMap({ buffer.readVarInt() }, { buffer.readEnum(CookingQuality.class) });
            mechanic.cookingQualityPointThresholds.clear();
            mechanic.cookingQualityPointThresholds.putAll(decodedCookingQualityPointThresholds);
            return mechanic;
        }
    }
}