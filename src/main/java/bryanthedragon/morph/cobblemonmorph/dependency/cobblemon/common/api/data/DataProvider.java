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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J%\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\f\u0010\rJ!\u0010\u0010\u001a\u00028\u0000\"\b\b\u0000\u0010\u000e*\u00020\u000b2\u0006\u0010\u000f\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/data/DataProvider;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lkotlin/Function0;", "", "action", "doAfterSync", "(Lnet/minecraft/server/level/ServerPlayer;Lkotlin/jvm/functions/Function0;)V", "Lnet/minecraft/resources/ResourceLocation;", "registryIdentifier", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "fromIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/data/DataRegistry;", "T", "registry", "register", "(Lcom/cobblemon/mod/common/api/data/DataRegistry;)Lcom/cobblemon/mod/common/api/data/DataRegistry;", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
public interface DataProvider {
    @NotNull
    public <T extends DataRegistry> T register(@NotNull T var1);

    @Nullable
    public DataRegistry fromIdentifier(@NotNull ResourceLocation var1);

    public void sync(@NotNull ServerPlayer var1);

    public void doAfterSync(@NotNull ServerPlayer var1, @NotNull Function0<Unit> var2);
}

