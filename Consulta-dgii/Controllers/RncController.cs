using System;
using System.Linq;
using System.Text;
using System.Web.Http;
using Consulta_dgii.Helpers;
using Consulta_dgii.Models;
using HtmlAgilityPack;
using RestSharp;

namespace Consulta_dgii.Controllers
{
    public class RncController : ApiController
    {
        // GET api/<controller>
        public Models.ResultRnc Get(string id)
        {
            var resultado = new ResultRnc();

            var client = new RestClient("http://www.dgii.gov.do/app/WebApps/Consultas/");

            var request = new RestRequest("rnc/RncWeb.aspx", Method.POST);

            request.AddParameter("__EVENTTARGET", "");
            request.AddParameter("__EVENTARGUMENT", "");
            request.AddParameter("__LASTFOCUS", "");
            request.AddParameter("__VIEWSTATE",
                "/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PDxYCHgdWaXNpYmxlZ2QWBAIBDw8WAh4EVGV4dGVkZAIDDzwrAAsBAA8WCh4IRGF0YUtleXMWAB4LXyFJdGVtQ291bnQCAR4JUGFnZUNvdW50AgEeFV8hRGF0YVNvdXJjZUl0ZW1Db3VudAIBHwBnZBYCZg9kFgICAQ9kFgxmDw8WAh8BBQswNTYwMTM0NTE5MWRkAgEPDxYCHwEFHUFNSEVEIEFZVUIgSEVSUkVSQSBQT1JUQUxBVElOZGQCAg8PFgIfAQUGJm5ic3A7ZGQCAw8PFgIfAQUCICBkZAIEDw8WAh8BBQZOT1JNQUxkZAIFDw8WAh8BBQZBQ1RJVk9kZGQfMgrkUtdsE6/qgOxobgHnnsmB8Q==");
            request.AddParameter("__EVENTVALIDATION",
                "/wEWBgKi/dSQAwKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CmGDH9QVaQPr1itVc3r/K/qvjJBc");
            request.AddParameter("rbtnlTipoBusqueda", "0");
            request.AddParameter("txtRncCed", id);
            request.AddParameter("btnBuscaRncCed", "Buscar");
            

            // easily add HTTP Headers
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");

            // execute the request
            var response = client.Execute(request);

            var doc = new HtmlDocument();
            doc.LoadHtml(response.Content);
            var trs = doc.DocumentNode.Descendants("tr")
                .FirstOrDefault(f => f.Attributes.Contains("class") && f.Attributes["class"].Value == "GridItemStyle");

            if (trs != null)
            {
                var valores = trs.Descendants("td").ToList();

                resultado.CedulaRnc = valores.First().InnerText.SuperSplit();
                resultado.Nombre = valores.Skip(1).First().InnerText.SuperSplit();
                resultado.NombreComercial = valores.Skip(2).First().InnerText.SuperSplit();
                resultado.Categoria = valores.Skip(3).First().InnerText.SuperSplit();
                resultado.RegimenDePago = valores.Skip(4).First().InnerText.SuperSplit();
                resultado.Estado = valores.Skip(5).First().InnerText.SuperSplit();
            }

            return resultado;
        }
    }
}