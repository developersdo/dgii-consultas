require 'net/http'
require 'nokogiri'
require './ProfileRNC'

class ConsultaRNC

	def self.getProfile(rncCedula)
		url = "http://www.dgii.gov.do/app/WebApps/Consultas/rnc/RncWeb.aspx"

		# Force para webforms
		force_params = {
			:__EVENTTARGET => '',
			:__EVENTARGUMENT => '',
			:__LASTFOCUS => '',
			:__VIEWSTATE => '/wEPDwUKMTY4ODczNzk2OA9kFgICAQ9kFgQCAQ8QZGQWAWZkAg0PZBYCAgMPPCsACwBkZHTpAYYQQIXs/JET7TFTjBqu3SYU',
			:__EVENTVALIDATION => '/wEWBgKl57TuAgKT04WJBAKM04WJBAKDvK/nCAKjwtmSBALGtP74CtBj1Z9nVylTy4C9Okzc2CBMDFcB'
		}

		# Valores a enviar
		params = {
			:rbtnlTipoBusqueda => "0",
			:txtRncCed => rncCedula,
			:btnBuscaRncCed => "Buscar",
		}

		# Agrego los params del force
		params.merge!(force_params)

		uri = URI(url)
		res = Net::HTTP.post_form(uri, params)

		profile = []
		
		doc = Nokogiri::HTML(res.body)
		doc.css('tr.GridItemStyle > td').map do |td|
			profile << td.text.strip
		end

		# retorno el perfil o nil
		profile.length > 0 ? ProfileRNC.new(profile) : nil
	end

end