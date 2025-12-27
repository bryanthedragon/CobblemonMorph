package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.netty.buffer.ByteBuf
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nMove.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Move.kt\ncom/cobblemon/mod/common/api/moves/Move\n+ 2 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,146:1\n33#2,3:147\n33#2,3:150\n*S KotlinDebug\n*F\n+ 1 Move.kt\ncom/cobblemon/mod/common/api/moves/Move\n*L\n36#1:147,3\n42#1:150,3\n*E\n"])
public open class Move(template: MoveTemplate, currentPp: Int, raisedPpStages: Int = 0) {
   public final val accuracy: Double
      public final get() {
         return this.template.getAccuracy();
      }


   public final var currentPp: Int
      public final get() {
         return (this.currentPp$delegate.getValue(this, $$delegatedProperties[0]) as java.lang.Number).intValue();
      }

      public final set(<set-?>) {
         this.currentPp$delegate.setValue(this, $$delegatedProperties[0], `<set-?>`);
      }


   public final val damageCategory: DamageCategory
      public final get() {
         return this.template.getDamageCategory();
      }


   public final val description: MutableComponent
      public final get() {
         return this.template.getDescription();
      }


   public final val displayName: MutableComponent
      public final get() {
         return this.template.getDisplayName();
      }


   public final val effectChances: Array<Double>
      public final get() {
         return this.template.getEffectChances();
      }


   private final var emit: Boolean

   public final val maxPp: Int
      public final get() {
         return this.template.getPp() + this.getRaisedPpStages() * this.template.getPp() / 5;
      }


   public final val name: String
      public final get() {
         return this.template.getName();
      }


   public final val observable: SimpleObservable<Move>

   public final val power: Double
      public final get() {
         return this.template.getPower();
      }


   public final var raisedPpStages: Int
      public final get() {
         return (this.raisedPpStages$delegate.getValue(this, $$delegatedProperties[1]) as java.lang.Number).intValue();
      }

      public final set(<set-?>) {
         this.raisedPpStages$delegate.setValue(this, $$delegatedProperties[1], `<set-?>`);
      }


   public final val template: MoveTemplate

   public final val type: ElementalType
      public final get() {
         return this.template.getElementalType();
      }


   init {
      this.template = template;
      this.emit = true;
      this.observable = new SimpleObservable<>();
      var `this_$iv`: Delegates = Delegates.INSTANCE;
      this.currentPp$delegate = (new Move$special$$inlined$observable$1(currentPp, this)) as ReadWriteProperty;
      `this_$iv` = Delegates.INSTANCE;
      this.raisedPpStages$delegate = (new Move$special$$inlined$observable$2(raisedPpStages, this)) as ReadWriteProperty;
   }

   public fun doThenUpdate(action: () -> Unit) {
      val oldEmit: Boolean = this.emit;
      this.emit = false;
      action.invoke();
      this.emit = oldEmit;
      this.update();
   }

   public fun update() {
      if (this.emit) {
         this.observable.emit(this);
      }
   }

   public fun raiseMaxPP(amount: Int): Boolean {
      val oldPp: Int = this.getMaxPp();
      this.doThenUpdate((new Function0<Unit>(this, amount) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$amount = `$amount`;
         }

         public final void invoke() {
            val ppRatio: Float = (float)this.this$0.getCurrentPp() / this.this$0.getMaxPp();
            this.this$0.setRaisedPpStages(this.this$0.getRaisedPpStages() + this.$amount);
            if (this.this$0.getRaisedPpStages() > 3) {
               this.this$0.setRaisedPpStages(3);
            }

            this.this$0.setCurrentPp((int)((float)Math.ceil((double)(ppRatio * (float)this.this$0.getMaxPp()))));
         }
      }) as () -> Unit);
      return oldPp != this.getMaxPp();
   }

   public fun saveToNBT(nbt: CompoundTag): CompoundTag {
      nbt.m_128359_("MoveName", this.getName());
      nbt.m_128405_("MovePP", this.getCurrentPp());
      nbt.m_128405_("RaisedPPStages", this.getRaisedPpStages());
      return nbt;
   }

   public fun saveToJSON(json: JsonObject): JsonObject {
      json.addProperty("MoveName", this.getName());
      json.addProperty("MovePP", this.getCurrentPp());
      json.addProperty("RaisedPPStages", this.getRaisedPpStages());
      return json;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getName());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.getCurrentPp());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.getRaisedPpStages());
   }

   public companion object {
      public fun loadFromNBT(nbt: CompoundTag): Move {
         val moveName: java.lang.String = nbt.m_128461_("MoveName");
         val var10000: Moves = Moves.INSTANCE;
         return var10000.getByNameOrDummy(moveName).create(nbt.m_128451_("MovePP"), nbt.m_128451_("RaisedPPStages"));
      }

      public fun loadFromJSON(json: JsonObject): Move {
         val moveName: java.lang.String = json.get("MoveName").getAsString();
         val var10000: Moves = Moves.INSTANCE;
         val template: MoveTemplate = var10000.getByNameOrDummy(moveName);
         val currentPp: Int = json.get("MovePP").getAsInt();
         val var6: JsonElement = json.get("RaisedPPStages");
         return new Move(template, currentPp, if (var6 != null) var6.getAsInt() else 0);
      }

      public fun loadFromBuffer(buffer: FriendlyByteBuf): Move {
         val moveName: java.lang.String = buffer.m_130277_();
         val currentPp: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val raisedPpStages: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val var10000: Moves = Moves.INSTANCE;
         return var10000.getByNameOrDummy(moveName).create(currentPp, raisedPpStages);
      }
   }
}
