Yocto meta-layer for RealSense
============================
This meta-layer adds the packages necessary to support Intel® RealSense RS4XX  depth cameras.The support is provided by means of Intel cross-platform [LibRealSense API](https://github.intel.com/PerCHW/librealsense).
This layer can later be extended to include RealSense [Linux SDK](https://github.com/IntelRealSense/realsense_sdk) and Middleware to your Yocto-based distribution.

Note for OEM - that LibrealSense library for RS4xx devices is not in publicly available at this time, and accessing it requires ```github.intel.com```.
Please refer to your Intel POC for details.

## Dependencies
This layer depends on packages provided by the following layers:
* `meta-openembedded` [http://cgit.openembedded.org/meta-openembedded/]

In addition, LibrealSense build recipe is dependent on the following packages:
* `libusb` and `libpng` - provide LibRealSense core
* `glfw` - is required for GUI-based demos and tutorials

Note for Yocto maintainers: `glfw`



Usage

### Ostro OS
1. Checkout the `master` branch to your project directory
2. Add the `meta-intel-realsense` layer to `conf/bblayers.conf` in your `build` directory
```bitbake
	OSTRO_LAYERS += "path/to/meta-intel-realsense"
```
3. If you are building an image with a graphical desktop you can add the following to `conf/auto.conf`
```bitbake
    CORE_IMAGE_EXTRA_INSTALL += "librealsense-graphical-examples"
```

### Building a reference Ostro image with this layer embedded:
1. sdfgsdgfsf
2. sfdsfsf
`   abc
`
``
def
``
```
ghi
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
