package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.UUID
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public data PlayerData(uuid: UUID,
   starterPrompted: Boolean,
   starterLocked: Boolean,
   starterSelected: Boolean,
   starterUUID: UUID?,
   keyItems: MutableSet<ResourceLocation>,
   battleTheme: ResourceLocation?,
   extraData: MutableMap<String, PlayerDataExtension>
) {
   public final var advancementData: PlayerAdvancementData
   public final var battleTheme: ResourceLocation?
   public final val extraData: MutableMap<String, PlayerDataExtension>
   public final var keyItems: MutableSet<ResourceLocation>
   public final var starterLocked: Boolean
   public final var starterPrompted: Boolean
   public final var starterSelected: Boolean
   public final var starterUUID: UUID?
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.starterPrompted = starterPrompted;
      this.starterLocked = starterLocked;
      this.starterSelected = starterSelected;
      this.starterUUID = starterUUID;
      this.keyItems = keyItems;
      this.battleTheme = battleTheme;
      this.extraData = extraData;
      this.advancementData = new PlayerAdvancementData();
   }

   public fun sendToPlayer(player: ServerPlayer) {
      CobblemonNetwork.INSTANCE.sendPacket(player, new SetClientPlayerDataPacket(this, null, 2, null));
   }

   public operator fun component1(): UUID {
      return this.uuid;
   }

   public operator fun component2(): Boolean {
      return this.starterPrompted;
   }

   public operator fun component3(): Boolean {
      return this.starterLocked;
   }

   public operator fun component4(): Boolean {
      return this.starterSelected;
   }

   public operator fun component5(): UUID? {
      return this.starterUUID;
   }

   public operator fun component6(): MutableSet<ResourceLocation> {
      return this.keyItems;
   }

   public operator fun component7(): ResourceLocation? {
      return this.battleTheme;
   }

   public operator fun component8(): MutableMap<String, PlayerDataExtension> {
      return this.extraData;
   }

   public fun copy(
      uuid: UUID = this.uuid,
      starterPrompted: Boolean = this.starterPrompted,
      starterLocked: Boolean = this.starterLocked,
      starterSelected: Boolean = this.starterSelected,
      starterUUID: UUID? = this.starterUUID,
      keyItems: MutableSet<ResourceLocation> = this.keyItems,
      battleTheme: ResourceLocation? = this.battleTheme,
      extraData: MutableMap<String, PlayerDataExtension> = this.extraData
   ): PlayerData {
      return new PlayerData(uuid, starterPrompted, starterLocked, starterSelected, starterUUID, keyItems, battleTheme, extraData);
   }

   public override fun toString(): String {
      return "PlayerData(uuid=${this.uuid}, starterPrompted=${this.starterPrompted}, starterLocked=${this.starterLocked}, starterSelected=${this.starterSelected}, starterUUID=${this.starterUUID}, keyItems=${this.keyItems}, battleTheme=${this.battleTheme}, extraData=${this.extraData})";
   }

   public override fun hashCode(): Int {
      var var10000: Int = this.uuid.hashCode() * 31;
      var var10001: Byte = this.starterPrompted;
      if (this.starterPrompted) {
         var10001 = 1;
      }

      var10000 = (var10000 + var10001) * 31;
      var10001 = this.starterLocked;
      if (this.starterLocked) {
         var10001 = 1;
      }

      var10000 = (var10000 + var10001) * 31;
      var10001 = this.starterSelected;
      if (this.starterSelected) {
         var10001 = 1;
      }

      return (
               (((var10000 + var10001) * 31 + (if (this.starterUUID == null) 0 else this.starterUUID.hashCode())) * 31 + this.keyItems.hashCode()) * 31
                  + (if (this.battleTheme == null) 0 else this.battleTheme.hashCode())
            )
            * 31
         + this.extraData.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PlayerData) {
         return false;
      } else {
         val var2: PlayerData = other as PlayerData;
         if (!(this.uuid == (other as PlayerData).uuid)) {
            return false;
         } else if (this.starterPrompted != var2.starterPrompted) {
            return false;
         } else if (this.starterLocked != var2.starterLocked) {
            return false;
         } else if (this.starterSelected != var2.starterSelected) {
            return false;
         } else if (!(this.starterUUID == var2.starterUUID)) {
            return false;
         } else if (!(this.keyItems == var2.keyItems)) {
            return false;
         } else if (!(this.battleTheme == var2.battleTheme)) {
            return false;
         } else {
            return this.extraData == var2.extraData;
         }
      }
   }

   public companion object {
      public fun defaultData(forPlayer: UUID): PlayerData {
         return new PlayerData(
            forPlayer,
            false,
            !Cobblemon.INSTANCE.getStarterConfig().getAllowStarterOnJoin(),
            false,
            null,
            new LinkedHashSet<>(),
            CobblemonSounds.PVP_BATTLE.m_11660_(),
            new LinkedHashMap<>()
         );
      }
   }
}
