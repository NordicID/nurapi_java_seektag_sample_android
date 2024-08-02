# nurapi_java_seektag_sample_android
###  SeekNearestTag Sample for NurApi
This repository contains a sample Android application that demonstrates the use of the NurApi to seek the nearest RFID tag. The application is developed using Android Studio and showcases how to integrate and utilize the NurApi and NurApi Utils libraries to perform tag seeking operations.

### Features
- Connect to an integrated Nordic ID RFID reader (HH83 or HH85).
- Seek the nearest RFID tag using the SeekNearestTag utility.
- Log various events and statuses during the tag seek process. These will appear to LogCat.

### Files

- [MainActivity.java](https://github.com/NordicID/nurapi_java_seektag_sample_android/blob/main/src/app/src/main/java/com/example/myapplication/MainActivity.java): The initial setup code to get Nur started and registration of an Nur event listener.
- [NurApiEventListener.java](https://github.com/NordicID/nurapi_java_seektag_sample_android/blob/main/src/app/src/main/java/com/example/myapplication/NurApiEventListener.java): Used for listening for various Nur api events, for this sample, we use the connected event to start a seek nearest tag.
