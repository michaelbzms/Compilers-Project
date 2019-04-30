package SymbolTable;

import java.util.HashMap;
import java.util.Map;


/** SymbolTable.SymbolTable -> SymbolTable.ClassInfo -> SymbolTable.MethodInfo -> SymbolTable.VariableInfo
 *                           -> SymbolTable.VariableInfo (fields)
 *  Implemented by 3 nested Maps with String keys:
 *    1. SymbolTable.SymbolTable.Map:  class_name     ->  SymbolTable.ClassInfo
 *    2. SymbolTable.ClassInfo.Map:    method_name    ->  SymbolTable.MethodInfo
 *    3. SymbolTable.MethodInfo.Map:   variable_name  ->  SymbolTable.VariableInfo
 *  as well as a Map for a class's fields:
 *    4. SymbolTable.ClassInfo.Map:    field_name     ->  SymbolTable.VariableInfo
 *
 *  The main class is a special case represented straight
 *  into the symbol table.
 */


@SuppressWarnings("WeakerAccess")
public class SymbolTable {
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();   // class name -> Class Info
	// Main class:
	private String mainClassName = null;
	private ClassInfo mainClassInfo = null;
	private Map<String, VariableInfo> mainMethodVariables = new HashMap<String, VariableInfo>();

	public String getMainClassName() { return mainClassName; }

	public boolean setMainClassName(String _mainClassName) {
		if (mainClassName == null){
			mainClassName = _mainClassName;
			//mainClassInfo = new ClassInfo();
			//mainClassInfo.putMethodInfo("main", new SymbolTable.MethodInfo());
            // TODO
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
//		if (className != null && className.equals(this.getMainClassName())){
//			ClassInfo mainInfo = new ClassInfo();
//			//mainInfo. //TODO
//		}
		return classes.get(className);
	}


	//////////////////////////////
	////  SEMANTIC CHECKING  /////
	//////////////////////////////

	public boolean checkForCyclicInheritance(){
		//TODO
		return false;
	}


	////////////////////////
	////     DEBUG     /////
	////////////////////////
	public void printDebugInfo(){
		System.out.println("Main class: name = " + getMainClassName() + "\nMain method variables are: ");
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
