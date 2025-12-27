package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

public data BedrockAnimationGroup(formatVersion: String, animations: Map<String, BedrockAnimation>) {
   public final val animations: Map<String, BedrockAnimation>
   public final val formatVersion: String

   init {
      this.formatVersion = formatVersion;
      this.animations = animations;
   }

   public operator fun component1(): String {
      return this.formatVersion;
   }

   public operator fun component2(): Map<String, BedrockAnimation> {
      return this.animations;
   }

   public fun copy(formatVersion: String = this.formatVersion, animations: Map<String, BedrockAnimation> = this.animations): BedrockAnimationGroup {
      return new BedrockAnimationGroup(formatVersion, animations);
   }

   public override fun toString(): String {
      return "BedrockAnimationGroup(formatVersion=${this.formatVersion}, animations=${this.animations})";
   }

   public override fun hashCode(): Int {
      return this.formatVersion.hashCode() * 31 + this.animations.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BedrockAnimationGroup) {
         return false;
      } else {
         val var2: BedrockAnimationGroup = other as BedrockAnimationGroup;
         if (!(this.formatVersion == (other as BedrockAnimationGroup).formatVersion)) {
            return false;
         } else {
            return this.animations == var2.animations;
         }
      }
   }
}
