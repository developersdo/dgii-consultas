import requests

url = "http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx"

# NO F*ing clue what this is ask Ahmed
data = {
#          '__EVENTTARGET': '',
#          '__EVENTARGUMENT': '',
#          '__LASTFOCUS': '',
#          '__VIEWSTATE': '/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PDxYCHgdWaXNpYmxlZ2QWBAIBDw8WAh4EVGV4dAUeTm8gZXhpc3RlbiByZWdpc3Ryb3MgQXNvY2lhZG9zZGQCAw88KwALAGRkNbZ1Awnu5Ci/S4J0pkzl4lgkQrg=',
#          '__EVENTVALIDATION': '/wEWBgKi/dSQAwKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CmGDH9QVaQPr1itVc3r/K/qvjJBc',
          'rbtnlTipoBusqueda': '0',
          'txtRncCed': '13095709',
          'btnBuscaRncCed': 'Buscar'
      }

headers = {'Content-type': 'application/x-www-form-urlencoded'}

r = requests.post(url,data=data,headers=headers)
print r.status_code
with open("a.html",'w') as a:
    a.write(r.text.encode('utf8'))
    import webbrowser
    webbrowser.open("a.html")
