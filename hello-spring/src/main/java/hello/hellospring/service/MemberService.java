package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

//스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을
//    커밋한다. 만약 런타임 예외가 발생하면 롤백한다.
//    JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.
//@Service
@Transactional
public class MemberService {
//    private final MemberRepository memberRepository = new
//        MemoryMemberRepository();

    //회원 리포지토리의 코드가
    //회원 서비스 코드를 DI 가능하게 변경한다.
    private final MemberRepository memberRepository;

    //    생성자에 @Autowired 를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서
//    주입한다. 생성자가 1개만 있으면 @Autowired 는 생략할 수 있다.
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
