package user.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import user.bean.UserDTO;
//import user.bean.UserDTO;
//import user.bean.UserPaging;
import user.service.UserService;


@Controller
@RequestMapping(value="user") // 중복처리되는 매핑 코드를 전역으로 처리 (슬래시 안써넣어도 자기가 알아서 구분자로 슬래시를 넣어줌)
public class UserController {
	
	@Autowired // UserService와 의존관계 형성
	private UserService userService;
	
	// 회원가입
	//@GetMapping(value="/user/writeForm")
	@RequestMapping(value="writeForm", method={RequestMethod.GET, RequestMethod.POST}) // 요청시 부르는 것
	public String writeForm() {
		return "/user/writeForm"; // jsp 찾아가자
	}
	
	// 아이디 중복조회
	@PostMapping(value="/isExistId")
	@ResponseBody // exist를 jsp가 아닌 실제 문자열이 넘어가게 함
	public String isExistId(@RequestParam String id) { 
		
		return userService.isExistId(id);
	}

	// 회원가입 post
	@PostMapping("write")
	@ResponseBody 
	public void write(@ModelAttribute UserDTO userDTO) {
		userService.write(userDTO);
	}
	
	@GetMapping(value="list") 		
	public String list(@RequestParam(required = false, defaultValue = "1") String pg, Model  model) {
		model.addAttribute("pg", pg);
		return "/user/list";
	}
	// 회원정보 조회
	@PostMapping(value="getUserList")
	@ResponseBody
	public Map<String, Object> getUserList(@RequestParam String pg) {
		return userService.getUserList(pg);
	}
	
	// 회원수정 페이지
	@GetMapping(value="updateForm") // 요청시 부르는 것
	public String userUpdateForm(@RequestParam String id, @RequestParam String pg, Model model) {
		model.addAttribute("id", id); // userUpdateForm으로 id파라미터 값 옮기기 위하여
		model.addAttribute("pg", pg);
		return "/user/updateForm"; // jsp 찾아가자
	}
	
	// 회원수정 post
	@PostMapping("getUser")
	@ResponseBody // 이거 안붙여주면 view로 뿌려줄 jsp 찾음
	public Optional<UserDTO> getUser(@RequestParam String id) { 
		
		return userService.getUser(id); 
	} 

	
	@PostMapping(value="update")
	@ResponseBody
	public void update(@RequestParam String id,
			@RequestParam String pwd,
			@RequestParam String name
			) {
		System.out.println(id);
		System.out.println(pwd);
		System.out.println(name);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		userDTO.setPwd(pwd);
		userDTO.setName(name);
		userService.update(userDTO);
	}
	
	
	// 회원정보 삭제
	@PostMapping(value="delete")
	@ResponseBody
	public void delete(@RequestParam String id) { 
		userService.delete(id);
	}
}