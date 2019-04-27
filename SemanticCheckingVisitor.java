import syntaxtree.*;
import visitor.GJDepthFirst;

public class SemanticCheckingVisitor extends GJDepthFirst<VisitorReturnInfo, VisitorParameterInfo> {

    public boolean detectedSemanticError = false;
    public String errorMsg = "";
    public SymbolTable ST;

    public SemanticCheckingVisitor(SymbolTable _ST){
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
    public VisitorReturnInfo visit(MainClass n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        n.f13.accept(this, argu);
        n.f14.accept(this, argu);
        n.f15.accept(this, new VisitorParameterInfo("main", "main"));
        n.f16.accept(this, argu);
        n.f17.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public VisitorReturnInfo visit(TypeDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    public VisitorReturnInfo visit(ClassDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, new VisitorParameterInfo(r1.name, "custom"));    // pass class name
        n.f5.accept(this, argu);
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
    public VisitorReturnInfo visit(ClassExtendsDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, new VisitorParameterInfo(r1.name, "custom"));     // pass class name
        n.f7.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public VisitorReturnInfo visit(VarDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (r0 == null) return null;
        // if custom type check if it exists
        if (r0.type == TypeEnum.CUSTOM && ST.lookupClass(r0.name) == null && !ST.getMainClassName().equals(r0.name)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid type \"" + r0.name + "\" in a variable declaration";
            return null;
        }
        return null;
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
    public VisitorReturnInfo visit(MethodDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;
        if (r1.type == TypeEnum.CUSTOM && ST.lookupClass(r1.name) == null && !ST.getMainClassName().equals(r1.name)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid return type \"" + r1.name + "\" in a method declaration";
            return null;
        }
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, new VisitorParameterInfo(r2.name, argu.name, null));  // pass method name, class name
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> FormalParameterTail()
     */
    public VisitorReturnInfo visit(FormalParameterList n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public VisitorReturnInfo visit(FormalParameter n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;
        if (r0.type == TypeEnum.CUSTOM && ST.lookupClass(r0.name) == null && !ST.getMainClassName().equals(r0.name)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid parameter type \"" + r0.name + "\" in a method declaration";
            return null;
        }
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ( FormalParameterTerm() )*
     */
    public VisitorReturnInfo visit(FormalParameterTail n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public VisitorReturnInfo visit(FormalParameterTerm n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */
    public VisitorReturnInfo visit(Type n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, new VisitorParameterInfo(null, "getType"));  // getType is used in Identifier()'s visit() for custom types
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public VisitorReturnInfo visit(ArrayType n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, null);
        n.f1.accept(this, null);
        n.f2.accept(this, null);
        return new VisitorReturnInfo(TypeEnum.INTARRAY);
    }

    /**
     * f0 -> "boolean"
     */
    public VisitorReturnInfo visit(BooleanType n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.BOOLEAN);
    }

    /**
     * f0 -> "int"
     */
    public VisitorReturnInfo visit(IntegerType n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */
    public VisitorReturnInfo visit(Statement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public VisitorReturnInfo visit(Block n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public VisitorReturnInfo visit(AssignmentStatement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;
        // check if identifier exists on symbol table
        VariableInfo varInfo;
        if ( argu.type.equals("main") ){
            varInfo = ST.lookupMainVariable(r0.name);
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared variable \"" + r0.name + "\" in main";
                return null;
            }
        } else {
            varInfo = ST.lookupVariable(argu.supername, argu.name, r0.name);
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared variable \"" + r0.name + "\" in method \"" + argu.name + "\" of the class \"" + argu.supername + "\"";
                return null;
            }
        }
        n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;

        // TODO: but also subtyping!
        if (r2.type != varInfo.getType() || (varInfo.getType() == TypeEnum.CUSTOM && !r2.name.equals(varInfo.getCustomTypeName()) )){
            this.detectedSemanticError = true;
            this.errorMsg = "Incompatible type: \"" + (r2.type == TypeEnum.CUSTOM ? r2.name : r2.type)  +
                            "\" instead of \"" + (varInfo.getType() == TypeEnum.CUSTOM ? varInfo.getCustomTypeName() : varInfo.getType());
            if ( argu.type.equals("main") ){
                this.errorMsg += "\" in main";
            } else {
                this.errorMsg += "\" in method \"" + argu.name + "\" of the class \"" + argu.supername + "\"";
            }
            return null;
        }

        n.f3.accept(this, argu);
        return null;
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
    public VisitorReturnInfo visit(ArrayAssignmentStatement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return null;
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
    public VisitorReturnInfo visit(IfStatement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    public VisitorReturnInfo visit(WhileStatement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    public VisitorReturnInfo visit(PrintStatement n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
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
    public VisitorReturnInfo visit(Expression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> Clause()
     * f1 -> "&&"
     * f2 -> Clause()
     */
    public VisitorReturnInfo visit(AndExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.BOOLEAN);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(CompareExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.BOOLEAN);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(PlusExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(MinusExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(TimesExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public VisitorReturnInfo visit(ArrayLookup n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public VisitorReturnInfo visit(ArrayLength n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    public VisitorReturnInfo visit(MessageSend n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;
        MethodInfo methodInfo = null;  // TODO: find return type of called method to return as the Expression's type
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        return new VisitorReturnInfo(""/* TODO: find this */, TypeEnum.CUSTOM);
    }

    /**
     * f0 -> Expression()
     * f1 -> ExpressionTail()
     */
    public VisitorReturnInfo visit(ExpressionList n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ( ExpressionTerm() )*
     */
    public VisitorReturnInfo visit(ExpressionTail n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public VisitorReturnInfo visit(ExpressionTerm n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> NotExpression()
     *       | PrimaryExpression()
     */
    public VisitorReturnInfo visit(Clause n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
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
    public VisitorReturnInfo visit(PrimaryExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public VisitorReturnInfo visit(IntegerLiteral n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        return new VisitorReturnInfo(TypeEnum.INTEGER);
    }

    /**
     * f0 -> "true"
     */
    public VisitorReturnInfo visit(TrueLiteral n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        return new VisitorReturnInfo(TypeEnum.BOOLEAN);
    }

    /**
     * f0 -> "false"
     */
    public VisitorReturnInfo visit(FalseLiteral n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        return new VisitorReturnInfo(TypeEnum.BOOLEAN);
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public VisitorReturnInfo visit(Identifier n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        if (argu != null && argu.type.equals("getType")) return new VisitorReturnInfo(n.f0.toString(), TypeEnum.CUSTOM);
        else return new VisitorReturnInfo(n.f0.toString());
    }

    /**
     * f0 -> "this"
     */
    public VisitorReturnInfo visit(ThisExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public VisitorReturnInfo visit(ArrayAllocationExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public VisitorReturnInfo visit(AllocationExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Clause()
     */
    public VisitorReturnInfo visit(NotExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public VisitorReturnInfo visit(BracketExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }


}
