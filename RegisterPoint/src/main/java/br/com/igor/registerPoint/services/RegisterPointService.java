package br.com.igor.registerPoint.services;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.igor.registerPoint.domain.QueryRegisterPoint;
import br.com.igor.registerPoint.domain.RegisterPoint;
import br.com.igor.registerPoint.domain.dto.CpfDto;
import br.com.igor.registerPoint.domain.dto.RegistePointSearchDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import br.com.igor.registerPoint.domain.StatusCheck;
import br.com.igor.registerPoint.domain.User;
import br.com.igor.registerPoint.domain.dto.RegisterPointDto;
import br.com.igor.registerPoint.exceptions.RegisterPointNotFoundException;
import br.com.igor.registerPoint.exceptions.UserNotFoundException;
import br.com.igor.registerPoint.repositories.RegisterPointRepository;
import br.com.igor.registerPoint.repositories.UserRepository;

@Service
public class RegisterPointService {

    @Autowired
    private RegisterPointRepository registerPointRepository;

    @Autowired
    private UserRepository userRepository;


    public StringBuilder getAllRegisterByCpf(RegistePointSearchDTO data) {

        User user = userRepository.findByCpf(data.cpf());


        if (user != null) {
            List<RegisterPointDto> records = registerPointRepository.findRegisterPointById(user.getId());

            if (records != null) {
                return buildRegisterPoint(records, user, data);
            } else {
                throw new RegisterPointNotFoundException("Don't have any records for this user");
            }
        } else {
            throw new UserNotFoundException("User not Found");
        }


    }

    @Transactional
    public String createRigsterPoint(CpfDto data) {


        User user = userRepository.findByCpf(data.cpf());
        LocalDateTime now = LocalDateTime.now();

        if (user != null) {


            List<QueryRegisterPoint> listStatus = registerPointRepository.findRegisterByDay(now.getDayOfMonth(), now.getMonthValue(), now.getYear(), user.getId());
            if (listStatus.isEmpty()) {
                RegisterPoint registerPoint = new RegisterPoint(user, StatusCheck.IN, now);
                registerPointRepository.save(registerPoint);
            } else if (listStatus.size() == 1 && listStatus.get(0).status() == StatusCheck.IN) {
                RegisterPoint registerPoint = new RegisterPoint(user, StatusCheck.OUT, now);
                registerPointRepository.save(registerPoint);

            } else {
                QueryRegisterPoint queryregisterPoint = listStatus.stream()
                        .filter(registerPoints -> registerPoints.status() == StatusCheck.OUT).findFirst().orElse(null);
                RegisterPoint r = registerPointRepository.findById(queryregisterPoint.id()).orElseThrow(() -> new RegisterPointNotFoundException("Not Found Register Point"));
                registerPointRepository.RegisterARepeatDate(LocalDateTime.now(), r.getId());
            }

        }
        return "registred";
    }

    private StringBuilder buildRegisterPoint(List<RegisterPointDto> listDto, User user, RegistePointSearchDTO rpsDto) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        int lengthMonth = YearMonth.of(rpsDto.year(), rpsDto.month()).lengthOfMonth();

        LocalDate endOfMonth = LocalDate.of(rpsDto.year(), rpsDto.month(), lengthMonth);

        LocalDate currentDay = LocalDate.of(rpsDto.year(), rpsDto.month(), 1);


        LocalTime hoursExtra = LocalTime.of(00, 00, 00);
        LocalTime delays = LocalTime.of(00, 00, 00);


        List<RegisterPointDto> in = chooseList(listDto, StatusCheck.IN);
        List<RegisterPointDto> out = chooseList(listDto, StatusCheck.OUT);

        sb.append("Name: " + user.getName());
        sb.append("   Cpf: " + user.getCpf());
        sb.append("    Department: " + user.getDepartment() + "\n");
        sb.append("Start time of Workday     " + user.getStartOfTheWorkday());
        sb.append("        End time of Workday     " + user.getEndOfTheWorkday());

        sb.append("\nDate           Registers\n");
        int i = 0;
        while (!currentDay.isAfter(endOfMonth)) {


            sb.append(currentDay + " ");

            LocalDate entry;
            LocalDate exit;
            Integer indexIn = findRegisterForDay(in, currentDay);
            Integer indexOut = findRegisterForDay(out, currentDay);

            if (indexIn != null) entry = in.get(indexIn).date().toLocalDate();
            else entry = null;
            if (indexOut != null) exit = out.get(indexOut).date().toLocalDate();
            else exit = null;

            if (entry == null && exit == null) {
                sb.append("   Absence   ");
            } else {
                if (entry != null && entry.equals(currentDay)) {

                    sb.append(in.get(indexIn).date().format(hourFormatter) + "      ");
                } else sb.append("      ");
                if (exit != null && exit.equals(currentDay)) {
                    sb.append(out.get(indexOut).date().format(hourFormatter) + "      ");
                } else sb.append("      ");
                if (indexIn != null && indexOut != null) {

                    //in
                    LocalTime inDelaysOrExtra = getHourMinutesAndSeconds(in.get(indexIn).date().toLocalTime(), user.getStartOfTheWorkday());
                    if (isBefore(in.get(indexIn).date().toLocalTime(), user.getStartOfTheWorkday())) {
                        sb.append("   + " + inDelaysOrExtra.format(hourFormatter));
                        hoursExtra = plusHour(hoursExtra, inDelaysOrExtra);


                    } else {
                        sb.append("   - " + inDelaysOrExtra.format(hourFormatter));
                        delays = plusHour(delays, inDelaysOrExtra);

                    }


                    //out
                    LocalTime outDelaysOrExtra = getHourMinutesAndSeconds(out.get(indexOut).date().toLocalTime(), user.getEndOfTheWorkday());
                    if (isBefore(out.get(indexOut).date().toLocalTime(), user.getEndOfTheWorkday())) {
                        sb.append("   - " + outDelaysOrExtra.format(hourFormatter));
                        delays = plusHour(delays, outDelaysOrExtra);

                    } else {
                        sb.append("   + " + outDelaysOrExtra.format(hourFormatter));
                        hoursExtra = plusHour(hoursExtra, outDelaysOrExtra);
                    }


                }
            }


            sb.append(" \n");
            currentDay = currentDay.plusDays(1);

        }
        LocalTime freehours;
        sb.append("Hours  Extra: "+hoursExtra + " Delays: " + delays);
        if(hoursExtra.isAfter(delays)){
             freehours = minusHour(hoursExtra,delays);
            sb.append("\nfree hours: +" + freehours.format(hourFormatter));
        }else{
            freehours = minusHour(delays,hoursExtra);
            sb.append("\nfree hours: -" + freehours.format(hourFormatter));

        }
        return sb;

    }


    private LocalTime getHourMinutesAndSeconds(LocalTime data, LocalTime dataReg) {


        Duration durationBetween = Duration.between(data, dataReg);

        int seconds = Math.abs((int) durationBetween.getSeconds());

        return  LocalTime.of(seconds/3600,(seconds % 3600)/60,seconds % 60);

    }


    private LocalTime plusHour(LocalTime originHour, LocalTime plusTime){
        return originHour.plusHours(plusTime.getHour()).plusMinutes(plusTime.getMinute()).plusSeconds(plusTime.getSecond());
    }
    private LocalTime minusHour(LocalTime originHour, LocalTime plusTime){
        return originHour
                .minusHours(plusTime.getHour())
                .minusMinutes(plusTime.getMinute())
                .minusSeconds(plusTime.getSecond());
    }



    private Boolean isBefore(LocalTime data, LocalTime dataReg) {
        return data.isBefore(dataReg);
    }


        private List<RegisterPointDto> chooseList(List<RegisterPointDto> list, StatusCheck status){
        return  list.stream()
                .filter(registerPoint -> registerPoint.status() == status)
                .collect(Collectors.toList());
    }


    private Integer findRegisterForDay(List<RegisterPointDto> registerList, LocalDate date) {
        for (int i = 0;i< registerList.size(); i++) {
            LocalDate registerDate = registerList.get(i).date().toLocalDate();
            if (registerDate.equals(date)) {
                return i;
            }
        }
        return null;
    }

        
   }



