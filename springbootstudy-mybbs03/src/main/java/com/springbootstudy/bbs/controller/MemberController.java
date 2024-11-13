package com.springbootstudy.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springbootstudy.bbs.domain.Member;
import com.springbootstudy.bbs.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes("member")
public class MemberController {
	// 회원 관련 Business 로직을 담당하는 객체를 의존성 주입하도록 설정
		@Autowired
		private MemberService memberService;
		

		@PostMapping("/login")	
		public String login(Model model, @RequestParam("userId") String id, 
				@RequestParam("pass") String pass, 
				HttpSession session, HttpServletResponse response) 
						throws ServletException, IOException {
			log.info("MemberController.login()");
			
			// MemberService 클래스를 사용해 로그인 성공여부 확인
			int result = memberService.login(id, pass);
			
			if(result == -1) { // 회원 아이디가 존재하지 않으면
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("	alert('존재하지 않는 아이디 입니다.');");
				out.println("	history.back();");
				out.println("</script>");
				
				/* 컨트롤러에서 null을 반환하거나 메서드의 반환 타입이 void일 경우
				 * Writer나 OutputStream을 이용해 응답 결과를 직접 작성할 수 있다.
				 * DispatcherServlet을 경유해 리소스 자원에 접근하는 경우에
				 * 자바스크립트의 history.back()은 약간의 문제를 일으킬 수 있다.
				 * history 객체를 이용하는 경우 서버로 요청을 보내는 것이 아니라
				 * 브라우저의 접속 이력에서 이전 페이지로 이동되기 때문에 발생한다. 
				 **/
				return null;
				
			} else if(result == 0) { // 비밀번호가 틀리면
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("	alert('비밀번호가 다릅니다.');");
				out.println("	location.href='loginForm'");
				out.println("</script>");
				
				return null;
			}		
			
			// 로그인을 성공하면 회원 정보를 DB에서 가져와 세션에 저장한다.
			Member member = memberService.getMember(id);
			session.setAttribute("isLogin", true);
			
			/* 클래스 레벨에 @SessionAttributes("member") 애노테이션을
			 * 지정하고 그 컨트롤러의 메서드에서 아래와 같이 동일한 이름으로 모델에
			 * 추가하면 스프링이 세션 영역에 데이터를 저장해 준다.
			 **/ 
			model.addAttribute("member", member);
			System.out.println("member.name : " + member.getName());

			/* 클라이언트 요청을 처리한 후 리다이렉트 해야할 경우 아래와 같이 redirect:
			 * 접두어를 붙여 뷰 이름을 반환하면 된다. 뷰 이름에 redirect 접두어가 붙으면
			 * HttpServletResponse를 사용해서 지정한 경로로 Redirect 된다. 
			 * redirect 접두어 뒤에 경로를 지정할 때 "/"로 시작하면 ContextRoot를
			 * 기준으로 절대 경로 방식으로 Redirect 된다. "/"로 시작하지 않으면 현재 
			 * 경로를 기준으로 상대 경로로 Redirect 된다. 또한 다른 사이트로 Redirect
			 * 되기를 원한다면 redirect:http://사이트 주소를 지정한다.
			 * 
			 * 로그인이 성공하면 게시글 리스트로 리다이렉트 된다.
			 **/		
			return "redirect:/boardList";
		}
		
		/* "/membeLogout"으로 들어오는 GET 방식 요청 처리 메서드
		 * 스프링 시큐리티를 사용하면 스프링 시큐리티 설정이 우선 적용되어 POST 방식의 
		 * "/logout"이 기본 URL로 맵핑된다. GET 방식으로 "/logout" 요청을 보내면 405
		 * (Method Not Allowed)가 발생한다. 그래서 아래와 같이 맵핑 URL을 적용했다.
		 * 
		 * 이 컨트롤러 맵핑은 직접 로그아웃을 처리하는 방법에 대해서 설명하고 있지만
		 * com.springbootstudy.bbs.configurations.SecuurityConfig 클래스에는 스프링
		 * 시큐리티를 적용해 로그인과 로그아웃을 적용하는 설정이 주석으로 설명되어 있다.  
		 **/
		@GetMapping("/memberLogout")
		public String logout(HttpSession session) {	
			log.info("MemberController.logout(HttpSession session)");
			// 현재 세션을 종료하고 새로운 세션을 시작한다.
			session.invalidate();
			
			/* 클라이언트 요청을 처리한 후 리다이렉트 해야할 경우 아래와 같이 redirect:
			 * 접두어를 붙여 뷰 이름을 반환하면 된다. 뷰 이름에 redirect 접두어가 붙으면
			 * HttpServletResponse를  사용해서 지정한 경로로 Redirect 된다. 
			 * redirect 접두어 뒤에 경로를 지정할 때 "/"로 시작하면 ContextRoot를
			 * 기준으로 절대 경로 방식으로 Redirect 된다. "/"로 시작하지 않으면 현재 
			 * 경로를 기준으로 상대 경로로 Redirect 된다. 또한 다른 사이트로 Redirect
			 * 되기를 원한다면 redirect:http://사이트 주소를 지정한다.
			 * 
			 * 로그아웃 되면 로그인 폼으로 리다이렉트 된다.
			 **/
			return "redirect:/loginForm";
		}
		
		@GetMapping("/overlapIdCheck")
		public String overlapIdCheck(Model model, @RequestParam("id") String id) {
			model.addAttribute("id",id);
			
			return "member/overlapIdCheck.html";
		}
		
}
