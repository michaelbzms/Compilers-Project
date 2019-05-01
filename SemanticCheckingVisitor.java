import MiniJavaType.*;
import SymbolTable.*;
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
        //TODO: remove unncessary calls to terminals?
        //n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        //n.f2.accept(this, argu);
        //n.f3.accept(this, argu);
        //n.f4.accept(this, argu);
        //n.f5.accept(this, argu);
        //n.f6.accept(this, argu);
        //n.f7.accept(this, argu);
        //n.f8.accept(this, argu);
        //n.f9.accept(this, argu);
        //n.f10.accept(this, argu);
        //n.f11.accept(this, argu);  //TODO: ignore this?
        //n.f12.accept(this, argu);
        //n.f13.accept(this, argu);
        n.f14.accept(this, null);
        n.f15.accept(this, new VisitorParameterInfo("main", "main"));
        //n.f16.accept(this, argu);
        //n.f17.accept(this, argu);
        return null;
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
        //n.f0.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, null);
        if (r1 == null) return null;
        //n.f2.accept(this, argu);
        n.f3.accept(this, null);
        n.f4.accept(this, new VisitorParameterInfo(r1.getName(), "custom"));    // pass class name
        //n.f5.accept(this, argu);
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
    public VisitorReturnInfo visit(ClassExtendsDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo _ret=null;
        //n.f0.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, null);
        if (r1 == null) return null;
        //n.f2.accept(this, argu);
        //n.f3.accept(this, argu);  // this is checked by previous visitor
        //n.f4.accept(this, argu);
        n.f5.accept(this, null);
        n.f6.accept(this, new VisitorParameterInfo(r1.getName(),"custom"));     // pass class name
        //n.f7.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public VisitorReturnInfo visit(VarDeclaration n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, null);
        if (r0 == null) return null;

        //TODO: remove? Duplicates are checked by previous visitor
        //n.f1.accept(this, argu);
        //n.f2.accept(this, null);

        // if custom type then check that it exists
        if (r0.getType().getTypeEnum() == TypeEnum.CUSTOM && ST.lookupClass(r0.getType().getCustomTypeName()) == null ){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid type \"" + r0.getName() + "\" in a variable declaration";
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
        //n.f0.accept(this, argu);
        VisitorReturnInfo r1 = n.f1.accept(this, null);
        if (r1 == null) return null;
        if (r1.getType().getTypeEnum() == TypeEnum.CUSTOM && ST.lookupClass(r1.getName()) == null && !ST.getMainClassName().equals(r1.getName())){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid return type \"" + r1.getName() + "\" in a method declaration";
            return null;
        }
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;
        //n.f3.accept(this, argu);
        n.f4.accept(this, null);
        //n.f5.accept(this, argu);
        //n.f6.accept(this, argu);
        n.f7.accept(this, null);
        n.f8.accept(this, new VisitorParameterInfo(r2.getName(), argu.getName(), "method"));   // pass method name, class name
        //n.f9.accept(this, argu);
        VisitorReturnInfo r10 = n.f10.accept(this, new VisitorParameterInfo(r2.getName(), argu.getName(), "method"));  // pass method name, class name
        if (r10 == null) return null;

        // check that expression is of the method's return type
        MethodInfo methodInfo = SemanticChecks.checkMethodExistsForCustomType(ST, argu.getName(), r2.getName());
        if (methodInfo == null) { System.err.println("Warning: Missing method from SymbolTable.SymbolTable?"); return null; }
        if (!SemanticChecks.checkType(ST, r10.getType(), methodInfo.getReturnType())){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid return type of the method \"" + r2.getName() + "\": " + r10.getType().getDebugInfo() + " instead of " + methodInfo.getReturnType().getDebugInfo();
            return null;
        }

        //n.f11.accept(this, argu);
        //n.f12.accept(this, argu);
        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> FormalParameterTail()
     */
    public VisitorReturnInfo visit(FormalParameterList n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public VisitorReturnInfo visit(FormalParameter n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;

        // check if type exists
        if (r0.getType().getTypeEnum() == TypeEnum.CUSTOM && ST.lookupClass(r0.getType().getCustomTypeName()) == null){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid parameter type \"" + r0.getName() + "\" in a method declaration";
            return null;
        }

        n.f1.accept(this, argu);
        return null;
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
        //n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return null;
    }

    /**
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */
    public VisitorReturnInfo visit(Type n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return n.f0.accept(this, new VisitorParameterInfo(null, null, null, "getType"));  // getTypeEnum is used in Identifier()'s visit() for custom types
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
        return new VisitorReturnInfo(MiniJavaType.INTARRAY);
    }

    /**
     * f0 -> "boolean"
     */
    public VisitorReturnInfo visit(BooleanType n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(MiniJavaType.BOOLEAN);
    }

    /**
     * f0 -> "int"
     */
    public VisitorReturnInfo visit(IntegerType n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(MiniJavaType.INTEGER);
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
        //n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        //n.f2.accept(this, argu);
        return null;
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
        if (argu == null) {
            System.err.println("Missing parameter for assignment");
            return null;
        } else if ( argu.getType().equals("main") ){
            varInfo = ST.lookupMainVariable(r0.getName());
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared variable \"" + r0.getName() + "\" in main";
                return null;
            }
        } else {
            varInfo = SemanticChecks.checkVariableOrFieldExists(ST,  argu.getSupername(), argu.getName(), r0.getName());
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared variable \"" + r0.getName() + "\" in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
                return null;
            }
        }

        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;

        // check if expression is of the correct type
        if ( !SemanticChecks.checkType(ST, r2.getType(), varInfo.getType()) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Incompatible assignment type: \"" + r2.getType().getDebugInfo()  + "\" instead of \"" + varInfo.getType().getDebugInfo();
            if ( argu.getType().equals("main") ){
                this.errorMsg += "\" in main";
            } else {
                this.errorMsg += "\" in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
            }
            this.errorMsg += " for the variable \"" + r0.getName() +"\"";
            return null;
        }

        //n.f3.accept(this, argu);
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

        // check if array-variable exists on the SymbolTable.SymbolTable
        VariableInfo varInfo;
        if (argu == null) {
            System.err.println("Missing parameter for assignment");
            return null;
        } else if ( argu.getType().equals("main") ){
            varInfo = ST.lookupMainVariable(r0.getName());
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared array-variable \"" + r0.getName() + "\" in main";
                return null;
            }
        } else {
            varInfo = SemanticChecks.checkVariableOrFieldExists(ST, argu.getSupername(), argu.getName(), r0.getName());
            if (varInfo == null) {
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared array-variable \"" + r0.getName() + "\" in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
                return null;
            }
        }

        // check if variable is an array-variable
        if (varInfo.getType().getTypeEnum() != TypeEnum.INTARRAY){
            this.detectedSemanticError = true;
            this.errorMsg = "Use of non-array variable \"" + r0.getName() + "\" as an array variable";
            return null;
        }

        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        //n.f3.accept(this, argu);
        //n.f4.accept(this, argu);
        VisitorReturnInfo r5 = n.f5.accept(this, argu);
        //n.f6.accept(this, argu);
        if (r2 == null || r5 == null) return null;

        // check if index type and value type are integers
        if ( !SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER) ) {
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid (not an integer) index type \"" + r2.getType() + "\" in array assignment of \"" + r0.getName() + "\"";
            return null;
        } else if ( !SemanticChecks.checkType(ST, r5.getType(), MiniJavaType.INTEGER) ) {
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid (not an integer) value type \"" + r2.getType() + "\" in array assignment of \"" + r0.getName() + "\"";
            return null;
        }

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
        //n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;

        // check condition is BOOLEAN
        if (!SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.BOOLEAN)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid (not boolean) condition type in if statement";
            return null;
        }

        //n.f3.accept(this, argu);
        VisitorReturnInfo r4 = n.f4.accept(this, argu);
        if (r4 == null) return null;
        //n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return null;
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
        //n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;

        // check condition is BOOLEAN
        if (!SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.BOOLEAN)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid (not boolean) condition type in while statement";
            return null;
        }

        //n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return null;
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
        //n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        //n.f3.accept(this, argu);
        //n.f4.accept(this, argu);
        return null;
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
        return n.f0.accept(this, new VisitorParameterInfo(argu.getName(), argu.getSupername(), argu.getType(), "getVariableType"));  // if it comes to "<identifier>" consider it a variable
    }

    /**
     * f0 -> Clause()
     * f1 -> "&&"
     * f2 -> Clause()
     */
    public VisitorReturnInfo visit(AndExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;
        if ( !SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.BOOLEAN) ||
             !SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.BOOLEAN) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Bad operands for operator \"&&\" (must be booleans)";
            return null;
        }
        return new VisitorReturnInfo(MiniJavaType.BOOLEAN);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(CompareExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;
        if ( !SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTEGER) ||
             !SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Bad operands for operator \"<\" (must be integers)";
            return null;
        }
        return new VisitorReturnInfo(MiniJavaType.BOOLEAN);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(PlusExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;
        if ( !SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTEGER) ||
             !SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Bad operands for operator \"+\" (must be integers)";
            return null;
        }
        return new VisitorReturnInfo(MiniJavaType.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(MinusExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;
        if ( !SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTEGER) ||
            ! SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Bad operands for operator \"-\" (must be integers)";
            return null;
        }
        return new VisitorReturnInfo(MiniJavaType.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public VisitorReturnInfo visit(TimesExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;
        if ( !SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTEGER) ||
             !SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER) ){
            this.detectedSemanticError = true;
            this.errorMsg = "Bad operands for operator \"*\" (must be integers)";
            return null;
        }
        return new VisitorReturnInfo(MiniJavaType.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public VisitorReturnInfo visit(ArrayLookup n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        VisitorReturnInfo r2 = n.f2.accept(this, argu);
        //n.f3.accept(this, argu);
        if (r0 == null || r2 == null) return null;

        // check that r0 is of type INTARRAY and that r2 is of type INTEGER
        if (!SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTARRAY)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid array lookup on non-array primary expression in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
            return null;
        } else if (!SemanticChecks.checkType(ST, r2.getType(), MiniJavaType.INTEGER)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid index primary expression (not integer) on array lookup in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
            return null;
        }

        return new VisitorReturnInfo(MiniJavaType.INTEGER);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public VisitorReturnInfo visit(ArrayLength n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;

        // check that r0 is of type INTARRAY
        if (!SemanticChecks.checkType(ST, r0.getType(), MiniJavaType.INTARRAY)){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid array length query on non-array primary expression in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
            return null;
        }

        //n.f1.accept(this, argu);
        //n.f2.accept(this, argu);
        if (detectedSemanticError) return null;
        return new VisitorReturnInfo(MiniJavaType.INTEGER);
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
        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        //n.f1.accept(this, null);
        VisitorReturnInfo r2 = n.f2.accept(this, null);
        if (r0 == null || r2 == null) return null;

        String classNameToCall;
        String methodNameToCall;
        MethodInfo methodInfo;
        if (r0.getType() != null && (r0.getType().getTypeEnum() != TypeEnum.CUSTOM)) {
            this.detectedSemanticError = true;
            this.errorMsg = "Calling a method on non-object primary expression " + (r0.getName() != null ? " \"" + r0.getName() + "\"" : "");
            return null;
        }
        else if (r0.getName() != null && r0.getName().equals("this")) {
            // check that method exists for "this"
            if (argu.getType() == "main") {
                this.detectedSemanticError = true;
                this.errorMsg = "Main class cannot call methods with \"this\" as it cannot have any such methods";
                return null;
            } else if (argu.getType() == "method") {
                methodInfo = SemanticChecks.checkMethodExistsForCustomType(ST, argu.getSupername(), r2.getName());
                if (methodInfo == null) {
                    this.detectedSemanticError = true;
                    this.errorMsg = "Class \"" + argu.getSupername() + "\" does not have a method \"" + r2.getName() + "\"";
                    return null;
                }
                classNameToCall = argu.getSupername();
                methodNameToCall = r2.getName();
            } else {
                System.err.println("Warning: invalid arguments");
                return null;
            }
        }
        else if (r0.getName() != null && r0.getName().equals("methodCall")) {
            // check that methodCall type has that method
            if (r0.getType().getTypeEnum() != TypeEnum.CUSTOM){
                this.detectedSemanticError = true;
                this.errorMsg = "Method called on a primitive type \"" + r0.getType().getDebugInfo() + "\"";
                return null;
            } else {
                methodInfo = SemanticChecks.checkMethodExistsForCustomType(ST, r0.getType().getCustomTypeName(), r2.getName());
                if (methodInfo == null){
                    this.detectedSemanticError = true;
                    this.errorMsg = "Custom type \"" + r0.getType().getCustomTypeName() + "\" does not have a method \"" + r2.getName() + "\"";
                    return null;
                }
                classNameToCall = r0.getType().getCustomTypeName();
                methodNameToCall = r2.getName();
            }
        }
        else if (r0.getName() != null && r0.isAlloced()) {
            // check if allocation type has that method
            if (r0.getType().getTypeEnum() != TypeEnum.CUSTOM){   // only possible for INTARRAY type
                this.detectedSemanticError = true;
                this.errorMsg = "Method called on newly allocated primitive type \"" + r0.getType().getDebugInfo() + "\"";
                return null;
            } else {
                methodInfo = SemanticChecks.checkMethodExistsForCustomType(ST, r0.getType().getCustomTypeName(), r2.getName());
                if (methodInfo == null){
                    this.detectedSemanticError = true;
                    this.errorMsg = "Custom type \"" + r0.getType().getCustomTypeName() + "\" does not have a method \"" + r2.getName() + "\"";
                    return null;
                }
                classNameToCall = r0.getType().getCustomTypeName();
                methodNameToCall = r2.getName();
            }
        }
        else if (r0.getName() != null){
            // check that variable exists in context
            VariableInfo varInfo;
            if ("main".equals(argu.getType())){
                varInfo = ST.lookupMainVariable(r0.getName());
                if (varInfo == null) {
                    this.detectedSemanticError = true;
                    this.errorMsg = "Use of undeclared variable \"" + r0.getName() + "\" in a method call in main";
                    return null;
                }
            } else if ("method".equals(argu.getType())) {
                varInfo = SemanticChecks.checkVariableOrFieldExists(ST, argu.getSupername(), argu.getName(), r0.getName());
                if (varInfo == null) {
                    this.detectedSemanticError = true;
                    this.errorMsg = "Use of undeclared variable \"" + r0.getName() + "\" in a method call in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
                    return null;
                }
            } else { System.err.println("Warning: invalid arguments: argu.getTypeEnum() = " + argu.getType()); return null; }
            // and that its class has that method
            if (varInfo.getType().getTypeEnum() != TypeEnum.CUSTOM){
                this.detectedSemanticError = true;
                this.errorMsg = "Calling a method on non-object " + (r0.getName() != null ? " \"" + r0.getName() + "\"" : "");
                return null;
            }
            methodInfo = SemanticChecks.checkMethodExistsForCustomType(ST, varInfo.getType().getCustomTypeName(), r2.getName());
            if (methodInfo == null){
                this.detectedSemanticError = true;
                this.errorMsg = "Class \"" + varInfo.getType().getCustomTypeName() + "\" does not have a method \"" + r2.getName() + "\"";
                return null;
            }
            classNameToCall = varInfo.getType().getCustomTypeName();
            methodNameToCall = r2.getName();
        }
        else {
            System.err.println("Warning: Unexpected behaviour of method call");
            return null;
        }

        //n.f3.accept(this, argu);

        /// DEBUG ///
        if (classNameToCall == null){
            System.err.println("NULL className!");
        } else if (ST.lookupClass(classNameToCall) == null){
            System.err.println("className is not the name of a class!");
            throw new NullPointerException();
        } else if (ST.lookupMethod(classNameToCall, methodNameToCall) == null){
            System.err.println("methodName does not exist in existing class!");
            throw new NullPointerException();
        }

        n.f4.accept(this, new ExtendedVisitorParameterInfo(argu.getSupername(), argu.getName(), classNameToCall, methodNameToCall, argu.getType()));
        //n.f5.accept(this, argu);
        return new VisitorReturnInfo("methodCall", methodInfo.getReturnType());
    }


    /**
     * f0 -> Expression()
     * f1 -> ExpressionTail()
     */
    public VisitorReturnInfo visit(ExpressionList n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;

        // TODO: CHECK DIS

        /// DEBUG ///
        if (argu.getClassNameToCall() == null || argu.getMethodNameToCall() == null){
            System.err.println("Warning: ExpressionList not given class and methods to call!");
            throw new NullPointerException();
        }

        VisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;

        // check that expression is of the correct type
        if (ST.getNumberOfArguments(argu.getClassNameToCall(), argu.getMethodNameToCall()) > 0) {
            VariableInfo argInfo = ST.lookupArgumentAtPos(argu.getClassNameToCall(), argu.getMethodNameToCall(), 0);
            if (!SemanticChecks.checkType(ST, r0.getType(), argInfo.getType())) {
                this.detectedSemanticError = true;
                this.errorMsg = "Invalid parameter type in call for method \"" + argu.getMethodNameToCall() + "\" of the class \"" + argu.getClassNameToCall();
                this.errorMsg += "\" at pos " + argu.getArgNum() + " : expected " + argInfo.getType().getDebugInfo() + " but got " + r0.getType().getDebugInfo();
                return null;
            }
        }

        VisitorParameterInfo param = new ExtendedVisitorParameterInfo(argu, 1, argu.getClassNameToCall(), argu.getMethodNameToCall());
        n.f1.accept(this, param);

        // check that all arguments have been covered
        if (param.getArgNum() < ST.getNumberOfArguments(argu.getClassNameToCall(), argu.getMethodNameToCall())){
            this.detectedSemanticError = true;
            this.errorMsg = "Less parameters than expected in call for method \"" + argu.getMethodNameToCall() + "\" of the class \"" + argu.getClassNameToCall() + "\"";
            return null;
        }

        return null;
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
        //n.f0.accept(this, null);
        VisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;

        // check that expression is of the correct type
        VariableInfo argInfo = ST.lookupArgumentAtPos(argu.getClassNameToCall(), argu.getMethodNameToCall(), argu.getArgNum());
        if (argInfo == null) {
            this.detectedSemanticError = true;
            this.errorMsg = "More parameters than expected in call for method \"" + argu.getMethodNameToCall() + "\" of the class \"" + argu.getClassNameToCall() + "\"";
            return null;
        } else if (!SemanticChecks.checkType(ST, r1.getType(), argInfo.getType())){
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid parameter type in call for method \"" + argu.getMethodNameToCall() + "\" of the class \"" + argu.getClassNameToCall() + "\"";
            this.errorMsg += " at pos " + argu.getArgNum() + " : expected " + argInfo.getType().getDebugInfo() + " but got " + r1.getType().getDebugInfo();
            return null;
        }
        argu.nextArg();    // augment argNum for next check (should work if we are DFSing, right? TODO: check)

        return null;
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
        //n.f0.accept(this, null);
        return new VisitorReturnInfo(n.f0.toString(), MiniJavaType.INTEGER);
    }

    /**
     * f0 -> "true"
     */
    public VisitorReturnInfo visit(TrueLiteral n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        //n.f0.accept(this, null);
        return new VisitorReturnInfo("true", MiniJavaType.BOOLEAN);
    }

    /**
     * f0 -> "false"
     */
    public VisitorReturnInfo visit(FalseLiteral n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        //n.f0.accept(this, null);
        return new VisitorReturnInfo("false", MiniJavaType.BOOLEAN);
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public VisitorReturnInfo visit(Identifier n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        if (argu != null && argu.getPurpose() != null && argu.getPurpose().equals("getType")) {
            return new VisitorReturnInfo(n.f0.toString(), new MiniJavaType(TypeEnum.CUSTOM, n.f0.toString()));
        }
        else if (argu != null && argu.getPurpose() != null && argu.getPurpose().equals("getVariableType") && argu.getName() != null && argu.getSupername() != null){
            VariableInfo varInfo = SemanticChecks.checkVariableOrFieldExists(ST, argu.getSupername(), argu.getName(), n.f0.toString());
             if (varInfo != null){
                return new VisitorReturnInfo(n.f0.toString(), varInfo.getType());
            } else {
                // TODO: check again
                this.detectedSemanticError = true;
                this.errorMsg = "Use of undeclared variable \"" + n.f0.toString() + "\" in expression";
                if (argu.getSupername() != null && argu.getName() != null){
                    this.errorMsg += " in method \"" + argu.getName() + "\" of the class \"" + argu.getSupername() + "\"";
                }
                return null;
            }
        }
        else return new VisitorReturnInfo(n.f0.toString());
    }

    /**
     * f0 -> "this"
     */
    public VisitorReturnInfo visit(ThisExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        n.f0.accept(this, null);
        return new VisitorReturnInfo("this", new MiniJavaType(TypeEnum.CUSTOM, argu.getSupername()));  // this is an object of the current class //TODO: right?
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
        //n.f0.accept(this, null);
        //n.f1.accept(this, null);
        //n.f2.accept(this, null);
        VisitorReturnInfo r3 = n.f3.accept(this, argu);
        if (r3 == null) return null;

        // check that r3 is of type INTEGER
        if ( !SemanticChecks.checkType(ST, r3.getType(), MiniJavaType.INTEGER) ) {
            this.detectedSemanticError = true;
            this.errorMsg = "Invalid array size (not an integer) in an array allocation";
            return null;
        }

        //n.f4.accept(this, null);

        VisitorReturnInfo res = new VisitorReturnInfo("new int[" + r3.getName() + "]", MiniJavaType.INTARRAY);
        res.setAlloced(true);
        return res;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public VisitorReturnInfo visit(AllocationExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        //n.f0.accept(this, null);
        VisitorReturnInfo r1 = n.f1.accept(this, new VisitorParameterInfo(null, null, null, "getType"));

        // check if it is a custom type and that it exists
        if ( r1.getType().getTypeEnum() == TypeEnum.CUSTOM && ST.lookupClass(r1.getType().getCustomTypeName()) == null ){
            this.detectedSemanticError = true;
            this.errorMsg = "Tried to allocate an object of a non-existant custom type: \"" + r1.getType().getCustomTypeName() + "\"";
            return null;
        } else if (r1.getType().getTypeEnum() != TypeEnum.CUSTOM ){
            //TODO: is this an error? -> probably
            this.detectedSemanticError = true;
            this.errorMsg = "Tried to allocate an object of a primitive data type: \"" + r1.getType().getTypeEnum() + "\"";
            return null;
        }

        //n.f2.accept(this, null);
        //n.f3.accept(this, null);
        r1.setAlloced(true);
        return r1;
    }

    /**
     * f0 -> "!"
     * f1 -> Clause()
     */
    public VisitorReturnInfo visit(NotExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        //n.f0.accept(this, null);
        return n.f1.accept(this, argu);
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public VisitorReturnInfo visit(BracketExpression n, VisitorParameterInfo argu) {
        if (detectedSemanticError) return null;
        //n.f0.accept(this, null);
        //n.f2.accept(this, null);
        return n.f1.accept(this, argu);
    }


}
