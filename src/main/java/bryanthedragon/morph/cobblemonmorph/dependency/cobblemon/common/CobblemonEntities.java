package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonChestBoatEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level

public object CobblemonEntities : PlatformRegistry<Registry<EntityType<?>>, ResourceKey<Registry<EntityType<?>>>, EntityType<?>> {
   public final val BOAT: EntityType<CobblemonBoatEntity>
   public final val BOAT_KEY: ResourceLocation = MiscUtilsKt.cobblemonResource("boat")
   public final val CHEST_BOAT: EntityType<CobblemonChestBoatEntity>
   public final val CHEST_BOAT_KEY: ResourceLocation = MiscUtilsKt.cobblemonResource("chest_boat")
   public final val EMPTY_POKEBALL: EntityType<EmptyPokeBallEntity>
   public final val EMPTY_POKEBALL_KEY: ResourceLocation = MiscUtilsKt.cobblemonResource("empty_pokeball")
   public final val GENERIC_BEDROCK_ENTITY: EntityType<GenericBedrockEntity>
   public final val GENERIC_BEDROCK_ENTITY_KEY: ResourceLocation = MiscUtilsKt.cobblemonResource("generic_bedrock")
   public final val POKEMON: EntityType<PokemonEntity>
   public final val POKEMON_KEY: ResourceLocation = MiscUtilsKt.cobblemonResource("pokemon")
   public open val registry: Registry<EntityType<*>>
   public open val registryKey: ResourceKey<Registry<EntityType<*>>>

   public fun registerAttributes(consumer: (EntityType<out LivingEntity>, Builder) -> Unit) {
      consumer.invoke(POKEMON, PokemonEntity.Companion.createAttributes());
   }

   @JvmStatic
   fun `POKEMON$lambda$0`(var0: EntityType, world: Level): PokemonEntity {
      return new PokemonEntity(world, null, null, 6, null);
   }

   @JvmStatic
   fun `EMPTY_POKEBALL$lambda$1`(var0: EntityType, world: Level): EmptyPokeBallEntity {
      val var10002: PokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
      return new EmptyPokeBallEntity(var10002, world, null, 4, null);
   }

   @JvmStatic
   fun `GENERIC_BEDROCK_ENTITY$lambda$2`(var0: EntityType, world: Level): GenericBedrockEntity {
      return new GenericBedrockEntity(world);
   }

   @JvmStatic
   fun {
      val var10000: DefaultedRegistry = BuiltInRegistries.f_256780_;
      registry = var10000 as Registry<EntityType<?>>;
      val var5: ResourceKey = Registries.f_256939_;
      registryKey = var5;
      val var6: CobblemonEntities = INSTANCE;
      var var10001: java.lang.String = POKEMON_KEY.m_135815_();
      var var0: Any = var6.create(
         var10001,
         net.minecraft.world.entity.EntityType.Builder.m_20704_(CobblemonEntities::POKEMON$lambda$0, MobCategory.CREATURE).m_20712_(POKEMON_KEY.toString())
      );
      POKEMON = var0 as EntityType<PokemonEntity>;
      val var7: CobblemonEntities = INSTANCE;
      var10001 = EMPTY_POKEBALL_KEY.m_135815_();
      var0 = var7.create(
         var10001,
         net.minecraft.world.entity.EntityType.Builder.m_20704_(CobblemonEntities::EMPTY_POKEBALL$lambda$1, MobCategory.MISC)
            .m_20712_(EMPTY_POKEBALL_KEY.toString())
      );
      EMPTY_POKEBALL = var0 as EntityType<EmptyPokeBallEntity>;
      val var8: CobblemonEntities = INSTANCE;
      var10001 = BOAT_KEY.m_135815_();
      var0 = var8.create(
         var10001,
         net.minecraft.world.entity.EntityType.Builder.m_20704_(CobblemonBoatEntity::new, MobCategory.MISC)
            .m_20699_(1.375F, 0.5625F)
            .m_20702_(10)
            .m_20712_(BOAT_KEY.toString())
      );
      BOAT = var0 as EntityType<CobblemonBoatEntity>;
      val var9: CobblemonEntities = INSTANCE;
      var10001 = CHEST_BOAT_KEY.m_135815_();
      var0 = var9.create(
         var10001,
         net.minecraft.world.entity.EntityType.Builder.m_20704_(CobblemonChestBoatEntity::new, MobCategory.MISC)
            .m_20699_(1.375F, 0.5625F)
            .m_20702_(10)
            .m_20712_(CHEST_BOAT_KEY.toString())
      );
      CHEST_BOAT = var0 as EntityType<CobblemonChestBoatEntity>;
      val var10: CobblemonEntities = INSTANCE;
      var10001 = GENERIC_BEDROCK_ENTITY_KEY.m_135815_();
      var0 = var10.create(
         var10001,
         net.minecraft.world.entity.EntityType.Builder.m_20704_(CobblemonEntities::GENERIC_BEDROCK_ENTITY$lambda$2, MobCategory.MISC)
            .m_20712_(GENERIC_BEDROCK_ENTITY_KEY.toString())
      );
      GENERIC_BEDROCK_ENTITY = var0 as EntityType<GenericBedrockEntity>;
   }
}
