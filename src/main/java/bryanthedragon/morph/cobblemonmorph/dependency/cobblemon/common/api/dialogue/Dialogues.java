/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueActionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueFaceProviderAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueInputAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialoguePredicateAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DialogueTextAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TranslatedTextAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

/**
 * Registry for dialogue data.
 *
 * @see Dialogue
 * @since December 29th, 2023
 * @author Hiroku
 */final class Dialogues : JsonDataRegistry<Dialogue> {
    override val id = cobblemonResource("dialogues")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<Dialogues>()

    val dialogues = mutableMapOf<ResourceLocation, Dialogue>()
    /** If you need custom adapters registered, subscribe to this and register them. */
    val gsonObservable: SimpleObservable<GsonBuilder> = SimpleObservable()

    override fun sync(player: ServerPlayer) {}

    override val gson = GsonBuilder()
        .registerTypeAdapter(DialogueAction::class.java, DialogueActionAdapter)
        .registerTypeAdapter(DialoguePredicate::class.java, DialoguePredicateAdapter)
        .registerTypeAdapter(DialogueInput::class.java, DialogueInputAdapter)
        .registerTypeAdapter(DialogueFaceProvider::class.java, DialogueFaceProviderAdapter)
        .registerTypeAdapter(DialogueText::class.java, DialogueTextAdapter)
        .registerTypeAdapter(Expression::class.java, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike::class.java, ExpressionLikeAdapter)
        .registerTypeAdapter(MutableComponent::class.java, TranslatedTextAdapter)
        .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter)
        .also { gsonObservable.emit(it) }
        .create()

    override val typeToken = TypeToken.get(Dialogue::class.java)
    override val resourcePath = "dialogues"

    override fun reload(data: Map<ResourceLocation, Dialogue>) {
        dialogues.putAll(data)
        observable.emit(this)
    }
}