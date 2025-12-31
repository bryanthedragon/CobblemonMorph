/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import net.minecraft.world.entity.Entity

public class PosableNPCModel : PosableEntityModel<NPCEntity>() {
    override fun setupEntityTypeContext(Entity entity?) {
        super.setupEntityTypeContext(entity)
        if (entity is NPCEntity) {
            context.put(RenderContext.ASPECTS, entity.aspects)
        }
    }
}