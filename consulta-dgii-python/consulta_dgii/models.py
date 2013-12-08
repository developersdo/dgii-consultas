#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Rnc: 
  """
  Class that represents a RNC record to be returned from the Web service
  """
  def __init__(self, rnc_values):
    self.cedula_rnc = rnc_values[0]
    self.nombre  = rnc_values[1]
    self.nombre_comercial = rnc_values[2]
    self.categoria = rnc_values[3] 
    self.regimen_de_pago = rnc_values[4] 
    self.estado = rnc_values[5]        

  @classmethod
  def fromdict(cls, data_list):  
    return cls(data_list)
    
  def __str__(self):    
    local_data = self.__dict__.copy()    
    return ('cedula_o_rnc: {cedula_rnc}\n' \
            'Nombre: {nombre}\n' \
            'Nombre_Comercial: {nombre_comercial}\n' \
            'Regimen_Pago: {regimen_de_pago}\n' \
            'Estado: {estado}\n' \
            ).format(**local_data)    