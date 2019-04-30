
public class VisitorParameterInfo {
    private String name;
    private String supername = null;
    private String type;
    private String purpose = null;
    private int argNum = 0;

    public VisitorParameterInfo(String _name, String _type){
        name = _name; type =_type;
    }

    public VisitorParameterInfo(String _name, String _supername, String _type){
        name = _name;
        supername = _supername;
        type = _type;
    }

    public VisitorParameterInfo(String _name, String _supername, String _type, String _purpose){
        name = _name;
        supername = _supername;
        type = _type;
        purpose = _purpose;
    }

    public VisitorParameterInfo(VisitorParameterInfo _argu, int addArgNum){
        this.name = _argu.name;
        this.supername = _argu.supername;
        this.type = _argu.type;
        this.purpose = _argu.purpose;
        this.argNum = addArgNum;
    }

    public int getArgNum(){ return argNum; }
    public void nextArg() { argNum++; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSupername() { return supername; }
    public void setSupername(String supername) { this.supername = supername; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
