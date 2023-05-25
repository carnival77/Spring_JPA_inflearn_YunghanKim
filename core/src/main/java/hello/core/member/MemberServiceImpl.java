package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    // DIP 위반 : 의존관계가 추상(인터페이스)(memberRepository) 뿐만 아니라 구체(구현) 클래스(MemoryMemberRepository)까지 모두 의존하는 문제점이 있음
    // OCP 위반 : 기능을 확장해서 변경하면, 클라이언트 코드(MemberServiceImpl)에 영향을 준다!
//    private final MemberRepository memberRepository = new
//        MemoryMemberRepository();

    // DIP 위반 -> DIP 준수 : 추상에만 의존하도록 변경(인터페이스에만 의존)
    // 클라이언트인 MemberServiceImpl 에 MemberRepository 의 구현 객체를 대신 생성하고 주입하는 AppConfig 생성
    private final MemberRepository memberRepository;

    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다
    @Autowired // 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입 - AutoAppConfig
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않는다!
//    단지 MemberRepository 인터페이스만 의존한다.
//    MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
//    MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정된다.
//    MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다

    //객체의 생성과 연결은 AppConfig 가 담당한다.
//    DIP 완성: MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 된다. 이제 구체 클래스를
//    몰라도 된다.
    //관심사의 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public void join(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}