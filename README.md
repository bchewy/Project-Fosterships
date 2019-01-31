# Project Fosterships
Project Fosterships is an idea to help foster worthwhile relationships with groups of people who attend camp/events by breaking the ice while promoting a sense of belonging amongst themselves.

[Read more on our hosted site!](http://projectfosterships.ml)

2nd Runner Up for ICT Byte Hackathon Organised by Portfolio II & Overflow.

## Build Status
1. Master Branch (Stable) - [![Build Status](https://travis-ci.org/bchewy/Project-Fosterships.svg?branch=master)](https://travis-ci.org/bchewy/Project-Fosterships)
2. [Development Branch](https://github.com/bchewy/Project-Fosterships/tree/development) (Unstable/Unusable) - [![Build Status](https://travis-ci.org/bchewy/Project-Fosterships.svg?branch=production)](https://travis-ci.org/bchewy/Project-Fosterships)

### Libaries/dependancies
Gradle Version : 4.6\
Android Plugin Version : 3.2.1\
Android API : 27-28\
(Gradle Module: app)
```java
    //Firebase dependencies
    implementation 'com.google.firebase:firebase-core:16.0.5'
    implementation 'com.firebaseui:firebase-ui-storage:4.2.1'
    implementation 'com.google.firebase:firebase-database:16.0.4'
    implementation 'com.google.firebase:firebase-storage:16.0.4'

    //Glide & others
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
```
(Gradle Module: project)
```java
buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.1'
        

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath 'com.google.gms:google-services:4.0.1'
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

## Tools & Libaries
- Travis-CI
- Glide module
- Firebase
- Google Cloud Platform

#### Special Thanks!
* Mr Wesley Teo (@westwq)

Licensed under The MIT License
