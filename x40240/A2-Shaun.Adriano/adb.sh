#!/bin/sh
#
#
DEVICE_FLAG="$1"
adb $DEVICE_FLAG install -r ./bin/*.apk
if [ $? -ne 0 ]; then exit $?; fi
adb $DEVICE_FLAG logcat *:V
