package com.oracle.truffle.js.nodes.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.BreakIterator;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;

public class CreateSegmentDataObjectNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   CreateDataPropertyNode createSegmentPropertyNode;
   @Node.Child
   CreateDataPropertyNode createIndexPropertyNode;
   @Node.Child
   CreateDataPropertyNode createInputPropertyNode;
   @Node.Child
   CreateDataPropertyNode createIsWordLikePropertyNode;

   protected CreateSegmentDataObjectNode(JSContext context) {
      this.context = context;
      this.createSegmentPropertyNode = CreateDataPropertyNode.create(context, IntlUtil.KEY_SEGMENT);
      this.createIndexPropertyNode = CreateDataPropertyNode.create(context, IntlUtil.KEY_INDEX);
      this.createInputPropertyNode = CreateDataPropertyNode.create(context, IntlUtil.KEY_INPUT);
   }

   public static CreateSegmentDataObjectNode create(JSContext context) {
      return new CreateSegmentDataObjectNode(context);
   }

   public JSObject execute(BreakIterator icuIterator, JSSegmenter.Granularity granularity, TruffleString string, int startIndex, int endIndex) {
      JSObject result = JSOrdinary.create(this.context, this.getRealm());
      this.createSegmentPropertyNode.executeVoid(result, Strings.substring(this.context, string, startIndex, endIndex - startIndex));
      this.createIndexPropertyNode.executeVoid(result, startIndex);
      this.createInputPropertyNode.executeVoid(result, string);
      if (granularity == JSSegmenter.Granularity.WORD) {
         this.createIsWordLikeProperty(result, isWordLike(icuIterator));
      }

      return result;
   }

   private void createIsWordLikeProperty(JSObject target, boolean isWordLike) {
      if (this.createIsWordLikePropertyNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.createIsWordLikePropertyNode = this.insert(CreateDataPropertyNode.create(this.context, IntlUtil.KEY_IS_WORD_LIKE));
      }

      this.createIsWordLikePropertyNode.executeVoid(target, isWordLike);
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean isWordLike(BreakIterator icuIterator) {
      return icuIterator.getRuleStatus() != 0;
   }
}
