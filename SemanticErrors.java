import MiniJavaType.MiniJavaType;

public class SemanticErrors {

    private static String giveContext(int beginLine){
        return "in main():" + (beginLine > 0 ? " line " + beginLine : "") + "\n";
    }

    private static String giveContext(int beginLine, String className){
        return "in class \"" + className + "\":" + (beginLine > 0 ? " line " + beginLine : "") + "\n";
    }

    private static String giveContext(int beginLine, String className, String methodName){
        return "in method \"" + methodName + "\" of the class \"" + className + "\":" + (beginLine > 0 ? " line " + beginLine : "") + "\n";
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

    public static String badOperands(String operator, MiniJavaType expectedType, MiniJavaType givenType1, MiniJavaType givenType2, int beginLine){
        return giveContext(beginLine) + "Bad operands for operator \"" + operator + "\", must be " + expectedType.getDebugInfo() + " instead given " + givenType1.getDebugInfo() + " and " + givenType2.getDebugInfo();
    }

    public static String badOperands(String className, String methodName, String operator, MiniJavaType expectedType, MiniJavaType givenType1, MiniJavaType givenType2, int beginLine){
        return giveContext(beginLine, className, methodName) + "Bad operands for operator \"" + operator + "\", must be " + expectedType.getDebugInfo() + " instead given " + givenType1.getDebugInfo() + " and " + givenType2.getDebugInfo();
    }

    public static String callingMethodOnNonObject(String expr, int beginLine){
        return giveContext(beginLine) + "Calling method on non-object " + (expr != null ? expr : "");
    }

    public static String callingMethodOnNonObject(String className, String methodName, String expr, int beginLine){
        return giveContext(beginLine, className, methodName) + "Calling method on non-object " + (expr != null ? expr : "");
    }

}
