package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 빈 등록 <- 자바 코드로 직접 스프링 빈 등록하기
// @Service, @Repository, @Autowired 애노테이션을 제거 가능하다.
//회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired 애노테이션을 제거하고
//    진행한다.
@Configuration
public class SpringConfig {

    // [Jdbc 사용]
    //    DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다. 스프링 부트는 데이터베이스 커넥션
//    정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다. 그래서 DI를 받을 수 있다.
//    private final DataSource dataSource;

    //    생성자에 @Autowired 를 사용하면 객체 생성 시점
    //    에 스프링 컨테이너에서 해당 스프링 빈을 찾아서
//    주입한다. 생성자가 1개만 있으면 @Autowired 는 생략할 수 있다.
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    //    *회원 리포지토리의 코드가
//    회원 서비스 코드를 DI 가능하게 변경한다.
//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

    // [JPA 사용]
    //    private final DataSource dataSource;
//    private final EntityManager em;

//    public SpringConfig(DataSource dataSource, EntityManager em) {
//        this.dataSource = dataSource;
//        this.em = em;
//    }

//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

    // [Spring Data JPA 사용]
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
////        return new JpaMemberRepository(em);
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JdbcMemberRepository(dataSource);
////        return new MemoryMemberRepository();
//    }
}