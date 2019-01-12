var path = require('path');

module.exports = {
    entry: './src/main/js/todoapp.js',
    devtool: 'sourcemaps',
    cache: true,
    
    //mode: 'production', XXX
    debug: true,

	optimization: {
		// XXX
		minimize: false
	},
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/app-bundled.js'
    },
    module: {
        loaders: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: 'babel',
                query: {
                    cacheDirectory: true,
                    presets: ['es2015', 'react']
                }
            }
        ]
    }
};