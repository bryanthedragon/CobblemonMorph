package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag

public class IllusionEffect(mock: PokemonProperties = new PokemonProperties(), scale: Float = 1.0F) : BattleEffect, MocKEffect {
   public open var mock: PokemonProperties
   public open var scale: Float

   init {
      this.mock = mock;
      this.scale = scale;
   }

   public constructor(disguise: Pokemon) : this(
         disguise.createPokemonProperties(PokemonPropertyExtractor.ILLUSION), disguise.getForm().getBaseScale() * disguise.getScaleModifier()
      )
   protected override fun apply(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
      entity.getEffects().setMockEffect(this);
      future.complete(entity);
   }

   protected override fun revert(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
      entity.getEffects().setMockEffect(null);
      SchedulingFunctionsKt.afterOnServer$default(0, 1.0F, (new Function0<Unit>(entity, future) {
         {
            super(0);
            this.$entity = `$entity`;
            this.$future = `$future`;
         }

         public final void invoke() {
            this.$entity.cry();
            this.$future.complete(this.$entity);
         }
      }) as Function0, 1, null);
   }

   public override fun saveToNbt(): CompoundTag {
      val nbt: CompoundTag = new CompoundTag();
      nbt.m_128359_("EntityEffectID", ID);
      nbt.m_128365_("PokemonEntityMock", this.getMock().saveToNBT() as Tag);
      nbt.m_128350_("PokemonEntityScale", this.getScale());
      return nbt;
   }

   public override fun loadFromNBT(nbt: CompoundTag) {
      if (nbt.m_128441_("PokemonEntityMock")) {
         val var10001: PokemonProperties = new PokemonProperties();
         val var10002: CompoundTag = nbt.m_128469_("PokemonEntityMock");
         this.setMock(var10001.loadFromNBT(var10002));
      }

      if (nbt.m_128441_("PokemonEntityScale")) {
         this.setScale(nbt.m_128457_("PokemonEntityScale"));
      }
   }

   override fun getExposedSpecies(): Species? {
      return MocKEffect.DefaultImpls.getExposedSpecies(this);
   }

   override fun getExposedForm(): FormData? {
      return MocKEffect.DefaultImpls.getExposedForm(this);
   }

   fun IllusionEffect() {
      this(null, 0.0F, 3, null);
   }

   public companion object {
      public final val ID: String
   }
}
