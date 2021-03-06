require('./check-versions')();
var server = require('pushstate-server');
var opn = require('opn')
var ora = require('ora')
var rm = require('rimraf')
var path = require('path')
var chalk = require('chalk')
var webpack = require('webpack');
var config = require('../config');

if(process.env.NODE_ENV == 'test'){
    var webpackConfig = require('./webpack.test.conf');
}else{
    var webpackConfig = require('./webpack.prod.conf');
}



var spinner = ora('building for ' + process.env.NODE_ENV + '...')
spinner.start()


rm(path.join(config.build.assetsRoot, config.build.assetsSubDirectory), err => {
    if (err) throw err
    webpack(webpackConfig, function (err, stats) {
        spinner.stop()
        if (err) throw err
        process.stdout.write(stats.toString({
                colors: true,
                modules: false,
                children: false,
                chunks: false,
                chunkModules: false
            }) + '\n\n')

        console.log(chalk.cyan('  Build complete.\n'))
    })
})
