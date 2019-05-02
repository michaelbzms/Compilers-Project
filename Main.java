import SymbolTable.*;
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
				// print offsets
				System.out.println("\nOffsets for given file's classes are: ");
				printOffsets(symbolTable);
				// Debug:
				//System.out.println("\nDebug Info is:");
				//symbolTable.printDebugInfo();
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

    private static void printOffsets(SymbolTable ST){
		for (MyPair<String, ClassInfo> c : ST.getOrderedClasses()){
			int startingFieldOffset = 0, startingMethodOffset = 0;
			if (c.getSecond().getMotherClassName() != null) {
				ClassInfo motherClass = ST.lookupClass(c.getSecond().getMotherClassName());
				if (motherClass != null) {
					startingFieldOffset = motherClass.getNextFieldOffset(ST);
					startingMethodOffset = motherClass.getNextMethodOffset(ST);
				}
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
				if (c.getSecond().getMotherClassName() == null || SemanticChecks.checkMethodExistsForCustomType(ST, c.getSecond().getMotherClassName(), m.getFirst()) == null) {
					System.out.println(c.getFirst() + "." + m.getFirst() + " : " + (startingMethodOffset));
					startingMethodOffset += 8;
				}
			}
		}
	}

}
