import MiniJavaType.MiniJavaType;

public class SemanticErrors {

    private static String giveContext(int beginLine){
        return "in main():" + (beginLine > 0 ? " line " + beginLine : "") + "\n    ";
    }

    private static String giveContext(int beginLine, String className){
        return "in class \"" + className + "\":" + (beginLine > 0 ? " line " + beginLine : "") + "\n    ";
    }

    private static String giveContext(int beginLine, String className, String methodName){
        return "in method \"" + methodName + "\" of the class \"" + className + "\":" + (beginLine > 0 ? " line " + beginLine : "") + "\n    ";
    }

    ////////////////////////////////////////////////////////////////////////////////

    public static String useOfUndeclaredVariable(String varName, int beginLine){
        return giveContext(beginLine) + "Use of undeclared variable \"" + varName + "\"";
    }

    public static String useOfUndeclaredVariable(String className, String methodName, String varName, int beginLine){
        return giveContext(beginLine, className, methodName) + "Use of undeclared variable \"" + varName + "\"";
    }

    public static String useAsAnArrayOfNotArray(String varName, int beginLine){
        return giveContext(beginLine) + "Use of non-array variable \"" + varName + "\" as an array";
    }

    public static String useAsAnArrayOfNotArray(String className, String methodName, String varName, int beginLine){
        return giveContext(beginLine, className, methodName) + "Use of non-array variable \"" + varName + "\" as an array";
    }

    public static String expectedInteger(String situation, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine) + "Expected INTEGER for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String expectedInteger(String className, String methodName, String situation, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine, className, methodName) + "Expected INTEGER for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String expectedBoolean(String situation, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine) + "Expected BOOLEAN for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String expectedBoolean(String className, String methodName, String situation, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine, className, methodName) + "Expected BOOLEAN for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String expectedCertainType(String situation, MiniJavaType expectedType, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine) + "Expected " + expectedType.getDebugInfo() + " for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String expectedCertainType(String className, String methodName, String situation, MiniJavaType expectedType, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine, className, methodName) + "Expected " + expectedType.getDebugInfo() + " for " + situation + " but instead got " + givenType.getDebugInfo();
    }

    public static String nonExistantType(String situation, String givenTypeStr, int beginLine){
        return giveContext(beginLine) + "Type \"" + givenTypeStr  + "\" given for " + situation + " does not exist";
    }

    public static String nonExistantType(String className, String situation, String givenTypeStr, int beginLine){
        return giveContext(beginLine, className) + "Type \"" + givenTypeStr  + "\" given for " + situation + " does not exist";
    }

    public static String nonExistantType(String className, String methodName, String situation, String givenTypeStr, int beginLine){
        return giveContext(beginLine, className, methodName) + "Type \"" + givenTypeStr  + "\" given for " + situation + " does not exist";
    }

    public static String illegalAllocType(MiniJavaType givenType, int beginLine){
        return giveContext(beginLine) + "Tried to allocate an object of primitive type " + givenType.getDebugInfo();
    }

    public static String illegalAllocType(String className, String methodName, MiniJavaType givenType, int beginLine){
        return giveContext(beginLine, className, methodName) + "Tried to allocate an object of primitive type " + givenType.getDebugInfo();
    }

    public static String badOperands(String operator, MiniJavaType expectedType, MiniJavaType givenType1, MiniJavaType givenType2, int beginLine){
        return giveContext(beginLine) + "Bad operands for operator \"" + operator + "\", must be " + expectedType.getDebugInfo() + " instead given " + givenType1.getDebugInfo() + " and " + givenType2.getDebugInfo();
    }

    public static String badOperands(String className, String methodName, String operator, MiniJavaType expectedType, MiniJavaType givenType1, MiniJavaType givenType2, int beginLine){
        return giveContext(beginLine, className, methodName) + "Bad operands for operator \"" + operator + "\", must be " + expectedType.getDebugInfo() + " instead given " + givenType1.getDebugInfo() + " and " + givenType2.getDebugInfo();
    }

    public static String callingMethodOnNonObject(String expr, MiniJavaType type, int beginLine){
        return giveContext(beginLine) + "Calling method on non-object \"" + (expr != null ? expr : "") + "\" of type " + type.getDebugInfo();
    }

    public static String callingMethodOnNonObject(String className, String methodName, String expr, MiniJavaType type, int beginLine){
        return giveContext(beginLine, className, methodName) + "Calling method on non-object \"" + (expr != null ? expr : "") + "\" of type " + type.getDebugInfo();
    }

    public static String moreParametersThanExpected(String classToCall, String methodToCall, int expectedArgNumber, int beginLine){
        return giveContext(beginLine) + "More parameters than expected (expected " + expectedArgNumber + ") in a call to the method \"" + methodToCall + "\" of the class \"" + classToCall + "\"";
    }

    public static String moreParametersThanExpected(String className, String methodName, String classToCall, String methodToCall, int expectedArgNumber, int beginLine){
        return giveContext(beginLine, className, methodName) + "More parameters than expected (expected " + expectedArgNumber + ") in a call to the method \"" + methodToCall + "\" of the class \"" + classToCall + "\"";
    }

    public static String lessParametersThanExpected(String classToCall, String methodToCall, int expectedArgNumber, int givenArgNumber, int beginLine){
        return giveContext(beginLine) + "Less parameters than expected (expected " + expectedArgNumber + " but only got " + givenArgNumber + ") in a call to the method \"" + methodToCall + "\" of the class \"" + classToCall + "\"";
    }

    public static String lessParametersThanExpected(String className, String methodName, String classToCall, String methodToCall, int expectedArgNumber, int givenArgNumber, int beginLine){
        return giveContext(beginLine, className, methodName) + "Less parameters than expected (expected " + expectedArgNumber + " but only got " + givenArgNumber + ") in a call to the method \"" + methodToCall + "\" of the class \"" + classToCall + "\"";
    }

    public static String methodDoesNotExist(String targetClass, String targetMethod, int beginLine){
        return giveContext(beginLine) + "Class \"" + targetClass + "\" does not have a method \"" + targetMethod + "\"";
    }

    public static String methodDoesNotExist(String className, String methodName, String targetClass, String targetMethod, int beginLine){
        return giveContext(beginLine, className, methodName) + "Class \"" + targetClass + "\" does not have a method \"" + targetMethod + "\"";
    }

    public static String methodCalledOnPrimitiveType(String calledMethod, MiniJavaType type, int beginLine){
        return giveContext(beginLine) + "Method \"" + calledMethod + "\" called on primitive type " + type.getDebugInfo();
    }

    public static String methodCalledOnPrimitiveType(String className, String methodName, String calledMethod, MiniJavaType type, int beginLine){
        return giveContext(beginLine, className, methodName) + "Method \"" + calledMethod + "\" called on primitive type " + type.getDebugInfo();
    }

}
