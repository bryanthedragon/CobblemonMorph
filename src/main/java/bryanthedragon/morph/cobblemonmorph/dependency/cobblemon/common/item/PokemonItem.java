/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0013\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0018\u00010\u00112\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0019\u0010\u0015\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/item/PokemonItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "asPokemon", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "asRenderablePokemon", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "", "", "aspects", "(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Set;", "Lnet/minecraft/network/chat/Component;", "getName", "(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/network/chat/Component;", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpeciesAndAspects", "(Lnet/minecraft/world/item/ItemStack;)Lkotlin/Pair;", "species", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/pokemon/Species;", "Lorg/joml/Vector4f;", "tint", "(Lnet/minecraft/world/item/ItemStack;)Lorg/joml/Vector4f;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonItem.kt\ncom/cobblemon/mod/common/item/PokemonItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,135:1\n1549#2:136\n1620#2,3:137\n*S KotlinDebug\n*F\n+ 1 PokemonItem.kt\ncom/cobblemon/mod/common/item/PokemonItem\n*L\n65#1:136\n65#1:137,3\n*E\n"})
public final class PokemonItem
extends CobblemonItem {
    @NotNull
    public static final Companion Companion = new Companion(null);

    public PokemonItem() {
        Item.Properties properties2 = new Item.Properties().m_41487_(1);
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"Settings().maxCount(1)");
        super(properties2);
    }

    @NotNull
    public Component m_7626_(@NotNull ItemStack stack) {
        Component component;
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Species species = this.species(stack);
        if (species != null && (species = species.getTranslatedName()) != null) {
            component = (Component)species;
        } else {
            Component component2 = super.m_7626_(stack);
            component = component2;
            Intrinsics.checkNotNullExpressionValue((Object)component2, (String)"super.getName(stack)");
        }
        return component;
    }

    @Nullable
    public final Pokemon asPokemon(@NotNull ItemStack stack) {
        Pokemon pokemon;
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Species species = this.species(stack);
        if (species == null) {
            return null;
        }
        Species species2 = species;
        Set set2 = this.aspects(stack);
        if (set2 == null) {
            set2 = SetsKt.emptySet();
        }
        Set aspects = set2;
        Pokemon $this$asPokemon_u24lambda_u240 = pokemon = new Pokemon();
        boolean bl = false;
        $this$asPokemon_u24lambda_u240.setSpecies(species2);
        $this$asPokemon_u24lambda_u240.setAspects(aspects);
        return pokemon;
    }

    @Nullable
    public final Pair<Species, Set<String>> getSpeciesAndAspects(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Species species = this.species(stack);
        if (species == null) {
            return null;
        }
        Set set2 = this.aspects(stack);
        if (set2 == null) {
            set2 = SetsKt.emptySet();
        }
        return TuplesKt.to((Object)species, set2);
    }

    @Nullable
    public final RenderablePokemon asRenderablePokemon(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Pokemon pokemon = this.asPokemon(stack);
        return pokemon != null ? pokemon.asRenderablePokemon() : null;
    }

    private final Species species(ItemStack stack) {
        Species species;
        CompoundTag compoundTag = stack.m_41783_();
        if (compoundTag == null) {
            return null;
        }
        CompoundTag nbt = compoundTag;
        if (!nbt.m_128441_("species")) {
            return null;
        }
        try {
            ResourceLocation identifier = new ResourceLocation(nbt.m_128461_("species"));
            species = PokemonSpecies.INSTANCE.getByIdentifier(identifier);
        }
        catch (ResourceLocationException resourceLocationException) {
            species = null;
        }
        return species;
    }

    /*
     * WARNING - void declaration
     */
    private final Set<String> aspects(ItemStack stack) {
        void $this$mapTo$iv$iv;
        CompoundTag compoundTag = stack.m_41783_();
        if (compoundTag == null) {
            return null;
        }
        CompoundTag nbt = compoundTag;
        if (!nbt.m_128441_("aspects")) {
            return null;
        }
        ListTag listTag = nbt.m_128437_("aspects", 8);
        Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(DataKeys.POK\u2026ment.STRING_TYPE.toInt())");
        Iterable $this$map$iv = (Iterable)listTag;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Tag tag = (Tag)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.m_7916_());
        }
        return CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
    }

    @NotNull
    public final Vector4f tint(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        CompoundTag compoundTag = stack.m_41783_();
        if (compoundTag == null) {
            return new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        CompoundTag nbt = compoundTag;
        float red = nbt.m_128441_("TintRed") ? nbt.m_128457_("TintRed") : 1.0f;
        float green = nbt.m_128441_("TintGreen") ? nbt.m_128457_("TintGreen") : 1.0f;
        float blue = nbt.m_128441_("TintBlue") ? nbt.m_128457_("TintBlue") : 1.0f;
        float alpha = nbt.m_128441_("TintAlpha") ? nbt.m_128457_("TintAlpha") : 1.0f;
        return new Vector4f(red, green, blue, alpha);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Pokemon pokemon, int count, @Nullable Vector4f tint) {
        return Companion.from(pokemon, count, tint);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull PokemonProperties properties2, int count, @Nullable Vector4f tint) {
        return Companion.from(properties2, count, tint);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Species species, @NotNull String[] aspects, int count, @Nullable Vector4f tint) {
        return Companion.from(species, aspects, count, tint);
    }

    @JvmStatic
    @NotNull
    public static final ItemStack from(@NotNull Species species, @NotNull Set<String> aspects, int count, @Nullable Vector4f tint) {
        return Companion.from(species, aspects, count, tint);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Pokemon pokemon, int count) {
        return Companion.from(pokemon, count);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Pokemon pokemon) {
        return Companion.from(pokemon);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull PokemonProperties properties2, int count) {
        return Companion.from(properties2, count);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull PokemonProperties properties2) {
        return Companion.from(properties2);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Species species, @NotNull String[] aspects, int count) {
        return Companion.from(species, aspects, count);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final ItemStack from(@NotNull Species species, String ... aspects) {
        return Companion.from(species, aspects);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J-\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0004\b\t\u0010\nJ-\u0010\t\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0004\b\t\u0010\rJA\u0010\t\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u0010\"\u00020\u00112\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0004\b\t\u0010\u0013J;\u0010\t\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0004\b\t\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/item/PokemonItem$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "", "count", "Lorg/joml/Vector4f;", "tint", "Lnet/minecraft/world/item/ItemStack;", "from", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;ILorg/joml/Vector4f;)Lnet/minecraft/world/item/ItemStack;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;ILorg/joml/Vector4f;)Lnet/minecraft/world/item/ItemStack;", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "", "aspects", "(Lcom/cobblemon/mod/common/pokemon/Species;[Ljava/lang/String;ILorg/joml/Vector4f;)Lnet/minecraft/world/item/ItemStack;", "", "(Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;ILorg/joml/Vector4f;)Lnet/minecraft/world/item/ItemStack;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nPokemonItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonItem.kt\ncom/cobblemon/mod/common/item/PokemonItem$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,135:1\n1855#2,2:136\n*S KotlinDebug\n*F\n+ 1 PokemonItem.kt\ncom/cobblemon/mod/common/item/PokemonItem$Companion\n*L\n118#1:136,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Pokemon pokemon, int count, @Nullable Vector4f tint) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return this.from(pokemon.getSpecies(), pokemon.getAspects(), count, tint);
        }

        public static /* synthetic */ ItemStack from$default(Companion companion, Pokemon pokemon, int n, Vector4f vector4f, int n2, Object object) {
            if ((n2 & 2) != 0) {
                n = 1;
            }
            if ((n2 & 4) != 0) {
                vector4f = null;
            }
            return companion.from(pokemon, n, vector4f);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull PokemonProperties properties2, int count, @Nullable Vector4f tint) {
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            return this.from(properties2.create(), count, tint);
        }

        public static /* synthetic */ ItemStack from$default(Companion companion, PokemonProperties pokemonProperties, int n, Vector4f vector4f, int n2, Object object) {
            if ((n2 & 2) != 0) {
                n = 1;
            }
            if ((n2 & 4) != 0) {
                vector4f = null;
            }
            return companion.from(pokemonProperties, n, vector4f);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Species species, @NotNull String[] aspects, int count, @Nullable Vector4f tint) {
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)aspects, (String)"aspects");
            return this.from(species, ArraysKt.toSet((Object[])aspects), count, tint);
        }

        public static /* synthetic */ ItemStack from$default(Companion companion, Species species, String[] stringArray, int n, Vector4f vector4f, int n2, Object object) {
            if ((n2 & 4) != 0) {
                n = 1;
            }
            if ((n2 & 8) != 0) {
                vector4f = null;
            }
            return companion.from(species, stringArray, n, vector4f);
        }

        @JvmStatic
        @NotNull
        public final ItemStack from(@NotNull Species species, @NotNull Set<String> aspects, int count, @Nullable Vector4f tint) {
            CompoundTag compoundTag;
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            ItemStack stack = new ItemStack((ItemLike)CobblemonItems.POKEMON_MODEL, count);
            CompoundTag $this$from_u24lambda_u241 = compoundTag = stack.m_41784_();
            boolean bl = false;
            $this$from_u24lambda_u241.m_128359_("species", species.getResourceIdentifier().toString());
            ListTag list = new ListTag();
            Iterable $this$forEach$iv = aspects;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String aspect = (String)element$iv;
                boolean bl2 = false;
                list.add((Object)StringTag.m_129297_((String)aspect));
            }
            $this$from_u24lambda_u241.m_128365_("aspects", (Tag)list);
            if (tint != null) {
                $this$from_u24lambda_u241.m_128350_("TintRed", tint.x);
                $this$from_u24lambda_u241.m_128350_("TintGreen", tint.y);
                $this$from_u24lambda_u241.m_128350_("TintBlue", tint.z);
                $this$from_u24lambda_u241.m_128350_("TintAlpha", tint.w);
            }
            return stack;
        }

        public static /* synthetic */ ItemStack from$default(Companion companion, Species species, Set set2, int n, Vector4f vector4f, int n2, Object object) {
            if ((n2 & 4) != 0) {
                n = 1;
            }
            if ((n2 & 8) != 0) {
                vector4f = null;
            }
            return companion.from(species, set2, n, vector4f);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Pokemon pokemon, int count) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, pokemon, count, null, 4, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, pokemon, 0, null, 6, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull PokemonProperties properties2, int count) {
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, properties2, count, null, 4, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull PokemonProperties properties2) {
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, properties2, 0, null, 6, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Species species, @NotNull String[] aspects, int count) {
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)aspects, (String)"aspects");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, species, aspects, count, null, 8, null);
        }

        @JvmStatic
        @JvmOverloads
        @NotNull
        public final ItemStack from(@NotNull Species species, String ... aspects) {
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter((Object)aspects, (String)"aspects");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem$Companion.from$default(this, species, aspects, 0, null, 12, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

