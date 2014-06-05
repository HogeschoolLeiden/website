# you need the following in /etc/sudoers for this script to work
# %group_name    ALL = (tomcat_user) NOPASSW: ALL
# replace <group_name> with the actual group name
# replace <tomcat_user> with the system tomcat user

TOMCATUSER=tomcat6
NOW=$(date +"%d-%m-%y-%H:%M:%S")
PID=$(pidof java)
PATH=/tmp

echo ""
echo "Total CPU usage: " >  $PATH/thread-dump-$NOW.ostd
/usr/bin/top -bn1 -H -p $PID | /usr/bin/awk '{ SUM += $9} END { print SUM }' >>  $PATH/thread-dump-$NOW.ostd
echo "" >> $PATH/thread-dump-$NOW.ostd
echo ""
echo "top -bn1 -H -p $PID > $PATH/thread-dump-$NOW.ostd"
/usr/bin/sudo -u $TOMCATUSER top -bn1 -H -p $PID >> $PATH/thread-dump-$NOW.ostd
echo "jstack -l $PID > $PATH/thread-dump-$NOW.tdump"
/usr/bin/sudo -u $TOMCATUSER jstack -l $PID > $PATH/thread-dump-$NOW.tdump

echo ""
echo "to remove the files use the following command"
echo "sudo -u $TOMCATUSER rm -f $PATH/thread-dump-$NOW.*"