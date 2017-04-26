#!/bin/bash
###set -x 
#######################################################################
for j in `ls /var/lib/tomcat8/webapps/puzzle_cats/WEB-INF/lib/*.jar`
do 
  cp=${j}:${cp}
done
cp=${cp}/usr/share/tomcat8/lib/servlet-api.jar:/var/lib/tomcat8/lib/log4j-1.2.17.jar:
cp=${cp}.
###echo ${cp}
#######################################################################
for j in `find . -name "*.java"`
do
  cl=`echo ${j} | sed -e 's/java/class/g'`
  cl_stat=`stat -c %Y ${cl}`
  if [ $? -gt 0 ] || [ `stat -c %Y ${j}` -gt `stat -c %Y ${cl}` ]
  then
    echo "Compiling ${j}"
    javac -g:vars -classpath "${cp}" ${j}
###    javac -Xdiags:verbose -Xlint:unchecked -Xlint:deprecation -g:vars -classpath "${cp}" ${j}
  fi
done
chown -R tomcat8.tomcat8 /var/lib/tomcat8/webapps/puzzle_cats/
