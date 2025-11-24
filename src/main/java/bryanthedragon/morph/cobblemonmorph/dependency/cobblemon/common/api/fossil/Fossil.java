/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0019\u001a\u00020\u0017\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\r\u00a2\u0006\u0004\b$\u0010%J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\u000f\u001a\u00020\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\r\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0011\u001a\u00020\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\r\u00a2\u0006\u0004\b\u0011\u0010\u0010R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\r8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R*\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00178\u0006@@X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/api/fossil/Fossil;", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "", "isIngredient", "(Lnet/minecraft/world/item/ItemStack;)Z", "", "ingredients", "matchesIngredients", "(Ljava/util/List;)Z", "matchesIngredientsSubSet", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "fossils", "Ljava/util/List;", "getFossils", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "<set-?>", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "setIdentifier$common", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFossil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Fossil.kt\ncom/cobblemon/mod/common/api/fossil/Fossil\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,80:1\n1726#2,2:81\n1747#2,3:83\n1728#2:86\n1726#2,2:87\n1774#2,4:89\n1774#2,4:93\n1728#2:97\n1747#2,3:98\n*S KotlinDebug\n*F\n+ 1 Fossil.kt\ncom/cobblemon/mod/common/api/fossil/Fossil\n*L\n50#1:81,2\n51#1:83,3\n50#1:86\n65#1:87,2\n66#1:89,4\n67#1:93,4\n65#1:97\n77#1:98,3\n*E\n"})
public final class Fossil
implements StringRepresentable {
    @NotNull
    private final PokemonProperties result;
    @NotNull
    private final List<NbtItemPredicate> fossils;
    @NotNull
    private transient ResourceLocation identifier;

    public Fossil(@NotNull ResourceLocation identifier, @NotNull PokemonProperties result, @NotNull List<NbtItemPredicate> fossils) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter((Object)result, (String)"result");
        Intrinsics.checkNotNullParameter(fossils, (String)"fossils");
        this.result = result;
        this.fossils = fossils;
        this.identifier = identifier;
    }

    @NotNull
    public final PokemonProperties getResult() {
        return this.result;
    }

    @NotNull
    public final List<NbtItemPredicate> getFossils() {
        return this.fossils;
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public final void setIdentifier$common(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.identifier = resourceLocation;
    }

    @NotNull
    public String m_7912_() {
        String string = this.identifier.toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.toString()");
        return string;
    }

    @NotNull
    public final MutableComponent getName() {
        MutableComponent mutableComponent = Component.m_237115_((String)(this.identifier.m_135827_() + ".fossil." + this.identifier.m_135815_() + ".name"));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"${identifi\u2026${identifier.path}.name\")");
        return mutableComponent;
    }

    public final boolean matchesIngredients(@NotNull List<ItemStack> ingredients) {
        boolean bl;
        block8: {
            Intrinsics.checkNotNullParameter(ingredients, (String)"ingredients");
            if (this.fossils.size() != ingredients.size()) {
                return false;
            }
            Iterable $this$all$iv = this.fossils;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    boolean bl2;
                    block7: {
                        NbtItemPredicate fossil = (NbtItemPredicate)element$iv;
                        boolean bl3 = false;
                        Iterable $this$any$iv = ingredients;
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl2 = false;
                        } else {
                            for (Object element$iv2 : $this$any$iv) {
                                ItemStack it = (ItemStack)element$iv2;
                                boolean bl4 = false;
                                RegistryLikeCondition<Item> registryLikeCondition = fossil.getItem();
                                Item item = it.m_41720_();
                                Intrinsics.checkNotNullExpressionValue((Object)item, (String)"it.item");
                                DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
                                Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
                                if (!(registryLikeCondition.fits(item, (Registry<Item>)((Registry)defaultedRegistry)) && fossil.getNbt().m_57479_(it))) continue;
                                bl2 = true;
                                break block7;
                            }
                            bl2 = false;
                        }
                    }
                    if (bl2) continue;
                    bl = false;
                    break block8;
                }
                bl = true;
            }
        }
        return bl;
    }

    public final boolean matchesIngredientsSubSet(@NotNull List<ItemStack> ingredients) {
        boolean bl;
        block10: {
            Intrinsics.checkNotNullParameter(ingredients, (String)"ingredients");
            if (this.fossils.size() < ingredients.size()) {
                return false;
            }
            Iterable $this$all$iv = ingredients;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    int n;
                    int count$iv;
                    int n2;
                    ItemStack ingredient = (ItemStack)element$iv;
                    boolean bl2 = false;
                    Iterable $this$count$iv = ingredients;
                    boolean $i$f$count = false;
                    if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                        n2 = 0;
                    } else {
                        count$iv = 0;
                        for (Object element$iv2 : $this$count$iv) {
                            ItemStack item = (ItemStack)element$iv2;
                            boolean bl3 = false;
                            if (!ingredient.m_220165_(item.m_220173_()) || ++count$iv >= 0) continue;
                            CollectionsKt.throwCountOverflow();
                        }
                        n2 = count$iv;
                    }
                    $this$count$iv = this.fossils;
                    int n3 = n2;
                    $i$f$count = false;
                    if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                        v2 = 0;
                    } else {
                        count$iv = 0;
                        for (Object element$iv2 : $this$count$iv) {
                            NbtItemPredicate fossil = (NbtItemPredicate)element$iv2;
                            boolean bl4 = false;
                            RegistryLikeCondition<Item> registryLikeCondition = fossil.getItem();
                            Item item = ingredient.m_41720_();
                            Intrinsics.checkNotNullExpressionValue((Object)item, (String)"ingredient.item");
                            DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
                            Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
                            if (!(registryLikeCondition.fits(item, (Registry<Item>)((Registry)defaultedRegistry)) && fossil.getNbt().m_57479_(ingredient)) || ++count$iv >= 0) continue;
                            CollectionsKt.throwCountOverflow();
                        }
                        v2 = n = count$iv;
                    }
                    if (n3 <= n) continue;
                    bl = false;
                    break block10;
                }
                bl = true;
            }
        }
        return bl;
    }

    public final boolean isIngredient(@NotNull ItemStack itemStack) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
            Iterable $this$any$iv = this.fossils;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    NbtItemPredicate it = (NbtItemPredicate)element$iv;
                    boolean bl2 = false;
                    RegistryLikeCondition<Item> registryLikeCondition = it.getItem();
                    Item item = itemStack.m_41720_();
                    Intrinsics.checkNotNullExpressionValue((Object)item, (String)"itemStack.item");
                    DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
                    Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
                    if (!(registryLikeCondition.fits(item, (Registry<Item>)((Registry)defaultedRegistry)) && it.getNbt().m_57479_(itemStack))) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }
}

