package com.example.dbflutetest;

import com.example.dbflutetest.dbflute.exbhv.MemberBhv;
import com.example.dbflutetest.dbflute.exentity.Member;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
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
		app.run();
	}

	public void run(){
		System.out.println("OK");
		ListResultBean<Member> members = memberBhv.selectList(cb -> cb.paging(10, 2));
		members.forEach(System.out::println);
	}
}
