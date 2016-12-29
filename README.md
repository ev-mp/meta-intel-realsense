Yocto meta-layer for RealSense - RS4xx Devices Prototype
============================
This meta-layer adds the packages necessary to support Intel® RealSense RS4XX  depth cameras.The support is provided by means of Intel cross-platform [LibRealSense API](https://github.intel.com/PerCHW/librealsense).
This layer can later be extended to include RealSense [Linux SDK](https://github.com/IntelRealSense/realsense_sdk) and Middleware to your Yocto-based distribution.

Note for OEM -  LibrealSense API for RS4xx devices is not in publicly available at this time, and accessing it requires ```github.intel.com```.
Please refer to your Intel POC for details.

## Dependencies
This layer depends on packages provided by the following layers:
* `meta-openembedded` [http://cgit.openembedded.org/meta-openembedded/]

In addition, LibrealSense build recipe is dependent on the following packages:
* `libusb`and `libpng` are required to build LibRealSense core.
* `glfw` - is required for GUI-based demos and tutorials

Note for Yocto maintainers: `glfw` is an OpenGl wrapper library that requires a desktop environemnt, such as 'x11'


### Integration with Yocto-based Distributions
1. Checkout the `master` branch to your project directory
2. Add the `meta-intel-realsense` layer to `conf/bblayers.conf` in your `build` directory
```bitbake
	OSTRO_LAYERS += "path/to/meta-intel-realsense"
```
3. If you are building an image with a graphical desktop you can add the following to `conf/auto.conf`
```bitbake
    CORE_IMAGE_EXTRA_INSTALL += "librealsense-graphical-examples"
```

### Reference Ostro image with Intel® RealSense layer embedded:
1. [Ostro-xt](https://github.com/ostroproject/ostro-os-xt) is a distribution image derived from Yocto that enables X11 and XFCE Desktop and provides support for RealSense devices prior to RS4xx line.
2. A dedicated ostro-xt branch for this meta-layer validation is publicly available [here](https://github.com/ev-mp/ostro-os-xt-1/tree/ds5_proto)

#### Setup and Build Instructions:
* Prerequisites:
	-	Make sure the Build machine is up to date:
	- Ubuntu-based Desktop/Server.
	- 8+ cores are strongly recommended.
	- RAM 16Gb. Hard Disk with 60Gb free-space available


* Prepare Environment :
	-	Make sure the Build machine is up to date:
		```
		$sudo apt-get update && dist-upgrade
		```
	-	Install the Yocto-build required packages:
		```
		$sudo apt-get install aptitude chrpath gawk git gnome-commander meld texinfo libstdc++6 gawk wget git-core diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm
		```

* Build:
	-	Download Ostro-xt distribution with Intel-meta-layer:
		```
		$git clone --recursive -b ds5_proto https://github.com/ev-mp/ostro-os-xt-1 ./ostro-build/
		$cd ostro-build
		```
	-	Initialize Yocto build environment:
	```
	$source ostro-init-build-env
	```
	-	Select build image configuration and the required distribution image format
	```
	$sed -i '/#require conf\/distro\/include\/ostro-os-development.inc/a require conf/distro/include/ostro-os-development.inc' conf/local.conf
	$sed -i '/#OSTRO_VM_IMAGE_TYPES =/a OSTRO_VM_IMAGE_TYPES ="dsk.xz dsk.bmap"' conf/local.conf
	```
	-	Activate Ostro build
	```
	$bitbake ostro-xt-image-swupd
	```
------
Ostro Distribution build process is ~2-4 hours long, depending on the Host hardware spec and internel connection.

The resulted image will be located in `ostro-build/build/tmp-glibc/deploy/images/intel-corei7-64` directory.

In order to build cross-compiler toolchain for Off-target software development use the following command:
```
$bitbake ostro-xt-image-swupd -c populate_sdk
```


License
=======
Copyright 2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
```bitbake
	 http://www.apache.org/licenses/LICENSE-2.0
```
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
