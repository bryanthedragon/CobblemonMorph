package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import java.util.Optional
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.HolderSet.Named
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike

@SourceDebugExtension(["SMAP\nPossibleHeldItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PossibleHeldItem.kt\ncom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,53:1\n1#2:54\n*E\n"])
public class PossibleHeldItem(item: String, nbt: CompoundTag? = null, percentage: Double = 100.0) {
   public final val item: String
   public final val nbt: CompoundTag?
   public final val percentage: Double

   init {
      this.item = item;
      this.nbt = nbt;
      this.percentage = percentage;
   }

   public fun createStack(ctx: SpawningContext): ItemStack? {
      val itemRegistry: Registry = ctx.getWorld().m_9598_().m_175515_(Registries.f_256913_);
      var var12: Item;
      if (StringsKt.startsWith$default(this.item, "#", false, 2, null)) {
         val var10000: ResourceKey = Registries.f_256913_;
         val var10003: java.lang.String = this.item.substring(1);
         val `$this$createStack_u24lambda_u241`: Optional = itemRegistry.m_203431_(TagKey.m_203882_(var10000, new ResourceLocation(var10003)));
         if (`$this$createStack_u24lambda_u241`.isPresent() && (`$this$createStack_u24lambda_u241`.get() as Named).m_203632_() > 0) {
            var12 = (Item)`$this$createStack_u24lambda_u241`.get();
            var12 = ((var12 as Named).m_213653_(ctx.getWorld().f_46441_).get() as Holder).m_203334_() as Item;
         } else {
            Cobblemon.INSTANCE.getLOGGER().error("Unable to find matching spawn held items for tag: ${this.item}");
            var12 = null;
         }
      } else {
         var12 = itemRegistry.m_7745_(new ResourceLocation(this.item)) as Item;
         var12 = if (var12 != null) (if (!(var12 == Items.f_41852_)) var12 else null) else null;
      }

      if (var12 == null) {
         Cobblemon.INSTANCE.getLOGGER().error("Unable to find matching spawn held item for ID: ${this.item}");
         return null;
      } else {
         val stack: ItemStack = new ItemStack(var12 as ItemLike, 1);
         if (this.nbt != null) {
            stack.m_41751_(this.nbt);
         }

         return stack;
      }
   }
}
