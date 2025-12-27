package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class AbilityRegistrySyncPacket(abilities: Collection<AbilityTemplate>) : DataRegistrySyncPacket(abilities) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: AbilityTemplate) {
      buffer.m_130070_(entry.getName());
      buffer.m_130070_(entry.getDisplayName());
      buffer.m_130070_(entry.getDescription());
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): AbilityTemplate {
      val var10002: java.lang.String = buffer.m_130277_();
      val var10004: java.lang.String = buffer.m_130277_();
      val var10005: java.lang.String = buffer.m_130277_();
      return new AbilityTemplate(var10002, null, var10004, var10005, 2, null);
   }

   public override fun synchronizeDecoded(entries: Collection<AbilityTemplate>) {
      Abilities.INSTANCE.receiveSyncPacket$common(entries);
   }

   @SourceDebugExtension(["SMAP\nAbilityRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/AbilityRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): AbilityRegistrySyncPacket {
         val var2: AbilityRegistrySyncPacket = new AbilityRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
