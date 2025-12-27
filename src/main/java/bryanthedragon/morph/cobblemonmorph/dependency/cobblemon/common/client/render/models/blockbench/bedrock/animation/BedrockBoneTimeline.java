package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

public data BedrockBoneTimeline(position: BedrockBoneValue, rotation: BedrockBoneValue, scale: BedrockBoneValue) {
   public final val position: BedrockBoneValue
   public final val rotation: BedrockBoneValue
   public final val scale: BedrockBoneValue

   init {
      this.position = position;
      this.rotation = rotation;
      this.scale = scale;
   }

   public operator fun component1(): BedrockBoneValue {
      return this.position;
   }

   public operator fun component2(): BedrockBoneValue {
      return this.rotation;
   }

   public operator fun component3(): BedrockBoneValue {
      return this.scale;
   }

   public fun copy(position: BedrockBoneValue = this.position, rotation: BedrockBoneValue = this.rotation, scale: BedrockBoneValue = this.scale): BedrockBoneTimeline {
      return new BedrockBoneTimeline(position, rotation, scale);
   }

   public override fun toString(): String {
      return "BedrockBoneTimeline(position=${this.position}, rotation=${this.rotation}, scale=${this.scale})";
   }

   public override fun hashCode(): Int {
      return (this.position.hashCode() * 31 + this.rotation.hashCode()) * 31 + this.scale.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is BedrockBoneTimeline) {
         return false;
      } else {
         val var2: BedrockBoneTimeline = other as BedrockBoneTimeline;
         if (!(this.position == (other as BedrockBoneTimeline).position)) {
            return false;
         } else if (!(this.rotation == var2.rotation)) {
            return false;
         } else {
            return this.scale == var2.scale;
         }
      }
   }
}
