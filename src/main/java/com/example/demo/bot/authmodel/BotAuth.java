package com.example.demo.bot.authmodel;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BotAuth {

	@Id
	@GeneratedValue
	private UUID uuid;
	
	private String managerName;
	
	private String chatId;

	public BotAuth() {
		
	}
	
	public BotAuth(String managerName, String chatId) {
		this.managerName = managerName;
		this.chatId = chatId;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	
	
	
}
