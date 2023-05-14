import org.apache.coyote.Response;
import org.example.Dto.UserDto;
import org.example.ProjectForLearn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectForLearn.class ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createUser() {
        UserDto userDto = new UserDto();
        userDto.setUserName("Naruto");
        userDto.setLastName("Uzumaki");
        userDto.setAge(14);
        userDto.setPhone("1123311");

        ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity("/user/", userDto, UserDto.class);

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        UserDto createdUser = responseEntity.getBody();
        assertNotNull(createdUser);
    }

    @Test
    public void testFindAllUsers(){
        ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(
                "/user/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>() {}
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<UserDto> listOfUsers = responseEntity.getBody();
        assertNotNull(listOfUsers);
    }


}
