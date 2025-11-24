/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u00048\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\f\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "contains", "Z", "getContains", "()Z", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "target", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getTarget", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartyMemberRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyMemberRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,36:1\n1747#2,3:37\n*S KotlinDebug\n*F\n+ 1 PartyMemberRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement\n*L\n33#1:37,3\n*E\n"})
public final class PartyMemberRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokemonProperties target = new PokemonProperties();
    private final boolean contains;
    @NotNull
    public static final String ADAPTER_VARIANT = "party_member";

    public PartyMemberRequirement() {
        this.contains = true;
    }

    @NotNull
    public final PokemonProperties getTarget() {
        return this.target;
    }

    public final boolean getContains() {
        return this.contains;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        boolean bl;
        block4: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
            PokemonStore<?> pokemonStore = storeCoordinates != null ? storeCoordinates.getStore() : null;
            PartyStore partyStore = pokemonStore instanceof PartyStore ? (PartyStore)pokemonStore : null;
            if (partyStore == null) {
                return false;
            }
            PartyStore party = partyStore;
            Iterable $this$any$iv = party;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Pokemon member = (Pokemon)element$iv;
                    boolean bl2 = false;
                    if (!(!Intrinsics.areEqual((Object)member.getUuid(), (Object)pokemon.getUuid()) && this.target.matches(member))) continue;
                    bl = true;
                    break block4;
                }
                bl = false;
            }
        }
        boolean has = bl;
        return this.contains == has;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

