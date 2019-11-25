'use strict'

module.exports = {
	devServer: {
		proxy: {
			'/api': {
				target: 'http://localhost:8000',
				pathRewrite: {
					'^/api': ''
				},
				changeOrigin: true,
			}
		}
	},
	transpileDependencies:[/node_modules[/\\\\]vuetify[/\\\\]/], // ie 지원 babel-polyfill설정
	outputDir: 'target/dist',
	assetsDir: 'static'
};