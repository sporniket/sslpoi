# sslpoi : Sporny Script Language (Pun Obviously Intended)

> [WARNING] Please read carefully this note before using this project. It contains important facts.

Content

1. What is **Sporny Script Language**, and when to use it ?
2. What should you know before using **Sporny Script Language** ?
3. How to use **Sporny Script Language** ?
4. Known issues
5. Miscellanous

##1. What is **Sporny Script Language**, and when to use it ?
**Sporny Script Language** is a collection of software components to embed scripting capabilities in a Java project called the "client".

The principle is that a client will provide to the software components a source in an expected syntax, and it will get an abstract syntax tree that it will process anyway it needs : actual scripting, Ioc implementation, whatever...


### What's new in v0.2.0
* Fixed bugs
   * #3 : if/elseif require else section before endif
* Implemented language features
   * #4 : drop explicit argument mapping in favor of ordered arguments in call statements

### What's new in v0.1.0
* Implemented language features
   * declaration of an identifier (type, array marker, optionnal initialization)
   * call statement
   * if/else if/else
   * event call back (```on eventname ...```)
   * structured access (```call method from object```)

###Licence
 **Sporny Script Language** is free software: you can redistribute it and/or modify it under the terms of the
 GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 option) any later version.

 **Sporny Script Language** is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 more details.
 
 You should have received a copy of the GNU Lesser General Public License along with **Sporny Script Language**.
 If not, see http://www.gnu.org/licenses/ .


##2. What should you know before using **Sporny Script Language** ?
**Sporny Script Language** relies on :

* [JFlex](http://jflex.de/) : a lexical analyzer generator (also known as scanner generator) for Java, written in Java.
* [CUP](http://www2.cs.tum.edu/projects/cup/) : Construction of Useful Parsers, an LALR parser generator for Java.

> Do not use **Sporny Script Language** if this project or one of it's dependencies is not suitable for your project

##3. How to use **Sporny Script Language** ?

###From source
To get the latest available code, one must clone the git repository, build and install to the maven local repository.

	git clone https://github.com/sporniket/sslpoi.git
	cd sslpoi
	mvn install

###Maven
Add the following dependencies to your project.

	<dependency>
	    <groupId>com.sporniket.scripting.sslpoi</groupId>
	    <artifactId>sslpoi-core</artifactId>
	    <version><!-- the version to use --></version>
	</dependency>

###Directions and sample code
Read the javadoc and look at the test code.

##4. Known issues
See the [project issues](https://github.com/sporniket/sslpoi/issues) page.

##5. Miscellanous
### Report issues
Use the [project issues](https://github.com/sporniket/sslpoi/issues) page.