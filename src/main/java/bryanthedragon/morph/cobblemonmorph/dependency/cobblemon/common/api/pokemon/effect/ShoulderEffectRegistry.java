/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.effects.PotionBaseEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010%\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u0012J\u001f\u0010\u0006\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\t\u001a\u00020\u00022\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u000f\u0010\u0013\u001a\u00020\rH\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J-\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\u0013\u0010\u0015J\u001f\u0010\u0016\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0007R\u001f\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR(\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffectRegistry;", "", "", "name", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "get", "(Ljava/lang/String;)Ljava/lang/Class;", "clazz", "getName", "(Ljava/lang/Class;)Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "onEffectEnd", "(Lnet/minecraft/server/level/ServerPlayer;)V", "refreshEffects", "register$common", "()V", "register", "effect", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Class;", "unregister", "POTION_EFFECT", "Ljava/lang/Class;", "getPOTION_EFFECT", "()Ljava/lang/Class;", "", "effects", "Ljava/util/Map;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nShoulderEffectRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShoulderEffectRegistry.kt\ncom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffectRegistry\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,67:1\n1#2:68\n766#3:69\n857#3,2:70\n1855#3:72\n1855#3,2:73\n1856#3:75\n*S KotlinDebug\n*F\n+ 1 ShoulderEffectRegistry.kt\ncom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffectRegistry\n*L\n56#1:69\n56#1:70,2\n56#1:72\n57#1:73,2\n56#1:75\n*E\n"})
public final class ShoulderEffectRegistry {
    @NotNull
    public static final ShoulderEffectRegistry INSTANCE = new ShoulderEffectRegistry();
    @NotNull
    private static final Map<String, Class<? extends ShoulderEffect>> effects = new LinkedHashMap();
    @NotNull
    private static final Class<? extends ShoulderEffect> POTION_EFFECT = INSTANCE.register("potion_effect", PotionBaseEffect.class);

    private ShoulderEffectRegistry() {
    }

    @NotNull
    public final Class<? extends ShoulderEffect> getPOTION_EFFECT() {
        return POTION_EFFECT;
    }

    public final void register$common() {
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGIN, null, (Function1)new Function1<ServerPlayerEvent.Login, Unit>(this){
            final /* synthetic */ ShoulderEffectRegistry this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayerEvent.Login it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                ShoulderEffectRegistry.access$refreshEffects(this.this$0, it.getPlayer());
            }
        }, 1, null);
    }

    @NotNull
    public final Class<? extends ShoulderEffect> register(@NotNull String name, @NotNull Class<? extends ShoulderEffect> effect) {
        Class<? extends ShoulderEffect> clazz;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(effect, (String)"effect");
        Class<? extends ShoulderEffect> it = clazz = effect;
        boolean bl = false;
        effects.put(name, it);
        return clazz;
    }

    @Nullable
    public final Class<? extends ShoulderEffect> unregister(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return effects.remove(name);
    }

    @NotNull
    public final String getName(@NotNull Class<? extends ShoulderEffect> clazz) {
        String string;
        block3: {
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            for (Map.Entry<String, Class<? extends ShoulderEffect>> it : effects.entrySet()) {
                boolean bl = false;
                string = Intrinsics.areEqual(it.getValue(), clazz) ? it.getKey() : null;
                if (string == null) {
                    continue;
                }
                break block3;
            }
            string = null;
        }
        if (string == null) {
            throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
        }
        return string;
    }

    @Nullable
    public final Class<? extends ShoulderEffect> get(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return effects.get(name);
    }

    @ApiStatus.Internal
    public final void onEffectEnd(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        ServerTaskTracker.INSTANCE.momentarily((Function0<Unit>)((Function0)new Function0<Unit>(this, player){
            final /* synthetic */ ShoulderEffectRegistry this$0;
            final /* synthetic */ ServerPlayer $player;
            {
                this.this$0 = $receiver;
                this.$player = $player;
                super(0);
            }

            public final void invoke() {
                ShoulderEffectRegistry.access$refreshEffects(this.this$0, this.$player);
            }
        }));
    }

    /*
     * WARNING - void declaration
     */
    private final void refreshEffects(ServerPlayer player) {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = PlayerExtensionsKt.party(player);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Pokemon it = (Pokemon)element$iv$iv;
            boolean bl = false;
            if (!(it.getState() instanceof ShoulderedState)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon pkm = (Pokemon)element$iv;
            boolean bl = false;
            Iterable $this$forEach$iv2 = pkm.getForm().getShoulderEffects();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                ShoulderEffect it = (ShoulderEffect)element$iv2;
                boolean bl2 = false;
                PokemonState pokemonState = pkm.getState();
                Intrinsics.checkNotNull((Object)pokemonState, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState");
                it.applyEffect(pkm, player, ((ShoulderedState)pokemonState).isLeftShoulder());
            }
        }
    }

    public static final /* synthetic */ void access$refreshEffects(ShoulderEffectRegistry $this, ServerPlayer player) {
        $this.refreshEffects(player);
    }
}

