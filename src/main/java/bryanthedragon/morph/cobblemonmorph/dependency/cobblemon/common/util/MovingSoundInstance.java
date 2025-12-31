/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.phys.Vec3

public class MovingSoundInstance(
    val SoundEvent sound,
    private val SoundSource category,
    val pos: () -> Vec3?,
    private val startingVol: Float,
    private val Float pitch,
    var loop: Boolean = true,
    var duration: Int = 20,
    private val repeatDelay: Int = 0
) : AbstractTickableSoundInstance (sound, category, SoundInstance.createUnseededRandom()) {
    var time = 0
    init {
        this.looping = loop
        this.x = pos.invoke()?.x ?: 0.0
        this.y = pos.invoke()?.y ?: 0.0
        this.z = pos.invoke()?.z ?: 0.0
        this.volume = startingVol
    }

    override fun tick() {
        if(!loop && time > duration) {
            this.stop()
        } else {
            this.x = pos.invoke()?.x ?: 0.0
            this.y = pos.invoke()?.y ?: 0.0
            this.z = pos.invoke()?.z ?: 0.0
        }
        if (repeatDelay > 0 && time > duration + repeatDelay) {
            time = 0
        } else if (repeatDelay == 0 && time > duration) {
            time = 0
        } else if (repeatDelay < 0) {
            time = 0
        } else {
            time++
        }
    }

}