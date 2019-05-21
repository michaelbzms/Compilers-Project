package Util;

import MiniJavaType.MiniJavaType;


public class ExtendedVisitorReturnInfo extends VisitorReturnInfo {

    String resultVarNameOrConstant = null;

    public ExtendedVisitorReturnInfo(String _name){
        super(_name);
    }

    public ExtendedVisitorReturnInfo(MiniJavaType _type, String _resultVarName){
        super(_type);
        resultVarNameOrConstant = _resultVarName;
    }

    public ExtendedVisitorReturnInfo(String _name, MiniJavaType _type, String _resultVarName){
        super(_name, _type);
        resultVarNameOrConstant = _resultVarName;
    }

    public ExtendedVisitorReturnInfo(String _name, MiniJavaType _type, int _beginLine, String _resultVarName){
        super(_name, _type, _beginLine);
        resultVarNameOrConstant = _resultVarName;
    }


    public String getResultVarNameOrConstant() {
        return resultVarNameOrConstant;
    }

    public void setResultVarNameOrConstant(String resultVarNameOrConstant) {
        this.resultVarNameOrConstant = resultVarNameOrConstant;
    }
}
