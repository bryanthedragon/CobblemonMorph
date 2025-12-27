package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.item.ItemStack

public open class PartySelectPokemonDTO(pokemonProperties: PokemonProperties, aspects: Set<String>, heldItem: ItemStack = ItemStack.f_41583_, currentHealth: Int, maxHealth: Int, enabled: Boolean) {
   public final val aspects: Set<String>
   public final var currentHealth: Int
   public final var enabled: Boolean
   public final val heldItem: ItemStack
   public final var maxHealth: Int
   public final val pokemonProperties: PokemonProperties

   init {
      this.pokemonProperties = pokemonProperties;
      this.aspects = aspects;
      this.heldItem = heldItem;
      this.currentHealth = currentHealth;
      this.maxHealth = maxHealth;
      this.enabled = enabled;
   }

   @JvmOverloads
   public constructor(pokemon: Pokemon, enabled: Boolean = true) : this(
         pokemon.createPokemonProperties(
            PokemonPropertyExtractor.SPECIES,
            PokemonPropertyExtractor.LEVEL,
            PokemonPropertyExtractor.NICKNAME,
            PokemonPropertyExtractor.POKEBALL,
            PokemonPropertyExtractor.STATUS
         ),
         pokemon.getAspects(),
         pokemon.heldItemNoCopy$common(),
         pokemon.getCurrentHealth(),
         pokemon.getHp(),
         enabled
      )
   public constructor(buffer: FriendlyByteBuf)  {
      var var10001: PokemonProperties = new PokemonProperties();
      val var10002: CompoundTag = buffer.m_130260_();
      var10001 = var10001.loadFromNBT(var10002);
      val var3: java.util.List = buffer.m_236845_(PartySelectPokemonDTO::_init_$lambda$0);
      val var4: java.util.Set = CollectionsKt.toSet(var3);
      val var10003: ItemStack = buffer.m_130267_();
      this(var10001, var4, var10003, buffer.readInt(), buffer.readInt(), buffer.readBoolean());
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130079_(this.pokemonProperties.saveToNBT());
      buffer.m_236828_(this.aspects, PartySelectPokemonDTO::writeToBuffer$lambda$1);
      buffer.m_130055_(this.heldItem);
      buffer.writeInt(this.currentHealth);
      buffer.writeInt(this.maxHealth);
      buffer.writeBoolean(this.enabled);
   }

   @JvmOverloads
   open fun PartySelectPokemonDTO(pokemon: Pokemon) {
      this(pokemon, false, 2, null);
   }

   @JvmStatic
   fun `_init_$lambda$0`(it: FriendlyByteBuf): java.lang.String {
      return it.m_130277_();
   }

   @JvmStatic
   fun `writeToBuffer$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, aspect: java.lang.String) {
      `$buffer`.m_130070_(aspect);
   }
}
