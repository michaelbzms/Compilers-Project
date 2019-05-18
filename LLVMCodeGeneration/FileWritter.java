package LLVMCodeGeneration;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileWritter {

    FileOutputStream out = null;

    public FileWritter(String filename){
        try {
            out = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not find/create output file");
        }
    }

    public void emit(String output){
        if (out == null) return;
        try {
            out.write(output.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not emit to output file");
        }
    }

    public void close(){
        if (out == null) return;
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not close output file");
        }
    }

}
