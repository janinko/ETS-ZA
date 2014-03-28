ETS-ZA
======

Effective tatctis for survival - zombie apocalypse


Usage
=====

Build:
```
mvn clean install -DoutDir=/path/to/netlogo/extensions/gbui
```
or place it there manually.

In netlogo project code add:
```
extensions [ gbui ]
```
and then you can use:
```
gbui:print-agent-info <TURTLE>
```
