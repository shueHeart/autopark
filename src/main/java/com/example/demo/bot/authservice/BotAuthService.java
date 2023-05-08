package com.example.demo.bot.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bot.authmodel.BotAuth;
import com.example.demo.bot.authrepository.BotAuthRepository;

@Service
public class BotAuthService {

	@Autowired
	private BotAuthRepository botAuthRepository;
	
	public BotAuth auth(String managerName, String chatId) {
		
		BotAuth botauth = new BotAuth(managerName, chatId);
		
		return botAuthRepository.save(botauth);
		
	}
	
	
	public boolean existsAuthByChatId(String chatId) {
		
		return botAuthRepository.existsByChatId(chatId);
		
	}
	
	public void logoutByChatId(String chatId) {
		botAuthRepository.removeByChatId(chatId);
	}
	
	public BotAuth getByChatId(String chatId) {
		
		return botAuthRepository.findByChatId(chatId);
		
	}
	
}
