使用keytool工具生成jks证书
keytool -genkeypair -alias baojian -keypass baojian -keyalg RSA -keysize 1024 -validity 365 -keystore D:/baojian.jks -storepass baojian
````
-alias 别名
-keypass 指定生成密钥的密码
-keyalg 指定密钥使用的加密算法（如 RSA）
-keysize 密钥大小
-validity 过期时间，单位：天
-keystore 指定存储密钥的 密钥库的生成路径、名称。
-storepass 指定访问密钥库的密码。
````
keytool -export -alias felordcn -keystore <jks证书全路径>  -file <导出cer的全路径>
