package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent.Login
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientPlayerEvent.Logout
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Post
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ClientTickEvent.Pre
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Started
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Starting
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Stopped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerEvent.Stopping
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Death
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.RightClickBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.RightClickEntity

public object PlatformEvents {
   public final val CHANGE_DIMENSION: EventObservable<ChangeDimensionEvent> = new EventObservable()
   public final val CLIENT_ITEM_TOOLTIP: EventObservable<ItemTooltipEvent> = new EventObservable()
   public final val CLIENT_PLAYER_LOGIN: EventObservable<Login> = new EventObservable()
   public final val CLIENT_PLAYER_LOGOUT: EventObservable<Logout> = new EventObservable()
   public final val CLIENT_TICK_POST: EventObservable<Post> = new EventObservable()
   public final val CLIENT_TICK_PRE: EventObservable<Pre> = new EventObservable()
   public final val PLAYER_DEATH: CancelableObservable<Death> = new CancelableObservable()
   public final val RIGHT_CLICK_BLOCK: CancelableObservable<RightClickBlock> = new CancelableObservable()
   public final val RIGHT_CLICK_ENTITY: CancelableObservable<RightClickEntity> = new CancelableObservable()
   public final val SERVER_PLAYER_LOGIN: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Login> = new EventObservable()
   public final val SERVER_PLAYER_LOGOUT: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.Logout> = new EventObservable()
   public final val SERVER_STARTED: EventObservable<Started> = new EventObservable()
   public final val SERVER_STARTING: EventObservable<Starting> = new EventObservable()
   public final val SERVER_STOPPED: EventObservable<Stopped> = new EventObservable()
   public final val SERVER_STOPPING: EventObservable<Stopping> = new EventObservable()
   public final val SERVER_TICK_POST: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Post> = new EventObservable()
   public final val SERVER_TICK_PRE: EventObservable<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerTickEvent.Pre> = new EventObservable()
}
