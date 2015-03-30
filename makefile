all:
	mkdir -p out
	javac -d out -cp ./src/java:./lib/ojdbc6.jar:./apache-tomcat-6.0.18/lib/servlet-api.jar src/java/*.java

deploy:	
	javac -d ./apache-tomcat-6.0.18/webapps/ROOT/WEB-INF/classes/ -cp ./src/java:./lib/ojdbc6.jar:./apache-tomcat-6.0.18/lib/servlet-api.jar src/java/*.java
	cp -vr ./src/html/* ./apache-tomcat-6.0.18/webapps/ROOT/
	mkdir -p ./apache-tomcat-6.0.18/webapps/ROOT/WEB-INF/lib/
	cp -v ./lib/* ./apache-tomcat-6.0.18/webapps/ROOT/WEB-INF/lib/
