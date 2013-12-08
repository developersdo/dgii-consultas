<?php
include('include/class.php');

$consulta = new Rnc();

echo $consulta->queryDoc('130267537') . "\n"; // Rnc valido Test OK
echo $consulta->queryDoc('00543266297'); // cedula Fail