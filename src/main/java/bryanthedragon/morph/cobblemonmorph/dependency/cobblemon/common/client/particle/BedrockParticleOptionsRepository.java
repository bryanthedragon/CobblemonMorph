/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleOptions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleReader
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.endsWith
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.nio.charset.StandardCharsets
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

/**
 * Client-side storage of loaded [BedrockParticleOptions]s.
 *
 * @author Hiroku
 * @since February 11th, 2023
 */
public final class BedrockParticleOptionsRepository {
    private val GSON = GsonBuilder().create()
    private val effects = mutableMapOf<ResourceLocation, BedrockParticleOptions>()

    fun loadEffects(resourceResourceManager manager) {
        LOGGER.info("Loading particle effects...")
        effects.clear()
        resourceManager.listResources("bedrock/particles") { path -> path.endsWith(".particle.json") }.forEach { identifier, resource ->
            try {
                resource.open().use { stream ->
                    val json = String(stream.readAllBytes(), StandardCharsets.UTF_8)
                    val effect = SnowstormParticleReader.loadEffect(GSON.fromJson(json, JsonObject.class))
                    effects[effect.id] = effect
                }
            }
            catch (Exception e) {
                LOGGER.error(e)
            }
        }

        LOGGER.info("Loaded ${effects.size} particle effects")
    }

    fun getEffect(ResourceLocation identifier): BedrockParticleOptions? = effects[identifier]
}