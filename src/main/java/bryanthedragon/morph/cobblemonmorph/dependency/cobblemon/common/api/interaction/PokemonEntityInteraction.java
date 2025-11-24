/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ'\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u000b\u0010\nR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction;", "Lcom/cobblemon/mod/common/api/interaction/EntityInteraction;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "entity", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "onInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/item/ItemStack;)Z", "processInteraction", "", "Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction$Ownership;", "getAccepted", "()Ljava/util/Set;", "accepted", "Lnet/minecraft/sounds/SoundEvent;", "getSound", "()Lnet/minecraft/sounds/SoundEvent;", "sound", "Ownership", "common"})
public interface PokemonEntityInteraction
extends EntityInteraction<PokemonEntity> {
    @NotNull
    public Set<Ownership> getAccepted();

    @Nullable
    public SoundEvent getSound();

    @Override
    public boolean onInteraction(@NotNull ServerPlayer var1, @NotNull PokemonEntity var2, @NotNull ItemStack var3);

    public boolean processInteraction(@NotNull ServerPlayer var1, @NotNull PokemonEntity var2, @NotNull ItemStack var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @Nullable
        public static SoundEvent getSound(@NotNull PokemonEntityInteraction $this) {
            return CobblemonSounds.ITEM_USE;
        }

        public static boolean onInteraction(@NotNull PokemonEntityInteraction $this, @NotNull ServerPlayer player, @NotNull PokemonEntity entity2, @NotNull ItemStack stack) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Pokemon pokemon = entity2.getPokemon();
            StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
            Ownership ownership = storeCoordinates == null ? Ownership.WILD : (Intrinsics.areEqual((Object)storeCoordinates.getStore().getUuid(), (Object)player.m_20148_()) ? Ownership.OWNER : Ownership.OWNED_ANOTHER);
            return $this.getAccepted().contains((Object)ownership) ? $this.processInteraction(player, entity2, stack) : false;
        }

        public static void consumeItem(@NotNull PokemonEntityInteraction $this, @NotNull ServerPlayer player, @NotNull ItemStack stack, int amount) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            EntityInteraction.DefaultImpls.consumeItem($this, player, stack, amount);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction$Ownership;", "", "<init>", "(Ljava/lang/String;I)V", "OWNER", "OWNED_ANOTHER", "WILD", "common"})
    public static final class Ownership
    extends Enum<Ownership> {
        public static final /* enum */ Ownership OWNER = new Ownership();
        public static final /* enum */ Ownership OWNED_ANOTHER = new Ownership();
        public static final /* enum */ Ownership WILD = new Ownership();
        private static final /* synthetic */ Ownership[] $VALUES;

        public static Ownership[] values() {
            return (Ownership[])$VALUES.clone();
        }

        public static Ownership valueOf(String value2) {
            return Enum.valueOf(Ownership.class, value2);
        }

        static {
            $VALUES = ownershipArray = new Ownership[]{Ownership.OWNER, Ownership.OWNED_ANOTHER, Ownership.WILD};
        }
    }
}

