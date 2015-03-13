obo-parser
==========

A java parser for obo file format as provided by unipathway

Release
=======

- [obo-parser-0.2.1](https://github.com/institut-de-genomique/obo-parser/archive/obo-parser-0.2.1.zip)

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
$ gradle install
```

Usage
=====

````java
Parser parser = new Parser( "unipathway.obo" );
UPA    term1  = (UPA) parser.getTerm("UPa:UPA00033");
ULS    term2  = (ULS) parser.getTerm("UPa:ULS00003");
````

All Objects inherit from class [Term](src/main/java/fr/cea/ig/obo/model/Term.java).

[TermRelations](src/main/java/fr/cea/ig/obo/model/Term.java) describes entities which contains some sub-entities, like: [UPA](src/main/java/fr/cea/ig/obo/model/UPA.java), [ULS](src/main/java/fr/cea/ig/obo/model/ULS.java), [UER](src/main/java/fr/cea/ig/obo/model/ULS.java), [UPA](src/main/java/fr/cea/ig/obo/model/UER.java), [UCR](src/main/java/fr/cea/ig/obo/model/UCR.java).

If you need to specialize these definitions you can extends these sub-entities (UPA, ULS, UER, UCR) .


Build Information
=================

Travis Log: [here](https://travis-ci.org/institut-de-genomique/obo-parser)

LICENSE
=======

Open source approved by the [Free Software Foundation](https://www.fsf.org/): [CeCILL-C](LICENSE)
