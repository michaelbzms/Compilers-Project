package SymbolTable;

import MiniJavaType.MiniJavaType;
import java.util.HashMap;
import java.util.Map;


public class MethodInfo {
    private MiniJavaType returnType;
    private Map<String, VariableInfo> variables = new HashMap<String, VariableInfo>();  // variable name -> Variable Info

    public MethodInfo(MiniJavaType _returnType){ returnType = _returnType; }

    public MiniJavaType getReturnType() { return returnType; }

    public VariableInfo getVariableInfo(String variableName){
        return variables.get(variableName);
    }

    public boolean putVariableInfo(String variableName, VariableInfo variableInfo){
        if ( variables.containsKey(variableName) ){ return false; }
        variables.put(variableName, variableInfo);
        return true;
    }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo() {
        System.out.println("      > return_type = " + returnType.getDebugInfo());
        for (Map.Entry<String, VariableInfo> entry : variables.entrySet()) {
            System.out.println("         > variable_name = " + entry.getKey());
            VariableInfo variableInfo = entry.getValue();
            variableInfo.printDebugInfo(true);
        }
    }
}
