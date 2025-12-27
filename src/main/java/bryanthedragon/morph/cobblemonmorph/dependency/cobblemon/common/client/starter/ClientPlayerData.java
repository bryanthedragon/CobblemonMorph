package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter

import java.util.UUID

public data ClientPlayerData(promptStarter: Boolean = true, starterLocked: Boolean = true, starterSelected: Boolean = false, starterUUID: UUID? = null) {
   public final var promptStarter: Boolean
   public final var starterLocked: Boolean
   public final var starterSelected: Boolean
   public final var starterUUID: UUID?

   init {
      this.promptStarter = promptStarter;
      this.starterLocked = starterLocked;
      this.starterSelected = starterSelected;
      this.starterUUID = starterUUID;
   }

   public operator fun component1(): Boolean {
      return this.promptStarter;
   }

   public operator fun component2(): Boolean {
      return this.starterLocked;
   }

   public operator fun component3(): Boolean {
      return this.starterSelected;
   }

   public operator fun component4(): UUID? {
      return this.starterUUID;
   }

   public fun copy(
      promptStarter: Boolean = this.promptStarter,
      starterLocked: Boolean = this.starterLocked,
      starterSelected: Boolean = this.starterSelected,
      starterUUID: UUID? = this.starterUUID
   ): ClientPlayerData {
      return new ClientPlayerData(promptStarter, starterLocked, starterSelected, starterUUID);
   }

   public override fun toString(): String {
      return "ClientPlayerData(promptStarter=${this.promptStarter}, starterLocked=${this.starterLocked}, starterSelected=${this.starterSelected}, starterUUID=${this.starterUUID})";
   }

   public override fun hashCode(): Int {
      var var10000: Int = this.promptStarter;
      if (this.promptStarter) {
         var10000 = 1;
      }

      var10000 = var10000 * 31;
      var var10001: Byte = this.starterLocked;
      if (this.starterLocked) {
         var10001 = 1;
      }

      var10000 = (var10000 + var10001) * 31;
      var10001 = this.starterSelected;
      if (this.starterSelected) {
         var10001 = 1;
      }

      return (var10000 + var10001) * 31 + (if (this.starterUUID == null) 0 else this.starterUUID.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is ClientPlayerData) {
         return false;
      } else {
         val var2: ClientPlayerData = other as ClientPlayerData;
         if (this.promptStarter != (other as ClientPlayerData).promptStarter) {
            return false;
         } else if (this.starterLocked != var2.starterLocked) {
            return false;
         } else if (this.starterSelected != var2.starterSelected) {
            return false;
         } else {
            return this.starterUUID == var2.starterUUID;
         }
      }
   }

   fun ClientPlayerData() {
      this(false, false, false, null, 15, null);
   }
}
