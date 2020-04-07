# Action Pack Development Kit
This project enables Action Pack development in Java. No more need to deal with all the Automic Automation Engine objects from AWI. Full focus on developing reusable building blocks for your automated processes leveraging your existing coding best practices!

## Build 
The Maven build creates a JAR-file.

```
mvn package
```

## Install 
Install the development kit in your local Maven repository

```
mvn install
```

## Usage
### Create a new project using the action-pack-archetype
This is probably the easiest way to create a new action pack. Download and build the action-pack-archetype and install it in your local Maven repository. As soon as the action-pack-archetype is available you may create a new project with the following command:

```
mvn archetype:generate -B -DarchetypeGroupId=com.broadcom 
                          -DarchetypeArtifactId=action-pack-archetype 
                          -DarchetypeVersion=0.0.1
                          -DgroupId=com.broadcom 
                          -DartifactId=my-action-pack 
                          -Dversion=1.0-SNAPSHOT
```

### Create a new project manually
Create a new Java Maven project and add this development kit as dependency to your project's pom file:

```
<dependency>
  <groupId>com.broadcom</groupId>
  <artifactId>action-pack-sdk</artifactId>
  <version>0.0.1</version>
</dependency>
```
Furthermore please add the following plugins to your pom file:

**maven-jar-plugin**

This is used to ensure that the artifact version of the Action Pack project will be published to the manifest of the JAR file. This is a prerequisite for the correct initialization of the Action Packs version attribute.

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.2.0</version>
  <configuration>
    <archive>                   
      <manifest>
        <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
        <addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
      </manifest>
    </archive>
  </configuration>
</plugin>
```

**maven-shade-plugin**

This is used to create a shaded version of the generated JAR that contains all dependencies and ensures that the main class of the development kit gets defined as the main class for the Action Pack project as well.

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>2.3</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>shade</goal>
      </goals>
      <configuration>
        <createDependencyReducedPom>false</createDependencyReducedPom>
        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <manifestEntries>
              <Main-Class>com.broadcom.apdk.CLI</Main-Class>
            </manifestEntries>
          </transformer>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
        </transformers>
      </configuration>
    </execution>
  </executions>
</plugin>
```
**maven-antrun-plugin (OPTIONAL)**

The maven-shade-plugin retains a renamed version of the original JAR file. The maven-antrun-plugin may be used to delete this JAR file.

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-antrun-plugin</artifactId>
  <version>1.7</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>run</goal>
      </goals>
      <configuration>
        <target>
          <delete file="${project.build.directory}/original-${project.build.finalName}.jar"/>
        </target>
      </configuration>
    </execution>
  </executions>
</plugin>
```

**exec-maven-plugin (OPTIONAL)**

This plugin may be used to execute the generated executable JAR file at the end of the Maven package phase in order to generate an export of the Action Pack. This will generate a ZIP file that can be installed as Action Pack in CDD.

```
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>1.3.2</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>exec</goal>
      </goals>
      <configuration>
        <executable>java</executable>
        <workingDirectory>${project.build.directory}</workingDirectory>
        <arguments>
          <argument>-jar</argument>
          <argument>${project.build.finalName}.jar</argument>
          <argument>-e</argument>
        </arguments>
      </configuration>  
    </execution>
  </executions>
</plugin>
```

### Test your action pack
Even without installing the action pack in Automic Automation or Automic Continuous Devlivery Automation you can test its functionality. If you implemented your action pack based on the provided archetype building the project will result in an executable JAR file. Use the following command to see what options you have:

```
java -jar <name of your jar-file> -h
```


