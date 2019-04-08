import syntaxtree.*;
import visitor.GJNoArguDepthFirst;


class ReturnInfo {
    public String name = null;
    public Type type = null;

    public ReturnInfo(String _name){
        name = _name;
    }

    public ReturnInfo(Type _type){
        type = _type;
    }
}



public class CreateSymbolTableVisitor extends GJNoArguDepthFirst<ReturnInfo>{

    public boolean detectedSemanticError = false;
    public SymbolTable ST = null;

    public CreateSymbolTableVisitor(SymbolTable _ST){
        super();
        this.ST = _ST;
    }


    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
    public ReturnInfo visit(MainClass n) {
        n.f0.accept(this);
        ReturnInfo r1 = n.f1.accept(this);      // r1 -> main class name
        ST.setMainClassName(r1.name);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        ReturnInfo r11 = n.f11.accept(this);   // r11 -> name of main()'s String[] args variable
        ST.setMainClassArgName(r11.name);
        n.f12.accept(this);
        n.f13.accept(this);
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
        return null;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public ReturnInfo visit(TypeDeclaration n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public ReturnInfo visit(ClassDeclaration n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
    public ReturnInfo visit(ClassExtendsDeclaration n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public ReturnInfo visit(VarDeclaration n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
    public ReturnInfo visit(MethodDeclaration n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        n.f7.accept(this);
        n.f8.accept(this);
        n.f9.accept(this);
        n.f10.accept(this);
        n.f11.accept(this);
        n.f12.accept(this);
        return _ret;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
    */
    public ReturnInfo visit(FormalParameterList n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public ReturnInfo visit(FormalParameter n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> ( FormalParameterTerm() )*
    */
    public ReturnInfo visit(FormalParameterTail n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public ReturnInfo visit(FormalParameterTerm n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public ReturnInfo visit(Type n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public ReturnInfo visit(ArrayType n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> "boolean"
    */
    public ReturnInfo visit(BooleanType n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "int"
    */
    public ReturnInfo visit(IntegerType n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public ReturnInfo visit(Statement n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public ReturnInfo visit(Block n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public ReturnInfo visit(AssignmentStatement n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    public ReturnInfo visit(ArrayAssignmentStatement n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        return _ret;
    }

    /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
    public ReturnInfo visit(IfStatement n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        n.f6.accept(this);
        return _ret;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public ReturnInfo visit(WhileStatement n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public ReturnInfo visit(PrintStatement n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | Clause()
    */
    public ReturnInfo visit(Expression n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
    public ReturnInfo visit(AndExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(CompareExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(PlusExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(MinusExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(TimesExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public ReturnInfo visit(ArrayLookup n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public ReturnInfo visit(ArrayLength n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
    public ReturnInfo visit(MessageSend n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        n.f5.accept(this);
        return _ret;
    }

    /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
    public ReturnInfo visit(ExpressionList n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> ( ExpressionTerm() )*
    */
    public ReturnInfo visit(ExpressionTail n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public ReturnInfo visit(ExpressionTerm n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
    public ReturnInfo visit(Clause n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | BracketExpression()
    */
    public ReturnInfo visit(PrimaryExpression n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public ReturnInfo visit(IntegerLiteral n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "true"
    */
    public ReturnInfo visit(TrueLiteral n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "false"
    */
    public ReturnInfo visit(FalseLiteral n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public ReturnInfo visit(Identifier n) {
        return new ReturnInfo(n.f0.toString());
    }

    /**
    * f0 -> "this"
    */
    public ReturnInfo visit(ThisExpression n) {
        return null;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public ReturnInfo visit(ArrayAllocationExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        n.f4.accept(this);
        return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public ReturnInfo visit(AllocationExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        n.f3.accept(this);
        return _ret;
    }

    /**
    * f0 -> "!"
    * f1 -> Clause()
    */
    public ReturnInfo visit(NotExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public ReturnInfo visit(BracketExpression n) {
        ReturnInfo _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

}
