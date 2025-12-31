/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas.CobblemonAtlases
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.client.model.geom.ModelPart

import net.minecraft.server.packs.PackType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.resources.ResourceLocation

/**
 * The data registry responsible for berry fruit and flower models.
 * This is only present on the client.
 */
public final class BerryModelRepository : JsonDataRegistry<TexturedModel> {

    override val id = cobblemonResource("berry_models")
    override val type = PackType.CLIENT_RESOURCES
    override val observable = SimpleObservable<BerryModelRepository>()
    override val Gson gson = TexturedModel.GSON
    override val typeToken: TypeToken<TexturedModel> = TypeToken.get(TexturedModel.class)
    override val resourcePath = "bedrock/berries"
    private val rawModels = hashMapOf<ResourceLocation, TexturedModel>()
    private val processedModels = hashMapOf<ResourceLocation, ModelPart>()

    override fun sync(ServerPlayer player) {}

    override fun reload(Map<ResourceLocation, T> dataexturedModel>) {
        data.forEach { (identifier, model) ->
            this.rawModels[identifier] = model
        }
        observable.emit(this)
        Cobblemon.LOGGER.info("Loaded {} berry models",this.rawModels.size)
    }

    //This runs after the berry atlas + berry registry has been created, hotfixes models to use the u-v vals of the atlas
    fun patchModels() {
        Berries.all().forEach {
            val fruitModel = rawModels[it.fruitModelIdentifier]
            val flowerModel = rawModels[it.flowerModelIdentifier]
            val fruitTexId = it.fruitTexture
            val flowerTexId = it.flowerTexture
            val fruitTex = CobblemonAtlases.BERRY_SPRITE_ATLAS.getSprite(fruitTexId)
            val flowerTex = CobblemonAtlases.BERRY_SPRITE_ATLAS.getSprite(flowerTexId)
            processedModels[it.fruitModelIdentifier] = fruitModel?.createWithUvOverride(
                fruitTex.x,
                fruitTex.y,
                CobblemonAtlases.BERRY_SPRITE_ATLAS.textureAtlas.width,
                CobblemonAtlases.BERRY_SPRITE_ATLAS.textureAtlas.height
            )?.bakeRoot()!!
            processedModels[it.flowerModelIdentifier] = flowerModel?.createWithUvOverride(
                flowerTex.x,
                flowerTex.y,
                CobblemonAtlases.BERRY_SPRITE_ATLAS.textureAtlas.width,
                CobblemonAtlases.BERRY_SPRITE_ATLAS.textureAtlas.height
            )?.bakeRoot()!!
        }
    }

    fun modelOf(ResourceLocation identifier) = this.processedModels[identifier]
}