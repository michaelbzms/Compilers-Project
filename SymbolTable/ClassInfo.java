package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassInfo {
    private List<MyPair<String, VariableInfo>> orderedFields = new ArrayList<>();   // used for printing their offsets in order
    private List<MyPair<String, MethodInfo>> orderedMethods = new ArrayList<>();    // ^^
    private Map<String, VariableInfo> fields = new HashMap<>();     // field name  -> SymbolTable.VariableInfo
    private Map<String, MethodInfo> methods = new HashMap<>();      // method name -> SymbolTable.MethodInfo
    private String motherClassName = null;    // name of the class this class extends (if it extends one)

    public ClassInfo() { }

    public ClassInfo(String _motherClassName) {
        motherClassName = _motherClassName;
    }

    public VariableInfo getFieldInfo(String fieldName) { return fields.get(fieldName);}

    public MethodInfo getMethodInfo(String methodName) { return methods.get(methodName); }

    public String getMotherClassName() { return motherClassName; }

    public boolean putFieldInfo(String fieldName, VariableInfo fieldInfo) {
        if (fields.containsKey(fieldName)) {
            return false;
        }
        fields.put(fieldName, fieldInfo);
        orderedFields.add(new MyPair<>(fieldName, fieldInfo));
        return true;
    }

    public boolean putMethodInfo(String methodName, MethodInfo methodInfo) {
        if (methods.containsKey(methodName)) {
            return false;
        }
        methods.put(methodName, methodInfo);
        orderedMethods.add(new MyPair<>(methodName, methodInfo));
        return true;
    }

    public boolean setMotherClassName(String _motherClassName) {
        if (motherClassName == null) {
            motherClassName = _motherClassName;
            return true;
        } else return false;
    }

    public List<MyPair<String, VariableInfo>> getOrderedFields() {
        return orderedFields;
    }

    public List<MyPair<String, MethodInfo>> getOrderedMethods() {
        return orderedMethods;
    }

    public int getNextFieldOffset(SymbolTable ST){
        int sum = 0;
        if (motherClassName != null){
            ClassInfo motherClass = ST.lookupClass(motherClassName);
            if (motherClass != null) sum = motherClass.getNextFieldOffset(ST);
        }
        for (MyPair<String, VariableInfo> f : orderedFields){
            sum += f.getSecond().getType().getOffsetOfType();
        }
        return sum;
    }

    public int getNextMethodOffset(SymbolTable ST){
        int sum = 0;
        if (motherClassName != null){
            ClassInfo motherClass = ST.lookupClass(motherClassName);
            if (motherClass != null) sum = motherClass.getNextFieldOffset(ST);
        }
        sum += orderedMethods.size() * 8;
        return sum;
    }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo() {
        if (getMotherClassName() != null) {
            System.out.println("  mother_class = " + getMotherClassName());
        }
//        System.out.println("  Fields in order are: ");
//        for (MyPair<String, VariableInfo> node : orderedFields){
//            System.out.print(node.getFirst() + ": " + node.getSecond().getTypeEnum().getDebugInfo() + ", ");
//        }
//        System.out.println("$\n  Methods in order are: ");
//        for (MyPair<String, MethodInfo> node : orderedMethods){
//            System.out.print(node.getFirst() + ": " + node.getSecond().getReturnType().getDebugInfo() + ", ");
//        }
//        System.out.println("$\n");
        for (Map.Entry<String, VariableInfo> entry : fields.entrySet()) {
            System.out.println("   > field_name = " + entry.getKey());
            VariableInfo fieldInfo = entry.getValue();
            fieldInfo.printDebugInfo(false);
        }
        for (Map.Entry<String, MethodInfo> entry : methods.entrySet()) {
            System.out.println("   > method_name = " + entry.getKey());
            MethodInfo methodInfo = entry.getValue();
            methodInfo.printDebugInfo();
        }
    }
}