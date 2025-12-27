package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel

import kotlin.jvm.functions.Function0
import net.minecraft.resources.ResourceLocation
import org.joml.Vector3f

public data InteractWheelOption(iconResource: ResourceLocation,
   tooltipText: String?,
   colour: () -> Vector3f? = <unrepresentable>.INSTANCE as Function0,
   onPress: () -> Unit
) {
   public final val colour: () -> Vector3f?
   public final val iconResource: ResourceLocation
   public final val onPress: () -> Unit
   public final val tooltipText: String?

   init {
      this.iconResource = iconResource;
      this.tooltipText = tooltipText;
      this.colour = colour;
      this.onPress = onPress;
   }

   public operator fun component1(): ResourceLocation {
      return this.iconResource;
   }

   public operator fun component2(): String? {
      return this.tooltipText;
   }

   public operator fun component3(): () -> Vector3f? {
      return this.colour;
   }

   public operator fun component4(): () -> Unit {
      return this.onPress;
   }

   public fun copy(
      iconResource: ResourceLocation = this.iconResource,
      tooltipText: String? = this.tooltipText,
      colour: () -> Vector3f? = this.colour,
      onPress: () -> Unit = this.onPress
   ): InteractWheelOption {
      return new InteractWheelOption(iconResource, tooltipText, colour, onPress);
   }

   public override fun toString(): String {
      return "InteractWheelOption(iconResource=${this.iconResource}, tooltipText=${this.tooltipText}, colour=${this.colour}, onPress=${this.onPress})";
   }

   public override fun hashCode(): Int {
      return ((this.iconResource.hashCode() * 31 + (if (this.tooltipText == null) 0 else this.tooltipText.hashCode())) * 31 + this.colour.hashCode()) * 31
         + this.onPress.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is InteractWheelOption) {
         return false;
      } else {
         val var2: InteractWheelOption = other as InteractWheelOption;
         if (!(this.iconResource == (other as InteractWheelOption).iconResource)) {
            return false;
         } else if (!(this.tooltipText == var2.tooltipText)) {
            return false;
         } else if (!(this.colour == var2.colour)) {
            return false;
         } else {
            return this.onPress == var2.onPress;
         }
      }
   }
}
