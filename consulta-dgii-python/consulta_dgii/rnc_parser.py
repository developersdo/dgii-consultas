#!/usr/bin/env python
# -*- coding: utf-8 -*-

import requests, json
import sys, os
from bs4 import BeautifulSoup
from models import Rnc

#Json Configuration file name 
main_base = os.path.dirname(os.path.abspath(__file__))
CONFIG_FILE_NAME = "config.json"      
CONFIG_FILE = os.path.join(main_base, CONFIG_FILE_NAME)
 
def load_config(json_file):
  """
  Loading configuration from json file
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
  #Making the request 
  result = requests.get(uri, params = req_params, headers=req_headers)  
  if result.status_code == requests.codes.ok:
    #Http request was successful, parsing...
    soup = BeautifulSoup(result.content)             
    data_rows  = soup.find('tr', attrs={'class': 'GridItemStyle'})
    if not data_rows:
      raise Exception(config_data['not_found_string'])
    else:
      tds = data_rows.findChildren('td')   
      rnc_vals = [str(td.text.strip()) for td in tds]
      rnc = Rnc(rnc_vals)    
      return rnc
    
def main():  
  if len(sys.argv) < 2:
    sys.exit('Usage: %s [rnc_or_cedula]' % sys.argv[0])
  rnc_req = get_rnc_record(sys.argv[1])
  print rnc_req
    
if __name__ == "__main__":
  main()