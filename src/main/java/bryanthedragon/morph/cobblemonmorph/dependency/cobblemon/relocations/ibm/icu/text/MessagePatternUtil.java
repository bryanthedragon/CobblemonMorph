package com.cobblemon.mod.relocations.ibm.icu.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MessagePatternUtil {
   private MessagePatternUtil() {
   }

   public static MessagePatternUtil.MessageNode buildMessageNode(String patternString) {
      return buildMessageNode(new MessagePattern(patternString));
   }

   public static MessagePatternUtil.MessageNode buildMessageNode(MessagePattern pattern) {
      int limit = pattern.countParts() - 1;
      if (limit < 0) {
         throw new IllegalArgumentException("The MessagePattern is empty");
      } else if (pattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
         throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
      } else {
         return buildMessageNode(pattern, 0, limit);
      }
   }

   private static MessagePatternUtil.MessageNode buildMessageNode(MessagePattern pattern, int start, int limit) {
      int prevPatternIndex = pattern.getPart(start).getLimit();
      MessagePatternUtil.MessageNode node = new MessagePatternUtil.MessageNode();
      int i = start + 1;

      while (true) {
         MessagePattern.Part part = pattern.getPart(i);
         int patternIndex = part.getIndex();
         if (prevPatternIndex < patternIndex) {
            node.addContentsNode(new MessagePatternUtil.TextNode(pattern.getPatternString().substring(prevPatternIndex, patternIndex)));
         }

         if (i == limit) {
            return node.freeze();
         }

         MessagePattern.Part.Type partType = part.getType();
         if (partType == MessagePattern.Part.Type.ARG_START) {
            int argLimit = pattern.getLimitPartIndex(i);
            node.addContentsNode(buildArgNode(pattern, i, argLimit));
            i = argLimit;
            part = pattern.getPart(argLimit);
         } else if (partType == MessagePattern.Part.Type.REPLACE_NUMBER) {
            node.addContentsNode(MessagePatternUtil.MessageContentsNode.createReplaceNumberNode());
         }

         prevPatternIndex = part.getLimit();
         i++;
      }
   }

   private static MessagePatternUtil.ArgNode buildArgNode(MessagePattern pattern, int start, int limit) {
      MessagePatternUtil.ArgNode node = MessagePatternUtil.ArgNode.createArgNode();
      MessagePattern.Part part = pattern.getPart(start);
      MessagePattern.ArgType argType = node.argType = part.getArgType();
      part = pattern.getPart(++start);
      node.name = pattern.getSubstring(part);
      if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
         node.number = part.getValue();
      }

      start++;
      switch (argType) {
         case SIMPLE:
            node.typeName = pattern.getSubstring(pattern.getPart(start++));
            if (start < limit) {
               node.style = pattern.getSubstring(pattern.getPart(start));
            }
            break;
         case CHOICE:
            node.typeName = "choice";
            node.complexStyle = buildChoiceStyleNode(pattern, start, limit);
            break;
         case PLURAL:
            node.typeName = "plural";
            node.complexStyle = buildPluralStyleNode(pattern, start, limit, argType);
            break;
         case SELECT:
            node.typeName = "select";
            node.complexStyle = buildSelectStyleNode(pattern, start, limit);
            break;
         case SELECTORDINAL:
            node.typeName = "selectordinal";
            node.complexStyle = buildPluralStyleNode(pattern, start, limit, argType);
      }

      return node;
   }

   private static MessagePatternUtil.ComplexArgStyleNode buildChoiceStyleNode(MessagePattern pattern, int start, int limit) {
      MessagePatternUtil.ComplexArgStyleNode node = new MessagePatternUtil.ComplexArgStyleNode(MessagePattern.ArgType.CHOICE);

      while (start < limit) {
         MessagePattern.Part part = pattern.getPart(start);
         double value = pattern.getNumericValue(part);
         int var10 = start + 2;
         int msgLimit = pattern.getLimitPartIndex(var10);
         MessagePatternUtil.VariantNode variant = new MessagePatternUtil.VariantNode();
         variant.selector = pattern.getSubstring(pattern.getPart(start + 1));
         variant.numericValue = value;
         variant.msgNode = buildMessageNode(pattern, var10, msgLimit);
         node.addVariant(variant);
         start = msgLimit + 1;
      }

      return node.freeze();
   }

   private static MessagePatternUtil.ComplexArgStyleNode buildPluralStyleNode(MessagePattern pattern, int start, int limit, MessagePattern.ArgType argType) {
      MessagePatternUtil.ComplexArgStyleNode node = new MessagePatternUtil.ComplexArgStyleNode(argType);
      MessagePattern.Part offset = pattern.getPart(start);
      if (offset.getType().hasNumericValue()) {
         node.explicitOffset = true;
         node.offset = pattern.getNumericValue(offset);
         start++;
      }

      while (start < limit) {
         MessagePattern.Part selector = pattern.getPart(start++);
         double value = -1.23456789E8;
         MessagePattern.Part part = pattern.getPart(start);
         if (part.getType().hasNumericValue()) {
            value = pattern.getNumericValue(part);
            start++;
         }

         int msgLimit = pattern.getLimitPartIndex(start);
         MessagePatternUtil.VariantNode variant = new MessagePatternUtil.VariantNode();
         variant.selector = pattern.getSubstring(selector);
         variant.numericValue = value;
         variant.msgNode = buildMessageNode(pattern, start, msgLimit);
         node.addVariant(variant);
         start = msgLimit + 1;
      }

      return node.freeze();
   }

   private static MessagePatternUtil.ComplexArgStyleNode buildSelectStyleNode(MessagePattern pattern, int start, int limit) {
      MessagePatternUtil.ComplexArgStyleNode node = new MessagePatternUtil.ComplexArgStyleNode(MessagePattern.ArgType.SELECT);

      while (start < limit) {
         MessagePattern.Part selector = pattern.getPart(start++);
         int msgLimit = pattern.getLimitPartIndex(start);
         MessagePatternUtil.VariantNode variant = new MessagePatternUtil.VariantNode();
         variant.selector = pattern.getSubstring(selector);
         variant.msgNode = buildMessageNode(pattern, start, msgLimit);
         node.addVariant(variant);
         start = msgLimit + 1;
      }

      return node.freeze();
   }

   public static class ArgNode extends MessagePatternUtil.MessageContentsNode {
      private MessagePattern.ArgType argType;
      private String name;
      private int number = -1;
      private String typeName;
      private String style;
      private MessagePatternUtil.ComplexArgStyleNode complexStyle;

      public MessagePattern.ArgType getArgType() {
         return this.argType;
      }

      public String getName() {
         return this.name;
      }

      public int getNumber() {
         return this.number;
      }

      public String getTypeName() {
         return this.typeName;
      }

      public String getSimpleStyle() {
         return this.style;
      }

      public MessagePatternUtil.ComplexArgStyleNode getComplexStyle() {
         return this.complexStyle;
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('{').append(this.name);
         if (this.argType != MessagePattern.ArgType.NONE) {
            sb.append(',').append(this.typeName);
            if (this.argType == MessagePattern.ArgType.SIMPLE) {
               if (this.style != null) {
                  sb.append(',').append(this.style);
               }
            } else {
               sb.append(',').append(this.complexStyle.toString());
            }
         }

         return sb.append('}').toString();
      }

      private ArgNode() {
         super(MessagePatternUtil.MessageContentsNode.Type.ARG);
      }

      private static MessagePatternUtil.ArgNode createArgNode() {
         return new MessagePatternUtil.ArgNode();
      }
   }

   public static class ComplexArgStyleNode extends MessagePatternUtil.Node {
      private MessagePattern.ArgType argType;
      private double offset;
      private boolean explicitOffset;
      private volatile List<MessagePatternUtil.VariantNode> list = new ArrayList<>();

      public MessagePattern.ArgType getArgType() {
         return this.argType;
      }

      public boolean hasExplicitOffset() {
         return this.explicitOffset;
      }

      public double getOffset() {
         return this.offset;
      }

      public List<MessagePatternUtil.VariantNode> getVariants() {
         return this.list;
      }

      public MessagePatternUtil.VariantNode getVariantsByType(
         List<MessagePatternUtil.VariantNode> numericVariants, List<MessagePatternUtil.VariantNode> keywordVariants
      ) {
         if (numericVariants != null) {
            numericVariants.clear();
         }

         keywordVariants.clear();
         MessagePatternUtil.VariantNode other = null;

         for (MessagePatternUtil.VariantNode variant : this.list) {
            if (variant.isSelectorNumeric()) {
               numericVariants.add(variant);
            } else if ("other".equals(variant.getSelector())) {
               if (other == null) {
                  other = variant;
               }
            } else {
               keywordVariants.add(variant);
            }
         }

         return other;
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('(').append(this.argType.toString()).append(" style) ");
         if (this.hasExplicitOffset()) {
            sb.append("offset:").append(this.offset).append(' ');
         }

         return sb.append(this.list.toString()).toString();
      }

      private ComplexArgStyleNode(MessagePattern.ArgType argType) {
         this.argType = argType;
      }

      private void addVariant(MessagePatternUtil.VariantNode variant) {
         this.list.add(variant);
      }

      private MessagePatternUtil.ComplexArgStyleNode freeze() {
         this.list = Collections.unmodifiableList(this.list);
         return this;
      }
   }

   public static class MessageContentsNode extends MessagePatternUtil.Node {
      private MessagePatternUtil.MessageContentsNode.Type type;

      public MessagePatternUtil.MessageContentsNode.Type getType() {
         return this.type;
      }

      @Override
      public String toString() {
         return "{REPLACE_NUMBER}";
      }

      private MessageContentsNode(MessagePatternUtil.MessageContentsNode.Type type) {
         this.type = type;
      }

      private static MessagePatternUtil.MessageContentsNode createReplaceNumberNode() {
         return new MessagePatternUtil.MessageContentsNode(MessagePatternUtil.MessageContentsNode.Type.REPLACE_NUMBER);
      }

      public static enum Type {
         TEXT,
         ARG,
         REPLACE_NUMBER;
      }
   }

   public static class MessageNode extends MessagePatternUtil.Node {
      private volatile List<MessagePatternUtil.MessageContentsNode> list = new ArrayList<>();

      public List<MessagePatternUtil.MessageContentsNode> getContents() {
         return this.list;
      }

      @Override
      public String toString() {
         return this.list.toString();
      }

      private MessageNode() {
      }

      private void addContentsNode(MessagePatternUtil.MessageContentsNode node) {
         if (node instanceof MessagePatternUtil.TextNode && !this.list.isEmpty()) {
            MessagePatternUtil.MessageContentsNode lastNode = this.list.get(this.list.size() - 1);
            if (lastNode instanceof MessagePatternUtil.TextNode) {
               MessagePatternUtil.TextNode textNode = (MessagePatternUtil.TextNode)lastNode;
               textNode.text = textNode.text + ((MessagePatternUtil.TextNode)node).text;
               return;
            }
         }

         this.list.add(node);
      }

      private MessagePatternUtil.MessageNode freeze() {
         this.list = Collections.unmodifiableList(this.list);
         return this;
      }
   }

   public static class Node {
      private Node() {
      }
   }

   public static class TextNode extends MessagePatternUtil.MessageContentsNode {
      private String text;

      public String getText() {
         return this.text;
      }

      @Override
      public String toString() {
         return "«" + this.text + "»";
      }

      private TextNode(String text) {
         super(MessagePatternUtil.MessageContentsNode.Type.TEXT);
         this.text = text;
      }
   }

   public static class VariantNode extends MessagePatternUtil.Node {
      private String selector;
      private double numericValue = -1.23456789E8;
      private MessagePatternUtil.MessageNode msgNode;

      public String getSelector() {
         return this.selector;
      }

      public boolean isSelectorNumeric() {
         return this.numericValue != -1.23456789E8;
      }

      public double getSelectorValue() {
         return this.numericValue;
      }

      public MessagePatternUtil.MessageNode getMessage() {
         return this.msgNode;
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         if (this.isSelectorNumeric()) {
            sb.append(this.numericValue).append(" (").append(this.selector).append(") {");
         } else {
            sb.append(this.selector).append(" {");
         }

         return sb.append(this.msgNode.toString()).append('}').toString();
      }

      private VariantNode() {
      }
   }
}
