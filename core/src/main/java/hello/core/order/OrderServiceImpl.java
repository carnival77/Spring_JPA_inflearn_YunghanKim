package hello.core.order;

import com.sun.tools.javadoc.Main;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다. 생성자는 삭제해야 한다.
public class OrderServiceImpl implements OrderService {

    // DIP 위반 : 의존관계가 추상(인터페이스)(memberRepository) 뿐만 아니라 구체(구현) 클래스(MemoryMemberRepository)까지 모두 의존하는 문제점이 있음
    // OCP 위반 : 기능을 확장해서 변경하면, 클라이언트 코드(OrderServiceImpl)에 영향을 준다!
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // DIP 위반 : 의존관계가 추상(인터페이스)(discountPolicy) 뿐만 아니라 구체(구현) 클래스(RateDiscountPolicy, FixDiscountPolicy)까지 모두 의존하는 문제점이 있음
    // OCP 위반 : FixDiscountPolicy 를 RateDiscountPolicy 로 변경하는 순간 OrderServiceImpl 의 소스 코드도 함께 변경
    // 기능을 확장해서 변경하면, 클라이언트 코드(OrderServiceImpl)에 영향을 준다!
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // DIP 위반 -> DIP 준수 : 추상에만 의존하도록 변경(인터페이스에만 의존)
    // 클라이언트인 OrderServiceImpl 에 DiscountPolicy, MemberRepository 의 구현 객체를 대신 생성하고 주입하는 AppConfig 생성
    // 생성자 주입
//    생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
//    불변, 필수 의존관계에 사용
//    생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다
//    @Autowired // 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입 - AutoAppConfig
//    public OrderServiceImpl(DiscountPolicy discountPolicy,
//        MemberRepository memberRepository) {
//        this.discountPolicy = discountPolicy;
//        this.memberRepository = memberRepository;
//    }

//    public OrderServiceImpl(@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy,
//        MemberRepository memberRepository) {
//        this.discountPolicy = discountPolicy;
//        this.memberRepository = memberRepository;
//    }

        public OrderServiceImpl(@MainDiscountPolicy DiscountPolicy discountPolicy,
        MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    //    생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의
//    특징을 잘 살리는 방법
//    기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
//    생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
//    항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지
//    않는게 좋다.

//    수정자 주입(setter 주입)
//  setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입
//    선택, 변경 가능성이 있는 의존관계에 사용
//    자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.
//      주입할 대상이 없어도 동작하게하려면 @Autowired(required = false) 로 지정
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    //    @Autowired
//    public void setDiscountPolicy(@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

//    필드 주입
//외부에서 변경이 불가능해서 테스트 하기 힘들다
//    DI 프레임워크가 없으면 아무것도 할 수 없다.
//    애플리케이션의 실제 코드와 관계 없는 테스트 코드 및 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private DiscountPolicy discountPolicy

//    일반 메서드 주입
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy
//        discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

//
//    설계 변경으로 OrderServiceImpl 은 FixDiscountPolicy 를 의존하지 않는다!
//    단지 DiscountPolicy 인터페이스만 의존한다.
//    OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
//    OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부( AppConfig )에서
//    결정한다.
//    OrderServiceImpl 은 이제부터 실행에만 집중하면 된다.
//    OrderServiceImpl 에는 MemoryMemberRepository , FixDiscountPolicy 객체의 의존관계가 주입된다.

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
