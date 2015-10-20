//JFlex specification for VErbose SpornyScript (vess)
// [FIXME] for now use a sample spec to get the build up and running.
/* JFlex example: partial Java language lexer specification */
package com.sporniket.scripting.ssl.vess;
import java_cup.runtime.*;

/**
 * This class is a simple example lexer.
 */
%%

%class AnalyzerLexical
%unicode
%cupsym AnalyzerSymbols
%cup
%line
%column

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
//Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

//TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
//EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
//DocumentationComment = "/**" {CommentContent} "*"+ "/"
//CommentContent       = ( [^*] | \*+ [^/*] )*

//char classes
Alpha = [A-Za-z]
Num = [0-9]
NumNotZero = [1-9]
AlphaNum = {Alpha} | {Num}

Identifier = {Alpha} {AlphaNum}*
ClassName = {Identifier} ( "." {Identifier} )+

DecIntegerLiteral = 0 | [1-9][0-9]*

%state STRING

%%

/* keywords */
<YYINITIAL> "define"           { return symbol(AnalyzerSymbols.DEFINE); }
<YYINITIAL> "as"            { return symbol(AnalyzerSymbols.AS); }
<YYINITIAL> "new"              { return symbol(AnalyzerSymbols.NEW); }

<YYINITIAL> {
  /* identifiers */ 
  {ClassName}                   { return symbol(AnalyzerSymbols.CLASSNAME); }
  {Identifier}                   { return symbol(AnalyzerSymbols.IDENTIFIER); }
 
  /* literals */
//  {DecIntegerLiteral}            { return symbol(AnalyzerSymbols.INTEGER_LITERAL); }
//  \"                             { string.setLength(0); yybegin(STRING); }

  /* operators */
//  "="                            { return symbol(AnalyzerSymbols.EQ); }
//  "=="                           { return symbol(AnalyzerSymbols.EQEQ); }
//  "+"                            { return symbol(AnalyzerSymbols.PLUS); }

  /* comments */
//  {Comment}                      { /* ignore */ }
 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

//<STRING> {
//  \"                             { yybegin(YYINITIAL); 
//                                   return symbol(AnalyzerSymbols.STRING_LITERAL, 
//                                   string.toString()); }
//  [^\n\r\"\\]+                   { string.append( yytext() ); }
//  \\t                            { string.append('\t'); }
//  \\n                            { string.append('\n'); }

//  \\r                            { string.append('\r'); }
//  \\\"                           { string.append('\"'); }
//  \\                             { string.append('\\'); }
//}

/* error fallback */
[^]                              { throw new Error("Illegal character <"+ yytext()+">"); }