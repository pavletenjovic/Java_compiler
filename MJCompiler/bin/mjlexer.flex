package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"length"    { return new_symbol(sym.LEN, yytext()); }
"const"     { return new_symbol(sym.CONST, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"continue"	{ return new_symbol(sym.CONTINUE, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"switch"	{ return new_symbol(sym.SWITCH, yytext()); }

"case"		{ return new_symbol(sym.CASE, yytext()); }
"for"		{ return new_symbol(sym.FOR, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }
"?"			{ return new_symbol(sym.QUESTIONMARK, yytext()); }

"==" 		{ return new_symbol(sym.EQ, yytext()); }
"!=" 		{ return new_symbol(sym.NEQ, yytext()); }
">" 		{ return new_symbol(sym.GT, yytext()); }
">=" 		{ return new_symbol(sym.GTE, yytext()); }
"<" 		{ return new_symbol(sym.LT, yytext()); }
"<=" 		{ return new_symbol(sym.LTE, yytext()); }

"enum"		{ return new_symbol(sym.ENUM, yytext()); }
"++" 		{ return new_symbol(sym.INC, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LBRACKET, yytext()); }
"]"			{ return new_symbol(sym.RBRACKET, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }

"new" 		{ return new_symbol(sym.NEW, yytext()); }
"true"  	{ return new_symbol(sym.BOOL, 1); }
"false" 	{ return new_symbol(sym.BOOL, 0); }


<YYINITIAL> "//" { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER, Integer.parseInt(yytext())); }

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

\'[^\']\' 	{return new_symbol(sym.CHAR, yytext().charAt(1));}

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }
