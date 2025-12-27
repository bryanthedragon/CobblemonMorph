package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import net.minecraft.network.FriendlyByteBuf

public data HealItemActionResponse(item: String) : ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
   public final var item: String

   init {
      this.item = item;
   }

   public override fun saveToBuffer(buffer: FriendlyByteBuf) {
      super.saveToBuffer(buffer);
      buffer.m_130070_(this.item);
   }

   public override fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionResponse {
      super.loadFromBuffer(buffer);
      val var10001: java.lang.String = buffer.m_130277_();
      this.item = var10001;
      return this;
   }

   public override fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
      return !forceSwitch;
   }

   public override fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
      return "healitem ${activeBattlePokemon.getPNX()} ${this.item}";
   }

   public operator fun component1(): String {
      return this.item;
   }

   public fun copy(item: String = this.item): HealItemActionResponse {
      return new HealItemActionResponse(item);
   }

   public override fun toString(): String {
      return "HealItemActionResponse(item=${this.item})";
   }

   public override fun hashCode(): Int {
      return this.item.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is HealItemActionResponse) {
         return false;
      } else {
         return this.item == (other as HealItemActionResponse).item;
      }
   }
}
