package LLVMCodeGeneration;

import java.util.Map;
import MiniJavaType.*;
import SemanticAnalysis.SemanticChecks;
import SymbolTable.*;
import Util.ExtendedVisitorReturnInfo;
import Util.MyPair;
import Util.VisitorParameterInfo;
import visitor.GJDepthFirst;
import syntaxtree.*;


public class LLVMCodeGeneratingVisitor extends GJDepthFirst<ExtendedVisitorReturnInfo, VisitorParameterInfo> {

    private FileWritter out;
    private final SymbolTable ST;
    private LLVMVarGenerator varGenerator;


    public LLVMCodeGeneratingVisitor(SymbolTable _ST, String outputFilename){
        ST = _ST;
        out = new FileWritter(outputFilename);
        varGenerator = new LLVMVarGenerator();
    }

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public ExtendedVisitorReturnInfo visit(Goal n, VisitorParameterInfo argu) {

        // generate VTable for main and all other classes
        out.emit( "@." + ST.getMainClassName() + "_vtable = global [0 x i8*] []\n");
        for (MyPair<String, ClassInfo> c : ST.getOrderedClasses()){
            out.emit(LLVMCodeGenerating.generateVTableForClass(c.getFirst(), c.getSecond()) + "\n");
        }

        out.emit("\ndeclare i8* @calloc(i32, i32)\n" +
                "declare i32 @printf(i8*, ...)\n" +
                "declare void @exit(i32)\n" +
                "\n" +
                "@_cint = constant [4 x i8] c\"%d\\0a\\00\"\n" +
                "@_cOOB = constant [15 x i8] c\"Out of bounds\\0a\\00\"\n" +
                "define void @print_int(i32 %i) {\n" +
                "    %_str = bitcast [4 x i8]* @_cint to i8*\n" +
                "    call i32 (i8*, ...) @printf(i8* %_str, i32 %i)\n" +
                "    ret void\n" +
                "}\n" +
                "\n" +
                "define void @throw_oob() {\n" +
                "    %_str = bitcast [15 x i8]* @_cOOB to i8*\n" +
                "    call i32 (i8*, ...) @printf(i8* %_str)\n" +
                "    call void @exit(i32 1)\n" +
                "    ret void\n" +
                "}\n\n");

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);

        out.close();

        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "VisitorParameterInfo"_ret
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
    public ExtendedVisitorReturnInfo visit(MainClass n, VisitorParameterInfo argu) {

        out.emit("define i32 @main() {\n");

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
        n.f14.accept(this, new VisitorParameterInfo("main", "main"));
        n.f15.accept(this, new VisitorParameterInfo("main", "main"));
        n.f16.accept(this, argu);
        n.f17.accept(this, argu);

        out.emit("    ret i32 0\n}\n\n");

        return null;
    }

    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public ExtendedVisitorReturnInfo visit(TypeDeclaration n, VisitorParameterInfo argu) {
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
    public ExtendedVisitorReturnInfo visit(ClassDeclaration n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;
        n.f3.accept(this, new VisitorParameterInfo(r1.getName(), "method"));
        n.f4.accept(this, new VisitorParameterInfo(r1.getName(), "method"));
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
    public ExtendedVisitorReturnInfo visit(ClassExtendsDeclaration n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;
        n.f5.accept(this, new VisitorParameterInfo(r1.getName(), "method"));
        n.f6.accept(this, new VisitorParameterInfo(r1.getName(), "method"));
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public ExtendedVisitorReturnInfo visit(VarDeclaration n, VisitorParameterInfo argu) {
        //ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        //ExtendedVisitorReturnInfo r1 = n.f1.accept(this, argu);
        //if (r0 == null || r1 == null) return null;
        //out.emit("    %" + r1.getName() + " = alloca " + r0.getType().getLLVMType() + "\n");
    	// this doesnt really work because it also is used on field declarations
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
    public ExtendedVisitorReturnInfo visit(MethodDeclaration n, VisitorParameterInfo argu) {

        varGenerator.resetCounter();   // set start of local var counter to 0

        n.f1.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r2 == null) return null;

        MethodInfo methodInfo = ST.lookupMethod(argu.getName(), r2.getName());
        if (methodInfo == null) return null;

        out.emit("define " + methodInfo.getReturnType().getLLVMType() + " @" + argu.getName() + "." + r2.getName() + "(i8* %this");

        n.f4.accept(this, new VisitorParameterInfo(r2.getName(), argu.getName(), "method"));

        out.emit(") {\n");

        // allocate stack space for args in correct order //TODO: Do not do this if I use the following loop to allocate space for both local and arguments
        //for ( MyPair<String, VariableInfo> v : methodInfo.getArgList() ){
        //	out.emit("    %" + v.getFirst() + " = alloca " + v.getSecond().getType().getLLVMType() + "\n");
        //}

        // allocate space for arguments and local variables alike (order does not matter since we use these variables to store/load from/to them, right? TODO)
        for ( Map.Entry<String, VariableInfo> v : methodInfo.getVariablesMap().entrySet() ){
        	out.emit("    %" + v.getKey() + " = alloca " + v.getValue().getType().getLLVMType() + "\n");
        }

        //n.f7.accept(this, new VisitorParameterInfo(r2.getName(), argu.getName(), "method"));
        n.f8.accept(this, new VisitorParameterInfo(r2.getName(), argu.getName(), "method"));

        //n.f9.accept(this, argu);
        ExtendedVisitorReturnInfo r10 = n.f10.accept(this, argu);
        if (r10 == null) return null;

        // hardcoded for now: (TODO: fix return of Expression)
        if (r10.getResultVarNameOrConstant() == null && methodInfo.getNumberOfArguments() > 0)
        	r10.setResultVarNameOrConstant("%" + methodInfo.getArgList().get(0).getFirst());

        // TODO: what if return type is an object? -> we have to return a reference
        out.emit("    ret " + methodInfo.getReturnType().getLLVMType() + " " + r10.getResultVarNameOrConstant() + "\n");

        out.emit("}\n\n");

        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> FormalParameterTail()
     */
    public ExtendedVisitorReturnInfo visit(FormalParameterList n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public ExtendedVisitorReturnInfo visit(FormalParameter n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r0 == null || r1 == null) return null;

        out.emit(", " + r0.getType().getLLVMType() + " %" + r1.getName());

        return null;
    }

    /**
     * f0 -> ( FormalParameterTerm() )*
     */
    public ExtendedVisitorReturnInfo visit(FormalParameterTail n, VisitorParameterInfo argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public ExtendedVisitorReturnInfo visit(FormalParameterTerm n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(Type n, VisitorParameterInfo argu) {
        return n.f0.accept(this, new VisitorParameterInfo(null, null, null, "getType"));  // getTypeEnum is used in Identifier()'s visit() for custom types
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public ExtendedVisitorReturnInfo visit(ArrayType n, VisitorParameterInfo argu) {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return new ExtendedVisitorReturnInfo(MiniJavaType.INTARRAY, null);
    }

    /**
     * f0 -> "boolean"
     */
    public ExtendedVisitorReturnInfo visit(BooleanType n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo(MiniJavaType.BOOLEAN, null);
    }

    /**
     * f0 -> "int"
     */
    public ExtendedVisitorReturnInfo visit(IntegerType n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, null);
    }

    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */
    public ExtendedVisitorReturnInfo visit(Statement n, VisitorParameterInfo argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public ExtendedVisitorReturnInfo visit(Block n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(AssignmentStatement n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;

        VariableInfo varInfo = ST.lookupVariable(argu.getSupername(), argu.getName(), r0.getName());
        if (varInfo != null){
            // identifier is a local variable
            String llvmType = varInfo.getType().getLLVMType();
            out.emit("    store " + llvmType + " " + r2.getResultVarNameOrConstant() + ", " + llvmType + "* " + r0.getResultVarNameOrConstant() + "\n");
        } else {
            // (IT COULD ALSO BE A FIELD OF A SUPERCLASS!)
            varInfo = SemanticChecks.checkFieldExists(ST, argu.getSupername(), argu.getName(), r0.getName());
            if (varInfo != null){
                // identifier is a field of "this" object
                //TODO: implement once I implement object allocation
            } else System.err.println("Unknown identifier in assignment?!");  // should not happen cause of semantic checks
        }

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
    public ExtendedVisitorReturnInfo visit(ArrayAssignmentStatement n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        ExtendedVisitorReturnInfo r5 = n.f5.accept(this, argu);
        if (r0 == null || r2 == null || r5 == null) return null;

        String offsetplusone = varGenerator.generateLocalVarName();
        String elemptr = varGenerator.generateLocalVarName();
        out.emit("    " + offsetplusone + " = add i32 " + r2.getResultVarNameOrConstant() + ", 1\n");   // negate length in 0 pos
        out.emit("    " + elemptr + " = getelementptr i32, i32* " + r0.getResultVarNameOrConstant() + ", i32 " + offsetplusone + "\n");
        out.emit("    store i32 " + r5.getResultVarNameOrConstant() + ", i32* " + elemptr + "\n");

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
    public ExtendedVisitorReturnInfo visit(IfStatement n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(WhileStatement n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(PrintStatement n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(Expression n, VisitorParameterInfo argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> Clause()
     * f1 -> "&&"
     * f2 -> Clause()
     */
    public ExtendedVisitorReturnInfo visit(AndExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;

        // TODO: Short-circuiting!
        // for now:

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = and i1 " + r0.getResultVarNameOrConstant() + ", " + r2.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.BOOLEAN, res);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public ExtendedVisitorReturnInfo visit(CompareExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = icmp slt i32 " + r0.getResultVarNameOrConstant() + ", " + r2.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.BOOLEAN, res);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public ExtendedVisitorReturnInfo visit(PlusExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r1 = n.f2.accept(this, argu);
        if (r0 == null || r1 == null) return null;

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = add i32 " + r0.getResultVarNameOrConstant() + ", " + r1.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, res);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public ExtendedVisitorReturnInfo visit(MinusExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r1 = n.f2.accept(this, argu);
        if (r0 == null || r1 == null) return null;

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = sub i32 " + r0.getResultVarNameOrConstant() + ", " + r1.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, res);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public ExtendedVisitorReturnInfo visit(TimesExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r1 = n.f2.accept(this, argu);
        if (r0 == null || r1 == null) return null;

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = mul i32 " + r0.getResultVarNameOrConstant() + ", " + r1.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, res);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public ExtendedVisitorReturnInfo visit(ArrayLookup n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        ExtendedVisitorReturnInfo r2 = n.f2.accept(this, argu);
        if (r0 == null || r2 == null) return null;

        String offsetplusone = varGenerator.generateLocalVarName();
        String elemptr = varGenerator.generateLocalVarName();
        String element = varGenerator.generateLocalVarName();
        out.emit("    " + offsetplusone + " = add i32 " + r2.getResultVarNameOrConstant() + ", 1\n");   // negate length in 0 pos
        out.emit("    " + elemptr + " = getelementptr i32, i32* " + r0.getResultVarNameOrConstant() + ", i32 " + offsetplusone + "\n");
        out.emit("    " + element + " = load i32, i32* " + elemptr + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, element);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public ExtendedVisitorReturnInfo visit(ArrayLength n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r0 = n.f0.accept(this, argu);
        if (r0 == null) return null;

        String len = varGenerator.generateLocalVarName();
        out.emit("    " + len + " = load i32, i32* " + r0.getResultVarNameOrConstant() + "\n");

        return new ExtendedVisitorReturnInfo(MiniJavaType.INTEGER, len);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    public ExtendedVisitorReturnInfo visit(MessageSend n, VisitorParameterInfo argu) {
        //TODO: Method Call
        ExtendedVisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Expression()
     * f1 -> ExpressionTail()
     */
    public ExtendedVisitorReturnInfo visit(ExpressionList n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ( ExpressionTerm() )*
     */
    public ExtendedVisitorReturnInfo visit(ExpressionTail n, VisitorParameterInfo argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public ExtendedVisitorReturnInfo visit(ExpressionTerm n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> NotExpression()
     *       | PrimaryExpression()
     */
    public ExtendedVisitorReturnInfo visit(Clause n, VisitorParameterInfo argu) {
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
    public ExtendedVisitorReturnInfo visit(PrimaryExpression n, VisitorParameterInfo argu) {
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public ExtendedVisitorReturnInfo visit(IntegerLiteral n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo(n.f0.toString(), MiniJavaType.INTEGER, n.f0.beginLine , n.f0.toString());
    }

    /**
     * f0 -> "true"
     */
    public ExtendedVisitorReturnInfo visit(TrueLiteral n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo("true", MiniJavaType.BOOLEAN, n.f0.beginLine, "1");
    }

    /**
     * f0 -> "false"
     */
    public ExtendedVisitorReturnInfo visit(FalseLiteral n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo("false", MiniJavaType.BOOLEAN, n.f0.beginLine, "0");
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public ExtendedVisitorReturnInfo visit(Identifier n, VisitorParameterInfo argu) {
        if (argu != null && argu.getPurpose() != null && argu.getPurpose().equals("getType"))
            return new ExtendedVisitorReturnInfo(n.f0.toString(), new MiniJavaType(TypeEnum.CUSTOM, n.f0.toString()), n.f0.beginLine, null);
        else
            return new ExtendedVisitorReturnInfo(n.f0.toString(), null, n.f0.beginLine, "%" + n.f0.toString());  // in case we want a variable
    }

    /**
     * f0 -> "this"
     */
    public ExtendedVisitorReturnInfo visit(ThisExpression n, VisitorParameterInfo argu) {
        return new ExtendedVisitorReturnInfo("this", new MiniJavaType(TypeEnum.CUSTOM, argu.getSupername()), n.f0.beginLine, "%this");
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public ExtendedVisitorReturnInfo visit(ArrayAllocationExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r3 = n.f3.accept(this, argu);
        if (r3 == null) return null;

        String lenplusone = varGenerator.generateLocalVarName();
        String arr = varGenerator.generateLocalVarName();
        out.emit("    " + lenplusone + " = add i32 " + r3.getResultVarNameOrConstant() + ", 1\n");
        out.emit("    " + arr + " = call i8* @calloc(i32 4, i32 " + lenplusone + ")\n");
        // (!) Store length of array at its first element - real elements start from 1... (REMEMBER)
        out.emit("    store i32 " + r3.getResultVarNameOrConstant() + ", i32* " + arr + "\n");

        ExtendedVisitorReturnInfo res = new ExtendedVisitorReturnInfo(MiniJavaType.INTARRAY, arr);
        res.setAlloced(true);
        return res;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public ExtendedVisitorReturnInfo visit(AllocationExpression n, VisitorParameterInfo argu) {
        // TODO: Object allocation
        ExtendedVisitorReturnInfo _ret=null;
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
    public ExtendedVisitorReturnInfo visit(NotExpression n, VisitorParameterInfo argu) {
        ExtendedVisitorReturnInfo r1 = n.f1.accept(this, argu);
        if (r1 == null) return null;

        String res = varGenerator.generateLocalVarName();
        out.emit("    " + res + " = xor i1 " + r1.getResultVarNameOrConstant() + ", 1\n");  // xor with 1 is "not"

        return new ExtendedVisitorReturnInfo(MiniJavaType.BOOLEAN, res);
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public ExtendedVisitorReturnInfo visit(BracketExpression n, VisitorParameterInfo argu) {
        //TODO: Do I need to emit parentheses? -> I don't thunk so, the order of computation will be correct due to dfsing
        return n.f1.accept(this, argu);
    }

}
