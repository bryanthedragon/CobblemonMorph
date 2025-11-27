/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataProvider {
    @NotNull
    public <T extends DataRegistry> T register(@NotNull T var1);

    @Nullable
    public DataRegistry fromIdentifier(@NotNull ResourceLocation var1);

    public void sync(@NotNull ServerPlayer var1);

    public void doAfterSync(@NotNull ServerPlayer var1, @NotNull Function0<Unit> var2);
}

