all:
	javac -cp nept -sourcepath . -d bin -Xlint org/kaivos/röda/Röda.java

röda.jar:
	jar cvfm röda.jar Manifest.txt -C bin/ . -C nept/ org/kaivos/nept/
