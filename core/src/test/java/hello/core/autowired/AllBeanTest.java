package hello.core.autowired;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // new AnnotationConfigApplicationContext() 를 통해 스프링 컨테이너를 생성한다.
        // AutoAppConfig.class , DiscountService.class 를 파라미터로 넘기면서 해당 클래스를 자동으로 스프링 빈으로 등록한다
        // 그 결과 @Component가 붙은 memberService, FixDiscountPolicy, RateDiscountPolicy 가
        // AutoAppConfig의 @ComponentScan에 의해 등록된다.
        ApplicationContext ac = new
            AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000,
            "fixDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);
    }

    static class DiscountService {

        // Map<String, DiscountPolicy> : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로
        //DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
        private final Map<String, DiscountPolicy> policyMap;
        // List<DiscountPolicy> : DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
        private final List<DiscountPolicy> policies;

        // DiscountService는 Map으로 모든 DiscountPolicy 를 주입받는다. 이때 fixDiscountPolicy ,
        //rateDiscountPolicy 가 주입된다.
        public DiscountService(Map<String, DiscountPolicy> policyMap,
            List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        // discount () 메서드는 discountCode로 "fixDiscountPolicy"가 넘어오면 map에서
        //fixDiscountPolicy 스프링 빈을 찾아서 실행한다. 물론 “rateDiscountPolicy”가 넘어오면
        //rateDiscountPolicy 스프링 빈을 찾아서 실행한다.
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);
            return discountPolicy.discount(member, price);
        }
    }
}