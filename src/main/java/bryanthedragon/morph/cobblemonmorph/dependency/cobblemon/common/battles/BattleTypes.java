package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public object BattleTypes {
   public final val DOUBLES: BattleType = makeBattleType$default(INSTANCE, "doubles", null, 1, 2, 2, null)
   public final val MULTI: BattleType = makeBattleType$default(INSTANCE, "multi", null, 2, 1, 2, null)
   public final val SINGLES: BattleType = makeBattleType$default(INSTANCE, "singles", null, 1, 1, 2, null)
   public final val TRIPLES: BattleType = makeBattleType$default(INSTANCE, "triples", null, 1, 3, 2, null)

   public fun makeBattleType(
      name: String,
      displayName: MutableComponent = LocalizationUtilsKt.lang("battle.types.$name"),
      actorsPerSide: Int,
      slotsPerActor: Int
   ): BattleType {
      return new BattleType(name, displayName, actorsPerSide, slotsPerActor) {
         @NotNull
         private final java.lang.String name;
         @NotNull
         private final MutableComponent displayName;
         private final int actorsPerSide;
         private final int slotsPerActor;

         {
            this.name = `$name`;
            this.displayName = `$displayName`;
            this.actorsPerSide = `$actorsPerSide`;
            this.slotsPerActor = `$slotsPerActor`;
         }

         @NotNull
         @Override
         public java.lang.String getName() {
            return this.name;
         }

         @NotNull
         @Override
         public MutableComponent getDisplayName() {
            return this.displayName;
         }

         @Override
         public int getActorsPerSide() {
            return this.actorsPerSide;
         }

         @Override
         public int getSlotsPerActor() {
            return this.slotsPerActor;
         }

         @Override
         public int getPokemonPerSide() {
            return BattleType.DefaultImpls.getPokemonPerSide(this);
         }

         @NotNull
         @Override
         public FriendlyByteBuf saveToBuffer(@NotNull FriendlyByteBuf buffer) {
            return BattleType.DefaultImpls.saveToBuffer(this, buffer);
         }
      };
   }
}
