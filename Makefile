GENERATED_ROOT_FILES = JavaCharStream.java MiniJavaParser.java MiniJavaParserConstants.java MiniJavaParserTokenManager.java ParseException.java Token.java TokenMgrError.java

all: compile

compile: ./*.java SymbolTable/*.java MiniJavaType/*.java
	java -jar Resources/jtb132di.jar MiniJava.jj
	java -jar Resources/javacc5.jar MiniJava-jtb.jj
	javac Main.java

execute:
	java Main

clean:
	# remove generated stuff?
	rm -rf visitor/ syntaxtree/ MiniJava-jtb.jj
	rm -f $(GENERATED_ROOT_FILES)
	# remove .class files
	rm -f *.class */*.class *~
