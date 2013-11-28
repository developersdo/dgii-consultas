import requests

url = "http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx"

# NO F*ing clue what this is ask Ahmed
data = {
          '__EVENTTARGET': '',
          '__EVENTARGUMENT': '',
          '__LASTFOCUS': '',
          '__VIEWSTATE': '/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PZBYCAgMPPCsACwBkZHTpAYYQQIXs/JET7TFTjBqu3SYU',
          '__EVENTVALIDATION': '/wEWBgKl57TuAgKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CtBj1Z9nVylTy4C9Okzc2CBMDFcB',
          'rbtnlTipoBusqueda': '0',
          'txtRncCed': '130957096',
          'btnBuscaRncCed': 'Buscar'
      }

headers = {'Content-type': 'application/x-www-form-urlencoded'}

r = requests.post(url,data=data,headers=headers)
#print r.status_code
with open("a.html",'w') as a:
    a.write(r.text)
    import webbrowser
    webbrowser.open("a.html")
