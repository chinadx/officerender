/*
 * =========================================================================
 * AUI  APICLOUD UI 框架    流浪男  QQ：343757327  http://www.liulangna.com
 * =========================================================================
 * =========================================================================
 */
var aui = aui || {};
(function(aui, window, document){
	//后退
	window.addEventListener('click', function(event) {
		var action = event.target;
		if (action && action.classList.contains('aui-back')) {
			api.closeWin();
		}
	}, true);
})(aui, window, document);
/**
 *loading
 *弹出菜单 
 */
(function(aui, window, document, name) {
    aui.loading = function(className,method){       
        window.addEventListener('click', function(e) {
            var dd = document.getElementsByClassName(className);
            console.log(dd[0].id);
            document.getElementById(dd[0].id).style.display = 'block';
        })
    }
})(aui, window, document,'loading');
/**
 *Popover
 *弹出菜单 
 */
(function(aui, window, document, name) {
    aui.popover = function(className,method){
        
        window.addEventListener('click', function(e) {
            var dd = document.getElementsByClassName(className);
            console.log(dd[0].id);
            document.getElementById(dd[0].id).style.display = 'block';
        })
    }
})(aui, window, document,'popover');
/*
(function() {
	window.aui = function (params) {
		var aui = this;
		aui.dd = function(){
			alert(1);
		}
		window.addEventListener('click', function(event) {
		var action = event.target;
		if (action && action.classList.contains('aui-back')) {
			alert(1);
		}
	}, true);

	}
})();*/