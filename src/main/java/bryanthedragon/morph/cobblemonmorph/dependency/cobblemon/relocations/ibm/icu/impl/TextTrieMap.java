package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TextTrieMap<V> {
   private TextTrieMap<V>.Node _root = new TextTrieMap.Node();
   boolean _ignoreCase;

   public TextTrieMap(boolean ignoreCase) {
      this._ignoreCase = ignoreCase;
   }

   public TextTrieMap<V> put(CharSequence text, V val) {
      TextTrieMap.CharIterator chitr = new TextTrieMap.CharIterator(text, 0, this._ignoreCase);
      this._root.add(chitr, val);
      return this;
   }

   public Iterator<V> get(String text) {
      return this.get(text, 0);
   }

   public Iterator<V> get(CharSequence text, int start) {
      return this.get(text, start, null);
   }

   public Iterator<V> get(CharSequence text, int start, TextTrieMap.Output output) {
      TextTrieMap.LongestMatchHandler<V> handler = new TextTrieMap.LongestMatchHandler<>();
      this.find(text, start, handler, output);
      if (output != null) {
         output.matchLength = handler.getMatchLength();
      }

      return handler.getMatches();
   }

   public void find(CharSequence text, TextTrieMap.ResultHandler<V> handler) {
      this.find(text, 0, handler, null);
   }

   public void find(CharSequence text, int offset, TextTrieMap.ResultHandler<V> handler) {
      this.find(text, offset, handler, null);
   }

   private void find(CharSequence text, int offset, TextTrieMap.ResultHandler<V> handler, TextTrieMap.Output output) {
      TextTrieMap.CharIterator chitr = new TextTrieMap.CharIterator(text, offset, this._ignoreCase);
      this.find(this._root, chitr, handler, output);
   }

   private synchronized void find(TextTrieMap<V>.Node node, TextTrieMap.CharIterator chitr, TextTrieMap.ResultHandler<V> handler, TextTrieMap.Output output) {
      Iterator<V> values = node.values();
      if (values == null || handler.handlePrefixMatch(chitr.processedLength(), values)) {
         TextTrieMap<V>.Node nextMatch = node.findMatch(chitr, output);
         if (nextMatch != null) {
            this.find(nextMatch, chitr, handler, output);
         }
      }
   }

   public void putLeadCodePoints(UnicodeSet output) {
      this._root.putLeadCodePoints(output);
   }

   private static char[] toCharArray(CharSequence text) {
      char[] array = new char[text.length()];

      for (int i = 0; i < array.length; i++) {
         array[i] = text.charAt(i);
      }

      return array;
   }

   private static char[] subArray(char[] array, int start) {
      if (start == 0) {
         return array;
      } else {
         char[] sub = new char[array.length - start];
         System.arraycopy(array, start, sub, 0, sub.length);
         return sub;
      }
   }

   private static char[] subArray(char[] array, int start, int limit) {
      if (start == 0 && limit == array.length) {
         return array;
      } else {
         char[] sub = new char[limit - start];
         System.arraycopy(array, start, sub, 0, limit - start);
         return sub;
      }
   }

   public static class CharIterator implements Iterator<Character> {
      private boolean _ignoreCase;
      private CharSequence _text;
      private int _nextIdx;
      private int _startIdx;
      private Character _remainingChar;

      CharIterator(CharSequence text, int offset, boolean ignoreCase) {
         this._text = text;
         this._nextIdx = this._startIdx = offset;
         this._ignoreCase = ignoreCase;
      }

      @Override
      public boolean hasNext() {
         return this._nextIdx != this._text.length() || this._remainingChar != null;
      }

      public Character next() {
         if (this._nextIdx == this._text.length() && this._remainingChar == null) {
            return null;
         } else {
            Character next;
            if (this._remainingChar != null) {
               next = this._remainingChar;
               this._remainingChar = null;
            } else if (this._ignoreCase) {
               int cp = UCharacter.foldCase(Character.codePointAt(this._text, this._nextIdx), true);
               this._nextIdx = this._nextIdx + Character.charCount(cp);
               char[] chars = Character.toChars(cp);
               next = chars[0];
               if (chars.length == 2) {
                  this._remainingChar = chars[1];
               }
            } else {
               next = this._text.charAt(this._nextIdx);
               this._nextIdx++;
            }

            return next;
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("remove() not supported");
      }

      public int nextIndex() {
         return this._nextIdx;
      }

      public int processedLength() {
         if (this._remainingChar != null) {
            throw new IllegalStateException("In the middle of surrogate pair");
         } else {
            return this._nextIdx - this._startIdx;
         }
      }
   }

   private static class LongestMatchHandler<V> implements TextTrieMap.ResultHandler<V> {
      private Iterator<V> matches = null;
      private int length = 0;

      private LongestMatchHandler() {
      }

      @Override
      public boolean handlePrefixMatch(int matchLength, Iterator<V> values) {
         if (matchLength > this.length) {
            this.length = matchLength;
            this.matches = values;
         }

         return true;
      }

      public Iterator<V> getMatches() {
         return this.matches;
      }

      public int getMatchLength() {
         return this.length;
      }
   }

   private class Node {
      private char[] _text;
      private List<V> _values;
      private List<TextTrieMap<V>.Node> _children;

      private Node() {
      }

      private Node(char[] text, List<V> values, List<TextTrieMap<V>.Node> children) {
         this._text = text;
         this._values = values;
         this._children = children;
      }

      public int charCount() {
         return this._text == null ? 0 : this._text.length;
      }

      public Iterator<V> values() {
         return this._values == null ? null : this._values.iterator();
      }

      public void add(TextTrieMap.CharIterator chitr, V value) {
         StringBuilder buf = new StringBuilder();

         while (chitr.hasNext()) {
            buf.append(chitr.next());
         }

         this.add(TextTrieMap.toCharArray(buf), 0, value);
      }

      public TextTrieMap<V>.Node findMatch(TextTrieMap.CharIterator chitr, TextTrieMap.Output output) {
         if (this._children == null) {
            return null;
         } else if (!chitr.hasNext()) {
            if (output != null) {
               output.partialMatch = true;
            }

            return null;
         } else {
            TextTrieMap<V>.Node match = null;
            Character ch = chitr.next();

            for (TextTrieMap<V>.Node child : this._children) {
               if (ch < child._text[0]) {
                  break;
               }

               if (ch == child._text[0]) {
                  if (child.matchFollowing(chitr, output)) {
                     match = child;
                  }
                  break;
               }
            }

            return match;
         }
      }

      public void putLeadCodePoints(UnicodeSet output) {
         if (this._children != null) {
            for (TextTrieMap<V>.Node child : this._children) {
               char c0 = child._text[0];
               if (!UCharacter.isHighSurrogate(c0)) {
                  output.add(c0);
               } else if (child.charCount() >= 2) {
                  output.add(Character.codePointAt(child._text, 0));
               } else if (child._children != null) {
                  for (TextTrieMap<V>.Node grandchild : child._children) {
                     char c1 = grandchild._text[0];
                     int cp = Character.toCodePoint(c0, c1);
                     output.add(cp);
                  }
               }
            }
         }
      }

      private void add(char[] text, int offset, V value) {
         if (text.length == offset) {
            this._values = this.addValue(this._values, value);
         } else if (this._children == null) {
            this._children = new LinkedList<>();
            TextTrieMap<V>.Node child = TextTrieMap.this.new Node(TextTrieMap.subArray(text, offset), this.addValue(null, value), null);
            this._children.add(child);
         } else {
            ListIterator<TextTrieMap<V>.Node> litr = this._children.listIterator();

            while (litr.hasNext()) {
               TextTrieMap<V>.Node next = litr.next();
               if (text[offset] < next._text[0]) {
                  litr.previous();
                  break;
               }

               if (text[offset] == next._text[0]) {
                  int matchLen = next.lenMatches(text, offset);
                  if (matchLen == next._text.length) {
                     next.add(text, offset + matchLen, value);
                  } else {
                     next.split(matchLen);
                     next.add(text, offset + matchLen, value);
                  }

                  return;
               }
            }

            litr.add(TextTrieMap.this.new Node(TextTrieMap.subArray(text, offset), this.addValue(null, value), null));
         }
      }

      private boolean matchFollowing(TextTrieMap.CharIterator chitr, TextTrieMap.Output output) {
         boolean matched = true;

         for (int idx = 1; idx < this._text.length; idx++) {
            if (!chitr.hasNext()) {
               if (output != null) {
                  output.partialMatch = true;
               }

               matched = false;
               break;
            }

            Character ch = chitr.next();
            if (ch != this._text[idx]) {
               matched = false;
               break;
            }
         }

         return matched;
      }

      private int lenMatches(char[] text, int offset) {
         int textLen = text.length - offset;
         int limit = this._text.length < textLen ? this._text.length : textLen;
         int len = 0;

         while (len < limit && this._text[len] == text[offset + len]) {
            len++;
         }

         return len;
      }

      private void split(int offset) {
         char[] childText = TextTrieMap.subArray(this._text, offset);
         this._text = TextTrieMap.subArray(this._text, 0, offset);
         TextTrieMap<V>.Node child = TextTrieMap.this.new Node(childText, this._values, this._children);
         this._values = null;
         this._children = new LinkedList<>();
         this._children.add(child);
      }

      private List<V> addValue(List<V> list, V value) {
         if (list == null) {
            list = new LinkedList<>();
         }

         list.add(value);
         return list;
      }
   }

   public static class Output {
      public int matchLength;
      public boolean partialMatch;
   }

   public interface ResultHandler<V> {
      boolean handlePrefixMatch(int var1, Iterator<V> var2);
   }
}
