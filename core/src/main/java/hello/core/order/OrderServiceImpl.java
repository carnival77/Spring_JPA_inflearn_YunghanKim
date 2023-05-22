package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

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
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다
    public OrderServiceImpl(DiscountPolicy discountPolicy,
        MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }
//
//    설계 변경으로 OrderServiceImpl 은 FixDiscountPolicy 를 의존하지 않는다!
//    단지 DiscountPolicy 인터페이스만 의존한다.
//    OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
//    OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부( AppConfig )에서
//    결정한다.
//    OrderServiceImpl 은 이제부터 실행에만 집중하면 된다.
//    OrderServiceImpl 에는 MemoryMemberRepository , FixDiscountPolicy 객체의 의존관계가 주입된다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
