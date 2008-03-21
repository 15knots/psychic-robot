// $ANTLR : "StdCParser.g" -> "StdCLexer.java"$

package parser.grammar;

public interface StdCTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int LITERAL_typedef = 4;
	int LITERAL_asm = 5;
	int LITERAL_volatile = 6;
	int LCURLY = 7;
	int RCURLY = 8;
	int SEMI = 9;
	int LITERAL_auto = 10;
	int LITERAL_register = 11;
	int LITERAL_extern = 12;
	int LITERAL_static = 13;
	int LITERAL_const = 14;
	int LITERAL_void = 15;
	int LITERAL_char = 16;
	int LITERAL_short = 17;
	int LITERAL_int = 18;
	int LITERAL_long = 19;
	int LITERAL_float = 20;
	int LITERAL_double = 21;
	int LITERAL_signed = 22;
	int LITERAL_unsigned = 23;
	int ID = 24;
	int LITERAL_struct = 25;
	int LITERAL_union = 26;
	int COMMA = 27;
	int COLON = 28;
	int LITERAL_enum = 29;
	int ASSIGN = 30;
	int STAR = 31;
	int LPAREN = 32;
	int RPAREN = 33;
	int LBRACKET = 34;
	int RBRACKET = 35;
	int VARARGS = 36;
	int LITERAL_while = 37;
	int LITERAL_do = 38;
	int LITERAL_for = 39;
	int LITERAL_goto = 40;
	int LITERAL_continue = 41;
	int LITERAL_break = 42;
	int LITERAL_return = 43;
	int LITERAL_case = 44;
	int LITERAL_default = 45;
	int LITERAL_if = 46;
	int LITERAL_else = 47;
	int LITERAL_switch = 48;
	int DIV_ASSIGN = 49;
	int PLUS_ASSIGN = 50;
	int MINUS_ASSIGN = 51;
	int STAR_ASSIGN = 52;
	int MOD_ASSIGN = 53;
	int RSHIFT_ASSIGN = 54;
	int LSHIFT_ASSIGN = 55;
	int BAND_ASSIGN = 56;
	int BOR_ASSIGN = 57;
	int BXOR_ASSIGN = 58;
	int QUESTION = 59;
	int LOR = 60;
	int LAND = 61;
	int BOR = 62;
	int BXOR = 63;
	int BAND = 64;
	int EQUAL = 65;
	int NOT_EQUAL = 66;
	int LT = 67;
	int LTE = 68;
	int GT = 69;
	int GTE = 70;
	int LSHIFT = 71;
	int RSHIFT = 72;
	int PLUS = 73;
	int MINUS = 74;
	int DIV = 75;
	int MOD = 76;
	int INC = 77;
	int DEC = 78;
	int LITERAL_sizeof = 79;
	int BNOT = 80;
	int LNOT = 81;
	int PTR = 82;
	int DOT = 83;
	int CharLiteral = 84;
	int StringLiteral = 85;
	int IntOctalConst = 86;
	int LongOctalConst = 87;
	int UnsignedOctalConst = 88;
	int IntIntConst = 89;
	int LongIntConst = 90;
	int UnsignedIntConst = 91;
	int IntHexConst = 92;
	int LongHexConst = 93;
	int UnsignedHexConst = 94;
	int FloatDoubleConst = 95;
	int DoubleDoubleConst = 96;
	int LongDoubleConst = 97;
	int NTypedefName = 98;
	int NInitDecl = 99;
	int NDeclarator = 100;
	int NStructDeclarator = 101;
	int NDeclaration = 102;
	int NCast = 103;
	int NPointerGroup = 104;
	int NExpressionGroup = 105;
	int NFunctionCallArgs = 106;
	int NNonemptyAbstractDeclarator = 107;
	int NInitializer = 108;
	int NStatementExpr = 109;
	int NEmptyExpression = 110;
	int NParameterTypeList = 111;
	int NFunctionDef = 112;
	int NCompoundStatement = 113;
	int NParameterDeclaration = 114;
	int NCommaExpr = 115;
	int NUnaryExpr = 116;
	int NLabel = 117;
	int NPostfixExpr = 118;
	int NRangeExpr = 119;
	int NStringSeq = 120;
	int NInitializerElementLabel = 121;
	int NLcurlyInitializer = 122;
	int NAsmAttribute = 123;
	int NGnuAsmExpr = 124;
	int NTypeMissing = 125;
	int Vocabulary = 126;
	int Space = 127;
	int Whitespace = 128;
	int Newline = 129;
	int Comment = 130;
	int CPPComment = 131;
	int PREPROC_DIRECTIVE = 132;
	int LineDirective = 133;
	int StringLiteralSingleLine = 134;
	int BadStringLiteral = 135;
	int Escape = 136;
	int Digit = 137;
	int LongSuffix = 138;
	int UnsignedSuffix = 139;
	int FloatSuffix = 140;
	int Exponent = 141;
	int Number = 142;
	int IDletter = 143;
}
