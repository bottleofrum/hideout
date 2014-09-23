package com.lylynx.hideout.signup;

import com.lylynx.hideout.spring.security.user.AccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";
    private final UserDetailsService userService;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    public SignupController(final AccountRepository accountRepository, final UserDetailsService userService,
                            final PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
        /*if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepository.save(signupForm.createAccount(passwordEncoder));
		userService.signin(account);
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";*/

        throw new RuntimeException();
    }
}
