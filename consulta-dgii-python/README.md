dgii-consultas-python
=====================

Wrapper en python para consultas al portal de DGII

Requerimientos
------------
* beautifulsoup4
* requests

Usage
-----
**Ejemplo de uso desde la linea de comandos:**

    python rnc_parser 999999999

**Ejemplo de uso desde codigo:**
```  
import rnc_parser  
rnc = rnc_parser.get_rnc_record("999999999")
```    