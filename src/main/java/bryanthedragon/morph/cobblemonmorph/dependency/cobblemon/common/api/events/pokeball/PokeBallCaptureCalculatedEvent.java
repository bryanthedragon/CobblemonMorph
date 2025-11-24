/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\"\u0010#J;\u0010\n\u001a\u00020\u00072,\u0010\t\u001a(\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u00a2\u0006\u0002\b\b\u00a2\u0006\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001e\u001a\u00020\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/api/events/pokeball/PokeBallCaptureCalculatedEvent;", "", "Lkotlin/Function2;", "Lnet/minecraft/server/level/ServerPlayer;", "Lkotlin/ParameterName;", "name", "player", "", "Lkotlin/ExtensionFunctionType;", "action", "ifPlayer", "(Lkotlin/jvm/functions/Function2;)V", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "captureResult", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "getCaptureResult", "()Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "setCaptureResult", "(Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;)V", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getPokeBallEntity", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getPokemonEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lnet/minecraft/world/entity/LivingEntity;", "getThrower", "()Lnet/minecraft/world/entity/LivingEntity;", "<init>", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;)V", "common"})
public final class PokeBallCaptureCalculatedEvent {
    @NotNull
    private final LivingEntity thrower;
    @NotNull
    private final PokemonEntity pokemonEntity;
    @NotNull
    private final EmptyPokeBallEntity pokeBallEntity;
    @NotNull
    private CaptureContext captureResult;

    public PokeBallCaptureCalculatedEvent(@NotNull LivingEntity thrower, @NotNull PokemonEntity pokemonEntity, @NotNull EmptyPokeBallEntity pokeBallEntity, @NotNull CaptureContext captureResult) {
        Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        Intrinsics.checkNotNullParameter((Object)captureResult, (String)"captureResult");
        this.thrower = thrower;
        this.pokemonEntity = pokemonEntity;
        this.pokeBallEntity = pokeBallEntity;
        this.captureResult = captureResult;
    }

    @NotNull
    public final LivingEntity getThrower() {
        return this.thrower;
    }

    @NotNull
    public final PokemonEntity getPokemonEntity() {
        return this.pokemonEntity;
    }

    @NotNull
    public final EmptyPokeBallEntity getPokeBallEntity() {
        return this.pokeBallEntity;
    }

    @NotNull
    public final CaptureContext getCaptureResult() {
        return this.captureResult;
    }

    public final void setCaptureResult(@NotNull CaptureContext captureContext) {
        Intrinsics.checkNotNullParameter((Object)captureContext, (String)"<set-?>");
        this.captureResult = captureContext;
    }

    public final void ifPlayer(@NotNull Function2<? super PokeBallCaptureCalculatedEvent, ? super ServerPlayer, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        if (this.thrower instanceof ServerPlayer) {
            action2.invoke((Object)this, (Object)this.thrower);
        }
    }
}

