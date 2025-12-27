@file:SourceDebugExtension(["SMAP\nText.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/TextKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,183:1\n1#2:184\n2661#3,7:185\n*S KotlinDebug\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/TextKt\n*L\n183#1:185,7\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text

import java.util.Arrays
import java.util.HashMap
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.ClickEvent.Action
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo
import net.minecraft.network.chat.HoverEvent.ItemStackInfo
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

public final val BOLD: Object = new Object()
public final val ITALIC: Object = new Object()
public final val OBFUSCATED: Object = new Object()
public final val RESET: Object = new Object()
public final val UNDERLINED: Object = new Object()
public final val textClickHandlers: HashMap<UUID, (ServerPlayer) -> Unit> = new HashMap()

public fun text(vararg components: Any): MutableComponent {
   return new Text().parse(Arrays.copyOf(components, components.length));
}

public fun click(consumed: AtomicBoolean, action: (ServerPlayer) -> Unit): ClickEvent {
   val uuid: UUID = UUID.randomUUID();
   val var10000: java.util.Map = textClickHandlers;
   var10000.put(uuid, new Function1<ServerPlayer, Unit>(consumed, action, uuid) {
      {
         super(1);
         this.$consumed = `$consumed`;
         this.$action = `$action`;
         this.$uuid = `$uuid`;
      }

      public final void invoke(@NotNull ServerPlayer it) {
         if (!this.$consumed.get()) {
            this.$action.invoke(it);
            this.$consumed.set(true);
         }

         TextKt.getTextClickHandlers().remove(this.$uuid);
      }
   });
   return new ClickEvent(Action.RUN_COMMAND, "/cobblemonclicktext $uuid");
}

public fun click(onlyOnce: Boolean = false, action: (ServerPlayer) -> Unit): ClickEvent {
   val uuid: UUID = UUID.randomUUID();
   val var10000: java.util.Map = textClickHandlers;
   var10000.put(uuid, if (onlyOnce) (new Function1<ServerPlayer, Unit>(uuid, action) {
      {
         super(1);
         this.$uuid = `$uuid`;
         this.$action = `$action`;
      }

      public final void invoke(@NotNull ServerPlayer it) {
         TextKt.getTextClickHandlers().remove(this.$uuid);
         this.$action.invoke(it);
      }
   }) as Function1 else (new Function1<ServerPlayer, Unit>(action) {
      {
         super(1);
         this.$action = `$action`;
      }

      public final void invoke(@NotNull ServerPlayer it) {
         this.$action.invoke(it);
      }
   }) as Function1);
   return new ClickEvent(Action.RUN_COMMAND, "/cobblemonclicktext $uuid");
}

@JvmSynthetic
fun `click$default`(var0: Boolean, var1: Function1, var2: Int, var3: Any): ClickEvent {
   if ((var2 and 1) != 0) {
      var0 = false;
   }

   return click(var0, var1);
}

public fun hover(text: Component): HoverEvent {
   return new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130831_, text);
}

public fun hover(text: String): HoverEvent {
   val var10000: Component = Component.m_130674_(text);
   return hover(var10000);
}

public fun hover(item: ItemStack): HoverEvent {
   return new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130832_, new ItemStackInfo(item));
}

public fun hover(entity: LivingEntity): HoverEvent {
   return new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130833_, new EntityTooltipInfo(entity.m_6095_(), entity.m_20148_(), entity.m_5446_()));
}

public fun String.red(): MutableComponent {
   return text(ChatFormatting.RED, `$this$red`);
}

public fun String.black(): MutableComponent {
   return text(ChatFormatting.BLACK, `$this$black`);
}

public fun String.darkBlue(): MutableComponent {
   return text(ChatFormatting.DARK_BLUE, `$this$darkBlue`);
}

public fun String.darkGreen(): MutableComponent {
   return text(ChatFormatting.DARK_GREEN, `$this$darkGreen`);
}

public fun String.darkAqua(): MutableComponent {
   return text(ChatFormatting.DARK_AQUA, `$this$darkAqua`);
}

public fun String.darkRed(): MutableComponent {
   return text(ChatFormatting.DARK_RED, `$this$darkRed`);
}

public fun String.darkPurple(): MutableComponent {
   return text(ChatFormatting.DARK_PURPLE, `$this$darkPurple`);
}

public fun String.gold(): MutableComponent {
   return text(ChatFormatting.GOLD, `$this$gold`);
}

public fun String.gray(): MutableComponent {
   return text(ChatFormatting.GRAY, `$this$gray`);
}

public fun String.darkGray(): MutableComponent {
   return text(ChatFormatting.DARK_GRAY, `$this$darkGray`);
}

public fun String.blue(): MutableComponent {
   return text(ChatFormatting.BLUE, `$this$blue`);
}

public fun String.green(): MutableComponent {
   return text(ChatFormatting.GREEN, `$this$green`);
}

public fun String.aqua(): MutableComponent {
   return text(ChatFormatting.AQUA, `$this$aqua`);
}

public fun String.lightPurple(): MutableComponent {
   return text(ChatFormatting.LIGHT_PURPLE, `$this$lightPurple`);
}

public fun String.yellow(): MutableComponent {
   return text(ChatFormatting.YELLOW, `$this$yellow`);
}

public fun String.white(): MutableComponent {
   return text(ChatFormatting.WHITE, `$this$white`);
}

public fun MutableComponent.red(): MutableComponent {
   `$this$red`.m_6270_(`$this$red`.m_7383_().m_131140_(ChatFormatting.RED));
   return `$this$red`;
}

public fun MutableComponent.black(): MutableComponent {
   `$this$black`.m_6270_(`$this$black`.m_7383_().m_131140_(ChatFormatting.BLACK));
   return `$this$black`;
}

public fun MutableComponent.darkBlue(): MutableComponent {
   `$this$darkBlue`.m_6270_(`$this$darkBlue`.m_7383_().m_131140_(ChatFormatting.DARK_BLUE));
   return `$this$darkBlue`;
}

public fun MutableComponent.darkGreen(): MutableComponent {
   `$this$darkGreen`.m_6270_(`$this$darkGreen`.m_7383_().m_131140_(ChatFormatting.DARK_GREEN));
   return `$this$darkGreen`;
}

public fun MutableComponent.darkAqua(): MutableComponent {
   `$this$darkAqua`.m_6270_(`$this$darkAqua`.m_7383_().m_131140_(ChatFormatting.DARK_AQUA));
   return `$this$darkAqua`;
}

public fun MutableComponent.darkRed(): MutableComponent {
   `$this$darkRed`.m_6270_(`$this$darkRed`.m_7383_().m_131140_(ChatFormatting.DARK_RED));
   return `$this$darkRed`;
}

public fun MutableComponent.darkPurple(): MutableComponent {
   `$this$darkPurple`.m_6270_(`$this$darkPurple`.m_7383_().m_131140_(ChatFormatting.DARK_PURPLE));
   return `$this$darkPurple`;
}

public fun MutableComponent.gold(): MutableComponent {
   `$this$gold`.m_6270_(`$this$gold`.m_7383_().m_131140_(ChatFormatting.GOLD));
   return `$this$gold`;
}

public fun MutableComponent.gray(): MutableComponent {
   `$this$gray`.m_6270_(`$this$gray`.m_7383_().m_131140_(ChatFormatting.GRAY));
   return `$this$gray`;
}

public fun MutableComponent.darkGray(): MutableComponent {
   `$this$darkGray`.m_6270_(`$this$darkGray`.m_7383_().m_131140_(ChatFormatting.DARK_GRAY));
   return `$this$darkGray`;
}

public fun MutableComponent.blue(): MutableComponent {
   `$this$blue`.m_6270_(`$this$blue`.m_7383_().m_131140_(ChatFormatting.BLUE));
   return `$this$blue`;
}

public fun MutableComponent.green(): MutableComponent {
   `$this$green`.m_6270_(`$this$green`.m_7383_().m_131140_(ChatFormatting.GREEN));
   return `$this$green`;
}

public fun MutableComponent.aqua(): MutableComponent {
   `$this$aqua`.m_6270_(`$this$aqua`.m_7383_().m_131140_(ChatFormatting.AQUA));
   return `$this$aqua`;
}

public fun MutableComponent.lightPurple(): MutableComponent {
   `$this$lightPurple`.m_6270_(`$this$lightPurple`.m_7383_().m_131140_(ChatFormatting.LIGHT_PURPLE));
   return `$this$lightPurple`;
}

public fun MutableComponent.yellow(): MutableComponent {
   `$this$yellow`.m_6270_(`$this$yellow`.m_7383_().m_131140_(ChatFormatting.YELLOW));
   return `$this$yellow`;
}

public fun MutableComponent.white(): MutableComponent {
   `$this$white`.m_6270_(`$this$white`.m_7383_().m_131140_(ChatFormatting.WHITE));
   return `$this$white`;
}

public fun MutableComponent.font(identifier: ResourceLocation): MutableComponent {
   `$this$font`.m_6270_(`$this$font`.m_7383_().m_131150_(identifier));
   return `$this$font`;
}

public fun String.text(): MutableComponent {
   return text(`$this$text`);
}

public fun String.stripCodes(): String {
   return new Regex("[&§][A-Ea-e0-9K-Ok-oRr]").replace(`$this$stripCodes`, "");
}

public fun MutableComponent.onClick(consumed: AtomicBoolean, action: (ServerPlayer) -> Unit): MutableComponent {
   `$this$onClick`.m_6270_(`$this$onClick`.m_7383_().m_131142_(click(consumed, action)));
   return `$this$onClick`;
}

public fun MutableComponent.onClick(onlyOnce: Boolean = false, action: (ServerPlayer) -> Unit): MutableComponent {
   `$this$onClick`.m_6270_(`$this$onClick`.m_7383_().m_131142_(click(onlyOnce, action)));
   return `$this$onClick`;
}

@JvmSynthetic
fun `onClick$default`(var0: MutableComponent, var1: Boolean, var2: Function1, var3: Int, var4: Any): MutableComponent {
   if ((var3 and 1) != 0) {
      var1 = false;
   }

   return onClick(var0, var1, var2);
}

public fun MutableComponent.onHover(string: String): MutableComponent {
   `$this$onHover`.m_6270_(`$this$onHover`.m_7383_().m_131144_(hover(string)));
   return `$this$onHover`;
}

public fun MutableComponent.onHover(text: Component): MutableComponent {
   `$this$onHover`.m_6270_(`$this$onHover`.m_7383_().m_131144_(hover(text)));
   return `$this$onHover`;
}

public fun MutableComponent.onHover(text: MutableComponent): MutableComponent {
   `$this$onHover`.m_6270_(`$this$onHover`.m_7383_().m_131144_(hover(text as Component)));
   return `$this$onHover`;
}

public fun MutableComponent.underline(): MutableComponent {
   `$this$underline`.m_6270_(`$this$underline`.m_7383_().m_131162_(true));
   return `$this$underline`;
}

public fun MutableComponent.bold(): MutableComponent {
   `$this$bold`.m_6270_(`$this$bold`.m_7383_().m_131136_(true));
   return `$this$bold`;
}

public fun MutableComponent.italicise(): MutableComponent {
   `$this$italicise`.m_6270_(`$this$italicise`.m_7383_().m_131155_(true));
   return `$this$italicise`;
}

public fun MutableComponent.strikethrough(): MutableComponent {
   `$this$strikethrough`.m_6270_(`$this$strikethrough`.m_7383_().m_178522_(true));
   return `$this$strikethrough`;
}

public fun MutableComponent.obfuscate(): MutableComponent {
   `$this$obfuscate`.m_6270_(`$this$obfuscate`.m_7383_().m_178524_(true));
   return `$this$obfuscate`;
}

public fun MutableComponent.suggest(command: String): MutableComponent {
   `$this$suggest`.m_6270_(`$this$suggest`.m_7383_().m_131142_(new ClickEvent(Action.SUGGEST_COMMAND, command)));
   return `$this$suggest`;
}

public fun MutableComponent.add(other: Component): MutableComponent {
   `$this$add`.m_7220_(other);
   return `$this$add`;
}

public fun MutableComponent.add(string: String): MutableComponent {
   add(`$this$add`, text(string) as Component);
   return `$this$add`;
}

public operator fun MutableComponent.plus(component: Component): MutableComponent {
   return add(`$this$plus`, component);
}

public operator fun MutableComponent.plus(string: String): MutableComponent {
   return add(`$this$plus`, string);
}

public fun Iterable<MutableComponent>.sum(separator: MutableComponent = text(", ")): MutableComponent {
   val var10000: MutableComponent;
   if (CollectionsKt.any(`$this$sum`)) {
      val `iterator$iv`: java.util.Iterator = `$this$sum`.iterator();
      if (!`iterator$iv`.hasNext()) {
         throw new UnsupportedOperationException("Empty collection can't be reduced.");
      }

      var `accumulator$iv`: Any = `iterator$iv`.next();

      while (iterator$iv.hasNext()) {
         `accumulator$iv` = plus(plus(`accumulator$iv` as MutableComponent, separator as Component), (`iterator$iv`.next() as MutableComponent) as Component);
      }

      var10000 = `accumulator$iv` as MutableComponent;
   } else {
      var10000 = text("");
   }

   return var10000;
}

@JvmSynthetic
fun `sum$default`(var0: java.lang.Iterable, var1: MutableComponent, var2: Int, var3: Any): MutableComponent {
   if ((var2 and 1) != 0) {
      var1 = text(", ");
   }

   return sum(var0, var1);
}
