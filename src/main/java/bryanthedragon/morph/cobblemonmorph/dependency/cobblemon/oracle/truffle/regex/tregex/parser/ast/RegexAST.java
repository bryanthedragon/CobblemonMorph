package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexOptions;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.RegexProperties;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.ASTDebugDumpVisitor;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.Equivalence;

public final class RegexAST implements StateIndex<RegexASTNode>, JsonConvertible {
   private final RegexLanguage language;
   private final RegexSource source;
   private RegexFlags flags;
   private final Counter.ThresholdCounter nodeCount = new Counter.ThresholdCounter(Integer.MAX_VALUE, "parse tree explosion");
   private final Counter.ThresholdCounter groupCount = new Counter.ThresholdCounter(32767, "too many capture groups");
   private final Counter quantifierCount = new Counter();
   private final RegexProperties properties = new RegexProperties();
   private final TBitSet referencedGroups = new TBitSet(64);
   private RegexASTNode[] nodes;
   private Group root;
   private Group wrappedRoot;
   private Group[] captureGroups;
   private final List<QuantifiableTerm> zeroWidthQuantifiables = new ArrayList<>();
   private final GlobalSubTreeIndex subtrees = new GlobalSubTreeIndex();
   private final List<PositionAssertion> reachableCarets = new ArrayList<>();
   private final List<PositionAssertion> reachableDollars = new ArrayList<>();
   private StateSet<RegexAST, PositionAssertion> nfaAnchoredInitialStates;
   private StateSet<RegexAST, RegexASTNode> hardPrefixNodes;
   private final EconomicMap<GroupBoundaries, GroupBoundaries> groupBoundariesDeduplicationMap = EconomicMap.create();
   private final EconomicMap<RegexASTNode, List<SourceSection>> sourceSections;

   public RegexAST(RegexLanguage language, RegexSource source, RegexFlags flags) {
      this.language = language;
      this.source = source;
      this.flags = flags;
      this.sourceSections = source.getOptions().isDumpAutomataWithSourceSections() ? EconomicMap.create(Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE) : null;
   }

   public RegexLanguage getLanguage() {
      return this.language;
   }

   public RegexSource getSource() {
      return this.source;
   }

   public RegexFlags getFlags() {
      return this.flags;
   }

   public void setFlags(RegexFlags flags) {
      this.flags = flags;
   }

   public RegexOptions getOptions() {
      return this.source.getOptions();
   }

   public Encodings.Encoding getEncoding() {
      return this.source.getEncoding();
   }

   public Group getRoot() {
      return this.root;
   }

   public void setRoot(Group root) {
      this.root = root;
   }

   public Group getWrappedRoot() {
      return this.wrappedRoot;
   }

   public boolean rootIsWrapped() {
      return this.wrappedRoot != null && this.root != this.wrappedRoot;
   }

   public Counter.ThresholdCounter getNodeCount() {
      return this.nodeCount;
   }

   public int getNumberOfNodes() {
      return this.nodeCount.getCount();
   }

   public Counter.ThresholdCounter getGroupCount() {
      return this.groupCount;
   }

   public int getNumberOfCaptureGroups() {
      return this.groupCount.getCount();
   }

   public Counter getQuantifierCount() {
      return this.quantifierCount;
   }

   public void registerZeroWidthQuantifiable(QuantifiableTerm zeroWidthQuantifiable) {
      zeroWidthQuantifiable.getQuantifier().setZeroWidthIndex(this.zeroWidthQuantifiables.size());
      this.zeroWidthQuantifiables.add(zeroWidthQuantifiable);
   }

   public List<QuantifiableTerm> getZeroWidthQuantifiables() {
      return this.zeroWidthQuantifiables;
   }

   public Group getGroupByBoundaryIndex(int index) {
      if (this.captureGroups == null) {
         this.captureGroups = new Group[this.getNumberOfCaptureGroups()];

         for (RegexASTNode n : this.nodes) {
            if (n instanceof Group && ((Group)n).isCapturing()) {
               this.captureGroups[((Group)n).getGroupNumber()] = (Group)n;
            }
         }
      }

      return this.captureGroups[index / 2];
   }

   public RegexProperties getProperties() {
      return this.properties;
   }

   public boolean isLiteralString() {
      Group r = this.getRoot();
      RegexProperties p = this.getProperties();
      return !p.hasBackReferences()
         && !p.hasAlternations()
         && !p.hasLookAroundAssertions()
         && !r.hasLoops()
         && (!r.startsWithCaret() && !r.endsWithDollar() || !this.getFlags().isMultiline())
         && (!p.hasCharClasses() || p.charClassesCanBeMatchedWithMask());
   }

   @Override
   public int getNumberOfStates() {
      return this.nodes.length;
   }

   public int getId(RegexASTNode state) {
      return state.getId();
   }

   public RegexASTNode getState(int id) {
      return this.nodes[id];
   }

   public void setIndex(RegexASTNode[] index) {
      this.nodes = index;
   }

   public int getWrappedPrefixLength() {
      return this.rootIsWrapped() ? this.wrappedRoot.getFirstAlternative().size() - (this.flags.isSticky() ? 1 : 2) : 0;
   }

   public RegexASTNode getEntryAfterPrefix() {
      return (RegexASTNode)(this.rootIsWrapped() ? this.wrappedRoot.getFirstAlternative().getTerms().get(this.getWrappedPrefixLength()) : this.wrappedRoot);
   }

   public GlobalSubTreeIndex getSubtrees() {
      return this.subtrees;
   }

   public List<PositionAssertion> getReachableCarets() {
      return this.reachableCarets;
   }

   public List<PositionAssertion> getReachableDollars() {
      return this.reachableDollars;
   }

   public StateSet<RegexAST, PositionAssertion> getNfaAnchoredInitialStates() {
      return this.nfaAnchoredInitialStates;
   }

   public StateSet<RegexAST, RegexASTNode> getHardPrefixNodes() {
      return this.hardPrefixNodes;
   }

   public RegexASTRootNode createRootNode() {
      RegexASTRootNode node = new RegexASTRootNode();
      this.createNFAHelperNodes(node);
      return node;
   }

   public BackReference createBackReference(int groupNumber) {
      this.referencedGroups.set(groupNumber);
      return this.register(new BackReference(groupNumber));
   }

   public boolean isGroupReferenced(int groupNumber) {
      return this.referencedGroups.get(groupNumber);
   }

   public CharacterClass createCharacterClass(CodePointSet matcherBuilder) {
      assert this.getEncoding().getFullSet().contains(matcherBuilder);

      return this.register(new CharacterClass(matcherBuilder));
   }

   public Group createGroup() {
      return this.register(new Group());
   }

   public Group createCaptureGroup(int groupNumber) {
      return this.register(new Group(groupNumber));
   }

   public LookAheadAssertion createLookAheadAssertion(boolean negated) {
      LookAheadAssertion assertion = new LookAheadAssertion(negated);
      this.createNFAHelperNodes(assertion);
      return this.register(assertion);
   }

   public LookBehindAssertion createLookBehindAssertion(boolean negated) {
      LookBehindAssertion assertion = new LookBehindAssertion(negated);
      this.createNFAHelperNodes(assertion);
      return this.register(assertion);
   }

   public AtomicGroup createAtomicGroup() {
      AtomicGroup atomicGroup = new AtomicGroup();
      this.createNFAHelperNodes(atomicGroup);
      return this.register(atomicGroup);
   }

   public void createNFAHelperNodes(RegexASTSubtreeRootNode rootNode) {
      this.nodeCount.inc(4);
      PositionAssertion anchored = new PositionAssertion(PositionAssertion.Type.CARET);
      rootNode.setAnchoredInitialState(anchored);
      MatchFound unAnchored = new MatchFound();
      rootNode.setUnAnchoredInitialState(unAnchored);
      MatchFound end = new MatchFound();
      rootNode.setMatchFound(end);
      PositionAssertion anchoredEnd = new PositionAssertion(PositionAssertion.Type.DOLLAR);
      rootNode.setAnchoredFinalState(anchoredEnd);
   }

   public PositionAssertion createPositionAssertion(PositionAssertion.Type type) {
      return this.register(new PositionAssertion(type));
   }

   public Sequence createSequence() {
      return this.register(new Sequence());
   }

   public SubexpressionCall createSubexpressionCall(int groupNumber) {
      return this.register(new SubexpressionCall(groupNumber));
   }

   public BackReference register(BackReference backReference) {
      this.nodeCount.inc();
      return backReference;
   }

   public CharacterClass register(CharacterClass characterClass) {
      this.nodeCount.inc();
      return characterClass;
   }

   public Group register(Group group) {
      this.nodeCount.inc();
      return group;
   }

   public LookAheadAssertion register(LookAheadAssertion lookAheadAssertion) {
      this.nodeCount.inc();
      return lookAheadAssertion;
   }

   public LookBehindAssertion register(LookBehindAssertion lookBehindAssertion) {
      this.nodeCount.inc();
      return lookBehindAssertion;
   }

   public AtomicGroup register(AtomicGroup atomicGroup) {
      this.nodeCount.inc();
      return atomicGroup;
   }

   public PositionAssertion register(PositionAssertion positionAssertion) {
      this.nodeCount.inc();
      return positionAssertion;
   }

   public Sequence register(Sequence sequence) {
      this.nodeCount.inc();
      return sequence;
   }

   public SubexpressionCall register(SubexpressionCall subexpressionCall) {
      this.nodeCount.inc();
      return subexpressionCall;
   }

   public boolean isNFAInitialState(RegexASTNode node) {
      return node.getId() >= 1 && node.getId() <= this.getWrappedPrefixLength() * 2 + 2;
   }

   private void createNFAInitialStates() {
      if (this.nfaAnchoredInitialStates == null) {
         this.hardPrefixNodes = StateSet.create(this);
         this.nfaAnchoredInitialStates = StateSet.create(this);
         int nextID = 1;
         MatchFound mf = new MatchFound();
         this.initNodeId(mf, nextID++);
         mf.setNext(this.getEntryAfterPrefix());
         PositionAssertion pos = new PositionAssertion(PositionAssertion.Type.CARET);
         this.initNodeId(pos, nextID++);
         this.nfaAnchoredInitialStates.add(pos);
         pos.setNext(this.getEntryAfterPrefix());

         for (int i = this.getWrappedPrefixLength() - 1; i >= 0; i--) {
            RegexASTNode prefixNode = this.getWrappedRoot().getFirstAlternative().getTerms().get(i);
            this.hardPrefixNodes.add(prefixNode);
            mf = new MatchFound();
            this.initNodeId(mf, nextID++);
            mf.setNext(prefixNode);
            pos = new PositionAssertion(PositionAssertion.Type.CARET);
            this.initNodeId(pos, nextID++);
            this.nfaAnchoredInitialStates.add(pos);
            pos.setNext(prefixNode);
         }
      }
   }

   public MatchFound getNFAUnAnchoredInitialState(int prefixOffset) {
      this.createNFAInitialStates();

      assert this.nodes[prefixOffset * 2 + 1] != null;

      return (MatchFound)this.nodes[prefixOffset * 2 + 1];
   }

   public PositionAssertion getNFAAnchoredInitialState(int prefixOffset) {
      this.createNFAInitialStates();

      assert this.nodes[prefixOffset * 2 + 2] != null;

      return (PositionAssertion)this.nodes[prefixOffset * 2 + 2];
   }

   public void createPrefix() {
      if (!this.root.startsWithCaret() && !this.properties.hasNonLiteralLookBehindAssertions()) {
         int prefixLength = 0;

         for (RegexASTSubtreeRootNode subtreeRootNode : this.subtrees) {
            if (subtreeRootNode.isLookBehindAssertion()) {
               LookBehindAssertion lb = subtreeRootNode.asLookBehindAssertion();
               int minPath = lb.getMinPath();

               for (RegexASTSubtreeRootNode laParent = lb.getSubTreeParent(); !(laParent instanceof RegexASTRootNode); laParent = laParent.getSubTreeParent()) {
                  if (laParent instanceof LookBehindAssertion) {
                     throw new UnsupportedRegexException("nested look-behind assertions");
                  }

                  minPath += laParent.getMinPath();
               }

               prefixLength = Math.max(prefixLength, lb.getLiteralLength() - minPath);
            }
         }

         if (prefixLength == 0) {
            this.wrappedRoot = this.root;
         } else {
            Group wrapRoot = this.createGroup();
            wrapRoot.setPrefix();
            Sequence wrapRootSeq = this.createSequence();
            wrapRoot.add(wrapRootSeq);
            wrapRootSeq.setPrefix();

            for (int i = 0; i < prefixLength; i++) {
               wrapRootSeq.add(this.createPrefixAnyMatcher());
            }

            if (!this.flags.isSticky()) {
               Group prevOpt = null;

               for (int i = 0; i < prefixLength; i++) {
                  Group opt = this.createGroup();
                  opt.setPrefix();
                  opt.add(this.createSequence());
                  opt.add(this.createSequence());
                  opt.getFirstAlternative().setPrefix();
                  opt.getAlternatives().get(1).setPrefix();
                  opt.getAlternatives().get(1).add(this.createPrefixAnyMatcher());
                  if (prevOpt != null) {
                     opt.getAlternatives().get(1).add(prevOpt);
                  }

                  prevOpt = opt;
               }

               wrapRootSeq.add(prevOpt);
            }

            this.root.getSubTreeParent().setGroup(wrapRoot);
            wrapRootSeq.add(this.root);
            this.wrappedRoot = wrapRoot;
         }
      } else {
         this.wrappedRoot = this.root;
      }
   }

   public void hidePrefix() {
      if (this.wrappedRoot != this.root) {
         this.root.getSubTreeParent().setGroup(this.root);
      }
   }

   public void unhidePrefix() {
      if (this.wrappedRoot != this.root) {
         this.root.getSubTreeParent().setGroup(this.wrappedRoot);
      }
   }

   public GroupBoundaries createGroupBoundaries(TBitSet updateIndices, TBitSet clearIndices, int lastGroup) {
      if (!this.getOptions().getFlavor().usesLastGroupResultField()) {
         GroupBoundaries staticInstance = GroupBoundaries.getStaticInstance(this.language, updateIndices, clearIndices);
         if (staticInstance != null) {
            return staticInstance;
         }
      }

      GroupBoundaries lookup = new GroupBoundaries(updateIndices, clearIndices, lastGroup);
      if (this.groupBoundariesDeduplicationMap.containsKey(lookup)) {
         return this.groupBoundariesDeduplicationMap.get(lookup);
      } else {
         GroupBoundaries gb = new GroupBoundaries(updateIndices.copy(), clearIndices.copy(), lastGroup);
         this.groupBoundariesDeduplicationMap.put(gb, gb);
         return gb;
      }
   }

   private CharacterClass createPrefixAnyMatcher() {
      CharacterClass anyMatcher = this.createCharacterClass(this.getEncoding().getFullSet());
      anyMatcher.setPrefix();
      return anyMatcher;
   }

   private void addToIndex(RegexASTNode node) {
      assert node.getId() >= 0;

      assert node.getId() < this.nodes.length;

      assert this.nodes[node.getId()] == null;

      this.nodes[node.getId()] = node;
   }

   private void initNodeId(RegexASTNode node, int id) {
      node.setId(id);
      this.addToIndex(node);
   }

   public List<SourceSection> getSourceSections(RegexASTNode node) {
      return this.getOptions().isDumpAutomataWithSourceSections() ? this.sourceSections.get(node) : null;
   }

   public void addSourceSection(RegexASTNode node, Token token) {
      if (this.getOptions().isDumpAutomataWithSourceSections() && token != null && token.getSourceSection() != null) {
         this.getOrCreateSourceSections(node).add(token.getSourceSection());
      }
   }

   public void addSourceSections(RegexASTNode node, Collection<SourceSection> src) {
      if (this.getOptions().isDumpAutomataWithSourceSections() && src != null) {
         this.getOrCreateSourceSections(node).addAll(src);
      }
   }

   private List<SourceSection> getOrCreateSourceSections(RegexASTNode node) {
      List<SourceSection> sections = this.sourceSections.get(node);
      if (sections == null) {
         sections = new ArrayList<>();
         this.sourceSections.put(node, sections);
      }

      return sections;
   }

   public InnerLiteral extractInnerLiteral() {
      assert this.properties.hasInnerLiteral();

      int literalEnd = this.properties.getInnerLiteralEnd();
      int literalStart = this.properties.getInnerLiteralStart();
      AbstractStringBuffer literal = this.getEncoding().createStringBuffer(literalEnd - literalStart);
      AbstractStringBuffer mask = this.getEncoding().createStringBuffer(literalEnd - literalStart);
      boolean hasMask = false;

      for (int i = literalStart; i < literalEnd; i++) {
         CharacterClass cc = this.root.getFirstAlternative().getTerms().get(i).asCharacterClass();

         assert cc.getCharSet().matchesSingleChar() || cc.getCharSet().matches2CharsWith1BitDifference();

         assert this.getEncoding().isFixedCodePointWidth(cc.getCharSet());

         cc.extractSingleChar(literal, mask);
         hasMask |= cc.getCharSet().matches2CharsWith1BitDifference();
      }

      return new InnerLiteral(literal.materialize(), hasMask ? mask.materialize() : null, this.root.getFirstAlternative().get(literalStart).getMaxPath() - 1);
   }

   public boolean canTransformToDFA() {
      boolean couldCalculateLastGroup = !this.getOptions().getFlavor().usesLastGroupResultField()
         || !this.getProperties().hasCaptureGroupsInLookAroundAssertions();
      return this.getNumberOfNodes() <= 4000
         && this.getNumberOfCaptureGroups() <= 127
         && !this.getProperties().hasBackReferences()
         && !this.getProperties().hasLargeCountedRepetitions()
         && !this.getProperties().hasNegativeLookAheadAssertions()
         && !this.getProperties().hasNonLiteralLookBehindAssertions()
         && !this.getProperties().hasNegativeLookBehindAssertions()
         && !this.getRoot().hasQuantifiers()
         && !this.getProperties().hasAtomicGroups()
         && couldCalculateLastGroup;
   }

   @CompilerDirectives.TruffleBoundary
   public String canTransformToDFAFailureReason() {
      StringJoiner sb = new StringJoiner(", ");
      if (this.getNumberOfNodes() > 4000) {
         sb.add(String.format("Parser tree has too many nodes: %d (threshold: %d)", this.getNumberOfNodes(), 4000));
      }

      if (this.getNumberOfCaptureGroups() > 127) {
         sb.add(String.format("regex has too many capture groups: %d (threshold: %d)", this.getNumberOfCaptureGroups(), 127));
      }

      if (this.getProperties().hasBackReferences()) {
         sb.add("regex has back-references");
      }

      if (this.getProperties().hasLargeCountedRepetitions()) {
         sb.add(String.format("regex has large counted repetitions (threshold: %d for single CC, %d for groups)", 20, 6));
      }

      if (this.getProperties().hasNegativeLookAheadAssertions()) {
         sb.add("regex has negative look-ahead assertions");
      }

      if (this.getProperties().hasNegativeLookBehindAssertions()) {
         sb.add("regex has negative look-behind assertions");
      }

      if (this.getProperties().hasNonLiteralLookBehindAssertions()) {
         sb.add("regex has non-literal look-behind assertions");
      }

      if (this.getRoot().hasQuantifiers()) {
         sb.add("could not unroll all quantifiers");
      }

      if (this.getProperties().hasAtomicGroups()) {
         sb.add("regex has atomic groups");
      }

      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("source", this.source),
         Json.prop("root", this.root),
         Json.prop("debugAST", ASTDebugDumpVisitor.getDump(this.wrappedRoot)),
         Json.prop("wrappedRoot", this.wrappedRoot),
         Json.prop("reachableCarets", this.reachableCarets),
         Json.prop("startsWithCaret", this.root.startsWithCaret()),
         Json.prop("endsWithDollar", this.root.endsWithDollar()),
         Json.prop("reachableDollars", this.reachableDollars),
         Json.prop("properties", this.properties)
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray sourceSectionsToJson(List<SourceSection> sourceSections) {
      return sourceSections == null ? Json.array() : sourceSectionsToJson(sourceSections.stream());
   }

   @CompilerDirectives.TruffleBoundary
   public static JsonArray sourceSectionsToJson(Stream<SourceSection> sourceSections) {
      return sourceSections == null
         ? Json.array()
         : Json.array(sourceSections.map(x -> Json.obj(Json.prop("start", x.getCharIndex()), Json.prop("end", x.getCharEndIndex()))));
   }
}
