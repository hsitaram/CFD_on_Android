LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := tridriver
LOCAL_SRC_FILES := globals.cpp common.cpp edge.cpp laplace_solver.cpp polygon.cpp triangle.cpp triangulation.cpp trielement.cpp tridriver.cpp

include $(BUILD_SHARED_LIBRARY)
