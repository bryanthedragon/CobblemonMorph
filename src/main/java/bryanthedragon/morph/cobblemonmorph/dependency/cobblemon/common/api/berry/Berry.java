package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryYieldCalculationEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.awt.Color
import java.util.ArrayList;
import java.util.Arrays
import java.util.EnumSet
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

@SourceDebugExtension(["SMAP\nBerry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,348:1\n17#2,2:349\n19#2:353\n13579#3,2:351\n1#4:354\n1855#5,2:355\n1855#5,2:357\n1855#5,2:359\n*S KotlinDebug\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry\n*L\n169#1:349,2\n169#1:353\n169#1:351,2\n238#1:355,2\n297#1:357,2\n310#1:359,2\n*E\n"])
public class Berry(identifier: ResourceLocation,
   baseYield: IntRange,
   preferredBiomeTags: List<TagKey<Biome>>,
   growthTime: IntRange,
   refreshRate: IntRange,
   favoriteMulches: EnumSet<MulchVariant>,
   growthFactors: Collection<GrowthFactor>,
   spawnConditions: List<BerrySpawnCondition>,
   vararg growthPoints: Any,
   randomizedGrowthPoints: Boolean = true,
   mutations: Map<ResourceLocation, ResourceLocation>,
   sproutShapeBoxes: Collection<AABB>,
   matureShapeBoxes: Collection<AABB>,
   flavors: Map<Flavor, Int>,
   tintIndexes: Map<Int, Color>,
   flowerModelIdentifier: ResourceLocation,
   flowerTexture: ResourceLocation,
   fruitModelIdentifier: ResourceLocation,
   fruitTexture: ResourceLocation,
   weight: Float
) {
   public final val baseYield: IntRange
   public final val favoriteMulches: EnumSet<MulchVariant>
   private final val flavors: Map<Flavor, Int>
   public final val flowerModelIdentifier: ResourceLocation
   public final val flowerTexture: ResourceLocation
   public final val fruitModelIdentifier: ResourceLocation
   public final val fruitTexture: ResourceLocation
   public final val growthFactors: Collection<GrowthFactor>
   public final var growthPoints: Array<GrowthPoint>
   public final val growthTime: IntRange

   public final var identifier: ResourceLocation
      public final set(<set-?>) {
         this.identifier = `<set-?>`;
      }


   public final lateinit var matureShape: VoxelShape
      private set

   private final val matureShapeBoxes: Collection<AABB>
   public final val mutations: Map<ResourceLocation, ResourceLocation>
   public final val preferredBiomeTags: List<TagKey<Biome>>
   public final val randomizedGrowthPoints: Boolean
   public final val refreshRate: IntRange
   private final lateinit var shapedFlower: HashMap<Int, VoxelShape>
   private final lateinit var shapedFruit: HashMap<Int, VoxelShape>
   public final val spawnConditions: List<BerrySpawnCondition>

   public final lateinit var sproutShape: VoxelShape
      private set

   private final val sproutShapeBoxes: Collection<AABB>
   public final val tintIndexes: Map<Int, Color>
   public final val weight: Float

   init {
      this.baseYield = baseYield;
      this.preferredBiomeTags = preferredBiomeTags;
      this.growthTime = growthTime;
      this.refreshRate = refreshRate;
      this.favoriteMulches = favoriteMulches;
      this.growthFactors = growthFactors;
      this.spawnConditions = spawnConditions;
      this.growthPoints = growthPoints;
      this.randomizedGrowthPoints = randomizedGrowthPoints;
      this.mutations = mutations;
      this.sproutShapeBoxes = sproutShapeBoxes;
      this.matureShapeBoxes = matureShapeBoxes;
      this.flavors = flavors;
      this.tintIndexes = tintIndexes;
      this.flowerModelIdentifier = flowerModelIdentifier;
      this.flowerTexture = flowerTexture;
      this.fruitModelIdentifier = fruitModelIdentifier;
      this.fruitTexture = fruitTexture;
      this.weight = weight;
      this.identifier = identifier;
      this.validate$common();
   }

   public fun item(): BerryItem? {
      return CobblemonItems.INSTANCE.berries().get(this.identifier);
   }

   public fun block(): BerryBlock? {
      return CobblemonBlocks.INSTANCE.berries().get(this.identifier);
   }

   public fun flavor(flavor: Flavor): Int {
      val var10000: Int = this.flavors.get(flavor);
      return var10000 ?: 0;
   }

   public fun dislikedBy(nature: Nature): Boolean {
      val var10000: Flavor = nature.getDislikedFlavor();
      if (var10000 == null) {
         return false;
      } else {
         return this.flavor(var10000) > 0;
      }
   }

   public fun calculateYield(world: Level, state: BlockState, pos: BlockPos, placer: LivingEntity? = null): Int {
      val base: Int = RangesKt.random(this.baseYield, Random.Default as Random);
      val bonus: Pair = this.bonusYield(world, state, pos);
      var var20: Int = base + (bonus.getFirst() as java.lang.Number).intValue();
      val var10000: BlockEntity = world.m_7702_(pos);
      val treeEntity: BerryBlockEntity = var10000 as BerryBlockEntity;
      if (BerryBlock.Companion.getMulch(state) === MulchVariant.RICH) {
         var20 = Math.min(var20 + 1, this.maxYield());
         treeEntity.decrementMulchDuration(world, pos, state);
      }

      val event: BerryYieldCalculationEvent = new BerryYieldCalculationEvent(
         this, world, state, pos, placer, var20, bonus.getSecond() as MutableCollection<GrowthFactor>
      );
      val `this_$iv`: EventObservable = CobblemonEvents.BERRY_YIELD;
      val `events$iv`: Array<BerryYieldCalculationEvent> = new BerryYieldCalculationEvent[]{event};
      `this_$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

      for (Object element$iv$iv : events$iv) {
         var20 = ((BerryYieldCalculationEvent)`element$iv$iv`).getYield();
      }

      return var20;
   }

   public fun minYield(): Int {
      val var10000: Int = this.baseYield.getFirst();
      val var1: java.lang.Iterable = this.growthFactors;
      var var2: Int = 0;

      for (Object var4 : var1) {
         var2 += (var4 as GrowthFactor).minYield();
      }

      return var10000 + var2;
   }

   public fun maxYield(): Int {
      val var10000: Int = this.baseYield.getLast();
      val var1: java.lang.Iterable = this.growthFactors;
      var var2: Int = 0;

      for (Object var4 : var1) {
         var2 += (var4 as GrowthFactor).maxYield();
      }

      return var10000 + var2;
   }

   public fun canMutateWith(partner: Berry): Boolean {
      return this.mutationWith(partner) != null;
   }

   public fun mutationWith(partner: Berry): Berry? {
      val var10000: ResourceLocation = this.mutations.get(partner.identifier);
      return if (var10000 == null) null else Berries.INSTANCE.getByIdentifier(var10000);
   }

   public fun partnerForMutation(resulting: Berry): Berry? {
      val var2: java.util.Iterator = this.mutations.entrySet().iterator();

      var var10000: Berry;
      while (true) {
         if (var2.hasNext()) {
            val var3: Entry = var2.next() as Entry;
            val var7: Berry = if (var3.getValue() as ResourceLocation == resulting.identifier)
               Berries.INSTANCE.getByIdentifier(var3.getKey() as ResourceLocation)
               else
               null;
            if (var7 == null) {
               continue;
            }

            var10000 = var7;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000;
   }

   internal fun validate() {
      if (this.baseYield.getFirst() < 0 || this.baseYield.getLast() < 0) {
         throw new IllegalArgumentException("A berry base yield must be a positive range");
      } else if (this.growthTime.getFirst() < 0 || this.growthTime.getLast() < 0) {
         throw new IllegalArgumentException("The growth time must be a positive range");
      } else if (this.refreshRate.getFirst() >= 0 && this.refreshRate.getLast() >= 0) {
         val maxYield: java.lang.Iterable;
         for (Object element$iv : maxYield) {
            (`element$iv` as GrowthFactor).validateArguments();
         }

         val var7: Int = this.maxYield();
         if (this.growthPoints.length < var7) {
            throw new IllegalArgumentException(
               "Anchor points must have enough elements for the max possible yield of $var7 you've provided ${this.growthPoints.length} points"
            );
         } else {
            this.shapedFlower = new HashMap<>();
            this.shapedFruit = new HashMap<>();
            this.sproutShape = this.createAndUniteShapes(this.sproutShapeBoxes);
            this.matureShape = this.createAndUniteShapes(this.matureShapeBoxes);
         }
      } else {
         throw new IllegalArgumentException("The refresh rate must be a positive range");
      }
   }

   internal fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.identifier);
      buffer.writeInt(this.baseYield.getFirst());
      buffer.writeInt(this.baseYield.getLast());
      buffer.m_245616_(this.favoriteMulches, MulchVariant::class.java);
      buffer.writeInt(this.growthTime.getFirst());
      buffer.writeInt(this.growthTime.getLast());
      buffer.writeInt(this.refreshRate.getFirst());
      buffer.writeInt(this.refreshRate.getLast());
      buffer.m_236828_(ArraysKt.toList(this.growthPoints), Berry::encode$lambda$5);
      buffer.writeBoolean(this.randomizedGrowthPoints);
      buffer.m_236831_(this.mutations, Berry::encode$lambda$6, Berry::encode$lambda$7);
      buffer.m_236828_(this.sproutShapeBoxes, Berry::encode$lambda$8);
      buffer.m_236828_(this.matureShapeBoxes, Berry::encode$lambda$9);
      buffer.m_236831_(this.flavors, Berry::encode$lambda$10, Berry::encode$lambda$11);
      buffer.m_236831_(this.tintIndexes, Berry::encode$lambda$12, Berry::encode$lambda$13);
      buffer.m_130085_(this.flowerModelIdentifier);
      buffer.m_130085_(this.flowerTexture);
      buffer.m_130085_(this.fruitModelIdentifier);
      buffer.m_130085_(this.fruitTexture);
   }

   private fun bonusYield(world: Level, state: BlockState, pos: BlockPos): Pair<Int, Collection<GrowthFactor>> {
      var bonus: Int = 0;
      val passed: ArrayList = new ArrayList();
      val hasBiomeMulch: Boolean = this.favoriteMulches.contains(BerryBlock.Companion.getMulch(state));

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val factor: GrowthFactor = `element$iv` as GrowthFactor;
         if ((`element$iv` as GrowthFactor).isValid(world as LevelReader, state, pos)) {
            bonus += factor.yield();
            passed.add(factor);
         } else if (hasBiomeMulch) {
            bonus += factor.yield();
         }
      }

      return TuplesKt.to(bonus, passed);
   }

   private fun createAndUniteShapes(boxes: Collection<AABB>): VoxelShape {
      var shape: Any = null;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         shape = if (shape == null)
            Block.m_49796_(
               (`element$iv` as AABB).f_82288_,
               (`element$iv` as AABB).f_82289_,
               (`element$iv` as AABB).f_82290_,
               (`element$iv` as AABB).f_82291_,
               (`element$iv` as AABB).f_82292_,
               (`element$iv` as AABB).f_82293_
            )
            else
            Shapes.m_83110_(
               (VoxelShape)shape,
               Block.m_49796_(
                  (`element$iv` as AABB).f_82288_,
                  (`element$iv` as AABB).f_82289_,
                  (`element$iv` as AABB).f_82290_,
                  (`element$iv` as AABB).f_82291_,
                  (`element$iv` as AABB).f_82292_,
                  (`element$iv` as AABB).f_82293_
               )
            );
      }

      var var10000: Any = shape;
      if (shape == null) {
         var10000 = Shapes.m_83144_();
      }

      return (VoxelShape)var10000;
   }

   @JvmStatic
   fun `encode$lambda$5`(writer: FriendlyByteBuf, value: GrowthPoint) {
      writer.writeDouble(value.getPosition().f_82479_);
      writer.writeDouble(value.getPosition().f_82480_);
      writer.writeDouble(value.getPosition().f_82481_);
      writer.writeDouble(value.getRotation().f_82479_);
      writer.writeDouble(value.getRotation().f_82480_);
      writer.writeDouble(value.getRotation().f_82481_);
   }

   @JvmStatic
   fun `encode$lambda$6`(writer: FriendlyByteBuf, key: ResourceLocation) {
      writer.m_130085_(key);
   }

   @JvmStatic
   fun `encode$lambda$7`(writer: FriendlyByteBuf, value: ResourceLocation) {
      writer.m_130085_(value);
   }

   @JvmStatic
   fun `encode$lambda$8`(writer: FriendlyByteBuf, value: AABB) {
      val var10000: ByteBuf = writer as ByteBuf;
      NetExtensionsKt.writeBox(var10000, value);
   }

   @JvmStatic
   fun `encode$lambda$9`(writer: FriendlyByteBuf, value: AABB) {
      val var10000: ByteBuf = writer as ByteBuf;
      NetExtensionsKt.writeBox(var10000, value);
   }

   @JvmStatic
   fun `encode$lambda$10`(writer: FriendlyByteBuf, key: Flavor) {
      writer.m_130068_(key);
   }

   @JvmStatic
   fun `encode$lambda$11`(writer: FriendlyByteBuf, value: Int) {
      writer.writeInt(value);
   }

   @JvmStatic
   fun `encode$lambda$12`(writer: FriendlyByteBuf, key: Int) {
      writer.writeInt(key);
   }

   @JvmStatic
   fun `encode$lambda$13`(writer: FriendlyByteBuf, value: Color) {
      writer.writeInt(value.getRGB());
   }

   @SourceDebugExtension(["SMAP\nBerry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry$Companion\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,348:1\n37#2,2:349\n*S KotlinDebug\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry$Companion\n*L\n331#1:349,2\n*E\n"])
   public companion object {
      internal fun decode(buffer: FriendlyByteBuf): Berry {
         val identifier: ResourceLocation = buffer.m_130281_();
         val baseYield: IntRange = new IntRange(buffer.readInt(), buffer.readInt());
         val favMulchs: EnumSet = buffer.m_247336_(MulchVariant.class);
         val growthTime: IntRange = new IntRange(buffer.readInt(), buffer.readInt());
         val refreshRate: IntRange = new IntRange(buffer.readInt(), buffer.readInt());
         val var10000: java.util.List = buffer.m_236845_(Berry.Companion::decode$lambda$0);
         val growthPoints: Array<GrowthPoint> = var10000.toArray(new GrowthPoint[0]);
         val var18: Boolean = buffer.readBoolean();
         val var19: java.util.Map = buffer.m_236847_(Berry.Companion::decode$lambda$1, Berry.Companion::decode$lambda$2);
         val sproutShapeBoxes: java.util.List = buffer.m_236845_(Berry.Companion::decode$lambda$3);
         val matureShapeBoxes: java.util.List = buffer.m_236845_(Berry.Companion::decode$lambda$4);
         val flavors: java.util.Map = buffer.m_236847_(Berry.Companion::decode$lambda$5, Berry.Companion::decode$lambda$6);
         val tintIndexes: java.util.Map = buffer.m_236847_(Berry.Companion::decode$lambda$7, Berry.Companion::decode$lambda$8);
         val flowerModelIdentifier: ResourceLocation = buffer.m_130281_();
         val flowerTexture: ResourceLocation = buffer.m_130281_();
         val fruitModelIdentifier: ResourceLocation = buffer.m_130281_();
         val fruitTexture: ResourceLocation = buffer.m_130281_();
         val var10004: java.util.List = CollectionsKt.emptyList();
         val var10008: java.util.Collection = SetsKt.emptySet();
         val var10009: java.util.List = CollectionsKt.emptyList();
         val var10013: java.util.Collection = sproutShapeBoxes;
         val var10014: java.util.Collection = matureShapeBoxes;
         return new Berry(
            identifier,
            baseYield,
            var10004,
            growthTime,
            refreshRate,
            favMulchs,
            var10008,
            var10009,
            growthPoints,
            var18,
            var19,
            var10013,
            var10014,
            flavors,
            tintIndexes,
            flowerModelIdentifier,
            flowerTexture,
            fruitModelIdentifier,
            fruitTexture,
            0.0F
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(reader: FriendlyByteBuf): GrowthPoint {
         return new GrowthPoint(
            new Vec3(reader.readDouble(), reader.readDouble(), reader.readDouble()), new Vec3(reader.readDouble(), reader.readDouble(), reader.readDouble())
         );
      }

      @JvmStatic
      fun `decode$lambda$1`(reader: FriendlyByteBuf): ResourceLocation {
         return reader.m_130281_();
      }

      @JvmStatic
      fun `decode$lambda$2`(reader: FriendlyByteBuf): ResourceLocation {
         return reader.m_130281_();
      }

      @JvmStatic
      fun `decode$lambda$3`(it: FriendlyByteBuf): AABB {
         return NetExtensionsKt.readBox(it as ByteBuf);
      }

      @JvmStatic
      fun `decode$lambda$4`(it: FriendlyByteBuf): AABB {
         return NetExtensionsKt.readBox(it as ByteBuf);
      }

      @JvmStatic
      fun `decode$lambda$5`(reader: FriendlyByteBuf): Flavor {
         return reader.m_130066_(Flavor.class) as Flavor;
      }

      @JvmStatic
      fun `decode$lambda$6`(reader: FriendlyByteBuf): Int {
         return reader.readInt();
      }

      @JvmStatic
      fun `decode$lambda$7`(reader: FriendlyByteBuf): Int {
         return reader.readInt();
      }

      @JvmStatic
      fun `decode$lambda$8`(reader: FriendlyByteBuf): Color {
         return new Color(reader.readInt());
      }
   }
}
