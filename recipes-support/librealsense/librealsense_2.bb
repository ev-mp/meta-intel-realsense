SUMMARY = "A cross-platform library for capturing data from the RealSense RS4XX and SR300 cameras"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "libusb1"
RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-tests = "librealsense"

PV = "2.5.7_PRQ"
KBRANCH ?= "master"
SRC_URI = "git://github.intel.com/PerCHW/librealsense.git;branch=${KBRANCH};protocol=http;tag=v${PV}"

S = "${WORKDIR}/git"

inherit pkgconfig cmake

BACKEND ?= "V4L2"

EXTRA_OECMAKE = " \
	-DBACKEND=${BACKEND} -DRS_USE_${BACKEND}_BACKEND=true \
	-DBUILD_EXAMPLES:BOOL=${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'true', 'false', d)} \
	-DCMAKE_SKIP_RPATH=true \
	-DCMAKE_BUILD_TYPE=Release \
"

do_install_append () {
	install -d "${D}${sysconfdir}/udev/rules.d"
	install -m 0644 ${S}/config/99-realsense-libusb.rules ${D}${sysconfdir}/udev/rules.d
	install -d "${D}${bindir}"
	install -m 755 ${B}/unit-tests/*-test ${D}${bindir}
}

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-tests"

#FILES_${PN} = "${libdir}/* ${sysconfdir}/udev/rules.d/*"
#FILES_${PN}-dev += "${includedir}/${PN}"
FILES_${PN} = "${libdir}/lib*${SOLIBS} ${sysconfdir}/udev/rules.d/*"
FILES_${PN}-dev += "${libdir}/lib*${SOLIBSDEV} ${includedir}/${PN}"

#FILES_${PN}-examples += "\
#	${bindir}/c-tutorial-1-depth \
#	${bindir}/cpp-data-collect \
#	${bindir}/cpp-enumerate-devices \
#	${bindir}/cpp-fw-logger \
#	${bindir}/cpp-headless \
#	${bindir}/cpp-terminal \
#	${bindir}/cpp-tutorial-1-depth \
#"

TMP_FILES_${PN}-examples += "\
	${bindir}/c-tutorial-1-depth \
	${bindir}/cpp-data-collect \
	${bindir}/cpp-enumerate-devices \
	${bindir}/cpp-fw-logger \
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
