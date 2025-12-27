package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.util.Arrays
import java.util.UUID
import net.minecraft.nbt.CompoundTag

public interface CobblemonConverter<S> : CobblemonAdapter<S> {
   public abstract fun root(): Path {
   }

   public open fun exists(target: Path): Boolean {
   }

   public abstract fun party(user: UUID, nbt: CompoundTag): PlayerPartyStore {
   }

   public abstract fun pc(user: UUID, nbt: CompoundTag): PCStore {
   }

   public abstract fun translate(nbt: CompoundTag): Pokemon {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <S> exists(`$this`: CobblemonConverter<S>, target: Path): Boolean {
         val var10001: Array<LinkOption> = new LinkOption[0];
         return Files.exists(target, Arrays.copyOf(var10001, var10001.length));
      }
   }
}
