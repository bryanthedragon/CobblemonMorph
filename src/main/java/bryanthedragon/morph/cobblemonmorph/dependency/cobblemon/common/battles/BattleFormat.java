package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nBattleFormat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,76:1\n1855#2,2:77\n*S KotlinDebug\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat\n*L\n61#1:77,2\n*E\n"])
public data BattleFormat(mod: String = "cobblemon",
   battleType: BattleType = BattleTypes.INSTANCE.getSINGLES(),
   ruleSet: Set<String> = SetsKt.emptySet(),
   gen: Int = 9
) {
   public final val battleType: BattleType
   public final val gen: Int
   public final val mod: String
   public final val ruleSet: Set<String>

   init {
      this.mod = mod;
      this.battleType = battleType;
      this.ruleSet = ruleSet;
      this.gen = gen;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf): FriendlyByteBuf {
      buffer.m_130070_(this.mod);
      this.battleType.saveToBuffer(buffer);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.ruleSet.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }

      return buffer;
   }

   public fun toFormatJSON(): String {
      return StringsKt.replace$default(
         StringsKt.trimIndent(
            "\n            {\n                \"mod\": \"${this.mod}\",\n                \"gameType\": \"${this.battleType.getName()}\",\n                \"gen\": ${this.gen},\n                \"ruleset\": [${CollectionsKt.joinToString$default(
               this.ruleSet, null, null, null, 0, null, <unrepresentable>.INSTANCE, 31, null
            )}],\n                \"effectType\": \"Format\"\n            }\n        "
         ),
         "\n",
         "",
         false,
         4,
         null
      );
   }

   public operator fun component1(): String {
      return this.mod;
   }

   public operator fun component2(): BattleType {
      return this.battleType;
   }

   public operator fun component3(): Set<String> {
      return this.ruleSet;
   }

   public operator fun component4(): Int {
      return this.gen;
   }

   public fun copy(mod: String = this.mod, battleType: BattleType = this.battleType, ruleSet: Set<String> = this.ruleSet, gen: Int = this.gen): BattleFormat {
      return new BattleFormat(mod, battleType, ruleSet, gen);
   }

   public override fun toString(): String {
      return "BattleFormat(mod=${this.mod}, battleType=${this.battleType}, ruleSet=${this.ruleSet}, gen=${this.gen})";
   }

   public override fun hashCode(): Int {
      return ((this.mod.hashCode() * 31 + this.battleType.hashCode()) * 31 + this.ruleSet.hashCode()) * 31 + Integer.hashCode(this.gen);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BattleFormat) {
         return false;
      } else {
         val var2: BattleFormat = other as BattleFormat;
         if (!(this.mod == (other as BattleFormat).mod)) {
            return false;
         } else if (!(this.battleType == var2.battleType)) {
            return false;
         } else if (!(this.ruleSet == var2.ruleSet)) {
            return false;
         } else {
            return this.gen == var2.gen;
         }
      }
   }

   fun BattleFormat() {
      this(null, null, null, 0, 15, null);
   }

   @SourceDebugExtension(["SMAP\nBattleFormat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,76:1\n1#2:77\n*E\n"])
   public companion object {
      public final val GEN_9_DOUBLES: BattleFormat
      public final val GEN_9_MULTI: BattleFormat
      public final val GEN_9_SINGLES: BattleFormat

      public fun loadFromBuffer(buffer: FriendlyByteBuf): BattleFormat {
         val mod: java.lang.String = buffer.m_130277_();
         val battleType: BattleType = BattleType.Companion.loadFromBuffer(buffer);
         val ruleSet: java.util.Set = new LinkedHashSet();
         val var5: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

         for (int var6 = 0; var6 < var5; var6++) {
            val var10001: java.lang.String = buffer.m_130277_();
            ruleSet.add(var10001);
         }

         return new BattleFormat(mod, battleType, ruleSet, 0, 8, null);
      }
   }
}
