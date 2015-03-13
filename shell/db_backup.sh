week=`date -d '1 days ago' +%w`

/usr/local/mysql/bin/mysqldump -h 192.168.0.36 -uroot -paoramarket wifiapp2 |gzip >/wifiapp/db_backup/wifiapp2_$week.gz
#/usr/local/mysql/bin/mysqldump -u$DB_USER -p$DB_PASS --$DB_NAME | gzip > /backup/mysqlbackup/all_$BACKUP_DATE.gz
#gzip -f  /wifiapp/db_backup/wifiapp2_$week.sql

