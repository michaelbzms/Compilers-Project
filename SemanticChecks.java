/** This is a namespace of static functions used to perform semantic checks **/

import MiniJavaType.MiniJavaType;
import SymbolTable.*;
import MiniJavaType.*;

public class SemanticChecks {

    public static boolean checkType(SymbolTable ST, MiniJavaType givenType, MiniJavaType targetType){
        if (ST == null || givenType == null || targetType == null){
            System.err.println("Null parameters to checkType()");
            return false;
        }

        // check if types match
        if (givenType.equals(targetType)) return true;

        // if not, check for subtyping
        if (givenType.getTypeEnum() == TypeEnum.CUSTOM && targetType.getTypeEnum() == TypeEnum.CUSTOM){
            String className = givenType.getCustomTypeName();
            ClassInfo classInfo = ST.lookupClass(className);
            while (classInfo != null && !className.equals(targetType.getCustomTypeName())){
                className = classInfo.getMotherClassName();
                if (className != null) classInfo = ST.lookupClass(className);
                else classInfo = null;
            }
            if (classInfo != null) return true;
        }

        // else
        return false;
    }

    public static MethodInfo checkMethodExistsForCustomType(SymbolTable ST, String customTypeName, String methodName){
        if (ST == null || customTypeName == null || methodName == null){
            System.err.println("Null parameters to checkMethodExistsForCustomType()");
            return null;
        }
        // First check if it is a method of customTypeName
        MethodInfo methodInfo = ST.lookupMethod(customTypeName, methodName);
        // if that fails check if it is a method of a superclass (local methods override superclass methods)
        if (methodInfo == null){
            ClassInfo classInfo = ST.lookupClass(customTypeName);
            if (classInfo != null) {
                String motherClassName = classInfo.getMotherClassName();
                while (motherClassName != null && methodInfo == null) {
                    classInfo = ST.lookupClass(classInfo.getMotherClassName());
                    methodInfo = ST.lookupMethod(motherClassName, methodName);
                    motherClassName = classInfo.getMotherClassName();
                }
            }
        }
        return methodInfo;
    }

    public static VariableInfo checkVariableOrFieldExists(SymbolTable ST, String customTypeName, String methodName, String varName){
        if (ST == null || customTypeName == null || varName == null){
            System.err.println("Null parameters to checkMethodExistsForCustomType()");
            return null;
        }
        VariableInfo varInfo = null;
        // First check if it is a local variable of the method (if method is given)
        if (methodName != null){
            varInfo = ST.lookupVariable(customTypeName, methodName, varName);
        }
        // If that fails then check if it is a field of customTypeName (local variables shadow fields)
        if (varInfo == null){
            varInfo = ST.lookupField(customTypeName, varName);
        }
        // if that fails then check if it is a field of a superclass (local fields shadow superclass fields)
        if (varInfo == null){
            ClassInfo classInfo = ST.lookupClass(customTypeName);
            if (classInfo != null) {
                String motherClassName = classInfo.getMotherClassName();
                while (motherClassName != null && varInfo == null) {
                    classInfo = ST.lookupClass(classInfo.getMotherClassName());
                    varInfo = ST.lookupField(motherClassName, varName);
                    motherClassName = classInfo.getMotherClassName();
                }
            }
        }
        return varInfo;
    }

}
