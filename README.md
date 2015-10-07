# sslpoi : Sporny Script Language (Pun Obviously Intended)

SSL is a collection of software components to embed scripting capabilities in a Java project called the "client".

The principle is that a client will provide to the software components a source in an expected syntax, and it will get an abstract syntax tree that it will process anyway it needs : actual scripting, Ioc implementation, etc...

## How to use in your project with maven

### From source
To get the latest available code, one must clone the git repository, build and install to the maven local repository.

```
git clone https://github.com/sporniket/sslpoi.git
cd sslpoi/ssl
mvn install
```

### Add a dependency to your project
Add the needed dependencies in your pom file :

```
<dependency>
	<groupId>com.sporniket.scripting.ssl</groupId>
	<artifactId>ssl-core</artifactId>
	<version><!-- the version to use --></version>
</dependency>
```
