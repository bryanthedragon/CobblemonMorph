package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.LinkedHashSet
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nRenderablePokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RenderablePokemon.kt\ncom/cobblemon/mod/common/pokemon/RenderablePokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n1855#2,2:44\n*S KotlinDebug\n*F\n+ 1 RenderablePokemon.kt\ncom/cobblemon/mod/common/pokemon/RenderablePokemon\n*L\n29#1:44,2\n*E\n"])
public data RenderablePokemon(species: Species, aspects: Set<String>) {
   public final var aspects: Set<String>

   public final val form: FormData
      public final get() {
         return this.form$delegate.getValue() as FormData;
      }


   public final var species: Species

   init {
      this.species = species;
      this.aspects = aspects;
      this.form$delegate = LazyKt.lazy((new Function0<FormData>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         @NotNull
         public final FormData invoke() {
            return this.this$0.getSpecies().getForm(this.this$0.getAspects());
         }
      }) as Function0);
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf): FriendlyByteBuf {
      buffer.m_130085_(this.species.getResourceIdentifier());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.aspects.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }

      return buffer;
   }

   public operator fun component1(): Species {
      return this.species;
   }

   public operator fun component2(): Set<String> {
      return this.aspects;
   }

   public fun copy(species: Species = this.species, aspects: Set<String> = this.aspects): RenderablePokemon {
      return new RenderablePokemon(species, aspects);
   }

   public override fun toString(): String {
      return "RenderablePokemon(species=${this.species}, aspects=${this.aspects})";
   }

   public override fun hashCode(): Int {
      return this.species.hashCode() * 31 + this.aspects.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is RenderablePokemon) {
         return false;
      } else {
         val var2: RenderablePokemon = other as RenderablePokemon;
         if (!(this.species == (other as RenderablePokemon).species)) {
            return false;
         } else {
            return this.aspects == var2.aspects;
         }
      }
   }

   public companion object {
      public fun loadFromBuffer(buffer: FriendlyByteBuf): RenderablePokemon {
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         val var10001: ResourceLocation = buffer.m_130281_();
         val var8: Species = var10000.getByIdentifier(var10001);
         val aspects: java.util.Set = new LinkedHashSet();
         val var4: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

         for (int var5 = 0; var5 < var4; var5++) {
            val var9: java.lang.String = buffer.m_130277_();
            aspects.add(var9);
         }

         return new RenderablePokemon(var8, aspects);
      }
   }
}
