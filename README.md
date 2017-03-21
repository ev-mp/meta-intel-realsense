Librealsense2 meta-layer for Up Board distribution based on Poky 2.12 release
============================
This meta-layer adds the packages necessary to support Intel® RealSense RS400/RS410/RS430/RS450 depth cameras.The support is provided by means of Intel cross-platform [LibRealSense API](https://github.intel.com/PerCHW/librealsense).
This layer can later be extended to include RealSense [Linux SDK](https://github.com/IntelRealSense/realsense_sdk) and Middleware stack to your Yocto-based distribution.

Note for OEM -  LibrealSense API for RS4xx devices is not in publicly available at this time, and accessing it requires ```github.intel.com```.
Please refer to your Intel POC for details.

## Dependencies
This layer is used as extension for Poky (Yocto-based) distribution for Up Board
* [`meta-up-board`](https://github.com/emutex/meta-up-board)


In addition, LibrealSense build recipe is dependent on the following packages:
* `libusb`and `libpng` are required to build LibRealSense core.
* `glfw` - is required for GUI-based demos and tutorials

A note for Yocto maintainers: `glfw` is an OpenGl wrapper library that requires a desktop GUI environment ('x11')


### Setting up Build environment and Integration with Poky meta-layers
* Follow the 'Up Board' build environment  [instruction](https://github.com/emutex/meta-up-board/tree/krogoth)  provided.
	Perform all stages to configure the workstation for building Poky distro,
	except for invoking the actual build ('bitbake ...')

* Navigate to '../poky' directory that has the following structure
```
.
└── poky
    ├── bitbake
    ├── build
    ├── documentation
    ├── meta
    ├── meta-intel
    ├── meta-poky
    ├── meta-selftest
    ├── meta-skeleton
    ├── meta-up-board
    ├── meta-yocto
    ├── meta-yocto-bsp
    └── scripts
```
* Download LibRealSense meta-layer
```bitbake
	git clone -b poky_krogoth https://github.com/ev-mp/meta-intel-realsense.git
```
	* to create the following entry
```
.
└── poky
    ...
    ├── meta-intel-realsense
    ...
```

### Adding Librealsense recipes to Poky distro

* Add `meta-intel-realsense` layer to `build/conf/bblayers.conf` in your `build` directory
```bitbake
	OSTRO_LAYERS += "<path_to>/meta-intel-realsense"
```
* Specify Librealsense packages to be added to distro adding the line to `build/conf/auto.conf`
```
CORE_IMAGE_EXTRA_INSTALL += "librealsense librealsense-examples"
```
	* In case of building image with a graphical user interface, e.g. `sato`,
	you can also add Librealsense demos with OpenGl support
	```
	CORE_IMAGE_EXTRA_INSTALL += "librealsense librealsense-examples librealsense-graphical-examples"
	```

Proceed to distro build with the original [instruction](https://github.com/emutex/meta-up-board/tree/krogoth)


License
=======
Copyright 2017 Intel Corporation

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
