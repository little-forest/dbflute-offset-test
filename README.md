# 再現手順

## 準備

1. Docker がインストールされていれば、 `startMySQL.sh` を実行することで起動できます。
2. テスト用のテーブルとデータを用意済みなので replace-schema を実行してください。

## 実行

```
./gradlew bootRun
``

`member`テーブルから合計100件のレコードを取得するテスト。


```
testPage =============================================================================
PageSize:  31, PageNum:   1, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   2, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   3, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   4, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   7
```

testPageByOffset1 ====================================================================
PageSize:  31, Offset:   0, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  31, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  62, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  93, AllPageCount:   1, AllRecordCount:    7, FetchRecords:   7

testPageByOffset2 ====================================================================
PageSize:  31, Offset:   0, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  31, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  62, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  93, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   7
PageSize:  31, Offset: 100, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   0
```
