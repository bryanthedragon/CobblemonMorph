/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.MoonPhase;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\t\b\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fB\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u0010J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/MoonPhaseRequirement;", "Lcom/cobblemon/mod/common/pokemon/evolution/requirements/template/EntityQueryRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/entity/LivingEntity;", "queriedEntity", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/LivingEntity;)Z", "Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "moonPhase", "Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "getMoonPhase", "()Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "<init>", "()V", "(Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;)V", "Companion", "common"})
public final class MoonPhaseRequirement
implements EntityQueryRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MoonPhase moonPhase;
    @NotNull
    public static final String ADAPTER_VARIANT = "moon_phase";

    public MoonPhaseRequirement(@NotNull MoonPhase moonPhase2) {
        Intrinsics.checkNotNullParameter((Object)((Object)moonPhase2), (String)"moonPhase");
        this.moonPhase = moonPhase2;
    }

    public MoonPhaseRequirement() {
        this(MoonPhase.FULL_MOON);
    }

    @NotNull
    public final MoonPhase getMoonPhase() {
        return this.moonPhase;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon, @NotNull LivingEntity queriedEntity) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)queriedEntity, (String)"queriedEntity");
        try {
            Level level = queriedEntity.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"queriedEntity.world");
            MoonPhase moonPhase2 = MoonPhase.Companion.ofWorld(level);
            bl = this.moonPhase == moonPhase2;
        }
        catch (IndexOutOfBoundsException e) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/MoonPhaseRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

