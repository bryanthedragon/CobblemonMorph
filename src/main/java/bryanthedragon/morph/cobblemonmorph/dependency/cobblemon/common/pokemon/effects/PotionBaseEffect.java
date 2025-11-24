/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.effects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.StatusEffectInstanceAccessor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001:\u0001#B/\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u000f\u001a\u00020\u0006\u0012\u0006\u0010\u001f\u001a\u00020\u0006\u0012\u0006\u0010\u001d\u001a\u00020\u0006\u00a2\u0006\u0004\b!\u0010\"J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000e\u0010\nR\u0017\u0010\u000f\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0010\u001a\u0004\b\u001e\u0010\u0012R\u0017\u0010\u001f\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0010\u001a\u0004\b \u0010\u0012\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/pokemon/effects/PotionBaseEffect;", "Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "isLeft", "", "applyEffect", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/server/level/ServerPlayer;Z)V", "Lcom/cobblemon/mod/common/pokemon/effects/PotionBaseEffect$ShoulderStatusEffectInstance;", "createStatus", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/pokemon/effects/PotionBaseEffect$ShoulderStatusEffectInstance;", "removeEffect", "ambient", "Z", "getAmbient", "()Z", "", "amplifier", "I", "getAmplifier", "()I", "Lnet/minecraft/world/effect/MobEffect;", "effect", "Lnet/minecraft/world/effect/MobEffect;", "getEffect", "()Lnet/minecraft/world/effect/MobEffect;", "showIcon", "getShowIcon", "showParticles", "getShowParticles", "<init>", "(Lnet/minecraft/world/effect/MobEffect;IZZZ)V", "ShoulderStatusEffectInstance", "common"})
public final class PotionBaseEffect
implements ShoulderEffect {
    @NotNull
    private final MobEffect effect;
    private final int amplifier;
    private final boolean ambient;
    private final boolean showParticles;
    private final boolean showIcon;

    public PotionBaseEffect(@NotNull MobEffect effect, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        this.effect = effect;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
    }

    @NotNull
    public final MobEffect getEffect() {
        return this.effect;
    }

    public final int getAmplifier() {
        return this.amplifier;
    }

    public final boolean getAmbient() {
        return this.ambient;
    }

    public final boolean getShowParticles() {
        return this.showParticles;
    }

    public final boolean getShowIcon() {
        return this.showIcon;
    }

    @Override
    public void applyEffect(@NotNull Pokemon pokemon, @NotNull ServerPlayer player, boolean isLeft) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        MobEffectInstance effect = player.m_21124_(this.effect);
        if (effect instanceof ShoulderStatusEffectInstance && ((ShoulderStatusEffectInstance)effect).m_19564_() >= this.amplifier) {
            if (((ShoulderStatusEffectInstance)effect).m_19564_() == this.amplifier) {
                Set<UUID> set2 = ((ShoulderStatusEffectInstance)effect).getShoulderSources$common();
                UUID uUID = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                set2.add(uUID);
            }
            return;
        }
        player.m_7292_((MobEffectInstance)this.createStatus(pokemon));
    }

    @Override
    public void removeEffect(@NotNull Pokemon pokemon, @NotNull ServerPlayer player, boolean isLeft) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        MobEffectInstance mobEffectInstance = player.m_21124_(this.effect);
        ShoulderStatusEffectInstance shoulderStatusEffectInstance = mobEffectInstance instanceof ShoulderStatusEffectInstance ? (ShoulderStatusEffectInstance)mobEffectInstance : null;
        if (shoulderStatusEffectInstance == null) {
            return;
        }
        ShoulderStatusEffectInstance effect = shoulderStatusEffectInstance;
        if (effect.m_19564_() == this.amplifier && effect.f_19506_ == this.ambient && effect.m_19572_() == this.showParticles && effect.m_19575_() == this.showIcon) {
            effect.getShoulderSources$common().remove(pokemon.getUuid());
        }
    }

    private final ShoulderStatusEffectInstance createStatus(Pokemon pokemon) {
        return new ShoulderStatusEffectInstance(this.effect, this.amplifier, this.ambient, this.showParticles, this.showIcon, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010#\u001a\u00020\u0007\u0012\u0006\u0010$\u001a\u00020\u0007\u0012\u0006\u0010%\u001a\u00020\u0007\u0012\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b(\u0010)J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\f\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0001H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0000H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00018\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u001e\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/pokemon/effects/PotionBaseEffect$ShoulderStatusEffectInstance;", "Lnet/minecraft/world/effect/MobEffectInstance;", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "", "applyUpdateEffect", "(Lnet/minecraft/world/entity/LivingEntity;)V", "", "isInfinite", "()Z", "Ljava/lang/Runnable;", "overwriteCallback", "update", "(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/Runnable;)Z", "that", "upgrade", "(Lnet/minecraft/world/effect/MobEffectInstance;)Z", "other", "upgradeFrom", "(Lcom/cobblemon/mod/common/pokemon/effects/PotionBaseEffect$ShoulderStatusEffectInstance;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "writeNbt", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "Ljava/util/UUID;", "shoulderSources", "Ljava/util/Set;", "getShoulderSources$common", "()Ljava/util/Set;", "Lnet/minecraft/world/effect/MobEffectInstance;", "Lnet/minecraft/world/effect/MobEffect;", "effect", "", "amplifier", "ambient", "showParticles", "showIcon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "startingPokemon", "<init>", "(Lnet/minecraft/world/effect/MobEffect;IZZZLcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
    public static final class ShoulderStatusEffectInstance
    extends MobEffectInstance {
        @NotNull
        private final Set<UUID> shoulderSources;
        @Nullable
        private MobEffectInstance upgrade;

        public ShoulderStatusEffectInstance(@NotNull MobEffect effect, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @NotNull Pokemon startingPokemon) {
            Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
            Intrinsics.checkNotNullParameter((Object)startingPokemon, (String)"startingPokemon");
            super(effect, -1, amplifier, ambient, showParticles, showIcon);
            Object[] objectArray = new UUID[1];
            Intrinsics.checkNotNullExpressionValue((Object)startingPokemon.getUuid(), (String)"startingPokemon.uuid");
            this.shoulderSources = SetsKt.hashSetOf((Object[])objectArray);
        }

        @NotNull
        public final Set<UUID> getShoulderSources$common() {
            return this.shoulderSources;
        }

        @NotNull
        public CompoundTag m_19555_(@NotNull CompoundTag nbt) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            nbt.m_128405_("Id", -999);
            return nbt;
        }

        public boolean m_267577_() {
            return !((Collection)this.shoulderSources).isEmpty();
        }

        public boolean m_19558_(@NotNull MobEffectInstance that) {
            Intrinsics.checkNotNullParameter((Object)that, (String)"that");
            if (that.m_19564_() > this.m_19564_()) {
                if (that instanceof ShoulderStatusEffectInstance) {
                    this.upgradeFrom((ShoulderStatusEffectInstance)that);
                    return true;
                }
                this.upgrade = that;
                return true;
            }
            return false;
        }

        public boolean m_19552_(@NotNull LivingEntity entity2, @NotNull Runnable overwriteCallback) {
            block2: {
                Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
                Intrinsics.checkNotNullParameter((Object)overwriteCallback, (String)"overwriteCallback");
                if (this.m_19544_().m_6584_(entity2.f_19797_, this.m_19564_())) {
                    this.m_19550_(entity2);
                }
                MobEffectInstance mobEffectInstance = this.upgrade;
                if (mobEffectInstance == null) break block2;
                MobEffectInstance it = mobEffectInstance;
                boolean bl = false;
                it.f_19503_ += -1;
                if (it.f_19503_ == 0) {
                    this.upgrade = null;
                    overwriteCallback.run();
                }
            }
            return !((Collection)this.shoulderSources).isEmpty();
        }

        public void m_19550_(@NotNull LivingEntity entity2) {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            MobEffectInstance mobEffectInstance = this.upgrade;
            this.m_19544_().m_6742_(entity2, mobEffectInstance != null ? mobEffectInstance.m_19564_() : this.m_19564_());
        }

        private final void upgradeFrom(ShoulderStatusEffectInstance other) {
            this.shoulderSources.clear();
            CollectionsKt.addAll((Collection)this.shoulderSources, (Iterable)other.shoulderSources);
            this.f_19506_ = other.f_19506_;
            Intrinsics.checkNotNull((Object)((Object)this), (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.StatusEffectInstanceAccessor");
            StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor)((Object)this);
            accessor.setAmplifier(other.m_19564_());
            accessor.setShowIcon(other.m_19575_());
            accessor.setShowParticles(other.m_19572_());
        }
    }
}

