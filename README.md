# Raport Mistrza Zmianowego
 
The programme was made as an apprenticeship to facilitate and modernise certain processes in the company regarding the input of reports. Thanks to this programme, the company started storing its reports in a database and not on paper

I used JavaFX with Maven to produce the programme. I implemented the mysql-connector-java library to connect to the database and itextpdf to generate 2 PDF templates

How to launch a project:<br>
1. You need to create a database on the server named report_shiftmaster and implement the tables from a file named <code>report_shiftmaster.sql</code> from the <code>database</code> folder<br>
2. You must add a line to the VM options: <code>--add-exports javafx.base/com.sun.javafx.event=org.controlsfx.controls</code><br>
3. After starting the application, you need to go to settings and set the location to the database on the server and set the user with which you will log into the database<br>
4. Then log in using any of the previously created user accounts in the database and start using the programme<br>
