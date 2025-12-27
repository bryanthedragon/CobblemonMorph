package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownPokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1855#2,2:453\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownPokemon\n*L\n406#1:453,2\n*E\n"])
public class ShowdownPokemon {
   public final lateinit var ability: String
   public final var active: Boolean
   public final lateinit var baseAbility: String
   public final lateinit var condition: String
   public final lateinit var details: String
   public final lateinit var ident: String
   public final val moves: MutableList<String> = (new ArrayList()) as java.util.List
   public final lateinit var pokeball: String
   public final var reviving: Boolean

   public final val uuid: UUID by LazyKt.lazy(
      (
         new Function0<UUID>(this) {
            {
               super(0);
               this.this$0 = `$receiver`;
            }

            public final UUID invoke() {
               return UUID.fromString(
                  StringsKt.trim(StringsKt.split$default(this.this$0.getDetails(), new java.lang.String[]{","}, false, 0, 6, null).get(1) as java.lang.String)
                     .toString()
               );
            }
         }
      ) as Function0
   )
      public final get() {
         val var10000: Any = this.uuid$delegate.getValue();
         return var10000 as UUID;
      }


   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getIdent());
      buffer.m_130070_(this.getDetails());
      buffer.m_130070_(this.getCondition());
      buffer.writeBoolean(this.active);
      buffer.writeBoolean(this.reviving);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.moves.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }

      buffer.m_130070_(this.getBaseAbility());
      buffer.m_130070_(this.getPokeball());
      buffer.m_130070_(this.getAbility());
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownPokemon {
      var var10001: java.lang.String = buffer.m_130277_();
      this.setIdent(var10001);
      var10001 = buffer.m_130277_();
      this.setDetails(var10001);
      var10001 = buffer.m_130277_();
      this.setCondition(var10001);
      this.active = buffer.readBoolean();
      this.reviving = buffer.readBoolean();
      val var2: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int var3 = 0; var3 < var2; var3++) {
         val var10000: java.util.List = this.moves;
         var10001 = buffer.m_130277_();
         var10000.add(var10001);
      }

      var10001 = buffer.m_130277_();
      this.setBaseAbility(var10001);
      var10001 = buffer.m_130277_();
      this.setPokeball(var10001);
      var10001 = buffer.m_130277_();
      this.setAbility(var10001);
      return this;
   }
}
