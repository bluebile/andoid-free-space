window.freeSpace = function(str, callback) {
	cordova.exec(callback, 
		function(err) {
            alert('Nothing to echo.');
        }, 
        "FreeSpace", 
        "getEspacoLivrePercent", 
        ['percente']
    ); 		
};

window.gpsAtivo = function(str, callback) {
	cordova.exec(callback, 
		function(err) {
            alert('Nothing to echo.');
        }, 
        "FreeSpace", 
        "getGPSAtivo", 
        ['percente']
    ); 		
};