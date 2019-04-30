import MiniJavaType.TypeEnum;

public class VisitorReturnInfo {
    private String name = null;
    private TypeEnum type = null;
    private boolean isAlloced = false;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeEnum getType() { return type; }
    public void setType(TypeEnum type) { this.type = type; }

    public boolean isAlloced() { return isAlloced; }
    public void setAlloced(boolean alloced) { isAlloced = alloced; }
}
