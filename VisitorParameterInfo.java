
public class VisitorParameterInfo {
    public String name;
    public String supername = null;
    public String type;

    public VisitorParameterInfo(String _name, String _type){
        name = _name; type =_type;
    }

    public VisitorParameterInfo(String _name, String _supername, String _type){
        name = _name;
        supername = _supername;
        type = _type;
    }
}
