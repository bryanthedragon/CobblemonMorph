/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class PartySelectPokemonDTO {
    @NotNull
    private final PokemonProperties pokemonProperties;
    @NotNull
    private final Set<String> aspects;
    @NotNull
    private final ItemStack heldItem;
    private int currentHealth;
    private int maxHealth;
    private boolean enabled;

    public PartySelectPokemonDTO(@NotNull PokemonProperties pokemonProperties, @NotNull Set<String> aspects, @NotNull ItemStack heldItem2, int currentHealth, int maxHealth, boolean enabled) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"pokemonProperties");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)heldItem2, (String)"heldItem");
        this.pokemonProperties = pokemonProperties;
        this.aspects = aspects;
        this.heldItem = heldItem2;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.enabled = enabled;
    }

    @SuppressWarnings("rawtypes")
    public /* synthetic */ PartySelectPokemonDTO(PokemonProperties pokemonProperties, Set set2, ItemStack itemStack, int n, int n2, boolean bl, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 4) != 0) {
            ItemStack itemStack2 = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack2, (String)"EMPTY");
            itemStack = itemStack2;
        }
        this(pokemonProperties, set2, itemStack, n, n2, bl);
    }

    @NotNull
    public final PokemonProperties getPokemonProperties() {
        return this.pokemonProperties;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @NotNull
    public final ItemStack getHeldItem() {
        return this.heldItem;
    }

    public final int getCurrentHealth() {
        return this.currentHealth;
    }

    public final void setCurrentHealth(int n) {
        this.currentHealth = n;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    public final void setMaxHealth(int n) {
        this.maxHealth = n;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    @JvmOverloads
    public PartySelectPokemonDTO(@NotNull Pokemon pokemon, boolean enabled) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PokemonPropertyExtractor[] pokemonPropertyExtractorArray = new PokemonPropertyExtractor[]{PokemonPropertyExtractor.SPECIES, PokemonPropertyExtractor.LEVEL, PokemonPropertyExtractor.NICKNAME, PokemonPropertyExtractor.POKEBALL, PokemonPropertyExtractor.STATUS};
        this(pokemon.createPokemonProperties(pokemonPropertyExtractorArray), pokemon.getAspects(), pokemon.heldItemNoCopy$common(), pokemon.getCurrentHealth(), pokemon.getHp(), enabled);
    }

    public /* synthetic */ PartySelectPokemonDTO(Pokemon pokemon, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        this(pokemon, bl);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PartySelectPokemonDTO(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        PokemonProperties pokemonProperties = new PokemonProperties();
        CompoundTag compoundTag = buffer.m_130260_();
        Intrinsics.checkNotNull((Object)compoundTag, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
        PokemonProperties pokemonProperties2 = pokemonProperties.loadFromNBT(compoundTag);
        List list = buffer.m_236845_(PartySelectPokemonDTO::_init_$lambda$0);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
        Set set2 = CollectionsKt.toSet((Iterable)list);
        ItemStack itemStack = buffer.m_130267_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"buffer.readItemStack()");
        this(pokemonProperties2, set2, itemStack, buffer.readInt(), buffer.readInt(), buffer.readBoolean());
    }

    @SuppressWarnings("rawtypes")
    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130079_(this.pokemonProperties.saveToNBT());
        buffer.m_236828_((Collection)this.aspects, (arg_0, arg_1) -> PartySelectPokemonDTO.writeToBuffer$lambda$1(buffer, arg_0, arg_1));
        buffer.m_130055_(this.heldItem);
        buffer.writeInt(this.currentHealth);
        buffer.writeInt(this.maxHealth);
        buffer.writeBoolean(this.enabled);
    }

    @JvmOverloads
    public PartySelectPokemonDTO(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this(pokemon, false, 2, null);
    }

    private static final String _init_$lambda$0(FriendlyByteBuf it) {
        return it.m_130277_();
    }

    private static final void writeToBuffer$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String aspect) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(aspect);
    }
}

