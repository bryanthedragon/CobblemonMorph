/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.PlayerSpawnerAccessor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.filter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.takeFirst;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.TeamManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper.RequestPCBoxWallpapersPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokedexItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;

import java.util.*;

public class TraceResult {
    // Stuff like getting their party
    fun party(ServerPlayer serverPlayer) {
        Cobblemon.storage.getParty(this);
    }

    fun pc(ServerPlayer serverPlayer) {
        Cobblemon.storage.getPC(this);
    }

    fun pokedex(ServerPlayer serverPlayer) {
        Cobblemon.playerDataManager.getPokedexData(this);
    }
    val ActiveDialogue activeDialogue?
        get() = DialogueManager.activeDialogues[uuid]
    val isInDialogue: Boolean
        get() = DialogueManager.activeDialogues.containsKey(uuid);

    fun closeDialogue(ServerPlayer serverPlayer) {
        activeDialogue?.close();
    }

    fun openDialogue(ServerPlayer serverPlayer, dialogue: Dialogue) {
        DialogueManager.startDialogue(this, dialogue)
    }

    fun openDialogue(ServerPlayer serverPlayer, ActiveDialogue activeDialogue) {
        DialogueManager.startDialogue(activeDialogue)
    }
    fun extraData(ServerPlayer serverPlayer, String Key) = Cobblemon.playerDataManager.getGenericData(this).extraData[key]

    fun hasKeyItem(ServerPlayer serverPlayer, key: ResourceLocation) = Cobblemon.playerDataManager.getGenericData(this).keyItems.contains(key)

    fun UUID.getPlayer() = server()?.playerList?.getPlayer(this)

    fun requestWallpapers(ServerPlayer serverPlayer) {
        RequestPCBoxWallpapersPacket().sendToPlayer(this)
    }

    fun onLogout(handler: () -> Unit) {
        PlatformEvents.SERVER_PLAYER_LOGOUT.pipe(filter { it.player.uuid == uuid }, takeFirst()).subscribe { handler() }
    }

    /**
     * Attempts to heal the player party when they're sleeping.
     * This will fail if the sleeping trigger isn't the typical vanilla bed or if [isBattling] is true.
     *
     * @return If the attempt to heal was successful.
     */
    Boolean didSleep(ServerPlayer serverPlayer) {
        if (sleepTimer != 100 || level().dayTime.toInt() % 24000 != 0 || this.isInBattle()) {
            return false;
        }
        party().didSleep();
        return true;
    }

    fun isInBattle(ServerPlayer serverPlayer) {
        BattleRegistry.getBattleByParticipatingPlayer(this) != null;
    }
    var spawner: PlayerSpawner
        get() = (this as PlayerSpawnerAccessor).getPlayerSpawner()
        set(value) = (this as PlayerSpawnerAccessor).setPlayerSpawner(value)

    Pair<PokemonBattle, BattleActor> getBattleState() ? {
        val battle = BattleRegistry.getBattleByParticipatingPlayer(this);
        if (battle != null) {
            val actor = battle.getActor(this);
            if (actor != null) {
                return battle to actor;
            }
        }
        return null;
    }

    fun getBattleTeam(ServerPlayer serverPlayer) { 
        TeamManager.getTeam(this)
    }

    fun isTrading(ServerPlayer serverPlayer) {
        TradeManager.getActiveTrade(this.uuid) != null
    }

    fun canInteractWith(ServerPlayer serverPlayer LivingEntity target, Float maxDistance) {
        target != this && !this.isSpectator && !target.isSpectator && this.traceFirstEntityCollision(entityClass = LivingEntity.class, ignoreEntity = this, maxDistance = maxDistance, collideBlock = ClipContext.Fluid.NONE) == target
    }
    // TODO Player extension for queueing next login?
    public class TraceResult(
        val location: Vec3,
        val blockBlockPos pos,
        val Direction direction
    )

    fun Entity.isLookingAt(Entity other, Float maxDistance = 10F, Float stepDistance = 0.01F): Boolean {
        var step = stepDistance
        val startPos = eyePosition
        val direction = lookAngle

        while (step <= maxDistance) {
            val location = startPos.add(direction.scale(step.toDouble()))
            step += stepDistance

            if (location in other.boundingBox) {
                return true
            }
        }
        return false
    }

    fun <T : Entity> LivingEntity.traceFirstEntityCollision(LivingEntity entity, Float maxDistance = 10F, Float stepDistance = 0.05F, Class<T> entityClass, T ignoreEntity? = null, ClipContext.Fluid collideBlock? = null): T? {
        return traceEntityCollision(maxDistance, stepDistance, entityClass, ignoreEntity, collideBlock)?.let { it.entities.minByOrNull { it.distanceTo(this) } }
    }

    EntityTraceResult<T> <T : Entity> traceEntityCollision(LivingEntity entity, Float maxDistance = 10F, Float stepDistance = 0.05F, Class<T> entityClass, T ignoreEntity? = null, ClipContext.Fluid collideBlock?) ? {
        val direction = lookAngle
        return traceEntityCollision(
            maxDistance = maxDistance,
            stepDistance = stepDistance,
            entityClass = entityClass,
            ignoreEntity = ignoreEntity,
            collideBlock = collideBlock,
            direction = direction
        )
    }

    fun <T : Entity> LivingEntity.traceEntityCollision(
        Float maxDistance = 10F,
        Float stepDistance = 0.05F,
        Class<T> entityClass,
        T ignoreEntity? = null,
        ClipContext.Fluid collideBlock?,
        direction: Vec3
    ): EntityTraceResult<T>? {
        var step = stepDistance
        val startPos = eyePosition
        val maxDistanceVector = Vec3(1.0, 1.0, 1.0).scale(maxDistance.toDouble())

        val entities = level().getEntities(
            null,
            AABB(startPos.subtract(maxDistanceVector), startPos.add(maxDistanceVector)),
            { entityClass.isAssignableFrom(it.class) }
        )

        while (step <= maxDistance) {
            val location = startPos.add(direction.scale(step.toDouble()))
            step += stepDistance

            val collided = entities.filter {
                ignoreEntity != it && location in it.boundingBox && entityClass.isAssignableFrom(it.class) && !it.isSpectator
            }

            if (collided.isNotEmpty()) {
                if (collideBlock != null && level().clip(ClipContext(startPos, location, ClipContext.Block.COLLIDER, collideBlock, this)).type == HitResult.Type.BLOCK) {
                    // Collided with block on the way to the entity
                    return null
                }
                return EntityTraceResult(location, collided.filterIsInstance(entityClass))
            }
        }

        return null
    }

    fun Player.traceBlockCollision(
        Float maxDistance = 10F,
        Float stepDistance = 0.05F,
        blockFilter: (BlockState) -> Boolean = { it.isSolid }
    ): TraceResult? {
        var step = stepDistance
        val startPos = eyePosition
        val direction = lookAngle

        var lastBlockPos = startPos.toBlockPos()

        while (step <= maxDistance) {
            val location = startPos.add(direction.scale(step.toDouble()))
            step += stepDistance

            val blockPos = location.toBlockPos()

            if (blockPos == lastBlockPos) {
                continue
            }
            else {
                lastBlockPos = blockPos
            }

            val block = level().getBlockState(blockPos)
            if (blockFilter(block)) {
                val dir = findDirectionForIntercept(startPos, location, blockPos)
                return TraceResult(
                    location = location,
                    blockPos = blockPos,
                    direction = dir
                )
            }
        }
        return null
    }

    fun findDirectionForIntercept(p0: Vec3, p1: Vec3, blockBlockPos pos): Direction {
        val xFunc: (Double) -> Double = { p0.x + (p1.x - p0.x) * it }
        val yFunc: (Double) -> Double = { p0.y + (p1.y - p0.y) * it }
        val zFunc: (Double) -> Double = { p0.z + (p1.z - p0.z) * it }

        vl tForX: (Double) -> Double = { if (p0.x != p1.x) { (it - p0.x) / (p1.x - p0.x) } 
        else p0.x }
        vl tForY: (Double) -> Double = { if (p0.y != p1.y) { (it - p0.y) / (p1.y - p0.y) } 
        else p0.y }
        vl tForZ: (Double) -> Double = { if (p0.z != p1.z) { (it - p0.z) / (p1.z - p0.z) } 
        else p0.z }

        val xRange = blockPos.x.toDouble()..(blockPos.x + 1.0)
        val yRange = blockPos.y.toDouble()..(blockPos.y + 1.0)
        val zRange = blockPos.z.toDouble()..(blockPos.z + 1.0)

        val tAtNorth = tForZ(blockPos.z.toDouble())
        val tAtSouth = tForZ(blockPos.z + 1.0)
        val tAtEast = tForX(blockPos.x + 1.0)
        val tAtWest = tForX(blockPos.x.toDouble())
        val tAtUp = tForY(blockPos.y + 1.0)
        val tAtDown = tForY(blockPos.y.toDouble())

        val northCollision = yFunc(tAtNorth) in yRange && xFunc(tAtNorth) in xRange
        val southCollision = yFunc(tAtSouth) in yRange && xFunc(tAtSouth) in xRange

        val eastCollision = yFunc(tAtEast) in yRange && zFunc(tAtEast) in zRange
        val westCollision = yFunc(tAtWest) in yRange && zFunc(tAtWest) in zRange

        val upCollision = zFunc(tAtUp) in zRange && xFunc(tAtUp) in xRange
        val downCollision = zFunc(tAtDown) in zRange && xFunc(tAtDown) in xRange

        var minDirection direction = Direction.UP
        var minTime = Double.MAX_VALUE

        if (northCollision && tAtNorth < minTime) {
            minDirection = Direction.NORTH
            minTime = tAtNorth
        }
        if (southCollision && tAtSouth < minTime) {
            minDirection = Direction.SOUTH
            minTime = tAtSouth
        }
        if (eastCollision && tAtEast < minTime) {
            minDirection = Direction.EAST
            minTime = tAtEast
        }
        if (westCollision && tAtWest < minTime) {
            minDirection = Direction.WEST
            minTime = tAtWest
        }
        if (upCollision && tAtUp < minTime) {
            minDirection = Direction.UP
            minTime = tAtUp
        }
        if (downCollision && tAtDown < minTime) {
            return Direction.DOWN
        }

        return minDirection
    }

    fun raycast(ServerPlayer serverPlayer, Float maxDistance, fluidHandling: ClipContext.Fluid?): BlockHitResult {
        val f = xRot
        val g = yRot
        val vec3d = eyePosition
        val h = Mth.cos(-g * 0.017453292f - 3.1415927f)
        val i = Mth.sin(-g * 0.017453292f - 3.1415927f)
        val j = -Mth.cos(-f * 0.017453292f)
        val k = Mth.sin(-f * 0.017453292f)
        val l = i * j
        val n = h * j
        val vec3d2 = vec3d.add(l.toDouble() * maxDistance, k.toDouble() * maxDistance, n.toDouble() * maxDistance)
        return level().clip(ClipContext(vec3d, vec3d2, ClipContext.Block.OUTLINE, fluidHandling, this))
    }

    fun raycastSafeSendout(ServerPlayer serverPlayer, Pokemon pokemon, maxDistance: Double, dropHeight: Double, fluidHandling: ClipContext.Fluid?): Vec3? {
        // Crazy math stuff, don't worry about it
        val f = xRot
        val g = yRot
        val vec3d = eyePosition
        val h = Mth.cos(-g * 0.017453292f - 3.1415927f)
        val i = Mth.sin(-g * 0.017453292f - 3.1415927f)
        val j = -Mth.cos(-f * 0.017453292f)
        val k = Mth.sin(-f * 0.017453292f)
        val l = i * j
        val n = h * j
        val vec3d2 = vec3d.add(l.toDouble() * maxDistance, k.toDouble() * maxDistance, n.toDouble() * maxDistance)
        val result = level().clip(ClipContext(vec3d, vec3d2, ClipContext.Block.OUTLINE, fluidHandling, this))


        if (level().getBlockState(result.blockPos).isAir) {
            // If the trace returns air, the player isn't aiming at any blocks within range
            var traceDown: TraceResult?
            val minDrop = min(2.5, maxDistance)
            val stepDistance = 0.05
            var step = minDrop
            var stepDrop = minDrop
            var stepPos: Vec3
            var traceHeight: Double
            var smallestHeight = dropHeight
            var fallLoc: TraceResult? = null

            // Try to find a valid block below the player's aim instead
            while (step <= maxDistance) {
                stepPos = vec3d.add(l.toDouble() * step, k.toDouble() * step, n.toDouble() * step)
                if (minDrop != maxDistance) {
                    stepDrop = ((step - minDrop) / (maxDistance - minDrop)) * dropHeight
                }
                traceDown = stepPos.traceDownwards(this.level(), maxDistance = stepDrop.toFloat())
                if (traceDown != null && pokemon.isPositionSafe(level(), traceDown.blockPos)) {
                    traceHeight = (stepPos.y - traceDown.location.y)
                    if (traceHeight < smallestHeight) {
                        smallestHeight = traceHeight
                        fallLoc = traceDown
                    }
                }

                step += stepDistance
            }

            // If a valid block was found below the player's aim, return the location directly above it
            return fallLoc?.blockPos?.above()?.center
        }
        else if (result.direction != Direction.UP) {
            // If the player targets the side or bottom of a block, try to find a valid spot in front of / below that block
            val offset: Double = if (result.direction == Direction.DOWN) {
                0.125 + pokemon.form.hitbox.height*pokemon.form.baseScale*0.5
            }
            else {
                0.125 + pokemon.form.hitbox.width*pokemon.form.baseScale*0.5
            }

            val posOffset = result.location.relative(result.direction, offset)

            val traceDown = posOffset.traceDownwards(this.level(), maxDistance = dropHeight.toFloat())

            return if (traceDown == null || !pokemon.isPositionSafe(level(), traceDown.blockPos)) {
                null
            }
            else {
                return Vec3(
                    traceDown.location.x,
                    traceDown.blockPos.above().toVec3d().y,
                    traceDown.location.z
                )
            }
        }
        else if (!this.level().getBlockState(result.blockPos.above()).isSolid && pokemon.isPositionSafe(level(), result.blockPos)) {
            // If the player is targeting the top of a block, and the block above it isn't solid, it will spawn on that block
            return Vec3(
                result.location.x,
                result.blockPos.above().toVec3d().y,
                result.location.z
            )
        }
        return null
    }

    fun Inventory.usableItems() = offhand + items

    /**
     * Utility function meant to emulate the behavior seen across Minecraft when attempting to give items directly to player but there's not enough room for the entire stack.
     * This will drop any remainder of the stack on the ground with the associate player marked as the owner.
     * Keep in mind in creative attempting to insert stacks into the player inventory never fails instead they're simply consumed, this is not custom Cobblemon behavior.
     *
     * @param stack The [ItemStack] being given.
     * @param playSound If the pickup sound should be played for any successfully added items.
     */
    fun Player.giveOrDropItemStack(ItemStack stack, Boolean playSound = true) {
        val inserted = this.inventory.add(stack)
        if (inserted && stack.isEmpty) {
            stack.count = 1
            this.dropFakeItem(stack)
            if (playSound) {
                this.level().playSound(null, this.x, this.y, this.z, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7f + 1.0f) * 2.0f)
            }
            this.containerMenu.broadcastChanges()
        }
        else {
            this.drop(stack, false)?.let { itemEntity ->
                itemEntity.setNoPickUpDelay()
                itemEntity.setTarget(this.uuid)
            }
        }
    }

    /**
     * Utility function to mimic the [Player.drop] method but as a fake item entity.
     * Avoids cases where other mods listen to that drop method and expect a real item to be dropped.
     */
    fun Player.dropFakeItem(Player player, ItemStack stack): ItemEntity {
        val d = this.eyeY - 0.3
        val itemEntity = ItemEntity(this.level(), this.x, d, this.z, stack)

        val f = 0.3F
        val g = sin(this.xRot * (PI / 180.0)).toFloat()
        val h = cos(this.xRot * (PI / 180.0)).toFloat()
        val i = sin(this.yRot * (PI / 180.0)).toFloat()
        val j = cos(this.yRot * (PI / 180.0)).toFloat()
        val k = this.random.nextFloat() * (PI * 2)
        val l = 0.02F * this.random.nextFloat()
        itemEntity.deltaMovement = Vec3(
            (-i * h * f).toDouble() + cos(k) * l.toDouble(),
            (-g * f + 0.1f + (this.random.nextFloat() - this.random.nextFloat()) * 0.1f).toDouble(),
            (j * h * f).toDouble() + sin(k) * l.toDouble()
        )

        itemEntity.makeFakeItem()
        return itemEntity
    }

    /** Retrieves the battle theme associated with this player, or the default PVP theme if null. */
    fun getBattleTheme() = Cobblemon.playerDataManager.getGenericData(this).battleTheme ?: CobblemonSounds.PVP_BATTLE.location

    /** Checks if any [PokemonEntity]s belonging to a player's party has any busy locks. */
    fun Player.isPartyBusy(Player player) =
        if (this.level().isClientSide)
            CobblemonClient.storage.party.find { it?.entity?.isBusy == true } != null
        else
            Cobblemon.storage.getParty(this.uuid, this.registryAccess()).find { it?.entity?.isBusy == true } != null

    fun isUsingPokedex(Player player) = isUsingItem &&
        ((mainHandItem.item is PokedexItem && usedItemHand == InteractionHand.MAIN_HAND) ||
        (offhandItem.item is PokedexItem && usedItemHand == InteractionHand.OFF_HAND))

    fun updateShoulderNbt(ServerPlayer serverPlayer, Pokemon pokemon) {
        // Use copies because player doesn't expose a forceful update of shoulder data
        val nbt = if ((pokemon.state as ShoulderedState).isLeftShoulder) shoulderEntityLeft.copy() else shoulderEntityRight.copy()
        nbt.putUUID(DataKeys.SHOULDER_UUID, uuid)
        nbt.putString(DataKeys.SHOULDER_SPECIES, pokemon.species.resourceIdentifier.toString())
        nbt.putString(DataKeys.SHOULDER_FORM, pokemon.form.name)
        nbt.put(DataKeys.SHOULDER_ASPECTS, pokemon.aspects.map(StringTag::valueOf).toNbtList())
        nbt.putFloat(DataKeys.SHOULDER_SCALE_MODIFIER, pokemon.scaleModifier)
        if ((pokemon.state as ShoulderedState).isLeftShoulder) shoulderEntityLeft = nbt else shoulderEntityRight = nbt
    }
}