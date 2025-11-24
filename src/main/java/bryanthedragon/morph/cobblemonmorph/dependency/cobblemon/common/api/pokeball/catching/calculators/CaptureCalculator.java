/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokemonCatchRateEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J/\u0010\n\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH&\u00a2\u0006\u0004\b\r\u0010\u000eJ'\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "target", "", "catchRate", "getCatchRate", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;F)F", "", "id", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "processCapture", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "common"})
public interface CaptureCalculator {
    @NotNull
    public String id();

    @NotNull
    public CaptureContext processCapture(@NotNull LivingEntity var1, @NotNull EmptyPokeBallEntity var2, @NotNull PokemonEntity var3);

    public float getCatchRate(@NotNull LivingEntity var1, @NotNull EmptyPokeBallEntity var2, @NotNull PokemonEntity var3, float var4);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nCaptureCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CaptureCalculator.kt\ncom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator$DefaultImpls\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,60:1\n14#2,5:61\n19#2:69\n13579#3:66\n13580#3:68\n14#4:67\n*S KotlinDebug\n*F\n+ 1 CaptureCalculator.kt\ncom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator$DefaultImpls\n*L\n56#1:61,5\n56#1:69\n56#1:66\n56#1:68\n56#1:67\n*E\n"})
    public static final class DefaultImpls {
        /*
         * WARNING - void declaration
         */
        public static float getCatchRate(@NotNull CaptureCalculator $this, @NotNull LivingEntity thrower, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull PokemonEntity target, float catchRate) {
            void $this$iv;
            Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
            Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            PokemonCatchRateEvent event = new PokemonCatchRateEvent(thrower, pokeBallEntity, target, catchRate);
            EventObservable<PokemonCatchRateEvent> eventObservable = CobblemonEvents.POKEMON_CATCH_RATE;
            PokemonCatchRateEvent[] pokemonCatchRateEventArray = new PokemonCatchRateEvent[]{event};
            PokemonCatchRateEvent[] events$iv = pokemonCatchRateEventArray;
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            PokemonCatchRateEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                PokemonCatchRateEvent element$iv$iv;
                PokemonCatchRateEvent pokemonCatchRateEvent = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                PokemonCatchRateEvent it = pokemonCatchRateEvent;
            }
            return event.getCatchRate();
        }
    }
}

