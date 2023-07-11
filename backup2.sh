#!/bin/bash

#所需备份的文件名称与路径
backup_file_resource="village-yt.jar"
backup_file_resource_dir="/opt"

# 所需备份的文件夹名称与路径
backup_dir_resource=""
backup_dir_resource_dir=""

#backup_file_dir="/home/tanchengjin/Desktop/"
#backup_dir_in_path="/home/tanchengjin/Desktop/"
# 指定备份的文件名称
#backup_file_des="backup-$(date +%Y-%m-%d-%H-%M-%S).tar.gz"
#backup_file_="backup-$(date +%Y-%m-%d-%H-%M-%S).tar.gz"

# 指定备份文件夹
#backup_dir="/home/tanchengjin/Desktop/tmp"

# 备份文件的存储路径
backup_dir_destination="/root/bak"

# 定义备份目录和文件名
#backup_dir="/home/user/backup"
backup_file_name="$(date +%Y%m%d%H%M%S).tar.gz"

log_file_name="out.log"

log_file_name_full_path="${backup_file_resource_dir}"/"${log_file_name}"

Green_font_prefix="\033[32m" && Red_font_prefix="\033[31m" && Green_background_prefix="\033[42;37m" && Red_background_prefix="\033[41;37m" && Font_color_suffix="\033[0m"
Info="${Green_font_prefix}[信息]${Font_color_suffix}"
Error="${Red_font_prefix}[错误]${Font_color_suffix}"
Tip="${Green_font_prefix}[注意]${Font_color_suffix}"

backFile_Jar_Front() {
  # 创建备份目录（如果不存在）
  mkdir -p "${backup_dir_destination}"

  target="${backup_dir_destination}/backend-front-${backup_file_name}"

  # 执行备份
  #tar -czf "$backup_dir/$backup_file" "$backup_file_dir" -C"$backup_file_resource_dir" "$backup_file_resource"
  #使用-C不包含路径
  tar -czf "${target}" -C"$backup_file_resource_dir" "$backup_file_resource" -C"$backup_dir_resource_dir" "$backup_dir_resource"

  # 输出备份完成消息
  echo "备份已完成：${backup_dir_resource_dir}/${backup_dir_resource}   ======> ${target}"
  echo "备份已完成：${backup_file_resource_dir}/${backup_file_resource} ======> ${target}"
}

backFile_Jar() {
  # 创建备份目录（如果不存在）
  mkdir -p "${backup_dir_destination}"

 target="$backup_dir_destination/backend-$backup_file_name"

  # 执行备份
  #使用-C不包含路径
  tar -czf "${target}" -C"$backup_file_resource_dir" "$backup_file_resource"

  # 输出备份完成消息
  echo "备份已完成：${backup_file_resource_dir}/${backup_file_resource} ======> ${target}"
}

backFile_Front() {
  # 创建备份目录（如果不存在）
  mkdir -p "${backup_dir_destination}"

  # 执行备份
  target="$backup_dir_destination/front-$backup_file_name"
  #使用-C不包含路径
  tar -czf "${target}" -C"$backup_dir_resource_dir" "$backup_dir_resource"

  # 输出备份完成消息
  echo "备份已完成：${backup_dir_resource_dir}/${backup_dir_resource}   ======> ${target}"
}
Kill_Jar() {
  result=$(check_jar_pid)
  if [ -z "$result" ]; then
    echo "$backup_file_resource" is already stopped
  else
    echo kill $PID
    kill $PID
  fi
}

function check_jar_pid() {
  PID=$(ps -ef | grep "$backup_file_resource" | grep -v grep | awk '{ print $2 }')
  if [ -z "$PID" ]; then
    return 0
  else
    return 1
  fi
}
Start_Jar() {
  result=$(check_jar_pid)
  if [ -z "$result" ]; then
    nohup java -jar "$backup_file_resource" --spring.profiles.active=yt >"${log_file_name_full_path}" 2>&1 &
    tail -f "$log_file_name_full_path"
  else
    echo "${backup_file_resource} starting"
  fi
}

Restart_Jar() {
  result=$(check_jar_pid)
  if [ -z "$result" ]; then
    nohup java -jar "$backup_file_resource" --spring.profiles.active=yt >"${log_file_name_full_path}" 2>&1 &
    tail -f "$log_file_name_full_path"
  else
    echo kill $PID
    kill $PID

    nohup java -jar "$backup_file_resource" --spring.profiles.active=yt >"${log_file_name_full_path}" 2>&1 &
    tail -f "$log_file_name_full_path"
  fi
}

check() {
  if [[ -e ${backup_dir_resource_dir}/${backup_dir_resource} ]]; then
    echo && echo -e "${backup_dir_resource_dir}/${backup_dir_resource} ${Green_font_prefix}yes${Font_color_suffix}"
  else
    echo && echo -e "${backup_dir_resource_dir}/${backup_dir_resource} ${Red_font_prefix}no${Font_color_suffix}"
  fi

  if [[ -e ${backup_file_resource_dir}/${backup_file_resource} ]]; then
    echo && echo -e "${backup_file_resource_dir}/${backup_file_resource} ${Green_font_prefix}yes${Font_color_suffix}"
  else
    echo && echo -e "${backup_file_resource_dir}/${backup_file_resource} ${Red_font_prefix}no${Font_color_suffix}"
  fi
  #检查move移动时的参数
  if [[ -e ${backup_dir_resource} ]]; then
    echo && echo -e "${backup_dir_resource} ${Green_font_prefix}yes${Font_color_suffix}"
  else
    echo && echo -e "${backup_dir_resource} ${Red_font_prefix}no${Font_color_suffix}"
  fi

  if [[ -e ${backup_file_resource} ]]; then
    echo && echo -e "${backup_file_resource} ${Green_font_prefix}yes${Font_color_suffix}"
  else
    echo && echo -e "${backup_file_resource} ${Red_font_prefix}no${Font_color_suffix}"
  fi
}

Deploy() {
  mv "${backup_file_resource}" "${backup_file_resource_dir}/${backup_file_resource}"
  mv "${backup_dir_resource}" "${backup_dir_resource_dir}/${backup_dir_resource}"
  Kill_Jar
  Start_Jar
}

echo && echo -e " 部署脚本

 ${Green_font_prefix}0.${Font_color_suffix} 检查
————————————
 ${Green_font_prefix}1.${Font_color_suffix} 备份文件与文件夹
 ${Green_font_prefix}2.${Font_color_suffix} 备份文件
 ${Green_font_prefix}3.${Font_color_suffix} 备份文件夹
————————————
 ${Green_font_prefix}4.${Font_color_suffix} 停止Jar包
 ${Green_font_prefix}5.${Font_color_suffix} 部署Jar包
 ${Green_font_prefix}6.${Font_color_suffix} 重启Jar包
 ${Green_font_prefix}7.${Font_color_suffix} 启动Jar包
————————————"

echo
read -e -p " 请输入数字 [0-4]:" num
case "$num" in
0)
  check
  ;;
1)
  backFile_Jar
  ;;
2)
  backFile_Jar
  ;;
3)
    backFile_Front
  ;;
4)
  Kill_Jar
  ;;
5)
  Deploy
  ;;
6)
  Restart_Jar
  ;;
7)
  Start_Jar
  ;;
*)
  echo "请输入正确数字 [0-9]"
  ;;
esac
