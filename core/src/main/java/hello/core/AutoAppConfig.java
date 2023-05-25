package hello.core;

import static org.springframework.context.annotation.ComponentScan.Filter;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록
@ComponentScan(
    excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
        Configuration.class))
public class AutoAppConfig {
    // 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}