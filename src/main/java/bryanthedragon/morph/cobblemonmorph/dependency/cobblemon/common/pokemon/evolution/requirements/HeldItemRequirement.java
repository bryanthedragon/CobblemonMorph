/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemIdentifierCondition;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\t\b\u0016\u00a2\u0006\u0004\b\f\u0010\rB\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\u000eJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/HeldItemRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "itemCondition", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "getItemCondition", "()Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "<init>", "()V", "(Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;)V", "Companion", "common"})
public final class HeldItemRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final NbtItemPredicate itemCondition;
    @NotNull
    public static final String ADAPTER_VARIANT = "held_item";

    public HeldItemRequirement(@NotNull NbtItemPredicate itemCondition) {
        Intrinsics.checkNotNullParameter((Object)itemCondition, (String)"itemCondition");
        this.itemCondition = itemCondition;
    }

    @NotNull
    public final NbtItemPredicate getItemCondition() {
        return this.itemCondition;
    }

    public HeldItemRequirement() {
        this(new NbtItemPredicate(new ItemIdentifierCondition(new ResourceLocation("air")), null, 2, null));
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        RegistryLikeCondition<Item> registryLikeCondition = this.itemCondition.getItem();
        Item item = pokemon.heldItemNoCopy$common().m_41720_();
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"pokemon.heldItemNoCopy().item");
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
        return registryLikeCondition.fits(item, (Registry<Item>)((Registry)defaultedRegistry)) && this.itemCondition.getNbt().m_57479_(pokemon.heldItemNoCopy$common());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/HeldItemRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

