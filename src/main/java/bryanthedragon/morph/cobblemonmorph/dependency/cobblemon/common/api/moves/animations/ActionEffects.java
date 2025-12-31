/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import java.awt.Color

/**
 * Data registry containing all of the [ActionEffectTimeline]s that can be triggered from various actions.
 *
 * @author Hiroku
 * @since October 21st, 2023
 */
public final class ActionEffects : JsonDataRegistry<ActionEffectTimeline> {
    override val ResourceLocation id = cobblemonResource("action_effects")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<ActionEffects>()

    init {
        ActionEffectKeyframe.register<AnimationActionEffectKeyframe>("animation")
        ActionEffectKeyframe.register<EntityMoLangActionEffectKeyframe>("entity_molang")
        ActionEffectKeyframe.register<MoLangActionEffectKeyframe>("molang")
        ActionEffectKeyframe.register<ParallelActionEffectKeyframe>("parallel")
        ActionEffectKeyframe.register<CanInterruptActionEffectKeyframe>("can_interrupt")
        ActionEffectKeyframe.register<CannotInterruptActionEffectKeyframe>("cannot_interrupt")
        ActionEffectKeyframe.register<RemoveHoldsActionEffectKeyframe>("remove_holds")
        ActionEffectKeyframe.register<AddHoldsActionEffectKeyframe>("add_holds")
        ActionEffectKeyframe.register<MoveToTargetActionEffectKeyframe>("move_to_target")
        ActionEffectKeyframe.register<ReturnToPositionActionEffectKeyframe>("return_to_position")
        ActionEffectKeyframe.register<PauseActionEffectKeyframe>("pause")
        ActionEffectKeyframe.register<SavePositionActionEffectKeyframe>("save_position")
        ActionEffectKeyframe.register<ForkActionEffectKeyframe>("fork")
        ActionEffectKeyframe.register<SequenceActionEffectKeyframe>("sequence")
        ActionEffectKeyframe.register<RunActionEffectKeyframe>("run_action_effect")
        ActionEffectKeyframe.register<EntityParticlesActionEffectKeyframe>("entity_particles")
        ActionEffectKeyframe.register<EntitySoundActionEffectKeyframe>("entity_sound")
    }

    override val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .registerTypeAdapter(ActionEffectKeyframe.class, ActionEffectKeyframeAdapter)
        .registerTypeAdapter(MinMaxBounds.Doubles.class, FloatNumberRangeAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(Collection.class, AABB.class).type, BoxCollectionAdapter)
        .registerTypeAdapter(AABB.class, BoxAdapter)
        .registerTypeAdapter(Vec3.class, VerboseVec3dAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(IntRange.class, VerboseIntRangeAdapter)
        .registerTypeAdapter(Color.class, LiteralHexColorAdapter)
        .registerTypeAdapter(Stat.class, CobblemonStatTypeAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(
            TypeToken.getParameterized(
                TypeToken.get(List.class).type,
                TypeToken.get(ActionEffectKeyframe.class).type
            ).type,
            SingleToPluralAdapter(ActionEffectKeyframe.class) { it }
        )
        .create()

    override val typeToken: TypeToken<ActionEffectTimeline> = TypeToken.get(ActionEffectTimeline.class)
    override val resourcePath = "action_effects"

    val actionEffects = mutableMapOf<ResourceLocation, ActionEffectTimeline>()
    override fun reload(data: Map<ResourceLocation, ActionEffectTimeline>) {
        actionEffects.clear()
        actionEffects.putAll(data)
        observable.emit(this)
    }

    fun getEffectWithBattleContext(ResourceLocation id, pokemon: BattlePokemon): ActionEffectTimeline? {
        val species = pokemon.entity?.exposedSpecies ?: pokemon.effectedPokemon.species
        val contextedEffect = actionEffects[ResourceLocation.fromNamespaceAndPath(id.namespace, id.path + "_" + species)]
        return contextedEffect ?: actionEffects[id]
    }

    override fun sync(ServerPlayer player) {}
}