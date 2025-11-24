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
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u001b\b\u0017\u0012\u0006\u0010)\u001a\u00020(\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b*\u0010+B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b*\u0010\u0006B?\u0012\u0006\u0010$\u001a\u00020#\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010 \u001a\u00020\r\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b*\u0010,J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000e\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\"\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\"\u0010 \u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b \u0010\u000f\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013R\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "", "currentHealth", "I", "getCurrentHealth", "()I", "setCurrentHealth", "(I)V", "", "enabled", "Z", "getEnabled", "()Z", "setEnabled", "(Z)V", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "Lnet/minecraft/world/item/ItemStack;", "getHeldItem", "()Lnet/minecraft/world/item/ItemStack;", "maxHealth", "getMaxHealth", "setMaxHealth", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "pokemonProperties", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getPokemonProperties", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/Set;Lnet/minecraft/world/item/ItemStack;IIZ)V", "common"})
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

