/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.goals;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u000f\u0012\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\u0004J\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonFloatToSurfaceGoal;", "Lnet/minecraft/world/entity/ai/goal/Goal;", "", "canStart", "()Z", "Ljava/util/EnumSet;", "Lnet/minecraft/entity/ai/goal/Goal$Control;", "getControls", "()Ljava/util/EnumSet;", "shouldRunEveryTick", "", "tick", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Companion", "common"})
public final class PokemonFloatToSurfaceGoal
extends Goal {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokemonEntity pokemonEntity;
    private static final EnumSet<Goal.Flag> controls = EnumSet.of((Enum)Goal.Flag.JUMP);

    public PokemonFloatToSurfaceGoal(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        this.pokemonEntity = pokemonEntity;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    @NotNull
    public EnumSet<Goal.Flag> m_7684_() {
        EnumSet<Goal.Flag> enumSet = controls;
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"controls");
        return enumSet;
    }

    public boolean m_8036_() {
        boolean canSwimInWater = this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanSwimInWater();
        boolean canSwimInLava = this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanSwimInLava();
        boolean canBreatheUnderlava = this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanBreatheUnderlava();
        boolean canBreatheUnderwater = this.pokemonEntity.getBehaviour().getMoving().getSwim().getCanBreatheUnderwater();
        if (!this.pokemonEntity.getNavigation().m_26571_()) {
            return false;
        }
        if (this.pokemonEntity.m_20077_() && !canBreatheUnderlava) {
            return true;
        }
        return canSwimInWater && !canBreatheUnderwater && this.pokemonEntity.m_20069_() && this.pokemonEntity.m_204036_(FluidTags.f_13131_) > this.pokemonEntity.m_20204_();
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        if (this.pokemonEntity.m_217043_().m_188501_() < 0.8f) {
            this.pokemonEntity.m_21569_().m_24901_();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/ai/goals/PokemonFloatToSurfaceGoal$Companion;", "", "Ljava/util/EnumSet;", "Lnet/minecraft/entity/ai/goal/Goal$Control;", "kotlin.jvm.PlatformType", "controls", "Ljava/util/EnumSet;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

