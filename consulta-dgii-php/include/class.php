<?php

/**
 * @Name: consultar DGII 
 * @author :Jose Ramon De Los Santos Oviedo
 * @version: 0.0.3
 * @date : 2013-11-02
 *
 **/
class Rnc
{
	private $_fileName = 'include/config.json';
	private $_dataJson;
	private $_url;
	private $_contentType;

	public function __construct()
	{
		if (!file_exists($this->_fileName))
			die('El archivo config no existe');

		$handle = fopen($this->_fileName, 'r');
		$this->_dataJson = json_decode(fread($handle, filesize($this->_fileName)), true);
		fclose($handle);

		$this->_contentType = $this->_dataJson{'request_headers'}{'Content-Type'};
		$this->_url = $this->_dataJson{'url'} . '/' . $this->_dataJson{'web_resource'};
	}

	private function _getResource()
	{
		$fieldStr = '';
		foreach ($this->_dataJson{'request_parameters'} as $key => $value) {
			$fieldStr .= $key . '=' . $value . '&'; 
		}

		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $this->_url);
		curl_setopt($ch, CURLOPT_POST, count($fields));
		curl_setopt($ch, CURLOPT_POSTFIELDS, rtrim($fieldStr, '&'));
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$schemeHtml = curl_exec($ch);
		$httpStatus = curl_getinfo($ch, CURLINFO_HTTP_CODE);
		curl_close($ch);

		if($httpStatus != 200)
			die($this->_dataJson{'http_error_string'});

		$dom = new DomDocument();
		$dom->loadHtml($schemeHtml);
		$xpath = new DomXpath($dom);

		$tr = $xpath->query('//span[@id="lblMsg"]')->item(0);

		if ($tr->textContent)
			die($this->_dataJson{'not_found_string'});

		$tr = $xpath->query('//tr[@class="GridItemStyle"]')->item(0);

		foreach ($tr->childNodes as $node) {
			$rncValue[] = $node->nodeValue;
		}
		
		if (!headers_sent()) {
			header('Content-type: text/json');
			header('Content-type: application/json');
		}
		
		array_pop($rncValue);
		return json_encode($rncValue, JSON_FORCE_OBJECT);
	}

	public function queryDoc($doc)
	{
		$this->_dataJson{'request_parameters'}{'txtRncCed'} = $doc;
		return $this->_getResource();
	}
}