package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import com.google.gson.JsonObject
import io.netty.buffer.ByteBuf
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

public data BenchedMove(moveTemplate: MoveTemplate, ppRaisedStages: Int) {
   public final val moveTemplate: MoveTemplate
   public final val ppRaisedStages: Int

   init {
      this.moveTemplate = moveTemplate;
      this.ppRaisedStages = ppRaisedStages;
   }

   public fun saveToNBT(nbt: CompoundTag): CompoundTag {
      nbt.m_128359_("MoveName", this.moveTemplate.getName());
      nbt.m_128344_("RaisedPPStages", (byte)this.ppRaisedStages);
      return nbt;
   }

   public fun saveToJSON(json: JsonObject): JsonObject {
      json.addProperty("MoveName", this.moveTemplate.getName());
      json.addProperty("RaisedPPStages", this.ppRaisedStages);
      return json;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.moveTemplate.getName());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.ppRaisedStages);
   }

   public operator fun component1(): MoveTemplate {
      return this.moveTemplate;
   }

   public operator fun component2(): Int {
      return this.ppRaisedStages;
   }

   public fun copy(moveTemplate: MoveTemplate = this.moveTemplate, ppRaisedStages: Int = this.ppRaisedStages): BenchedMove {
      return new BenchedMove(moveTemplate, ppRaisedStages);
   }

   public override fun toString(): String {
      return "BenchedMove(moveTemplate=${this.moveTemplate}, ppRaisedStages=${this.ppRaisedStages})";
   }

   public override fun hashCode(): Int {
      return this.moveTemplate.hashCode() * 31 + Integer.hashCode(this.ppRaisedStages);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BenchedMove) {
         return false;
      } else {
         val var2: BenchedMove = other as BenchedMove;
         if (!(this.moveTemplate == (other as BenchedMove).moveTemplate)) {
            return false;
         } else {
            return this.ppRaisedStages == var2.ppRaisedStages;
         }
      }
   }

   public companion object {
      public fun loadFromNBT(nbt: CompoundTag): BenchedMove {
         val name: java.lang.String = nbt.m_128461_("MoveName");
         val var10000: BenchedMove = new BenchedMove;
         val var10002: Moves = Moves.INSTANCE;
         var var3: MoveTemplate = var10002.getByName(name);
         if (var3 == null) {
            var3 = MoveTemplate.Companion.dummy(name);
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var3, nbt.m_128445_("RaisedPPStages"));
         return var10000;
      }

      public fun loadFromJSON(json: JsonObject): BenchedMove {
         val name: java.lang.String = json.get("MoveName").getAsString();
         val var10000: BenchedMove = new BenchedMove;
         val var10002: Moves = Moves.INSTANCE;
         var var3: MoveTemplate = var10002.getByName(name);
         if (var3 == null) {
            var3 = MoveTemplate.Companion.dummy(name);
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var3, json.get("RaisedPPStages").getAsInt());
         return var10000;
      }

      public fun loadFromBuffer(buffer: FriendlyByteBuf): BenchedMove {
         val name: java.lang.String = buffer.m_130277_();
         val var10000: BenchedMove = new BenchedMove;
         val var10002: Moves = Moves.INSTANCE;
         var var3: MoveTemplate = var10002.getByName(name);
         if (var3 == null) {
            var3 = MoveTemplate.Companion.dummy(name);
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var3, NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
         return var10000;
      }
   }
}
