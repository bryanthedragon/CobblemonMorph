/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.world.entity.npc.VillagerProfession
 *  net.minecraft.world.entity.npc.VillagerTrades$ItemListing
 *  net.minecraft.world.entity.npc.VillagerTrades$ItemsForEmeralds
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003\u000e\u000f\u0010B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00022\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0002\u00a2\u0006\u0004\b\u000b\u0010\u0005\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/CobblemonTradeOffers;", "", "", "Lcom/cobblemon/mod/common/CobblemonTradeOffers$WandererTradeOffer;", "resolveWanderingTradeOffers", "()Ljava/util/List;", "Lnet/minecraft/world/entity/npc/VillagerProfession;", "profession", "Lcom/cobblemon/mod/common/CobblemonTradeOffers$VillagerTradeOffer;", "tradeOffersFor", "(Lnet/minecraft/world/entity/npc/VillagerProfession;)Ljava/util/List;", "tradeOffersForAll", "<init>", "()V", "TradeOfferHolder", "VillagerTradeOffer", "WandererTradeOffer", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonTradeOffers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonTradeOffers.kt\ncom/cobblemon/mod/common/CobblemonTradeOffers\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,92:1\n1549#2:93\n1620#2,3:94\n*S KotlinDebug\n*F\n+ 1 CobblemonTradeOffers.kt\ncom/cobblemon/mod/common/CobblemonTradeOffers\n*L\n26#1:93\n26#1:94,3\n*E\n"})
public final class CobblemonTradeOffers {
    @NotNull
    public static final CobblemonTradeOffers INSTANCE = new CobblemonTradeOffers();

    private CobblemonTradeOffers() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<VillagerTradeOffer> tradeOffersForAll() {
        void $this$mapTo$iv$iv;
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_256735_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"VILLAGER_PROFESSION");
        Iterable $this$map$iv = (Iterable)defaultedRegistry;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            VillagerProfession villagerProfession = (VillagerProfession)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(this.tradeOffersFor((VillagerProfession)p0));
        }
        return CollectionsKt.flatten((Iterable)((List)destination$iv$iv));
    }

    @NotNull
    public final List<VillagerTradeOffer> tradeOffersFor(@NotNull VillagerProfession profession) {
        List list;
        Intrinsics.checkNotNullParameter((Object)profession, (String)"profession");
        if (Intrinsics.areEqual((Object)profession, (Object)VillagerProfession.f_35590_)) {
            VillagerProfession villagerProfession = VillagerProfession.f_35590_;
            Intrinsics.checkNotNullExpressionValue((Object)villagerProfession, (String)"FARMER");
            list = CollectionsKt.listOf((Object)new VillagerTradeOffer(villagerProfession, 3, CollectionsKt.listOf((Object)new VillagerTrades.ItemsForEmeralds(CobblemonItems.VIVICHOKE_SEEDS, 24, 1, 1, 6))));
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @NotNull
    public final List<WandererTradeOffer> resolveWanderingTradeOffers() {
        return CollectionsKt.listOf((Object)new WandererTradeOffer(false, CollectionsKt.listOf((Object)new VillagerTrades.ItemsForEmeralds(CobblemonItems.VIVICHOKE_SEEDS, 24, 1, 1, 6))));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/CobblemonTradeOffers$TradeOfferHolder;", "", "", "Lnet/minecraft/village/TradeOffers$Factory;", "getTradeOffers", "()Ljava/util/List;", "tradeOffers", "common"})
    public static interface TradeOfferHolder {
        @NotNull
        public List<VillagerTrades.ItemListing> getTradeOffers();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ4\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u00052\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0007J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001a\u001a\u0004\b\u001b\u0010\u0004R\u0017\u0010\r\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\u0007R \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001e\u001a\u0004\b\u001f\u0010\u000b\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/CobblemonTradeOffers$VillagerTradeOffer;", "Lcom/cobblemon/mod/common/CobblemonTradeOffers$TradeOfferHolder;", "Lnet/minecraft/world/entity/npc/VillagerProfession;", "component1", "()Lnet/minecraft/world/entity/npc/VillagerProfession;", "", "component2", "()I", "", "Lnet/minecraft/village/TradeOffers$Factory;", "component3", "()Ljava/util/List;", "profession", "requiredLevel", "tradeOffers", "copy", "(Lnet/minecraft/world/entity/npc/VillagerProfession;ILjava/util/List;)Lcom/cobblemon/mod/common/CobblemonTradeOffers$VillagerTradeOffer;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/npc/VillagerProfession;", "getProfession", "I", "getRequiredLevel", "Ljava/util/List;", "getTradeOffers", "<init>", "(Lnet/minecraft/world/entity/npc/VillagerProfession;ILjava/util/List;)V", "common"})
    public static final class VillagerTradeOffer
    implements TradeOfferHolder {
        @NotNull
        private final VillagerProfession profession;
        private final int requiredLevel;
        @NotNull
        private final List<VillagerTrades.ItemListing> tradeOffers;

        public VillagerTradeOffer(@NotNull VillagerProfession profession, int requiredLevel, @NotNull List<? extends VillagerTrades.ItemListing> tradeOffers) {
            Intrinsics.checkNotNullParameter((Object)profession, (String)"profession");
            Intrinsics.checkNotNullParameter(tradeOffers, (String)"tradeOffers");
            this.profession = profession;
            this.requiredLevel = requiredLevel;
            this.tradeOffers = tradeOffers;
            if (this.requiredLevel < 1 || this.requiredLevel > 5) {
                throw new IllegalArgumentException(this.requiredLevel + " is not a valid level for a villager trade accepted range is 1-5");
            }
        }

        @NotNull
        public final VillagerProfession getProfession() {
            return this.profession;
        }

        public final int getRequiredLevel() {
            return this.requiredLevel;
        }

        @Override
        @NotNull
        public List<VillagerTrades.ItemListing> getTradeOffers() {
            return this.tradeOffers;
        }

        @NotNull
        public final VillagerProfession component1() {
            return this.profession;
        }

        public final int component2() {
            return this.requiredLevel;
        }

        @NotNull
        public final List<VillagerTrades.ItemListing> component3() {
            return this.tradeOffers;
        }

        @NotNull
        public final VillagerTradeOffer copy(@NotNull VillagerProfession profession, int requiredLevel, @NotNull List<? extends VillagerTrades.ItemListing> tradeOffers) {
            Intrinsics.checkNotNullParameter((Object)profession, (String)"profession");
            Intrinsics.checkNotNullParameter(tradeOffers, (String)"tradeOffers");
            return new VillagerTradeOffer(profession, requiredLevel, tradeOffers);
        }

        public static /* synthetic */ VillagerTradeOffer copy$default(VillagerTradeOffer villagerTradeOffer, VillagerProfession villagerProfession, int n, List list, int n2, Object object) {
            if ((n2 & 1) != 0) {
                villagerProfession = villagerTradeOffer.profession;
            }
            if ((n2 & 2) != 0) {
                n = villagerTradeOffer.requiredLevel;
            }
            if ((n2 & 4) != 0) {
                list = villagerTradeOffer.tradeOffers;
            }
            return villagerTradeOffer.copy(villagerProfession, n, list);
        }

        @NotNull
        public String toString() {
            return "VillagerTradeOffer(profession=" + this.profession + ", requiredLevel=" + this.requiredLevel + ", tradeOffers=" + this.tradeOffers + ")";
        }

        public int hashCode() {
            int result = this.profession.hashCode();
            result = result * 31 + Integer.hashCode(this.requiredLevel);
            result = result * 31 + ((Object)this.tradeOffers).hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof VillagerTradeOffer)) {
                return false;
            }
            VillagerTradeOffer villagerTradeOffer = (VillagerTradeOffer)other;
            if (!Intrinsics.areEqual((Object)this.profession, (Object)villagerTradeOffer.profession)) {
                return false;
            }
            if (this.requiredLevel != villagerTradeOffer.requiredLevel) {
                return false;
            }
            return Intrinsics.areEqual(this.tradeOffers, villagerTradeOffer.tradeOffers);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ*\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\t\u0010\u0004R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u0018\u001a\u0004\b\u0019\u0010\b\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/CobblemonTradeOffers$WandererTradeOffer;", "Lcom/cobblemon/mod/common/CobblemonTradeOffers$TradeOfferHolder;", "", "component1", "()Z", "", "Lnet/minecraft/village/TradeOffers$Factory;", "component2", "()Ljava/util/List;", "isRareTrade", "tradeOffers", "copy", "(ZLjava/util/List;)Lcom/cobblemon/mod/common/CobblemonTradeOffers$WandererTradeOffer;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "Ljava/util/List;", "getTradeOffers", "<init>", "(ZLjava/util/List;)V", "common"})
    public static final class WandererTradeOffer
    implements TradeOfferHolder {
        private final boolean isRareTrade;
        @NotNull
        private final List<VillagerTrades.ItemListing> tradeOffers;

        public WandererTradeOffer(boolean isRareTrade, @NotNull List<? extends VillagerTrades.ItemListing> tradeOffers) {
            Intrinsics.checkNotNullParameter(tradeOffers, (String)"tradeOffers");
            this.isRareTrade = isRareTrade;
            this.tradeOffers = tradeOffers;
        }

        public final boolean isRareTrade() {
            return this.isRareTrade;
        }

        @Override
        @NotNull
        public List<VillagerTrades.ItemListing> getTradeOffers() {
            return this.tradeOffers;
        }

        public final boolean component1() {
            return this.isRareTrade;
        }

        @NotNull
        public final List<VillagerTrades.ItemListing> component2() {
            return this.tradeOffers;
        }

        @NotNull
        public final WandererTradeOffer copy(boolean isRareTrade, @NotNull List<? extends VillagerTrades.ItemListing> tradeOffers) {
            Intrinsics.checkNotNullParameter(tradeOffers, (String)"tradeOffers");
            return new WandererTradeOffer(isRareTrade, tradeOffers);
        }

        public static /* synthetic */ WandererTradeOffer copy$default(WandererTradeOffer wandererTradeOffer, boolean bl, List list, int n, Object object) {
            if ((n & 1) != 0) {
                bl = wandererTradeOffer.isRareTrade;
            }
            if ((n & 2) != 0) {
                list = wandererTradeOffer.tradeOffers;
            }
            return wandererTradeOffer.copy(bl, list);
        }

        @NotNull
        public String toString() {
            return "WandererTradeOffer(isRareTrade=" + this.isRareTrade + ", tradeOffers=" + this.tradeOffers + ")";
        }

        public int hashCode() {
            int n = this.isRareTrade ? 1 : 0;
            if (n != 0) {
                n = 1;
            }
            int result = n;
            result = result * 31 + ((Object)this.tradeOffers).hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof WandererTradeOffer)) {
                return false;
            }
            WandererTradeOffer wandererTradeOffer = (WandererTradeOffer)other;
            if (this.isRareTrade != wandererTradeOffer.isRareTrade) {
                return false;
            }
            return Intrinsics.areEqual(this.tradeOffers, wandererTradeOffer.tradeOffers);
        }
    }
}

