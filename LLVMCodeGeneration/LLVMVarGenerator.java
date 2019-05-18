package LLVMCodeGeneration;

public class LLVMVarGenerator {

    long counter = 0;

    public LLVMVarGenerator(){ }
    public LLVMVarGenerator(int starting_count){ counter = starting_count; }

    public String generateLocalVarName(){
        return "%_" + counter++;
    }

}
