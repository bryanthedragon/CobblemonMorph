/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\t\b\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012B\u0017\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0013J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "amount", "I", "getAmount", "()I", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "target", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getTarget", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;I)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDefeatRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefeatRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n800#2,11:42\n1747#2,3:53\n*S KotlinDebug\n*F\n+ 1 DefeatRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement\n*L\n34#1:42,11\n35#1:53,3\n*E\n"})
public final class DefeatRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokemonProperties target;
    private final int amount;
    @NotNull
    public static final String ADAPTER_VARIANT = "defeat";

    public DefeatRequirement(@NotNull PokemonProperties target, int amount) {
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        this.target = target;
        this.amount = amount;
    }

    public DefeatRequirement() {
        this(new PokemonProperties(), 0);
    }

    @NotNull
    public final PokemonProperties getTarget() {
        return this.target;
    }

    public final int getAmount() {
        return this.amount;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        boolean bl;
        block4: {
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$filterIsInstance$iv = pokemon.getEvolutionProxy().current().progress();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof DefeatEvolutionProgress)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$any$iv = (List)destination$iv$iv;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    DefeatEvolutionProgress progress2 = (DefeatEvolutionProgress)element$iv;
                    boolean bl2 = false;
                    if (!(StringsKt.equals((String)progress2.currentProgress().getTarget().getOriginalString(), (String)this.target.getOriginalString(), (boolean)true) && progress2.currentProgress().getAmount() >= this.amount)) continue;
                    bl = true;
                    break block4;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

