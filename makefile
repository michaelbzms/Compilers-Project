all: compile

compile: ./*.java SymbolTable/*.java MiniJavaType/*.java
	java -jar Resources/jtb132di.jar MiniJava.jj
	java -jar Resources/javacc5.jar MiniJava-jtb.jj
	javac Main.java

execute:
	java Main

clean:
	rm -f *.class SymbolTable/*.class MiniJavaType/*.class *~
