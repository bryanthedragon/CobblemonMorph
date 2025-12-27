package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text

import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style

@SourceDebugExtension(["SMAP\nText.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/Text\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,183:1\n13579#2:184\n13580#2:186\n1#3:185\n*S KotlinDebug\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/Text\n*L\n41#1:184\n41#1:186\n*E\n"])
public class Text internal constructor() {
   private final var head: MutableComponent?
   private final var style: Style = Style.f_131099_

   public fun parse(vararg components: Any): MutableComponent {
      for (Object element$iv : components) {
         if (`element$iv` is MutableComponent) {
            this.addComponent(`element$iv` as MutableComponent);
            this.style = this.getBlankStyle();
         } else if (`element$iv` is ClickEvent) {
            this.style = this.style.m_131142_(`element$iv` as ClickEvent);
         } else if (`element$iv` is HoverEvent) {
            this.style = this.style.m_131144_(`element$iv` as HoverEvent);
         } else if (`element$iv` is ChatFormatting) {
            if ((`element$iv` as ChatFormatting).m_126664_()) {
               this.style = this.style.m_131140_(`element$iv` as ChatFormatting);
            } else if (`element$iv` === ChatFormatting.UNDERLINE || `element$iv` == TextKt.getUNDERLINED()) {
               this.style = this.style.m_131162_(true);
            } else if (`element$iv` === ChatFormatting.BOLD || `element$iv` == TextKt.getBOLD()) {
               this.style = this.style.m_131136_(true);
            } else if (`element$iv` === ChatFormatting.ITALIC || `element$iv` == TextKt.getITALIC()) {
               this.style = this.style.m_131155_(true);
            } else if (`element$iv` === ChatFormatting.OBFUSCATED || `element$iv` == TextKt.getOBFUSCATED()) {
               this.style = this.style.m_178524_(true);
            } else if (`element$iv` === ChatFormatting.RESET || `element$iv` == TextKt.getRESET()) {
               this.style = Style.f_131099_;
            }
         } else {
            val var10: MutableComponent = Companion.resolveComponent$common(`element$iv`);
            var10.m_6270_(this.style.m_131146_(var10.m_7383_()));
            this.addComponent(var10);
         }
      }

      var var10000: MutableComponent = this.head;
      if (this.head == null) {
         var10000 = Component.m_237113_("Empty!");
      }

      return var10000;
   }

   private fun addComponent(component: MutableComponent) {
      if (this.head == null) {
         this.head = component;
         component.m_6270_(this.style.m_131146_(component.m_7383_()));
         this.style = this.getBlankStyle();
      } else if (this.head != null) {
         val var5: MutableComponent = this.head;
         component.m_6270_(this.style.m_131146_(component.m_7383_()));
         TextKt.add(var5, component as Component);
      }
   }

   private fun getBlankStyle(): Style {
      return Style.f_131099_
         .m_131136_(false)
         .m_131155_(false)
         .m_131162_(false)
         .m_178524_(false)
         .m_131140_(ChatFormatting.WHITE)
         .m_131142_(null)
         .m_131144_(null);
   }

   public companion object {
      internal fun resolveComponent(text: Any): MutableComponent {
         val var10000: MutableComponent = Component.m_237115_(new Regex("&[A-Fa-f\\dk-oK-oRr]").replace(text.toString(), <unrepresentable>.INSTANCE));
         return var10000;
      }
   }
}
