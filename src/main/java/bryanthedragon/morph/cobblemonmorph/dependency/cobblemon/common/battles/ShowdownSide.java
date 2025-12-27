package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownSide\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1855#2,2:453\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownSide\n*L\n374#1:453,2\n*E\n"])
public class ShowdownSide {
   public final lateinit var id: String
   public final lateinit var name: UUID
   public final lateinit var pokemon: List<ShowdownPokemon>

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.getName());
      buffer.m_130070_(this.getId());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.getPokemon().size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as ShowdownPokemon).saveToBuffer(buffer);
      }
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownSide {
      val var10001: UUID = buffer.m_130259_();
      this.setName(var10001);
      val var7: java.lang.String = buffer.m_130277_();
      this.setId(var7);
      val pokemon: java.util.List = new ArrayList();
      val var3: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int var4 = 0; var4 < var3; var4++) {
         pokemon.add(new ShowdownPokemon().loadFromBuffer(buffer));
      }

      this.setPokemon(pokemon);
      return this;
   }
}
