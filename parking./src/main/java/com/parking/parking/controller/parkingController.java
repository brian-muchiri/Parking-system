package com.parking.parking.controller;

import com.parking.parking.controller.utils.Global;
import com.parking.parking.controller.utils.Slot;
import com.parking.parking.models.DbConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.python.util.PythonInterpreter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Float.parseFloat;




@RestController
public class parkingController {
    Global globalValues = new Global();

    DbConfig dbConfig = new DbConfig();
    @GetMapping("/hello")
    public ModelAndView hello(Model model){
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("Street C", "true", "22","url__3","position"));
        mav.addObject("slots", slots);
        return mav;
    }
    @GetMapping("/")
    public ModelAndView Search(Model model) throws SQLException {
        ModelAndView mav = new ModelAndView("index");
        List<Slot> slots = new ArrayList<>();
        String query = "select * from places;";
        List<String[]> streets = dbConfig.executeQuery(query);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/parking-lots");
        int i=0;
        for (String[] street : streets) {
            String url = UriComponentsBuilder.fromUriString("/reserve").queryParam("id", street[2]).queryParam("lot-name", street[0]).toUriString();
            slots.add(new Slot(street[0], "Slots available: "+String.valueOf(42-globalValues.availableSlots()[i]), street[3]+" Street",  url,"position"));
            i+=1;
        }
        model.addAttribute("slots",slots);
        return mav;
    }

    @GetMapping("/reserve")
    public ModelAndView Details(@RequestParam("id") String id,@RequestParam("lot-name") String name,Model model){
        ModelAndView mav = new ModelAndView("reserve");
        String url = UriComponentsBuilder.fromUriString("/reserve-processing").toUriString();
        model.addAttribute("name",name);
        model.addAttribute("id",id);
        model.addAttribute("url",url   );
        return mav;
    }
    @GetMapping("/test")
    public ModelAndView test(@RequestParam("id") String id,@RequestParam("lot-name") String name,Model model){
        ModelAndView mav = new ModelAndView("reserve");
        System.out.println("kjhgfdsdfghjk");
        return mav;
    }
    @GetMapping("/test2")
    public ModelAndView tes2(@RequestParam("id") String id,@RequestParam("lot-name") String name,Model model){
        ModelAndView mav = new ModelAndView("reserve");
        System.out.println("kjhgfdsdfghjk--------------------");
        return mav;
    }
    @GetMapping("/reserve-processing")
    public ModelAndView reserve_processing(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("license_plate") String license_plate,
            @RequestParam("vehicle_type") String vehicle_type,
            @RequestParam("parking_duration") String parking_duration,
            @RequestParam("date") String date,
            @RequestParam("time") String timeEntry,
            @RequestParam("terms_and_conditions") String terms_and_conditions,
            Model model) throws SQLException {
        ModelAndView mav = null;
        try {
            mav = new ModelAndView("comfirmation");
            String auth = authToken();
            String slot = "C1R2S06";
            String departure = calculateDeparture(timeEntry, parking_duration);
            String charge = String.valueOf(50 * parseFloat(parking_duration));
            String query1 = "INSERT INTO reservations(date_, plate_number, size_, slot_number, entry_time, departure_time, charge, payment_id, auth) VALUES ('" + date + "', '" + license_plate + "', '" + vehicle_type + "', '" + slot + "', '" + timeEntry + "', '" + departure + "', " + charge + ", '101', '" + auth + "');";
            String query2 = "insert into slots(slot_id,occupied, reserved)values('" + "C1R2S06" + "', false, true) ;";
            dbConfig.executeQuery(query1);
            dbConfig.executeQuery(query2);


//            sendSms(phone,auth,slot);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }
    public String calculateDeparture(String entry, String time_){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime entryTime = LocalTime.parse(entry, formatter);
        LocalTime departureTime = entryTime.plusHours(Long.parseLong(time_));
        String departure = departureTime.format(formatter);
        System.out.println(departure.getClass());
        return  departure;
    }
    public String authToken(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int LENGTH = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }













}
