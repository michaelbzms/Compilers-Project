import syntaxtree.*;
import visitor.GJDepthFirst;

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

class ParameterInfo{
    public String name = null;
    public String supername = null;
    public ParameterInfo(String _name){
        name = _name;
    }
    public ParameterInfo(String _name, String _supername){
        name = _name;
        supername = _supername;
    }
}



public class CreateSymbolTableVisitor extends GJDepthFirst<ReturnInfo, ParameterInfo> {

    public boolean detectedSemanticError = false;
    public String errorMsg = "";
    public SymbolTable ST;

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
    public ReturnInfo visit(MainClass n, ParameterInfo argu)  {
        if (detectedSemanticError) return null;
        n.f0.accept(this, null);
        ReturnInfo r1 = n.f1.accept(this, null);      // r1 -> main class name
        ST.setMainClassName(r1.name);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        n.f5.accept(this, null);
        n.f6.accept(this, null);
        n.f7.accept(this, null);
        n.f8.accept(this, null);
        n.f9.accept(this, null);
        n.f10.accept(this, null);
        ReturnInfo r11 = n.f11.accept(this, null);   // r11 -> name of main()'s String[] args variable
        ST.setMainClassArgName(r11.name);
        n.f12.accept(this, null);
        n.f13.accept(this, null);
        n.f14.accept(this, null);
        n.f15.accept(this, null);
        n.f16.accept(this, null);
        n.f17.accept(this, null);
        return null;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public ReturnInfo visit(ClassDeclaration n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, null);
        ReturnInfo r1 = n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, new ParameterInfo(r1.name));
        n.f4.accept(this, new ParameterInfo(r1.name));
        n.f5.accept(this, null);
        return null;
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
    public ReturnInfo visit(ClassExtendsDeclaration n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, null);
        ReturnInfo r1 = n.f1.accept(this, null);
        n.f2.accept(this, null);
        ReturnInfo r3 = n.f3.accept(this, null);
        if (ST.lookupClass(r3.name) == null){  // if in "class B extends A", A is not defined previously then error
            this.detectedSemanticError = true;
            this.errorMsg = "class " + r3.name + " has not been defined yet in \"class " + r1.name + " extends " + r3.name + "\"";
            return null;
        }

        n.f4.accept(this, null);
        n.f5.accept(this, new ParameterInfo(r1.name, r3.name));
        n.f6.accept(this, new ParameterInfo(r1.name, r3.name));
        n.f7.accept(this, null);
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public ReturnInfo visit(VarDeclaration n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
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
    public ReturnInfo visit(MethodDeclaration n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        n.f5.accept(this, null);
        n.f6.accept(this, null);
        n.f7.accept(this, null);
        n.f8.accept(this, null);
        n.f9.accept(this, null);
        n.f10.accept(this, null);
        n.f11.accept(this, null);
        n.f12.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
    */
    public ReturnInfo visit(FormalParameterList n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public ReturnInfo visit(FormalParameter n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> ( FormalParameterTerm() )*
    */
    public ReturnInfo visit(FormalParameterTail n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public ReturnInfo visit(FormalParameterTerm n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public ReturnInfo visit(Type n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public ReturnInfo visit(ArrayType n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "boolean"
    */
    public ReturnInfo visit(BooleanType n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> "int"
    */
    public ReturnInfo visit(IntegerType n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public ReturnInfo visit(Statement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
    public ReturnInfo visit(Block n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public ReturnInfo visit(AssignmentStatement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
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
    public ReturnInfo visit(ArrayAssignmentStatement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        n.f5.accept(this, null);
        n.f6.accept(this, null);
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
    public ReturnInfo visit(IfStatement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        n.f5.accept(this, null);
        n.f6.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public ReturnInfo visit(WhileStatement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public ReturnInfo visit(PrintStatement n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
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
    public ReturnInfo visit(Expression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
    public ReturnInfo visit(AndExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(CompareExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(PlusExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(MinusExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    public ReturnInfo visit(TimesExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    public ReturnInfo visit(ArrayLookup n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    public ReturnInfo visit(ArrayLength n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
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
    public ReturnInfo visit(MessageSend n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        n.f5.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
    public ReturnInfo visit(ExpressionList n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> ( ExpressionTerm() )*
    */
    public ReturnInfo visit(ExpressionTail n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    public ReturnInfo visit(ExpressionTerm n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
    public ReturnInfo visit(Clause n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
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
    public ReturnInfo visit(PrimaryExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> <INTEGER_LITERAL>
    */
    public ReturnInfo visit(IntegerLiteral n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> "true"
    */
    public ReturnInfo visit(TrueLiteral n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> "false"
    */
    public ReturnInfo visit(FalseLiteral n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, null);
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public ReturnInfo visit(Identifier n, ParameterInfo argu) {
        return new ReturnInfo(n.f0.toString());
    }

    /**
    * f0 -> "this"
    */
    public ReturnInfo visit(ThisExpression n, ParameterInfo argu) {
        return null;
    }

    /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
    public ReturnInfo visit(ArrayAllocationExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        n.f4.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
    public ReturnInfo visit(AllocationExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        n.f3.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "!"
    * f1 -> Clause()
    */
    public ReturnInfo visit(NotExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        return _ret;
    }

    /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
    public ReturnInfo visit(BracketExpression n, ParameterInfo argu) {
        if (detectedSemanticError) return null;
        ReturnInfo _ret=null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return _ret;
    }

}
