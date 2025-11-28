package org.graalvm.shadowed.org.jcodings.transcode;

final class GenericTranscoder extends Transcoder {
   protected GenericTranscoder(
      String source,
      String destination,
      int treeStart,
      String arrayKey,
      int inputUnitLength,
      int maxInput,
      int maxOutput,
      AsciiCompatibility compatibility,
      int stateSize
   ) {
      super(source, destination, treeStart, arrayKey, inputUnitLength, maxInput, maxOutput, compatibility, stateSize);
   }

   protected GenericTranscoder(
      byte[] source,
      byte[] destination,
      int treeStart,
      String arrayKey,
      int inputUnitLength,
      int maxInput,
      int maxOutput,
      AsciiCompatibility compatibility,
      int stateSize
   ) {
      super(source, destination, treeStart, arrayKey, inputUnitLength, maxInput, maxOutput, compatibility, stateSize);
   }
}
