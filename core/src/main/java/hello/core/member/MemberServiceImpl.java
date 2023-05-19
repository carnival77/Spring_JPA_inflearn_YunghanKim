package hello.core.member;

public class MemberServiceImpl implements MemberService {

    //다른 저장소로 변경할 때 OCP 원칙을 잘 준수할까요?
    //DIP를 잘 지키고 있을까요?
    //의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제점이 있음
    private final MemberRepository memberRepository = new
        MemoryMemberRepository();

    public void join(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}