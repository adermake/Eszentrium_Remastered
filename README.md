# Eszentrium_Remastered

## Development Setup
1. In die eigene settings.xml-Datei (auf Windows unter `%homepath%/.m2/settings.xml`) folgendes eintragen und FTP-Upload-Username und -Passwort eintragen:
```xml
<settings>
  <servers>
    <server>
      <id>esze_hostunlimited</id>
      <username>[HIER USERNAME EINSETZEN]</username>
      <password>[HIER PASSWORT EINSETZEN]</password>
    </server>
  </servers>
 </settings>
```
2. [Aktuellste Spigot BuildTools](https://hub.spigotmc.org/jenkins/job/BuildTools/) herunterladen, ausführen und mit folgenden Parametern compilen:
   - Im Tab General:
     - Version: 1.21.1
   - Im Tab Options:
     - Generate Remapped Jars
     - Compilation Options: SPIGOT und CRAFTBUKKIT
3. Projekt in IntelliJ clonen
4. Codeänderungen machen
5. Projekt mit `mvn clean package install` bauen