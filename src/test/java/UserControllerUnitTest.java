import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Controller.UserController;
import org.example.Dto.UserDto;
import org.example.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.http.ResponseEntity;


import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;



@RunWith(MockitoJUnitRunner.class)
public class UserControllerUnitTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserName("narutoUnit");
        userDto.setAge(13);
        userDto.setLastName("uzumakiUnit");
        userDto.setEmail("uzumaki@email.com");

        when(userService.createUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDto)));

        verify(userService, times(1)).createUser(userDto);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(users)));

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void testFindUserById() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setUserName("Mona");
        userDto.setLastName("Lisa");
        userDto.setAge(25);

        when(userService.findUserById(userId)).thenReturn(ResponseEntity.ok(userDto));

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Mona"))
                .andExpect(jsonPath("$.lastName").value("Lisa"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    public void testFindUserBy_NonExistingId() throws Exception {
        Long userId = 19L;

        when(userService.findUserById(userId)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
