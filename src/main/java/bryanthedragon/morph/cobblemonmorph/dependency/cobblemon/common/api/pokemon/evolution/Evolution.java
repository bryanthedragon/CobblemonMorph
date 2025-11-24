/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionTestedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PassiveEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\u0006J\u0017\u0010\f\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\nR\u001c\u0010\u0011\u001a\u00020\b8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0019\u001a\u00020\b8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u000e\"\u0004\b\u0018\u0010\u0010R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00128&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0015R\u0014\u0010 \u001a\u00020\u001d8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionLike;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "applyTo", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "evolutionMethod", "", "evolve", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "forceEvolve", "test", "getConsumeHeldItem", "()Z", "setConsumeHeldItem", "(Z)V", "consumeHeldItem", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getLearnableMoves", "()Ljava/util/Set;", "learnableMoves", "getOptional", "setOptional", "optional", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "getRequirements", "requirements", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "common"})
public interface Evolution
extends EvolutionLike {
    @NotNull
    public PokemonProperties getResult();

    public boolean getOptional();

    public void setOptional(boolean var1);

    public boolean getConsumeHeldItem();

    public void setConsumeHeldItem(boolean var1);

    @NotNull
    public Set<EvolutionRequirement> getRequirements();

    @NotNull
    public Set<MoveTemplate> getLearnableMoves();

    public boolean test(@NotNull Pokemon var1);

    public boolean evolve(@NotNull Pokemon var1);

    public void forceEvolve(@NotNull Pokemon var1);

    public void evolutionMethod(@NotNull Pokemon var1);

    public void applyTo(@NotNull Pokemon var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nEvolution.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Evolution.kt\ncom/cobblemon/mod/common/api/pokemon/evolution/Evolution$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,164:1\n1726#2,3:165\n1855#2,2:178\n800#2,11:180\n1855#2,2:191\n14#3,5:168\n19#3:176\n14#3,5:193\n19#3:201\n13579#4:173\n13580#4:175\n13579#4:198\n13580#4:200\n14#5:174\n14#5:199\n1#6:177\n*S KotlinDebug\n*F\n+ 1 Evolution.kt\ncom/cobblemon/mod/common/api/pokemon/evolution/Evolution$DefaultImpls\n*L\n74#1:165,3\n147#1:178,2\n156#1:180,11\n156#1:191,2\n76#1:168,5\n76#1:176\n158#1:193,5\n158#1:201\n76#1:173\n76#1:175\n158#1:198\n158#1:200\n76#1:174\n158#1:199\n*E\n"})
    public static final class DefaultImpls {
        /*
         * WARNING - void declaration
         */
        public static boolean test(@NotNull Evolution $this, @NotNull Pokemon pokemon) {
            void $this$iv;
            boolean bl;
            block4: {
                Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                Iterable $this$all$iv = $this.getRequirements();
                boolean $i$f$all = false;
                if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$all$iv) {
                        EvolutionRequirement requirement = (EvolutionRequirement)element$iv;
                        boolean bl2 = false;
                        if (requirement.check(pokemon)) continue;
                        bl = false;
                        break block4;
                    }
                    bl = true;
                }
            }
            boolean result = bl;
            EvolutionTestedEvent event = new EvolutionTestedEvent(pokemon, $this, result, result);
            EventObservable<EvolutionTestedEvent> $i$f$all = CobblemonEvents.EVOLUTION_TESTED;
            EvolutionTestedEvent[] evolutionTestedEventArray = new EvolutionTestedEvent[]{event};
            EvolutionTestedEvent[] events$iv = evolutionTestedEventArray;
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            EvolutionTestedEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                EvolutionTestedEvent element$iv$iv;
                EvolutionTestedEvent evolutionTestedEvent = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl3 = false;
                EvolutionTestedEvent it = evolutionTestedEvent;
            }
            return event.getResult();
        }

        public static boolean evolve(@NotNull Evolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            if ($this.getConsumeHeldItem()) {
                ItemStack itemStack = ItemStack.f_41583_;
                Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
                Pokemon.swapHeldItem$default(pokemon, itemStack, false, 2, null);
            }
            if ($this.getOptional()) {
                return pokemon.getEvolutionProxy().server().add($this);
            }
            $this.forceEvolve(pokemon);
            return true;
        }

        public static void forceEvolve(@NotNull Evolution $this, @NotNull Pokemon pokemon) {
            PokemonEntity pokemonEntity;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            boolean useEvolutionEffect = false;
            if (pokemon.getState() instanceof ShoulderedState) {
                pokemon.tryRecallWithAnimation();
            }
            if ((pokemonEntity = pokemon.getEntity()) != null) {
                // empty if block
            }
            $this.evolutionMethod(pokemon);
        }

        /*
         * WARNING - void declaration
         */
        public static void evolutionMethod(@NotNull Evolution $this, @NotNull Pokemon pokemon) {
            void events$iv;
            void $this$iv;
            void $this$filterIsInstanceTo$iv$iv;
            Object element$iv;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            $this.getResult().apply(pokemon);
            Object $this$forEach$iv = $this.getLearnableMoves();
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.iterator();
            while (iterator.hasNext()) {
                element$iv = iterator.next();
                MoveTemplate move = (MoveTemplate)element$iv;
                boolean bl = false;
                if (pokemon.getMoveSet().hasSpace()) {
                    pokemon.getMoveSet().add(move.create());
                } else {
                    pokemon.getBenchedMoves().add(new BenchedMove(move, 0));
                }
                ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
                if (serverPlayer == null) continue;
                Object[] objectArray = new Object[]{pokemon.getDisplayName(), move.getDisplayName()};
                serverPlayer.m_213846_((Component)LocalizationUtilsKt.lang("experience.learned_move", objectArray));
            }
            Iterable<Evolution> $this$filterIsInstance$iv = pokemon.getLockedEvolutions();
            boolean $i$f$filterIsInstance = false;
            iterator = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof PassiveEvolution)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$forEach$iv = (List)destination$iv$iv;
            $i$f$forEach = false;
            iterator = $this$forEach$iv.iterator();
            while (iterator.hasNext()) {
                element$iv = iterator.next();
                PassiveEvolution evolution = (PassiveEvolution)element$iv;
                boolean bl = false;
                evolution.attemptEvolution(pokemon);
            }
            ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
            if (serverPlayer != null) {
                serverPlayer.m_6330_(CobblemonSounds.EVOLVING, SoundSource.NEUTRAL, 1.0f, 1.0f);
            }
            $this$forEach$iv = CobblemonEvents.EVOLUTION_COMPLETE;
            EvolutionCompleteEvent[] $i$f$forEach2 = new EvolutionCompleteEvent[]{new EvolutionCompleteEvent(pokemon, $this)};
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
            void $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach3 = false;
            int n = ((void)$this$forEach$iv$iv).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void var11_20 = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                void it = var11_20;
            }
        }

        public static void applyTo(@NotNull Evolution $this, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            $this.getResult().apply(pokemon);
        }
    }
}

