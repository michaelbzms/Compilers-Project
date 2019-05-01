import SymbolTable.SymbolTable;
import syntaxtree.*;

import java.io.*;


class Main {

    public static void main (String [] args){
		for (int i = 0 ; i < args.length ; i++){                     // for each input file
			FileInputStream fis = null;
			try {
			    fis = new FileInputStream(args[i]);
			    MiniJavaParser parser = new MiniJavaParser(fis);
				Goal root = parser.Goal();
				System.out.println("Program \"" + args[i] + "\" parsed successfully.");

				// Create Symbol Table
				SymbolTable symbolTable = new SymbolTable();
				CreateSymbolTableVisitor STVisitor = new CreateSymbolTableVisitor(symbolTable);
			    root.accept(STVisitor, null);
			    if (STVisitor.detectedSemanticError) {
			    	System.out.println((STVisitor.errorMsg.equals("")) ? "Semantic error(1)" : "Semantic error(1): " + STVisitor.errorMsg);
					System.out.print("\n");
					continue;
				}
				// The Symbol Table has now been created //
				if ( symbolTable.checkForCyclicInheritance() ){
					System.out.println("Semantic error: Cyclic Inheritance detected\n");
					continue;
				}
				SemanticCheckingVisitor SCVisitor = new SemanticCheckingVisitor(symbolTable);
				root.accept(SCVisitor, null);
				if (SCVisitor.detectedSemanticError){
					System.out.println((SCVisitor.errorMsg.equals("")) ? "Semantic error(2)" : "Semantic error(2): " + SCVisitor.errorMsg);
					// Debug:
					//symbolTable.printDebugInfo();
					System.out.print("\n");
					continue;
				}
				System.out.println("Semantic check OK!\n");
				// Debug:
				symbolTable.printDebugInfo();
				// TODO: print offsets
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
