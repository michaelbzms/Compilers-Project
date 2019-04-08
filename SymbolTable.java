import java.util.Map;
import java.util.HashMap;


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


enum Type {
	INTEGER, BOOLEAN, INTARRAY;
}


class VariableInfo {
	private Type type;

	public VariableInfo(Type _type) { type = _type; }
	public Type getType() { return type; }
}

class MethodInfo {
	private Type returnType;
	private Map<String, VariableInfo> variables = new HashMap<String, VariableInfo>();  // variable name -> Variable Info

	public MethodInfo(Type _returnType){ returnType = _returnType; }
	public Type getReturnType() { return returnType; }

	public VariableInfo getVariableInfo(String variableName){
		return variables.get(variableName);
	}

	public boolean putVariableInfo(String variableName, VariableInfo variableInfo){
		if ( variables.containsKey(variableName) ){ return false; }
		variables.put(variableName, variableInfo);
		return true;
	}
}

class ClassInfo {
	private Map<String, VariableInfo> fields = new HashMap<String, VariableInfo>();     // field name  -> VariableInfo
	private Map<String, MethodInfo> methods = new HashMap<String, MethodInfo>();        // method name -> MethodInfo
	private String motherClassName = null;    // name of the class this class extends (if it extends one)

	public ClassInfo(){ }
	public ClassInfo(String _motherClassName) { motherClassName = _motherClassName; }

	public VariableInfo getFieldInfo(String fieldName) {
		return fields.get(fieldName);
	}

	public MethodInfo getMethodInfo(String methodName){
		return methods.get(methodName);
	}

	public String getMotherClassName(){
		return motherClassName;
	}

	public boolean putFieldInfo(String fieldName, VariableInfo fieldInfo){
		if ( fields.containsKey(fieldName) ){ return false; }
		fields.put(fieldName, fieldInfo);
		return true;
	}

	public boolean putMethodInfo(String methodName, MethodInfo methodInfo){
		if ( methods.containsKey(methodName) ){ return false; }
		methods.put(methodName, methodInfo);
		return true;
	}

	public boolean setMotherClassName(String _motherClassName){
		if (motherClassName == null){
			motherClassName = _motherClassName;
			return true;
		} else return false;
	}
}


public class SymbolTable {
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();   // class name -> Class Info
	// Main class:
	private String mainClassName = null;
	private String mainClassArgName = null;

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
		if ( classes.containsKey(className) ){ return false; }
		classes.put(className, classInfo);
		return true;
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
}