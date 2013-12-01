url = "http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx"

def get_with_requests():
    import requests

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
    return r.text.encode("latin-1")

def dump_to_file(text,name='a.html'):
    with open(name,'w') as a:
        a.write(text)
        import webbrowser
        webbrowser.open(name)

def get_data(html):
    import html5lib
    document = html5lib.parse(html)
    row = document.findall(".//{http://www.w3.org/1999/xhtml}tr[@class='GridItemStyle']")

    return {
    'cedula_rnc' : row[0][0].text,
    'nombre' : row[0][1].text,
    'nombre_comercial' : row[0][2].text,
    'categoria' : row[0][3].text,
    'regimen_de_pago' : row[0][4].text,
    'estado' : row[0][5].text,
    }


html = get_with_requests()
print get_data(html)
#dump_to_file(html)
