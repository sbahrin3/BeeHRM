function checkProductType(productId) {
	var e = document.getElementById('product_type_id');
	if ( e.value == '0') {
		sendAjax2('addProductType','div_center','productId=' + productId);
	}
}

function checkProductBrand(productId) {
	var e = document.getElementById('product_brand_id');
	if ( e.value == '0') {
		sendAjax2('addProductBrand','div_center','productId=' + productId);
	}
}


