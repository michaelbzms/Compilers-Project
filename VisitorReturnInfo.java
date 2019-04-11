
public class VisitorReturnInfo {
    public String name = null;
    public TypeEnum type = null;

    public VisitorReturnInfo(String _name){
        name = _name;
    }

    public VisitorReturnInfo(TypeEnum _type){
        type = _type;
    }

    public VisitorReturnInfo(String _name, TypeEnum _type){
        name = _name;
        type = _type;
    }
}
