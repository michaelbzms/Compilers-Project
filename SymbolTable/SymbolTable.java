package SymbolTable;

import MiniJavaType.MiniJavaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	// Main class:
	private String mainClassName = null;
	private final ClassInfo mainClassInfo;
	private MethodInfo mainMethodInfo;
	private String mainClassArg = null;

	// Other (Custom) classes:
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();   // class name -> Class Info
	private List<MyPair<String, ClassInfo>> orderedClasses = new ArrayList<>();   // used for printing their offsets in order


	public SymbolTable(){
		mainClassInfo = new ClassInfo();
		mainMethodInfo = new MethodInfo(MiniJavaType.VOID);
		mainClassInfo.putMethodInfo("main", mainMethodInfo);
	}

	public String getMainClassName() { return mainClassName; }

    public String getMainClassArg() { return mainClassArg; }

	public boolean setMainClassNameAndArg(String _mainClassName, String _mainClassArg) {
		if (mainClassName == null){
			mainClassName = _mainClassName;
			mainClassArg = _mainClassArg;
			return true;
		} else return false;
	}

	public boolean putMainVariable(String variableName, VariableInfo variableInfo){
	    if (variableName != null && variableName.equals(mainClassArg)) return false;   // cannot use same name as arg
		return mainMethodInfo.putVariableInfo(variableName, variableInfo);
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

	public boolean putArgument(String className, String methodName, String argumentName, VariableInfo argumentInfo){
		ClassInfo classInfo = classes.get(className);
		if (classInfo != null){
			MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
			if (methodInfo != null){
				return methodInfo.putArgumentInfo(argumentName, argumentInfo);
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
		orderedClasses.add(new MyPair<String, ClassInfo>(className, classInfo));
		return true;
	}

	public VariableInfo lookupMainVariable(String variableName){
		return mainMethodInfo.getVariableInfo(variableName);
	}

	public VariableInfo lookupVariable(String className, String methodName, String variableName){
		if (this.getMainClassName() != null && this.getMainClassName().equals(className)){
			return this.lookupMainVariable(variableName);
		} else {
			ClassInfo classInfo = lookupClass(className);
			if (classInfo != null) {
				MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
				return (methodInfo != null) ? methodInfo.getVariableInfo(variableName) : null;
			} else return null;
		}
	}

	public VariableInfo lookupArgumentAtPos(String className, String methodName, int pos){
		if (this.getMainClassName() != null && this.getMainClassName().equals(className)){
			return null;  // main has no arguments (the one it has is not supported in MiniJava)
		} else {
            // (!) method might be inherited!
            ClassInfo classInfo = lookupClass(className);
            if (classInfo != null) {
                MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
                classInfo = classInfo.getMotherClass();
                while (classInfo != null && methodInfo == null) {
					methodInfo = classInfo.getMethodInfo(methodName);
                    classInfo = classInfo.getMotherClass();
                }
                return (methodInfo != null) ? methodInfo.getArgumentInfoAtPos(pos) : null;
			} else return null;
		}
	}

	public VariableInfo lookupField(String className, String fieldName){
		// Warning: this method does not take into account inherited fields! (use one from SemanticAnalysis.SemanticChecks.java instead)
		if (this.getMainClassName() != null && this.getMainClassName().equals(className))
			return null;   // main class can have no fields
		ClassInfo classInfo = lookupClass(className);
		return (classInfo != null) ? classes.get(className).getFieldInfo(fieldName) : null;
	}

	public MethodInfo lookupMethod(String className, String methodName){
		// Warning: this method does not take into account inherited methods! (use one from SemanticAnalysis.SemanticChecks.java instead)
		if (this.getMainClassName() != null && this.getMainClassName().equals(className))
			return ("main".equals(methodName) ?  mainMethodInfo : null);
		ClassInfo classInfo = lookupClass(className);
		return (classInfo != null) ? classInfo.getMethodInfo(methodName) : null;
	}

	public ClassInfo lookupClass(String className){
		// Note: takes into account main class as well
		return (this.getMainClassName() != null && this.getMainClassName().equals(className)) ? mainClassInfo : classes.get(className);
	}

	public int getNumberOfArguments(String className, String methodName){
		if (this.getMainClassName() != null && this.getMainClassName().equals(className)){
			return 1;  // main has one arguments (but it is not supported in MiniJava)
		} else {
		    // (!) method might be inherited!
			ClassInfo classInfo = lookupClass(className);
			if (classInfo != null) {
				MethodInfo methodInfo = classInfo.getMethodInfo(methodName);
                classInfo = classInfo.getMotherClass();
                while (classInfo != null && methodInfo == null) {
                    methodInfo = classInfo.getMethodInfo(methodName);
                    classInfo = classInfo.getMotherClass();
                }
				return (methodInfo != null) ? methodInfo.getNumberOfArguments() : 0;
			} else return 0;
		}
	}

	public List<MyPair<String, ClassInfo>> getOrderedClasses() {
		return orderedClasses;
	}


	//////////////////////////////
	////  SEMANTIC CHECKING  /////
	//////////////////////////////

	// Note: this check is obsolete and it is not necessary
	//       but I couldn't find the heart to delete it
	private boolean checkForCircle(String className, ClassInfo classInfo){
		while (classInfo.getMotherClassName() != null){
			if (classInfo.getMotherClassName().equals(className)){   // detected circle
				return true;
			}
			classInfo = lookupClass(classInfo.getMotherClassName());
		}
		return false;
	}

	public boolean checkForCyclicInheritance(){
		if (checkForCircle(mainClassName, mainClassInfo)){
			return true;
		}
		for (Map.Entry<String, ClassInfo> c : classes.entrySet()) {
			if (checkForCircle(c.getKey(), c.getValue())){
				return true;
			}
		}
		return false;
	}


	public void calculateOffsets(){
		for (MyPair<String, ClassInfo> c : this.getOrderedClasses()){
			int startingFieldOffset = 0, startingMethodOffset = 0;
			ClassInfo motherClass = c.getSecond().getMotherClass();
			if (motherClass != null) {
				startingFieldOffset = motherClass.getNextFieldOffset();
				startingMethodOffset = motherClass.getNextMethodOffset();
			}
			// calculate offsets for fields
			for (MyPair<String, VariableInfo> f : c.getSecond().getOrderedFields()){
				System.out.println(c.getFirst() + "." + f.getFirst() + " : " + (startingFieldOffset));
				f.getSecond().setOffset(startingFieldOffset);   // (!) must be same object (2 references) as in the map TODO: check
				startingFieldOffset += f.getSecond().getType().getOffsetOfType();
			}
			// calculate offsets for methods
			for (MyPair<String, MethodInfo> m : c.getSecond().getOrderedMethods()){
				if (!m.getSecond().isOverride()) {              // if method is a new one and not an @override
					System.out.println(c.getFirst() + "." + m.getFirst() + " : " + (startingMethodOffset));
					m.getSecond().setOffset(startingMethodOffset);
					startingMethodOffset += 8;
				} else {
					// set the same offset as mother's class method (which could also be an override)
					m.getSecond().setOffset(m.getSecond().getOverride().getOffset());
				}
			}
		}
	}


	////////////////////////
	////     DEBUG     /////
	////////////////////////
	public void printDebugInfo(){
		System.out.println("Main class is: " + getMainClassName() + "\nMain method return type and variables are: ");
		mainMethodInfo.printDebugInfo();
		System.out.println("\nOther classes are: ");
		for (Map.Entry<String, ClassInfo> entry : classes.entrySet()) {
			System.out.println("\n> class_name = " + entry.getKey());
			ClassInfo classInfo = entry.getValue();
			classInfo.printDebugInfo();
		}
	}
}
