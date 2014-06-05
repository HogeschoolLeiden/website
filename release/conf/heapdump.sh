# you need the following in /etc/sudoers for this script to work
# %group_name    ALL = (tomcat_user) NOPASSW: ALL
# replace <group_name> with the actual group name
# replace <tomcat_user> with the system tomcat user

TOMCATUSER=tomcat6
NOW=$(date +"%d-%m-%y-%H:%M:%S")
PID=$(pidof java)
PATH=/tmp

echo ""
echo "jmap -dump:file=./heap-dump-$NOW.hprof $1"
/usr/bin/sudo -u $TOMCATUSER jmap -dump:file=$PATH/heap-dump-$NOW.hprof $PID

echo ""
echo "to remove the files use the following command"
echo "sudo -u $TOMCATUSER rm -f $PATH/thread-dump-$NOW.*"