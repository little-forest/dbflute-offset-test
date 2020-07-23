
mkdir -p mydbflute/dbflute-1.2.3
cd mydbflute/dbflute-1.2.3
curl -LO https://github.com/dbflute/dbflute-core/releases/download/dbflute-1.2.3/dbflute-1.2.3.zip
unzip dbflute-1.2.3.zip
cd ../../
unzip mydbflute/dbflute-1.2.3/etc/client-template/dbflute_dfclient.zip
mv dbflute_dfclient dbflute_testdb

sed -i -e 's/@database@/mysql/' basicInfoMap.dfprop
sed -i -e 's/@targetLanguage@/java/' basicInfoMap.dfprop
sed -i -e 's/@packageBase@/com.example.dbflutetest/' basicInfoMap.dfprop
sed -i -e 's/@targetContainer@/spring/' basicInfoMap.dfprop
sed -i -e 's/@driver@/com.mysql.jdbc.Driver/' databaseInfoMap.dfprop

sed -i -e 's|@url@|jdbc:mysql://localhost:3306/dbflute-test|' databaseInfoMap.dfprop
sed -i -e 's|@schema@|$$NoNameSchema$$|' databaseInfoMap.dfprop
sed -i -e 's|@user@|test|' databaseInfoMap.dfprop
sed -i -e 's|@password@|testpass|' databaseInfoMap.dfprop

chmod +x manage.sh

