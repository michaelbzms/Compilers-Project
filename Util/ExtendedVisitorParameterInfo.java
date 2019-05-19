package Util;

public class ExtendedVisitorParameterInfo extends VisitorParameterInfo {

    private final String ClassNameToCall;
    private final String MethodNameToCall;

    public ExtendedVisitorParameterInfo(String curClassName, String curMethodName, String toCallClassname, String toCallMethodName, String _type){
        super(curMethodName, curClassName, _type);
        ClassNameToCall = toCallClassname;
        MethodNameToCall = toCallMethodName;
    }

    public ExtendedVisitorParameterInfo(VisitorParameterInfo argu, int argNum, String toCallClassname, String toCallMethodName){
        super(argu, argNum);
        ClassNameToCall = toCallClassname;
        MethodNameToCall = toCallMethodName;
    }

    public String getClassNameToCall() { return ClassNameToCall; }
    public String getMethodNameToCall() { return MethodNameToCall; }

}
