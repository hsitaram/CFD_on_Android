LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := tridriver
LOCAL_SRC_FILES := tridriver.cpp

include $(BUILD_SHARED_LIBRARY)
