package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nItemDropEntry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemDropEntry.kt\ncom/cobblemon/mod/common/api/drop/ItemDropEntry\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"])
public open class ItemDropEntry : DropEntry {
   public open val dropMethod: ItemDropMethod?
   public open val item: ResourceLocation = new ResourceLocation("minecraft:fish")
   public open val maxSelectableTimes: Int = 1
   public open val nbt: CompoundTag?
   public open val percentage: Float = 100.0F
   public open val quantity: Int = 1
   public open val quantityRange: IntRange?

   public override fun drop(entity: LivingEntity?, world: ServerLevel, pos: Vec3, player: ServerPlayer?) {
      val var10000: Item = world.m_9598_().m_175515_(Registries.f_256913_).m_7745_(this.getItem()) as Item;
      if (var10000 == null) {
         Cobblemon.INSTANCE.getLOGGER().error("Unable to load drop item: ${this.getItem()}");
      } else {
         val var10002: ItemLike = var10000 as ItemLike;
         val var10003: IntRange = this.getQuantityRange();
         val stack: ItemStack = new ItemStack(var10002, if (var10003 != null) RangesKt.random(var10003, Random.Default as Random) else this.getQuantity());
         val inLava: Boolean = world.m_8055_(Vec3ExtensionsKt.toBlockPos(pos)).m_60734_() == Blocks.f_49991_;
         var var18: ItemDropMethod = this.getDropMethod();
         if (var18 == null) {
            var18 = Cobblemon.INSTANCE.getConfig().getDefaultDropItemMethod();
         }

         val dropMethod: ItemDropMethod = if (inLava) ItemDropMethod.TO_INVENTORY else var18;
         val var19: CompoundTag = this.getNbt();
         if (var19 != null) {
            stack.m_41751_(var19);
         }

         if (dropMethod === ItemDropMethod.ON_PLAYER && player != null) {
            world.m_7967_((new ItemEntity(player.m_9236_(), player.m_20185_(), player.m_20186_(), player.m_20189_(), stack)) as Entity);
         } else if (dropMethod === ItemDropMethod.TO_INVENTORY && player != null) {
            val name: Component = stack.m_41786_();
            val var14: Int = stack.m_41613_();
            val var16: Boolean = player.m_36356_(stack);
            if (Cobblemon.INSTANCE.getConfig().getAnnounceDropItems()) {
               var var10001: MutableComponent;
               if (var16) {
                  val var17: Array<Any> = new Object[]{var14, null};
                  val var10004: MutableComponent = name.m_6881_();
                  var17[1] = TextKt.green(var10004);
                  var10001 = LocalizationUtilsKt.lang("drop.item.inventory", var17);
               } else {
                  val var13: Array<Any> = new Object[1];
                  var13[0] = name;
                  var10001 = LocalizationUtilsKt.lang("drop.item.full", var13);
                  var10001 = TextKt.red(var10001);
               }

               player.m_213846_(var10001 as Component);
            }
         } else if (dropMethod === ItemDropMethod.ON_ENTITY && entity != null) {
            world.m_7967_((new ItemEntity(entity.m_9236_(), entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), stack)) as Entity);
         } else {
            world.m_7967_((new ItemEntity(world as Level, pos.f_82479_, pos.f_82480_, pos.f_82481_, stack)) as Entity);
         }
      }
   }
}
