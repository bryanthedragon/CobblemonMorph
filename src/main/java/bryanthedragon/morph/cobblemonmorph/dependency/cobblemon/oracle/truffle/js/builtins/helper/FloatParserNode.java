package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;

public class FloatParserNode extends Node {
   final BranchProfile exponentBranch = BranchProfile.create();
   @Node.Child
   TruffleString.ReadCharUTF16Node charAtNode = TruffleString.ReadCharUTF16Node.create();
   @Node.Child
   TruffleString.SubstringByteIndexNode substringNode = TruffleString.SubstringByteIndexNode.create();
   @Node.Child
   TruffleString.ParseDoubleNode parseDoubleNode = TruffleString.ParseDoubleNode.create();

   protected FloatParserNode() {
   }

   public static FloatParserNode create() {
      return new FloatParserNode();
   }

   public double parse(TruffleString input) {
      return new FloatParser(input, this).getResult();
   }
}
