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
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0019\u0010\t\u001a\u0004\u0018\u00010\u00068\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\t\u0010\u000bR\u0019\u0010\f\u001a\u0004\u0018\u00010\u00068\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\n\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/WeatherRequirement;", "Lcom/cobblemon/mod/common/pokemon/evolution/requirements/template/EntityQueryRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/entity/LivingEntity;", "queriedEntity", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/LivingEntity;)Z", "isRaining", "Ljava/lang/Boolean;", "()Ljava/lang/Boolean;", "isThundering", "<init>", "()V", "Companion", "common"})
public final class WeatherRequirement
implements EntityQueryRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final Boolean isRaining;
    @Nullable
    private final Boolean isThundering;
    @NotNull
    public static final String ADAPTER_VARIANT = "weather";

    @Nullable
    public final Boolean isRaining() {
        return this.isRaining;
    }

    @Nullable
    public final Boolean isThundering() {
        return this.isThundering;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon, @NotNull LivingEntity queriedEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)queriedEntity, (String)"queriedEntity");
        Level world = queriedEntity.m_9236_();
        return Intrinsics.areEqual((Object)this.isRaining, (Object)true) && !world.m_46471_() ? false : (Intrinsics.areEqual((Object)this.isRaining, (Object)false) && world.m_46471_() ? false : (Intrinsics.areEqual((Object)this.isThundering, (Object)true) && !world.m_46470_() ? false : !Intrinsics.areEqual((Object)this.isThundering, (Object)false) || !world.m_46470_()));
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/WeatherRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

