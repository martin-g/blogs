/*
 * Grunt.js is a tool for automated JavaScript development
 * https://github.com/cowboy/grunt
 *
 * To use it:
 * 1) install node.js - http://nodejs.org/#download. This will install 'npm' (Node Package Manager) too
 * 2) install phantomjs - http://code.google.com/p/phantomjs/downloads/list
 * 3) install grunt - 'npm -g install grunt'
 * 4) run it: grunt lint, grunt lint:core, grunt qunit, grunt qunit:local
 */

 /*global module: true */

module.exports = function(grunt) {
	"use strict";

	var
		testsJs = [
			'src/main/webapp/js-test/tests/helloworld.js',
			'src/main/webapp/js-test/tests/echo.js',
			'src/main/webapp/js-test/tests/forminput.js',
			'src/main/webapp/js-test/tests/ajax/form.js',
		],
		gruntJs = [
			'grunt.js'
		];

	// Project configuration.
	grunt.initConfig({
		lint: {
			tests: testsJs,
			grunt: gruntJs
		},

		jshint: {
			options: {
				"boss": true,
				"browser": true,
				"curly": true,
				"eqnull": true,
				"eqeqeq": true,
				"expr": true,
				"evil": true,
				"jquery": true,
				"latedef": true,
				"noarg": true,
				"onevar": false,
				"smarttabs": true,
				"trailing": true,
				"undef": true,
				"strict": true,
				"predef": [
					"Wicket"
				]
			}
		},
		qunit: {
			index: ['http://localhost:51982/js-test/all.html']
		}
	});
};
