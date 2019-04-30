package SymbolTable;

import MiniJavaType.MiniJavaType;


public class VariableInfo {
    private MiniJavaType type;

    public VariableInfo(MiniJavaType _type) { type = _type; }

    public MiniJavaType getType() { return type; }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo(boolean onemoreindent) {
        System.out.println("      " + ((onemoreindent) ? "      " : "") + "> variable_type = " + type.getDebugInfo());
    }
}
