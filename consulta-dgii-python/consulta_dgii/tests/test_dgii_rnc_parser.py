#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
test_dgii_rnc_parser
--------------------

Tests for `consulta_dgii.rnc_parser` module.
"""
import unittest, os, sys
sys.path.append(os.path.abspath('..'))
import rnc_parser
class RncParser(unittest.TestCase):

  def test_valid_rnc(self):
    rnc = rnc_parser.get_rnc_record("130267537")    
    self.assertEqual(rnc.nombre,"CLARO 8 DEVELOPMENT S A", msg="RNC: 130267537 es valido")
    
  def test_valid_cedula(self):
    rnc = rnc_parser.get_rnc_record("00113453914")    
    self.assertEqual(rnc.nombre,"JUAN           ANTONIO BULOS", msg="CEDULA: 00113453914 es valida")

if __name__ == '__main__':
    unittest.main()