package LLVMCodeGeneration;

import SymbolTable.ClassInfo;
import SymbolTable.MethodInfo;
import Util.MyPair;


public class LLVMCodeGenerating {

    private static String getMethodType(String className, String methodName, MethodInfo methodInfo){
        String sig = methodInfo.getReturnType().getLLVMType();
        int argnum = methodInfo.getNumberOfArguments();
        sig += " (i8*";    // "this" ptr
        for (int i = 0 ; i < argnum ; i++){
            sig += ", " + methodInfo.getArgumentInfoAtPos(i).getType().getLLVMType();
        }
        sig += ")* @" + className + "." + methodName;
        return sig;
    }

    ///////////////////////////////////////////////////////////////////////

    public static String generateVTableForClass(String className, ClassInfo classInfo){  // (should not be used for main class)
        String out = "@." + className + "_vtable = global ";
        out += "[" + classInfo.getTotalNumberOfMethods() + " x i8*] [";
        String allMethodsOrdered = "";
        String currClassName = className;
        ClassInfo currClass = classInfo;
        while (currClass != null){
            String addon = "";
            for (MyPair<String, MethodInfo> m : currClass.getOrderedMethods()){
                if (!m.getSecond().isOverride() && !m.getFirst().equals("main")){
                    addon += "i8* bitcast (" + getMethodType(currClassName, m.getFirst(), m.getSecond()) + " to i8*)";
                }
            }
            if (!addon.equals("")) allMethodsOrdered = addon + (allMethodsOrdered.equals("") ? "" : ", ") + allMethodsOrdered;   // prepend (!)
            currClassName = currClass.getMotherClassName();
            currClass = currClass.getMotherClass();
        }
        out += allMethodsOrdered + "]";
        return out;
    }

}
