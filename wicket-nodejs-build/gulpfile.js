'use strict';

var gulp = require('gulp');

// load plugins
var $ = require('gulp-load-plugins')();

/**
 * Some constants to save some typing later
 */
var webappPath = 'src/main/webapp/',
    resources  = 'src/main/resources/';

/**
 * Compiles all .less resources in the classpath to .css
 * and saves the CSS in target/classes.
 * In Wicket code just use something like:
 *   response.render(CssHeaderItem.forReference(new CssResourceReference(HomePage.class, "res/demo.css")));
 */
gulp.task('stylesInPackages', function () {
    return gulp.src(resources + '/**/*.less')
    .pipe($.less())
    .pipe(gulp.dest('target/classes/'));
});

/**
 * Compiles all .less resources in src/main/webapp to .css
 * Just a demo how to compile Less resources in src/main/webapp and
 * be able to use them with 'mvn jetty:run' or Wicket Quickstart's Start.java
 * Also see 'clean' task below
 */
gulp.task('stylesInWebapp', function () {
    return gulp.src(webappPath + '**/*.less')
        .pipe($.less())
        .pipe(gulp.dest(webappPath));
});

/**
 * A task with dependencies. Does nothing but executes the dependencies
 */
gulp.task('styles', ['stylesInWebapp', 'stylesInPackages']);

/**
 * A task that executes JSHint on all JavaScript resources in the classpath
 * and minimizes them with UglifyJS. The minimized versions are stored in
 * target/classes/ with extension .min.js, so Wicket JavaScriptResourceReference
 * will pick them up automatically in DEPLOYMENT mode
 */
gulp.task('scripts', function () {
    return gulp.src(resources + '/**/*.js')
        .pipe($.jshint())
        .pipe($.jshint.reporter(require('jshint-stylish')))
//        .pipe($.jshint.reporter('fail'))  // uncomment to make the build fail when their is a warning
        .pipe($.rename({suffix: '.min'}))
        .pipe($.uglify())
        .pipe(gulp.dest('target/classes/'));
});

/**
 * The clean task.
 * Removes all compiles Less resources (the CSS) from src/main/webapp
 */
gulp.task('clean', function () {
    return gulp.src(
        [
            webappPath + '**/*.css'
        ],
        { read: false }
    ).pipe($.rimraf());
});

/**
 * The build task just executes 'styles' and 'scripts' tasks
 */
gulp.task('build', ['styles', 'scripts']);

/**
 * Executing just 'gulp' will execute 'clean' and start 'build' tasks
 */
gulp.task('default', ['clean'], function () {
    gulp.start('build');
});
