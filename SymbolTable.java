import java.util.*;

/** SymbolTable -> ClassInfo -> MethodInfo -> VariableInfo
 *                           -> VariableInfo (fields)
 *  Implemented by 3 nested Maps with String keys:
 *    1. SymbolTable.Map:  class_name     ->  ClassInfo
 *    2. ClassInfo.Map:    method_name    ->  MethodInfo
 *    3. MethodInfo.Map:   variable_name  ->  VariableInfo
 *  as well as a Map for a class's fields:
 *    4. ClassInfo.Map:    field_name     ->  VariableInfo
 *
 *  The main class is a special case represented straight
 *  into the symbol table.
 */


enum TypeEnum {
	INTEGER, BOOLEAN, INTARRAY, CUSTOM
}


class VariableInfo {
	private TypeEnum type;
	private String customTypeName = null;

	public VariableInfo(TypeEnum _type) { type = _type; }
	public VariableInfo(TypeEnum _type, String _customTypeName) {
		type = _type;
		customTypeName = _customTypeName;
	}
	public TypeEnum getType() { return type; }

	////////////////////////
	////     DEBUG     /////
	////////////////////////
	public void printDebugInfo(boolean onemoreindent) {
		System.out.println("      " + ((onemoreindent) ? "      " : "") + "> variable_type = " + type + ((type == TypeEnum.CUSTOM) ? " -> " + customTypeName : ""));
	}
}

class MethodInfo {
	private TypeEnum returnType;
	private Map<String, VariableInfo> variables = new HashMap<String, VariableInfo>();  // variable name -> Variable Info

	public MethodInfo(TypeEnum _returnType){ returnType = _returnType; }
	public TypeEnum getReturnType() { return returnType; }

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
		System.out.println("      > return_type = " + returnType);
		for (Map.Entry<String, VariableInfo> entry : variables.entrySet()) {
			System.out.println("         > variable_name = " + entry.getKey());
			VariableInfo variableInfo = entry.getValue();
			variableInfo.printDebugInfo(true);
		}
	}
}

class ClassInfo {
	private List<MyPair<String, VariableInfo>> orderedFields = new ArrayList<>();   // used for printing their offsets in order
	private List<MyPair<String, MethodInfo>> orderedMethods = new ArrayList<>();    // ^^
	private Map<String, VariableInfo> fields = new HashMap<>();     // field name  -> VariableInfo
	private Map<String, MethodInfo> methods = new HashMap<>();      // method name -> MethodInfo
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

	////////////////////////
	////     DEBUG     /////
	////////////////////////
	public void printDebugInfo() {
		if (getMotherClassName() != null) {
			System.out.println("  mother_class = " + getMotherClassName());
		}
		System.out.println("  Fields in order are: ");
		for (MyPair<String, VariableInfo> node : orderedFields){
			System.out.print(node.getFirst() + ": " + node.getSecond().getType() + ", ");
		}
		System.out.println("$\n  Methods in order are: ");
		for (MyPair<String, MethodInfo> node : orderedMethods){
			System.out.print(node.getFirst() + ": " + node.getSecond().getReturnType() + ", ");
		}
		System.out.println("$\n");
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


public class SymbolTable {
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();   // class name -> Class Info
	// Main class:
	private String mainClassName = null;
	private String mainClassArgName = null;
	private Map<String, VariableInfo> mainMethodVariables = new HashMap<String, VariableInfo>();

	public String getMainClassName() { return mainClassName; }
	public String getMainClassArgName() { return mainClassArgName; }

	public boolean setMainClassName(String _mainClassName) {
		if (mainClassName == null){
			mainClassName = _mainClassName;
			return true;
		} else return false;
	}

	public boolean setMainClassArgName(String _mainClassArgName) {
		if (mainClassArgName == null){
			mainClassArgName = _mainClassArgName;
			return true;
		} else return false;
	}

	public boolean putMainVariable(String variableName, VariableInfo variableInfo){
		if ( mainMethodVariables.containsKey(variableName) ) return false;
		mainMethodVariables.put(variableName, variableInfo);
		return true;
	}

	public boolean putVariable(String className, String methodName, String variableName, VariableInfo variableInfo){
		ClassInfo classInfo = classes.get(className);
		if (classInfo != null){
			MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
			if (methodInfo != null){
				return methodInfo.putVariableInfo(variableName, variableInfo);
			} else return false;
		} else return false;
	}

	public boolean putMethod(String className, String methodName, MethodInfo methodInfo){
		ClassInfo classInfo = classes.get(className);
		if (classInfo != null){
			return classInfo.putMethodInfo(methodName, methodInfo);
		} else return false;
	}

	public boolean putField(String className, String fieldName, VariableInfo fieldInfo){
		ClassInfo classInfo = classes.get(className);
		if (classInfo != null){
			return classInfo.putFieldInfo(fieldName, fieldInfo);
		} else return false;
	}

	public boolean putClass(String className, ClassInfo classInfo){
		if ( classes.containsKey(className) ) return false;
		classes.put(className, classInfo);
		return true;
	}

	public VariableInfo lookupMainVariable(String variableName){
		return mainMethodVariables.get(variableName);
	}

	public VariableInfo lookupVariable(String className, String methodName, String variableName){
		ClassInfo classInfo = lookupClass(className);
		if (classInfo != null){
			MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
			return (methodInfo != null) ? methodInfo.getVariableInfo(variableName) : null;
		} else return null;
	}

	public VariableInfo lookupField(String className, String fieldName){
		ClassInfo classInfo = lookupClass(className);
		return (classInfo != null) ? classes.get(className).getFieldInfo(fieldName) : null;
	}

	public MethodInfo lookupMethod(String className, String methodName){
		ClassInfo classInfo = lookupClass(className);
		return (classInfo != null) ? classInfo.getMethodInfo(methodName) : null;
	}

	public ClassInfo lookupClass(String className){
		return classes.get(className);
	}

	////////////////////////
	////     DEBUG     /////
	////////////////////////
	public void printDebugInfo(){
		System.out.println("Main class: name = " + getMainClassName() + ", args_name = " + getMainClassArgName() + "\nMain method variables are: ");
		for (Map.Entry<String, VariableInfo> entry : mainMethodVariables.entrySet()) {
			System.out.println("   > variable_bame = " + entry.getKey());
			VariableInfo variableInfo = entry.getValue();
			variableInfo.printDebugInfo(false);
		}
		System.out.println("Statistics for classes are: ");
		for (Map.Entry<String, ClassInfo> entry : classes.entrySet()) {
			System.out.println("> class_name = " + entry.getKey());
			ClassInfo classInfo = entry.getValue();
			classInfo.printDebugInfo();
		}
	}
}