package SymbolTable;

import MiniJavaType.MiniJavaType;
import Util.MyPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MethodInfo {
    private MiniJavaType returnType;
    private Map<String, VariableInfo> variables = new HashMap<>();               // variable name -> Variable Info
    private List<MyPair<String, VariableInfo>> arguments = new ArrayList<>();    // ordered list of pairs (name, varInfo)
    private MethodInfo override = null;
    private int offset = -1;

    public MethodInfo(MiniJavaType _returnType){ returnType = _returnType; }

    public MiniJavaType getReturnType() { return returnType; }

    public VariableInfo getVariableInfo(String variableName){
        return variables.get(variableName);
    }

    public void setOverride(MethodInfo _override){ override = _override; }

    public MethodInfo getOverride() { return override; }

    public boolean isOverride(){ return override != null; }

    public boolean putVariableInfo(String variableName, VariableInfo variableInfo){
        if ( variables.containsKey(variableName) ){ return false; }
        variables.put(variableName, variableInfo);
        return true;
    }

    public boolean putArgumentInfo(String argumentName, VariableInfo argumentInfo){
        // same as putVariableInfo but also adds it to list of (ordered) arguments
        if ( variables.containsKey(argumentName) ){ return false; }
        variables.put(argumentName, argumentInfo);
        arguments.add(new MyPair<String, VariableInfo>(argumentName, argumentInfo));
        return true;
    }

    public VariableInfo getArgumentInfoAtPos(int pos){
        if (pos < 0 || pos >= arguments.size()) return null;
        MyPair<String, VariableInfo> arg = arguments.get(pos);
        return (arg != null) ? arg.getSecond() : null;
    }

    public int getNumberOfArguments(){
        return arguments.size();
    }

    public List<MyPair<String, VariableInfo>> getArgList(){
        return arguments;
    }

    public Map<String, VariableInfo> getVariablesMap(){
        return variables;
    }

    public boolean hasSameSignatureWith(MethodInfo other){
        // must have same return type
        if (!this.getReturnType().equals(other.getReturnType())) return false;

        // must have equal number of arguments
        int numOfArgs;
        if (( numOfArgs = this.getNumberOfArguments()) != other.getNumberOfArguments()) return false;

        // those arguments must have 1-1 the same type
        for (int i = 0 ; i < numOfArgs ; i++){
            if ( ! this.getArgumentInfoAtPos(i).getType().equals( other.getArgumentInfoAtPos(i).getType() ) )
                return false;
        }

        return true;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo() {
        System.out.println("      > isOverride = " + isOverride());
        System.out.println("      > return_type = " + returnType.getDebugInfo());
        System.out.print("      > args: ");
        for (MyPair<String, VariableInfo> arg : arguments ){
            System.out.print(arg.getFirst() + ", ");
        }
        System.out.println("$");
        for (Map.Entry<String, VariableInfo> entry : variables.entrySet()) {
            System.out.println("      > variable_name = " + entry.getKey());
            VariableInfo variableInfo = entry.getValue();
            variableInfo.printDebugInfo(true);
        }
    }
}
