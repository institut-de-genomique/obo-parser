**obo-parser**
==========

A java parser for obo file format as provided by unipathway

**Release**
=======

- [v0.1.0](https://github.com/institut-de-genomique/obo-parser/tree/v0.1.0)

**Installation**
============

```bash
$ git clone https://github.com/institut-de-genomique/obo-parser.git
$ cd obo-parser
$ mvn install
$ mvn test
```

**Usage**
=====

````java
Parser parser = new Parser( "unipathway.obo" );
UPA    term1  = (UPA) parser.getTerm("UPa:UPA00033");
ULS    term2  = (ULS) parser.getTerm("UPa:ULS00003");
````

Each Object inherit from class [Term](src/main/java/fr/cea/ig/obo/model/Term.java).
[TermRelations](src/main/java/fr/cea/ig/obo/model/Term.java) describes entities which contains some sub-entities, like: UPA, ULS, UER, UCR.

If you need to specialize these definition you can extends these sub-entities (UPA, ULS, UER, UCR) .


**LICENSE**
=======

CeCILL-C