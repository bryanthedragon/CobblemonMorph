/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.EntityEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H$\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ%\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H$\u00a2\u0006\u0004\b\u000b\u0010\bJ\u001f\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\nR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/effects/BattleEffect;", "Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Ljava/util/concurrent/CompletableFuture;", "future", "", "apply", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Ljava/util/concurrent/CompletableFuture;)V", "end", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Ljava/util/concurrent/CompletableFuture;", "revert", "start", "", "battleOnly", "Z", "getBattleOnly", "()Z", "<init>", "()V", "common"})
public abstract class BattleEffect
implements EntityEffect {
    private final boolean battleOnly;

    public boolean getBattleOnly() {
        return this.battleOnly;
    }

    @Override
    @Nullable
    public CompletableFuture<PokemonEntity> start(@NotNull PokemonEntity entity2) {
        Boolean progress2;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        CompletableFuture<PokemonEntity> completableFuture = entity2.getEffects().getProgress();
        Boolean bl = progress2 = completableFuture != null ? Boolean.valueOf(completableFuture.isDone()) : null;
        if (Intrinsics.areEqual((Object)progress2, (Object)true) || progress2 == null) {
            CompletableFuture<PokemonEntity> future2 = new CompletableFuture<PokemonEntity>();
            entity2.getEffects().setProgress(future2);
            this.apply(entity2, future2);
            return future2;
        }
        return null;
    }

    @Override
    @Nullable
    public CompletableFuture<PokemonEntity> end(@NotNull PokemonEntity entity2) {
        Boolean progress2;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        CompletableFuture<PokemonEntity> completableFuture = entity2.getEffects().getProgress();
        Boolean bl = progress2 = completableFuture != null ? Boolean.valueOf(completableFuture.isDone()) : null;
        if (Intrinsics.areEqual((Object)progress2, (Object)true) || progress2 == null) {
            CompletableFuture<PokemonEntity> future2 = new CompletableFuture<PokemonEntity>();
            entity2.getEffects().setProgress(future2);
            this.revert(entity2, future2);
            return future2;
        }
        return null;
    }

    protected abstract void apply(@NotNull PokemonEntity var1, @NotNull CompletableFuture<PokemonEntity> var2);

    protected abstract void revert(@NotNull PokemonEntity var1, @NotNull CompletableFuture<PokemonEntity> var2);
}

