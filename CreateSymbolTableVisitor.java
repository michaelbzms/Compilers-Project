import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.Pair;


private class ReturnInfo {
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
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    public ReturnInfo visit(Goal n) {
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return null;
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
        n.f12.accept(this);
        n.f13.accept(this);
        n.f14.accept(this);
        n.f15.accept(this);
        n.f16.accept(this);
        n.f17.accept(this);
        // insert main class info to ST (only need to know its class's name and String[] args variable name)
        ST.insert(r1.name, new ClassInfo(r11.name));
        return ;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public R visit(TypeDeclaration n) {
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
    public R visit(ClassDeclaration n) {
        R _ret=null;
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
    public R visit(ClassExtendsDeclaration n) {
        R _ret=null;
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
    public R visit(VarDeclaration n) {
        R _ret=null;
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
    public R visit(MethodDeclaration n) {
        R _ret=null;
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
    public R visit(FormalParameterList n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public R visit(FormalParameter n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> ( FormalParameterTerm() )*
    */
    public R visit(FormalParameterTail n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public R visit(FormalParameterTerm n) {
        R _ret=null;
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
    public R visit(Type n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public R visit(ArrayType n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

    /**
    * f0 -> "boolean"
    */
    public R visit(BooleanType n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "int"
    */
    public R visit(IntegerType n) {
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
    public R visit(Statement n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public R visit(Block n) {
        R _ret=null;
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
    public R visit(AssignmentStatement n) {
        R _ret=null;
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
    public R visit(ArrayAssignmentStatement n) {
        R _ret=null;
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
    public R visit(IfStatement n) {
        R _ret=null;
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
    public R visit(WhileStatement n) {
        R _ret=null;
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
    public R visit(PrintStatement n) {
        R _ret=null;
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
    public R visit(Expression n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
    public R visit(AndExpression n) {
        R _ret=null;
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
    public R visit(CompareExpression n) {
        R _ret=null;
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
    public R visit(PlusExpression n) {
        R _ret=null;
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
    public R visit(MinusExpression n) {
        R _ret=null;
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
    public R visit(TimesExpression n) {
        R _ret=null;
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
    public R visit(ArrayLookup n) {
        R _ret=null;
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
    public R visit(ArrayLength n) {
        R _ret=null;
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
    public R visit(MessageSend n) {
        R _ret=null;
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
    public R visit(ExpressionList n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> ( ExpressionTerm() )*
    */
    public R visit(ExpressionTail n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public R visit(ExpressionTerm n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
    public R visit(Clause n) {
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
    public R visit(PrimaryExpression n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public R visit(IntegerLiteral n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "true"
    */
    public R visit(TrueLiteral n) {
        return n.f0.accept(this);
    }

    /**
    * f0 -> "false"
    */
    public R visit(FalseLiteral n) {
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
    public R visit(ThisExpression n) {
        return new ReturnInfo(false);
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public R visit(ArrayAllocationExpression n) {
        R _ret=null;
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
    public R visit(AllocationExpression n) {
        R _ret=null;
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
    public R visit(NotExpression n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public R visit(BracketExpression n) {
        R _ret=null;
        n.f0.accept(this);
        n.f1.accept(this);
        n.f2.accept(this);
        return _ret;
    }

}
