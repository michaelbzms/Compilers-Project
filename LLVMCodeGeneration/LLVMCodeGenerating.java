package LLVMCodeGeneration;

import SymbolTable.ClassInfo;
import SymbolTable.MethodInfo;
import Util.MyPair;


public class LLVMCodeGenerating {

    public static String getMethodType(String className, String methodName, MethodInfo methodInfo){
        String sig = methodInfo.getReturnType().getLLVMType();
        int argnum = methodInfo.getNumberOfArguments();
        sig += " (i8*";    // "this" ptr
        for (int i = 0 ; i < argnum ; i++){
            sig += ", " + methodInfo.getArgumentInfoAtPos(i).getType().getLLVMType();
        }
        sig += ")*";
        if (className != null && methodName != null) {
            sig += " @" + className + "." + methodName;
        }
        return sig;
    }

    ///////////////////////////////////////////////////////////////////////

    public static String generateVTableForClass(String className, ClassInfo classInfo){  // (should not be used for main class)
        String out = "@." + className + "_vtable = global ";
        out += "[" + classInfo.getTotalNumberOfMethods() + " x i8*] [";
        String currClassName = className;
        ClassInfo currClass = classInfo;
        String[] VTable = new String[classInfo.getTotalNumberOfMethods()];
        for (int i = 0 ; i < VTable.length ; i++) VTable[i] = null;
        while (currClass != null){
            for (MyPair<String, MethodInfo> m : currClass.getOrderedMethods()){
                if (VTable[m.getSecond().getOffset() / 8] == null && !m.getFirst().equals("main")){   // if not added before (by an override)
                    VTable[m.getSecond().getOffset() / 8] = "i8* bitcast (" + getMethodType(currClassName, m.getFirst(), m.getSecond()) + " to i8*)";
                }
            }
            currClassName = currClass.getMotherClassName();
            currClass = currClass.getMotherClass();
        }
        for (int i = 0 ; i < VTable.length ; i++) {
            out += ((i > 0) ? ", " : "") + VTable[i];
        }
        out += "]";
        return out;
    }

}
