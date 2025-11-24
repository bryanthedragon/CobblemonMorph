/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/CandyExperienceSource;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/item/ItemStack;", "getStack", "()Lnet/minecraft/world/item/ItemStack;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V", "common"})
public class CandyExperienceSource
implements ExperienceSource {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final ItemStack stack;

    public CandyExperienceSource(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        this.player = player;
        this.stack = stack;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public final ItemStack getStack() {
        return this.stack;
    }

    @Override
    public boolean isBattle() {
        return ExperienceSource.DefaultImpls.isBattle(this);
    }

    @Override
    public boolean isInteraction() {
        return ExperienceSource.DefaultImpls.isInteraction(this);
    }

    @Override
    public boolean isCommand() {
        return ExperienceSource.DefaultImpls.isCommand(this);
    }

    @Override
    public boolean isSidemod() {
        return ExperienceSource.DefaultImpls.isSidemod(this);
    }
}

