/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

/**
 * Contains information for forcing a model to be baked
 *
 * @param modelLocation The location of the model
 * @param modelIdentifier The identifier that the BakedModel will be registered to
 */
public record BakingOverride(ResourceLocation modelLocation, ModelResourceLocation modelIdentifier) {
    @SuppressWarnings("null")
    public BakedModel getModel() {
        return Minecraft.getInstance().getModelManager().getModel(modelIdentifier);
    }
}

