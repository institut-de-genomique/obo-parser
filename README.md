obo-parser
==========

A java parser for obo file format as provided by unipathway

Release
=======

- [v0.1.0](https://github.com/institut-de-genomique/obo-parser/tree/v0.1.0)

Download
========

- Users: get current [stable release](https://github.com/institut-de-genomique/obo-parser/archive/v0.1.0.zip)
- Developers: get it from git


```bash
$ git clone https://github.com/institut-de-genomique/obo-parser.git
````

Installation
============

Go to obo-parser directory and launch maven to install and test it.

```bash
$ cd obo-parser
$ mvn install
$ mvn test
```

Usage
=====

````java
Parser parser = new Parser( "unipathway.obo" );
UPA    term1  = (UPA) parser.getTerm("UPa:UPA00033");
ULS    term2  = (ULS) parser.getTerm("UPa:ULS00003");
````

All Objects inherit from class [Term](src/main/java/fr/cea/ig/obo/model/Term.java).

[TermRelations](src/main/java/fr/cea/ig/obo/model/Term.java) describes entities which contains some sub-entities, like: [UPA](src/main/java/fr/cea/ig/obo/model/UPA.java), [UER](src/main/java/fr/cea/ig/obo/model/ULS.java), [UPA](src/main/java/fr/cea/ig/obo/model/UER.java), [UCR](src/main/java/fr/cea/ig/obo/model/UCR.java).

If you need to specialize these definitions you can extends these sub-entities (UPA, ULS, UER, UCR) .


LICENSE
=======

[CeCILL-C](LICENSE)