//JFlex specification for VErbose SpornyScript (vess)
// [FIXME] for now use a sample spec to get the build up and running.
/* JFlex example: partial Java language lexer specification */
package com.sporniket.scripting.ssl.vess;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

/**
 * This class is a simple example lexer.
 */
%%

%class AnalyzerLexical
%public
%final
%unicode
%line
%column

//CUP specific directives.
%cupsym AnalyzerSymbols
%cup
%eofval{
  return symbol(AnalyzerSymbols.EOF);
%eofval}

%{
  StringBuffer string = new StringBuffer();
  
  /**
   * link to the symbol factory
   */
  private ComplexSymbolFactory mySymbolFactory ;
  public ComplexSymbolFactory getSymbolFactory() {return mySymbolFactory;}
  public void setSymbolFactory(ComplexSymbolFactory symbolFactory) {mySymbolFactory = symbolFactory ;}

    //private int csline,cscolumn;
    public Symbol symbol(int code){
	return getSymbolFactory().newSymbol(AnalyzerSymbols.terminalNames[code], code,new Location(yyline+1,yycolumn+1-yylength()),new Location(yyline+1,yycolumn+1));
    }
    public Symbol symbol(int code, Object value){
	return getSymbolFactory().newSymbol(AnalyzerSymbols.terminalNames[code], code, new Location(yyline+1, yycolumn +1), new Location(yyline+1,yycolumn+yylength()), value);
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
PackagePrefix = ( {Identifier} "." )+

DecIntegerLiteral = 0 | [1-9][0-9]*

%state STRING

%%

/* keywords */
<YYINITIAL> "define"           { return symbol(AnalyzerSymbols.DEFINE); }
<YYINITIAL> "as"            { return symbol(AnalyzerSymbols.AS); }
<YYINITIAL> "new"              { return symbol(AnalyzerSymbols.NEW); }
<YYINITIAL> "call"              { return symbol(AnalyzerSymbols.CALL); }
<YYINITIAL> "using"              { return symbol(AnalyzerSymbols.USING); }
<YYINITIAL> "from"              { return symbol(AnalyzerSymbols.FROM); }
<YYINITIAL> ","              { return symbol(AnalyzerSymbols.COMMA); }

<YYINITIAL> {
  /* identifiers */ 
  {PackagePrefix}                   { return symbol(AnalyzerSymbols.PACKAGEPREFIX, yytext()); }
  {Identifier}                   { return symbol(AnalyzerSymbols.IDENTIFIER, yytext()); }
 
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

//EOF Rules, necessary to return a complex symbol.
//<<EOF>>  { return symbol(AnalyzerSymbols.EOF); }
