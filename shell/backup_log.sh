#lastdate=`TZ=GMT+$((-8+24)) date '+%Y-%m-%d'`
#tabdate=`TZ=GMT+$((-8+24)) date '+%Y%m%d'`

lastdate=`date -d '-7 day' '+%Y-%m-%d'`
tabdate=`date -d '-7 day' '+%Y%m%d'`


#delete from tbl_wifi_logs where indate between convert('$lastdate 00:00:00',datetime) and convert('$lastdate 23:59:59',datetime);

/usr/local/mysql/bin/mysql -h 192.168.0.36 -uwifi -pwifiapp  -e "
use wifiapp2;
create table tbl_wifi_logs$tabdate like tbl_wifi_logs;
insert into tbl_wifi_logs$tabdate   select *  from tbl_wifi_logs where indate between convert('$lastdate 00:00:00',datetime) and convert('$lastdate 23:59:59',datetime);
delete from tbl_wifi_logs where indate between convert('$lastdate 00:00:00',datetime) and convert('$lastdate 23:59:59',datetime);
quit"

