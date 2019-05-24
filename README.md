# chat
a easy chat online project based on JavaWeb and "ajax";

## Allocation
Eclipse -> 2019-03<br>
Jdk -> 11.0.3<br>
Tomcat -> 9.0<br>
mysql Ver 15.1 Distrib 10.3.13-Mariadb<br>

## Usage
you are the bigest one, you can add, delete user and room. This don't have a functin of 'register'.

## Others
I have used a datapool. You need add the  following to tomcat/conf/context.xml (if you use Eclipse you should add to /Servers/tomcat/context.xml). Besides don't forget to add connector of mysql to this project.

	<Resource
		name="jdbc/mariadb"
		auth="Container"
		type="javax.sql.DataSource"
		maxActive="100"
		maxIdle="30"
		maxWait="10000"
		username="my"
		password="1234"
		driverClassName="org.mariadb.jdbc.Driver"
		url="jdbc:mariadb://127.0.0.1:3306/chat"
		/>
    
# Others
Welcome to see my project! If you find something not correct, please contact me!
