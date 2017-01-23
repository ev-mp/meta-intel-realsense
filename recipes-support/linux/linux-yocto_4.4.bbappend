FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://uvc.cfg"
#SRC_URI += "file://librealsense_formats_${PN}_4.4.patch"
SRC_URI += "file://realsense-camera-formats_${PN}_4.4.patch"
SRC_URI += "file://realsense-hid_${PN}_4.4.patch"
SRC_URI += "file://realsense-metadata_${PN}_4.4.patch"
