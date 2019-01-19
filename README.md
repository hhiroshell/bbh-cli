bbh-cli
=======
Beehive Booking Helper CLI.

How to build locally
--------------------
Build a fat jar using gradle.

    ./gradlew clean shadowJar

Create a native image.

    native-image -jar build/libs/bbhelper.cli-1.0-SNAPSHOT-all.jar --enable-https

Links
-----

- About https support
    * https://blog.taylorwood.io/2018/10/04/graalvm-https.html
    * https://github.com/oracle/graal/blob/master/substratevm/URL-PROTOCOLS.md#https-support
    * https://github.com/oracle/graal/blob/master/substratevm/JCA-SECURITY-SERVICES.md#native-implementations