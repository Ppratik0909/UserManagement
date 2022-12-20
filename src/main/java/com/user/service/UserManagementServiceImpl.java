package com.user.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bindings.LoginForm;
import com.user.bindings.UnlockAccForm;
import com.user.bindings.UserRegForm;
import com.user.entity.CityMasterEntity;
import com.user.entity.CountryMasterEntity;
import com.user.entity.StateMasterEntity;
import com.user.entity.UserDtlsEntity;
import com.user.repository.CityMasterRepo;
import com.user.repository.CountryMasterRepo;
import com.user.repository.StateMasterRepo;
import com.user.repository.UserDtlsRepo;
import com.user.util.EmailUtils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private CountryMasterRepo countryRepo;
	@Autowired
	private StateMasterRepo stateRepo;

	@Autowired
	private CityMasterRepo cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String Login(LoginForm logform) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPassword(logform.getEmail(), logform.getPwd());

		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity != null && entity.getAccSatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		return "SUCCESS";
	}

	@Override
	public String emailCheck(String emailId) {
		UserDtlsEntity entity = userDtlsRepo.findByEmail(emailId);
		if (entity == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> loadCountries() {
		List<CountryMasterEntity> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		for (CountryMasterEntity Entity : countries) {
			countryMap.put(Entity.getCountryId(), Entity.getCoutryName());

		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStates(Integer countryId) {
		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();
		for (StateMasterEntity stateMasterEntity : states) {
			stateMap.put(stateMasterEntity.getCountryId(), stateMasterEntity.getStateName());
		}

		return stateMap;
	}

	@Override
	public Map<Integer, String> loadCities(Integer stateId) {
		List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);
		Map<Integer, String> citiesMap = new HashMap<>();
		for (CityMasterEntity cityMasterEntity : cities) {
			citiesMap.put(cityMasterEntity.getCityId(), cityMasterEntity.getCityName());
		}
		return citiesMap;
	}

	@Override
	public String registerUser(UserRegForm userform) {
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(userform, entity);
		entity.setAccSatus("LOCKED");
		entity.setPassword(generateRandomPwd());
		userDtlsRepo.save(entity);
		String email = userform.getEmail();
		String subject = "User Registration -Samarth IT.....!";

		String filename = "User Management.txt";

		String body = readMailBodyContent(filename, entity);

		boolean isSent = emailUtils.sendEmail(email, subject, body);
		if (isSent) {
			return "SUCCESS";
		}

		return "FAIL";
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccform) {
		if (!unlockAccform.getNewPwd().equals(unlockAccform.getConfirmNewPwd())) {
			return "Password and Confirm Password Should be Same";
		}
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPassword(unlockAccform.getEmail(),
				unlockAccform.getTempPwd());

		if (entity == null) {
			return "Incorrect Temporary Password";
		}
		entity.setPassword(unlockAccform.getNewPwd());
		entity.setAccSatus("UNLOCKED");
		userDtlsRepo.save(entity);

		return "Account Unlocked";
	}

	@Override
	public String forgotPwd(String email) {
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		if (entity == null) {
			return "Invaild Email Id";
		}
		String filname = "RecoverPassword.txt";
		String body = readMailBodyContent(filname, entity);
		String subject = "Recover Password -Samarth IT...!";
		boolean isSent = emailUtils.sendEmail(email, subject, body);
		if (isSent) {
			return "Password sent to registered email";

		}
		return null;
	}

	private String generateRandomPwd() {
		int leftLimit = 48; // numeral '0' 
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBodyContent(String fileName, UserDtlsEntity entity) {
		String mailBody = null;
		try {
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			mailBody = sb.toString();
			mailBody = mailBody.replace("{FNAME}", entity.getFname());
			mailBody = mailBody.replace("{LNAME}", entity.getLname());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPassword());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody = mailBody.replace("{PWD}", entity.getPassword());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

}
