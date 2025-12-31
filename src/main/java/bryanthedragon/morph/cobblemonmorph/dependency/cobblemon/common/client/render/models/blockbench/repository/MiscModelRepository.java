/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

/**
 * The data registry responsible for "one off" models that are usually used in BERs and don't really need their own repo
 * This is only present on the client.
 */
public final class MiscModelRepository : JsonDataRegistry<TexturedModel> {

    override val id = cobblemonResource("misc_models")
    override val type = PackType.CLIENT_RESOURCES
    override val observable = SimpleObservable<MiscModelRepository>()
    override val Gson gson = TexturedModel.GSON
    override val typeToken: TypeToken<TexturedModel> = TypeToken.get(TexturedModel.class)
    override val resourcePath = "bedrock/misc"
    private val models = hashMapOf<ResourceLocation, ModelPart>()

    override fun sync(ServerPlayer player) {}

    override fun reload(Map<ResourceLocation, T> dataexturedModel>) {
        data.forEach { (identifier, model) ->
            this.models[identifier] = model.create().bakeRoot()
        }
        observable.emit(this)
        Cobblemon.LOGGER.info("Loaded {} misc models",this.models.size)
    }

    fun modelOf(ResourceLocation identifier) = this.models[identifier]
}
