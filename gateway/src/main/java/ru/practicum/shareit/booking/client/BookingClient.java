package ru.practicum.shareit.booking.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.web.dto.BookingDto;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.validator.BookingValidator;

import java.util.Map;


@Service
public class BookingClient extends BaseClient {
  private final BookingValidator bookingValidator=new BookingValidator();
    private static final String API_PREFIX = "/bookings";

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(  builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> addBooking( int userId, BookingDto bookingDto) {
        bookingValidator.checkBookingDto(bookingDto);
        logger.info("save booking dto {}", bookingDto);
        return post("",userId,bookingDto);
    }

    public  ResponseEntity<Object> approvedBooking( int userId, Boolean approved, int bookingId) {
         Map<String,Object> parameters = Map.of( "approved", approved);
         logger.info("approved booking with id {}", bookingId);
         return patch("/"+bookingId+"?approved={approved}", Long.valueOf(userId),parameters,BookingDto.class);
    }

    public  ResponseEntity<Object> getBooking( int userId, String state, Integer from, Integer size) {
        Map<String,Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", Long.valueOf(userId),parameters);
    }

    public ResponseEntity<Object>  getBooking( int userId, int bookingId) {
        logger.info("get booking with id {}", bookingId);
        return get("/"+bookingId,userId);
    }


    public ResponseEntity<Object> getBookingOwner(int userId, String state, Integer from, Integer size) {
        Map<String,Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&from={from}&size={size}", Long.valueOf(userId),parameters);
    }


}
