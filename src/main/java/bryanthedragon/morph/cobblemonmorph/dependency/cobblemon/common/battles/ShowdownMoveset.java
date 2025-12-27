package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownMoveset\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,452:1\n1855#2,2:453\n1864#2,3:455\n1855#2,2:458\n1855#2,2:460\n1#3:462\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownMoveset\n*L\n286#1:453,2\n352#1:455,3\n291#1:458,2\n297#1:460,2\n*E\n"])
public class ShowdownMoveset {
   public final var canDynamax: Boolean
   public final var canMegaEvo: Boolean
   public final var canTerastallize: String?
   public final var canUltraBurst: Boolean
   public final var canZMove: List<InBattleGimmickMove?>?
   public final var maxMoves: List<InBattleGimmickMove?>?
   public final lateinit var moves: List<InBattleMove>
   public final var trapped: Boolean

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.getMoves().size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as InBattleMove).saveToBuffer(buffer);
      }

      buffer.writeBoolean(this.trapped);
      buffer.writeBoolean(this.canMegaEvo);
      buffer.writeBoolean(this.canUltraBurst);
      buffer.m_236821_(this.canZMove, ShowdownMoveset::saveToBuffer$lambda$3);
      buffer.writeBoolean(this.canDynamax);
      buffer.m_236821_(this.maxMoves, ShowdownMoveset::saveToBuffer$lambda$6);
      buffer.m_236821_(this.canTerastallize, ShowdownMoveset::saveToBuffer$lambda$7);
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownMoveset {
      val moves: java.util.List = new ArrayList();
      val var3: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int var4 = 0; var4 < var3; var4++) {
         moves.add(InBattleMove.Companion.loadFromBuffer(buffer));
      }

      this.setMoves(moves);
      this.trapped = buffer.readBoolean();
      this.canMegaEvo = buffer.readBoolean();
      this.canUltraBurst = buffer.readBoolean();
      this.canZMove = buffer.m_236868_(ShowdownMoveset::loadFromBuffer$lambda$11) as MutableList<InBattleGimmickMove>;
      this.canDynamax = buffer.readBoolean();
      this.maxMoves = buffer.m_236868_(ShowdownMoveset::loadFromBuffer$lambda$14) as MutableList<InBattleGimmickMove>;
      this.canTerastallize = buffer.m_236868_(ShowdownMoveset::loadFromBuffer$lambda$15) as java.lang.String;
      this.setGimmickMapping();
      return this;
   }

   public fun hasActiveGimmick(): Boolean {
      return !this.canDynamax && this.maxMoves != null;
   }

   public fun getGimmicks(): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset.Gimmick> {
      val var10000: java.util.List;
      if (!this.hasActiveGimmick()) {
         val var1: java.util.List = CollectionsKt.createListBuilder();
         if (this.canMegaEvo) {
            var1.add(ShowdownMoveset.Gimmick.MEGA_EVOLUTION);
         }

         if (this.canUltraBurst) {
            var1.add(ShowdownMoveset.Gimmick.ULTRA_BURST);
         }

         if (this.canZMove != null) {
            var1.add(ShowdownMoveset.Gimmick.Z_POWER);
         }

         if (this.canDynamax) {
            var1.add(ShowdownMoveset.Gimmick.DYNAMAX);
         }

         if (this.canTerastallize != null) {
            var1.add(ShowdownMoveset.Gimmick.TERASTALLIZATION);
         }

         var10000 = CollectionsKt.toList(CollectionsKt.build(var1));
      } else {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   public fun setGimmickMapping(): Unit? {
      var var10000: java.util.List = this.canZMove;
      if (this.canZMove == null) {
         var10000 = this.maxMoves;
      }

      val var13: Unit;
      if (var10000 != null) {
         val gimmickMoves: java.util.List = var10000;
         val `$this$forEachIndexed$iv`: java.lang.Iterable = this.getMoves();
         var `index$iv`: Int = 0;

         for (Object item$iv : $this$forEachIndexed$iv) {
            val var8: Int = `index$iv`++;
            if (var8 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            (`item$iv` as InBattleMove).setGimmickMove(gimmickMoves.get(var8) as InBattleGimmickMove);
         }

         var13 = Unit.INSTANCE;
      } else {
         var13 = null;
      }

      return var13;
   }

   public fun blockGimmick(gimmick: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset.Gimmick) {
      switch (ShowdownMoveset.WhenMappings.$EnumSwitchMapping$0[gimmick.ordinal()]) {
         case 1:
            this.canMegaEvo = false;
            break;
         case 2:
            this.canDynamax = false;
            break;
         case 3:
            this.canUltraBurst = false;
            break;
         case 4:
            this.canZMove = null;
            break;
         default:
            this.canTerastallize = null;
      }
   }

   @JvmStatic
   fun `saveToBuffer$lambda$3$lambda$2$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, zmove: InBattleGimmickMove) {
      zmove.saveToBuffer(`$buffer`);
   }

   @JvmStatic
   fun `saveToBuffer$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, canZMove: java.util.List) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         `$buffer`.m_236821_(`element$iv` as InBattleGimmickMove, ShowdownMoveset::saveToBuffer$lambda$3$lambda$2$lambda$1);
      }
   }

   @JvmStatic
   fun `saveToBuffer$lambda$6$lambda$5$lambda$4`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, maxMove: InBattleGimmickMove) {
      maxMove.saveToBuffer(`$buffer`);
   }

   @JvmStatic
   fun `saveToBuffer$lambda$6`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, maxMoves: java.util.List) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         `$buffer`.m_236821_(`element$iv` as InBattleGimmickMove, ShowdownMoveset::saveToBuffer$lambda$6$lambda$5$lambda$4);
      }
   }

   @JvmStatic
   fun `saveToBuffer$lambda$7`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, teraType: java.lang.String) {
      `$buffer`.m_130070_(teraType);
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$11$lambda$10$lambda$9`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): InBattleGimmickMove {
      return InBattleGimmickMove.Companion.loadFromBuffer(`$buffer`);
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$11`(`$moves`: java.util.List, `$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.util.List {
      val zMoves: java.util.List = new ArrayList();
      val var4: Int = `$moves`.size();

      for (int var5 = 0; var5 < var4; var5++) {
         zMoves.add(`$buffer`.m_236868_(ShowdownMoveset::loadFromBuffer$lambda$11$lambda$10$lambda$9));
      }

      return zMoves;
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$14$lambda$13$lambda$12`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): InBattleGimmickMove {
      return InBattleGimmickMove.Companion.loadFromBuffer(`$buffer`);
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$14`(`$moves`: java.util.List, `$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.util.List {
      val maxMoves: java.util.List = new ArrayList();
      val var4: Int = `$moves`.size();

      for (int var5 = 0; var5 < var4; var5++) {
         maxMoves.add(`$buffer`.m_236868_(ShowdownMoveset::loadFromBuffer$lambda$14$lambda$13$lambda$12));
      }

      return maxMoves;
   }

   @JvmStatic
   fun `loadFromBuffer$lambda$15`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   public enum Gimmick(id: String) {
      MEGA_EVOLUTION("mega"),
      ULTRA_BURST("ultra"),
      Z_POWER("zmove"),
      DYNAMAX("max"),
      TERASTALLIZATION("terastal")
      public final val id: String

      init {
         this.id = id;
      }
   }
}
