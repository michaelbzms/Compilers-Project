import MiniJavaType.*;


public class VisitorReturnInfo {
    private String name = null;
    private MiniJavaType type = null;
    private boolean isAlloced = false;

    public VisitorReturnInfo(String _name){
        name = _name;
    }

    public VisitorReturnInfo(MiniJavaType _type){
        type = _type;
    }

    public VisitorReturnInfo(String _name, MiniJavaType _type){
        name = _name;
        type = _type;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public MiniJavaType getType() { return type; }
    public void setType(MiniJavaType type) { this.type = type; }

    public boolean isAlloced() { return isAlloced; }
    public void setAlloced(boolean alloced) { isAlloced = alloced; }
}
