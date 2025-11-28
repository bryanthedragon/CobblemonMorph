package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Assert;
import com.cobblemon.mod.relocations.ibm.icu.impl.RBBIDataWrapper;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class RBBITableBuilder {
   private RBBIRuleBuilder fRB;
   private int fRootIx;
   private List<RBBITableBuilder.RBBIStateDescriptor> fDStates;
   private List<short[]> fSafeTable;
   private static final int MAX_STATE_FOR_8BITS_TABLE = 255;
   int[] fLookAheadRuleMap;
   int fLASlotsInUse = 1;

   RBBITableBuilder(RBBIRuleBuilder rb, int rootNodeIx) {
      this.fRootIx = rootNodeIx;
      this.fRB = rb;
      this.fDStates = new ArrayList<>();
   }

   void buildForwardTable() {
      if (this.fRB.fTreeRoots[this.fRootIx] != null) {
         this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
         if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
         }

         if (this.fRB.fSetBuilder.sawBOF()) {
            RBBINode bofTop = new RBBINode(8);
            RBBINode bofLeaf = new RBBINode(3);
            bofTop.fLeftChild = bofLeaf;
            bofTop.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            bofLeaf.fParent = bofTop;
            bofLeaf.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = bofTop;
         }

         RBBINode cn = new RBBINode(8);
         cn.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
         this.fRB.fTreeRoots[this.fRootIx].fParent = cn;
         RBBINode endMarkerNode = cn.fRightChild = new RBBINode(6);
         cn.fRightChild.fParent = cn;
         this.fRB.fTreeRoots[this.fRootIx] = cn;
         this.fRB.fTreeRoots[this.fRootIx].flattenSets();
         if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
         }

         this.calcNullable(this.fRB.fTreeRoots[this.fRootIx]);
         this.calcFirstPos(this.fRB.fTreeRoots[this.fRootIx]);
         this.calcLastPos(this.fRB.fTreeRoots[this.fRootIx]);
         this.calcFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
         if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("pos") >= 0) {
            System.out.print("\n");
            this.printPosSets(this.fRB.fTreeRoots[this.fRootIx]);
         }

         if (this.fRB.fChainRules) {
            this.calcChainedFollowPos(this.fRB.fTreeRoots[this.fRootIx], endMarkerNode);
         }

         if (this.fRB.fSetBuilder.sawBOF()) {
            this.bofFixup();
         }

         this.buildStateTable();
         this.mapLookAheadRules();
         this.flagAcceptingStates();
         this.flagLookAheadStates();
         this.flagTaggedStates();
         this.mergeRuleStatusVals();
      }
   }

   void calcNullable(RBBINode n) {
      if (n != null) {
         if (n.fType == 0 || n.fType == 6) {
            n.fNullable = false;
         } else if (n.fType != 4 && n.fType != 5) {
            this.calcNullable(n.fLeftChild);
            this.calcNullable(n.fRightChild);
            if (n.fType == 9) {
               n.fNullable = n.fLeftChild.fNullable || n.fRightChild.fNullable;
            } else if (n.fType == 8) {
               n.fNullable = n.fLeftChild.fNullable && n.fRightChild.fNullable;
            } else if (n.fType != 10 && n.fType != 12) {
               n.fNullable = false;
            } else {
               n.fNullable = true;
            }
         } else {
            n.fNullable = true;
         }
      }
   }

   void calcFirstPos(RBBINode n) {
      if (n != null) {
         if (n.fType != 3 && n.fType != 6 && n.fType != 4 && n.fType != 5) {
            this.calcFirstPos(n.fLeftChild);
            this.calcFirstPos(n.fRightChild);
            if (n.fType == 9) {
               n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
               n.fFirstPosSet.addAll(n.fRightChild.fFirstPosSet);
            } else if (n.fType == 8) {
               n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
               if (n.fLeftChild.fNullable) {
                  n.fFirstPosSet.addAll(n.fRightChild.fFirstPosSet);
               }
            } else if (n.fType == 10 || n.fType == 12 || n.fType == 11) {
               n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
            }
         } else {
            n.fFirstPosSet.add(n);
         }
      }
   }

   void calcLastPos(RBBINode n) {
      if (n != null) {
         if (n.fType != 3 && n.fType != 6 && n.fType != 4 && n.fType != 5) {
            this.calcLastPos(n.fLeftChild);
            this.calcLastPos(n.fRightChild);
            if (n.fType == 9) {
               n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
               n.fLastPosSet.addAll(n.fRightChild.fLastPosSet);
            } else if (n.fType == 8) {
               n.fLastPosSet.addAll(n.fRightChild.fLastPosSet);
               if (n.fRightChild.fNullable) {
                  n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
               }
            } else if (n.fType == 10 || n.fType == 12 || n.fType == 11) {
               n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
            }
         } else {
            n.fLastPosSet.add(n);
         }
      }
   }

   void calcFollowPos(RBBINode n) {
      if (n != null && n.fType != 3 && n.fType != 6) {
         this.calcFollowPos(n.fLeftChild);
         this.calcFollowPos(n.fRightChild);
         if (n.fType == 8) {
            for (RBBINode i : n.fLeftChild.fLastPosSet) {
               i.fFollowPos.addAll(n.fRightChild.fFirstPosSet);
            }
         }

         if (n.fType == 10 || n.fType == 11) {
            for (RBBINode i : n.fLastPosSet) {
               i.fFollowPos.addAll(n.fFirstPosSet);
            }
         }
      }
   }

   void addRuleRootNodes(List<RBBINode> dest, RBBINode node) {
      if (node != null) {
         if (node.fRuleRoot) {
            dest.add(node);
         } else {
            this.addRuleRootNodes(dest, node.fLeftChild);
            this.addRuleRootNodes(dest, node.fRightChild);
         }
      }
   }

   void calcChainedFollowPos(RBBINode tree, RBBINode endMarkNode) {
      List<RBBINode> leafNodes = new ArrayList<>();
      tree.findNodes(leafNodes, 3);
      List<RBBINode> ruleRootNodes = new ArrayList<>();
      this.addRuleRootNodes(ruleRootNodes, tree);
      Set<RBBINode> matchStartNodes = new HashSet<>();

      for (RBBINode node : ruleRootNodes) {
         if (node.fChainIn) {
            matchStartNodes.addAll(node.fFirstPosSet);
         }
      }

      Iterator var10 = leafNodes.iterator();

      while (true) {
         RBBINode endNode;
         while (true) {
            if (!var10.hasNext()) {
               return;
            }

            endNode = (RBBINode)var10.next();
            if (endNode.fFollowPos.contains(endMarkNode)) {
               if (!this.fRB.fLBCMNoChain) {
                  break;
               }

               int c = this.fRB.fSetBuilder.getFirstChar(endNode.fVal);
               if (c == -1) {
                  break;
               }

               int cLBProp = UCharacter.getIntPropertyValue(c, 4104);
               if (cLBProp != 9) {
                  break;
               }
            }
         }

         for (RBBINode startNode : matchStartNodes) {
            if (startNode.fType == 3 && endNode.fVal == startNode.fVal) {
               endNode.fFollowPos.addAll(startNode.fFollowPos);
            }
         }
      }
   }

   void bofFixup() {
      RBBINode bofNode = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
      Assert.assrt(bofNode.fType == 3);
      Assert.assrt(bofNode.fVal == 2);

      for (RBBINode startNode : this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet) {
         if (startNode.fType == 3 && startNode.fVal == bofNode.fVal) {
            bofNode.fFollowPos.addAll(startNode.fFollowPos);
         }
      }
   }

   void buildStateTable() {
      int lastInputSymbol = this.fRB.fSetBuilder.getNumCharCategories() - 1;
      RBBITableBuilder.RBBIStateDescriptor failState = new RBBITableBuilder.RBBIStateDescriptor(lastInputSymbol);
      this.fDStates.add(failState);
      RBBITableBuilder.RBBIStateDescriptor initialState = new RBBITableBuilder.RBBIStateDescriptor(lastInputSymbol);
      initialState.fPositions.addAll(this.fRB.fTreeRoots[this.fRootIx].fFirstPosSet);
      this.fDStates.add(initialState);

      while (true) {
         RBBITableBuilder.RBBIStateDescriptor T = null;

         for (int tx = 1; tx < this.fDStates.size(); tx++) {
            RBBITableBuilder.RBBIStateDescriptor temp = this.fDStates.get(tx);
            if (!temp.fMarked) {
               T = temp;
               break;
            }
         }

         if (T == null) {
            return;
         }

         T.fMarked = true;

         for (int a = 1; a <= lastInputSymbol; a++) {
            Set<RBBINode> U = null;

            for (RBBINode p : T.fPositions) {
               if (p.fType == 3 && p.fVal == a) {
                  if (U == null) {
                     U = new HashSet<>();
                  }

                  U.addAll(p.fFollowPos);
               }
            }

            int ux = 0;
            boolean UinDstates = false;
            if (U != null) {
               Assert.assrt(U.size() > 0);

               for (int ix = 0; ix < this.fDStates.size(); ix++) {
                  RBBITableBuilder.RBBIStateDescriptor temp2 = this.fDStates.get(ix);
                  if (U.equals(temp2.fPositions)) {
                     U = temp2.fPositions;
                     ux = ix;
                     UinDstates = true;
                     break;
                  }
               }

               if (!UinDstates) {
                  RBBITableBuilder.RBBIStateDescriptor newState = new RBBITableBuilder.RBBIStateDescriptor(lastInputSymbol);
                  newState.fPositions = U;
                  this.fDStates.add(newState);
                  ux = this.fDStates.size() - 1;
               }

               T.fDtran[a] = ux;
            }
         }
      }
   }

   void mapLookAheadRules() {
      this.fLookAheadRuleMap = new int[this.fRB.fScanner.numRules() + 1];

      for (RBBITableBuilder.RBBIStateDescriptor sd : this.fDStates) {
         int laSlotForState = 0;
         boolean sawLookAheadNode = false;

         for (RBBINode node : sd.fPositions) {
            if (node.fType == 4) {
               sawLookAheadNode = true;
               int ruleNum = node.fVal;

               assert ruleNum < this.fLookAheadRuleMap.length;

               assert ruleNum > 0;

               int laSlot = this.fLookAheadRuleMap[ruleNum];
               if (laSlot != 0) {
                  if (laSlotForState == 0) {
                     laSlotForState = laSlot;
                  } else {
                     assert laSlot == laSlotForState;
                  }
               }
            }
         }

         if (sawLookAheadNode) {
            if (laSlotForState == 0) {
               laSlotForState = ++this.fLASlotsInUse;
            }

            for (RBBINode nodex : sd.fPositions) {
               if (nodex.fType == 4) {
                  int ruleNumx = nodex.fVal;
                  int existingVal = this.fLookAheadRuleMap[ruleNumx];

                  assert existingVal == 0 || existingVal == laSlotForState;

                  this.fLookAheadRuleMap[ruleNumx] = laSlotForState;
               }
            }
         }
      }
   }

   void flagAcceptingStates() {
      List<RBBINode> endMarkerNodes = new ArrayList<>();
      this.fRB.fTreeRoots[this.fRootIx].findNodes(endMarkerNodes, 6);

      for (int i = 0; i < endMarkerNodes.size(); i++) {
         RBBINode endMarker = endMarkerNodes.get(i);

         for (int n = 0; n < this.fDStates.size(); n++) {
            RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(n);
            if (sd.fPositions.contains(endMarker)) {
               if (sd.fAccepting == 0) {
                  sd.fAccepting = this.fLookAheadRuleMap[endMarker.fVal];
                  if (sd.fAccepting == 0) {
                     sd.fAccepting = 1;
                  }
               }

               if (sd.fAccepting == 1 && endMarker.fVal != 0) {
                  sd.fAccepting = this.fLookAheadRuleMap[endMarker.fVal];
               }
            }
         }
      }
   }

   void flagLookAheadStates() {
      List<RBBINode> lookAheadNodes = new ArrayList<>();
      this.fRB.fTreeRoots[this.fRootIx].findNodes(lookAheadNodes, 4);

      for (int i = 0; i < lookAheadNodes.size(); i++) {
         RBBINode lookAheadNode = lookAheadNodes.get(i);

         for (int n = 0; n < this.fDStates.size(); n++) {
            RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(n);
            if (sd.fPositions.contains(lookAheadNode)) {
               int lookaheadSlot = this.fLookAheadRuleMap[lookAheadNode.fVal];

               assert sd.fLookAhead == 0 || sd.fLookAhead == lookaheadSlot;

               sd.fLookAhead = lookaheadSlot;
            }
         }
      }
   }

   void flagTaggedStates() {
      List<RBBINode> tagNodes = new ArrayList<>();
      this.fRB.fTreeRoots[this.fRootIx].findNodes(tagNodes, 5);

      for (int i = 0; i < tagNodes.size(); i++) {
         RBBINode tagNode = tagNodes.get(i);

         for (int n = 0; n < this.fDStates.size(); n++) {
            RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(n);
            if (sd.fPositions.contains(tagNode)) {
               sd.fTagVals.add(tagNode.fVal);
            }
         }
      }
   }

   void mergeRuleStatusVals() {
      if (this.fRB.fRuleStatusVals.size() == 0) {
         this.fRB.fRuleStatusVals.add(1);
         this.fRB.fRuleStatusVals.add(0);
         SortedSet<Integer> s0 = new TreeSet<>();
         this.fRB.fStatusSets.put(s0, 0);
         SortedSet<Integer> s1 = new TreeSet<>();
         s1.add(0);
         this.fRB.fStatusSets.put(s1, 0);
      }

      for (int n = 0; n < this.fDStates.size(); n++) {
         RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(n);
         Set<Integer> statusVals = sd.fTagVals;
         Integer arrayIndexI = this.fRB.fStatusSets.get(statusVals);
         if (arrayIndexI == null) {
            arrayIndexI = this.fRB.fRuleStatusVals.size();
            this.fRB.fStatusSets.put(statusVals, arrayIndexI);
            this.fRB.fRuleStatusVals.add(statusVals.size());
            this.fRB.fRuleStatusVals.addAll(statusVals);
         }

         sd.fTagsIdx = arrayIndexI;
      }
   }

   void printPosSets(RBBINode n) {
      if (n != null) {
         RBBINode.printNode(n);
         System.out.print("         Nullable:  " + n.fNullable);
         System.out.print("         firstpos:  ");
         this.printSet(n.fFirstPosSet);
         System.out.print("         lastpos:   ");
         this.printSet(n.fLastPosSet);
         System.out.print("         followpos: ");
         this.printSet(n.fFollowPos);
         this.printPosSets(n.fLeftChild);
         this.printPosSets(n.fRightChild);
      }
   }

   boolean findDuplCharClassFrom(RBBIRuleBuilder.IntPair categories) {
      int numStates = this.fDStates.size();
      int numCols = this.fRB.fSetBuilder.getNumCharCategories();
      int table_base = 0;
      int table_dupl = 0;

      while (categories.first < numCols - 1) {
         int limitSecond = categories.first < this.fRB.fSetBuilder.getDictCategoriesStart() ? this.fRB.fSetBuilder.getDictCategoriesStart() : numCols;

         for (categories.second = categories.first + 1; categories.second < limitSecond; categories.second++) {
            for (int state = 0; state < numStates; state++) {
               RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(state);
               table_base = sd.fDtran[categories.first];
               table_dupl = sd.fDtran[categories.second];
               if (table_base != table_dupl) {
                  break;
               }
            }

            if (table_base == table_dupl) {
               return true;
            }
         }

         categories.first++;
      }

      return false;
   }

   void removeColumn(int column) {
      int numStates = this.fDStates.size();

      for (int state = 0; state < numStates; state++) {
         RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(state);

         assert column < sd.fDtran.length;

         int[] newArray = Arrays.copyOf(sd.fDtran, sd.fDtran.length - 1);
         System.arraycopy(sd.fDtran, column + 1, newArray, column, newArray.length - column);
         sd.fDtran = newArray;
      }
   }

   boolean findDuplicateState(RBBIRuleBuilder.IntPair states) {
      int numStates = this.fDStates.size();
      int numCols = this.fRB.fSetBuilder.getNumCharCategories();

      while (states.first < numStates - 1) {
         RBBITableBuilder.RBBIStateDescriptor firstSD = this.fDStates.get(states.first);

         for (states.second = states.first + 1; states.second < numStates; states.second++) {
            RBBITableBuilder.RBBIStateDescriptor duplSD = this.fDStates.get(states.second);
            if (firstSD.fAccepting == duplSD.fAccepting && firstSD.fLookAhead == duplSD.fLookAhead && firstSD.fTagsIdx == duplSD.fTagsIdx) {
               boolean rowsMatch = true;

               for (int col = 0; col < numCols; col++) {
                  int firstVal = firstSD.fDtran[col];
                  int duplVal = duplSD.fDtran[col];
                  if (firstVal != duplVal && (firstVal != states.first && firstVal != states.second || duplVal != states.first && duplVal != states.second)) {
                     rowsMatch = false;
                     break;
                  }
               }

               if (rowsMatch) {
                  return true;
               }
            }
         }

         states.first++;
      }

      return false;
   }

   boolean findDuplicateSafeState(RBBIRuleBuilder.IntPair states) {
      int numStates = this.fSafeTable.size();

      while (states.first < numStates - 1) {
         short[] firstRow = this.fSafeTable.get(states.first);

         for (states.second = states.first + 1; states.second < numStates; states.second++) {
            short[] duplRow = this.fSafeTable.get(states.second);
            boolean rowsMatch = true;
            int numCols = firstRow.length;

            for (int col = 0; col < numCols; col++) {
               int firstVal = firstRow[col];
               int duplVal = duplRow[col];
               if (firstVal != duplVal && (firstVal != states.first && firstVal != states.second || duplVal != states.first && duplVal != states.second)) {
                  rowsMatch = false;
                  break;
               }
            }

            if (rowsMatch) {
               return true;
            }
         }

         states.first++;
      }

      return false;
   }

   void removeState(RBBIRuleBuilder.IntPair duplStates) {
      int keepState = duplStates.first;
      int duplState = duplStates.second;

      assert keepState < duplState;

      assert duplState < this.fDStates.size();

      this.fDStates.remove(duplState);
      int numStates = this.fDStates.size();
      int numCols = this.fRB.fSetBuilder.getNumCharCategories();

      for (int state = 0; state < numStates; state++) {
         RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(state);

         for (int col = 0; col < numCols; col++) {
            int existingVal = sd.fDtran[col];
            int newVal = existingVal;
            if (existingVal == duplState) {
               newVal = keepState;
            } else if (existingVal > duplState) {
               newVal = existingVal - 1;
            }

            sd.fDtran[col] = newVal;
         }
      }
   }

   void removeSafeState(RBBIRuleBuilder.IntPair duplStates) {
      int keepState = duplStates.first;
      int duplState = duplStates.second;

      assert keepState < duplState;

      assert duplState < this.fSafeTable.size();

      this.fSafeTable.remove(duplState);
      int numStates = this.fSafeTable.size();

      for (int state = 0; state < numStates; state++) {
         short[] row = this.fSafeTable.get(state);

         for (int col = 0; col < row.length; col++) {
            int existingVal = row[col];
            int newVal = existingVal;
            if (existingVal == duplState) {
               newVal = keepState;
            } else if (existingVal > duplState) {
               newVal = existingVal - 1;
            }

            row[col] = (short)newVal;
         }
      }
   }

   int removeDuplicateStates() {
      RBBIRuleBuilder.IntPair dupls = new RBBIRuleBuilder.IntPair(3, 0);

      int numStatesRemoved;
      for (numStatesRemoved = 0; this.findDuplicateState(dupls); numStatesRemoved++) {
         this.removeState(dupls);
      }

      return numStatesRemoved;
   }

   int getTableSize() {
      if (this.fRB.fTreeRoots[this.fRootIx] == null) {
         return 0;
      } else {
         int size = RBBIDataWrapper.RBBIStateTable.fHeaderSize;
         int numRows = this.fDStates.size();
         int numCols = this.fRB.fSetBuilder.getNumCharCategories();
         boolean use8Bits = numRows <= 255;
         int rowSize = (use8Bits ? 1 : 2) * (3 + numCols);
         size += numRows * rowSize;
         return size + 7 & -8;
      }
   }

   RBBIDataWrapper.RBBIStateTable exportTable() {
      RBBIDataWrapper.RBBIStateTable table = new RBBIDataWrapper.RBBIStateTable();
      if (this.fRB.fTreeRoots[this.fRootIx] == null) {
         return table;
      } else {
         Assert.assrt(this.fRB.fSetBuilder.getNumCharCategories() < 32767 && this.fDStates.size() < 32767);
         table.fNumStates = this.fDStates.size();
         table.fDictCategoriesStart = this.fRB.fSetBuilder.getDictCategoriesStart();
         table.fLookAheadResultsSize = this.fLASlotsInUse == 1 ? 0 : this.fLASlotsInUse + 1;
         boolean use8Bits = table.fNumStates <= 255;
         int rowLen = 3 + this.fRB.fSetBuilder.getNumCharCategories();
         if (use8Bits) {
            int tableSize = this.getTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize;
            table.fTable = new char[tableSize];
            table.fRowLen = rowLen;
         } else {
            int tableSize = (this.getTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize) / 2;
            table.fTable = new char[tableSize];
            table.fRowLen = rowLen * 2;
         }

         if (this.fRB.fLookAheadHardBreak) {
            table.fFlags |= 1;
         }

         if (this.fRB.fSetBuilder.sawBOF()) {
            table.fFlags |= 2;
         }

         if (use8Bits) {
            table.fFlags |= 4;
         }

         int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories();

         for (int state = 0; state < table.fNumStates; state++) {
            RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(state);
            int row = state * rowLen;
            if (use8Bits) {
               Assert.assrt(0 <= sd.fAccepting && sd.fAccepting <= 255);
               Assert.assrt(0 <= sd.fLookAhead && sd.fLookAhead <= 255);
            } else {
               Assert.assrt(0 <= sd.fAccepting && sd.fAccepting <= 65535);
               Assert.assrt(0 <= sd.fLookAhead && sd.fLookAhead <= 65535);
            }

            table.fTable[row + 0] = (char)sd.fAccepting;
            table.fTable[row + 1] = (char)sd.fLookAhead;
            table.fTable[row + 2] = (char)sd.fTagsIdx;

            for (int col = 0; col < numCharCategories; col++) {
               if (use8Bits) {
                  Assert.assrt(0 <= sd.fDtran[col] && sd.fDtran[col] <= 255);
               }

               table.fTable[row + 3 + col] = (char)sd.fDtran[col];
            }
         }

         return table;
      }
   }

   void buildSafeReverseTable() {
      StringBuilder safePairs = new StringBuilder();
      int numCharClasses = this.fRB.fSetBuilder.getNumCharCategories();
      int numStates = this.fDStates.size();

      for (int c1 = 0; c1 < numCharClasses; c1++) {
         for (int c2 = 0; c2 < numCharClasses; c2++) {
            int wantedEndState = -1;
            int endState = 0;

            for (int startState = 1; startState < numStates; startState++) {
               RBBITableBuilder.RBBIStateDescriptor startStateD = this.fDStates.get(startState);
               int s2 = startStateD.fDtran[c1];
               RBBITableBuilder.RBBIStateDescriptor s2StateD = this.fDStates.get(s2);
               endState = s2StateD.fDtran[c2];
               if (wantedEndState < 0) {
                  wantedEndState = endState;
               } else if (wantedEndState != endState) {
                  break;
               }
            }

            if (wantedEndState == endState) {
               safePairs.append((char)c1);
               safePairs.append((char)c2);
            }
         }
      }

      assert this.fSafeTable == null;

      this.fSafeTable = new ArrayList<>();

      for (int row = 0; row < numCharClasses + 2; row++) {
         this.fSafeTable.add(new short[numCharClasses]);
      }

      short[] startStatex = this.fSafeTable.get(1);

      for (int charClass = 0; charClass < numCharClasses; charClass++) {
         startStatex[charClass] = (short)(charClass + 2);
      }

      for (int row = 2; row < numCharClasses + 2; row++) {
         System.arraycopy(startStatex, 0, this.fSafeTable.get(row), 0, startStatex.length);
      }

      for (int pairIdx = 0; pairIdx < safePairs.length(); pairIdx += 2) {
         int c1 = safePairs.charAt(pairIdx);
         int c2 = safePairs.charAt(pairIdx + 1);
         short[] rowState = this.fSafeTable.get(c2 + 2);
         rowState[c1] = 0;
      }

      RBBIRuleBuilder.IntPair states = new RBBIRuleBuilder.IntPair(1, 0);

      while (this.findDuplicateSafeState(states)) {
         this.removeSafeState(states);
      }
   }

   int getSafeTableSize() {
      if (this.fSafeTable == null) {
         return 0;
      } else {
         int size = RBBIDataWrapper.RBBIStateTable.fHeaderSize;
         int numRows = this.fSafeTable.size();
         int numCols = this.fSafeTable.get(0).length;
         boolean use8Bits = numRows <= 255;
         int rowSize = (use8Bits ? 1 : 2) * (3 + numCols);
         size += numRows * rowSize;
         return size + 7 & -8;
      }
   }

   RBBIDataWrapper.RBBIStateTable exportSafeTable() {
      RBBIDataWrapper.RBBIStateTable table = new RBBIDataWrapper.RBBIStateTable();
      table.fNumStates = this.fSafeTable.size();
      boolean use8Bits = table.fNumStates <= 255;
      int numCharCategories = this.fSafeTable.get(0).length;
      int rowLen = 3 + numCharCategories;
      int tableSize = this.getSafeTableSize() - RBBIDataWrapper.RBBIStateTable.fHeaderSize;
      if (use8Bits) {
         table.fFlags |= 4;
         table.fTable = new char[tableSize];
         table.fRowLen = rowLen;
      } else {
         tableSize /= 2;
         table.fTable = new char[tableSize];
         table.fRowLen = rowLen * 2;
      }

      for (int state = 0; state < table.fNumStates; state++) {
         short[] rowArray = this.fSafeTable.get(state);
         int row = state * rowLen;

         for (int col = 0; col < numCharCategories; col++) {
            if (use8Bits) {
               Assert.assrt(rowArray[col] <= 255);
            }

            table.fTable[row + 3 + col] = (char)rowArray[col];
         }
      }

      return table;
   }

   void printSet(Collection<RBBINode> s) {
      for (RBBINode n : s) {
         RBBINode.printInt(n.fSerialNum, 8);
      }

      System.out.println();
   }

   void printStates() {
      System.out.print("state |           i n p u t     s y m b o l s \n");
      System.out.print("      | Acc  LA    Tag");

      for (int c = 0; c < this.fRB.fSetBuilder.getNumCharCategories(); c++) {
         RBBINode.printInt(c, 4);
      }

      System.out.print("\n");
      System.out.print("      |---------------");

      for (int var4 = 0; var4 < this.fRB.fSetBuilder.getNumCharCategories(); var4++) {
         System.out.print("----");
      }

      System.out.print("\n");

      for (int n = 0; n < this.fDStates.size(); n++) {
         RBBITableBuilder.RBBIStateDescriptor sd = this.fDStates.get(n);
         RBBINode.printInt(n, 5);
         System.out.print(" | ");
         RBBINode.printInt(sd.fAccepting, 3);
         RBBINode.printInt(sd.fLookAhead, 4);
         RBBINode.printInt(sd.fTagsIdx, 6);
         System.out.print(" ");

         for (int var5 = 0; var5 < this.fRB.fSetBuilder.getNumCharCategories(); var5++) {
            RBBINode.printInt(sd.fDtran[var5], 4);
         }

         System.out.print("\n");
      }

      System.out.print("\n\n");
   }

   void printReverseTable() {
      System.out.printf("    Safe Reverse Table \n");
      if (this.fSafeTable == null) {
         System.out.printf("   --- nullptr ---\n");
      } else {
         int numCharCategories = this.fSafeTable.get(0).length;
         System.out.printf("state |           i n p u t     s y m b o l s \n");
         System.out.printf("      | Acc  LA    Tag");

         for (int c = 0; c < numCharCategories; c++) {
            System.out.printf(" %2d", c);
         }

         System.out.printf("\n");
         System.out.printf("      |---------------");

         for (int var5 = 0; var5 < numCharCategories; var5++) {
            System.out.printf("---");
         }

         System.out.printf("\n");

         for (int n = 0; n < this.fSafeTable.size(); n++) {
            short[] rowArray = this.fSafeTable.get(n);
            System.out.printf("  %3d | ", n);
            System.out.printf("%3d %3d %5d ", 0, 0, 0);

            for (int var6 = 0; var6 < numCharCategories; var6++) {
               System.out.printf(" %2d", rowArray[var6]);
            }

            System.out.printf("\n");
         }

         System.out.printf("\n\n");
      }
   }

   void printRuleStatusTable() {
      int thisRecord = 0;
      int nextRecord = 0;
      List<Integer> tbl = this.fRB.fRuleStatusVals;
      System.out.print("index |  tags \n");
      System.out.print("-------------------\n");

      while (nextRecord < tbl.size()) {
         thisRecord = nextRecord;
         nextRecord = nextRecord + tbl.get(nextRecord) + 1;
         RBBINode.printInt(thisRecord, 7);

         for (int i = thisRecord + 1; i < nextRecord; i++) {
            int val = tbl.get(i);
            RBBINode.printInt(val, 7);
         }

         System.out.print("\n");
      }

      System.out.print("\n\n");
   }

   static class RBBIStateDescriptor {
      boolean fMarked;
      int fAccepting;
      int fLookAhead;
      SortedSet<Integer> fTagVals = new TreeSet<>();
      int fTagsIdx;
      Set<RBBINode> fPositions = new HashSet<>();
      int[] fDtran;

      RBBIStateDescriptor(int maxInputSymbol) {
         this.fDtran = new int[maxInputSymbol + 1];
      }
   }
}
