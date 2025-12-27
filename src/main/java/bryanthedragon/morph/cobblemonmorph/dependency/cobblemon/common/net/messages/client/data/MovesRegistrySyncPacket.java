package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nMovesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,64:1\n13579#2,2:65\n37#3,2:67\n*S KotlinDebug\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket\n*L\n35#1:65,2\n53#1:67,2\n*E\n"])
public class MovesRegistrySyncPacket(moves: List<MoveTemplate>) : DataRegistrySyncPacket(moves) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: MoveTemplate) {
      buffer.m_130070_(entry.getName());
      buffer.writeInt(entry.getNum());
      buffer.m_130070_(entry.getElementalType().getName());
      buffer.m_130070_(entry.getDamageCategory().getName());
      buffer.writeDouble(entry.getPower());
      buffer.m_130068_(entry.getTarget());
      buffer.writeDouble(entry.getAccuracy());
      buffer.writeInt(entry.getPp());
      buffer.writeInt(entry.getPriority());
      buffer.writeDouble(entry.getCritRatio());
      buffer.m_130130_(entry.getEffectChances().length);

      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         buffer.writeDouble((`element$iv` as java.lang.Number).doubleValue());
      }
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): MoveTemplate {
      val name: java.lang.String = buffer.m_130277_();
      val num: Int = buffer.readInt();
      val var10000: ElementalTypes = ElementalTypes.INSTANCE;
      var var10001: java.lang.String = buffer.m_130277_();
      val type: ElementalType = var10000.getOrException(var10001);
      val var22: DamageCategories = DamageCategories.INSTANCE;
      var10001 = buffer.m_130277_();
      val damageCategory: DamageCategory = var22.getOrException(var10001);
      val power: Double = buffer.readDouble();
      val target: MoveTarget = buffer.m_130066_(MoveTarget.class) as MoveTarget;
      val accuracy: Double = buffer.readDouble();
      val pp: Int = buffer.readInt();
      val priority: Int = buffer.readInt();
      val critRatio: Double = buffer.readDouble();
      val effectChances: ArrayList = new ArrayList();
      val `$this$toTypedArray$iv`: Int = buffer.m_130242_();

      for (int $i$f$toTypedArray = 0; $i$f$toTypedArray < $this$toTypedArray$iv; $i$f$toTypedArray++) {
         effectChances.add(buffer.readDouble());
      }

      return new MoveTemplate(
         name, num, type, damageCategory, power, target, accuracy, pp, priority, critRatio, effectChances.toArray(new java.lang.Double[0]), null
      );
   }

   public override fun synchronizeDecoded(entries: Collection<MoveTemplate>) {
      Moves.INSTANCE.receiveSyncPacket$common(entries);
   }

   @SourceDebugExtension(["SMAP\nMovesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,64:1\n1#2:65\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MovesRegistrySyncPacket {
         val var2: MovesRegistrySyncPacket = new MovesRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
