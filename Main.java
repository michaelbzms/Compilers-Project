import SymbolTable.SymbolTable;
import syntaxtree.*;

import java.io.*;


class Main {

    public static void main (String [] args){
		for (int i = 0 ; i < args.length ; i++){                     // for each input file
			FileInputStream fis = null;
			System.out.println("Running program \"" + args[i] + "\"...");
			try {
			    fis = new FileInputStream(args[i]);
			    MiniJavaParser parser = new MiniJavaParser(fis);
				Goal root = parser.Goal();
				System.out.println("[√] Parsed OK!");

				// Create Symbol Table
				SymbolTable symbolTable = new SymbolTable();
				CreateSymbolTableVisitor STVisitor = new CreateSymbolTableVisitor(symbolTable);
			    root.accept(STVisitor, null);
			    if (STVisitor.detectedSemanticError) {
			    	System.out.println("[x] " + ((STVisitor.errorMsg.equals("")) ? "Semantic error" : "Semantic error " + STVisitor.errorMsg));
					continue;
				}
				// The Symbol Table has now been created //
				if ( symbolTable.checkForCyclicInheritance() ){
					System.out.println("[x] Semantic error:\nCyclic Inheritance detected");
					continue;
				}
				SemanticCheckingVisitor SCVisitor = new SemanticCheckingVisitor(symbolTable);
				root.accept(SCVisitor, null);
				if (SCVisitor.detectedSemanticError){
					System.out.println("[x] " + ((SCVisitor.errorMsg.equals("")) ? "Semantic error" : "Semantic error " + SCVisitor.errorMsg));
					// Debug:
					//symbolTable.printDebugInfo();
					continue;
				}
				System.out.println("[√] Semantic check OK!");
				// Debug:
				//symbolTable.printDebugInfo();
				// TODO: print offsets
			}
			catch(ParseException ex){
			    System.out.println("[x] Parsing error: " + ex.getMessage());
			}
			catch(FileNotFoundException ex){
			    System.out.println(ex.getMessage());
			}
			finally {
				if (i + 1 < args.length)
					System.out.println("----------------------------------------------");
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
