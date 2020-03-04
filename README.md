# prototype_android
androidアプリ(試作)

- リモートからローカルサーバと通信する場合
1. androidstudioからbuild.gradle(Module: app)を開く
1. productFlavors内のdevの「buildConfigField "String", "API_ROOT"」の右に通信先を記載して「sync」をクリック
1. res/xml/network_security_config.xmlを開く
1. domainタグに通信先のドメイン(http://抜きのやつ)を記載
1. ビルド
