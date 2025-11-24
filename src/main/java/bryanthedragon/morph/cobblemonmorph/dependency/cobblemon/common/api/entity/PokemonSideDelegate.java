/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0006\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\n\u001a\u00020\u00052\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "changePokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lnet/minecraft/world/damagesource/DamageSource;", "source", "drop", "(Lnet/minecraft/world/damagesource/DamageSource;)V", "", "status", "handleStatus", "(B)V", "updatePostDeath", "()V", "common"})
public interface PokemonSideDelegate
extends EntitySideDelegate<PokemonEntity> {
    public void changePokemon(@NotNull Pokemon var1);

    public void drop(@Nullable DamageSource var1);

    public void updatePostDeath();

    public void handleStatus(byte var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void drop(@NotNull PokemonSideDelegate $this, @Nullable DamageSource source) {
        }

        public static void updatePostDeath(@NotNull PokemonSideDelegate $this) {
        }

        public static void handleStatus(@NotNull PokemonSideDelegate $this, byte status) {
        }

        public static void initialize(@NotNull PokemonSideDelegate $this, @NotNull PokemonEntity entity2) {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            EntitySideDelegate.DefaultImpls.initialize($this, (Entity)entity2);
        }

        public static void tick(@NotNull PokemonSideDelegate $this, @NotNull PokemonEntity entity2) {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            EntitySideDelegate.DefaultImpls.tick($this, (Entity)entity2);
        }

        public static void onTrackedDataSet(@NotNull PokemonSideDelegate $this, @NotNull EntityDataAccessor<?> data) {
            Intrinsics.checkNotNullParameter(data, (String)"data");
            EntitySideDelegate.DefaultImpls.onTrackedDataSet($this, data);
        }
    }
}

