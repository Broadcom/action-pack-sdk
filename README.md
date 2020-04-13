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
                          -DarchetypeVersion=0.0.3 
                          -DgroupId=com.broadcom 
                          -DartifactId=my-action-pack 
                          -Dversion=1.0.0-SNAPSHOT
```

### Test your action pack
Even without installing the action pack in Automic Automation or Automic Continuous Devlivery Automation you can test its functionality. If you implemented your action pack based on the provided archetype building the project will result in an executable JAR file. Use the following command to see what options you have:

```
java -jar <name of your jar-file> -h
```


