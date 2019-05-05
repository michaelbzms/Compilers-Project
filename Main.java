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

				// Print offsets
				System.out.println("\nOffsets for given file's classes are: ");
				printOffsets(symbolTable);

				// Debug:
				if (DEBUG_MODE) {
					System.out.println("\nDebug Info is:");
					symbolTable.printDebugInfo();
				}
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

    private static void printOffsets(SymbolTable ST){
    	// Note: for the next assignment I should probably store them somewhere
		for (MyPair<String, ClassInfo> c : ST.getOrderedClasses()){
			int startingFieldOffset = 0, startingMethodOffset = 0;
			ClassInfo motherClass = c.getSecond().getMotherClass();
			if (motherClass != null) {
				startingFieldOffset = motherClass.getNextFieldOffset();
				startingMethodOffset = motherClass.getNextMethodOffset();
			}
			// print offsets for fields
			for (MyPair<String, VariableInfo> f : c.getSecond().getOrderedFields()){
				int offset = f.getSecond().getType().getOffsetOfType();
				System.out.println(c.getFirst() + "." + f.getFirst() + " : " + (startingFieldOffset));
				startingFieldOffset += offset;
			}
			// print offsets for methods
			for (MyPair<String, MethodInfo> m : c.getSecond().getOrderedMethods()){
				// only if method is a new one and not an @override
				if (!m.getSecond().isOverride()) {
					System.out.println(c.getFirst() + "." + m.getFirst() + " : " + (startingMethodOffset));
					startingMethodOffset += 8;
				}
			}
		}
	}

}
