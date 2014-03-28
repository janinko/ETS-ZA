ETS-ZA
======

Effective tatctis for survival - zombie apocalypse


Usage
=====

Prepare build:
Copy ``NetLogo.jar`` and ``lib/scala-library.jar`` from NetLogo install dir to ``ets-za/lib/``.

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
