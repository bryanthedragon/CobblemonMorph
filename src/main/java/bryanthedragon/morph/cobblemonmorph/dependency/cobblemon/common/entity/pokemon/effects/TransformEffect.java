/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.BattleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects.PokemonAspectsKt;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)B\u001b\b\u0016\u0012\u0006\u0010%\u001a\u00020$\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b&\u0010'B%\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001d\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b&\u0010(J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ%\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0014\u00a2\u0006\u0004\b\u000e\u0010\tJ\u000f\u0010\u000f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u00020\u00168\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u001d8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/effects/TransformEffect;", "Lcom/cobblemon/mod/common/entity/pokemon/effects/BattleEffect;", "Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Ljava/util/concurrent/CompletableFuture;", "future", "", "apply", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Ljava/util/concurrent/CompletableFuture;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "revert", "saveToNbt", "()Lnet/minecraft/nbt/CompoundTag;", "", "doCry", "Z", "getDoCry", "()Z", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "mock", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getMock", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "setMock", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "", "scale", "F", "getScale", "()F", "setScale", "(F)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "mimic", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;FZ)V", "Companion", "common"})
public final class TransformEffect
extends BattleEffect
implements MocKEffect {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private PokemonProperties mock;
    private float scale;
    private final boolean doCry;
    @NotNull
    private static final String ID = "TRANSFORM";

    public TransformEffect(@NotNull PokemonProperties mock, float scale, boolean doCry) {
        Intrinsics.checkNotNullParameter((Object)mock, (String)"mock");
        this.mock = mock;
        this.scale = scale;
        this.doCry = doCry;
    }

    public /* synthetic */ TransformEffect(PokemonProperties pokemonProperties, float f, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            pokemonProperties = new PokemonProperties();
        }
        if ((n & 2) != 0) {
            f = 1.0f;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        this(pokemonProperties, f, bl);
    }

    @Override
    @NotNull
    public PokemonProperties getMock() {
        return this.mock;
    }

    public void setMock(@NotNull PokemonProperties pokemonProperties) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"<set-?>");
        this.mock = pokemonProperties;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public final boolean getDoCry() {
        return this.doCry;
    }

    public TransformEffect(@NotNull Pokemon mimic, boolean doCry) {
        Intrinsics.checkNotNullParameter((Object)mimic, (String)"mimic");
        this(mimic.createPokemonProperties(PokemonPropertyExtractor.TRANSFORM), mimic.getForm().getBaseScale() * mimic.getScaleModifier(), doCry);
    }

    public /* synthetic */ TransformEffect(Pokemon pokemon, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        this(pokemon, bl);
    }

    @Override
    protected void apply(@NotNull PokemonEntity entity2, @NotNull CompletableFuture<PokemonEntity> future2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(future2, (String)"future");
        PokemonProperties pokemonProperties = this.getMock();
        pokemonProperties.setAspects(SetsKt.plus(pokemonProperties.getAspects(), (Iterable)PokemonAspectsKt.getSHINY_ASPECT().provide(entity2.getPokemon())));
        entity2.getEffects().setMockEffect(this);
        SchedulingFunctionsKt.afterOnServer$default(0, 1.0f, (Function0)new Function0<Unit>(this, entity2, future2){
            final /* synthetic */ TransformEffect this$0;
            final /* synthetic */ PokemonEntity $entity;
            final /* synthetic */ CompletableFuture<PokemonEntity> $future;
            {
                this.this$0 = $receiver;
                this.$entity = $entity;
                this.$future = $future;
                super(0);
            }

            public final void invoke() {
                if (this.this$0.getDoCry()) {
                    this.$entity.cry();
                }
                this.$future.complete(this.$entity);
            }
        }, 1, null);
    }

    @Override
    protected void revert(@NotNull PokemonEntity entity2, @NotNull CompletableFuture<PokemonEntity> future2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(future2, (String)"future");
        entity2.getEffects().setMockEffect(null);
        SchedulingFunctionsKt.afterOnServer$default(0, 1.0f, (Function0)new Function0<Unit>(entity2, future2){
            final /* synthetic */ PokemonEntity $entity;
            final /* synthetic */ CompletableFuture<PokemonEntity> $future;
            {
                this.$entity = $entity;
                this.$future = $future;
                super(0);
            }

            public final void invoke() {
                this.$entity.cry();
                this.$future.complete(this.$entity);
            }
        }, 1, null);
    }

    @Override
    @NotNull
    public CompoundTag saveToNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_("EntityEffectMock", ID);
        nbt.m_128365_("PokemonEntityMock", (Tag)this.getMock().saveToNBT());
        nbt.m_128350_("PokemonEntityScale", this.getScale());
        return nbt;
    }

    @Override
    public void loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        if (nbt.m_128441_("PokemonEntityMock")) {
            PokemonProperties pokemonProperties = new PokemonProperties();
            CompoundTag compoundTag = nbt.m_128469_("PokemonEntityMock");
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.POKEMON_ENTITY_MOCK)");
            this.setMock(pokemonProperties.loadFromNBT(compoundTag));
        }
        if (nbt.m_128441_("PokemonEntityScale")) {
            this.setScale(nbt.m_128457_("PokemonEntityScale"));
        }
    }

    @Override
    @Nullable
    public Species getExposedSpecies() {
        return MocKEffect.DefaultImpls.getExposedSpecies(this);
    }

    @Override
    @Nullable
    public FormData getExposedForm() {
        return MocKEffect.DefaultImpls.getExposedForm(this);
    }

    public TransformEffect() {
        this(null, 0.0f, false, 7, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/effects/TransformEffect$Companion;", "", "", "ID", "Ljava/lang/String;", "getID", "()Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final String getID() {
            return ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

