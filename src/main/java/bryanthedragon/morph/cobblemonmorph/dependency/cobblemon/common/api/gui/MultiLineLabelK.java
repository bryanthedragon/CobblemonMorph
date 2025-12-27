package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui

import java.util.stream.Collectors
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nMultiLineLabelK.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MultiLineLabelK.kt\ncom/cobblemon/mod/common/api/gui/MultiLineLabelK\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1864#2,3:62\n*S KotlinDebug\n*F\n+ 1 MultiLineLabelK.kt\ncom/cobblemon/mod/common/api/gui/MultiLineLabelK\n*L\n48#1:62,3\n*E\n"])
public class MultiLineLabelK(comps: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK.TextWithWidth>, font: ResourceLocation? = null) {
   private final val comps: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK.TextWithWidth>
   private final val font: ResourceLocation?

   init {
      this.comps = comps;
      this.font = font;
   }

   public fun renderLeftAligned(context: GuiGraphics, x: Number, y: Number, ySpacing: Number, colour: Int, shadow: Boolean = true) {
      val `$this$forEachIndexed$iv`: java.lang.Iterable = this.comps;
      var `index$iv`: Int = 0;

      for (Object item$iv : $this$forEachIndexed$iv) {
         val var12: Int = `index$iv`++;
         if (var12 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         val textWithWidth: MultiLineLabelK.TextWithWidth = `item$iv` as MultiLineLabelK.TextWithWidth;
         val var16: Float = y.floatValue() + ySpacing.floatValue() * var12;
         val var17: java.lang.String = textWithWidth.getText().getString();
         val var18: ResourceLocation = this.font;
         GuiUtilsKt.drawString(context, var17, x, var16, colour, shadow, var18);
      }
   }

   public companion object {
      private final val mcFont: Font

      public fun create(component: Component, width: Number, maxLines: Number): MultiLineLabelK {
         return this.create(component, width, maxLines, null);
      }

      public fun create(component: Component, width: Number, maxLines: Number, font: ResourceLocation?): MultiLineLabelK {
         val var5: Any = MultiLineLabelK.access$getMcFont$cp()
            .m_92865_()
            .m_92414_(component as FormattedText, width.intValue(), Style.f_131099_)
            .stream()
            .limit(maxLines.longValue())
            .map(MultiLineLabelK.Companion::create$lambda$0)
            .collect(Collectors.toList());
         return new MultiLineLabelK(var5 as MutableList<MultiLineLabelK.TextWithWidth>, font);
      }

      @JvmStatic
      fun `create$lambda$0`(`$tmp0`: Function1, p0: Any): MultiLineLabelK.TextWithWidth {
         return `$tmp0`.invoke(p0) as MultiLineLabelK.TextWithWidth;
      }
   }

   public class TextWithWidth internal constructor(text: FormattedText, width: Int) {
      public final val text: FormattedText
      public final val width: Int

      init {
         this.text = text;
         this.width = width;
      }
   }
}
