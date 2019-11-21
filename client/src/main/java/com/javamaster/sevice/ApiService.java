package com.javamaster.sevice;

import com.javamaster.converter.CustomListToUser;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ApiService {

    private final String API_URL = "http://localhost:8080/api/v1/";

    private final RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder
                .basicAuthentication("admin", "admin")
                .build();
    }

    public List <User> users(){
        List users = restTemplate.getForObject(API_URL + "users", List.class, new HashMap<>());
        CustomListToUser customListToUser = new CustomListToUser();
        if (users != null) {
            return customListToUser.convert(users);
        }
        else return null;
    }
    public List <Role> roles() {
        List roles = restTemplate.getForObject(API_URL + "roles", List.class ,new  HashMap<>());
        CustomListToUser customListToUser = new CustomListToUser();
        if (roles != null) {
            return customListToUser.allRoles(roles);
        }
        else return null;
    }

    public User user(User user){
        Map<String, Object> map = new HashMap<>();
        map.put("customUser", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> request = new HttpEntity<>(map, headers);
        List list = restTemplate.postForObject(API_URL + "user", request, List.class);
        if (list != null){
            return user;
        } else return null;
    }

    public void deleteById(Long id){
        Map < String, Long > params = new HashMap < >();
        params.put("id", id);
        restTemplate.delete(API_URL + "user/{id}", params);
    }

    public User update(User user){
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> request = new HttpEntity<>(map, headers);
        restTemplate.put(API_URL + "user/update", request);

        return  user;
    }
    public User getUser(Long id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> request = new HttpEntity<>(map, headers);
        List list = restTemplate.postForObject(API_URL + "user/id", request, List.class);
        CustomListToUser customListToUser = new CustomListToUser();
        if (list != null) {
            return  customListToUser.convertToUser((LinkedHashMap) list.get(0));
        }
        return null;
    }
}
