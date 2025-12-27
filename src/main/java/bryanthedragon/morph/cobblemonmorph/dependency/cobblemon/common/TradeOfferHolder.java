package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
public object CobblemonTradeOffers {
   public fun tradeOffersForAll(): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers.VillagerTradeOffer> {
      val var10000: DefaultedRegistry = BuiltInRegistries.f_256735_;
      val `$this$map$iv`: java.lang.Iterable = var10000 as java.lang.Iterable;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10000 as java.lang.Iterable, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(this.tradeOffersFor(`item$iv$iv` as VillagerProfession));
      }

      return CollectionsKt.flatten(`destination$iv$iv` as java.util.List);
   }

   public fun tradeOffersFor(profession: VillagerProfession): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers.VillagerTradeOffer> {
      val var10000: java.util.List;
      if (profession == VillagerProfession.f_35590_) {
         val var10002: VillagerProfession = VillagerProfession.f_35590_;
         var10000 = CollectionsKt.listOf(
            new CobblemonTradeOffers.VillagerTradeOffer(var10002, 3, CollectionsKt.listOf(new ItemsForEmeralds(CobblemonItems.VIVICHOKE_SEEDS, 24, 1, 1, 6)))
         );
      } 
      else {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   public fun resolveWanderingTradeOffers(): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers.WandererTradeOffer> {
      return CollectionsKt.listOf(
         new CobblemonTradeOffers.WandererTradeOffer(false, CollectionsKt.listOf(new ItemsForEmeralds(CobblemonItems.VIVICHOKE_SEEDS, 24, 1, 1, 6)))
      );
   }

   public interface TradeOfferHolder {
      public val tradeOffers: List<Factory>
   }

   public data VillagerTradeOffer(profession: VillagerProfession, requiredLevel: Int, tradeOffers: List<Factory>) : CobblemonTradeOffers.TradeOfferHolder {
      public final val profession: VillagerProfession
      public final val requiredLevel: Int
      public open val tradeOffers: List<Factory>

      init {
         this.profession = profession;
         this.requiredLevel = requiredLevel;
         this.tradeOffers = tradeOffers;
         if (this.requiredLevel < 1 || this.requiredLevel > 5) {
            throw new IllegalArgumentException("${this.requiredLevel} is not a valid level for a villager trade accepted range is 1-5");
         }
      }

      public operator fun component1(): VillagerProfession {
         return this.profession;
      }

      public operator fun component2(): Int {
         return this.requiredLevel;
      }

      public operator fun component3(): List<Factory> {
         return this.tradeOffers;
      }

      public fun copy(profession: VillagerProfession = this.profession, requiredLevel: Int = this.requiredLevel, tradeOffers: List<Factory> = this.tradeOffers): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers.VillagerTradeOffer {
         return new CobblemonTradeOffers.VillagerTradeOffer(profession, requiredLevel, tradeOffers);
      }

      public override fun toString(): String {
         return "VillagerTradeOffer(profession=${this.profession}, requiredLevel=${this.requiredLevel}, tradeOffers=${this.tradeOffers})";
      }

      public override fun hashCode(): Int {
         return (this.profession.hashCode() * 31 + Integer.hashCode(this.requiredLevel)) * 31 + this.tradeOffers.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this == other) {
            return true;
         } 
         else if (other !is CobblemonTradeOffers.VillagerTradeOffer) {
            return false;
         } 
         else {
            val var2: CobblemonTradeOffers.VillagerTradeOffer = other as CobblemonTradeOffers.VillagerTradeOffer;
            if (!(this.profession == (other as CobblemonTradeOffers.VillagerTradeOffer).profession)) {
               return false;
            } 
            else if (this.requiredLevel != var2.requiredLevel) {
               return false;
            } 
            else {
               return this.tradeOffers == var2.tradeOffers;
            }
         }
      }
   }

   public data WandererTradeOffer(isRareTrade: Boolean, tradeOffers: List<Factory>) : CobblemonTradeOffers.TradeOfferHolder {
      public final val isRareTrade: Boolean
      public open val tradeOffers: List<Factory>

      init {
         this.isRareTrade = isRareTrade;
         this.tradeOffers = tradeOffers;
      }

      public operator fun component1(): Boolean {
         return this.isRareTrade;
      }

      public operator fun component2(): List<Factory> {
         return this.tradeOffers;
      }

      public fun copy(isRareTrade: Boolean = this.isRareTrade, tradeOffers: List<Factory> = this.tradeOffers): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers.WandererTradeOffer {
         return new CobblemonTradeOffers.WandererTradeOffer(isRareTrade, tradeOffers);
      }

      public override fun toString(): String {
         return "WandererTradeOffer(isRareTrade=${this.isRareTrade}, tradeOffers=${this.tradeOffers})";
      }

      public override fun hashCode(): Int {
         var var10000: Byte = this.isRareTrade;
         if (this.isRareTrade) {
            var10000 = 1;
         }

         return var10000 * 31 + this.tradeOffers.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is CobblemonTradeOffers.WandererTradeOffer) {
            return false;
         } else {
            val var2: CobblemonTradeOffers.WandererTradeOffer = other as CobblemonTradeOffers.WandererTradeOffer;
            if (this.isRareTrade != (other as CobblemonTradeOffers.WandererTradeOffer).isRareTrade) {
               return false;
            } else {
               return this.tradeOffers == var2.tradeOffers;
            }
         }
      }
   }
}
