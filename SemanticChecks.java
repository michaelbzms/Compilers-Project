/** This is a namespace of static functions used to perform semantic checks **/

public class SemanticChecks {


    public static boolean checkType(SymbolTable ST, MiniJavaType givenType, MiniJavaType targetType){
        if (ST == null || givenType == null || targetType == null){
            System.err.println("Null parameters to checkType()");
            return false;
        }

        // check if types match
        if (givenType.equals(targetType)) return true;

        // if not, check for subtyping
        if (givenType.getType() == TypeEnum.CUSTOM && targetType.getType() == TypeEnum.CUSTOM){
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

}
