package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public interface BattleType {
   public val actorsPerSide: Int
   public val displayName: MutableComponent
   public val name: String

   public open val pokemonPerSide: Int
      public open get() {
      }


   public val slotsPerActor: Int

   public open fun saveToBuffer(buffer: FriendlyByteBuf): FriendlyByteBuf {
   }

   public companion object {
      public fun loadFromBuffer(buffer: FriendlyByteBuf): BattleType {
         val name: java.lang.String = buffer.m_130277_();
         val displayName: Component = buffer.m_130238_();
         val actorsPerSide: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val slotsPerActor: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val var10000: BattleTypes = BattleTypes.INSTANCE;
         val var10002: MutableComponent = displayName.m_6881_();
         return var10000.makeBattleType(name, var10002, actorsPerSide, slotsPerActor);
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getPokemonPerSide(`$this`: BattleType): Int {
         return `$this`.getActorsPerSide() * `$this`.getSlotsPerActor();
      }

      @JvmStatic
      fun saveToBuffer(`$this`: BattleType, buffer: FriendlyByteBuf): FriendlyByteBuf {
         buffer.m_130070_(`$this`.getName());
         buffer.m_130083_(`$this`.getDisplayName() as Component);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, `$this`.getActorsPerSide());
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, `$this`.getSlotsPerActor());
         return buffer;
      }
   }
}
