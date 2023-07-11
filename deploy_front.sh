#bin/bash
#备份文件保存路径
front_target=/home/project/wenshang/bakws
#前端文件所在路径
front_dir=/home/project/wenshang/village-admin

if [ ! -e "${front_target}" ]; then
    mkdir -p ${front_target}
fi

if [ -e "${front_dir}" ]; then
    cp ${front_dir} "${front_target}/village-ws-bak-$(date "+%Y%m%d%H%M%S")"
fi



