package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import java.util.ArrayList;

import net.minecraft.world.entity.schedule.Activity;

public object CobblemonActivities {
   public final val BATTLING_ACTIVITY: Activity = new Activity("pokemon_battling")
   public final val activities: MutableList<Activity> = (new ArrayList()) as java.util.List

   public fun register(activity: Activity): Activity {
      activities.add(activity);
      return activity;
   }
}
