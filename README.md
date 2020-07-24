# 再現手順

## 準備

1. Docker がインストールされていれば、 `startMySQL.sh` を実行することで起動できます。
2. テスト用のテーブルとデータを用意済みなので replace-schema を実行してください。

## 実行

```
./gradlew bootRun
```

## 結果

`member` テーブルから合計100件のレコードを取得するテスト。 PageSize を 31 にしているので、4ページに分割。

### 1. 通常のpaging

[テストコード](https://github.com/little-forest/dbflute-offset-test/blob/master/src/main/java/com/example/dbflutetest/DBFluteTest.java#L26-L41)

問題なし。

```
testPage =============================================================================
PageSize:  31, PageNum:   1, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   2, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   3, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, PageNum:   4, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   7
```

### 2. xfetchScope() を使用

[テストコード](https://github.com/little-forest/dbflute-offset-test/blob/master/src/main/java/com/example/dbflutetest/DBFluteTest.java#L43-L63)

最後だけ AllRecordCount が 7 になってしまう。

```
testPageByOffset1 ====================================================================
PageSize:  31, Offset:   0, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  31, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  62, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  93, AllPageCount:   1, AllRecordCount:    7, FetchRecords:   7
```

### 3. xfetchScope() + disablePagingCountLater() を使用

[テストコード](https://github.com/little-forest/dbflute-offset-test/blob/master/src/main/java/com/example/dbflutetest/DBFluteTest.java#L65-L86)

AllRecordCount は正しく取得できるが、4回目の取得で `page.existsNextPage()` が `true` を返してしまい、5回ループしてしまう。

```
testPageByOffset2 ====================================================================
PageSize:  31, Offset:   0, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  31, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  62, AllPageCount:   4, AllRecordCount:  100, FetchRecords:  31
PageSize:  31, Offset:  93, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   7
PageSize:  31, Offset: 100, AllPageCount:   4, AllRecordCount:  100, FetchRecords:   0
```

## 調査

### 2. について

[PagingInvoker#deriveAllRecordCountByLastPage()](https://github.com/dbflute/dbflute-core/blob/21dc2fdcd77aa02cf3f595f2382ebf161fdbf87e/dbflute-runtime/src/main/java/org/dbflute/cbean/paging/PagingInvoker.java#L195-L197) で、 `pagingBean.getFetchPageNumber()` が 0 を返しているため、 `baseSize` が 0 になってしまうことが原因。

### 3. について

(挙動の確認のみ)

[PagingInvoker#doPaging()](https://github.com/dbflute/dbflute-core/blob/21dc2fdcd77aa02cf3f595f2382ebf161fdbf87e/dbflute-runtime/src/main/java/org/dbflute/cbean/paging/PagingInvoker.java#L94-L111) 内のif文で、`disablePagingCountLater()`指定有無によって分岐している。

`disablePagingCountLater()`を指定していると、`deriveAllRecordCountByLastPage()`は呼び出されないので、`allRecordCount`は、正しく計算される。
ただし、毎回`select count(*) 〜`が実行されてしまう。





