grammar SuiteDocGrammar;

// Lexer rules
WS              : [ \t\r\n]+ -> skip;  // Ignore whitespace, tabs, carriage returns, and newlines
STRING          : '"' (~["\\])* '"';   // Matches quoted strings correctly handling all characters
NUMBER          : [0-9]+;              // For numbers, if needed

// Parser rules
suiteDocAnnotation : '@SuiteDoc' '(' suiteDocBody ')';
suiteDocBody       : suiteDocAttribute ( ',' suiteDocAttribute )* ;
suiteDocAttribute  : descriptionAttribute
                  | contactAttribute
                  | beforeTestStepsAttribute
                  | afterTestStepsAttribute
                  | useCasesAttribute
                  | tagsAttribute
                  ;

descriptionAttribute     : 'description' '=' '@Desc' '(' STRING ')';
contactAttribute         : 'contact' '=' '@Contact' '(' contactBody ')';
contactBody              : 'name' '=' STRING ',' 'email' '=' STRING;
beforeTestStepsAttribute : 'beforeTestSteps' '=' '{' step ( ',' step )* '}';
afterTestStepsAttribute  : 'afterTestSteps' '=' '{' step ( ',' step )* '}';
step                     : '@Step' '(' 'value' '=' STRING ',' 'expected' '=' STRING ')';
useCasesAttribute        : 'useCases' '=' '{' useCase ( ',' useCase )* '}';
useCase                  : '@UseCase' '(' 'id' '=' STRING ')';
tagsAttribute            : 'tags' '=' '{' testTag ( ',' testTag )* '}';
testTag                  : '@TestTag' '(' 'value' '=' STRING ')';
