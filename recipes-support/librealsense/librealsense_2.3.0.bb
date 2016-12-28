SUMMARY = "A cross-platform library for capturing data from the RealSense F200, SR300 and R200 cameras"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "libusb1"
RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-tests = "librealsense"

#SRC_URI = "https://github.com/IntelRealSense/librealsense/archive/v${PV}.tar.gz"
#SRC_URI[md5sum] = "27ce627c02731623c23894baeb73b2b1"
#SRC_URI[sha256sum] = "ee41ecb493b0b5ccf9c413b49529cb880b305188923e343a96b4e1f28982f9e0"

SRC_URI = "git://github.intel.com/PerCHW/librealsense.git;branch=ds5_new;protocol=http"
SRCREV = "${AUTOREV}"
PR = "r0"

#S = "${WORKDIR}/${PN}-${PV}"
S = "${WORKDIR}/git"

inherit pkgconfig cmake

BACKEND ?= "V4L2"

EXTRA_OECMAKE = " \
	-DBACKEND=${BACKEND} -DRS_USE_${BACKEND}_BACKEND=true \
	-DBUILD_EXAMPLES:BOOL=${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'true', 'false', d)} \
	-DCMAKE_SKIP_RPATH=true \
	-DCMAKE_BUILD_TYPE=Release \
	-DCMAKE_CXX_FLAGS='${CMAKE_CXX_FLAGS} -std=c++11' \
"

do_install_append () {
	install -d "${D}${sysconfdir}/udev/rules.d"
	install -m 0644 ${S}/config/99-realsense-libusb.rules ${D}${sysconfdir}/udev/rules.d
	install -d "${D}${bindir}"
	install -m 755 ${B}/unit-tests/*-test ${D}${bindir}
}

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-tests"

FILES_${PN} = "${libdir}/* ${sysconfdir}/udev/rules.d/*"
FILES_${PN}-dev += "${includedir}/${PN}"

TMP_FILES_${PN}-examples += "\
	${bindir}/c-tutorial-1-depth \
	${bindir}/cpp-data-collect \
	${bindir}/cpp-enumerate-devices \
	${bindir}/cpp-headless \
	${bindir}/cpp-terminal \
	${bindir}/cpp-tutorial-1-depth \
"

TMP_FILES_${PN}-graphical-examples += "\
	${bindir}/c-tutorial-2-streams \
	${bindir}/cpp-capture \
	${bindir}/cpp-config-ui \
	${bindir}/cpp-multicam \
	${bindir}/cpp-pointcloud \
	${bindir}/cpp-tutorial-2-streams \
"

FILES_${PN}-tests += "\
	${bindir}/live-test \
"

python () {
	if bb.utils.contains("DISTRO_FEATURES", "x11 opengl", True, False, d):
		pn = d.getVar("PN", True)

		# add packages for examples and tests
		packages = d.getVar("PACKAGES", True)
		extra_packages = [ pn + "-examples", pn + "-graphical-examples" ]
		d.setVar("PACKAGES", packages + " " + " ".join(extra_packages))

		# package the files properly to examples and tests
		files_examples = d.getVar("TMP_FILES_" + pn + "-examples", False)
		d.setVar("FILES_%s-examples" % pn, files_examples)
		files_graphical_examples = d.getVar("TMP_FILES_" + pn + "-graphical-examples", False)
		d.setVar("FILES_%s-graphical-examples" % pn, files_graphical_examples)

		# set depends and rdepends
		d.appendVar("DEPENDS", " libpng libglu glfw")
		d.setVar("RDEPENDS_%s-examples" % pn, "librealsense")
		d.setVar("RDEPENDS_%s-graphical-examples" % pn, "libgl-mesa librealsense")
}
