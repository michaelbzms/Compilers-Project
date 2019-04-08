import java.util.Map;
import java.util.HashMap;
import java.util.Pair;


enum Type {
	INTEGER, BOOLEAN, INTARRAY;
}


class VariableInfo {
	public Type type;
	public VariableInfo(Type _type) { 
		type = _type; 
	}
}

class FunctionInfo {
	public Type returnType;
	public Pair<String, VariableInfo>[] args;
	public FunctionInfo(Type _returnType, Pair<String, VariableInfo>[] _args){
		returnType = _returnType;
		args = _args;
	}
}

class ClassInfo {
	public boolean isMain;
	public String strargsname = null;
	public Pair<String, VariableInfo>[] fields = null;
	public Pair<String, FunctionInfo>[] methods = null;
	public ClassInfo(String _strargsname){
		isMain = true;
		strargsname = _strargsname;
	}
	public ClassInfo(Pair<String, VariableInfo>[] _fields, Pair<String, FunctionInfo>[] _methods){
		isMain = false;
		fields = _fields;
		methods = _methods;
	}
}


public class SymbolTable {
	
	private Map<String, VariableInfo> VariableST = new HashMap<String, VariableInfo>();
	private Map<String, FunctionInfo> FunctionST = new HashMap<String, FunctionInfo>();
	private Map<String, ClassInfo> ClassST = new HashMap<String, ClassInfo>();


	public boolean insert_variable(String name, VariableInfo info){
		if ( VariableST.containsKey(name) ){ return false; }
		VariableST.put(name, info);
		return true;
	}

	public boolean insert_function(String name, FunctionInfo info){
		if ( FunctionST.containsKey(name) ){ return false; }
		FunctionST.put(name, info);
		return true;
	}

	public boolean insert_class(String name, ClassInfo info){
		if ( ClassST.containsKey(name) ){ return false; }
		ClassST.put(name, info);
		return true;
	}

	public VariableInfo lookup_variable(String name){
		return VariableST.get(name);
	}

	public FunctionInfo lookup_function(String name){
		return FunctionInfo.get(name);
	}

	public ClassInfo lookup_class(String name){
		return ClassInfo.get(name);
	}

}