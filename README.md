[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
***
# YOMOU CRAWLER API
YOMOU CRAWLERで収集した情報のREST APIを提供する。

## DESCRIPTION
* 情報はJSONで出力する

### DATABASE
MySQLでの動作を前提としている。
YOMOU CRAWLERのデータに接続し、下記のスクリプトを実行する。

  /crawler/src/config/schema.sql
  /crawler/src/config/data.sql

  /crawler/src/test/resources/jdbc.properties については、自身の環境に合わせて変更すること。

## USAGE


## License
YOMOU CRAWLER API is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
