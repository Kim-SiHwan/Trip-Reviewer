const path = require('path');
module.exports = {
  outputDir: "../src/main/resources/static",
  indexPath: "../static/index.html",
  devServer: {
    proxy: {
      '/api': {
        target: "http://localhost:8080",
        ws:true,
        changeOrigin:true
      }
    }
  },


  transpileDependencies: [
    'vuetify'
  ]
}
