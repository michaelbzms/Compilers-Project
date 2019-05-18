package SymbolTable;

import MiniJavaType.MiniJavaType;


public class VariableInfo {
    private MiniJavaType type;
    private int offset = -1;      // only used for fields

    public VariableInfo(MiniJavaType _type) { type = _type; }

    public MiniJavaType getType() { return type; }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo(boolean onemoreindent) {
        System.out.println("      " + ((onemoreindent) ? "      " : "") + "> variable_type = " + type.getDebugInfo());
    }
}
