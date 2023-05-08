package com.example.demo.bot;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.demo.bot.authservice.BotAuthService;
import com.example.demo.controller.LoginController;
import com.example.demo.enterprise.model.Enterprise;
import com.example.demo.enterprise.model.EnterpriseDTO;
import com.example.demo.enterprise.service.EnterpriseService;
import com.example.demo.manager.model.Manager;
import com.example.demo.manager.service.ManagerService;
import com.example.demo.report.model.Report;
import com.example.demo.report.model.ReportDTO;
import com.example.demo.report.service.ReportService;
import com.example.demo.vehicle.model.Vehicle;
import com.example.demo.vehicle.model.VehicleDTO;
import com.example.demo.vehicle.repository.VehicleRepository;
import com.example.demo.vehicle.service.VehicleService;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
    private Message requestMessage = new Message();
    private final SendMessage response = new SendMessage();
//    private final Producer producerService;
    @Autowired
    private final ManagerService userService;
//    @Autowired
//    private final TelegramBotsApi telegramBotsApi;
    
    @Autowired
    private BotAuthService botAuthService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private ReportService reportService;

    private final String botUsername = "@autopark_spring_bot";
    private final String botToken = "6066821948:AAFtZ7H9OerK2jj8NmkYM_W-1u1i5mZN8Ro";

    public TelegramBot(TelegramBotsApi telegramBotsApi) throws TelegramApiException {
//            TelegramBotsApi telegramBotsApi,
//            @Value("${telegram-bot.name}") String botUsername,
//            @Value("${telegram-bot.token}") String botToken,
//            Producer producerService, UserService userService) throws TelegramApiException {
//        this.botUsername = botUsername;
//        this.botToken = botToken;
//        this.producerService = producerService;
//        this.userService = userService;

        this.userService = new ManagerService();
		telegramBotsApi.registerBot(this);
    }

    /**
     * Этот метод вызывается при получении обновлений через метод GetUpdates.
     *
     * @param request Получено обновление
     */
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update request) {

    	requestMessage = request.getMessage();
		response.setChatId(requestMessage.getChatId().toString());

    	if (requestMessage.getText().equals("/start")) {
    		
    		try {
				onStart(response);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		defaultMsg(response, "Представьтесь, пожалуйста");
    		return;
    	}
    	if (requestMessage.getText().contains("/login")) {
			
    		String login = requestMessage.getText().substring(7, 17);
    		log.info(login);
	    	Manager manager = userService.loadUserByUsername(login);
	    	if (manager != null) {
	    		defaultMsg(response, "Добро пожаловать, " + manager.getUsername());
	    		botAuthService.auth(manager.getUsername(), response.getChatId());
	    		return;
	    	}
	    	
	    	defaultMsg(response, "Таких не знаем...");
	    	
	    	return;
    	}
    	
    	if (requestMessage.getText().equals("/enterprise_list")) {
    		if (botAuthService.existsAuthByChatId(response.getChatId())) {
    			
    			List<EnterpriseDTO> enterprises = enterpriseService.findAllEnterprisesForManager(userService.loadUserByUsername(botAuthService.getByChatId(response.getChatId()).getManagerName()).getUuid());
    			
    			defaultMsg(response, "У вас " + enterprises.size() + " предприятий");
    			defaultMsg(response, "Вот их наименования и идентификаторы: ");
    			
    			for (EnterpriseDTO enterprise : enterprises) {
    				log.info(enterprise.getUuid() + "");
    				defaultMsg(response, enterprise.getName() + ":" + enterprise.getUuid());	
    			}
    			
    			
    			return;
    		}
    		defaultMsg(response, "Нет таких...");
    	}
    	
    	if (requestMessage.getText().contains("/vehicles")) {
    		UUID enterpriseUuid = UUID.fromString(requestMessage.getText().substring(10));
    		
    		List<VehicleDTO> vehicles = vehicleService.findAllByEnterpriseUuid(enterpriseUuid);
    		
    		defaultMsg(response, "А вот ее машинки");
    		
    		for (VehicleDTO vehicle : vehicles) {
    			
    			defaultMsg(response, vehicle.getUuid() + "");	
    			
    		}
    		
    	}
    	
    	if (requestMessage.getText().contains("/report")) {
    		log.info(requestMessage.getText());
    		UUID vehicleUuid = UUID.fromString(requestMessage.getText().substring(8, 44));
    		log.info(vehicleUuid + "");
    		String dates = requestMessage.getText().substring(44);
    		dates = dates.trim();
    		log.info(dates);
    		String[] splittedDates = dates.split(" ");
    		log.info(splittedDates[0]);
    		log.info(splittedDates[1]);
    		Long startDate = Long.valueOf(splittedDates[0]);
    		Long endDate = Long.valueOf(splittedDates[1]);
    		
    		List<ReportDTO> reports = reportService.reportsByVehicleAndStartAndEndDate(vehicleUuid, startDate, endDate);
    		
    		
    		defaultMsg(response, "Вот список отчетов: ");
    		
    		for (ReportDTO report : reports) {
    			
    			String msg = "";
    			
//    			TimeZone timeZone = TimeZone.getTimeZone(getCurrentUser().getTimezone());
//    			SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//    			date.setTimeZone(timeZone);
    			
    			msg += "Пробег: " + report.getResult();
    			msg += " С: " + report.getStartDate();
    			msg += " По: " + report.getEndDate();
    			
    			defaultMsg(response, msg);	
    			
    		}
    		
    	}
    	
    	
    	if (requestMessage.getText().equals("/logout")) { 
    		botAuthService.logoutByChatId(response.getChatId());
    		defaultMsg(response, "Разлогинився...");

    	}
		
		
    	
    }

    /**
     * Метод отправки сообщения со списком мыслей - по команде "/idea"
     *
     * @param response - метод обработки сообщения
     */
    private void onStart(SendMessage response) throws TelegramApiException {
//    	defaultMsg(response, "Тебя как звать то? \n");
//        if (userService.getUserList().isEmpty()) {
//            defaultMsg(response, "В списке нет мыслей. \n");
//        } else {
//            defaultMsg(response, "Вот список ваших мыслей: \n");
//            for (User txt : userService.getUserList()) {
//                response.setText(txt.toString());
//                execute(response);
//            }
//        }
    }

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     * @param msg - сообщение
     */
    private void defaultMsg(SendMessage response, String msg) {
        response.setText(msg);
        try {
			execute(response);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	public String getBotToken() {
		return botToken;
	}
}