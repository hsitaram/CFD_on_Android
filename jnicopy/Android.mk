LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := nativehello
LOCAL_SRC_FILES := nativehello.cpp sampleclass.cpp

include $(BUILD_SHARED_LIBRARY)
