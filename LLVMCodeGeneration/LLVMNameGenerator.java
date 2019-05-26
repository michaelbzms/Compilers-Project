package LLVMCodeGeneration;

public class LLVMNameGenerator {

    long var_counter = 0, label_counter = 0;

    public LLVMNameGenerator(){ }

    public String generateLocalVarName(){
        return "%_" + (var_counter++);
    }

    public String generateLabelName(){
        return "label" + (label_counter++);
    }

    public void resetLocalCounter(){
        var_counter = 0;
        label_counter = 0;
    }

}
