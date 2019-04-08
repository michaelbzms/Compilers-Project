all: compile

compile:
	java -jar Resources/jtb132di.jar MiniJava.jj
	java -jar Resources/javacc5.jar MiniJava-jtb.jj
	javac Main.java

execute:
	java Main

clean:
	rm -f *.class *~
