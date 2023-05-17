package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//스프링 빈을 등록하는 2가지 방법
//    컴포넌트 스캔과 자동 의존관계 설정
//    자바 코드로 직접 스프링 빈 등록하기
//
//스프링 빈을 등록 <- 컴포넌트 스캔과 자동 의존관계 설정(O)
//@Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
//@Controller
//@Service
//@Repository

//    > @Controller 가 있으면 스프링 빈으로 자동 등록
@Controller
public class HelloController {

    //    컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버( viewResolver )가 화면을 찾아서 처리한다.
    //    스프링 부트 템플릿엔진 기본 viewName 매핑
    //    resources:templates/ +{ViewName}+ .html
    @GetMapping("hello") // http://localhost:8080/hello
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // ${data}에 value 매핑
        return "hello"; // resources/templates/hello.html 뷰 렌더링
    }

    @GetMapping("hello-mvc") // http://localhost:8080/hello-mvc?name=spring
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string") // http://localhost:8080/hello-string?name=spring
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    // @ResponseBody 를 사용하고, 객체를 반환하면 객체가 JSON으로 변환됨
//    @ResponseBody 를 사용
//    HTTP의 BODY에 문자 내용을 직접 반환
//    viewResolver 대신에 HttpMessageConverter 가 동작
//    기본 문자처리: StringHttpMessageConverter
//    기본 객체처리: MappingJackson2HttpMessageConverter
//    byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
