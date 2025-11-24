/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SingleEntitySpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.annotations.SerializedName;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 72\u00020\u0001:\u00017B\u0007\u00a2\u0006\u0004\b6\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0019\u0010\u0015\u001a\u0004\u0018\u00010\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001f\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR$\u0010\u001f\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\r\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001b\u00100\u001a\u00020+8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u001a\u00102\u001a\u0002018\u0016X\u0096D\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "", "autoLabel", "()V", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "doSpawn", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Lcom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction;", "Lkotlin/ranges/IntRange;", "getDerivedLevelRange", "()Lkotlin/ranges/IntRange;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "isValid", "()Z", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "drops", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "getDrops", "()Lcom/cobblemon/mod/common/api/drop/DropTable;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem;", "heldItems", "Ljava/util/List;", "getHeldItems", "()Ljava/util/List;", "levelRange", "Lkotlin/ranges/IntRange;", "getLevelRange", "setLevelRange", "(Lkotlin/ranges/IntRange;)V", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "pokemon", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getPokemon", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "setPokemon", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemonExample$delegate", "Lkotlin/Lazy;", "getPokemonExample", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemonExample", "", "type", "Ljava/lang/String;", "getType", "()Ljava/lang/String;", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonSpawnDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,115:1\n1#2:116\n*E\n"})
public final class PokemonSpawnDetail
extends SpawnDetail {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String type = TYPE;
    @NotNull
    private PokemonProperties pokemon = new PokemonProperties();
    @SerializedName(value="level", alternate={"levelRange"})
    @Nullable
    private IntRange levelRange;
    @Nullable
    private final DropTable drops;
    @Nullable
    private final List<PossibleHeldItem> heldItems;
    @NotNull
    private final Lazy pokemonExample$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Pokemon>(this){
        final /* synthetic */ PokemonSpawnDetail this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Pokemon invoke() {
            return this.this$0.getPokemon().create();
        }
    }));
    @NotNull
    private static final String TYPE = "pokemon";

    @Override
    @NotNull
    public String getType() {
        return this.type;
    }

    @NotNull
    public final PokemonProperties getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull PokemonProperties pokemonProperties) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"<set-?>");
        this.pokemon = pokemonProperties;
    }

    @Nullable
    public final IntRange getLevelRange() {
        return this.levelRange;
    }

    public final void setLevelRange(@Nullable IntRange intRange) {
        this.levelRange = intRange;
    }

    @Nullable
    public final DropTable getDrops() {
        return this.drops;
    }

    @Nullable
    public final List<PossibleHeldItem> getHeldItems() {
        return this.heldItems;
    }

    private final Pokemon getPokemonExample() {
        Lazy lazy = this.pokemonExample$delegate;
        return (Pokemon)lazy.getValue();
    }

    @Override
    @NotNull
    public MutableComponent getName() {
        String string = this.getDisplayName();
        if (string != null) {
            String it = string;
            boolean bl = false;
            MutableComponent mutableComponent = MiscUtilsKt.asTranslated(it);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"it.asTranslated()");
            return mutableComponent;
        }
        String speciesString = this.pokemon.getSpecies();
        if (speciesString != null) {
            MutableComponent mutableComponent;
            String string2 = speciesString.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)string2, (Object)"random")) {
                MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("species.random", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"species.random\")");
                return mutableComponent2;
            }
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(speciesString, null, 1, null));
            if (species == null) {
                MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("species.unknown", new Object[0]);
                mutableComponent = mutableComponent3;
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"{\n                lang(\"\u2026s.unknown\")\n            }");
            } else {
                mutableComponent = species.getTranslatedName();
            }
            return mutableComponent;
        }
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("a_pokemon", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"a_pokemon\")");
        return mutableComponent;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void autoLabel() {
        VariableStruct pokemonStruct;
        block5: {
            Object object;
            List<String> list;
            block7: {
                Species species;
                block6: {
                    void it;
                    pokemonStruct = this.pokemon.asStruct();
                    if (this.pokemon.getSpecies() == null) break block5;
                    String string = this.pokemon.getSpecies();
                    Intrinsics.checkNotNull((Object)string);
                    species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
                    if (species == null) break block5;
                    list = this.getLabels();
                    object = species.getSecondaryType();
                    if (object == null) break block6;
                    ElementalType elementalType = object;
                    List<String> list2 = list;
                    boolean bl = false;
                    Object[] objectArray = new String[2];
                    Intrinsics.checkNotNullExpressionValue((Object)species.getPrimaryType().getName().toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    Intrinsics.checkNotNullExpressionValue((Object)it.getName().toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    List list3 = CollectionsKt.listOf((Object[])objectArray);
                    list = list2;
                    object = list3;
                    if (list3 != null) break block7;
                }
                String string = species.getPrimaryType().getName().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                object = CollectionsKt.listOf((Object)string);
            }
            list.addAll((Collection)object);
            if (this.getHeight() == -1) {
                this.setHeight((int)Math.ceil(this.getPokemonExample().getForm().getHitbox().f_20378_ * this.getPokemonExample().getForm().getBaseScale()));
            }
            if (this.getWidth() == -1) {
                this.setWidth((int)Math.ceil(this.getPokemonExample().getForm().getHitbox().f_20377_ * this.getPokemonExample().getForm().getBaseScale()));
            }
        }
        this.getStruct().setDirectly("pokemon", pokemonStruct);
        super.autoLabel();
    }

    @NotNull
    public final IntRange getDerivedLevelRange() {
        IntRange intRange;
        IntRange levelRange = this.levelRange;
        boolean bl = false;
        if (levelRange == null && this.pokemon.getLevel() == null) {
            intRange = new IntRange(1, Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel());
        } else if (levelRange == null) {
            Integer n = this.pokemon.getLevel();
            Intrinsics.checkNotNull((Object)n);
            int n2 = n;
            Integer n3 = this.pokemon.getLevel();
            Intrinsics.checkNotNull((Object)n3);
            intRange = new IntRange(n2, n3.intValue());
        } else {
            intRange = levelRange;
        }
        return intRange;
    }

    @Override
    public boolean isValid() {
        boolean isValidSpecies;
        boolean bl = isValidSpecies = this.pokemon.getSpecies() != null;
        if (!isValidSpecies) {
            Cobblemon.INSTANCE.getLOGGER().error("Invalid species for spawn detail: " + this.getId());
        }
        return super.isValid() && isValidSpecies;
    }

    @NotNull
    public SingleEntitySpawnAction<PokemonEntity> doSpawn(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return new PokemonSpawnAction(ctx, this, null, 4, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail$Companion;", "", "", "TYPE", "Ljava/lang/String;", "getTYPE", "()Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final String getTYPE() {
            return TYPE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

