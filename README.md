# musicapp
Web-Application url http://13.58.68.235:8080/music-app/ 
Deployed to AWS EC-2 instance

Tehnologies stack:
  - PostgreSQL
  - Java 12,
  - JDBC,
  - JSP, Java EE, 
  - Junit 5, Test Containers, 
  - Bootstrap, Css, jQuery,
  - Maven, 
  - Apache Tomcat, 
  - AWS, 
  - Docker, 
  - Design Patterns (MVC, Command, Repositoy/Specification, Chain of Responsibility and others)
  

Open source, music-listening application.
The project represents music-library with oportunity for users to aggregate tracks in playlists. 

Application use-cases.
Possible roles and their specialities:

- User (Registered user of application): 
  - gets 3 months free-trial, afterwards that he must pay to renew his subscription
  - listen to the music,
  - search/filter music,
  - use library to easily navigate throw tracks, musicians and playlists 
  - create/update/remove personal playlists
  - add/remove track to/from playlist
  - has a personal profile (with option to update personal information)
 
- Admin 
  - all facilities of user
  - upload/update/remove tracks to application
  - add/remove/update singers and authors
  - create public and personal playlists
  - use the user summary table (with filter)
  - register another admin
  - some more sorting options in library
  
- Guest (Unregistered user)
  - register as user
  - search and filter music
  - listen music
  
  
  # Getting started
  
  ## Prerequisites:
  - PostgreSQL 11
  - Maven
  - JDK 11
  - Docker for testcontainers
  - Jenkins
  - JUnit 5
  - tomcat 9
  
  ### tomcat start 
  `sh <userdir>/tomcat/9.0.22/libexec/bin/startup.sh`
  
  ##### change tomcat port if needed:
  1) Locate server.xml in {Tomcat installation folder}\ conf \
  
  2) Find the line similar to this -
  
  <Connector port="8080" protocol="HTTP/1.1" 
             connectionTimeout="20000" 
             redirectPort="8443" />
  Change 8080 to some other port number like 8181
  
  3) Restart tomcat.
  
  ### postgres start
  `pg_ctl -D /Library/PostgreSQL/11/data start`
  
  specify your directory path
  
  `psql -U postgres -c "create database music"`
  if database music does not exist
  
  `psql -U postgres -d music -a -f <path/to/initTables.sql>`
  `psql -U postgres -d music -a -f <path/to/initDefaultUsers.sql>`
  
  ### docker start
  
 `open --background -a Docker`
  
  ### jenkins start
  `java -jar Applications/Jenkins/jenkins.war -httpPort=9090`
  
  specify another port if needed within last flag option
  
  ### sonar start (optionally)
  `sh <userdir>/sonarqube-7.7/bin/macosx-universal-64/sonar.sh start`
  
  specify path to sonar.sh file as shown on example
  
  ### postgres init music db (if needed)
  
  ### postgres init default users (if needed)
  
  
