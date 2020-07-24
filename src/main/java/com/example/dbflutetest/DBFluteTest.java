package com.example.dbflutetest;

import com.example.dbflutetest.dbflute.exbhv.MemberBhv;
import com.example.dbflutetest.dbflute.exentity.Member;
import org.dbflute.cbean.result.PagingResultBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DBFluteTest {
    private final MemberBhv memberBhv;

    public DBFluteTest(MemberBhv memberBhv) {
        this.memberBhv = memberBhv;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBFluteTest.class, args);
        DBFluteTest app = ctx.getBean(DBFluteTest.class);
        app.testPage();
        app.testPageByOffset1();
        app.testPageByOffset2();
    }

    public void testPage() {
        System.out.println("testPage =============================================================================");
        int pageSize = 31;
        int pageNum = 1;
        do {
        } while (showPage(pageSize, pageNum++));
        System.out.println();
    }

    private boolean showPage(final int pageSize, final int pageNum) {
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> cb.paging(pageSize, pageNum));
        System.out.printf("PageSize: %3d, PageNum: %3d, AllPageCount: %3d, AllRecordCount: %4d, FetchRecords: %3d\n",
                pageSize, pageNum, page.getAllPageCount(), page.getAllRecordCount(), page.size());
//		page.forEach(System.out::println);
        return page.existsNextPage();
    }

    public void testPageByOffset1() {
        System.out.println("testPageByOffset1 ====================================================================");
        int pageSize = 31;
        int offset = 0;
        PagingResultBean<Member> page = null;
        do {
            page = showPageByOffset1(offset, pageSize);
            offset += page.size();
        } while (page.existsNextPage());
        System.out.println();
    }

    private PagingResultBean<Member> showPageByOffset1(final int offset, final int pageSize) {
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            cb.xfetchScope(offset, pageSize);
        });
        System.out.printf("PageSize: %3d, Offset: %3d, AllPageCount: %3d, AllRecordCount: %4d, FetchRecords: %3d\n",
                pageSize, offset, page.getAllPageCount(), page.getAllRecordCount(), page.size());
//		page.forEach(System.out::println);
        return page;
    }

    public void testPageByOffset2() {
        System.out.println("testPageByOffset2 ====================================================================");
        int pageSize = 31;
        int offset = 0;
        PagingResultBean<Member> page = null;
        do {
            page = showPageByOffset2(offset, pageSize);
            offset += page.size();
        } while (page.existsNextPage());
        System.out.println();
    }

    private PagingResultBean<Member> showPageByOffset2(final int offset, final int pageSize) {
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            cb.xfetchScope(offset, pageSize);
            cb.disablePagingCountLater();
        });
        System.out.printf("PageSize: %3d, Offset: %3d, AllPageCount: %3d, AllRecordCount: %4d, FetchRecords: %3d\n",
                pageSize, offset, page.getAllPageCount(), page.getAllRecordCount(), page.size());
//		page.forEach(System.out::println);
        return page;
    }
}
