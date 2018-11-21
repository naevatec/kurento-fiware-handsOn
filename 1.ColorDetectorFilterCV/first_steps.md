# First steps


## 1. KMS Installation (dev) Installation (Ubuntu 16.04)

  ```
   # KMS for Ubuntu 16.04 (Xenial)
   DISTRO="xenial"

   # Add Kurento repository
   sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 5AFA7A83

   # Kurento Media Server - Release packages
   deb [arch=amd64] http://ubuntu.openvidu.io/6.8.1 $DISTRO kms6
   EOF

  ```

  Install dependencies: 

  ```
  sudo apt-get update
  sudo apt-get install kurento-media-server-dev

  ```

## 2. OpenCV module creation
  
  Create a workdirectory and execute:

  ```
  kurento-module-scaffold.sh ColorDetectorFilter colorDetector opencv_filter
  
  ```

  Modify `colorDetector/color-detector-filter/src/server/interface/colordetectorfilter.ColorDetectorFilter.kmd.json` to add the event. (check the provided json).

  Develop filter logic `colorDetector/color-detector-filter/src/server/implementation/objects/ColorDetectorOpenCVImpl.cpp` (check provided cpp).


  ### Generate the module code in the folder created by the scaffolder:
  

  Edit generated CMAKE with: 


  ```
  # Use "-fPIC" / "-fPIE" for all targets by default, including static libs
  set(CMAKE_POSITION_INDEPENDENT_CODE ON)

  ```

  And make

  ```
  mkdir build
  cd build
  cmake .. 

  ```

  ### Generate client code:

  ```
  #Java
  cmake .. -DGENERATE_JAVA_CLIENT_PROJECT=TRUE

  #JS
  make .. -DGENERATE_JS_CLIENT_PROJECT=TRUE

  ```

  ### Work-arround for Java 1.8

  Change the `build/java/pom.xml` to force Java 1.8 compilation
  
  ```
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.target>1.8</maven.compiler.target>
    <maven.compiler.source>1.8</maven.compiler.source>
  </properties>

  ```
  

  ### Compile the module
  

  ```
  make

  ```

  ### Install module java package
  

  ```
  make java_install

  ```
  
## 3. Restart Kurento with the module

  
  We have two options: 


  ### A. Generate and install debian package.

  You will need `debuild`

  ```
  sudo apt-get install debuild
  
  ```

  ```
   debuild -us -uc
   dpkg -i

  ```

  ### B. Let Kurento do the hard work.
  

  Configure in `/etc/default/kurento-media-server` the following enviromental variables

  ```
  KURENTO_MODULES_PATH=<module_path>/build/src
  GST_PLUGIN_PATH=<module_path>/build/src

  ``` 

  Restart Kurento

  ```
  sudo service kurento-media-server restart

  ```