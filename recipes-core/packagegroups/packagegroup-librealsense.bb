SUMMARY = "librealsense for Upboard with Yocto"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
    librealsense \
    librealsense-examples \
    librealsense-graphical-examples \
    librealsense-tests \
"
