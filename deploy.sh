#!/bin/bash

jar_dir="/home/blog"
jar_name=@build.finalName@.jar
deploy_jar_tmp_full_name="/home/blog/deploy/"${jar_name}
jar_full_name="${jar_dir}/${jar_name}"
dev_conf=@spring.application.profiles@
jar_log_dir=${jar_dir}/logs
jar_log=${jar_log_dir}/log.log
backup_dir=${jar_dir}/backup

#backup
if [ ! -e "${backup_dir}" ]; then
  mkdir -p ${backup_dir}
fi

if [ -e "${jar_full_name}" ]; then
  cp ${jar_full_name} "${backup_dir}/village-yt.jar$(date "+%Y%m%d%H%M%S")"
fi

#release
echo release ${jar_name}

if [ -e "${deploy_jar_tmp_full_name}" ]; then
  #    if [ -e "${jar_full_name}" ]; then
  mv ${deploy_jar_tmp_full_name} ${jar_full_name}
#    else
#        echo "${jar_full_name} 文件不存在"
#    fi
else
  echo "${deploy_jar_tmp_full_name} 文件不存在"
fi

sleep 1

#kill
PID=$(ps -ef | grep ${jar_name} | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]; then
  echo village-yt.jar is already stopped
else
  echo kill $PID
  kill $PID
fi

sleep 5

#start
echo starting..
if [ ! -e "${jar_log_dir}" ]; then
  mkdir -p ${jar_log_dir}
fi
nohup java -Xms256m -Xmx256m -XX:PermSize=128M -XX:MaxPermSize=256M -jar ${jar_full_name} --spring.profiles.active=${dev_conf} >${jar_log} 2>&1 &
tail -f ${jar_log}
