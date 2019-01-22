bbh-cli
=======
Beehive Booking Helper CLI.

How to build locally
--------------------
Build a fat jar using gradle.

    ./gradlew clean shadowJar
    
Create a reflection configuration file for picocli.

```bash
wget https://github.com/remkop/picocli/releases/download/v3.9.1/picocli-all-3.9.1.zip -O picocli-all-3.9.1.zip
```
```bash
unzip picocli-all-3.9.1.zip
```
```bash
java \
    -cp 'picocli-shell-jline3-all-3.9.1/picocli-3.9.1.jar:picocli-shell-jline3-all-3.9.1/picocli-codegen-3.9.1-tests.jar:picocli-shell-jline3-all-3.9.1/picocli-codegen-3.9.1.jar:build/libs/bbhelper.cli-1.0-SNAPSHOT-all.jar' \
    picocli.codegen.aot.graalvm.ReflectionConfigGenerator \
    jp.gr.java_conf.hhiroshell.bbhelper.cli.Main \
    jp.gr.java_conf.hhiroshell.bbhelper.cli.Login \
    jp.gr.java_conf.hhiroshell.bbhelper.cli.Logout \
    jp.gr.java_conf.hhiroshell.bbhelper.cli.Search \
    > reflectconfig-picocli.json
```
  
Create a native image.

    native-image \
        -jar build/libs/bbhelper.cli-1.0-SNAPSHOT-all.jar \
        -H:ReflectionConfigurationFiles=reflectconfig-picocli.json,reflectconfig-additional.json \
        -H:+ReportUnsupportedElementsAtRuntime \
        --static --no-server --enable-https

Links
-----

- About https support
    * https://blog.taylorwood.io/2018/10/04/graalvm-https.html
    * https://github.com/oracle/graal/blob/master/substratevm/URL-PROTOCOLS.md#https-support
    * https://github.com/oracle/graal/blob/master/substratevm/JCA-SECURITY-SERVICES.md#native-implementations