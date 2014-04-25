obo-parser
==========

A java parser for obo file format as provided by unipathway


Installation
============

```bash
$ git clone https://github.com/institut-de-genomique/obo-parser.git
$ cd obo-parser
$ mvn install
$ mvn test
```

Usage
=====

````java
Parser parser = new Parser( "unipathway.obo" );
UPA    term   = (UPA) parser.getTerm("UPa:UPA00033");
ULS    term   = (ULS) parser.getTerm("UPa:ULS00003");
````

Each Object come from Term definition see api.


LICENSE
=======

CeCILL-C