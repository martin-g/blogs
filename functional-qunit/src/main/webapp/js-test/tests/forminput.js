$q(document).ready(function() {
	"use strict";

	module('Form Input');

	asyncTest('Change StringProperty', function () {
		expect(2);

		onPageLoad(function($) {

			var $stringPropertyInput = $('#stringProperty');
			var text = 'qunit test value';
			$stringPropertyInput.val(text);
		
			onPageLoad(function($$) {

				var $feedback = $$('span.feedbackPanelINFO');
				equal($feedback.length, 1, 'The feedback is here')
				equal($feedback.text().indexOf("stringProperty = '"+text+"'"), 29, 'The entered text is here')
				
				start();
			});
			
			$('input[value=save]').click();
		});
		getIframe().attr('src', '/forminput');
	});

	asyncTest('Change the locale', function () {
		expect(2);

		onPageLoad(function($) {

			var $select = $('select[name=localeSelect]');
			var locale = '2'; // German
			
			onPageLoad(function($$) {

				var $integerInRangeProperty = $$('label[for=integerInRangeProperty]');
				equal($integerInRangeProperty.length, 1, 'The label for integerInRangeProperty is here')
				equal($integerInRangeProperty.text(), 'Nur Werte zwischen 0 und 100 sind erlaubt', 'The german version is correct')
				
				start();
			});
			
			$select.val(locale);
			$('input[value=save]').click();
		});
		getIframe().attr('src', '/forminput');
	});

});
