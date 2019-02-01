# ben :coffee:

[![Build Status](https://travis-ci.org/juanpablolvl99/ben.svg?branch=master)](https://travis-ci.org/juanpablolvl99/ben)
[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)

### This is a very basic BitTorrent encoding parser created in Java.

>To use this lib in your maven project do the following steps.

1. ```git clone https://github.com/juanpablolvl99/ben && cd ben```
2. ```mvn clean package```
3. ```mkdir <maven-project-path>/lib```
4. ```mv target/ben-1.0.jar <maven-project-path>/lib/```

>After that add the following dependency to your pom.xml.
```
<dependency>
    <groupId>com.torrent.ben</groupId>
    <artifactId>ben</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${pom.basedir}/lib/ben-1.0.jar</systemPath>
</dependency>
```

### TODO:

1. ~~Create Tests.~~
2. Add Test coverage.
3. ~~Update Readme with a "use dependency guide".~~

---
Feel free to fork and improve.
