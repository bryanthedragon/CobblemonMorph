package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

public data ItemTooltipEvent(stack: ItemStack, context: TooltipFlag, lines: MutableList<Component>) {
   public final val context: TooltipFlag
   public final val lines: MutableList<Component>
   public final val stack: ItemStack

   init {
      this.stack = stack;
      this.context = context;
      this.lines = lines;
   }

   public operator fun component1(): ItemStack {
      return this.stack;
   }

   public operator fun component2(): TooltipFlag {
      return this.context;
   }

   public operator fun component3(): MutableList<Component> {
      return this.lines;
   }

   public fun copy(stack: ItemStack = this.stack, context: TooltipFlag = this.context, lines: MutableList<Component> = this.lines): ItemTooltipEvent {
      return new ItemTooltipEvent(stack, context, lines);
   }

   public override fun toString(): String {
      return "ItemTooltipEvent(stack=${this.stack}, context=${this.context}, lines=${this.lines})";
   }

   public override fun hashCode(): Int {
      return (this.stack.hashCode() * 31 + this.context.hashCode()) * 31 + this.lines.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is ItemTooltipEvent) {
         return false;
      } else {
         val var2: ItemTooltipEvent = other as ItemTooltipEvent;
         if (!(this.stack == (other as ItemTooltipEvent).stack)) {
            return false;
         } else if (!(this.context == var2.context)) {
            return false;
         } else {
            return this.lines == var2.lines;
         }
      }
   }
}
