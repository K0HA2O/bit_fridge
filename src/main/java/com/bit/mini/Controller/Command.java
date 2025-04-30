package com.bit.mini.Controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public interface Command {

	void execute(Model model);
}
