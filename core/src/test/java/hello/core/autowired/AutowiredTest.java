package hello.core.autowired;

import hello.core.member.Member;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class AutowiredTest {

    //Member는 스프링 빈이 아니다.

    // @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
    //호출 안됨
    @Autowired(required = false)
    public void setNoBean1(Member member) {
        System.out.println("setNoBean1 = " + member);
    }

    // org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
    //null 호출
    @Autowired
    public void setNoBean2(@Nullable Member member) {
        System.out.println("setNoBean2 = " + member);
    }

    // Optional<> : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다
    //Optional.empty 호출
    @Autowired(required = false)
    public void setNoBean3(Optional<Member> member) {
        System.out.println("setNoBean3 = " + member);
    }
}
