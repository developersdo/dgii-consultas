using System.Linq;
using HtmlAgilityPack;
using RestSharp;

namespace consulta_dgii_csharp
{
    public class RncQueryWrapper
    {
        /// <summary>
        /// Método que hace el query a la base de datos de RNCs
        /// </summary>
        /// <param name="rncCedula">RNC o Cedula a usar</param>
        /// <returns></returns>
        public static ResultRnc QueryByRnc(string rncCedula)
        {
            var result = new ResultRnc();
            var client = new RestClient("http://www.dgii.gov.do/app/WebApps/Consultas/");
            var request = new RestRequest("rnc/RncWeb.aspx", Method.POST);

            //Esto en realidad es un force. Son valores que ASP.NET espera en el HTTP Request. 
            //Denle las gracias a Web Forms por esta ;)
            request.AddParameter("__EVENTTARGET", "");
            request.AddParameter("__EVENTARGUMENT", "");
            request.AddParameter("__LASTFOCUS", "");
            request.AddParameter("__VIEWSTATE",
                "/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PDxYCHgdWaXNpYmxlZ2QWBAIBDw8WAh4EVGV4dGVkZAIDDzwrAAsBAA8WCh4IRGF0YUtleXMWAB4LXyFJdGVtQ291bnQCAR4JUGFnZUNvdW50AgEeFV8hRGF0YVNvdXJjZUl0ZW1Db3VudAIBHwBnZBYCZg9kFgICAQ9kFgxmDw8WAh8BBQswNTYwMTM0NTE5MWRkAgEPDxYCHwEFHUFNSEVEIEFZVUIgSEVSUkVSQSBQT1JUQUxBVElOZGQCAg8PFgIfAQUGJm5ic3A7ZGQCAw8PFgIfAQUCICBkZAIEDw8WAh8BBQZOT1JNQUxkZAIFDw8WAh8BBQZBQ1RJVk9kZGQfMgrkUtdsE6/qgOxobgHnnsmB8Q==");
            request.AddParameter("__EVENTVALIDATION",
                "/wEWBgKi/dSQAwKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CmGDH9QVaQPr1itVc3r/K/qvjJBc");
            
            //Estos son los valores
            request.AddParameter("rbtnlTipoBusqueda", "0");
            request.AddParameter("txtRncCed", rncCedula);
            request.AddParameter("btnBuscaRncCed", "Buscar");

            // HTTP Headers
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");

            var response = client.Execute(request);

            var doc = new HtmlDocument();
            doc.LoadHtml(response.Content);
            var trs = doc.DocumentNode.Descendants("tr")
                .FirstOrDefault(f => f.Attributes.Contains("class") && f.Attributes["class"].Value == "GridItemStyle");

            if (trs != null)
            {
                var valores = trs.Descendants("td").ToList();

                result.CedulaRnc = valores.First().InnerText.SuperSplit();
                result.Nombre = valores.Skip(1).First().InnerText.SuperSplit();
                result.NombreComercial = valores.Skip(2).First().InnerText.SuperSplit();
                result.Categoria = valores.Skip(3).First().InnerText.SuperSplit();
                result.RegimenDePago = valores.Skip(4).First().InnerText.SuperSplit();
                result.Estado = valores.Skip(5).First().InnerText.SuperSplit();
            }

            return result;
        }
    }
}
