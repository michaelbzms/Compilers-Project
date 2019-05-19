package Util;

import MiniJavaType.MiniJavaType;


public class ExtendedVisitorReturnInfo extends VisitorReturnInfo {

    String resultVarName = null;

    public ExtendedVisitorReturnInfo(String _name){
        super(_name);
    }

    public ExtendedVisitorReturnInfo(MiniJavaType _type, String _resultVarName){
        super(_type);
        resultVarName = _resultVarName;
    }

    public ExtendedVisitorReturnInfo(String _name, MiniJavaType _type, String _resultVarName){
        super(_name, _type);
        resultVarName = _resultVarName;
    }

    public ExtendedVisitorReturnInfo(String _name, MiniJavaType _type, int _beginLine, String _resultVarName){
        super(_name, _type, _beginLine);
        resultVarName = _resultVarName;
    }


    public String getResultVarName() {
        return resultVarName;
    }

    public void setResultVarName(String resultVarName) {
        this.resultVarName = resultVarName;
    }
}
