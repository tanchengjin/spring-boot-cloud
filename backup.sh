#!/bin/bash

v_time=`date "+%Y-%m-%d %H:%M:%S"`
bakstr="[Start auto backup gitlab data:]"${v_time}
logfile="/home/localgitlab/test/auto_back.txt"
echo >> $logfile
echo $bakstr >> $logfile

pathSrc="/home/localgitlab/disk_new/backup"
filesSrc=$(ls $pathSrc)

pathDst="/media/localgitlab/a1df1b08-ae43-45ea-8067-994497738cb2/backups"
filesDst=$(ls $pathDst)

nSrcFileCount=0

### Backup file: The file that determines the original path is not in the target path,
### and is not copied.
for filename in $filesSrc
do
    nSrcFileCount=$(( $nSrcFileCount + 1 ))
    need_copy="yes"
    for dstfile in $filesDst
    do
        if [ $filename == $dstfile ];then
            need_copy="no"
        fi
    done

    if [ $need_copy == 'yes' ];then
        echo $filename " need backup" >> $logfile
        strCompletePath=${pathSrc}'/'${filename}
        cp $strCompletePath $pathDst
        if [ $? -eq 0 ]; then
            echo "copy file success" >> $logfile
        else
            echo "copy file fail" >> $logfile
        fi
    else
        echo $filename " not need backup" >> $logfile
    fi
done

echo "Src dir have file count:" $nSrcFileCount >> $logfile

### Keep only data within 180 days
delSrc=$(find $pathSrc -type f -mtime +180)
for delfilename in $delSrc
do
    echo "Need delete file: " $delfilename >> $logfile
    rm -f $delfilename
    if [ $? -eq 0 ]; then
        echo "delete file success" >> $logfile
    else
        echo "delete file fail" >> $logfile
    fi
done