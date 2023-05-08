package com.example.demo.bot.authrepository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.bot.authmodel.BotAuth;

public interface BotAuthRepository extends CrudRepository<BotAuth, UUID> {

	boolean existsByChatId(String chatId);
	
	void removeByChatId(String chatId);
	
	BotAuth findByChatId(String chatId);
	
}
