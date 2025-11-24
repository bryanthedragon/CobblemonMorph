/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.EntityEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000f\u001a\u00020\f\u00a2\u0006\u0004\b\u001f\u0010 J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R*\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u000e\"\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker;", "", "", "forceWipe", "()V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "saveToNbt", "()Lnet/minecraft/nbt/CompoundTag;", "Ljava/util/concurrent/CompletableFuture;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "wipe", "()Ljava/util/concurrent/CompletableFuture;", "entity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;", "mockEffect", "Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;", "getMockEffect", "()Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;", "setMockEffect", "(Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;)V", "progress", "Ljava/util/concurrent/CompletableFuture;", "getProgress", "setProgress", "(Ljava/util/concurrent/CompletableFuture;)V", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "common"})
@SourceDebugExtension(value={"SMAP\nEffectTracker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EffectTracker.kt\ncom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n1#2:55\n*E\n"})
public final class EffectTracker {
    @NotNull
    private final PokemonEntity entity;
    @Nullable
    private CompletableFuture<PokemonEntity> progress;
    @Nullable
    private MocKEffect mockEffect;

    public EffectTracker(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.entity = entity2;
    }

    @NotNull
    public final PokemonEntity getEntity() {
        return this.entity;
    }

    @Nullable
    public final CompletableFuture<PokemonEntity> getProgress() {
        return this.progress;
    }

    public final void setProgress(@Nullable CompletableFuture<PokemonEntity> completableFuture) {
        this.progress = completableFuture;
    }

    @Nullable
    public final MocKEffect getMockEffect() {
        return this.mockEffect;
    }

    public final void setMockEffect(@Nullable MocKEffect mocKEffect) {
        this.mockEffect = mocKEffect;
    }

    @Nullable
    public final CompletableFuture<PokemonEntity> wipe() {
        MocKEffect mocKEffect = this.mockEffect;
        return mocKEffect != null ? mocKEffect.end(this.entity) : null;
    }

    public final void forceWipe() {
        this.mockEffect = null;
    }

    @NotNull
    public final CompoundTag saveToNbt() {
        CompoundTag nbt;
        block0: {
            nbt = new CompoundTag();
            MocKEffect mocKEffect = this.mockEffect;
            if (mocKEffect == null) break block0;
            MocKEffect effect = mocKEffect;
            boolean bl = false;
            nbt.m_128365_("EntityEffectMock", (Tag)effect.saveToNbt());
        }
        return nbt;
    }

    /*
     * Unable to fully structure code
     */
    public final void loadFromNBT(@NotNull CompoundTag nbt) {
        block2: {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            if (!nbt.m_128441_("EntityEffectMock")) break block2;
            mockTag = nbt.m_128469_("EntityEffectMock");
            v0 = this;
            Intrinsics.checkNotNullExpressionValue((Object)mockTag, (String)"mockTag");
            v1 = EntityEffect.Companion.loadFromNbt(mockTag);
            if (v1 == null) ** GOTO lbl-1000
            var4_4 = var3_3 = v1;
            var6_5 = v0;
            $i$a$-takeIf-EffectTracker$loadFromNBT$1 = false;
            var7_7 = it instanceof MocKEffect;
            v0 = var6_5;
            v1 = var7_7 != false ? var3_3 : null;
            if (v1 != null) {
                it = v1;
                var6_5 = v0;
                $i$a$-let-EffectTracker$loadFromNBT$2 = false;
                v2 = (MocKEffect)it;
                v0 = var6_5;
            } else lbl-1000:
            // 2 sources

            {
                v2 = null;
            }
            v0.mockEffect = v2;
        }
    }
}

