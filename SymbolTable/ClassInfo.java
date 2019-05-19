package SymbolTable;

import Util.MyPair;

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
    private ClassInfo motherClass = null;     // reference to that class in the symbol table

    public ClassInfo() { }

    public ClassInfo(String _motherClassName, ClassInfo _motherClass) {
        motherClassName = _motherClassName;
        motherClass = _motherClass;
    }

    public VariableInfo getFieldInfo(String fieldName) { return fields.get(fieldName);}

    public MethodInfo getMethodInfo(String methodName) { return methods.get(methodName); }

    public String getMotherClassName() { return motherClassName; }

    public ClassInfo getMotherClass() { return motherClass; }

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

        // check if method is an override
        ClassInfo classInfo = this.getMotherClass();
        MethodInfo otherMethodInfo = null;
        while (classInfo != null && otherMethodInfo == null) {
            otherMethodInfo = classInfo.getMethodInfo(methodName);
            classInfo = classInfo.getMotherClass();
        }
        // and if it is then set methodInfo's override field to that method before inserting it to this ClassInfo
        if (otherMethodInfo != null){
            methodInfo.setOverride(otherMethodInfo);
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

    public int getNextFieldOffset(){
        int sum = 0;
        if (motherClass != null) sum = motherClass.getNextFieldOffset();
        for (MyPair<String, VariableInfo> f : orderedFields){
            sum += f.getSecond().getType().getOffsetOfType();
        }
        return sum;
    }

    public int getNextMethodOffset(){
        int sum = 0;
        if (motherClass != null) sum = motherClass.getNextMethodOffset();
        for (MyPair<String, MethodInfo> m : orderedMethods){
            // only add to offsets if it is a new method and not an @override (and not the main method -> don't count it as inherited)
            if (!m.getSecond().isOverride() && !m.getFirst().equals("main")) sum += 8;
        }
        return sum;
    }

    public int getTotalNumberOfMethods(){
        // TODO: faster way?
        return getNextMethodOffset() / 8;
    }

    ////////////////////////
    ////     DEBUG     /////
    ////////////////////////
    public void printDebugInfo() {
        if (getMotherClassName() != null) {
            System.out.println("  mother_class = " + getMotherClassName());
        }
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