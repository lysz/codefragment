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