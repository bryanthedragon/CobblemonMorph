/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00e6\u0080\u0001\u0018\u0000 \t2\u00020\u0001:\u0001\tJ \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6\u0002\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "", "invoke", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "Companion", "common"})
public interface PokemonPropertyExtractor {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE;
    @JvmField
    @NotNull
    public static final List<PokemonPropertyExtractor> ALL = new ArrayList();
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor SPECIES = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$SPECIES$lambda$0);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor FORM = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$FORM$lambda$1);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor SHINY = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$SHINY$lambda$2);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor ASPECTS = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$ASPECTS$lambda$3);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor LEVEL = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$LEVEL$lambda$4);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor GENDER = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$GENDER$lambda$5);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor FRIENDSHIP = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$FRIENDSHIP$lambda$6);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor POKEBALL = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$POKEBALL$lambda$7);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor NATURE = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$NATURE$lambda$8);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor ABILITY = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$ABILITY$lambda$9);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor NICKNAME = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$NICKNAME$lambda$10);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor STATUS = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$STATUS$lambda$11);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor IVS = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$IVS$lambda$12);
    @JvmField
    @NotNull
    public static final PokemonPropertyExtractor EVS = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor$Companion.$$INSTANCE.add(DefaultImpls::access$EVS$lambda$13);
    @JvmField
    @NotNull
    public static final List<PokemonPropertyExtractor> ILLUSION;
    @JvmField
    @NotNull
    public static final List<PokemonPropertyExtractor> TRANSFORM;

    public void invoke(@NotNull Pokemon var1, @NotNull PokemonProperties var2);

    static {
        Object[] objectArray = new PokemonPropertyExtractor[]{SPECIES, FORM, ASPECTS, GENDER, NICKNAME, SHINY};
        ILLUSION = CollectionsKt.mutableListOf((Object[])objectArray);
        objectArray = new PokemonPropertyExtractor[]{SPECIES, FORM, ASPECTS, GENDER};
        TRANSFORM = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0014\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0006\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0001R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\n\u00a8\u0006\u0001R\u0017\u0010\u000b\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\f\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\r\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u000e\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u000f\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0007\u00a8\u0006\u0001R\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\n\u00a8\u0006\u0001R\u0017\u0010\u0011\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0012\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0013\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0014\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0015\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0016\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0017\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0007\u00a8\u0006\u0001R\u0017\u0010\u0018\u001a\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0007\u00a8\u0006\u0001R\u001d\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\n\u00a8\u0006\u0001\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;", "extractor", "add", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;)Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;", "ABILITY", "Lcom/cobblemon/mod/common/api/pokemon/PokemonPropertyExtractor;", "", "ALL", "Ljava/util/List;", "ASPECTS", "EVS", "FORM", "FRIENDSHIP", "GENDER", "ILLUSION", "IVS", "LEVEL", "NATURE", "NICKNAME", "POKEBALL", "SHINY", "SPECIES", "STATUS", "TRANSFORM", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final PokemonPropertyExtractor add(@NotNull PokemonPropertyExtractor extractor) {
            Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
            ALL.add(extractor);
            return extractor;
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        private static void SPECIES$lambda$0(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setSpecies(pokemon.getSpecies().getResourceIdentifier().toString());
        }

        private static void FORM$lambda$1(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setForm(pokemon.getForm().formOnlyShowdownId());
        }

        private static void SHINY$lambda$2(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setShiny(pokemon.getShiny());
        }

        private static void ASPECTS$lambda$3(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setAspects(pokemon.getAspects());
        }

        private static void LEVEL$lambda$4(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setLevel(pokemon.getLevel());
        }

        private static void GENDER$lambda$5(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setGender(pokemon.getGender());
        }

        private static void FRIENDSHIP$lambda$6(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setFriendship(pokemon.getFriendship());
        }

        private static void POKEBALL$lambda$7(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setPokeball(pokemon.getCaughtBall().getName().toString());
        }

        private static void NATURE$lambda$8(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setNature(pokemon.getNature().getName().toString());
        }

        private static void ABILITY$lambda$9(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setAbility(pokemon.getAbility().getName());
        }

        private static void NICKNAME$lambda$10(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setNickname(pokemon.getNickname());
        }

        private static void STATUS$lambda$11(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            Object object = pokemon.getStatus();
            properties2.setStatus(object != null && (object = ((PersistentStatusContainer)object).getStatus()) != null ? ((Status)object).getShowdownName() : null);
        }

        private static void IVS$lambda$12(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setIvs(pokemon.getIvs());
        }

        private static void EVS$lambda$13(Pokemon pokemon, PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            properties2.setEvs(pokemon.getEvs());
        }

        public static final /* synthetic */ void access$SPECIES$lambda$0(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.SPECIES$lambda$0(pokemon, properties2);
        }

        public static final /* synthetic */ void access$FORM$lambda$1(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.FORM$lambda$1(pokemon, properties2);
        }

        public static final /* synthetic */ void access$SHINY$lambda$2(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.SHINY$lambda$2(pokemon, properties2);
        }

        public static final /* synthetic */ void access$ASPECTS$lambda$3(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.ASPECTS$lambda$3(pokemon, properties2);
        }

        public static final /* synthetic */ void access$LEVEL$lambda$4(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.LEVEL$lambda$4(pokemon, properties2);
        }

        public static final /* synthetic */ void access$GENDER$lambda$5(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.GENDER$lambda$5(pokemon, properties2);
        }

        public static final /* synthetic */ void access$FRIENDSHIP$lambda$6(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.FRIENDSHIP$lambda$6(pokemon, properties2);
        }

        public static final /* synthetic */ void access$POKEBALL$lambda$7(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.POKEBALL$lambda$7(pokemon, properties2);
        }

        public static final /* synthetic */ void access$NATURE$lambda$8(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.NATURE$lambda$8(pokemon, properties2);
        }

        public static final /* synthetic */ void access$ABILITY$lambda$9(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.ABILITY$lambda$9(pokemon, properties2);
        }

        public static final /* synthetic */ void access$NICKNAME$lambda$10(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.NICKNAME$lambda$10(pokemon, properties2);
        }

        public static final /* synthetic */ void access$STATUS$lambda$11(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.STATUS$lambda$11(pokemon, properties2);
        }

        public static final /* synthetic */ void access$IVS$lambda$12(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.IVS$lambda$12(pokemon, properties2);
        }

        public static final /* synthetic */ void access$EVS$lambda$13(Pokemon pokemon, PokemonProperties properties2) {
            DefaultImpls.EVS$lambda$13(pokemon, properties2);
        }
    }
}

