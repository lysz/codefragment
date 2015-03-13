#!/bin/bash

echo "start......"

set -x

yesterday=$(date "+%Y-%m-%d" -d "-1days")
current=$(date "+%Y-%m-%d")

#yesterday=2014-12-06
#current=2014-12-07



mysql_yesterday=\'${yesterday}\'
mysql_current=\'${current}\'

#host_59_mount_dir=/wifiapp/workspace/WIFI/WebRoot/images/user/
host_59_to_58_dir=/wifiapp/export_data/

#host_58_mount_dir=/wifiapp/workspace/WIFI/WebRoot/hotapp/
#host_29_mount_dir=/wifiapp/workspace/WIFI/WebRoot/hotapp/

rm -rf ${host_59_to_58_dir}*.dat

echo "beginning to dump mysql data....."

/usr/local/mysql/bin/mysql -h 192.168.0.36 -uroot -paoramarket <<EOC
use wifiapp2;
select * from tbl_wifi_logs where indate > ${mysql_yesterday} and indate < ${mysql_current} into outfile "${host_59_to_58_dir}${yesterday}_tbl_wifi_logs.dat" fields terminated by '^' lines terminated by '\r\n';
select id, uid, wifi_mac, wifi_type, city, indate,longitude,latitude,channel from tbl_wifi_use_succ_logs where indate > ${mysql_yesterday} and indate < ${mysql_current}  into outfile "${host_59_to_58_dir}${yesterday}_tbl_wifi_use_succ_logs.dat" fields terminated by '^' lines terminated by '\r\n';
select id, uid, mac_address, wifi_type, indate, score, uid from tbl_wifi_share_account_logs where indate > ${mysql_yesterday} and indate < ${mysql_current} into outfile "${host_59_to_58_dir}${yesterday}_tbl_wifi_share_account_logs.dat" fields terminated by '^' lines terminated by '\r\n';
select id, mac_address, indate, createby_client_id, city, wifi_type,channel,longitude,latitude,gaode_longitude,gaode_latitude from tbl_wifi_share_account where indate > ${mysql_yesterday} and indate < ${mysql_current}  into outfile "${host_59_to_58_dir}${yesterday}_tbl_wifi_share_account.dat" fields terminated by '^' lines terminated by '\r\n';
select id,uid,reg_account,indate,status,remark,score,signdays,image,use_minute,channel from tbl_user_account where indate > ${mysql_yesterday} and indate < ${mysql_current} into outfile "${host_59_to_58_dir}${yesterday}_tbl_user_account.dat" fields terminated by '^' lines terminated by '\r\n';
select id,account,status,score,use_minute,channel,level,indate from tbl_reg_account where indate > ${mysql_yesterday} and indate < ${mysql_current} into outfile "${host_59_to_58_dir}${yesterday}_tbl_reg_account.dat" fields terminated by '^' lines terminated by '\r\n';
exit
EOC


echo "end to dump mysql data...."

cd $host_59_to_58_dir
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_wifi_logs.dat > win_${yesterday}_tbl_wifi_logs.dat
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_wifi_use_succ_logs.dat >win_${yesterday}_tbl_wifi_use_succ_logs.dat
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_wifi_share_account_logs.dat > win_${yesterday}_tbl_wifi_share_account_logs.dat
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_wifi_share_account.dat > win_${yesterday}_tbl_wifi_share_account.dat
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_user_account.dat > win_${yesterday}_tbl_user_account.dat
iconv -c -f UTF-8 -t GB18030 ${yesterday}_tbl_reg_account.dat > win_${yesterday}_tbl_reg_account.dat

echo "end to convert"
rm -rf ${yesterday}_*.dat
#rm -rf ${yesterday}*.txt
#rm -rf ${host_29_mount_dir}*.txt

#mv *.txt ${host_29_mount_dir}

ftp -n <<!
open 192.168.0.29
user lili lili055
prompt
bin
cd /resource/export_data_59/to_ox
lcd ${host_59_to_58_dir}
mput *.dat
bye
!


set +x
echo "+++++++++++++++++++++++ ${current}+++++++++++++++++++++++++++++++++++"
