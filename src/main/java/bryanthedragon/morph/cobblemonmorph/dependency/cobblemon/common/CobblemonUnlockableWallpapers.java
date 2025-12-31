/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import com.bedrockk.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.UnlockablePCWallpaper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TextAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType

/**
 * A data registry for unlockable wallpapers that can be applied to a PC box. This is different to a resource packed
 * wallpaper, as those are purely client-side and don't require any unlocking.
 *
 * See docs/pc_wallpapers.md
 *
 * @author Hiroku
 * @since February 9th, 2025
 */
public final class CobblemonUnlockableWallpapers : JsonDataRegistry<UnlockablePCWallpaper> {
    override val Gson gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(Component.class, TextAdapter)
        .create()

    override val typeToken = TypeToken.get(UnlockablePCWallpaper.class)
    override val resourcePath = "unlockable_pc_box_wallpapers"
    override val ResourceLocation id = cobblemonResource("unlockable_pc_box_wallpapers")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonUnlockableWallpapers>()

    val unlockableWallpapers = mutableMapOf<ResourceLocation, UnlockablePCWallpaper>()

    override fun sync(ServerPlayer player) { /* These don't sync, they're applied to a PC instance from which they're hooked into RequestChangePCBoxWallpaperHandler. */ }

    override fun reload(data: Map<ResourceLocation, UnlockablePCWallpaper>) {
        unlockableWallpapers.clear()
        data.forEach { (id, value) -> value.id = id }
        unlockableWallpapers.putAll(data)
        observable.emit(this)
    }
}