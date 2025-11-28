package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Assert;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.MutableCodePointTrie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class RBBISetBuilder {
   RBBIRuleBuilder fRB;
   RBBISetBuilder.RangeDescriptor fRangeList;
   MutableCodePointTrie fTrie;
   CodePointTrie fFrozenTrie;
   int fGroupCount;
   int fDictCategoriesStart;
   boolean fSawBOF;
   private static final int MAX_CHAR_CATEGORIES_FOR_8BITS_TRIE = 255;

   RBBISetBuilder(RBBIRuleBuilder rb) {
      this.fRB = rb;
   }

   void buildRanges() {
      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
         this.printSets();
      }

      this.fRangeList = new RBBISetBuilder.RangeDescriptor();
      this.fRangeList.fStartChar = 0;
      this.fRangeList.fEndChar = 1114111;

      for (RBBINode usetNode : this.fRB.fUSetNodes) {
         UnicodeSet inputSet = usetNode.fInputSet;
         int inputSetRangeCount = inputSet.getRangeCount();
         int inputSetRangeIndex = 0;
         RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList;

         while (inputSetRangeIndex < inputSetRangeCount) {
            int inputSetRangeBegin = inputSet.getRangeStart(inputSetRangeIndex);
            int inputSetRangeEnd = inputSet.getRangeEnd(inputSetRangeIndex);

            while (rlRange.fEndChar < inputSetRangeBegin) {
               rlRange = rlRange.fNext;
            }

            if (rlRange.fStartChar < inputSetRangeBegin) {
               rlRange.split(inputSetRangeBegin);
            } else {
               if (rlRange.fEndChar > inputSetRangeEnd) {
                  rlRange.split(inputSetRangeEnd + 1);
               }

               if (rlRange.fIncludesSets.indexOf(usetNode) == -1) {
                  rlRange.fIncludesSets.add(usetNode);
               }

               if (inputSetRangeEnd == rlRange.fEndChar) {
                  inputSetRangeIndex++;
               }

               rlRange = rlRange.fNext;
            }
         }
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
         this.printRanges();
      }

      int dictGroupCount = 0;

      for (RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         for (RBBISetBuilder.RangeDescriptor rlSearchRange = this.fRangeList; rlSearchRange != rlRange; rlSearchRange = rlSearchRange.fNext) {
            if (rlRange.fIncludesSets.equals(rlSearchRange.fIncludesSets)) {
               rlRange.fNum = rlSearchRange.fNum;
               rlRange.fIncludesDict = rlSearchRange.fIncludesDict;
               break;
            }
         }

         if (rlRange.fNum == 0) {
            rlRange.fFirstInGroup = true;
            if (rlRange.isDictionaryRange()) {
               rlRange.fNum = ++dictGroupCount;
               rlRange.fIncludesDict = true;
            } else {
               this.fGroupCount++;
               rlRange.fNum = this.fGroupCount + 2;
               this.addValToSets(rlRange.fIncludesSets, this.fGroupCount + 2);
            }
         }
      }

      this.fDictCategoriesStart = this.fGroupCount + 3;

      for (RBBISetBuilder.RangeDescriptor var10 = this.fRangeList; var10 != null; var10 = var10.fNext) {
         if (var10.fIncludesDict) {
            var10.fNum = var10.fNum + (this.fDictCategoriesStart - 1);
            if (var10.fFirstInGroup) {
               this.addValToSets(var10.fIncludesSets, var10.fNum);
            }
         }
      }

      this.fGroupCount += dictGroupCount;
      String eofString = "eof";
      String bofString = "bof";

      for (RBBINode usetNode : this.fRB.fUSetNodes) {
         UnicodeSet inputSet = usetNode.fInputSet;
         if (inputSet.contains(eofString)) {
            this.addValToSet(usetNode, 1);
         }

         if (inputSet.contains(bofString)) {
            this.addValToSet(usetNode, 2);
            this.fSawBOF = true;
         }
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
         this.printRangeGroups();
      }

      if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("esets") >= 0) {
         this.printSets();
      }
   }

   void buildTrie() {
      this.fTrie = new MutableCodePointTrie(0, 0);

      for (RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         this.fTrie.setRange(rlRange.fStartChar, rlRange.fEndChar, rlRange.fNum);
      }
   }

   void mergeCategories(RBBIRuleBuilder.IntPair categories) {
      assert categories.first >= 1;

      assert categories.second > categories.first;

      assert categories.first < this.fDictCategoriesStart && categories.second < this.fDictCategoriesStart
         || categories.first >= this.fDictCategoriesStart && categories.second >= this.fDictCategoriesStart;

      for (RBBISetBuilder.RangeDescriptor rd = this.fRangeList; rd != null; rd = rd.fNext) {
         int rangeNum = rd.fNum;
         if (rangeNum == categories.second) {
            rd.fNum = categories.first;
         } else if (rangeNum > categories.second) {
            rd.fNum--;
         }
      }

      this.fGroupCount--;
      if (categories.second <= this.fDictCategoriesStart) {
         this.fDictCategoriesStart--;
      }
   }

   void freezeTrieIfNotYet() {
      if (this.fFrozenTrie == null) {
         boolean use8Bits = this.getNumCharCategories() <= 255;
         this.fFrozenTrie = this.fTrie.buildImmutable(CodePointTrie.Type.FAST, use8Bits ? CodePointTrie.ValueWidth.BITS_8 : CodePointTrie.ValueWidth.BITS_16);
         this.fTrie = null;
      }
   }

   int getTrieSize() {
      this.freezeTrieIfNotYet();
      return this.fFrozenTrie.toBinary(new ByteArrayOutputStream());
   }

   void serializeTrie(OutputStream os) throws IOException {
      this.freezeTrieIfNotYet();
      this.fFrozenTrie.toBinary(os);
   }

   void addValToSets(List<RBBINode> sets, int val) {
      for (RBBINode usetNode : sets) {
         this.addValToSet(usetNode, val);
      }
   }

   void addValToSet(RBBINode usetNode, int val) {
      RBBINode leafNode = new RBBINode(3);
      leafNode.fVal = val;
      if (usetNode.fLeftChild == null) {
         usetNode.fLeftChild = leafNode;
         leafNode.fParent = usetNode;
      } else {
         RBBINode orNode = new RBBINode(9);
         orNode.fLeftChild = usetNode.fLeftChild;
         orNode.fRightChild = leafNode;
         orNode.fLeftChild.fParent = orNode;
         orNode.fRightChild.fParent = orNode;
         usetNode.fLeftChild = orNode;
         orNode.fParent = usetNode;
      }
   }

   int getNumCharCategories() {
      return this.fGroupCount + 3;
   }

   int getDictCategoriesStart() {
      return this.fDictCategoriesStart;
   }

   boolean sawBOF() {
      return this.fSawBOF;
   }

   int getFirstChar(int category) {
      int retVal = -1;

      for (RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         if (rlRange.fNum == category) {
            retVal = rlRange.fStartChar;
            break;
         }
      }

      return retVal;
   }

   void printRanges() {
      System.out.print("\n\n Nonoverlapping Ranges ...\n");

      for (RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         System.out.printf("%04x-%04x ", rlRange.fStartChar, rlRange.fEndChar);

         for (int i = 0; i < rlRange.fIncludesSets.size(); i++) {
            RBBINode usetNode = rlRange.fIncludesSets.get(i);
            String setName = "anon";
            RBBINode setRef = usetNode.fParent;
            if (setRef != null) {
               RBBINode varRef = setRef.fParent;
               if (varRef != null && varRef.fType == 2) {
                  setName = varRef.fText;
               }
            }

            System.out.print(setName);
            System.out.print("  ");
         }

         System.out.println("");
      }
   }

   void printRangeGroups() {
      System.out.print("\nRanges grouped by Unicode Set Membership...\n");

      for (RBBISetBuilder.RangeDescriptor rlRange = this.fRangeList; rlRange != null; rlRange = rlRange.fNext) {
         if (rlRange.fFirstInGroup) {
            int groupNum = rlRange.fNum;
            if (groupNum < 10) {
               System.out.print(" ");
            }

            System.out.print(groupNum + " ");
            if (groupNum >= this.fDictCategoriesStart) {
               System.out.print(" <DICT> ");
            }

            for (int i = 0; i < rlRange.fIncludesSets.size(); i++) {
               RBBINode usetNode = rlRange.fIncludesSets.get(i);
               String setName = "anon";
               RBBINode setRef = usetNode.fParent;
               if (setRef != null) {
                  RBBINode varRef = setRef.fParent;
                  if (varRef != null && varRef.fType == 2) {
                     setName = varRef.fText;
                  }
               }

               System.out.print(setName);
               System.out.print(" ");
            }

            int var8 = 0;

            for (RBBISetBuilder.RangeDescriptor tRange = rlRange; tRange != null; tRange = tRange.fNext) {
               if (tRange.fNum == rlRange.fNum) {
                  if (var8++ % 5 == 0) {
                     System.out.print("\n    ");
                  }

                  RBBINode.printHex(tRange.fStartChar, -1);
                  System.out.print("-");
                  RBBINode.printHex(tRange.fEndChar, 0);
               }
            }

            System.out.print("\n");
         }
      }

      System.out.print("\n");
   }

   void printSets() {
      System.out.print("\n\nUnicode Sets List\n------------------\n");

      for (int i = 0; i < this.fRB.fUSetNodes.size(); i++) {
         RBBINode usetNode = this.fRB.fUSetNodes.get(i);
         RBBINode.printInt(2, i);
         String setName = "anonymous";
         RBBINode setRef = usetNode.fParent;
         if (setRef != null) {
            RBBINode varRef = setRef.fParent;
            if (varRef != null && varRef.fType == 2) {
               setName = varRef.fText;
            }
         }

         System.out.print("  " + setName);
         System.out.print("   ");
         System.out.print(usetNode.fText);
         System.out.print("\n");
         if (usetNode.fLeftChild != null) {
            usetNode.fLeftChild.printTree(true);
         }
      }

      System.out.print("\n");
   }

   static class RangeDescriptor {
      int fStartChar = 0;
      int fEndChar = 0;
      int fNum = 0;
      boolean fIncludesDict = false;
      boolean fFirstInGroup = false;
      List<RBBINode> fIncludesSets;
      RBBISetBuilder.RangeDescriptor fNext;

      RangeDescriptor() {
         this.fIncludesSets = new ArrayList<>();
      }

      RangeDescriptor(RBBISetBuilder.RangeDescriptor other) {
         this.fStartChar = other.fStartChar;
         this.fEndChar = other.fEndChar;
         this.fNum = other.fNum;
         this.fIncludesDict = other.fIncludesDict;
         this.fFirstInGroup = other.fFirstInGroup;
         this.fIncludesSets = new ArrayList<>(other.fIncludesSets);
      }

      void split(int where) {
         Assert.assrt(where > this.fStartChar && where <= this.fEndChar);
         RBBISetBuilder.RangeDescriptor nr = new RBBISetBuilder.RangeDescriptor(this);
         nr.fStartChar = where;
         this.fEndChar = where - 1;
         nr.fNext = this.fNext;
         this.fNext = nr;
      }

      boolean isDictionaryRange() {
         for (int i = 0; i < this.fIncludesSets.size(); i++) {
            RBBINode usetNode = this.fIncludesSets.get(i);
            String setName = "";
            RBBINode setRef = usetNode.fParent;
            if (setRef != null) {
               RBBINode varRef = setRef.fParent;
               if (varRef != null && varRef.fType == 2) {
                  setName = varRef.fText;
               }
            }

            if (setName.equals("dictionary")) {
               return true;
            }
         }

         return false;
      }
   }
}
