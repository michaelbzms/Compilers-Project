import syntaxtree.*;
import visitor.*;
import java.io.*;


class Main {

    public static void main (String [] args){
		for (int i = 0 ; i < args.length ; i++){                     // for each input file
			FileInputStream fis = null;
			try {
			    fis = new FileInputStream(args[0]);
			    MiniJavaParser parser = new MiniJavaParser(fis);
			    System.out.println("Program \"" + args[i] + "\" parsed successfully.");

			    // Create Symbol Table
				SymbolTable symbolTable = new SymbolTable();
			    CreateSymbolTableVisitor STVisitor = new CreateSymbolTableVisitor(symbolTable);
			    Goal root = parser.Goal();
			    root.accept(STVisitor, null);
			    if (STVisitor.detectedSemanticError) {
			    	System.out.println((STVisitor.errorMsg.equals("")) ? "Semantic error" : "Semantic error: " + STVisitor.errorMsg);
			    	continue;
			    }

			    // TODO: more semantic checks on Symbol Table

			    System.out.println("Semantic check OK!");
			}
			catch(ParseException ex){
			    System.out.println("Parsing error: " + ex.getMessage());
			}
			catch(FileNotFoundException ex){
			    System.out.println(ex.getMessage());
			}
			finally {
			    try {
					if (fis != null) fis.close();
			    }
			    catch (IOException ex){
					System.err.println(ex.getMessage());
			    }
			}
		}
    }

}
