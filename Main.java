import LLVMCodeGeneration.LLVMCodeGeneratingVisitor;
import SemanticAnalysis.CreateSymbolTableVisitor;
import SemanticAnalysis.SemanticCheckingVisitor;
import SymbolTable.*;
import syntaxtree.*;
import java.io.*;


class Main {

	private static final boolean DEBUG_MODE = false;                 // set if you want to see debug information of the Symbol Table for correct inputs

    public static void main (String [] args){
		for (int i = 0 ; i < args.length ; i++){                     // for each input file
			FileInputStream fis = null;
			System.out.println("Running program \"" + args[i] + "\"...");
			try {
			    fis = new FileInputStream(args[i]);
			    MiniJavaParser parser = new MiniJavaParser(fis);

			    // Parse input file
			    Goal root = parser.Goal();
				System.out.println("[√] Parsed OK!");

				// Create Symbol Table with a first visitor who also catches some semantic errors (that need not the Symbol Table done)
				SymbolTable symbolTable = new SymbolTable();
				CreateSymbolTableVisitor STVisitor = new CreateSymbolTableVisitor(symbolTable);
			    root.accept(STVisitor, null);
			    if (STVisitor.detectedSemanticError) {
			    	System.out.println("[x] " + ((STVisitor.errorMsg.equals("")) ? "Semantic error" : "Semantic error " + STVisitor.errorMsg));
					continue;
				}

			    // Then call a second visitor to check all the rest of semantic errors
				SemanticCheckingVisitor SCVisitor = new SemanticCheckingVisitor(symbolTable);
				root.accept(SCVisitor, null);
				if (SCVisitor.detectedSemanticError){
					System.out.println("[x] " + ((SCVisitor.errorMsg.equals("")) ? "Semantic error" : "Semantic error " + SCVisitor.errorMsg));
					continue;
				}

				System.out.println("[√] Semantic check OK!");

				// calculate offsets
				symbolTable.calculateOffsets();

				// Debug:
				if (DEBUG_MODE) {
					System.out.println("\nDebug Info is:");
					symbolTable.printDebugInfo();
				}

				// Generate .ll file
				LLVMCodeGeneratingVisitor LLVMVisitor = new LLVMCodeGeneratingVisitor(symbolTable, convertToLLFile(args[i]));
				root.accept(LLVMVisitor, null);
			}
			catch(ParseException ex){
			    System.out.println("[x] Parsing error: " + ex.getMessage());
			}
			catch(FileNotFoundException ex){
			    System.out.println(ex.getMessage());
			}
			finally {
				if (i + 1 < args.length)
					// Print a line to separate different inputs/outputs
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

	private static String convertToLLFile(String input){
		return input.substring(0, input.lastIndexOf('.') + 1) + "ll";
	}

}
