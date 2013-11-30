#!/usr/bin/env python
# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import json
import sys,os

#Json Configuration file name 
main_base = os.path.dirname(os.path.abspath(__file__))
CONFIG_FILE_NAME = "config.json"      
CONFIG_FILE = os.path.join(main_base, CONFIG_FILE_NAME)

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

def load_config(json_file):
  """
  Loading configuration
  """  
  with open(json_file, 'r') as file:    
    config_data = json.load(file)            
  return config_data
  
def get_rnc_record(cedula_rnc, config_data = None):
  """
  Create and return a Rnc record by parsing the website from configuration
  """  
  #Loading common parameters  
  if not config_data:
    config_data = load_config(CONFIG_FILE)  
  req_headers = config_data['request_headers'] 
  req_cookies = config_data.get('request_cookies')     
  req_params = config_data['request_parameters']    
  uri = ''.join([config_data['url'], config_data['web_resource']])  
  
  #Setting the default parameter
  req_params['txtRncCed'] = cedula_rnc
  #Making the requests 
  result = requests.get(uri, params = req_params, headers=req_headers)      
  if result.status_code == 200:
    #Http request was successful, parsing...
    soup = BeautifulSoup(result.content)            
    data_rows  = [row for row in soup.findAll('tr', attrs={'class': 'GridItemStyle'})]
    if not data_rows:
      raise Exception(config_data['not_found_string'])
    if len(data_rows)>0:
      tds = data_rows[0].find_all('td')    
      rnc_vals = [str(td.text.strip()) for td in tds]
      rnc = Rnc(rnc_vals)    
      return rnc
    
def main():  
  if len(sys.argv) < 2:
    sys.exit('Usage: %s [rnc_or_cedula]' % sys.argv[0])
  rnc_req = get_rnc_record(sys.argv[1]) # tested for "00113453914")  
    
if __name__ == "__main__":
  main()